/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item;


import java.util.Date;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.domain.PdmDomainConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.PharmacyOrderableItemFileConverter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.pdm.PdmDomain;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.orderableitem.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.orderableitem.OrderableItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.pharmacyorderableitem.PharmacyOrderableItemFile;


/**
 * Convert to Orderable Item document.
 */
public class OrderableItemConverter extends AbstractConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Convert to Orderable Item.
     * 
     * @param orderableItemVo orderable item
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @param domains domain files
     * @param isLocal local environment
     * @return document
     */
    public static OrderableItem toOrderableItem(OrderableItemVo orderableItemVo, Map<FieldKey, Difference> differences,
                                                ItemAction itemAction, DomainItem[] domains, boolean isLocal) {

        OrderableItem item = FACTORY.createOrderableItem();

        ItemAction orderableFileAction = toOrderableFileAction(orderableItemVo, differences, itemAction);

        if (isLocal
            && (orderableItemVo.isLocalUse() || ItemAction.INACTIVATE.equals(orderableFileAction))
            && PharmacyOrderableItemFileConverter.hasNewOrModifiedFields(PharmacyOrderableItemFileConverter.FIELDS,
                differences, orderableFileAction)) {

            PharmacyOrderableItemFile pharmacyOrderableItemFile = PharmacyOrderableItemFileConverter
                .toPharmacyOrderableItemFile(orderableItemVo, differences, orderableFileAction);
            pharmacyOrderableItemFile.setAction(orderableFileAction);
            item.setPharmacyOrderableItemFile(pharmacyOrderableItemFile);

        }

        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(domains);

        if (pdmDomain.isSetDosageFormFile() && (pdmDomain.getDosageFormFile().size() == 1)) {
            item.setDosageFormFile(pdmDomain.getDosageFormFile().get(0));
        }

        if (pdmDomain.isSetMedicationRoutesFile() && (pdmDomain.getMedicationRoutesFile().size() == 1)) {
            item.setMedicationRoutesFile(pdmDomain.getMedicationRoutesFile().get(0));
        }

        if (pdmDomain.isSetStandardMedicationRoutesFile() && (pdmDomain.getStandardMedicationRoutesFile().size() == 1)) {
            item.setStandardMedicationRoutesFile(pdmDomain.getStandardMedicationRoutesFile().get(0));
        }

        return item;

    }

    /**
     * Get orderable file action.
     * 
     * @param orderableVo Product
     * @param differences differences
     * @param itemAction default action
     * @return drug file action
     */
    private static ItemAction toOrderableFileAction(OrderableItemVo orderableVo, Map<FieldKey, Difference> differences,
                                                    ItemAction itemAction) {

        if (orderableVo.isLocalUse() && (Boolean.FALSE.equals(getOldValue(FieldKey.LOCAL_USE, differences)))) {

            // send MODIFY action for reactivation if item has previously been activated at any point
            if (orderableVo.isPreviouslyMarkedForLocalUse()) {
                differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, new Date(), null));

                return ItemAction.MODIFY;
            }

            return ItemAction.ADD;
        }

        if (!orderableVo.isLocalUse() && Boolean.TRUE.equals(getOldValue(FieldKey.LOCAL_USE, differences))) {
            differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, null, new Date()));

            return ItemAction.INACTIVATE;
        }

        if (ItemAction.INACTIVATE.equals(itemAction) && !hasOldValue(FieldKey.ITEM_STATUS, differences)) {
            return ItemAction.MODIFY; // override disingenuous inactivation
        }

        if (ItemAction.INACTIVATE.equals(itemAction)) { // manually add inactivation date for national messages
            differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, null, orderableVo
                .getInactivationDate()));
        }

        return itemAction;
    }
}
