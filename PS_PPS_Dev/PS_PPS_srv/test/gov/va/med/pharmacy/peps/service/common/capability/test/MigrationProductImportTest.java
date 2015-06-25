/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.MigrationProductGroupVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.domain.common.capability.CsFedScheduleDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DispenseUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DosageFormDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugClassDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugTextDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDosageFormDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugClassDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugIngredientDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbGenericNameDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbProductDomainCapability;
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
import gov.va.med.pharmacy.peps.service.common.capability.RulesCapability;
import gov.va.med.pharmacy.peps.service.common.utility.ProductCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;


/**
 * Test the base class of MigrationCapability. Also test that a sub class still has access to the super's Spring injected
 * classes.
 */
public class MigrationProductImportTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(MigrationProductImportTest.class);

    private static final String EXCEPT_MIGRATION_END_MSG = " threw a Migration exception. ";
    private static final String EXCEPT_UNCAUGHT_END_MSG = " threw an uncaught exception. ";
    private static final String PROP_USER_DIR = "user.dir";
    private static final String DIR_PATH_CSV = "/etc/csv/test/";

    private MigrationCapability migrationCapability;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private NdcDomainCapability ndcDomainCapability;

    // MigrationproductImportTest
    private DrugUnitDomainCapability drugUnitDomainCapability;
    private DispenseUnitDomainCapability dispenseUnitDomainCapability;
    private DosageFormDomainCapability dosageFormDomainCapability;
    private IngredientDomainCapability ingredientDomainCapability;
    private DrugClassDomainCapability drugClassDomainCapability;
    private GenericNameDomainCapability genericNameDomainCapability;

    // MigrationproductImportTest
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private MigrationErrorDomainCapability migrationErrorDomainCapability;
    private SpecialHandlingDomainCapability specialHandlingDomainCapability;
    private IntendedUseDomainCapability intendedUseDomainCapability;
    private OrderUnitDomainCapability orderUnitDomainCapability;
    private CsFedScheduleDomainCapability csFedScheduleDomainCapability;
    
    // MigrationproductImportTest
    private ProductDomainCapability productDomainCapability;
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;
    private DrugTextDomainCapability drugTextDomainCapability;
    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;

    // MigrationproductImportTest
    private FdbProductDomainCapability fdbProductDomainCapability;
    private FdbDrugClassDomainCapability fdbDrugClassDomainCapability;
    private FdbDrugUnitDomainCapability fdbDrugUnitDomainCapability;
    private FdbDosageFormDomainCapability fdbDosageFormDomainCapability;
    private FdbDrugIngredientDomainCapability fdbDrugIngredientDomainCapability;
    private FdbGenericNameDomainCapability fdbGenericNameDomainCapability;

    /**
     *  MigrationproductImportTestGet instance of {@link RulesCapability}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        // MigrationProductImportTest
        migrationCapability = SpringTestConfigUtility.getNationalSpringBean(MigrationCapability.class);
        resetNationalDatabaseDomainCapability = getNationalCapability(ResetNationalDatabaseDomainCapability.class);
        drugUnitDomainCapability = getNationalCapability(DrugUnitDomainCapability.class);
        dispenseUnitDomainCapability = getNationalCapability(DispenseUnitDomainCapability.class);
        genericNameDomainCapability = getNationalCapability(GenericNameDomainCapability.class);
        ingredientDomainCapability = getNationalCapability(IngredientDomainCapability.class);
        drugClassDomainCapability = getNationalCapability(DrugClassDomainCapability.class);
        dosageFormDomainCapability = getNationalCapability(DosageFormDomainCapability.class);
        managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        
        // MigrationproductImportTest
        itemAuditHistoryDomainCapability = getNationalCapability(ItemAuditHistoryDomainCapability.class);
        migrationErrorDomainCapability = getNationalCapability(MigrationErrorDomainCapability.class);
        csFedScheduleDomainCapability = getNationalCapability(CsFedScheduleDomainCapability.class);
        productDomainCapability = getNationalCapability(ProductDomainCapability.class);

        specialHandlingDomainCapability = getNationalCapability(SpecialHandlingDomainCapability.class);
        intendedUseDomainCapability = getNationalCapability(IntendedUseDomainCapability.class);
        orderUnitDomainCapability = getNationalCapability(OrderUnitDomainCapability.class);
        orderableItemDomainCapability = getNationalCapability(OrderableItemDomainCapability.class);
        manufacturerDomainCapability = getNationalCapability(ManufacturerDomainCapability.class);
        packageTypeDomainCapability = getNationalCapability(PackageTypeDomainCapability.class);
        ndcDomainCapability = getNationalCapability(NdcDomainCapability.class);
        
        // MigrationproductImportTest
        drugTextDomainCapability = getNationalCapability(DrugTextDomainCapability.class);
        standardMedRouteDomainCapability = getNationalCapability(StandardMedRouteDomainCapability.class);

        fdbProductDomainCapability = getNationalCapability(FdbProductDomainCapability.class);
        fdbDrugClassDomainCapability = getNationalCapability(FdbDrugClassDomainCapability.class);
        fdbDrugUnitDomainCapability = getNationalCapability(FdbDrugUnitDomainCapability.class);
        fdbDosageFormDomainCapability = getNationalCapability(FdbDosageFormDomainCapability.class);
        fdbDrugIngredientDomainCapability = getNationalCapability(FdbDrugIngredientDomainCapability.class);
        fdbGenericNameDomainCapability = getNationalCapability(FdbGenericNameDomainCapability.class);

        // MigrationproductImportTest
        migrationCapability.setDrugUnitDomainCapability(drugUnitDomainCapability);
        migrationCapability.setDispenseUnitDomainCapability(dispenseUnitDomainCapability);
        migrationCapability.setGenericNameDomainCapability(genericNameDomainCapability);
        migrationCapability.setIngredientDomainCapability(ingredientDomainCapability);
        migrationCapability.setDrugClassDomainCapability(drugClassDomainCapability);
        migrationCapability.setDosageFormDomainCapability(dosageFormDomainCapability);
        migrationCapability.setManagedItemCapability(managedItemCapability);
        migrationCapability.setItemAuditHistoryDomainCapability(itemAuditHistoryDomainCapability);
        migrationCapability.setMigrationErrorDomainCapability(migrationErrorDomainCapability);
        migrationCapability.setSpecialHandlingDomainCapability(specialHandlingDomainCapability);
        migrationCapability.setIntendedUseDomainCapability(intendedUseDomainCapability);
        migrationCapability.setOrderUnitDomainCapability(orderUnitDomainCapability);
        
        // MigrationproductImportTest
        migrationCapability.setCsFedScheduleDomainCapability(csFedScheduleDomainCapability);
        migrationCapability.setProductDomainCapability(productDomainCapability);
        migrationCapability.setOrderableItemDomainCapability(orderableItemDomainCapability);
        migrationCapability.setManufacturerDomainCapability(manufacturerDomainCapability);
        migrationCapability.setPackageTypeDomainCapability(packageTypeDomainCapability);
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);
        migrationCapability.setResetNationalDatabaseDomainCapability(resetNationalDatabaseDomainCapability);
        migrationCapability.setDrugTextDomainCapability(drugTextDomainCapability);
        migrationCapability.setStandardMedRouteDomainCapability(standardMedRouteDomainCapability);

        
        // MigrationproductImportTest
        migrationCapability.setFdbProductDomainCapability(fdbProductDomainCapability);
        migrationCapability.setFdbDrugClassDomainCapability(fdbDrugClassDomainCapability);
        migrationCapability.setFdbDrugUnitDomainCapability(fdbDrugUnitDomainCapability);
        migrationCapability.setFdbDosageFormDomainCapability(fdbDosageFormDomainCapability);
        migrationCapability.setFdbDrugIngredientDomainCapability(fdbDrugIngredientDomainCapability);
        migrationCapability.setFdbGenericNameDomainCapability(fdbGenericNameDomainCapability);

        migrationCapability.init();
    }

    /**
     * MigrationproductImportTestclear
     */
    protected void clear() {
        migrationCapability.clear();
        migrationCapability = null;
        resetNationalDatabaseDomainCapability = null;
        managedItemCapability = null;
        ndcDomainCapability = null;
        drugUnitDomainCapability = null;
        drugClassDomainCapability = null;
        genericNameDomainCapability = null;
        dispenseUnitDomainCapability = null;
        dosageFormDomainCapability = null;
        ingredientDomainCapability = null;
      

        // MigrationproductImportTest
        itemAuditHistoryDomainCapability = null;
        migrationErrorDomainCapability = null;
        specialHandlingDomainCapability = null;
       
        csFedScheduleDomainCapability = null;
        productDomainCapability = null;
        orderableItemDomainCapability = null;
        manufacturerDomainCapability = null;
        packageTypeDomainCapability = null;
        standardMedRouteDomainCapability = null;
        
        intendedUseDomainCapability = null;
        orderUnitDomainCapability = null;
    };

    /**
     * MigrationproductImportTest test_ImportProductWithCSV
     */
    public void testImportProductWithCSV() {

        int iSuccess = 0;
        int iFailure = 0;
        int iDuplicate = 0;
        String name = "";
        Map<String, ProductVo> map = new HashMap<String, ProductVo>();

        String testFilePathOut = System.getProperty(PROP_USER_DIR).concat(DIR_PATH_CSV);
        String testProductFilename = testFilePathOut.concat("ATP_OneProduct.csv");

        ProductCsvFile productCsvFile = new ProductCsvFile();

        try {
            productCsvFile.openForImport(testProductFilename);
        } catch (MigrationException me) {
            fail(testProductFilename + EXCEPT_MIGRATION_END_MSG + me.toString());
        } catch (Exception e) {
            fail(testProductFilename + EXCEPT_UNCAUGHT_END_MSG + e.toString());
        }

        boolean keepGoing = true;

        while (keepGoing) {
            try {
                ProductVo vo = (ProductVo) managedItemCapability.retrieveBlankTemplate(EntityType.PRODUCT);
                vo = productCsvFile.getNextProduct(vo);

                if (vo == null) {
                    keepGoing = false;
                } else {
                    map.put(vo.getVaProductName(), vo);
                    iSuccess++;
                }
            } catch (MigrationException e) {
                LOG.error("Migration exception");
                migrationCapability.saveMigrationErrorMessage("50.68", e);
            }
        }

        try {
            productCsvFile.closeImport();
        } catch (Exception e) {
            fail("uncaught Exception while closing file" + e.toString());
        }

        LOG.info("Processed  " + iSuccess + " succesfully from product csv file.");
        LOG.info("Processed " + iFailure + " that failed from product csv file.");

        iSuccess = 0;
        iFailure = 0;
        iDuplicate = 0;

        try {
            MigrationProductGroupVo groupVo = migrationCapability.retriveProductsFromVista("0",
                PPSConstants.I37, PPSConstants.I1);
            List<ProductVo> vistaList = groupVo.getProductList();

            for (ProductVo productVo : vistaList) {
                name = productVo.getVaProductName();
                productVo = migrationCapability.mergeProducts(map.get(name), productVo);
                MigrationVariablesVo migrationVariablesVo = migrationCapability.migrateProducts(productVo);

                if (migrationVariablesVo.getISuccessfullyMigrated() == 1) {
                    LOG.info("Successfully migrated product " + name);
                }

                iSuccess += migrationVariablesVo.getISuccessfullyMigrated();
                iFailure += migrationVariablesVo.getIErroredOnMigration();
                iDuplicate += migrationVariablesVo.getIDuplicatesNotMigrated();
            }
        } catch (Exception e) {
            fail("testActiveProductsNoCSv threw an exception. " + e.toString() + " for product " + name);
        }

        LOG.info("Active Product Success=" + iSuccess);
        LOG.info("Active Product FailureN=" + iFailure);
        LOG.info("Active Product Duplicates=" + iDuplicate);

        assertEquals("Should have been 15 successful Products in file", PPSConstants.I16, iSuccess);
        assertEquals("Should have been 3 failures in file", PPSConstants.I2, iFailure);
        assertEquals("Should have been 0 duplicates in file", 0, iDuplicate);

        clear();

    }
}
