package org.iobserve.models;

import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.util.BaseEntity;
import org.iobserve.models.util.RevisionedBean;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class Changelog extends RevisionedBean {
    public enum OPERATION {CREATE,DELETE,UPDATE,APPEND}

    private OPERATION operation;
    private DataTransportObject data;

    public OPERATION getOperation() {
        return operation;
    }

    public void setOperation(OPERATION operation) {
        this.operation = operation;
    }

    public DataTransportObject getData() {
        return data;
    }

    public void setData(DataTransportObject data) {
        this.data = data;
    }
}
