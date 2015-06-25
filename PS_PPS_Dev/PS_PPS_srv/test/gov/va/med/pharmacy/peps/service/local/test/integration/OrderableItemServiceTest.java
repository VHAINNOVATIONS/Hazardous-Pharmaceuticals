/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;


import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * This test class is used to test the Orderable Item Service
 */
public class OrderableItemServiceTest extends IntegrationTestCase {

//    private ManagedItemService localManagedItemService;
  //  private ManagedItemService nationalManagedItemService;
  //  private RequestService requestService;

    /**
     * setup the test
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
       

        //   this.localManagedItemService = getLocalOneService(ManagedItemService.class);
      //  this.nationalManagedItemService = getNationalService(ManagedItemService.class);
      //  this.requestService = getNationalService(RequestService.class);
    }

//    /**
//     * Setup the orderable item to test
//     * 
//     * @param oi The OrderableItemVo
//     * @param noi True if this is a NationalOrderableItem
//     * @throws ItemNotFoundException ItemNotFoundException
//     * 
//     * @deprecated This method sets up invalid data! The classes generated are not populated correctly!
//     */
//    @Deprecated
//    private void setupOI(OrderableItemVo oi, boolean noi) throws ItemNotFoundException {
//        OrderableItemGenerator gen = new OrderableItemGenerator();
//        gen.generateOrderableItemVo(oi, !noi, noi);
//
//        // have to do this here because the generator can't use the managed item service
//        // set drug class
//        Collection<AdministrationScheduleAssocVo> schedules = new ArrayList<AdministrationScheduleAssocVo>();
//        AdministrationScheduleAssocVo schedule = new AdministrationScheduleAssocVo();
//        AdministrationScheduleVo adminSchedule = new AdministrationScheduleVo();
//        adminSchedule.setId("9991");
//        adminSchedule.setFrequency(new Long("240"));
//        adminSchedule.setScheduleName("Q4H");
//
//        schedule.setAdministrationSchedule(adminSchedule);
//        schedule.setDefaultSchedule(true);
//        schedules.add(schedule);
//
//        oi.setAdminSchedules(schedules);
//
//        // DataFields<DataField> vadfs = oi.getDataFields();
//        // vadfs.getDataField(FieldKey.SCHEDULE).addSelection(
//        // (AdministrationScheduleVo) managedItemService.retrieve("9991", EntityType.ADMINISTRATION_SCHEDULE));
//    }

    /**
     * Skip this testcase until local comes back
     */
    public void testSkip() {
        boolean isTrue = true;
        assertTrue("skipped test class.", isTrue);
    }

    /**
     * Test retrieving a blank OI Template
     * 
     * @throws Exception
     */
    
//    public void testRetrieveEmpty() throws Exception {
//
//        OrderableItemVo orderableItemVo = (OrderableItemVo) localManagedItemService
//            .retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);
//
//        assertNotNull("Null returned", orderableItemVo);
//
//    }

    /**
     * Test retrieving an OI
     * 
     * @throws ItemNotFoundException
     */
    
//    public void testRetrieve() throws ItemNotFoundException {
//        String id = "9992";
//        OrderableItemVo orderableItemVo = (OrderableItemVo) localManagedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
//
//        assertNotNull(new String("Null returned"), orderableItemVo);
//        assertEquals("Wrong ID", id, orderableItemVo.getId());
//    }

    /**
     * @throws ItemNotFoundException
     */
    
//    public void testRetrieveTemplateById() throws ItemNotFoundException {
//
//        String id = "9993";
//
//        OrderableItemVo orderableItemVo = (OrderableItemVo) localManagedItemService.retrieveTemplate(id,
//            EntityType.ORDERABLE_ITEM);
//
//        assertNotNull("Null returned", orderableItemVo);
//
//    }

    /**
     * Add LOI from existing template
     * 
     * @throws Exception
     */
    
//    public void testAddLOIFromExistingTemplate() throws Exception {
//        OrderableItemVo orderableItem = (OrderableItemVo) localManagedItemService.retrieveTemplate("6713",
//            EntityType.ORDERABLE_ITEM);
//
//        makeUnique(orderableItem);
//        orderableItem.setLocal();
//
//        orderableItem = (OrderableItemVo) localManagedItemService.create(orderableItem, PLM1).getItem();
//        assertNotNull("ID should not be null", orderableItem.getId());
//
//        OrderableItemVo retrieved = (OrderableItemVo) localManagedItemService.retrieve(orderableItem.getId(),
//            EntityType.ORDERABLE_ITEM);
//
//        assertSameLoi(orderableItem, retrieved);
//    }

