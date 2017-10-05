package org.iobserve.models.util;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Revision extends AbstractBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9076622572526849776L;

	private Long revisionNumber;
	private Date lastUpdate;
	private Long changelogSequence;

	public Revision() {
		revisionNumber = 0L;
		changelogSequence = 0L;
		lastUpdate = new Date();
	}

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
