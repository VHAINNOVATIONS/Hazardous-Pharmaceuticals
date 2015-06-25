/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;
import java.util.HashMap;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.AtcCanisterVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.WardGroupForAtcVo;


/**
 * validates atc canisters
 */
public class AtcCanistersValidator extends AbstractValidator<Collection<AtcCanisterVo>> {

    /**
     * validate
     * 
     * @param target the atc canisters
     * @param errors the errors collection
     * 
     */
    public void validate(Collection<AtcCanisterVo> target, Errors errors) {
        if ((target == null) || (target.isEmpty())) {
            rejectIfNullOrEmpty(target, errors, FieldKey.ATC_CANISTERS);

            return;
        }

        HashMap<String, WardGroupForAtcVo> wardGroupMap = new HashMap<String, WardGroupForAtcVo>();

        // each ward group can be listed only once
        for (AtcCanisterVo atc : target) {
            if (atc == null) {
                errors.addError(FieldKey.ATC_CANISTERS, ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.ATC_CANISTERS});

                return;
            }

            long atcCanister = 0;

            if (atc.getAtcCanister() != null) {
                atcCanister = atc.getAtcCanister();
            }

            WardGroupForAtcVo wardGroup = atc.getWardGroupForAtc();

            // ward group for atc canister are required fields
            if ((wardGroup == null) || isNullOrEmpty(wardGroup.getValue())) {
                errors.addError(FieldKey.ATC_CANISTERS, ErrorKey.ATC_CANISTER_REQ_FIELD_EMPTY,
                    new Object[] {FieldKey.ATC_CANISTERS});

                return;
            }// end if

            // the value of atc canister should be between 0 and 800
            rejectIfOutsideRangeInclusive(atcCanister, 0, PPSConstants.I800, errors, 
                FieldKey.ATC_CANISTERS, FieldKey.ATC_CANISTER);
            
            // each ward group can be listed only once
            
            if (wardGroupMap.containsKey(wardGroup.getValue())) {
                errors.addError(FieldKey.ATC_CANISTERS, ErrorKey.ATC_WARD_GROUP_DUPLICATE,
                    new Object[] {FieldKey.ATC_CANISTERS});
            } else {
                wardGroupMap.put(wardGroup.getValue(), wardGroup);
            }
        }// end for

    }// end validate

}
