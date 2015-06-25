/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.exception.AuthenticationException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.capability.UserCapability;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * Perform security actions on a user
 */
public class UserServiceImpl implements UserService {
    private UserCapability userCapability;
 

    /**
     * Retrieve the fields this user is allowed to edit.
     * 
     * @param user UserVo for which to retrieve authorized fields
     * @param obj Object the user is working with. Could be any ValueObject.
     * @return FieldAuthorizationVo containing fields that are disabled and editable
     */
    public FieldAuthorizationVo authorizeFields(UserVo user, Object obj) {
        return userCapability.authorizeFields(user, obj);
    }

    /**
     * Authenticate this user by verifying the username and password exist. Set the available roles for the User and load
     * user preferences.
     * 
     * @param user User
     * @return UserVo containing user information
     * 
     * @throws AuthenticationException if the given username/password pair do not authenticate
     */
    public UserVo login(UserVo user) throws AuthenticationException {
        return userCapability.login(user);
    }
    
    /**
     * Upate
     * 
     * @param user User
     * @return UserVo containing user information
     * 
     * @throws AuthenticationException if the given username/password pair do not authenticate
     */
    public UserVo update(UserVo user) throws AuthenticationException {
        return userCapability.update(user);
    }

    /**
     * Update/persist the user's selected display settings and preferences. Preferences are stored in the
     * {@link UserVo#getPreferences()} {@link Map}.
     * 
     * @param user {@link UserVo} with updated preferences
     * @return updated UserVo
     * 
     * @throws ValueObjectValidationException if errors validating user preferences
     */
    public UserVo updatePreferences(UserVo user) throws ValueObjectValidationException {
        return userCapability.updatePreferences(user);
    }

    /**
     * Load user by ID (KAAJEE DUZ)
     * 
     * @param duz Long
     * @param station String
     * @return UserVo
     * 
     * @throws AuthenticationException if user could not be found in the database
     */
    public UserVo retrieve(Long duz, String station) throws AuthenticationException {
        return userCapability.retrieve(duz, station);
    }
    
    
    /**
     * Retrieve All Users
     * 
     * @return UserVo
     * 
     * @throws AuthenticationException if user could not be found in the database
     */
    public Collection<UserVo> retrieveAll() throws AuthenticationException {
        return userCapability.retrieveAll();
    }
    
    /**
     * Retrieve all notification types
     * 
     * @return List<NotificationTypeVo>
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.UserService#retrieveAllNotificationTypes()
     */
    public List<NotificationType> retrieveAllNotificationTypes() {
        return userCapability.retrieveAllNotificationTypes();
    }
    
    /**
     * Log the user out of the system.
     * 
     * @param user UserVo to log out
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.UserService#logout(gov.va.med.pharmacy.peps.common.vo.UserVo)
     */
    public void logout(UserVo user) {
        userCapability.logout(user);
    }

    /**
     * Create a new {@link UserVo}.
     * 
     * @param user {@link UserVo} to create
     * @return created {@link UserVo}
     * @throws ValidationException if error validating data in {@link UserVo}
     */
    public UserVo create(UserVo user) throws ValidationException {
        return userCapability.create(user);
    }
    
    /**
     * Return true if a {@link UserVo} with the given ID exists.
     * 
     * @param id Long
     * @return boolean
     */
    public boolean exists(Long id) {
        return userCapability.exists(id);
    }
    
    /**
     * setUserCapability
     * @param userCapability userCapability property
     */
    public void setUserCapability(UserCapability userCapability) {
        this.userCapability = userCapability;
    }

}
