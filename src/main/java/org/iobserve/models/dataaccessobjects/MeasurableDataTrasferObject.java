package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

import java.util.List;
import java.util.Set;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class MeasurableDataTrasferObject extends RevisionedBeanDataTransportObject {
    private Set<StatusInfo> statusInformations;


    private Set<TimeSeries> timeSeries;

    public MeasurableDataTrasferObject() {
        super();
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
