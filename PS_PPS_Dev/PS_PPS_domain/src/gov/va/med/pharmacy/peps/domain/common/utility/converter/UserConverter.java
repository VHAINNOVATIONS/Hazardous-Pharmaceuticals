/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotifUserPrefDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotifUserPrefDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplSessionPreferenceDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplUserDo;


/**
 * Convert to/from {@link UserVo} and {@link EplUserDo}.
 */
public class UserConverter extends Converter<UserVo, EplUserDo> {

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplUserDo toDataObject(UserVo data) {
        EplUserDo user = new EplUserDo();

        user.setId(data.getId());
        user.setDuz(data.getDuz());
        user.setFirstName(data.getFirstName());
        user.setLastName(data.getLastName());
        user.setLocation(data.getLocation());
        user.setUserName(data.getUsername());

        if (data.getRolePsrFlag()) {
            user.setRolePsrFlag("Y");
        } else {
            user.setRolePsrFlag("N");
        }

        if (data.getRolePnmFlag()) {
            user.setRolePnmFlag("Y");
        } else {
            user.setRolePnmFlag("N");
        }

        if (data.getRolePnsFlag()) {
            user.setRolePnsFlag("Y");
        } else {
            user.setRolePnsFlag("N");
        }

        if (data.getRoleMigFlag()) {
            user.setRoleMigFlag("Y");
        } else {
            user.setRoleMigFlag("N");
        }

        if (data.getRolePsrApvdFlag()) {
            user.setRolePsrApvdFlag("Y");
        } else {
            user.setRolePsrApvdFlag("N");
        }

        if (data.getRolePnmApvdFlag()) {
            user.setRolePnmApvdFlag("Y");
        } else {
            user.setRolePnmApvdFlag("N");
        }

        if (data.getRolePnsApvdFlag()) {
            user.setRolePnsApvdFlag("Y");
        } else {
            user.setRolePnsApvdFlag("N");
        }

        if (data.getRoleMigApvdFlag()) {
            user.setRoleMigApvdFlag("Y");
        } else {
            user.setRoleMigApvdFlag("N");
        }

        if (data.getSessionPreferences() != null) {
            user.setEplSessionPreferences(toSessionPreferences(data.getSessionPreferences(), user));
        }

        if (data.getNotifications() != null) {
            user.setEplNotifUserPrefs(toUserPrefs(data.getPreferences(), user.getId()));
        }

        return user;
    }

    /**
     * Converts Map<SessionPreferenceType, String> to Set of EplSessionPreferenceDo
     * 
     * @param preferenceTypes Map of preference types (from UserVo)
     * @param user associated user
     * @return Set of EplSessionPreferenceDo
     */
    private Set<EplSessionPreferenceDo> toSessionPreferences(Map<SessionPreferenceType, String> preferenceTypes,
        EplUserDo user) {
        Set<EplSessionPreferenceDo> types = new HashSet<EplSessionPreferenceDo>();

        for (SessionPreferenceType key : preferenceTypes.keySet()) {
            types.add(toSessionPreference(key, preferenceTypes.get(key), user));
        }

        return types;
    }

    /**
     * Converts Map<SessionPreferenceType, String> to Set of EplSessionPreferenceDo
     * 
     * @param preferenceTypes List
     * @param userId associated user
     * @return Set of EplSessionPreferenceDo
     */
    private Set<EplNotifUserPrefDo> toUserPrefs(List<NotificationType> preferenceTypes, Long userId) {
        Set<EplNotifUserPrefDo> types = new HashSet<EplNotifUserPrefDo>();

        for (NotificationType notificationType : preferenceTypes) {

            EplNotifUserPrefDoKey doKey = new EplNotifUserPrefDoKey();
            doKey.setNotificationType(notificationType.name());
            doKey.setUserIdFk(userId);

            EplNotifUserPrefDo userAssoc = new EplNotifUserPrefDo();
            userAssoc.setKey(doKey);

            types.add(userAssoc);
        }

        return types;
    }

