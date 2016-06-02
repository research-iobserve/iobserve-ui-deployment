package org.iobserve.models;

import org.iobserve.models.util.Measurable;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

import javax.persistence.*;
import java.util.List;

/**
 * Created by cdor on 25.04.16.
 */
@Entity
public class CommunicationInstance extends Measurable{
    @OneToOne(cascade = CascadeType.ALL)
    private ServiceInstance source;
    @OneToOne(cascade = CascadeType.ALL)
    private ServiceInstance target;
    @ManyToOne(cascade = CascadeType.ALL)
    private Communication communication;

    public CommunicationInstance() {
    }

    public CommunicationInstance(ServiceInstance source, ServiceInstance target, Communication communication) {
        this.source = source;
        this.target = target;
        this.communication = communication;
    }

    public CommunicationInstance(ServiceInstance source, ServiceInstance target) {
        this.source = source;
        this.target = target;
    }

    public CommunicationInstance(List<StatusInfo> statusInfoList, List<TimeSeries> timeSeriesList, ServiceInstance source, ServiceInstance target, Communication communication) {
        super(statusInfoList, timeSeriesList);
        this.source = source;
        this.target = target;
        this.communication = communication;
    }

    public ServiceInstance getSource() {
        return source;
    }

    public void setSource(ServiceInstance source) {
        this.source = source;
    }

    public ServiceInstance getTarget() {
        return target;
    }

    public void setTarget(ServiceInstance target) {
        this.target = target;
    }

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }
}
