/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;


import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
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
import gov.va.med.pharmacy.peps.service.common.utility.NdcCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.OiCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ProductCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;


/**
 * Test the base class of MigrationCapability. Also test that a sub class still has access to the super's Spring injected
 * classes.
 */
public class ExportItemsTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(ExportItemsTest.class);

    private static final String EXCEPT_MIGRATION_END_MSG = " threw a Migration exception. ";
    private static final String EXCEPT_UNCAUGHT_END_MSG = " threw an uncaught exception. ";

    private static final String TMP = "/tmp/";

    private MigrationCapability migrationCapability;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private NdcDomainCapability ndcDomainCapability;

    //private MigrationRequestCapability migrationRequestCapablity;

    private DrugUnitDomainCapability drugUnitDomainCapability;
    private DispenseUnitDomainCapability dispenseUnitDomainCapability;
    private DosageFormDomainCapability dosageFormDomainCapability;
    private IngredientDomainCapability ingredientDomainCapability;
    private DrugClassDomainCapability drugClassDomainCapability;
    private GenericNameDomainCapability genericNameDomainCapability;

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
    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;

    /**
     * Get instance of {@link RulesCapability}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

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
        drugTextDomainCapability = getNationalCapability(DrugTextDomainCapability.class);
        standardMedRouteDomainCapability = getNationalCapability(StandardMedRouteDomainCapability.class);

        migrationCapability.setDrugUnitDomainCapability(drugUnitDomainCapability);
        migrationCapability.setDispenseUnitDomainCapability(dispenseUnitDomainCapability);
        migrationCapability.setGenericNameDomainCapability(genericNameDomainCapability);
        migrationCapability.setStandardMedRouteDomainCapability(standardMedRouteDomainCapability);
        migrationCapability.setIngredientDomainCapability(ingredientDomainCapability);
        migrationCapability.setDrugClassDomainCapability(drugClassDomainCapability);
        migrationCapability.setDosageFormDomainCapability(dosageFormDomainCapability);
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
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);
        migrationCapability.setResetNationalDatabaseDomainCapability(resetNationalDatabaseDomainCapability);
        migrationCapability.setDrugTextDomainCapability(drugTextDomainCapability);

        migrationCapability.init();
    }

    /**
     * clear the class data
     */
    protected void tearDown() {
        migrationCapability.clear();
        migrationCapability = null;
        resetNationalDatabaseDomainCapability = null;
        managedItemCapability = null;
        ndcDomainCapability = null;
        drugUnitDomainCapability = null;
        dispenseUnitDomainCapability = null;
        dosageFormDomainCapability = null;
        ingredientDomainCapability = null;
        drugClassDomainCapability = null;
        genericNameDomainCapability = null;

        itemAuditHistoryDomainCapability = null;
        migrationErrorDomainCapability = null;
        specialHandlingDomainCapability = null;
        intendedUseDomainCapability = null;
        orderUnitDomainCapability = null;
        csFedScheduleDomainCapability = null;
        productDomainCapability = null;
        orderableItemDomainCapability = null;
        manufacturerDomainCapability = null;
        packageTypeDomainCapability = null;
    }

    /**
     * Test of all product export to CSV
     * 
     *  originally this method exported product, ndc and oi to csv.  broken up due to out of memory errors on the 
     *  CI server
     *  
     * @throws ValidationException 
     */
    public void testExportProductToCsv() throws ValidationException {

//        assertTrue("This test specifically causes OutOfMemeoryError.", 1 == java.lang.Math.abs(-1));

        // first test the export of the product
        String testFilePathOut = System.getProperty(PPSConstants.USER_DIR).concat(TMP);
        String testProductFilename = testFilePathOut.concat("ExportProduct.csv");
        ProductCsvFile productCsvFile = new ProductCsvFile();

        try {
            productCsvFile.openForExport(testProductFilename);
        } catch (MigrationException me) {
            fail(testProductFilename + EXCEPT_MIGRATION_END_MSG + me.toString());
        } catch (Exception e) {
            fail(testProductFilename + " threw an uncaught Exception " + e.toString());
        }

        int iSuccess = 0;
        int iFailure = 0;

        try {
            List<Long> list = resetNationalDatabaseDomainCapability.getIds(EntityType.PRODUCT, false);
            LOG.info("Got " + list.size() + " products from database.");
            String productName = "";

            for (Long longVal : list) {
                ProductVo productVo = (ProductVo) managedItemCapability.retrieveBlankTemplate(EntityType.PRODUCT);
                LOG.info("Processing ID " + longVal.toString());
                productVo = resetNationalDatabaseDomainCapability.getProduct(longVal, productVo);

                if (productVo == null) {
                    LOG.warn("ProductVo was null for product " + productName);
                } else {
                    productName = productVo.getVaProductName();
                    productCsvFile.putNextProduct(productVo);
                    iSuccess++;
                }
            }

            productCsvFile.closeExport();
        } catch (Exception e) {
            fail("Uncaught Exception " + e.toString());
        }

        LOG.info("Product Success = " + iSuccess);
        LOG.info("Product FailureN = " + iFailure);

        assertTrue("Success should be greater than 1!", iSuccess > 1);
        assertTrue("There should be no failures!", iFailure < 1);
        migrationCapability.clear();

    }

    /**
     * test the export of ndc's to a csv file.
     *
     * @throws ValidationException ValidationException
     */
    public void testExportNdcToCsv() throws ValidationException {

//        assertTrue("This test specifically causes OutOfMemeoryError.", 1 == java.lang.Math.abs(-1));

        // Now test the export NDC

        String testFilePathOut = System.getProperty(PPSConstants.USER_DIR).concat(TMP);
        String testNdcFilename = testFilePathOut.concat("ExportNdc.csv");
        NdcCsvFile ndcCsvFile = new NdcCsvFile();

        try {
            ndcCsvFile.openForExport(testNdcFilename);
        } catch (MigrationException me) {
            fail(testNdcFilename + EXCEPT_MIGRATION_END_MSG + me.toString());
        } catch (Exception e) {
            fail(testNdcFilename + " threw an uncaughtException " + e.toString());
        }

        int iSuccess = 0;
        int iFailure = 0;
        String ndc = "";

        try {
            List<Long> list = this.resetNationalDatabaseDomainCapability.getIds(EntityType.NDC, false);
            NdcVo ndcVo = (NdcVo) managedItemCapability.retrieveBlankTemplate(EntityType.NDC);

            for (Long longVal : list) {
                ndcVo = resetNationalDatabaseDomainCapability.getNdcs(longVal, ndcVo);

                if (ndcVo == null) {
                    LOG.warn("NDCVo was null");
                } else {
                    ndc = ndcVo.getNdc();
                    ndcCsvFile.putNextNdc(ndcVo);
                    iSuccess++;
                }
            }
        } catch (Exception e) {
            LOG.error("Uncaught exception on putting row - " + ndc);
            iFailure++;
        }

        try {
            ndcCsvFile.closeExport();
        } catch (Exception e) {
            fail(" uncaugth Exception closing file " + e.toString());
        }

        LOG.info("NDC Success=" + iSuccess);
        LOG.info("NDC FailureN=" + iFailure);

        assertTrue(" Success should be greater than 1", iSuccess > 1);
        assertTrue(" There should be no failures", iFailure < 1);
        migrationCapability.clear();
    }

    /**
     * test the export of orderable items to a csv file
     *
     * @throws ValidationException ValidationException
     */
    public void testExportOiToCsv() throws ValidationException {

//        assertTrue("This test specifically causes OutOfMemeoryError.", 1 == java.lang.Math.abs(-1));

        int iSuccess = 0;
        int iFailure = 0;

        // Now test the ExportOI
        String testFilePathOut = System.getProperty(PPSConstants.USER_DIR).concat(TMP);
        String testOiFilename = testFilePathOut.concat("ExportOi.csv");

        OiCsvFile oiCsvFile = new OiCsvFile();

        try {
            oiCsvFile.openForExport(testOiFilename);
        } catch (MigrationException me) {
            fail(testOiFilename + EXCEPT_MIGRATION_END_MSG + me.toString());
        } catch (Exception e) {
            fail(testOiFilename + EXCEPT_UNCAUGHT_END_MSG + e.toString());
        }

        try {
            List<Long> list = resetNationalDatabaseDomainCapability.getIds(EntityType.ORDERABLE_ITEM, false);
            String oiName = "";

            for (Long longVal : list) {
                OrderableItemVo orderableItemVo =
                        (OrderableItemVo) managedItemCapability.retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);

                try {
                    orderableItemVo = resetNationalDatabaseDomainCapability.getOrderableItem(longVal, orderableItemVo);

                    if (orderableItemVo == null) {
                        LOG.warn("OIVo was null");
                    } else {
                        oiName = orderableItemVo.getOiName();
                        oiCsvFile.putNextOi(orderableItemVo);
                        iSuccess++;
                    }
                } catch (Exception e) {
                    LOG.error("Uncaught exception on putting row " + oiName);
                    iFailure++;
                }
            }

            //oiCsvFile.closeExport();

        } catch (Exception e) {
            fail("uncaught Exception " + e.toString());
        }

        try {
            oiCsvFile.closeExport();
        } catch (Exception e) {
            fail("uncaugth Exception closing file " + e.toString());
        }

        LOG.info("OI Success=" + iSuccess);
        LOG.info("OI FailureN=" + iFailure);

        assertTrue("Success should be greater than 1", iSuccess > 1);
        assertTrue("There should be no failures", iFailure < 1);
        migrationCapability.clear();

    }
}
