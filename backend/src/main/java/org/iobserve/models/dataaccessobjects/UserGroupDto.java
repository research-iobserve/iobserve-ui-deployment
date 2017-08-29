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

    private String workload;
    private List<Service> invokedServices;

    // private String usergroupGroupId;

    public UserGroupDto() {
        super();
    }

    public String getWorkload() {
        return this.workload;
    }

    public void setWorkload(final String workload) {
        this.workload = workload;
    }

    public List<Service> getServices() {
        return this.invokedServices;
    }

    public void setServices(final List<Service> services) {
        this.invokedServices = services;
    }

    // public String getUsergroupGroupId() {
    // return this.usergroupGroupId;
    // }
    //
    // public void setUsergroupGroupId(final String usergroupGroupId) {
    // this.usergroupGroupId = usergroupGroupId;
    // }

}
