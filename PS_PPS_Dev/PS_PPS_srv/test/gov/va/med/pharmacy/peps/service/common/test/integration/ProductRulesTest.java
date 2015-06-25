/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.test.integration;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.DispenseUnitPerDoseGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.PossibleDosagesPackageGenerator;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.NationalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.OiScheduleTypeVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;


/**
 * this class tests the cross item rules for product and oi
 */
public class ProductRulesTest extends IntegrationTestCase {

    private static final String S9992 = "9992";
    
    //    private ManagedItemService localManagedItemService;
    private ManagedItemService nationalManagedItemService;
    private DosageFormVo dosageForm;
    private DrugUnitVo drugUnit;
    private List<DispenseUnitPerDoseVo> dispenseUnitsPerDose;

    /**
     * setup the test
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
     

     //   this.localManagedItemService = getLocalOneService(ManagedItemService.class);
        this.nationalManagedItemService = getNationalService(ManagedItemService.class);
        getNationalService(RequestService.class);

    }

//    /**
//     * In the following test case a new NOI is created. Two products are added to the NOI, (1)product1 with application
//     * package use set to a value other than "non-va med", and (2)product2 with application package set to "non-va
//     * med".Adding product2 makes the non-va med of the orderable item true. Then product2 is rejected by the national
//     * manager and this makes the non-va med of the orderable item false.
//     * 
//     * @throws Exception
//     */
//    public void testNonVaMedRulesByRejectingProductItem() throws Exception {
//
//        // create a new NOI
//        OrderableItemVo orderableItem = (OrderableItemVo) localManagedItemService.retrieveTemplate(S9992,
//            EntityType.ORDERABLE_ITEM);
//        makeUnique(orderableItem);
//        orderableItem.setNonVaMed(true);
//        orderableItem = (OrderableItemVo) localManagedItemService.create(orderableItem, PLM1).getItem();
//        assertNotNull("The NOI ID should not be null", orderableItem.getId());
//
//        System.out.println("OI '" + orderableItem.toShortString() + "' with ID '" + orderableItem.getId()
//            + "' created. Waiting for Local to send to National...");
//        Thread.sleep(20000);
//
//        orderableItem = (OrderableItemVo) localManagedItemService.retrieve(orderableItem.getId(), EntityType.ORDERABLE_ITEM);
//        assertTrue("The value of Non-VA Med should be true", orderableItem.getNonVaMed());
//
//        // add a new product to the orderable item with the value of application package use set to something other
//        // than non-va med. the value of non-va med for the orderable item would be false
//        ProductVo product1 = (ProductVo) localManagedItemService.retrieveTemplate(S9992, EntityType.PRODUCT);
//        makeUnique(product1);
//        product1.setAtcMnemonic(null);
//        product1.setOrderableItem(orderableItem);
//        product1.setPreviousOrderableItem(null);
//
//        // set the application package use
//        ListDataField<String> appPkgUse = product1.getDataFields().getDataField(FieldKey.APPLICATION_PACKAGE_USE);
//        appPkgUse.unselect();
//        appPkgUse.addStringSelection("O-OUTPATIENT");
//
//        // insert the product
//        product1 = (ProductVo) localManagedItemService.create(product1, PLM1).getItem();
//
//        System.out.println("Product1 '" + product1.toShortString() + "' with ID '" + product1.getId()
//            + "' created. Waiting for Local to send to National...");
//        Thread.sleep(20000);
//
//        // retrieve the NOI again to test the value of formulary status
//        orderableItem = (OrderableItemVo) localManagedItemService.retrieve(orderableItem.getId(), EntityType.ORDERABLE_ITEM);
//        assertFalse("The value of Non-VA Med should be false", orderableItem.getNonVaMed());
//
//        // add another product with the value of application package use "non-va med".
//        // the value of non-va med for the orderable item would be true
//        ProductVo product2 = (ProductVo) localManagedItemService.retrieveTemplate("9991", EntityType.PRODUCT);
//        makeUnique(product2);
//        product2.setAtcMnemonic(null);
//        product2.setOrderableItem(orderableItem);
//        product2.setPreviousOrderableItem(null);
//
//        // set the application package use
//        ListDataField<String> appPkgUse2 = product2.getDataFields().getDataField(FieldKey.APPLICATION_PACKAGE_USE);
//        appPkgUse2.unselect();
//        appPkgUse2.addStringSelection("X-NON-VA MED");
//
//        // insert the product
//        product2 = (ProductVo) localManagedItemService.create(product2, PLM1).getItem();
//
//        System.out.println("Product2 '" + product2.toShortString() + "' with ID '" + product2.getId()
//            + "' created. Waiting for Local to send to National...");
//        Thread.sleep(20000);
//
//        // retrieve the NOI again to test the value of non-va med
//        orderableItem = (OrderableItemVo) localManagedItemService.retrieve(orderableItem.getId(), EntityType.ORDERABLE_ITEM);
//        assertTrue("The value of Non-VA Med should be true", orderableItem.getNonVaMed());
//
//        // reject product 2
//        // after this the non-va med of the orderable item would be set to false again
//        Collection<RequestVo> colRequest = requestService.retrieve(product2.getId(), EntityType.PRODUCT);
//        List<RequestVo> listRequest = new ArrayList<RequestVo>();
//        listRequest.addAll(colRequest);
//
//        // there should be only one request in this collection
//        assertEquals("Add request was not created", 1, colRequest.size());
//
//        // reject the product item
//        // rejection is done by pnm2
//        RequestVo request = listRequest.get(0);
//        System.out.println("Request made for Product2 with ID '" + request.getId() + "'");
//
//        product2 = (ProductVo) nationalManagedItemService.retrieve(request.getItemId(), EntityType.PRODUCT);
//        request = nationalManagedItemService.rejectRequest(product2, request, Collections.EMPTY_LIST, PNM2);
//        request.setRequestRejectReason(RequestRejectionReason.INAPPROPRIATE_REQUEST);
//
//        ProcessedRequestVo processedRequest = nationalManagedItemService.commitRequest(product2, request,
//            Collections.EMPTY_LIST, PNM2);
//        product2 = (ProductVo) processedRequest.getItem();
//
//        System.out.println("Rejected Product2 '" + product2.toShortString() + "' with ID '" + product2.getId()
//            + "', status is now '" + product2.getItemStatus() + "'. Waiting for National to send update to Locals...");
//        Thread.sleep(20000);
//
//        // retrieve the product2 and check the item status is "Inactive"
//        product2 = (ProductVo) nationalManagedItemService.retrieve(product2.getId(), EntityType.PRODUCT);
//        assertEquals("Product2 should be rejected", RequestItemStatus.REJECTED, product2.getRequestItemStatus());
//        assertEquals("Product2 should be inactive", ItemStatus.INACTIVE, product2.getItemStatus());
//
//        // retrieve the NOI again to test the value of non-va med
//        orderableItem = (OrderableItemVo) localManagedItemService.retrieve(orderableItem.getId(), EntityType.ORDERABLE_ITEM);
//        assertFalse("The value of Non-VA Med should be false", orderableItem.getNonVaMed());
//    }

//    /**
//     * In the following test case a new NOI is created. Two products are added to the NOI, (1)product1 with application
//     * package use set to a value other than "non-va med", and (2)product2 with application package set to "non-va
//     * med".Because of product2, the non-va med of the orderable item becomes "true". product is approved and then
//     * in-activated by the national manager. This makes the non-va med of the orderable item false. Then product2 is
//     * activated by the national manager and this makes the non-va med of the orderable item true again.
//     * 
//     * @throws Exception
//     */
////    public void testNonVAMedRuleByInactivatingAndActivatingProduct() throws Exception {
//        // create a new NOI
//        OrderableItemVo orderableItem = (OrderableItemVo) localManagedItemService.retrieveTemplate(S9992,
//            EntityType.ORDERABLE_ITEM);
//        makeUnique(orderableItem);
//        orderableItem.setNonVaMed(true);
//        orderableItem = (OrderableItemVo) localManagedItemService.create(orderableItem, PLM1).getItem();
//        assertNotNull("The NOI ID should not be null", orderableItem.getId());
//
//        System.out.println("Waiting for OI create message to be sent from Local to National...");
//        Thread.sleep(20000);
//
//        OrderableItemVo retrievedOi = (OrderableItemVo) localManagedItemService.retrieve(orderableItem.getId(),
//            EntityType.ORDERABLE_ITEM);
//        assertTrue("The value of Non-VA Med should be true", retrievedOi.getNonVaMed());
//
//        // add a new product to the orderable item with the value of application package use set to something other
//        // than non-va med. the value of non-va med for the orderable item would be false
//        ProductVo product1 = (ProductVo) localManagedItemService.retrieveTemplate(S9992, EntityType.PRODUCT);
//        makeUnique(product1);
//        product1.setAtcMnemonic(null);
//        product1.setOrderableItem(orderableItem);
//        product1.setPreviousOrderableItem(null);
//
//        // set the application package use
//        DataFields<DataField> vadfs = product1.getDataFields();
//        ListDataField<String> appPkgUse = vadfs.getDataField(FieldKey.APPLICATION_PACKAGE_USE);
//        appPkgUse.unselect();
//        appPkgUse.addStringSelection("O-OUTPATIENT");
//
//        // insert the product
//        localManagedItemService.create(product1, PLM1);
//
//        System.out.println("Waiting for Product 1 create message to be sent from Local to National...");
//        Thread.sleep(20000);
//
//        // retrieve the NOI again to test the value of non-va med
//        retrievedOi = (OrderableItemVo) localManagedItemService.retrieve(retrievedOi.getId(), EntityType.ORDERABLE_ITEM);
//        assertFalse("The value of Non-VA Med should be false", retrievedOi.getNonVaMed());
//
//        // add another product with the value of application package use "non-va med".
//        // the value of non-va med for the orderable item would be true
//        ProductVo product2 = (ProductVo) localManagedItemService.retrieveTemplate(S9992, EntityType.PRODUCT);
//        makeUnique(product2);
//        product2.setAtcMnemonic(null);
//        product2.setOrderableItem(retrievedOi);
//        product2.setPreviousOrderableItem(null);
//
//        // set the application package use
//        vadfs = product2.getDataFields();
//        ListDataField<String> appPkgUse2 = vadfs.getDataField(FieldKey.APPLICATION_PACKAGE_USE);
//        appPkgUse2.unselect();
//        appPkgUse2.addStringSelection("X-NON-VA MED");
//
//        // insert the product
//        product2 = (ProductVo) localManagedItemService.create(product2, PLM1).getItem();
//
//        System.out.println("Waiting for Product 2 create message to be sent from Local to National...");
//        Thread.sleep(20000);
//
//        // retrieve the NOI again to test the value of non-va med
//        retrievedOi = (OrderableItemVo) localManagedItemService.retrieve(retrievedOi.getId(), EntityType.ORDERABLE_ITEM);
//        assertTrue("The value of Non-VA Med should be true", retrievedOi.getNonVaMed());
//
//        // approve product2
//        // after this the non-va med of the orderable item would be set to false again
//        Collection<RequestVo> colRequest = requestService.retrieve(product2.getId(), EntityType.PRODUCT);
//        List<RequestVo> listRequest = new ArrayList<RequestVo>();
//        listRequest.addAll(colRequest);
//
//        // there should be only one request in this collection
//        assertEquals("Add request was not created", 1, colRequest.size());
//
//        // approve the product item
//        // 1st approval by PNM1
//        RequestVo request = listRequest.get(0);
//        product2 = (ProductVo) nationalManagedItemService.retrieve(request.getItemId(), EntityType.PRODUCT);
//        ProcessedRequestVo processedRequest = nationalManagedItemService.approveRequest(product2, request,
//            Collections.EMPTY_LIST, PNM1);
//
//        request = processedRequest.getRequest();
//        product2 = (ProductVo) processedRequest.getItem();
//        processedRequest = nationalManagedItemService.commitRequest(product2, request, Collections.EMPTY_LIST, PNM1);
//
//        System.out.println("Waiting for Product 2 approval...");
//        Thread.sleep(20000);
//
//        // retrieve the NOI again to test the value of non-va med
//        retrievedOi = (OrderableItemVo) nationalManagedItemService.retrieve(retrievedOi.getId(), EntityType.ORDERABLE_ITEM);
//        System.out.println("Product parent OI Non-VA Med: " + product2.getOrderableItem().getNonVaMed());
//        System.out.println("Retrieved OI Non-VA Med: " + retrievedOi.getNonVaMed());
//        assertTrue("The value of Non-VA Med should be true", retrievedOi.getNonVaMed());
//
//        // retrieve the product2 and the request
//        product2 = (ProductVo) nationalManagedItemService.retrieve(product2.getId(), EntityType.PRODUCT);
//        colRequest = requestService.retrieve(product2.getId(), EntityType.PRODUCT);
//        listRequest.clear();
//        listRequest.addAll(colRequest);
//
//        // there should be only one request in this collection
//        assertEquals("There shold be only one request", 1, colRequest.size());
//
//        // approve the request for the 2nd time by PNM2
//        processedRequest = nationalManagedItemService.approveRequest(product2, request, Collections.EMPTY_LIST,
//            new UserGenerator().getNationalManagerTwo());
//
//        request = processedRequest.getRequest();
//        product2 = (ProductVo) processedRequest.getItem();
//
//        processedRequest = nationalManagedItemService.commitRequest(product2, request, Collections.EMPTY_LIST,
//            new UserGenerator().getNationalManagerTwo());
//
//        // retrieve the product2
//        product2 = (ProductVo) nationalManagedItemService.retrieve(product2.getId(), EntityType.PRODUCT);
//
//        // check that the product item is approved
//        assertEquals("The product 2 request item status should be Approved", RequestItemStatus.APPROVED, product2
//            .getRequestItemStatus());
//
//        // Inactivate Product2
//        ProductVo newProduct2 = product2.copy();
//        newProduct2.setItemStatus(ItemStatus.INACTIVE);
//        ProcessedItemVo processedItem = nationalManagedItemService.commitModifications(product2
//            .compareDifferences(newProduct2), product2, new UserGenerator().getNationalManagerOne());
//
//        // item status is a second review field, therefore mod request is generated
//
//        assertEquals("Item status should be Active", ItemStatus.ACTIVE, ((ProductVo) processedItem.getItem())
//            .getItemStatus());
//
//        // check mod request is created
//        colRequest = requestService.retrieve(product2.getId(), EntityType.PRODUCT);
//        listRequest = new ArrayList<RequestVo>();
//        listRequest.addAll(colRequest);
//        RequestVo modRequest = null;
//
//        for (RequestVo req : colRequest) {
//            if (RequestType.MODIFICATION.equals(req.getRequestType())) {
//                modRequest = req;
//            }
//        }
//
//        // there should be only one request in this collection
//        assertNotNull("Modification request was not created", modRequest);
//
//        // PNM2 approves the mod request
//        product2 = (ProductVo) nationalManagedItemService.retrieve(product2.getId(), EntityType.PRODUCT);
//        processedRequest = nationalManagedItemService.submitRequest(product2, modRequest, Collections.EMPTY_LIST,
//            new UserGenerator().getNationalManagerTwo());
//        modRequest = processedRequest.getRequest();
//        product2 = (ProductVo) processedRequest.getItem();
//
//        processedRequest = nationalManagedItemService.commitRequest(product2, modRequest, Collections.EMPTY_LIST,
//            new UserGenerator().getNationalManagerTwo());
//
//        // retrieve the product
//        product2 = (ProductVo) nationalManagedItemService.retrieve(product2.getId(), EntityType.PRODUCT);
//        assertEquals("product2 Item status should be Inactive", ItemStatus.INACTIVE, product2.getItemStatus());
//
//        // reactivate the inactive item
//        newProduct2 = product2.copy();
//        newProduct2.setItemStatus(ItemStatus.ACTIVE);
//        processedItem = nationalManagedItemService.commitModifications(product2.compareDifferences(newProduct2), product2,
//            new UserGenerator().getNationalManagerOne());
//
//        // item status is a second review field, therefore mod request is generated
//
//        assertEquals("Item status should be Inactive", ItemStatus.INACTIVE, ((ProductVo) processedItem.getItem())
//            .getItemStatus());
//
//        // check new mod request is created
//        colRequest = requestService.retrieve(product2.getId(), EntityType.PRODUCT);
//        listRequest = new ArrayList<RequestVo>();
//        listRequest.addAll(colRequest);
//        RequestVo newModRequest = null;
//
//        for (RequestVo req : colRequest) {
//            if (RequestType.MODIFICATION.equals(req.getRequestType())
//                && (req.getRequestState().equals(RequestState.PENDING_SECOND_REVIEW))) {
//                newModRequest = req;
//            }
//        }
//
//        // there should be only one request in this collection
//        assertNotNull("New Modification request was not created", newModRequest);
//
//        // PNM2 approves the mod request
//        product2 = (ProductVo) nationalManagedItemService.retrieve(product2.getId(), EntityType.PRODUCT);
//        processedRequest = nationalManagedItemService.submitRequest(product2, newModRequest, Collections.EMPTY_LIST,
//            new UserGenerator().getNationalManagerTwo());
//        newModRequest = processedRequest.getRequest();
//        processedRequest = nationalManagedItemService.commitRequest(product2, newModRequest, Collections.EMPTY_LIST,
//            new UserGenerator().getNationalManagerTwo());
//
//        // retrieve the product
//        product2 = (ProductVo) nationalManagedItemService.retrieve(product2.getId(), EntityType.PRODUCT);
//        assertEquals("product2 Item status should be Active", ItemStatus.ACTIVE, product2.getItemStatus());
//
//        // retrieve the NOI again to test the value of non-va med
//        retrievedOi = (OrderableItemVo) nationalManagedItemService.retrieve(retrievedOi.getId(), EntityType.ORDERABLE_ITEM);
//        assertTrue("The value of Non-VA Med should be True", retrievedOi.getNonVaMed());
//
//    }

//    /**
//     * In this test we create a new NOI. A product is added to the orderable item with application package use set to a value
//     * other than non-va med. The value of the application package use of the product is later modified at the local to
//     * "non-va" med. In this case after the product is modified the value of non-va med for the orderable item is "true" at
//     * local, while "false" at national
//     * 
//     * @throws Exception
//     */
//    public void testNonVAMedRulesByMOdifyingApplicationPackageUseAtLocal() throws Exception {
//
//        // create a new NOI
//        OrderableItemVo orderableItem = (OrderableItemVo) localManagedItemService.retrieveTemplate(S9992,
//            EntityType.ORDERABLE_ITEM);
//        makeUnique(orderableItem);
//
//        // set the non-va med to true
//        orderableItem.setNonVaMed(true);
//
//        // create the NOI
//        orderableItem = (OrderableItemVo) localManagedItemService.create(orderableItem, PLM1).getItem();
//        assertNotNull("The NOI ID should not be null", orderableItem.getId());
//        OrderableItemVo retrievedOi = (OrderableItemVo) localManagedItemService.retrieve(orderableItem.getId(),
//            EntityType.ORDERABLE_ITEM);
//        assertTrue("The value of Non-VA Med should be true", retrievedOi.getNonVaMed());
//
//        // add a new product to the orderable item with the value of application package use set to something other
//        // than non-va med. the value of non-va med for the orderable item would be false
//        ProductVo product1 = (ProductVo) localManagedItemService.retrieveTemplate(S9992, EntityType.PRODUCT);
//        makeUnique(product1);
//        product1.setAtcMnemonic(null);
//
//        product1.setOrderableItem(orderableItem);
//        product1.setPreviousOrderableItem(null);
//
//        // set the application package use
//        DataFields<DataField> vadfs = product1.getDataFields();
//        ListDataField<String> appPkgUse = vadfs.getDataField(FieldKey.APPLICATION_PACKAGE_USE);
//        appPkgUse.unselect();
//        appPkgUse.addStringSelection("O-OUTPATIENT");
//
//        // insert the product
//        product1 = (ProductVo) localManagedItemService.create(product1, PLM1).getItem();
//        Thread.sleep(30000);
//
//        // retrieve the NOI both at local and national to test the value of non-va med
//        OrderableItemVo localOi = (OrderableItemVo) localManagedItemService.retrieve(retrievedOi.getId(),
//            EntityType.ORDERABLE_ITEM);
//        assertFalse("The value of Non-VA Med should be false", localOi.getNonVaMed());
//        OrderableItemVo nationalOi = (OrderableItemVo) nationalManagedItemService.retrieve(retrievedOi.getId(),
//            EntityType.ORDERABLE_ITEM);
//        assertFalse("The value of Non-VA Med should be false", nationalOi.getNonVaMed());
//
//        // at local modify the value of application package use to add "non-va med"
//        ProductVo newProduct1 = product1.copy();
//        newProduct1.getDataFields().getDataField(FieldKey.APPLICATION_PACKAGE_USE).unselect();
//        newProduct1.getDataFields().getDataField(FieldKey.APPLICATION_PACKAGE_USE).addStringSelection("X-NON-VA MED");
//
//        // commit the modification
//        ProcessedItemVo processedItem = localManagedItemService.commitModifications(
//            product1.compareDifferences(newProduct1), product1, new UserGenerator().getLocalManagerOne());
//        Thread.sleep(10000);
//
//        // retrieve the NOI both at local and national and test the value of non-va med
//        localOi = (OrderableItemVo) localManagedItemService.retrieve(retrievedOi.getId(), EntityType.ORDERABLE_ITEM);
//        assertTrue("The value of Non-VA Med should be true", localOi.getNonVaMed());
//
//        nationalOi = (OrderableItemVo) nationalManagedItemService.retrieve(retrievedOi.getId(), EntityType.ORDERABLE_ITEM);
//        assertFalse("The value of Non-VA Med should be false", nationalOi.getNonVaMed());
//
//    }

