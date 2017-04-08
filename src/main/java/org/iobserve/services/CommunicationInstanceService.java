package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.Communication;
import org.iobserve.models.CommunicationInstance;
import org.iobserve.models.ServiceInstance;
import org.iobserve.models.dataaccessobjects.CommunicationInstanceDto;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by cdor on 13.06.16.
 */
public class CommunicationInstanceService extends AbstractSystemComponentService<CommunicationInstance,CommunicationInstanceDto> {

    @Inject
    public CommunicationInstanceService(EntityManagerFactory entityManagerFactory, EntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, DtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected CommunicationInstanceDto transformModelToDto(CommunicationInstance communicationInstance) {
        return this.modelToDtoMapper.transform(communicationInstance);
    }

    @Override
    protected CommunicationInstance transformDtoToModel(CommunicationInstanceDto communicationInstanceDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final CommunicationInstance communicationInstance = dtoToBasePropertyEntityMapper.transform(communicationInstanceDto);

        communicationInstance.setCommunication(entityManager.find(Communication.class, communicationInstanceDto.getCommunicationId()));
        communicationInstance.setSource(entityManager.find(ServiceInstance.class, communicationInstanceDto.getSourceId()));
        communicationInstance.setTarget(entityManager.find(ServiceInstance.class, communicationInstanceDto.getTargetId()));

        entityManager.close();

        return communicationInstance;
    }


}
