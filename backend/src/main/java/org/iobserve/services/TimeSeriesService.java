package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.dataaccessobjects.TimeSeriesDto;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;
import org.iobserve.models.util.TimeSeries;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class TimeSeriesService extends AbstractSystemComponentService<TimeSeries,TimeSeriesDto> {

    @Inject
    public TimeSeriesService(EntityManagerFactory entityManagerFactory, IEntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected TimeSeriesDto transformModelToDto(TimeSeries timeSeries) {
        return this.modelToDtoMapper.transform(timeSeries);
    }

    @Override
    protected TimeSeries transformDtoToModel(TimeSeriesDto timeSeriesDto) {
        return dtoToBasePropertyEntityMapper.transform(timeSeriesDto);
    }
}