    /**
     * A new single ingredient product is created that satisfies all the pre-conditions for auto creation of possible
     * dosages. The product is approved and then checked to see that the possible dosages have been created.
     * 
     * @throws Exception Exception
     */
    public void testCreatePossibleDosagesAutomatically() throws Exception {
        buildDosageForm();

        // create the dosage form
        dosageForm = (DosageFormVo) nationalManagedItemService.create(dosageForm, PNM1).getItem();

        assertNotNull("dosage form was not created.", dosageForm.getId());

        // create a new NOI
        OrderableItemVo orderableItem = buildNOIFromExisting(S9992, dosageForm);

        // create the NOI
        orderableItem = (OrderableItemVo) nationalManagedItemService.create(orderableItem, PNM1).getItem();
        assertNotNull("orderable item was not created.", orderableItem.getId());

        // make Product
        ProductVo product = buildSinglIngredientProductFromExisting(S9992, orderableItem, drugUnit);

        // create the product
        product = (ProductVo) nationalManagedItemService.create(product, PNM1).getItem();

        // check that possible dosages were not created
        product = (ProductVo) nationalManagedItemService.retrieve(product.getId(), EntityType.PRODUCT);
        Collection<NationalPossibleDosagesVo> colPossibleDosages = product.getNationalPossibleDosages();

        if (colPossibleDosages != null) {
            assertTrue("Possible dosages be empty", colPossibleDosages.isEmpty());
        }

        // retrieve the product and check that product is approved
        product = (ProductVo) nationalManagedItemService.retrieve(product.getId(), EntityType.PRODUCT);
        assertEquals("The product was not approved", RequestItemStatus.APPROVED, product.getRequestItemStatus());

        // check that possible dosages were created
        colPossibleDosages = product.getNationalPossibleDosages();
        assertNotNull("Possible Dosages should not be null", colPossibleDosages);
        assertFalse("Possible dosages should not be empty", colPossibleDosages.isEmpty());
        assertEquals("Incorrect number of rows in Possible Dosages MDF created", dispenseUnitsPerDose.size(), product
            .getNationalPossibleDosages().size());

        // List<NationalPossibleDosagesVo> possibleDosages = new ArrayList<NationalPossibleDosagesVo>();
        // possibleDosages.addAll(product.getNationalPossibleDosages());
        //
        // for (int i = 0; i < possibleDosages.size(); i++) {
        //
        // assertEquals("possibleDosagesDispenseUnitsPerDose value not correct", dispenseUnitsPerDose.get(i)
        // .getStrDispenseUnitPerDose(), possibleDosages.get(i).getPossibleDosagesDispenseUnitsPerDose().toString());
        //
        // // Collection<PossibleDosagesPackageVo> possibledosage = possibleDosages.get(i).getPossibleDosagePackage();
        // // assertEquals("possibleDosagesPackage value not correct", lstPossibleDosages.get(0).getValue(),
        // // possibleDosages.get(i).getPossibleDosagePackage());
        // }
    }

