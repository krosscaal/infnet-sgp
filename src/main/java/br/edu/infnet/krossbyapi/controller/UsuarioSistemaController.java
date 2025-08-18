/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.controller;

import br.edu.infnet.krossbyapi.domain.entity.UsuarioSistema;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.service.UsuarioSistemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario-sistema")
public class UsuarioSistemaController extends ControllerBase<UsuarioSistema, Long> implements ControllerMap<UsuarioSistema, Long>{

    private final UsuarioSistemaService service;

    public UsuarioSistemaController(UsuarioSistemaService service) {
        this.service = service;
    }

    @Override
    protected ResponseEntity<UsuarioSistema> acaoBuscarPorId(Long id) throws BusinessException {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Override
    protected ResponseEntity<List<UsuarioSistema>> acaoListarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Override
    protected ResponseEntity<UsuarioSistema> acaoIncluir(UsuarioSistema dto) throws BusinessException {
        return new ResponseEntity<>(service.incluir(dto), HttpStatus.CREATED);
    }

    @Override
    protected ResponseEntity<UsuarioSistema> acaoAlterar(Long id, UsuarioSistema dto) throws BusinessException {
        return ResponseEntity.ok(service.alterar(id, dto));
    }

    @Override
    protected void acaoExcluir(Long id) throws BusinessException {
        service.excluir(id);
    }

    @PatchMapping(value = "/{id}/inativar")
    public ResponseEntity<UsuarioSistema> inativar(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(service.inativar(id));
    }

    @Override
    public UsuarioSistema obterPorIdMap(Long id) {
        return service.buscarPorIdMap(id);
    }

    @Override
    public List<UsuarioSistema> obterListaMap() {
        return service.buscarTodosMap();
    }

    @Override
    public UsuarioSistema incluirMap(UsuarioSistema objeto) {
        return service.incluirMap(objeto);
    }

    @Override
    public UsuarioSistema alterarMap(Long id, UsuarioSistema objeto) {
        return service.alterarMap(id, objeto);
    }

    @Override
    public void excluirMap(Long id) {
        service.excluirMap(id);
    }

    @PatchMapping(value = "/map/{id}/inativar")
    public UsuarioSistema inativarMap(@PathVariable("id") Long id) {
        return service.inativarMap(id);
    }
}
