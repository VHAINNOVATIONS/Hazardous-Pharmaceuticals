/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.search.test.integration;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;


/**
 * Tests the search product by generic name
 */
public class ProductGenericNameSearchTest extends ProductSearchTestCase {

    /**
     * Constructor
     * 
     * @param name String
     */
    public ProductGenericNameSearchTest(String name) {
        super(name);
    }

    /**
     * Sets up test fixtures
     * 
     * @see gov.va.med.pharmacy.peps.service.common.search.test.integration.ProductSearchTestCase#setUp()
     */
    public void setUp() {
        super.setUp();
        getSearchCriteria().setSearchDomain(SearchDomain.ADVANCED);

    }

    /**
     * Tests the begins with search
     * 
     * @throws Exception Exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.search.test.integration.ManagedItemSearchTestCase#testBeginsWith()
     */
    public void testBeginsWith() throws Exception {

        String firstPart = "generic_name.firstPart";
        String testValue = getTermFixture().getString(firstPart);
        SearchCriteriaVo testVo = getSearchCriteria();

        List<SearchTermVo> terms = new ArrayList<SearchTermVo>();
        terms.add(new SearchTermVo(EntityType.PRODUCT, FieldKey.GENERIC_NAME, testValue, SearchType.BEGINS_WITH));
        testVo.setSearchTerms(terms);

        Date start = new Date();
        List<ManagedItemVo> results = getManagedItemService().search(testVo);
        Date end = new Date();

        if (results == null || results.size() < 1) {
            fail("No results found.");
        }

        for (ManagedItemVo product : results) {
            assertTrue("should be true.",
                       ((ProductVo) product).getGenericName().getValue()
                               .startsWith(getTermFixture().getString(firstPart)));
        }

        assertTrue(MAX_SEARCH_RETRIEVE_FAIL, underMaxWaitTime(start, end));
        assertTrue(TOO_LONG_RETRIEVE_FAIL, underTooLongTime(start, end));

//        getSearchCriteria().getSearchTerms().add(
//            new SearchTermVo(EntityType.PRODUCT, FieldKey.GENERIC_NAME, testValue, SearchType.BEGINS_WITH));
//        List<ManagedItemVo> results = getManagedItemService().search(getSearchCriteria());
//
//        if (results.size() == 0) {
//            fail("No results found");
//        }
//
//        for (ManagedItemVo product : results) {
//            assertTrue("should be true", ((ProductVo) product).getGenericName().getValue().startsWith(
//                getTermFixture().getString("generic_name.firstPart")));
//        }

    }

    /**
     * Tests the contains search
     * 
     * @throws Exception Exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.search.test.integration.ManagedItemSearchTestCase#testContains()
     */
    public void testContains() throws Exception {
        String midPart = "generic_name.middlePart";
        String testValue = getTermFixture().getString(midPart);
        getSearchCriteria().getSearchTerms().add(
            new SearchTermVo(EntityType.PRODUCT, FieldKey.GENERIC_NAME, testValue, SearchType.CONTAINS));

        Date start = new Date();
        List<ManagedItemVo> results = getManagedItemService().search(getSearchCriteria());
        Date end = new Date();

        if (results.size() == 0) {
            fail(" No results found");
        }

        for (ManagedItemVo product : results) {
            assertTrue(" should be true", ((ProductVo) product).getGenericName().getValue().contains(
                getTermFixture().getString(midPart)));
        }

        assertTrue(MAX_SEARCH_RETRIEVE_FAIL, underMaxWaitTime(start, end));
        assertTrue(TOO_LONG_RETRIEVE_FAIL, underTooLongTime(start, end));

    }

   

    /**
     * Test the exact search
     * 
     * @throws Exception Exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.test.integration.search.ManagedItemSearchTestCase#testIsExactly()
     */
    public void testIsExactly() throws Exception {
        String lastPart = "generic_name";
        String testValue = getTermFixture().getString(lastPart);
        getSearchCriteria().getSearchTerms().add(
            new SearchTermVo(EntityType.PRODUCT, FieldKey.GENERIC_NAME, testValue, SearchType.EQUALS));

        Date start = new Date();
        List<ManagedItemVo> results = getManagedItemService().search(getSearchCriteria());
        Date end = new Date();

        if (results.size() == 0) {
            fail("No results found");
        }

        for (ManagedItemVo product : results) {
            assertTrue("should be true", ((ProductVo) product).getGenericName().getValue().equals(
                getTermFixture().getString(lastPart)));
        }

        assertTrue(MAX_SEARCH_RETRIEVE_FAIL, underMaxWaitTime(start, end));
        assertTrue(TOO_LONG_RETRIEVE_FAIL, underTooLongTime(start, end));

    }
}
