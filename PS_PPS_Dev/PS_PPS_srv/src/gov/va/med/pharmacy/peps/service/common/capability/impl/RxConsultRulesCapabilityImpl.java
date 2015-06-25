/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.RxConsultDomainCapability;


/**
 * Execute RX Consult specific rules.
 */
public class RxConsultRulesCapabilityImpl extends DefaultRulesCapabilityImpl<RxConsultVo> {

    /**
     * Merge the existing Local item that matches the given National item by uniqueness fields.
     * <p>
     * Calls the default implementation in the super class, which inactivates existing Local items and inserts item from
     * National. Then updates the Products using the existing Local item to point to the new National item.
     * 
     * @param nationalItem {@link ManagedItemVo} from National
     * 
     * @see #insertFromNational(ManagedItemVo)
     * @param user {@link UserVo} performing operation
     * @return merged {@link ManagedItemVo}
     * @throws ValidationException if error validating data during update
     * 
     * @see gov.va.med.pharmacy.peps.service.common.capability.impl.DefaultRulesCapabilityImpl#mergeFromNational(
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo, gov.va.med.pharmacy.peps.common.vo.UserVo)
     */
    @Override
    public RxConsultVo mergeFromNational(RxConsultVo nationalItem, UserVo user) throws ValidationException {
        UserVo systemUser = UserVo.getSystemUser(getEnvironmentUtility());
        
        ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
        audit.setAuditItemType(EntityType.RX_CONSULT);
        audit.setAuditItemId(nationalItem.getId());
        audit.setSiteName(getEnvironmentUtility().getSiteNumber()); 
        audit.setEventCategory(EventCategory.SYSTEM_EVENT);
        audit.setReason("NeNew National Warning Label replaced duplicate existing Local Warning Label."); 
        audit.setOldValue("");
        audit.setNewValue("");
        audit.setUsername(systemUser.getUsername());
        
        nationalItem.getItemAuditHistory().add(audit);

        RxConsultVo result = insertFromNationalWithoutDuplicateCheck(nationalItem, user);

        RxConsultDomainCapability domainCapability = getManagedItemCapabilityFactory().getDomainCapability(
            EntityType.RX_CONSULT);
        List<RxConsultVo> existingItems = domainCapability.retrieveDuplicates(nationalItem);

        for (RxConsultVo existingItem : existingItems) {
            domainCapability.reassociateExistingItems(existingItem, nationalItem, user);
        }

        return result;
    }
}
