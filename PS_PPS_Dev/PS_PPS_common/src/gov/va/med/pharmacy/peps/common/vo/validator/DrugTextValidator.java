/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugTextSynonymVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Validator for drug text
 */
public class DrugTextValidator extends AbstractValidator<DrugTextVo> {

    /**
     * validates drug unit
     * 
     * @param target the DrugUnitVo
     * @param errors the errors collection
     */
    public void validate(DrugTextVo target, Errors errors) {

        // Drug Text entry is optional for OI so do not reject if null
        // but do not continue other validation either.
        if (target == null) {
             
            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.VALUE);
       
        rejectIfLengthOutsideRangeInclusive(target.getValue(), 1, PPSConstants.I40, errors, FieldKey.VALUE);
        
        if (target.getDrugTextSynonyms() != null) {

            for (DrugTextSynonymVo txt : target.getDrugTextSynonyms()) {
                rejectIfLengthOutsideRangeInclusive(txt.getDrugTextSynonymName(), 1, PPSConstants.I40, errors,
                    FieldKey.DRUG_TEXT_SYNONYMS);
            }
        }

    }
    
    /**
     * validates drug unit
     * 
     * @param target the DrugUnitVo
     * @param user the User
     * @param environment the environment
     * @param errors the errors collection
     */
    public  void validate(DrugTextVo target, UserVo user, Environment environment, Errors errors) {
        validate(target, errors);
    
        if (environment.isLocal()) {
            rejectIfNullOrEmpty(target.getTextLocal(), errors, FieldKey.TEXT_LOCAL);
            rejectIfLengthOutsideRangeInclusive(target.getTextLocal(), 1, PPSConstants.I2000, errors, FieldKey.TEXT_LOCAL);
        } else {
            rejectIfNullOrEmpty(target.getTextNational(), errors, FieldKey.TEXT_NATIONAL);
            rejectIfLengthOutsideRangeInclusive(target.getTextNational(), 1, 
                PPSConstants.I2000, errors, FieldKey.TEXT_NATIONAL);
        }
        
    }
}
