/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * A search term where a particular value for a field is being searched
 */
public class SearchTermVo extends ValueObject {

    /** RANGE_SEARCH_DELIIMITER */
    public static final String RANGE_SEARCH_DELIIMITER = "-";

    /** SQL_TRUE_NON_VADF */
    public static final String SQL_TRUE_NON_VADF = "Y";

    /** SQL_FALSE_NON_VADF */
    public static final String SQL_FALSE_NON_VADF = "N";

    /** SQL_TRUE_VADF */
    public static final String SQL_TRUE_VADF = "true";

    /** SQL_FALSE_VADF */
    public static final String SQL_FALSE_VADF = "false";

    /** SETUP_ATC_FIELD_KEY */
    public static final FieldKey SETUP_ATC_FIELD_KEY = FieldKey.LOCAL_PRINT_NAME;

    private static final long serialVersionUID = 1L;

    /**
     * FieldKey for the search term
     */
    private SearchFieldVo searchField;

    /**
     * Type of search for search term
     */
    private SearchType searchType = SearchType.BEGINS_WITH;

    /**
     * Value for search term
     */
    private String value;

    private Boolean advancedAndSearch = false;

    private SearchTermConjunction searchTermConjunction = SearchTermConjunction.OR;

    /**
     * Constructor that sets the default search type to CONTAINS
     */
    public SearchTermVo() {

        this.searchType = SearchType.CONTAINS;
    }

    /**
     * Constructor that builds the search field
     * 
     * @param entityType property
     * @param fieldKey property
     * @param value property
     */
    public SearchTermVo(EntityType entityType, FieldKey fieldKey, String value) {

        this.searchType = SearchType.CONTAINS;
        this.searchField = new SearchFieldVo(fieldKey, entityType);
        this.value = value;
    }

    /**
     * Constructor that sets the default search type to CONTAINS
     * 
     * @param searchField property
     * @param value property
     */
    public SearchTermVo(SearchFieldVo searchField, String value) {

        this.searchType = SearchType.CONTAINS;
        this.searchField = searchField;
        this.value = value;
    }

    /**
     * Constructor that builds search field
     * 
     * @param entityType property
     * @param fieldKey property
     * @param value property
     * @param searchType property
     */
    public SearchTermVo(EntityType entityType, FieldKey fieldKey, String value, SearchType searchType) {

        this.searchType = searchType;
        this.searchField = new SearchFieldVo(fieldKey, entityType);
        this.value = value;
    }

    /**
     * Constructor
     * 
     * @param searchField property
     * @param value property
     * @param searchType property
     */
    public SearchTermVo(SearchFieldVo searchField, String value, SearchType searchType) {

        this.searchType = searchType;
        this.searchField = searchField;
        this.value = value;
    }

    /**
     * Instantiate a SearchTermVo for the given SearchDomain type.
     * <p>
     * An empty SearchTermVo is one with an {@link EntityType}, either {@link FieldKey#SEARCH_ALL_FIELDS} or
     * {@link FieldKey#SEARCH_NO_FIELDS}, empty String value, and {@link SearchType#CONTAINS}.
     * 
     * @param searchDomain type for which to create an empty SearchTermVo
     * @return empty SearchTermVo.
     * 
     * @see isEmpty()
     */
    public static SearchTermVo newEmptyInstance(SearchDomain searchDomain) {

        SearchTermVo searchTerm;

        switch (searchDomain) {
            case ADVANCED:
                searchTerm = new SearchTermVo(EntityType.PRODUCT, FieldKey.SEARCH_NO_FIELDS, "", SearchType.CONTAINS);
                break;

            case SIMPLE:
                searchTerm = new SearchTermVo(EntityType.PRODUCT, FieldKey.SEARCH_ALL_FIELDS, "", SearchType.CONTAINS);
                break;

            case DOMAIN:
                searchTerm = new SearchTermVo(EntityType.DRUG_CLASS, FieldKey.SEARCH_NO_FIELDS, "", SearchType.CONTAINS);
                break;

            case PRODUCT:
                searchTerm = new SearchTermVo(EntityType.PRODUCT, FieldKey.SEARCH_ALL_FIELDS, "", SearchType.CONTAINS);
                break;

            case ORDERABLE_ITEM:
                searchTerm = new SearchTermVo(EntityType.ORDERABLE_ITEM, FieldKey.SEARCH_NO_FIELDS, "", SearchType.CONTAINS);
                break;

            case SETUP_ATC:
                searchTerm = new SearchTermVo(EntityType.PRODUCT, SETUP_ATC_FIELD_KEY, "", SearchType.CONTAINS);
                break;

            default:
                searchTerm = new SearchTermVo();
                break;
        }

        return searchTerm;
    }

    /**
     * An empty SearchTermVo is one with any {@link EntityType}, either {@link FieldKey#SEARCH_ALL_FIELDS} or
     * {@link FieldKey#SEARCH_NO_FIELDS}, empty String value, and {@link SearchType#CONTAINS}.
     * 
     * @return boolean true if this SearchTermvo is empty
     */
    public boolean isEmpty() {

        FieldKey fieldKey = getSearchField().getFieldKey();

        return (FieldKey.SEARCH_ALL_FIELDS.equals(fieldKey) || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS.equals(fieldKey)
            || FieldKey.SEARCH_NO_FIELDS.equals(fieldKey)) && (value == null || value.trim().length() == 0)
            && SearchType.CONTAINS.equals(searchType);
    }

