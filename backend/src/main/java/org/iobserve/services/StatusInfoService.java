package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.dataaccessobjects.StatusInfoDto;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;
import org.iobserve.models.util.StatusInfo;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class StatusInfoService extends AbstractSystemComponentService<StatusInfo,StatusInfoDto>{

    @Inject
    public StatusInfoService(EntityManagerFactory entityManagerFactory, IEntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected StatusInfoDto transformModelToDto(StatusInfo statusInfo) {
        return this.modelToDtoMapper.transform(statusInfo);
    }

    @Override
    protected StatusInfo transformDtoToModel(StatusInfoDto statusInfoDto) {
        return dtoToBasePropertyEntityMapper.transform(statusInfoDto);
    }
}
