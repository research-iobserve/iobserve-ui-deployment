package org.iobserve.models;

import org.iobserve.models.util.BaseEntity;

import javax.persistence.Column;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public class NestedMeasurement extends BaseEntity{
    @Column(name="parent_id")
    private String parentId;


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
