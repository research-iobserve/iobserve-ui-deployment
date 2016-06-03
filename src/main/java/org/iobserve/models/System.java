package org.iobserve.models;

import org.iobserve.models.util.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by cdor on 25.04.16.
 */
@Entity
public class System extends BaseEntity {
    private String name;
    //Nodes
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="system_id", referencedColumnName="id")
    private List<NodeGroup> nodeGroups;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="system_id", referencedColumnName="id")
    private List<Node> nodes;

    //Communication
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="system_id", referencedColumnName="id")
    private List<Communication> communications;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="system_id", referencedColumnName="id")
    private List<CommunicationInstance> communicationInstances;

    //Services
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="system_id", referencedColumnName="id")
    private List<Service> services;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="system_id", referencedColumnName="id")
    private List<ServiceInstance> serviceInstances;

    public System(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NodeGroup> getNodeGroups() {
        return nodeGroups;
    }

    public void setNodeGroups(List<NodeGroup> nodeGroups) {
        this.nodeGroups = nodeGroups;
    }

    public List<Communication> getCommunications() {
        return communications;
    }

    public void setCommunications(List<Communication> communications) {
        this.communications = communications;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<CommunicationInstance> getCommunicationInstances() {
        return communicationInstances;
    }

    public void setCommunicationInstances(List<CommunicationInstance> communicationInstances) {
        this.communicationInstances = communicationInstances;
    }

    public List<ServiceInstance> getServiceInstances() {
        return serviceInstances;
    }

    public void setServiceInstances(List<ServiceInstance> serviceInstances) {
        this.serviceInstances = serviceInstances;
    }

}
