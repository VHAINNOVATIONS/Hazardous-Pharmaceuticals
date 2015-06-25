/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessDomainType.DomainState;


/**
 * MockMigrationCapabilityImpl
 *
 */
public class MockMigrationCapabilityImpl {

    /**
     * migrationVariablesVO
     */
    private MigrationVariablesVo migrationVariablesVo;

    /**
     * MigrationVariablesVo
     * @param ienIn ien
     * @param size size
     * @param pState pState
     * @param recordCounter recordCounter
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo
     */
    public MigrationVariablesVo migrateDrugUnits(String ienIn, int size, int pState, int recordCounter,
                                                 ProcessDomainType pProcessDomainType) {
        migrationVariablesVo = new MigrationVariablesVo();
        int errors = 2;
        int duplicates = PPSConstants.I3;
        int successMigrated = 0;

        int ien = Integer.parseInt(ienIn);

        ien = getUnits(ien, size, pState, recordCounter, pProcessDomainType);

        // get the quantity of the drug units migrated
        successMigrated = pProcessDomainType.getRecordFetchQty();

        successMigrated = (successMigrated - (errors + duplicates));

        migrationVariablesVo.setStrLastIEN(String.valueOf(ien));

        // ensure the counts are set correctly for the migrated drug units
        migrationVariablesVo.setIDuplicatesNotMigrated(duplicates);
        migrationVariablesVo.setIErroredOnMigration(errors);
        migrationVariablesVo.setISuccessfullyMigrated(successMigrated);

        return migrationVariablesVo;
    }

    /**
     * getUnits
     * @param ien ien
     * @param size size
     * @param pState pState
     * @param recordCounter recordCounter
     * @param pProcessDomainType pProcessDomainType
     * @return units
     */
    private int getUnits(int ien, int size, int pState, int recordCounter, ProcessDomainType pProcessDomainType) {
        boolean started = false;
        int tempIen = ien;
        int skipFactor = PPSConstants.I100;

        for (int i = 0; i < size; i++, tempIen++) {
            if (tempIen > PPSConstants.I400 && !started) {
                tempIen += skipFactor;
                started = true;
            }

            if (pProcessDomainType.getDomainState() == DomainState.ACTIVE) {
                if (recordCounter >= pProcessDomainType.getMaxRecord() / 2) {
                    migrationVariablesVo.setEndOfFile(true);
                    break;
                }

            } else if (pProcessDomainType.getDomainState() == DomainState.INACTIVE) {
                
                //simulate eof file
                if ((i + recordCounter) >= pProcessDomainType.getMaxRecord()) {
                    migrationVariablesVo.setEndOfFile(true);
                    break;
                }
            } else if (pProcessDomainType.getDomainState() == DomainState.CLASS0) {
                if (recordCounter >= pProcessDomainType.getMaxRecord() / PPSConstants.I3) {
                    migrationVariablesVo.setEndOfFile(true);
                    break;
                }

            } else if (pProcessDomainType.getDomainState() == DomainState.CLASS1) {
                if (recordCounter >= pProcessDomainType.getMaxRecord() / 2) {
                    migrationVariablesVo.setEndOfFile(true);
                    break;
                }
            } else if (pProcessDomainType.getDomainState() == DomainState.CLASS2) {
                if (recordCounter >= pProcessDomainType.getMaxRecord()) {
                    migrationVariablesVo.setEndOfFile(true);
                    break;
                }
            }

        }

        return tempIen;

    }

    /**
     * migrateDispenseUnits
     * @param ienIn IEN
     * @param size size
     * @param pState pState
     * @param recordCounter recordCounter
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo
     */
    public MigrationVariablesVo migrateDispenseUnits(String ienIn, int size, int pState, int recordCounter,
                                                     ProcessDomainType pProcessDomainType) {

        migrationVariablesVo = new MigrationVariablesVo();
        int errors = 2;
        int duplicates = PPSConstants.I3;
        int successMigrated = 0;

        int ien = Integer.parseInt(ienIn);

        // get the dispense units units domain
        ien = getUnits(ien, size, pState, recordCounter, pProcessDomainType);
        successMigrated = pProcessDomainType.getRecordFetchQty();
        successMigrated = (successMigrated - (errors + duplicates));

        // get the last IEN for the dispense unit 
        migrationVariablesVo.setStrLastIEN(String.valueOf(ien));
        migrationVariablesVo.setIDuplicatesNotMigrated(duplicates);
        migrationVariablesVo.setIErroredOnMigration(errors);
        migrationVariablesVo.setISuccessfullyMigrated(successMigrated);

        return migrationVariablesVo;
    }

    /**
     * migrateVAGeneric
     * @param ienIn IEN
     * @param size size
     * @param pState pState
     * @param recordCounter recordCounter
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo
     */
    public MigrationVariablesVo migrateVAGeneric(String ienIn, int size, int pState, int recordCounter,
                                                 ProcessDomainType pProcessDomainType) {

        migrationVariablesVo = new MigrationVariablesVo();
        int errors = 2;
        int duplicates = PPSConstants.I3;
        int successMigrated = 0;

        int ien = Integer.parseInt(ienIn);

        ien = getUnits(ien, size, pState, recordCounter, pProcessDomainType);

        successMigrated = pProcessDomainType.getRecordFetchQty();

        successMigrated = (successMigrated - (errors + duplicates));

        // set the IEN for themigrated VA Generic.
        migrationVariablesVo.setStrLastIEN(String.valueOf(ien));

        migrationVariablesVo.setIDuplicatesNotMigrated(duplicates);
        migrationVariablesVo.setIErroredOnMigration(errors);
        migrationVariablesVo.setISuccessfullyMigrated(successMigrated);
        
        return migrationVariablesVo;
    }

