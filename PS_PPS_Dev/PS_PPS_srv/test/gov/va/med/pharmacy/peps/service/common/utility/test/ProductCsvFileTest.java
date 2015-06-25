/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.test;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.domain.common.capability.test.DomainCapabilityTestCase;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.utility.ProductCsvFile;


/**
 * Unit test for ProdictCsvFile subclass and CsvFile superclass
 * This unit test reads and writes files to the file system
 * 
 * Methods/tests defined in this class are:
 * 1. generateProductVo - helper method to create a test ProductVo
 * 2. getNextProduct - get next Product from file, returns Product as a ProductVo object
 * 3. openForExport - open export and write header line
 * 4. putNextProduct - put next Product to file, input is a ProductVo object
 */
public class ProductCsvFileTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(ProductCsvFileTest.class);

    private static String USERDIR = "user.dir";
    private static String ERROR_MSG = "Fields do not match!";
    private static String FORMULARY = "FORMULARY";
    private static String REFRIGERATE = "REFRIGERATE";
    private static String DO_NOT_REFRIGERATE = "DO NOT REFRIGERATE";

    private static String[] PROD_TEST_DATA = { "Test_ProductName", //ProductName (required)
        "OI Name", // Oi Name
        "20111212", //PropInactivationDate (optional)

        //SynonymMult(name,ndcode,vendor,vsn,use,ordunit,ordunit$,dispunit,dispunit$)
        "synonym1::10::abc::123::use::BOX::1.0::10.0::2.0",
        "N", //WitnessReq (required)
        "Y", //PatientSpecificLabel (required)
        "N", //ProtectFromLight (required)
        "N", //DoNotHandleIfPregnant (required)
        "Y", //FollowUpTime (required)
        "N", //HazardousToDispose (required)
        "Y", //HazardousToHandle (required)
        "N", //HazardousToPatient (required)
        "Y", //LongTermOutOfStock (required)
        "N", //LowSafetyMargin (required)
        "Y", //NonRenewable (required)
        "N", //LabMonitorMark (required)
        FORMULARY, //Formulary (required)
        "Test_NcpdpDispUnit".toUpperCase(), //NcpdpDispenseUnit (required)
        "Test_NQM".toUpperCase(), //NcpdpQtyMultiplier (required)
        "YES", //ApprovedForSplitting
        "Y", //DoNotMail
        "1", //AwWsAmisCategory
        "10", //AwWsAmisConvertNumber
        "2", //DawCode
        "12D", //DayOrDoseLimit
        "255", //DispDaysSupplyLimit
        "Test_DispenseLimitForOrder".toUpperCase(), //DispLimitForOrder
        "Test_DispenseLimitForSchedule".toUpperCase(), //DispLimitForSchedule
        "Test_DispenseOverride".toUpperCase(), //DispLimitOverride
        "Test_DispenseOverrideReason".toUpperCase(), //DispLimitOverrideReason
        "15", //DispQtyLimitDose
        "16", //DispQtyLimitTime
        "Test_DispenseQtyOverride".toUpperCase(), //DispQtyOverride
        "Test_DispenseQtyOverrideReason".toUpperCase(), //DispQtyOverrideReason
        "98.0", //DispUnitPerOrderUnit
        "Test_Fsn".toUpperCase(), //FsnNsn
        "Y", //HighRiskMed
        "N", //HighRiskFollowup
        "Followup time = 10 days", //HighRiskFollowupTime
        "Expire max time = 12 months", //InpatMedExpOrdMaxTime
        "Expire min time = 1 month", //InpatMedExpOrdMinTime
        "Disp limit = 500", //MaxDispenseLimit
        "50", //MaxDosePerDay
        "Rx Message Test".toUpperCase(), //RxMessage
        "23", //MonitorMaxDays
        "BX", //OrderUnit abbreviation
        "Darth".toUpperCase(), //DispOverrideReasonBy
        "Qty Message Test".toUpperCase(), //QtyDispMessage
        RequestRejectionReason.ITEM_EXISTS.toString(), //RequestRejectReason
        "Test_RejectReasonText".toUpperCase(), //RejectReasonText
        "Drug1::D1||Drug2::D2".toUpperCase(), //SecondaryDrugClass
        "Total_Disp_Qty_Test.toUpperCase()", //TotalDispenseQty
        "UnitDoseSchedule_Test".toUpperCase(), //UnitDoseSchedule
        "O", //UnitDoseScheduleType
        "Y", //Refrigerate
        "Default Mail Service Test".toUpperCase(), //Default Mail Service
        "1", //RecallLevel
        "Tallman Test".toUpperCase(), //TallmanLettering
        "8", //DeaSchedule
        "daily", //MonitorRoutine
        "DrugText1||DrugText2".toUpperCase(), //DrugText(multiples must be sorted to pass test)
        "35", //NumberOfDoses
    };

    // this test file should already exist (delivered to stream)
    private String testFilePathIn = System.getProperty(USERDIR).concat("/etc/csv/test/");
    private String badHeaderFilename = testFilePathIn.concat("ProductCsvFile_TestBadHeader.csv");

    // this test file is created during the unit test
    private String testFilePathOut = System.getProperty(USERDIR).concat("/tmp/");
    private String testProductFilename = testFilePathOut.concat("ProductCsvFile_Test.csv");

    // this file does not exist
    private String doesNotExistFilename = testFilePathIn.concat("ProductCsvFile_DoesNotExist.csv");

    private DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);

    // blank product vo used as initial vo
    private ManagedItemCapability managedItemCapability;
    private ProductVo blankProdVo;

    /**
     * initialize the ProductVo blank template
     */
    @Override
    protected void setUp() {
        LOG.info("---------- " + getName() + " ----------");

        managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        blankProdVo = (ProductVo) managedItemCapability.retrieveBlankTemplate(EntityType.PRODUCT);
    }

    /**
     * create and initialize an ProductVo to test export and import methods
     * 
     * @return ProductVo 
     * @throws ParseException  
     * 
     */
    private ProductVo generateProductVo() throws ParseException {

        ProductVo prodVo = blankProdVo;
        DataFields<DataField> vadfs = prodVo.getVaDataFields();

        // disable lenient parsing of dates
        df.setLenient(false);

        //VaProductName field
        prodVo.setVaProductName(PROD_TEST_DATA[ProductCsvFile.PROD_NAME]);

        OrderableItemVo oiVo = new OrderableItemVo();
        oiVo.setOiName(PROD_TEST_DATA[ProductCsvFile.OI_NAME]);
        prodVo.setOrderableItem(oiVo);

        //PropInactivationDate field
        //store date in prodVo variable
        DataField<Date> proposedInactivationDate = vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);

        if (PROD_TEST_DATA[ProductCsvFile.PROP_INACT_DATE].equals("")) {
            proposedInactivationDate.selectValue(null);
        } else {
            Date piDate = df.parse(PROD_TEST_DATA[ProductCsvFile.PROP_INACT_DATE]);
            proposedInactivationDate.selectValue(piDate);
        }

        prodVo.getVaDataFields().setDataField(proposedInactivationDate);

        //SynonymMultiple field 
        //synonyms composed of (name,ndcode,vendor,vsn,use,orderunit,orderunit$,dispunit,dispunit$)
        if (PROD_TEST_DATA[ProductCsvFile.SYNONYM_MULT].length() > 0) {
            Collection<SynonymVo> synonyms = new ArrayList<SynonymVo>();

            // split multiple field into ingredient strings
            String[] synMultiple =
                PROD_TEST_DATA[ProductCsvFile.SYNONYM_MULT].split(ProductCsvFile.FIELD_MULTIPLE_SEPARATOR_SPLIT);

            //iterate on each synonym string containing 9 values
            for (String oneSyn : synMultiple) {
                SynonymVo synonym = new SynonymVo();

                // split into synonym values, must be nine
                String[] synValues = oneSyn.split(ProductCsvFile.MULTIPLE_VALUE_SEPARATOR_SPLIT);

                if (synValues.length == PPSConstants.I9) {

                    // set the name, unit, and strength
                    synonym.setSynonymName(synValues[0]);
                    synonym.setNdcCode(synValues[1]);
                    synonym.setSynonymVendor(synValues[2]);
                    synonym.setSynonymVsn(synValues[PPSConstants.I3]);
                    IntendedUseVo intendedUse = new IntendedUseVo();
                    intendedUse.setIntendedUse(synValues[PPSConstants.I4]);
                    synonym.setSynonymIntendedUse(intendedUse);
                    
                    // Set the orderunit
                    OrderUnitVo orderUnit = new OrderUnitVo();
                    orderUnit.setAbbrev(synValues[PPSConstants.I5]);
                    synonym.setSynonymOrderUnit(orderUnit);

                    // try setting the price fields
                    try {
                        Double price = Double.parseDouble(synValues[PPSConstants.I6]);
                        synonym.setSynonymPricePerOrderUnit(price);
                        price = Double.parseDouble(synValues[PPSConstants.I7]);
                        synonym.setSynonymDispenseUnitPerOrderUnit(price);
                        price = Double.parseDouble(synValues[PPSConstants.I8]);
                        synonym.setSynonymPricePerDispenseUnit(price);
                        synonyms.add(synonym);
                    } catch (NumberFormatException ex) {
                        fail("Should have been able to parse the numbers!" + ex.getMessage());
                    }
                } else {
                    fail("generateProductVo: invalid test data for Synonym multiple");
                }
            }

            prodVo.setSynonyms(synonyms);
        }

        //WitnessRequired field
        //store Y/N value in boolean data field
        DataField<Boolean> witness = vadfs.getDataField(FieldKey.WITNESS_TO_ADMINISTRATION);

        if (PROD_TEST_DATA[ProductCsvFile.WITNESS_REQ].equals("Y")) {
            witness.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.WITNESS_REQ].equals("N")) {
            witness.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for WitnessRequired field");
        }

        prodVo.getVaDataFields().setDataField(witness);

        //PatientSpecificLabel field
        DataField<Boolean> pslabel = vadfs.getDataField(FieldKey.PATIENT_SPECIFIC_LABEL);

        if (PROD_TEST_DATA[ProductCsvFile.PAT_SPEC_LABEL].equals("Y")) {
            pslabel.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.PAT_SPEC_LABEL].equals("N")) {
            pslabel.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for PatientSpecificLabel field");
        }

        prodVo.getVaDataFields().setDataField(pslabel);


        
        return generateProductVo2(prodVo, vadfs);
    }
    
    /**
     * generateProductVo2
     * @param prodVo prodVo
     * @param vadfs vadfs
     * @return ProductVo
     */
    private ProductVo generateProductVo2(ProductVo prodVo, DataFields<DataField> vadfs) {
        
        //ProtectFromLight field
        DataField<Boolean> protect = vadfs.getDataField(FieldKey.PROTECT_FROM_LIGHT);

        if (PROD_TEST_DATA[ProductCsvFile.IPROTECT].equals("Y")) {
            protect.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.IPROTECT].equals("N")) {
            protect.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for ProtectFromLight field");
        }
        
        prodVo.getVaDataFields().setDataField(protect);

        //DoNotHandleIfPregnant field
        DataField<Boolean> pregnant = vadfs.getDataField(FieldKey.DO_NOT_HANDLE_IF_PREGNANT);

        if (PROD_TEST_DATA[ProductCsvFile.IPREGNANT].equals("Y")) {
            pregnant.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.IPREGNANT].equals("N")) {
            pregnant.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for DoNotHandleIfPregnant field");
        }

        prodVo.getVaDataFields().setDataField(pregnant);

        //FollowUpTime field
        DataField<Boolean> followup = vadfs.getDataField(FieldKey.FOLLOW_UP_TIME);

        if (PROD_TEST_DATA[ProductCsvFile.IFOLLOWUP].equals("Y")) {
            followup.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.IFOLLOWUP].equals("N")) {
            followup.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for FollowUpTime field");
        }

        prodVo.getVaDataFields().setDataField(followup);

        //HazardousToDispose field
        DataField<Boolean> hazardDisp = vadfs.getDataField(FieldKey.HAZARDOUS_TO_DISPOSE);

        if (PROD_TEST_DATA[ProductCsvFile.IHAZARDTODISP].equals("Y")) {
            hazardDisp.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.IHAZARDTODISP].equals("N")) {
            hazardDisp.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for HazardousToDispose field");
        }

        prodVo.getVaDataFields().setDataField(hazardDisp);

        //HazardousToHandle field
        DataField<Boolean> hazardHand = vadfs.getDataField(FieldKey.HAZARDOUS_TO_HANDLE);

        if (PROD_TEST_DATA[ProductCsvFile.IHAZARDTOHAND].equals("Y")) {
            hazardHand.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.IHAZARDTOHAND].equals("N")) {
            hazardHand.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for HazardousToHandle field");
        }

        prodVo.getVaDataFields().setDataField(hazardHand);

        //HazardousToPatient field
        DataField<Boolean> hazardPat = vadfs.getDataField(FieldKey.HAZARDOUS_TO_PATIENT);

        if (PROD_TEST_DATA[ProductCsvFile.IHAZARDTOPAT].equals("Y")) {
            hazardPat.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.IHAZARDTOPAT].equals("N")) {
            hazardPat.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for HazardousToPatient field");
        }

        prodVo.getVaDataFields().setDataField(hazardPat);

        //LongTermOutOfStock field
        DataField<Boolean> longterm = vadfs.getDataField(FieldKey.LONG_TERM_OUT_OF_STOCK);

        if (PROD_TEST_DATA[ProductCsvFile.ILONGTERM].equals("Y")) {
            longterm.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.ILONGTERM].equals("N")) {
            longterm.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for LongTermOutOfStock field");
        }

        prodVo.getVaDataFields().setDataField(longterm);

        //LowSafetyMargin field
        //use Y/N string to set boolean data field
        DataField<Boolean> lowmargin = vadfs.getDataField(FieldKey.LOW_SAFETY_MARGIN);

        if (PROD_TEST_DATA[ProductCsvFile.ILOWMARGIN].equals("Y")) {
            lowmargin.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.ILOWMARGIN].equals("N")) {
            lowmargin.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for LowSafetyMargin field");
        }

        prodVo.getVaDataFields().setDataField(lowmargin);

        //NonRenewable field
        DataField<Boolean> nonrenew = vadfs.getDataField(FieldKey.NON_RENEWABLE);

        if (PROD_TEST_DATA[ProductCsvFile.INONRENEW].equals("Y")) {
            nonrenew.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.INONRENEW].equals("N")) {
            nonrenew.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for NonRenewable field");
        }

        prodVo.getVaDataFields().setDataField(nonrenew);

        //LabMonitorMark field
        DataField<Boolean> labmonitor = vadfs.getDataField(FieldKey.LAB_MONITOR_MARK);

        if (PROD_TEST_DATA[ProductCsvFile.ILABMONITOR].equals("Y")) {
            labmonitor.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.ILABMONITOR].equals("N")) {
            labmonitor.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for LabMonitorMark field");
        }

        prodVo.getVaDataFields().setDataField(labmonitor);
        
        return generateProductVo3(prodVo, vadfs);
    }
    
    /**
     * generateProductVo3
     * @param prodVo prodVo
     * @param vadfs vadfs
     * @return ProductVo
     */
    private ProductVo generateProductVo3(ProductVo prodVo, DataFields<DataField> vadfs) {
        
        
        //Formulary field
        if (PROD_TEST_DATA[ProductCsvFile.IFORMULARY].length() > 0) {
            ListDataField<String> formulary = vadfs.getDataField(FieldKey.FORMULARY);
            formulary.addStringSelection(PROD_TEST_DATA[ProductCsvFile.IFORMULARY]);
            prodVo.getVaDataFields().setDataField(formulary);
        }

        //NcpcpDispenseUnit field
        if (PROD_TEST_DATA[ProductCsvFile.INCPDPDISP].length() > 0) {
            ListDataField<String> ndu = vadfs.getDataField(FieldKey.NCPDP_DISPENSE_UNIT);
            ndu.removeSelection("EA-EACH"); // remove default
            ndu.addStringSelection(PROD_TEST_DATA[ProductCsvFile.INCPDPDISP]);
            prodVo.getVaDataFields().setDataField(ndu);
        }

        //NcpcpQtyMultiplier field
        if (PROD_TEST_DATA[ProductCsvFile.INCPDPQTYMULT].length() > 0) {
            DataField<String> nqm = vadfs.getDataField(FieldKey.NDCDP_QUANTITY_MULTIPLIER);
            nqm.selectValue(PROD_TEST_DATA[ProductCsvFile.INCPDPQTYMULT]);
            prodVo.getVaDataFields().setDataField(nqm);
        }

        //ApprovedForSplitting field
        if (PROD_TEST_DATA[ProductCsvFile.IAPPFORSPLIT].length() > 0) {
            ListDataField<String> split = vadfs.getDataField(FieldKey.APPROVED_FOR_SPLITTING);
            split.addStringSelection(PROD_TEST_DATA[ProductCsvFile.IAPPFORSPLIT]);
            prodVo.getVaDataFields().setDataField(split);
        }

        //DoNotMail field
        DataField<Boolean> dontmail = vadfs.getDataField(FieldKey.DO_NOT_MAIL);

        if (PROD_TEST_DATA[ProductCsvFile.IDONOTMAIL].equals("Y")) {
            dontmail.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.IDONOTMAIL].equals("N")) {
            dontmail.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for DoNotMail field");
        }

        prodVo.getVaDataFields().setDataField(dontmail);

        //ArWsAmisCatagory field
        if (PROD_TEST_DATA[ProductCsvFile.IARWSAMISCAT].length() > 0) {
            ListDataField<String> catagory = vadfs.getDataField(FieldKey.AR_WS_AMIS_CATEGORY);
            catagory.addStringSelection(PROD_TEST_DATA[ProductCsvFile.IARWSAMISCAT]);
            prodVo.getVaDataFields().setDataField(catagory);
        }

        //ArWsAmisConvertNumber field
        //store string in long data field
        if (PROD_TEST_DATA[ProductCsvFile.IARWSAMISNUM].length() > 0) {
            DataField<Long> cnum = vadfs.getDataField(FieldKey.AR_WS_CONVERSION_NUMBER);

            try {
                Long.parseLong(PROD_TEST_DATA[ProductCsvFile.IARWSAMISNUM]);
                cnum.selectStringValue(PROD_TEST_DATA[ProductCsvFile.IARWSAMISNUM]);
            } catch (NumberFormatException ex) {
                fail("generateProductVo: invalid test data for ArWsAmisConvertNumber field");
            }

            prodVo.getVaDataFields().setDataField(cnum);
        }

        //DawCode field
        //store string in list data field, not a multiple
        if (PROD_TEST_DATA[ProductCsvFile.IDAWCODE].length() > 0) {
            ListDataField<String> dcode = vadfs.getDataField(FieldKey.DAW_CODE);
            dcode.removeSelection("0-NO PRODUCT SELECTION INDICATED"); // remove default
            dcode.addStringSelection(PROD_TEST_DATA[ProductCsvFile.IDAWCODE]);
            prodVo.getVaDataFields().setDataField(dcode);
        }

        //DaysOrDoseLimit field
        if (PROD_TEST_DATA[ProductCsvFile.IDODLIMIT].length() > 0) {
            DataField<String> dlimit = vadfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);
            dlimit.selectValue(PROD_TEST_DATA[ProductCsvFile.IDODLIMIT]);
            prodVo.getVaDataFields().setDataField(dlimit);
        }

        //DispDaysSupplyLimit field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPSUPPLIMIT].length() > 0) {
            DataField<Long> slimit = vadfs.getDataField(FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT);

            try {
                Long.parseLong(PROD_TEST_DATA[ProductCsvFile.IDISPSUPPLIMIT]);
                slimit.selectStringValue(PROD_TEST_DATA[ProductCsvFile.IDISPSUPPLIMIT]);
            } catch (NumberFormatException ex) {
                fail("generateProductVo: invalid test data for DispDaysSupplyLimit field");
            }

            prodVo.getVaDataFields().setDataField(slimit);
        }

        //DispLimitForOrder
        if (PROD_TEST_DATA[ProductCsvFile.IDISPLIMITORD].length() > 0) {
            DataField<String> olimit = vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_ORDER);
            olimit.selectValue(PROD_TEST_DATA[ProductCsvFile.IDISPLIMITORD]);
            prodVo.getVaDataFields().setDataField(olimit);
        }

        //DispLimitForSchedule
        if (PROD_TEST_DATA[ProductCsvFile.IDISPLIMITSCH].length() > 0) {
            DataField<String> dlsch = vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_SCHEDULE);
            dlsch.selectValue(PROD_TEST_DATA[ProductCsvFile.IDISPLIMITSCH]);
            prodVo.getVaDataFields().setDataField(dlsch);
        }

        //DispOverride
        if (PROD_TEST_DATA[ProductCsvFile.IDISPORIDE].length() > 0) {
            DataField<String> doride = vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE);
            doride.selectValue(PROD_TEST_DATA[ProductCsvFile.IDISPORIDE]);
            prodVo.getVaDataFields().setDataField(doride);
        }

        //DispOverrideReason
        if (PROD_TEST_DATA[ProductCsvFile.IDISPORIDEREASON].length() > 0) {
            DataField<String> doreason = vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE_REASON);
            doreason.selectValue(PROD_TEST_DATA[ProductCsvFile.IDISPORIDEREASON]);
            prodVo.getVaDataFields().setDataField(doreason);
        }

        return generateProductVo4(prodVo, vadfs);
    }
    
    /**
     * generateProductVo4
     * @param prodVo prodVo
     * @param vadfs vadfs
     * @return ProductVo
     */
    private ProductVo generateProductVo4(ProductVo prodVo, DataFields<DataField> vadfs) {
        
        
        //DispenseQtyLimitDose field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPQTYLIMITDOSE].length() > 0) {
            GroupDataField dqlimit = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_LIMIT);
            DataField<Long> dqlimitdose = dqlimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_DOSE);

            try {
                Long.parseLong(PROD_TEST_DATA[ProductCsvFile.IDISPQTYLIMITDOSE]);
                dqlimitdose.selectStringValue(PROD_TEST_DATA[ProductCsvFile.IDISPQTYLIMITDOSE]);
            } catch (NumberFormatException ex) {
                fail("generateProductVo: invalid test data for DispenseQtyLimitDose field");
            }

            prodVo.getVaDataFields().setDataField(dqlimit);
        }

        //DispenseQtyLimitTime field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPQTYLIMITTIME].length() > 0) {
            GroupDataField dqlimit = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_LIMIT);
            DataField<Long> dqlimittime = dqlimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_TIME);

            try {
                Long.parseLong(PROD_TEST_DATA[ProductCsvFile.IDISPQTYLIMITTIME]);
                dqlimittime.selectStringValue(PROD_TEST_DATA[ProductCsvFile.IDISPQTYLIMITTIME]);
            } catch (NumberFormatException ex) {
                fail("generateProductVo: invalid test data for DispenseQtyLimitTime field");
            }

            prodVo.getVaDataFields().setDataField(dqlimit);
        }

        //DispQtyOverride
        //store string in string data field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPQTYORIDE].length() > 0) {
            DataField<String> dqoride = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE);
            dqoride.selectValue(PROD_TEST_DATA[ProductCsvFile.IDISPQTYORIDE]);
            prodVo.getVaDataFields().setDataField(dqoride);
        }

        //DispQtyOverrideReason field
        //store string in string data field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPQTYORIDEREASON].length() > 0) {
            DataField<String> dqoreason = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE_REASON);
            dqoreason.selectValue(PROD_TEST_DATA[ProductCsvFile.IDISPQTYORIDEREASON]);
            prodVo.getVaDataFields().setDataField(dqoreason);
        }

        //DispenseUnitsPerOrderUnit field
        //store string in double data field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPLUNITORDUNIT].length() > 0) {
            DataField<Double> dunits = vadfs.getDataField(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);

            try {
                Double.parseDouble(PROD_TEST_DATA[ProductCsvFile.IDISPLUNITORDUNIT]);
                dunits.selectStringValue(PROD_TEST_DATA[ProductCsvFile.IDISPLUNITORDUNIT]);
            } catch (NumberFormatException ex) {
                fail("generateProductVo: invalid test data for DispenseUnitsPerOrderUnit field");
            }

            prodVo.getVaDataFields().setDataField(dunits);
        }

        //FsnNsn field
        //store string in string data field
        if (PROD_TEST_DATA[ProductCsvFile.IFSNNSN].length() > 0) {
            DataField<String> fsn = vadfs.getDataField(FieldKey.FSN);
            fsn.selectValue(PROD_TEST_DATA[ProductCsvFile.IFSNNSN]);
            prodVo.getVaDataFields().setDataField(fsn);
        }

        //HighRiskMed field
        DataField<Boolean> hirisk = vadfs.getDataField(FieldKey.HIGH_RISK);

        if (PROD_TEST_DATA[ProductCsvFile.IHIRISK].equals("Y")) {
            hirisk.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.IHIRISK].equals("N")) {
            hirisk.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for HighRiskMed field");
        }

        prodVo.getVaDataFields().setDataField(hirisk);

        //HighRiskFollowup field
        DataField<Boolean> hiriskfu = vadfs.getDataField(FieldKey.HIGH_RISK_FOLLOWUP);

        if (PROD_TEST_DATA[ProductCsvFile.IHIRISKFOLLOWUP].equals("Y")) {
            hiriskfu.selectValue(true);
        } else if (PROD_TEST_DATA[ProductCsvFile.IHIRISKFOLLOWUP].equals("N")) {
            hiriskfu.selectValue(false);
        } else {
            fail("generateProductVo: invalid test data for HighRiskFollowup field");
        }

        prodVo.getVaDataFields().setDataField(hiriskfu);

        //HighRiskFollowupTime
        if (PROD_TEST_DATA[ProductCsvFile.IHIRISKFUTIME].length() > 0) {
            DataField<String> hirisktime = vadfs.getDataField(FieldKey.HIGH_RISK_FOLLOWUP_TIME_PERIOD);
            hirisktime.selectValue(PROD_TEST_DATA[ProductCsvFile.IHIRISKFUTIME]);
            prodVo.getVaDataFields().setDataField(hirisktime);
        }

        //InpatMedExpOrdMaxTime field
        //store string in string data field
        if (PROD_TEST_DATA[ProductCsvFile.IINPMEDEXPMAXTIME].length() > 0) {
            DataField<String> maxtime = vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME);
            maxtime.selectValue(PROD_TEST_DATA[ProductCsvFile.IINPMEDEXPMAXTIME]);
            prodVo.getVaDataFields().setDataField(maxtime);
        }

        return generateProductVo5(prodVo, vadfs);
    }
    
    /**
     * generateProductVo5
     * @param prodVo prodVo
     * @param vadfs vadfs
     * @return ProductVo
     */
    private ProductVo generateProductVo5(ProductVo prodVo, DataFields<DataField> vadfs) {
        
        //InpatMedExpOrdMinTime field
        //store string in string data field
        if (PROD_TEST_DATA[ProductCsvFile.IINPMEDEXPMINTIME].length() > 0) {
            DataField<String> mintime = vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME);
            mintime.selectValue(PROD_TEST_DATA[ProductCsvFile.IINPMEDEXPMINTIME]);
            prodVo.getVaDataFields().setDataField(mintime);
        }

        //MaxDispenseLimit field
        if (PROD_TEST_DATA[ProductCsvFile.IMAXDISPLIMIT].length() > 0) {
            DataField<String> mdlimit = vadfs.getDataField(FieldKey.MAX_DISPENSE_LIMIT);
            mdlimit.selectValue(PROD_TEST_DATA[ProductCsvFile.IMAXDISPLIMIT]);
            prodVo.getVaDataFields().setDataField(mdlimit);
        }

        //MaxDosePerDay field
        if (PROD_TEST_DATA[ProductCsvFile.IMAXDOSDAY].length() > 0) {
            try {
                Long maxdose = Long.parseLong(PROD_TEST_DATA[ProductCsvFile.IMAXDOSDAY]);
                prodVo.setMaxDosePerDay(maxdose);
            } catch (NumberFormatException ex) {
                fail("generateProductVo: invalid test data for MaxDosePerDay field");
            }
        }

        //RxMessage field
        if (PROD_TEST_DATA[ProductCsvFile.IRXMESSAGE].length() > 0) {
            DataField<String> rxmsg = vadfs.getDataField(FieldKey.MESSAGE);
            rxmsg.selectValue(PROD_TEST_DATA[ProductCsvFile.IRXMESSAGE]);
            prodVo.getVaDataFields().setDataField(rxmsg);
        }

        //MonitorMaxDays field
        //store string in long data field
        if (PROD_TEST_DATA[ProductCsvFile.IMONMAXDAYS].length() > 0) {
            DataField<Long> mmdays = vadfs.getDataField(FieldKey.MONITOR_MAX_DAYS);

            try {
                Long.parseLong(PROD_TEST_DATA[ProductCsvFile.IMONMAXDAYS]);
                mmdays.selectStringValue(PROD_TEST_DATA[ProductCsvFile.IMONMAXDAYS]);
            } catch (NumberFormatException ex) {
                fail("generateProductVo: invalid test data for MonitorMaxDays field");
            }

            prodVo.getVaDataFields().setDataField(mmdays);
        }

        //OrderUnit field
        if (PROD_TEST_DATA[ProductCsvFile.IORDERUNIT].length() > 0) {
            OrderUnitVo orderUnit = new OrderUnitVo();

            // only abbrev is set, service completes vo as needed 
            orderUnit.setAbbrev(PROD_TEST_DATA[ProductCsvFile.IORDERUNIT]);
            prodVo.setOrderUnit(orderUnit);
        }

        //DispOverrideReasonBy field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPORIDEREASONBY].length() > 0) {
            DataField<String> dorby = vadfs.getDataField(FieldKey.OVERRIDE_REASON_ENTERER);
            dorby.selectValue(PROD_TEST_DATA[ProductCsvFile.IDISPORIDEREASONBY]);
            prodVo.getVaDataFields().setDataField(dorby);
        }

        //QtyDispMesssage field
        if (PROD_TEST_DATA[ProductCsvFile.IQTYDISPMSG].length() > 0) {
            DataField<String> qdmsg = vadfs.getDataField(FieldKey.QUANTITY_DISPENSE_MESSAGE);
            qdmsg.selectValue(PROD_TEST_DATA[ProductCsvFile.IQTYDISPMSG]);
            prodVo.getVaDataFields().setDataField(qdmsg);
        }

        //RequestRejectReason field
        if (PROD_TEST_DATA[ProductCsvFile.IREQREJREASON].length() > 0) {
            Boolean found = false;

            for (RequestRejectionReason reason : RequestRejectionReason.values()) {
                if (PROD_TEST_DATA[ProductCsvFile.IREQREJREASON].equals(reason.toString())) {
                    prodVo.setRequestRejectionReason(reason);
                    found = true;
                }
            }

            if (!found) {
                fail("generateProductVo: invalid test data for RequestRejectReason field");
            }
        }

        return generateProductVo6(prodVo, vadfs);
    }
    
    /**
     * generateProductVo6
     * @param prodVo prodVo
     * @param vadfs vadfs
     * @return ProductVo
     */
    private ProductVo generateProductVo6(ProductVo prodVo, DataFields<DataField> vadfs) {
        
        //RejectReasonText field
        if (PROD_TEST_DATA[ProductCsvFile.IREJREASONTEXT].length() > 0) {
            prodVo.setRejectionReasonText(PROD_TEST_DATA[ProductCsvFile.IREJREASONTEXT]);
        }

        //SecondaryDrugClass
        Collection<DrugClassGroupVo> drugs = new ArrayList<DrugClassGroupVo>();

        // split multiple field into drugs strings
        String[] drugMultiple =
            PROD_TEST_DATA[ProductCsvFile.ISECONDARYDRUGS].split(ProductCsvFile.FIELD_MULTIPLE_SEPARATOR_SPLIT);

        //iterate on each drug string containing name,code
        for (String oneDrug : drugMultiple) {
            DrugClassGroupVo drug = new DrugClassGroupVo();
            DrugClassVo drugClass = new DrugClassVo();

            // split into drug values, must be two
            String[] drugValues = oneDrug.split(ProductCsvFile.MULTIPLE_VALUE_SEPARATOR_SPLIT);

            if (drugValues.length == 2) {

                // set the class and code and primary flag
                drugClass.setClassification(drugValues[0]);
                drugClass.setCode(drugValues[1]);
                drug.setDrugClass(drugClass);
                drug.setPrimary(false);
                drugs.add(drug);
            } else {
                fail("generateProductVo: invalid test data for SecondaryDrugClass field");
            }

        }

        prodVo.setDrugClasses(drugs);

        //TotalDispenseQty field
        if (PROD_TEST_DATA[ProductCsvFile.ITOTALDISPQTY].length() > 0) {
            DataField<String> tdqty = vadfs.getDataField(FieldKey.TOTAL_DISPENSE_QUANTITY);
            tdqty.selectValue(PROD_TEST_DATA[ProductCsvFile.ITOTALDISPQTY]);
            prodVo.getVaDataFields().setDataField(tdqty);
        }

        //UnitDoseSchedule field
        if (PROD_TEST_DATA[ProductCsvFile.IUNITDOSESCH].length() > 0) {
            DataField<String> udsch = vadfs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE);
            udsch.selectValue(PROD_TEST_DATA[ProductCsvFile.IUNITDOSESCH]);
            prodVo.getVaDataFields().setDataField(udsch);
        }

        //UnitDoseScheduleType field
        //store string in list data field
        if (PROD_TEST_DATA[ProductCsvFile.IUNITDOSESCHTYPE].length() > 0) {
            ListDataField<String> udstype = vadfs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE_TYPE);
            udstype.addStringSelection(PROD_TEST_DATA[ProductCsvFile.IUNITDOSESCHTYPE]);
            prodVo.getVaDataFields().setDataField(udstype);
        }

        // Refrigeration field
        if (PROD_TEST_DATA[ProductCsvFile.IREFRIG].length() > 0) {
            if (PROD_TEST_DATA[ProductCsvFile.IREFRIG].equals("Y")) {
                vadfs.getDataField(FieldKey.REFRIGERATION).addStringSelection(REFRIGERATE);
            } else if (PROD_TEST_DATA[ProductCsvFile.IREFRIG].equals("N")) {
                vadfs.getDataField(FieldKey.REFRIGERATION).addStringSelection(DO_NOT_REFRIGERATE);
            }
        }

        //DefaultMailService field
        if (PROD_TEST_DATA[ProductCsvFile.IDEFMAILSERV].length() > 0) {
            DataField<String> dmserv = vadfs.getDataField(FieldKey.DEFAULT_MAIL_SERVICE);
            dmserv.selectValue(PROD_TEST_DATA[ProductCsvFile.IDEFMAILSERV]);
            prodVo.getVaDataFields().setDataField(dmserv);
        }

        //RecallLevel field
        if (PROD_TEST_DATA[ProductCsvFile.IRECALLLEVEL].length() > 0) {
            ListDataField<String> rlevel = vadfs.getDataField(FieldKey.RECALL_LEVEL);
            rlevel.addStringSelection(PROD_TEST_DATA[ProductCsvFile.IRECALLLEVEL]);
            prodVo.getVaDataFields().setDataField(rlevel);
        }

        //TallmanLettering field
        if (PROD_TEST_DATA[ProductCsvFile.ITMANLETTER].length() > 0) {
            prodVo.setTallManLettering(PROD_TEST_DATA[ProductCsvFile.ITMANLETTER]);
        }

        //DeaSchedule field
        if (PROD_TEST_DATA[ProductCsvFile.IDEASCH].length() > 0) {
            ListDataField<String> dsch = vadfs.getDataField(FieldKey.DEA_SCHEDULE);
            dsch.addStringSelection(PROD_TEST_DATA[ProductCsvFile.IDEASCH]);
            prodVo.getVaDataFields().setDataField(dsch);
        }

        //MonitorRoutine field
        if (PROD_TEST_DATA[ProductCsvFile.IMONROUTINE].length() > 0) {
            DataField<String> mroutine = vadfs.getDataField(FieldKey.MONITOR_ROUTINE);
            mroutine.selectValue(PROD_TEST_DATA[ProductCsvFile.IMONROUTINE]);
            prodVo.getVaDataFields().setDataField(mroutine);
        }

        //Drug Text field (optional)
        if (PROD_TEST_DATA[ProductCsvFile.IDRUGTEXT].length() > 0) {
            String[] dTextArray = PROD_TEST_DATA[ProductCsvFile.IDRUGTEXT].split(ProductCsvFile.FIELD_MULTIPLE_SEPARATOR_SPLIT);
            List<DrugTextVo> drugTexts = new ArrayList<DrugTextVo>();

            for (String selection : dTextArray) {
                DrugTextVo drugText = new DrugTextVo();
                drugText.setValue(selection);
                drugTexts.add(drugText);
            }

            prodVo.setNationalDrugTexts(drugTexts);
        }

        //NumberOfDoses field
        if (PROD_TEST_DATA[ProductCsvFile.INOOFDOSES].length() > 0) {
            DataField<Long> nodoses = vadfs.getDataField(FieldKey.NUMBER_OF_DOSES);

            try {
                Long.parseLong(PROD_TEST_DATA[ProductCsvFile.INOOFDOSES]);
                nodoses.selectStringValue(PROD_TEST_DATA[ProductCsvFile.INOOFDOSES]);
            } catch (NumberFormatException ex) {
                fail("generateProductVo: invalid test data for NumberOfDoses field");
            }

            prodVo.getVaDataFields().setDataField(nodoses);
        }

        return prodVo;

    }

    /**
     * Use the openForExport, putNextProduct, and closeExport methods to create a
     * test Product file with one row. The exported file will be used to test the
     * import methods.
     * @throws ParseException 
     */
    public void testExport() throws ParseException {

        ProductVo testVo = generateProductVo();

        ProductCsvFile prodFile = new ProductCsvFile();

        try {
            prodFile.openForExport(testProductFilename);
            prodFile.putNextProduct(testVo);
            prodFile.closeExport();
        } catch (MigrationException ex) {
            fail("should not throw a MigrationException");
        }
    }

    /**
     * Test openForImport method, file not found exception
     *
     */
    public void testOpenForImport1() {

        ProductCsvFile prodFile = new ProductCsvFile();

        try {
            prodFile.openForImport(doesNotExistFilename);
            fail("openForImport should have raised MigrationException with file not found message");
        } catch (MigrationException ex) {
            assertTrue("Should be not found in message.", ex.getMessage().contains("not found"));
        }
    }

    /**
     * 
     * Test openForImport method, bad header exception
     */
    public void testOpenForImport2() {

        ProductCsvFile prodFile = new ProductCsvFile();

        try {
            prodFile.openForImport(badHeaderFilename);
            fail("openForImport should have raised MigrationException with bad header message");
        } catch (MigrationException ex) {
            assertEquals(ERROR_MSG, "Column header on line 1 is invalid. ", ex.getMessage());
        }
    }

    /**
     * Test openForImport, getNextProduct, and closeImport methods. Test imports
     * product file created by the testExport() test. Test validates that all file
     * fields match data in PROD_TEST_DATA array.
     * Also tests the item and item request status fields are set.
     * Also tests that null is returned at EOF.
     */
    public void testImport() {

        ProductCsvFile prodFile = new ProductCsvFile();

        try {
            prodFile.openForImport(testProductFilename);

            ProductVo prodVo = prodFile.getNextProduct(blankProdVo);
            @SuppressWarnings("rawtypes")
            DataFields<DataField> vadfs = prodVo.getVaDataFields();
            testImport1(prodVo, vadfs);
            testImport2(prodVo, vadfs);
            testImport3(prodVo, vadfs);

            //RequestRejectReason field
            if (PROD_TEST_DATA[ProductCsvFile.IREQREJREASON].length() > 0) {
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IREQREJREASON], prodVo.getRequestRejectionReason()
                    .toString());
            }

            //RejectReasonText field
            if (PROD_TEST_DATA[ProductCsvFile.IREJREASONTEXT].length() > 0) {
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IREJREASONTEXT], prodVo.getRejectionReasonText());
            }

            //SecondaryVaDrugClass
            String sdrugStr = "";
            Collection<DrugClassGroupVo> drugs = prodVo.getDrugClasses();

            // iterate for each drug in list
            for (DrugClassGroupVo drug : drugs) {
                if (sdrugStr.length() > 0) {
                    sdrugStr = sdrugStr.concat(ProductCsvFile.FIELD_MULTIPLE_SEPARATOR);
                }

                sdrugStr = sdrugStr.concat(drug.getDrugClass().getClassification());
                sdrugStr = sdrugStr.concat(ProductCsvFile.MULTIPLE_VALUE_SEPARATOR);
                sdrugStr = sdrugStr.concat(drug.getDrugClass().getCode());
            }

            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.ISECONDARYDRUGS], sdrugStr);

            //TotalDispenseQty field
            if (PROD_TEST_DATA[ProductCsvFile.ITOTALDISPQTY].length() > 0) {
                DataField<String> tdqty = vadfs.getDataField(FieldKey.TOTAL_DISPENSE_QUANTITY);
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.ITOTALDISPQTY], tdqty.getValue());
            }

            //UnitDoseSchedule field
            if (PROD_TEST_DATA[ProductCsvFile.IUNITDOSESCH].length() > 0) {
                DataField<String> udsch = vadfs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE);
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IUNITDOSESCH], udsch.getValue());
            }

            //UnitDoseScheduleType field
            //assumes a single selection
            if (PROD_TEST_DATA[ProductCsvFile.IUNITDOSESCHTYPE].length() > 0) {
                ListDataField<String> udstype = vadfs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE_TYPE);
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IUNITDOSESCHTYPE], udstype.getValue().get(0));
            }

            if (PROD_TEST_DATA[ProductCsvFile.IREFRIG].length() > 0) {
                ListDataField<String> refrigerate = vadfs.getDataField(FieldKey.REFRIGERATION);

                if (refrigerate.contains(REFRIGERATE)) {
                    assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IREFRIG], "Y");
                }

                if (refrigerate.contains(DO_NOT_REFRIGERATE)) {
                    assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IREFRIG], "N");
                }
            }

            //DefaultMailService field
            if (PROD_TEST_DATA[ProductCsvFile.IDEFMAILSERV].length() > 0) {
                DataField<String> dmserv = vadfs.getDataField(FieldKey.DEFAULT_MAIL_SERVICE);
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDEFMAILSERV], dmserv.getValue());
            }

            //RecallLevel field
            if (PROD_TEST_DATA[ProductCsvFile.IRECALLLEVEL].length() > 0) {
                ListDataField<String> rlevel = vadfs.getDataField(FieldKey.RECALL_LEVEL);
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IRECALLLEVEL], rlevel.getValue().get(0));
            }

            //TallmanLettering field
            if (PROD_TEST_DATA[ProductCsvFile.ITMANLETTER].length() > 0) {
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.ITMANLETTER], prodVo.getTallManLettering());
            }

            //DeaSchedule field
            if (PROD_TEST_DATA[ProductCsvFile.IDEASCH].length() > 0) {
                ListDataField<String> dsch = vadfs.getDataField(FieldKey.DEA_SCHEDULE);
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDEASCH], dsch.getValue().get(0));
            }

            //MonitorRoutine field
            if (PROD_TEST_DATA[ProductCsvFile.IMONROUTINE].length() > 0) {
                DataField<String> mroutine = vadfs.getDataField(FieldKey.MONITOR_ROUTINE);
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IMONROUTINE], mroutine.getValue());
            }

            //Drug Text multiple field (optional)
            if (PROD_TEST_DATA[ProductCsvFile.IDRUGTEXT].length() > 0) {
                String drugTextStr = "";
                List<DrugTextVo> dTextArray = prodVo.getNationalDrugTexts();

                for (DrugTextVo drugText : dTextArray) {
                    if (drugTextStr.length() > 0) {
                        drugTextStr = drugTextStr.concat(ProductCsvFile.FIELD_MULTIPLE_SEPARATOR);
                    }

                    drugTextStr = drugTextStr.concat(drugText.getValue());
                }

                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDRUGTEXT], drugTextStr);
            }

            //NumberOfDoses field
            if (PROD_TEST_DATA[ProductCsvFile.INOOFDOSES].length() > 0) {
                DataField<Long> nodoses = vadfs.getDataField(FieldKey.NUMBER_OF_DOSES);
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.INOOFDOSES], nodoses.getValue().toString());
            }

        } catch (MigrationException ex) {

            //-------------------------------------
            // should not get an exception first oi
            //-------------------------------------
            fail("getNextProduct should not throw MigrationException" + ex.getMessage());
        }

        //-----------------------------------------------------------
        // second get should return null value (only one row in file)
        //-----------------------------------------------------------
        try {
            ProductVo prodVo = prodFile.getNextProduct(blankProdVo);
            assertEquals(ERROR_MSG, null, prodVo);
        } catch (MigrationException ex) {
            fail("getNextProduct should not throw a MigrationException");
        }

        try {
            prodFile.closeImport();
        } catch (MigrationException me) {
            fail("Failed closing Product file for import : " + me.getMessage());
        }

    } //end testImport

    /**
     * testImport1
     * @param prodVo prodVo
     * @param vadfs vadfs
     */
    private void testImport1(ProductVo prodVo, DataFields<DataField> vadfs) {

        //VaProductName field
        assertEquals("Product Names should be equal.", PROD_TEST_DATA[ProductCsvFile.PROD_NAME].toUpperCase(Locale.US), 
            prodVo.getVaProductName());

        //ProposedInactivationDate field
        DataField<Date> proposedInactivationDate = vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);

        if (proposedInactivationDate.getValue() == null) {
            assertEquals("Inactivation Dates should be equal", PROD_TEST_DATA[ProductCsvFile.PROP_INACT_DATE], "");
        } else {
            assertEquals("Inactivation dates should be equal.", PROD_TEST_DATA[ProductCsvFile.PROP_INACT_DATE],
                         df.format(proposedInactivationDate.getValue()));
        }

        //SynonymMultiple field (name,ndcode,vendor,vsn,use,orderunit,orderunit$,dispunit,dispunit$)
        String tempStr = "";
        Collection<SynonymVo> synonyms = prodVo.getSynonyms();

        // iterate for each synonym in list
        for (SynonymVo synonym : synonyms) {
            if (tempStr.length() > 0) {
                tempStr = tempStr.concat(ProductCsvFile.FIELD_MULTIPLE_SEPARATOR);
            }

            tempStr = tempStr.concat(synonym.getSynonymName());
            tempStr = tempStr.concat(ProductCsvFile.MULTIPLE_VALUE_SEPARATOR);
            tempStr = tempStr.concat(synonym.getNdcCode());
            tempStr = tempStr.concat(ProductCsvFile.MULTIPLE_VALUE_SEPARATOR);
            tempStr = tempStr.concat(synonym.getSynonymVendor());
            tempStr = tempStr.concat(ProductCsvFile.MULTIPLE_VALUE_SEPARATOR);
            tempStr = tempStr.concat(synonym.getSynonymVsn());
            tempStr = tempStr.concat(ProductCsvFile.MULTIPLE_VALUE_SEPARATOR);
            tempStr = tempStr.concat(synonym.getSynonymIntendedUse().getValue());
            tempStr = tempStr.concat(ProductCsvFile.MULTIPLE_VALUE_SEPARATOR);
            tempStr = tempStr.concat(synonym.getSynonymOrderUnit().getAbbrev());
            tempStr = tempStr.concat(ProductCsvFile.MULTIPLE_VALUE_SEPARATOR);
            tempStr = tempStr.concat(synonym.getSynonymPricePerOrderUnit().toString());
            tempStr = tempStr.concat(ProductCsvFile.MULTIPLE_VALUE_SEPARATOR);
            tempStr = tempStr.concat(synonym.getSynonymDispenseUnitPerOrderUnit().toString());
            tempStr = tempStr.concat(ProductCsvFile.MULTIPLE_VALUE_SEPARATOR);
            tempStr = tempStr.concat(synonym.getSynonymPricePerDispenseUnit().toString());
        }

        assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.SYNONYM_MULT], tempStr);

        //WitnessRequired field
        DataField<Boolean> witness = vadfs.getDataField(FieldKey.WITNESS_TO_ADMINISTRATION);

        if (witness.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.WITNESS_REQ], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.WITNESS_REQ], "N");
        }

        //PatientSpecificLabel field
        DataField<Boolean> pslabel = vadfs.getDataField(FieldKey.PATIENT_SPECIFIC_LABEL);

        if (pslabel.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.PAT_SPEC_LABEL], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.PAT_SPEC_LABEL], "N");
        }

        //ProtectFromLight field
        DataField<Boolean> protect = vadfs.getDataField(FieldKey.PROTECT_FROM_LIGHT);

        if (protect.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IPROTECT], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IPROTECT], "N");
        }

        //DoNotHandleIfPregnant field
        DataField<Boolean> pregnant = vadfs.getDataField(FieldKey.DO_NOT_HANDLE_IF_PREGNANT);

        if (pregnant.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IPREGNANT], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IPREGNANT], "N");
        }

        //FollowUpTime field
        DataField<Boolean> followup = vadfs.getDataField(FieldKey.FOLLOW_UP_TIME);

        if (followup.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IFOLLOWUP], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IFOLLOWUP], "N");
        }

        //HazardousToDispose field
        DataField<Boolean> hazardDisp = vadfs.getDataField(FieldKey.HAZARDOUS_TO_DISPOSE);

        if (hazardDisp.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IHAZARDTODISP], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IHAZARDTODISP], "N");
        }

        //HazardousToHandle field
        DataField<Boolean> hazardHand = vadfs.getDataField(FieldKey.HAZARDOUS_TO_HANDLE);

        if (hazardHand.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IHAZARDTOHAND], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IHAZARDTOHAND], "N");
        }

        //HazardousToPatient field
        DataField<Boolean> hazardPat = vadfs.getDataField(FieldKey.HAZARDOUS_TO_PATIENT);

        if (hazardPat.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IHAZARDTOPAT], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IHAZARDTOPAT], "N");
        }

    }
    
    /**
     * testImport2
     * @param prodVo prodVo
     * @param vadfs vadfs
     */
    private void testImport2(ProductVo prodVo, DataFields<DataField> vadfs) {
        
        //LongTermOutOfStock field
        DataField<Boolean> longterm = vadfs.getDataField(FieldKey.LONG_TERM_OUT_OF_STOCK);

        if (longterm.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.ILONGTERM], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.ILONGTERM], "N");
        }

        //LowSafetyMargin field
        DataField<Boolean> lowmargin = vadfs.getDataField(FieldKey.LOW_SAFETY_MARGIN);

        if (lowmargin.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.ILOWMARGIN], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.ILOWMARGIN], "N");
        }

        //NonRenewable field
        DataField<Boolean> nonrenew = vadfs.getDataField(FieldKey.NON_RENEWABLE);

        if (nonrenew.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.INONRENEW], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.INONRENEW], "N");
        }

        //LabMonitorMark field
        DataField<Boolean> labmonitor = vadfs.getDataField(FieldKey.LAB_MONITOR_MARK);

        if (labmonitor.getValue()) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.ILABMONITOR], "Y");
        } else {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.ILABMONITOR], "N");
        }

        //Formulary field
        ListDataField<String> formulary = vadfs.getDataField(FieldKey.FORMULARY);
        assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IFORMULARY], formulary.getValue().get(0));

        //NcpcpDispenseUnit field
        ListDataField<String> ndu = vadfs.getDataField(FieldKey.NCPDP_DISPENSE_UNIT);
        assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.INCPDPDISP], ndu.getValue().get(0));

        //NcpcpQtyMultiplier field
        DataField<String> nqm = vadfs.getDataField(FieldKey.NDCDP_QUANTITY_MULTIPLIER);
        assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.INCPDPQTYMULT], nqm.getValue());

        //ApprovedForSplitting field
        if (PROD_TEST_DATA[ProductCsvFile.IAPPFORSPLIT].length() > 0) {
            ListDataField<String> split = vadfs.getDataField(FieldKey.APPROVED_FOR_SPLITTING);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IAPPFORSPLIT], split.getValue().get(0));
        }

        //DoNotMail field
        if (PROD_TEST_DATA[ProductCsvFile.IDONOTMAIL].length() > 0) {
            DataField<Boolean> dontmail = vadfs.getDataField(FieldKey.DO_NOT_MAIL);

            if (dontmail.getValue()) {
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDONOTMAIL], "Y");
            } else {
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDONOTMAIL], "N");
            }
        }

        //ArWsAmisCatagory field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPORIDE].length() > 0) {
            ListDataField<String> catagory = vadfs.getDataField(FieldKey.AR_WS_AMIS_CATEGORY);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IARWSAMISCAT], catagory.getValue().get(0));
        }

        //ArWsAmisConvertNumber field
        if (PROD_TEST_DATA[ProductCsvFile.IARWSAMISNUM].length() > 0) {
            DataField<Long> cnum = vadfs.getDataField(FieldKey.AR_WS_CONVERSION_NUMBER);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IARWSAMISNUM], cnum.getValue().toString());
        }

        //DawCode field
        if (PROD_TEST_DATA[ProductCsvFile.IDAWCODE].length() > 0) {
            ListDataField<String> dcode = vadfs.getDataField(FieldKey.DAW_CODE);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDAWCODE], dcode.getValue().get(0));
        }

        //DaysOrDoseLimit field
        if (PROD_TEST_DATA[ProductCsvFile.IDODLIMIT].length() > 0) {
            DataField<String> dlimit = vadfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDODLIMIT], dlimit.getValue());
        }

        //DispDaysSupplyLimit field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPSUPPLIMIT].length() > 0) {
            DataField<Long> slimit = vadfs.getDataField(FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDISPSUPPLIMIT], slimit.getValue().toString());
        }

        //DispLimitForOrder
        //store string in string data field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPLIMITORD].length() > 0) {
            DataField<String> dlord = vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_ORDER);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDISPLIMITORD], dlord.getValue());
        }

        //DispLimitForSchedule
        //store string in string data field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPLIMITSCH].length() > 0) {
            DataField<String> dlsch = vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_SCHEDULE);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDISPLIMITSCH], dlsch.getValue());
        }

        //DispOverride
        if (PROD_TEST_DATA[ProductCsvFile.IDISPORIDE].length() > 0) {
            DataField<String> doride = vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDISPORIDE], doride.getValue());
        }

        //DispOverrideReason
        if (PROD_TEST_DATA[ProductCsvFile.IDISPORIDEREASON].length() > 0) {
            DataField<String> doreason = vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE_REASON);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDISPORIDEREASON], doreason.getValue());
        }

        //DispenseQtyLimitDose field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPQTYLIMITDOSE].length() > 0) {
            GroupDataField dqlimit = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_LIMIT);
            DataField<Long> dqlimitdose = dqlimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_DOSE);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDISPQTYLIMITDOSE], dqlimitdose.getValue().toString());
        }

    }
    
    /**
     * testImport3
     * @param prodVo prodVo
     * @param vadfs vadfs
     */
    private void testImport3(ProductVo prodVo, DataFields<DataField> vadfs) {
        
        //DispenseQtyLimitTime field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPQTYLIMITTIME].length() > 0) {
            GroupDataField dqlimit = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_LIMIT);
            DataField<Long> dqlimittime = dqlimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_TIME);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDISPQTYLIMITTIME], dqlimittime.getValue().toString());
        }

        //DispQtyOverride
        if (PROD_TEST_DATA[ProductCsvFile.IDISPQTYORIDE].length() > 0) {
            DataField<String> dqoride = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDISPQTYORIDE], dqoride.getValue());
        }

        //DispQtyOverrideReason
        if (PROD_TEST_DATA[ProductCsvFile.IDISPQTYORIDEREASON].length() > 0) {
            DataField<String> dqoreason = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE_REASON);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDISPQTYORIDEREASON], dqoreason.getValue());
        }

        //DispenseUnitsPerOrderUnit field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPLUNITORDUNIT].length() > 0) {
            DataField<Double> dunits = vadfs.getDataField(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDISPLUNITORDUNIT], dunits.getValue().toString());
        }

        //FsnNsn field
        if (PROD_TEST_DATA[ProductCsvFile.IFSNNSN].length() > 0) {
            DataField<String> fsn = vadfs.getDataField(FieldKey.FSN);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IFSNNSN], fsn.getValue());
        }

        //HighRiskMed field
        if (PROD_TEST_DATA[ProductCsvFile.IHIRISK].length() > 0) {
            DataField<Boolean> hirisk = vadfs.getDataField(FieldKey.HIGH_RISK);

            if (hirisk.getValue()) {
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IHIRISK], "Y");
            } else {
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IHIRISK], "N");
            }
        }

        //HighRiskFollowup field
        if (PROD_TEST_DATA[ProductCsvFile.IHIRISKFOLLOWUP].length() > 0) {
            DataField<Boolean> hiriskfu = vadfs.getDataField(FieldKey.HIGH_RISK_FOLLOWUP);

            if (hiriskfu.getValue()) {
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IHIRISKFOLLOWUP], "Y");
            } else {
                assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IHIRISKFOLLOWUP], "N");
            }
        }

        //InpatMedExpOrdMaxTime field
        if (PROD_TEST_DATA[ProductCsvFile.IINPMEDEXPMAXTIME].length() > 0) {
            DataField<String> maxtime = vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IINPMEDEXPMAXTIME], maxtime.getValue());
        }

        //InpatMedExpOrdMinTime field
        if (PROD_TEST_DATA[ProductCsvFile.IINPMEDEXPMINTIME].length() > 0) {
            DataField<String> mintime = vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IINPMEDEXPMINTIME], mintime.getValue());
        }

        //MaxDispenseLimit field
        if (PROD_TEST_DATA[ProductCsvFile.IMAXDISPLIMIT].length() > 0) {
            DataField<String> mdlimit = vadfs.getDataField(FieldKey.MAX_DISPENSE_LIMIT);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IMAXDISPLIMIT], mdlimit.getValue());
        }

        //MaxDosePerDay field
        if (PROD_TEST_DATA[ProductCsvFile.IMAXDOSDAY].length() > 0) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IMAXDOSDAY], prodVo.getMaxDosePerDay().toString());
        }

        //RxMessage field
        if (PROD_TEST_DATA[ProductCsvFile.IRXMESSAGE].length() > 0) {
            DataField<String> rxmsg = vadfs.getDataField(FieldKey.MESSAGE);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IRXMESSAGE], rxmsg.getValue());
        }

        //MonitorMaxDays field
        if (PROD_TEST_DATA[ProductCsvFile.IMONMAXDAYS].length() > 0) {
            DataField<Long> mmdays = vadfs.getDataField(FieldKey.MONITOR_MAX_DAYS);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IMONMAXDAYS], mmdays.getValue().toString());
        }

        //OrderUnit field
        if (PROD_TEST_DATA[ProductCsvFile.IORDERUNIT].length() > 0) {
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IORDERUNIT], prodVo.getOrderUnit().getAbbrev());
        }

        //DispOverrideReasonBy field
        if (PROD_TEST_DATA[ProductCsvFile.IDISPORIDEREASONBY].length() > 0) {
            DataField<String> dorby = vadfs.getDataField(FieldKey.OVERRIDE_REASON_ENTERER);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IDISPORIDEREASONBY], dorby.getValue());
        }

        //QtyDispMesssage field
        if (PROD_TEST_DATA[ProductCsvFile.IQTYDISPMSG].length() > 0) {
            DataField<String> qdmsg = vadfs.getDataField(FieldKey.QUANTITY_DISPENSE_MESSAGE);
            assertEquals(ERROR_MSG, PROD_TEST_DATA[ProductCsvFile.IQTYDISPMSG], qdmsg.getValue());
        }

    }
} // end ProductCsvFileTest class
