/***************************************************************************
 * Copyright (C) 2017 iObserve Project (https://www.iobserve-devops.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package org.iobserve.services;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.Communication;
import org.iobserve.models.dataaccessobjects.CommunicationDto;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;

/**
 * Created by cdor on 13.06.16.
 */
public class CommunicationService extends AbstractSystemComponentService<Communication, CommunicationDto> {

    @Inject
    public CommunicationService(final EntityManagerFactory entityManagerFactory,
            final IEntityToDtoMapper modelToDtoMapper, final ServiceLocator serviceLocator,
            final IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected CommunicationDto transformModelToDto(final Communication communication) {
        return this.modelToDtoMapper.transform(communication);
    }

    @Override
    protected Communication transformDtoToModel(final CommunicationDto communicationDto) {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final Communication communication = this.dtoToBasePropertyEntityMapper.transform(communicationDto);

        communication.setSource(entityManager.find(org.iobserve.models.Service.class, communicationDto.getSourceId()));
        communication.setTarget(entityManager.find(org.iobserve.models.Service.class, communicationDto.getTargetId()));
        entityManager.close();

        return communication;
    }
}
