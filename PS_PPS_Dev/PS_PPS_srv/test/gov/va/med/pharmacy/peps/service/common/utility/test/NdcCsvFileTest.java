/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.test;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcSourceType;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.domain.common.capability.test.DomainCapabilityTestCase;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.utility.NdcCsvFile;


/**
 *
 *  Unit test for NdcCsvFile subclass and CsvFile superclass
 * This unit test reads and writes files to the file system
 *
 * Unit tests defined in this class are:
 *  1. generateNdcVo - helper method to create a test NdcVo
 *  2. getNextNdc - get next NDC from file, returns NDC as a NdcVo object
 *  3. openForExport - open export and write header line
 *  4. putNextNdc - put next NDC to file, input is a NdcVo object
 */
public class NdcCsvFileTest extends DomainCapabilityTestCase {

    private static final String NULL_DATE = "00000000";
    private static final String SHOULD_MATCH = "Test Data should match!";
    
    // test data for one NDC
    // change this data to validate all possible values
    private static final String[] NDC_TEST_DATA = { 
        "012345678901", //Ndc (required)
        "Test_Product".toUpperCase(), //VaProductName
        "Test_ProductVuid".toUpperCase(), //ProductVuid
        "Test_ProductGcnSeqNo".toUpperCase(), //ProductGncSeqNo
        NULL_DATE, //InactivationDate
        "Test_Manufacturer".toUpperCase(), //Manufacturer
        "12345.2222", //NdcDispenseUnitPerOrderUnit
        PPSConstants.PRESCRIPTION, //OtcRxIndicator
        "9999.9999", //PackageSize
        "Test_PackageType".toUpperCase(), //PackageType
        "Test_TradeName".toUpperCase(), //TradeName
        "N", //Refrigeration
        "", //ProtectFromLight (defaults to N)
        "", //ProposedInactivationDate
        "TestPreNdc".toUpperCase(), //PreviousNdc
        "TestPreUpc".toUpperCase(), //PreviousUpcUpn
        "TestOrderU".toUpperCase(), //OrderUnit
        "", //I_SOURCE
        "" }; //ProductNumber

    // this test file should already exist (delivered to stream)
    private String testFilePathIn = System.getProperty(PPSConstants.USER_DIR).concat("/etc/csv/test/");
    private String badHeaderFilename = testFilePathIn.concat("NdcCsvFile_TestBadHeader.csv");

    // this test file is created during the unit test
    private String testFilePathOut = System.getProperty(PPSConstants.USER_DIR).concat("/tmp/");
    private String testNdcFilename = testFilePathOut.concat("NdcCsvFile_Test.csv");

    // this file does not exist
    private String doesNotExistFilename = testFilePathIn.concat("NdcCsvFile_DoesNotExist.csv");

