/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine.add.test;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.utility.impl.test.EnvironmentUtilityStub;
import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.ProductGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachineFactory;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.add.ApprovedAddState;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.add.PendingSecondApprovedAddState;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.add.RejectedAddState;

import junit.framework.TestCase;


/**
 * getOldValueShortString
 */
public class AddStateMachineTest extends TestCase {

    private String testName = "TEST NAME 4012353";
    private UserVo reviewer1;
    private UserVo reviewer2;
    private UserVo localRequester;
    private Collection<ModDifferenceVo> differences;
    private Collection<ModDifferenceVo> blankArray;

    private RequestStateMachineFactory factory;

    /**
     * test setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {
        reviewer1 = new UserGenerator().getNationalManagerOne();

        reviewer2 = new UserGenerator().getNationalManagerTwo();

        localRequester = new UserGenerator().getLocalManagerOne();

        differences = new ArrayList<ModDifferenceVo>();
        blankArray = new ArrayList<ModDifferenceVo>();

        EnvironmentUtilityStub environmentUtility = new EnvironmentUtilityStub();
        environmentUtility.setNational();
        factory = new RequestStateMachineFactory(environmentUtility);
    }

    /**
     * Uses a manufacturer, tests a single approval item to see if the state transitions are correct
     * 
     * @throws Exception Exception
     */
    public void testApproveSingleApproval() throws Exception {

        // set the productGenerator for testApproveSingleApproval 
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

        // set the NdcGenerator for testApproveSingleApproval
        NdcGenerator ndcGen = new NdcGenerator();
        NdcVo ndc = ndcGen.generateMinimalNdc();
        ndc.setRequestItemStatus(RequestItemStatus.PENDING);
        ndc.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        ndc.setId("42");
        ndc.setLocalUse(false);

        // Create a test Request for testApproveSingleApproval.
        RequestVo request = new RequestVo(ndc.getId(), ndc.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            localRequester, RequestItemStatus.PENDING, false, // Not under review.
            false, // Not marked for PSR.
            null); // No request details yet.

        RequestStateMachine stateTester = factory.getRequestStateMachine(ndc, prod, request);

        request = request.copy();
        request.setNewItemRequestStatus(RequestItemStatus.APPROVED);

        stateTester = stateTester.process(request, blankArray, reviewer1, false);

        assertTrue("Single Review Approval did not transition to the correct state. ",
            stateTester instanceof PendingSecondApprovedAddState);
        assertFalse("Single Review item SingleApproval State not marked as completed.", stateTester.isCompleted());
    }

    /** Uses a manufacturer, tests a single approval item to see if the state transitions are correct
     * 
     * @throws Exception Exception
     */
    public void testNewNDCPendingProduct() throws Exception {

        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.PENDING);
        prod.setItemStatus(ItemStatus.ACTIVE);
        prod.setId("43");
        prod.setLocalUse(false);

        NdcGenerator ndcGen = new NdcGenerator();
        NdcVo ndc = ndcGen.generateMinimalNdc();
        ndc.setRequestItemStatus(RequestItemStatus.PENDING);
        ndc.setItemStatus(ItemStatus.ACTIVE);
        ndc.setId("43");
        ndc.setLocalUse(false);

        // Create a test Request.
        RequestVo request = new RequestVo(ndc.getId(), ndc.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_SECOND_REVIEW, reviewer1, reviewer1, RequestItemStatus.APPROVED, false, false,
            null);

        RequestStateMachine stateTester = factory.getRequestStateMachine(ndc, prod, request);

//        request = request.copy();
//        request.setNewItemRequestStatus(RequestItemStatus.APPROVED);

        stateTester = stateTester.process(request, blankArray, reviewer1, false);

