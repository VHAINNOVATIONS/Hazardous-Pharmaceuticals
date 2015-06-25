/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.search.test.integration;


import java.io.File;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.FileUtility;
import gov.va.med.pharmacy.peps.common.utility.test.generator.SearchTemplateGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.service.common.session.SearchTemplateService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * Class for search template tests
 */
public class SearchTemplateTest extends IntegrationTestCase {
    
    
    /**
     * LOG
     */
    protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SearchTemplateTest.class);

    private static final String SHOULDBETRUE = "should be true";
    private static final String DISP_NAME = "displayableName";
    private SearchTemplateService searchService;
    private SearchTemplateVo searchCriteria;
    private UserVo user;

    /**
     * Constructor
     * 
     * @param name String
     */
    public SearchTemplateTest(String name) {
        super(name);
    }

    /**
     * Sets up the test fixtures
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        LOG.info(getName());
        searchCriteria = createSearchTemplate();

        searchService = getNationalService(gov.va.med.pharmacy.peps.service.common.session.SearchTemplateService.class);
        user = new UserGenerator().getLocalAdministratorOne();
        FileUtility.deleteDir(new File("templates"));
    }

    /**
     * Creates a search template fixture
     * 
     * @return SearchCriteriaVo
     */
    private SearchTemplateVo createSearchTemplate() {
        SearchTemplateGenerator generator = new SearchTemplateGenerator();
        SearchTemplateVo search = generator.getFirst();

        PrintTemplateVo print = new PrintTemplateVo();
        print.setTemplateName("Test Template");
        print.setTemplateLocation(TemplateLocation.ITEM_AUDIT_HISTORY);
        search.setPrintTemplate(print);
        search.setId(null);

        return search;
    }

    /**
     * Tears down the test fixtures for SearchTemplateTest.
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() {
        searchCriteria = null;
    }

    /**
     * Tests the save search template functionality for a user level template for SearchTemplateTest.
     * 
     * @throws Exception Exception
     */
    public void testSaveUserSearch() throws Exception {
        String tName = "NEW TEMPLATE ONE";
        getSearchCriteria().setTemplateName(tName);
        getManagedItemService().create(user, getSearchCriteria(), true);
        List<SearchTemplateVo> templates = getManagedItemService().retrieve(user);
        boolean found = false;

        for (SearchTemplateVo search : templates) {

            if (search.getTemplateName().equals(tName)) {
                found = true;
            }
        }

        assertTrue("should be true!", found);
    }

    /**
     * Tests the save search template functionality for a system level template for SearchTemplateTest.
     * 
     * @throws Exception Exception
     */
    public void testSaveSystemSearch() throws Exception {
        String testSave = "testSaveSystemSearch";
        getSearchCriteria().setTemplateName(testSave);
        getSearchCriteria().setSystemLevel(true);
        getManagedItemService().create(user, getSearchCriteria(), true);
        List<SearchTemplateVo> templates = getManagedItemService().retrieve(user);
        boolean found = false;

        for (SearchTemplateVo search : templates) {

            if (search.getTemplateName().equals(testSave)) {
                found = true;
            }
        }

        assertTrue("should be true.", found);
    }

    /**
     * Tests save a duplicate user level search when already warned of duplicate.
     * 
     * @throws Exception Exception
     */
    public void testSaveUserDuplicateSearchWarned() throws Exception {
        String dup = "testSaveUserDuplicateSearchWarned";
        String notes = "New Notes";
        getSearchCriteria().setTemplateName(dup);

        // save original search
        getManagedItemService().create(user, getSearchCriteria(), true);
        List<SearchTemplateVo> templates = getManagedItemService().retrieve(user);
        boolean found = false;

        // run through the templates and see if any are duplicates.
        for (SearchTemplateVo search : templates) {

            if (search.getTemplateName().equals(dup)) {
                found = true;
                setSearchCriteria(search);
            }
        }

        assertTrue(SHOULDBETRUE, found);

        // Change search and save again with same name
        getSearchCriteria().setNotes(notes);
        getManagedItemService().create(user, getSearchCriteria(), true);
        List<SearchTemplateVo> newTemplates = getManagedItemService().retrieve(user);
        found = false;

        for (SearchTemplateVo search : newTemplates) {

            if (search.getTemplateName().equals(dup)) {
                found = true;
                assertTrue(SHOULDBETRUE, search.getNotes().equals(notes));
            }
        }

        assertTrue(SHOULDBETRUE, found);
    }

    /**
     * Tests the save of a system level duplicate search when user has been warned of duplicates.
     * 
     * @throws Exception Exception
     */
    public void testSaveSystemDuplicateSearchWarned() throws Exception {
        String dup = "testSaveSystemDuplicateSearchWarned";
        String notesMod = "Test Notes mod";
        getSearchCriteria().setTemplateName(dup);
        getSearchCriteria().setSystemLevel(true);

        // save original search
        getManagedItemService().create(user, getSearchCriteria(), true);
        List<SearchTemplateVo> templates = getManagedItemService().retrieve(user);
        boolean found = false;

        for (SearchTemplateVo search : templates) {

            if (search.getTemplateName().equals(dup)) {
                found = true;
                setSearchCriteria(search);
            }
        }

        assertTrue(SHOULDBETRUE, found);

        // Change search and save again with same name
        getSearchCriteria().setNotes(notesMod);
        getManagedItemService().create(user, getSearchCriteria(), true);
        List<SearchTemplateVo> newTemplates = getManagedItemService().retrieve(user);
        found = false;

        for (SearchTemplateVo search : newTemplates) {

            if (search.getTemplateName().equals(dup)) {
                found = true;
                assertTrue(SHOULDBETRUE, search.getNotes().equals(notesMod));
            }
        }

        assertTrue(SHOULDBETRUE, found);
    }

    /**
     * Tests duplicate user level search when search should not be saved because of warned setting.
     * 
     * @throws Exception Exception
     */
    public void testSaveUserDuplicateSearchNotWarned() throws Exception {
        String dup = "testSaveUserDuplicateSearchNotWarned";

        
        getSearchCriteria().setTemplateName(dup);

        // save original search
        getManagedItemService().create(user, getSearchCriteria(), true);
        List<SearchTemplateVo> templates = getManagedItemService().retrieve(user);
        boolean found = false;

        for (SearchTemplateVo search : templates) {

            if (search.getTemplateName().equals(dup)) {
                found = true;
            }
        }

        assertTrue(SHOULDBETRUE, found);

        // Change the search detials and save again with same name
        getSearchCriteria().setDisplayableName(DISP_NAME);
        getManagedItemService().create(user, getSearchCriteria(), false);
        List<SearchTemplateVo> newTemplates = getManagedItemService().retrieve(user);
        found = false;

        for (SearchTemplateVo search : newTemplates) {

            if (search.getTemplateName().equals(dup)) {
                found = true;
                assertFalse(SHOULDBETRUE, search.getDisplayableName().equals(DISP_NAME));
            }
        }

        assertTrue(SHOULDBETRUE, found);

    }

    /**
     * Tests duplicate system level search when search should not be saved because of warned setting.
     * 
     * @throws Exception Exception
     */
    public void testSaveSystemDuplicateSearchNotWarned() throws Exception {
        String duplicateNotWarned = "testSaveSystemDuplicateSearchNotWarned";
        getSearchCriteria().setTemplateName(duplicateNotWarned);
        getSearchCriteria().setSystemLevel(true);

        // save original search
        getManagedItemService().create(user, getSearchCriteria(), true);
        List<SearchTemplateVo> templates = getManagedItemService().retrieve(user);
        boolean found = false;

        for (SearchTemplateVo search : templates) {

            if (search.getTemplateName().equals(duplicateNotWarned)) {
                found = true;
            }
        }

        assertTrue(SHOULDBETRUE, found);

        // Change search and save again with same name
        getSearchCriteria().setDisplayableName(DISP_NAME);
        getManagedItemService().create(user, getSearchCriteria(), false);
        List<SearchTemplateVo> newTemplates = getManagedItemService().retrieve(user);
        found = false;

        for (SearchTemplateVo search : newTemplates) {

            if (search.getTemplateName().equals(duplicateNotWarned)) {
                found = true;
                assertFalse("should be false", search.getDisplayableName().equals(DISP_NAME));
            }
        }

        assertTrue(SHOULDBETRUE, found);

    }

    /**
     * getManagedItemService
     * @return searchService property
     */
    public SearchTemplateService getManagedItemService() {
        return searchService;
    }

    /**
     * getManagedItemService
     * @param searchServiceIn property
     */
    public void getManagedItemService(SearchTemplateService searchServiceIn) {
        this.searchService = searchServiceIn;
    }

    /**
     * getSearchCriteria
     * @return searchCriteria property
     */
    public SearchTemplateVo getSearchCriteria() {
        return searchCriteria;
    }

    /**
     * setSearchCriteria
     * @param searchCriteria property
     */
    public void setSearchCriteria(SearchTemplateVo searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    /**
     * Return current user for the SearchTemplateTest
     * 
     * @return user property
     */
    public UserVo getUser() {
        return user;
    }

    /**
     * setUser for the SearchTemplateTest
     * @param user property
     */
    public void setUser(UserVo user) {
        this.user = user;
    }

}
