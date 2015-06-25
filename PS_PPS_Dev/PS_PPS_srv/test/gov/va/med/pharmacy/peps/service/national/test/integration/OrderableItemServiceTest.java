/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.test.generator.OrderableItemGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleAssocVo;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedRequestVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * tests OI service
 */
public class OrderableItemServiceTest extends IntegrationTestCase {
    private static final Logger LOG = Logger.getLogger(OrderableItemServiceTest.class);
    private ManagedItemService managedItemService;
    private RequestService requestService;
    
    private String s9991 = "9991";
    private String s9992 = "9992";
    private String s9993 = "9993";
    private String s99950000 = "999599999";

    /**
     * setUp
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        LOG.debug("---------------- " + getName() + " ---------------");
        managedItemService = getNationalService(ManagedItemService.class);
        requestService = getNationalService(RequestService.class);
    }

    /**
     * Setup an OI for the test.
     * 
     * @param oi OI to update
     * @param noi True if NOI
     * @throws ItemNotFoundException if error
     * 
     * @deprecated This method sets up invalid data! The classes generated are not populated correctly!
     */
    @Deprecated
    private void setupOI(OrderableItemVo oi, boolean noi) throws ItemNotFoundException {
        OrderableItemGenerator gen = new OrderableItemGenerator();
        gen.generateOrderableItemVo(oi, false, noi);

        // have to do this here because the generator can't use the managed item service
        // set admin Schedules
        Collection<AdministrationScheduleAssocVo> schedules = new ArrayList<AdministrationScheduleAssocVo>();
        AdministrationScheduleAssocVo schedule = new AdministrationScheduleAssocVo();
        AdministrationScheduleVo adminSchedule = new AdministrationScheduleVo();
        adminSchedule.setId(s9991);
        adminSchedule.setFrequency(new Long("240"));
        adminSchedule.setScheduleName("Q4H");

        schedule.setAdministrationSchedule(adminSchedule);
        schedule.setDefaultSchedule(true);
        schedules.add(schedule);

        oi.setAdminSchedules(schedules);
    }

    /**
     * testRetrieveEmpty
     * 
     * @throws Exception Exception
     */
    public void testRetrieveEmpty() throws Exception {

        OrderableItemVo orderableItemVo = (OrderableItemVo) managedItemService
            .retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);

