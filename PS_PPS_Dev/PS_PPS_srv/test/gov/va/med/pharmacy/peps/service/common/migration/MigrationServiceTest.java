/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.MigrationFileVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationFileDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.test.DomainCapabilityTestCase;
import gov.va.med.pharmacy.peps.service.common.capability.MigrationCapability;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationProcessState;
import gov.va.med.pharmacy.peps.service.common.session.MigrationService;
import gov.va.med.pharmacy.peps.service.common.session.impl.MigrationProcessManagerImpl;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;



/**
 * MigrationServiceTest
 *
 */
public class MigrationServiceTest extends DomainCapabilityTestCase {
    
    private MigrationService myMigrationService;
    private MigrationCapability myMigrationCapability;
    private MigrationProcessState myMigrationState;
    private DrugUnitDomainCapability drugUnitDomainCapability;

    private MigrationFileDomainCapability migrationFileDomainCapability;


    /**
     * main
     * @param args args
     */
    public static void main(String[] args) {
        MigrationServiceTest test = new MigrationServiceTest();

        test.testMigrationStart();
    }
    
    /**
     * setUp
     */
    public void setUp() {
        myMigrationState = new MigrationProcessState();
        migrationFileDomainCapability = getNationalCapability(MigrationFileDomainCapability.class);

     
        myMigrationService = SpringTestConfigUtility.getNationalSpringBean(MigrationService.class);
        myMigrationCapability = SpringTestConfigUtility.getNationalSpringBean(MigrationCapability.class);
        drugUnitDomainCapability = getNationalCapability(DrugUnitDomainCapability.class);
     
        myMigrationCapability.setDrugUnitDomainCapability(drugUnitDomainCapability);

    }



    /**
     * testMigrationStart
     */
    public void testMigrationStart() {
        testResetDatabaseStart();

        myMigrationState = myMigrationService.startMigration(getTestUser());
        assertTrue("start migration test failed!", myMigrationState.isRunning());
        assertTrue("stop migration status is not set to STARTED",
                   myMigrationState.getStatus().equals(ProcessStatus.RUNNING));
        myMigrationService.getMigrationProcessState();

    }

    /**
     * testMigrationStop()
     */
    public void testMigrationStop() {
        testResetDatabaseStart();

        UserVo user = new UserVo();
        myMigrationState = myMigrationService.startMigration(user);
        assertTrue("Start migration test failed", myMigrationState.getStatus() == ProcessStatus.RUNNING);

        myMigrationState = myMigrationService.stopMigration();
        assertFalse("Stop migration running flag was not set to false", myMigrationState.isRunning());
    }

    /**
     * testMigrationPause()
     */
    public void testMigrationPause() {
        UserVo user = new UserVo();

        testResetDatabaseStart();

        myMigrationState = myMigrationService.startMigration(user);
        assertTrue(" start migration test failed", myMigrationState.isRunning());

        myMigrationState = myMigrationService.pauseMigration();
        assertTrue(" pause migration running flag was is not set to true", myMigrationState.isRunning());
        assertTrue(" pause migration status is not set to PAUSED",
                   myMigrationState.getStatus().equals(ProcessStatus.PAUSED));
    }

    /**
     * testMigrationResume
     */
    public void testMigrationResume() {
        testResetDatabaseStart();

        UserVo user = new UserVo();
        myMigrationState = myMigrationService.startMigration(user);
        assertTrue("This Start migration test failed", myMigrationState.isRunning());

        myMigrationState = myMigrationService.pauseMigration();
        assertTrue("pause migration running flag was is not set to true", myMigrationState.isRunning());
        assertTrue("pause migration status is not set to PAUSED",
                   myMigrationState.getStatus().equals(ProcessStatus.PAUSED));

        myMigrationState = myMigrationService.resumeMigration();
        assertTrue("resume migration running flag was is not set to true", myMigrationState.isRunning());
        assertTrue("resume migration status is not set to RESUMED",
                   myMigrationState.getStatus().equals(ProcessStatus.RESUMED)
                           || myMigrationState.getStatus().equals(ProcessStatus.RUNNING));

    }

