/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.migration;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.service.common.capability.impl.MigrationCapabilityImpl;


/**
 * MockMigrationCapabilityImplTestUtil
 */
public class MockMigrationCapabilityImplTestUtil extends
        MigrationCapabilityImpl {

    private MigrationVariablesVo migrationVariablesVo;

    /**
     * migrateDrugUnits
     * @param ienIn ienIn
     * @param size size
     * @param pState pState
     * @return MigrationVariablesVo
     */
    @Override
    public MigrationVariablesVo migrateDrugUnits(String ienIn, int size,
            int pState) {
        migrationVariablesVo = new MigrationVariablesVo();
        int errors = 2;
        int duplicates = PPSConstants.I3;
        int successMigrated = 0;
        int ien = Integer.parseInt(ienIn);
        ien = ien + size;
        successMigrated = ien;
        successMigrated = (successMigrated - (errors + duplicates));

        // set the IEN for the for the drugUnit
        migrationVariablesVo.setStrLastIEN(String.valueOf(ien));

        migrationVariablesVo.setIDuplicatesNotMigrated(duplicates);
        migrationVariablesVo.setIErroredOnMigration(errors);
        migrationVariablesVo.setISuccessfullyMigrated(successMigrated);

        // simulate eof file
        if (ien >= ProcessDomainType.DRUG_UNITS_ACTIVE.getMaxRecord()) {
            migrationVariablesVo.setEndOfFile(true);
        }

        return migrationVariablesVo;
    }

    /**
     * migrateDispenseUnits
     * @param ienIn ienIn
     * @param size size
     * @param pState pState
     * @return MigrationVariableVo
     */
    @Override
    public MigrationVariablesVo migrateDispenseUnits(String ienIn, int size,
            int pState) {

        migrationVariablesVo = new MigrationVariablesVo();
        int errors = 2;
        int duplicates = PPSConstants.I3;
        int successMigrated = 0;

        int ien = Integer.parseInt(ienIn);
        
        ien = ien + size;
        successMigrated = ien;

        successMigrated = (successMigrated - (errors + duplicates));

        migrationVariablesVo.setStrLastIEN(String.valueOf(ien));

        migrationVariablesVo.setIDuplicatesNotMigrated(duplicates);
        migrationVariablesVo.setIErroredOnMigration(errors);
        migrationVariablesVo.setISuccessfullyMigrated(successMigrated);

        return migrationVariablesVo;
    }

}
