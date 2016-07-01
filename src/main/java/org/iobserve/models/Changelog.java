package org.iobserve.models;


import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.util.ChangelogOperation;
import org.iobserve.models.util.RevisionedBean;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@XmlType(name = "changelog")
public class Changelog extends RevisionedBean {


    private ChangelogOperation operation;
    private DataTransportObject data;

    public Changelog() {
    }

    public ChangelogOperation  getOperation() {
        return operation;
    }

    public void setOperation(ChangelogOperation  operation) {
        this.operation = operation;
    }

    public DataTransportObject getData() {
        return data;
    }

    public void setData(DataTransportObject data) {
        this.data = data;
    }
}
