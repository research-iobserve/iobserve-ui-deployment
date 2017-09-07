package org.iobserve.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.iobserve.models.util.Measurable;

@Entity
public class UserGroup extends Measurable {

    private String name;

    @ElementCollection
    private List<String> services;

    /**
     * constructor
     */
    public UserGroup() {
        super();
    }

    public UserGroup(final List<String> calledServices) {
        this.services = this.services;
    }

    public UserGroup(final String name, final List<String> calledServices) {
        this.name = name;
        this.services = calledServices;
    }

    /**
     * getters and setters
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    public List<String> getServices() {
        return this.services;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public void setServices(final List<String> services) {
        this.services = services;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public void addServices(final String serviceId) {
        this.services.add(serviceId);
    }

}
