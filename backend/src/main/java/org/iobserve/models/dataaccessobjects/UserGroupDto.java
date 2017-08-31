package org.iobserve.models.dataaccessobjects;

import java.util.List;

import org.iobserve.models.ModelType;
import org.iobserve.models.Service;
import org.iobserve.models.UserGroup;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.services.UserGroupService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 *
 * @author jweg
 *
 */
@ModelClassOfDto(value = UserGroup.class, service = UserGroupService.class)
@JsonTypeName(ModelType.TypeName.USERGROUP)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGroupDto extends MeasurableDataTrasferObject {

    private String name;
    private List<Service> services;
    private String usergroupId;
    private String serviceId;

    public UserGroupDto() {
        super();
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Service> getServices() {
        return this.services;
    }

    public void setServices(final List<Service> services) {
        this.services = services;
    }

    public String getUsergroupId() {
        return this.usergroupId;
    }

    public void setUsergroupId(final String usergroupId) {
        this.usergroupId = usergroupId;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(final String serviceId) {
        this.serviceId = serviceId;
    }

}
