/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;


/**
 * Validate the criteria for a request search.
 */
public class SearchRequestCriteriaValidator extends AbstractValidator<SearchRequestCriteriaVo> {

    /**
     * The criteria for a request search cannot be for both "All" and "Local Requests". If neither is checked, the end date
     * must be after the start date.
     * 
     * @param value {@link SearchRequestCriteriaVo} to validate
     * @param errors {@link Errors} to which to add validation errors
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(SearchRequestCriteriaVo value, Errors errors) {
        if (value.isLocalSearch()) {
            rejectIfTrue(value.getSearchRequestEndDate().equals(value.getSearchRequestStartDate()),
                ErrorKey.START_END_DATE_EQUAL, errors, FieldKey.SEARCH_REQUEST_CRITERIA);

            rejectIfTrue(value.getSearchRequestStartDate().compareTo(value.getSearchRequestEndDate()) > 0,
                ErrorKey.START_END_DATE_PRECEEDS, errors, FieldKey.SEARCH_REQUEST_CRITERIA);
            
        } else {
            boolean b = value.isAllRequests() || value.isMarkedForPepsSecondReview() || value.isPendingAddition();
            b = b || value.isPendingModification() || value.isPendingSecondApprovalAddition();
            b = b || value.isPendingSecondApprovalModification() || value.isProblemReports() || value.isUnderReview();
            b = b || value.isNotLastReviewer();

            rejectIfFalse(b, ErrorKey.EMPTY_SEARCH, errors, FieldKey.SEARCH_REQUEST_CRITERIA);
            
            b = value.isMarkedForPepsSecondReview() || value.isPendingAddition() || value.isPendingModification();
            b = b || value.isPendingSecondApprovalAddition() || value.isPendingSecondApprovalModification();
            b = b || value.isProblemReports() || value.isUnderReview() || value.isNotLastReviewer();

            rejectIfTrue(value.isAllRequests() && b, ErrorKey.ALL_AND_OTHER_FILTER_SEARCH, errors,
                FieldKey.SEARCH_REQUEST_CRITERIA);
        }
    }
}