    /**
     * Converts EplVaPreferenceTypeDo to PreferenceTypeVo
     * 
     * @param preferenceType SessionPreferenceType enum value
     * @param preferenceValue preference value (e.g. a search template FK for a default search template)
     * @param user associated user
     * @return PreferenceTypeVo
     */
    private EplSessionPreferenceDo toSessionPreference(SessionPreferenceType preferenceType, String preferenceValue,
        EplUserDo user) {
        EplSessionPreferenceDo sessionPreferenceDo = new EplSessionPreferenceDo();

        sessionPreferenceDo.setPrefName(preferenceType.name());
        sessionPreferenceDo.setPrefValue(preferenceValue);
        sessionPreferenceDo.setEplUser(user);

        return sessionPreferenceDo;
    }

    /**
     * Fully copies data from the given EplUserDo into a {@link ValueObject}.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data EplUserDo to convert
     * @return fully populated {@link ValueObject}
     */
    @Override
    protected UserVo toValueObject(EplUserDo data) {
        UserVo user = new UserVo();
        user.setFirstName(data.getFirstName());
        user.setLastName(data.getLastName());
        user.setUsername(data.getUserName());

        if ("Y".equals(data.getRolePsrFlag())) {
            user.setRolePsrFlag(true);
        } else {
            user.setRolePsrFlag(false);
        }

        if ("Y".equals(data.getRolePnmFlag())) {
            user.setRolePnmFlag(true);
        } else {
            user.setRolePnmFlag(false);
        }

        if ("Y".equals(data.getRolePnsFlag())) {
            user.setRolePnsFlag(true);
        } else {
            user.setRolePnsFlag(false);
        }

        if ("Y".equals(data.getRoleMigFlag())) {
            user.setRoleMigFlag(true);
        } else {
            user.setRoleMigFlag(false);
        }

        if ("Y".equals(data.getRolePsrApvdFlag())) {
            user.setRolePsrApvdFlag(true);
        } else {
            user.setRolePsrApvdFlag(false);
        }

        if ("Y".equals(data.getRolePnmApvdFlag())) {
            user.setRolePnmApvdFlag(true);
        } else {
            user.setRolePnmApvdFlag(false);
        }

        if ("Y".equals(data.getRolePnsApvdFlag())) {
            user.setRolePnsApvdFlag(true);
        } else {
            user.setRolePnsApvdFlag(false);
        }

        if ("Y".equals(data.getRoleMigApvdFlag())) {
            user.setRoleMigApvdFlag(true);
        } else {
            user.setRoleMigApvdFlag(false);
        }

        user.setLocation(data.getLocation());
        user.setId(data.getId());
        user.setDuz(data.getDuz());

        if (data.getEplSessionPreferences() != null) {
            user.setSessionPreferences(toSessionPreferences(data.getEplSessionPreferences()));
        }

        if (data.getEplNotifUserPrefs() != null) {
            user.setNotifications(toNotificationPreferences(data.getEplNotifUserPrefs()));
            user.generatePreferenceGroups();
        }

        return user;
    }

    /**
     * Converts Set of EplPreferenceTypeeDo to Set of PreferenceTypeVo
     * 
     * @param data Collection of EplPreferenceTypeeDo
     * @return Collection of PreferenceTypeVo
     */
    private Map<SessionPreferenceType, String> toSessionPreferences(Collection<EplSessionPreferenceDo> data) {
        Map<SessionPreferenceType, String> types = new HashMap<SessionPreferenceType, String>();

        SessionPreferenceType key;

        for (EplSessionPreferenceDo typeDo : data) {
            key = SessionPreferenceType.valueOf(typeDo.getPrefName());
            types.put(key, typeDo.getPrefValue());
        }

        return types;
    }

    /**
     * Converts Set of EplPreferenceTypeeDo to Set of PreferenceTypeVo
     * 
     * @param data Collection of EplPreferenceTypeeDo
     * @return Collection of PreferenceTypeVo
     */
    private List<NotificationType> toNotificationPreferences(Collection<EplNotifUserPrefDo> data) {
        List<NotificationType> types = new ArrayList<NotificationType>();

        for (EplNotifUserPrefDo typeDo : data) {

            NotificationType notif = NotificationType.valueOf(typeDo.getKey().getNotificationType());
            types.add(notif);

        }

        return types;
    }
}
