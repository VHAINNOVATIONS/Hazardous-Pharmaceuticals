/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;


/**
 * Validates the NdcVo
 */
public class NdcValidator extends AbstractValidator<NdcVo> {

    private static final Double NDCDUPOU_MAX = 99999.9999;
    private static final Double NDCDUPOU_MIN = 0.0;

    /**
     * The Ndc must not be null. If the Ndc is not null, the route generic code must not be null or empty and the route name
     * must not be null or empty.
     * 
     * @param target instance of OrderableItem
     * @param user the user making changes
     * @param environment whether the environment is local or national
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(NdcVo target, UserVo user, Environment environment, Errors errors) {

        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.NDC);

            return;
        }

        // item status can not be null
        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);

        validatePage1(target, user, environment, errors);
        validatePage2(target, user, environment, errors);
        validateAdditionalFields(target, user, environment, errors);

        DataFields<DataField> vadfs = target.getVaDataFields();

        // Application Package Use is only mandatory if Local Use is selected.
        if (environment.isLocal() && target.isLocalUse()) {
            rejectIfNullOrEmpty(vadfs.getDataField(FieldKey.APPLICATION_PACKAGE_USE).getValue(), errors,
                FieldKey.APPLICATION_PACKAGE_USE);
        }

        if (vadfs.getDataField(FieldKey.PREVIOUS_UPC_UPN) != null
            && StringUtils.isNotBlank((vadfs.getDataField(FieldKey.PREVIOUS_UPC_UPN).getValue()))) {
            FieldKey.PREVIOUS_UPC_UPN.newValidatorInstance().validate(vadfs.getDataField(FieldKey.PREVIOUS_UPC_UPN), errors);
        }

        if (vadfs.getDataField(FieldKey.PREVIOUS_NDC) != null
            && StringUtils.isNotBlank(vadfs.getDataField(FieldKey.PREVIOUS_NDC).getValue())) {
            FieldKey.PREVIOUS_NDC.newValidatorInstance().validate(vadfs.getDataField(FieldKey.PREVIOUS_NDC), errors);
        }

    }

    /**
     * Specifically validates the NDC fields for a given page in the NDC Add wizard
     * 
     * @param target the NdcVO being validated
     * @param user user making changes
     * @param errors the errors list of the validate function
     * @param pageNum the page number the validation is for
     * @param environment whether the environment is local or national
     * @return
     */
    public void validate(NdcVo target, UserVo user, Environment environment, int pageNum, Errors errors) {

        switch (pageNum) {
            case 1:
                this.validatePage1(target, user, environment, errors);
                break;
            case 2:
                this.validatePage2(target, user, environment, errors);
                break;
            default:
                validate(target, user, environment, errors);
                break;
        }
    }

    /**
     * This method is overriden
     * 
     * @param target -N/A
     * @param errors -N/A
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(NdcVo target, Errors errors) {

    }

    /**
     * validates the page 1 of the Add NDC Wizard for both Local and National Add
     * 
     * @param target the NDC
     * @param user the user
     * @param environment local/national environment
     * @param errors the errors collection
     */
    public void validatePage1(NdcVo target, UserVo user, Environment environment, Errors errors) {

        DataFields<DataField> vadfs = target.getVaDataFields();

        if ((target.getProduct() == null) || (target.getProduct().getId() == null)) {
            errors.addError(ErrorKey.PRODUCT_PARENT_EMPTY, new Object[] { FieldKey.PRODUCT });

        }

        FieldKey.NDC.newValidatorInstance().validate(target.getNdc(), user, environment, errors);

        if ((target.getUpcUpn() != null) && (target.getUpcUpn().trim().length() > 0)) {
            FieldKey.UPC_UPN.newValidatorInstance().validate(target.getUpcUpn(), errors);
        }

        // Commenting out validation of rules from previous delivered version
        // Rule 1: Trade Name is optional when a new NDC item request is created at a Local PEPS.
        // Rule 2: Trade Name is mandatory when a new NDC item request is approved at the National PEPS.
        // Rule 3: Trade Name is mandatory when a new NDC item is created at the National PEPS.
        //if ((target.isNewInstance() && environment.isNational())
        //    || !RequestItemStatus.PENDING.equals(target.getRequestItemStatus())) {

        // PPS-N Rule 1: Trade Name is mandatory for an NDC item.
        rejectIfNullOrEmpty(target.getTradeName(), errors, FieldKey.TRADE_NAME);
        rejectIfLongerThanMax(target.getTradeName(), PPSConstants.I40, errors, FieldKey.TRADE_NAME);

        // PPS-N Rule 1: Package Size is mandatory for an NDC item.
        FieldKey.PACKAGE_SIZE.newValidatorInstance().validate(target.getPackageSize(), errors);

        // Validate OTC_RX Indicator
        (new OtcRxValidator()).validate(target.getOtcRxIndicator(), errors);

//        // Category
//        if (target.getCategories() == null) {
//            errors
//                .addError(FieldKey.CATEGORIES, ErrorKey.COMMON_FIELD_DEPENDS_ON_PARENT, new Object[] {
//                    FieldKey.PRODUCT, FieldKey.CATEGORIES });
//        }

        // ndc price per order unit is a mandatory local only field (it is mandatory at local, not at national)
        if (environment.isLocal()) {
            DataField<Double> ndcPricePerOrderUnit = vadfs.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT);

            if ((ndcPricePerOrderUnit != null) && (ndcPricePerOrderUnit.getValue() != null)) {
                FieldKey.NDC_PRICE_PER_ORDER_UNIT.newValidatorInstance().validate(ndcPricePerOrderUnit, errors);
            }
        }

