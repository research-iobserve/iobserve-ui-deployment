package org.iobserve.services;

import org.iobserve.models.Changelog;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.models.mappers.DtoToBaseEntityMapper;
import org.iobserve.models.util.BaseEntity;
import org.iobserve.models.util.TimeSeries;
import org.iobserve.models.util.TimeSeriesDto;


import java.util.Date;
import java.util.List;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class ChangelogService extends AbstractSystemComponentService<Changelog,ChangelogDto> {
    //TODO Concurencey issues with different revisions



    @Override
    protected ChangelogDto transformModelToDto(Changelog changelog) {
           return this.modelToDtoMapper.transform(changelog);
    }

    public void addChangelogs(List<Changelog> changelogs){
        final long revision = 0; //TODO get Revision number
        final Date date = new Date();

        for (int i = 0; i < changelogs.size(); i++) {
            final Changelog changelog = changelogs.get(i);

            changelog.setRevisionNumber(revision);
            changelog.setChangelogSequence(new Long(i));
            changelog.setLastUpdate(date);

            applyChangelog(changelog);
        }
        //Todo send to socket
        entityManager.persist(changelogs);
    }


    private void applyChangelog(Changelog changelog){
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

    private void updateEntity(Changelog changelog) {
        BaseEntity entity = DtoToBaseEntityMapper.transform(changelog.getData());
        entityManager.merge(entity);
        entityManager.getTransaction().commit();

    }

    private void appentEntity(Changelog changelog) {
        //TODO Append more than timeseries only
        if(changelog.getData() instanceof TimeSeriesDto){
            TimeSeriesDto seriesDto = (TimeSeriesDto) changelog.getData();
            TimeSeries series = DtoToBaseEntityMapper.transform(seriesDto);


        }

    }

    private void deleteEntity(Changelog changelog) {
        ModelClassOfDto modelClass = changelog.getData().getClass().getAnnotation(ModelClassOfDto.class);

        BaseEntity entity = entityManager.find(modelClass.value(), changelog.getData().getId());
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    private void createEntity(Changelog changelog) {
        BaseEntity entity = DtoToBaseEntityMapper.transform(changelog.getData());
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }





}
