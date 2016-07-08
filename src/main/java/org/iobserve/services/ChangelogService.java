package org.iobserve.services;

import org.iobserve.models.Changelog;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.models.mappers.DtoToBaseEntityMapper;
import org.iobserve.models.util.BaseEntity;
import org.iobserve.models.util.TimeSeries;
import org.iobserve.models.util.TimeSeriesDto;
import org.iobserve.services.websocket.ChangelogStreamService;


import javax.inject.Inject;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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

    public void addChangelogs(final String systemId, List<ChangelogDto> changelogs){
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

        //Todo send to socket
        //entityManager.persist(changelogs);
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

    private void updateEntity(ChangelogDto changelog) {
        BaseEntity entity = dtoToBaseEntityMapper.transform(changelog.getData());

        final EntityTransaction transaction = entityManager.getTransaction();
        if(!transaction.isActive()) transaction.begin();
        entityManager.merge(entity);
        transaction.commit();


    }

    private void appentEntity(ChangelogDto changelog) {
        //TODO Append more than timeseries only
        if(changelog.getData() instanceof TimeSeriesDto){
            TimeSeriesDto seriesDto = (TimeSeriesDto) changelog.getData();
            TimeSeries series = dtoToBaseEntityMapper.transform(seriesDto);
        }

    }

    private void deleteEntity(ChangelogDto changelog) {
        ModelClassOfDto modelClass = changelog.getData().getClass().getAnnotation(ModelClassOfDto.class);

        BaseEntity entity = entityManager.find(modelClass.value(), changelog.getData().getId());

        final EntityTransaction transaction = entityManager.getTransaction();
        if(!transaction.isActive()) transaction.begin();
        entityManager.remove(entity);
        transaction.commit();

    }

    private void createEntity(ChangelogDto changelog) {
        BaseEntity entity = dtoToBaseEntityMapper.transform(changelog.getData());

        final EntityTransaction transaction = entityManager.getTransaction();
        if(!transaction.isActive()) transaction.begin();
        entityManager.persist(entity);
        transaction.commit();

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
