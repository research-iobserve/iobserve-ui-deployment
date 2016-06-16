package org.iobserve.models.dataaccessobjects;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.NodeGroup
 */
@XmlType(name = "nodeGroup")
public class NodeGroupDto extends SystemIdDataTransportObject {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
