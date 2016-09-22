package org.iobserve.models.util;

import org.iobserve.models.util.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
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
