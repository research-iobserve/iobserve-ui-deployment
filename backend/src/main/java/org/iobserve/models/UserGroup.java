package org.iobserve.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.iobserve.models.util.Measurable;

@Entity
public class UserGroup extends Measurable {

    private String name;

    @ElementCollection
    private List<Service> invokedServices;

    public UserGroup() {
        super();
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @ElementCollection
    @Column(name = "services")
    public List<Service> getServices() {
        return this.invokedServices;
    }

    public void setServices(final List<Service> services) {
        this.invokedServices = services;
    }

}
