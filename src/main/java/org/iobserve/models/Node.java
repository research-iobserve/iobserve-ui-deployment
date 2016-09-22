package org.iobserve.models;

import org.iobserve.models.util.Measurable;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Entity
public class Node extends Measurable {

    private String name;

    @OneToMany(mappedBy="node",cascade = CascadeType.ALL)
    private List<ServiceInstance> services;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @XmlTransient
    private NodeGroup nodeGroup;


    private String ip;
    private String hostname;




    public Node() {
        super();
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

    public NodeGroup getNodeGroup() {
        return nodeGroup;
    }

    public void setNodeGroup(NodeGroup group) {
        this.nodeGroup = group;
    }
}