    /**
     * @throws Exception
     */
    
//    public void testAddLocalFromBlankTemplateMandFieldsOnly() throws Exception {
//        OrderableItemVo orderableItemVo = (OrderableItemVo) localManagedItemService
//            .retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);
//
//        setupOI(orderableItemVo, false);
//        ProcessedItemVo processedItem = localManagedItemService.create(orderableItemVo, PLM1);
//        OrderableItemVo insertedVo = (OrderableItemVo) processedItem.getItem();
//        assertNotNull("ID should not be null", insertedVo.getId());
//
//        OrderableItemVo retrievedVo = (OrderableItemVo) localManagedItemService.retrieve(insertedVo.getId(),
//            EntityType.ORDERABLE_ITEM);
//
//        assertTrue("LOI's should have Local type", retrievedVo.isLocalOnlyData());
//        assertNotNull("Orderable Item should not be null", retrievedVo);
//        assertEquals("Parent id incorrect", orderableItemVo.getOrderableItemParent().getId(), retrievedVo
//            .getOrderableItemParent().getId());
//        assertEquals("Orderable Item names should be equal", orderableItemVo.getOiName(), retrievedVo.getOiName());
//        assertFalse("There should be data fields saved", retrievedVo.getDataFields().isEmpty());
//        assertEquals("product type should be equal", orderableItemVo.getDataFields().getDataField(FieldKey.CATEGORIES)
//            .getValue(), orderableItemVo.getDataFields().getDataField(FieldKey.CATEGORIES).getValue());
//
//        assertEquals("LOI New Item Request should be approved", RequestItemStatus.APPROVED, retrievedVo
//            .getRequestItemStatus());
//
//    }

    /**
     * Add NOI from existing template
     * 
     * @throws Exception
     */
    
//    public void testAddNOIFromExistingTemplate() throws Exception {
//        OrderableItemVo orderableItem = (OrderableItemVo) localManagedItemService.retrieveTemplate("9992",
//            EntityType.ORDERABLE_ITEM);
//        makeUnique(orderableItem);
//
//        orderableItem = (OrderableItemVo) localManagedItemService.create(orderableItem, PLM1).getItem();
//        assertNotNull("ID should not be null", orderableItem.getId());
//
//        OrderableItemVo retrieved = (OrderableItemVo) localManagedItemService.retrieve(orderableItem.getId(),
//            EntityType.ORDERABLE_ITEM);
//
//        assertSameNoi(orderableItem, retrieved);
//    }

