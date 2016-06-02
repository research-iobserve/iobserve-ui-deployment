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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="system_id", referencedColumnName="id")
    private List<NodeGroup> nodeGroups;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="system_id", referencedColumnName="id")
    private List<Communication> communications;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="system_id", referencedColumnName="id")
    private List<Service> services;

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
}
