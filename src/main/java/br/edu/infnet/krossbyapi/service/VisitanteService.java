/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import br.edu.infnet.krossbyapi.domain.entity.Usuario;
import br.edu.infnet.krossbyapi.domain.entity.UsuarioCondominio;
import br.edu.infnet.krossbyapi.domain.entity.Visitante;
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
    private final MoradiaService moradiaService;
    private final UsuarioCondominioService usuarioCondominioService;
    private final Map<Long, Visitante> visitanteMap = new ConcurrentHashMap<>();
    private final AtomicLong visitanteId = new AtomicLong(1);

    public VisitanteService(VisitanteRepository visitanteRepository, UsuarioService usuarioService, MoradiaService moradiaService, UsuarioCondominioService usuarioCondominioService) {
        this.visitanteRepository = visitanteRepository;
        this.usuarioService = usuarioService;
        this.moradiaService = moradiaService;
        this.usuarioCondominioService = usuarioCondominioService;
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
            Usuario usuarioObj = usuarioService.buscarPorId(entidade.getUsuarioVisitante().getId());
            Moradia moradiaDestinoObj = moradiaService.buscarPorId(entidade.getMoradiaDestinoVisitante().getId());entidade.setId(null);
            UsuarioCondominio autorizadoObj = usuarioCondominioService.buscarPorId(entidade.getUsuarioAutorizacao().getId());
            entidade.setUsuarioVisitante(usuarioObj);
            entidade.setMoradiaDestinoVisitante(moradiaDestinoObj);
            entidade.setUsuarioAutorizacao(autorizadoObj);
            return visitanteRepository.save(entidade);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Visitante alterar(Long idObjeto, Visitante entidade) throws BusinessException {
        try {
            Usuario usuarioObj = usuarioService.buscarPorId(entidade.getUsuarioVisitante().getId());
            Moradia moradiaDestinoObj = moradiaService.buscarPorId(entidade.getMoradiaDestinoVisitante().getId());
            UsuarioCondominio autorizadoObj = usuarioCondominioService.buscarPorId(entidade.getUsuarioAutorizacao().getId());
            Visitante visitanteObj = this.buscarPorIdVisitante(idObjeto);

            visitanteObj.setUsuarioVisitante(usuarioObj);
            visitanteObj.setCartaoAcesso(entidade.getCartaoAcesso());
            visitanteObj.setTipoAcesso(entidade.getTipoAcesso());
            visitanteObj.setUsuarioAutorizacao(autorizadoObj);
            visitanteObj.setMoradiaDestinoVisitante(moradiaDestinoObj);
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
        return visitanteRepository.findById(idVisitante).orElseThrow(()-> new UsuarioException("Registro de Visitante não encontrado!"));
    }


    @Override
    public Visitante incluirMap(Visitante objeto) {
        Usuario usuarioObj = usuarioService.buscarPorIdMap(objeto.getUsuarioVisitante().getId());
        Moradia moradiaDestinoObj = moradiaService.buscarPorIdMap(objeto.getMoradiaDestinoVisitante().getId());
        UsuarioCondominio autorizadoObj = usuarioCondominioService.buscarPorIdMap(objeto.getUsuarioAutorizacao().getId());

        objeto.setUsuarioVisitante(usuarioObj);
        objeto.setMoradiaDestinoVisitante(moradiaDestinoObj);
        objeto.setUsuarioAutorizacao(autorizadoObj);
        objeto.setId(visitanteId.getAndIncrement());
        this.visitanteMap.put(objeto.getId(), objeto);
        return objeto;
    }

    @Override
    public Visitante alterarMap(Long idObjeto, Visitante objeto) {
        this.verificaExisteMap(idObjeto);
        Usuario usuarioObj = usuarioService.buscarPorIdMap(objeto.getUsuarioVisitante().getId());
        Moradia moradiaDestinoObj = moradiaService.buscarPorIdMap(objeto.getMoradiaDestinoVisitante().getId());
        UsuarioCondominio autorizadoObj = usuarioCondominioService.buscarPorIdMap(objeto.getUsuarioAutorizacao().getId());
        Visitante visitante = this.visitanteMap.get(idObjeto);

        visitante.setUsuarioVisitante(usuarioObj);
        visitante.setCartaoAcesso(objeto.getCartaoAcesso());
        visitante.setTipoAcesso(objeto.getTipoAcesso());
        visitante.setUsuarioAutorizacao(autorizadoObj);
        visitante.setMoradiaDestinoVisitante(moradiaDestinoObj);
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
            throw new BusinessException("Registro de Visitante não existe");
        }
    }
}
