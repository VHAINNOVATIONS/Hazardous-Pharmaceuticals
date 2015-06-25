/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Package Size Validator
 */
public class PackageSizeValidator extends AbstractValidator<Double> {

    private static final int FOUR = 4; 
    private static final double MAXRANGE = 999999.9999; 
    
    /**
     * validates PackageSize
     * 
     * @param target the PackageSize
     * @param errors the errors collection
     */
    public void validate(Double target, Errors errors) {
        if ((target == null) || (target.doubleValue() == 0)) {
            rejectIfNull(target, errors, FieldKey.PACKAGE_SIZE);

            return;
        } else {
            rejectIfMoreDecimals(target, FOUR, errors, FieldKey.PACKAGE_SIZE);
            rejectIfOutsideRangeInclusive(target, 0, MAXRANGE, errors, FieldKey.PACKAGE_SIZE);
        }
    }
}
