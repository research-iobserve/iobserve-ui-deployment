package org.iobserve.models;


import org.iobserve.models.util.ChangelogOperation;
import org.iobserve.models.util.AbstractRevisionedBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Entity
public class Changelog extends AbstractRevisionedBean {


    /**
	 * 
	 */
	private static final long serialVersionUID = -3332068548423546707L;

	private ChangelogOperation operation;

    //@Column(columnDefinition = "TEXT")
    @Lob
    @Column(length=10000)
    private String data;

    public Changelog() {
        super();
    }

    public ChangelogOperation  getOperation() {
        return operation;
    }

    public void setOperation(ChangelogOperation  operation) {
        this.operation = operation;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
