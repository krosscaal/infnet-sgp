/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.controller;

import br.edu.infnet.krossbyapi.domain.entity.Visitante;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.service.VisitanteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/visitante")
public class VisitanteController extends ControllerBase<Visitante, Long> {

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
    protected ResponseEntity<Visitante> acaoIncluir(@Valid Visitante dto)  {
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

    @GetMapping(value = "/cpf")
    public ResponseEntity<List<Visitante>> acaoBuscarPorCpf(@RequestParam(value = "cpf") String cpf) throws BusinessException {
        return ResponseEntity.ok(visitanteService.buscarPorCpf(cpf));
    }

    @GetMapping(value = "/data")
    public ResponseEntity<List<Visitante>> acaoBuscarVisitantesPorDataIngreso(@RequestParam(value = "data") String data) {
        return ResponseEntity.ok(visitanteService.buscarVisitantesPorDataIngresso(data));
    }
}
