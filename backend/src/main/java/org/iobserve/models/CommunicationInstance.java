package org.iobserve.models;

import org.iobserve.models.util.AbstractMeasurable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class CommunicationInstance extends AbstractMeasurable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2701549210151796441L;

	@NotNull
    @XmlTransient
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private ServiceInstance source;

    @NotNull
    @XmlTransient
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private ServiceInstance target;

    @NotNull
    @XmlTransient
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
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
