/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.controller;

import br.edu.infnet.krossbyapi.domain.entity.Condominio;
import br.edu.infnet.krossbyapi.domain.record.CondominioRecord;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.service.CondominioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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


    @GetMapping(value = "/map/{id}")
    public Condominio obterPorIdMap(@PathVariable("id") Long idCondominio) {
        return service.buscarPorIdMap(idCondominio);
    }

    @GetMapping(value = "/map")
    public List<Condominio> obterListaMap() {
        return service.listarTodosMap();
    }

    @PostMapping(value = "/map")
    public Condominio incluirMap(@RequestBody CondominioRecord condominioRecord) throws BusinessException {
        return service.incluirMap(condominioRecord);
    }

    @PutMapping(value = "/map/{id}")
    public Condominio alterarMap(@PathVariable("id") Long idCondominio, @RequestBody CondominioRecord dto) throws BusinessException {
        return service.alterarMap(idCondominio, dto);
    }

    @DeleteMapping(value = "/map/{id}")
    public void excluirMap(@PathVariable("id") Long idCondominio) throws BusinessException {
        service.excluirMap(idCondominio);
    }

    @PatchMapping(value = "/map/{id}/inativar")
    public Condominio inativarMap(@PathVariable("id") Long idCondominio) throws BusinessException {
        return service.inativarMap(idCondominio);
    }

}
