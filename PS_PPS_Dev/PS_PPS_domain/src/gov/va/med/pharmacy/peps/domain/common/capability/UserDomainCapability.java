/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.AuthenticationException;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform CRUD operations on user
 */
public interface UserDomainCapability {

    /**
     * Retrieve all possible users
     * 
     * @return List of Generic Names
     */
    List<UserVo> retrieveAll();

    /**
     * Retrieves user by username.
     * 
     * @param username username (login name)
     * @return matching user
     */
    UserVo retrieve(UserVo username);

    /**
     * Retrieves user by id
     * 
     * @param duz String
     * @param station String
     * @return matching user
     * 
     * @throws AuthenticationException if user could not be found in the database
     */
    UserVo retrieve(Long duz, String station) throws AuthenticationException;

    /**
     * Insert the given UserVo.
     * 
     * @param user UserVo
     * @return UserVo with new ID
     */
    UserVo create(UserVo user);
    
    /**
     * Return true if a {@link UserVo} with the given ID exists.
     * 
     * @param id Long
     * @return boolean
     */
    boolean exists(Long id);

    /**
     * Update the given UserVo.
     * 
     * @param userVo UserVo
     * @return UserVo
     */
    UserVo update(UserVo userVo);

    /**
     * Update the given user's preferences.
     * 
     * @param userVo UserVo
     * @return UserVo
     */
    UserVo updatePreferences(UserVo userVo);
}
