/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
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
public class MigrationDrugClassTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(MigrationDrugClassTest.class);

    private static final String EXPECT_STR = "Expecting string equivalent value";
    private static final String EXPECT_NUM = "Expecting numeric equivalent value";
    private static final String EXPECT_EOF = "Expecting end of file";
    
    // MigrationDrugClassTest
    private MigrationCapability migrationCapability;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private NdcDomainCapability ndcDomainCapability;
    
    //private MigrationRequestCapability migrationRequestCapablity;

    // MigrationDrugClassTest
    private DrugUnitDomainCapability drugUnitDomainCapability;
    private DispenseUnitDomainCapability dispenseUnitDomainCapability;
    private DosageFormDomainCapability dosageFormDomainCapability;
    private IngredientDomainCapability ingredientDomainCapability;
    private DrugClassDomainCapability drugClassDomainCapability;
    private GenericNameDomainCapability genericNameDomainCapability;
   
    // MigrationDrugClassTest
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private MigrationErrorDomainCapability migrationErrorDomainCapability;
    private SpecialHandlingDomainCapability specialHandlingDomainCapability;
    private IntendedUseDomainCapability intendedUseDomainCapability;
    private OrderUnitDomainCapability orderUnitDomainCapability;
    private CsFedScheduleDomainCapability csFedScheduleDomainCapability;
    private ProductDomainCapability productDomainCapability;
    
    // MigrationDrugClassTest
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;
    private DrugTextDomainCapability drugTextDomainCapability;
    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;
    

    /**
     * MigrationDrugClassTestGet instance of {@link RulesCapability}
     *      * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        // MigrationDrugClassTest
        migrationCapability = SpringTestConfigUtility.getNationalSpringBean(MigrationCapability.class);
        resetNationalDatabaseDomainCapability = getNationalCapability(ResetNationalDatabaseDomainCapability.class);
        drugUnitDomainCapability = getNationalCapability(DrugUnitDomainCapability.class);
        dispenseUnitDomainCapability = getNationalCapability(DispenseUnitDomainCapability.class);
        genericNameDomainCapability = getNationalCapability(GenericNameDomainCapability.class);
        ingredientDomainCapability = getNationalCapability(IngredientDomainCapability.class);
        drugClassDomainCapability = getNationalCapability(DrugClassDomainCapability.class);
        dosageFormDomainCapability = getNationalCapability(DosageFormDomainCapability.class);
        managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        itemAuditHistoryDomainCapability = getNationalCapability(ItemAuditHistoryDomainCapability.class);
        
        // MigrationDrugClassTest
        migrationErrorDomainCapability = getNationalCapability(MigrationErrorDomainCapability.class);
        specialHandlingDomainCapability = getNationalCapability(SpecialHandlingDomainCapability.class);
        intendedUseDomainCapability = getNationalCapability(IntendedUseDomainCapability.class);
        orderUnitDomainCapability = getNationalCapability(OrderUnitDomainCapability.class);
        csFedScheduleDomainCapability = getNationalCapability(CsFedScheduleDomainCapability.class);
        productDomainCapability = getNationalCapability(ProductDomainCapability.class);
        orderableItemDomainCapability = getNationalCapability(OrderableItemDomainCapability.class);
        manufacturerDomainCapability = getNationalCapability(ManufacturerDomainCapability.class);
        packageTypeDomainCapability = getNationalCapability(PackageTypeDomainCapability.class);
        
        // MigrationDrugClassTest
        ndcDomainCapability = getNationalCapability(NdcDomainCapability.class);
        drugTextDomainCapability = getNationalCapability(DrugTextDomainCapability.class); 
        standardMedRouteDomainCapability = getNationalCapability(StandardMedRouteDomainCapability.class);

        migrationCapability.setDrugUnitDomainCapability(drugUnitDomainCapability);
        migrationCapability.setDispenseUnitDomainCapability(dispenseUnitDomainCapability);
        migrationCapability.setGenericNameDomainCapability(genericNameDomainCapability);
        migrationCapability.setIngredientDomainCapability(ingredientDomainCapability);
        migrationCapability.setDrugClassDomainCapability(drugClassDomainCapability);
        migrationCapability.setDosageFormDomainCapability(dosageFormDomainCapability);
        migrationCapability.setManagedItemCapability(managedItemCapability);
        
        // MigrationDrugClassTest
        migrationCapability.setItemAuditHistoryDomainCapability(itemAuditHistoryDomainCapability);
        migrationCapability.setMigrationErrorDomainCapability(migrationErrorDomainCapability);
        migrationCapability.setSpecialHandlingDomainCapability(specialHandlingDomainCapability);
        migrationCapability.setIntendedUseDomainCapability(intendedUseDomainCapability);
        migrationCapability.setOrderUnitDomainCapability(orderUnitDomainCapability);
        migrationCapability.setCsFedScheduleDomainCapability(csFedScheduleDomainCapability);
        migrationCapability.setProductDomainCapability(productDomainCapability);
        
        // MigrationDrugClassTest
        migrationCapability.setOrderableItemDomainCapability(orderableItemDomainCapability);
        migrationCapability.setManufacturerDomainCapability(manufacturerDomainCapability);
        migrationCapability.setPackageTypeDomainCapability(packageTypeDomainCapability);
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);
        migrationCapability.setResetNationalDatabaseDomainCapability(resetNationalDatabaseDomainCapability);
        migrationCapability.setDrugTextDomainCapability(drugTextDomainCapability);
        migrationCapability.setStandardMedRouteDomainCapability(standardMedRouteDomainCapability);
        
        // MigrationDrugClassTest
        migrationCapability.init();
    }
   
    /**
     * MigrationDrugClassTestclear
     */
    protected void clear() {
        migrationCapability.clear();
        migrationCapability = null;
        resetNationalDatabaseDomainCapability = null;
        managedItemCapability = null;
        ndcDomainCapability = null;
        drugUnitDomainCapability = null;
        
        // MigrationDrugClassTest
        dispenseUnitDomainCapability = null;
        dosageFormDomainCapability = null;
        ingredientDomainCapability = null;
        drugClassDomainCapability = null;
        genericNameDomainCapability = null;
     
        // MigrationDrugClassTest
        itemAuditHistoryDomainCapability = null;
        migrationErrorDomainCapability = null;
        specialHandlingDomainCapability = null;
        intendedUseDomainCapability = null;
        orderUnitDomainCapability = null;
        
        // MigrationDrugClassTest
        csFedScheduleDomainCapability = null;
        productDomainCapability = null;
        orderableItemDomainCapability = null;
        manufacturerDomainCapability = null;
        packageTypeDomainCapability = null;
        standardMedRouteDomainCapability = null;
    }
    
    /**
     * MigrationDrugClassTest Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testDrugClass0() {

        MigrationVariablesVo vo = migrationCapability.migrateDrugClass("0", PPSConstants.I37, 0);
        LOG.info("Drug Class (0) getStrLastIEN=" + vo.getStrLastIEN());
        LOG.info("Drug Class (0) Success=" + vo.getISuccessfullyMigrated());
        LOG.info("Drug Class (0) FailureN=" + vo.getIErroredOnMigration());
        LOG.info("Drug Class (0) Duplicates=" + vo.getIDuplicatesNotMigrated());
        LOG.info("Drug Class (0) isEOF=" + vo.isEndOfFile());

        assertEquals(EXPECT_STR, "47", vo.getStrLastIEN());
        assertEquals(EXPECT_NUM, PPSConstants.I5, vo.getISuccessfullyMigrated());
        assertEquals(EXPECT_NUM, 2, vo.getIErroredOnMigration());
        assertEquals(EXPECT_NUM, 0, vo.getIDuplicatesNotMigrated());
        assertTrue(EXPECT_EOF, vo.isEndOfFile());
   
        // Drug Class 1
        vo = migrationCapability.migrateDrugClass("1", PPSConstants.I37, 1);
        LOG.info("Drug Class (1) getStrLastIEN=" + vo.getStrLastIEN());
        LOG.info("Drug Class (1) Success=" + vo.getISuccessfullyMigrated());
        LOG.info("Drug Class (1) FailureN=" + vo.getIErroredOnMigration());
        LOG.info("Drug Class (1) Duplicates=" + vo.getIDuplicatesNotMigrated());
        LOG.info("Drug Class (1) isEOF=" + vo.isEndOfFile());

        assertEquals(EXPECT_STR, "17", vo.getStrLastIEN());
        assertEquals(EXPECT_NUM, PPSConstants.I11, vo.getISuccessfullyMigrated());
        assertEquals(EXPECT_NUM, 0, vo.getIErroredOnMigration());
        assertEquals(EXPECT_NUM, 0, vo.getIDuplicatesNotMigrated());
        assertTrue(EXPECT_EOF, vo.isEndOfFile());
      
        // Drug Class 2
        vo = migrationCapability.migrateDrugClass("2", PPSConstants.I37, 2);
        LOG.info("Drug Class (2) getStrLastIEN=" + vo.getStrLastIEN());
        LOG.info("Drug Class (2) Success=" + vo.getISuccessfullyMigrated());
        LOG.info("Drug Class (2) FailureN=" + vo.getIErroredOnMigration());
        LOG.info("Drug Class (2) Duplicates=" + vo.getIDuplicatesNotMigrated());
        LOG.info("Drug Class (2) isEOF=" + vo.isEndOfFile());

        assertEquals(EXPECT_STR, "5242", vo.getStrLastIEN());
        assertEquals(EXPECT_NUM, PPSConstants.I5, vo.getISuccessfullyMigrated());
        assertEquals(EXPECT_NUM, PPSConstants.I3, vo.getIErroredOnMigration());
        assertEquals(EXPECT_NUM, 0, vo.getIDuplicatesNotMigrated());
        assertTrue(EXPECT_EOF, vo.isEndOfFile());
        clear();
    }

}