    /**
     * testResetDatabaseStart
     */
    public void testResetDatabaseStart() {
        myMigrationState = myMigrationService.startDataBaseReset();
       
        while (myMigrationState.isDbResetRunning()) {

            assertTrue("start migration test failed", myMigrationState.isDbResetRunning());
            assertTrue("stop migration status is not set to DB_STARTED",
                       myMigrationState.getStatus().equals(ProcessStatus.DB_RESET_RUNNING));

            myMigrationState = myMigrationService.getDataBaseResetStatus();
        
        }
     
        assertTrue("Count should be at least 15.", myMigrationState.getDbCount() >= PPSConstants.I15);

    }

    /**
     * testRetrieveMigrationFile
     */
    public void testRetrieveMigrationFile() {
        Map<Object, Object> domainMap = myMigrationState.getDomainMapByFileId();

        myMigrationService.populateMigrationDomains(myMigrationState);

        MigratedDomain migratedNDCDomain =
                (MigratedDomain) domainMap.get(String.valueOf(ProcessDomainType.NDC_CSV_FILE_ACTIVE.getFileNumber()));

        assertTrue("Package Types migrated should not be 0", migratedNDCDomain.getPackageTypesMigrated() != 0);
        assertTrue("Manufacturers migrated should not be 0", migratedNDCDomain.getManufacturersMigrated() != 0);

    }

    /**
     * getTestUser
     * @return UserVo
     */
    protected UserVo getTestUser() {
        UserVo user = new UserGenerator().getNationalManagerOne();
        user.setFirstName("FirstNme Domain Test Case");
        user.setLastName("Lastname Domain Test Case");
        user.setLocation("Location Domain Test Case");
        user.setStationNumber("333");
        user.setUsername("Full UserName");

        return user;
    }

    /**
     * testRetrieveMigrationErrors
     * @throws Exception Exception
     */
    public void testRetrieveMigrationErrors() throws Exception {
        myMigrationService.populateMigrationDomainErrors(myMigrationState);

        List<MigratedDomain> domainList = myMigrationState.getMigratedDomainList();
        assertTrue("The domain list size should not be zero", domainList.size() > 0);

        try {
            for (MigratedDomain domain : domainList) {
                List<ErrorDetails> errorDetailList = domain.getErrorDetailList();

                for (ErrorDetails error : errorDetailList) {
                    assertTrue("ensure we can comb the list.", error.getFileId().length() > 0);
                }
            }
        } catch (Exception e) {
            fail("Error was thrown:  " + e.getMessage());
        }
    }

    /**
     * testSaveDomainVo
     */
    public void testSaveDomainVo() {
        Map<Object, Object> domainMap = myMigrationState.getDomainMapByFileId();
        assertTrue("Domain map should not be empty!", domainMap.size() > 0);

        MigratedDomain drugUnitMigratedDomain =
                (MigratedDomain) domainMap.get(ProcessDomainType.DRUG_UNITS_ACTIVE.getFileNumber());
        drugUnitMigratedDomain.setTotalMigrated(PPSConstants.I600);
        drugUnitMigratedDomain.setTotalErrors(PPSConstants.I7);
        drugUnitMigratedDomain.setDuplicatesNoMigrated(PPSConstants.I9);
        drugUnitMigratedDomain.setCount(PPSConstants.I1200);

        saveDomainVo(ProcessDomainType.DRUG_UNITS_ACTIVE, drugUnitMigratedDomain);

        MigratedDomain ndcMigratedDomain =
                (MigratedDomain) domainMap.get(ProcessDomainType.NDC_CSV_FILE_ACTIVE.getFileNumber());
        ndcMigratedDomain.setTotalMigrated(PPSConstants.I1200);
        ndcMigratedDomain.setTotalErrors(PPSConstants.I45);
        ndcMigratedDomain.setDuplicatesNoMigrated(PPSConstants.I58);
        ndcMigratedDomain.setCount(new Integer("1306"));

        ndcMigratedDomain.setManufacturersMigrated(PPSConstants.I100);
        ndcMigratedDomain.setPackageTypesMigrated(PPSConstants.I37);

        saveDomainVo(ProcessDomainType.NDC_CSV_FILE_ACTIVE, ndcMigratedDomain);
    }

