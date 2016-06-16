package org.iobserve.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.iobserve.models.util.Measurable;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

import javax.persistence.*;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Entity
public class NodeGroup extends Measurable{
    @OneToMany(mappedBy = "nodeGroup",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Node> nodes;
    private String name;

    public NodeGroup() {
    }

    public NodeGroup(String name, List<Node> nodes) {
        this.nodes = nodes;
        this.name = name;
    }

    public NodeGroup(String name) {
        this.name = name;
    }

    public NodeGroup(List<StatusInfo> statusInfoList, List<TimeSeries> timeSeriesList, List<Node> nodes, String name) {
        super(statusInfoList, timeSeriesList);
        this.nodes = nodes;
        this.name = name;
    }

    public NodeGroup(List<StatusInfo> statusInfoList, List<TimeSeries> timeSeriesList, String name) {
        super(statusInfoList, timeSeriesList);
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
