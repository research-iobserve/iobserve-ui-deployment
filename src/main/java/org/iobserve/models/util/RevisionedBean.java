package org.iobserve.models.util;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Date;

/**
 * Created by cdor on 25.05.16.
 */
@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class RevisionedBean extends BaseEntity {
    private Long revisionNumber;
    private Long changelogSequence;
    private Date lastUpdate;

    public RevisionedBean() {
    }

    public Long getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Long revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public Long getChangelogSequence() {
        return changelogSequence;
    }

    public void setChangelogSequence(Long changelogSequence) {
        this.changelogSequence = changelogSequence;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