    /**
     * Add NOI from blank template
     * 
     * @throws Exception
     */
    
//    public void testAddNOIFromBlankTemplate() throws Exception {
//        OrderableItemVo orderableItemVo = (OrderableItemVo) localManagedItemService
//            .retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);
//
//        UserVo user = new UserVo();
//        user.setUsername("PLM1L1");
//        user.setLocation("local-1");
//
//        setupOI(orderableItemVo, true);
//        ProcessedItemVo processedItem = localManagedItemService.create(orderableItemVo, PLM1);
//        OrderableItemVo insertedVo = (OrderableItemVo) processedItem.getItem();
//        assertNotNull("ID should not be null", insertedVo.getId());
//
//        OrderableItemVo retrievedVo = (OrderableItemVo) localManagedItemService.retrieve(insertedVo.getId(),
//            EntityType.ORDERABLE_ITEM);
//
//        assertNull("NOI should not have parent", retrievedVo.getOrderableItemParent());
//        assertTrue("NOI's should have National type", retrievedVo.isNational());
//        assertEquals("NOI New Item Request from PLM should be pending", RequestItemStatus.PENDING, retrievedVo
//            .getRequestItemStatus());
//        assertEquals("Item status incorrect", retrievedVo.getItemStatus(), orderableItemVo.getItemStatus());
//        assertEquals("Dosage form incorrect", retrievedVo.getDosageForm().getDosageFormName(), orderableItemVo
//            .getDosageForm().getDosageFormName());
//        assertEquals("OI Name incorrect", retrievedVo.getOiName(), orderableItemVo.getOiName());
//        assertEquals("VISTA OI Name incorrect", retrievedVo.getVistaOiName(), orderableItemVo.getVistaOiName());
//        assertEquals("National Formulary Indicator incorrect", retrievedVo.getNationalFormularyIndicator(), orderableItemVo
//            .getNationalFormularyIndicator());
//
//        // OI-Drug Text entry
//        Collection<DrugTextVo> inserterDrugText = orderableItemVo.getLocalDrugTexts();
//        Collection<DrugTextVo> retrievedDrugText = retrievedVo.getLocalDrugTexts();
//
//        for (DrugTextVo idrugTxt : inserterDrugText) {
//            idrugTxt = (DrugTextVo) localManagedItemService.retrieve(idrugTxt.getId(), EntityType.DRUG_TEXT);
//
//            assertTrue("incorrect drug text", retrievedDrugText.contains(idrugTxt));
//        }
//
//        // labs
//
//        // lab name admin
//        Collection<LabDisplayAdministrationVo> colInsertedLabNameAdmin = orderableItemVo.getLabDisplayAdministration();
//        Collection<LabDisplayAdministrationVo> colRetrievedLabNameAdmin = retrievedVo.getLabDisplayAdministration();
//
//        for (LabDisplayAdministrationVo labAdmin : colInsertedLabNameAdmin) {
//            assertTrue("incorrect Lab name admin", colRetrievedLabNameAdmin.contains(labAdmin));
//        }
//
//        // lab name finish
//        Collection<LabDisplayFinishingAnOrderVo> colInsertedLabNameFinish = orderableItemVo.getLabDisplayFinishingAnOrder();
//        Collection<LabDisplayFinishingAnOrderVo> colRetrievedLabNameFinish = retrievedVo.getLabDisplayFinishingAnOrder();
//
//        for (LabDisplayFinishingAnOrderVo labFinish : colInsertedLabNameFinish) {
//            assertTrue("incorrect Lab name finish", colRetrievedLabNameFinish.contains(labFinish));
//        }
//
//        // lab name initial
//        Collection<LabDisplayOrderEntryVo> colInsertedLabNameInitial = orderableItemVo.getLabDisplayOrderEntry();
//        Collection<LabDisplayOrderEntryVo> colRetrievedLabNameInitial = retrievedVo.getLabDisplayOrderEntry();
//
//        for (LabDisplayOrderEntryVo labInitial : colInsertedLabNameInitial) {
//            assertTrue("incorrect Lab name initial", colRetrievedLabNameInitial.contains(labInitial));
//        }
//
//        // vitals
//
//        // vital name administration
//        Collection<VitalsDisplayAdministrationVo> colInsertedVitalsNameAdmin = orderableItemVo
//            .getVitalsDisplayAdministration();
//        Collection<VitalsDisplayAdministrationVo> colRetrievedVitalsNameAdmin = retrievedVo.getVitalsDisplayAdministration();
//
//        for (VitalsDisplayAdministrationVo vitalsAdmin : colInsertedVitalsNameAdmin) {
//            assertTrue("incorrect Vitals name admin", colRetrievedVitalsNameAdmin.contains(vitalsAdmin));
//        }
//
//        // vital name finish
//        Collection<VitalsDisplayFinishAnOrderVo> colInsertedVitalsNameFinish = orderableItemVo
//            .getVitalsDisplayFinishAnOrder();
//        Collection<VitalsDisplayFinishAnOrderVo> colRetrievedVitalsNameFinish = retrievedVo.getVitalsDisplayFinishAnOrder();
//
//        for (VitalsDisplayFinishAnOrderVo vitalsFinish : colInsertedVitalsNameFinish) {
//            assertTrue("incorrect Vitals name finish", colRetrievedVitalsNameFinish.contains(vitalsFinish));
//        }
//
//        // vital name initial
//        Collection<VitalsDisplayOrderEntryVo> colInsertedVitalsNameInitial = orderableItemVo.getVitalsDisplayOrderEntry();
//        Collection<VitalsDisplayOrderEntryVo> colRetrievedVitalsNameInitial = retrievedVo.getVitalsDisplayOrderEntry();
//
//        for (VitalsDisplayOrderEntryVo vitalsFinish : colInsertedVitalsNameInitial) {
//            assertTrue("incorrect Vitals name initial", colRetrievedVitalsNameInitial.contains(vitalsFinish));
//        }
//
//        // go through all the va data fields for product and check that the values set match the values retrieved
//        Collection<FieldKey> fields = FieldKey.getOrderableItemVaDataFields();
//
//        for (FieldKey key : fields) {
//            System.out.println(key);
//
//            if (key.isGroupDataField()) {
//                GroupDataField groupDf = (GroupDataField) orderableItemVo.getDataFields().getDataField(key);
//                GroupDataField savedGroupDf = (GroupDataField) retrievedVo.getDataFields().getDataField(key);
//                assertEquals("data field " + key + " incorrect", groupDf, savedGroupDf);
//            }
//
//            if (key.isListDataField()) {
//                ListDataField list = (ListDataField) orderableItemVo.getDataFields().getDataField(key);
//                ListDataField savedList = (ListDataField) retrievedVo.getDataFields().getDataField(key);
//
//                if (list != null && savedList != null) {
//                    for (Object selection : list.getValue()) {
//                        assertTrue("incorrect list data field " + key, savedList.contains((String) selection));
//                    }
//
//                    // check that the editable property of the list data fields are correct
//                    assertEquals("incorrect editable value of list data field " + key, list.isEditable(), savedList
//                        .isEditable());
//                }
//            }
//            else if (key.isPrimaryKeyDataField()) {
//                assertEquals("primary key data field " + key + " incorrect", orderableItemVo.getDataFields().getDataField(
//                    key).getUniqueId(), retrievedVo.getDataFields().getDataField(key).getUniqueId());
//            }
//            else {
//                if (orderableItemVo.getDataFields().getDataField(key) != null) {
//                    assertEquals("data field " + key + " incorrect", orderableItemVo.getDataFields().getDataField(key)
//                        .getValue(), retrievedVo.getDataFields().getDataField(key).getValue());
//                }
//
//            }
//        }
//
//    }

