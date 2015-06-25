/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.capability.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IAnswer;

import gov.va.med.pharmacy.peps.common.exception.BusinessRuleException;
import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.utility.impl.test.EnvironmentUtilityStub;
import gov.va.med.pharmacy.peps.common.utility.test.generator.ProductGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedRequestVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NotificationDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaFileSynchCapability;
import gov.va.med.pharmacy.peps.service.common.capability.RulesCapability;
import gov.va.med.pharmacy.peps.service.common.utility.ManagedItemCapabilityFactory;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachineFactory;
import gov.va.med.pharmacy.peps.service.national.messagingservice.outbound.capability.SendToLocalCapability;

import junit.framework.TestCase;


/**
 * Tests request management processing.
 * 
 * This test class uses the EasyMock framework. For more information on using EasyMock, please refer to the online docs:
 * http://www.easymock.org/EasyMock2_4_Documentation.html
 */
public class RequestManagementTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(RequestManagementTest.class);
    private static final String REQUEST_MOD_TYPE = "Request should be a mod request type";
    private static final String REQUEST_CREATED = "Request should have been created";
    private static final String REQUEST_ONE_DETAIL = "Request should have one request detail";
    private static final String ITEM_SHOULD_NOT_UPDATE = "Item should NOT have been updated";
    private static final String REQUESTOR_SET_INCORRECT = "Request detail requestor set incorrectly";
    private static final String LAST_REVIEWER_INCORRECT = "Request detail last reviewer set incorrectly";
    private static final String REQUESTOR_SHOULD_SET = "Requester should be set";
    private static final String LAST_REVIEWER = "Last Reviewer should be set";
    private static final String DETAIL_TYPE_WRONG = "Request detail field type is wrong";
    private static final String SEC_APPROVAL_STATE = "Request should be in 2nd approval state";
    private static final String PRE_APPROVED = "Request detail should be pre-approved";
    private static final String REQ_COMP = "Request should be completed";
    private static final String REQ_DETAIL_APPR = "Request detail should be approved";
    private static final String TWO_REQ_DETAILS = "Request should have two request details";
    private static final String ITEM_UPDATED = "Item should have been updated";
    private static final String REQ_NOT_COMP = "Request should NOT be completed";
    private static final String ITEM_CMOP_UPDATE = "Item's CMOP Dispense should have been updated";
    private static final String ITEM_CMOP_ORIG = "Item's CMOP Dispense should still be original value";
    private static final String UNEXP_FIELD_MOD = "Unexpected field mod seen";
    private static final String ITEM_DO_NOT_MAIL = "Item's Do Not Mail should have been updated";
    private static final String REQ_ONE_DETAIL = "Request should still have one request detail";
    private static final String CMOP_NAT_FAILED = "Retrieving CMOP Dispense (National) field failed!";
    private static final String DO_NOT_MAIL_FIELD_FAILED = "Retrieving Do Not Mail field failed!";
    private static final String TEST1 = "test1";
    private static final String TEST2 = "test2";

    private UserVo reviewer1;
    private UserVo reviewer2;
    private UserVo requesterLocal1;
    private ManagedItemCapabilityImpl miCap;
    private RequestCapabilityImpl reqCap;

    private Collection mocks = new ArrayList();
    private ManagedItemVo itemInstanceThatRepresentsDatabaseCopy;
    private RequestVo requestInstanceThatRepresentsDatabaseCopy;

    private IAnswer<Long> getRevisionNumberAnswer;

    private IAnswer<RequestVo> retrieveRequestAnswer;

    private Capture<ManagedItemVo> capItemUpdated;
    private IAnswer<ManagedItemVo> updateItemAnswer;
    private IAnswer<ManagedItemVo> retrieveItemAnswer;

    private Capture<ProductVo> capItemUpdatedProd;
    private IAnswer<ProductVo> updateItemAnswerProd;
    private IAnswer<ProductVo> retrieveItemAnswerProd;

    private Capture<RequestVo> capRequestCreated;
    private IAnswer<RequestVo> createRequestAnswer;

    private IAnswer<RequestVo> createReqDetailsRequest;

    /**
     * test setup
     * 
     * @throws Exception if error
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {
        LOG.info("---------- " + getName() + " ----------");

        // Create the users.  specifically created outside of buildMocks
//        LOG.info("begin: build mock users");
        reviewer1 = new UserGenerator().getNationalManagerOne();

//        LOG.info("built mock user reviewer 1, duz: " + reviewer1.getDuz());
        reviewer2 = new UserGenerator().getNationalManagerTwo();

//        LOG.info("built mock user reviewer 2, duz: " + reviewer2.getDuz());
        requesterLocal1 = new UserGenerator().getLocalManagerOne();

//        LOG.info("built mock user requesterLocal1, duz: " + requesterLocal1.getDuz());
//        LOG.info("end: build mock users");

        buildMocks();
    }

    /**
     * test teardown.
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown() {
        mocks.clear();
    }

    /**
     * Create mock object
     * 
     * @param <T> type of mock
     * @param toMock class to create a mock from
     * @return Mock object
     */
    protected <T> T createMock(java.lang.Class<T> toMock) {
        T ret = EasyMock.createMock(toMock);
        mocks.add(ret);

        return ret;
    }

    /**
     * Reset mock objects for reuse.
     */
    protected void resetMocks() {
        for (Object mockNext : mocks) {
            EasyMock.reset(mockNext);
        }
    }


    /**
     * Test a first and second reviewer approving a new Product request from local 1.
     * 
     * @throws Exception if error
     */

    /*  public void testApproveProductAddFromLocal() throws Exception {
        ProcessedRequestVo info;

        // Create a test Product, as if it was submitted by a local site up to national.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.PENDING);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

        // Create a test Request as if it came up from a local site.
        RequestVo request = new RequestVo(prod.getId(), prod.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            requesterLocal1, // The user requesting this addition.
            RequestItemStatus.PENDING, false, // Not under review.
            false, // Not marked for PSR.
            null); // No request details yet.
        info = continueWithAnotherRequestFlow(request, prod);
        request = info.getRequest();
        prod = (ProductVo) info.getItem();

        // Simulate the 1st reviewer hitting the 'Approve' button on the request (should NOT DO ANYTHING).
        request = miCap.approveRequest(prod, request, new ArrayList<ModDifferenceVo>(0), reviewer1).getRequest();
        assertEquals("Request new-item-req-status should be approved", RequestItemStatus.APPROVED, request
            .getNewItemRequestStatus());
        assertEquals(REQUESTOR_SHOULD_SET, requesterLocal1, request.getNewItemRequestor());
        assertFalse("Request should NOT be under review", request.isUnderReview());

        // Simulate the 1st reviewer hitting the 'Accept' button from the request summary page (should NOT DO ANYTHING).
        Collection<ModDifferenceVo> modDiffs = new ArrayList<ModDifferenceVo>(0);
        info = miCap.commitRequest(prod, request, modDiffs, reviewer1);
        ProductVo postProd = (ProductVo) info.getItem();
        RequestVo postRequest = info.getRequest();
        assertEquals(REQ_NOT_COMP, RequestState.PENDING_SECOND_REVIEW, postRequest.getRequestState());
        assertEquals("Request should NOT have request details", 0, postRequest.getRequestDetails().size());
        assertEquals(REQUESTOR_SHOULD_SET, requesterLocal1, postRequest.getNewItemRequestor());
        assertEquals(LAST_REVIEWER, reviewer1, postRequest.getLastReviewer());

        // Don't care about this! assertEquals("Item should still be pending", RequestItemStatus.PENDING,
        // postProd.getRequestItemStatus());
        // Don't care about this! assertEquals("Item should be active", gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE,
        // postProd.getItemStatus());
        // Don't care about this! assertEquals("Item should have same ID", prod.getId(), postProd.getId());

        // The item should NOT have been updated...verify this.
        assertFalse(ITEM_SHOULD_NOT_UPDATE, capItemUpdatedProd.hasCaptured());

        // Start another request flow up.
        info = continueWithAnotherRequestFlow(postRequest, prod);
        request = info.getRequest();
        prod = (ProductVo) info.getItem();

        // Simulate the 1st reviewer hitting the 'Approve' button on the request AGAIN.
        request = miCap.approveRequest(prod, request, new ArrayList<ModDifferenceVo>(0), reviewer1).getRequest();
        assertEquals("Request should be approved", RequestItemStatus.APPROVED, request.getNewItemRequestStatus());
        assertFalse("Request should NOT be under review", request.isUnderReview());

        // Simulate the 1st reviewer hitting the 'Accept' button from the request summary page AGAIN.
        modDiffs = new ArrayList<ModDifferenceVo>(0);
        info = miCap.commitRequest(prod, request, modDiffs, reviewer1);
        postProd = (ProductVo) info.getItem();
        postRequest = info.getRequest();
        assertEquals(REQ_NOT_COMP, RequestState.PENDING_SECOND_REVIEW, postRequest.getRequestState());
        assertEquals("Request should NOT have request details", 0, postRequest.getRequestDetails().size());
        assertFalse(ITEM_SHOULD_NOT_UPDATE, capItemUpdatedProd.hasCaptured());

        // Start another request flow up.
        info = continueWithAnotherRequestFlow(postRequest, postProd);
        request = info.getRequest();
        prod = (ProductVo) info.getItem();

        // Simulate 2nd reviewer making another change to the mod request, a 1-review field.
        DataField<Boolean> doNotMailOriginal = prod.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertNotNull(DO_NOT_MAIL_FIELD_FAILED, doNotMailOriginal);

        // Begin the process to update a product.
        modDiffs.clear();

        // Update this field's setting.
        DataField<Boolean> doNotMailNew = DataField.newInstance(FieldKey.DO_NOT_MAIL);
        doNotMailNew.setDataFieldId(doNotMailOriginal.getDataFieldId());
        doNotMailNew.setDefaultValue(false);
        doNotMailNew.selectValue(doNotMailOriginal.getValue().booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        doNotMailNew.setEditable(doNotMailOriginal.isEditable() ? false : true);

        Difference diff = new Difference(FieldKey.DO_NOT_MAIL, doNotMailOriginal, doNotMailNew);
        ModDifferenceVo modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason(TEST2);
        modDiff.setAcceptChange(true);

        modDiffs.add(modDiff);

        // Simulate the 2nd reviewer hitting the 'Approve' button on the request.
        request = miCap.approveRequest(prod, request, new ArrayList<ModDifferenceVo>(0), reviewer2).getRequest();
        assertEquals("Request should be approved", RequestItemStatus.APPROVED, request.getNewItemRequestStatus());
        assertFalse("Request should NOT be under review", request.isUnderReview());

        // Simulate the 2nd reviewer hitting the 'Accept' button from the request summary page.
        info = miCap.commitRequest(prod, request, modDiffs, reviewer2);
        postProd = (ProductVo) info.getItem();
        postRequest = info.getRequest();
        assertEquals(REQ_NOT_COMP, RequestState.PENDING_SECOND_REVIEW, postRequest.getRequestState());
        assertEquals(REQUEST_ONE_DETAIL, 1, postRequest.getRequestDetails().size());
        modDiffs = new ArrayList<ModDifferenceVo>(postRequest.getRequestDetails());
        modDiff = ((List<ModDifferenceVo>) modDiffs).get(0);
        assertEquals(REQ_DETAIL_APPR, RequestItemStatus.APPROVED, modDiff.getModRequestItemStatus());
        assertEquals(REQUESTOR_SET_INCORRECT, reviewer2, modDiff.getRequestor());
        assertEquals(LAST_REVIEWER_INCORRECT, reviewer2, modDiff.getReviewer());
        assertEquals(REQUESTOR_SHOULD_SET, requesterLocal1, postRequest.getNewItemRequestor());
        assertEquals(LAST_REVIEWER, reviewer2, postRequest.getLastReviewer());
        assertFalse(ITEM_SHOULD_NOT_UPDATE, capItemUpdatedProd.hasCaptured());

        // Start another request flow up.
        info = continueWithAnotherRequestFlow(postRequest, postProd);
        request = info.getRequest();
        prod = (ProductVo) info.getItem();

        // 3rd reviewer makes no new changes.
        modDiffs.clear();

        // Simulate the 3rd reviewer hitting the 'Approve' button on the request.
        request = miCap.approveRequest(prod, request, new ArrayList<ModDifferenceVo>(0), reviewer1).getRequest();
        assertEquals("Request should be approved", RequestItemStatus.APPROVED, request.getNewItemRequestStatus());
        assertFalse("Request should NOT be under review", request.isUnderReview());

        // Simulate the 3rd reviewer hitting the 'Accept' button from the request summary page.
        info = miCap.commitRequest(prod, request, modDiffs, reviewer1);
        postProd = (ProductVo) info.getItem();
        postRequest = info.getRequest();
        assertEquals(REQ_COMP, RequestState.COMPLETED, postRequest.getRequestState());
        assertEquals(REQUEST_ONE_DETAIL, 1, postRequest.getRequestDetails().size());
        modDiffs = new ArrayList<ModDifferenceVo>(postRequest.getRequestDetails());
        modDiff = ((List<ModDifferenceVo>) modDiffs).get(0);
        assertEquals(REQ_DETAIL_APPR, RequestItemStatus.APPROVED, modDiff.getModRequestItemStatus());
        assertEquals(REQUESTOR_SET_INCORRECT, reviewer2, modDiff.getRequestor());
        assertEquals(LAST_REVIEWER_INCORRECT, reviewer1, modDiff.getReviewer());
        assertEquals(REQUESTOR_SHOULD_SET, requesterLocal1, postRequest.getNewItemRequestor());
        assertEquals(LAST_REVIEWER, reviewer1, postRequest.getLastReviewer());

        // The item should have been updated...verify this.
        ProductVo productUpdated = (ProductVo) capItemUpdatedProd.getValue();
        assertEquals(ITEM_UPDATED, postProd, productUpdated);
        DataField<Boolean> doNotMailUpdated = productUpdated.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertEquals(ITEM_DO_NOT_MAIL, doNotMailNew, doNotMailUpdated);
    }
    */

    /**
     * Test a first and second reviewer making and approving mods to a product at national.
     * 
     * @throws Exception if error
     */
    public void testApproveProductModFromNationalTwoReviews() throws Exception {
        ProcessedRequestVo info;

        // setup the collection of ModDifferences object.
        Collection<ModDifferenceVo> colModDiff = new ArrayList<ModDifferenceVo>();
        ModDifferenceVo modDiff = null;
        Difference diff = null;

        // Create a test Product, as if it was already added and approved.
        // This is for testApproveProductModFromNationalTwoReviews
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

        itemInstanceThatRepresentsDatabaseCopy = prod.copy();

        // Simulate a PNM making a change to a two-review field.
        // Begin the process to update a product.
        colModDiff.clear();
        boolean cmopDispenseNew = !prod.getCmopDispense();
        diff = new Difference(FieldKey.CMOP_DISPENSE, prod.getCmopDispense(), cmopDispenseNew);
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason(TEST1);
        modDiff.setAcceptChange(true);

        // add the modDifference for the testApproveProductModFromNationalTwoReviews
        colModDiff.add(modDiff);

        // Update the product, which should create a mod request.
        ProductVo returnedProduct = (ProductVo) miCap.commitModifications(colModDiff, prod, reviewer1).getItem();
        RequestVo requestCreated = capRequestCreated.getValue();
        assertNotNull(REQUEST_CREATED, requestCreated);
        assertEquals(REQUEST_MOD_TYPE, RequestType.MODIFICATION, requestCreated.getRequestType());
        assertEquals(SEC_APPROVAL_STATE, RequestState.PENDING_SECOND_REVIEW, requestCreated.getRequestState());
        assertEquals(REQUEST_ONE_DETAIL, 1, requestCreated.getRequestDetails().size());
        modDiff = (new ArrayList<ModDifferenceVo>(requestCreated.getRequestDetails())).get(0);
        assertEquals(DETAIL_TYPE_WRONG, FieldKey.CMOP_DISPENSE, modDiff.getDifference().getFieldKey());
        assertEquals(PRE_APPROVED, RequestItemStatus.APPROVED, modDiff.getModRequestItemStatus());
        assertFalse(ITEM_SHOULD_NOT_UPDATE, capItemUpdated.hasCaptured());

        // returned and original products should be equal except for IAH and revision number
        prod.getItemAuditHistory().clear();
        returnedProduct.getItemAuditHistory().clear();
        returnedProduct.setNewAuditHistory(null);
        prod.setRevisionNumber(returnedProduct.getRevisionNumber());

        assertEquals(ITEM_SHOULD_NOT_UPDATE, prod, returnedProduct);
        assertEquals(REQUESTOR_SET_INCORRECT, reviewer1, modDiff.getRequestor());
        assertEquals(LAST_REVIEWER_INCORRECT, reviewer1, modDiff.getReviewer());

        // Start another request flow up.
        info = continueWithAnotherRequestFlow(requestCreated, prod);
        RequestVo request = info.getRequest();
        prod = (ProductVo) info.getItem();

        // Simulate the 1st reviewer hitting the 'Accept' button from the request summary page 
        // This (should NOT DO ANYTHING).
        Collection<ModDifferenceVo> modDiffs = new ArrayList<ModDifferenceVo>(0);
        info = miCap.commitRequest(prod, request, modDiffs, reviewer1, false);
        ProductVo postProd = (ProductVo) info.getItem();
        RequestVo postRequest = info.getRequest();
        assertEquals(REQ_NOT_COMP, RequestState.PENDING_SECOND_REVIEW, postRequest.getRequestState());
        assertEquals(REQUESTOR_SHOULD_SET, reviewer1, postRequest.getNewItemRequestor());
        assertEquals(LAST_REVIEWER, reviewer1, postRequest.getLastReviewer());
        assertEquals(REQ_ONE_DETAIL, 1, postRequest.getRequestDetails().size());
        assertFalse(ITEM_SHOULD_NOT_UPDATE, capItemUpdatedProd.hasCaptured());

        // DataField<Boolean> cmopDispenseOrig2 = postProd.getDataFields().getDataField(FieldKey.CMOP_DISPENSE);
        // assertEquals(ITEM_CMOP_ORIG, cmopDispenseOriginal, cmopDispenseOrig2);

        // Start another request flow up.
        info = continueWithAnotherRequestFlow(postRequest, postProd);
        request = info.getRequest();
        prod = (ProductVo) info.getItem();

        // Simulate 2nd reviewer making another change to the mod request, this time a 1-review field.
        DataField<Boolean> doNotMailOriginal = prod.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertNotNull(DO_NOT_MAIL_FIELD_FAILED, doNotMailOriginal);

        // Begin the process to update a product.
        colModDiff.clear();

        // Update this field's setting.
        DataField<Boolean> doNotMailNew = DataField.newInstance(FieldKey.DO_NOT_MAIL);
        doNotMailNew.setDataFieldId(doNotMailOriginal.getDataFieldId());
        doNotMailNew.setDefaultValue(false);
        doNotMailNew.selectValue(doNotMailOriginal.getValue().booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        doNotMailNew.setEditable(doNotMailOriginal.isEditable() ? false : true);

        diff = new Difference(FieldKey.DO_NOT_MAIL, doNotMailOriginal, doNotMailNew);
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason(TEST2);
        modDiff.setAcceptChange(true);

        colModDiff.add(modDiff);

        // Simulate the 2nd reviewer hitting the 'Accept' button from the request summary page.
        info = miCap.commitRequest(prod, request, colModDiff, reviewer2, false);
        postProd = (ProductVo) info.getItem();
        postRequest = info.getRequest();
        assertEquals(REQ_COMP, RequestState.COMPLETED, postRequest.getRequestState());
        assertEquals(REQUESTOR_SHOULD_SET, reviewer1, postRequest.getNewItemRequestor());
        assertEquals(LAST_REVIEWER, reviewer2, postRequest.getLastReviewer());
        assertEquals(TWO_REQ_DETAILS, 2, postRequest.getRequestDetails().size());
        modDiffs = new ArrayList<ModDifferenceVo>(postRequest.getRequestDetails());

        for (ModDifferenceVo modDiffNext : modDiffs) {
            if (FieldKey.CMOP_DISPENSE.equals(modDiffNext.getDifference().getFieldKey())) {
                assertEquals(REQ_DETAIL_APPR, RequestItemStatus.APPROVED, modDiffNext.getModRequestItemStatus());
                assertEquals(REQUESTOR_SET_INCORRECT, reviewer1, modDiffNext.getRequestor());
                assertEquals(LAST_REVIEWER_INCORRECT, reviewer2, modDiffNext.getReviewer());
            } else if (FieldKey.DO_NOT_MAIL.equals(modDiffNext.getDifference().getFieldKey())) {
                assertEquals(REQ_DETAIL_APPR, RequestItemStatus.APPROVED, modDiffNext.getModRequestItemStatus());
                assertEquals(REQUESTOR_SET_INCORRECT, reviewer2, modDiffNext.getRequestor());
                assertEquals(LAST_REVIEWER_INCORRECT, reviewer2, modDiffNext.getReviewer());
            } else {
                fail(UNEXP_FIELD_MOD);
            }
        }

        // The item should have been updated...verify this by ensuring productUpdated equals the postProd.
        ProductVo productUpdated = (ProductVo) capItemUpdatedProd.getValue();
        assertEquals(ITEM_UPDATED, postProd, productUpdated);

        boolean cmopDispenseUpdated = productUpdated.getCmopDispense();
        assertEquals(ITEM_CMOP_UPDATE, cmopDispenseNew, cmopDispenseUpdated);

        DataField<Boolean> doNotMailUpdated = productUpdated.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertEquals(ITEM_DO_NOT_MAIL, doNotMailNew, doNotMailUpdated);
    }

    /**
     * Test a first, second, and third reviewer making and approving mods to a product at national.
     * 
     * @throws Exception if error
     */
    public void testApproveProductModFromNationalThreeReviews() throws Exception {
        ProcessedRequestVo info;
        Collection<ModDifferenceVo> colModDiff = new ArrayList<ModDifferenceVo>();
        ModDifferenceVo modDiff = null;
        Difference diff = null;

        // testApproveProductModFromNationalThreeReviews
        // Create a test Product, as if it was already added and approved.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

        itemInstanceThatRepresentsDatabaseCopy = prod.copy();

        // Simulate a PNM making a change to a two-review field. Begin the process to update a product.
        colModDiff.clear();
        boolean cmopDispenseOriginal = prod.getCmopDispense();
        boolean cmopDispenseNew = !prod.getCmopDispense();
        diff = new Difference(FieldKey.CMOP_DISPENSE, cmopDispenseOriginal, cmopDispenseNew);
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason(TEST1);
        modDiff.setAcceptChange(true);

        colModDiff.add(modDiff);

        // Update the product, which should create a mod request.
        ProductVo returnedProduct = (ProductVo) miCap.commitModifications(colModDiff, prod, reviewer1).getItem();
        RequestVo requestCreated = capRequestCreated.getValue();
        assertNotNull(REQUEST_CREATED, requestCreated);
        assertEquals(REQUEST_MOD_TYPE, RequestType.MODIFICATION, requestCreated.getRequestType());
        assertEquals(SEC_APPROVAL_STATE, RequestState.PENDING_SECOND_REVIEW, requestCreated.getRequestState());
        assertEquals(REQUESTOR_SHOULD_SET, reviewer1, requestCreated.getNewItemRequestor());
        assertEquals("Last reviewer should be set", reviewer1, requestCreated.getLastReviewer());
        assertEquals(REQUEST_ONE_DETAIL, 1, requestCreated.getRequestDetails().size());
        modDiff = (new ArrayList<ModDifferenceVo>(requestCreated.getRequestDetails())).get(0);
        assertEquals(DETAIL_TYPE_WRONG, FieldKey.CMOP_DISPENSE, modDiff.getDifference().getFieldKey());
        assertEquals(PRE_APPROVED, RequestItemStatus.APPROVED, modDiff.getModRequestItemStatus());

        // returned and original products should be equal except for IAH and revision number
        prod.getItemAuditHistory().clear();
        returnedProduct.getItemAuditHistory().clear();
        returnedProduct.setNewAuditHistory(null);

        prod.setRevisionNumber(returnedProduct.getRevisionNumber());
        assertEquals(ITEM_SHOULD_NOT_UPDATE, prod, returnedProduct);

        assertEquals(REQUESTOR_SET_INCORRECT, reviewer1, modDiff.getRequestor());
        assertEquals(LAST_REVIEWER_INCORRECT, reviewer1, modDiff.getReviewer());

        // Start another request flow up.
        info = continueWithAnotherRequestFlow(requestCreated, prod);
        RequestVo request = info.getRequest();
        prod = (ProductVo) info.getItem();

        // Simulate the 1st reviewer hitting the 'Accept' button from the request summary page (should NOT DO ANYTHING).
        Collection<ModDifferenceVo> modDiffs = new ArrayList<ModDifferenceVo>(0);
        info = miCap.commitRequest(prod, request, modDiffs, reviewer1, false);
        ProductVo postProd = (ProductVo) info.getItem();
        RequestVo postRequest = info.getRequest();
        assertEquals(REQ_NOT_COMP, RequestState.PENDING_SECOND_REVIEW, postRequest.getRequestState());
        assertEquals(REQUESTOR_SHOULD_SET, reviewer1, postRequest.getNewItemRequestor());
        assertEquals(LAST_REVIEWER, reviewer1, postRequest.getLastReviewer());
        assertEquals(REQ_ONE_DETAIL, 1, postRequest.getRequestDetails().size());
        assertFalse(ITEM_SHOULD_NOT_UPDATE, capItemUpdatedProd.hasCaptured());

        boolean cmopDispenseOrig2 = postProd.getCmopDispense();
        assertEquals(ITEM_CMOP_ORIG, cmopDispenseOriginal, cmopDispenseOrig2);
        info = continueWithAnotherRequestFlow(postRequest, postProd);
        request = info.getRequest();
        prod = (ProductVo) info.getItem();
        String gcnSeqNoOriginal = prod.getGcnSequenceNumber();
        colModDiff.clear();
        String gcnSeqNoNew = ("011111".equals(gcnSeqNoOriginal) ? "022222" : "011111");
        diff = new Difference(FieldKey.GCN_SEQUENCE_NUMBER, gcnSeqNoOriginal, gcnSeqNoNew);
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason(TEST1);
        modDiff.setAcceptChange(true);
        colModDiff.add(modDiff);
        info = miCap.commitRequest(prod, request, colModDiff, reviewer2, false);
        postProd = (ProductVo) info.getItem();
        postRequest = info.getRequest();
        assertEquals("Request should not be completed", RequestState.PENDING_SECOND_REVIEW, postRequest.getRequestState());
        assertEquals(REQUESTOR_SHOULD_SET, reviewer1, postRequest.getNewItemRequestor());
        assertEquals(LAST_REVIEWER, reviewer2, postRequest.getLastReviewer());
        assertEquals(TWO_REQ_DETAILS, 2, postRequest.getRequestDetails().size());
        modDiffs = new ArrayList<ModDifferenceVo>(postRequest.getRequestDetails());

        for (ModDifferenceVo modDiffNext : modDiffs) {
            if (FieldKey.CMOP_DISPENSE.equals(modDiffNext.getDifference().getFieldKey())) {
                assertEquals(REQ_DETAIL_APPR, RequestItemStatus.APPROVED, modDiffNext.getModRequestItemStatus());
                assertEquals(REQUESTOR_SET_INCORRECT, reviewer1, modDiffNext.getRequestor());
                assertEquals(LAST_REVIEWER_INCORRECT, reviewer2, modDiffNext.getReviewer());
            } else if (FieldKey.GCN_SEQUENCE_NUMBER.equals(modDiffNext.getDifference().getFieldKey())) {
                assertEquals(REQ_DETAIL_APPR, RequestItemStatus.APPROVED, modDiffNext.getModRequestItemStatus());
                assertEquals(REQUESTOR_SET_INCORRECT, reviewer2, modDiffNext.getRequestor());
                assertEquals(LAST_REVIEWER_INCORRECT, reviewer2, modDiffNext.getReviewer());
            } else {
                fail(UNEXP_FIELD_MOD);
            }
        }

        assertFalse(ITEM_SHOULD_NOT_UPDATE, capItemUpdatedProd.hasCaptured());
        boolean cmopDispenseOrig3 = postProd.getCmopDispense();
        assertEquals(ITEM_CMOP_ORIG, cmopDispenseOriginal, cmopDispenseOrig3);
        assertEquals("Item's GCNSEQNO should still be original value", gcnSeqNoOriginal, postProd.getGcnSequenceNumber());
        info = continueWithAnotherRequestFlow(postRequest, postProd);
        request = info.getRequest();
        prod = (ProductVo) info.getItem();

        // Simulate the 3rd (the 1st again) reviewer hitting the 'Accept' button from the request summary page.
        colModDiff.clear(); // No new changes by 3rd reviewer.
        info = miCap.commitRequest(prod, request, colModDiff, reviewer1, false);
        postProd = (ProductVo) info.getItem();
        postRequest = info.getRequest();
        assertEquals(REQ_COMP, RequestState.COMPLETED, postRequest.getRequestState());
        assertEquals(REQUESTOR_SHOULD_SET, reviewer1, postRequest.getNewItemRequestor());
        assertEquals(LAST_REVIEWER, reviewer1, postRequest.getLastReviewer());
        assertEquals(TWO_REQ_DETAILS, 2, postRequest.getRequestDetails().size());
        modDiffs = new ArrayList<ModDifferenceVo>(postRequest.getRequestDetails());

        for (ModDifferenceVo modDiffNext : modDiffs) {
            if (FieldKey.CMOP_DISPENSE.equals(modDiffNext.getDifference().getFieldKey())) {
                assertEquals(REQ_DETAIL_APPR, RequestItemStatus.APPROVED, modDiffNext.getModRequestItemStatus());
                assertEquals(REQUESTOR_SET_INCORRECT, reviewer1, modDiffNext.getRequestor());
                assertEquals(LAST_REVIEWER_INCORRECT, reviewer1, modDiffNext.getReviewer());
            } else if (FieldKey.GCN_SEQUENCE_NUMBER.equals(modDiffNext.getDifference().getFieldKey())) {
                assertEquals(REQ_DETAIL_APPR, RequestItemStatus.APPROVED, modDiffNext.getModRequestItemStatus());
                assertEquals(REQUESTOR_SET_INCORRECT, reviewer2, modDiffNext.getRequestor());
                assertEquals(LAST_REVIEWER_INCORRECT, reviewer1, modDiffNext.getReviewer());
            } else {
                fail(UNEXP_FIELD_MOD);
            }
        }

        // The item should have been updated...verify this.
        ProductVo productUpdated = (ProductVo) capItemUpdatedProd.getValue();
        assertEquals(ITEM_UPDATED, postProd, productUpdated);
        boolean cmopDispenseUpdated = productUpdated.getCmopDispense();
        assertEquals(ITEM_CMOP_UPDATE, cmopDispenseNew, cmopDispenseUpdated);
        assertEquals("Item's GCNSEQNO should have been updated", gcnSeqNoNew, productUpdated.getGcnSequenceNumber());
    }

    /**
     * Test a PNM making a change to a two review field, then the second reviewer making another change to this same field to
     * force a conflict to occur, then un-accepting this field after the conflict occurs to complete the mod request.
     * 
     * @throws Exception if error
     * 
     */
    public void revisitTestApproveProductModFromNationalTwoReviewsWithConflicts() throws Exception {
        ProcessedRequestVo info;

        Collection<ModDifferenceVo> colModDiff = new ArrayList<ModDifferenceVo>();
        ModDifferenceVo modDiff = null;
        Difference diff = null;

        // Create a an approved and active test Product.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

        // Simulate a PNM making a change to a two-review field.
        // Retrieve the CMOP Dispense (National) field.
        boolean cmopDispenseOriginal = prod.getCmopDispense();
        assertNotNull(CMOP_NAT_FAILED, cmopDispenseOriginal);

        // Begin the process to update a product
        colModDiff.clear(); // Update this field's setting.

        boolean cmopDispenseNew = !prod.getCmopDispense();

        diff = new Difference(FieldKey.CMOP_DISPENSE, cmopDispenseOriginal, cmopDispenseNew);

        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason(TEST1);
        modDiff.setAcceptChange(true);

        colModDiff.add(modDiff);

        RequestVo requestCreated = capRequestCreated.getValue();
        assertNotNull(REQUEST_CREATED, requestCreated);
        assertEquals(REQUEST_MOD_TYPE, RequestType.MODIFICATION, requestCreated.getRequestType());
        assertEquals(SEC_APPROVAL_STATE, RequestState.PENDING_SECOND_REVIEW, requestCreated.getRequestState());
        assertEquals(REQUEST_ONE_DETAIL, 1, requestCreated.getRequestDetails().size());
        modDiff = (new ArrayList<ModDifferenceVo>(requestCreated.getRequestDetails())).get(0);
        assertEquals(DETAIL_TYPE_WRONG, FieldKey.CMOP_DISPENSE, modDiff.getDifference().getFieldKey());
        assertEquals("Request detail new value is incorrect", cmopDispenseNew, modDiff.getDifference().getNewValue());
        assertEquals(PRE_APPROVED, RequestItemStatus.APPROVED, modDiff.getModRequestItemStatus());
        assertFalse(ITEM_SHOULD_NOT_UPDATE, capItemUpdatedProd.hasCaptured());

        // Start another request flow up.
        info = continueWithAnotherRequestFlow(requestCreated, prod);
        RequestVo request = info.getRequest();
        prod = (ProductVo) info.getItem();

        // Simulate 2nd reviewer making another change to the mod request, for the same field as the first PNM changed.

        boolean cmopDispenseChg = prod.getCmopDispense();
        assertNotNull(CMOP_NAT_FAILED, cmopDispenseChg);

        // Begin the process to update a product.
        colModDiff.clear();

        // Update this field's setting
        boolean cmopDispenseNew2 = !cmopDispenseChg;

        diff = new Difference(FieldKey.CMOP_DISPENSE, cmopDispenseChg, cmopDispenseNew2);

        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason("test11");
        modDiff.setAcceptChange(true);

        colModDiff.add(modDiff);

        // Simulate the 2nd reviewer hitting the 'Accept' button from the request summary page.
        try {
            info = miCap.commitRequest(prod, request, colModDiff, reviewer2, false);
            fail("Should have thrown a conflict business rule exception.");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        ProductVo postProd = (ProductVo) info.getItem();
        RequestVo postRequest = info.getRequest();
        assertEquals(REQ_COMP, RequestState.COMPLETED, postRequest.getRequestState());
        assertEquals(TWO_REQ_DETAILS, 2, postRequest.getRequestDetails().size());
        Collection<ModDifferenceVo> modDiffs = new ArrayList<ModDifferenceVo>(postRequest.getRequestDetails());

        for (ModDifferenceVo modDiffNext : modDiffs) {
            if (FieldKey.CMOP_DISPENSE.equals(modDiffNext.getDifference().getFieldKey())) {
                assertEquals(REQ_DETAIL_APPR, RequestItemStatus.APPROVED, modDiffNext.getModRequestItemStatus());
                assertEquals(LAST_REVIEWER_INCORRECT, reviewer2.getUsername(), modDiffNext.getReviewer().getUsername());
            } else if (FieldKey.DO_NOT_MAIL.equals(modDiffNext.getDifference().getFieldKey())) {
                assertEquals(REQ_DETAIL_APPR, RequestItemStatus.APPROVED, modDiffNext.getModRequestItemStatus());
                assertEquals(LAST_REVIEWER_INCORRECT, reviewer2.getUsername(), modDiffNext.getReviewer().getUsername());
            } else {
                fail(UNEXP_FIELD_MOD);
            }
        }

        // The item should have been updated...verify this.
        ProductVo productUpdated = (ProductVo) capItemUpdatedProd.getValue();
        assertEquals(ITEM_UPDATED, postProd, productUpdated);

        boolean cmopDispenseUpdated = productUpdated.getCmopDispense();
        assertEquals(ITEM_CMOP_UPDATE, cmopDispenseNew, cmopDispenseUpdated);

        // DataField<Boolean> doNotMailUpdated = productUpdated.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        // assertEquals(ITEM_DO_NOT_MAIL, doNotMailNew, doNotMailUpdated);
    }

    /**
     * Test a first reviewer making and approving mods to a product at national...this shouldn't even create a request.
     * 
     * @throws Exception if error
     */
    public void testApproveProductModFromNationalOneReview() throws Exception {
        ProcessedRequestVo info;

        Collection<ModDifferenceVo> colModDiff = new ArrayList<ModDifferenceVo>();
        ModDifferenceVo modDiff = null;
        Difference diff = null;

        // Create a test Product, as if it was already added and approved.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

        // Start another request flow up.
        info = continueWithAnotherRequestFlow(null, prod);
        prod = (ProductVo) info.getItem();

        // Simulate a PNM making a change to a one-review field.
        DataField<Boolean> doNotMailOriginal = prod.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertNotNull(DO_NOT_MAIL_FIELD_FAILED, doNotMailOriginal);

        // Begin the process to update a product.
        colModDiff.clear();

        // Update this field's setting.
        DataField<Boolean> doNotMailNew = DataField.newInstance(FieldKey.DO_NOT_MAIL);
        doNotMailNew.setDataFieldId(doNotMailOriginal.getDataFieldId());
        doNotMailNew.setDefaultValue(false);
        doNotMailNew.selectValue(doNotMailOriginal.getValue() ? false : true); // Toggle value.
        doNotMailNew.setEditable(doNotMailOriginal.isEditable() ? false : true); // Toggle editable status.

        diff = new Difference(FieldKey.DO_NOT_MAIL, doNotMailOriginal, doNotMailNew);
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason(TEST2);
        modDiff.setAcceptChange(true);

        colModDiff.add(modDiff);

        // Update the product, which should NOT create a mod request.
        ProductVo returnedProduct = (ProductVo) miCap.commitModifications(colModDiff, prod, reviewer1).getItem();
        assertFalse("Request should NOT have been created", capRequestCreated.hasCaptured());
        assertTrue(ITEM_UPDATED, capItemUpdatedProd.hasCaptured());
        assertEquals(ITEM_UPDATED, returnedProduct, capItemUpdatedProd.getValue());
        DataField<Boolean> doNotMailUpdated = returnedProduct.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertEquals(ITEM_DO_NOT_MAIL, doNotMailNew, doNotMailUpdated);
    }

    /**
     * Test a first reviewer approving a 1-review VADF field change Product mod request from local 1.
     * 
     * @throws Exception if error
     */
    public void testApproveProductModFromLocalOneReview() throws Exception {
        ProcessedRequestVo info;
        Collection<ModDifferenceVo> colModDiff = new ArrayList<ModDifferenceVo>();
        ModDifferenceVo modDiff = null;
        Difference diff = null;

        // Create a test Product, as if it was submitted by a local site up to national.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.PENDING);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);
        DataField<Boolean> doNotMailOriginal = prod.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertNotNull(DO_NOT_MAIL_FIELD_FAILED, doNotMailOriginal);
        doNotMailOriginal.setEditable(false);

        // Simulate the PLM making a requested field change.

        colModDiff.clear();

        DataField<Boolean> doNotMailNew = DataField.newInstance(FieldKey.DO_NOT_MAIL);
        doNotMailNew.setDataFieldId(doNotMailOriginal.getDataFieldId());
        doNotMailNew.setDefaultValue(false);
        doNotMailNew.selectValue(doNotMailOriginal.getValue().booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        doNotMailNew.setEditable(false);

        diff = new Difference(FieldKey.DO_NOT_MAIL, doNotMailOriginal, doNotMailNew);
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason(TEST2);
        modDiff.setAcceptChange(true);
        modDiff.setRequestToMakeEditable(true); // Local's request to make this field editable.
        modDiff.setRequestToModifyValue(true); // Local's request to change the value of the field.
        modDiff.setSiteName("Local-1");
        modDiff.setModRequestItemStatus(RequestItemStatus.PENDING);
        modDiff.setRequestor(requesterLocal1);
        modDiff.setReviewer(null);

        colModDiff.add(modDiff);

        // Create a test Request.
        RequestVo request = new RequestVo(prod.getId(), prod.getEntityType(), RequestType.MODIFICATION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            requesterLocal1, // User requesting field change(s).
            RequestItemStatus.PENDING, // Don't care for mod requests.
            false, // Not under review.
            false, // Not marked for PSR.
            colModDiff);

        // Start the first request flow pass.
        info = continueWithAnotherRequestFlow(request, prod);
        RequestVo requestNoUpdated = info.getRequest();
        ProductVo prodNoUpdate = (ProductVo) info.getItem();

        // Simulate the 1st reviewer hitting the 'Accept' button from the request summary page (should complain about
        // 'pending' field-mod request).
        boolean isRuleExceptionSeen = false;
        Collection<ModDifferenceVo> modDiffs = new ArrayList<ModDifferenceVo>(0); // No new changes.

        try {
            info = miCap.commitRequest(prodNoUpdate, requestNoUpdated, modDiffs, reviewer1, false);
        } catch (BusinessRuleException bre) {
            isRuleExceptionSeen = true;
        }

        assertTrue("Business rule exception for having 'pending' field-mod requests not seen", isRuleExceptionSeen);
        assertFalse(ITEM_SHOULD_NOT_UPDATE, capItemUpdatedProd.hasCaptured());

        // Start another request flow up.
        info = continueWithAnotherRequestFlow(request, prod);
        request = info.getRequest();
        prod = (ProductVo) info.getItem();

        // Simulate the 1st reviewer hitting the 'Accept' button from the request summary page AGAIN, but with field-mod set
        // to 'approved'.
        assertEquals(REQUEST_ONE_DETAIL, 1, request.getRequestDetails().size());
        modDiffs = new ArrayList<ModDifferenceVo>(request.getRequestDetails());
        modDiff = ((List<ModDifferenceVo>) modDiffs).get(0);
        modDiff.setModRequestItemStatus(RequestItemStatus.APPROVED);
        assertEquals(REQUESTOR_SET_INCORRECT, requesterLocal1, modDiff.getRequestor());
        assertNull("Request detail last reviewer should not be set yet", modDiff.getReviewer());

        modDiffs = new ArrayList<ModDifferenceVo>(0); // No new changes.
        info = miCap.commitRequest(prod, request, modDiffs, reviewer1, false);
        ProductVo postProd = (ProductVo) info.getItem();
        RequestVo postRequest = info.getRequest();
        assertEquals(REQ_COMP, RequestState.COMPLETED, postRequest.getRequestState());
        assertEquals(REQUESTOR_SHOULD_SET, requesterLocal1, postRequest.getNewItemRequestor());
        assertEquals(LAST_REVIEWER, reviewer1, postRequest.getLastReviewer());
        assertEquals(REQUEST_ONE_DETAIL, 1, postRequest.getRequestDetails().size());
        modDiffs = new ArrayList<ModDifferenceVo>(postRequest.getRequestDetails());
        modDiff = ((List<ModDifferenceVo>) modDiffs).get(0);
        assertEquals(REQUESTOR_SET_INCORRECT, requesterLocal1, modDiff.getRequestor());
        assertEquals(LAST_REVIEWER_INCORRECT, reviewer1, modDiff.getReviewer());

        // The item should have been updated...verify this.
        assertTrue(ITEM_UPDATED, capItemUpdatedProd.hasCaptured());
        ProductVo productUpdated = (ProductVo) capItemUpdatedProd.getValue();
        assertEquals(ITEM_UPDATED, postProd, productUpdated);
        DataField<Boolean> doNotMailUpdated = productUpdated.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertEquals("Item's Do Not Mail value should have been updated", doNotMailNew.getValue(), doNotMailUpdated.getValue());
        assertEquals("Item's Do Not Mail editable status should have been unlocked", true, doNotMailUpdated.isEditable());
    }

    /**
     * Test a first reviewer rejecting a new Product request from local 1. A second review should not be needed.
     * 
     * @throws Exception if error
     */

    /*   public void testRejectProductAddFromLocal() throws Exception {
        ProcessedRequestVo info;

        // Create a test Product, as if it was submitted by a local site up to national.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.PENDING);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

        // Create a test Request.
        RequestVo request = new RequestVo(prod.getId(), prod.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            requesterLocal1, RequestItemStatus.PENDING, false, // Not under review.
            false, // Not marked for PSR.
            null); // No request details yet.
        info = continueWithAnotherRequestFlow(request, prod);
        RequestVo requestNoUpdate = info.getRequest();
        ProductVo prodNoUpdate = (ProductVo) info.getItem();

        // Simulate the 1st reviewer hitting the 'Reject' button on the request.
        requestNoUpdate = miCap.rejectRequest(prodNoUpdate, requestNoUpdate, null, reviewer1);
        assertEquals("Request new-item-req-status should be rejected", RequestItemStatus.REJECTED, requestNoUpdate
            .getNewItemRequestStatus());
        assertFalse("Request should NOT be under review", requestNoUpdate.isUnderReview());

        // Simulate the 1st reviewer hitting the 'Accept' button from the request summary page, but with no reject reason
        // info filled out...should do nothing!
        boolean isRuleExceptionSeen = false;
        Collection<ModDifferenceVo> modDiffs = new ArrayList<ModDifferenceVo>(0);

        try {
            info = miCap.commitRequest(prodNoUpdate, requestNoUpdate, modDiffs, reviewer1);
        }
        catch (BusinessRuleException bre) {
            isRuleExceptionSeen = true;
        }

        assertTrue("Business rule exception for not having rejection reason filled out not seen", isRuleExceptionSeen);
        assertFalse(ITEM_SHOULD_NOT_UPDATE, capItemUpdatedProd.hasCaptured());

        // Start another request flow up.
        info = continueWithAnotherRequestFlow(request, prod);
        request = info.getRequest();
        prod = (ProductVo) info.getItem();

        // Simulate the 1st reviewer hitting the 'Reject' button on the request.
        request = miCap.rejectRequest(prod, request, null, reviewer1);
        assertEquals("Request new-item-req-status should be rejected", RequestItemStatus.REJECTED, request
            .getNewItemRequestStatus());
        assertFalse("Request should NOT be under review", request.isUnderReview());

        // Simulate the 1st reviewer hitting the 'Accept' button from the request summary page, but with reject reason info
        // filled out...should complete request!
        modDiffs = new ArrayList<ModDifferenceVo>(0);

        request.setRequestRejectReason(RequestRejectionReason.INAPPROPRIATE_REQUEST);

        info = miCap.commitRequest(prod, request, modDiffs, reviewer1);
        ProductVo postProd = (ProductVo) info.getItem();
        RequestVo postRequest = info.getRequest();
        assertEquals(REQ_COMP, RequestState.COMPLETED, postRequest.getRequestState());
        assertEquals("Request should NOT have request details", 0, postRequest.getRequestDetails().size());

        // The item should have been rejected/inactivated...verify this.
        assertTrue(ITEM_UPDATED, capItemUpdatedProd.hasCaptured());
        ProductVo productUpdated = (ProductVo) capItemUpdatedProd.getValue();
        assertEquals(ITEM_UPDATED, postProd, productUpdated);
        assertEquals("Item should be inactivated", gov.va.med.pharmacy.peps.common.vo.ItemStatus.INACTIVE, productUpdated
            .getItemStatus());
        assertEquals("Item should be inactivated", RequestItemStatus.REJECTED, productUpdated.getRequestItemStatus());
    }
    */

//    /**
//     * Test a first reviewer rejecting a new NDC request from local 1. A second review should not be needed.
//     * 
//     * @throws Exception if error
//     */

    /*  public void testRejectNdcAddFromLocal() throws Exception {
          ProcessedRequestVo info;

          // Create a test NDC, as if it was submitted by a local site up to national.
          NdcGenerator ndcGen = new NdcGenerator();
          NdcVo ndc = ndcGen.generateMinimalNdc();
          ndc.setRequestItemStatus(RequestItemStatus.PENDING);
          ndc.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
          ndc.setId("42");
          ndc.setLocalUse(false);

          // Create a test Request.
          RequestVo request = new RequestVo(ndc.getId(), ndc.getEntityType(), RequestType.ADDITION,
              RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
              requesterLocal1, RequestItemStatus.PENDING, false, // Not under review.
              false, // Not marked for PSR.
              null); // No request details yet.
          info = continueWithAnotherRequestFlow(request, ndc);
          RequestVo requestNoUpdate = info.getRequest();
          NdcVo ndcNoUpdate = (NdcVo) info.getItem();

          // Simulate the 1st reviewer hitting the 'Reject' button on the request.
          requestNoUpdate = miCap.rejectRequest(ndcNoUpdate, requestNoUpdate, null, reviewer1);
          assertEquals("Request new-item-req-status should be rejected", RequestItemStatus.REJECTED, requestNoUpdate
              .getNewItemRequestStatus());
          assertFalse("Request should NOT be under review", requestNoUpdate.isUnderReview());

          // Simulate the 1st reviewer hitting the 'Accept' button from the request summary page, but with no reject reason
          // info filled out...should do nothing!
          boolean isRuleExceptionSeen = false;
          Collection<ModDifferenceVo> modDiffs = new ArrayList<ModDifferenceVo>(0);

          try {
              info = miCap.commitRequest(ndcNoUpdate, requestNoUpdate, modDiffs, reviewer1);
          }
          catch (BusinessRuleException bre) {
              isRuleExceptionSeen = true;
          }

          assertTrue("Business rule exception for not having rejection reason filled out not seen", isRuleExceptionSeen);
          assertFalse(ITEM_SHOULD_NOT_UPDATE, capItemUpdated.hasCaptured());

          // Start another request flow up.
          info = continueWithAnotherRequestFlow(request, ndc);
          request = info.getRequest();
          ndc = (NdcVo) info.getItem();

          // Simulate the 1st reviewer hitting the 'Reject' button on the request.
          request = miCap.rejectRequest(ndc, request, null, reviewer1);
          assertEquals("Request new-item-req-status should be rejected", RequestItemStatus.REJECTED, request
              .getNewItemRequestStatus());
          assertFalse("Request should NOT be under review", request.isUnderReview());

          // Simulate the 1st reviewer hitting the 'Accept' button from the request summary page, but with reject reason info
          // filled out...should complete request!
          modDiffs = new ArrayList<ModDifferenceVo>(0);

          request.setRequestRejectReason(RequestRejectionReason.INAPPROPRIATE_REQUEST);

          info = miCap.commitRequest(ndc, request, modDiffs, reviewer1);
          NdcVo postNdc = (NdcVo) info.getItem();
          RequestVo postRequest = info.getRequest();
          assertEquals(REQ_COMP, RequestState.COMPLETED, postRequest.getRequestState());
          assertEquals("Request should NOT have request details", 0, postRequest.getRequestDetails().size());

          // The item should have been rejected/inactivated...verify this.
          assertTrue(ITEM_UPDATED, capItemUpdated.hasCaptured());
          NdcVo ndcUpdated = (NdcVo) capItemUpdated.getValue();
          assertEquals(ITEM_UPDATED, postNdc, ndcUpdated);
          assertEquals("Item should be inactivated", gov.va.med.pharmacy.peps.common.vo.ItemStatus.INACTIVE, ndcUpdated
              .getItemStatus());
          assertEquals("Item should be inactivated", RequestItemStatus.REJECTED, ndcUpdated.getRequestItemStatus());
      }
    */

    /**
     * test setup
     * 
     * @throws Exception if error
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void buildMocks() throws Exception {
        mocks.clear();

        EnvironmentUtilityStub national = new EnvironmentUtilityStub();
        national.setNational();

        // Create classes under test.
        miCap = new ManagedItemCapabilityImpl();
        reqCap = new RequestCapabilityImpl();
        reqCap.setRequestStateMachineFactory(new RequestStateMachineFactory(national));

        NationalSettingDomainCapability natSetDomCap = EasyMock.createMock(NationalSettingDomainCapability.class);

        EasyMock.expect(natSetDomCap.retrieve()).andReturn(new ArrayList<NationalSettingVo>()).anyTimes();
        EasyMock.replay(natSetDomCap);

        DrugReferenceCapability drugRefCap = EasyMock.createMock(DrugReferenceCapability.class);

        miCap.setNationalSettingDomainCapability(natSetDomCap);
        miCap.setDrugReferenceCapability(drugRefCap);

        RequestDomainCapability reqDomCap = createMock(RequestDomainCapability.class);

        retrieveRequestAnswer = new IAnswer<RequestVo>() {

            public RequestVo answer() throws Throwable {
                return requestInstanceThatRepresentsDatabaseCopy;
            }
        };

        // Mock up the retriveNonCompletedRequest method to always return a null
        EasyMock
            .expect(reqDomCap.retrieveNonCompletedRequest((String) EasyMock.anyObject(), (EntityType) EasyMock.anyObject()))
            .andReturn(null).anyTimes();

        EasyMock.expect(reqDomCap.retrieve((String) EasyMock.anyObject())).andAnswer(retrieveRequestAnswer).anyTimes();
        capRequestCreated = new Capture<RequestVo>();
        createRequestAnswer = new IAnswer<RequestVo>() {

            public RequestVo answer() throws Throwable {
                RequestVo reqToReturn = (RequestVo) (EasyMock.getCurrentArguments()[0]);

                // Simulate filling out IDs.
                // if (reqToReturn.getId() != null) {
                // throw new IllegalArgumentException("Create processes should not have IDs already.");
                // }

                reqToReturn.setId("r" + System.currentTimeMillis());
                Collection<ModDifferenceVo> mods = reqToReturn.getRequestDetails();

                if (mods != null && mods.size() > 0) {
                    for (ModDifferenceVo mod : mods) {
                        if (mod.getId() == null) {
                            mod.setId("rd" + System.currentTimeMillis());
                        }
                    }
                }

                return reqToReturn;
            }
        };

        EasyMock.expect(
            reqDomCap.create(EasyMock.and((RequestVo) EasyMock.anyObject(), EasyMock.capture(capRequestCreated)),
                (UserVo) EasyMock.anyObject())).andAnswer(createRequestAnswer);

        EasyMock.expect(reqDomCap.updateRequest((RequestVo) EasyMock.anyObject(), (UserVo) EasyMock.anyObject())).andAnswer(
            createRequestAnswer);

        createReqDetailsRequest = new IAnswer<RequestVo>() {

            public RequestVo answer() throws Throwable {
                return (RequestVo) (EasyMock.getCurrentArguments()[0]);
            }
        };

        EasyMock.expect(reqDomCap.createRequestDetails((RequestVo) EasyMock.anyObject(), (UserVo) EasyMock.anyObject()))
            .andAnswer(createReqDetailsRequest);
        EasyMock.replay(reqDomCap); // if EasyMock.replay is not called mock won't be wired up and ready for use
        reqCap.setRequestDomainCapability(reqDomCap);

        // Provide dependencies to managed item cap.
        VistaFileSynchCapability vistaCap = createMock(VistaFileSynchCapability.class);
        vistaCap.sendModifiedItemToVista((ManagedItemVo) EasyMock.anyObject(),
            (Collection<Difference>) EasyMock.anyObject(), (UserVo) EasyMock.anyObject(), EasyMock.anyBoolean(),
            EasyMock.anyBoolean());

        EasyMock.replay(vistaCap);

        SendToLocalCapability sendToLocalCap = createMock(SendToLocalCapability.class);
        sendToLocalCap.send((ManagedItemVo) EasyMock.anyObject());
        EasyMock.replay(sendToLocalCap);

        NotificationDomainCapability notifCap = createMock(NotificationDomainCapability.class);
        EasyMock.expect(notifCap.create((NotificationVo) EasyMock.anyObject(), (UserVo) EasyMock.anyObject())).andReturn(
            new NotificationVo());
        EasyMock.replay(notifCap);

        RulesCapability rulesCap = createMock(RulesCapability.class);
        rulesCap.enforceRules((ManagedItemVo) EasyMock.anyObject(), (UserVo) EasyMock.anyObject(), EasyMock.anyBoolean());
        rulesCap.enforceRules((ManagedItemVo) EasyMock.anyObject(), (UserVo) EasyMock.anyObject(), EasyMock.anyBoolean());

        // EasyMock.expect(rulesCap.checkForWarnings((Collection<ModDifferenceVo>) EasyMock.anyObject())).andReturn(
        // new Errors());
        EasyMock.expect(
            rulesCap.checkForWarnings((ManagedItemVo) EasyMock.anyObject(), (Collection<ModDifferenceVo>) EasyMock
                .anyObject(), EasyMock.anyBoolean())).andReturn(new Errors()).anyTimes();
        Capture<ManagedItemVo> rulesCapabilityCapture = new Capture<ManagedItemVo>();

        IAnswer<ManagedItemVo> rulesCapabilityAnswer = new IAnswer<ManagedItemVo>() {

            public ManagedItemVo answer() throws Throwable {
                return (ManagedItemVo) (EasyMock.getCurrentArguments()[1]);
            }
        };

        EasyMock.expect(
            rulesCap.processCompletedRequest((RequestVo) EasyMock.anyObject(), EasyMock.capture(rulesCapabilityCapture),
                (Collection<ModDifferenceVo>) EasyMock.anyObject(), (UserVo) EasyMock.anyObject())).andAnswer(
                rulesCapabilityAnswer);

        EasyMock.replay(rulesCap);

        // Create generic managed item domain mock:

        final ManagedItemDomainCapability miDomCap = createMock(ManagedItemDomainCapability.class);
        capItemUpdated = new Capture<ManagedItemVo>();

        updateItemAnswer = new IAnswer<ManagedItemVo>() {

            public ManagedItemVo answer() throws Throwable {
                return (ManagedItemVo) (EasyMock.getCurrentArguments()[0]);
            }
        };

        retrieveItemAnswer = new IAnswer<ManagedItemVo>() {

            public ManagedItemVo answer() throws Throwable {
                return itemInstanceThatRepresentsDatabaseCopy;
            }
        };

        EasyMock.expect(miDomCap.retrieve((String) EasyMock.anyObject())).andAnswer(retrieveItemAnswer);
        EasyMock.expect(
            miDomCap.update(EasyMock.and((ManagedItemVo) EasyMock.anyObject(), EasyMock.capture(capItemUpdated)),
                (UserVo) EasyMock.anyObject())).andAnswer(updateItemAnswer);

        getRevisionNumberAnswer = new IAnswer<Long>() {

            public Long answer() throws Throwable {
                return (Long) (itemInstanceThatRepresentsDatabaseCopy.getRevisionNumber());
            }
        };

        EasyMock.expect(miDomCap.getRevisionNumber((String) EasyMock.anyObject())).andAnswer(getRevisionNumberAnswer);
        EasyMock.replay(miDomCap);

        // Create a product specific item domain mock:

        final ProductDomainCapability productDomCap = createMock(ProductDomainCapability.class);
        capItemUpdatedProd = new Capture<ProductVo>();

        updateItemAnswerProd = new IAnswer<ProductVo>() {

            public ProductVo answer() throws Throwable {
                return (ProductVo) (EasyMock.getCurrentArguments()[0]);
            }
        };

        retrieveItemAnswerProd = new IAnswer<ProductVo>() {

            public ProductVo answer() throws Throwable {
                return (ProductVo) itemInstanceThatRepresentsDatabaseCopy;
            }
        };

        EasyMock.expect(productDomCap.retrieve((String) EasyMock.anyObject())).andAnswer(retrieveItemAnswerProd);
        EasyMock.expect(
            productDomCap.update(EasyMock.and((ProductVo) EasyMock.anyObject(), EasyMock.capture(capItemUpdatedProd)),
                (UserVo) EasyMock.anyObject())).andAnswer(updateItemAnswerProd);
        EasyMock.expect(productDomCap.getRevisionNumber((String) EasyMock.anyObject())).andAnswer(getRevisionNumberAnswer);
        EasyMock.expect(productDomCap.getCmopIdForVaPrintName(
            (String) EasyMock.anyObject(), "CAP")).andReturn("1111").anyTimes();
        EasyMock.replay(productDomCap);

        // Wire up dependencies to the managed item cap factory.

        ManagedItemCapabilityFactory miCapFactory = createMock(ManagedItemCapabilityFactory.class);
        EasyMock.expect(miCapFactory.getRulesCapability((EntityType) EasyMock.anyObject())).andReturn(rulesCap).anyTimes();

        final Capture<EntityType> capEntityType = new Capture<EntityType>();

        IAnswer<ManagedItemDomainCapability> retrieveManagedItemDomain = new IAnswer<ManagedItemDomainCapability>() {

            public ManagedItemDomainCapability answer() throws Throwable {
                EntityType entType = capEntityType.getValue();

                if (EntityType.PRODUCT.equals(entType)) {
                    return productDomCap;
                } else {
                    return miDomCap;
                }
            }
        };

        EasyMock.expect(
            miCapFactory.getDomainCapability(EasyMock
                .and((EntityType) EasyMock.anyObject(), EasyMock.capture(capEntityType)))).andAnswer(
                retrieveManagedItemDomain).anyTimes();
        EasyMock.replay(miCapFactory);

        miCap.setRequestCapability(reqCap);
        miCap.setRequestDomainCapability(reqDomCap);
        miCap.setEnvironmentUtility(national);
        miCap.setManagedItemCapabilityFactory(miCapFactory);
        miCap.setVistaFileSynchCapability(vistaCap);

        //      miCap.setStsCapability(stsInterfaceCapability);
        //      miCap.setSendToLocalCapability(sendToLocalCap);
        miCap.setNotificationDomainCapability(notifCap);
    }

    /**
     * Handles setting up for another request flow, using a previous RequestVo instance as a simulated saved request that is
     * to be retrieved and processed for the next flow of request processing. <BR>
     * This call should be made, for example, if to simulate a 2nd reviewer reviewing a request that the 1st reviewer
     * finished reviewing.
     * 
     * @param previousRequest The request that simulates a request saved to the database, that is to be retrieved and
     *            processed in another flow of request processing.
     * @param previousItem previous {@link ManagedItemVo}
     * @return RequestVo The request that is ready to be processed for the next request flow steps.
     * 
     * @throws Exception if error
     */
    protected ProcessedRequestVo continueWithAnotherRequestFlow(RequestVo previousRequest, ManagedItemVo previousItem)
        throws Exception {

        ProcessedRequestVo info = new ProcessedRequestVo(previousItem, previousRequest, new Errors());

        // Set the IDs of the previous request, if specified.
        if (previousRequest != null) {

            // ensure that id's are non-null.

            if (previousRequest.getId() == null) {
                previousRequest.setId("r" + System.currentTimeMillis());
            }

            Collection<ModDifferenceVo> mods = previousRequest.getRequestDetails();

            if (mods != null && mods.size() > 0) {
                for (ModDifferenceVo mod : mods) {
                    if (mod.getId() == null) {
                        mod.setId("rd" + System.currentTimeMillis());
                    }
                }
            }

        }

        // Set the ID of the previous item, if specified.
        if (previousItem != null) {
            if (previousItem.getId() == null) {
                previousItem.setId("i" + System.currentTimeMillis());
            }
        }

        // Doesn't work for some reason...resetMocks();
        buildMocks();

        // Make a copy of the previous Request to place into the request domain capability mock.
        //11/12/2008 - need to remove this dependency...instead, we should compare the currently updated
        // RequestVo
        // to the original one, as provided by the Struts ManagedItemAction class.
        requestInstanceThatRepresentsDatabaseCopy = (previousRequest == null ? null : (RequestVo) copyItem(previousRequest));

        itemInstanceThatRepresentsDatabaseCopy = (previousItem == null ? null : (ManagedItemVo) copyItem(previousItem));

        // RequestDomainCapability reqDomCap = createMock(RequestDomainCapability.class);
        // reqDomCap.updateRequestDetails((RequestVo) EasyMock.anyObject());
        // reqDomCap.updateRequest((RequestVo) EasyMock.anyObject());
        // EasyMock.expect(reqDomCap.retrieveRequestDetailById((String) EasyMock.anyObject())).andReturn(originalRequest);
        // EasyMock.replay(reqDomCap); // if EasyMock.replay is not called mock won't be wired up and ready for use
        // reqCap.setRequestDomainCapability(reqDomCap);

        info.setItem(itemInstanceThatRepresentsDatabaseCopy);
        info.setRequest(requestInstanceThatRepresentsDatabaseCopy);

        return info;
    }

    /**
     * Create a copy of the specified ManagedItemVo. <BR>
     * Typical usage is to copy an item VO prior to making system updates that are then finally validated. If the item proves
     * to be invalid, these system updates should not be seen by the user, hence the original item VO should not be touched
     * by these system updates.
     * 
     * @param itemOrig The ManagedItemVo instance to copy.
     * @return ManagedItemVo A copy of the input item instance.
     */
    protected ValueObject copyItem(ValueObject itemOrig) {
        ValueObject itemCopy = null;

        try {
            itemCopy = itemOrig.clone();
        } catch (CloneNotSupportedException cnse) {
            throw new CommonException(cnse, CommonException.UNEXPECTED_EXCEPTION_SEEN);
        }

        return itemCopy;
    }
}
