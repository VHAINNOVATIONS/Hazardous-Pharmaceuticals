/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.search.test.integration;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;


/**
 * Searches for products only
 */
public abstract class ProductSearchTestCase extends ManagedItemSearchTestCase { 

    private static boolean INITIALIZED = false;

    private ProductVo product;

    /**
     * Constructor
     * 
     * @param name String
     */
    public ProductSearchTestCase(String name) {
        super(name);
    }

    /**
     * Sets up the test fixtures
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        super.setUp();
        getSearchCriteria().setEntityType(EntityType.PRODUCT);
        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.PENDING);

        getSearchCriteria().setRequestStatus(requestStatus);

        if (!INITIALIZED) {
            try {
                createProductData();
            } catch (Exception e) {
                fail(e.toString());
            }
        }
    }

    /**
     * Initializes the database
     * 
     */
    private void createProductData() {
        try {

            product = (ProductVo) getManagedItemService().retrieveTemplate("9993", EntityType.PRODUCT);

            product.setTradeName(getTermFixture().getString("trade_name"));
            GenericNameVo gn = new GenericNameVo();
            gn.setId("9991");
            gn.setValue(getTermFixture().getString("generic_name"));
            product.setGenericName(gn);
            product.setVaProductName(getTermFixture().getString("product_name"));
            product.setVaPrintName(getTermFixture().getString("print_name"));

            Collection<ActiveIngredientVo> activeIngredients = new ArrayList<ActiveIngredientVo>();
            ActiveIngredientVo ing = new ActiveIngredientVo();
            IngredientVo ingredientName = new IngredientVo();
            ingredientName.setId("9992");
            ingredientName.setValue("ATROPINE SULFATE");

            DrugUnitVo drugUnitVo = new DrugUnitVo();
            drugUnitVo.setId("9994");
            drugUnitVo.setValue("0.5ML");
            ing.setIngredient(ingredientName);
            ing.setDrugUnit(drugUnitVo);

            ing.setStrength("12");
            ing.setActive(true);
            activeIngredients.add(ing);
            product.setActiveIngredients(activeIngredients);

            getManagedItemService().create(product, getUser());

        } catch (DuplicateItemException ex) { 
            fail("This function should not throw a DuplicateItemException.");
        } catch (ValidationException e) {
            fail(e.toString());
        }

        INITIALIZED = true;

    }

}