    /**
     * Test modifying a local orderable item.
     * 
     * @throws Exception Exception
     */
    
//    public void testModifyLOI() throws Exception {
//        OrderableItemVo updatedItem = (OrderableItemVo) localManagedItemService.retrieve("67150000",
//            EntityType.ORDERABLE_ITEM);
//        OrderableItemVo oldItem = updatedItem.copy();
//
//        updatedItem.setNonVaMed(true);
//
//        updatedItem.getDataFields().getDataField(FieldKey.PATIENT_INSTRUCTIONS).selectValue(
//            RandomGenerator.nextAlphabetic(10));
//
//        updatedItem.getOiRoute().clear();
//
//        OiRouteVo oiRoute = new OiRouteVo();
//        LocalMedicationRouteVo route = (LocalMedicationRouteVo) localManagedItemService.retrieveMinimal("6717",
//            EntityType.LOCAL_MEDICATION_ROUTE);
//        oiRoute.setLocalMedicationRoute(route);
//        updatedItem.getOiRoute().add(oiRoute);
//
//        oiRoute = new OiRouteVo();
//        route = (LocalMedicationRouteVo) localManagedItemService.retrieveMinimal("6718", EntityType.LOCAL_MEDICATION_ROUTE);
//        oiRoute.setLocalMedicationRoute(route);
//        oiRoute.setDefaultRoute(true);
//        updatedItem.getOiRoute().add(oiRoute);
//
//        Collection<ModDifferenceVo> modDifferences = localManagedItemService.submitModifications(oldItem, updatedItem, PLM1)
//            .getModDifferences();
//        updatedItem = (OrderableItemVo) localManagedItemService.commitModifications(modDifferences, oldItem, PLM1).getItem();
//
//        OrderableItemVo retrieved = (OrderableItemVo) localManagedItemService.retrieve(updatedItem.getId(),
//            EntityType.ORDERABLE_ITEM);
//
//        Collection<Difference> differences = updatedItem.diff(retrieved);
//        System.out.println("Differneces:");
//        System.out.println(differences);
//        assertTrue("There should be no differences", differences.isEmpty());
//    }


    /**
     * Junit to test Optomistic Locking implementation
     * 
     * @throws Exception
     */
    
//    public void testOptomisticLocking() throws Exception {
//        String id = "67150000";
//        OrderableItemVo oldItem = (OrderableItemVo) localManagedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
//        OrderableItemVo one = (OrderableItemVo) localManagedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
//        OrderableItemVo two = (OrderableItemVo) localManagedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
//
//        // PLM1 mods one
//        one.getDataFields().getDataField(FieldKey.PATIENT_INSTRUCTIONS).selectValue(RandomGenerator.nextAlphabetic(10));
//        Collection<ModDifferenceVo> modDifferences = localManagedItemService.submitModifications(oldItem, one, PLM1)
//            .getModDifferences();
//        localManagedItemService.commitModifications(modDifferences, one, PLM1);
//
//        // PLM2 mods two
//        two.getDataFields().getDataField(FieldKey.PATIENT_INSTRUCTIONS).selectValue(RandomGenerator.nextAlphabetic(10));
//        modDifferences = localManagedItemService.submitModifications(oldItem, two, PLM2).getModDifferences();
//
//        try {
//            localManagedItemService.commitModifications(modDifferences, two, PLM2);
//            fail("An OptimisticLockingException should be thrown");
//        }
//        catch (OptimisticLockingException e) {
//            assertNotNull("An OptimisticLockingException should be thrown", e);
//        }
//    }

    /**
     * Test Generating the OI Names
     * 
     * @throws Exception
     */
    
//    public void testGenerateOiNames() throws Exception {
//
//        OrderableItemVo orderableItemVo = (OrderableItemVo) localManagedItemService
//            .retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);
//
//        // Set mandatory fields
//        orderableItemVo.setLocal();
//        orderableItemVo.setItemStatus(ItemStatus.ACTIVE);
//
//        // Set Dosage Form
//        DosageFormVo dosageForm = new DosageFormVo();
//        dosageForm.setId("9995");
//        dosageForm.setDosageFormName("TAB,SA");
//        orderableItemVo.setDosageForm(dosageForm);
//
//        // Set Generic Name
//        GenericNameVo genericName = new GenericNameVo();
//        genericName.setId("9991");
//        genericName.setValue("ATROPINE");
//        orderableItemVo.setGenericName(genericName);
//
//        // Retrieve Oi Name and VistA OI name
//        OrderableItemVo generatedOI = (OrderableItemVo) localManagedItemService.generateOINames(orderableItemVo);
//
//        // Verify OI name and VistA OI name set correctly
//        assertEquals("VistA OI name is not correctly set", "ATROPINE", generatedOI.getVistaOiName());
//        assertEquals("OI name is not corerctly set", "ATROPINE TAB,SA", generatedOI.getOiName());
//    }

