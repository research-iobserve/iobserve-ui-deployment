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

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.Changelog;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.dataaccessobjects.RevisionDto;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;
import org.iobserve.models.util.AbstractBaseEntity;
import org.iobserve.models.util.AbstractRevisionedBean;
import org.iobserve.models.util.Revision;
import org.iobserve.services.websocket.ChangelogStreamService;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class ChangelogService extends AbstractSystemComponentService<Changelog, ChangelogDto> {

    private final HashMap<String, Revision> revisions = new HashMap<>();

    private final ChangelogStreamService changelogStreamService;

    @Inject
    public ChangelogService(final EntityManagerFactory entityManagerFactory, final IEntityToDtoMapper modelToDtoMapper,
            final ServiceLocator serviceLocator, final IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper,
            final ChangelogStreamService changelogStreamService) {

        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);

        this.changelogStreamService = changelogStreamService;
    }

    public synchronized void addChangelogs(final String systemId, final List<ChangelogDto> changelogs)
            throws ConstraintViolationException {

        final Date date = new Date();
        final Revision revision = this.getNextRevision(systemId);
        revision.setLastUpdate(date);

        // validate ChangelogList
        this.validate(changelogs);

        // process changelogs
        for (int i = 0; i < changelogs.size(); i++) {
            final ChangelogDto changelog = changelogs.get(i);
            final Long changelogSequence = (long) i;

            changelog.setId(this.generateId());
            changelog.setSystemId(systemId);
            changelog.setChangelogSequence(changelogSequence);
            changelog.setLastUpdate(date);
            changelog.setRevisionNumber(revision.getRevisionNumber());

            this.applyChangelog(changelog);
            this.persistChangelog(changelog);

            revision.setChangelogSequence(changelogSequence);
        }
        this.changelogStreamService.broadcastChangelogs(systemId, changelogs);
    }

    private void validate(final List<ChangelogDto> changelogs) throws ConstraintViolationException {
        for (int i = 0; i < changelogs.size(); i++) {
            final ChangelogDto changelog = changelogs.get(i);
            this.validateChangelog(changelog);
        }
    }

    private void validateChangelog(final ChangelogDto changelog) throws ConstraintViolationException {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final DataTransportObject dto = changelog.getData();

        final Class<?> dtoClass = dto.getClass().getAnnotation(ModelClassOfDto.class).service();
        @SuppressWarnings("unchecked")
        final AbstractService<?, DataTransportObject> service = (AbstractService<?, DataTransportObject>) this.serviceLocator
                .getService(dtoClass);
        final AbstractBaseEntity entity = service.transformDtoToModel(dto);

        final Set<ConstraintViolation<AbstractBaseEntity>> constraintViolations = validator.validate(entity);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("Changelog is invalid", constraintViolations);
        }
    }

    @Transactional
    private void persistChangelog(final ChangelogDto changelogDto) {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final Changelog changelog = this.transformDtoToModel(changelogDto);

        final EntityTransaction transaction = entityManager.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }
        entityManager.persist(changelog);
        transaction.commit();
        entityManager.close();

    }

    private void applyChangelog(final ChangelogDto changelog) {
        switch (changelog.getOperation()) {
        case CREATE:
            this.createEntity(changelog);
            break;
        case DELETE:
            this.deleteEntity(changelog);
            break;
        case APPEND:
            this.appendEntity(changelog);
            break;
        case UPDATE:
            this.updateEntity(changelog);
            break;
        default:
        }
    }

    @Transactional
    private void updateEntity(final ChangelogDto changelog) {

        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        final DataTransportObject dto = changelog.getData();
        final Class<? extends AbstractService<?, ?>> dtoClass = dto.getClass().getAnnotation(ModelClassOfDto.class)
                .service();
        @SuppressWarnings("unchecked")
        final AbstractService<?, DataTransportObject> service = (AbstractService<?, DataTransportObject>) this.serviceLocator
                .getService(dtoClass);

        final AbstractBaseEntity entity = service.transformDtoToModel(dto);

        this.setRevisionOfEntity(entity, changelog);

        final EntityTransaction transaction = entityManager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }

        entityManager.merge(entity);
        transaction.commit();
        entityManager.close();

    }

    /**
     * Universal append for TimeSeries, StatusInfo and SeriesElement Due to the uni-directional
     * mapping of the models, the objects only have to be persisted.
     *
     * @param changelog
     */
    @Transactional
    private void appendEntity(final ChangelogDto changelog) {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();

        final DataTransportObject dto = changelog.getData();
        final Class<?> dtoClass = dto.getClass().getAnnotation(ModelClassOfDto.class).service();
        @SuppressWarnings("unchecked")
        final AbstractService<?, DataTransportObject> service = (AbstractService<?, DataTransportObject>) this.serviceLocator
                .getService(dtoClass);

        final AbstractBaseEntity entity = service.transformDtoToModel(dto);

        if (!transaction.isActive()) {
            transaction.begin();
        }
        entityManager.persist(entity);
        transaction.commit();
        entityManager.close();
    }

    @Transactional
    private void deleteEntity(final ChangelogDto changelog) {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final ModelClassOfDto modelClass = changelog.getData().getClass().getAnnotation(ModelClassOfDto.class);

        final AbstractBaseEntity entity = entityManager.find(modelClass.value(), changelog.getData().getId());

        this.setRevisionOfEntity(entity, changelog);

        final EntityTransaction transaction = entityManager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        entityManager.remove(entity);
        transaction.commit();
        entityManager.close();

    }

    @Transactional
    private void createEntity(final ChangelogDto changelog) {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        final DataTransportObject dto = changelog.getData();
        final Class<?> dtoServiceClass = dto.getClass().getAnnotation(ModelClassOfDto.class).service();
        @SuppressWarnings("unchecked")
        final AbstractService<?, DataTransportObject> service = (AbstractService<?, DataTransportObject>) this.serviceLocator
                .getService(dtoServiceClass);

        final AbstractBaseEntity entity = service.transformDtoToModel(dto);

        this.setRevisionOfEntity(entity, changelog);

        final EntityTransaction transaction = entityManager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        entityManager.merge(entity);
        transaction.commit();
        entityManager.close();
    }

    private void setRevisionOfEntity(final AbstractBaseEntity entity, final ChangelogDto changelog) {
        if (entity instanceof AbstractRevisionedBean) {
            this.setRevisionOfEntity((AbstractRevisionedBean) entity, changelog);
        }
    }

    private void setRevisionOfEntity(final AbstractRevisionedBean revisionedBean, final ChangelogDto changelog) {
        revisionedBean.setRevisionNumber(changelog.getRevisionNumber());
        revisionedBean.setChangelogSequence(changelog.getChangelogSequence());
        revisionedBean.setLastUpdate(changelog.getLastUpdate());

        if (revisionedBean.getSystemId() == null) {
            revisionedBean.setSystemId(changelog.getSystemId());
        }
    }

    private Revision loadRevisionFromDatabase(final String systemId) {
        final Revision revision = new Revision();
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final Query query = entityManager.createQuery(
                "select changelog from Changelog changelog where systemId = :systemId order by changelog.revisionNumber desc")
                .setParameter("systemId", systemId);
        @SuppressWarnings("unchecked")
        final List<Changelog> results = query.getResultList();

        if (!results.isEmpty()) {
            final Changelog changelog = results.get(0);
            revision.setRevisionNumber(changelog.getRevisionNumber());
            revision.setChangelogSequence(changelog.getChangelogSequence());
        }
        entityManager.close();

        this.revisions.put(systemId, revision);

        return revision;
    }

    private Revision getNextRevision(final String systemId) {
        Revision revision;
        if (this.revisions.get(systemId) == null) {
            this.loadRevisionFromDatabase(systemId);
        }
        revision = this.revisions.get(systemId);

        revision.setRevisionNumber(revision.getRevisionNumber() + 1);
        revision.setChangelogSequence(0L);

        this.revisions.put(systemId, revision);

        return revision;
    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }

    public synchronized RevisionDto getLatestRevision(final String systemId) {
        Revision revision = this.revisions.get(systemId);
        if (revision == null) {
            revision = this.loadRevisionFromDatabase(systemId);
        }

        return this.modelToDtoMapper.transform(revision);
    }

    @Override
    protected ChangelogDto transformModelToDto(final Changelog changelog) {
        final ChangelogDto changelogDto = this.modelToDtoMapper.transform(changelog);

        final ObjectMapper objectMapper = new ObjectMapper();
        DataTransportObject data = null;
        try {
            data = objectMapper.readValue(changelog.getData(), DataTransportObject.class);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        changelogDto.setData(data);

        return changelogDto;
    }

    @Override
    protected Changelog transformDtoToModel(final ChangelogDto changelogDto) {
        final Changelog changelog = this.dtoToBasePropertyEntityMapper.transform(changelogDto);

        final ObjectMapper objectMapper = new ObjectMapper();
        String data = "";

        try {
            data = objectMapper.writeValueAsString(changelogDto.getData());
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }

        changelog.setData(data);

        return changelog;
    }

}
