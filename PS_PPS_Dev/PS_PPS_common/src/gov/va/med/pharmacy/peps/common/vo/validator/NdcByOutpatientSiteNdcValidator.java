/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.NdcByOutpatientSiteNdcVo;
import gov.va.med.pharmacy.peps.common.vo.OutpatientSiteVo;


/**
 * validate NdcByOutpatientSiteNdc
 */
public class NdcByOutpatientSiteNdcValidator extends AbstractValidator<Collection<NdcByOutpatientSiteNdcVo>> {

    /**
     * validate
     * 
     * @param target the NdcByOutpatientSiteVo
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(Collection<NdcByOutpatientSiteNdcVo> target, Errors errors) {
        if ((target == null) || (target.isEmpty())) {
            rejectIfNullOrEmpty(target, errors, FieldKey.NDC_BY_OUTPATIENT_SITE_NDC);

            return;
        }

        for (NdcByOutpatientSiteNdcVo ndcByOutpatientSite : target) {
            if (ndcByOutpatientSite == null) {
                errors.addError(FieldKey.NDC_BY_OUTPATIENT_SITE_NDC, ErrorKey.COMMON_EMPTY,
                    new Object[] {FieldKey.NDC_BY_OUTPATIENT_SITE_NDC});

                return;
            }

            // outpatient site is mandatory
            OutpatientSiteVo outpatientSite = ndcByOutpatientSite.getOutpatientSite();

            if ((outpatientSite == null) || (isNullOrEmpty(outpatientSite.getValue()))) {
                errors.addError(FieldKey.NDC_BY_OUTPATIENT_SITE_NDC, ErrorKey.COMMON_EMPTY,
                    new Object[] {FieldKey.OUTPATIENT_SITE});
            }

            // last cmop ndc is optional and 5 to 15 characters long
            if (!isNullOrEmpty(ndcByOutpatientSite.getLastCmopNdc())) {
                rejectIfLengthOutsideRangeInclusive(ndcByOutpatientSite.getLastCmopNdc(), NUM_5, NUM_15, errors,
                    FieldKey.NDC_BY_OUTPATIENT_SITE_NDC, FieldKey.LAST_CMOP_NDC);
            }

            // last local ndc is optional and 5 to 15 characters long
            if (!isNullOrEmpty(ndcByOutpatientSite.getLastLocalNdc())) {
                rejectIfLengthOutsideRangeInclusive(ndcByOutpatientSite.getLastLocalNdc(), NUM_5, NUM_15, errors,
                    FieldKey.NDC_BY_OUTPATIENT_SITE_NDC, FieldKey.LAST_LOCAL_NDC);
            }
        }// end for
    }
}
