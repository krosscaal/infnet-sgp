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

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CorrespondenciaService implements ServiceBase<Correspondencia, Long> {
    private final CorrespondenciaRepository correspondenciaRepository;
    private final MoradiaService moradiaService;
    private final UsuarioSistemaService usuarioSistemaService;

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
        return correspondenciaRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("Correspondencia com id:%d não encontrado", id)));
    }
}
