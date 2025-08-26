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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements ServiceBase<Usuario, Long> {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validarUsuario(Usuario usuario) {
        if (usuario.getNome() != null && usuario.getNome().trim().length() < 4) {
            throw new UsuarioException("Nome do Usuário é obrigatorio, e deve possuir mínimo 4 letras!");
        }
        if (usuario.getSobreNome() != null && usuario.getSobreNome().trim().length() < 5) {
            throw new UsuarioException("SobreNome do Usuário é obrigatorio, é deve possuir mínimo 5 letras!");
        }

    }

    @Override
    public Usuario buscarPorId(Long idObjeto) {
        try {
            return this.buscarUsuarioPorId(idObjeto);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario incluir(Usuario entidade) {
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
        try {
            this.buscarUsuarioPorId(idObjeto);
            usuarioRepository.deleteById(idObjeto);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Usuário não pode ser apagado por estar em uso atualmente");
        }
    }

    private Usuario buscarUsuarioPorId(Long idObjeto) throws UsuarioException {
        return usuarioRepository.findById(idObjeto).orElseThrow(()-> new UsuarioException(String.format("Usuário informado com id %d, não existe", idObjeto)));
    }

    public Usuario inativar(Long idObjeto) throws UsuarioException {
        Usuario usuario = this.buscarUsuarioPorId(idObjeto);
        if (EnumTipoSituacao.INATIVO.equals(usuario.getSituacao())) {
            throw new UsuarioException("Situacao do usuário já está inativa!");
        }
        usuario.setSituacao(EnumTipoSituacao.INATIVO);
        return usuarioRepository.save(usuario);
    }
}
