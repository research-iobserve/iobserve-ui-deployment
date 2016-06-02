package org.iobserve.models.util;

import javax.persistence.*;
import java.util.List;

/**
 * Created by cdor on 25.04.16.
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Measurable extends RevisionedBean{
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="parent_id", referencedColumnName="id")
    private List<StatusInfo> statusInformations;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="parent_id", referencedColumnName="id")
    private List<TimeSeries> timeSeries;

    public Measurable() {
    }

    public Measurable(List<StatusInfo> statusInformations, List<TimeSeries> timeSeries) {
        this.statusInformations = statusInformations;
        this.timeSeries = timeSeries;
    }

    public List<StatusInfo> getStatusInformations() {
        return statusInformations;
    }

    public void setStatusInformations(List<StatusInfo> statusInfoList) {
        this.statusInformations = statusInfoList;
    }

    public List<TimeSeries> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(List<TimeSeries> timeSeriesList) {
        this.timeSeries = timeSeriesList;
    }
}
