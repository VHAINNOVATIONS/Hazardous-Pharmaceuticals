/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item;


import java.util.Date;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.domain.NdfDomainConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.domain.PdmDomainConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf.VaProductFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.DrugFileConverter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.ndf.NdfDomain;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.pdm.PdmDomain;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.productitem.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.productitem.ProductItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vaproduct.VaProductFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.DrugFile;


/**
 * Converts Product VOs to Product documents.
 */
public class ProductItemConverter extends AbstractConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Convert to Product document.
     * 
     * @param productVo product
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @param domains domain files
     * @param isLocalEnvironment true if local
     * @return Product document
     */
    public static ProductItem toProductItem(ProductVo productVo, Map<FieldKey, Difference> differences,
                                            ItemAction itemAction, DomainItem[] domains, boolean isLocalEnvironment) {

        ProductItem item = FACTORY.createProductItem();

        ItemAction productFileAction = toProductFileAction(differences, itemAction);

        // Product File (#50.68)
        if (RequestItemStatus.APPROVED.equals(productVo.getRequestItemStatus())
            && VaProductFileConverter.hasNewOrModifiedFields(VaProductFileConverter.FIELDS, differences, productFileAction)) {

            VaProductFile vaProductFile = VaProductFileConverter.toVaProductFile(productVo, differences, productFileAction);

            // override item action if previously pending
            vaProductFile.setAction(productFileAction);

            item.setVaProductFile(vaProductFile);
        }

        ItemAction drugFileAction = toDrugFileAction(productVo, differences, productFileAction); // inherit 50.68 action

        // Drug File (#50)
        if (isLocalEnvironment && (productVo.isLocalUse() || ItemAction.INACTIVATE.equals(drugFileAction))
            && DrugFileConverter.hasNewOrModifiedFields(DrugFileConverter.FIELDS, differences, drugFileAction)) {

            DrugFile drugFile = DrugFileConverter.toDrugFile(productVo, differences, drugFileAction);
            drugFile.setAction(drugFileAction);
            item.setDrugFile(drugFile);
        }

        // NDF Domain
        NdfDomain ndfDomain = NdfDomainConverter.toNdfDomain(domains);

        if (ndfDomain.isSetVaDispenseUnitFile() && (ndfDomain.getVaDispenseUnitFile().size() == 1)) {
            item.setVaDispenseUnitFile(ndfDomain.getVaDispenseUnitFile().get(0));
        }

        if (ndfDomain.isSetVaDrugClassFile() && (ndfDomain.getVaDrugClassFile().size() == 1)) {
            item.setVaDrugClassFile(ndfDomain.getVaDrugClassFile().get(0));
        }

        if (ndfDomain.isSetVaGenericFile() && (ndfDomain.getVaGenericFile().size() == 1)) {
            item.setVaGenericFile(ndfDomain.getVaGenericFile().get(0));
        }

        // PDM Domain
        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(domains);

        if (pdmDomain.isSetDosageFormFile() && (pdmDomain.getDosageFormFile().size() == 1)) {
            item.setDosageFormFile(pdmDomain.getDosageFormFile().get(0));
        }

        if (pdmDomain.isSetMedicationRoutesFile() && (pdmDomain.getMedicationRoutesFile().size() == 1)) {
            item.setMedicationRoutesFile(pdmDomain.getMedicationRoutesFile().get(0));
        }

        if (pdmDomain.isSetRxConsultFile() && (pdmDomain.getRxConsultFile().size() == 1)) {
            item.setRxConsultFile(pdmDomain.getRxConsultFile().get(0));
        }

        return item;
    }

    /**
     * Get product file action.
     * 
     * @param differences differences
     * @param itemAction default action
     * @return action
     */
    private static ItemAction toProductFileAction(Map<FieldKey, Difference> differences, ItemAction itemAction) {

        // check if an Add action
        if (RequestItemStatus.PENDING.equals(getOldValue(FieldKey.REQUEST_ITEM_STATUS, differences))) {
            return ItemAction.ADD;
        }

        
        // check if an modify action
        if (ItemAction.INACTIVATE.equals(itemAction) && !hasOldValue(FieldKey.ITEM_STATUS, differences)) {
            return ItemAction.MODIFY; // override disingenuous inactivation
        }

        // otherswise and inactivation message
        if (ItemStatus.INACTIVE.equals(getOldValue(FieldKey.ITEM_STATUS, differences))
            && ItemStatus.ACTIVE.equals(getNewValue(FieldKey.ITEM_STATUS, differences))) { // reactivate

            differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, new Date(), null));
        }

        return itemAction;
    }

    /**
     * Get drug file action.
     * 
     * @param productVo Product
     * @param differences differences
     * @param itemAction default action
     * @return drug file action
     */
    private static ItemAction toDrugFileAction(ProductVo productVo, Map<FieldKey, Difference> differences,
                                               ItemAction itemAction) {

        if (productVo.isLocalUse() && (Boolean.FALSE.equals(getOldValue(FieldKey.LOCAL_USE, differences)))) {

            // send MODIFY action for reactivation if item has previously been activated at any point
            if (productVo.isPreviouslyMarkedForLocalUse()) {
                differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, new Date(), null));

                return ItemAction.MODIFY;
            }

            return ItemAction.ADD;
        }

        if (!productVo.isLocalUse() && Boolean.TRUE.equals(getOldValue(FieldKey.LOCAL_USE, differences))) {
            differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, null, new Date()));

            return ItemAction.INACTIVATE;
        }

        if (ItemAction.INACTIVATE.equals(itemAction)) { // manually add inactivation date for national messages
            differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, null, productVo
                .getInactivationDate()));
        }

        return itemAction;
    }
}