    /**
     * testDomainFileNumbers
     */
    public void testDomainFileNumbers() {
        Map<Object, Object> domainMap = myMigrationState.getDomainMapByFileId();
        MigratedDomain drugUnitMigratedDomain =
                (MigratedDomain) domainMap.get(ProcessDomainType.DRUG_UNITS_ACTIVE.getFileNumber());
        MigratedDomain migratedVaDispenseUnit =
                (MigratedDomain) domainMap.get(ProcessDomainType.VA_DISPENSE_UNIT_ACTIVE.getFileNumber());
        MigratedDomain migratedVaGenericName =
                (MigratedDomain) domainMap.get(ProcessDomainType.VA_GENERIC_NAME_ACTIVE.getFileNumber());
        MigratedDomain migratedDosageForm =
                (MigratedDomain) domainMap.get(ProcessDomainType.DOSAGE_FORM_ACTIVE.getFileNumber());
        MigratedDomain migratedDrugClass =
                (MigratedDomain) domainMap.get(ProcessDomainType.DRUG_CLASS_0.getFileNumber());
        MigratedDomain migratedIngredients =
                (MigratedDomain) domainMap.get(ProcessDomainType.DRUG_INGREDIENTS_ACTIVE.getFileNumber());
        MigratedDomain migratedOrderableItemsCsv =
                (MigratedDomain) domainMap.get(ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE.getFileNumber());
        MigratedDomain migratedVaProduct =
                (MigratedDomain) domainMap.get(ProcessDomainType.VA_PRODUCT_ACTIVE.getFileNumber());
        MigratedDomain migratedNDCCsvFile =
                (MigratedDomain) domainMap.get(ProcessDomainType.NDC_CSV_FILE_ACTIVE.getFileNumber());

        assertTrue("Drug Unit File numbers should match!", drugUnitMigratedDomain.getFileNumber() 
                == ProcessDomainType.DRUG_UNITS_ACTIVE.getFileNumber());
        assertTrue("Dispense Unit File numbers should match!", migratedVaDispenseUnit.getFileNumber() 
                == ProcessDomainType.VA_DISPENSE_UNIT_ACTIVE.getFileNumber());
        assertTrue("Generic Name File numbers should match!", migratedVaGenericName.getFileNumber() 
                == ProcessDomainType.VA_GENERIC_NAME_ACTIVE.getFileNumber());
        assertTrue("Dosage Form File numbers should match!", migratedDosageForm.getFileNumber() 
                == ProcessDomainType.DOSAGE_FORM_ACTIVE.getFileNumber());
        assertTrue("Drug Class File numbers should match!", migratedDrugClass.getFileNumber() 
                == ProcessDomainType.DRUG_CLASS_0.getFileNumber());
        assertTrue("Ingredient File numbers should match!", migratedIngredients.getFileNumber() 
                == ProcessDomainType.DRUG_INGREDIENTS_ACTIVE.getFileNumber());
        assertTrue("Orderable Item File numbers should match!", migratedOrderableItemsCsv.getFileNumber()
                == ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE
                .getFileNumber());
        assertTrue("Product File numbers should match!", migratedVaProduct.getFileNumber() 
                == ProcessDomainType.VA_PRODUCT_ACTIVE.getFileNumber());
        assertTrue("NDC File numbers should match!", migratedNDCCsvFile.getFileNumber() 
                == ProcessDomainType.NDC_CSV_FILE_ACTIVE.getFileNumber());

    }

    /**
     * testMigratedDomainList
     */
    public void testMigratedDomainList() {

        Map<Object, Object> domainMap = createDomainMap();
        MigratedDomain domain = (MigratedDomain) domainMap.get(ProcessDomainType.DRUG_INGREDIENTS_ACTIVE);
        assertNotNull("Ingredient domain should not be null", domain);
        
        List<MigratedDomain> domainList = myMigrationState.getMigratedDomainList();

        assertTrue("domain list size should be greater than zero!", domainList.size() > 0);


    }

