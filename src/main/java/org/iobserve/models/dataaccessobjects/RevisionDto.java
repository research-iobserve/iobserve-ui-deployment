package org.iobserve.models.dataaccessobjects;

import java.util.Date;

/**
 * no need to extend DataTransportObject (no Id or any other behaviour since it is not based on Entities
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public class RevisionDto {
    Long revisionNumber;
    Date lastUpdate;
    Long changelogSequence;


    public Long getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Long revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getChangelogSequence() {
        return changelogSequence;
    }

    public void setChangelogSequence(Long changelogSequence) {
        this.changelogSequence = changelogSequence;
    }
}
