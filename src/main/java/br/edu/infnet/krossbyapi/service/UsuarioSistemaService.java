/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.Usuario;
import br.edu.infnet.krossbyapi.domain.entity.UsuarioSistema;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.exception.UsuarioException;
import br.edu.infnet.krossbyapi.repository.UsuarioSistemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UsuarioSistemaService implements ServiceBase<UsuarioSistema, Long> {
    private final UsuarioSistemaRepository repository;
    private final UsuarioService usuarioService;

    public UsuarioSistemaService(UsuarioSistemaRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    @Override
    public UsuarioSistema buscarPorId(Long idObjeto) throws BusinessException {
        try {
            return this.buscarUsuarioSistemaPorId(idObjeto);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<UsuarioSistema> listarTodos() {
        return repository.findAll();
    }

    @Override
    public UsuarioSistema incluir(UsuarioSistema entidade) throws BusinessException {
        try {
            this.validarUsuarioSistema(entidade);
            Usuario usuarioObj = usuarioService.buscarPorId(entidade.getUsuario().getId());
            entidade.setId(null);
            entidade.setSituacao(EnumTipoSituacao.ATIVO);
            entidade.setUsuario(usuarioObj);
            return repository.save(entidade);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public UsuarioSistema alterar(Long idObjeto, UsuarioSistema entidade) throws BusinessException {
        try {
            UsuarioSistema usuarioSistema = this.buscarUsuarioSistemaPorId(idObjeto);
            Usuario usuarioObj = usuarioService.buscarPorId(entidade.getUsuario().getId());
            this.validarUsuarioSistema(entidade);
            usuarioSistema.setUsuario(usuarioObj);
            usuarioSistema.setEmail(entidade.getEmail());
            usuarioSistema.setSenha(entidade.getSenha());
            usuarioSistema.setPassword(entidade.getPassword());
            usuarioSistema.setSituacao(entidade.getSituacao());

            return repository.save(usuarioSistema);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void excluir(Long idObjeto) throws NoSuchElementException {
        this.buscarUsuarioSistemaPorId(idObjeto);
        repository.deleteById(idObjeto);
    }

    private UsuarioSistema buscarUsuarioSistemaPorId(Long idUsuario) throws NoSuchElementException {
        return repository.findById(idUsuario).orElseThrow(()-> new NoSuchElementException("Usuario Sistema não encontrado"));
    }

    private void validarUsuarioSistema(UsuarioSistema entidade) throws UsuarioException {
        if (entidade.getEmail().trim().isEmpty()) {
            throw new UsuarioException("Email do usuário é obrigatório!");
        }
    }

    public UsuarioSistema inativar(Long id) throws NoSuchElementException {
        UsuarioSistema usuarioSistema = this.buscarUsuarioSistemaPorId(id);
        if (EnumTipoSituacao.INATIVO.equals(usuarioSistema.getSituacao())) {
            throw new UsuarioException("Usuario Sistema já está inativo.");
        }
        usuarioSistema.setSituacao(EnumTipoSituacao.INATIVO);
        return repository.save(usuarioSistema);
    }

}