    /**
     * Save domainVo
     * @param pProcessDomainType pProcessDomainType
     * @param migratedDomain migratedDomain
     */
    public void saveDomainVo(ProcessDomainType pProcessDomainType, MigratedDomain migratedDomain) {
        List<MigrationFileVo> domainNames = migrationFileDomainCapability.retrieveMigrationFile();

        for (MigrationFileVo dbDomain : domainNames) {
            if (migratedDomain.getFileNumber() == null) {
                fail("saveDomainVo==>migratedDomain.getFileNumber() == null, for "
                                   + migratedDomain.getName());
            } else {
                if (migratedDomain.getFileNumber().equals(ProcessDomainType.NDC_CSV_FILE_ACTIVE.getFileNumber())) {

                    if (dbDomain.getFileId().equals(ProcessDomainType.PACKAGE_TYPES.getFileNumber())) {
                        dbDomain.setRowsMigratedQty(migratedDomain.getPackageTypesMigrated());
                        migrationFileDomainCapability.update(dbDomain, this.getTestUser());
                    } else if (dbDomain.getFileId().equals(ProcessDomainType.MANUFACTURES.getFileNumber())) {
                        dbDomain.setRowsMigratedQty(migratedDomain.getManufacturersMigrated());
                        migrationFileDomainCapability.update(dbDomain, this.getTestUser());
                    } else if (dbDomain.getFileId().equals(ProcessDomainType.NDC_CSV_FILE_ACTIVE.getFileNumber())) {
                        dbDomain.setRowsMigratedQty(migratedDomain.getTotalMigrated());
                        dbDomain.setErrorQty(migratedDomain.getTotalErrors());
                        dbDomain.setRowsNotMigratedQty(migratedDomain.getDuplicatesNoMigrated());
                        dbDomain.setRowsProcessedQty(migratedDomain.getCount());
                        migrationFileDomainCapability.update(dbDomain, this.getTestUser());
                    }
                } else if (dbDomain.getFileId().equals(pProcessDomainType.getFileNumber())) {
                    dbDomain.setRowsMigratedQty(migratedDomain.getTotalMigrated());
                    dbDomain.setErrorQty(migratedDomain.getTotalErrors());
                    dbDomain.setRowsNotMigratedQty(migratedDomain.getDuplicatesNoMigrated());
                    dbDomain.setRowsProcessedQty(migratedDomain.getCount());
                    migrationFileDomainCapability.update(dbDomain, this.getTestUser());
                    break;
                }
            }
        }

    }

    /**
     * createDomainMap
     * @return Map<Object, Object>
     */
    private Map<Object, Object> createDomainMap() {
        Map<Object, Object> domainMap = new HashMap<Object, Object>();
        domainMap.put(ProcessDomainType.DRUG_UNITS_ACTIVE, new MigratedDrugUnit());
        domainMap.put(ProcessDomainType.VA_DISPENSE_UNIT_ACTIVE, new MigratedVaDispenseUnit());
        domainMap.put(ProcessDomainType.VA_GENERIC_NAME_ACTIVE, new MigratedVaGenericeName());
        domainMap.put(ProcessDomainType.DOSAGE_FORM_ACTIVE, new MigratedDosageForm());
        domainMap.put(ProcessDomainType.DRUG_CLASS_0, new MigratedDrugClass());
        domainMap.put(ProcessDomainType.DRUG_INGREDIENTS_ACTIVE, new MigratedIngredients());
        domainMap.put(ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE, new MigratedOrderableItemsCsv());
        domainMap.put(ProcessDomainType.VA_PRODUCT_ACTIVE, new MigratedVaProduct());

        return domainMap;
    }

    /**
     * MockMigrationProcessManagerImpl
     *
     */
    public class MockMigrationProcessManagerImpl extends MigrationProcessManagerImpl {

//        /**
//         * setMigrationProcess
//         * @param pmigrationProcess pmigrationProcess
//         */
//        public void setMigrationProcess(MigrationProcess pmigrationProcess) {
//            super.setMigrationProcess(pmigrationProcess);
//        }

    }

}
