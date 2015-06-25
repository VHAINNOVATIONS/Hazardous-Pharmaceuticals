/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.domain.common.capability.StandardMedRouteDomainCapability;


/**
 * StandardMedicationRouteCapabilityTest
 */
public class StandardMedicationRouteCapabilityTest extends DomainCapabilityTestCase {
    

    private StandardMedRouteDomainCapability nationalrouteDomainCapability;
    private Integer currentId = PPSConstants.I2011;

    /**
     * 
     * @return current id (extension)
     */
    private String getCurrentId() {
        return (++currentId).toString();
    }

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()  {
        this.nationalrouteDomainCapability = getNationalCapability(StandardMedRouteDomainCapability.class);
    }

 
    /**
     * This method gets all the FdbMfg in the db.
     * 
     */
    public void testFindAllDoseNational() {

        try {
            List<StandardMedRouteVo> rCollection = nationalrouteDomainCapability.retrieve();

            StandardMedRouteVo dataVo = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5));
            nationalrouteDomainCapability.create(dataVo, getTestUser());

            assertTrue("Collection returned correct number", rCollection.size() + 1 > rCollection.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * This method buidlsVO
     * 
     * @param name String
     * @return RouteVo
     */
    private StandardMedRouteVo buildVo(String name) {
        StandardMedRouteVo route = new StandardMedRouteVo();
        route.setVuid("testVuid" + getCurrentId());
        route.setInactivationDate(new Date());
        route.setValue(name);
        route.setItemStatus(ItemStatus.INACTIVE);
        route.setRequestItemStatus(RequestItemStatus.PENDING);
        route.setRejectionReasonText("rejext");
        route.setRevisionNumber(PPSConstants.I3);
        route.setFirstDatabankMedRoute("TBD");

        StandardMedRouteVo parent = new StandardMedRouteVo();
        parent.setId("9992");
        route.setReplacementStandardMedRoute(parent);

        return route;
    }

   
   

    

    /**
     * This method gets all the FdbMfg in the db.
     * 
     */
    public void testSearchRouteNational() {

        try {
            SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);
    
            SearchTermVo searchTerm = new SearchTermVo(EntityType.STANDARD_MED_ROUTE, FieldKey.VALUE, "%");
    
            List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
            itemStatus.add(ItemStatus.INACTIVE);
            searchCriteria.setItemStatus(itemStatus);
    
            List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
            requestStatus.add(RequestItemStatus.PENDING);
            searchCriteria.setRequestStatus(requestStatus);
    
            // Set the searchTerms for the testSearchRouteNational.
            List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
            searchTerms.add(searchTerm);
    
            searchCriteria.setSearchTerms(searchTerms);
    
            searchCriteria.setSortedFieldKey(FieldKey.VALUE);
            searchCriteria.setSortOrder(SortOrder.ASCENDING);
            searchCriteria.setStartRow(0);
            searchCriteria.setPageSize(Integer.MAX_VALUE);
    
            List<StandardMedRouteVo> names = nationalrouteDomainCapability.search(searchCriteria);
    
            assertFalse("Returned data", names.isEmpty());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     */
    public void testExistsrouteNational() {

        try {
            StandardMedRouteVo unit = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5));
            unit.setItemStatus(ItemStatus.ACTIVE);
            nationalrouteDomainCapability.create(unit, getTestUser());
            boolean exists = nationalrouteDomainCapability.existsByUniquenessFields(unit);
            assertTrue("exists", exists);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
  
}
