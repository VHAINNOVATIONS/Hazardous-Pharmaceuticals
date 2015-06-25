/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.Date;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.MigrationControlVo;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationControlDomainCapability;


/**
 * MigrationControlDomainCapabilityTest.
 */
public class MigrationControlDomainCapabilityTest extends DomainCapabilityTestCase {
    private MigrationControlDomainCapability migrationControlDomainCapability;

    /**
     * MigrationControlDomainCapabilityTest
     */
    public MigrationControlDomainCapabilityTest() {
        super();
    }
    
    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        this.migrationControlDomainCapability = getNationalCapability(MigrationControlDomainCapability.class);
    }

  /**
   * testDeleteMigrationControl
   * @throws Exception Exception
   */
    public void testDeleteMigrationControl() throws Exception {

        migrationControlDomainCapability.deleteAll();
        MigrationControlVo migrationControlState = migrationControlDomainCapability.retrieveMigrationControlInfo();

        assertNull("The Migration Control Delete did not delete the record.",
                   migrationControlState.getMigrationStatus());
    }

    /**
     * This method should create one migration control file in the database
     * 
     * @throws Exception Exception
     */
    public void testCreateMigrationControl() throws Exception {

        MigrationControlVo dataVo = new MigrationControlVo();
        dataVo.setEplId(new Long(1));
        dataVo.setUserName("Patrick A. Keller");
        dataVo.setStartDateTime(new Date());
        dataVo.setStopDateTime(new Date());
        dataVo.setThreadId(Long.parseLong("12345"));
        dataVo.setMigrationStatus("50");
        dataVo.setUserNdcFile("Some NDC File");
        dataVo.setUserOiFile("Some Orderable Item File");
        dataVo.setUserProductFile("Some Va Product File");

        dataVo.setCreatedBy("Patrick");
        dataVo.setCreatedDate(new Date());

        MigrationControlVo returnedVo = migrationControlDomainCapability.create(dataVo, getTestUser());
        assertNotNull("50", returnedVo.getMigrationStatus());

        MigrationControlVo migrationControlStatus = migrationControlDomainCapability.retrieveMigrationControlInfo();
        assertNotNull("50", migrationControlStatus.getMigrationStatus());

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllMigrationControl() throws Exception {
        MigrationControlVo migrationControlStatus;

        migrationControlStatus = migrationControlDomainCapability.retrieveMigrationControlInfo();
        
        assertTrue("The migration status should not be a null value.", 
            StringUtils.isNotBlank(migrationControlStatus.getMigrationStatus()));

    }

    /**
     * testUpdateMigrationControl
     * @throws Exception Exception
     */
    public void testUpdateMigrationControl() throws Exception {

        String migrationStatus = new String("75");
        String threadId = new String("2222");

        MigrationControlVo migrationControlState = migrationControlDomainCapability.retrieveMigrationControlInfo();
        migrationControlState.setMigrationStatus(migrationStatus);
        migrationControlState.setThreadId(Long.parseLong(threadId));
        migrationControlState.setEplId(new Long(1));
        
        MigrationControlVo returnedVo = migrationControlDomainCapability.update(migrationControlState, getTestUser());
        assertNotNull("75", returnedVo.getMigrationStatus());

        migrationControlState = migrationControlDomainCapability.retrieveMigrationControlInfo();

        assertEquals("The Migration Control Status was not updated!", migrationStatus,
                     migrationControlState.getMigrationStatus());
        assertEquals("The Migration Control Status was not updated.", migrationStatus, 
            migrationControlState.getMigrationStatus());
    }
}

