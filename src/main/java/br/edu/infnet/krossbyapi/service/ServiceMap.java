/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.service;

import java.util.List;

public interface ServiceMap<T, ID> {
    T incluirMap(T objeto);
    T alterarMap(ID idObjeto, T objeto);
    T buscarPorIdMap(ID idObjeto);
    List<T> buscarTodosMap();
    void excluirMap(ID idObjeto);

}
