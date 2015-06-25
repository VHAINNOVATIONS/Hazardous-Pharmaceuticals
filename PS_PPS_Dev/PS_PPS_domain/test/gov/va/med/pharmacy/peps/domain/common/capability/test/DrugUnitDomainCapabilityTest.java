/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
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
import gov.va.med.pharmacy.peps.domain.common.capability.DrugUnitDomainCapability;


/**
 * DrugUnitDomainCapabilityTest.
 */
public class DrugUnitDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final String IEN = "34534";
    private static final Long REV = 3L;
    private static final int PAGE_SIZE = 10;
    
    private DrugUnitDomainCapability nationalDrugUnitDomainCapability;
    

       

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localDrugUnitDomainCapability = getLocalOneCapability(DrugUnitDomainCapability.class);
        this.nationalDrugUnitDomainCapability = getNationalCapability(DrugUnitDomainCapability.class);
    }


    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllDrugUnitNational() throws Exception {
        int originalCount = 0;
        List<DrugUnitVo> rCollection;

        rCollection = nationalDrugUnitDomainCapability.retrieve();
        originalCount = rCollection.size();

        DrugUnitVo dataVo = buildVo("name2");
        dataVo.setItemStatus(ItemStatus.ACTIVE);
        nationalDrugUnitDomainCapability.create(dataVo, getTestUser());
        rCollection = nationalDrugUnitDomainCapability.retrieve();

        assertEquals("Collection returned correct number", originalCount + 1, rCollection.size());
    }

    /**
     * This method buidlsVO
     * @param name String 
     * @return UnitVo
     */
    private DrugUnitVo buildVo(String name) {

        DrugUnitVo dataVo = new DrugUnitVo();
        dataVo.setValue(name);
        dataVo.setDrugUnitIen(IEN);
        dataVo.setInactivationDate(new java.util.Date());
        dataVo.setItemStatus(ItemStatus.INACTIVE);
        dataVo.setRequestItemStatus(RequestItemStatus.PENDING);
        dataVo.setRejectionReasonText("test");
        dataVo.setRevisionNumber(REV);

        return dataVo;
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testCreateDrugUnitLocal() throws Exception {
//
//        DrugUnitVo dataVo = buildVo("name11");
//        DrugUnitVo returnedVo = localDrugUnitDomainCapability.create(dataVo, getTestUser());
//        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testCreateDrugUnitNational() throws Exception {

        DrugUnitVo dataVo = buildVo("name3");
        DrugUnitVo returnedVo = nationalDrugUnitDomainCapability.create(dataVo, getTestUser());
        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
        assertEquals("IEN submitted not equal IEN returned", dataVo.getDrugUnitIen(), returnedVo.getDrugUnitIen());
    }


    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testUpdateDrugUnitNational() throws Exception {

        List<DrugUnitVo> names = nationalDrugUnitDomainCapability.retrieve();

        names.get(0).setRejectionReasonText(PPSConstants.TEST_REASON_TEXT);

        nationalDrugUnitDomainCapability.update(names.get(0), getTestUser());

        DrugUnitVo retrievedUpdated = nationalDrugUnitDomainCapability.retrieve(names.get(0).getId());

        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), PPSConstants.TEST_REASON_TEXT);

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testSearchDrugUnitNational() throws Exception {

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);

        SearchTermVo searchTerm = new SearchTermVo();
        searchTerm.setSearchField(new SearchFieldVo(FieldKey.VALUE, EntityType.DRUG_UNIT));
        searchTerm.setValue("a");

        // set the Item Status for testSearchDrugUnitNational
        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.INACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.PENDING);
        searchCriteria.setRequestStatus(requestStatus);

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        // set the search terms for testSearchDrugUnitNational
        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PAGE_SIZE);

        List<DrugUnitVo> names = nationalDrugUnitDomainCapability.search(searchCriteria);

        assertFalse("Returned data", names.isEmpty());

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testExistsDrugUnitNational() throws Exception {

        DrugUnitVo unit = buildVo("namd11");
        unit.setItemStatus(ItemStatus.ACTIVE);
        nationalDrugUnitDomainCapability.create(unit, getTestUser());
        boolean exists = nationalDrugUnitDomainCapability.existsByUniquenessFields(unit);
        assertTrue("exists", exists);
    }


}
