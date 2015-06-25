/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.exception.AuthenticationException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform security actions on a user
 */
public interface UserService {

    /**
     * Retrieve the fields this user is allowed to edit on the given model.
     * 
     * @param user UserVo for which to retrieve authorized fields
     * @param obj Object the user is working with. Could be any ValueObject.
     * @return FieldAuthorizationVo containing fields that are disabled and editable
     */
    FieldAuthorizationVo authorizeFields(UserVo user, Object obj);

    /**
     * Authenticate this user by verifying the username and password exist. Set the available roles for the User and load
     * user preferences.
     * 
     * @param user String username for the PEPS system
     * @return UserVo containing user information
     * 
     * @throws AuthenticationException if the given username/password pair do not authenticate
     */
    UserVo login(UserVo user) throws AuthenticationException;

    /**
     * Updates teh user
     * 
     * @param user String username for the PEPS system
     * @return UserVo containing user information
     * 
     * @throws AuthenticationException if the given username/password pair do not authenticate
     */
    UserVo update(UserVo user) throws AuthenticationException;

    
    /**
     * Log the user out of the system.
     * 
     * @param user UserVo to log out
     */
    void logout(UserVo user);

    /**
     * Update/persist the user's selected display settings and preferences. Preferences are stored in the
     * {@link UserVo#getPreferences()} {@link Map}.
     * 
     * @param user {@link UserVo} with updated preferences
     * @return updated UserVo
     * @throws ValueObjectValidationException if errors validating user preferences
     */
    UserVo updatePreferences(UserVo user) throws ValueObjectValidationException;

    /**
     * Load user by ID (KAAJEE DUZ)
     * 
     * @param duz Long
     * @param station String
     * @return UserVo
     * 
     * @throws AuthenticationException if user could not be found in the database
     */
    UserVo retrieve(Long duz, String station) throws AuthenticationException;

    /**
     * RetrieveAllusers
     * 
     * @return UserVo
     * 
     * @throws AuthenticationException if user could not be found in the database
     */
    Collection<UserVo> retrieveAll() throws AuthenticationException;
    
    /**
     * Retrieve all notification types to populate preference selection screen
     * 
     * @return List of notification types
     */
    List<NotificationType> retrieveAllNotificationTypes();

    /**
     * Create a new {@link UserVo}.
     * 
     * @param user {@link UserVo} to create
     * @return created {@link UserVo}
     * @throws ValidationException if error validating data in {@link UserVo}
     */
    UserVo create(UserVo user) throws ValidationException;

    /**
     * Return true if a {@link UserVo} with the given ID exists.
     * 
     * @param id Long
     * @return boolean
     */
    boolean exists(Long id);
}
