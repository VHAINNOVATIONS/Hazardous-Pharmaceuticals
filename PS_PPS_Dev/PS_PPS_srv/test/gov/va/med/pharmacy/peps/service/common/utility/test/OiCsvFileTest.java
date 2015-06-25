/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.test;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.SubCategory;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.MultitextDataField;
import gov.va.med.pharmacy.peps.domain.common.capability.test.DomainCapabilityTestCase;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.utility.OiCsvFile;


/**
 * 
 * Unit test for OiCsvFile subclass and CsvFile superclass
 * This unit test reads and writes files to the file system
 * 
 *  Methods/tests defined in this class are:
 *  1. generateOiVo - helper method to create a test OrderableItemVo
 *  2. getNextOi - get next OI from file, returns OI as a OrderableItemVo object
 *  3. openForExport - open export and write header line
 *  5. putNextOi - put next OI to file, input is a OrderableItemVo object
 */  
public class OiCsvFileTest extends DomainCapabilityTestCase {

    private static String DIRECTORY = "user.dir";
    private static String ERROR_MSG = "Fields do not match!";
    
    // change this data to validate all possible values
    private static String[] OI_TEST_DATA = { 
        "Test_OiName".toUpperCase(), //OiName (required)
        "Test_VistaOiName".toUpperCase(), //VistaOiName (required)
        "SUPPLY", //ProductType (required, defaults to MEDICATION)
        "Test_DosageFormName".toUpperCase(), //DosageFormName (required)
        "20111010", //InactivationDate
        "20100510", //ProposedInactivationDate
        "Y", //LifetimeCumulDosage (required)
        "N", //FormularyIndicator (required)
        "Y", //NonVaMed (required)
        "N", //OiIvFlag (required)
        "12D", //DaysOrDoseLimit
        "Synonym1||Synonym2||Synonym3".toUpperCase(), //Synonym (multiples must be sorted to pass test)
        "DrugText1||DrugText2".toUpperCase(), //DrugText(multiples must be sorted to pass test)
        RequestRejectionReason.ITEM_EXISTS.toString(), //RequestRejectReason
        "Test_RejectReasonText".toUpperCase(), //RejectReasonText
        "Test_PatientInstructions".toUpperCase(), //PatientInstructions
        "Test_OtherLanguageInstructions".toUpperCase(), //OtherLanguageInstructions
        "Std_Med_Route".toUpperCase() //StandardMedRoute
    };

  
    // this test file should already exist (delivered to stream)
    private String testFilePathIn = System.getProperty(DIRECTORY).concat("/etc/csv/test/");
    private String badHeaderFilename = testFilePathIn.concat("OiCsvFile_TestBadHeader.csv");

    // this test file is created during the unit test
    private String testFilePathOut = System.getProperty(DIRECTORY).concat("/tmp/");
    private String testOiFilename = testFilePathOut.concat("OiCsvFile_Test.csv");

    // this file does not exist
    private String doesNotExistFilename = testFilePathIn.concat("OiCsvFile_DoesNotExist.csv");

