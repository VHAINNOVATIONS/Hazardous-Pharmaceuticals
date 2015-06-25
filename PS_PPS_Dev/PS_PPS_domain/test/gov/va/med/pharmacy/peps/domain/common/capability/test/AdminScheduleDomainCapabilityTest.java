/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleTypeVo;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.HospitalLocationSelectionVo;
import gov.va.med.pharmacy.peps.common.vo.HospitalLocationVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.WardMultipleVo;
import gov.va.med.pharmacy.peps.common.vo.WardSelectionVo;
import gov.va.med.pharmacy.peps.domain.common.capability.AdministrationScheduleDomainCapability;


/**
 * AdminScheduleDomainCapabilityTest.
 */
public class AdminScheduleDomainCapabilityTest extends DomainCapabilityTestCase {
    
    private static final String REASON_TEXT = "updatedRejectREasonTExt";
    
//    private AdministrationScheduleDomainCapability localadminScheduleDomainCapability;
    private AdministrationScheduleDomainCapability nationaladminScheduleDomainCapability;

    /**
     * This method buidlsVO
     * 
     * @param name String defines uniqueness
     * @return AdministrationScheduleVo
     */
    private AdministrationScheduleVo buildVo(String name) {

        AdministrationScheduleVo adminSchedule = new AdministrationScheduleVo();
        adminSchedule.setInactivationDate(new Date());
        adminSchedule.setItemStatus(ItemStatus.INACTIVE);
        adminSchedule.setRequestItemStatus(RequestItemStatus.PENDING);
        adminSchedule.setRejectionReasonText("rejected");

        AdministrationScheduleTypeVo sched = new AdministrationScheduleTypeVo();
        sched.setId("1");

        adminSchedule.setAdminScheduleType(sched);
        adminSchedule.setFrequency(new Long("99"));

        adminSchedule.setOtherLanguageExpansion("other");
        adminSchedule.setPackagePrefix("pre");
        adminSchedule.setValue(name);
        adminSchedule.setScheduleOutpatientExpansion("outpat");
        adminSchedule.setRevisionNumber(new Long("23"));
        adminSchedule.setStandardAdministrationTimes("admin");
        adminSchedule.setStandardShifts("shift");

        WardMultipleVo ward = new WardMultipleVo();
        WardSelectionVo wardSelection = new WardSelectionVo();
        wardSelection.setValue("HOSPITAL");

        ward.setWardSelection(wardSelection);
        ward.setWardAdminTimes("0200-0600-1000-1400-1800-2200");

        Collection<WardMultipleVo> wards = new ArrayList<WardMultipleVo>();
        wards.add(ward);
        adminSchedule.setWardMultiple(wards);

        HospitalLocationVo hosp = new HospitalLocationVo();

        HospitalLocationSelectionVo locSel = new HospitalLocationSelectionVo();
        locSel.setValue("MIKES MENTAL CLINIC");

        hosp.setHospitalLocationSelection(locSel);
        hosp.setAdministrationTimes("30");
        hosp.setShifts("50");
        Collection<HospitalLocationVo> hospitalLocationMultiple = new ArrayList<HospitalLocationVo>();
        hospitalLocationMultiple.add(hosp);
        adminSchedule.setHospitalLocationMultiple(hospitalLocationMultiple);

        return adminSchedule;
    }

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        //        this.localadminScheduleDomainCapability = getLocalOneCapability(AdministrationScheduleDomainCapability.class);
        this.nationaladminScheduleDomainCapability = getNationalCapability(AdministrationScheduleDomainCapability.class);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testFindAllAdminLocal() throws Exception {
//
//        List<AdministrationScheduleVo> rCollection = localadminScheduleDomainCapability.retrieve();
//
//        AdministrationScheduleVo dataVo = buildVo("name1");
//        localadminScheduleDomainCapability.create(dataVo, getTestUser());
//
//        assertTrue("Collection returned correct number", rCollection.size() + 1 > rCollection.size());
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testFindAllAdminNational() throws PharmacyException {

        List<AdministrationScheduleVo> rCollection = nationaladminScheduleDomainCapability.retrieve();
        AdministrationScheduleVo dataVo = buildVo("name2");
        nationaladminScheduleDomainCapability.create(dataVo, getTestUser());

        assertTrue("Collection returned correct number", rCollection.size() + 1 > rCollection.size());
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveAdminScheduleLocal() throws Exception {
//        AdministrationScheduleVo name = localadminScheduleDomainCapability.retrieve("9995");
//
//        System.out.println(name.getWardMultiple());
//        System.out.println(name.getHospitalLocationMultiple());
//        assertNotNull("Returned with ward", name.getWardMultiple().size());
//        assertNotNull("Returned with hospitals", name.getHospitalLocationMultiple().size());
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testCreateAdminScheduleLocal() throws Exception {
//        AdministrationScheduleVo adminSchedule = buildVo("name3");
//        AdministrationScheduleVo returnedVo = localadminScheduleDomainCapability.create(adminSchedule, getTestUser());
//        assertNotNull("Returned with id", returnedVo.getId());
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testCreateAdminScheduleNational() throws PharmacyException {

        AdministrationScheduleVo adminSchedule = buildVo("name4");
        AdministrationScheduleVo returnedVo = nationaladminScheduleDomainCapability.create(adminSchedule, getTestUser());
        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testUpdateAdminScheduleLocal() throws Exception {
//        List<AdministrationScheduleVo> names = localadminScheduleDomainCapability.retrieve();
//
//        names.get(0).setRejectionReasonText(REASON_TEXT);
//        localadminScheduleDomainCapability.update(names.get(0), getTestUser());
//
//        AdministrationScheduleVo retrievedUpdated = localadminScheduleDomainCapability.retrieve(names.get(0).getId());
//
//        assertEquals("Should be equal", REASON_TEXT, retrievedUpdated.getRejectionReasonText());
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testUpdateAdminScheduleWardLocal() throws Exception {
//        AdministrationScheduleVo name = localadminScheduleDomainCapability.retrieve("9992");
//
//        int wardSize = name.getWardMultiple().size();
//
//        // 9992 has only one ward
//        // add another ward
//        WardMultipleVo ward = new WardMultipleVo();
//
//        WardSelectionVo wardSelection = new WardSelectionVo();
//        wardSelection.setValue("NEWWARD");
//
//        ward.setWardSelection(wardSelection);
//        ward.setWardAdminTimes("0500-1100-1700-2300");
//        name.getWardMultiple().add(ward);
//        AdministrationScheduleVo returnedName = localadminScheduleDomainCapability.update(name, getTestUser());
//
//        System.out.println(returnedName.getWardMultiple());
//        assertEquals("Should be equal", returnedName.getWardMultiple().size(), wardSize + 1);
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testUpdateAdminScheduleNational() throws PharmacyException {
        List<AdministrationScheduleVo> names = nationaladminScheduleDomainCapability.retrieve();

        names.get(0).setRejectionReasonText(REASON_TEXT);
        names.get(0).setPackagePrefix("PSJI");

        nationaladminScheduleDomainCapability.update(names.get(0), getTestUser());

        AdministrationScheduleVo retrievedUpdated = nationaladminScheduleDomainCapability.retrieve(names.get(0).getId());

        assertEquals("Should be equal", REASON_TEXT, retrievedUpdated.getRejectionReasonText());

    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
////    public void testCreateShouldSaveRejectReasonText() throws Exception {
//        AdministrationScheduleVo adminSchedule = buildVo("name5");
//        adminSchedule.setRejectionReasonText("TheText");
//        AdministrationScheduleVo returnedVo = localadminScheduleDomainCapability.create(adminSchedule, getTestUser());
//        assertEquals("should match", "TheText", returnedVo.getRejectionReasonText());
//    }

//    /**
//     * Test
//     * 
//     * @throws Exception
//     */
//    public void testRetrievedSchedulesShouldNeverHaveNullIds() throws Exception {
//
//        List<AdministrationScheduleVo> names = localadminScheduleDomainCapability.retrieve();
//
//        for (AdministrationScheduleVo schedule : names) {
//            assertTrue("IDs should not be null after retrieve", schedule.getId() != null);
//        }
//    }

//    

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testSearchAdminScheduleNational() throws PharmacyException {
        int originalCount = 0;
        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);

        SearchTermVo searchTerm = new SearchTermVo();
        searchTerm.setSearchField(new SearchFieldVo(FieldKey.VALUE, EntityType.ADMINISTRATION_SCHEDULE));
        searchTerm.setValue("name");

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.INACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.PENDING);
        searchCriteria.setRequestStatus(requestStatus);

        // AdminScheduleDomainCapability set search terms.
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PPSConstants.I10);

        List<AdministrationScheduleVo> names;

        names = nationaladminScheduleDomainCapability.search(searchCriteria);
        originalCount = names.size();

        // add another
        AdministrationScheduleVo adminSchedule = buildVo("name7");
        nationaladminScheduleDomainCapability.create(adminSchedule, getTestUser());

        names = nationaladminScheduleDomainCapability.search(searchCriteria);

        assertEquals("Returned data", originalCount + 1, names.size());
    }

    /**
     * testRevisionNumber
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testRevisionNumber() throws PharmacyException {
        String id = "9991";
        long revNumber = nationaladminScheduleDomainCapability.getRevisionNumber(id);

        assertNotNull("Version number not returned", revNumber);
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testExistsAdminScheduleNational() throws PharmacyException {

        AdministrationScheduleVo adminSchedule = buildVo("name8");
        adminSchedule.setItemStatus(ItemStatus.ACTIVE);
        nationaladminScheduleDomainCapability.create(adminSchedule, getTestUser());
        boolean exists = nationaladminScheduleDomainCapability.existsByUniquenessFields(adminSchedule);
        assertTrue("exists", exists);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
////    public void testExistsAdminScheduleLocal() throws Exception {
//
//        AdministrationScheduleVo adminSchedule = buildVo("name9");
//        adminSchedule.setItemStatus(ItemStatus.ACTIVE);
//        localadminScheduleDomainCapability.create(adminSchedule, getTestUser());
//        boolean exists = localadminScheduleDomainCapability.existsByUniquenessFields(adminSchedule);
//        assertTrue("exists", exists);
//    }

}
