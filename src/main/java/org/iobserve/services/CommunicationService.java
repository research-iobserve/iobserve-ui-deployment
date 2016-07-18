package org.iobserve.services;

import org.iobserve.models.*;
import org.iobserve.models.dataaccessobjects.CommunicationDto;

import javax.persistence.EntityManager;

/**
 * Created by cdor on 13.06.16.
 */
public class CommunicationService extends AbstractSystemComponentService<Communication,CommunicationDto> {

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
