package org.iobserve.models;

import org.iobserve.models.util.AbtractMeasurable;

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
public class ServiceInstance extends AbtractMeasurable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 935355593196315592L;

	private String name;

    @NotNull
    @XmlTransient
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "node")
    private Node node;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @XmlTransient
    private Service service;

    public ServiceInstance() {
        super();
    }

    public ServiceInstance(String name) {
        super();
        this.name = name;
    }

    public ServiceInstance(String name, Node node, Service service) {
        super();
        this.name = name;
        this.node = node;
        this.service = service;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
