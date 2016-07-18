package org.iobserve.services;

import org.iobserve.models.dataaccessobjects.StatusInfoDto;
import org.iobserve.models.dataaccessobjects.TimeSeriesDto;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class StatusInfoService extends AbstractSystemComponentService<StatusInfo,StatusInfoDto>{
    @Override
    protected StatusInfoDto transformModelToDto(StatusInfo statusInfo) {
        return this.modelToDtoMapper.transform(statusInfo);
    }

    @Override
    protected StatusInfo transformDtoToModel(StatusInfoDto statusInfoDto) {
        final StatusInfo statusInfo = dtoToBasePropertyEntityMapper.transform(statusInfoDto);
        return statusInfo;
    }
}
