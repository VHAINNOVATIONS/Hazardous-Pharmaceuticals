/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.external.common.test.InterfaceTestCase;

import firstdatabank.database.FDBDataManager;
import firstdatabank.database.FDBSQLException;
import firstdatabank.dif.DispensableDrug;
import firstdatabank.dif.DispensableGeneric;
import firstdatabank.dif.DrugSearchFilter;
import firstdatabank.dif.FDBCode;
import firstdatabank.dif.FDBDispensableDrugLoadType;
import firstdatabank.dif.FDBMonographSource;
import firstdatabank.dif.FDBSubsetSearchType;
import firstdatabank.dif.FilterDrugSubset;
import firstdatabank.dif.Monograph;
import firstdatabank.dif.MonographLines;
import firstdatabank.dif.MonographSection;
import firstdatabank.dif.Navigation;
import firstdatabank.dif.PLWLookupResult;
import firstdatabank.dif.PLWLookupResults;
import firstdatabank.dif.PackagedDrug;
import firstdatabank.dif.PackagedDrugs;




/**
 * Verify FDB-DIF functionality. Specifically, verify Patient Medication Information functionality.
 */
public class FdbTest extends InterfaceTestCase {
    private static final Logger LOG = Logger.getLogger(FdbTest.class);
    private static final String FDB_CE = "FDB-CE";
    private static final Long GCN1 = 6561L;
    private static final Long GCN2 = 6666L;
    private static final Long GCN3 = 4592L;
    private FDBDataManager fdbDataManager;
    private DispensableGeneric[] generic;

    /**
     * Start the Spring ApplicationContext to get the FDBDataManager
     * 
     */
    public void setUp() {
        this.fdbDataManager = getSpringBean("fdbDataManager");

        generic = new DispensableGeneric[PPSConstants.I3];

        try {
            
            //  load Warfarin
            generic[0] = new DispensableGeneric(fdbDataManager);
            generic[0].load(GCN1, "", "");

            generic[1] = new DispensableGeneric(fdbDataManager);
            generic[1].load(GCN2, "", "");

            generic[2] = new DispensableGeneric(fdbDataManager);
            generic[2].load(GCN3, "", "");
        } catch (Exception e) {
            fail("Error loading generics." + e.getMessage());
        }
    }
    
    
    /**
     * testSingleMultiSource
     */
    public void testSingleMultiSource() {
        DispensableDrug drug = new DispensableDrug(fdbDataManager);
        FDBCode fdbCode = new FDBCode(fdbDataManager);
        String drugCode = "";
        String pDrugCode = "";

        for (int i = PPSConstants.I1000; i < PPSConstants.I1200; i++) {
            Long l = new Long(i);

            try {
                drug.load(l, FDBDispensableDrugLoadType.fdbDDLTGCNSeqNo, "", "", "");
                drugCode = drug.getMultiSourceCode();
                fdbCode.load(PPSConstants.I24, drug.getMultiSourceCode());
                FilterDrugSubset filterDrugSubset = new FilterDrugSubset();
                filterDrugSubset.setSearchType(FDBSubsetSearchType.fdbSSTNone);
                PackagedDrugs pDrugs = drug.getPackagedDrugs(filterDrugSubset);

                if (pDrugs.count() > 0) {
                    PackagedDrug pDrug = pDrugs.item(0);
                    pDrugCode = pDrug.getMultiSourceCode(); 
                    LOG.info("DispensibleDrug:" + drug.getGCNSeqNo() + ":" + drugCode 
                        + " : PackagedDrug: " + pDrug.getNDCFormatted() + ":" + pDrugCode);
                    
                }
                
//                for (int j = 0; j < pDrugs.count(); j++) {
//                    PackagedDrug pDrug = pDrugs.item(j);
//                    pDrugCode = pDrug.getMultiSourceCode();
//                    
//                    if ("9".equals(drugCode) || "9".equals(pDrugCode)) {
//                        LOG.info("DispensibleDrug:" + 
//                                drug.getDescription() + ":" + drug.getGCNSeqNo() + ":" +
//                                drugCode + 
//                                " : PackagedDrug: " + pDrug.getNDCFormatted() + ":" + pDrugCode);
//                    }
//                }
                
            }  catch (Exception e) {    
                
                // LOG.info("Exception is "  + e.getMessage()); 
                pDrugCode = "";
            } 
        }
        
        assertNotNull("drug should not be null", drug);

    }   
    
