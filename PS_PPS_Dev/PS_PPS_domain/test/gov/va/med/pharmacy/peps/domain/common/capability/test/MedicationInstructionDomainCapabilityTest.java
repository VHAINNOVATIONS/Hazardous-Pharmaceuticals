/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;




/**
 * MedicationInstructionDomainCapabilityTest
 */
public class MedicationInstructionDomainCapabilityTest extends DomainCapabilityTestCase {
    
    private boolean isTrue = true;
    
//    private MedicationInstructionDomainCapability localMedicationInstructionDomainCapability;
//    private MedicationInstructionDomainCapability nationalMedicationInstructionDomainCapability;
    

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

    }
    
    /**
     * This test case only should exist at Local
     */
    public void testMockTest() {
        assertTrue("No tests at National.", isTrue);
    }
    
//
////    /**
////     * This method gets all the FdbMfg in the db.
////     * 
////     * @throws Exception
////     */
////    public void testFindAllMedicationInstructionLocal() throws Exception {
////        int originalCount = 0;
////        List<MedicationInstructionVo> nameCollection;
////
////        nameCollection = localMedicationInstructionDomainCapability.retrieve();
////
////        if (nameCollection != null) {
////            originalCount = nameCollection.size();
////        }
////
////        List<LocalMedicationRouteVo> routes = localMedicationRouteDomainCapability.retrieve();
////        MedicationInstructionVo dataVo = buildVo(routes.get(0));
////        dataVo.setItemStatus(ItemStatus.ACTIVE);
////        localMedicationInstructionDomainCapability.create(dataVo, getTestUser());
////
////        nameCollection = localMedicationInstructionDomainCapability.retrieve();
////
////        assertEquals("Collection returned correct number", originalCount + 1, nameCollection.size());
////    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testFindAllMedicationInstructionNational() throws Exception {
//        int originalCount = 0;
//        List<MedicationInstructionVo> rCollection;
//
//        rCollection = nationalMedicationInstructionDomainCapability.retrieve();
//        originalCount = rCollection.size();
//
//        List<LocalMedicationRouteVo> routes = nationalLocalMedcationRouteDomainCapablity.retrieve();
//        MedicationInstructionVo dataVo = buildVo(routes.get(0));
//        dataVo.setItemStatus(ItemStatus.ACTIVE);
//        nationalMedicationInstructionDomainCapability.create(dataVo, getTestUser());
//        rCollection = nationalMedicationInstructionDomainCapability.retrieve();
//
//        assertEquals("Collection returned correct number", originalCount + 1, rCollection.size());
//    }
//
//    /**
//     * This method builds a medication instruction vo
//     * 
//     * @param route LocalMedicationRouteVo
//     * @return MedicationInstructionVo
//     */
//    private MedicationInstructionVo buildVo(LocalMedicationRouteVo route) {
//        return buildVo(route, UUID.randomUUID().toString());
//    }
//
//    /**
//     * 
//     * @param route LocalMedicationRouteVo
//     * @param name String
//     * @return MedicationInstructionVo
//     */
//    private MedicationInstructionVo buildVo(LocalMedicationRouteVo route, String name) {
//
//        MedicationInstructionVo objectVo = new MedicationInstructionVo();
//        objectVo.setValue(name);
//        objectVo.setInactivationDate(new Date());
//        objectVo.setItemStatus(ItemStatus.INACTIVE);
//        objectVo.setRequestItemStatus(RequestItemStatus.PENDING);
//        objectVo.setRejectionReasonText("rejected");
//        objectVo.setAdditionalInstruction("addInf");
//        objectVo.setDefaultAdminTimes("default");
//        objectVo.setFrequency(4L);
//        objectVo.setInstructions("instructions");
//        objectVo.setMedInstructionExpansion("exp");
//        objectVo.setRequestRejectionReason(RequestRejectionReason.INCOMPLETE_INFORMATION);
//
//        Collection<PossibleIntendedUseVo> use = new ArrayList<PossibleIntendedUseVo>();
//        PossibleIntendedUseVo te = new PossibleIntendedUseVo();
//        te.setValue("INPATIEN");
//        use.add(te);
//
//        objectVo.setIntendedUse(use);
//        objectVo.setOtherLanguageExpansion("other");
//        objectVo.setMedInstructionSchedule("sch");
//        objectVo.setMedInstructionSynonym("sn");
//        objectVo.setPlural("pliral");
//
//        objectVo.setRevisionNumber(3);
//
//        WardMultipleVo ward = new WardMultipleVo();
//
//        WardSelectionVo wardSelection = new WardSelectionVo();
//        wardSelection.setValue("MYNEW");
//
//        ward.setWardSelection(wardSelection);
//        ward.setWardAdminTimes("ADMIN");
//
//        Collection<WardMultipleVo> wards = new ArrayList<WardMultipleVo>();
//        wards.add(ward);
//        objectVo.setMedInstructionWardMultiple(wards);
//
//        objectVo.setLocalMedicationRoute(route);
//
//        return objectVo;
//    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
////    public void testCreateMedicationInstructionLocal() throws Exception {
////        List<LocalMedicationRouteVo> routes = localMedicationRouteDomainCapability.retrieve();
////
////        MedicationInstructionVo medINt = buildVo(routes.get(0));
////        MedicationInstructionVo returnedVo = localMedicationInstructionDomainCapability.create(medINt, getTestUser());
////        assertNotNull("Returned  with id", returnedVo.getId());
////    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testCreateMedicationInstructionNational() throws Exception {
//        List<LocalMedicationRouteVo> routes = nationalLocalMedcationRouteDomainCapablity.retrieve();
//
//        MedicationInstructionVo medINt = buildVo(routes.get(0));
//        MedicationInstructionVo returnedVo = nationalMedicationInstructionDomainCapability.create(medINt, getTestUser());
//        assertNotNull("Returned  with id", returnedVo.getId());
//    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
////    public void testUpdateMedicationInstructionLocal() throws Exception {
////
////        List<MedicationInstructionVo> names = localMedicationInstructionDomainCapability.retrieve();
////
////        MedicationInstructionVo instruction = localMedicationInstructionDomainCapability.retrieve(names.get(0).getId());
////
////        instruction.setRejectionReasonText("updatedRejectREasonTExt");
////
////        Collection<PossibleIntendedUseVo> use = new ArrayList<PossibleIntendedUseVo>();
////        PossibleIntendedUseVo te = new PossibleIntendedUseVo();
////        te.setValue("Outpatient");
////        use.add(te);
////        instruction.setIntendedUse(use);
////
////        localMedicationInstructionDomainCapability.update(instruction, getTestUser());
////
////        MedicationInstructionVo retrievedUpdated = localMedicationInstructionDomainCapability.retrieve(instruction.getId());
////
////        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), "updatedRejectREasonTExt");
////        assertEquals("Should be equal", "Outpatient", retrievedUpdated.getIntendedUse().iterator().next().getValue());
////    }
//
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testUpdateMedicationInstructionNational() throws Exception {
//
//        List<MedicationInstructionVo> names = nationalMedicationInstructionDomainCapability.retrieve();
//
//        MedicationInstructionVo instruction = nationalMedicationInstructionDomainCapability.retrieve(names.get(0).getId());
//
//        instruction.setRejectionReasonText("updatedRejectREasonTExt");
//
//        nationalMedicationInstructionDomainCapability.update(instruction, getTestUser());
//
//        MedicationInstructionVo retrievedUpdated = nationalMedicationInstructionDomainCapability.retrieve(instruction
//            .getId());
//
//        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), "updatedRejectREasonTExt");
//
//    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
////    public void testSearchMedicationInstructionLocal() throws Exception {
////        String name = UUID.randomUUID().toString();
////        int originalCount = 0;
////        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);
////
////        SearchTermVo searchTerm = new SearchTermVo(EntityType.MEDICATION_INSTRUCTION, FieldKey.VALUE, name);
////
////        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
////        itemStatus.add(ItemStatus.INACTIVE);
////        searchCriteria.setItemStatus(itemStatus);
////
////        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
////        requestStatus.add(RequestItemStatus.PENDING);
////        searchCriteria.setRequestStatus(requestStatus);
////
////        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
////        searchTerms.add(searchTerm);
////
////        searchCriteria.setSearchTerms(searchTerms);
////
////        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
////        searchCriteria.setSortOrder(SortOrder.ASCENDING);
////        searchCriteria.setStartRow(0);
////        searchCriteria.setPageSize(10);
////
////        List<MedicationInstructionVo> names;
////        names = localMedicationInstructionDomainCapability.search(searchCriteria);
////        originalCount = names.size();
////
////        List<LocalMedicationRouteVo> routes = localMedicationRouteDomainCapability.retrieve();
////
////        MedicationInstructionVo dataVo = buildVo(routes.get(0), name);
////        localMedicationInstructionDomainCapability.create(dataVo, getTestUser());
////
////        names = localMedicationInstructionDomainCapability.search(searchCriteria);
////
////        assertEquals("Collection returned correct number", originalCount + 1, names.size());
////    }
//
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testExistsMedicationInstructionNational() throws Exception {
//
//        List<LocalMedicationRouteVo> routes = nationalLocalMedcationRouteDomainCapablity.retrieve();
//
//        MedicationInstructionVo med = buildVo(routes.get(0));
//        med.setItemStatus(ItemStatus.ACTIVE);
//        nationalMedicationInstructionDomainCapability.create(med, getTestUser());
//        boolean exists = nationalMedicationInstructionDomainCapability.existsByUniquenessFields(med);
//        assertTrue("exists", exists);
//    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
////    public void testExistsMedicationInstructionLocal() throws Exception {
////
////        List<LocalMedicationRouteVo> routes = localMedicationRouteDomainCapability.retrieve();
////
////        MedicationInstructionVo med = buildVo(routes.get(0));
////        med.setItemStatus(ItemStatus.ACTIVE);
////        localMedicationInstructionDomainCapability.create(med, getTestUser());
////        boolean exists = localMedicationInstructionDomainCapability.existsByUniquenessFields(med);
////        assertTrue("exists", exists);
////    }

}
