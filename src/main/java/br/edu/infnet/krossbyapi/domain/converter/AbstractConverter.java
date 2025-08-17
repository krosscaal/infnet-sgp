/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.domain.converter;

import org.modelmapper.ModelMapper;

public abstract class AbstractConverter<E, D> implements Converter<E, D> {
    protected ModelMapper modelMapper;

    private final Class<E> entityClass;
    private final Class<D> dtoClass;


    protected AbstractConverter(ModelMapper modelMapper, Class<E> entityClass, Class<D> dtoClass) {
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public D toDTO(E entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, dtoClass);
    }

    @Override
    public E toEntity(D dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, entityClass);
    }
    protected abstract void configureMapping();

}
