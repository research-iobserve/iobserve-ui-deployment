package org.iobserve.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.iobserve.models.util.Measurable;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

import javax.persistence.*;
import java.util.List;

/**
 * Created by cdor on 25.04.16.
 */
@Entity
public class Node extends Measurable {

    private String name;
    @OneToMany(mappedBy="node",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ServiceInstance> services;
    private String ip;
    private String hostname;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private NodeGroup group;


    public Node() {
    }

    public Node(String name, String ip, String hostname) {
        this.name = name;
        this.ip = ip;
        this.hostname = hostname;
    }

    public Node(String name, List<ServiceInstance> services, String ip, String hostname) {
        this.name = name;
        this.services = services;
        this.ip = ip;
        this.hostname = hostname;
    }

    public Node(List<StatusInfo> statusInfoList, List<TimeSeries> timeSeriesList, String name, List<ServiceInstance> services, String ip, String hostname) {
        super(statusInfoList, timeSeriesList);
        this.name = name;
        this.services = services;
        this.ip = ip;
        this.hostname = hostname;
    }

    public Node(List<StatusInfo> statusInfoList, List<TimeSeries> timeSeriesList, String name, String ip, String hostname) {
        super(statusInfoList, timeSeriesList);
        this.name = name;
        this.ip = ip;
        this.hostname = hostname;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceInstance> getServices() {
        return services;
    }

    public void setServices(List<ServiceInstance> services) {
        this.services = services;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public NodeGroup getGroup() {
        return group;
    }

    public void setGroup(NodeGroup group) {
        this.group = group;
    }
}