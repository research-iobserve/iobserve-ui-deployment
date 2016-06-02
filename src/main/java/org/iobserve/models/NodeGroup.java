package org.iobserve.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.iobserve.models.util.Measurable;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

import javax.persistence.*;
import java.util.List;

/**
 * Created by cdor on 25.04.16.
 */
@Entity
public class NodeGroup extends Measurable{
    @Column(name="system_id")
    private String system;
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
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

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
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
