/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;



import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;


/**
 * PackageTypeVo validator
 */
public class PackageTypeValidator extends AbstractValidator<PackageTypeVo> {

    /**
     * validates PackageTypeVo
     * 
     * @param target the PackageTypeVo
     * @param errors the errors collection
     */
    public void validate(PackageTypeVo target, Errors errors) {

        if (target == null) {
            rejectIfNull(target, errors, FieldKey.PACKAGE_TYPE);

            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.VALUE);
        rejectIfLongerThanMax(target.getValue(), NUM_40, errors, FieldKey.VALUE);
        
    }
}
