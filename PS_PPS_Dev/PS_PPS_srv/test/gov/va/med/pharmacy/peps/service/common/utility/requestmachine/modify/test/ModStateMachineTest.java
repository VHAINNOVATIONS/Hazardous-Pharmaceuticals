/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine.modify.test;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.utility.impl.test.EnvironmentUtilityStub;
import gov.va.med.pharmacy.peps.common.utility.test.generator.ProductGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachineFactory;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.modify.CompletedModState;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.modify.PendingSecondApprovalModState;

import junit.framework.TestCase;


/**
 * This class is used to test the modification states.
 * 
 */
public class ModStateMachineTest extends TestCase {
    private UserVo reviewer1;
    private UserVo reviewer2;
    private UserVo localRequester;
    private Collection<ModDifferenceVo> colModDiff;
    private Collection<ModDifferenceVo> blankArray;

    private RequestStateMachineFactory factory;
    private String id3 = "314159265";
    private String id4 = "314159264";
    private String test2 = "test2";
    private String local1 = "Local-1";

    /**
     * test setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {
        
        // create the users
        reviewer1 = new UserGenerator().getNationalManagerOne();
        reviewer2 = new UserGenerator().getNationalManagerTwo();
        localRequester = new UserGenerator().getLocalManagerOne();

        colModDiff = new ArrayList<ModDifferenceVo>();
        blankArray = new ArrayList<ModDifferenceVo>();

        EnvironmentUtilityStub environmentUtility = new EnvironmentUtilityStub();
        environmentUtility.setNational();
        factory = new RequestStateMachineFactory(environmentUtility);
    }

    /**
     * Test approving a single modification
     * 
     * @throws Exception Exception
     */
    public void testSingleApprovalMod() throws Exception {

        // Create a test Product, as if it was submitted by a local site up to national.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);
        DataField<Boolean> doNotMailOriginal = prod.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertNotNull("1. Retrieving Do Not Mail field failed!", doNotMailOriginal);
        doNotMailOriginal.setEditable(false);

        // Simulate the PLM making a requested field change.

        colModDiff.clear();

        DataField<Boolean> doNotMailNew = DataField.newInstance(FieldKey.DO_NOT_MAIL);
        doNotMailNew.setDataFieldId(doNotMailOriginal.getDataFieldId());
        doNotMailNew.setDefaultValue(false);
        doNotMailNew.selectValue(doNotMailOriginal.getValue().booleanValue() ? Boolean.FALSE : Boolean.TRUE); // Toggle
        doNotMailNew.setEditable(false); // Local can't change this value!

        Difference diff = new Difference(FieldKey.DO_NOT_MAIL, doNotMailOriginal, doNotMailNew);
        ModDifferenceVo modDiff = new ModDifferenceVo();
        modDiff.setId(id3);
        modDiff.setModificationReason(test2);
        modDiff.setAcceptChange(true);
        modDiff.setDifference(diff);
        
        modDiff.setRequestToMakeEditable(true); // Local's request to make this field editable.
        modDiff.setRequestToModifyValue(true); // Local's request to change the value of the field.
        modDiff.setSiteName(local1);
       
        modDiff.setReviewer(null);
        modDiff.setModRequestItemStatus(RequestItemStatus.PENDING);
        modDiff.setRequestor(localRequester);
        colModDiff.add(modDiff);

        // Create a test Request.
        RequestVo request = new RequestVo(prod.getId(), prod.getEntityType(), RequestType.MODIFICATION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            localRequester, // User requesting field change(s).
            RequestItemStatus.PENDING, // Don't care for mod requests.
            false, // Not under review.
            false, // Not marked for PSR.
            colModDiff);

        // Get the RequestStateMachine
        RequestStateMachine machine = factory.getRequestStateMachine(prod, null, request);
        request = request.copy();

        for (ModDifferenceVo mod : request.getRequestDetails()) {
            mod.setModRequestItemStatus(RequestItemStatus.APPROVED);
        }

        machine = machine.process(request, blankArray, reviewer1, false);

        assertTrue("2.Single Review Mod request did not transition to the correct state", machine instanceof CompletedModState);
        assertTrue("2.Single Review Mod request not marked as completed", machine.isCompleted());

    }

