/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;


/**
 * validates Single Multi Source Product
 */
public class SingleMultiSourceProductValidator extends AbstractValidator<SingleMultiSourceProductVo> {
    
    /**
     * validates SingleMultiSourceProductVo
     * ID3023: This is now an optional field.
     * 
     * @param target the SingleMultiSourceProductVo
     * @param errors the errors collection
     */
    public void validate(SingleMultiSourceProductVo target, Errors errors) {
        if (target == null) {

            return;
        }

    }
}
