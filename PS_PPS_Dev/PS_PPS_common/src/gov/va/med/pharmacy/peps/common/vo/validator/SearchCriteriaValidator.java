/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;


/**
 * Validates the SearchCriteriaVo
 */
public class SearchCriteriaValidator extends AbstractValidator<SearchCriteriaVo> {
   
    /**
     * validate
     * 
     * The route must not be null. If the route is not null, the route generic code must not be null or empty and the route
     * name must not be null or empty.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(SearchCriteriaVo target, Errors errors) {

        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.SEARCH_CRITERIA);

            return;
        }

        if (!target.isAdvanced() && target.isEmpty()) {
            errors.addError(ErrorKey.EMPTY_SEARCH, new Object[] { FieldKey.SEARCH_CRITERIA });

            return;
        }

        // search term can not be null or an empty list
        if ((target.getSearchTerms() == null) || (target.getSearchTerms().isEmpty())) {
            errors.addError(ErrorKey.EMPTY_SEARCH, new Object[] { FieldKey.SEARCH_CRITERIA });

            return;
        }

        // at least one of the following fields should have value
        if (!target.isAdvanced() && isNullOrEmpty(target.getStrength()) && isNullOrEmpty(target.getDosageForm())
            && isNullOrEmpty(target.getSearchTerms().get(0).getValue())) {
            errors.addError(ErrorKey.EMPTY_SEARCH, new Object[] { FieldKey.SEARCH_CRITERIA });

            return;
        }

        // strength is allowed to be alpha-numeric
        // commenting out so as to allow alpha-mumeric
//        if (!isNullOrEmpty(target.getStrength())) {
//            rejectIfNotNumeric(target.getStrength(), errors, FieldKey.STRENGTH);
//        }

        if (!target.isAdvanced() && validateSearchForDuplicates(target.getSearchTerms())) {
            errors.addError(ErrorKey.DUPLICATE_SEARCH_TERMS);
        }

        for (SearchTermVo newSearchTerm : target.getSearchTerms()) {

            validate2(newSearchTerm, errors, target);
        }
    }

    
    /**
     * validate2
     * @param newSearchTerm vnewSearchTerm
     * @param errors errors
     * @param target target
     */
    public void validate2(SearchTermVo newSearchTerm, Errors errors, SearchCriteriaVo target) {
   
        // Ignore the 'NONE' search term for advanced search.
        if (target.isAdvanced() && FieldKey.SEARCH_NO_FIELDS.equals(newSearchTerm.getFieldKey())) {
            return;
        }

        rejectIfNullOrEmpty(newSearchTerm.getValue(), errors, newSearchTerm.getFieldKey());

        // Checks to see if the search term is appropriate for the type of search
//        if (!validateSearchTerm(target, newSearchTerm)) {
//            LOG.error("SEARCH criteria" + target);
//            errors.addError(ErrorKey.INVALID_SEARCH, newSearchTerm.getFieldKey());
//        }

        if (FieldKey.NDC.equals(newSearchTerm.getFieldKey()) || FieldKey.NDC_CODE.equals(newSearchTerm.getFieldKey())) {

            String stripped = newSearchTerm.getValue().replaceAll("%", "");

            if (stripped.length() == 0) {
                return;
            }

            if (stripped.contains("-")) {
                String[] segments = stripped.split("-");
                checkLength(segments, errors);
                
            }

            final int min = 1;
            final int max = 11;
            rejectIfNotNumeric(newSearchTerm.formatValue(), errors, newSearchTerm.getFieldKey());
            rejectIfLengthOutsideRangeInclusive(newSearchTerm.formatValue(), min, max, errors, newSearchTerm
                .getFieldKey());
        }

        if (newSearchTerm.isDrugClassCodeRangeSearchTerm()) {

            if (newSearchTerm.getValue() != null) {
                if (newSearchTerm.getValue().split(SearchTermVo.RANGE_SEARCH_DELIIMITER).length == 2) {
                    rejectIfFalse(newSearchTerm.getRangeStart().compareToIgnoreCase(newSearchTerm.getRangeEnd()) <= 0,
                        ErrorKey.RANGE_SEARCH_ORDER, errors, newSearchTerm.getFieldKey());
                } else {
                    errors.addError(newSearchTerm.getFieldKey(), ErrorKey.RANGE_SEARCH_BOUNDS);
                }
            }
        }
    }
    
    /**
     * checkLength
     * @param segments segments
     * @param errors errors
     */
    private void checkLength(String[] segments, Errors errors) {
        if (segments.length > NUM_3 || segments.length < NUM_2) {
            errors.addError(ErrorKey.INVALID_NDC_FORMAT, new Object[] { FieldKey.SEARCH_CRITERIA });
        } else if (segments.length == NUM_3) {
            if (segments[0].length() > NUM_5) {
                errors.addError(ErrorKey.INVALID_NDC_FORMAT, FieldKey.SEARCH_CRITERIA);
            }

            if (segments[1].length() > NUM_4) {
                errors.addError(ErrorKey.INVALID_NDC_FORMAT, FieldKey.SEARCH_CRITERIA);
            }

            if (segments[2].length() > NUM_2) {
                errors.addError(ErrorKey.INVALID_NDC_FORMAT, FieldKey.SEARCH_CRITERIA);
            }
        } else if (segments.length == NUM_2) {
            if (segments[0].length() > NUM_5) {
                errors.addError(ErrorKey.INVALID_NDC_FORMAT, FieldKey.SEARCH_CRITERIA);
            }

            if (segments[1].length() > NUM_4) {
                errors.addError(ErrorKey.INVALID_NDC_FORMAT, FieldKey.SEARCH_CRITERIA);
            }
        }
    }
    
    
    /**
     * Searches for duplicates
     * 
     * @param searchTerms - terms to be searched
     * @return boolean that indicates if duplicates are found
     */
    private boolean validateSearchForDuplicates(List<SearchTermVo> searchTerms) {

        Set<FieldKey> duplicates = new HashSet<FieldKey>();

        for (SearchTermVo searchTerm : searchTerms) {
            duplicates.add(searchTerm.getFieldKey());
        }

        return searchTerms.size() != duplicates.size();
    }

    /**
     * Checks a search term to see if it is valid for a certain item search.
     * 
     * @param criteria - the search criteria
     * @param searchTerm - the term to be evaluated
     * @return boolean
     */
    public boolean validateSearchTerm(SearchCriteriaVo criteria, SearchTermVo searchTerm) {

        boolean valid = true;

        if (searchTerm == null) {
            valid = false;
        }

        if (searchTerm != null && !searchTerm.isAllSearch()) {
            Collection<EntityType> possibleTypes = new ArrayList<EntityType>();
            FieldKey fieldKey = searchTerm.getFieldKey();
            
            if (fieldKey.isSearchableField()) {
                fieldKey = fieldKey.getSearchableFieldKey();
            }
            
            possibleTypes.addAll(fieldKey.getEntityTypes());

            if (searchTerm.getFieldKey().isGroupedDataField()) {
                possibleTypes.addAll(searchTerm.getFieldKey().getGroupKey().getEntityTypes());
            }
            
            valid = possibleTypes.contains(criteria.getEntityType());
        }

        return valid;
    }
}
