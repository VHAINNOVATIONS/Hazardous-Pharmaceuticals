/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ExcludeDosageCheck;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * dosage form service tests
 */
public class DosageFormServiceTest extends IntegrationTestCase {
    
    private ManagedItemService nationalManagedItemService;

    /**
     * DosageFormServiceTest
     * 
     * @param name Name
     */
    public DosageFormServiceTest(String name) {
        super(name);
    }

    /**
     * Setup the environment
     */
    protected void setUp() {
        nationalManagedItemService = getNationalService(ManagedItemService.class);
    }

    /**
     * create a blank template for dosage form name. check that the default value of exclude from dosage check is set to "No"
     * 
     * @throws Exception Exception
     */
    public void testCreateBlankTemplate() throws Exception {
        DosageFormVo dosageForm = (DosageFormVo) nationalManagedItemService.retrieveBlankTemplate(EntityType.DOSAGE_FORM);
        
        // check the exclude from dosage check is set to "No"
        assertEquals("Defalut value of Exclude from dosage check not set correctly!", ExcludeDosageCheck.NO, dosageForm
            .getExcludeFromDosageChks());
    }
}
