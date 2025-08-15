/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface ControllerMap<T, K> {
    @GetMapping(value = "/map/{id}")
    public T obterPorIdMap(@PathVariable("id") K id);

    @GetMapping(value = "/map")
    public List<T> obterListaMap();

    @PostMapping(value = "/map")
    public T incluirMap(@RequestBody T objeto);

    @PutMapping(value = "/map/{id}")
    public T alterarMap(@PathVariable("id") K id, @RequestBody T objeto);

    @DeleteMapping(value = "/map/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirMap(@PathVariable("id") K id);

    @PatchMapping(value = "/map/{id}/inativar")
    public T inativarMap(@PathVariable("id") K id);
}
