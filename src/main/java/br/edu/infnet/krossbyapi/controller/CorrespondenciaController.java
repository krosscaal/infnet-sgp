/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.controller;

import br.edu.infnet.krossbyapi.domain.entity.Correspondencia;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.service.CorrespondenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/correspondencia")
public class CorrespondenciaController extends ControllerBase<Correspondencia, Long> implements ControllerMap<Correspondencia, Long> {
    private final CorrespondenciaService service;

    public CorrespondenciaController(CorrespondenciaService service) {
        this.service = service;
    }


    @Override
    protected ResponseEntity<Correspondencia> acaoBuscarPorId(Long id) throws BusinessException {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Override
    protected ResponseEntity<List<Correspondencia>> acaoListarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Override
    protected ResponseEntity<Correspondencia> acaoIncluir(Correspondencia dto) throws BusinessException {
        return new ResponseEntity<>(service.incluir(dto), HttpStatus.CREATED);
    }

    @Override
    protected ResponseEntity<Correspondencia> acaoAlterar(Long id, Correspondencia dto) throws BusinessException {
        return ResponseEntity.ok(service.alterar(id, dto));
    }

    @Override
    protected void acaoExcluir(Long id) throws BusinessException {
        service.excluir(id);
    }

    @Override
    public Correspondencia obterPorIdMap(Long id) {
        return service.buscarPorIdMap(id);
    }

    @Override
    public List<Correspondencia> obterListaMap() {
        return service.buscarTodosMap();
    }

    @Override
    public Correspondencia incluirMap(Correspondencia objeto) {
        return service.incluirMap(objeto);
    }

    @Override
    public Correspondencia alterarMap(Long id, Correspondencia objeto) {
        return service.alterarMap(id, objeto);
    }

    @Override
    public void excluirMap(Long id) {
        service.excluirMap(id);
    }

}
