package org.iobserve.models.dataaccessobjects;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */

public abstract class NestedDataAccessObject extends DataTransportObject {

    private String parentId;
    private String parentType;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

}