    /**
     * this method tests that possible dosages can be manually added to a product that has been already been approved when
     * certain criteria are met in this test case all the criteria are met and the add is successful in this test. Also we
     * test the modification to the possible dosages: 1) the package value of the possible dosages is modified to a value
     * that does exist in the package field of the matching dosage form unit(that matches with the ingredient unit of the
     * single-ingredient product) of the units MDF of the dosage form of the associated OI . This case modification is
     * successful. 2)the package value of the possible dosages is modified to a value that does not exist in the package
     * field of the matching dosage form unit(that matches with the ingredient unit of the single-ingredient product) of the
     * units MDF of the dosage form of the associated OI . This case modification is not successful.
     * 
     * @throws Exception Exception
     */
    public void testPossibleDosagesManualAdded() throws Exception {

        buildDosageForm();

        // remove the dispense units per dose because we do not want possible dosages to be automatically added
        dosageForm.setDfDispenseUnitsPerDose(null);

        // create the dosage form
        dosageForm = (DosageFormVo) nationalManagedItemService.create(dosageForm, PNM1).getItem();
        assertNotNull("dosage form was not created!", dosageForm.getId());
        OrderableItemVo orderableItem = buildNOIFromExisting(S9992, dosageForm);
        OiScheduleTypeVo scheduleType = new OiScheduleTypeVo();
        scheduleType.setId("1");
        orderableItem.setOiScheduleType(scheduleType);

        // create the NOI
        orderableItem = (OrderableItemVo) nationalManagedItemService.create(orderableItem, PNM1).getItem();
        assertNotNull("orderable item was not created!", orderableItem.getId());



    }

