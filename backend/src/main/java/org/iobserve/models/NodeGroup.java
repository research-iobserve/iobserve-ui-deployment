package org.iobserve.models;

import org.iobserve.models.util.AbstractMeasurable;

import javax.persistence.*;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Entity
public class NodeGroup extends AbstractMeasurable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8957458065144920232L;
	
	@OneToMany(mappedBy = "nodeGroup",cascade = CascadeType.ALL)
    private List<Node> nodes;
    private String name;

    public NodeGroup() {
        super();
    }

    public NodeGroup(String name, List<Node> nodes) {
        super();
        this.nodes = nodes;
        this.name = name;
    }

    public NodeGroup(String name) {
        super();
        this.name = name;
    }


    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
