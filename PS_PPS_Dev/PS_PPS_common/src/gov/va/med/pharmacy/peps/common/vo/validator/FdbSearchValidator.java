/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;



/**
 * 
 * FdbSearchValidator
 * 
 */
public class FdbSearchValidator extends AbstractValidator<FDBSearchOptionVo> {


    @Override
    public void validate(FDBSearchOptionVo target, Errors errors) {

        String searchString = target.getFdbSearchString();
        
        if (searchString.isEmpty()) {
            errors.addError(ErrorKey.EMPTY_SEARCH, new Object[] { FieldKey.SEARCH_CRITERIA });

            return;
        }
        
        if (target.getFdbSearchOptionType().isGcnSeqNoSearch()) { 
            if (!StringUtils.isNumeric(searchString.trim())) {
                errors.addError(ErrorKey.COMMON_NOT_NUMERIC, new Object[] { FieldKey.SEARCH_CRITERIA });
            
                return;
            }
        }
        
    }
    
}
