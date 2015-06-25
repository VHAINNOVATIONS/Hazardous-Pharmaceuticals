/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderUnitDomainCapability;


/**
 * OrderUnitDomainCapabilityTest
 */
public class OrderUnitDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final String XYZ = "XYZ";
    
//    private OrderUnitDomainCapability localOrderUnitDomainCapability;
    private OrderUnitDomainCapability nationalOrderUnitDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localOrderUnitDomainCapability = getLocalOneCapability(OrderUnitDomainCapability.class);
        this.nationalOrderUnitDomainCapability = getNationalCapability(OrderUnitDomainCapability.class);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testFindAllOrderUnitLocal() throws Exception {
//
//        List<OrderUnitVo> nameCollection = localOrderUnitDomainCapability.retrieve();
//
//        for (OrderUnitVo order : nameCollection) {
//            assertTrue("statius is active", order.getItemStatus().name().equals("ACTIVE"));
//        }
//
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     */
    public void testFindAllOrderUnitNational() {

        List<OrderUnitVo> nameCollection = nationalOrderUnitDomainCapability.retrieve();

        for (OrderUnitVo order : nameCollection) {
            assertTrue("statius is active", order.getItemStatus().name().equals("ACTIVE"));
        }
    }

    /**
     * This method builds OrderUnitVo
     * 
     * @param name String
     * @return OrderUnitVo
     */
    private OrderUnitVo buildVo(String name) {
        OrderUnitVo dataVo = new OrderUnitVo();
        dataVo.setValue(name);
        dataVo.setAbbrev(name);
        dataVo.setExpansion("exp");
        dataVo.setInactivationDate(new Date());
        dataVo.setItemStatus(ItemStatus.INACTIVE);
        dataVo.setRequestItemStatus(RequestItemStatus.PENDING);
        dataVo.setRejectionReasonText("reason");
        dataVo.setRevisionNumber(PPSConstants.I3);

        return dataVo;
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
////    public void testCreateOrderUnitLocal() throws Exception {
//
//        OrderUnitVo dosageForm = buildVo("ae1");
//        OrderUnitVo returnedVo = localOrderUnitDomainCapability.create(dosageForm, getTestUser());
//        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testCreateOrderUnitNational() throws Exception {

        OrderUnitVo dosageForm = buildVo("ae5");

        OrderUnitVo returnedVo = nationalOrderUnitDomainCapability.create(dosageForm, getTestUser());
        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testUpdateOrderUnitLocal() throws Exception {
//
//        List<OrderUnitVo> names = localOrderUnitDomainCapability.retrieve();
//
//        names.get(0).setAbbrev(XYZ);
//
//        localOrderUnitDomainCapability.update(names.get(0), getTestUser());
//
//        OrderUnitVo retrievedUpdated = localOrderUnitDomainCapability.retrieve(names.get(0).getId());
//
//        assertEquals("Should be equal", retrievedUpdated.getAbbrev(), XYZ);
//
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testUpdateOrderUnitNational() throws Exception {

        List<OrderUnitVo> names = nationalOrderUnitDomainCapability.retrieve();

        names.get(0).setAbbrev(XYZ);

        nationalOrderUnitDomainCapability.update(names.get(0), getTestUser());

        OrderUnitVo retrievedUpdated = nationalOrderUnitDomainCapability.retrieve(names.get(0).getId());

        assertEquals("Should be equal", retrievedUpdated.getAbbrev(), XYZ);

    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testSearchOrderUnitLocal() throws Exception {
//        int initialCount = 0;
//        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);
//
//        SearchTermVo searchTerm = new SearchTermVo(EntityType.ORDER_UNIT, FieldKey.VALUE, "a");
//
//        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
//        itemStatus.add(ItemStatus.INACTIVE);
//        searchCriteria.setItemStatus(itemStatus);
//
//        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
//        requestStatus.add(RequestItemStatus.PENDING);
//        searchCriteria.setRequestStatus(requestStatus);
//
//        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
//        searchTerms.add(searchTerm);
//
//        searchCriteria.setSearchTerms(searchTerms);
//
//        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
//        searchCriteria.setSortOrder(SortOrder.ASCENDING);
//        searchCriteria.setStartRow(0);
//        searchCriteria.setPageSize(10);
//
//        List<OrderUnitVo> names;
//
//        names = localOrderUnitDomainCapability.search(searchCriteria);
//        initialCount = names.size();
//
//        OrderUnitVo dosageForm = buildVo("ae6");
//        localOrderUnitDomainCapability.create(dosageForm, getTestUser());
//        names = localOrderUnitDomainCapability.search(searchCriteria);
//
//        assertEquals("Should be one more than initial count", initialCount + 1, names.size());
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testSearchOrderUnitNational() throws Exception {
        int initialCount = 0;
        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);

        SearchTermVo searchTerm = new SearchTermVo(EntityType.ORDER_UNIT, FieldKey.VALUE, "a");

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.INACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.PENDING);
        searchCriteria.setRequestStatus(requestStatus);

        // set the searchTemrs for OrderUnitDomainCapabilityTest
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PPSConstants.I10);

        List<OrderUnitVo> names;

        names = nationalOrderUnitDomainCapability.search(searchCriteria);
        initialCount = names.size();

        OrderUnitVo dosageForm = buildVo("ae2");
        nationalOrderUnitDomainCapability.create(dosageForm, getTestUser());

        names = nationalOrderUnitDomainCapability.search(searchCriteria);

        assertEquals("Should be one more than initial count", initialCount + 1, names.size());
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testExistsOrderUnitNational() throws Exception {

        OrderUnitVo unit = buildVo("ae3");
        unit.setItemStatus(ItemStatus.ACTIVE);
        nationalOrderUnitDomainCapability.create(unit, getTestUser());
        boolean exists = nationalOrderUnitDomainCapability.existsByUniquenessFields(unit);
        assertTrue("exists", exists);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testExistsOrderUnitLocal() throws Exception {
//
//        OrderUnitVo unit = buildVo("ae4");
//        unit.setItemStatus(ItemStatus.ACTIVE);
//        localOrderUnitDomainCapability.create(unit, getTestUser());
//        boolean exists = localOrderUnitDomainCapability.existsByUniquenessFields(unit);
//        assertTrue("exists", exists);
//    }
}
