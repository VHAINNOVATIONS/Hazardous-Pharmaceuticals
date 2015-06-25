/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.SeqNumDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.impl.SeqNumDomainCapabilityImpl;
import gov.va.med.pharmacy.peps.domain.common.dao.EplCmopIdGeneratorDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplCmopIdGeneratorDo;


/**
 * SeqNumDomainCapabilityTest's brief summary
 * 
 * Details of SeqNumDomainCapabilityTest's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@RunWith(JUnit4.class)
public class SeqNumDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(SeqNumDomainCapabilityTest.class);
    
    private static final String SHORT_LINE = "-------------";
    private static final String AA000 = "AA000";
    private static final String AA010 = "AA010";
    private static final String AA100 = "AA100";
    private static final String A0010 = "A0010";
    private static final String A0100 = "A0100";
    private static final String A1000 = "A1000";
    private static final String RET_ID = "Returned Id";
    private static final String LONG_GT_ZERO = "should be a long greater than zero";
    
    
//    private SeqNumDomainCapability localSeqNumDomainCapability;

    /** exception */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SeqNumDomainCapability nationalSeqNumDomainCapability;
    private SeqNumDomainCapabilityImpl seqNumDomainCapabilityImpl;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @throws Exception if error
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    @Before
    public void setUp() throws Exception {
        
        LOG.debug(SHORT_LINE + " setUp() " + SHORT_LINE);
        
//        this.localSeqNumDomainCapability = getLocalOneCapability(SeqNumDomainCapability.class);
        this.nationalSeqNumDomainCapability = getNationalCapability(SeqNumDomainCapability.class);
        this.seqNumDomainCapabilityImpl = new SeqNumDomainCapabilityImpl();

    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveProduceIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.PRODUCT, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveDosageFormIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.DOSAGE_FORM, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveCmopIdHistory() throws Exception {
//        String id = localSeqNumDomainCapability.generateCmopId("B", getTestUser());
//        assertNotNull(RET_ID, id);
//        System.out.println(id);
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveDrugUnitIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.DRUG_UNIT, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveFedScheduleIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateFedScheduleId(getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveIngredientIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.INGREDIENT, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveOrderUnitIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.ORDER_UNIT, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveLocalMedRouteIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.LOCAL_MEDICATION_ROUTE, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveStandardMedRouteIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.STANDARD_MEDICATION_ROUTE, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveVaDispenseUnitIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.DISPENSE_UNIT, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveVaDrugClassIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.DRUG_CLASS, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception exception
     */
    @Test
    public void testRetrieveGenNamesIdNational() throws Exception {
        String id = nationalSeqNumDomainCapability.generateId(EntityType.GENERIC_NAME, getTestUser());
        assertNotNull(RET_ID, id);
        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception exception
     */
    @Test
    public void testRetrieveMedicationInstructionIdNational() throws Exception {
        String id = nationalSeqNumDomainCapability.generateId(EntityType.MEDICATION_INSTRUCTION, getTestUser());
        assertNotNull(RET_ID, id);
        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveNdcIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.NDC, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveAdminScheduleIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.ADMINISTRATION_SCHEDULE, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception exception
     */
    @Test
    public void testRetrieveNdcIdNational() throws Exception {
        String id = nationalSeqNumDomainCapability.generateId(EntityType.NDC, getTestUser());
        assertNotNull(RET_ID, id);
        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveOwnerIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generatedOwnerId(getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception exception
     */
    @Test
    public void testRetrieveOwnerIdNational() throws Exception {
        String id = nationalSeqNumDomainCapability.generatedOwnerId(getTestUser());
        assertNotNull(RET_ID, id);
        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveOrderabelItemIdLocal() throws Exception {
//        String id = localSeqNumDomainCapability.generateId(EntityType.ORDERABLE_ITEM, getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception exception
     */
    @Test
    public void testRetrieveOrderableItemIdNational() throws Exception {
        String id = nationalSeqNumDomainCapability.generateId(EntityType.ORDERABLE_ITEM, getTestUser());
        assertNotNull(RET_ID, id);
        assertTrue(LONG_GT_ZERO, Long.parseLong(id) > 0);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveSearchTemplateIdLocal() throws Exception {
//        Long id = localSeqNumDomainCapability.generateSearchTemplateId(getTestUser());
//        assertNotNull(RET_ID, id);
//        assertTrue(LONG_GT_ZERO, id > 0);
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception exception
     */
    @Test
    public void testRetrieveSearchTemplateIdNational() throws Exception {
        Long id = nationalSeqNumDomainCapability.generateSearchTemplateId(getTestUser());
        assertNotNull(RET_ID, id);
        assertTrue(LONG_GT_ZERO, id > 0);
    }

    /**
     * testCreateCmopIdOneLetter
     *
     */
    @Test
    public void testCreateCmopIdOneLetter() { //NOPMD

        cmopIdGeneratorHelper("A", "A0000", "A0001");
    }

    /**
     * testCreateCmopIdOneLetterOnesToTens
     *
     */
    @Test
    public void testCreateCmopIdOneLetterOnesToTens() { //NOPMD

        cmopIdGeneratorHelper("A", "A0009", A0010);
    }

    /**
     * testCreateCmopIdOneLetterTensIncrement
     *
     */
    @Test
    public void testCreateCmopIdOneLetterTensIncrement() { //NOPMD

        cmopIdGeneratorHelper("A", A0010, "A0011");
    }

    /**
     * testCreateCmopIdOneLetterTensToHundreds
     *
     */
    @Test
    public void testCreateCmopIdOneLetterTensToHundreds() { //NOPMD

        cmopIdGeneratorHelper("A", "A0099", A0100);
    }

    /**
     * testCreateCmopIdOneLetterTensToHundredsIncrement
     *
     */
    @Test
    public void testCreateCmopIdOneLetterTensToHundredsIncrement() { //NOPMD

        cmopIdGeneratorHelper("A", A0100, "A0101");
    }

    /**
     * testCreateCmopIdOneLetterHundredsToThousands
     *
     */
    @Test
    public void testCreateCmopIdOneLetterHundredsToThousands() { //NOPMD

        cmopIdGeneratorHelper("A", "A0999", A1000);
    }

    /**
     * testCreateCmopIdOneLetterThousandsIncrement
     *
     */
    @Test
    public void testCreateCmopIdOneLetterThousandsIncrement() { //NOPMD

        cmopIdGeneratorHelper("A", A1000, "A1001");
    }

    /**
     * testCreateCmopIdOneLetterToTwo
     *
     */
    @Test
    public void testCreateCmopIdOneLetterToTwo() { //NOPMD

        cmopIdGeneratorHelper("A", "A9999", AA000);
    }

    /**
     * testCreateCmopIdTwoLetterOnesIncrement
     *
     */
    @Test
    public void testCreateCmopIdTwoLetterOnesIncrement() { //NOPMD

        cmopIdGeneratorHelper("A", AA000, "AA001");
    }

    /**
     * testCreateCmopIdTwoLetterOnesToTens
     *
     */
    @Test
    public void testCreateCmopIdTwoLetterOnesToTens() { //NOPMD

        cmopIdGeneratorHelper("A", "AA009", AA010);
    }

    /**
     * testCreateCmopIdTwoLetterTensIncrement
     *
     */
    @Test
    public void testCreateCmopIdTwoLetterTensIncrement() { //NOPMD

        cmopIdGeneratorHelper("A", AA010, "AA011");
    }

    /**
     * testCreateCmopIdTwoLetterTensToHundreds
     *
     */
    @Test
    public void testCreateCmopIdTwoLetterTensToHundreds() { //NOPMD

        cmopIdGeneratorHelper("A", "AA099", AA100);
    }

    /**
     * testCreateCmopIdTwoLetterHundredsIncrement
     *
     */
    @Test
    public void testCreateCmopIdTwoLetterHundredsIncrement() { //NOPMD

        cmopIdGeneratorHelper("A", AA100, "AA101");
    }

    /**
     * testCreateCmopIdTwoLetterToNextTwoLetter
     *
     */
    @Test
    public void testCreateCmopIdTwoLetterToNextTwoLetter() { //NOPMD

        cmopIdGeneratorHelper("A", "AA999", "AB000");
    }

    /**
     * testCreateCmopIdRollFromAZtoB
     *
     */
    @Test
    public void testCreateCmopIdRollFromAZtoB() { //NOPMD

        cmopIdGeneratorHelper("A", "AZ999", "B0000");
    }

    /**
     * testCreateCmopIdThrowsIllegalArgumentException
     *
     */
    @Test
    public void testCreateCmopIdThrowsIllegalArgumentException() { //NOPMD

        exception.expect(IllegalArgumentException.class);
        cmopIdGeneratorHelper("Z", "ZZ999", "");
    }

    
    /**
     * 
     * Description
     *
     * @param seed String seed to use (A-Z)
     * @param lastUsedCmopId the last simulated cmopId used
     * @param expectedCmopId the cmop id expected to be generated
     */
    private void cmopIdGeneratorHelper(String seed, String lastUsedCmopId, String expectedCmopId) {
        EplCmopIdGeneratorDao eplCmopIdGeneratorDao = EasyMock.createMock(EplCmopIdGeneratorDao.class);
        UserVo user = new UserVo();
        
        StringBuffer realSeed = new StringBuffer();
        realSeed.append(seed);
        
        seqNumDomainCapabilityImpl.setEplCmopIdGeneratorDao(eplCmopIdGeneratorDao);

        EplCmopIdGeneratorDo cmop = new EplCmopIdGeneratorDo();
        cmop.setLastUsedCmopId(lastUsedCmopId);
        EasyMock.expect(eplCmopIdGeneratorDao.retrieve(realSeed.toString())).andReturn(cmop);
        EasyMock.expect(eplCmopIdGeneratorDao.update(cmop, user)).andReturn(cmop);
        EasyMock.replay(eplCmopIdGeneratorDao);
        
//        LOG.debug("seed: .............. " + realSeed.toString());
//        LOG.debug("lastUsedCmopId: .... " + lastUsedCmopId);
//        LOG.debug("cmop.lastUsedCmopId: " + cmop.getLastUsedCmopId());
//        LOG.debug("---------------------");        

        seqNumDomainCapabilityImpl.generateCmopId(realSeed, user);
        
//        LOG.debug("seed: .............. " + realSeed.toString());
//        LOG.debug("lastUsedCmopId: .... " + lastUsedCmopId);
//        LOG.debug("cmop.lastUsedCmopId: " + cmop.getLastUsedCmopId());
//        LOG.debug("---------------------");
        
        EasyMock.verify(eplCmopIdGeneratorDao);
        
        assertEquals("The CMOP ID should be " + lastUsedCmopId, expectedCmopId, cmop.getLastUsedCmopId());
    }
}
