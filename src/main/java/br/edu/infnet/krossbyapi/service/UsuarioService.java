/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.Usuario;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.exception.UsuarioException;
import br.edu.infnet.krossbyapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioService implements ServiceBase<Usuario, Long> , ServiceMap<Usuario, Long>{
    private final UsuarioRepository usuarioRepository;
    private final Map<Long, Usuario> usuarioMap = new ConcurrentHashMap<>();

    public static final AtomicLong usuarioId = new AtomicLong(1);

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new UsuarioException("Nome do Usuário é obrigatorio!");
        }
        if (usuario.getSobreNome() == null || usuario.getSobreNome().trim().isEmpty()) {
            throw new UsuarioException("SobreNome do Usuário é obrigatorio!");
        }
    }

    @Override
    public Usuario buscarPorId(Long idObjeto) throws BusinessException {
        try {
            return this.buscarUsuarioPorId(idObjeto);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario incluir(Usuario entidade) throws BusinessException {
        try {
            this.validarUsuario(entidade);
            entidade.setId(null);
            entidade.setSituacao(EnumTipoSituacao.ATIVO);
            return usuarioRepository.save(entidade);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Usuario alterar(Long idObjeto, Usuario entidade) throws BusinessException {
        try {
            this.validarUsuario(entidade);
            Usuario usuarioObj = this.buscarPorId(idObjeto);
            usuarioObj.setNome(entidade.getNome());
            usuarioObj.setSobreNome(entidade.getSobreNome());
            usuarioObj.setCpf(entidade.getCpf());
            usuarioObj.setRg(entidade.getRg());
            usuarioObj.setTelefone1(entidade.getTelefone1());
            usuarioObj.setTelefone2(entidade.getTelefone2());
            usuarioObj.setSituacao(entidade.getSituacao());
            return usuarioRepository.save(usuarioObj);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void excluir(Long idObjeto) throws BusinessException {
        usuarioRepository.deleteById(idObjeto);
    }

    private Usuario buscarUsuarioPorId(Long idObjeto) throws UsuarioException {
        return usuarioRepository.findById(idObjeto).orElseThrow(()-> new UsuarioException("Usuario não exite"));
    }

    public Usuario inativar(Long idObjeto) throws UsuarioException {
        Usuario usuario = this.buscarUsuarioPorId(idObjeto);
        if (EnumTipoSituacao.INATIVO.equals(usuario.getSituacao())) {
            throw new UsuarioException("Situacao do usuário já está inativa!");
        }
        usuario.setSituacao(EnumTipoSituacao.INATIVO);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario incluirMap(Usuario objeto) {
        this.validarUsuario(objeto);
        objeto.setId(usuarioId.getAndIncrement());
        objeto.setSituacao(EnumTipoSituacao.ATIVO);
        usuarioMap.put(objeto.getId(), objeto);
        return objeto;
    }
    private void verificaExisteMap(Long idObjeto) {
        if (!usuarioMap.containsKey(idObjeto)) {
            throw new UsuarioException("usuário não encontrado em Map");
        }
    }

    @Override
    public Usuario alterarMap(Long idObjeto, Usuario objeto) {
        this.verificaExisteMap(idObjeto);
        Usuario usuario = this.usuarioMap.get(idObjeto);
        usuario.setNome(objeto.getNome());
        usuario.setSobreNome(objeto.getSobreNome());
        usuario.setCpf(objeto.getCpf());
        usuario.setRg(objeto.getRg());
        usuario.setTelefone1(objeto.getTelefone1());
        usuario.setTelefone2(objeto.getTelefone2());
        usuario.setSituacao(objeto.getSituacao());
        this.usuarioMap.put(idObjeto, usuario);
        return usuario;
    }

    @Override
    public Usuario buscarPorIdMap(Long idObjeto) {
        this.verificaExisteMap(idObjeto);
        return this.usuarioMap.get(idObjeto);
    }

    @Override
    public List<Usuario> buscarTodosMap() {
        return new ArrayList<>(usuarioMap.values());
    }

    @Override
    public void excluirMap(Long idObjeto) {
        this.verificaExisteMap(idObjeto);
        this.usuarioMap.remove(idObjeto);
    }

    public Usuario inativarMap(Long idObjeto) {
        this.verificaExisteMap(idObjeto);
        Usuario usuario = this.usuarioMap.get(idObjeto);
        if (EnumTipoSituacao.INATIVO.equals(usuario.getSituacao())) {
            throw new UsuarioException("usuario em Map já está inativo");
        }
        usuario.setSituacao(EnumTipoSituacao.INATIVO);
        this.usuarioMap.put(idObjeto, usuario);
        return usuario;
    }
}
