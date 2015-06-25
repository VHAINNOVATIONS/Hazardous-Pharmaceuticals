/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.SearchTemplateValidator;

import junit.framework.TestCase;


/**
 * Verify {@link SearchTemplateValidator}
 */
public class SearchTemplateValidatorTest extends TestCase {
    private static final UserVo PNM1 = new UserGenerator().getNationalManagerOne();
    private static final Logger LOG = Logger.getLogger(SearchTemplateValidatorTest.class);
    private static final String DIG = "Dig";

    private SearchTemplateValidator searchTemplateValidator;

    /**
     * Instantiate the validator.
     * 
     * @throws Exception 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {

        LOG.debug("--------------- " + getName() + " ---------------");
        this.searchTemplateValidator = new SearchTemplateValidator();
    }

    /**
     * Tests an attempt to save a system template without administrative privileges.
     */
    public void testSaveSystemSearchNoPermissions() {
        Errors errors = new Errors();
        SearchTemplateVo searchTemplate = new SearchTemplateVo();

        searchTemplate.setTemplateName("testSaveSystemSearchNoPermissions");
        searchTemplate.setSystemLevel(true);
        searchTemplate.getSearchCriteria().getSearchTerms().add(
            new SearchTermVo(EntityType.PRODUCT, FieldKey.GENERIC_NAME, DIG));

        searchTemplateValidator.validate(searchTemplate, PNM1, errors);

        LOG.debug(errors.getLocalizedErrorsAsString());

        assertTrue("Should have errors!", errors.hasErrors());
    }

    /**
     * Tests saving a template with no name.
     */
    public void testSaveTemplateNoName() {
        Errors errors = new Errors();
        SearchTemplateVo searchTemplate = new SearchTemplateVo();

        searchTemplate.getSearchCriteria().getSearchTerms().add(
            new SearchTermVo(EntityType.PRODUCT, FieldKey.GENERIC_NAME, DIG));

        searchTemplateValidator.validate(searchTemplate, PNM1, errors);

        LOG.debug(errors.getLocalizedErrorsAsString());

        assertTrue("Should have errors", errors.hasErrors());
    }
}
