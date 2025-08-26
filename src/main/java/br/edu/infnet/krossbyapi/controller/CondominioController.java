/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.controller;

import br.edu.infnet.krossbyapi.domain.record.CondominioRecord;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.service.CondominioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(value = "/condominio")
public class CondominioController extends ControllerBase<CondominioRecord, Long> {

    private final CondominioService service;

    public CondominioController(CondominioService service) {
        this.service = service;
    }

    @Override
    protected ResponseEntity<CondominioRecord> acaoBuscarPorId(Long id) throws BusinessException {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Override
    protected ResponseEntity<List<CondominioRecord>> acaoListarTodos() {
        return ResponseEntity.ok().body(service.listarTodos());    }

    @Override
    protected ResponseEntity<CondominioRecord> acaoIncluir(CondominioRecord dto) throws BusinessException {
        return new ResponseEntity<>(service.incluir(dto), HttpStatus.CREATED);
    }

    @Override
    protected ResponseEntity<CondominioRecord> acaoAlterar(Long id, CondominioRecord dto) throws BusinessException {
        return ResponseEntity.ok(service.alterar(id, dto));
    }

    @Override
    protected void acaoExcluir(Long id) throws BusinessException {
        service.excluir(id);
    }
    @PatchMapping(value = "/{id}/inativar")
    protected ResponseEntity<CondominioRecord> acaoInativar(@PathVariable("id") Long id) throws BusinessException {
        return ResponseEntity.ok(service.inativar(id));
    }

}
