package org.iobserve.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

import org.iobserve.models.util.Measurable;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Entity
public class Service extends Measurable {
    private String name;
    private String description;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<ServiceInstance> instances;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @XmlTransient
    @JoinColumn(name = "userGroup")
    private UserGroup userGroup;

    public Service() {
        super();
    }

    // public Service(final String id) {
    // super();
    // super.setId(id);
    // }

    public Service(final String name, final String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public Service(final String name, final String description, final List<ServiceInstance> instances) {
        this.name = name;
        this.description = description;
        this.instances = instances;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<ServiceInstance> getInstances() {
        return this.instances;
    }

    public void setInstances(final List<ServiceInstance> instances) {
        this.instances = instances;
    }

}