    /**
     * test of the NDC SEarch
     * 
     * @throws Exception exception
     */
    public void testNdcSearch() throws Exception {
        Navigation navigation = null;
        PackagedDrugs drugs = null;

        try {
            
            navigation = new Navigation(fdbDataManager);
            DrugSearchFilter dsf = new DrugSearchFilter();
            dsf.setIncludePrivateLabelers(true);      

            //   drugs = navigation.NDCSearch("00002416507", new DrugSearchFilter());
            drugs = navigation.NDCSearch("50428707032", dsf);

            //drugs = navigation.NDCSearch("63629444201", dsf);
            
            for (int index = 0; index < drugs.count(); index++) {
                PackagedDrug drug = drugs.item(index);
                LOG.debug("Drug is  " + drug.getNDCFormatted());
            }
 
        } catch (Exception e) {
            fail("Error was  " + e.getMessage());
        }

        if (drugs == null) {
            assert (false) : "Drugs == null";
        }

        assertTrue("No NDCs Found", drugs.count() > 0);
    }
    
    
    /**
     * test of the NDC SEarch
     * 
     * @throws Exception exception
     */
    public void testGcnSearch() throws Exception {

        PackagedDrugs drugs = null;

        try {
            DispensableGeneric dispGeneric = new DispensableGeneric(fdbDataManager);
            dispGeneric.load(Long.parseLong("67735"), "", "");
            drugs = dispGeneric.getPackagedDrugs(new FilterDrugSubset());
            
            for (int index = 0; index < drugs.count(); index++) {
                PackagedDrug drug = drugs.item(index);
                LOG.debug("Generic Drug is " + drug.getNDCFormatted());
            }
            
            DispensableDrug dispDrug = new DispensableDrug(fdbDataManager);
            dispDrug.load(Long.parseLong("67735"),
                FDBDispensableDrugLoadType.fdbDDLTGCNSeqNo, "", "", "");
            

            drugs = dispDrug.getPackagedDrugs(new FilterDrugSubset());
            
            for (int index = 0; index < drugs.count(); index++) {
                PackagedDrug drug = drugs.item(index);
                LOG.debug("Drug is " + drug.getNDCFormatted());
            }
        } catch (Exception e) {
            fail("Error was " + e.getMessage());
        }

        
    }

    /**
     * Test the PMI Retrieval
     * 
     * @throws Exception Exception
     */
    public void testPMIRetrieve() throws Exception {
        LOG.info("GCNSeqNo:  " + generic[1].getID());
        LOG.info("");

        Monograph monograph = generic[1].getPEMMonograph(FDB_CE, FDBMonographSource.fdbMSFDBOnly); // Spanish: FDB-CS
        assertNotNull("Monograph should not be null", monograph);

        LOG.info("Title: ");
        printMonographSection(monograph, "T");
        LOG.info("");

        // Common Brand Name

        printMonographSection(monograph, "C");
        LOG.info("");

        // Missed Dose
        printMonographSection(monograph, "D");
        LOG.info("");

        // Phonetics
        LOG.info("Phonetics: ");
        printMonographSection(monograph, "F");
        LOG.info("");

        // How To Use
        printMonographSection(monograph, "H");
        LOG.info("");

        // Drug Interactions
        printMonographSection(monograph, "I");
        LOG.info("");

        // Medication Alert
        printMonographSection(monograph, "M");
        LOG.info("");

        // Notes
        printMonographSection(monograph, "N");
        LOG.info("");

        // Overdose
        printMonographSection(monograph, "O");
        LOG.info("");

        // Precautions
        printMonographSection(monograph, "P");
        LOG.info("");

        // Storage
        printMonographSection(monograph, "R");
        LOG.info("");

        // Side Effects
        printMonographSection(monograph, "S");
        LOG.info("");

        // Uses
        printMonographSection(monograph, "U");
        LOG.info("");

        // Warnings
        printMonographSection(monograph, "W");
        LOG.info("");

        // Disclaimer
        printMonographSection(monograph, "Z");
        LOG.info("");
    }

    /**
     * test Warning label
     * 
     * @throws Exception exception
     */
    public void testWarningLabel() throws Exception {
        assertNotNull("generic should not be null", generic);
        assertTrue("generic should have at least a size of 3", generic.length >= PPSConstants.I3);

        printWarningLabel(generic[0], false);
        printWarningLabel(generic[1], false);
        printWarningLabel(generic[2], false);

        printWarningLabel(generic[0], true);
        printWarningLabel(generic[1], true);
        printWarningLabel(generic[2], true);

        // LOG.info();
        // PCMLookupResults counseling = generic.getCounselingMessages("FDB-PE"); // FDB-PS
        //
        // for (int i = 0; i < counseling.count(); i++) {
        // PCMLookupResult result = counseling.item(i);
        //
        // LOG.info(i + " Patient Text: " + result.getPatientText1() + " " + result.getPatientText2());
        // LOG.info(i + " Professional Text: " + result.getProfessionalText1() + " "
        // + result.getProfessionalText2());
        // }
    }

    /**
     * printWarningLabel
     * 
     * @param genericIn Generic
     * @param spanishIn Spanish
     * @throws FDBSQLException FDBSQLException
     */
    private void printWarningLabel(DispensableGeneric genericIn, boolean spanishIn) throws FDBSQLException {
        LOG.info("GCNSeqNo: " + genericIn.getID());
        LOG.info("Name: " + genericIn.getGenericDrugName());

        PLWLookupResults warningLabels = genericIn.getLabelWarnings(spanishIn ? "FDB-CS" : FDB_CE);

        for (int i = 0; i < warningLabels.count(); i++) {
            PLWLookupResult result = warningLabels.item(i);
            LOG.info("LabelWarningId: " + result.getLabelWarningID());
            LOG.info("WarningText: " + result.getWarningText());
            
        }


    }

    /**
     * 
     * @param monograph Monograph
     * @param sectionName sectionName
     */
    private void printMonographSection(Monograph monograph, String sectionName) {
        MonographSection section = monograph.getSections().getItemBySectionID(sectionName);

        if (section == null) {
            LOG.info("Unable to locate section '" + sectionName + "'");
        } else {
            MonographLines lines = section.getLines();

            for (int i = 0; i < lines.count(); i++) {
                LOG.info(lines.item(i).getLineText());
            }
        }
    }
}
