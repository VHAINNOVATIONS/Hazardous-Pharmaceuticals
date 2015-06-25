/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.BusinessRuleException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.exception.RequestCompletedException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedRequestVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * test request service methods
 */
public class RequestServiceTest extends IntegrationTestCase {
    private static final Logger LOG = Logger.getLogger(RequestServiceTest.class);
    
    private RequestService requestService;
    private ManagedItemService managedItemService;
    
    private String s9991 = "9991";
    private String gcn = "123456";
    private String gcn2 = "654321";

//    private ManagedItemService localOneManagedItemService;

    /**
     * Set up the test
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        LOG.debug("----------------- " + getName() + " ---------------");
        this.requestService = getNationalService(RequestService.class);
        this.managedItemService = getNationalService(ManagedItemService.class);
    }

    /**
     * Tests national editing and approving the CMOP Dispense (National) field.
     * 
     * @throws Exception Exception
     */
    public void testEditingCmopDispense() throws Exception {
        final String id = s9991;
        RequestVo pickRequest = null;
        ProductVo retrievedProduct = null;
        Collection<ModDifferenceVo> colModDiff = new ArrayList<ModDifferenceVo>();
        ModDifferenceVo modDiff = null;
        Difference diff = null;

        // Retrieve a Product and change its CMOP dispense
        retrievedProduct = (ProductVo) managedItemService.retrieve(id, EntityType.PRODUCT);
        ProductVo updatedItem = retrievedProduct.copy();
        updatedItem.setCmopDispense(!retrievedProduct.getCmopDispense());

        colModDiff = managedItemService.submitModifications(retrievedProduct, updatedItem, PNM1).getModDifferences();

        // Update the product.
        ProductVo returnedProduct = (ProductVo) managedItemService.commitModifications(colModDiff, retrievedProduct, PNM1
            ).getItem();

        // Verify that the product's field did NOT change.

        // Retrieve the product again.
        retrievedProduct = (ProductVo) managedItemService.retrieve(id, EntityType.PRODUCT);

        // Retrieve the CMOP Dispense (National) field.
        assertEquals("CMOP Dispense (National)  value should not be changed.", returnedProduct.getCmopDispense(),
            retrievedProduct.getCmopDispense());

        // Have 2nd reviewer approve the field change.
        UserVo secondReviewer = PNM2;

        // Retrieve pending 2nd review mod request.
        pickRequest = searchForProductRequestByLastReviewerUserName(retrievedProduct, PNM1);

        // Verify data on request is correct.
        assertNotNull("Could not locate mod request.", pickRequest);
        assertEquals("Request should be PENDING 2nd APPROVAL", RequestState.PENDING_SECOND_REVIEW, pickRequest
            .getRequestState());
        List<ModDifferenceVo> mods = new ArrayList<ModDifferenceVo>(pickRequest.getRequestDetails());
        ModDifferenceVo modDiffTest = mods.get(0);
        Difference diffTest = modDiffTest.getDifference();
        assertEquals("Field request field key not correct", FieldKey.CMOP_DISPENSE, diffTest.getFieldKey());
        assertEquals("Old Value's value not correct", diffTest.getOldValue(), returnedProduct.getCmopDispense());
        assertEquals("New Value's value not correct", diffTest.getNewValue(), !returnedProduct.getCmopDispense());

        // Simulate 2nd reviewer making another change to the mod request, this time a 1-review field.
        DataField<Boolean> doNotMailOriginal = retrievedProduct.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertNotNull("Retrieving Do Not Mail field failed!", doNotMailOriginal);

        // Begin the process to update a product.
        colModDiff.clear();

        // Update the DO_NOT_MAIL's datafield setting.
        DataField<Boolean> doNotMailNew = DataField.newInstance(FieldKey.DO_NOT_MAIL);
        doNotMailNew.setDataFieldId(doNotMailOriginal.getDataFieldId());
        doNotMailNew.setDefaultValue(false);
        doNotMailNew.selectValue(doNotMailOriginal.getValue().booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        doNotMailNew.setEditable(doNotMailOriginal.isEditable() ? false : true);

        diff = new Difference(FieldKey.DO_NOT_MAIL, doNotMailOriginal, doNotMailNew);
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason("test2");
        modDiff.setAcceptChange(true);
        modDiff.setRequestToMakeEditable(doNotMailNew.isEditable());

        colModDiff.add(modDiff);

        // Complete the Request.
        ProcessedRequestVo processedRequest = managedItemService.commitRequest(retrievedProduct, pickRequest, colModDiff,
            secondReviewer, false);
        returnedProduct = (ProductVo) processedRequest.getItem();

        // Verify the Request is now completed.
        RequestVo requestTest = requestService.retrieve(pickRequest.getId());
        assertEquals("Request should be COMPLETED", RequestState.COMPLETED, requestTest.getRequestState());
        assertEquals(" Request should have two request details", 2, requestTest.getRequestDetails().size());

        // Verify that the 2-nd review change was applied.

        // Retrieve the product again.
        retrievedProduct = (ProductVo) managedItemService.retrieve(id, EntityType.PRODUCT);

        // Retrieve the CMOP Dispense (National) field.
        assertEquals("CMOP Dispense (National) value was not changed.", returnedProduct.getCmopDispense(), retrievedProduct
            .getCmopDispense());
        DataField<Boolean> doNotMailUpdated = retrievedProduct.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertEquals("Item's Do Not Mail should have been updated", doNotMailNew, doNotMailUpdated);
    }

//    /**
//     * This test adds a new product that uses an existing VA Print name reviewer1 approves that new product and gets the
//     * warning message that the VA Print name is already in use
//     * 
//     * This does not stop the save process, just a warning message This is tied to the scenario 3 of CMOP ID doc
//     * 
//     * 
//     * @throws Exception
//     */
//    public void testApproveProductWithExistingVAPrintName() throws Exception {
//
//        // PLM1 adds a product that will have a existing va print name
//        ProductVo product = (ProductVo) localOneManagedItemService.retrieveTemplate("9992", EntityType.PRODUCT);
//        ProductVo existingProduct = (ProductVo) localOneManagedItemService.retrieve("9992", EntityType.PRODUCT);
//
//        // Set required fields, set VA Print Name to match existing item
//        for (ActiveIngredientVo ingredient : product.getActiveIngredients()) {
//            ingredient.setStrength("123");
//        }
//
//        product.setVaPrintName(existingProduct.getVaPrintName());
//        product.setVaProductName(RandomGenerator.nextAlphabetic(10));
//        product.setId(null);
//
//        // insert the product
//        product = (ProductVo) localOneManagedItemService.create(product, PLM1).getItem();
//        System.out.println("Created Product with ID '" + product.getId() + "'");
//
//        System.out.println("Waiting for National to process new Product...");
//        Thread.sleep(10000);
//
//        // retrieve the request for the newly created product
//        Collection<RequestVo> requests = requestService.retrieve(product.getId(), EntityType.PRODUCT);
//
//        // there should be only one request in this collection
//        assertEquals("Add request was not created", 1, requests.size());
//
//        // 1st approval is done by pnm1
//        RequestVo request = requests.iterator().next();
//        System.out.println("Request '" + request.getId() + "' created for Product '" + request.getItemId() + "' in state: "
//            + request.getRequestState());
//
//        ProcessedRequestVo processedRequest = managedItemService.approveRequest(product, request, Collections.EMPTY_LIST,
//            PNM1);
//        product = (ProductVo) processedRequest.getItem();
//        request = processedRequest.getRequest();
//        System.out.println("First appproval of Request '" + request.getId() + "' for Product '"
//            + processedRequest.getItem().getId() + "'");
//        System.out.println("Request is now in state: " + request.getRequestState());
//
//        Errors warnings = processedRequest.getWarnings();
//        assertFalse("There should be warning message", warnings.getErrors().isEmpty());
//        assertTrue("Warning should be generated for using an existing VA Print Name", warnings
//            .hasError(ErrorKey.CMOP_ID_EXISTS));
//
//        // PNM1 commits the request
//        processedRequest = managedItemService.commitRequest(product, request, Collections.EMPTY_LIST, PNM1);
//        product = (ProductVo) processedRequest.getItem();
//        request = processedRequest.getRequest();
//        System.out.println("Committed first appproval of Request '" + request.getId() + "' for Product '" + product.getId()
//            + "'");
//        System.out.println("Request is now in state: " + request.getRequestState());
//
//        // PNM2 approves the product again
//        processedRequest = managedItemService.approveRequest(product, request, Collections.EMPTY_LIST, PNM2);
//        product = (ProductVo) processedRequest.getItem();
//        request = processedRequest.getRequest();
//        System.out.println("Second appproval of Request '" + request.getId() + "' for Product '"
//            + processedRequest.getItem().getId() + "'");
//        System.out.println("Request is now in state: " + request.getRequestState());
//
//        // PNM2 commits the request
//        processedRequest = managedItemService.commitRequest(product, request, Collections.EMPTY_LIST, PNM2);
//        product = (ProductVo) processedRequest.getItem();
//        request = processedRequest.getRequest();
//        System.out.println("Committed second appproval of Request '" + request.getId() + "' for Product '"
//            + processedRequest.getItem().getId() + "'");
//        System.out.println("Request is now in state: " + request.getRequestState());
//
//        assertEquals("Incorrect CmopID", existingProduct.getVaPrintName(), product.getVaPrintName());
//    }

    /**
     * Test a first, second, and third reviewer making and approving mods to a product at national.
     * 
     * @throws Exception Exception
     */
    public void testApproveProductModFromNationalThreeReviews() throws Exception {
        final String id = s9991;

        Collection<ModDifferenceVo> colModDiff = new ArrayList<ModDifferenceVo>();
        ModDifferenceVo modDiff = null;
        Difference diff = null;
        ProductVo prod = null;
        RequestVo requestCreated = null;

        // Retrieve a Product.
        prod = (ProductVo) managedItemService.retrieve(id, EntityType.PRODUCT);

        // PNM makes a change to a two-review field.
        boolean originalCmopDispense = prod.getCmopDispense();
        diff = new Difference(FieldKey.CMOP_DISPENSE, prod.getCmopDispense(), !prod.getCmopDispense());
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason("test12");
        modDiff.setAcceptChange(true);

        colModDiff.add(modDiff);

        // Update the product, which should create a mod request.
        UserVo reviewer1 = PNM1;
        managedItemService.commitModifications(colModDiff, prod, reviewer1);
        prod = (ProductVo) managedItemService.retrieve(id, EntityType.PRODUCT);

        requestCreated = searchForProductRequestByLastReviewerUserName(prod, reviewer1);
        assertNotNull("1.Request should have been created", requestCreated);
        assertEquals("Request should be a mod request type", RequestType.MODIFICATION, requestCreated.getRequestType());
        assertEquals("Request should be in 2nd approval state", RequestState.PENDING_SECOND_REVIEW, requestCreated
            .getRequestState());
        assertEquals("1.Requester should be set", reviewer1.getUsername(), requestCreated.getNewItemRequestor().getUsername());
        assertEquals("Last reviewer should be set", reviewer1.getUsername(), requestCreated.getLastReviewer().getUsername());
        assertEquals("Request should have one request detail", 1, requestCreated.getRequestDetails().size());
        modDiff = (new ArrayList<ModDifferenceVo>(requestCreated.getRequestDetails())).get(0);
        assertEquals("Request detail field type is wronge", FieldKey.CMOP_DISPENSE, modDiff.getDifference().getFieldKey());
        assertEquals("Request detail new value is incorrect", !prod.getCmopDispense(), modDiff.getDifference().getNewValue());
        assertEquals("Request detail should be pre-approved", RequestItemStatus.APPROVED, modDiff.getModRequestItemStatus());
        assertEquals("1.Request detail requestor set incorrectly", reviewer1.getUsername(), modDiff.getRequestor()
            .getUsername());
        assertEquals("2.Request detail last reviewer set incorrectly", reviewer1.getUsername(), modDiff.getReviewer()
            .getUsername());

        // Retrieve the CMOP Dispense (National) field...should NOT be updated.
        assertEquals("CMOP Dispense (National) value should not be changed.", originalCmopDispense, prod.getCmopDispense());

        // Start another request flow up.
        RequestVo request = requestCreated;

        // Simulate 2nd reviewer making another change to the mod request, another 2-review field (GCNSEQNO).
        String gcnSeqNoOriginal = prod.getGcnSequenceNumber();

        // Begin the process to update a product.
        colModDiff.clear();

        // Update this field's setting.
        String gcnTest = "011111";
        String gcnSeqNoNew = (gcnTest.equals(gcnSeqNoOriginal) ? "022222" : gcnTest);

        diff = new Difference(FieldKey.GCN_SEQUENCE_NUMBER, gcnSeqNoOriginal, gcnSeqNoNew);
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason("test1");
        modDiff.setAcceptChange(true);

        colModDiff.add(modDiff);

        // Simulate the 2nd reviewer hitting the 'Accept' button from the request summary page.
        UserVo reviewer2 = PNM2;
        managedItemService.commitRequest(prod, request, colModDiff, reviewer2, false);
        prod = (ProductVo) managedItemService.retrieve(id, EntityType.PRODUCT);
        request = searchForProductRequestByLastReviewerUserName(prod, reviewer2);
        assertNotNull("Request should have been created", request);
        assertEquals("Request should not be completed", RequestState.PENDING_SECOND_REVIEW, request.getRequestState());
        assertEquals("Requester should be set", reviewer1.getUsername(), request.getNewItemRequestor().getUsername());
        assertEquals("Last Reviewer should be set", reviewer2.getUsername(), request.getLastReviewer().getUsername());
        assertEquals("Request should have two request details", 2, request.getRequestDetails().size());
        colModDiff = new ArrayList<ModDifferenceVo>(request.getRequestDetails());

        for (ModDifferenceVo modDiffNext : colModDiff) {
            if (FieldKey.CMOP_DISPENSE.equals(modDiffNext.getDifference().getFieldKey())) {
                assertEquals("3.Request detail should be approved", RequestItemStatus.APPROVED, modDiffNext
                    .getModRequestItemStatus());
                assertEquals("3.Request detail requestor set incorrectly", reviewer1.getUsername(), modDiffNext.getRequestor()
                    .getUsername());
                assertEquals("3.Request detail last reviewer set incorrectly", reviewer2.getUsername(), modDiffNext
                    .getReviewer().getUsername());
            } else if (FieldKey.GCN_SEQUENCE_NUMBER.equals(modDiffNext.getDifference().getFieldKey())) {
                assertEquals("Request detail should be approved", RequestItemStatus.APPROVED, modDiffNext
                    .getModRequestItemStatus());
                assertEquals("Request detail requestor set incorrectly", reviewer2.getUsername(), modDiffNext.getRequestor()
                    .getUsername());
                assertEquals("Request detail last reviewer set incorrectly", reviewer2.getUsername(), modDiffNext
                    .getReviewer().getUsername());
            } else {
                fail("Unexpected field mod seen");
            }
        }

        assertEquals("Item's CMOP Dispense should still be original value", originalCmopDispense, prod.getCmopDispense());
        assertEquals("Item's GCNSEQNO should still be original value", gcnSeqNoOriginal, prod.getGcnSequenceNumber());

        // Start another request flow up.

        // Simulate the 3rd (the 1st again) reviewer hitting the 'Accept' button from the request summary page.
        UserVo reviewer3 = PNM1;
        colModDiff.clear(); // No new changes by 3rd reviewer.
        managedItemService.commitRequest(prod, request, colModDiff, reviewer3, false);
        prod = (ProductVo) managedItemService.retrieve(id, EntityType.PRODUCT);


        // The item should have been updated...verify this.
        // assertEquals("Item's CMOP Dispense should have been updated", !originalCmopDispense, prod.getCmopDispense());
        assertEquals("Item's GCNSEQNO should have been updated", gcnSeqNoNew, prod.getGcnSequenceNumber());

    }

    /**
     * Search at national for a pending second review modification.
     * 
     * @param product ProductVo with requests
     * @param user UserVo performing search
     * @return RequestVo
     * @throws ItemNotFoundException exception
     * @throws ValueObjectValidationException if error in criteria data
     * @throws RequestCompletedException if the request has been completed
     */
    protected RequestVo searchForProductRequestByLastReviewerUserName(ProductVo product, UserVo user)
        throws ItemNotFoundException, ValueObjectValidationException, RequestCompletedException {

        RequestVo pickRequest = null;

        SearchRequestCriteriaVo searchCriteria = new SearchRequestCriteriaVo();
        searchCriteria.setLocalSearch(false);
        searchCriteria.setPendingSecondApprovalModification(true);

        Collection<RequestSummaryVo> colResult = requestService.search(searchCriteria, null);

        LOG.debug(" SearchPendingRequests: " + colResult.toString());

        for (RequestSummaryVo requestSummary : colResult) {

            // If the request summary is for the same product as the one I just modified...
            if (product.getVaProductName().equals(requestSummary.getVaProductName())) {
                RequestVo reqTest = requestService.retrieve(requestSummary.getRequestId());

                if (reqTest.getLastReviewer().getUsername().equals(user.getUsername())) {
                    pickRequest = reqTest;
                }
            }
        }

        return pickRequest;
    }

    /**
     * test retrieve request detail by Id
     * @throws PharmacyException exception
     * @throws ItemNotFoundException exception
     */
    public void revisitTestRetrieveRequestDetailById() throws ItemNotFoundException, PharmacyException {
        RequestVo requestDetail = requestService.retrieve("1");

        assertEquals("Incorrect request Id", "1", requestDetail.getId());
    }

    /**
     * test Simple review add product
     * @throws PharmacyException PharmacyException
     * 
     */
    public void revisitTestSimpleReviewProductAdd() throws PharmacyException {

        // search for the pending items
        SearchRequestCriteriaVo searchCriteria = new SearchRequestCriteriaVo();
        searchCriteria.setAllRequests(true);
        Collection<RequestSummaryVo> colResult = requestService.search(searchCriteria, null);

        LOG.debug("SearchPendingRequests: " + colResult.toString());

        for (RequestSummaryVo request : colResult) {
            assertEquals("The New Item Request should be pending", RequestItemStatus.PENDING, request.getRequestItemStatus());
        }

    }

    /**
     * Verify that mod conflicts cannot be saved to the database.
     * 
     * @throws Exception if error
     */
    public void testModConflict() throws Exception {
        ProductVo original = (ProductVo) managedItemService.retrieve(s9991, EntityType.PRODUCT);
        ProductVo one = original.copy();
        
        one.setGcnSequenceNumber(gcn);
        managedItemService.commitModifications(original.compareDifferences(one), original, PNM1);

        Collection<RequestVo> requests = requestService.retrieve(s9991, EntityType.PRODUCT);
        RequestVo currentRequest = requests.iterator().next();

        for (RequestVo requestVo : requests) {
            if (!requestVo.isCompleted()) {
                currentRequest = requestVo;
            }
        }

        ProductVo two = original.copy();
        two.setGcnSequenceNumber(gcn2);

        try {
            managedItemService.commitRequest(original, currentRequest, original.compareDifferences(two), PNM2, false);
            fail("Should have thrown BusinessRulesException for conflict!");
        } catch (BusinessRuleException e) {
            LOG.debug(e.toString());
        }
    }

    /**
     * Verify that add conflicts cannot be saved to the database.
     * 
     * @throws Exception if error
     */
    public void testAddConflict() throws Exception {
        ProductVo template = (ProductVo) managedItemService.retrieveTemplate(s9991, EntityType.PRODUCT);
        template.setVaPrintName(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        template.setVaProductName(RandomGenerator.nextAlphabetic(PPSConstants.I10));

        for (ActiveIngredientVo activeIngredient : template.getActiveIngredients()) {
            activeIngredient.setStrength(RandomGenerator.nextNumeric(PPSConstants.I3));
        }

        template.setGcnSequenceNumber(gcn);
        ProcessedItemVo processedItem = managedItemService.create(template, PNM1);

        ProductVo two = (ProductVo) managedItemService.retrieve(processedItem.getItem().getId(), EntityType.PRODUCT);
        ProductVo twoCopy = two.copy();
        twoCopy.setGcnSequenceNumber(gcn2);

        Collection<RequestVo> requests = requestService.retrieve(processedItem.getItem().getId(), EntityType.PRODUCT);
        RequestVo currentRequest = requests.iterator().next();

        for (RequestVo requestVo : requests) {
            if (!requestVo.isCompleted()) {
                currentRequest = requestVo;
            }
        }

        managedItemService.commitRequest(two, currentRequest, two.compareDifferences(twoCopy), PNM2, false);

        ProductVo three = (ProductVo) managedItemService.retrieve(processedItem.getItem().getId(), EntityType.PRODUCT);
        ProductVo threeCopy = three.copy();
        threeCopy.setGcnSequenceNumber("987654");

        requests = requestService.retrieve(processedItem.getItem().getId(), EntityType.PRODUCT);
        currentRequest = requests.iterator().next();

        for (RequestVo requestVo : requests) {
            if (!requestVo.isCompleted()) {
                currentRequest = requestVo;
            }
        }

        try {
            managedItemService.commitRequest(three, currentRequest, three.compareDifferences(threeCopy), PNM1, false);
            fail("Should have thrown BusinessRulesException for conflict");
        } catch (BusinessRuleException e) {
            LOG.debug("");
        }
    }
}
