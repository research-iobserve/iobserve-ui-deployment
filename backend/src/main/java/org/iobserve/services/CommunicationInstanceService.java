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
import org.iobserve.models.CommunicationInstance;
import org.iobserve.models.ServiceInstance;
import org.iobserve.models.dataaccessobjects.CommunicationInstanceDto;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;

/**
 * Created by cdor on 13.06.16.
 */
public class CommunicationInstanceService
        extends AbstractSystemComponentService<CommunicationInstance, CommunicationInstanceDto> {

    @Inject
    public CommunicationInstanceService(final EntityManagerFactory entityManagerFactory,
            final IEntityToDtoMapper modelToDtoMapper, final ServiceLocator serviceLocator,
            final IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected CommunicationInstanceDto transformModelToDto(final CommunicationInstance communicationInstance) {
        return this.modelToDtoMapper.transform(communicationInstance);
    }

    @Override
    protected CommunicationInstance transformDtoToModel(final CommunicationInstanceDto communicationInstanceDto) {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final CommunicationInstance communicationInstance = this.dtoToBasePropertyEntityMapper
                .transform(communicationInstanceDto);

        communicationInstance.setCommunication(
                entityManager.find(Communication.class, communicationInstanceDto.getCommunicationId()));
        communicationInstance
                .setSource(entityManager.find(ServiceInstance.class, communicationInstanceDto.getSourceId()));
        communicationInstance
                .setTarget(entityManager.find(ServiceInstance.class, communicationInstanceDto.getTargetId()));

        entityManager.close();

        return communicationInstance;
    }

}
