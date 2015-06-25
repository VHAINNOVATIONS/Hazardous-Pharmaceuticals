/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.domain;


import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf.DrugIngredientsFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf.DrugManufacturerFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf.DrugUnitsFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf.VaDispenseUnitFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf.VaDrugClassFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf.VaGenericFileConverter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.ndf.NdfDomain;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.ndf.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.drugingredients.DrugIngredientsFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.drugmanufacturer.DrugManufacturerFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.drugunits.DrugUnitsFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vadispenseunit.VaDispenseUnitFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vadrugclass.VaDrugClassFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vageneric.VaGenericFile;


/**
 * Convert one or more domain VOs to VistA domain files.
 */
public class NdfDomainConverter extends AbstractConverter {

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Convert one or more domain VOs to VistA domain files.
     * 
     * @param items domain items
     * @return NDF domain document
     */
    public static NdfDomain toNdfDomain(DomainItem[] items) {
        NdfDomain ndfDomain = FACTORY.createNdfDomain();

        for (DomainItem item : items) {
            ItemAction action = toNdfFileAction(item.getDifference(), item.getAction());

            if ((item.getItem() instanceof IngredientVo)
                && (DrugIngredientsFileConverter.hasNewOrModifiedFields(DrugIngredientsFileConverter.FIELDS, item
                    .getDifference(), action))) {

                DrugIngredientsFile drugIngredientsFile = DrugIngredientsFileConverter.toDrugIngredientsFile(
                    (IngredientVo) item.getItem(), item.getDifference(), action);
                drugIngredientsFile.setAction(action);
                ndfDomain.getDrugIngredientsFile().add(drugIngredientsFile);
            } else if ((item.getItem() instanceof ManufacturerVo)
                && DrugManufacturerFileConverter.hasNewOrModifiedFields(DrugManufacturerFileConverter.FIELDS, item
                    .getDifference(), action)) {

                DrugManufacturerFile drugManufacturerFile = DrugManufacturerFileConverter.toDrugManufacturerFile(
                    (ManufacturerVo) item.getItem(), item.getDifference(), action);
                drugManufacturerFile.setAction(action);
                ndfDomain.getDrugManufacturerFile().add(drugManufacturerFile);
            } else if ((item.getItem() instanceof DrugUnitVo)
                && (DrugUnitsFileConverter.hasNewOrModifiedFields(DrugUnitsFileConverter.FIELDS, item.getDifference(),
                    action))) {

                DrugUnitsFile drugUnitsFile = DrugUnitsFileConverter.toDrugUnitsFile((DrugUnitVo) item.getItem(), item
                    .getDifference(), action);
                drugUnitsFile.setAction(action);
                ndfDomain.getDrugUnitsFile().add(drugUnitsFile);
            } else if ((item.getItem() instanceof DispenseUnitVo)
                && (VaDispenseUnitFileConverter.hasNewOrModifiedFields(VaDispenseUnitFileConverter.FIELDS, item
                    .getDifference(), action))) {

                VaDispenseUnitFile vaDispenseUnitFile = VaDispenseUnitFileConverter.toVaDispenseUnitFile(
                    (DispenseUnitVo) item.getItem(), item.getDifference(), action);
                vaDispenseUnitFile.setAction(action);
                ndfDomain.getVaDispenseUnitFile().add(vaDispenseUnitFile);
            } else if ((item.getItem() instanceof DrugClassVo)
                && (VaDrugClassFileConverter.hasNewOrModifiedFields(VaDrugClassFileConverter.FIELDS, item.getDifference(),
                    action))) {

                VaDrugClassFile vaDrugClassFile = VaDrugClassFileConverter.toVaDrugClassFile((DrugClassVo) item.getItem(),
                    item.getDifference(), action);
                vaDrugClassFile.setAction(action);
                ndfDomain.getVaDrugClassFile().add(vaDrugClassFile);
            } else if ((item.getItem() instanceof GenericNameVo)
                && (VaGenericFileConverter.hasNewOrModifiedFields(VaGenericFileConverter.FIELDS, item.getDifference(),
                    action))) {

                VaGenericFile vaGenericFile = VaGenericFileConverter.toVaGenericFile((GenericNameVo) item.getItem(), item
                    .getDifference(), action);
                vaGenericFile.setAction(action);
                ndfDomain.getVaGenericFile().add(vaGenericFile);
            }
        }

        return ndfDomain;
    }

    /**
     * Get NDF domain action.
     * 
     * @param differences differences
     * @param itemAction default action
     * @return action
     */
    private static ItemAction toNdfFileAction(Map<FieldKey, Difference> differences, ItemAction itemAction) {

        // M5P2
        // if (RequestItemStatus.PENDING.equals(getOldValue(FieldKey.REQUEST_ITEM_STATUS, differences))) {
        // return ItemAction.ADD;
        // }

        return itemAction;
    }
}
