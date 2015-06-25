/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.SerializationUtils;
import org.hibernate.Hibernate;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.LocalRequestFilter;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplRequestDetailDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplRequestDo;


/**
 * Test method to exercise request domain capabilities
 */
public class RequestDomainCapabilityTest extends DomainCapabilityTestCase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            RequestDomainCapabilityTest.class);
    private static final RequestRejectionReason REQUEST_REJECT_REASON = RequestRejectionReason.INCOMPLETE_INFORMATION;
    private static final String SITE1 = "SITE1";
    private static final String S9991 = "9991";
    private static final String S9998 = "9998";
    private RequestDomainCapability requestDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.debug("---------------" + getName() + "----------------");
        this.requestDomainCapability = getNationalCapability(RequestDomainCapability.class);
    }

    /**
     * method to build a requestDo to be used in tests
     * 
     * @return EplRequestDo
     */
    private EplRequestDo buildRequestDo() {

        EplNdcDo ndc = new EplNdcDo();
        ndc.setEplId((new Long("9996")));
        EplRequestDo req = new EplRequestDo();

        req.setId(new Long("99"));
        req.setRequestType("ADDITION");
        req.setSiteName(SITE1);
        req.setItemType("NDC");
        req.setRequestStatus(RequestState.PENDING_FIRST_REVIEW.toString());
        req.setNewItemRequestStatus(RequestItemStatus.PENDING.toString());
        req.setUnderReviewFlag("Y");
        req.setMarkedForPsr("Y");
        req.setPsrName("PSRNAME");

        req.setEplNdc(ndc);
        req.setRequestRejectReason(REQUEST_REJECT_REASON.toString());

        HashSet details = new HashSet();

        EplRequestDetailDo detail = new EplRequestDetailDo();
        detail.setStatus("APPROVED");
        detail.setSiteName(SITE1);
        detail.setEplRequest(req);
        detail.setId(new Long("99"));

        DataField<Double> oldPricePerOrderUnit = DataField.newInstance(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
        oldPricePerOrderUnit.selectValue(new Double("54.8"));
        oldPricePerOrderUnit.setDataFieldId(new Long("44"));
        DataField<Double> newPricePerOrderUnit = DataField.newInstance(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
        newPricePerOrderUnit.selectValue(new Double("4.5"));
        newPricePerOrderUnit.setEditable(oldPricePerOrderUnit.isEditable());

        Difference diff2 = new Difference(FieldKey.NDC_PRICE_PER_ORDER_UNIT, oldPricePerOrderUnit, newPricePerOrderUnit);
        byte[] bytes = SerializationUtils.serialize(diff2);
    
        Blob difference = Hibernate.createBlob(bytes);
        detail.setDifference(difference);

        details.add(detail);

        req.setEplRequestDetails(details);

        return req;

    }

    /**
     * Verify a request can be added
     * 
     * @throws Exception exception
     */
    public void testAddRequestDo() throws Exception {

        EplRequestDo testDo = buildRequestDo();

        // the create method actually adds it to the database
        EplRequestDo returnedDo = requestDomainCapability.createRequestDo(testDo, true, getTestUser());

        // retrieve the data to see if it was stored in the database
        RequestVo returnedVo = requestDomainCapability.retrieve(returnedDo.getId().toString());

        // compare the data retrieved with the data sent to ensure it was added
        assertNotNull("Returned Item Result not returned!", returnedVo);

        // retrieve the not completed requests to see if it was stored in the database
        returnedVo =
            requestDomainCapability
                .retrieveNonCompletedRequest(returnedDo.getEplNdc().getEplId().toString(), EntityType.NDC);

        // compare the data retrieved with the data sent to ensure it was added
        assertNotNull("Returned Item Result not returned.", returnedVo);
        

    }

    /**
     * Verify a request can be added
     * 
     * @throws Exception exception
     */
    public void testLocalRequestsSearch() throws Exception {
        Random random = new Random(System.currentTimeMillis());

        EplRequestDo localOne = buildRequestDo();
        localOne.setId(random.nextLong());
        localOne.setSiteName(String.valueOf(random.nextLong()));

        EplRequestDo localTwo = buildRequestDo();
        localTwo.setId(random.nextLong());
        localTwo.setSiteName(String.valueOf(random.nextLong()));

        requestDomainCapability.createRequestDo(localOne, true, getTestUser());
        requestDomainCapability.createRequestDo(localTwo, true, getTestUser());

        SearchRequestCriteriaVo localOneCriteria = new SearchRequestCriteriaVo();
        localOneCriteria.setLocalRequest(LocalRequestFilter.ONLY_LOCAL);
        localOneCriteria.setSiteNumber(localOne.getSiteName());
        List<RequestSummaryVo> localOneResults = requestDomainCapability.localSearch(localOneCriteria);
        assertEquals("Should have only received one request!", 1, localOneResults.size());
        assertEquals("Should have gotten back the localOne request ID", localOne.getId(), new Long(localOneResults.get(0)
            .getRequestId()));

        SearchRequestCriteriaVo localTwoCriteria = new SearchRequestCriteriaVo();
        localTwoCriteria.setLocalRequest(LocalRequestFilter.ONLY_LOCAL);
        localTwoCriteria.setSiteNumber(localTwo.getSiteName());
        List<RequestSummaryVo> localTwoResults = requestDomainCapability.localSearch(localTwoCriteria);
        assertEquals("Should have only received one request", 1, localTwoResults.size());
        assertEquals("Should have gotten back the localTwo request ID", localTwo.getId(), new Long(localTwoResults.get(0)
            .getRequestId()));
    }

    /**
     * method to build a requestVo to be used in tests
     * 
     * @return RequestVo
     */
    private RequestVo buildRequestVo() {

        UserVo newItemRequestor = new UserVo();
        newItemRequestor.setUsername("user1");
        newItemRequestor.setLocation("site-1");

        Collection<ModDifferenceVo> colReqDetail = generateStubNDCModReqCollection();
        RequestVo request = new RequestVo(colReqDetail);
        request.setEntityType(EntityType.PRODUCT);
        request.setItemId(S9991);
        request.setSiteName("mnewsite1");
        request.setNote("note1");
        request.setRequestType(RequestType.ADDITION);
        request.setNewItemRequestStatus(RequestItemStatus.PENDING);
        request.setRequestState(RequestState.PENDING_FIRST_REVIEW);
        request.setNewItemRequestor(newItemRequestor);
        request.setUnderReview(false);
        request.setMarkedForPepsSecondReview(false);
        request.setPsrName("PSR");

        request.setRequestRejectionReason(REQUEST_REJECT_REASON);

        return request;

    }

    /**
     * method to build a requestVo to be used in tests
     * 
     * @return RequestVo
     */
    private RequestVo buildDrugClassRequestVo() {

        UserVo newItemRequestor = new UserVo();
        newItemRequestor.setUsername("user2");
        newItemRequestor.setLocation("site-2");

        RequestVo request = new RequestVo();
        request.setEntityType(EntityType.DRUG_CLASS);
        request.setItemId(S9991);
        request.setNote("note2");
        request.setSiteName("mnewsit2e");
        request.setRequestType(RequestType.ADDITION);
        request.setNewItemRequestStatus(RequestItemStatus.PENDING);
        request.setRequestState(RequestState.PENDING_FIRST_REVIEW);
        request.setNewItemRequestor(newItemRequestor);
        request.setMarkedForPepsSecondReview(false);
        request.setRequestRejectionReason(REQUEST_REJECT_REASON);
        request.setUnderReview(false);

        return request;

    }

    /**
     * method to build a requestVo to be used in tests
     * 
     * @return RequestVo
     */
    private RequestVo buildRequestReportVo() {

        UserVo newItemRequestor = new UserVo();
        newItemRequestor.setUsername("user");
        newItemRequestor.setLocation("site-");

        RequestVo request = new RequestVo();
        request.setEntityType(EntityType.PRODUCT);
        request.setItemId(S9991);
        request.setNote("note");
        request.setSiteName("mnewsite");
        request.setRequestType(RequestType.PROBLEM_REPORT);
        request.setNewItemRequestStatus(RequestItemStatus.PENDING);
        request.setRequestState(RequestState.PENDING_FIRST_REVIEW);
        request.setNewItemRequestor(newItemRequestor);
        request.setUnderReview(false);
        request.setMarkedForPepsSecondReview(false);
        request.setRequestRejectionReason(REQUEST_REJECT_REASON);

        return request;

    }

    /**
     * Create request details
     * 
     * @return Collection<ModDifferenceVo>
     */
    private Collection<ModDifferenceVo> generateStubNDCModReqCollection() {

        Collection<ModDifferenceVo> colModDiff = new ArrayList<ModDifferenceVo>();

        // modify editable datafield

        DataField<Double> oldPricePerOrderUnit = DataField.newInstance(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
        oldPricePerOrderUnit.selectValue(new Double("54.9"));
        oldPricePerOrderUnit.setDataFieldId(new Long("44"));
        DataField<Double> newPricePerOrderUnit = DataField.newInstance(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
        newPricePerOrderUnit.selectValue(new Double("4.50"));
        newPricePerOrderUnit.setEditable(oldPricePerOrderUnit.isEditable());

        Difference dif2 = new Difference(FieldKey.NDC_PRICE_PER_ORDER_UNIT, oldPricePerOrderUnit, newPricePerOrderUnit);
        ModDifferenceVo modDiff2 = new ModDifferenceVo();
        modDiff2.setDifference(dif2);
        modDiff2.setAcceptChange(false);
        modDiff2.setSiteName("newSite3");
        modDiff2.setModRequestItemStatus(RequestItemStatus.PENDING);
        modDiff2.setRequestRejectReason(REQUEST_REJECT_REASON.toString());

        // add to the collection
        colModDiff.add(modDiff2);

        // modify non-editable va data fields
        DataField<Double> oldUnitPrice = DataField.newInstance(FieldKey.UNIT_PRICE);
        oldUnitPrice.selectValue(new Double("12"));
        oldUnitPrice.setDataFieldId(new Long("42"));
        DataField<Double> newUnitPrice = DataField.newInstance(FieldKey.UNIT_PRICE);
        newUnitPrice.selectValue(new Double("6"));
        newUnitPrice.setEditable(oldUnitPrice.isEditable());

        Difference dif3 = new Difference(FieldKey.UNIT_PRICE, oldUnitPrice, newUnitPrice);

        ModDifferenceVo modDiff3 = new ModDifferenceVo();
        modDiff3.setDifference(dif3);
        modDiff3.setRequestToModifyValue(false);
        modDiff3.setRequestToMakeEditable(true);
        modDiff3.setSiteName("newSite");
        modDiff3.setModRequestItemStatus(RequestItemStatus.PENDING);
        modDiff3.setRequestRejectReason(REQUEST_REJECT_REASON.toString());

        // add to the collection
        colModDiff.add(modDiff3);

        return colModDiff;
    }

    /**
     * testAddRequestVo
     * 
     * @throws Exception Exception
     */
    public void testAddRequestVo() throws Exception {

        RequestVo requestVo = buildRequestVo();

        RequestVo newVO = requestDomainCapability.create(requestVo, getTestUser());

        // the vo build in the db is identical to on in db
        assertEquals("Item id Should be equal", requestVo.getItemId(), newVO.getItemId());
        assertEquals("item type should be equal", requestVo.getEntityType(), newVO.getEntityType());
        assertEquals("number of details should be equal", requestVo.getRequestDetails().size(), newVO.getRequestDetails()
            .size());

        Difference initial = null;
        Difference saved = null;

        for (ModDifferenceVo test : requestVo.getRequestDetails()) {
            if (test.getDifference().getFieldKey().equals(FieldKey.UNIT_PRICE)) {
                initial = test.getDifference();
            }
        }

        for (ModDifferenceVo test : newVO.getRequestDetails()) {
            if (test.getDifference().getFieldKey().equals(FieldKey.UNIT_PRICE)) {
                saved = test.getDifference();
            }
        }

       
        assertEquals("Difference is same! ", initial, saved);

    }

    /**
     * testAddRequestForDrugClass
     * 
     * @throws Exception Exception
     */
    public void testAddRequestForDrugClass() throws Exception {

        RequestVo requestVo = buildDrugClassRequestVo();

        RequestVo newVO = requestDomainCapability.create(requestVo, getTestUser());

        assertEquals("Should be equal!", requestVo.getItemId(), newVO.getItemId());

        
        // retrieve the not completed requests to see if it was stored in the database
        newVO = requestDomainCapability.retrieveNonCompletedRequest(newVO.getItemId(), EntityType.DRUG_CLASS);

        // compare the data retrieved with the data sent to ensure it was added
        assertNotNull("Returned Item Result was not returned", newVO);
        
    }

    /**
     * testRetrieveRequestForDrugClass
     * 
     * @throws Exception Exception
     */
    public void testRetrieveRequestForDrugClass() throws Exception {

        String itemId = S9991;

        Collection<RequestVo> requests = requestDomainCapability.retrieve(itemId, EntityType.DRUG_CLASS);

        for (RequestVo vo : requests) {
            LOG.debug("TEST RETRIEVE: " + vo.toString());
        }

        assertNotNull("Returned Item Result not returned. ", requests);
    }

    /**
     * testAddRequestVoForReport
     *
     * @throws Exception exception
     */
    public void testAddRequestVoForReport() throws Exception {

        RequestVo requestVo = buildRequestReportVo();

        RequestVo newVO = requestDomainCapability.create(requestVo, getTestUser());

        LOG.debug(newVO);

        assertEquals("Should be equal", requestVo.getItemId(), newVO.getItemId());

    }

    /**
     * testRetrieveRequestsByItemId
     *
     * @throws Exception exception
     */
    public void testRetrieveRequestsByItemId() throws Exception {

        String itemId = S9998;
        Collection<RequestVo> requests = requestDomainCapability.retrieve(itemId, EntityType.NDC);

        for (RequestVo vo : requests) {
            LOG.debug("TEST RETRIEVE:" + vo.toString());
        }

        assertNotNull("The returned Item Result not returned", requests);
    }

    /**
     * testRetrieveReportByItemId
     *
     * @throws Exception exception
     */
    public void testRetrieveReportByItemId() throws Exception {

        String itemId = S9991;
        Collection<RequestVo> requests = requestDomainCapability.retrieveReportsByItemId(itemId, EntityType.PRODUCT);

        for (RequestVo vo : requests) {
            LOG.debug("TEST RETRIEVE : " + vo.toString());
        }

        assertNotNull("Returned Item Result not returned", requests);
    }

    /**
     * Method to test the request search, verifying requests are returned.
     * 
     * @throws Exception exception
     */
    public void testNationalSearch() throws Exception {
        SearchRequestCriteriaVo requestCriteria = new SearchRequestCriteriaVo();
        requestCriteria.setLocalSearch(false);

        requestCriteria.setPendingAddition(true);
        requestCriteria.setPendingModification(true);
        requestCriteria.setPendingSecondApprovalAddition(true);
        requestCriteria.setPendingSecondApprovalModification(true);

        Collection<RequestSummaryVo> results = requestDomainCapability.nationalSearch(requestCriteria, null);

        LOG.debug(results.size());

        assertFalse("No results were returned!", results.isEmpty());
    }

    /**
     * Method to test the request search using dates
     * 
     * @throws Exception exception
     */
    public void testLocalSearch() throws Exception {
        SearchRequestCriteriaVo requestCriteria = new SearchRequestCriteriaVo();
        requestCriteria.setLocalSearch(true);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date startSearchDate = cal.getTime();
        Date searchEndDate = Calendar.getInstance().getTime();

        requestCriteria.setSearchRequestStartDate(startSearchDate);
        requestCriteria.setSearchRequestEndDate(searchEndDate);

        Collection<RequestSummaryVo> results = requestDomainCapability.localSearch(requestCriteria);

        // Verify requests were returned
        assertFalse("No results returned!", results.isEmpty());
    }

    /**
     * method to test updates to request details
     * 
     * @throws Exception exception
     */
    public void testupdateRequestDetails() throws Exception {
        String itemId = S9998;
        Collection<RequestVo> requests = requestDomainCapability.retrieve(itemId, EntityType.PRODUCT);

        for (RequestVo vo : requests) {

            vo.setRequestState(RequestState.COMPLETED);

            LOG.debug("request vo retrieved is " + vo);

            vo.setNote("Additional Information in the note.");

            Collection<ModDifferenceVo> modList = vo.getRequestDetails();

            for (ModDifferenceVo mod : modList) {

                // mod.setDifference(null);
                mod.setModificationReason("new Reason");
            }

            requestDomainCapability.updateRequest(vo, getTestUser());

        }

        // Retrieve updated requests and verify change to note
        Collection<RequestVo> verify = requestDomainCapability.retrieve(itemId, EntityType.PRODUCT);

        for (RequestVo vo : verify) {
            String note = vo.getNote();
            assertTrue("Request note not updated!", "Additional Information".equals(note));
        }

    }

}