    private DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);

    // blank oi vo used as initial vo
    private ManagedItemCapability managedItemCapability;
    private OrderableItemVo blankOiVo;

    /**
     * Default constructor for OiCsvFileTest
     */
    public OiCsvFileTest() {
        super();
    }
    
    /**
     * initialize the OrderableItemVO blank template
     */ 
    @Override
    protected void setUp() {

        managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        blankOiVo = (OrderableItemVo) managedItemCapability.retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);
    }

    /**
     *
     * create and initialize an OrderableItemVo to test export and import methods
     * @return OrderableItemVo
     * @throws ParseException ParseException
     */ 
    private OrderableItemVo generateOiVo() throws ParseException {

        OrderableItemVo oiVo = blankOiVo;
        DataFields<DataField> vadfs = oiVo.getVaDataFields();

        // disable lenient parsing of dates
        df.setLenient(false);

        //OiName field
        oiVo.setOiName(OI_TEST_DATA[OiCsvFile.I_OI_NAME]);

        //OiVistaName field
        oiVo.setVistaOiName(OI_TEST_DATA[OiCsvFile.I_VISTA_OI_NAME]);

        //ProductType field
        List<Category> category = new ArrayList<Category>();
        category.add(Category.COMPOUND);
        oiVo.setCategories(category);
       
        //ProductType field
        List<SubCategory> subCategory = new ArrayList<SubCategory>();
        subCategory.add(SubCategory.HERBAL);
        oiVo.setCategories(category);
        
        //DosageFormName field
        DosageFormVo dosageForm = new DosageFormVo();
        dosageForm.setDosageFormName(OI_TEST_DATA[OiCsvFile.I_DOSE_FORM]);
        oiVo.setDosageForm(dosageForm);

        //InactivationDate field
        if (OI_TEST_DATA[OiCsvFile.I_INACT_DATE].equals("")) {
            oiVo.setItemStatus(ItemStatus.ACTIVE);
            oiVo.setInactivationDate(null);          
        } else {
            Date inactiveDate = df.parse(OI_TEST_DATA[OiCsvFile.I_INACT_DATE]);
            oiVo.setItemStatus(ItemStatus.INACTIVE);
            oiVo.setInactivationDate(inactiveDate);            
        }

        //PropInactivationDate field
        DataField<Date> proposedInactivationDate = vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);
        
        if (OI_TEST_DATA[OiCsvFile.I_PROP_INACT_DATE].equals("")) {
            proposedInactivationDate.selectValue(null);
            oiVo.getVaDataFields().setDataField(proposedInactivationDate);
        } else {
            Date inactiveDate = df.parse(OI_TEST_DATA[OiCsvFile.I_PROP_INACT_DATE]);
            proposedInactivationDate.selectValue(inactiveDate);
            oiVo.getVaDataFields().setDataField(proposedInactivationDate);
        }

        //LifetimeCumulDosage field
        DataField<Boolean> lifetime = vadfs.getDataField(FieldKey.LIFETIME_CUMULATIVE_DOSAGE);
        
        if (OI_TEST_DATA[OiCsvFile.I_LIFE_DOSE].equals("Y")) {
            lifetime.selectValue(true);
            oiVo.getVaDataFields().setDataField(lifetime);
        }
        
        if (OI_TEST_DATA[OiCsvFile.I_LIFE_DOSE].equals("N")) {
            lifetime.selectValue(false);
            oiVo.getVaDataFields().setDataField(lifetime);
        }

        //NationalFormularyIndicator field
        if (OI_TEST_DATA[OiCsvFile.I_NAT_FORM_IND].equals("Y")) {
            oiVo.setNationalFormularyIndicator(true);
        }
        
        if (OI_TEST_DATA[OiCsvFile.I_NAT_FORM_IND].equals("N")) {
            oiVo.setNationalFormularyIndicator(false);
        }

        //NonVaMed field
        if (OI_TEST_DATA[OiCsvFile.I_NON_VA_MED].equals("Y")) {
            oiVo.setNonVaMed(true);
        }
        
        if (OI_TEST_DATA[OiCsvFile.I_NON_VA_MED].equals("N")) {
            oiVo.setNonVaMed(false);
        }

        //OiIvFlag field
        DataField<Boolean> ivFlag = vadfs.getDataField(FieldKey.OI_IV_FLAG);
        
        if (OI_TEST_DATA[OiCsvFile.I_OI_IV_FLAG].equals("Y")) {
            ivFlag.selectValue(true);
            oiVo.getVaDataFields().setDataField(ivFlag);
        }
        
        if (OI_TEST_DATA[OiCsvFile.I_OI_IV_FLAG].equals("N")) {
            ivFlag.selectValue(false);
            oiVo.getVaDataFields().setDataField(ivFlag);
        }

        //DaysOrDoseLimit field
        if (OI_TEST_DATA[OiCsvFile.I_DOD_LIMIT].length() > 0) {
            DataField<String> dLimit = vadfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);
            dLimit.selectValue(OI_TEST_DATA[OiCsvFile.I_DOD_LIMIT]);
            oiVo.getVaDataFields().setDataField(dLimit);
        }

        return generateOiVo2(oiVo, vadfs);
    }

    /**
     *
     * create and initialize an OrderableItemVo to test export and import methods
     * @param oiVo OrderableItemVo
     * @param vadfs VA Data fields
     * @return OrderableItemVo
     * @throws ParseException ParseException
     */ 
    private OrderableItemVo generateOiVo2(OrderableItemVo oiVo,  DataFields<DataField> vadfs) throws ParseException {
        
        //Synonym field
        if (OI_TEST_DATA[OiCsvFile.I_SYNONYM].length() > 0) {
            MultitextDataField<String> synonym = vadfs.getDataField(FieldKey.ORDERABLE_ITEM_SYNONYM);
            String[] synonymArray = OI_TEST_DATA[OiCsvFile.I_SYNONYM].split(OiCsvFile.FIELD_MULTIPLE_SEPARATOR_SPLIT);
            
            for (String selection : synonymArray) {
                synonym.addStringSelection(selection);
            }
            
            oiVo.getVaDataFields().setDataField(synonym);
        }

        //Drug Text field
        if (OI_TEST_DATA[OiCsvFile.I_DRUG_TEXT].length() > 0) {
            String[] dTextArray = OI_TEST_DATA[OiCsvFile.I_DRUG_TEXT].split(OiCsvFile.FIELD_MULTIPLE_SEPARATOR_SPLIT);
            List<DrugTextVo> drugTexts = new ArrayList<DrugTextVo>();
            
            for (String selection : dTextArray) {
                DrugTextVo drugText = new DrugTextVo();
                drugText.setValue(selection);
                drugTexts.add(drugText);
            }
            
            oiVo.setNationalDrugTexts(drugTexts);
        }
        
        //RequestRejectReason field
        if (OI_TEST_DATA[OiCsvFile.I_REQ_REQ_REASON].length() > 0) {
            oiVo.setRequestRejectionReason(RequestRejectionReason.ITEM_EXISTS);
            
            if (OI_TEST_DATA[OiCsvFile.I_REQ_REQ_REASON].length() > 0) {
                if (OI_TEST_DATA[OiCsvFile.I_REQ_REQ_REASON].equals(RequestRejectionReason.ITEM_EXISTS.toString())) {
                    oiVo.setRequestRejectionReason(RequestRejectionReason.ITEM_EXISTS);
                } else if (OI_TEST_DATA[OiCsvFile.I_REQ_REQ_REASON].equals("INAPPROPRIATE_REQUEST")) {
                    oiVo.setRequestRejectionReason(RequestRejectionReason.INAPPROPRIATE_REQUEST);
                } else if (OI_TEST_DATA[OiCsvFile.I_REQ_REQ_REASON].equals("INCOMPLETE_INFORMATION")) {
                    oiVo.setRequestRejectionReason(RequestRejectionReason.INCOMPLETE_INFORMATION);
                } else {
                    fail("Invalid TestData, RequestRejectReason");
                }
            }
        }

        //RejectReasonText field
        if (OI_TEST_DATA[OiCsvFile.I_REQ_REQ_TEXT].length() > 0) {
            oiVo.setRejectionReasonText(OI_TEST_DATA[OiCsvFile.I_REQ_REQ_TEXT]);
        }

        //PatientInstructions field 
        if (OI_TEST_DATA[OiCsvFile.I_PAT_INSTR].length() > 0) {
            DataField<String> patientInstructions = vadfs.getDataField(FieldKey.PATIENT_INSTRUCTIONS);
            patientInstructions.selectValue(OI_TEST_DATA[OiCsvFile.I_PAT_INSTR]);
            oiVo.getVaDataFields().setDataField(patientInstructions);
        }

        //OtherLanguageInstructions field 
        if (OI_TEST_DATA[OiCsvFile.I_OTHER_INSTR].length() > 0) {
            DataField<String> otherInstructions = vadfs.getDataField(FieldKey.OTHER_LANGUAGE_INSTRUCTIONS);
            otherInstructions.selectValue(OI_TEST_DATA[OiCsvFile.I_OTHER_INSTR]);
            oiVo.getVaDataFields().setDataField(otherInstructions);
        }

        //StandardMedRoute field (optional)
        if (OI_TEST_DATA[OiCsvFile.I_STD_MED_ROUTE].length() > 0) {
            StandardMedRouteVo stdVo = new StandardMedRouteVo();
            stdVo.setValue(OI_TEST_DATA[OiCsvFile.I_STD_MED_ROUTE]);
            oiVo.setStandardMedRoute(stdVo);
        }

        return oiVo;

    } //end generateOiVo

    /**
     * Use the openForExport, putNextOi, and closeExport methods to create a
     * test OI file with one row. The exported file will be used to test the
     * import methods.
     * @throws ParseException 
     * 
     */ 
    public void testExport() throws ParseException {

        OrderableItemVo testOi = generateOiVo();

        OiCsvFile oiFile = new OiCsvFile();
        
        try {
            oiFile.openForExport(testOiFilename);
            oiFile.putNextOi(testOi);
            oiFile.closeExport();
        } catch (MigrationException ex) {
            fail("should not throw a MigrationException");
        }
    }
    
    /**
     * Test openForImport method, file not found exception
     */ 
    public void testOpenForImport1() {

        OiCsvFile oiFile = new OiCsvFile();

        try {
            oiFile.openForImport(doesNotExistFilename);
            fail("openForImpory should have raised MigrationException with file not found message");
        } catch (MigrationException ex) {
            assertTrue("Should contain not found", ex.getMessage().contains("not found"));
        }
    }

    /**
     * Test openForImport method, bad header exception
     */ 
    public void testOpenForImport2() {

        OiCsvFile oiFile = new OiCsvFile();

        try {
            oiFile.openForImport(badHeaderFilename);
            fail("openForImport should have raised MigrationException with bad header message");
        } catch (MigrationException ex) {
            assertTrue("Invalid Column Headers!", ex.getMessage().startsWith("Column header on line 1 is not valid."));
        }
    }

    /**
     * Test openForImport, getNextNdc, and closeImport methods. Test imports
     * ndc file created by the testExport() test. Test validates that all file
     * fields match data in ndcTestData array.
     * Also tests the item and item request status fields are set.
     * Also tests that null is returned at EOF.
     * 
     */ 
    public void testImport() {

        OiCsvFile oiFile = new OiCsvFile();

        //--------------------------------------------
        //get one oi from file and validate each field
        //--------------------------------------------
        try {
            oiFile.openForImport(testOiFilename);

            managedItemCapability = getNationalCapability(ManagedItemCapability.class);
            OrderableItemVo oiVo =
                    (OrderableItemVo) managedItemCapability.retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);

            OrderableItemVo oi = oiFile.getNextOi(oiVo);
            DataFields<DataField> vadfs = oi.getVaDataFields();

            //Oi name field
            assertEquals("Name should be the same.", OI_TEST_DATA[OiCsvFile.I_OI_NAME], oi.getOiName());

            //OiVistaName field
            assertEquals("Vista OI Name should be the same.", OI_TEST_DATA[OiCsvFile.I_VISTA_OI_NAME], oi.getVistaOiName());

        
            //DosageFormName field
            //only tests name
            assertEquals("Dosage Forms should be the same.", 
                         OI_TEST_DATA[OiCsvFile.I_DOSE_FORM], oi.getDosageForm().getDosageFormName());

            //InactivationDate field
            if (oi.getInactivationDate() == null) {
                assertEquals("Inactivation Date should be null", OI_TEST_DATA[OiCsvFile.I_INACT_DATE], "");
                assertEquals("Item Status should be the same.", ItemStatus.ACTIVE, oi.getItemStatus());
            } else {
                assertEquals("Inactivation Date should be the same", 
                             OI_TEST_DATA[OiCsvFile.I_INACT_DATE], df.format(oi.getInactivationDate()));
                assertEquals("Item Status should be Inacative.", ItemStatus.INACTIVE, oi.getItemStatus());
            }

            //ProposedInactivationDate field
            DataField<Date> proposedInactivationDate = vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);
            
            if (proposedInactivationDate.getValue() == null) {
                assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_PROP_INACT_DATE], "");
            } else {
                assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_PROP_INACT_DATE], 
                             df.format(proposedInactivationDate.getValue()));
            }

            //LifetimeCumulDosage field
            DataField<Boolean> lifetime = vadfs.getDataField(FieldKey.LIFETIME_CUMULATIVE_DOSAGE);
            
            if (lifetime.getValue()) {
                assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_LIFE_DOSE], "Y");
            } else {
                assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_LIFE_DOSE], "N");
            }

            //NationalFormularyIndicator field
            if (oi.getNationalFormularyIndicator()) {
                assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_NAT_FORM_IND], "Y");
            } else {
                assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_NAT_FORM_IND], "N");
            }

            //NonVaMed field
            if (oi.getNonVaMed()) {
                assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_NON_VA_MED], "Y");
            } else {
                assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_NON_VA_MED], "N");
            }

            
            oi = testImport2(oi, vadfs);


        } catch (MigrationException ex) {
            fail("getNextOi should not throw MigrationException" + ex.getMessage());
        }

        //-----------------------------------------------------------
        // second get should return null value (only one row in file)
        //-----------------------------------------------------------
        try {
            managedItemCapability = getNationalCapability(ManagedItemCapability.class);
            OrderableItemVo oiVo =
                    (OrderableItemVo) managedItemCapability.retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);

            OrderableItemVo oi = oiFile.getNextOi(oiVo);
            assertEquals(ERROR_MSG, null, oi);
        } catch (MigrationException ex) {
            fail("getNextOi should not throw a MigrationException");
        }
        
        try {
            oiFile.closeImport();
        } catch (MigrationException me) {
            fail("Failed closing OI file for import : " + me.getMessage());
        }

    } //end testImport

    /**
     * Called by testImport
     * @param oi OrderableItemVo
     * @param vadfs DataFields
     * @return OrderableItemVo
     * 
     */ 
    private OrderableItemVo testImport2(OrderableItemVo oi,  DataFields<DataField> vadfs) {
    
        //OiIvFlag field
        DataField<Boolean> ivFlag = vadfs.getDataField(FieldKey.OI_IV_FLAG);
        
        if (ivFlag.getValue()) {
            assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_OI_IV_FLAG], "Y");
        } else {
            assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_OI_IV_FLAG], "N");
        }

        //DaysOrDoseLimit field
        DataField<String> dLimit = vadfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);
        
        if (OI_TEST_DATA[OiCsvFile.I_DOD_LIMIT].length() > 0) {
            assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_DOD_LIMIT], dLimit.getValue());
        } else {
            assertEquals(ERROR_MSG, null, dLimit.getValue());
        }

        //Synonym multiple field (optional)
        if (OI_TEST_DATA[OiCsvFile.I_SYNONYM].length() > 0) {
            MultitextDataField<String> synonyms = vadfs.getDataField(FieldKey.ORDERABLE_ITEM_SYNONYM);
            String synonymStr = "";
            List<String> synonymArray = synonyms.getValue();
            
            for (String synonym : synonymArray) {
                if (synonymStr.length() > 0) {
                    synonymStr = synonymStr.concat(OiCsvFile.FIELD_MULTIPLE_SEPARATOR);
                }
                
                synonymStr = synonymStr.concat(synonym);
            }
            
            assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_SYNONYM], synonymStr);
        }

        //Drug Text field (multiple)
        if (OI_TEST_DATA[OiCsvFile.I_DRUG_TEXT].length() > 0) {
            String drugTextStr = "";
            List<DrugTextVo> dTextArray = oi.getNationalDrugTexts();
            
            for (DrugTextVo drugText : dTextArray) {
                if (drugTextStr.length() > 0) {
                    drugTextStr = drugTextStr.concat(OiCsvFile.FIELD_MULTIPLE_SEPARATOR);
                }
                
                drugTextStr = drugTextStr.concat(drugText.getValue());
            }
            
            assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_DRUG_TEXT], drugTextStr);
        }
        
        //RequestRejectReason field (optional)
        if (OI_TEST_DATA[OiCsvFile.I_REQ_REQ_REASON].length() > 0) {
            assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_REQ_REQ_REASON], oi.getRequestRejectionReason().toString());
        }

        //RejectReasonText field
        if (OI_TEST_DATA[OiCsvFile.I_REQ_REQ_TEXT].length() > 0) {
            assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_REQ_REQ_TEXT], oi.getRejectionReasonText());
        }

        //PatientInstructions field 
        if (OI_TEST_DATA[OiCsvFile.I_PAT_INSTR].length() > 0) {
            DataField<String> patientInstructions = vadfs.getDataField(FieldKey.PATIENT_INSTRUCTIONS);
            assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_PAT_INSTR], patientInstructions.getValue());
        }

        //OtherLanguageInstructions field 
        if (OI_TEST_DATA[OiCsvFile.I_OTHER_INSTR].length() > 0) {
            DataField<String> otherInstructions = vadfs.getDataField(FieldKey.OTHER_LANGUAGE_INSTRUCTIONS);
            assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_OTHER_INSTR], otherInstructions.getValue());
        }

        //StandardMedRoute field (optional)
        if (OI_TEST_DATA[OiCsvFile.I_STD_MED_ROUTE].length() > 0) {
            assertEquals(ERROR_MSG, OI_TEST_DATA[OiCsvFile.I_STD_MED_ROUTE], oi.getStandardMedRoute().getValue());
        }
        
        return oi;
    }
}
