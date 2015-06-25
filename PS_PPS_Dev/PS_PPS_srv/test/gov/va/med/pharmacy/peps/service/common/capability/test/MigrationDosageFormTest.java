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
public class MigrationDosageFormTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(MigrationDosageFormTest.class);

    //MigrationDosageFormTest
    private static final String EXPECT_STR = "Expecting string equivalent value";
    private static final String EXPECT_NUM = "Expecting numeric equivalent value";
    private static final String EXPECT_EOF = "Expecting end of file";
    
    
    //MigrationDosageFormTest
    private MigrationCapability migrationCapability;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private NdcDomainCapability ndcDomainCapability;
    
    //private MigrationRequestCapability migrationRequestCapablity;

    
    //MigrationDosageFormTest
    private DrugUnitDomainCapability drugUnitDomainCapability;
    private DispenseUnitDomainCapability dispenseUnitDomainCapability;
    private DosageFormDomainCapability dosageFormDomainCapability;
    private IngredientDomainCapability ingredientDomainCapability;
    private DrugClassDomainCapability drugClassDomainCapability;
    private GenericNameDomainCapability genericNameDomainCapability;
   
    
    //MigrationDosageFormTest
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private MigrationErrorDomainCapability migrationErrorDomainCapability;
    private SpecialHandlingDomainCapability specialHandlingDomainCapability;
    private IntendedUseDomainCapability intendedUseDomainCapability;
    private OrderUnitDomainCapability orderUnitDomainCapability;
    private CsFedScheduleDomainCapability csFedScheduleDomainCapability;
    
    //MigrationDosageFormTest
    private ProductDomainCapability productDomainCapability;
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;
    private DrugTextDomainCapability drugTextDomainCapability;
    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;
    

    /**
     * MigrationDosageFormTest Get instance of {@link RulesCapability}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        
        //MigrationDosageFormTest
        migrationCapability = SpringTestConfigUtility.getNationalSpringBean(MigrationCapability.class);
        resetNationalDatabaseDomainCapability = getNationalCapability(ResetNationalDatabaseDomainCapability.class);
        drugUnitDomainCapability = getNationalCapability(DrugUnitDomainCapability.class);
        dispenseUnitDomainCapability = getNationalCapability(DispenseUnitDomainCapability.class);
        genericNameDomainCapability = getNationalCapability(GenericNameDomainCapability.class);
        ingredientDomainCapability = getNationalCapability(IngredientDomainCapability.class);
        drugClassDomainCapability = getNationalCapability(DrugClassDomainCapability.class);
        dosageFormDomainCapability = getNationalCapability(DosageFormDomainCapability.class);
        managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        
        //MigrationDosageFormTest
        itemAuditHistoryDomainCapability = getNationalCapability(ItemAuditHistoryDomainCapability.class);
        migrationErrorDomainCapability = getNationalCapability(MigrationErrorDomainCapability.class);
        specialHandlingDomainCapability = getNationalCapability(SpecialHandlingDomainCapability.class);
        intendedUseDomainCapability = getNationalCapability(IntendedUseDomainCapability.class);
        orderUnitDomainCapability = getNationalCapability(OrderUnitDomainCapability.class);
        csFedScheduleDomainCapability = getNationalCapability(CsFedScheduleDomainCapability.class);
        productDomainCapability = getNationalCapability(ProductDomainCapability.class);
        orderableItemDomainCapability = getNationalCapability(OrderableItemDomainCapability.class);
        manufacturerDomainCapability = getNationalCapability(ManufacturerDomainCapability.class);
        packageTypeDomainCapability = getNationalCapability(PackageTypeDomainCapability.class);
        ndcDomainCapability = getNationalCapability(NdcDomainCapability.class);
        
        //MigrationDosageFormTest
        drugTextDomainCapability = getNationalCapability(DrugTextDomainCapability.class); 
        standardMedRouteDomainCapability = getNationalCapability(StandardMedRouteDomainCapability.class);

        migrationCapability.setDrugUnitDomainCapability(drugUnitDomainCapability);
        migrationCapability.setDispenseUnitDomainCapability(dispenseUnitDomainCapability);
        migrationCapability.setGenericNameDomainCapability(genericNameDomainCapability);
        migrationCapability.setIngredientDomainCapability(ingredientDomainCapability);
        migrationCapability.setDrugClassDomainCapability(drugClassDomainCapability);
        migrationCapability.setDosageFormDomainCapability(dosageFormDomainCapability);
        
        //MigrationDosageFormTest
        migrationCapability.setManagedItemCapability(managedItemCapability);
        migrationCapability.setItemAuditHistoryDomainCapability(itemAuditHistoryDomainCapability);
        migrationCapability.setMigrationErrorDomainCapability(migrationErrorDomainCapability);
        migrationCapability.setSpecialHandlingDomainCapability(specialHandlingDomainCapability);
        migrationCapability.setIntendedUseDomainCapability(intendedUseDomainCapability);
        migrationCapability.setOrderUnitDomainCapability(orderUnitDomainCapability);
        migrationCapability.setCsFedScheduleDomainCapability(csFedScheduleDomainCapability);
        migrationCapability.setProductDomainCapability(productDomainCapability);
        migrationCapability.setOrderableItemDomainCapability(orderableItemDomainCapability);
        migrationCapability.setManufacturerDomainCapability(manufacturerDomainCapability);
        migrationCapability.setPackageTypeDomainCapability(packageTypeDomainCapability);
        
        //MigrationDosageFormTest
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);
        migrationCapability.setResetNationalDatabaseDomainCapability(resetNationalDatabaseDomainCapability);
        migrationCapability.setDrugTextDomainCapability(drugTextDomainCapability);
        migrationCapability.setStandardMedRouteDomainCapability(standardMedRouteDomainCapability);
        
        migrationCapability.init();
    }
   
    /**
     * MigrationDosageFormTestclear
     */
    protected void clear() {
        migrationCapability.clear();
        migrationCapability = null;
        resetNationalDatabaseDomainCapability = null;
        managedItemCapability = null;
        ndcDomainCapability = null;
        drugUnitDomainCapability = null;
        
        //MigrationDosageFormTest
        dispenseUnitDomainCapability = null;
        dosageFormDomainCapability = null;
        ingredientDomainCapability = null;
        drugClassDomainCapability = null;
        genericNameDomainCapability = null;
     
        itemAuditHistoryDomainCapability = null;
        migrationErrorDomainCapability = null;
        specialHandlingDomainCapability = null;
        intendedUseDomainCapability = null;
        
        //MigrationDosageFormTest
        orderUnitDomainCapability = null;
        csFedScheduleDomainCapability = null;
        productDomainCapability = null;
        orderableItemDomainCapability = null;
        manufacturerDomainCapability = null;
        packageTypeDomainCapability = null;
        standardMedRouteDomainCapability = null;
    };

    /**
     * Send in a known invalid testMigrateDosageFormActive to verify validation occurs.
     */
    public void testMigrateDosageFormActive() {
        MigrationVariablesVo vo = migrationCapability.migrateDosageForm("2", PPSConstants.I37, 1);
        LOG.info("Migrate (active) Dosage Form getStrLastIEN=" + vo.getStrLastIEN());
        LOG.info("Migrate (active) Dosage Form Success=" + vo.getISuccessfullyMigrated());
        LOG.info("Migrate (active) Dosage Form FailureN=" + vo.getIErroredOnMigration());
        LOG.info("Migrate (active) Dosage Form Duplicates=" + vo.getIDuplicatesNotMigrated());
        LOG.info("Migrate (active) Dosage Form isEOF=" + vo.isEndOfFile());

        assertEquals(EXPECT_STR, "15", vo.getStrLastIEN());
        assertEquals(EXPECT_NUM, PPSConstants.I9, vo.getISuccessfullyMigrated());
        assertEquals(EXPECT_NUM, PPSConstants.I6, vo.getIErroredOnMigration());
        assertEquals(EXPECT_NUM, 0, vo.getIDuplicatesNotMigrated());
        assertTrue(EXPECT_EOF, vo.isEndOfFile());
  
        vo = migrationCapability.migrateDosageForm("2", PPSConstants.I37, 0);
        LOG.info("Migrate (inactive) Dosage Form getStrLastIEN=" + vo.getStrLastIEN());
        LOG.info("Migrate (inactive) Dosage Form Success=" + vo.getISuccessfullyMigrated());
        LOG.info("Migrate (inactive) Dosage Form FailureN=" + vo.getIErroredOnMigration());
        LOG.info("Migrate (inactive) Dosage Form Duplicates=" + vo.getIDuplicatesNotMigrated());
        LOG.info("Migrate (inactive) Dosage Form isEOF=" + vo.isEndOfFile());

        assertEquals(EXPECT_STR, "62", vo.getStrLastIEN());
        assertEquals(EXPECT_NUM, PPSConstants.I13, vo.getISuccessfullyMigrated());
        assertEquals(EXPECT_NUM, PPSConstants.I3, vo.getIErroredOnMigration());
        assertEquals(EXPECT_NUM, 1, vo.getIDuplicatesNotMigrated());
        assertTrue(EXPECT_EOF, vo.isEndOfFile());
        clear();
    }


}
