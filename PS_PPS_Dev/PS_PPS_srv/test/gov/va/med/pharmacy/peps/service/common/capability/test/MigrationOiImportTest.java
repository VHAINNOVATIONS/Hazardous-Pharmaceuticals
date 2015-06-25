/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.domain.common.capability.CsFedScheduleDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DispenseUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DosageFormDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugClassDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugTextDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugUnitDomainCapability;
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
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.MigrationCapability;
import gov.va.med.pharmacy.peps.service.common.utility.OiCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;




/**
 * Test the base class of MigrationCapability. Also test that a sub class still has access to the super's Spring injected
 * classes.
 */
public class MigrationOiImportTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(MigrationOiImportTest.class);
 
    private static final String EXCEPT_MIGRATION_END_MSG = " threw a Migration exception. ";
    private static final String EXCEPT_UNCAUGHT_END_MSG = " threw an uncaught exception. ";

    private static final String PROP_USER_DIR = "user.dir";
    private static final String DIR_PATH_CSV = "/etc/csv/test/";

    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private MigrationCapability migrationCapability;
    private NdcDomainCapability ndcDomainCapability;
    
    //private MigrationRequestCapability migrationRequestCapablity;

    // migrationOiImportTest
    private DrugUnitDomainCapability drugUnitDomainCapability;
    private DispenseUnitDomainCapability dispenseUnitDomainCapability;
    private DosageFormDomainCapability dosageFormDomainCapability;
    private IngredientDomainCapability ingredientDomainCapability;
    private DrugClassDomainCapability drugClassDomainCapability;
    private GenericNameDomainCapability genericNameDomainCapability;
   
    // migrationOiImportTest
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private MigrationErrorDomainCapability migrationErrorDomainCapability;
    private SpecialHandlingDomainCapability specialHandlingDomainCapability;
    private IntendedUseDomainCapability intendedUseDomainCapability;
    private OrderUnitDomainCapability orderUnitDomainCapability;
    private CsFedScheduleDomainCapability csFedScheduleDomainCapability;
    private ProductDomainCapability productDomainCapability;
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;
    private DrugTextDomainCapability drugTextDomainCapability;
    
    // migrationOiImportTest
    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;

    /**
     * Get instance of {@link RulesCapability}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        // migrationOiImportTest
        migrationCapability = SpringTestConfigUtility.getNationalSpringBean(MigrationCapability.class);
        resetNationalDatabaseDomainCapability = getNationalCapability(ResetNationalDatabaseDomainCapability.class);
        drugUnitDomainCapability = getNationalCapability(DrugUnitDomainCapability.class);
        dispenseUnitDomainCapability = getNationalCapability(DispenseUnitDomainCapability.class);
        genericNameDomainCapability = getNationalCapability(GenericNameDomainCapability.class);
        ingredientDomainCapability = getNationalCapability(IngredientDomainCapability.class);
        drugClassDomainCapability = getNationalCapability(DrugClassDomainCapability.class);
        dosageFormDomainCapability = getNationalCapability(DosageFormDomainCapability.class);
        managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        
        // migrationOiImportTest
        itemAuditHistoryDomainCapability = getNationalCapability(ItemAuditHistoryDomainCapability.class);
        migrationErrorDomainCapability = getNationalCapability(MigrationErrorDomainCapability.class);
        specialHandlingDomainCapability = getNationalCapability(SpecialHandlingDomainCapability.class);
        intendedUseDomainCapability = getNationalCapability(IntendedUseDomainCapability.class);
        orderUnitDomainCapability = getNationalCapability(OrderUnitDomainCapability.class);
        csFedScheduleDomainCapability = getNationalCapability(CsFedScheduleDomainCapability.class);
        productDomainCapability = getNationalCapability(ProductDomainCapability.class);
        orderableItemDomainCapability = getNationalCapability(OrderableItemDomainCapability.class);
        manufacturerDomainCapability = getNationalCapability(ManufacturerDomainCapability.class);
        
            // migrationOiImportTest;
        packageTypeDomainCapability = getNationalCapability(PackageTypeDomainCapability.class);
        ndcDomainCapability = getNationalCapability(NdcDomainCapability.class);
        drugTextDomainCapability = getNationalCapability(DrugTextDomainCapability.class); 
        standardMedRouteDomainCapability = getNationalCapability(StandardMedRouteDomainCapability.class);

        // migrationOiImportTest
        migrationCapability.setDrugUnitDomainCapability(drugUnitDomainCapability);
        migrationCapability.setDispenseUnitDomainCapability(dispenseUnitDomainCapability);
        migrationCapability.setGenericNameDomainCapability(genericNameDomainCapability);
        migrationCapability.setIngredientDomainCapability(ingredientDomainCapability);
        migrationCapability.setDrugClassDomainCapability(drugClassDomainCapability);
        migrationCapability.setDosageFormDomainCapability(dosageFormDomainCapability);
        migrationCapability.setManagedItemCapability(managedItemCapability);
        migrationCapability.setItemAuditHistoryDomainCapability(itemAuditHistoryDomainCapability);
        migrationCapability.setMigrationErrorDomainCapability(migrationErrorDomainCapability);
        
        // migrationOiImportTest
        migrationCapability.setSpecialHandlingDomainCapability(specialHandlingDomainCapability);
        migrationCapability.setIntendedUseDomainCapability(intendedUseDomainCapability);
        migrationCapability.setOrderUnitDomainCapability(orderUnitDomainCapability);
        migrationCapability.setCsFedScheduleDomainCapability(csFedScheduleDomainCapability);
        migrationCapability.setProductDomainCapability(productDomainCapability);
        migrationCapability.setOrderableItemDomainCapability(orderableItemDomainCapability);
        migrationCapability.setManufacturerDomainCapability(manufacturerDomainCapability);
        migrationCapability.setPackageTypeDomainCapability(packageTypeDomainCapability);
        
        // migrationOiImportTest
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);
        migrationCapability.setResetNationalDatabaseDomainCapability(resetNationalDatabaseDomainCapability);
        migrationCapability.setDrugTextDomainCapability(drugTextDomainCapability);
        migrationCapability.setStandardMedRouteDomainCapability(standardMedRouteDomainCapability);
        
        migrationCapability.init();
    }
   
    /**
     * migrationOiImportTestclear
     */
    protected void clear() {
        
        // migrationOiImportTest
        migrationCapability.clear();
        migrationCapability = null;
        resetNationalDatabaseDomainCapability = null;
        managedItemCapability = null;
        ndcDomainCapability = null;
        drugUnitDomainCapability = null;
        dispenseUnitDomainCapability = null;
        
        // migrationOiImportTest
        dosageFormDomainCapability = null;
        ingredientDomainCapability = null;
        drugClassDomainCapability = null;
        genericNameDomainCapability = null;
     
        itemAuditHistoryDomainCapability = null;
        migrationErrorDomainCapability = null;
        specialHandlingDomainCapability = null;
        intendedUseDomainCapability = null;
        orderUnitDomainCapability = null;
        
        // migrationOiImportTest
        csFedScheduleDomainCapability = null;
        productDomainCapability = null;
        orderableItemDomainCapability = null;
        manufacturerDomainCapability = null;
        packageTypeDomainCapability = null;
        standardMedRouteDomainCapability = null;
    };

    /**
     * testOIImportFromCSV
     */
    public void testOIImportFromCSV() {

        String testFilePathIn = System.getProperty(PROP_USER_DIR).concat(DIR_PATH_CSV);
        String strFileName = testFilePathIn.concat("ATP_OIData.csv");
        OiCsvFile oiCsvFile = new OiCsvFile();

        try {
            oiCsvFile.openForImport(strFileName);
        } catch (MigrationException me) {
            fail(strFileName + EXCEPT_MIGRATION_END_MSG + me.toString());
        } catch (Exception e) {
            fail(strFileName + EXCEPT_UNCAUGHT_END_MSG + e.toString());
        }

        int iSuccess = 0;
        int iFailure = 0;
        int iDuplicate = 0;
        OrderableItemVo oiVo = null;

        try {

            while (true) {
                oiVo = (OrderableItemVo) managedItemCapability.retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);

                try {
                    oiVo = oiCsvFile.getNextOi(oiVo);

                    if (oiVo == null) {
                        break;
                    } else {
                        MigrationVariablesVo migrationVariablesVo = migrationCapability.migrateOrderabeItems(oiVo);
                        iSuccess += migrationVariablesVo.getISuccessfullyMigrated();
                        iFailure += migrationVariablesVo.getIErroredOnMigration();
                        iDuplicate += migrationVariablesVo.getIDuplicatesNotMigrated();
                    }

                } catch (MigrationException me) {
                    migrationCapability.saveMigrationErrorMessage("50.7", me);
                    iFailure++;
                }
            }
        } catch (Exception e) {
            fail(strFileName + " threw an unknown exception. " + e.toString() + " for OI " + oiVo.getOiName());
        }

        LOG.info("Orderable Item Success=" + iSuccess);
        LOG.info("Orderable Item Failures=" + iFailure);
        LOG.info("Orderable Item Duplicates=" + iDuplicate);

        assertEquals("Should have been 23 successful OIs in file", PPSConstants.I23, iSuccess);
        assertEquals("Should have been 11 failures in file", PPSConstants.I11, iFailure);
        assertEquals("Should have been 0 duplicates in file", 0, iDuplicate);

        clear();
    }


}