    /**
     * this method tests that possible dosages can not be manually added to a product when all the criteria are not met In
     * the following test case we show that the ingredient unit of the product does not match with any of the units of units
     * multiple of the dosage form of the associated orderable item.
     * 
     * 
     * 
     * @throws Exception Exception
     */
    public void testPossibleDosagesManualAddFailed() throws Exception {

        buildDosageForm();

        // remove the dispense units per dose because we do not want possible dosages to be automatically added
        dosageForm.setDfDispenseUnitsPerDose(null);

        // create the dosage form
        dosageForm = (DosageFormVo) nationalManagedItemService.create(dosageForm, PNM1).getItem();

        assertNotNull("dosage form was not created", dosageForm.getId());

        // bulid the NOI
        OrderableItemVo orderableItem = buildNOIFromExisting(S9992, dosageForm);

        // add the schedule type to the orderable item
        OiScheduleTypeVo scheduleType = new OiScheduleTypeVo();
        scheduleType.setId("1");
        orderableItem.setOiScheduleType(scheduleType);

        // create the NOI
        orderableItem = (OrderableItemVo) nationalManagedItemService.create(orderableItem, PNM1).getItem();
        assertNotNull("orderable item was not created", orderableItem.getId());
        Thread.sleep(PPSConstants.I10000);

        drugUnit = (DrugUnitVo) nationalManagedItemService.retrieve("99977", EntityType.DRUG_UNIT);


    }

