/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;


/**
 * validates OTC/RX Indicator
 */
public class OtcRxValidator extends AbstractValidator<OtcRxVo> {
    
    /**
     * validates OtcRxVo
     * 
     * @param target the OtcRxVo
     * @param errors the errors collection
     */
    public void validate(OtcRxVo target, Errors errors) {
        rejectIfNull(target, errors, FieldKey.OTC_RX_INDICATOR);
        
        if (target != null) {   
            rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.OTC_RX_INDICATOR);
        }
        
    //    if (!(target.getId().equalsIgnoreCase(OtcRx.OTC.toString()) ||
    //        target.getId().equalsIgnoreCase(OtcRx.RX.toString()))) {
    //        ErrorKey key = new ErrorKey("invalid.otc");
    //        errors.addError(key);
    //    }
        
    }
}

