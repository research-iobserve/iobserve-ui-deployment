package org.iobserve.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.Changelog;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.dataaccessobjects.*;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;
import org.iobserve.models.util.*;
import org.iobserve.services.websocket.ChangelogStreamService;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.*;
import java.io.IOException;
import java.util.*;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class ChangelogService extends AbstractSystemComponentService<Changelog,ChangelogDto> {

    private final HashMap<String,Revision> revisions = new HashMap<>();

    private final ChangelogStreamService changelogStreamService;

    @Inject
    public ChangelogService(EntityManagerFactory entityManagerFactory, IEntityToDtoMapper modelToDtoMapper,
                            ServiceLocator serviceLocator, IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper,
                            ChangelogStreamService changelogStreamService) {

        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);

        this.changelogStreamService = changelogStreamService;
    }

    public synchronized void addChangelogs(final String systemId, List<ChangelogDto> changelogs) throws ConstraintViolationException {

        final Date date = new Date();
        final Revision revision = getNextRevision(systemId);
        revision.setLastUpdate(date);

        //validate ChangelogList
        validate(changelogs);

        //process changelogs
        for (int i = 0; i < changelogs.size(); i++) {
            final ChangelogDto changelog = changelogs.get(i);
            final Long changelogSequence = (long) i;

            changelog.setId(generateId());
            changelog.setSystemId(systemId);
            changelog.setChangelogSequence(changelogSequence);
            changelog.setLastUpdate(date);
            changelog.setRevisionNumber(revision.getRevisionNumber());

            applyChangelog(changelog);
            persistChangelog(changelog);

            revision.setChangelogSequence(changelogSequence);
        }
        this.changelogStreamService.broadcastChangelogs(systemId, changelogs);
    }

    private void validate(List<ChangelogDto> changelogs) throws ConstraintViolationException{
        for (int i = 0; i < changelogs.size(); i++) {
            final ChangelogDto changelog = changelogs.get(i);
            validateChangelog(changelog);
        }
    }

    private void validateChangelog(ChangelogDto changelog) throws ConstraintViolationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        DataTransportObject dto = changelog.getData();

        final Class dtoClass = dto.getClass().getAnnotation(ModelClassOfDto.class).service();
        final AbstractService service = (AbstractService) serviceLocator.getService(dtoClass);
        final AbstractBaseEntity entity = service.transformDtoToModel(dto);

        Set<ConstraintViolation<AbstractBaseEntity>> constraintViolations = validator.validate(entity);

        if(constraintViolations.size() > 0) {
            throw new ConstraintViolationException("Changelog is invalid", constraintViolations);
        }
    }


    @Transactional
    private void persistChangelog(ChangelogDto changelogDto){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Changelog changelog = this.transformDtoToModel(changelogDto);

        EntityTransaction transaction = entityManager.getTransaction();

        if(!transaction.isActive()) transaction.begin();
        entityManager.persist(changelog);
        transaction.commit();
        entityManager.close();

    }


    private void applyChangelog(ChangelogDto changelog){
        switch (changelog.getOperation()){
            case CREATE:
                createEntity(changelog);
                break;
            case DELETE:
                deleteEntity(changelog);
                break;
            case APPEND:
                appendEntity(changelog);
                break;
            case UPDATE:
                updateEntity(changelog);
                break;
            default:
        }
    }

    @Transactional
    private void updateEntity(ChangelogDto changelog) {

        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        final DataTransportObject dto = changelog.getData();
        final Class dtoClass = dto.getClass().getAnnotation(ModelClassOfDto.class).service();
        final AbstractService service = (AbstractService) serviceLocator.getService(dtoClass);

        final AbstractBaseEntity entity = service.transformDtoToModel(dto);

        setRevisionOfEntity(entity, changelog);

        final EntityTransaction transaction = entityManager.getTransaction();
        if(!transaction.isActive()) transaction.begin();
        entityManager.merge(entity);
        transaction.commit();
        entityManager.close();


    }

    /**
     * Universal append for TimeSeries, StatusInfo and SeriesElement
     * Due to the uni-directional mapping of the models, the objects only
     * have to be persisted.
     * @param changelog
     */
    @Transactional
    private void appendEntity(ChangelogDto changelog) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();

        final DataTransportObject dto = changelog.getData();
        final Class dtoClass = dto.getClass().getAnnotation(ModelClassOfDto.class).service();
        final AbstractService service = (AbstractService) this.serviceLocator.getService(dtoClass);

        final AbstractBaseEntity entity = service.transformDtoToModel(dto);

        if(!transaction.isActive()) transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
        entityManager.close();
    }

    @Transactional
    private void deleteEntity(ChangelogDto changelog) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        ModelClassOfDto modelClass = changelog.getData().getClass().getAnnotation(ModelClassOfDto.class);

        AbstractBaseEntity entity = entityManager.find(modelClass.value(), changelog.getData().getId());

        setRevisionOfEntity(entity, changelog);

        final EntityTransaction transaction = entityManager.getTransaction();
        if(!transaction.isActive()) transaction.begin();
        entityManager.remove(entity);
        transaction.commit();
        entityManager.close();

    }

    @Transactional
    private void createEntity(ChangelogDto changelog) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        final DataTransportObject dto = changelog.getData();
        final Class dtoServiceClass = dto.getClass().getAnnotation(ModelClassOfDto.class).service();
        final AbstractService service = (AbstractService) this.serviceLocator.getService(dtoServiceClass);

        final AbstractBaseEntity entity = service.transformDtoToModel(dto);

        setRevisionOfEntity(entity, changelog);

        final EntityTransaction transaction = entityManager.getTransaction();
        if(!transaction.isActive()) transaction.begin();
        entityManager.merge(entity);
        transaction.commit();
        entityManager.close();
    }


    private void setRevisionOfEntity(AbstractBaseEntity entity, ChangelogDto changelog){
        if(entity instanceof AbstractRevisionedBean){
            setRevisionOfEntity((AbstractRevisionedBean) entity, changelog);
        }
    }

    private void setRevisionOfEntity(AbstractRevisionedBean revisionedBean,ChangelogDto changelog){
        revisionedBean.setRevisionNumber(changelog.getRevisionNumber());
        revisionedBean.setChangelogSequence(changelog.getChangelogSequence());
        revisionedBean.setLastUpdate(changelog.getLastUpdate());

        if(revisionedBean.getSystemId() == null){
            revisionedBean.setSystemId(changelog.getSystemId());
        }
    }

    private Revision loadRevisionFromDatabase(String systemId){
        final Revision revision = new Revision();
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("select changelog from Changelog changelog where systemId = :systemId order by changelog.revisionNumber desc")
                .setParameter("systemId", systemId);
        List<Changelog> results = query.getResultList();

        if(!results.isEmpty()){
            Changelog changelog = results.get(0);
            revision.setRevisionNumber(changelog.getRevisionNumber());
            revision.setChangelogSequence(changelog.getChangelogSequence());
        }
        entityManager.close();

        this.revisions.put(systemId,revision);

        return revision;
    }

    private Revision getNextRevision(String systemId){
        Revision revision;
        if(this.revisions.get(systemId) == null){
            loadRevisionFromDatabase(systemId);
        }
        revision = this.revisions.get(systemId);

        revision.setRevisionNumber(revision.getRevisionNumber()+1);
        revision.setChangelogSequence(0L);

        this.revisions.put(systemId, revision);

        return revision;
    }

    public String generateId(){
        return UUID.randomUUID().toString();
    }

    public synchronized RevisionDto getLatestRevision(String systemId) {
        Revision revision  = revisions.get(systemId);
        if(revision == null){
            revision = loadRevisionFromDatabase(systemId);
        }

        return modelToDtoMapper.transform(revision);
    }

    @Override
    protected ChangelogDto transformModelToDto(Changelog changelog) {
        ChangelogDto changelogDto = this.modelToDtoMapper.transform(changelog);

        ObjectMapper objectMapper = new ObjectMapper();
        DataTransportObject data = null;
        try {
            data = objectMapper.readValue(changelog.getData(),DataTransportObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        changelogDto.setData(data);

        return changelogDto;
    }

    @Override
    protected Changelog transformDtoToModel(ChangelogDto changelogDto) {
        final Changelog changelog = dtoToBasePropertyEntityMapper.transform(changelogDto);

        final ObjectMapper objectMapper = new ObjectMapper();
        String data = "";

        try {
            data = objectMapper.writeValueAsString(changelogDto.getData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        changelog.setData(data);

        return changelog;
    }

}
