/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.search.test.integration;


import java.io.File;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.FileUtility;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.SearchTemplateService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * Class for search template tests
 */
public class SearchTemplateValidatorTest extends IntegrationTestCase {
    
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ManagedItemSearchTestCase.class);
    private SearchTemplateService searchTemplateService;
    private SearchTemplateVo searchTemplate;

    /**
     * Constructor
     * 
     * @param name String
     */
    public SearchTemplateValidatorTest(String name) {
        super(name);
    }

    /**
     * Sets up the test fixtures
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        LOG.info(getName());
        this.searchTemplate = new SearchTemplateVo();
        this.searchTemplateService = getNationalService(SearchTemplateService.class);
        FileUtility.deleteDir(new File("templates"));
    }

    /**
     * Tears down the test fixtures
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() {
        searchTemplate = null;
    }

    /**
     * Creates a stubbed user for testing
     * 
     * @return UserVo
     */
    public UserVo createServiceUserVo() {
        return new UserGenerator().getNationalManagerOne();
    }

    /**
     * Tests an attempt to save a system template without administrative privileges.
     */
    public void testSaveSystemSearchNoPermissions() {
        setSearchTemplate(new SearchTemplateVo());

        getSearchTemplate().setTemplateName("testSaveSystemSearchNoPermissions");
        getSearchTemplate().setSystemLevel(true);
        getSearchTemplate().getSearchCriteria().getSearchTerms().add(
            new SearchTermVo(EntityType.PRODUCT, FieldKey.GENERIC_NAME, "Dig1"));
        boolean failed = false;

        try {
            getSearchTemplateService().create(createServiceUserVo(), getSearchTemplate(), true);
        } catch (ValidationException ex) {
            failed = true;
        }

        assertTrue("should be true.", failed);

    }

    /**
     * Tests saving a template with no name.
     */
    public void testSaveTemplateNoName() {
        setSearchTemplate(new SearchTemplateVo());

        getSearchTemplate().getSearchCriteria().getSearchTerms().add(
            new SearchTermVo(EntityType.PRODUCT, FieldKey.GENERIC_NAME, "Dig2"));

        boolean failed = false;

        try {
            getSearchTemplateService().create(createServiceUserVo(), getSearchTemplate(), true);
        } catch (ValidationException ex) {
            failed = true;
        }

        assertTrue("should be true", failed);

    }

    /**
     * getSearchTemplateService
     * @return searchTemplateService property
     */
    public SearchTemplateService getSearchTemplateService() {
        return searchTemplateService;
    }

    /**
     * setSearchTemplateService
     * @param searchTemplateService searchTemplateService property
     */
    public void setSearchTemplateService(SearchTemplateService searchTemplateService) {
        this.searchTemplateService = searchTemplateService;
    }

    /**
     * getSearchTemplate
     * @return searchTemplate property
     */
    public SearchTemplateVo getSearchTemplate() {
        return searchTemplate;
    }

    /**
     * setSearchTemplate
     * @param searchTemplate searchTemplate property
     */
    public void setSearchTemplate(SearchTemplateVo searchTemplate) {
        this.searchTemplate = searchTemplate;
    }

}
