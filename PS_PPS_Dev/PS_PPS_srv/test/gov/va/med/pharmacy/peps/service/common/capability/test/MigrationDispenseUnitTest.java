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
public class MigrationDispenseUnitTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(MigrationDispenseUnitTest.class);

    private static final String EXPECT_STR = "Expecting string equivalent value";
    private static final String EXPECT_NUM = "Expecting numeric equivalent value";
    private static final String EXPECT_EOF = "Expecting end of file";
    private static final String DISPENSE_UNIT_FILE = "50.64";
    
    private MigrationCapability migrationCapability;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private NdcDomainCapability ndcDomainCapability;
    
    //private MigrationRequestCapability migrationRequestCapablity;

    private IngredientDomainCapability ingredientDomainCapability;
    private DrugClassDomainCapability drugClassDomainCapability;
    private GenericNameDomainCapability genericNameDomainCapability;
    private DrugUnitDomainCapability drugUnitDomainCapability;
    private DispenseUnitDomainCapability dispenseUnitDomainCapability;
    private DosageFormDomainCapability dosageFormDomainCapability;
  
   
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private MigrationErrorDomainCapability migrationErrorDomainCapability;
    private SpecialHandlingDomainCapability specialHandlingDomainCapability;
    private IntendedUseDomainCapability intendedUseDomainCapability;
    private OrderUnitDomainCapability orderUnitDomainCapability;
    private CsFedScheduleDomainCapability csFedScheduleDomainCapability;
    private ProductDomainCapability productDomainCapability;
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private DrugTextDomainCapability drugTextDomainCapability;
    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;
    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;
 
    

    /**
     * Get instance of MigrationDispenseUnit setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        migrationCapability = SpringTestConfigUtility.getNationalSpringBean(MigrationCapability.class);
        migrationErrorDomainCapability = getNationalCapability(MigrationErrorDomainCapability.class);
        specialHandlingDomainCapability = getNationalCapability(SpecialHandlingDomainCapability.class);
        intendedUseDomainCapability = getNationalCapability(IntendedUseDomainCapability.class);
        resetNationalDatabaseDomainCapability = getNationalCapability(ResetNationalDatabaseDomainCapability.class);
        drugUnitDomainCapability = getNationalCapability(DrugUnitDomainCapability.class);
        dispenseUnitDomainCapability = getNationalCapability(DispenseUnitDomainCapability.class);
        genericNameDomainCapability = getNationalCapability(GenericNameDomainCapability.class);
        ingredientDomainCapability = getNationalCapability(IngredientDomainCapability.class);
        drugClassDomainCapability = getNationalCapability(DrugClassDomainCapability.class);
        dosageFormDomainCapability = getNationalCapability(DosageFormDomainCapability.class);
        managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        itemAuditHistoryDomainCapability = getNationalCapability(ItemAuditHistoryDomainCapability.class);
    
        orderUnitDomainCapability = getNationalCapability(OrderUnitDomainCapability.class);
        csFedScheduleDomainCapability = getNationalCapability(CsFedScheduleDomainCapability.class);
        productDomainCapability = getNationalCapability(ProductDomainCapability.class);
        orderableItemDomainCapability = getNationalCapability(OrderableItemDomainCapability.class);
        manufacturerDomainCapability = getNationalCapability(ManufacturerDomainCapability.class);
        packageTypeDomainCapability = getNationalCapability(PackageTypeDomainCapability.class);
    
        drugTextDomainCapability = getNationalCapability(DrugTextDomainCapability.class); 
        standardMedRouteDomainCapability = getNationalCapability(StandardMedRouteDomainCapability.class);
        ndcDomainCapability = getNationalCapability(NdcDomainCapability.class);
        migrationCapability.setDrugUnitDomainCapability(drugUnitDomainCapability);
        migrationCapability.setDispenseUnitDomainCapability(dispenseUnitDomainCapability);
        migrationCapability.setGenericNameDomainCapability(genericNameDomainCapability);
        migrationCapability.setIngredientDomainCapability(ingredientDomainCapability);
        migrationCapability.setDrugClassDomainCapability(drugClassDomainCapability);
   
        migrationCapability.setManagedItemCapability(managedItemCapability);
        migrationCapability.setDosageFormDomainCapability(dosageFormDomainCapability);
        migrationCapability.setItemAuditHistoryDomainCapability(itemAuditHistoryDomainCapability);
        migrationCapability.setMigrationErrorDomainCapability(migrationErrorDomainCapability);
        migrationCapability.setSpecialHandlingDomainCapability(specialHandlingDomainCapability);
        migrationCapability.setIntendedUseDomainCapability(intendedUseDomainCapability);
        migrationCapability.setOrderUnitDomainCapability(orderUnitDomainCapability);
        migrationCapability.setCsFedScheduleDomainCapability(csFedScheduleDomainCapability);
     
        migrationCapability.setOrderableItemDomainCapability(orderableItemDomainCapability);
        migrationCapability.setProductDomainCapability(productDomainCapability);
        migrationCapability.setManufacturerDomainCapability(manufacturerDomainCapability);
        migrationCapability.setPackageTypeDomainCapability(packageTypeDomainCapability);
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);
        migrationCapability.setResetNationalDatabaseDomainCapability(resetNationalDatabaseDomainCapability);
        migrationCapability.setDrugTextDomainCapability(drugTextDomainCapability);
        migrationCapability.setStandardMedRouteDomainCapability(standardMedRouteDomainCapability);
        
        //MigrationDispenseUnitTest init()
        migrationCapability.init();
    }
   
    /**
     * MigrationDispenseUnitTest clear
     */
    protected void clear() {
        migrationCapability.clear();
        migrationCapability = null;
        resetNationalDatabaseDomainCapability = null;
        managedItemCapability = null;
        ndcDomainCapability = null;
        drugUnitDomainCapability = null;
        dispenseUnitDomainCapability = null;
        
        // MigrationDispenseUnit clear fields
        dosageFormDomainCapability = null;
        ingredientDomainCapability = null;
        drugClassDomainCapability = null;
        genericNameDomainCapability = null;
     
        // MigrationDispenseUnit clear fields
        itemAuditHistoryDomainCapability = null;
        migrationErrorDomainCapability = null;
        specialHandlingDomainCapability = null;
        intendedUseDomainCapability = null;
        orderUnitDomainCapability = null;
        csFedScheduleDomainCapability = null;
        productDomainCapability = null;
        
        // MigrationDispenseUnit clear fields
        orderableItemDomainCapability = null;
        manufacturerDomainCapability = null;
        packageTypeDomainCapability = null;
        standardMedRouteDomainCapability = null;
    }
    
    /**
     * testActiveDispenseUnits.
     */
    public void testActiveDispenseUnits() {

        MigrationVariablesVo vo = migrationCapability.migrateDispenseUnits("0", PPSConstants.I37, 1);
        List<MigrationErrorVo> list = migrationCapability.retrieveMigrationErrorMessages(DISPENSE_UNIT_FILE);

        LOG.info("Active Dispense Unit getStrLastIEN=" + vo.getStrLastIEN());
        LOG.info("Active Dispense Unit Success=" + vo.getISuccessfullyMigrated());
        LOG.info("Active Dispense Unit FailureN=" + vo.getIErroredOnMigration());
        LOG.info("Active Dispense Unit ErrorVO size is =" + list.size());
        LOG.info("Active Dispense Unit Duplicates=" + vo.getIDuplicatesNotMigrated());
        LOG.info("Active Dispense Unit isEOF=" + vo.isEndOfFile());

        assertEquals(EXPECT_STR, "8", vo.getStrLastIEN());
        assertEquals(EXPECT_NUM, 2, vo.getISuccessfullyMigrated());
        assertEquals(EXPECT_NUM, 1, vo.getIErroredOnMigration());
        assertEquals(EXPECT_NUM, 0, vo.getIDuplicatesNotMigrated());

        if (list.size() < 1) {
            assertTrue("The List size of " + list.size() + " should be at least 1 if not greater", list.size() >= 1);
        }

        assertTrue(EXPECT_EOF, vo.isEndOfFile());
    

        vo = migrationCapability.migrateDispenseUnits("0", PPSConstants.I37, 0);
        list = migrationCapability.retrieveMigrationErrorMessages(DISPENSE_UNIT_FILE);

        LOG.info("Inactive Dispense Unit getStrLastIEN=" + vo.getStrLastIEN());
        LOG.info("Inactive Dispense Unit Success=" + vo.getISuccessfullyMigrated());
        LOG.info("Inactive Dispense Unit FailureN=" + vo.getIErroredOnMigration());
        LOG.info("Inactive Dispense Unit ErrorVO size is =" + list.size());
        LOG.info("Inactive Dispense Unit Duplicates=" + vo.getIDuplicatesNotMigrated());
        LOG.info("Inactive Dispense Unit isEOF=" + vo.isEndOfFile());

        assertEquals(EXPECT_STR, "54", vo.getStrLastIEN());
        assertEquals(EXPECT_NUM, PPSConstants.I10, vo.getISuccessfullyMigrated());
        assertEquals(EXPECT_NUM, 2, vo.getIErroredOnMigration());
        assertEquals(EXPECT_NUM, 1, vo.getIDuplicatesNotMigrated());

        if (list.size() < 2) {
            assertTrue("List size " + list.size() + " should be at least 2 if not greater", list.size() >= 2);
        }

        assertTrue(EXPECT_EOF, vo.isEndOfFile());
        clear();
    }    

}