    /**
     * test Modifying the OI IV Flag data field
     * 
     * @throws Exception
     */
    
//    public void testModifyOiIvFlag() throws Exception {
//        String id = "67150000";
//        OrderableItemVo newVo = (OrderableItemVo) localManagedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
//        OrderableItemVo oldVo = (OrderableItemVo) localManagedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
//        DataField<Boolean> oiIvFlag = newVo.getDataFields().getDataField(FieldKey.OI_IV_FLAG);
//
//        if (oiIvFlag == null) {
//            oiIvFlag = DataField.newInstance(FieldKey.OI_IV_FLAG);
//            oiIvFlag.selectValue(true);
//            newVo.getDataFields().setDataField(oiIvFlag);
//        }
//        else if (oiIvFlag.getValue()) {
//            oiIvFlag.selectValue(false);
//            newVo.getDataFields().setDataField(oiIvFlag);
//        }
//        else {
//            oiIvFlag.selectValue(true);
//            newVo.getDataFields().setDataField(oiIvFlag);
//        }
//
//        Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>();
//        Collection<Difference> differences = oldVo.diff(newVo);
//
//        for (Difference diff : differences) {
//            ModDifferenceVo modDiff = new ModDifferenceVo();
//            modDiff.setDifference(diff);
//            modDiff.setModificationReason("test reason");
//            modDifferences.add(modDiff);
//        }
//
//        localManagedItemService.commitModifications(modDifferences, oldVo, PLM1);
//
//        OrderableItemVo retrievedVo = (OrderableItemVo) localManagedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
//
//        assertEquals("Oi IV Flag was wrong", oiIvFlag.getValue(), retrievedVo.getDataFields().getDataField(
//            FieldKey.OI_IV_FLAG).getValue());
//    }

    /**
     * @throws Exception
     */
    
//    public void testLocalNoOIMedRoute() throws Exception {
//
//        OrderableItemVo orderableItemVo = (OrderableItemVo) localManagedItemService
//            .retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);
//
//        // Set mandatory fields
//        orderableItemVo.setOiName("TEST BLANK LOI FROM LOCAL" + new Long(new Date().getTime()).toString());
//        orderableItemVo.setLocal();
//        orderableItemVo.setItemStatus(ItemStatus.ACTIVE);
//        orderableItemVo.setLocalUse(true);
//        DosageFormVo dosageForm = new DosageFormVo();
//        dosageForm.setId("9995");
//        dosageForm.setDosageFormName("TAB,SA");
//        orderableItemVo.setDosageForm(dosageForm);
//
//        orderableItemVo.setVistaOiName("my VistA OI Name" + new Long(new Date().getTime()).toString());
//
//        DataFields<DataField> vadfs = orderableItemVo.getDataFields();
//
//        OrderableItemVo parent = new OrderableItemVo();
//        parent.setId("9993");
//        orderableItemVo.setOrderableItemParent(parent);
//
//        orderableItemVo.setFormularyStatus(FormularyStatus.FORMULARY);
//
//        ListDataField<String> productType = orderableItemVo.getDataFields().getDataField(FieldKey.CATEGORIES);
//        List<String> typeList = new ArrayList<String>();
//        typeList.add("HERBAL");
//        productType.selectValue(typeList);
//        orderableItemVo.getDataFields().setDataField(productType);
//
//        try {
//            ProcessedItemVo processedItem = localManagedItemService.create(orderableItemVo, PLM1);
//            OrderableItemVo insertedVo = (OrderableItemVo) processedItem.getItem();
//        }
//        catch (ValueObjectValidationException e) {
//            assertNotNull("Did not recieve correct exception", e);
//        }
//
//    }

    /**
     * Add NOI from existing inactive template
     * 
     * @throws Exception
     */
    
//    public void testAddNOIFromExistingInactiveTemplate() throws Exception {
//        OrderableItemVo orderableItem = (OrderableItemVo) localManagedItemService.retrieveTemplate("9992",
//            EntityType.ORDERABLE_ITEM);
//        makeUnique(orderableItem);
//        orderableItem.inactivate();
//        orderableItem = (OrderableItemVo) localManagedItemService.create(orderableItem, PLM1).getItem();
//        assertNotNull("ID should not be null", orderableItem.getId());
//        assertEquals("NOI should be inactive", ItemStatus.INACTIVE, orderableItem.getItemStatus());
//
//        orderableItem = (OrderableItemVo) localManagedItemService.retrieveTemplate(orderableItem.getId(),
//            EntityType.ORDERABLE_ITEM);
//        makeUnique(orderableItem);
//        orderableItem = (OrderableItemVo) localManagedItemService.create(orderableItem, PLM1).getItem();
//        assertNotNull("ID should not be null", orderableItem.getId());
//
//        OrderableItemVo retrieved = (OrderableItemVo) localManagedItemService.retrieve(orderableItem.getId(),
//            EntityType.ORDERABLE_ITEM);
//
//        assertSameNoi(orderableItem, retrieved);
//    }

