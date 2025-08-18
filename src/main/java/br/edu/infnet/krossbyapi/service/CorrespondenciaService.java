/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.Correspondencia;
import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import br.edu.infnet.krossbyapi.domain.entity.UsuarioSistema;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.repository.CorrespondenciaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CorrespondenciaService implements ServiceBase<Correspondencia, Long>, ServiceMap<Correspondencia, Long> {
    private final CorrespondenciaRepository correspondenciaRepository;
    private final MoradiaService moradiaService;
    private final UsuarioSistemaService usuarioSistemaService;
    private final Map<Long, Correspondencia> correspondenciaMap = new ConcurrentHashMap<>();
    private final AtomicLong idCorrespondencia = new AtomicLong(1);

    public CorrespondenciaService(CorrespondenciaRepository correspondenciaRepository, MoradiaService moradiaService, UsuarioSistemaService usuarioSistemaService) {
        this.correspondenciaRepository = correspondenciaRepository;
        this.moradiaService = moradiaService;
        this.usuarioSistemaService = usuarioSistemaService;
    }

    @Override
    public Correspondencia buscarPorId(Long idObjeto) throws BusinessException {
        return this.buscarCorrespondenciaPorId(idObjeto);
    }

    @Override
    public List<Correspondencia> listarTodos() {
        return correspondenciaRepository.findAll();
    }

    @Override
    public Correspondencia incluir(Correspondencia entidade) throws BusinessException {
        Moradia moradia = moradiaService.buscarPorId(entidade.getMoradiaEntrega().getId());
        UsuarioSistema usuarioRecepcao = usuarioSistemaService.buscarPorId(entidade.getUsuarioRecepcao().getId());
        this.validarCorrespondencia(entidade);
        entidade.setId(null);
        entidade.setMoradiaEntrega(moradia);
        entidade.setUsuarioRecepcao(usuarioRecepcao);
        return correspondenciaRepository.save(entidade);
    }

    private void validarCorrespondencia(Correspondencia entidade) {
        if (entidade.getMoradiaEntrega() == null) {
            throw new BusinessException("campo moradia é obrigatório");
        }
        if (entidade.getNomeDestinatario().isEmpty()) {
            throw new BusinessException("Campo destinatario é obrigatório");
        }

    }

    @Override
    public Correspondencia alterar(Long idObjeto, Correspondencia entidade) throws BusinessException {
        Moradia moradia = moradiaService.buscarPorId(entidade.getMoradiaEntrega().getId());
        UsuarioSistema usuarioRecepcao = usuarioSistemaService.buscarPorId(entidade.getUsuarioRecepcao().getId());
        this.validarCorrespondencia(entidade);
        Correspondencia correspondencia = this.buscarCorrespondenciaPorId(idObjeto);
        correspondencia.setMoradiaEntrega(moradia);
        correspondencia.setUsuarioRecepcao(usuarioRecepcao);
        correspondencia.setEmailDestinatario(entidade.getEmailDestinatario());
        correspondencia.setNomeDestinatario(entidade.getNomeDestinatario());
        correspondencia.setEmailDestinatario(entidade.getEmailDestinatario());
        correspondencia.setCodigoIdentificadorDaEntrega(entidade.getCodigoIdentificadorDaEntrega());
        correspondencia.setNomeMoradorRecepcao(entidade.getNomeMoradorRecepcao());
        correspondencia.setTelefoneDestinatario(entidade.getTelefoneDestinatario());
        return correspondenciaRepository.save(correspondencia);
    }

    @Override
    public void excluir(Long idObjeto) throws BusinessException {
        this.buscarCorrespondenciaPorId(idObjeto);
        this.correspondenciaRepository.deleteById(idObjeto);
    }

    private Correspondencia buscarCorrespondenciaPorId(Long id) {
        return correspondenciaRepository.findById(id).orElseThrow(()-> new BusinessException(String.format("Correspondencia com id:%d não encontrado", id)));
    }

    @Override
    public Correspondencia incluirMap(Correspondencia objeto) {
        Moradia moradia = moradiaService.buscarPorIdMap(objeto.getMoradiaEntrega().getId());
        UsuarioSistema usuarioRecepcao = usuarioSistemaService.buscarPorIdMap(objeto.getUsuarioRecepcao().getId());
        this.validarCorrespondencia(objeto);
        objeto.setId(idCorrespondencia.getAndIncrement());
        objeto.setMoradiaEntrega(moradia);
        objeto.setUsuarioRecepcao(usuarioRecepcao);
        this.correspondenciaMap.put(objeto.getId(), objeto);
        return objeto;
    }

    @Override
    public Correspondencia alterarMap(Long idObjeto, Correspondencia objeto) {
        Moradia moradia = moradiaService.buscarPorIdMap(objeto.getMoradiaEntrega().getId());
        UsuarioSistema usuarioRecepcao = usuarioSistemaService.buscarPorIdMap(objeto.getUsuarioRecepcao().getId());
        this.validarCorrespondencia(objeto);
        this.verificaExisteMap(idObjeto);
        Correspondencia correspondencia = this.correspondenciaMap.get(idObjeto);
        correspondencia.setEmailDestinatario(objeto.getEmailDestinatario());
        correspondencia.setNomeDestinatario(objeto.getNomeDestinatario());
        correspondencia.setEmailDestinatario(objeto.getEmailDestinatario());
        correspondencia.setCodigoIdentificadorDaEntrega(objeto.getCodigoIdentificadorDaEntrega());
        correspondencia.setNomeMoradorRecepcao(objeto.getNomeMoradorRecepcao());
        correspondencia.setTelefoneDestinatario(objeto.getTelefoneDestinatario());
        correspondencia.setMoradiaEntrega(moradia);
        correspondencia.setUsuarioRecepcao(usuarioRecepcao);
        this.correspondenciaMap.put(idObjeto, correspondencia);
        return correspondencia;
    }

    @Override
    public Correspondencia buscarPorIdMap(Long idObjeto) {
        this.verificaExisteMap(idObjeto);
        return this.correspondenciaMap.get(idObjeto);
    }

    @Override
    public List<Correspondencia> buscarTodosMap() {
        return new ArrayList<>(correspondenciaMap.values());
    }

    @Override
    public void excluirMap(Long idObjeto) {
        this.verificaExisteMap(idObjeto);
        this.correspondenciaMap.remove(idObjeto);
    }
    public void verificaExisteMap(Long id) {
        if (!this.correspondenciaMap.containsKey(id)) {
            throw new BusinessException(String.format("correspondencia com id:%d não encontrado em Map", id));
        }
    }
}
