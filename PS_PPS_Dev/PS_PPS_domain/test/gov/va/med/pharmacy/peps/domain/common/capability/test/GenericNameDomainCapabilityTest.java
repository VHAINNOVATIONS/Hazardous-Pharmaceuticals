/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.capability.GenericNameDomainCapability;


/**
 * GenericNameDomainCapabilityTest.
 */
public class GenericNameDomainCapabilityTest extends DomainCapabilityTestCase {
    private static final String REASON_TEXT = "updatedRejectREasonTExt";
    private GenericNameDomainCapability nationalgenericNameDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        this.nationalgenericNameDomainCapability = getNationalCapability(GenericNameDomainCapability.class);
    }

   

    /**
     * This method gets all the FdbMfg in the db.
     * @throws PharmacyException PharmacyException

     */
    public void testFindAllGenericNameNational() throws PharmacyException {
        int originalCount = 0;
        List<GenericNameVo> rCollection;

        rCollection = nationalgenericNameDomainCapability.retrieve();
        originalCount = rCollection.size();

        GenericNameVo dataVo = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        dataVo.setItemStatus(ItemStatus.ACTIVE);
        nationalgenericNameDomainCapability.create(dataVo, getTestUser());
        rCollection = nationalgenericNameDomainCapability.retrieve();

        assertEquals("Collection returned correct number", originalCount + 1, rCollection.size());
    }

    /**
     * This method buidlsVO
     * 
     * @param name String
     * @return GenericNameVo
     */
    private GenericNameVo buildVo(String name) {
        GenericNameVo dataVo = new GenericNameVo();
        dataVo.setVuid("testDispVuid");
        dataVo.setValue(name);
        dataVo.setGenericIen(String.valueOf(RandomGenerator.nextInt()));
        dataVo.setMasterEntryForVuid(true);
        dataVo.setInactivationDate(new Date());
        dataVo.setItemStatus(ItemStatus.INACTIVE);
        dataVo.setRequestItemStatus(RequestItemStatus.PENDING);
        dataVo.setRejectionReasonText("reason");
        dataVo.setRevisionNumber(PPSConstants.I3);

        VuidStatusHistoryVo statusVo = new VuidStatusHistoryVo();
        statusVo.setVuid(new Long("23423"));
        statusVo.setEffectiveDtmStatus(ItemStatus.ACTIVE);
        statusVo.setEffectiveDateTime(new Date());
        List<VuidStatusHistoryVo> list = new ArrayList<VuidStatusHistoryVo>();
        list.add(statusVo);
        dataVo.setEffectiveDates(list);
        
        return dataVo;
    }

  

    /**
     * This method gets all the FdbMfg in the db.
     * @throws PharmacyException PharmacyException 
     */
    public void testCreateGenericNameNational() throws PharmacyException {

        GenericNameVo dataVo = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I10));

        GenericNameVo returnedVo = nationalgenericNameDomainCapability.create(dataVo, getTestUser());
        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
    }


    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException 
     */
    public void testUpdategenericNameNational() throws PharmacyException {

        List<GenericNameVo> names = nationalgenericNameDomainCapability.retrieve();
        GenericNameVo name = nationalgenericNameDomainCapability.retrieve(names.get(0).getId());

        name.setRejectionReasonText(REASON_TEXT);

        nationalgenericNameDomainCapability.update(name, getTestUser());

        GenericNameVo retrievedUpdated = nationalgenericNameDomainCapability.retrieve(name.getId());

        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), REASON_TEXT);

    }


    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testSearchGenericNameNational() throws PharmacyException {

        int intialCount = 0;
        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);

        SearchTermVo searchTerm = new SearchTermVo(EntityType.GENERIC_NAME, FieldKey.VALUE, "%");

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.INACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        // testSearchGenericNameNatonal
        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.PENDING);
        searchCriteria.setRequestStatus(requestStatus);

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        // testSearchGenericNameNatonal
        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(Integer.MAX_VALUE);

        List<GenericNameVo> names = nationalgenericNameDomainCapability.search(searchCriteria);
        intialCount = names.size();

        GenericNameVo genericName = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        nationalgenericNameDomainCapability.create(genericName, getTestUser());

        names = nationalgenericNameDomainCapability.search(searchCriteria);
        assertEquals("Returned data", intialCount + 1, names.size());
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException  
     */
    public void testExistsgenericNameNational() throws PharmacyException {

        GenericNameVo unit = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        unit.setItemStatus(ItemStatus.ACTIVE);
        nationalgenericNameDomainCapability.create(unit, getTestUser());
        boolean exists = nationalgenericNameDomainCapability.existsByUniquenessFields(unit);
        assertTrue("exists", exists);
    }

}
