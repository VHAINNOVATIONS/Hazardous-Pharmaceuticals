/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;


import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.MigrationErrorVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
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
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;





/**
 * Test the base class of MigrationCapability. Also test that a sub class still has access to the super's Spring injected
 * classes.
 */
public class MigrationDrugUnitTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(MigrationDrugUnitTest.class);

    private static final String EXPECT_STR = "Expecting string equivalent value";
    private static final String EXPECT_NUM = "Expecting numeric equivalent value";
    private static final String EXPECT_EOF = "Expecting end of file";
    private static final String DRUG_UNIT_FILE = "50.607";
    

    // MigrationDrugUnitTest
    private MigrationCapability migrationCapability;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private NdcDomainCapability ndcDomainCapability;
   
    // MigrationDrugUnitTest
    private DrugUnitDomainCapability drugUnitDomainCapability;
    private DispenseUnitDomainCapability dispenseUnitDomainCapability;
    private DosageFormDomainCapability dosageFormDomainCapability;
    private IngredientDomainCapability ingredientDomainCapability;
    private DrugClassDomainCapability drugClassDomainCapability;
    private GenericNameDomainCapability genericNameDomainCapability;
   
    // MigrationDrugUnitTest
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private MigrationErrorDomainCapability migrationErrorDomainCapability;
    private SpecialHandlingDomainCapability specialHandlingDomainCapability;
    private IntendedUseDomainCapability intendedUseDomainCapability;
    private OrderUnitDomainCapability orderUnitDomainCapability;
    private CsFedScheduleDomainCapability csFedScheduleDomainCapability;
    private ProductDomainCapability productDomainCapability;
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private ManufacturerDomainCapability manufacturerDomainCapability;
    
    // MigrationDrugUnitTest
    private PackageTypeDomainCapability packageTypeDomainCapability;
    private DrugTextDomainCapability drugTextDomainCapability;
    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;
    

    /**
     * MigrationDrugUnitTestGet instance of {@link RulesCapability}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        // MigrationDrugUnitTest
        migrationCapability = SpringTestConfigUtility.getNationalSpringBean(MigrationCapability.class);
        resetNationalDatabaseDomainCapability = getNationalCapability(ResetNationalDatabaseDomainCapability.class);
        drugUnitDomainCapability = getNationalCapability(DrugUnitDomainCapability.class);
        dispenseUnitDomainCapability = getNationalCapability(DispenseUnitDomainCapability.class);
        genericNameDomainCapability = getNationalCapability(GenericNameDomainCapability.class);
        specialHandlingDomainCapability = getNationalCapability(SpecialHandlingDomainCapability.class);
        intendedUseDomainCapability = getNationalCapability(IntendedUseDomainCapability.class);
        ingredientDomainCapability = getNationalCapability(IngredientDomainCapability.class);
        drugClassDomainCapability = getNationalCapability(DrugClassDomainCapability.class);
        dosageFormDomainCapability = getNationalCapability(DosageFormDomainCapability.class);
        managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        itemAuditHistoryDomainCapability = getNationalCapability(ItemAuditHistoryDomainCapability.class);
        migrationErrorDomainCapability = getNationalCapability(MigrationErrorDomainCapability.class);
       
        
        // MigrationDrugUnitTest
        orderUnitDomainCapability = getNationalCapability(OrderUnitDomainCapability.class);
        csFedScheduleDomainCapability = getNationalCapability(CsFedScheduleDomainCapability.class);
        productDomainCapability = getNationalCapability(ProductDomainCapability.class);
        orderableItemDomainCapability = getNationalCapability(OrderableItemDomainCapability.class);
        manufacturerDomainCapability = getNationalCapability(ManufacturerDomainCapability.class);
        packageTypeDomainCapability = getNationalCapability(PackageTypeDomainCapability.class);
        ndcDomainCapability = getNationalCapability(NdcDomainCapability.class);
        drugTextDomainCapability = getNationalCapability(DrugTextDomainCapability.class); 
        standardMedRouteDomainCapability = getNationalCapability(StandardMedRouteDomainCapability.class);

        // MigrationDrugUnitTest
        migrationCapability.setDrugUnitDomainCapability(drugUnitDomainCapability);
        migrationCapability.setDispenseUnitDomainCapability(dispenseUnitDomainCapability);
        migrationCapability.setGenericNameDomainCapability(genericNameDomainCapability);
        migrationCapability.setIngredientDomainCapability(ingredientDomainCapability);
        migrationCapability.setDrugClassDomainCapability(drugClassDomainCapability);
        migrationCapability.setDosageFormDomainCapability(dosageFormDomainCapability);
        migrationCapability.setManagedItemCapability(managedItemCapability);
      
        migrationCapability.setOrderUnitDomainCapability(orderUnitDomainCapability);
        migrationCapability.setCsFedScheduleDomainCapability(csFedScheduleDomainCapability);
        
        migrationCapability.setItemAuditHistoryDomainCapability(itemAuditHistoryDomainCapability);
        migrationCapability.setMigrationErrorDomainCapability(migrationErrorDomainCapability);
        migrationCapability.setSpecialHandlingDomainCapability(specialHandlingDomainCapability);
        migrationCapability.setIntendedUseDomainCapability(intendedUseDomainCapability);
        
        // MigrationDrugUnitTest
        migrationCapability.setProductDomainCapability(productDomainCapability);
        migrationCapability.setOrderableItemDomainCapability(orderableItemDomainCapability);
        migrationCapability.setManufacturerDomainCapability(manufacturerDomainCapability);
        migrationCapability.setPackageTypeDomainCapability(packageTypeDomainCapability);
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);
        migrationCapability.setResetNationalDatabaseDomainCapability(resetNationalDatabaseDomainCapability);
        migrationCapability.setDrugTextDomainCapability(drugTextDomainCapability);
        migrationCapability.setStandardMedRouteDomainCapability(standardMedRouteDomainCapability);
        
        // MigrationDrugUnitTest
        migrationCapability.init();
    }
   
    /**
     * MigrationDrugUnitTestclear
     */
    protected void clear() {
        migrationCapability.clear();
        migrationCapability = null;
        resetNationalDatabaseDomainCapability = null;
        managedItemCapability = null;
        ndcDomainCapability = null;
        drugUnitDomainCapability = null;
        dispenseUnitDomainCapability = null;
        
        // MigrationDrugUnitTest
        dosageFormDomainCapability = null;
        ingredientDomainCapability = null;
        drugClassDomainCapability = null;
        genericNameDomainCapability = null;
     
        // MigrationDrugUnitTest
        itemAuditHistoryDomainCapability = null;
        migrationErrorDomainCapability = null;
        specialHandlingDomainCapability = null;
        intendedUseDomainCapability = null;
        orderUnitDomainCapability = null;
        csFedScheduleDomainCapability = null;
        productDomainCapability = null;
        orderableItemDomainCapability = null;
        manufacturerDomainCapability = null;
        
        // MigrationDrugUnitTest
        packageTypeDomainCapability = null;
        standardMedRouteDomainCapability = null;
    };

    /**
     * MigrationDrugUnitTestSend in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testActiveDrugUnits() {

        MigrationVariablesVo vo = migrationCapability.migrateDrugUnits("0", PPSConstants.I37, 1);
        List<MigrationErrorVo> list = migrationCapability.retrieveMigrationErrorMessages(DRUG_UNIT_FILE);

        LOG.info("Active Drug Unit getStrLastIEN=" + vo.getStrLastIEN());
        LOG.info("Active Drug Unit Success=" + vo.getISuccessfullyMigrated());
        LOG.info("Active Drug Unit FailureN=" + vo.getIErroredOnMigration());
        LOG.info("Active Drug Unit ErrorVO size is =" + list.size());
        LOG.info("Active Drug Unit Duplicates=" + vo.getIDuplicatesNotMigrated());
        LOG.info("Active Drug Unit EOF=" + vo.isEndOfFile());

        assertEquals(EXPECT_STR, "677", vo.getStrLastIEN());
        assertEquals(EXPECT_NUM, PPSConstants.I5, vo.getISuccessfullyMigrated());
        assertEquals(EXPECT_NUM, PPSConstants.I3, vo.getIErroredOnMigration());

        if (list.size() < 2) {
            assertTrue("The List size of " + list.size() + "  should be at least 2 if not greater", list.size() >= 2);
        }

        assertEquals(EXPECT_NUM, 0, vo.getIDuplicatesNotMigrated());
        assertTrue(EXPECT_EOF, vo.isEndOfFile());
  
        vo = migrationCapability.migrateDrugUnits("0", PPSConstants.I37, 0);
        list = migrationCapability.retrieveMigrationErrorMessages(DRUG_UNIT_FILE);

        LOG.info("Inactive Drug Unit getStrLastIEN=" + vo.getStrLastIEN());
        LOG.info("Inactive Drug Unit Success=" + vo.getISuccessfullyMigrated());
        LOG.info("Inactive Drug Unit FailureN=" + vo.getIErroredOnMigration());
        LOG.info("Inactive Drug Unit ErrorVO size is =" + list.size());
        LOG.info("Inactive Drug Unit Duplicates=" + vo.getIDuplicatesNotMigrated());
        LOG.info("Inactive Drug Unit EOF=" + vo.isEndOfFile());

        assertEquals(EXPECT_STR, "54", vo.getStrLastIEN());
        assertEquals(EXPECT_NUM, PPSConstants.I7, vo.getISuccessfullyMigrated());
        assertEquals(EXPECT_NUM, 2, vo.getIErroredOnMigration());
        assertEquals(EXPECT_NUM, 2, vo.getIDuplicatesNotMigrated());

        if (list.size() < 2) {
            assertTrue("List size " + list.size() + " should be at least 2 if not greater", list.size() >= 2);
        }

        assertTrue(EXPECT_EOF, vo.isEndOfFile());
        clear();
    }

}
