package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.dataaccessobjects.SeriesElementDto;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;
import org.iobserve.models.util.SeriesElement;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class SeriesElementService extends AbstractSystemComponentService<SeriesElement,SeriesElementDto> {

    @Inject
    public SeriesElementService(EntityManagerFactory entityManagerFactory, IEntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected SeriesElementDto transformModelToDto(SeriesElement seriesElement) {
        return this.modelToDtoMapper.transform(seriesElement);
    }

    @Override
    protected SeriesElement transformDtoToModel(SeriesElementDto seriesElementDto) {
        return dtoToBasePropertyEntityMapper.transform(seriesElementDto);
    }
}
