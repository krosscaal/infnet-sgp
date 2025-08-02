/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.exception.BusinessException;

import java.util.List;

public interface ServiceBase<T, ID> {
    T buscar(ID idObjeto) throws BusinessException;
    List<T> listarTodos();
    T salvar(T entidade) throws BusinessException;
    T atualizar(ID idObjeto, T entidade) throws BusinessException;
    void apagar(ID idObjeto) throws BusinessException;
}
