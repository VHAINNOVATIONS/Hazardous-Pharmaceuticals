/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.search.test.integration;


import java.util.ResourceBundle;

import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * Abstract superclass for search tests.
 */
public abstract class ManagedItemSearchTestCase extends IntegrationTestCase {

    /**
     * WILDCARD
     */
    public static final String WILDCARD = "*";

    
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ManagedItemSearchTestCase.class);

    private ManagedItemService managedItemService;
    private SearchCriteriaVo searchCriteria;
    private ResourceBundle termFixture;
    private UserVo user;

    /**
     * Constructor
     * 
     * @param name String
     */
    public ManagedItemSearchTestCase(String name) {
        super(name);
    }
    
    /**
     * Sets up the test fixtures
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        LOG.info(getName());

        this.managedItemService = getNationalService(ManagedItemService.class);
        this.searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);
        this.user = new UserGenerator().getNationalManagerOne();
        this.termFixture = SearchServiceTermFixture
            .getBundle("gov.va.med.pharmacy.peps.service.common.search.test.integration.SearchServiceTermFixture");
    }

    /**
     * Tears down the test fixtures
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() {
        searchCriteria = null;
    }

    /**
     * Tests the contains search type
     * 
     * @throws Exception Exception
     */
    public abstract void testContains() throws Exception;

    /**
     * Test the begins with search type
     * 
     * @throws Exception Exception
     */
    public abstract void testBeginsWith() throws Exception;

    /**
     * Test the is exactly search type
     * 
     * @throws Exception Exception
     */
    public abstract void testIsExactly() throws Exception;

    /**
     * getManagedItemService
     * @return searchService property
     */
    public ManagedItemService getManagedItemService() {
        return managedItemService;
    }

    /**
     * getSearchCriteria
     * @return searchCriteria property
     */
    public SearchCriteriaVo getSearchCriteria() {
        return searchCriteria;
    }

    /**
     * setSearchCriteria
     * @param searchCriteria property
     */
    public void setSearchCriteria(SearchCriteriaVo searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    /**
     * getTermFixture
     * @return termFixture property
     */
    public ResourceBundle getTermFixture() {
        return termFixture;
    }

    /**
     * setTermFixture
     * @param termFixture property
     */
    public void setTermFixture(ResourceBundle termFixture) {
        this.termFixture = termFixture;
    }

    /**
     * Return current user
     * 
     * @return user property
     */
    public UserVo getUser() {
        return user;
    }

    /**
     * setUser
     * @param user property
     */
    public void setUser(UserVo user) {
        this.user = user;
    }

}
