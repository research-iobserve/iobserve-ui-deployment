package org.iobserve.models.dataaccessobjects;

import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class MeasurableDataTrasferObject extends SystemIdDataTransportObject {
    private List<StatusInfo> statusInformations;

    private List<TimeSeries> timeSeries;


    public List<StatusInfo> getStatusInformations() {
        return statusInformations;
    }

    public void setStatusInformations(List<StatusInfo> statusInfoList) {
        this.statusInformations = statusInfoList;
    }

    public void addStatusInformation(StatusInfo info){
        statusInformations.add(info);
    }

    public List<TimeSeries> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(List<TimeSeries> timeSeriesList) {
        this.timeSeries = timeSeriesList;
    }

    public void addTimeSeries(TimeSeries series){
        timeSeries.add(series);
    }
}
