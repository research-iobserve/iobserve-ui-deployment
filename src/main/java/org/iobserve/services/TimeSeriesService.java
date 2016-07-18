package org.iobserve.services;

import org.iobserve.models.dataaccessobjects.TimeSeriesDto;
import org.iobserve.models.util.TimeSeries;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class TimeSeriesService extends AbstractSystemComponentService<TimeSeries,TimeSeriesDto> {
    @Override
    protected TimeSeriesDto transformModelToDto(TimeSeries timeSeries) {
        return this.modelToDtoMapper.transform(timeSeries);
    }

    @Override
    protected TimeSeries transformDtoToModel(TimeSeriesDto timeSeriesDto) {
        final TimeSeries series = dtoToBasePropertyEntityMapper.transform(timeSeriesDto);
        return series;
    }
}
