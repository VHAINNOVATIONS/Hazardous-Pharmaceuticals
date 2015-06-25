/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.SearchCriteriaValidator;

import junit.framework.TestCase;


/**
 * SearchCriteriaValidatorTest
 */
public class SearchCriteriaValidatorTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(SearchCriteriaValidatorTest.class);
    
    private static final String HAVE_ERRORS = "Should have errors";
    private static final String HAVE_NO_ERRORS = "Should have no errors";
    private SearchCriteriaValidator validator;
    private Errors errors;
    private SearchCriteriaVo ndcSearchCriteria;

    /**
     * Sets up each test case
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {
        final String line = "---------------";
        LOG.debug(line + getName() + line);
        this.validator = new SearchCriteriaValidator();
        this.errors = new Errors();
        this.ndcSearchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);
        ndcSearchCriteria.setEntityType(EntityType.NDC);
        ndcSearchCriteria.getSearchTerms().get(0).getSearchField().setFieldKey(FieldKey.NDC);
        ndcSearchCriteria.getSearchTerms().get(0).getSearchField().setEntityType(EntityType.NDC);
    }

    /**
     * Tests an advanced search where no search criteria has been entered.
     */
    public void testAdvancedNoCriteria() {
        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.ADVANCED, Environment.LOCAL);

        validator.validate(searchCriteria, getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertFalse(HAVE_NO_ERRORS, getErrors().hasErrors());
    }

    /**
     * Tests a simple search where no search criteria has been entered.
     */
    public void testSimpleNoCriteria() {
        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);

        validator.validate(searchCriteria, getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertTrue(HAVE_ERRORS, getErrors().hasErrors());
    }

    /**
     * Tests duplicate criteria
     */
    public void testDuplicateCriteria() {
        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);

        searchCriteria.getSearchTerms().add(new SearchTermVo(EntityType.PRODUCT, FieldKey.GENERIC_NAME, "Dig"));
        searchCriteria.getSearchTerms().add(new SearchTermVo(EntityType.PRODUCT, FieldKey.GENERIC_NAME, "Risp"));

        validator.validate(searchCriteria, getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertTrue(HAVE_ERRORS, getErrors().hasErrors());
    }

    /**
     * Tests the transformation of search terms
     * 
     * @throws Exception 
     */
    public void testNdcNeedsPadding() throws Exception {
        getNdcSearchCriteria().getSearchTerms().get(0).setValue("25-4-8");

        validator.validate(getNdcSearchCriteria(), getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertFalse(HAVE_NO_ERRORS, getErrors().hasErrors());

    }

    /**
     * Tests two dashes
     * 
     * @throws Exception 
     */
    public void testNdcTwoDashes() throws Exception {
        getNdcSearchCriteria().getSearchTerms().get(0).setValue("58922-6724-78");

        validator.validate(getNdcSearchCriteria(), getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertFalse(HAVE_NO_ERRORS, getErrors().hasErrors());
    }

    /**
     * Tests long first segment
     * 
     * @throws Exception 
     */
    public void testNdcLongFirstSegment() throws Exception {

        getNdcSearchCriteria().getSearchTerms().get(0).setValue("589221-6724-78");

        validator.validate(getNdcSearchCriteria(), getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertTrue(HAVE_ERRORS, getErrors().hasErrors());
    }

    /**
     * Tests long second segment
     * 
     * @throws Exception 
     */
    public void testNdcLongSecondSegment() throws Exception {

        getNdcSearchCriteria().getSearchTerms().get(0).setValue("58922-672473-78");

        validator.validate(getNdcSearchCriteria(), getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertTrue(HAVE_ERRORS, getErrors().hasErrors());
    }

    /**
     * Tests long last segment
     * 
     * @throws Exception 
     */
    public void testNdcLongLastSegment() throws Exception {

        getNdcSearchCriteria().getSearchTerms().get(0).setValue("258922-6724-78892");

        validator.validate(getNdcSearchCriteria(), getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertTrue(HAVE_ERRORS, getErrors().hasErrors());
    }

    /**
     * Tests ndc with only one dash
     * 
     * @throws Exception 
     */
    public void testNdcOneDash() throws Exception {
        getNdcSearchCriteria().getSearchTerms().get(0).setValue("258922-6724");

        validator.validate(getNdcSearchCriteria(), getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertTrue(HAVE_ERRORS, getErrors().hasErrors());
    }

    /**
     * Tests ndc with no dashes
     * 
     * @throws Exception 
     */
    public void testNdcNoDash() throws Exception {
        final String term = "2589226724";
        getNdcSearchCriteria().getSearchTerms().get(0).setValue(term);

        validator.validate(getNdcSearchCriteria(), getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertEquals("NDC not equal", term, getNdcSearchCriteria().getSearchTerms().get(0).getValue());
    }

    /**
     * Tests ndc with no dashes
     * 
     * @throws Exception 
     */
    public void testNdcThreeDashes() throws Exception {
        getNdcSearchCriteria().getSearchTerms().get(0).setValue("258-893-8-1");

        validator.validate(getNdcSearchCriteria(), getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertTrue(HAVE_ERRORS, getErrors().hasErrors());
    }

    /**
     * Tests ndc with no dashes and too long
     * 
     * @throws Exception 
     */
    public void testNdcTooLong() throws Exception {
        getNdcSearchCriteria().getSearchTerms().get(0).setValue("25892267246292");

        validator.validate(getNdcSearchCriteria(), getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertTrue(HAVE_ERRORS, getErrors().hasErrors());
    }

    /**
     * Tests ndc not numeric
     * 
     * @throws Exception 
     */
    public void testNdcNonNumeric() throws Exception {
        getNdcSearchCriteria().getSearchTerms().get(0).setValue("78fis729");

        validator.validate(getNdcSearchCriteria(), getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertTrue(HAVE_ERRORS, getErrors().hasErrors());
    }

    /**
     * Tests ndc with special characters
     * 
     * @throws Exception 
     */
    public void testNdcSpecChar() throws Exception {
        getNdcSearchCriteria().getSearchTerms().get(0).setValue("78&8");

        validator.validate(getNdcSearchCriteria(), getErrors());

        LOG.debug(getErrors().getLocalizedErrorsAsString());

        assertTrue(HAVE_ERRORS, getErrors().hasErrors());
    }

    /**
     * If searching for a field that is grouped by another field, the field should be valid.
     * <p>
     * For example if searching for {@link FieldKey#LOCAL_MEDICATION_ROUTE} on an Orderable Item, the search criteria should
     * be valid even though the FieldKey is not on an OI. In this case, the group, {@link FieldKey#OI_ROUTE} is on the OI.
     */
    public void testGroupedDataField() {
        SearchCriteriaVo criteria = new SearchCriteriaVo(SearchDomain.ADVANCED, Environment.LOCAL);
        criteria.setEntityType(EntityType.ORDERABLE_ITEM);
        criteria.getSearchTerms().clear();
        criteria.getSearchTerms().add(new SearchTermVo(EntityType.ORDERABLE_ITEM, FieldKey.LOCAL_MEDICATION_ROUTE, "ORAL"));

        try {
            criteria.validate();
        } catch (ValueObjectValidationException e) {
            LOG.error(e.getLocalizedMessage(), e);
            fail("Should be valid");
        }
    }

    /**
     * getNdcSearchCriteria
     * @return ndcSearchCriteria property
     */
    public SearchCriteriaVo getNdcSearchCriteria() {
        return ndcSearchCriteria;
    }

    /**
     * setNdcSearchCriteria
     * @param ndcSearchCriteria ndcSearchCriteria property
     */
    public void setNdcSearchCriteria(SearchCriteriaVo ndcSearchCriteria) {
        this.ndcSearchCriteria = ndcSearchCriteria;
    }

    /**
     * getValidator
     * @return validator property
     */
    public SearchCriteriaValidator getValidator() {
        return validator;
    }

    /**
     * setValidator
     * @param validator validator property
     */
    public void setValidator(SearchCriteriaValidator validator) {
        this.validator = validator;
    }

    /**
     * getErrors
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }

    /**
     * setErrors
     * @param errors errors property
     */
    public void setErrors(Errors errors) {
        this.errors = errors;
    }

}
