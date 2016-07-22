package org.iobserve.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iobserve.models.Changelog;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.dataaccessobjects.*;
import org.iobserve.models.util.*;
import org.iobserve.services.websocket.ChangelogStreamService;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class ChangelogService extends AbstractSystemComponentService<Changelog,ChangelogDto> {
    //TODO Concurencey issues with different revisions

    // TODO: revision number per System!
    private volatile Long revisionNumber; // TODO: synchronization necessary? addChangelogs is already synchronized
    private long changelogSequence;
    private Date lastUpdate;

    @Inject
    private ChangelogStreamService changelogStreamService;

    @Inject
    public ChangelogService(EntityManager entityManager) {
        Query query = entityManager.createQuery("select changelog from Changelog changelog order by changelog.revisionNumber desc");
        List<Changelog> results = query.getResultList();
        if(results.isEmpty()){
            this.revisionNumber = 0L;
        }else{
            this.revisionNumber = results.get(0).getRevisionNumber();
        }


        entityManager.close();
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


    public synchronized void addChangelogs(final String systemId, List<ChangelogDto> changelogs){

        final long revision = getIncreasedRevisionNumber();
        final Date date = new Date();
        this.lastUpdate = date;

        for (int i = 0; i < changelogs.size(); i++) {
            final ChangelogDto changelog = changelogs.get(i);
            Long changelogSequence = (long) i;
            changelog.setId(generateId());
            changelog.setSystemId(systemId);
            changelog.setRevisionNumber(revision);
            changelog.setChangelogSequence(changelogSequence);
            changelog.setLastUpdate(date);
            this.changelogSequence = i;

            applyChangelog(changelog);
            persistChangelog(changelog);

        }
        this.changelogStreamService.broadcastChangelogs(systemId, changelogs);

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

        final BaseEntity entity = service.transformDtoToModel(dto);

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
        final AbstractService service = (AbstractService) serviceLocator.getService(dtoClass);

        final BaseEntity entity = service.transformDtoToModel(dto);

        if(!transaction.isActive()) transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
        entityManager.close();
    }

    @Transactional
    private void deleteEntity(ChangelogDto changelog) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        ModelClassOfDto modelClass = changelog.getData().getClass().getAnnotation(ModelClassOfDto.class);

        BaseEntity entity = entityManager.find(modelClass.value(), changelog.getData().getId());

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
        final Class dtoClass = dto.getClass().getAnnotation(ModelClassOfDto.class).service();
        final AbstractService service = (AbstractService) serviceLocator.getService(dtoClass);

        final BaseEntity entity = service.transformDtoToModel(dto);

        setRevisionOfEntity(entity, changelog);

        final EntityTransaction transaction = entityManager.getTransaction();
        if(!transaction.isActive()) transaction.begin();
        entityManager.merge(entity);
        transaction.commit();
        entityManager.close();
    }

    private void setRevisionOfEntity(BaseEntity entity, ChangelogDto changelog){
        if(entity instanceof RevisionedBean){
            setRevisionOfEntity((RevisionedBean) entity, changelog);
        }
    }

    private void setRevisionOfEntity(RevisionedBean revisionedBean,ChangelogDto changelog){
        revisionedBean.setRevisionNumber(changelog.getRevisionNumber());
        revisionedBean.setChangelogSequence(changelog.getChangelogSequence());
        revisionedBean.setLastUpdate(changelog.getLastUpdate());
    }

    public Long getLatestRevisionNumber(){
     return this.revisionNumber;
    }

    public Long getIncreasedRevisionNumber(){
        synchronized (this.revisionNumber){
            this.revisionNumber++;
            return this.revisionNumber;
        }
    }

    public String generateId(){
        return UUID.randomUUID().toString();
    }

    public synchronized RevisionDto getLatestRevision() {
        RevisionDto revisionDto = new RevisionDto();
        // no need to synchronize, since addChangelogs is synchronized
        revisionDto.setRevisionNumber(this.getLatestRevisionNumber());
        revisionDto.setLastUpdate(this.lastUpdate);
        revisionDto.setChangelogSequence(this.changelogSequence);
        return revisionDto;
    }
}
