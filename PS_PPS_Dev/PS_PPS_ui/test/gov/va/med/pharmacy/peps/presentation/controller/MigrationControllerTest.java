/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mortbay.log.Log;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.presentation.common.controller.migration.MigrationController;
import gov.va.med.pharmacy.peps.service.common.migration.MigratedDomain;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessDomainType;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessStatus;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationExportState;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationProcessState;
import gov.va.med.pharmacy.peps.service.common.session.MigrationService;
import gov.va.med.pharmacy.peps.service.common.session.impl.MigrationCSVServiceImpl;
import gov.va.med.pharmacy.peps.service.common.session.impl.MigrationExportManagerImpl;
import gov.va.med.pharmacy.peps.service.common.session.impl.MigrationExportProcessImpl;
import gov.va.med.pharmacy.peps.service.common.session.impl.MigrationProcessImpl;
import gov.va.med.pharmacy.peps.service.common.session.impl.MigrationProcessManagerImpl;
import gov.va.med.pharmacy.peps.service.common.session.impl.MigrationServiceImpl;

import junit.framework.TestCase;


/**
 * MigrationControllerTest
 *
 */
public class MigrationControllerTest extends TestCase {

    /**
     * NDC_CSV_FILE1
     */
    public static final String NDC_CSV_FILE1 =
        "Ndc1^VaProductName1^ProductVuid^ProductGcnSeqNo3^NdcItemInactivationDate^Manufacturer^OrderUnit^OtcRxIndicator^"
            + "PackageSize1^PackageType1^TradeName1^Refrigeration^ProtectFromLight^ProposedInactivationDate^PreviousNdc^"
            + "PreviousUpcUpn^NdcDispenseUnitPerOrderUnit^Source^ProductNumber\n"
            + "^Test_Ndc^Test_ProductName^Test_ProductVuid^Test_ProductGcnSeqNo"
            + "^20111230^Test_Manufacturer^TBX^Test_OtcRxIndicator"
            + "^9999.9999^Test_PackageType^Test_TradeName^Y^N^20121230^PrevNdc^PrevUpcUpn^12345.2222^COTS^TestProductNumber^";

    /**
     * NDC_CSV_FILE2
     */
    public static final String NDC_CSV_FILE2 =
        "Ndc2^VaProductName2^ProductVuid^ProductGcnSeqNo3^NdcItemInactivationDate^Manufacturer^OrderUnit^OtcRxIndicator^"
            + "PackageSize2^PackageType2^TradeName2^Refrigeration^ProtectFromLight^ProposedInactivationDate^PreviousNdc^"
            + "PreviousUpcUpn2^NdcDispenseUnitPerOrderUnit^Source^ProductNumber\n^Test_Ndc^Test_ProductName^Test_ProductVuid^"
            + "Test_ProductGcnSeqNo2^20111230^Test_Manufacturer^TBX^Test_OtcRxIndicator^9999.9999^"
            + "Test_PackageType2^Test_TradeName^Y^N^20121230^PrevNdc^PrevUpcUpn^12345.2222^COTS^TestProductNumber^";

