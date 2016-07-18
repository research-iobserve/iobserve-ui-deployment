package org.iobserve.services;

import org.iobserve.models.dataaccessobjects.SeriesElementDto;
import org.iobserve.models.util.SeriesElement;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class SeriesElementService extends AbstractSystemComponentService<SeriesElement,SeriesElementDto> {

    @Override
    protected SeriesElementDto transformModelToDto(SeriesElement seriesElement) {
        return this.modelToDtoMapper.transform(seriesElement);
    }

    @Override
    protected SeriesElement transformDtoToModel(SeriesElementDto seriesElementDto) {
        final SeriesElement seriesElement = dtoToBasePropertyEntityMapper.transform(seriesElementDto);
        return seriesElement;
    }
}
