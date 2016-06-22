package org.iobserve.models;

import org.iobserve.models.util.Measurable;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class CommunicationInstance extends Measurable{

    @OneToOne(cascade = CascadeType.ALL)
    @XmlTransient
    private ServiceInstance source;

    @OneToOne(cascade = CascadeType.ALL)
    @XmlTransient
    private ServiceInstance target;

    @ManyToOne(cascade = CascadeType.ALL)
    @XmlTransient
    private Communication communication;

    private long workload;

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

    public long getWorkload() {
        return workload;
    }

    public void setWorkload(long workload) {
        this.workload = workload;
    }
}
