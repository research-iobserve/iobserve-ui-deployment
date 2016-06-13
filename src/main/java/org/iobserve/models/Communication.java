package org.iobserve.models;

import org.iobserve.models.util.Measurable;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

import javax.persistence.*;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Entity
public class Communication extends Measurable{
    private String technology;
    @OneToOne(cascade = CascadeType.ALL)
    private Service source;
    @OneToOne(cascade = CascadeType.ALL)
    private Service target;
    @OneToMany(mappedBy = "communication", cascade = CascadeType.ALL)
    private List<CommunicationInstance> instances;

    public Communication() {
    }

    public Communication(Service target, Service source, List<CommunicationInstance> instances, String technology) {
        this.instances = instances;
        this.target = target;
        this.source = source;
        this.technology = technology;
    }

    public Communication(String technology, Service source, Service target) {
        this.technology = technology;
        this.source = source;
        this.target = target;
    }

    public Communication(List<StatusInfo> statusInfoList, List<TimeSeries> timeSeriesList, String technology, Service source, Service target, List<CommunicationInstance> instances) {
        super(statusInfoList, timeSeriesList);
        this.technology = technology;
        this.source = source;
        this.target = target;
        this.instances = instances;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public Service getSource() {
        return source;
    }

    public void setSource(Service source) {
        this.source = source;
    }

    public Service getTarget() {
        return target;
    }

    public void setTarget(Service target) {
        this.target = target;
    }

    public List<CommunicationInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<CommunicationInstance> instances) {
        this.instances = instances;
    }
}