    /**
     * Test Rejecting a single modification request
     * 
     * @throws Exception Exception
     */
    public void testSingleRejectMod() throws Exception {

        // Create a test Product, as if it was submitted by a local site up to national.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);
        DataField<Boolean> doNotMailOriginal = prod.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertNotNull("2.Retrieving Do Not Mail field failed!", doNotMailOriginal);
        doNotMailOriginal.setEditable(false);

        // testSingleRejectMod Simulate the PLM making a requested field change.

        colModDiff.clear();

        DataField<Boolean> doNotMailNew = DataField.newInstance(FieldKey.DO_NOT_MAIL);
        doNotMailNew.setDataFieldId(doNotMailOriginal.getDataFieldId());
        doNotMailNew.setDefaultValue(false);
        
        // testSingleRejectMod Toggle Value
        doNotMailNew.selectValue(doNotMailOriginal.getValue().booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        doNotMailNew.setEditable(false); // Local can't change this value!

        Difference diff = new Difference(FieldKey.DO_NOT_MAIL, doNotMailOriginal, doNotMailNew);
        ModDifferenceVo modDiff = new ModDifferenceVo();
        modDiff.setId(id3);
        modDiff.setAcceptChange(true);
        modDiff.setRequestToMakeEditable(true); // Local's request to make this field editable.
        modDiff.setRequestToModifyValue(true); // Local's request to change the value of the field.
        modDiff.setSiteName(local1);
        modDiff.setDifference(diff);
        modDiff.setModificationReason(test2);
        modDiff.setModRequestItemStatus(RequestItemStatus.PENDING);
        modDiff.setRequestor(localRequester);
        modDiff.setReviewer(null);

        colModDiff.add(modDiff);

        // testSingleRejectMod Create a test Request.
        RequestVo request = new RequestVo(prod.getId(), prod.getEntityType(), RequestType.MODIFICATION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            localRequester, // User requesting field change(s).
            RequestItemStatus.PENDING, // Don't care for mod requests.
            false, // Not under review.
            false, // Not marked for a PPS Second Review.
            colModDiff);

        RequestStateMachine machine = factory.getRequestStateMachine(prod, null, request);

        request = request.copy();

        for (ModDifferenceVo mod : request.getRequestDetails()) {
            mod.setModRequestItemStatus(RequestItemStatus.REJECTED);

            mod.setComments("Test reject reason1");
        }

        machine = machine.process(request, blankArray, reviewer1, false);

        assertTrue("3.Single Review Mod request did not transition to the correct state", machine instanceof CompletedModState);
        assertTrue("3.Single Review Mod request not marked as completed", machine.isCompleted());

    }

    /**
     * Test approving a two review field
     * 
     * @throws Exception Exception
     */
    public void testTwoReviewItemFlows() throws Exception {

        Collection<ModDifferenceVo> colModDiff2 = new ArrayList<ModDifferenceVo>();
        ModDifferenceVo modDiff = null;
        Difference diff = null;

        // Create a test Product, as if it was already added and approved.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);

        // Simulate a PNM making a change to a two-review field.
        // Retrieve the CMOP Dispense (National) field.
        boolean cmopDispenseOriginal = prod.getCmopDispense();
        assertNotNull("Retrieving CMOP Dispense (National) field failed!", cmopDispenseOriginal);

        // Begin the process to update a product.
        colModDiff2.clear();

        // Update this field's setting.
        diff = new Difference(FieldKey.CMOP_DISPENSE, prod.getCmopDispense(), !prod.getCmopDispense());
        modDiff = new ModDifferenceVo();
        modDiff.setModRequestItemStatus(RequestItemStatus.APPROVED);
        modDiff.setDifference(diff);
        modDiff.setModificationReason(test2);
        modDiff.setAcceptChange(true);
        modDiff.setId(id3);

        colModDiff2.add(modDiff);

        // Create a test Request.
        RequestVo request = new RequestVo(prod.getId(), prod.getEntityType(), RequestType.MODIFICATION,
            RequestState.PENDING_SECOND_REVIEW, reviewer1, // No last reviewer.
            localRequester, // User requesting field change(s).
            RequestItemStatus.APPROVED, // Don't care for mod requests.
            false, // Not under review.
            false, // Not marked for PSR.
            colModDiff2);

        RequestStateMachine machine = factory.getRequestStateMachine(prod, null, request);

        request = request.copy();

        for (ModDifferenceVo mod : request.getRequestDetails()) {
            mod.setModRequestItemStatus(RequestItemStatus.APPROVED);
        }

        machine = machine.process(request, blankArray, reviewer2, false);

        assertTrue("4.Single Review Mod request did not transition to the correct state", machine instanceof CompletedModState);
        assertTrue("4.Single Review Mod request marked as completed", machine.isCompleted());
    }

    /**
     * Test making an extra mod in addition to a two review field.
     * 
     * @throws Exception Exception
     */
    public void testTwoReviewModExtraMod() throws Exception {

        // Create a test Product, as if it was submitted by a local site up to national.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);
        DataField<Boolean> doNotMailOriginal = prod.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertNotNull("4.Retrieving Do Not Mail field failed!", doNotMailOriginal);
        doNotMailOriginal.setEditable(false);

        // Simulate the PLM making a requested field change.

        colModDiff.clear();

        DataField<Boolean> doNotMailNew = DataField.newInstance(FieldKey.DO_NOT_MAIL);
        doNotMailNew.setDataFieldId(doNotMailOriginal.getDataFieldId());
        doNotMailNew.setDefaultValue(false);
        
        // Toggle value
        doNotMailNew.selectValue(doNotMailOriginal.getValue().booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        doNotMailNew.setEditable(false); // Local can't change this value!

        // set the differences
        Difference diff = new Difference(FieldKey.DO_NOT_MAIL, doNotMailOriginal, doNotMailNew);
        ModDifferenceVo modDiff = new ModDifferenceVo();
        modDiff.setId(id3);
        modDiff.setDifference(diff);
        modDiff.setModificationReason(test2);
        modDiff.setRequestToModifyValue(true); // Local's request to change the value of the field.
        modDiff.setAcceptChange(true);
        modDiff.setRequestToMakeEditable(true); // Local's request to make this field editable.
        modDiff.setSiteName(local1);
        modDiff.setModRequestItemStatus(RequestItemStatus.PENDING);
        modDiff.setRequestor(localRequester);
        modDiff.setReviewer(null);

        colModDiff.add(modDiff);

        // Create a test Request for testTwoReviewModExtraMod.
        RequestVo request = new RequestVo(prod.getId(), prod.getEntityType(), RequestType.MODIFICATION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            localRequester, // User requesting field change(s).
            RequestItemStatus.PENDING, // Don't care for mod requests.
            false, // Not under review.
            false, // Not marked for PSR.
            colModDiff);

        // create the state machine used to determine the next possible state.
        RequestStateMachine machine = factory.getRequestStateMachine(prod, null, request);

        request = request.copy();

        for (ModDifferenceVo mod : request.getRequestDetails()) {
            mod.setModRequestItemStatus(RequestItemStatus.REJECTED);

            mod.setComments("Test reject reason2");
        }

        // Simulate a PNM making a change to a two-review field.
        // Begin the process to update a product by clearin the mod differences.
        colModDiff.clear();

        // Update this Difference setting for the CMOP Dispense
        diff = new Difference(FieldKey.CMOP_DISPENSE, prod.getCmopDispense(), !prod.getCmopDispense());
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason(test2);
        modDiff.setAcceptChange(true);
        modDiff.setId(id4);

        colModDiff.add(modDiff);

        machine = machine.process(request, colModDiff, reviewer1, false);

        assertTrue("7.Single Review Mod request did not transition to the correct state",
            machine instanceof PendingSecondApprovalModState);
        assertFalse("7.Single Review Mod request not marked as completed", machine.isCompleted());

        machine = machine.process(request, blankArray, reviewer2, false);

        assertTrue("8.Single Review Mod request did not transition to the correct state", machine instanceof CompletedModState);
        assertTrue("8.Single Review Mod request marked as completed", machine.isCompleted());
    }

    /**
     * Test the review process where the national managers disagree
     * Local Test -> Bring back for PPSL
     * @throws Exception Exception
     */
    public void testTwoReviewModThreeApprovals() throws Exception {
      
        
        // Create a test Product, as if it was submitted by a local site up to national.
        ProductGenerator prodGen = new ProductGenerator();
        ProductVo prod = prodGen.getFirst();
        prod.setRequestItemStatus(RequestItemStatus.APPROVED);
        prod.setItemStatus(gov.va.med.pharmacy.peps.common.vo.ItemStatus.ACTIVE);
        prod.setId("42");
        prod.setLocalUse(false);
        DataField<Boolean> doNotMailOriginal = prod.getVaDataFields().getDataField(FieldKey.DO_NOT_MAIL);
        assertNotNull("Retrieving Do Not Mail field failed!", doNotMailOriginal);
        doNotMailOriginal.setEditable(false);

        // Simulate the PLM making a requested field change.
        colModDiff.clear();

        DataField<Boolean> doNotMailNew = DataField.newInstance(FieldKey.DO_NOT_MAIL);
        doNotMailNew.setDataFieldId(doNotMailOriginal.getDataFieldId());
        doNotMailNew.setDefaultValue(false);
        
        // Toggle Value
        doNotMailNew.selectValue(doNotMailOriginal.getValue().booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        doNotMailNew.setEditable(false); // Local can't change this value!

        Difference diff = new Difference(FieldKey.DO_NOT_MAIL, doNotMailOriginal, doNotMailNew);
        ModDifferenceVo modDiff = new ModDifferenceVo();
        modDiff.setId(id3);
        modDiff.setDifference(diff);
        modDiff.setModificationReason(test2);
        modDiff.setAcceptChange(true);
        modDiff.setRequestToMakeEditable(true); // Local's request to make this field editable.
        modDiff.setRequestToModifyValue(true); // Local's request to change the value of the field.
        modDiff.setSiteName(local1);
        modDiff.setModRequestItemStatus(RequestItemStatus.PENDING);
        modDiff.setRequestor(localRequester);
        modDiff.setReviewer(null);

        colModDiff.add(modDiff);

        // Create a test Request.
        RequestVo request = new RequestVo(prod.getId(), prod.getEntityType(), RequestType.MODIFICATION,
            RequestState.PENDING_FIRST_REVIEW, null, // No last reviewer.
            localRequester, // User requesting field change(s).
            RequestItemStatus.PENDING, // Don't care for mod requests.
            false, // Not under review.
            false, // Not marked for PSR.
            colModDiff);

        RequestStateMachine machine = factory.getRequestStateMachine(prod, null, request);

        request = request.copy();

        for (ModDifferenceVo mod : request.getRequestDetails()) {
            mod.setModRequestItemStatus(RequestItemStatus.REJECTED);

            mod.setComments("Test reject reason");
        }

        // Simulate a PNM making a change to a two-review field.
        // Begin the process to update a product.
        colModDiff.clear();

        // Update this field's setting.
        diff = new Difference(FieldKey.CMOP_DISPENSE, prod.getCmopDispense(), !prod.getCmopDispense());
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason(test2);
        modDiff.setAcceptChange(true);
        modDiff.setId(id4);

        colModDiff.add(modDiff);

        machine = machine.process(request, colModDiff, reviewer1, false);

        assertTrue("10.Single Review Mod request did not transition to the correct state",
            machine instanceof PendingSecondApprovalModState);
        assertFalse("10.Single Review Mod request not marked as completed", machine.isCompleted());

        // Simulate 2nd reviewer making another change to the mod request, another 2-review field (GCNSEQNO).
        String gcnSeqNoOriginal = prod.getGcnSequenceNumber();

        // Begin the process to update a product.
        colModDiff.clear();

        // Update this field's setting.
        String gcn01 = "011111";
        String gcnSeqNoNew = (gcn01.equals(gcnSeqNoOriginal) ? "022222" : gcn01);
        
        // Ensure change value to something else.
        diff = new Difference(FieldKey.GCN_SEQUENCE_NUMBER, gcnSeqNoOriginal, gcnSeqNoNew);
        modDiff = new ModDifferenceVo();
        modDiff.setDifference(diff);
        modDiff.setModificationReason("test1");
        modDiff.setAcceptChange(true);
        modDiff.setId("21215");

        colModDiff.add(modDiff);

        machine = machine.process(request, colModDiff, reviewer2, false);

        assertTrue("11.Single Review Mod request did not transition to the correct state",
            machine instanceof CompletedModState);
        assertTrue("11.Single Review Mod request not marked as completed", machine.isCompleted());
 
    }
}
