/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.UsuarioCondominio;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.exception.UsuarioException;
import br.edu.infnet.krossbyapi.repository.UsuarioCondominioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioCondominioService implements ServiceBase<UsuarioCondominio, Long>, ServiceMap<UsuarioCondominio, Long> {
    private final UsuarioCondominioRepository repository;
    private final UsuarioService usuarioService;
    private final Map<Long, UsuarioCondominio> usuarioCondominioMap = new ConcurrentHashMap<>();
    private final AtomicLong usuarioCondominioId = new AtomicLong(1);

    public UsuarioCondominioService(UsuarioCondominioRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    @Override
    public UsuarioCondominio buscarPorId(Long idObjeto) throws BusinessException {
        try {
            return this.buscarUsuarioCondominioPorId(idObjeto);

        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<UsuarioCondominio> listarTodos() {
        return repository.findAll();
    }

    @Override
    public UsuarioCondominio incluir(UsuarioCondominio entidade) throws BusinessException {
        try {
            usuarioService.validarUsuario(entidade.getUsuario());
            entidade.setId(null);
            entidade.setSituacao(EnumTipoSituacao.ATIVO);
            return repository.save(entidade);

        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public UsuarioCondominio alterar(Long idObjeto, UsuarioCondominio entidade) throws BusinessException {
        try {
            UsuarioCondominio usuarioCondominio = this.buscarUsuarioCondominioPorId(idObjeto);
            usuarioService.validarUsuario(entidade.getUsuario());
            usuarioCondominio.setUsuario(entidade.getUsuario());
            usuarioCondominio.setEmail(entidade.getEmail());
            usuarioCondominio.setTipoResidente(entidade.getTipoResidente());
            usuarioCondominio.setSituacao(entidade.getSituacao());
            usuarioCondominio.setMoradias(entidade.getMoradias());
            return repository.save(usuarioCondominio);

        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void excluir(Long idObjeto) throws BusinessException {
        try {
            this.buscarUsuarioCondominioPorId(idObjeto);
            this.repository.deleteById(idObjeto);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private UsuarioCondominio buscarUsuarioCondominioPorId(Long idUsuario) throws UsuarioException {
        return repository.findById(idUsuario).orElseThrow(()-> new UsuarioException("Usuario não encontrado"));
    }
    public UsuarioCondominio inativar(Long idUsuario) {
        UsuarioCondominio usuarioCondominioObj = this.buscarUsuarioCondominioPorId(idUsuario);
        if (EnumTipoSituacao.INATIVO.equals(usuarioCondominioObj.getSituacao())) {
            throw new BusinessException("Usuário Já está inativo.");
        }
        usuarioCondominioObj.setSituacao(EnumTipoSituacao.INATIVO);
        return repository.save(usuarioCondominioObj);
    }

    @Override
    public UsuarioCondominio incluirMap(UsuarioCondominio objeto) {
        usuarioService.validarUsuario(objeto.getUsuario());
        objeto.getUsuario().setId(UsuarioService.usuarioId.getAndIncrement());
        objeto.setId(usuarioCondominioId.getAndIncrement());
        objeto.setSituacao(EnumTipoSituacao.ATIVO);
        usuarioCondominioMap.put(objeto.getId(), objeto);
        return objeto;
    }

    @Override
    public UsuarioCondominio alterarMap(Long idObjeto, UsuarioCondominio objeto) {
        this.verificaExisteEmMap(idObjeto);
        UsuarioCondominio usuarioCondominioObj = this.usuarioCondominioMap.get(idObjeto);
        usuarioService.validarUsuario(objeto.getUsuario());
        usuarioCondominioObj.setUsuario(objeto.getUsuario());
        usuarioCondominioObj.setEmail(objeto.getEmail());
        usuarioCondominioObj.setTipoResidente(objeto.getTipoResidente());
        usuarioCondominioObj.setSituacao(objeto.getSituacao());
        usuarioCondominioObj.setMoradias(objeto.getMoradias());
        this.usuarioCondominioMap.put(idObjeto, usuarioCondominioObj);
        return usuarioCondominioObj;
    }

    @Override
    public UsuarioCondominio buscarPorIdMap(Long idObjeto) {
        this.verificaExisteEmMap(idObjeto);
        return this.usuarioCondominioMap.get(idObjeto);
    }

    @Override
    public List<UsuarioCondominio> buscarTodosMap() {
        return new ArrayList<>(usuarioCondominioMap.values());
    }

    @Override
    public void excluirMap(Long idObjeto) {
        this.verificaExisteEmMap(idObjeto);
        this.usuarioCondominioMap.remove(idObjeto);
    }

    private void verificaExisteEmMap(Long idUsuario) {
        if (!this.usuarioCondominioMap.containsKey(idUsuario)) {
            throw new BusinessException("USuário não existe.");
        }
    }

    public UsuarioCondominio inativarMap(Long idUsuario) {
        this.verificaExisteEmMap(idUsuario);
        UsuarioCondominio usuarioCondominioObj = this.usuarioCondominioMap.get(idUsuario);
        if (EnumTipoSituacao.INATIVO.equals(usuarioCondominioObj.getSituacao())) {
            throw new BusinessException("Usuário já está inativo");
        }
        usuarioCondominioObj.setSituacao(EnumTipoSituacao.INATIVO);
        this.usuarioCondominioMap.put(idUsuario, usuarioCondominioObj);
        return usuarioCondominioObj;
    }
}
