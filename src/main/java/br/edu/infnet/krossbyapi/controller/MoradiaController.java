/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.controller;

import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.service.MoradiaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/moradia")
public class MoradiaController extends ControllerBase<Moradia, Long> implements ControllerMap<Moradia, Long>{
    private final MoradiaService moradiaService;

    public MoradiaController(MoradiaService moradiaService) {
        this.moradiaService = moradiaService;
    }


    @Override
    protected ResponseEntity<Moradia> acaoBuscarPorId(Long id) throws BusinessException {
        return ResponseEntity.ok(this.moradiaService.buscarPorId(id));
    }

    @Override
    protected ResponseEntity<List<Moradia>> acaoListarTodos() {
        return ResponseEntity.ok(this.moradiaService.listarTodos());
    }

    @Override
    protected ResponseEntity<Moradia> acaoIncluir(Moradia dto) throws BusinessException {
        return new ResponseEntity<>(moradiaService.incluir(dto), HttpStatus.CREATED);
    }

    @Override
    protected ResponseEntity<Moradia> acaoAlterar(Long id, Moradia dto) throws BusinessException {
        return ResponseEntity.ok(moradiaService.alterar(id, dto));
    }

    @PatchMapping(value = "/{id}/inativar")
    public ResponseEntity<Moradia> inativar(@PathVariable Long id) throws BusinessException {
        return new ResponseEntity<>(moradiaService.inativar(id), HttpStatus.OK);
    }

    @Override
    protected void acaoExcluir(Long id) throws BusinessException {
        moradiaService.excluir(id);
    }

    @Override
    public Moradia obterPorIdMap(Long id) {
        return moradiaService.buscarPorIdMap(id);
    }

    @Override
    public List<Moradia> obterListaMap() {
        return moradiaService.buscarTodosMap();
    }

    @Override
    public Moradia incluirMap(Moradia objeto) {
        return moradiaService.incluirMap(objeto);
    }

    @Override
    public Moradia alterarMap(Long id, Moradia objeto) {
        return moradiaService.alterarMap(id, objeto);
    }

    @Override
    public void excluirMap(Long id) {
        moradiaService.excluirMap(id);
    }

    @PatchMapping(value = "/map/{id}/instivar")
    public Moradia inativarMap(@PathVariable("id") Long id) {
        return moradiaService.inativarMap(id);
    }
}
