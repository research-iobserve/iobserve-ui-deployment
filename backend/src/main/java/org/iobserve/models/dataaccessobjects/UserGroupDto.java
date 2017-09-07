package org.iobserve.models.dataaccessobjects;

import java.util.List;

import org.iobserve.models.ModelType;
import org.iobserve.models.Service;
import org.iobserve.models.UserGroup;
import org.iobserve.models.annotations.ModelClassOfDto;
import org.iobserve.services.UserGroupService;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 *
 * @author jweg
 *
 */
@ModelClassOfDto(value = UserGroup.class, service = UserGroupService.class)
@JsonTypeName(ModelType.TypeName.USERGROUP)
public class UserGroupDto extends MeasurableDataTrasferObject {

    private String name;
    List<Service> serviceIds;
    // private String usergroupId;
    // private String serviceId;

    public UserGroupDto() {
        super();
    }

    public UserGroupDto(final String name, final List<Service> services) {
        this.name = name;
        this.serviceIds = services;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Service> getServices() {
        return this.serviceIds;
    }

    public void setServices(final List<Service> services) {
        this.serviceIds = services;
    }

    // public String getUsergroupId() {
    // return this.usergroupId;
    // }
    //
    // public void setUsergroupId(final String usergroupId) {
    // this.usergroupId = usergroupId;
    // }
    //
    // public String getServiceId() {
    // return this.serviceId;
    // }
    //
    // public void setServiceId(final String serviceId) {
    // this.serviceId = serviceId;
    // }

}
