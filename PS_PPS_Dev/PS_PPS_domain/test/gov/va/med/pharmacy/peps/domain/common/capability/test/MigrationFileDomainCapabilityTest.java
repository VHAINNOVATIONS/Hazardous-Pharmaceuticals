/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.MigrationFileVo;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationFileDomainCapability;


/**
 * MigrationFileDomainCapabilityTest
 */
public class MigrationFileDomainCapabilityTest extends DomainCapabilityTestCase {
    private MigrationFileDomainCapability migrationFileDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        this.migrationFileDomainCapability = getNationalCapability(MigrationFileDomainCapability.class);
    }

    /**
     * This method should create three errors in the database
     * 
     * @throws Exception Exception
     */
    public void testCreateMigrationFileEntry() throws Exception {

        MigrationFileVo dataVo = new MigrationFileVo();

        dataVo.setFileId("50.681");
        dataVo.setMigrationFileName("This is a madeup File");
        dataVo.setErrorQty(PPSConstants.I25);
        dataVo.setRowsMigratedQty(PPSConstants.I100);
        dataVo.setRowsNotMigratedQty(PPSConstants.I50);
        dataVo.setRowsProcessedQty(PPSConstants.I175);

        dataVo.setCreatedBy("Patrick");
        dataVo.setCreatedDate(new Date());
        dataVo.setModifiedBy("Chris");
        dataVo.setModifiedDate(new Date());

        MigrationFileVo returnedVo2 = migrationFileDomainCapability.create(
                dataVo, getTestUser());
        assertNotNull("Returned NdcUnit with id", returnedVo2.getFileId());

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllMigrationFiles() throws Exception {
        List<MigrationFileVo> rCollection;
        rCollection = migrationFileDomainCapability.retrieveMigrationFile();

        assertTrue("Should be at least 11 in the Migration File entries.",
                rCollection.size() > PPSConstants.I10);

    }

    /**
     * testUpdateMigrationFile
     * 
     * @throws Exception Exception
     */
    public void testUpdateMigrationFile() throws Exception {

        int i1 = PPSConstants.I10;
        int i2 = PPSConstants.I37;
        int i3 = PPSConstants.I42;
        int i4 = PPSConstants.FIFTYSEVEN;

        List<MigrationFileVo> names = migrationFileDomainCapability
                .retrieveMigrationFile();
        String strFileId = names.get(0).getFileId();
        names.get(0).setRowsMigratedQty(i1);
        names.get(0).setRowsNotMigratedQty(i2);
        names.get(0).setErrorQty(i3);
        names.get(0).setRowsProcessedQty(i4);

        migrationFileDomainCapability.update(names.get(0), getTestUser());

        names = migrationFileDomainCapability.retrieveMigrationFile();

        for (MigrationFileVo vo : names) {
            if (vo.getFileId().equals(strFileId)) {
                assertEquals(
                        "The Rows Migrated Qty field did not update correctly",
                        i1, vo.getRowsMigratedQty().intValue());
                assertEquals(
                        "The Rows Not Migrated Qty field did not update correctly",
                        i2, vo.getRowsNotMigratedQty().intValue());
                assertEquals("The Error Qty field did not update correctly",
                        i3, vo.getErrorQty().intValue());
                assertEquals(
                        "The Rows Processed Qty field did not update correctly",
                        i4, vo.getRowsProcessedQty().intValue());
                break;
            }
        }
    }
}
