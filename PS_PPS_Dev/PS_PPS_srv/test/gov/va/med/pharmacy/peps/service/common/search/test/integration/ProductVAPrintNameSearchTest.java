/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.search.test.integration;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;


/**
 * ProductVAPrintNameSearchTest
 */
public class ProductVAPrintNameSearchTest extends ProductSearchTestCase {

    /**
     * Constructor
     * 
     * @param name String
     */
    public ProductVAPrintNameSearchTest(String name) {
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
        String testValue = "A";
        SearchCriteriaVo search = getSearchCriteria();

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        search.setItemStatus(itemStatus);

        List<RequestItemStatus> req = new ArrayList<RequestItemStatus>();
        req.add(RequestItemStatus.APPROVED);
        search.setRequestStatus(req);

        // create search terms for the ProductVaPrintNameSearchTest BeginsWith search.
        Collection<SearchTermVo> sT = search.getSearchTerms();

        sT.add(new SearchTermVo(EntityType.PRODUCT, FieldKey.VA_PRINT_NAME, testValue, SearchType.BEGINS_WITH));
     
        List<ManagedItemVo> results = getManagedItemService().search(search);

        if (results.size() == 0) {
            fail("No results found!");
        }

        for (ManagedItemVo product : results) {
            assertTrue("should be true!", ((ProductVo) product).getVaPrintName().startsWith("A"));
        }

    }

    /**
     * Tests the contains search
     * 
     * @throws Exception Exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.search.test.integration.ManagedItemSearchTestCase#testContains()
     */
    public void testContains() throws Exception {
        String testValue = "A";
        SearchCriteriaVo search = getSearchCriteria();

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        search.setItemStatus(itemStatus);

        // set the RequestItemStatus for the testContains for the ProductVAPrintNameSearchTest.
        List<RequestItemStatus> req = new ArrayList<RequestItemStatus>();
        req.add(RequestItemStatus.APPROVED);
        search.setRequestStatus(req);

        Collection<SearchTermVo> sT = search.getSearchTerms();

        sT.add(new SearchTermVo(EntityType.PRODUCT, FieldKey.VA_PRINT_NAME, testValue, SearchType.CONTAINS));
        List<ManagedItemVo> results = getManagedItemService().search(getSearchCriteria());

        if (results.size() == 0) {
            fail(" No results found");
        }

        for (ManagedItemVo product : results) {
            assertTrue(" should be true", ((ProductVo) product).getVaPrintName().contains("A"));
               
        }
    }

   

    /**
     * Tests the exactly search
     * 
     * @throws Exception Exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.test.integration.search.ManagedItemSearchTestCase#testIsExactly()
     */
    public void testIsExactly() throws Exception {
        String testValue = "DIGOXIN 0.25MG TAB";
        SearchCriteriaVo search = getSearchCriteria();

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        search.setItemStatus(itemStatus);

        List<RequestItemStatus> req = new ArrayList<RequestItemStatus>();
        req.add(RequestItemStatus.APPROVED);
        search.setRequestStatus(req);

        Collection<SearchTermVo> sT = search.getSearchTerms();

        sT.add(new SearchTermVo(EntityType.PRODUCT, FieldKey.VA_PRINT_NAME, testValue, SearchType.EQUALS));
     
        List<ManagedItemVo> results = getManagedItemService().search(getSearchCriteria());

        if (results.size() == 0) {
            fail("No results found");
        }

        for (ManagedItemVo product : results) {
            assertTrue("should be true", ((ProductVo) product).getVaPrintName().equals(testValue));
              
        }
    }

    
}