    /**
     * Move products between OIs
     * 
     * @throws Exception if error
     */
    
//    public void testMoveProducts() throws Exception {
//
//        // Create two new OIs, each with a Product associated to it
//        OrderableItemVo one = (OrderableItemVo) localManagedItemService.retrieve("99950000", EntityType.ORDERABLE_ITEM);
//        one.setId(null);
//        one.setVistaOiName(RandomGenerator.nextAlphabetic(10));
//        one = (OrderableItemVo) localManagedItemService.create(one, PLM1).getItem();
//
//        ProductVo oneProduct = (ProductVo) localManagedItemService.retrieve("99950000", EntityType.PRODUCT);
//        oneProduct.setId(null);
//        oneProduct.setOrderableItem(one);
//        oneProduct.setAtcMnemonic(null);
//        oneProduct.setVaProductName(RandomGenerator.nextAlphabetic(10));
//        localManagedItemService.create(oneProduct, PLM1);
//
//        OrderableItemVo two = (OrderableItemVo) localManagedItemService.retrieve("99950001", EntityType.ORDERABLE_ITEM);
//        two.setId(null);
//        two.setVistaOiName(RandomGenerator.nextAlphabetic(10));
//        two = (OrderableItemVo) localManagedItemService.create(two, PLM1).getItem();
//
//        ProductVo twoProduct = (ProductVo) localManagedItemService.retrieve("99950001", EntityType.PRODUCT);
//        twoProduct.setId(null);
//        twoProduct.setOrderableItem(two);
//        twoProduct.setAtcMnemonic(null);
//        twoProduct.setVaProductName(RandomGenerator.nextAlphabetic(10));
//        localManagedItemService.create(twoProduct, PLM1);
//
//        // Swap the Products
//        List<ManagedItemVo> oneChildren = localManagedItemService.retrieveChildren(one.getId(), EntityType.ORDERABLE_ITEM);
//        List<ManagedItemVo> twoChildren = localManagedItemService.retrieveChildren(two.getId(), EntityType.ORDERABLE_ITEM);
//        one.setChildren(twoChildren);
//        two.setChildren(oneChildren);
//
//        localManagedItemService.moveChildren(one, two, PLM1);
//
//        // Verify the products were moved
//        OrderableItemVo oneUpdated = (OrderableItemVo) localManagedItemService.retrieve(one.getId(),
//            EntityType.ORDERABLE_ITEM);
//        OrderableItemVo twoUpdated = (OrderableItemVo) localManagedItemService.retrieve(two.getId(),
//            EntityType.ORDERABLE_ITEM);
//
//        assertFalse("One should have at least one Product", oneUpdated.getProducts().isEmpty());
//
//        for (ManagedItemVo oneChild : oneUpdated.getProducts()) {
//            boolean found = false;
//
//            for (ManagedItemVo twoChild : twoChildren) {
//                if (twoChild.getId().equals(oneChild.getId())) {
//                    found = true;
//                }
//            }
//
//            assertTrue("One's children should equal what used to be two's, one's child ID '" + oneChild.getId()
//                + "' was not found in two's", found);
//        }
//
//        assertFalse("Two should have at least one Product", twoUpdated.getProducts().isEmpty());
//
//        for (ManagedItemVo twoChild : twoUpdated.getProducts()) {
//            boolean found = false;
//
//            for (ManagedItemVo oneChild : oneChildren) {
//                if (oneChild.getId().equals(twoChild.getId())) {
//                    found = true;
//                }
//            }
//
//            assertTrue("Two's children should equal what used to be one's, two's child ID '" + twoChild.getId()
//                + "' was not found in one's", found);
//        }
//    }

