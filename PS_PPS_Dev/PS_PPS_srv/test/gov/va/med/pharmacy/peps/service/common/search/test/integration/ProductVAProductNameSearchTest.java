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
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;


/**
 * Search product by VA Product name
 */
public class ProductVAProductNameSearchTest extends ProductSearchTestCase {

    /**
     * Constructor
     * 
     * @param name String
     */
    public ProductVAProductNameSearchTest(String name) {
        super(name);
    }

    /**
     * Sets up test fixtures for ProductVaProductNameSearchTest
     * 
     * @see gov.va.med.pharmacy.peps.service.common.search.test.integration.ProductSearchTestCase#setUp()
     */
    public void setUp() {
        super.setUp();

        getSearchCriteria().setSearchDomain(SearchDomain.ADVANCED);

    }

    /**
     * Tests the begins with search for ProductVaProductNameSearchTest
     * 
     * @throws Exception Exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.search.test.integration.ManagedItemSearchTestCase#testBeginsWith()
     */
    public void testBeginsWith() throws Exception {
        String firstPart = "product_name.firstPart";
        String testValue = getTermFixture().getString(firstPart);
        SearchCriteriaVo search = getSearchCriteria();

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        search.setItemStatus(itemStatus);

        Collection<SearchTermVo> sT = search.getSearchTerms();

        sT.add(new SearchTermVo(EntityType.PRODUCT, FieldKey.VA_PRODUCT_NAME, testValue, SearchType.BEGINS_WITH));
        List<ManagedItemVo> results = getManagedItemService().search(getSearchCriteria());

        if (results.size() == 0) {
            fail("1.No results found");
        }

        for (ManagedItemVo product : results) {
            assertTrue("1.should be true", ((ProductVo) product).getVaProductName().startsWith(
                getTermFixture().getString(firstPart)));
        }

    }

    /**
     * Tests the contains search for ProductVaProductNameSearchTest
     * 
     * @throws Exception Exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.search.test.integration.ManagedItemSearchTestCase#testContains()
     */
    public void testContains() throws Exception {
        String midPart = "product_name.middlePart";
        String testValue = getTermFixture().getString(midPart);
        SearchCriteriaVo search = getSearchCriteria();

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        search.setItemStatus(itemStatus);

        Collection<SearchTermVo> sT = search.getSearchTerms();

        sT.add(new SearchTermVo(EntityType.PRODUCT, FieldKey.VA_PRODUCT_NAME, testValue, SearchType.CONTAINS));
        List<ManagedItemVo> results = getManagedItemService().search(getSearchCriteria());

        if (results.size() == 0) {
            fail("2.No results found");
        }

        for (ManagedItemVo product : results) {
            assertTrue("2.should be true", ((ProductVo) product).getVaProductName().contains(
                getTermFixture().getString(midPart)));
        }
    }

    /**
     * Test the exact search for ProductVaProductNameSearchTest
     * 
     * @throws Exception Exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.test.integration.search.ManagedItemSearchTestCase#testIsExactly()
     */
    public void testIsExactly() throws Exception {

        String pName = "product_name";
        String testValue = getTermFixture().getString(pName);
        SearchCriteriaVo search = getSearchCriteria();

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        search.setItemStatus(itemStatus);

        Collection<SearchTermVo> sT = search.getSearchTerms();

        sT.add(new SearchTermVo(EntityType.PRODUCT, FieldKey.VA_PRODUCT_NAME, testValue, SearchType.EQUALS));
        List<ManagedItemVo> results = getManagedItemService().search(getSearchCriteria());

        if (results.size() == 0) {
            fail("No results found");
        }

        for (ManagedItemVo product : results) {
            assertTrue("should be true", ((ProductVo) product).getVaProductName().equals(
                getTermFixture().getString(pName)));
        }
    }
}