    private DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);

    // blank ndc vo used as initial vo
    private ManagedItemCapability managedItemCapability;
    private NdcVo blankNdcVo;

    /**
     * NdcCsvFileTest
     */
    public NdcCsvFileTest() {
        super();
    }
    
    /**
     * initialize the ndcVO blank template
     */
    @Override
    protected void setUp() {

        managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        blankNdcVo = (NdcVo) managedItemCapability.retrieveBlankTemplate(EntityType.NDC);

    }

    /**
     *  create and initialize an NdcVo to test file export and import methods
     *  @return NdcVo
     */
    private NdcVo generateNdcVo() {

        // start with an ndcVO blank template
        NdcVo ndc = blankNdcVo;
        

        // disable lenient parsing of dates
        df.setLenient(false);

        //Ndc field
        if (NDC_TEST_DATA[NdcCsvFile.I_NDC_NUM].length() > 0) {
            ndc.setNdc(NDC_TEST_DATA[NdcCsvFile.I_NDC_NUM]);
        }

        //VaProductName, ProductVuid, ProductGcnSeqNo fields 
        ProductVo product = new ProductVo();

        if (NDC_TEST_DATA[NdcCsvFile.I_PROD_NAME].length() > 0) {
            product.setVaProductName(NDC_TEST_DATA[NdcCsvFile.I_PROD_NAME]);
        }

        if (NDC_TEST_DATA[NdcCsvFile.I_PROD_VUID].length() > 0) {
            product.setVuid(NDC_TEST_DATA[NdcCsvFile.I_PROD_VUID]);
        }

        if (NDC_TEST_DATA[NdcCsvFile.I_PROD_GCN].length() > 0) {
            product.setGcnSequenceNumber(NDC_TEST_DATA[NdcCsvFile.I_PROD_GCN]);
        }

        ndc.setProduct(product);

        //NdcItemInactivationDate field
        //also set status values
        if (NDC_TEST_DATA[NdcCsvFile.I_INACTIVATION_DATE].equals("")
            || NDC_TEST_DATA[NdcCsvFile.I_INACTIVATION_DATE].equals(NULL_DATE)) {
            ndc.setInactivationDate(null);
        } else {
            try {
                ndc.setInactivationDate(df.parse(NDC_TEST_DATA[NdcCsvFile.I_INACTIVATION_DATE]));
            } catch (ParseException e) {
                fail("Parsing Exception.");
            }
        }

        //Manufacturer field
        ManufacturerVo manufacturer = new ManufacturerVo();

        if (NDC_TEST_DATA[NdcCsvFile.I_MANUFACTURER].length() > 0) {
            manufacturer.setValue(NDC_TEST_DATA[NdcCsvFile.I_MANUFACTURER]);
        }

        ndc.setManufacturer(manufacturer);

        //OrderUnit field
        OrderUnitVo orderUnit = new OrderUnitVo();

        if (NDC_TEST_DATA[NdcCsvFile.I_ORDER_UNIT].length() > 0) {
            orderUnit.setAbbrev(NDC_TEST_DATA[NdcCsvFile.I_ORDER_UNIT]);
        }

        ndc.setOrderUnit(orderUnit);

        //OtcRxIndicator field
        OtcRxVo otcRx = new OtcRxVo();

        if (NDC_TEST_DATA[NdcCsvFile.I_OTC_RX_IND].length() > 0) {
            otcRx.setValue(NDC_TEST_DATA[NdcCsvFile.I_OTC_RX_IND]);
        }

        ndc.setOtcRxIndicator(otcRx);

        //PackageSize field
        if (NDC_TEST_DATA[NdcCsvFile.I_PACKAGE_SIZE].length() > 0) {
            ndc.setPackageSize(Double.parseDouble(NDC_TEST_DATA[NdcCsvFile.I_PACKAGE_SIZE]));
        }

        return generateNdcVoPart2(ndc);
    }
        
    /**
     *  create and initialize an NdcVo to test file export and import methods
     *  @param ndc NdcVo
     *  @return NdcVo
     */
    private NdcVo generateNdcVoPart2(NdcVo ndc) {
    
        
        DataFields<DataField> vadfs = ndc.getVaDataFields();
        
        //PackageType field
        PackageTypeVo packageType = new PackageTypeVo();

        if (NDC_TEST_DATA[NdcCsvFile.I_PACKAGE_TYPE].length() > 0) {
            packageType.setValue(NDC_TEST_DATA[NdcCsvFile.I_PACKAGE_TYPE]);
        }

        ndc.setPackageType(packageType);

        //TradeName field
        if (NDC_TEST_DATA[NdcCsvFile.I_TRADE_NAME].length() > 0) {
            ndc.setTradeName(NDC_TEST_DATA[NdcCsvFile.I_TRADE_NAME]);
        }

        //Refrigeration field
        if (NDC_TEST_DATA[NdcCsvFile.I_REFRIG].length() > 0) {
            if (NDC_TEST_DATA[NdcCsvFile.I_REFRIG].equals("Y")) {
                vadfs.getDataField(FieldKey.REFRIGERATION).addStringSelection(PPSConstants.REFRIGERATE);
            }

            if (NDC_TEST_DATA[NdcCsvFile.I_REFRIG].equals("N")) {
                vadfs.getDataField(FieldKey.REFRIGERATION).addStringSelection(PPSConstants.DO_NOT_REFRIGERATE);
            }
        }

        //ProtectFromLight field
        if (NDC_TEST_DATA[NdcCsvFile.I_PROTECT].length() > 0) {
            if (NDC_TEST_DATA[NdcCsvFile.I_PROTECT].equals("Y")) {
                vadfs.getDataField(FieldKey.PROTECT_FROM_LIGHT).selectValue(true);
            } else if (NDC_TEST_DATA[NdcCsvFile.I_PROTECT].equals("N")) {
                vadfs.getDataField(FieldKey.PROTECT_FROM_LIGHT).selectValue(false);
            }
        }

        //ProposedInactivationDate field
        if (NDC_TEST_DATA[NdcCsvFile.I_PROP_INACT_DATE].equals("")
            || NDC_TEST_DATA[NdcCsvFile.I_PROP_INACT_DATE].equals(NULL_DATE)) {
            vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(null);
        } else {
            try {
                Date piDate = df.parse(NDC_TEST_DATA[NdcCsvFile.I_PROP_INACT_DATE]);
                vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(piDate);
            } catch (ParseException ex) {
                fail("invalid NDC_TEST_DATA, proposed inactivation date");
            }
        }

        //PreviousNdc field
        if (NDC_TEST_DATA[NdcCsvFile.I_PREV_NDC].length() > 0) {
            vadfs.getDataField(FieldKey.PREVIOUS_NDC).selectStringValue(NDC_TEST_DATA[NdcCsvFile.I_PREV_NDC]);
        }

        //PreviousUpcUpn field
        if (NDC_TEST_DATA[NdcCsvFile.I_PREV_NDC_UPN].length() > 0) {
            vadfs.getDataField(FieldKey.PREVIOUS_UPC_UPN).selectStringValue(NDC_TEST_DATA[NdcCsvFile.I_PREV_NDC_UPN]);
        }

        //NdcDispenseUnitPerOrderUnit field
        if (NDC_TEST_DATA[NdcCsvFile.I_DISP_UNIT].length() > 0) {
            ndc.setNdcDispUnitsPerOrdUnit(Double.parseDouble(NDC_TEST_DATA[NdcCsvFile.I_DISP_UNIT]));
        }

        //I_SOURCE field
        if (NDC_TEST_DATA[NdcCsvFile.I_SOURCE].length() > 0) {
            if (NDC_TEST_DATA[NdcCsvFile.I_SOURCE].equals(NdcSourceType.VA.toString())) {
                ndc.setSource(NdcSourceType.VA);
            } else if (NDC_TEST_DATA[NdcCsvFile.I_SOURCE].equals(NdcSourceType.COTS.toString())) {
                ndc.setSource(NdcSourceType.COTS);
            } else if (NDC_TEST_DATA[NdcCsvFile.I_SOURCE].equals(NdcSourceType.FDA.toString())) {
                ndc.setSource(NdcSourceType.FDA);
            } else {
                fail("Invalid NDC_TEST_DATA, I_SOURCE");
            }
        } else { // if null, then set it to VA if the product name exists, else COTS
            if (NDC_TEST_DATA[NdcCsvFile.I_PROD_NAME].length() > 0) {
                ndc.setSource(NdcSourceType.VA);
            } else {
                ndc.setSource(NdcSourceType.COTS);
            }
        }

        //ProductNumber field
        if (NDC_TEST_DATA[NdcCsvFile.I_PROD_NO].length() > 0) {
            vadfs.getDataField(FieldKey.PRODUCT_NUMBER).selectStringValue(NDC_TEST_DATA[NdcCsvFile.I_PROD_NO]);
        }

        return ndc;
    }

    /**
     * Use the openForExport, putNextNdc, and closeExport methods to create a
     * test ndc file with one row. The exported file will be used to test the
     * import methods.
     */
    public void testExport() {

        NdcVo testNdc = generateNdcVo();

        NdcCsvFile ndcFile = new NdcCsvFile();

        try {
            ndcFile.openForExport(testNdcFilename);
            ndcFile.putNextNdc(testNdc);
            ndcFile.closeExport();
        } catch (MigrationException ex) {
            fail("testExport should not throw a MigrationException: " + ex.getMessage());
        }
    }

    /**
     *  Test openForImport method, file not found exception
     */
    public void testOpenForImport1() {

        NdcCsvFile ndcFile = new NdcCsvFile();

        try {
            ndcFile.openForImport(doesNotExistFilename);
            fail("openForImport should have raised MigrationException with file not found message");
        } catch (MigrationException ex) {
            assertTrue("Not found", ex.getMessage().contains("not found"));
        }
    }

    /**
     * Test openForImport method, bad header exception
     */
    public void testOpenForImport2() {

        NdcCsvFile ndcFile = new NdcCsvFile();

        try {
            ndcFile.openForImport(badHeaderFilename);
            fail("openForImport should have raised MigrationException with bad header message");
        } catch (MigrationException ex) {
            assertEquals("Column header should be invalid!", "Column header on line 1 is invalid.", ex.getMessage());
        }
    }

    /**
     * Test openForImport, getNextNdc, and closeImport methods. Test imports
     * ndc file created by the testExport() test. Test validates that all file
     * fields match data in NDC_TEST_DATA array.
     * Also tests the item and item request status fields are set.
     * Also tests that null is returned at EOF.
     */
    public void testImport() {

        NdcCsvFile ndcFile = new NdcCsvFile();

        //-----------------------------------
        //one ndc from file and validate data
        //-----------------------------------
        try {
            ndcFile.openForImport(testNdcFilename);

            NdcVo ndc = ndcFile.getNextNdc(blankNdcVo);

            //Ndc name field (dashes need to be added to field data)
            String strNDC = NDC_TEST_DATA[NdcCsvFile.I_NDC_NUM];

            if (strNDC.length() == PPSConstants.I11) {
                strNDC = strNDC.substring(0, PPSConstants.I5) + "-" 
                    + strNDC.substring(PPSConstants.I5, PPSConstants.I9) + "-" 
                    + strNDC.substring(PPSConstants.I9, PPSConstants.I11);
            } else {
                strNDC = strNDC.substring(1, PPSConstants.I6) + "-" 
                    + strNDC.substring(PPSConstants.I6, PPSConstants.I10) + "-" 
                    + strNDC.substring(PPSConstants.I10, PPSConstants.I12);
            }

            assertEquals("NDC number should be equal.", strNDC, ndc.getNdc());

            //VaProductName field 
            if (ndc.getProduct().getVaProductName() == null) {
                assertEquals("Product Name should be empty!", NDC_TEST_DATA[NdcCsvFile.I_PROD_NAME], "");
            } else {
                assertEquals("Product Name should not be empty!", NDC_TEST_DATA[NdcCsvFile.I_PROD_NAME], ndc.getProduct()
                        .getVaProductName());
            }

            //ProductVuid field 
            if (ndc.getProduct().getVuid() == null) {
                assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PROD_VUID], "");
            } else {
                assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PROD_VUID], ndc.getProduct().getVuid());
            }

            //ProductGcnSeqNo field 
            if (ndc.getProduct().getVuid() == null) {
                assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PROD_GCN], "");
            } else {
                assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PROD_GCN], ndc.getProduct()
                        .getGcnSequenceNumber());
            }

            //NdcItemInactivationDate field
            if (ndc.getInactivationDate() == null) {
                Boolean dateIsNull = NDC_TEST_DATA[NdcCsvFile.I_INACTIVATION_DATE].equals("");
                Boolean dateIsZero = NDC_TEST_DATA[NdcCsvFile.I_INACTIVATION_DATE].equals(NULL_DATE);
                assertEquals(SHOULD_MATCH, true, dateIsNull || dateIsZero);
                assertEquals(SHOULD_MATCH, ItemStatus.ACTIVE, ndc.getItemStatus());
            } else {
                assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_INACTIVATION_DATE],
                             df.format(ndc.getInactivationDate()));
                assertEquals(SHOULD_MATCH, ItemStatus.INACTIVE, ndc.getItemStatus());
            }

            assertEquals(SHOULD_MATCH, RequestItemStatus.APPROVED, ndc.getRequestItemStatus());
            
            testImportPart2(ndc);
        } catch (MigrationException ex) {
            fail("getNextNdc shouldn't throw a MigrationException: " + ex.getMessage());
        }

        
        //-----------------------------------------------------------
        // second get should return null value (only one row in file)
        //-----------------------------------------------------------
        try {
            NdcVo prevNDC = new NdcVo();
            NdcVo ndc = ndcFile.getNextNdc(prevNDC);
            assertEquals(SHOULD_MATCH, null, ndc);
        } catch (MigrationException ex) {
            fail("getNextNdc should not throw a MigrationException: " + ex.getMessage());
        }

        try {
            ndcFile.closeImport();
        } catch (MigrationException e) {
            fail("Exception closing NDC File " + e.getMessage());
        }
    }
        

        /**
         * testImportPart2
         * @param ndc NdcVO
         * @throws MigrationException MigrationExcepton
         */
    private void testImportPart2(NdcVo ndc) throws MigrationException {
            
        DataFields<DataField> vadfs = ndc.getVaDataFields();
        
        //Manufacturer field
            
        if (ndc.getManufacturer().getValue() == null) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_MANUFACTURER], "");
        } else {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_MANUFACTURER], ndc.getManufacturer()
                        .getValue());
        }

        //OrderUnit field
        if (ndc.getOrderUnit().getAbbrev() == null) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_ORDER_UNIT], "");
        } else {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_ORDER_UNIT], ndc.getOrderUnit()
                    .getAbbrev());
        }

        //OtcRxIndicator field
        if (ndc.getOtcRxIndicator() == null || ndc.getOtcRxIndicator().getValue() == null) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_OTC_RX_IND], "");
        } else {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_OTC_RX_IND], ndc.getOtcRxIndicator()
                    .getValue());
        }

        //PackageSize field
        if (ndc.getPackageSize().toString() == null) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PACKAGE_SIZE], "");
        } else {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PACKAGE_SIZE], ndc.getPackageSize()
                    .toString());
        }

        //PackageType field
        if (ndc.getPackageType().getValue() == null) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PACKAGE_TYPE], ndc.getPackageType()
                    .getValue());
        } else {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PACKAGE_TYPE], ndc.getPackageType()
                    .getValue());
        }

        //TradeName field
        if (ndc.getPackageType().getValue() == null) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_TRADE_NAME], "");
        } else {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_TRADE_NAME], ndc.getTradeName());
        }

        //Refrigeration field
        ListDataField<String> refrigerate = vadfs.getDataField(FieldKey.REFRIGERATION);

        if (refrigerate.getValue() == null) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_REFRIG], "");
        }

        if (refrigerate.contains(PPSConstants.REFRIGERATE)) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_REFRIG], "Y");
        }

        if (refrigerate.contains(PPSConstants.DO_NOT_REFRIGERATE)) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_REFRIG], "N");
        }

        //ProtectFromLight field (defaults to false in blankNdcVo)
        DataField<Boolean> protectFromLight = vadfs.getDataField(FieldKey.PROTECT_FROM_LIGHT);

        if (NDC_TEST_DATA[NdcCsvFile.I_PROTECT] == "" || NDC_TEST_DATA[NdcCsvFile.I_PROTECT] == "N") {
            assertEquals(SHOULD_MATCH, false, (boolean) protectFromLight.getValue());
        }

        if (NDC_TEST_DATA[NdcCsvFile.I_PROTECT] == "Y") {
            assertEquals(SHOULD_MATCH, true, (boolean) protectFromLight.getValue());
        }

        //ProposedInactivationDate field
        DataField<Date> proposedInactivationDate = vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);

        if (proposedInactivationDate.getValue() == null) {
            Boolean dateIsNull = NDC_TEST_DATA[NdcCsvFile.I_PROP_INACT_DATE].equals("");
            Boolean dateIsZero = NDC_TEST_DATA[NdcCsvFile.I_PROP_INACT_DATE].equals(NULL_DATE);
            assertEquals(SHOULD_MATCH, true, dateIsNull || dateIsZero);
        } else {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PROP_INACT_DATE],
                         df.format(proposedInactivationDate.getValue()));
        }

        //PreviousNdc field, single selection
        DataField<String> previousNdc = vadfs.getDataField(FieldKey.PREVIOUS_NDC);

        if (previousNdc.getValue() == null) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PREV_NDC], "");
        } else {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PREV_NDC], previousNdc.getValue());
        }

        //PreviousUpcUpn field, single selection
        DataField<String> previousUpcUpn = vadfs.getDataField(FieldKey.PREVIOUS_UPC_UPN);

        if (previousUpcUpn.getValue() == null) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PREV_NDC_UPN], "");
        } else {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PREV_NDC_UPN],
                         previousUpcUpn.getValue());
        }

        //NdcDispenseUnitPerOrderUnit field
        if (ndc.getNdcDispUnitsPerOrdUnit() == null) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_DISP_UNIT], ndc
                    .getNdcDispUnitsPerOrdUnit().toString());
        } else {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_DISP_UNIT], ndc
                    .getNdcDispUnitsPerOrdUnit().toString());
        }

        //I_SOURCE field (field will always be set
        if (NDC_TEST_DATA[NdcCsvFile.I_SOURCE].length() > 0) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_SOURCE], ndc.getSource().toString());
        } else {
            if (NDC_TEST_DATA[NdcCsvFile.I_PROD_NAME].length() > 0) {
                assertEquals(SHOULD_MATCH, "VA", ndc.getSource().toString());
            } else {
                assertEquals(SHOULD_MATCH, "COTS", ndc.getSource().toString());
            }
        }

        //ProductNumber field
        DataField<String> productNumber = vadfs.getDataField(FieldKey.PRODUCT_NUMBER);

        if (productNumber.getValue() == null) {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PROD_NO], "");
        } else {
            assertEquals(SHOULD_MATCH, NDC_TEST_DATA[NdcCsvFile.I_PROD_NO], productNumber.getValue());
        }
    }
}
