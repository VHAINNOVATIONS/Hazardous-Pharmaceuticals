/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.search.test.integration;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;


/**
 * Tests the search product by ingredient
 */
public class ProductIngredientSearchTest extends ProductSearchTestCase {

    /**
     * Constructor
     * 
     * @param name String
     */
    public ProductIngredientSearchTest(String name) {
        super(name);
    }

    /**
     * Sets up test fixtures
     * 
     * @throws Exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.search.test.integration.ProductSearchTestCase#setUp()
     */
    public void setUp() {
        super.setUp();

        getSearchCriteria().setSearchDomain(SearchDomain.ADVANCED);
        
    }

    /**
     * Tests the begins with search for the ProductIngredientSearchTest
     * 
     * @throws Exception Exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.search.test.integration.ManagedItemSearchTestCase#testBeginsWith()
     */
    public void testBeginsWith() throws Exception {

        int passingProducts = 0;
        String firstPart = "ingredient.firstPart";
        String testValue = getTermFixture().getString(firstPart);
        getSearchCriteria().getSearchTerms().add(
            new SearchTermVo(EntityType.PRODUCT, FieldKey.INGREDIENT, testValue, SearchType.BEGINS_WITH));
        List<ManagedItemVo> results = getManagedItemService().search(getSearchCriteria());

        if (results.size() == 0) {
            fail("No results found! ");
        }

        for (ManagedItemVo product : results) {

            for (ActiveIngredientVo activeIngredient : ((ProductVo) product).getActiveIngredients()) {
                IngredientVo ingredientName = activeIngredient.getIngredient();

                if (ingredientName.getValue().startsWith(getTermFixture().getString(firstPart))) {
                    passingProducts++;
                    break;
                }
            }
        }

        // Check to ensure thatthere is a product where no active ingredients match the search criteria
        if (passingProducts != results.size()) {
            fail();
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
        int passingProducts = 0;

        String midPart = "ingredient.middlePart";
        String testValue = getTermFixture().getString(midPart);
        getSearchCriteria().getSearchTerms().add(
            new SearchTermVo(EntityType.PRODUCT, FieldKey.INGREDIENT, testValue, SearchType.CONTAINS));
        List<ManagedItemVo> results = getManagedItemService().search(getSearchCriteria());

        if (results.size() == 0) {
            fail("No results found!");
        }

        for (ManagedItemVo product : results) {

            for (ActiveIngredientVo activeIngredient : ((ProductVo) product).getActiveIngredients()) {
                IngredientVo ingredientName = activeIngredient.getIngredient();

                if (ingredientName.getValue().contains(getTermFixture().getString(midPart))) {
                    passingProducts++;
                    break;
                }
            }
        }

        // there is a product in testContains where no active ingredients match the search criteria
        if (passingProducts != results.size()) {
            fail();
        }
    }

    /**
     * Tests the is Exactly search
     * 
     * @throws Exception Exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.test.integration.search.ManagedItemSearchTestCase#testIsExactly()
     */
    public void testIsExactly() throws Exception {

        int passingProducts = 0;
        String testValue = "ACETAMINOPHEN";
        SearchCriteriaVo search = getSearchCriteria();

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        search.setItemStatus(itemStatus);

        // set the request item status for the isExactlyTest for the ProductIngredientSearchTest
        List<RequestItemStatus> req = new ArrayList<RequestItemStatus>();
        req.add(RequestItemStatus.APPROVED);
        search.setRequestStatus(req);

        Collection<SearchTermVo> sT = search.getSearchTerms();

        sT.add(new SearchTermVo(EntityType.PRODUCT, FieldKey.INGREDIENT, testValue, SearchType.EQUALS));

        List<ManagedItemVo> results = getManagedItemService().search(getSearchCriteria());

        if (results.size() == 0) {
            fail("No results found");
        }

        for (ManagedItemVo product : results) {

            for (ActiveIngredientVo activeIngredient : ((ProductVo) product).getActiveIngredients()) {
                IngredientVo ingredientName = activeIngredient.getIngredient();

                if (ingredientName.getValue().equals(testValue)) {
                    passingProducts++;
                    break;
                }
            }
        }

        // there is a product where no active ingredients match the search criteria
        if (passingProducts != results.size()) {
            fail();
        }
    }

}
