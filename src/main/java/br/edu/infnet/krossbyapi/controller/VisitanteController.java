/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.controller;

import br.edu.infnet.krossbyapi.domain.entity.Visitante;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.service.VisitanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/visitante")
public class VisitanteController extends ControllerBase<Visitante, Long> implements ControllerMap<Visitante, Long>{

    private final VisitanteService visitanteService;

    public VisitanteController(VisitanteService visitanteService) {
        this.visitanteService = visitanteService;
    }


    @Override
    protected ResponseEntity<Visitante> acaoBuscarPorId(Long id) throws BusinessException {
        return ResponseEntity.ok(visitanteService.buscarPorId(id));
    }

    @Override
    protected ResponseEntity<List<Visitante>> acaoListarTodos() {
        return ResponseEntity.ok(visitanteService.listarTodos());
    }

    @Override
    protected ResponseEntity<Visitante> acaoIncluir(Visitante dto) throws BusinessException {
        return new ResponseEntity<>(visitanteService.incluir(dto), HttpStatus.CREATED);
    }

    @Override
    protected ResponseEntity<Visitante> acaoAlterar(Long id, Visitante dto) throws BusinessException {
        return ResponseEntity.ok(visitanteService.alterar(id, dto));
    }

    @Override
    protected void acaoExcluir(Long id) throws BusinessException {
        visitanteService.excluir(id);
    }

    @Override
    public Visitante obterPorIdMap(Long id) {
        return visitanteService.buscarPorIdMap(id);
    }

    @Override
    public List<Visitante> obterListaMap() {
        return visitanteService.buscarTodosMap();
    }

    @Override
    public Visitante incluirMap(Visitante objeto) {
        return visitanteService.incluirMap(objeto);
    }

    @Override
    public Visitante alterarMap(Long id, Visitante objeto) {
        return visitanteService.alterarMap(id, objeto);
    }

    @Override
    public void excluirMap(Long id) {
        visitanteService.excluirMap(id);
    }

}
