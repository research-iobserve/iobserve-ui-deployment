package org.iobserve.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.iobserve.models.util.Measurable;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * Created by cdor on 25.04.16.
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceInstance extends Measurable {
    private String Name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "node")
    @XmlTransient
    private Node node;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @XmlTransient
    private Service service;

    public ServiceInstance() {
    }

    public ServiceInstance(String name) {
        Name = name;
    }

    public ServiceInstance(String name, Node node, Service service) {
        Name = name;
        this.node = node;
        this.service = service;
    }

    public ServiceInstance(List<StatusInfo> statusInfoList, List<TimeSeries> timeSeriesList, String name) {
        super(statusInfoList, timeSeriesList);
        Name = name;
    }

    public ServiceInstance(List<StatusInfo> statusInfoList, List<TimeSeries> timeSeriesList, String name, Node node, Service service) {
        super(statusInfoList, timeSeriesList);
        Name = name;
        this.node = node;
        this.service = service;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
