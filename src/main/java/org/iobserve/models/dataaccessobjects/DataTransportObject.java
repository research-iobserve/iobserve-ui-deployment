package org.iobserve.models.dataaccessobjects;

/**
 * @author Mathis Neumann
 */
public abstract class DataTransportObject {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
