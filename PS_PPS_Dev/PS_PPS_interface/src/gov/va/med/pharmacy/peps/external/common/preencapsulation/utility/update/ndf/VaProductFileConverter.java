/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf;


import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractEffectiveDateTimeFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vaproduct.ActiveIngredientsFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vaproduct.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vaproduct.VaProductFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vaproduct.VaProductFile.EffectiveDateTime.EffectiveDateTimeFile;


/**
 * Converts Product VO to Product File document.
 */
public class VaProductFileConverter extends AbstractConverter {

    /**
     * FIELDS
     */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.VA_PRODUCT_NAME, FieldKey.GENERIC_NAME, FieldKey.DOSAGE_FORM, FieldKey.ACTIVE_INGREDIENT,
            FieldKey.NATIONAL_FORMULARY_NAME, FieldKey.VA_PRINT_NAME, FieldKey.CMOP_ID, FieldKey.CMOP_DISPENSE,
            FieldKey.DISPENSE_UNIT, FieldKey.GCN_SEQUENCE_NUMBER, FieldKey.PRIMARY_DRUG_CLASS,
            FieldKey.NATIONAL_FORMULARY_INDICATOR, FieldKey.CS_FED_SCHEDULE, FieldKey.SINGLE_MULTISOURCE_PRODUCT,
            FieldKey.INACTIVATION_DATE, FieldKey.OVERRIDE_DF_DOSE_CHK_EXCLUSN, FieldKey.VUID, FieldKey.SERVICE_CODE })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private VaProductFileConverter() {
    }

    /**
     * Check for new/modified fields in the difference set for the VaProductFileConverter.
     * 
     * @param fields set of defined fields
     * @param differences difference set
     * @param itemAction add/modify/inactivate
     * @return true if has modified fields
     */
    public static boolean hasNewOrModifiedFields(Set<FieldKey> fields, Map<FieldKey, Difference> differences,
                                                 ItemAction itemAction) {

        if (hasNewValue(FieldKey.DRUG_CLASSES, differences)) { // check for primary drug class modification
            DrugClassVo newPrimaryClass = findPrimaryDrugClass((Collection<DrugClassGroupVo>) getNewValue(
                FieldKey.DRUG_CLASSES, differences));

            DrugClassVo oldPrimaryClass = findPrimaryDrugClass((Collection<DrugClassGroupVo>) getOldValue(
                FieldKey.DRUG_CLASSES, differences));

            if (!newPrimaryClass.equals(oldPrimaryClass)) {
                differences.put(FieldKey.PRIMARY_DRUG_CLASS, new Difference(FieldKey.PRIMARY_DRUG_CLASS, oldPrimaryClass,
                    newPrimaryClass));
            }
        }

        return AbstractConverter.hasNewOrModifiedFields(fields, differences, itemAction);
    }

    /**
     * Find the primary drug class. Same logic as {@link ProductVo#getPrimaryDrugClass()}.
     * 
     * @param group drug class group
     * @return primary drug class if found, otherwise an empty drug class
     */
    private static DrugClassVo findPrimaryDrugClass(Collection<DrugClassGroupVo> group) {
        if (group != null) {
            for (DrugClassGroupVo drugClass : group) {
                if (drugClass.getPrimary()) {
                    return drugClass.getDrugClass();
                }
            }
        }

        return new DrugClassVo();
    }

    /**
     * Convert product VO to a File.
     * 
     * @param productVo Product Item
     * @param differences old/new value differences
     * @param itemAction add/modify/inactivate
     * @return Product File
     */
    public static VaProductFile toVaProductFile(ProductVo productVo, Map<FieldKey, Difference> differences,
        ItemAction itemAction) {

        VaProductFile vaProductFile = FACTORY.createVaProductFile();
        vaProductFile.setCandidateKey(FACTORY.createVaProductFileCandidateKey());
        vaProductFile.setNumber(new Float("50.68"));

        // NAME M/M - Candidate Key
        vaProductFile.getCandidateKey().setName(FACTORY.createNameKey());
        vaProductFile.getCandidateKey().getName()
            .setValue((String) toCandidateKeyValue(FieldKey.VA_PRODUCT_NAME, differences, productVo.getVaProductName()));
        vaProductFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VA_PRODUCT_NAME, differences)) {
            vaProductFile.setName(FACTORY.createNameKey());
            vaProductFile.getName().setValue(productVo.getVaProductName());
            vaProductFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // VA GENERIC NAME M/O
        vaProductFile.setVaGenericName(FACTORY.createVaProductFileVaGenericName());
        vaProductFile.getVaGenericName().setValue(productVo.getGenericName().getValue());
        vaProductFile.getVaGenericName().setNumber(PPSConstants.F0POINT05);

        // DOSAGE FORM M/O
        vaProductFile.setDosageForm(FACTORY.createVaProductFileDosageForm());
        vaProductFile.getDosageForm().setValue(productVo.getOrderableItem().getDosageForm().getDosageFormName());
        vaProductFile.getDosageForm().setNumber(1f);

        Collection<ActiveIngredientVo> activeIngredients = productVo.getActiveIngredients();

        if (isValid(activeIngredients) || isUnset(FieldKey.ACTIVE_INGREDIENT, differences)) {
            ActiveIngredientVo activeIngredient = activeIngredients.iterator().next();

            // STRENGTH O/O
            VaProductFile.Strength strengthField = FACTORY.createVaProductFileStrength();
            strengthField.setNumber(2f);

            JAXBElement<VaProductFile.Strength> strengthElement = FACTORY.createVaProductFileStrength(strengthField);
            vaProductFile.setStrength(strengthElement);

            // UNITS O/O
            VaProductFile.Units unitsField = FACTORY.createVaProductFileUnits();
            unitsField.setNumber(new Float("3"));

            JAXBElement<VaProductFile.Units> unitsElement = FACTORY.createVaProductFileUnits(unitsField);
            vaProductFile.setUnits(unitsElement);

            if (isUnset(FieldKey.ACTIVE_INGREDIENT, differences) || (activeIngredients.size() > 1)) { // unset
                strengthElement.setNil(true);
                unitsElement.setNil(true);
            } else { // set
                strengthField.setValue(activeIngredient.getStrength());
                unitsField.setValue(activeIngredient.getDrugUnit().getValue());
            }
        }

        // NATIONAL FORMULARY NAME M/O
        vaProductFile.setNationalFormularyName(FACTORY.createVaProductFileNationalFormularyName());
        vaProductFile.getNationalFormularyName().setNumber(new Float("4"));
        vaProductFile.getNationalFormularyName().setValue(productVo.getNationalFormularyName());

        // VA PRINT NAME M/O
        vaProductFile.setVaPrintName(FACTORY.createVaProductFileVaPrintName());
        vaProductFile.getVaPrintName().setValue(productVo.getVaPrintName());
        vaProductFile.getVaPrintName().setNumber(new Float("5"));

        // VA PRODUCT IDENTIFIER M/O
        vaProductFile.setVaProductIdentifier(FACTORY.createVaProductFileVaProductIdentifier());
        vaProductFile.getVaProductIdentifier().setNumber(new Float("6f"));
        vaProductFile.getVaProductIdentifier().setValue(productVo.getCmopId());

        // TRANSMIT TO CMOP is an optional field so it may be Null or true or false. If null or false set to 0
        // otherwise set to 1.
        Boolean transmitToCMOP = Boolean.FALSE;

        if (productVo.getVaDataFields().getDataField(FieldKey.TRANSMIT_TO_CMOP) != null) {
            if (productVo.getVaDataFields().getDataField(FieldKey.TRANSMIT_TO_CMOP).getValue().booleanValue()) {
                transmitToCMOP = Boolean.TRUE;
            }
        }

        vaProductFile.setTransmitToCmop(FACTORY.createVaProductFileTransmitToCmop());
        vaProductFile.getTransmitToCmop().setValue(toBooleanOneOrZero(transmitToCMOP.booleanValue()));
        vaProductFile.getTransmitToCmop().setNumber(new Float("7"));

        // VA DISPENSE UNIT M/O
        vaProductFile.setVaDispenseUnit(FACTORY.createVaProductFileVaDispenseUnit());
        vaProductFile.getVaDispenseUnit().setValue(productVo.getDispenseUnit().getValue());
        vaProductFile.getVaDispenseUnit().setNumber(new Float("8"));

        // GCNSEQNO O/O
        if (isValid(productVo.getGcnSequenceNumber()) || isUnset(FieldKey.GCN_SEQUENCE_NUMBER, differences)) {
            VaProductFile.GcnSequenceNumber field = FACTORY.createVaProductFileGcnSequenceNumber();
            field.setNumber(new Float("11"));

            JAXBElement<VaProductFile.GcnSequenceNumber> element = FACTORY.createVaProductFileGcnSequenceNumber(field);
            vaProductFile.setGcnSequenceNumber(element);

            if (isUnset(FieldKey.GCN_SEQUENCE_NUMBER, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(new BigInteger(productVo.getGcnSequenceNumber()));
            }
        }

        // ACTIVE INGREDIENTS O/O
        if (isValid(activeIngredients) || isUnset(FieldKey.ACTIVE_INGREDIENT, differences)) {
            VaProductFile.ActiveIngredients field = FACTORY.createVaProductFileActiveIngredients();
            field.setNumber(new Float("14"));
            field.setMultiple(true);

            JAXBElement<VaProductFile.ActiveIngredients> element = FACTORY.createVaProductFileActiveIngredients(field);
            vaProductFile.setActiveIngredients(element);

            if (isUnset(FieldKey.ACTIVE_INGREDIENT, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (ActiveIngredientVo activeIngredientVo : activeIngredients) {
                    field.getActiveIngredientsFile().add(toActiveIngredientFile(activeIngredientVo));
                }
            }
        }

        return toVaProductFile2(vaProductFile, differences, itemAction, productVo);
    }

    /**
     * toVaProductFile2
     * @param vaProductFile vaProductFile
     * @param differences differences
     * @param itemAction itemAction
     * @param productVo productVo
     * @return VaProductFile
     */
    private static VaProductFile toVaProductFile2(VaProductFile vaProductFile,  Map<FieldKey, Difference> differences,
        ItemAction itemAction, ProductVo productVo) {
        
        // PRIMARY VA DRUG CLASS M/O
        vaProductFile.setPrimaryVaDrugClass(FACTORY.createVaProductFilePrimaryVaDrugClass());
        vaProductFile.getPrimaryVaDrugClass().setValue(productVo.getPrimaryDrugClass().getCode());
        vaProductFile.getPrimaryVaDrugClass().setNumber(new Float("15"));

        // NATIONAL FORMULARY INDICATOR M/O
        vaProductFile.setNationalFormularyIndicator(FACTORY.createVaProductFileNationalFormularyIndicator());
        vaProductFile.getNationalFormularyIndicator()
            .setValue(toBooleanOneOrZero(productVo.getNationalFormularyIndicator()));
        vaProductFile.getNationalFormularyIndicator().setNumber(new Float("17"));

        // CS FEDERAL SCHEDULE M/O
        vaProductFile.setCsFederalSchedule(FACTORY.createVaProductFileCsFederalSchedule());
        vaProductFile.getCsFederalSchedule().setNumber(new Float("19f"));
        vaProductFile.getCsFederalSchedule().setValue(toString(VALUE_DASH_PATTERN, productVo.getCsFedSchedule().getValue()));

        // SINGLE/MULTI SOURCE PRODUCT M/O
        vaProductFile.setSingleMultiSourceProduct(FACTORY.createVaProductFileSingleMultiSourceProduct());
        vaProductFile.getSingleMultiSourceProduct().setNumber(new Float("20"));
        vaProductFile.getSingleMultiSourceProduct().setValue(
            toString(VALUE_DASH_PATTERN, productVo.getSingleMultiSourceProduct().getValue()));

        // INACTIVATION DATE O/M
        Date inactivationDate = productVo.getInactivationDate();

        if (ItemAction.INACTIVATE.equals(itemAction) || isValid(inactivationDate)
            || isUnset(FieldKey.INACTIVATION_DATE, differences)) {

            VaProductFile.InactivationDate field = FACTORY.createVaProductFileInactivationDate();
            field.setNumber(new Float("21"));

            JAXBElement<VaProductFile.InactivationDate> element = FACTORY.createVaProductFileInactivationDate(field);
            vaProductFile.setInactivationDate(element);

            if (isUnset(FieldKey.INACTIVATION_DATE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(DateFormatter.toDateString(inactivationDate));
            }
        }

        // EFFECTIVE DATE TIME M/M
        vaProductFile.setEffectiveDateTime(FACTORY.createVaProductFileEffectiveDateTime());
        vaProductFile.getEffectiveDateTime().getEffectiveDateTimeFile().add(toEffectiveDateTime(productVo));
        vaProductFile.getEffectiveDateTime().setNumber(new Float("99.991"));
        vaProductFile.getEffectiveDateTime().setMultiple(true);

        Float vuidNum = new Float("99.99");
        
        // VUID M/M - Candidate Key
        vaProductFile.getCandidateKey().setVuid(FACTORY.createVuidKey());
        vaProductFile.getCandidateKey().getVuid().setValue(productVo.getVuid());
        vaProductFile.getCandidateKey().getVuid().setNumber(vuidNum);

        // VUID O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VUID, differences)) {
            vaProductFile.setVuid(FACTORY.createVuidKey());
            vaProductFile.getVuid().setValue(productVo.getVuid());
            vaProductFile.getVuid().setNumber(vuidNum);
        }

        // SERVICE CODE O/O
        if (StringUtils.isNotBlank(productVo.getServiceCode())) {
            VaProductFile.ServiceCode field = FACTORY.createVaProductFileServiceCode();
            field.setNumber(new Float("2000"));

            JAXBElement<VaProductFile.ServiceCode> element = FACTORY.createVaProductFileServiceCode(field);
            vaProductFile.setServiceCode(element);

        }
        
        return vaProductFile;
    }
    
    /**
     * Create datetime.
     * 
     * @param productVo product item
     * @return datetime
     */
    private static EffectiveDateTimeFile toEffectiveDateTime(ProductVo productVo) {
        EffectiveDateTimeFile effectiveDateTimeFile = FACTORY.createVaProductFileEffectiveDateTimeEffectiveDateTimeFile();
        effectiveDateTimeFile.setNumber(new Float("50.6899"));

        // EFFECTIVE DATE TIME M
        AbstractEffectiveDateTimeFile.EffectiveDateTime effectiveDateTime = ABSTRACT_FACTORY
            .createAbstractEffectiveDateTimeFileEffectiveDateTime();
        effectiveDateTime.setValue(DateFormatter.toDateString(new Date())); // current time
        effectiveDateTime.setNumber(PPSConstants.F0POINT01);
        effectiveDateTimeFile.setEffectiveDateTime(effectiveDateTime);

        // STATUS M
        AbstractEffectiveDateTimeFile.Status status = ABSTRACT_FACTORY.createAbstractEffectiveDateTimeFileStatus();
        status.setValue(toBooleanOneOrZero(productVo.getItemStatus().isActive()));
        status.setNumber(PPSConstants.F0POINT02);
        effectiveDateTimeFile.setStatus(status);

        return effectiveDateTimeFile;
    }

    /**
     * Create active ingredients.
     * 
     * @param activeIngredientVo ingredients
     * @return active ingredients
     */
    private static ActiveIngredientsFile toActiveIngredientFile(ActiveIngredientVo activeIngredientVo) {
        ActiveIngredientsFile activeIngredientFile = FACTORY.createActiveIngredientsFile();
        activeIngredientFile.setNumber(new Float("50.6814"));

        // NAME M
        activeIngredientFile.setActiveIngredient(FACTORY.createActiveIngredientsFileActiveIngredient());
        activeIngredientFile.getActiveIngredient().setValue(activeIngredientVo.getIngredient().getValue());
        activeIngredientFile.getActiveIngredient().setNumber(PPSConstants.F0POINT01);

        // STRENGTH M
        activeIngredientFile.setStrength(FACTORY.createActiveIngredientsFileStrength());
        activeIngredientFile.getStrength().setValue(activeIngredientVo.getStrength());
        activeIngredientFile.getStrength().setNumber(1f);

        // UNITS M
        activeIngredientFile.setUnit(FACTORY.createActiveIngredientsFileUnit());
        activeIngredientFile.getUnit().setValue(activeIngredientVo.getDrugUnit().getValue());
        activeIngredientFile.getUnit().setNumber(2f);

        return activeIngredientFile;
    }
}
