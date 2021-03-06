package org.iobserve.models;

import org.iobserve.models.util.AbstractMeasurable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Entity
public class Communication extends AbstractMeasurable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3098874362907685654L;

	private String technology;

    @NotNull
    @XmlTransient
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Service source;

    @NotNull
    @XmlTransient
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Service target;

    @OneToMany(mappedBy = "communication", cascade = CascadeType.ALL)
    private List<CommunicationInstance> instances;

    public Communication() {
        super();
    }

    public Communication(Service target, Service source, List<CommunicationInstance> instances, String technology) {
        this.instances = instances;
        this.target = target;
        this.source = source;
        this.technology = technology;
    }

    public Communication(String technology, Service source, Service target) {
        this.technology = technology;
        this.source = source;
        this.target = target;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public Service getSource() {
        return source;
    }

    public void setSource(Service source) {
        this.source = source;
    }

    public Service getTarget() {
        return target;
    }

    public void setTarget(Service target) {
        this.target = target;
    }

    public List<CommunicationInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<CommunicationInstance> instances) {
        this.instances = instances;
    }


}