    /**
     * Return true if this {@link SearchTermVo} is equal to {@link FieldKey#SEARCH_ALL_FIELDS}.
     * 
     * @return boolean
     */
    public boolean isAllSearch() {

        return (FieldKey.SEARCH_ALL_FIELDS.equals(searchField.getFieldKey()) || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS
                .equals(searchField.getFieldKey()));
    }

    /**
     * Description
     * 
     * @return dataFieldKey property
     */
    public FieldKey getFieldKey() {

        return searchField.getFieldKey();
    }

    /**
     * Description
     * 
     * @return value property
     */
    public String getValue() {

        return value;
    }

    /**
     * Some search terms (namely NDC) require formatting prior to validating or querying the database.
     * <p>
     * Formatting an NDC assumes validation has already occurred such that there are three segments!
     * 
     * @return formatted search term value
     */
    public String formatValue() {

        FieldKey key = getFieldKey();

        if ((FieldKey.NDC.equals(key) || FieldKey.NDC_CODE.equals(key)) && getValue().contains("-")) {
            String[] split = getValue().split("-");
            StringBuffer ndc = new StringBuffer();

            if (split != null && split.length > 0) {
                ndc.append(StringUtils.leftPad(split[0], PPSConstants.I5, '0'));
            }

            if (split != null && split.length > 1) {
                ndc.append(StringUtils.leftPad(split[1], PPSConstants.I4, '0'));
            }

            if (split != null && split.length > 2) {
                ndc.append(StringUtils.leftPad(split[2], 2, '0'));
            }

            return ndc.toString();
            
        } else if (FieldKey.VA_DRUG_CLASS.equals(key)) {

            // Extracts the Code form the drug class
            String code = getValue();
            String delimeter = "-";
            
            return code.substring(0, code.indexOf(delimeter)).trim();            
        } else if (key != null) { // NOPMD
            String val = getValue();

            if (val == null) {
                return val;
            }

            String compareValue = val.toLowerCase(Locale.getDefault());
            String startTrue = SQL_TRUE_VADF;
            String startY = "y";

            if (key.isBooleanSimpleDataField()) {
                if (compareValue.startsWith(startTrue) || compareValue.startsWith(startY)) {
                    return SQL_TRUE_VADF;
                } else {
                    return SQL_FALSE_VADF;
                }
            } else if (key.isPrimitiveField() && Boolean.class.isAssignableFrom(key.getFieldClass())) {
                if (compareValue.startsWith(startTrue) || compareValue.startsWith(startY)) {
                    return SQL_TRUE_NON_VADF;
                } else {
                    return SQL_FALSE_NON_VADF;
                }
            } else {
                return getValue();
            }
        } else {
            return getValue();
        }
    }

    /**
     * Description
     * 
     * @param value value property
     */
    public void setValue(String value) {

        this.value = trimToEmpty(value);
    }

    /**
     * Add logic to clear out an advanced search 'value' field if its field selection has changed.
     * This logic was added to deal with defect #2219 'Unexpected advanced search field behavior', whereby 
     * a previous field selection's value was being stamped on to a new field's value...it should instead be blank.
     */
    public void fixValueAfterFieldChange() {

        if (searchField != null) {

            //            if (searchField.isChangeSeen()) {
            setValue(null);

            //            }
        }
    }

    /**
     * Description
     * 
     * @return searchType property
     */
    public SearchType getSearchType() {

        return searchType;
    }

    /**
     * Description
     * 
     * @param searchType searchType property
     */
    public void setSearchType(SearchType searchType) {

        this.searchType = searchType;
    }

    /**
     * Description
     * 
     * @return searchField property
     */
    public SearchFieldVo getSearchField() {

        return searchField;
    }

    /**
     * Description
     * 
     * @param searchField searchField property
     */
    public void setSearchField(SearchFieldVo searchField) {
        
        this.searchField = searchField;
    }

    /**
     * A SearchTerm is for a range search if the value includes the special delimiter character.
     * 
     * @return boolean
     */
    public boolean isRangeSearchTerm() {

        return getValue() != null && getValue().contains(RANGE_SEARCH_DELIIMITER);
    }

    /**
     * True if {@link FieldKey#CODE} and {@link #isRangeSearchTerm()} is true.
     * 
     * @return boolean
     */
    public boolean isDrugClassCodeRangeSearchTerm() {

        return FieldKey.CODE.equals(getFieldKey()) && isRangeSearchTerm();
    }

    /**
     * Parse out the start of the range search.
     * <p>
     * Only valid if {@link #isRangeSearchTerm()} returns true!
     * 
     * @return String beginning of range search
     */
    public String getRangeStart() {

        return getValue().split(RANGE_SEARCH_DELIIMITER)[0];
    }

    /**
     * Parse out the end of the range search.
     * <p>
     * Only valid if {@link #isRangeSearchTerm()} returns true!
     * 
     * @return String end of range search
     */
    public String getRangeEnd() {

        return getValue().split(RANGE_SEARCH_DELIIMITER)[1];
    }

    /**
     * Description
     * 
     * @return searchTermConjunction property
     */
    public SearchTermConjunction getSearchTermConjunction() {

        return searchTermConjunction;
    }

    /**
     * Description
     * 
     * @param searchTermConjunction searchTermConjunction property
     */
    public void setSearchTermConjunction(SearchTermConjunction searchTermConjunction) {

        this.searchTermConjunction = searchTermConjunction;
    }

    /**
     * Description
     * @param advancedAndSearch the advancedAndSearch to set
     */
    public void setAdvancedAndSearch(Boolean advancedAndSearch) {

        this.advancedAndSearch = advancedAndSearch;
    }

    /**
     * Description
     * @return the advancedAndSearch
     */
    public Boolean getAdvancedAndSearch() {

        return advancedAndSearch;
    }
}
