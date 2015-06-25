/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;


/**
 * Roles used in PEPS
 */
public enum Role {

    /** LOCAL_SERVICE_MANAGER */
    LOCAL_SERVICE_MANAGER, 

//    NATIONAL_SERVICE_MANAGER,  //PSS_PPSN_MANAGER

    /** LOCAL_ADMINISTRATIVE_MANAGER */
    LOCAL_ADMINISTRATIVE_MANAGER, 

//    NATIONAL_ADMINISTRATIVE_MANAGER,  //(PSS_PPSN_SUPERVISOR)

    /** LOCAL_READ_ONLY */
    LOCAL_READ_ONLY, 

//    NATIONAL_READ_ONLY, //PSS_PPSN_VIEWER
//    SECOND_REVIEWER, //PSS_PPSN_SECOND_APPROVER

    // new keys

    /** PSS_PPSN_MANAGER */
    PSS_PPSN_MANAGER,

    /** PSS_PPSN_MIGRATOR */
    PSS_PPSN_MIGRATOR,

    /** PSS_PPSN_SECOND_APPROVER */
    PSS_PPSN_SECOND_APPROVER,

    /** PSS_PPSN_SUPERVISOR */
    PSS_PPSN_SUPERVISOR,

    /** PSS_PPSN_VIEWER */
    PSS_PPSN_VIEWER
    ;

    /**
     * Returns a list of roles that do not have edit permissions
     * 
     * @return a collection of roles that cannot edit
     */
    public static Collection<Role> getNoEditRoles() {
        ArrayList<Role> ret = new ArrayList<Role>();
        
        ret.add(LOCAL_READ_ONLY);

//        ret.add(NATIONAL_READ_ONLY);
        ret.add(PSS_PPSN_VIEWER);
        ret.add(PSS_PPSN_SECOND_APPROVER);
        
        
        return ret;
    }

    /**
     * isLocalManager()?
     * 
     * @return boolean true if this instance of {@link Role} is a local manager
     */
    public boolean isLocalManager() {
        return (LOCAL_ADMINISTRATIVE_MANAGER.equals(this) || LOCAL_SERVICE_MANAGER.equals(this));

    }

    /**
     * isNationalManager()?
     * 
     * @return boolean true if this instance of {@link Role} is a national manager
     */
    public boolean isNationalManager() {

        return (PSS_PPSN_SUPERVISOR.equals(this) || PSS_PPSN_MANAGER.equals(this));

//        return (NATIONAL_ADMINISTRATIVE_MANAGER.equals(this) || NATIONAL_SERVICE_MANAGER.equals(this));
    }

    /**
     * isServiceLevel()?
     * 
     * @return boolean true if this instance of {@link Role} is a service level manager
     */
    public boolean isServiceLevel() {

        return (LOCAL_SERVICE_MANAGER.equals(this) || PSS_PPSN_MANAGER.equals(this));

//        return (LOCAL_SERVICE_MANAGER.equals(this) || NATIONAL_SERVICE_MANAGER.equals(this));        
    }

    /**
     * isAdministrativeLevel()?
     * 
     * @return boolean true if this instance of {@link Role} is an administrative level manager
     */
    public boolean isAdministrativeLevel() {

        return (LOCAL_ADMINISTRATIVE_MANAGER.equals(this) || PSS_PPSN_SUPERVISOR.equals(this));

//        return (LOCAL_ADMINISTRATIVE_MANAGER.equals(this) || NATIONAL_ADMINISTRATIVE_MANAGER.equals(this));        
    }

    /**
     * isReadOnly()?
     * 
     * @return boolean true if this instance of {@link Role} is read only
     */
    public boolean isReadOnly() {

        return LOCAL_READ_ONLY.equals(this) || PSS_PPSN_VIEWER.equals(this) || PSS_PPSN_SECOND_APPROVER.equals(this);

//        return LOCAL_READ_ONLY.equals(this) || NATIONAL_READ_ONLY.equals(this);
    }

    /**
     * Specifies if this role is a migration level role
     * 
     * @return boolean
     */
    public boolean isMigrationLevel() {
        return PSS_PPSN_MIGRATOR.equals(this);
    }
}
