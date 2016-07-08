package org.iobserve.models.util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Date;

/**
 * Created by cdor on 25.05.16.
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class RevisionedBean extends BaseEntity {
    @Column(name="system_id")
    private String systemId;

    private Long revisionNumber;
    private Long changelogSequence;
    private Date lastUpdate; // TODO: set automatically!

    public RevisionedBean() {
        super();
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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
