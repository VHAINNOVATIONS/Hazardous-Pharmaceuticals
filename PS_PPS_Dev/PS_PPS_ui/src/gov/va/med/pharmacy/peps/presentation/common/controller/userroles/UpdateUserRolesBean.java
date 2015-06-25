/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.userroles;


import java.util.Map;


/**
 * UI form command bean
 */
public class UpdateUserRolesBean {

    private Map<Long, Map<String, Boolean>> userRoles;

    /**
     * Get the user roles
     * 
     * @return the user roles
     */
    public Map<Long, Map<String, Boolean>> getUserRoles() {
        return userRoles;
    }

    /**
     * 
     * Set the user roles
     *
     * @param userRoles the user roles
     */
    public void setUserRoles(Map<Long, Map<String, Boolean>> userRoles) {
        this.userRoles = userRoles;
    }

}
