/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugTextDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;


/**
 * Execute RX Consult specific rules.
 */
public class DrugTextRulesCapabilityImpl extends DefaultRulesCapabilityImpl<DrugTextVo> {

    /**
     * Merge the existing Local item that matches the given National item by uniqueness fields.
     * <p>
     * updates the drug text using the existing Local item to point to the new National item.
     * 
     * @param nationalItem {@link ManagedItemVo} from National
     * 
     * @see #insertFromNational(ManagedItemVo)
     * @param user {@link UserVo} performing operation
     * @return merged {@link ManagedItemVo}
     * @throws ValidationException if error validating data during update
     * 
     * @see gov.va.med.pharmacy.peps.service.common.capability.impl.DefaultRulesCapabilityImpl#mergeFromNational(
     * gov.va.med.pharmacy.peps.common.vo.ManagedItemVo,
     * gov.va.med.pharmacy.peps.common.vo.UserVo)
     */
    @Override
    public DrugTextVo mergeFromNational(DrugTextVo nationalItem, UserVo user) throws ValidationException {
        UserVo systemUser = UserVo.getSystemUser(getEnvironmentUtility());
        ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
        audit.setAuditItemType(EntityType.DRUG_TEXT);
        audit.setAuditItemId(nationalItem.getId());
        audit.setSiteName(getEnvironmentUtility().getSiteNumber()); 
        audit.setEventCategory(EventCategory.SYSTEM_EVENT);
        audit.setReason("New National Drug replaced duplicate existing Local Drug Text ."); 
        audit.setOldValue("");
        audit.setNewValue("");
        audit.setUsername(systemUser.getUsername());
        
        nationalItem.getItemAuditHistory().add(audit);

        // retrieve local item since it exists by unique fields
        DrugTextDomainCapability domainCapability = getManagedItemCapabilityFactory().getDomainCapability(
            EntityType.DRUG_TEXT);
        ManagedItemVo localItem = domainCapability.getUniqueItem(nationalItem);

        DrugTextVo existingLocal = (DrugTextVo) (localItem);

        // replace teh nationalitems local drug text with the existing Local's drug text
        nationalItem.setTextLocal(existingLocal.getTextLocal());

        DrugTextVo result = insertFromNationalWithoutDuplicateCheck(nationalItem, user);

        List<DrugTextVo> existingItems = domainCapability.retrieveDuplicates(nationalItem);

        for (DrugTextVo existingItem : existingItems) {
            domainCapability.reassociateExistingItems(existingItem, nationalItem, user);
        }

        return result;
    }

    /**
     * Update the existing Local item (by ID) with the data contained in the given item from National.
     * <p>
     * Default implementation ignores PENDING items. All other types have the VA data fields updated and merged appropriately
     * prior to saving in the database.
     * <p>
     * 
     * @param nationalItem ManagedItemVo from National
     * @param user {@link UserVo} performing operation
     * @return updated {@link ManagedItemVo}
     * @throws ValidationException if error validating data during update
     */
    @Override
    public DrugTextVo updateFromNational(DrugTextVo nationalItem, UserVo user) throws ValidationException {

        DrugTextVo updatedItem = nationalItem;

        if (!RequestItemStatus.PENDING.equals(updatedItem.getRequestItemStatus())) {
            ManagedItemDomainCapability domainCapability = getManagedItemCapabilityFactory().getDomainCapability(
                updatedItem.getEntityType());
            ManagedItemVo existingItem = domainCapability.retrieve(updatedItem.getId());

            // compare the existing item's local drug text value and national drug text value
            // if same overwrite national item's local drug text with national item's national drug text
            DrugTextVo existingLocal = (DrugTextVo) (existingItem);

            if (existingLocal.getTextLocal().equals(existingLocal.getTextNational())) {
                updatedItem.setTextLocal(updatedItem.getTextNational());
            } else {
                updatedItem.setTextLocal(existingLocal.getTextLocal());
            }

            updatedItem = (DrugTextVo) getManagedItemCapability().update(updatedItem, user);
        }

        return updatedItem;
    }

    /**
     * Insert the new dryg text from National into Local.
     * <p>
     * Default implementation sets local use to false, enforces business rules, and inserts the National item.
     * 
     * @param nationalItem {@link DrugTextVo} from National
     * @param user {@link UserVo} performing operation
     * @return inserted {@link DrugTextVo}
     * @throws ValidationException if error validating data during update
     * 
     * this method calls all the statements in enforceRules and handleEnforceRules I had to break these methods apart to
     * allow the automatic addition of local possible dosages to the product vo when it is inserted from national
     */
    @Override
    public DrugTextVo insertFromNational(DrugTextVo nationalItem, UserVo user) throws ValidationException {
        nationalItem.setTextLocal(nationalItem.getTextNational());

        return super.insertFromNational(nationalItem, user);
    }

}
