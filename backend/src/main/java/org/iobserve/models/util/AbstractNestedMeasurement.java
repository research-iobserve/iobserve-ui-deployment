package org.iobserve.models.util;

import org.iobserve.models.util.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractNestedMeasurement extends AbstractBaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6355722065109512130L;

	@Column(name="parent_id", nullable = false, updatable = false)
    private String parentId;

    @Column(name="parent_type", nullable = false, updatable = false)
    private String parentType; // node, serviceInstance, etc... JSON type identifier


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
