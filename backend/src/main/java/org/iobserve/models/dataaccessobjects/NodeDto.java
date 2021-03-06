package org.iobserve.models.dataaccessobjects;


import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.ModelType;
import org.iobserve.models.Node;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.services.NodeService;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.Node
 */
@ModelClassOfDto(value = Node.class, service = NodeService.class)
@JsonTypeName(ModelType.TypeName.NODE)
public class NodeDto extends MeasurableDataTrasferObject {

    private String name;
    private String ip;
    private String hostname;

    private String nodeGroupId;

    public NodeDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getNodeGroupId() {
        return nodeGroupId;
    }

    public void setNodeGroupId(String groupId) {
        this.nodeGroupId = groupId;
    }
}
