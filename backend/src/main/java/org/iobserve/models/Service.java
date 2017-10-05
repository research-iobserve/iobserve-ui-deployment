package org.iobserve.models;

import org.iobserve.models.util.AbtractMeasurable;

import javax.persistence.*;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Entity
public class Service extends AbtractMeasurable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7245322632353880274L;
	
	private String  name;
    private String description;


    @OneToMany(mappedBy = "service",cascade = CascadeType.ALL)
    private List<ServiceInstance> instances;

    public Service() {
        super();
    }

    public Service(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public Service(String name, String description, List<ServiceInstance> instances) {
        this.name = name;
        this.description = description;
        this.instances = instances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ServiceInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<ServiceInstance> instances) {
        this.instances = instances;
    }


}
