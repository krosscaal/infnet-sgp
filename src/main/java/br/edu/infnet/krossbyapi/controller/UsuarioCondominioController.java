/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.controller;

import br.edu.infnet.krossbyapi.domain.entity.UsuarioCondominio;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.service.UsuarioCondominioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario-condominio")
public class UsuarioCondominioController extends ControllerBase<UsuarioCondominio, Long> {
    private final UsuarioCondominioService service;

    public UsuarioCondominioController(UsuarioCondominioService service) {
        this.service = service;
    }

    @Override
    protected ResponseEntity<UsuarioCondominio> acaoBuscarPorId(Long id) throws BusinessException {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Override
    protected ResponseEntity<List<UsuarioCondominio>> acaoListarTodos() {
        return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<UsuarioCondominio> acaoIncluir(UsuarioCondominio dto) throws BusinessException {
        return new ResponseEntity<>(service.incluir(dto), HttpStatus.CREATED);
    }

    @Override
    protected ResponseEntity<UsuarioCondominio> acaoAlterar(Long id, UsuarioCondominio dto) throws BusinessException {
        return ResponseEntity.ok(service.alterar(id, dto));
    }

    @Override
    protected void acaoExcluir(Long id) throws BusinessException {
        service.excluir(id);
    }

    @PatchMapping(value = "/{id}/inativar")
    public ResponseEntity<UsuarioCondominio> inativar(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(service.inativar(id));
    }
}
