/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item;


import java.util.Date;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.domain.NdfDomainConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf.NdcUpnFileConverter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.ndf.NdfDomain;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.ndcitem.NdcItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.ndcitem.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.ndcupn.NdcUpnFile;


/**
 * Converts NDC VOs to NDC documents.
 */
public class NdcItemConverter extends AbstractConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Convert to NDC Item document.
     * 
     * @param ndcVo NDC VO
     * @param differences old/new value differences
     * @param itemAction add/modify/inactivate
     * @param domains domain files
     * @return NDC document
     */
    public static NdcItem toNdcItem(NdcVo ndcVo, Map<FieldKey, Difference> differences, ItemAction itemAction,
                                    DomainItem[] domains) {
        NdcItem item = FACTORY.createNdcItem();

        ItemAction ndcFileAction = toNdcFileAction(differences, itemAction);

        if (RequestItemStatus.APPROVED.equals(ndcVo.getRequestItemStatus())
            && NdcUpnFileConverter.hasNewOrModifiedFields(NdcUpnFileConverter.FIELDS, differences, ndcFileAction)) {
            NdcUpnFile ndcUpnFile = NdcUpnFileConverter.toNdcUpnFile(ndcVo, differences, ndcFileAction);
            ndcUpnFile.setAction(ndcFileAction);
            item.setNdcUpnFile(ndcUpnFile);
        }

        // Manufacturer
        NdfDomain ndfDomain = NdfDomainConverter.toNdfDomain(domains);

        if (ndfDomain.isSetDrugManufacturerFile() && (ndfDomain.getDrugManufacturerFile().size() == 1)) {
            item.setDrugManufacturerFile(ndfDomain.getDrugManufacturerFile().get(0));
        }

        return item;
    }

    /**
     * Get NDC file action.
     * 
     * @param differences differences
     * @param itemAction default action
     * @return action
     */
    private static ItemAction toNdcFileAction(Map<FieldKey, Difference> differences, ItemAction itemAction) {

        if (RequestItemStatus.PENDING.equals(getOldValue(FieldKey.REQUEST_ITEM_STATUS, differences))) {
            return ItemAction.ADD;
        }

        if (ItemAction.INACTIVATE.equals(itemAction) && !hasOldValue(FieldKey.ITEM_STATUS, differences)) {
            return ItemAction.MODIFY; // override disingenuous inactivation
        }

        // put in the Inactivation date Difference if the item is being in-activated. 
        if (ItemStatus.INACTIVE.equals(getOldValue(FieldKey.ITEM_STATUS, differences))
            && ItemStatus.ACTIVE.equals(getNewValue(FieldKey.ITEM_STATUS, differences))) { // reactivate

            differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, new Date(), null));
        }

        return itemAction;
    }

}