    /**
     * When an NOI is created at National that matches an LOI by uniqueness criteria (and not ID), then the LOI's local-only
     * field values need to be copied and the LOI's Products need to be moved to the NOI.
     * 
     * @throws Exception if error
     */
    
//    public void testMergeLoiIntoNoi() throws Exception {
//        UserGenerator userGenerator = new UserGenerator();
//        UserVo plm1 = userGenerator.getLocalManagerOne();
//        UserVo pnm1 = userGenerator.getNationalManagerOne();
//        UserVo pnm2 = userGenerator.getNationalManagerTwo();
//
//        DosageFormVo dosageForm = (DosageFormVo) localManagedItemService.retrieveMinimal("9991", EntityType.DOSAGE_FORM);
//
//        // Create an LOI with a few local-only fields set and a Product associated with it
//        OrderableItemVo localParent = (OrderableItemVo) localManagedItemService.retrieve("99950000",
//            EntityType.ORDERABLE_ITEM);
//        localParent.setId(null);
//        localParent.setProducts(Collections.EMPTY_LIST);
//        localParent.setNational();
//        localParent.setVistaOiName(RandomGenerator.nextAlphabetic(10));
//        localParent.setDosageForm(dosageForm);
//        localParent = (OrderableItemVo) localManagedItemService.create(localParent, plm1).getItem();
//        System.out
//            .println("Parent NOI '" + localParent.getVistaOiName() + "' created with ID '" + localParent.getId() + "'");
//
//        OrderableItemVo local = (OrderableItemVo) localManagedItemService.retrieve("6711", EntityType.ORDERABLE_ITEM);
//        local.setId(null);
//        local.setProducts(Collections.EMPTY_LIST);
//        local.setLocal();
//        local.setLocalUse(false);
//        local.setNationalFormularyIndicator(true);
//        local.setVistaOiName(RandomGenerator.nextAlphabetic(10));
//        local.setDosageForm(dosageForm);
//        local.setOrderableItemParent(localParent);
//        local = (OrderableItemVo) localManagedItemService.create(local, plm1).getItem();
//        System.out.println("LOI '" + local.getVistaOiName() + "' created with ID '" + local.getId() + "'");
//
//        ProductVo localProduct = (ProductVo) localManagedItemService.retrieve("9991", EntityType.PRODUCT);
//        localProduct.setId(null);
//        localProduct.setPreviousOrderableItem(null);
//        localProduct.setOrderableItem(local);
//        localProduct.setAtcMnemonic(null);
//        localProduct.setVaProductName(RandomGenerator.nextAlphabetic(10));
//        localProduct.getDataFields().getDataField(FieldKey.LOCAL_NON_FORMULARY).selectValue(true);
//        localProduct = (ProductVo) localManagedItemService.create(localProduct, plm1).getItem();
//        System.out.println("Product '" + localProduct.getVaProductName() + "' created with ID '" + localProduct.getId()
//            + "'");
//
//        System.out.println("Waiting for Local to send the Product to National...");
//        Thread.sleep(20000);
//
//        // Create and approve an NOI with the same uniqueness fields as the LOI
//        OrderableItemVo national = (OrderableItemVo) nationalManagedItemService.retrieve("99950000",
//            EntityType.ORDERABLE_ITEM);
//        national.setId(null);
//        national.setProducts(Collections.EMPTY_LIST);
//        national.setNational();
//        national.setVistaOiName(local.getVistaOiName());
//        national.setDosageForm(dosageForm);
//        national = (OrderableItemVo) nationalManagedItemService.create(national, pnm1).getItem();
//        System.out.println("NOI '" + national.getVistaOiName() + "' created with ID '" + national.getId() + "'");
//
//        RequestVo request = requestService.retrieve(national.getId(), EntityType.ORDERABLE_ITEM).iterator().next();
//
//        ProcessedRequestVo processedRequest = nationalManagedItemService.approveRequest(national, request,
//            Collections.EMPTY_LIST, pnm2);
//        national = (OrderableItemVo) processedRequest.getItem();
//        request = processedRequest.getRequest();
//
//        national = (OrderableItemVo) nationalManagedItemService.commitRequest(national, request, Collections.EMPTY_LIST,
//            pnm2).getItem();
//
//        System.out.println("NOI '" + national.getVistaOiName() + "' approved with ID '" + national.getId() + "'");
//
//        System.out.println("Waiting for National to send the NOI to Locals...");
//        Thread.sleep(20000);
//
//        // Verify the LOI was deleted, that the local-only fields were set on the NOI, and that the LOI's Product was moved
//        // its parent NOI
//
//        national = (OrderableItemVo) localManagedItemService.retrieve(national.getId(), EntityType.ORDERABLE_ITEM);
//
//        try {
//            localManagedItemService.retrieve(local.getId(), EntityType.ORDERABLE_ITEM);
//            fail("Should have thrown ItemNotFoundException");
//        }
//        catch (ItemNotFoundException e) {
//            assertNotNull("Should have thrown ItemNotFoundException", e);
//        }
//
//        // (tstavenger:May 27, 2009) M5P2 code should handle merging local-only data that can cause validation errors
//        // Collection<FieldKey> localOnly = FieldKey.getLocalOnlyFields(EntityType.ORDERABLE_ITEM);
//        //
//        // for (FieldKey fieldKey : localOnly) {
//        // Object localValue = ReflectionUtility.getValue(local, fieldKey);
//        // Object nationalValue = ReflectionUtility.getValue(national, fieldKey);
//        //
//        // assertEquals("NOI should have local-only values set for field '" + fieldKey.getKey() + "'", localValue,
//        // nationalValue);
//        // }
//
//        localParent = (OrderableItemVo) localManagedItemService.retrieve(localParent.getId(), EntityType.ORDERABLE_ITEM);
//
//        boolean found = false;
//
//        for (ProductVo product : localParent.getProducts()) {
//            if (product.getId().equals(localProduct.getId())) {
//                found = true;
//                break;
//            }
//        }
//
//        assertTrue("LOI's Product should have been moved to NOI", found);
//    }

