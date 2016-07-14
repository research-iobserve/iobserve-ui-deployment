package org.iobserve.models.dataaccessobjects;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.iobserve.models.NodeGroup;
import org.iobserve.models.annotations.ModelClassOfDto;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.NodeGroup
 */
@ModelClassOfDto(NodeGroup.class)
@JsonTypeName("nodeGroup")
public class NodeGroupDto extends MeasurableDataTrasferObject {

    private String name;

    public NodeGroupDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
