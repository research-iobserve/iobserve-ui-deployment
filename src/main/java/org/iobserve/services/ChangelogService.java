package org.iobserve.services;

import org.iobserve.models.Changelog;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.models.mappers.DtoToBaseEntityMapper;
import org.iobserve.models.util.BaseEntity;
import org.iobserve.models.util.RevisionedBean;
import org.iobserve.models.util.TimeSeries;
import org.iobserve.models.util.TimeSeriesDto;
import org.iobserve.services.websocket.ChangelogStreamService;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class ChangelogService extends AbstractSystemComponentService<Changelog,ChangelogDto> {
    //TODO Concurencey issues with different revisions

    private volatile Long revisionNumber;

    @Inject
    private ChangelogStreamService changelogStreamService;

    @Inject
    private DtoToBaseEntityMapper dtoToBaseEntityMapper;

    public ChangelogService() {
        //TODO
        //Query query = entityManager.createQuery("select changelog from Changelog changelog order by changelog.revisionNumber desc");
        revisionNumber = 0L;
        //dtoToBaseEntityMapper = new DtoToBaseEntityMapper();
    }

    @Override
    protected ChangelogDto transformModelToDto(Changelog changelog) {
           return null;//this.modelToDtoMapper.transform(changelog);
    }

    public synchronized void addChangelogs(final String systemId, List<ChangelogDto> changelogs){

        final long revision = getNewRevisionNumber();
        final Date date = new Date();

        for (int i = 0; i < changelogs.size(); i++) {
            final ChangelogDto changelog = changelogs.get(i);

            changelog.setRevisionNumber(revision);
            changelog.setChangelogSequence(new Long(i));
            changelog.setLastUpdate(date);
            applyChangelog(changelog);
            changelogStreamService.broadcastChangelog(systemId,changelog);
        }

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
                appentEntity(changelog);
                break;
            case UPDATE:
                updateEntity(changelog);
                break;
            default:
        }
    }

    @Transactional
    private void updateEntity(ChangelogDto changelog) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        BaseEntity entity = dtoToBaseEntityMapper.transform(changelog.getData());

        setRevidionOfEntity(entity, changelog);

        final EntityTransaction transaction = entityManager.getTransaction();
        if(!transaction.isActive()) transaction.begin();
        entityManager.merge(entity);
        transaction.commit();
        entityManager.close();


    }

    @Transactional
    private void appentEntity(ChangelogDto changelog) {
        //TODO Append more than timeseries only
        if(changelog.getData() instanceof TimeSeriesDto){
            TimeSeriesDto seriesDto = (TimeSeriesDto) changelog.getData();
            TimeSeries series = dtoToBaseEntityMapper.transform(seriesDto);
            //TODO
        }

    }

    @Transactional
    private void deleteEntity(ChangelogDto changelog) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ModelClassOfDto modelClass = changelog.getData().getClass().getAnnotation(ModelClassOfDto.class);

        BaseEntity entity = entityManager.find(modelClass.value(), changelog.getData().getId());

        setRevidionOfEntity(entity, changelog);

        final EntityTransaction transaction = entityManager.getTransaction();
        if(!transaction.isActive()) transaction.begin();
        entityManager.remove(entity);
        transaction.commit();
        entityManager.close();

    }

    @Transactional
    private void createEntity(ChangelogDto changelog) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        BaseEntity entity = dtoToBaseEntityMapper.transform(changelog.getData());

        setRevidionOfEntity(entity, changelog);

        final EntityTransaction transaction = entityManager.getTransaction();
        if(!transaction.isActive()) transaction.begin();
        entityManager.merge(entity);
        transaction.commit();
        entityManager.close();
    }

    private void setRevidionOfEntity(BaseEntity entity, ChangelogDto changelog){
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
     return revisionNumber;
    }

    public Long getNewRevisionNumber(){
        synchronized (revisionNumber){
            revisionNumber++;
            return revisionNumber;
        }
    }
}