        // ndc price per dispense unit
        DataField<Double> ndcPricePerDispenseUnit = vadfs.getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);


        FieldKey.NDC_PRICE_PER_DISPENSE_UNIT.newValidatorInstance().validate(ndcPricePerDispenseUnit, errors);


        // unit price
        if (environment.isLocal()) {
            DataField<Double> unitPrice = vadfs.getDataField(FieldKey.UNIT_PRICE);

            if ((unitPrice != null) && (unitPrice.getValue() != null)) {
                FieldKey.UNIT_PRICE.newValidatorInstance().validate(unitPrice, errors);
            }
        }

        String vendor = target.getVendor();
        String vsn = target.getVendorStockNumber();

        // validate vendor and vsn
//        if (environment.isLocal()) {

        if ((vendor != null) && (vendor.trim().length() > 0)) {
            FieldKey.VENDOR.newValidatorInstance().validate(target.getVendor(), errors);
        }

        if ((vsn != null) && (vsn.trim().length() > 0)) {
            FieldKey.VENDOR_STOCK_NUMBER.newValidatorInstance().validate(target.getVendorStockNumber(), errors);
        }

//        }

        rejectIfNull(target.getPackageType(), errors, FieldKey.PACKAGE_TYPE);

        rejectIfNull(target.getManufacturer(), errors, FieldKey.MANUFACTURER);

    }

    /**
     * validates the page 2 of the Add NDC Wizard for both Local and National Add
     * 
     * @param target the NDC
     * @param user the user
     * @param environment local/national environment
     * @param errors the errors collection
     */
    public void validatePage2(NdcVo target, UserVo user, Environment environment, Errors errors) {

        DataFields<DataField> vadfs = target.getVaDataFields();
        DataField<String> productNumber = vadfs.getDataField(FieldKey.PRODUCT_NUMBER);
        FieldKey.PRODUCT_NUMBER.newValidatorInstance().validate(productNumber, errors);

    }

    /**
     * validates additional NDC fields
     * 
     * @param target the NDC
     * @param user the user
     * @param environment local/national environment
     * @param errors the errors collection
     */
    private void validateAdditionalFields(NdcVo target, UserVo user,
                                     Environment environment, Errors errors) {

        // Request Item Status cannot be null
        rejectIfNull(target.getRequestItemStatus(), errors,
                     FieldKey.REQUEST_ITEM_STATUS);

        // Source cannot be null
        rejectIfNull(target.getSource(), errors, FieldKey.SOURCE);

        // NDC Dispense Unit Per Order Unit is optional, must be between 1 and
        // 99999.9999 with up to 4 decimal digits
        if (!isNull(target.getNdcDispUnitsPerOrdUnit())) {
            rejectIfMoreDecimals(target.getNdcDispUnitsPerOrdUnit(), PPSConstants.I4, errors,
                                 FieldKey.NDC_DISP_UNITS_PER_ORD_UNIT);
            rejectIfOutsideRangeInclusive(target.getNdcDispUnitsPerOrdUnit(), NDCDUPOU_MIN, NDCDUPOU_MAX,
                errors, FieldKey.NDC_DISP_UNITS_PER_ORD_UNIT);
        }
    }

    /**
     * determines if the string is null or empty
     * 
     * @param str the String argument
     * @return boolean
     */
    @SuppressWarnings("unused")
    private boolean isNullOrEmptyString(String str) {

        if ((str == null) || (str.trim().length() == 0)) {
            return true;
        }

        return false;
    }

}
