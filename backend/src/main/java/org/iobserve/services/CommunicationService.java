package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.*;
import org.iobserve.models.dataaccessobjects.CommunicationDto;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by cdor on 13.06.16.
 */
public class CommunicationService extends AbstractSystemComponentService<Communication,CommunicationDto> {

    @Inject
    public CommunicationService(EntityManagerFactory entityManagerFactory, EntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, DtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected CommunicationDto transformModelToDto(Communication communication) {
        return this.modelToDtoMapper.transform(communication);
    }

    @Override
    protected Communication transformDtoToModel(CommunicationDto communicationDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Communication communication = dtoToBasePropertyEntityMapper.transform(communicationDto);

        communication.setSource(entityManager.find(org.iobserve.models.Service.class, communicationDto.getSourceId()));
        communication.setTarget(entityManager.find(org.iobserve.models.Service.class, communicationDto.getTargetId()));
        entityManager.close();

        return communication;
    }
}
