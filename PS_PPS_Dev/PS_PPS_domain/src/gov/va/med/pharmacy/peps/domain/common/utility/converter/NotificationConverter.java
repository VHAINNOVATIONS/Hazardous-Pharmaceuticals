/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationDo;


/**
 * Convert to/from {@link NotificationVo} and {@link EplNotificationDo}.
 */
public class NotificationConverter extends Converter<NotificationVo, EplNotificationDo> {
    private NotificationStatusConverter notificationStatusConverter;

    /**
     * Fully copies data from the given {@link DataObject} into a {NotificationVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated NotificationVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplNotificationDo toDataObject(NotificationVo data) {
        EplNotificationDo noteDo = new EplNotificationDo();

        noteDo.setId(Long.valueOf(data.getId()));

        if (data.getNotificationType() != null) {
            noteDo.setNotificationType(data.getNotificationType().name());
        }

        return noteDo;
    }

    /**
     * toValueObject Notification Do
     * 
     * @param data EplNotificationDo to convert
     * @return fully populated NotificationVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected NotificationVo toValueObject(EplNotificationDo data) {
        NotificationVo type = new NotificationVo();

        type.setId(String.valueOf(data.getId()));
        type.setNotificationType(NotificationType.valueOf(data.getNotificationType()));
        type.setNotificationStatus(notificationStatusConverter.convert(data.getEplNotificationStatuses()));
        type.setModifiedBy(data.getLastModifiedBy());

        return type;
    }

    /**
     * setNotificationStatusConverter
     * @param notificationStatusConverter notificationStatusConverter property
     */
    public void setNotificationStatusConverter(NotificationStatusConverter notificationStatusConverter) {
        this.notificationStatusConverter = notificationStatusConverter;
    }
}
