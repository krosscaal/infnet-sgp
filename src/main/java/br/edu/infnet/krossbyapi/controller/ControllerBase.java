/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.controller;

import br.edu.infnet.krossbyapi.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public abstract class ControllerBase<T, K> {

    protected ControllerBase() {}

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<T> obter(@PathVariable("id") K id) throws BusinessException {
        return this.acaoBuscarPorId(id);
    }

    protected abstract ResponseEntity<T> acaoBuscarPorId(K id) throws BusinessException;


    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<T>> listar() {
        return this.acaoListarTodos();
    }

    protected abstract ResponseEntity<List<T>> acaoListarTodos();

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<T> incluir(@RequestBody T dto) throws BusinessException {
        return this.acaoIncluir(dto);
    }

    protected abstract ResponseEntity<T> acaoIncluir(T dto) throws BusinessException;

    @PutMapping(value = "/{id}")
    public ResponseEntity<T> alterar(@PathVariable("id") K id, @RequestBody T dto) throws BusinessException {
        return this.acaoAlterar(id, dto);
    }

    protected abstract ResponseEntity<T> acaoAlterar(K id, T dto) throws BusinessException;

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") K id) throws BusinessException {
        this.acaoExcluir(id);
    }

    protected abstract void acaoExcluir(K id) throws BusinessException;
}
