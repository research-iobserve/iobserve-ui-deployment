package org.iobserve.models.dataaccessobjects;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.NodeGroup
 */
public class NodeGroupDto extends SystemIdDataTransportObject {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
