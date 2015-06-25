/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;
import java.util.HashMap;

import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * validate the drug class
 */
public class DrugClassValidator extends AbstractValidator<Collection<DrugClassGroupVo>> {

    /**
     * validate
     * 
     * @param target the drug class
     * @param errors the errors collection
     * 
     */
    public void validate(Collection<DrugClassGroupVo> target, Errors errors) {
        if ((target == null) || (target.isEmpty())) {
            rejectIfNullOrEmpty(target, errors, FieldKey.DRUG_CLASS);

            return;
        }

        int primaryCount = 0;
        HashMap<String, DrugClassGroupVo> nameMap = new HashMap<String, DrugClassGroupVo>();

        // if no primary selected add error
        for (DrugClassGroupVo drugClass : target) {
            DrugClassVo drugClassification = drugClass.getDrugClass();

            if ((drugClassification == null) || isNullOrEmpty(drugClassification.getId())
                || isNullOrEmpty(drugClassification.getValue())) {
                errors.addError(FieldKey.DRUG_CLASS, ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.DRUG_CLASS});
            } else if (isNullOrEmpty(drugClassification.getCode())) {
                errors.addError(FieldKey.DRUG_CLASS, ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.CODE});
            } else if (isNullOrEmpty(drugClassification.getClassification())) {
                errors.addError(FieldKey.DRUG_CLASS, ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.CLASSIFICATION});
            } else {

                if (drugClass.getPrimary()) {
                    primaryCount++;
                }

                if (nameMap.containsKey(drugClassification.getValue())) {
                    errors.addError(FieldKey.DRUG_CLASS, ErrorKey.DRUG_CLASS_DUPLICATE, new Object[] {FieldKey.DRUG_CLASS});
                } else {
                    nameMap.put(drugClassification.getValue(), drugClass);
                }
            }
        }// end for

        if ((primaryCount != 1) && !((target.size() == 1) && (primaryCount == 0))) {
            errors.addError(FieldKey.DRUG_CLASS, ErrorKey.DRUG_CLASS_INCORRECT_PRIMARY, new Object[] {FieldKey.DRUG_CLASS});
        }

       
    }

}
