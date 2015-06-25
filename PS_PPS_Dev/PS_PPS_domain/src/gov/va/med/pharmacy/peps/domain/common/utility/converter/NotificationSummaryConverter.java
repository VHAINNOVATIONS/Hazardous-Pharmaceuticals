/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationStatusType;
import gov.va.med.pharmacy.peps.common.vo.NotificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplItemAuditHistoryDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationStatusDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPharmacySystemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;


/**
 * Convert a {@link EplNotificationDo} into a NotificationSummaryVo (and not the other way around).
 */
public class NotificationSummaryConverter extends Converter<NotificationSummaryVo, EplNotificationDo> {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(NotificationSummaryConverter.class);

    private NotificationStatusConverter notificationStatusConverter;

    /**
     * A NotificationSummaryVo is never saved to the database (the NotificationVo it represents a summary of is, however).
     * Therefore this method does nothing and returns null.
     * 
     * @see NotificationConverter#toDataObject(gov.va.med.pharmacy.peps.common.vo.NotificationVo)
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplNotificationDo toDataObject(NotificationSummaryVo data) {
        
        // This is not used so just return null
        return null;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a NotificationSummaryVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated NotificationSummaryVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected NotificationSummaryVo toValueObject(EplNotificationDo data) {
        NotificationSummaryVo notificationSummary = new NotificationSummaryVo();

        notificationSummary.setNotificationId(data.getId());
        notificationSummary.setOriginator(data.getCreatedBy());
        notificationSummary.setNotificationDate(data.getCreatedDtm());
        notificationSummary.setViewedBy(data.getLastModifiedBy());
        notificationSummary.setCreatedDate(data.getCreatedDtm());
        notificationSummary.setNotificationType(NotificationType.valueOf(data.getNotificationType()));

        return notificationSummary;
    }

    /**
     * Call {@link #convert(EplNotificationDo)} and then set data dependent upon a {@link UserVo}.
     * 
     * @param data {@link EplNotificationDo} to convert
     * @param user {@link UserVo} for which the notification is to be displayed
     * @return {@link NotificationSummaryVo}
     */
    public NotificationSummaryVo convert(EplNotificationDo data, UserVo user) {
        NotificationSummaryVo summary = convert(data);

        if (summary != null) {
            for (EplNotificationStatusDo status : data.getEplNotificationStatuses()) {
                if (user != null && status.getEplUser() != null
                    && status.getEplUser().getId().longValue() == user.getId().longValue()) {
                    if (NotificationStatusType.HIDDEN.toString().equals(status.getStatus())
                        || NotificationStatusType.VIEWEDHIDDEN.toString().equals(status.getStatus())) {
                        summary.setNotificationHidden(true);
                    } else {
                        summary.setNotificationHidden(false);
                    }

                    summary.setStatus(notificationStatusConverter.convert(status));
                    summary.setNotificationStatus(status.getStatus().toString());
                    break;

                }
            }
        }

        return summary;
    }

    /**
     * Call {@link #convert(EplNotificationDo, UserVo)} and then set data dependent upon a {@link EplItemAuditHistoryDo}.
     * 
     * @param data {@link EplNotificationDo} to convert
     * @param user {@link UserVo} for which the notification is to be displayed
     * @param audit {@link EplItemAuditHistoryDo} audit history pertaining to this notification
     * @return {@link NotificationSummaryVo}
     */
    public NotificationSummaryVo convert(EplNotificationDo data, UserVo user, EplItemAuditHistoryDo audit) {
        NotificationSummaryVo summary = convert(data, user);

        if (summary != null) {
            EntityType entityType = EntityType.valueOf(audit.getAuditItemType());
            summary.setItemType(entityType);

            summary.setId(String.valueOf(audit.getAuditItemId()));

            if (summary.getNotificationType() == null) {
                summary.setNotificationType(NotificationType.UNKNOWN_NON_SYSTEM_EVENT);
            }

            String itemName = getItemName(entityType, audit.getAuditItemId());
            summary.setItemName(itemName);

            if (EntityType.NDC.equals(entityType)) {
                summary.setNdc(itemName);
            }

            // LOCAL ONLY functionality PPS-L
//            try {
//                if (entityType != null && summary.getId() != null) {
//                    if (EntityType.PRODUCT.equals(entityType)) {
//                      
//                        EplProductDao dao = getDataAccessObject(EplProductDao.class);
//                        EplProductDo product = dao.retrieve(audit.getAuditItemId());
//                        summary.setLocalUse(toBoolean(product.getLocalUse()));
//                    } else if (EntityType.ORDERABLE_ITEM.equals(entityType)) {
//                        EplOrderableItemDao dao = getDataAccessObject(EplOrderableItemDao.class);
//                        EplOrderableItemDo orderableItem = dao.retrieve(audit.getAuditItemId());
//                        summary.setLocalUse(toBoolean(orderableItem.getLocalUse()));
//                    }
//                }
//            } catch (ObjectNotFoundException e) {
//                LOG.debug("Unable to retrieve local use value!", e);
//            }

        }

        return summary;
    }

    /**
     * Retrieve the minimal version of the NotificationSummaryVo associated with a notification and return the
     * {@link ManagedItemVo#toShortString()}.
     * 
     * @param entityType {@link EntityType} of item to retrieve
     * @param itemId ID of item
     * @return {@link ManagedItemVo#toShortString()} from item returned
     */
    private String getItemName(EntityType entityType, Long itemId) {
        ManagedItemDomainCapability domainCapability = getManagedItemDomainCapability(entityType);
        Criteria criteria = domainCapability.getDataAccessObject().getCriteria();

        if (EntityType.PHARMACY_SYSTEM.equals(entityType)) {
            criteria.add(Restrictions.eq(EplPharmacySystemDo.SITE_NUMBER, Integer.valueOf(itemId.intValue())));
        } else {
            criteria.add(Restrictions.eq(EplProductDo.EPL_ID, itemId));
        }

        DataObject data = null;

        try {
            data = (DataObject) criteria.uniqueResult();
        } catch (Exception e) {
            LOG.debug("Unable to retrieve an item for a notification, returning an empty string for the item's name.", e);
        }

        String itemName = "";

        if (data != null) {
            Converter<ManagedItemVo, DataObject> converter = domainCapability.getConverter();
            ManagedItemVo item = converter.convertMinimal(data);
            itemName = item.toShortString();
        }

        return itemName;
    }

    /**
     * setNotificationStatusConverter
     * @param notificationStatusConverter notificationStatusConverter property
     */
    public void setNotificationStatusConverter(NotificationStatusConverter notificationStatusConverter) {
        this.notificationStatusConverter = notificationStatusConverter;
    }
}
