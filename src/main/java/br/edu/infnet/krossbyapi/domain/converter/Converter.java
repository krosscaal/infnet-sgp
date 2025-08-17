/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.converter;

public interface Converter<E, D> {
    D toDTO(E entity);
    E toEntity(D dto);
}
