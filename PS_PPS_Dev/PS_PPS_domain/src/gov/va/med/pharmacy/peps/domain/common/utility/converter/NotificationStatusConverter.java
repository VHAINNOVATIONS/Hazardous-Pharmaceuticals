/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.NotificationStatusType;
import gov.va.med.pharmacy.peps.common.vo.NotificationStatusVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationStatusDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationStatusDoKey;


/**
 * Convert to/from {@link NotificationStatusVo} and {@link EplNotificationStatusDo}.
 */
public class NotificationStatusConverter extends
    AssociationConverter<NotificationStatusVo, EplNotificationStatusDo, EplNotificationDo> {

    private UserConverter userConverter;

    /**
     * Minimally copies data from the given NotificationStatusVo into a {@link DataObject}.
     * <p>
     * The returned {@link DataObject} likely only has the primary key data populated.
     * <p>
     * Default implementation calls {@link #toDataObject(ValueObject)}.
     * 
     * @param data {@link ValueObject} to convert
     * @return minimally populated NotificationStatusVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplNotificationStatusDo toDataObject(NotificationStatusVo data) {
        EplNotificationStatusDoKey key = new EplNotificationStatusDoKey();
        key.setUserIdFk(data.getUser().getId());

        EplNotificationStatusDo status = new EplNotificationStatusDo();
        status.setKey(key);
        status.setStatus(data.getStatus().name());
        status.setEplUser(userConverter.convert(data.getUser()));

        return status;
    }

    /**
     * Fully copies the Notification data to the dataObject.
     * 
     * @param data NotificationStatusVo to convert
     * @param parent EplNotificationDo representing the parent item in the association
     * @param sequence ordinal location of the current setNotificationIdFk to convert, used only if order matters in this
     *            association
     * @return fully populated EplNotificationStatusDo
     */
    @Override
    protected EplNotificationStatusDo toDataObject(NotificationStatusVo data, EplNotificationDo parent, int sequence) {
        EplNotificationStatusDo status = convert(data);
        status.getKey().setNotificationIdFk(parent.getId());

        return status;
    }

    /**
     * Create a new {@link EplNotificationDo} for the given Notification ID and user ID.
     * 
     * @param notificationId Long
     * @param userId Long
     * @param notifStatus NotificationStatusType
     * @return EplNotificationStatusDo
     * 
     * @deprecated Modify the Presentation/Service/Domain code to pass around an actual NotificationStatusVo instead of its
     *             separate pieces so {@link #convert(NotificationStatusVo, EplNotificationDo)} can be called instead of this
     *             one. Will be accomplished in M5P2
     */
    public EplNotificationStatusDo convert(Long notificationId, Long userId, NotificationStatusType notifStatus) {
        EplNotificationStatusDoKey key = new EplNotificationStatusDoKey();
        key.setNotificationIdFk(notificationId);
        key.setUserIdFk(userId);

        EplNotificationStatusDo status = new EplNotificationStatusDo();
        status.setKey(key);
        status.setStatus(notifStatus.name());

        return status;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a NotificationStatusVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated NotificationStatusVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected NotificationStatusVo toValueObject(EplNotificationStatusDo data) {
        NotificationStatusVo type = new NotificationStatusVo();

        type.setStatus(NotificationStatusType.valueOf(data.getStatus()));
        type.setUser(userConverter.convert(data.getEplUser()));

        return type;
    }

    /**
     * setUserConverter
     * @param userConverter userConverter property
     */
    public void setUserConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }
}
