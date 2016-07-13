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

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @XmlTransient
    private ServiceInstance source;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @XmlTransient
    private ServiceInstance target;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @XmlTransient
    private Communication communication;

    private Long workload;

    public CommunicationInstance() {
        super();
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

    public Long getWorkload() {
        return workload;
    }

    public void setWorkload(Long workload) {
        this.workload = workload;
    }
}
