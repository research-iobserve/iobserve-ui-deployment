package org.iobserve.models;


import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.util.ChangelogOperation;
import org.iobserve.models.util.RevisionedBean;

import javax.json.Json;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Entity
public class Changelog extends RevisionedBean {


    private ChangelogOperation operation;
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
