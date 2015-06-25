/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;



import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.PatientMedicationInstructionVo;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;

import junit.framework.TestCase;


/**
 * Test the base class of DefaultRulesCapability. Also test that a sub class still has access to the super's Spring injected
 * classes.
 */
public class PMIRetrieveTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(PMIRetrieveTest.class);
    private static final long GCN = 6561;
    private DrugReferenceCapability drugReferenceCapability;

    /**
     * Get instance of {@link RulesCapability}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.info("---------- " + getName() + " ----------");

        this.drugReferenceCapability = SpringTestConfigUtility.getNationalSpringBean(DrugReferenceCapability.class);
    }

    
    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testPMIRetrieveEnglish() {
        
        
        boolean spanish = false;
        String titleExpected = "WARFARIN - ORAL";
        
        PatientMedicationInstructionVo pmiVo = new PatientMedicationInstructionVo();
        
        try {
            pmiVo = this.drugReferenceCapability.retrievePatientMedicationInformation(GCN, spanish);
            
            if (pmiVo == null) {
                fail("The pmiVo is null. ");
            } else {
                assertNotSame(titleExpected, pmiVo.getTitle());
            }
        } catch (Exception e) {
            fail("Should have thrown a ValueObjectValidationException, not just a ValidationException " + e.getMessage());
        }
    }
    
    /**
     * Test the Retrieval of SPanish text for a PMI
     */
    public void testPMIRetrieveSpanish() {
        
    
        boolean spanish = true;
        String titleExpected = "WARFARINA - ORAL";
        
        PatientMedicationInstructionVo pmiVo = new PatientMedicationInstructionVo();
        
        try {
            pmiVo = this.drugReferenceCapability.retrievePatientMedicationInformation(GCN, spanish);
            
            if (pmiVo == null) {
                fail("The pmiVo is null.");
            } else {
                LOG.info("Title: " + pmiVo.getTitle());
                LOG.info("BrandName: " + pmiVo.getBrandName());
                LOG.info("MissedDose: " + pmiVo.getMissedDose());
                LOG.info("Phonetics: " + pmiVo.getPhonetics());
                LOG.info("HowToTake: " + pmiVo.getHowToTake());
                LOG.info("DrugInteractions: " + pmiVo.getDrugInteractions());
                LOG.info("MedicalAlerts: " + pmiVo.getMedicalAlerts());
                LOG.info("Notes: " + pmiVo.getNotes());
                LOG.info("Overdose: " + pmiVo.getOverdose());
                LOG.info("Precautions: " + pmiVo.getPrecautions());
                LOG.info("Storage: " + pmiVo.getStorage());
                LOG.info("SideEffects: " + pmiVo.getSideEffects());
                LOG.info("Uses: " + pmiVo.getUses());
                LOG.info("Warnings: " + pmiVo.getWarning());
                LOG.info("Disclaimer: " + pmiVo.getDisclaimer());
                assertNotSame(titleExpected, pmiVo.getTitle());                   
            }
        } catch (Exception e) {
            fail("Should have thrown a ValueObjectValidationException, not just a ValidationException");
        }
    }
    
}