    /**
     * migrateDrugIngredient
     * @param ienIn IEN
     * @param size size
     * @param pState pState
     * @param recordCounter recordCounter
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo
     */
    public MigrationVariablesVo migrateDrugIngredient(String ienIn, int size, int pState, int recordCounter,
                                                      ProcessDomainType pProcessDomainType) {
        migrationVariablesVo = new MigrationVariablesVo();
        ArrayList<IngredientVo> noPrimaryIngredients = new ArrayList<IngredientVo>();
        int errors = 2;
        int duplicates = PPSConstants.I3;
        int successMigrated = 0;
        int ien = Integer.parseInt(ienIn);

        ien = getUnits(ien, size, pState, recordCounter, pProcessDomainType);

        for (int i = 0; i < size; i++) {
            IngredientVo voIn = new IngredientVo();
            noPrimaryIngredients.add(voIn);
        }

        migrationVariablesVo.setIngredientList(noPrimaryIngredients);


        // migrated drug unit fetch quantity
        successMigrated = pProcessDomainType.getRecordFetchQty();
        successMigrated = (successMigrated - (errors + duplicates));
        migrationVariablesVo.setStrLastIEN(String.valueOf(ien));
        migrationVariablesVo.setIDuplicatesNotMigrated(duplicates);
        migrationVariablesVo.setIErroredOnMigration(errors);
        migrationVariablesVo.setISuccessfullyMigrated(successMigrated);

        return migrationVariablesVo;
    }

    /**
     * migrateDrugIngredient
     * @param noPrimaryIngredients noPrimaryIngredients
     * @param recordCounter recordCounter
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo
     */
    public MigrationVariablesVo migrateDrugIngredient(List<IngredientVo> noPrimaryIngredients, int recordCounter,
                                                      ProcessDomainType pProcessDomainType) {

        migrationVariablesVo = new MigrationVariablesVo();
        int recordCount1 = 0;

        int successMigrated = 0;
        int errors = PPSConstants.I5;
        int duplicates = PPSConstants.I10;

        for (int i = 0; i < noPrimaryIngredients.size(); i++) {
            recordCount1++;

        }
        
        migrationVariablesVo.setIngredientList(noPrimaryIngredients);

        //System.out.println("done with record count: " + recordCount1);
        
        successMigrated = recordCount1;
        successMigrated = (successMigrated - (errors + duplicates));

        migrationVariablesVo.setStrLastIEN(String.valueOf(recordCount1));

        migrationVariablesVo.setIDuplicatesNotMigrated(duplicates);
        migrationVariablesVo.setIErroredOnMigration(errors);
        migrationVariablesVo.setISuccessfullyMigrated(successMigrated);

        // end of file, this method is called only 1 time
        //simulate eof file
        migrationVariablesVo.setEndOfFile(true);

        return migrationVariablesVo;

    }

    /**
     * migrateDrugClass
     * @param ienIn IEN
     * @param size size
     * @param pState pState
     * @param recordCounter recordCounter
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo
     */
    public MigrationVariablesVo migrateDrugClass(String ienIn, int size, int pState, int recordCounter,
                                                 ProcessDomainType pProcessDomainType) {
        migrationVariablesVo = new MigrationVariablesVo();
        int errors = 2;
        int duplicates = PPSConstants.I3;
        int successMigrated = 0;

        int ien = Integer.parseInt(ienIn);

        // get the units for the Migrated DrugClass
        ien = getUnits(ien, size, pState, recordCounter, pProcessDomainType);
        successMigrated = pProcessDomainType.getRecordFetchQty();
        successMigrated = (successMigrated - (errors + duplicates));

        // set the Last IEN for the Migrated DrugClass
        migrationVariablesVo.setStrLastIEN(String.valueOf(ien));
        migrationVariablesVo.setIDuplicatesNotMigrated(duplicates);
        migrationVariablesVo.setIErroredOnMigration(errors);
        migrationVariablesVo.setISuccessfullyMigrated(successMigrated);

        return migrationVariablesVo;

    }

    /**
     * migrateDosageForm
     * @param ienIn IEN
     * @param size size
     * @param pState pState
     * @param recordCounter recordCounter
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo
     */
    public MigrationVariablesVo migrateDosageForm(String ienIn, int size, int pState, int recordCounter,
                                                  ProcessDomainType pProcessDomainType) {
        migrationVariablesVo = new MigrationVariablesVo();
        int errors = 2;
        int duplicates = PPSConstants.I3;
        int successMigrated = 0;

        int ien = Integer.parseInt(ienIn);

        ien = getUnits(ien, size, pState, recordCounter, pProcessDomainType);

        //System.out.println("done with ien records: " + ien);

        successMigrated = pProcessDomainType.getRecordFetchQty();

        successMigrated = (successMigrated - (errors + duplicates));

        // set the IEN of the migraed dosage form
        migrationVariablesVo.setStrLastIEN(String.valueOf(ien));
        migrationVariablesVo.setIDuplicatesNotMigrated(duplicates);
        migrationVariablesVo.setIErroredOnMigration(errors);
        migrationVariablesVo.setISuccessfullyMigrated(successMigrated);

        return migrationVariablesVo;
    }

}
