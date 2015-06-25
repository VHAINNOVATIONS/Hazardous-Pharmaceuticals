/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.exception.AuthenticationException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * Perform security actions on a user
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class UserServiceBean extends AbstractPepsStatelessSessionBean<UserService> implements UserService {
    private static final long serialVersionUID = 1L;

    /**
     * Retrieve the fields this user is allowed to edit.
     * 
     * @param user UserVo for which to retrieve authorized fields
     * @param obj Object the user is working with. Could be any ValueObject.
     * @return FieldAuthorizationVo containing fields that are disabled and editable
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public FieldAuthorizationVo authorizeFields(UserVo user, Object obj) {
        return getService().authorizeFields(user, obj);
    }

    /**
     * Authenticate this user by verifying the username and password exist. Set the available roles for the User and load
     * user preferences.
     * 
     * @param user The user
     * @return UserVo containing user information
     * 
     * @throws AuthenticationException if the given username/password pair do not authenticate
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public UserVo login(UserVo user) throws AuthenticationException {
        return getService().login(user);
    }

    /**
     * Log the user out of the system.
     * 
     * @param user UserVo to log out
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public void logout(UserVo user) {
        getService().logout(user);
    }

    /**
     * Update/persist the user's selected display settings and preferences. Preferences are stored in the
     * {@link UserVo#getPreferences()} {@link Map}.
     * 
     * @param user {@link UserVo} with updated preferences
     * @return updated UserVo
     * 
     * @throws ValueObjectValidationException if errors validating user preferences
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public UserVo updatePreferences(UserVo user) throws ValueObjectValidationException {
        return getService().updatePreferences(user);
    }

    /**
     * Load user by ID (KAAJEE DUZ)
     * 
     * @param duz Long
     * @param station String
     * @return UserVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @throws AuthenticationException if user could not be found in the database
     */
    public UserVo retrieve(Long duz, String station) throws AuthenticationException {
        return getService().retrieve(duz, station);
    }
    
    /**
     * Retreive All Users
     * 
     * @return UserVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @throws AuthenticationException if user could not be found in the database
     */
    public Collection<UserVo> retrieveAll() throws AuthenticationException {
        return getService().retrieveAll();
    }
    
    /**
     * Update
     *       
     * @param user UserVo
     * @return UserVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @throws AuthenticationException if user could not be found in the database
     */
    public UserVo update(UserVo user) throws AuthenticationException {
        return getService().update(user);
    }

    /**
     * Retrieve all notification types
     * 
     * @return List<NotificationTypeVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<NotificationType> retrieveAllNotificationTypes() {
        return getService().retrieveAllNotificationTypes();
    }

    /**
     * Create a new {@link UserVo}.
     * 
     * @param user {@link UserVo} to create
     * @return created {@link UserVo}
     * @throws ValidationException if error validating data in {@link UserVo}
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public UserVo create(UserVo user) throws ValidationException {
        return getService().create(user);
    }

    /**
     * Return true if a {@link UserVo} with the given ID exists.
     * 
     * @param id Long
     * @return boolean
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public boolean exists(Long id) {
        return getService().exists(id);
    }
}
