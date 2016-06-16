package org.iobserve.models.dataaccessobjects;


import javax.xml.bind.annotation.XmlType;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see org.iobserve.models.Service
 */
@XmlType(name = "service")
public class ServiceDto extends SystemIdDataTransportObject {

    private String name;
    private String description;
}
