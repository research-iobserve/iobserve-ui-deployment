package org.iobserve.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.iobserve.models.util.Measurable;

@Entity
public class UserGroup extends Measurable {

    private String name;

    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    private List<Service> services;

    /**
     * constructor
     */
    public UserGroup() {
        super();
    }

    public UserGroup(final List<Service> calledServices) {
        this.services = calledServices;
    }

    public UserGroup(final String name, final List<Service> calledServices) {
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

    // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // @JoinColumn(name = "id")
    public List<Service> getServices() {
        return this.services;
    }

    // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public void setServices(final List<Service> services) {
        this.services = services;
    }

    // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public void addServices(final Service service) {
        this.services.add(service);
    }

}
