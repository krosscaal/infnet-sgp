/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.Visitante;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.exception.UsuarioException;
import br.edu.infnet.krossbyapi.repository.VisitanteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VisitanteService implements ServiceBase<Visitante, Long>, ServiceMap<Visitante, Long>{

    private final VisitanteRepository visitanteRepository;
    private final UsuarioService usuarioService;
    private final Map<Long, Visitante> visitanteMap = new ConcurrentHashMap<>();
    private final AtomicLong visitanteId = new AtomicLong(1);

    public VisitanteService(VisitanteRepository visitanteRepository, UsuarioService usuarioService) {
        this.visitanteRepository = visitanteRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public Visitante buscarPorId(Long idObjeto) throws BusinessException {
        try {
            return this.buscarPorIdVisitante(idObjeto);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Visitante> listarTodos() {
        return visitanteRepository.findAll();
    }

    @Override
    public Visitante incluir(Visitante entidade) throws BusinessException {
        try {
            usuarioService.validarUsuario(entidade.getUsuarioVisitante());
            entidade.setId(null);
            return visitanteRepository.save(entidade);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Visitante alterar(Long idObjeto, Visitante entidade) throws BusinessException {
        try {
            Visitante visitanteObj = this.buscarPorIdVisitante(idObjeto);
            usuarioService.validarUsuario(entidade.getUsuarioVisitante());
            visitanteObj.setUsuarioVisitante(entidade.getUsuarioVisitante());
            visitanteObj.setCartaoAcesso(entidade.getCartaoAcesso());
            visitanteObj.setTipoAcesso(entidade.getTipoAcesso());
            visitanteObj.setUsuarioAutorizacao(entidade.getUsuarioAutorizacao());
            visitanteObj.setMoradiaDestinoVisitante(entidade.getMoradiaDestinoVisitante());
            visitanteObj.setObservacao(entidade.getObservacao());
            return visitanteRepository.save(visitanteObj);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void excluir(Long idObjeto) throws BusinessException {
        try {
            this.buscarPorIdVisitante(idObjeto);
            this.visitanteRepository.deleteById(idObjeto);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private Visitante buscarPorIdVisitante(Long idVisitante) throws UsuarioException {
        return visitanteRepository.findById(idVisitante).orElseThrow(()-> new UsuarioException("Usuário Visitante não encontrado!"));
    }


    @Override
    public Visitante incluirMap(Visitante objeto) {
        usuarioService.validarUsuario(objeto.getUsuarioVisitante());
        objeto.getUsuarioVisitante().setId(UsuarioService.usuarioId.getAndIncrement());
        objeto.setId(visitanteId.getAndIncrement());
        this.visitanteMap.put(objeto.getId(), objeto);
        return objeto;
    }

    @Override
    public Visitante alterarMap(Long idObjeto, Visitante objeto) {
        this.verificaExisteMap(idObjeto);
        Visitante visitante = this.visitanteMap.get(idObjeto);
        usuarioService.validarUsuario(objeto.getUsuarioVisitante());
        visitante.setUsuarioVisitante(objeto.getUsuarioVisitante());
        visitante.setCartaoAcesso(objeto.getCartaoAcesso());
        visitante.setTipoAcesso(objeto.getTipoAcesso());
        visitante.setUsuarioAutorizacao(objeto.getUsuarioAutorizacao());
        visitante.setMoradiaDestinoVisitante(objeto.getMoradiaDestinoVisitante());
        visitante.setObservacao(objeto.getObservacao());
        this.visitanteMap.put(idObjeto, visitante);
        return visitante;
    }

    @Override
    public Visitante buscarPorIdMap(Long idObjeto) {
        this.verificaExisteMap(idObjeto);
        return this.visitanteMap.get(idObjeto);
    }

    @Override
    public List<Visitante> buscarTodosMap() {
        return new ArrayList<>(visitanteMap.values());
    }

    @Override
    public void excluirMap(Long idObjeto) {
        this.verificaExisteMap(idObjeto);
        this.visitanteMap.remove(idObjeto);
    }
    private void verificaExisteMap(Long idObjeto) {
        if (!this.visitanteMap.containsKey(idObjeto)) {
            throw new BusinessException("Vistante não existe");
        }
    }
}