        assertNotNull("Null returned.", orderableItemVo);

    }

    /**
     * testRetrieve
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public void testRetrieve() throws ItemNotFoundException {
        String id = s9993;
        OrderableItemVo orderableItemVo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);

        assertNotNull("Null returned!", orderableItemVo);
        assertEquals("Wrong ID", id, orderableItemVo.getId());
    }

    /**
     * testRetrieveTemplateById
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public void testRetrieveTemplateById() throws ItemNotFoundException {

        String id = s9993;

        OrderableItemVo orderableItemVo = (OrderableItemVo) managedItemService.retrieveTemplate(id,
            EntityType.ORDERABLE_ITEM);

        assertNotNull("Null returned", orderableItemVo);

    }

    /**
     * Add NOI from existing template
     * 
     * @throws Exception Exception
     */
    public void testAddNOIFromExistingTemplate() throws Exception {
        OrderableItemVo orderableItem = (OrderableItemVo) managedItemService.retrieveTemplate(s9993,
            EntityType.ORDERABLE_ITEM);
        makeUnique(orderableItem);
        orderableItem = (OrderableItemVo) managedItemService.create(orderableItem, PNM1).getItem();
        assertNotNull("ID should not be null!", orderableItem.getId());

        OrderableItemVo retrieved = (OrderableItemVo) managedItemService.retrieve(orderableItem.getId(),
            EntityType.ORDERABLE_ITEM);

        assertSameNoi(orderableItem, retrieved);
    }

    /**
     * update NOI
     * 
     * @throws Exception Exception
     */
    public void testModifyNOI() throws Exception {
        String id = s99950000;
        OrderableItemVo newVo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
        OrderableItemVo oldVo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);

        // ListDataField<String> schedule = newVo.getDataFields().getDataField(FieldKey.SCHEDULE);
        // List<String> selection = new ArrayList<String>();
        // String scheduleValue = "CONTINUOUS";
        // selection.add(scheduleValue);
        // schedule.selectValue(selection);
        // newVo.getDataFields().setDataField(schedule);

        newVo.setNonVaMed(true);

        DataField<String> patientInst = newVo.getVaDataFields().getDataField(FieldKey.PATIENT_INSTRUCTIONS);
        String piString = "Updated patient instructions";
        patientInst.selectValue(piString);
        newVo.getVaDataFields().setDataField(patientInst);

        Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>();
        Collection<Difference> differences = oldVo.diff(newVo);

        for (Difference diff : differences) {
            ModDifferenceVo modDiff = new ModDifferenceVo();
            modDiff.setDifference(diff);
            modDiff.setModificationReason("test reason!");
            modDifferences.add(modDiff);
        }

        managedItemService.commitModifications(modDifferences, oldVo, PNM1);

        assertTrue("nonVaMed was true",  newVo.getNonVaMed());
        assertEquals("patientInstructions was wrong", patientInst, newVo.getVaDataFields().getDataField(
            FieldKey.PATIENT_INSTRUCTIONS));
    }

    /**
     * update NOI
     * 
     * 
     * Re-enable this test case when it is fixed!
     * @throws ValidationException exception
     * @throws OptimisticLockingException exception
     */
    public void xtestModifyScheduleFieldInNOI() throws OptimisticLockingException, ValidationException {
        String id = s9992;
        OrderableItemVo newVo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
        OrderableItemVo oldVo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);

        Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>();
        Collection<Difference> differences = oldVo.diff(newVo);

        for (Difference diff : differences) {
            ModDifferenceVo modDiff = new ModDifferenceVo();
            modDiff.setDifference(diff);
            modDiff.setModificationReason("test reason.");
            modDifferences.add(modDiff);
        }

        managedItemService.commitModifications(modDifferences, oldVo, PNM1);
        
    }

    /**
     * approve NOI
     * 
     * @throws Exception Exception
     */
    public void testApproveNOI() throws Exception {
        String id = s99950000;
        OrderableItemVo vo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);

        vo.setRequestItemStatus(RequestItemStatus.APPROVED);
        ManagedItemVo result = managedItemService.update(vo, PNM1);

        assertNotNull("Value null", result);
    }

    /**
     * Test method generateOINames on managedItemService
     * 
     * @throws Exception Exception
     */
    public void testGenerateOiNames() throws Exception {
        final String GENERIC_NAME = "CODEINE";
        final String DOSAGE_FORM = "GEL";

        OrderableItemVo orderableItemVo = (OrderableItemVo) managedItemService
            .retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);

        // Set mandatory fields
        orderableItemVo.setLocal();
        orderableItemVo.setItemStatus(ItemStatus.ACTIVE);

        // Set Dosage Form
        DosageFormVo dosageForm = new DosageFormVo();
        dosageForm.setId("99918");
        dosageForm.setDosageFormName(DOSAGE_FORM);
        orderableItemVo.setDosageForm(dosageForm);

        // Set Generic Name
        GenericNameVo genericName = new GenericNameVo();
        genericName.setId(s9992);
        genericName.setValue(GENERIC_NAME);
        orderableItemVo.setGenericName(genericName);

        // Retrieve Oi Name and VistA OI name
        OrderableItemVo generatedOI = (OrderableItemVo) managedItemService.generateOINames(orderableItemVo);

        // Verify OI name and VistA OI name set correctly
        assertEquals("VistA OI name is not correctly set", GENERIC_NAME, generatedOI.getVistaOiName());
        assertEquals("OI name is not corerctly set", GENERIC_NAME + " " + DOSAGE_FORM, generatedOI.getOiName());
    }

    /**
     * update OI IV flag
     * 
     * @throws Exception Exception
     */
    public void testModifyOiIvFlag() throws Exception {
        String id = s99950000;
        OrderableItemVo newVo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
        OrderableItemVo oldVo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
        DataField<Boolean> oiIvFlag = newVo.getVaDataFields().getDataField(FieldKey.OI_IV_FLAG);

        if (oiIvFlag == null) {
            oiIvFlag = DataField.newInstance(FieldKey.OI_IV_FLAG);
            oiIvFlag.selectValue(true);
            newVo.getVaDataFields().setDataField(oiIvFlag);
        } else if (oiIvFlag.getValue()) {
            oiIvFlag.selectValue(false);
            newVo.getVaDataFields().setDataField(oiIvFlag);
        } else {
            oiIvFlag.selectValue(true);
            newVo.getVaDataFields().setDataField(oiIvFlag);
        }

        Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>();
        Collection<Difference> differences = oldVo.diff(newVo);

        for (Difference diff : differences) {
            ModDifferenceVo modDiff = new ModDifferenceVo();
            modDiff.setDifference(diff);
            modDiff.setModificationReason("test reason");
            modDifferences.add(modDiff);
        }

        managedItemService.commitModifications(modDifferences, oldVo, PNM1);

        OrderableItemVo retrievedVo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);

        assertEquals("Oi IV Flag was wrong", oiIvFlag.getValue(), retrievedVo.getVaDataFields().getDataField(
            FieldKey.OI_IV_FLAG).getValue());
    }

    /**
     * testAddNOIFromBlankTemplate
     * 
     * @throws Exception Exception
     */
    public void testAddNOIFromBlankTemplate() throws Exception {

        OrderableItemVo orderableItemVo = (OrderableItemVo) managedItemService
            .retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);

        setupOI(orderableItemVo, true);
        ProcessedItemVo processItem = managedItemService.create(orderableItemVo, PNM1);
        OrderableItemVo insertedVo = (OrderableItemVo) processItem.getItem();
        assertNotNull(" ID should not be null", insertedVo.getId());

        OrderableItemVo retrievedVo = (OrderableItemVo) managedItemService.retrieve(insertedVo.getId(),
            EntityType.ORDERABLE_ITEM);

        assertNull("NOI should not have parent", retrievedVo.getOrderableItemParent());
        assertTrue("NOI's should have National type", retrievedVo.isNational());
        assertEquals("NOI New Item Request from PNM should be pending", RequestItemStatus.PENDING, retrievedVo
            .getRequestItemStatus());
        assertEquals("Item status incorrect", retrievedVo.getItemStatus(), orderableItemVo.getItemStatus());
        assertEquals("Dosage form incorrect", retrievedVo.getDosageForm().getDosageFormName(), orderableItemVo.getDosageForm()
            .getDosageFormName());
        assertEquals("OI Name incorrect", retrievedVo.getOiName(), orderableItemVo.getOiName());
        assertEquals("VISTA OI Name incorrect", retrievedVo.getVistaOiName(), orderableItemVo.getVistaOiName());
        assertEquals("National Formulary Indicator incorrect", retrievedVo.getNationalFormularyIndicator(), orderableItemVo
            .getNationalFormularyIndicator());

        // OI-Drug Text entry
        Collection<DrugTextVo> inserterDrugText = orderableItemVo.getLocalDrugTexts();
        Collection<DrugTextVo> retrievedDrugText = retrievedVo.getLocalDrugTexts();

        for (DrugTextVo idrugTxt : inserterDrugText) {
            idrugTxt = (DrugTextVo) managedItemService.retrieve(idrugTxt.getId(), EntityType.DRUG_TEXT);

            assertTrue("incorrect drug text", retrievedDrugText.contains(idrugTxt));
        }

        // go through all the va data fields for orderable item and check that the values set match the values retrieved
        Collection<FieldKey> fields = FieldKey.getOrderableItemVaDataFields();

        for (FieldKey key : fields) {
            LOG.debug(key);

            if (key.isGroupDataField()) {
                GroupDataField groupDf = (GroupDataField) orderableItemVo.getVaDataFields().getDataField(key);
                GroupDataField savedGroupDf = (GroupDataField) retrievedVo.getVaDataFields().getDataField(key);
                assertEquals(" data field " + key + "  incorrect", groupDf, savedGroupDf);
            }

            if (key.isListDataField()) {
                ListDataField list = (ListDataField) orderableItemVo.getVaDataFields().getDataField(key);
                ListDataField savedList = (ListDataField) retrievedVo.getVaDataFields().getDataField(key);

                if (list != null && savedList != null) {
                    for (Object selection : list.getValue()) {
                        assertTrue("incorrect list data field " + key, savedList.contains((String) selection));
                    }

                    // check that the editable property of the list data fields are correct
                    assertEquals("incorrect editable value of list data field " + key, list.isEditable(), savedList
                        .isEditable());
                }
            } else if (key.isPrimaryKeyDataField()) {
                assertEquals("primary key data field " + key + " incorrect.", orderableItemVo.getVaDataFields().getDataField(
                    key).getUniqueId(), retrievedVo.getVaDataFields().getDataField(key).getUniqueId());
            } else {
                if (orderableItemVo.getVaDataFields().getDataField(key) != null) {
                    assertEquals("data field " + key + " incorrect", orderableItemVo.getVaDataFields().getDataField(key)
                        .getValue(), retrievedVo.getVaDataFields().getDataField(key).getValue());
                }

            }
        }

    }

    /**
     * Add NOI from existing inactive template
     * 
     * @throws Exception Exception
     */
    public void testAddNOIFromExistingInactiveTemplate() throws Exception {
        OrderableItemVo orderableItem = (OrderableItemVo) managedItemService.retrieveTemplate(s9992,
            EntityType.ORDERABLE_ITEM);
        makeUnique(orderableItem);
        orderableItem.inactivate();
        orderableItem = (OrderableItemVo) managedItemService.create(orderableItem, PNM1).getItem();
        assertNotNull("ID should not be null ", orderableItem.getId());

        orderableItem = (OrderableItemVo) managedItemService.retrieveTemplate(orderableItem.getId(),
            EntityType.ORDERABLE_ITEM);
        makeUnique(orderableItem);
        orderableItem = (OrderableItemVo) managedItemService.create(orderableItem, PNM1).getItem();
        assertNotNull("ID should not be null! ", orderableItem.getId());

        OrderableItemVo retrieved = (OrderableItemVo) managedItemService.retrieve(orderableItem.getId(),
            EntityType.ORDERABLE_ITEM);

        assertSameNoi(orderableItem, retrieved);
    }

    /**
     * when National formulary indicator is "Yes" formulary status is set to "Formulary" when National formulary indicator is
     * "No" formulary status is set to "Non-Formulary"
     * 
     * @throws Exception Exception
     */
    public void testFormularyStatus() throws Exception {

        // create a new orderable item from existing templeate
        OrderableItemVo orderableItemVo = (OrderableItemVo) managedItemService.retrieveTemplate(s9991,
            EntityType.ORDERABLE_ITEM);
        setupOI(orderableItemVo, true);
        ProcessedItemVo processItem = managedItemService.create(orderableItemVo, PNM1);
        OrderableItemVo insertedVo = (OrderableItemVo) processItem.getItem();
        assertNotNull("ID should not be null", insertedVo.getId());

        // retrieve the request for the newly created orderable item
        Collection<RequestVo> colRequest = requestService.retrieve(insertedVo.getId(), EntityType.ORDERABLE_ITEM);
        List<RequestVo> listRequest = new ArrayList<RequestVo>();
        listRequest.addAll(colRequest);

        // there should be only one request in this collection
        assertEquals("Add request was not created", 1, colRequest.size());

        // approve the orderable item
        // approval is done by pnm2
        RequestVo request = listRequest.get(0);
        OrderableItemVo oi = (OrderableItemVo) managedItemService.retrieve(request.getItemId(), EntityType.ORDERABLE_ITEM);
        ProcessedRequestVo processedRequest = managedItemService.approveRequest(oi, request, Collections.EMPTY_LIST,
            new UserGenerator().getNationalManagerTwo());
        oi = (OrderableItemVo) processedRequest.getItem();
        request = processedRequest.getRequest();

        processedRequest = managedItemService.commitRequest(oi, request, Collections.EMPTY_LIST, new UserGenerator()
            .getNationalManagerTwo(), false);

        // retrieve the ordereable item and check that the item is approved
        OrderableItemVo retrievedOi = (OrderableItemVo) managedItemService.retrieve(request.getItemId(),
            EntityType.ORDERABLE_ITEM);
        assertEquals("Approval of the new orderable item failed", RequestItemStatus.APPROVED, retrievedOi
            .getRequestItemStatus());

        // now modify this orderable item
        OrderableItemVo newOrderableItem = retrievedOi.copy();

        // flip the value of national formulary indicator
        newOrderableItem.setNationalFormularyIndicator(!retrievedOi.getNationalFormularyIndicator());

        // commit the change
        ProcessedItemVo processedItem = managedItemService.commitModifications(retrievedOi
            .compareDifferences(newOrderableItem), retrievedOi, new UserGenerator().getNationalManagerOne());
        OrderableItemVo updatedItem = (OrderableItemVo) processedItem.getItem();
        assertEquals("National formulary indicator should not have been updated", retrievedOi
            .getNationalFormularyIndicator(), updatedItem.getNationalFormularyIndicator());

        // national formulary indicator is a second review field
        // a mod request will be created
        Collection<RequestVo> colReq = requestService.retrieve(retrievedOi.getId(), EntityType.ORDERABLE_ITEM);

        RequestVo modRequest = null;

        for (RequestVo req : colReq) {
            if (RequestType.MODIFICATION.equals(req.getRequestType())) {
                modRequest = req;
            }
        }

        assertNotNull("A Mod Request should have been generated", modRequest);

        // approve the mod request
        ProcessedRequestVo processedModReq = managedItemService.submitRequest(updatedItem, modRequest,
            Collections.EMPTY_LIST, new UserGenerator().getNationalManagerTwo());
        updatedItem = (OrderableItemVo) processedModReq.getItem();
        modRequest = processedModReq.getRequest();

        processedModReq = managedItemService.commitRequest(updatedItem, modRequest, Collections.EMPTY_LIST,
            new UserGenerator().getNationalManagerTwo(), false);

        // retrieve the updated item
        // OrderableItemVo updatedOrdItem = (OrderableItemVo) 
        managedItemService.retrieve(modRequest.getItemId(), EntityType.ORDERABLE_ITEM);

    }

    // /**
    // * update OI route
    // *
    // * @throws Exception
    // */
    // public void testModifyOiRoutes() throws Exception {
    // String id = s9992;
    // OrderableItemVo newVo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
    // OrderableItemVo oldVo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
    //
    // Collection<OiRouteVo> routes = newVo.getOiRoute();
    //
    // OiRouteVo oiRoute = new OiRouteVo();
    // LocalMedicationRouteVo route = new LocalMedicationRouteVo();
    // route.setId("9998");
    // route.setValue("RECTAL");
    //        
    // route.setRequestItemStatus(RequestItemStatus.APPROVED);
    // oiRoute.setLocalMedicationRoute(route);
    // routes.removeAll(routes);
    // routes.add(oiRoute);
    // newVo.setOiRoute(routes);
    //
    // Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>();
    // Collection<Difference> differences = oldVo.diff(newVo);
    //
    // for (Difference diff : differences) {
    // ModDifferenceVo modDiff = new ModDifferenceVo();
    // modDiff.setDifference(diff);
    // modDiff.setModificationReason("test reason");
    // modDifferences.add(modDiff);
    // }
    //
    // managedItemService.commitModifications(modDifferences, oldVo, PNM1);
    //
    // OrderableItemVo retrievedVo = (OrderableItemVo) managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
    //
    // assertEquals("Oi route was wrong", routes.size(), retrievedVo.getOiRoute().size());
    // }
    //
}
