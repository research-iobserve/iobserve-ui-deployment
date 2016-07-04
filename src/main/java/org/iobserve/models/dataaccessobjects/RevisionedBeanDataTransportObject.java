package org.iobserve.models.dataaccessobjects;

import java.util.Date;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public abstract class RevisionedBeanDataTransportObject extends DataTransportObject {
    String systemId;

    private Long revisionNumber;
    private Long changelogSequence;
    private Date lastUpdate;

    public RevisionedBeanDataTransportObject() {
        super();
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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
