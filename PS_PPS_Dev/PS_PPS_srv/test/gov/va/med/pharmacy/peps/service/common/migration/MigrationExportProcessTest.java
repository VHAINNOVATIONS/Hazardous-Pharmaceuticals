/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.domain.common.capability.ResetNationalDatabaseDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.test.DomainCapabilityTestCase;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationExportState;
import gov.va.med.pharmacy.peps.service.common.session.MigrationCSVService;
import gov.va.med.pharmacy.peps.service.common.session.MigrationService;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;


/**
 * MigrationExportProcessTest
 *
 */
public class MigrationExportProcessTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(MigrationExportProcessTest.class);

    private MigrationCSVService myService;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private MigrationService myMigrationService;

    /**
     * setupSpringConfiguration
     */
    private void setupSpringConfiguration() {
        myMigrationService = SpringTestConfigUtility.getNationalSpringBean(MigrationService.class);
        this.resetNationalDatabaseDomainCapability = getNationalCapability(ResetNationalDatabaseDomainCapability.class);
        myService = getNationalCapability(MigrationCSVService.class);
        myService.setResetNationalDatabaseDomainCapability(resetNationalDatabaseDomainCapability);
    }

    /**
     * setUp
     */
    @Before
    public void setUp() {
        setupSpringConfiguration();
    }

    /**
     * testStartDownloadExportFile
     * @throws InterruptedException InterruptedException
     */
    @Test
    public void testStartDownloadExportFile() throws InterruptedException {
        MigrationExportState status = null;
        boolean done = false;

        myMigrationService.startExportProcess();
        Thread.sleep(PPSConstants.I2000);
        
        while (!done) {
            status = myMigrationService.getExportStatus();
            LOG.debug("NDC record count: " + status.getNdcRecordCounter());
            LOG.debug("OI record count: " + status.getOiRecordCounter());
            LOG.debug("PRODUCT record count: " + status.getProductRecordCounter());
            LOG.debug("--------------------------------------------------" 
                    + "-------------------------------------------------------------");
            LOG.debug("running: " + status.getExportData() + "  status: " + status.getStatus()
                               + " Error count: " + status.getFailureCounter());
            LOG.debug("---------------------------------------------------" 
                    + "------------------------------------------------------------------");
            Thread.sleep(PPSConstants.I400);

            //if all data exports are completed
            if (status.isExportComplete()) {
                done = true;
                LOG.debug("--------------------------------------------------------------------");
                LOG.debug("Download export complete  : " + status.getRecordCounter() + " status: "
                                   + status.getStatus());
                LOG.debug("----------------------------------------------------------------------");
                myMigrationService.startExportProcess();
            }

            LOG.debug("1----------------------------------------------------------------------");
            LOG.debug("2----------------------------------------------------------------------");
            LOG.debug("3----------------------------------------------------------------------");
            LOG.debug("4----------------------------------------------------------------------");
            LOG.debug("5----------------------------------------------------------------------");

        }

        assertTrue("Export did not complete!", status.isExportComplete());
    }

}
