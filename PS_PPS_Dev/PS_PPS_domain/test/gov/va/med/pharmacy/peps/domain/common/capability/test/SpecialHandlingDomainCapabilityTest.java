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
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.domain.common.capability.SpecialHandlingDomainCapability;


/**
 * SpecialHandlingDomainCapabilityTest
 */
public class SpecialHandlingDomainCapabilityTest extends DomainCapabilityTestCase {

    //    private SpecialHandlingDomainCapability localSpecialHandlingDomainCapability;
    private SpecialHandlingDomainCapability nationalSpecialHandlingDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localSpecialHandlingDomainCapability = getLocalOneCapability(SpecialHandlingDomainCapability.class);
        this.nationalSpecialHandlingDomainCapability = getNationalCapability(SpecialHandlingDomainCapability.class);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testFindAllSpecialHandlingLocal() throws Exception {
//
//        List<SpecialHandlingVo> nameCollection = localSpecialHandlingDomainCapability.retrieve();
//
//        for (SpecialHandlingVo order : nameCollection) {
//            assertTrue("statius is active", order.getItemStatus().name().equals("ACTIVE"));
//        }
//
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllSpecialHandlingNational() throws Exception {

        List<SpecialHandlingVo> nameCollection = nationalSpecialHandlingDomainCapability.retrieve();

        for (SpecialHandlingVo order : nameCollection) {
            assertTrue("statius is active", order.getItemStatus().name().equals("ACTIVE"));
        }
    }

    /**
     * This method builds SpecialHandlingVo
     * 
     * @param code String
     * @return SpecialHandlingVo
     */
    private SpecialHandlingVo buildVo(String code) {
        SpecialHandlingVo dataVo = new SpecialHandlingVo();
        dataVo.setValue(code);
        dataVo.setCode(code);
        dataVo.setDescription("exp");
        dataVo.setInactivationDate(new Date());
        dataVo.setItemStatus(ItemStatus.INACTIVE);
        dataVo.setRequestItemStatus(RequestItemStatus.PENDING);
        dataVo.setRejectionReasonText("reason");
        dataVo.setRevisionNumber(PPSConstants.I3);

        return dataVo;
    }

    /**
     * This testCreateSpecialHandlingNational method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testCreateSpecialHandlingNational() throws Exception {

        SpecialHandlingVo dosageForm = buildVo("G");

        SpecialHandlingVo returnedVo = nationalSpecialHandlingDomainCapability.create(dosageForm, getTestUser());
        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testUpdateSpecialHandlingLocal() throws Exception {
//
//        List<SpecialHandlingVo> names = localSpecialHandlingDomainCapability.retrieve();
//
//        names.get(0).setDescription("XYZ");
//
//        localSpecialHandlingDomainCapability.update(names.get(0), getTestUser());
//
//        SpecialHandlingVo retrievedUpdated = localSpecialHandlingDomainCapability.retrieve(names.get(0).getId());
//
//        assertEquals("Should be equal", retrievedUpdated.getDescription(), "XYZ");
//
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testUpdateSpecialHandlingNational() throws Exception {

        List<SpecialHandlingVo> names = nationalSpecialHandlingDomainCapability.retrieve();
        String xyz = "XYZ";
        names.get(0).setDescription(xyz);

        nationalSpecialHandlingDomainCapability.update(names.get(0), getTestUser());

        SpecialHandlingVo retrievedUpdated = nationalSpecialHandlingDomainCapability.retrieve(names.get(0).getId());

        assertEquals("Should be equal", retrievedUpdated.getDescription(), xyz);

    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testSearchSpecialHandlingLocal() throws Exception {
//
//        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);
//        searchCriteria.setEntityType(EntityType.SPECIAL_HANDLING);
//
//        SearchTermVo searchTerm = new SearchTermVo(EntityType.SPECIAL_HANDLING, FieldKey.VALUE, "A");
//
//        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
//        itemStatus.add(ItemStatus.ACTIVE);
//        searchCriteria.setItemStatus(itemStatus);
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
//        List<SpecialHandlingVo> names;
//
//        names = localSpecialHandlingDomainCapability.search(searchCriteria);
//
//        assertTrue("Count greater than 0", names.size() > 0);
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testSearchSpecialHandlingNational() throws Exception {

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);
        searchCriteria.setEntityType(EntityType.SPECIAL_HANDLING);

        SearchTermVo searchTerm = new SearchTermVo(EntityType.SPECIAL_HANDLING, FieldKey.VALUE, "W");

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PPSConstants.I10);

        List<SpecialHandlingVo> names;

        names = nationalSpecialHandlingDomainCapability.search(searchCriteria);
        assertTrue("Count greater than 0", names.size() > 0);
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testExistsSpecialHandlingNational() throws Exception {

        SpecialHandlingVo unit = buildVo("T");
        unit.setItemStatus(ItemStatus.ACTIVE);
        nationalSpecialHandlingDomainCapability.create(unit, getTestUser());
        boolean exists = nationalSpecialHandlingDomainCapability.existsByUniquenessFields(unit);
        assertTrue("exists", exists);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testExistsSpecialHandlingLocal() throws Exception {
//
//        SpecialHandlingVo unit = buildVo("T");
//        unit.setItemStatus(ItemStatus.ACTIVE);
//        localSpecialHandlingDomainCapability.create(unit, getTestUser());
//        boolean exists = localSpecialHandlingDomainCapability.existsByUniquenessFields(unit);
//        assertTrue("exists", exists);
//    }
}
