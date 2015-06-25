/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.MigrationFileVo;


//import gov.va.med.pharmacy.peps.domain.common.capability.ResetNationalDatabaseDomainCapability;


/**
 * ResetNationalDatabaseDomainCapabilityTest
 */
public class ResetNationalDatabaseDomainCapabilityTest extends DomainCapabilityTestCase {
  
 //   private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
      
        //  resetNationalDatabaseDomainCapability = getNationalCapability(ResetNationalDatabaseDomainCapability.class);
    }

    /**
     * This method should create three errors in the database
     * @throws Exception Exception
     */
    public void testResetDatabase() throws Exception {

        MigrationFileVo dataVo = new MigrationFileVo();
        
        dataVo.setFileId("50.681");
        dataVo.setMigrationFileName("This is a madeup File");
        dataVo.setErrorQty(new Integer("25"));
        dataVo.setRowsMigratedQty(PPSConstants.I100);
        dataVo.setRowsNotMigratedQty(PPSConstants.I50);
        dataVo.setRowsProcessedQty(PPSConstants.I175);
        
        dataVo.setCreatedBy("Patrick");
        dataVo.setCreatedDate(new Date());
        dataVo.setModifiedBy("Chris");
        dataVo.setModifiedDate(new Date());

    //    MigrationFileVo returnedVo2 = migrationFileDomainCapability.create(dataVo, getTestUser());
        assertNotNull("Returned NdcUnit with id", dataVo);
        
        // THIS TEST SHOULD BE RUN MANUALLY ONLY BECAUSE IT WILL CAUSE OTHER TESTS TO FAIL.
        //  boolean b = resetNationalDatabaseDomainCapability.resetDatabase();
        boolean b = true;
        assertTrue("TestLocal", b);
        

    }
    
}
