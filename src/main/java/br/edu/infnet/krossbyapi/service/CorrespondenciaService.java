/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.Correspondencia;
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
    private final Map<Long, Correspondencia> correspondenciaMap = new ConcurrentHashMap<>();
    private final AtomicLong idCorrespondencia = new AtomicLong(1);

    public CorrespondenciaService(CorrespondenciaRepository correspondenciaRepository) {
        this.correspondenciaRepository = correspondenciaRepository;
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
        this.validarCorrespondencia(entidade);
        entidade.setId(null);
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
        this.validarCorrespondencia(entidade);
        Correspondencia correspondencia = this.buscarCorrespondenciaPorId(idObjeto);
        correspondencia.setMoradiaEntrega(entidade.getMoradiaEntrega());
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
        return correspondenciaRepository.findById(id).orElseThrow(()-> new BusinessException("id Correspondencia não encontrado"));
    }

    @Override
    public Correspondencia incluirMap(Correspondencia objeto) {
        this.validarCorrespondencia(objeto);
        objeto.setId(idCorrespondencia.getAndIncrement());
        this.correspondenciaMap.put(objeto.getId(), objeto);
        return objeto;
    }

    @Override
    public Correspondencia alterarMap(Long idObjeto, Correspondencia objeto) {
        this.validarCorrespondencia(objeto);
        this.verificaExisteMap(idObjeto);
        Correspondencia correspondencia = this.correspondenciaMap.get(idObjeto);
        correspondencia.setMoradiaEntrega(objeto.getMoradiaEntrega());
        correspondencia.setEmailDestinatario(objeto.getEmailDestinatario());
        correspondencia.setNomeDestinatario(objeto.getNomeDestinatario());
        correspondencia.setEmailDestinatario(objeto.getEmailDestinatario());
        correspondencia.setCodigoIdentificadorDaEntrega(objeto.getCodigoIdentificadorDaEntrega());
        correspondencia.setNomeMoradorRecepcao(objeto.getNomeMoradorRecepcao());
        correspondencia.setTelefoneDestinatario(objeto.getTelefoneDestinatario());
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
            throw new BusinessException("id correspondencia não encontrado em Map");
        }
    }
}
