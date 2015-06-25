/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf;


import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.drugingredients.DrugIngredientsFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.drugingredients.DrugIngredientsFile.EffectiveDateTime.EffectiveDateTimeFile;

import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.drugingredients.ObjectFactory;


/**
 * Converts Ingredient VO to Drug Ingredients File document.
 */
public class DrugIngredientsFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] { FieldKey.VALUE, FieldKey.PRIMARY_INGREDIENT, FieldKey.INACTIVATION_DATE, FieldKey.VUID })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private DrugIngredientsFileConverter() {
    }

    /**
     * Convert Ingredient VO to Drug Ingredients File document.
     * 
     * @param ingredientVo Ingredient
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return ingredients file
     */
    public static DrugIngredientsFile toDrugIngredientsFile(IngredientVo ingredientVo,
                                                            Map<FieldKey, Difference> differences, ItemAction itemAction) {

        DrugIngredientsFile drugIngredientsFile = FACTORY.createDrugIngredientsFile();
        drugIngredientsFile.setCandidateKey(FACTORY.createDrugIngredientsFileCandidateKey());
        drugIngredientsFile.setNumber(new Float("50.416"));

        // NAME M/M - Candidate Key
        drugIngredientsFile.getCandidateKey().setName(FACTORY.createNameKey());
        drugIngredientsFile.getCandidateKey().getName().setValue(
            (String) toCandidateKeyValue(FieldKey.VALUE, differences, ingredientVo.getValue()));
        drugIngredientsFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VALUE, differences)) {
            drugIngredientsFile.setName(FACTORY.createNameKey());
            drugIngredientsFile.getName().setValue(ingredientVo.getValue());
            drugIngredientsFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // PRIMARY INGREDIENT O/O
        if (isValid(ingredientVo.getPrimaryIngredient()) || isUnset(FieldKey.PRIMARY_INGREDIENT, differences)) {
            DrugIngredientsFile.PrimaryIngredient field = FACTORY.createDrugIngredientsFilePrimaryIngredient();
            field.setNumber(2f);

            JAXBElement<DrugIngredientsFile.PrimaryIngredient> element = FACTORY
                .createDrugIngredientsFilePrimaryIngredient(field);
            drugIngredientsFile.setPrimaryIngredient(element);

            if (isUnset(FieldKey.PRIMARY_INGREDIENT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(ingredientVo.getPrimaryIngredient().getValue());
            }
        }

        // INACTIVATION DATE O/M
        Date inactivationDate = ingredientVo.getInactivationDate();

        if (ItemAction.INACTIVATE.equals(itemAction) || isValid(inactivationDate)
            || isUnset(FieldKey.INACTIVATION_DATE, differences)) {

            DrugIngredientsFile.InactivationDate field = FACTORY.createDrugIngredientsFileInactivationDate();
            field.setNumber(new Float("3"));

            JAXBElement<DrugIngredientsFile.InactivationDate> element = FACTORY
                .createDrugIngredientsFileInactivationDate(field);
            drugIngredientsFile.setInactivationDate(element);

            if (isUnset(FieldKey.INACTIVATION_DATE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(DateFormatter.toDateString(inactivationDate));
            }
        }

        // VUID M/M - Candidate Key
        drugIngredientsFile.getCandidateKey().setVuid(FACTORY.createVuidKey());
        drugIngredientsFile.getCandidateKey().getVuid().setValue(
            ((String) toCandidateKeyValue(FieldKey.VUID, differences, ingredientVo.getVuid())));
        drugIngredientsFile.getCandidateKey().getVuid().setNumber(new Float("99.99"));

        // VUID O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VUID, differences)) {
            drugIngredientsFile.setVuid(FACTORY.createVuidKey());
            drugIngredientsFile.getVuid().setValue(ingredientVo.getVuid());
            drugIngredientsFile.getVuid().setNumber(new Float("99.99"));
        }

        // EFFECTIVE DATE TIME
        drugIngredientsFile.setEffectiveDateTime(FACTORY.createDrugIngredientsFileEffectiveDateTime());
        drugIngredientsFile.getEffectiveDateTime().getEffectiveDateTimeFile().add(createEffectiveDateTimeFile(ingredientVo));
        drugIngredientsFile.getEffectiveDateTime().setNumber(new Float("99.991"));
        drugIngredientsFile.getEffectiveDateTime().setMultiple(true);

        return drugIngredientsFile;
    }

    /**
     * Create effective date/time.
     * 
     * @param ingredientVo ingredient
     * @return effective date/time
     */
    private static EffectiveDateTimeFile createEffectiveDateTimeFile(IngredientVo ingredientVo) {
        EffectiveDateTimeFile effectiveDateTimeFile = FACTORY
            .createDrugIngredientsFileEffectiveDateTimeEffectiveDateTimeFile();
        effectiveDateTimeFile.setNumber(new Float("50.4169"));

        // EFFECTIVE DATE TIME M/M
        effectiveDateTimeFile.setEffectiveDateTime(ABSTRACT_FACTORY.createAbstractEffectiveDateTimeFileEffectiveDateTime());
        effectiveDateTimeFile.getEffectiveDateTime().setValue(DateFormatter.toDateString(new Date()));
        effectiveDateTimeFile.getEffectiveDateTime().setNumber(PPSConstants.F0POINT01);

        // STATUS M/M
        effectiveDateTimeFile.setStatus(ABSTRACT_FACTORY.createAbstractEffectiveDateTimeFileStatus());
        effectiveDateTimeFile.getStatus().setValue(toBooleanOneOrZero(ingredientVo.getItemStatus().isActive()));
        effectiveDateTimeFile.getStatus().setNumber(PPSConstants.F0POINT02);

        return effectiveDateTimeFile;
    }
}
