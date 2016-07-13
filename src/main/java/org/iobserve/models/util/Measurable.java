package org.iobserve.models.util;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by cdor on 25.04.16.
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Measurable extends RevisionedBean {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="parent_id", referencedColumnName="id")
    private Set<StatusInfo> statusInformations = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="parent_id", referencedColumnName="id")
    private Set<TimeSeries> timeSeries = new HashSet<>();

    public Measurable() {
        super();
    }

    public Measurable(Set<StatusInfo> statusInformations, Set<TimeSeries> timeSeries) {
        super();
        this.statusInformations = statusInformations;
        this.timeSeries = timeSeries;
    }

    public Set<StatusInfo> getStatusInformations() {
        return statusInformations;
    }

    public void setStatusInformations(Set<StatusInfo> statusInfoList) {
        this.statusInformations = statusInfoList;
    }

    public void addStatusInformation(StatusInfo info){
        statusInformations.add(info);
    }

    public Set<TimeSeries> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(Set<TimeSeries> timeSeriesList) {
        this.timeSeries = timeSeriesList;
    }

    public void addTimeSeries(TimeSeries series){
        timeSeries.add(series);
    }
}
