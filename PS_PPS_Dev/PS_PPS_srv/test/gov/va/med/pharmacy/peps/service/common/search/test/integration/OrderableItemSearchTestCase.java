/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.search.test.integration;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;


/**
 * Tests the orderable item search
 */
public abstract class OrderableItemSearchTestCase extends ManagedItemSearchTestCase {

    private static boolean INITIALIZED = false;

    private OrderableItemVo orderableItemVo;

    /**
     * Constructor
     * 
     * @param name String
     */
    public OrderableItemSearchTestCase(String name) {
        super(name);
    }

    /**
     * Sets up the test fixtures
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        super.setUp();
        getSearchCriteria().setEntityType(EntityType.ORDERABLE_ITEM);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.PENDING);

        getSearchCriteria().setRequestStatus(requestStatus);

        if (!INITIALIZED) {
            try {
                createOIData();
            } catch (Exception e) {
                fail(e.toString());
            }
        }
    }

    /**
     * Initializes the database
     * 
     */
    private void createOIData() {
        try {
            orderableItemVo = (OrderableItemVo) getManagedItemService().retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);
            initializeOrderableItemVo();
            getManagedItemService().create(orderableItemVo, getUser());
        } catch (DuplicateItemException ex) {
            fail("CreateOiDate shold not throw Exception!");
        } catch (ValidationException e) {
            fail(e.toString());
        }

        INITIALIZED = true;
    }

    /**
     * Utility method that creates a stubbed orderable item object for testing.
     * 
     */
    public void initializeOrderableItemVo() {
        orderableItemVo.setOiName(getTermFixture().getString("oi_name"));
        orderableItemVo.setLocal();
        orderableItemVo.setVistaOiName("vistaOi");

        DosageFormVo dosageForm = new DosageFormVo();
        dosageForm.setId("9994");
        dosageForm.setDosageFormName("TAB,SA");
        orderableItemVo.setDosageForm(dosageForm);

        OrderableItemVo parent = new OrderableItemVo();
        parent.setId("9993");
        orderableItemVo.setOrderableItemParent(parent);

    }

}