        assertTrue("1.Single Review Approval did not transition to the correct state.",
            stateTester instanceof PendingSecondApprovedAddState);
        assertFalse("1.Single Review item SingleApproval State marked as completed.", stateTester.isCompleted());
    }

    /** Uses a manufacturer, tests a single approval item to see if the state transitions are correct
     * 
     * @throws Exception Exception
     */
    public void testPendingNDCApprovedProduct() throws Exception {

        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

        NdcGenerator ndcGen = new NdcGenerator();
        NdcVo ndc = ndcGen.generateMinimalNdc();
        ndc.setRequestItemStatus(RequestItemStatus.PENDING);
        ndc.setItemStatus(ItemStatus.ACTIVE);
        ndc.setId("42");
        ndc.setLocalUse(false);

        // Create a test Request.
        RequestVo request = new RequestVo(ndc.getId(), ndc.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_SECOND_REVIEW, reviewer1, reviewer1, RequestItemStatus.APPROVED, false, false,
            null);

        RequestStateMachine stateTester = factory.getRequestStateMachine(ndc, prod, request);

        request = request.copy();
        request.setNewItemRequestStatus(RequestItemStatus.APPROVED);

        stateTester = stateTester.process(request, blankArray, reviewer2, false);

        assertTrue("Single Review Approval did not transition to the correct state.",
            stateTester instanceof ApprovedAddState);
        assertTrue("Single Review item SingleApproval State marked as not completed.", stateTester.isCompleted());
    }

    /**
     * Uses a manufacturer, tests a single approval item with a modification to see if the state transitions are correct
     * 
     * @throws Exception Exception
     */
    public void testApproveSingleApprovalWithModifications() throws Exception {

        // testApproveSingleApprovalWithModifications
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

     // testApproveSingleApprovalWithModifications
        NdcGenerator ndcGen = new NdcGenerator();
        NdcVo ndc = ndcGen.generateMinimalNdc();
        ndc.setRequestItemStatus(RequestItemStatus.PENDING);
        ndc.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        ndc.setId("42");
        ndc.setLocalUse(false);

        // Create a test Request for testApproveSingleApprovalWithModifications
        RequestVo request = new RequestVo(ndc.getId(), ndc.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            localRequester, RequestItemStatus.PENDING, false, // Not under review.
            false, // Not marked for PSR.
            null); // No request details yet.

        RequestStateMachine stateTester = factory.getRequestStateMachine(ndc, prod, request);

        request = request.copy();
        request.setNewItemRequestStatus(RequestItemStatus.APPROVED);
        NdcVo ndc2 = ndc.copy();

        ndc2.setTradeName("Bob");

        differences = ndc2.compareDifferences(ndc);
        stateTester = stateTester.process(request, differences, reviewer1, false);

        assertTrue("Single Review Approval did not transition to the correct state",
            stateTester instanceof PendingSecondApprovedAddState);
        assertFalse("Single Review item SingleApproval State not marked as completed", stateTester.isCompleted());
    }

    /**
     * Uses a manufacturer, tests a single rejection item to see if the state transitions are correct
     * 
     * @throws Exception Exception
     */
    public void testApproveSingleRejection() throws Exception {

        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

        NdcGenerator ndcGen = new NdcGenerator();
        NdcVo ndc = ndcGen.generateMinimalNdc();
        ndc.setRequestItemStatus(RequestItemStatus.PENDING);
        ndc.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        ndc.setId("42");
        ndc.setLocalUse(false);

        // Create a test Request.
        RequestVo request = new RequestVo(ndc.getId(), ndc.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            localRequester, RequestItemStatus.PENDING, false, // Not under review.
            false, // Not marked for PSR.
            null); // No request details yet.

        RequestStateMachine stateTester = factory.getRequestStateMachine(ndc, prod, request);

        request = request.copy();
        request.setNewItemRequestStatus(RequestItemStatus.REJECTED);
        request.setRequestRejectionReason(RequestRejectionReason.INAPPROPRIATE_REQUEST);

        stateTester = stateTester.process(request, blankArray, reviewer1, false);

        assertTrue("Single Review Rejection did not transition to the correct state",
            stateTester instanceof RejectedAddState);
        assertTrue("Single Review item RejectedAdd State not marked as completed", stateTester.isCompleted());
    }

    /**
     * Uses a Generic, tests a two review item rejection to see if the state transitions are correct
     * 
     * @throws Exception Exception
     */
    public void testTwoApprovalRejectImmediately() throws Exception {

        
        // Create a test Product, as if it was submitted by a local site up to national.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo product = prodGen.getFirst();
        product.setRequestItemStatus(RequestItemStatus.PENDING);
        product.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        product.setId("66");
        product.setLocalUse(false);

        // Create a test Request as if it came up from a local site.
        RequestVo request = new RequestVo(product.getId(), product.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            localRequester, // The user requesting this addition.
            RequestItemStatus.PENDING, false, false, // Not marked for PSR.
            null); // No request details yet.

        RequestStateMachine stateTester = factory.getRequestStateMachine(product, null, request);

        request = request.copy();
        request.setNewItemRequestStatus(RequestItemStatus.REJECTED);
        request.setRequestRejectionReason(RequestRejectionReason.INAPPROPRIATE_REQUEST);

        stateTester = stateTester.process(request, blankArray, reviewer1, false);

        assertTrue("Two review item Immediate Rejection did not transition to the correct state",
            stateTester instanceof RejectedAddState);
        assertTrue("Two review item RejectedAdd State not marked as completed!", stateTester.isCompleted());
    }

    /**
     * Uses a Generic, tests a two review item simple approval path to see if the state transitions are correct
     * 
     * @throws Exception Exception
     */
    public void testTwoApproval() throws Exception {

        // Create a test Product, as if it was submitted by a local site up to national.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.PENDING);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

        // Create a test Request as if it came up from a local site for testTwoApproval in AddMachineTest
        RequestVo request = new RequestVo(prod.getId(), prod.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            localRequester, // The user requesting this addition.
            RequestItemStatus.PENDING, false, // Not under review.
            false, // Not marked for PSR.
            null); // No request details yet so this field should still be null.

        RequestStateMachine stateTester = factory.getRequestStateMachine(prod, null, request);

        request = request.copy();
        request.setNewItemRequestStatus(RequestItemStatus.APPROVED);

        stateTester = stateTester.process(request, blankArray, reviewer1, false);

        assertTrue("Two review item first approval did not transition to the correct state.",
            stateTester instanceof PendingSecondApprovedAddState);
        assertFalse("Two review item SingleApproval State marked as completed.", stateTester.isCompleted());

        // Test to make sure the same approver can't approve this thing twice

        stateTester = factory.getRequestStateMachine(prod, null, request);

        stateTester = stateTester.process(request, blankArray, reviewer1, false);

        assertTrue(" Two review item redundant approval did not transition to the correct state",
            stateTester instanceof PendingSecondApprovedAddState);
        assertFalse(" Two review item SingleApproval State marked as completed", stateTester.isCompleted());

        stateTester = factory.getRequestStateMachine(prod, null, request);

        stateTester = stateTester.process(request, blankArray, reviewer2, false);

        assertTrue("Two review item second approval did not transition to the correct state. ",
            stateTester instanceof ApprovedAddState);
        assertTrue("Two review item  ApprovedAdd State not marked as completed", stateTester.isCompleted());
    }

    /**
     * Uses a Generic, tests a two review item slightly more complicated approval path to see if the state transitions are
     * correct
     * 
     * First user modifies and approves, second approves
     * 
     * @throws Exception Exception
     */
    public void testTwoApprovalWithFirstMods() throws Exception {
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.PENDING);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        
        prod.setId("42");
        prod.setLocalUse(false);

        // Create a test Request as if it came up from a local site for the testTwoApprovalWithFirstMods test
        RequestVo request = new RequestVo(prod.getId(), prod.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            localRequester, // The user requesting this addition.
            RequestItemStatus.PENDING, false, // Not under review.
            false, // Not marked for PSR.
            null); // No request details yet.

        // set the testor for testTwoApprovalWithFirstMods.
        RequestStateMachine stateTester = factory.getRequestStateMachine(prod, null, request);
        request = request.copy();
        request.setNewItemRequestStatus(RequestItemStatus.APPROVED);
        ProductVo prod2 = prod.copy();
        prod2.setVaProductName(testName);

        differences = prod2.compareDifferences(prod);

        for (ModDifferenceVo mod : differences) {
            if (mod.getId() == null) {
                mod.setId(String.valueOf(System.currentTimeMillis()));
            }
        }

        stateTester = stateTester.process(request, differences, reviewer1, false);

        assertTrue("Two review item first approval with mods did not transition to the correct state",
            stateTester instanceof PendingSecondApprovedAddState);
        assertFalse("Two review item SingleApproval State marked as completed! ", stateTester.isCompleted());

        // Test to make sure the same approver can't approve this thing twice
        stateTester = stateTester.process(request, blankArray, reviewer1, false);

        assertTrue("Two review item redundant approval with mods did not transition to the correct state",
            stateTester instanceof PendingSecondApprovedAddState);
        assertFalse("Two review item SingleApproval  State marked as completed", stateTester.isCompleted());

        // Test to make sure the same approver can't approve this thing twice
        stateTester = stateTester.process(request, blankArray, reviewer2, false);

        assertTrue("Two review item second approval did not transition to the correct state!  ",
            stateTester instanceof ApprovedAddState);
        assertTrue("Two review item ApprovedAdd State not marked as completed .", stateTester.isCompleted());
    }

    /**
     * Uses a Generic, tests a two review item slightly more complicated approval path to see if the state transitions are
     * correct
     * 
     * First user modifies and approves, second approves
     * 
     * @throws Exception Exception
     */
    public void testTwoApprovalWithSecondMods() throws Exception {
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.PENDING);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("43");
        prod.setLocalUse(false);

        // Create a test Request as if it came up from a local site.
        RequestVo request = new RequestVo(prod.getId(), prod.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            localRequester, // The user requesting this addition.
            RequestItemStatus.PENDING, false, // Not under review.
            false, // Not marked for PSR.
            null); // No request details yet.

        RequestStateMachine stateTester = factory.getRequestStateMachine(prod, null, request);

        request = request.copy();
        request.setNewItemRequestStatus(RequestItemStatus.APPROVED);

        stateTester = stateTester.process(request, blankArray, reviewer1, false);

        assertTrue("1.Two review item first approval did not transition to the correct state",
            stateTester instanceof PendingSecondApprovedAddState);
        assertFalse("1.Two review item SingleApproval State marked as completed", stateTester.isCompleted());

        // Test to make sure the same approver can't approve this thing twice
        stateTester = stateTester.process(request, blankArray, reviewer1, false);

        assertTrue("Two review item redundant approval did not transition to the correct state",
            stateTester instanceof PendingSecondApprovedAddState);
        assertFalse("2.Two review item SingleApproval State marked as completed", stateTester.isCompleted());

        ProductVo prod2 = prod.copy();

        prod2.setVaProductName(testName);
        prod2.setVaPrintName(testName);

        differences = prod2.compareDifferences(prod);

        for (ModDifferenceVo mod : differences) {
            if (mod.getId() == null) {
                mod.setId(String.valueOf(System.currentTimeMillis()));
            }
        }

        stateTester = stateTester.process(request, differences, reviewer2, false);

        assertTrue("Two review item second approval with mods did not transition to the correct state",
            stateTester instanceof PendingSecondApprovedAddState);
        assertFalse("3.Two review item SingleApproval State marked as completed", stateTester.isCompleted());

        stateTester = stateTester.process(request, blankArray, reviewer1, false);

        assertTrue("Two review item final approval did not transition to the correct state",
            stateTester instanceof ApprovedAddState);
        assertTrue("Two review item ApprovedAdd State not marked as completed", stateTester.isCompleted());

    }
}