    /**
     * This test verifies the following business rule for FormularyStatus
     * 
     * Rule 4: During an orderable item update at the Local PEPS instance, set Formulary Status (DF67) to "Formulary" if at
     * least one product item associated to the orderable item that have an Item Status (DF351) value of "Active" also have a
     * Local Non-Formulary (DF109) value of "N".
     * 
     * Rule 5: During an orderable item update at the Local PEPS instance, set Formulary Status (DF67) to "Non-Formulary" if
     * all product items associated to the orderable item that have an Item Status (DF351) value of "Active" also have a
     * Local Non-Formulary (DF109) value of "Y".
     * 
     * 
     * @throws Exception
     */
    
//    public void testFormularyStatus() throws Exception {
//
//        // create a new NOI
//        OrderableItemVo orderableItem = (OrderableItemVo) localManagedItemService.retrieveTemplate("9992",
//            EntityType.ORDERABLE_ITEM);
//        makeUnique(orderableItem);
//
//        orderableItem.setFormularyStatus(FormularyStatus.FORMULARY);
//
//        // create the NOI
//        orderableItem = (OrderableItemVo) localManagedItemService.create(orderableItem, PLM1).getItem();
//        assertNotNull("The NOI ID should not be null", orderableItem.getId());
//        OrderableItemVo retrievedOi = (OrderableItemVo) localManagedItemService.retrieve(orderableItem.getId(),
//            EntityType.ORDERABLE_ITEM);
//
//        assertNotNull("The value of formulary status field should be not null", retrievedOi.getFormularyStatus());
//
//        // testing rule #4
//        // add a new product to the orderable item with the value of local non-formulary set to "Yes"
//        ProductVo product1 = (ProductVo) localManagedItemService.retrieveTemplate("9991", EntityType.PRODUCT);
//        makeUnique(product1);
//        product1.setOrderableItem(orderableItem);
//        product1.getDataFields().getDataField(FieldKey.LOCAL_NON_FORMULARY).selectValue(true);
//
//        // insert the product
//        localManagedItemService.create(product1, PLM1);
//
//        // retrieve the NOI again to test the value of formulary status
//        retrievedOi = (OrderableItemVo) localManagedItemService.retrieve(retrievedOi.getId(), EntityType.ORDERABLE_ITEM);
//
//        // modify the NOI
//        OrderableItemVo newOi = retrievedOi.copy();
//        newOi.setLocalUse(!retrievedOi.isLocalUse());
//        Collection<ModDifferenceVo> modDifferences = localManagedItemService.submitModifications(retrievedOi, newOi, PLM1)
//            .getModDifferences();
//        OrderableItemVo updatedOi = (OrderableItemVo) localManagedItemService.commitModifications(modDifferences,
//            retrievedOi, PLM1).getItem();
//        assertEquals("The value of formulary status field should be Non-Formulary", FormularyStatus.NON_FORMULARY, updatedOi
//            .getFormularyStatus());
//
//        // testing rule #5
//        // add anther product with local non-formulary set to "No". In this case the formulary status of the oi
//        // should be "Formulary"
//        ProductVo product2 = (ProductVo) localManagedItemService.retrieveTemplate("9991", EntityType.PRODUCT);
//        makeUnique(product2);
//        product2.setOrderableItem(orderableItem);
//        product2.getDataFields().getDataField(FieldKey.LOCAL_NON_FORMULARY).selectValue(false);
//
//        // insert the product
//        localManagedItemService.create(product2, PLM1);
//
//        // modify the oi for the formulary to take place
//        retrievedOi = (OrderableItemVo) localManagedItemService.retrieve(retrievedOi.getId(), EntityType.ORDERABLE_ITEM);
//
//        newOi = retrievedOi.copy();
//        newOi.setLocalUse(!retrievedOi.isLocalUse());
//        modDifferences = localManagedItemService.submitModifications(retrievedOi, newOi, PLM1).getModDifferences();
//        updatedOi = (OrderableItemVo) localManagedItemService.commitModifications(modDifferences, retrievedOi, PLM1)
//            .getItem();
//
//        assertNotNull("The value of formulary status field should be not null", updatedOi.getFormularyStatus());
//    }
}
