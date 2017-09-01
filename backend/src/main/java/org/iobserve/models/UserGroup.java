package org.iobserve.models;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.iobserve.models.util.Measurable;

@Entity
// @XmlAccessorType(XmlAccessType.FIELD)
public class UserGroup extends Measurable {

    private String name;

    @ElementCollection
    // @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    // @JoinColumn(name="id", referencedColumnName="id")
    private List<String> services;

    // private String usergroupId;
    // private String serviceId;

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

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<String> getServices() {
        return this.services;
    }

    public void setServices(final List<String> services) {
        this.services = services;
    }

    // public String getUsergroupId() {
    // return this.usergroupId;
    // }
    //
    // public void setUsergroupId(final String usergroupId) {
    // this.usergroupId = usergroupId;
    // }
    //
    // public String getServiceId() {
    // return this.serviceId;
    // }
    //
    // public void setServiceId(final String serviceId) {
    // this.serviceId = serviceId;
    // }

}