    /**
     * NDC_CSV_FILEBAD
     */
    public static final String NDC_CSV_FILEBAD =
        "Ndc ProductVuid^ProductGcnSeqNo^NdcItemInactivationDate^Manufacturer^OrderUnit^OtcRxIndicator^"
            + "PackageSize^PackageType^TradeName^Refrigeration^ProtectFromLight^ProposedInactivationDate^PreviousNdc^"
            + "PreviousUpcUpn3^NdcDispenseUnitPerOrderUnit^Source^ProductNumber\n^Test_Ndc^Test_ProductName^Test_ProductVuid^"
            + "Test_ProductGcnSeqNo3^20111230^Test_Manufacturer^TBX^Test_OtcRxIndicator^9999.9999^"
            + "Test_PackageType3^Test_TradeName^Y^N^20121230^PrevNdc^PrevUpcUpn^12345.2222^COTS^TestProductNumber^";

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MigrationControllerTest.class);

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String REDIRECT = "redirect: migrationControl.go";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String MIG_START = "/migrationStart.go";
    private static final String CTL_DETAILS = "/controlDetails.go";
    private static final String MIG_CTL = "migration-control";
    private static final String MIG_STATE = "migrationState";
    private static final String NOT_NULL = "Should Not be null!";
    private static final String FILE1 = "file1";
    private static final String FILE2 = "file2";
    private static final String SHOULD_DIFF = "Should be different!";
    private static final String SHOULD_BE_2 = "Size should be 2";
    private static final String DBSTATUS = "dbstatus";
    
    private MigrationController testController;
    private MockHttpServletRequest testRequest;
    private MockHttpServletResponse testResponse;
    private MigrationService testMigrationService;
    private MockMultipartHttpServletRequest testMpRequest;
    private MigrationProcessManagerImpl testMigrationProcessManager;
    private MigrationExportManagerImpl testMigrationExportManager;
    private MigrationCSVServiceImpl testMigrationCSVService;

    /**
     * setUp
     * @throws Exception Exception
     */
    @Before
    public void setUp() throws Exception {
        testMigrationService = new MockMigrationServiceImpl();
        testMigrationExportManager = new MigrationExportManagerImpl();

        //export 
        MigrationExportProcessImpl migrationExportProcessImpl = new MigrationExportProcessImpl();
        migrationExportProcessImpl.setExportState(new MigrationExportState());

        testMigrationCSVService = new MigrationCSVServiceImpl();
        testMigrationCSVService.setExportState(new MigrationExportState());
        migrationExportProcessImpl.setMigrationCSVService(new MigrationCSVServiceImpl());

        testMigrationExportManager.setMigrationExportProcess(migrationExportProcessImpl);
        testMigrationExportManager.setTaskExportExecutor(new SimpleAsyncTaskExecutor());

        testMigrationService.setMigrationExportManager(testMigrationExportManager);
        testMigrationService.setMigrationProcessState(new MigrationProcessState());
        testMigrationProcessManager = new MigrationProcessManagerImpl();

        MigrationProcessImpl testMigrationProcessImpl = new MigrationProcessImpl();
        testMigrationProcessImpl.setState(new MigrationProcessState());

        testMigrationProcessManager.setMigrationProcess(testMigrationProcessImpl);
        testMigrationProcessManager.setTaskExecutor(new SimpleAsyncTaskExecutor());

        testMigrationService.setMigrationProcessManager(testMigrationProcessManager);

        testController = new MigrationController();
        testController.setMigrationService(testMigrationService);
        testController.setMigrationCSVService(new MigrationCSVServiceImpl());

        testRequest = new MockHttpServletRequest();
        testRequest.setSession(new MockHttpSession());
        testResponse = new MockHttpServletResponse();

        testMpRequest = new MockMultipartHttpServletRequest();

    }

    /**
     * testMigrationHome
     * @throws Exception Exception
     */
    @Test
    public void testMigrationHome() throws Exception {
        testRequest.setMethod(GET);
        testRequest.setRequestURI("/migrationHome.go");

        ModelAndView mv = testController.migrationHome(testRequest, testResponse);
       
        //Map<String, Object> model = mv.getModel();

        assertEquals("TestData should match!", REDIRECT, mv.getViewName());
    }

    /**
     * testMigrationDownLoad
     * @throws Exception Exception
     */
    @Test
    public void testMigrationDownLoad() throws Exception {
        testRequest.setMethod(GET);
        testRequest.setRequestURI("/download.go");
        String selected = "PROD";

        try {
            ModelAndView mv = testController.download(testRequest, testResponse, selected);
            Map<String, Object> model = mv.getModel();
            model.notify();
        } catch (Exception e) {
            fail("Test threw an exception.");
        }

    }

    /**
     * testValidateFile
     */
    @Test
    public void testValidateFile() {
        testRequest.setMethod(POST);
        testRequest.setRequestURI("/validateFiles.go");
        testMpRequest.addFile(new MockMultipartFile("ndcFile1", "NdcCsvFile_Test1.csv", TEXT_PLAIN, NDC_CSV_FILE1
            .getBytes()));
        ModelAndView mv = testController.validateFiles(testMpRequest, testResponse);
        assertNotNull("Model cannot be null!", mv);
    }

    /**
     * This test validates the header of 2 files and  Mocks the 
     * startMigration process
     * @throws Exception Exception
     */
    @Test
    public void testStartMigration() throws Exception {
        testRequest.setMethod(POST);
        testRequest.setRequestURI(MIG_START);

        ModelAndView mv = testController.startMigration(testMpRequest, testResponse, false);

        assertEquals("Name should match", mv.getViewName(), MIG_CTL);

        Map<String, Object> model = mv.getModel();
        MigrationProcessState state = (MigrationProcessState) model.get(MIG_STATE);
        assertNotNull("State must not be null!", state);

        assertEquals("Status should be RUNNING", state.getStatus(), ProcessStatus.RUNNING);
    }

    /**
     * This test stops the currently running migration.
     * stopped (Restart).
     * @throws Exception Exception
     */
    @Test
    public void testStopMigration() throws Exception {
        testRequest.setMethod(POST);
        testRequest.setRequestURI("/migrationStop.go");

        ModelAndView mv = testController.startMigration(testMpRequest, testResponse, false);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, mv.getViewName(), MIG_CTL);

        ModelAndView stopMv = testController.stopMigration(testMpRequest, testResponse);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, stopMv.getViewName(), "redirect: migrationSummary.go");

        Map<String, Object> model = stopMv.getModel();
        MigrationProcessState state = (MigrationProcessState) model.get(MIG_STATE);
        assertNotNull(NOT_NULL, state);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, state.getStatus(), ProcessStatus.STOPPED);
    }

    /**
     * This test pauses the currently running migration
     * @throws Exception Exception
     */
    @Test
    public void testPauseMigration() throws Exception {
        testRequest.setMethod(POST);
        testRequest.setRequestURI("/migrationPause.go");

        ModelAndView mv = testController.startMigration(testMpRequest, testResponse, false);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, mv.getViewName(), MIG_CTL);

        ModelAndView pauseMv = testController.pauseMigration(testMpRequest, testResponse);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, pauseMv.getViewName(), MIG_CTL);

        Map<String, Object> model = pauseMv.getModel();
        MigrationProcessState state = (MigrationProcessState) model.get(MIG_STATE);
        assertNotNull(NOT_NULL, state);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, state.getStatus(), ProcessStatus.PAUSED);
    }

    /**
     * This test resumes the previously paused migration 
     * @throws Exception Exception
     */
    @Test
    public void testResumeMigration() throws Exception {
        testRequest.setMethod(POST);
        testRequest.setRequestURI("/migrationResume.go");

        //start migration

        ModelAndView mv = testController.startMigration(testMpRequest, testResponse, false);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, mv.getViewName(), MIG_CTL);

        //pause migration

        ModelAndView pauseMv = testController.pauseMigration(testMpRequest, testResponse);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, pauseMv.getViewName(), MIG_CTL);

        ModelAndView resumeMv = testController.resumeMigration(testMpRequest, testResponse);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, resumeMv.getViewName(), REDIRECT);

        Map<String, Object> model = resumeMv.getModel();
        MigrationProcessState state = (MigrationProcessState) model.get(MIG_STATE);
        assertNotNull(NOT_NULL, state);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, ProcessStatus.RUNNING, state.getStatus());

    }

    /**
     * testMigrationControl
     * @throws Exception Exception
     */
    @Test
    public void testMigrationControl() throws Exception {
        testRequest.setMethod(GET);
        testRequest.setRequestURI("/controlMigration.go");

        //start migration
        ModelAndView mv = testController.startMigration(testMpRequest, testResponse, false);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, mv.getViewName(), MIG_CTL);

        ModelAndView controlMv = testController.migrationControl(testRequest, testResponse);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, controlMv.getViewName(), MIG_CTL);
        Map<String, Object> model = controlMv.getModel();

        MigrationProcessState state = (MigrationProcessState) model.get(MIG_STATE);
        assertNotNull(NOT_NULL, state);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, state.getStatus(), ProcessStatus.RUNNING);

    }

    /**
     * testMigrationDetailsWithSingleReport
     * @throws Exception Exception
     */
    @Test
    public void testMigrationDetailsWithSingleReport() throws Exception {
        testRequest.setMethod(GET);
        testRequest.setRequestURI(CTL_DETAILS);

        //start migration
        ModelAndView mv = testController.startMigration(testMpRequest, testResponse, false);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, mv.getViewName(), MIG_CTL);

        ProcessDomainType processDomainType = ProcessDomainType.DOSAGE_FORM_ACTIVE;

        ModelAndView detailsMv =
            testController.migrationDetails(testRequest, testResponse, Boolean.TRUE, Boolean.TRUE, processDomainType);
        Map<String, Object> model = detailsMv.getModel();

        MigratedDomain domain = (MigratedDomain) model.get("domain");
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, domain.getProcessDomainType(), ProcessDomainType.DOSAGE_FORM_ACTIVE);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, detailsMv.getViewName(), "migration-details-print");

        MigrationProcessState state = (MigrationProcessState) model.get(MIG_STATE);
        assertNotNull(NOT_NULL, state);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, state.getStatus(), ProcessStatus.RUNNING);

    }

    /**
     * testMigrationDetailsWithMultipleReport
     * @throws Exception Exception
     */
    @Test
    public void testMigrationDetailsWithMultipleReport() throws Exception {
        testRequest.setMethod(GET);
        testRequest.setRequestURI(CTL_DETAILS);

        //start migration
        ModelAndView mv = testController.startMigration(testMpRequest, testResponse, false);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, mv.getViewName(), MIG_CTL);

        ProcessDomainType processDomainType = ProcessDomainType.DOSAGE_FORM_ACTIVE;

        ModelAndView detailsMv =
            testController.migrationDetails(testRequest, testResponse, Boolean.FALSE, Boolean.FALSE, processDomainType);
        Map<String, Object> model = detailsMv.getModel();

        assertEquals("Should be equal", detailsMv.getViewName(), "migration-details");

        MigrationProcessState state = (MigrationProcessState) model.get(MIG_STATE);
        assertNotNull(NOT_NULL, state);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, state.getStatus(), ProcessStatus.RUNNING);

    }

    /**
     * testDataBaseReset
     * @throws Exception Exception
     */
    @Test
    public void testDataBaseReset() throws Exception {
        testRequest.setMethod(GET);
        testRequest.setRequestURI("/resetDataBase.go");

        //set validation complete here
        testController.setValidationComplete(true);
        ModelAndView mv = testController.startResetDataBase(testRequest, testResponse);
        Map<String, Object> model = mv.getModel();

        MigrationProcessState state = (MigrationProcessState) model.get(DBSTATUS);

        while (state.isDbResetRunning()) {
            if (mv.getViewName() == REDIRECT) {
                if (state.getStatus() == ProcessStatus.DB_RESET_RUNNING) {
                    testRequest.setMethod(GET);
                    testRequest.setRequestURI("/getResetDataBaseStatus.go");

                    ModelAndView mv1 = testController.getResetDataBaseStatus(testRequest, testResponse);
                    Map<String, Object> model1 = mv1.getModel();

                    state = (MigrationProcessState) model1.get(DBSTATUS);
                    assertNotNull(NOT_NULL, state);

                    if (((Integer) model1.get("dbProgress")) >= PPSConstants.I300) {
                        assertTrue("Should not be true!", ((Boolean) model1.get("dbResetComplete")));
                    }

                } else if (state.getStatus() == ProcessStatus.RUNNING) {
                    testRequest.setMethod(GET);
                    testRequest.setRequestURI(MIG_START);

                    ModelAndView mv2 = testController.startMigration(testRequest, testResponse, false);
                    Map<String, Object> model2 = mv2.getModel();
                    state = (MigrationProcessState) model2.get(MIG_STATE);

                }
            }

            Thread.sleep(PPSConstants.I300);
            Log.debug("view name: " + mv.getViewName());
        }
    }

    /**
     * testCalculateOverAllPercentComplete
     */
    @Test
    public void testCalculateOverAllPercentComplete() {
        MigrationProcessState state = null;
        int sumTotal = PPSConstants.NINEZEROZERO;
        int total;
        state = populateMigrationProcessState(new MigrationProcessState());

        double sum =
            state.getMigratedDrugUnit().getCount() + state.getMigratedVaDispenseUnit().getCount()
                + state.getMigratedVaGenericName().getCount() + state.getMigratedDosageForm().getCount()
                + state.getMigratedDrugClass().getCount() + state.getMigratedIngredients().getCount()
                + state.getMigratedOrderableItemsCsv().getCount() + state.getMigratedVaProduct().getCount()
                + state.getMigratedVaProduct().getCount();

        total = (int) ((sum / sumTotal) * PPSConstants.I100);

        LOG.debug("total percent " + total + "%");
        assertTrue("Not less than 100", total >= PPSConstants.I100);

    }

    /**
     * testMockMultipartHttpServletRequestWithByteArray
     * @throws IOException IOException
     */
    @Test
    public void testMockMultipartHttpServletRequestWithByteArray() throws IOException {
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        assertFalse("Should be at least one more!", request.getFileNames().hasNext());
        assertNull(NOT_NULL, request.getFile(FILE1));
        assertNull(NOT_NULL, request.getFile(FILE2));
        assertTrue("Cannot be empty", request.getFileMap().isEmpty());

        request.addFile(new MockMultipartFile(FILE1, "theContent1".getBytes()));
        request.addFile(new MockMultipartFile(FILE2, "theOrigFilename", TEXT_PLAIN, "theContent2".getBytes()));
        runTestMultipartHttpServletRequest(request);
    }

    /**
     * testMockMultipartHttpServletRequestWithInputStream
     * @throws IOException IOException
     */
    @Test
    public void testMockMultipartHttpServletRequestWithInputStream() throws IOException {
        try {
            MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
            request.addFile(new MockMultipartFile(FILE1, new ByteArrayInputStream("theContent1".getBytes())));
            request.addFile(new MockMultipartFile(FILE2, "theOrigFilename", TEXT_PLAIN, new ByteArrayInputStream(
                "theContent2".getBytes())));
            runTestMultipartHttpServletRequest(request);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    /**
     * runTestMultipartHttpServletRequest
     * @param pRequest pRequest
     * @throws IOException IOException
     */
    private void runTestMultipartHttpServletRequest(MultipartHttpServletRequest pRequest) throws IOException {
        Set<String> fileNames = new HashSet<String>();
        Iterator<String> fileIterator = pRequest.getFileNames();
        
        while (fileIterator.hasNext()) {
            fileNames.add(fileIterator.next());
        }
        
        assertEquals(SHOULD_BE_2, 2, fileNames.size());
        assertTrue(SHOULD_DIFF, fileNames.contains(FILE1));
        assertTrue(SHOULD_DIFF, fileNames.contains(FILE2));

        MultipartFile file1 = pRequest.getFile(FILE1);
        MultipartFile file2 = pRequest.getFile(FILE2);

        Map<String, MultipartFile> fileMap = pRequest.getFileMap();
        List<String> fileMapKeys = new LinkedList<String>(fileMap.keySet());
        assertEquals(SHOULD_BE_2, 2, fileMapKeys.size());
        assertEquals(SHOULD_BE_2, file1, fileMap.get(FILE1));
        assertEquals(SHOULD_BE_2, file2, fileMap.get(FILE2));

        assertEquals(SHOULD_BE_2, FILE1, file1.getName());
        assertEquals(SHOULD_BE_2, "", file1.getOriginalFilename());
        assertNull(NOT_NULL, file1.getContentType());
        assertTrue(SHOULD_DIFF, ObjectUtils.nullSafeEquals("theContent1".getBytes(), file1.getBytes()));
        assertTrue(SHOULD_DIFF,
            ObjectUtils.nullSafeEquals("theContent1".getBytes(), FileCopyUtils.copyToByteArray(file1.getInputStream())));
        assertEquals("Name should be file2", FILE2, file2.getName());
        assertEquals("Name should be the same", "theOrigFilename", file2.getOriginalFilename());
        assertEquals("Type should be the same", TEXT_PLAIN, file2.getContentType());

        assertTrue(SHOULD_DIFF, ObjectUtils.nullSafeEquals("theContent2".getBytes(), file2.getBytes()));
        assertTrue(SHOULD_DIFF,
            ObjectUtils.nullSafeEquals("theContent2".getBytes(), FileCopyUtils.copyToByteArray(file2.getInputStream())));
    }

    /**
     * populateMigrationProcessState
     * @param pState state
     * @return MigrationProcessState
     */
    private MigrationProcessState populateMigrationProcessState(MigrationProcessState pState) {
        pState.getMigratedDrugUnit().setCount(PPSConstants.I100);
        pState.getMigratedVaDispenseUnit().setCount(PPSConstants.I100);
        pState.getMigratedVaGenericName().setCount(PPSConstants.I100);
        pState.getMigratedDosageForm().setCount(PPSConstants.I100);
        pState.getMigratedDrugClass().setCount(PPSConstants.I100);
        pState.getMigratedIngredients().setCount(PPSConstants.I100);
        pState.getMigratedOrderableItemsCsv().setCount(PPSConstants.I100);
        pState.getMigratedVaProduct().setCount(PPSConstants.I100);
        pState.getMigratedNDCCsvFile().setCount(PPSConstants.I100);
        
        return pState;
    }

    /**
     * MockMigrationServiceImpl
     *
     */
    public class MockMigrationServiceImpl extends MigrationServiceImpl {

        /**
         * populateMigrationDomainErrors
         * @param pMigrationState pMigrationState
         */
        @Override
        public void populateMigrationDomainErrors(MigrationProcessState pMigrationState) {
            
        }

        /**
         * getMigrationProcessState
         * @return MigrationProcessState
         * 
         */
        @Override
        public MigrationProcessState getMigrationProcessState() {
            MigrationProcessState state = new MigrationProcessState();
            state.setStatus(ProcessStatus.RUNNING);

            return state;
        }

        /**
         * pauseMigration
         * @return MigrationProcessState
         */
        @Override
        public MigrationProcessState pauseMigration() {
            MigrationProcessState state = new MigrationProcessState();
            state.setStatus(ProcessStatus.PAUSED);
            
            return state;
        }

        /**
         * resumeMigration
         * @return MigrationProcessState
         */
        @Override
        public MigrationProcessState resumeMigration() {
            MigrationProcessState state = new MigrationProcessState();
            state.setStatus(ProcessStatus.RUNNING);
            
            return state;
        }

        /**
         * MigrationProcessState
         * @return MigrationProcessState
         */
        @Override
        public MigrationProcessState stopMigration() {
            MigrationProcessState state = new MigrationProcessState();
            state.setStatus(ProcessStatus.STOPPED);
            
            return state;
        }

        /**
         * startMigration
         * @param user UserVo
         * @return MigrationProcessState
         */
        @Override
        public MigrationProcessState startMigration(UserVo user) {

            MigrationProcessState state = new MigrationProcessState();
            state.setStatus(ProcessStatus.RUNNING);
            
            return state;
        }

    }

}
