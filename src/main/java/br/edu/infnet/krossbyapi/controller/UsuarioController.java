/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.controller;

import br.edu.infnet.krossbyapi.domain.entity.Usuario;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController extends ControllerBase<Usuario, Long> implements ControllerMap<Usuario, Long>{
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    protected ResponseEntity<Usuario> acaoBuscarPorId(Long id) throws BusinessException {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @Override
    protected ResponseEntity<List<Usuario>> acaoListarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @Override
    protected ResponseEntity<Usuario> acaoIncluir(Usuario dto) throws BusinessException {
        return new ResponseEntity<>(usuarioService.incluir(dto), HttpStatus.CREATED);
    }

    @Override
    protected ResponseEntity<Usuario> acaoAlterar(Long id, Usuario dto) throws BusinessException {
        return ResponseEntity.ok(usuarioService.alterar(id, dto));
    }

    @Override
    protected void acaoExcluir(Long id) throws BusinessException {
        usuarioService.excluir(id);
    }

    @PatchMapping(value = "/{id}/inativar")
    public ResponseEntity<Usuario> inativar(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(usuarioService.inativar(id));
    }

    @Override
    public Usuario obterPorIdMap(Long id) {
        return usuarioService.buscarPorIdMap(id);
    }

    @Override
    public List<Usuario> obterListaMap() {
        return usuarioService.buscarTodosMap();
    }

    @Override
    public Usuario incluirMap(Usuario objeto) {
        return usuarioService.incluirMap(objeto);
    }

    @Override
    public Usuario alterarMap(Long id, Usuario objeto) {
        return usuarioService.alterarMap(id, objeto);
    }

    @Override
    public void excluirMap(Long id) {
        usuarioService.excluirMap(id);
    }

    @PatchMapping(value = "/map/{id}/instivar")
    public Usuario inativarMap(@PathVariable("id") Long id) {
        return usuarioService.inativarMap(id);
    }
}
