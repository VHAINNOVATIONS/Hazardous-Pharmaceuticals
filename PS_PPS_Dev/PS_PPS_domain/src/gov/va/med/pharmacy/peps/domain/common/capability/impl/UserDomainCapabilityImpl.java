/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;

import gov.va.med.pharmacy.peps.common.exception.AuthenticationException;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.UserDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplNotifUserPrefDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplSessionPreferenceDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplUserDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplUserDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.UserConverter;


/**
 * Perform CRUD operations on user
 */
public class UserDomainCapabilityImpl implements UserDomainCapability {

    private static final Logger LOG = Logger.getLogger(UserDomainCapabilityImpl.class);

    private static final String USERID_FK = "key.userIdFk";

    private EplUserDao eplUserDao;
    private EplSessionPreferenceDao eplSessionPreferenceDao;
    private EplNotifUserPrefDao eplNotifUserPrefDao;
    private UserConverter userConverter;

    /**
     * Retrieves user by id
     * 
     * @param duz user
     * @param station String
     *            
     * @return matching user
     * 
     * @throws AuthenticationException
     *             if user could not be found in the database
     */
    public UserVo retrieve(Long duz, String station) throws AuthenticationException {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(EplUserDo.LOCATION, station);
        map.put(EplUserDo.DUZ, duz);

        try {
            List<EplUserDo> returnedDos = eplUserDao.retrieve(map);

            if (returnedDos.isEmpty()) {
                LOG.debug("UserDomain retrieve " + duz + ":" + station + " is empty.");

                return null;
            } else {
                return userConverter.convert(returnedDos.get(0));
            }
        } catch (ObjectNotFoundException e) {
            throw new AuthenticationException(e, AuthenticationException.USER_NOT_FOUND, duz + ":" + station);
        }
    }

    /**
     * Retrieves user by username.
     * 
     * @param user (login name)
     * @return matching user
     */
    public UserVo retrieve(UserVo user) {
        List<EplUserDo> users = eplUserDao.retrieve("userName", user.getUsername());

        if (users == null || users.isEmpty()) {
            return null;
        }

        return userConverter.convert(users.get(0));
    }

    /**
     * Retrieve all possible User
     * 
     * @return List of User
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.UserDomainCapability#retrieveDomain()
     */
    public List<UserVo> retrieveAll() {
        return userConverter.convert(eplUserDao.retrieve());
    }

    /**
     * Insert the given UserVo.
     * 
     * @param user
     *            UserVo
     * @return UserVo with new ID
     */
    public UserVo create(UserVo user) {

        // need to check if there are any approved supervisors.  If not then approve the first one to login.

        String query = "select count(*) from EplUserDo user where user.rolePnsApvdFlag LIKE 'Y'";
        Long rowsReturned = (Long) eplUserDao.executeHqlQuery(query).iterator().next();

        if (rowsReturned < 2) {
            if (user.getRolePnsFlag()) {
                user.setRolePnsApvdFlag(true);
            }
        }

//        query = "select count(*) from EplUserDo user where user.rolePnmApvdFlag LIKE 'Y'";
//        rowsReturned = (Long) eplUserDao.executeHqlQuery(query).iterator().next();
//
//        if (rowsReturned < 1) {
//            if (user.getRolePnmFlag()) {
//                user.setRolePnmApvdFlag(true);
//            }
//        }
//
//        query = "select count(*) from EplUserDo user where user.rolePsrApvdFlag LIKE 'Y'";
//        rowsReturned = (Long) eplUserDao.executeHqlQuery(query).iterator().next();
//
//        if (rowsReturned < 1) {
//            if (user.getRolePsrFlag()) {
//                user.setRolePsrApvdFlag(true);
//            }
//        }
//
//        query = "select count(*) from EplUserDo user where user.roleMigApvdFlag LIKE 'Y'";
//        rowsReturned = (Long) eplUserDao.executeHqlQuery(query).iterator().next();
//
//        if (rowsReturned < 1) {
//            if (user.getRoleMigFlag()) {
//                user.setRoleMigApvdFlag(true);
//            }
//        }

        EplUserDo userDo = userConverter.convert(user);
        EplUserDo insertedUser = eplUserDao.insert(userDo, user);

        eplSessionPreferenceDao.insert(insertedUser.getEplSessionPreferences(), user);
        UserVo returnedUser = userConverter.convert(insertedUser);

        return returnedUser;
    }

    /**
     * Return true if a {@link UserVo} with the given ID exists.
     * 
     * @param id
     *            Long
     * @return boolean
     */
    public boolean exists(Long id) {
        return eplUserDao.exists(id);
    }

    /**
     * setEplUserDao
     * @param eplUserDao
     *            eplUserDao property
     */
    public void setEplUserDao(EplUserDao eplUserDao) {
        this.eplUserDao = eplUserDao;
    }

    /**
     * setEplSessionPreferenceDao
     * @param eplSessionPreferenceDao
     *            property
     */
    public void setEplSessionPreferenceDao(
            EplSessionPreferenceDao eplSessionPreferenceDao) {
        this.eplSessionPreferenceDao = eplSessionPreferenceDao;
    }

    /**
     * Update the given UserVo.
     * 
     * @param userVo
     *            UserVo
     * @return UserDO
     */
    public UserVo update(UserVo userVo) {

        EplUserDo userDo = userConverter.convert(userVo);
        eplSessionPreferenceDao.delete(USERID_FK, userDo.getId());

        if (userDo.getEplSessionPreferences() != null) {
            eplSessionPreferenceDao.insert(userDo.getEplSessionPreferences(), userVo);
        }

        eplNotifUserPrefDao.delete(USERID_FK, userDo.getId());
        eplNotifUserPrefDao.insert(userDo.getEplNotifUserPrefs(), userVo);
        eplUserDao.update(userDo, userVo);

        return userVo;
    }

    /**
     * Update the given user's preferences.
     * 
     * @param userVo UserVo
     * @return UserVO
     */
    public UserVo updatePreferences(UserVo userVo) {

        LOG.debug(userVo.getPreferences());
        EplUserDo userDo = userConverter.convert(userVo);
        eplSessionPreferenceDao.delete(USERID_FK, userDo.getId());

        if (userDo.getEplSessionPreferences() != null) {
            eplSessionPreferenceDao.insert(userDo.getEplSessionPreferences(), userVo);
        }

        eplNotifUserPrefDao.delete("eplUser.id", userDo.getId());
        eplNotifUserPrefDao.insert(userDo.getEplNotifUserPrefs(), userVo);

        // Reset any preference groups
        userVo.generatePreferenceGroups();

        return userVo;
    }

    /**
     * setEplNotifUserPrefDao
     * @param eplNotifUserPrefDao eplNotifUserPrefDao property
     */
    public void setEplNotifUserPrefDao(EplNotifUserPrefDao eplNotifUserPrefDao) {
        this.eplNotifUserPrefDao = eplNotifUserPrefDao;
    }

    /**
     * setUserConverter
     * @param userConverter
     *            userConverter property
     */
    public void setUserConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }
}