    /**
     * builds the dosage form with units multiple and dispense unit per dose multiple being populated
     * 
     */
    private void buildDosageForm() {

        try { 
            
            // create a new dosage form with units multiple and dispense units per dose
            dosageForm = (DosageFormVo) nationalManagedItemService.retrieve("99963", EntityType.DOSAGE_FORM);
            makeUnique(dosageForm);
    
            // add units multiple
            drugUnit = (DrugUnitVo) nationalManagedItemService.retrieve("999161", EntityType.DRUG_UNIT);
            List<PossibleDosagesPackageVo> lstPossibleDosages = new ArrayList<PossibleDosagesPackageVo>();
            lstPossibleDosages.add(new PossibleDosagesPackageGenerator().getFirst());
    
            DosageFormUnitVo dosageFormUnit = new DosageFormUnitVo();
            dosageFormUnit.setDrugUnit(drugUnit);
            dosageFormUnit.setPackages(lstPossibleDosages);
    
            List<DosageFormUnitVo> dfUnits = new ArrayList<DosageFormUnitVo>();
            dfUnits.add(dosageFormUnit);
            dosageForm.setDfUnits(dfUnits);
    
            // add dispense units per dose multiple
            dispenseUnitsPerDose = new DispenseUnitPerDoseGenerator().getList();
            dosageForm.setDfDispenseUnitsPerDose(dispenseUnitsPerDose);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    /**
     * builds the orderable item
     * 
     * @param id the id of the existing orderable item
     * @param dosageFormIn the dosageForm Vo
     * @return OrderableItemVo
     */
    private OrderableItemVo buildNOIFromExisting(String id, DosageFormVo dosageFormIn) {
        OrderableItemVo orderableItem;
     
        try {
            orderableItem = (OrderableItemVo) nationalManagedItemService.retrieveTemplate(id,
                EntityType.ORDERABLE_ITEM);
            makeUnique(orderableItem);

            // set the dosage form
            orderableItem.setDosageForm(dosageFormIn);

            return orderableItem;
        } catch (ItemNotFoundException e) {
            fail(e.toString());
        }
        
        return null;
    }

    /**
     * 
     * 
     * @param id the id of the existing product
     * @param orderableItem the orderable item
     * @param drugUnitVal the drug unit for the single ingredient
     * @return ProductVo
     * @throws Exception
     */
    private ProductVo buildSinglIngredientProductFromExisting(String id, OrderableItemVo orderableItem,
                                                              DrugUnitVo drugUnitVal) {
        
        try {
            ProductVo product = (ProductVo) nationalManagedItemService.retrieveTemplate(id, EntityType.PRODUCT);
            makeUnique(product);
            product.setAtcMnemonic(null);
            product.setOrderableItem(orderableItem);
            product.setPreviousOrderableItem(null);
    
            // the product is a single ingredient item
            // the ingredient unit of the product matches with the dosage form unit of one of
            // the units in units MDF
            List<ActiveIngredientVo> ings = new ArrayList<ActiveIngredientVo>();
            ings.addAll(product.getActiveIngredients());
            ActiveIngredientVo ing = ings.get(0);
            ing.setDrugUnit(drugUnitVal);
          
            return product;
        } catch (Exception e) {
            fail(e.toString());
        }
        
        return null;
    }

//    /**
//     * approves a newly added product
//     * 
//     * @param prod the product to be approved
//     * @param user the user
//     * @return ProcessedRequestVo
//     * @throws Exception
//     */
//    private ProcessedRequestVo approveNewProduct(ProductVo prod, UserVo user) {
//        Collection<RequestVo> colRequest;
//        
//        try {
//            colRequest = requestService.retrieve(prod.getId(), EntityType.PRODUCT);
//        
//            List<RequestVo> listRequest = new ArrayList<RequestVo>();
//            listRequest.addAll(colRequest);
//    
//            // there should be only one request in this collection
//            assertEquals("Add request was not created", 1, colRequest.size());
//    
//            // approve the product item by PNM2
//            RequestVo request = listRequest.get(0);
//            ProductVo retrievedProduct = (ProductVo) nationalManagedItemService
//                .retrieve(request.getItemId(), EntityType.PRODUCT);
//            ProcessedRequestVo processedRequest = nationalManagedItemService.approveRequest(retrievedProduct, request,
//                Collections.EMPTY_LIST, user);
//    
//            retrievedProduct = (ProductVo) processedRequest.getItem();
//            request = processedRequest.getRequest();
//            processedRequest = nationalManagedItemService.commitRequest(retrievedProduct, request, 
//                Collections.EMPTY_LIST, user);
//    
//            return processedRequest;
//        } catch (ItemNotFoundException e) {
//            fail(e.toString());
//        } catch (ValidationException e) {
//            fail(e.toString());
//        }
//        
//        return null;
//
//    }
}
