/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.domain.common.capability.DispenseUnitDomainCapability;


/**
 * DispenseUnitDomainCapabilityTest.
 */
public class DispenseUnitDomainCapabilityTest extends DomainCapabilityTestCase {
    
    private static final String IEN = "34534";
    private static final Long REV = 3L;
    private static final int PAGE_SIZE = 10;
    
//    private DispenseUnitDomainCapability localDispenseUnitDomainCapability;
    private DispenseUnitDomainCapability nationalDispenseUnitDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
        this.nationalDispenseUnitDomainCapability = getNationalCapability(DispenseUnitDomainCapability.class);
    }


    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllDispenseUnitNational() throws Exception {

        List<DispenseUnitVo> rCollection = nationalDispenseUnitDomainCapability.retrieve();
        DispenseUnitVo dataVo = buildVo("nmae2");
        nationalDispenseUnitDomainCapability.create(dataVo, getTestUser());

        assertTrue("Collection returned correct number", rCollection.size() + 1 > rCollection.size());
    }

    /**
     * This method buidlsVO
     * 
     * @param name String
     * @return VaDispenseUnitVo
     */
    private DispenseUnitVo buildVo(String name) {
        DispenseUnitVo dataVo = new DispenseUnitVo();
        dataVo.setValue(name);
        dataVo.setInactivationDate(new Date());
        dataVo.setItemStatus(ItemStatus.INACTIVE);
        dataVo.setDispenseUnitIen(IEN);
        dataVo.setRequestItemStatus(RequestItemStatus.PENDING);
        dataVo.setRejectionReasonText("rjrected");
        dataVo.setRevisionNumber(REV);

        return dataVo;
    }


    /**
     * This testCreateDispenseUnitNational method tests the creation of a DispenseUnit
     * 
     * @throws Exception Exception
     */
    public void testCreateDispenseUnitNational() throws Exception {

        DispenseUnitVo dispenseUnit = buildVo("name6");
        DispenseUnitVo returnedVo = nationalDispenseUnitDomainCapability.create(dispenseUnit, getTestUser());
        assertNotNull("Returned with id", returnedVo.getId());
        assertEquals("IEN should match", dispenseUnit.getDispenseUnitIen(), returnedVo.getDispenseUnitIen());
    }


    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testUpdateDispenseUnitNational() throws Exception {

        List<DispenseUnitVo> names = nationalDispenseUnitDomainCapability.retrieve();

        names.get(0).setRejectionReasonText(PPSConstants.TEST_REASON_TEXT);

        nationalDispenseUnitDomainCapability.update(names.get(0), getTestUser());

        DispenseUnitVo retrievedUpdated = nationalDispenseUnitDomainCapability.retrieve(names.get(0).getId());

        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), PPSConstants.TEST_REASON_TEXT);

    }


    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testSearchDispenseUnitNational() throws Exception {

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);

        SearchTermVo searchTerm = new SearchTermVo();
        searchTerm.setSearchField(new SearchFieldVo(FieldKey.VALUE, EntityType.DISPENSE_UNIT));
        searchTerm.setValue("name");

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.INACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.PENDING);
        searchCriteria.setRequestStatus(requestStatus);

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PAGE_SIZE);

        List<DispenseUnitVo> names = nationalDispenseUnitDomainCapability.search(searchCriteria);

        assertEquals("Returned data", 1, names.size());

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testExistsDispenseUnitNational() throws Exception {

        DispenseUnitVo unit = buildVo("name3");
        unit.setItemStatus(ItemStatus.ACTIVE);
        nationalDispenseUnitDomainCapability.create(unit, getTestUser());
        boolean exists = nationalDispenseUnitDomainCapability.existsByUniquenessFields(unit);
        assertTrue("exists", exists);
    }

}
