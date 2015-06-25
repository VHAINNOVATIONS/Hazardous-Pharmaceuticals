/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.domain.common.capability.CsFedScheduleDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DispenseUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DosageFormDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugClassDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugTextDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbNdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.GenericNameDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.IngredientDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.IntendedUseDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationErrorDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderableItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ResetNationalDatabaseDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.SpecialHandlingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.StandardMedRouteDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.test.DomainCapabilityTestCase;
import gov.va.med.pharmacy.peps.external.common.fss.outbound.capability.FssInterfaceCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.MigrationCapability;
import gov.va.med.pharmacy.peps.service.common.utility.NdcCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;


/**
 * Test the base class of MigrationCapability. Also test that a sub class still has access to the super's Spring injected
 * classes.
 */
public class MigrationNDCImportTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(MigrationDrugUnitTest.class);
    private static final String EXCEPT_MIGRATION_END_MSG = " threw a Migration exception. ";
    private static final String EXCEPT_UNCAUGHT_END_MSG = " threw an uncaught exception. ";
    private static final String PROP_USER_DIR = "user.dir";
    private static final String DIR_PATH_CSV = "/etc/csv/test/";
    
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private NdcDomainCapability ndcDomainCapability;

    private MigrationCapability migrationCapability;
    
    // MigrationNDCImportTest
    private DrugUnitDomainCapability drugUnitDomainCapability;
    private DispenseUnitDomainCapability dispenseUnitDomainCapability;
    private DosageFormDomainCapability dosageFormDomainCapability;
    private IngredientDomainCapability ingredientDomainCapability;
    private DrugClassDomainCapability drugClassDomainCapability;
    private GenericNameDomainCapability genericNameDomainCapability;
   
    // MigrationNDCImportTest
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private MigrationErrorDomainCapability migrationErrorDomainCapability;
    private SpecialHandlingDomainCapability specialHandlingDomainCapability;
    private IntendedUseDomainCapability intendedUseDomainCapability;
    private OrderUnitDomainCapability orderUnitDomainCapability;
    private CsFedScheduleDomainCapability csFedScheduleDomainCapability;
    private ProductDomainCapability productDomainCapability;
    
    // MigrationNDCImportTest
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;
    private DrugTextDomainCapability drugTextDomainCapability;
    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;
    private FdbNdcDomainCapability fdbNdcDomainCapability;
    private FssInterfaceCapability fssInterfaceCapability;
     

    /**
     * Get instance of {@link RulesCapability} for MigrationNDCImportTest
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        migrationCapability = SpringTestConfigUtility.getNationalSpringBean(MigrationCapability.class);
        standardMedRouteDomainCapability = getNationalCapability(StandardMedRouteDomainCapability.class);
      
        drugUnitDomainCapability = getNationalCapability(DrugUnitDomainCapability.class);
        dispenseUnitDomainCapability = getNationalCapability(DispenseUnitDomainCapability.class);
        genericNameDomainCapability = getNationalCapability(GenericNameDomainCapability.class);
        ingredientDomainCapability = getNationalCapability(IngredientDomainCapability.class);
        drugClassDomainCapability = getNationalCapability(DrugClassDomainCapability.class);
        dosageFormDomainCapability = getNationalCapability(DosageFormDomainCapability.class);
        resetNationalDatabaseDomainCapability = getNationalCapability(ResetNationalDatabaseDomainCapability.class);
        
        managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        itemAuditHistoryDomainCapability = getNationalCapability(ItemAuditHistoryDomainCapability.class);
        migrationErrorDomainCapability = getNationalCapability(MigrationErrorDomainCapability.class);
        specialHandlingDomainCapability = getNationalCapability(SpecialHandlingDomainCapability.class);
        productDomainCapability = getNationalCapability(ProductDomainCapability.class);
        orderableItemDomainCapability = getNationalCapability(OrderableItemDomainCapability.class);
        manufacturerDomainCapability = getNationalCapability(ManufacturerDomainCapability.class);
        packageTypeDomainCapability = getNationalCapability(PackageTypeDomainCapability.class);
        ndcDomainCapability = getNationalCapability(NdcDomainCapability.class);
        drugTextDomainCapability = getNationalCapability(DrugTextDomainCapability.class); 
        fdbNdcDomainCapability = getNationalCapability(FdbNdcDomainCapability.class);
        fssInterfaceCapability = getNationalCapability(FssInterfaceCapability.class);

        intendedUseDomainCapability = getNationalCapability(IntendedUseDomainCapability.class);
        orderUnitDomainCapability = getNationalCapability(OrderUnitDomainCapability.class);
        csFedScheduleDomainCapability = getNationalCapability(CsFedScheduleDomainCapability.class);
        
        
        migrationCapability.setStandardMedRouteDomainCapability(standardMedRouteDomainCapability);
        migrationCapability.setDrugUnitDomainCapability(drugUnitDomainCapability);
        migrationCapability.setDispenseUnitDomainCapability(dispenseUnitDomainCapability);
        
        migrationCapability.setGenericNameDomainCapability(genericNameDomainCapability);
        
        migrationCapability.setItemAuditHistoryDomainCapability(itemAuditHistoryDomainCapability);
        migrationCapability.setMigrationErrorDomainCapability(migrationErrorDomainCapability);
        migrationCapability.setSpecialHandlingDomainCapability(specialHandlingDomainCapability);
        migrationCapability.setIntendedUseDomainCapability(intendedUseDomainCapability);
        migrationCapability.setIngredientDomainCapability(ingredientDomainCapability);
        migrationCapability.setDrugClassDomainCapability(drugClassDomainCapability);
        migrationCapability.setDosageFormDomainCapability(dosageFormDomainCapability);
        migrationCapability.setManagedItemCapability(managedItemCapability);
      
        migrationCapability.setOrderUnitDomainCapability(orderUnitDomainCapability);
        migrationCapability.setCsFedScheduleDomainCapability(csFedScheduleDomainCapability);
        migrationCapability.setProductDomainCapability(productDomainCapability);
        migrationCapability.setOrderableItemDomainCapability(orderableItemDomainCapability);
        migrationCapability.setManufacturerDomainCapability(manufacturerDomainCapability);
        migrationCapability.setPackageTypeDomainCapability(packageTypeDomainCapability);
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);
        migrationCapability.setResetNationalDatabaseDomainCapability(resetNationalDatabaseDomainCapability);
        migrationCapability.setDrugTextDomainCapability(drugTextDomainCapability);
        migrationCapability.setFdbNdcDomainCapability(fdbNdcDomainCapability);
        migrationCapability.setFssInterfaceCapability(fssInterfaceCapability);
        
        migrationCapability.init();
    }
   
    /**
     * clear
     */
    protected void clear() {
        migrationCapability.clear();
        migrationCapability = null;
        resetNationalDatabaseDomainCapability = null;
        managedItemCapability = null;
        ndcDomainCapability = null;
        drugUnitDomainCapability = null;
        
        // set these domain capabilities to null for the MigrationNDCImportTest.
        dispenseUnitDomainCapability = null;
        dosageFormDomainCapability = null;
        ingredientDomainCapability = null;
        drugClassDomainCapability = null;
        genericNameDomainCapability = null;
     
        itemAuditHistoryDomainCapability = null;
        migrationErrorDomainCapability = null;
        specialHandlingDomainCapability = null;
        
     // set these domain capabilities to null for the MigrationNDCImportTest.
        intendedUseDomainCapability = null;
        orderUnitDomainCapability = null;
        csFedScheduleDomainCapability = null;
        productDomainCapability = null;
        orderableItemDomainCapability = null;
        manufacturerDomainCapability = null;
        packageTypeDomainCapability = null;
        fdbNdcDomainCapability = null;
    };

    /**
     * NDC Test
     */
    public void testImportNDCs() {

        String testFilePathIn = System.getProperty(PROP_USER_DIR).concat(DIR_PATH_CSV);
        String strFileName = testFilePathIn.concat("NDCChrisTestFile.csv");
        NdcCsvFile ndcCsvFile = new NdcCsvFile();
        
        try {
            ndcCsvFile.openForImport(strFileName);
        } catch (MigrationException e) {
            fail(strFileName + EXCEPT_MIGRATION_END_MSG + e.toString());
        } catch (Exception xe) {
            fail(strFileName + EXCEPT_UNCAUGHT_END_MSG + xe.toString());
        }

        int iSuccess = 0;
        int iFailure = 0;
        int iDuplicate = 0;
        int iPackages = 0;
        int iManufacturers = 0;

        // set the NDC Domain Capability for the testImportNDCs method
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);

        while (true) {
            NdcVo ndcVo = (NdcVo) managedItemCapability.retrieveBlankTemplate(EntityType.NDC);

            try {
                NdcVo ndcVo2 = ndcCsvFile.getNextNdc(ndcVo);

                // break if the NDC is null for the testImportNDCs method
                if (ndcVo2 == null) {
                    break;
                } else {
                    MigrationVariablesVo vo = migrationCapability.migrateNDCs(ndcVo2);
                    iFailure += vo.getIErroredOnMigration();
                    iDuplicate += vo.getIDuplicatesNotMigrated();
                    iSuccess += vo.getISuccessfullyMigrated();
                    iManufacturers += vo.getINumManufacturersMigrated();
                    iPackages += vo.getINumPackageTypesMigrated();
                }
            } catch (MigrationException me) {
                migrationCapability.saveMigrationErrorMessage(PPSConstants.VA_NDC_FILE, me);
                iFailure++;
            } catch (Exception e) {
                LOG.error("  ERROR: " + e.toString());
            }

        }

        LOG.info("  Succesfully Migrated: " + iSuccess);
        LOG.info("  Failed Migration: " + iFailure);
        LOG.info("  Failed Dupliate: " + iDuplicate);
        LOG.info("  Package Types Migrated: " + iPackages);
        LOG.info("  Manufacturers Migrated: " + iManufacturers);

        assertEquals("Should have been 37 successful NDCs in file", PPSConstants.I37, iSuccess);
        assertEquals("Should have been 1 failure in file", 1, iFailure);
        assertEquals("Should have been 14 Migrationed Manufacturers", PPSConstants.I14, iManufacturers);
        assertEquals("Should have been 4 Migrated PackageTypes", PPSConstants.I4, iPackages);
        
        clear();

    }

    /**
     * NDC Test
     */
    public void testImportOneNDC() {

        String testFilePathIn = System.getProperty(PROP_USER_DIR).concat(DIR_PATH_CSV);
        String strFileName = testFilePathIn.concat("ndc_atp_test.csv");

        NdcCsvFile ndcCsvFile = new NdcCsvFile();

        try {
            ndcCsvFile.openForImport(strFileName);
        } catch (MigrationException me) {
            fail(strFileName + EXCEPT_MIGRATION_END_MSG + me.toString());
        } catch (Exception e) {
            fail(strFileName + EXCEPT_UNCAUGHT_END_MSG + e.toString());
        }

        int iSuccess = 0;
        int iFailure = 0;
        int iDuplicate = 0;
        int iPackages = 0;
        int iManufacturers = 0;

        migrationCapability.setNdcDomainCapability(ndcDomainCapability);

        while (true) {
            NdcVo ndcVo = (NdcVo) managedItemCapability.retrieveBlankTemplate(EntityType.NDC);

            try {
                NdcVo ndcVo2 = ndcCsvFile.getNextNdc(ndcVo);

                if (ndcVo2 == null) {
                    break;
                } else {
                    MigrationVariablesVo vo = migrationCapability.migrateNDCs(ndcVo2);
                    iFailure += vo.getIErroredOnMigration();
                    iDuplicate += vo.getIDuplicatesNotMigrated();
                    iSuccess += vo.getISuccessfullyMigrated();
                    iManufacturers += vo.getINumManufacturersMigrated();
                    iPackages += vo.getINumPackageTypesMigrated();
                }
            } catch (MigrationException me) {
                migrationCapability.saveMigrationErrorMessage("50.67", me);
                iFailure++;
            } catch (Exception e) {
                LOG.error("ERROR: " + e.toString());
            }

        }

        LOG.info("Succesfully Migrated: " + iSuccess);
        LOG.info("Failed Migration: " + iFailure);
        LOG.info("Failed Dupliate: " + iDuplicate);
        LOG.info("Package Types Migrated: " + iPackages);
        LOG.info("Manufacturers Migrated: " + iManufacturers);
        clear();

    }

}
