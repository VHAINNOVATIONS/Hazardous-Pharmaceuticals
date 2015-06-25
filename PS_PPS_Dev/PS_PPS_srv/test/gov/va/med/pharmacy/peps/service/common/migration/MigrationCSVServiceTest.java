/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.MigrationProductGroupVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
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
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.MigrationSynchFileCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.MigrationCapability;
import gov.va.med.pharmacy.peps.service.common.session.MigrationCSVService;
import gov.va.med.pharmacy.peps.service.common.session.impl.MigrationCSVServiceImpl;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;



/**
 * 
 * MigrationCSVServiceTest
 *
 */
public class MigrationCSVServiceTest extends DomainCapabilityTestCase {
    
    private static final Logger LOG = Logger.getLogger(MigrationCSVServiceTest.class);
    
    private static final String NDCFILE = "ndcFile";
    private static final String NDCCSVFILE = "NdcCsvFile_Test1.csv";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String OIFILE = "oiFile";
    private static final String PRODUCTFILE = "productsFile";
    private static final String OICSVFILE = "OrderableItemsFile.csv";
    
    private MigrationCSVService myService;
    private MockMultipartHttpServletRequest myRequest;

    private MigrationCapability migrationCapability;
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private ProductDomainCapability productDomainCapability;

    private NdcDomainCapability ndcDomainCapability;
    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;
    private OrderUnitDomainCapability orderUnitDomainCapability;
    private MigrationSynchFileCapability migrationSynchFileCapability;
    private ManagedItemCapability managedItemCapability;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;

    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private DrugReferenceCapability drugReferenceCapability;
    private DosageFormDomainCapability dosageFormDomainCapability;
    private CsFedScheduleDomainCapability csFedScheduleDomainCapability;
    private DrugUnitDomainCapability drugUnitDomainCapability;
    private DispenseUnitDomainCapability dispenseUnitDomainCapability;
    private IngredientDomainCapability ingredientDomainCapability;
    private DrugClassDomainCapability drugClassDomainCapability;
    private GenericNameDomainCapability genericNameDomainCapability;
    private MigrationErrorDomainCapability migrationErrorDomainCapability;
    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;

    private IntendedUseDomainCapability intendedUseDomainCapability;
    private DrugTextDomainCapability drugTextDomainCapability;
    private SpecialHandlingDomainCapability specialHandlingDomainCapability;

    /**
     * loadSpringConfiguration
     */
    private void loadSpringConfiguration() {

        this.standardMedRouteDomainCapability = getNationalCapability(StandardMedRouteDomainCapability.class);
        this.resetNationalDatabaseDomainCapability = getNationalCapability(ResetNationalDatabaseDomainCapability.class);
        this.manufacturerDomainCapability = getNationalCapability(ManufacturerDomainCapability.class);
        this.packageTypeDomainCapability = getNationalCapability(PackageTypeDomainCapability.class);
        this.orderUnitDomainCapability = getNationalCapability(OrderUnitDomainCapability.class);
        this.itemAuditHistoryDomainCapability = getNationalCapability(ItemAuditHistoryDomainCapability.class);
        this.drugReferenceCapability = getNationalCapability(DrugReferenceCapability.class);
        this.dosageFormDomainCapability = getNationalCapability(DosageFormDomainCapability.class);
        this.csFedScheduleDomainCapability = getNationalCapability(CsFedScheduleDomainCapability.class);
        this.drugUnitDomainCapability = getNationalCapability(DrugUnitDomainCapability.class);
        this.dispenseUnitDomainCapability = getNationalCapability(DispenseUnitDomainCapability.class);
        this.ingredientDomainCapability = getNationalCapability(IngredientDomainCapability.class);
        this.drugClassDomainCapability = getNationalCapability(DrugClassDomainCapability.class);
        this.genericNameDomainCapability = getNationalCapability(GenericNameDomainCapability.class);
        this.migrationErrorDomainCapability = getNationalCapability(MigrationErrorDomainCapability.class);
        this.drugTextDomainCapability = getNationalCapability(DrugTextDomainCapability.class);

        this.managedItemCapability = getNationalCapability(ManagedItemCapability.class);
        this.ndcDomainCapability = getNationalCapability(NdcDomainCapability.class);
        this.productDomainCapability = getNationalCapability(ProductDomainCapability.class);
        this.orderableItemDomainCapability = getNationalCapability(OrderableItemDomainCapability.class);
        this.migrationSynchFileCapability = getNationalCapability(MigrationSynchFileCapability.class);
        this.intendedUseDomainCapability = getNationalCapability(IntendedUseDomainCapability.class);
        this.specialHandlingDomainCapability = getNationalCapability(SpecialHandlingDomainCapability.class);

        this.migrationCapability = SpringTestConfigUtility.getNationalSpringBean(MigrationCapability.class);

        migrationCapability.setStandardMedRouteDomainCapability(standardMedRouteDomainCapability);
        migrationCapability.setManagedItemCapability(managedItemCapability);
        migrationCapability.setOrderableItemDomainCapability(orderableItemDomainCapability);
        migrationCapability.setProductDomainCapability(productDomainCapability);
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);

        migrationCapability.setManufacturerDomainCapability(this.manufacturerDomainCapability);
        migrationCapability.setPackageTypeDomainCapability(packageTypeDomainCapability);
        migrationCapability.setItemAuditHistoryDomainCapability(itemAuditHistoryDomainCapability);
        migrationCapability.setOrderUnitDomainCapability(orderUnitDomainCapability);
        migrationCapability.setDrugReferenceCapability(drugReferenceCapability);
        migrationCapability.setDosageFormDomainCapability(dosageFormDomainCapability);
        migrationCapability.setCsFedScheduleDomainCapability(csFedScheduleDomainCapability);
        migrationCapability.setDrugUnitDomainCapability(drugUnitDomainCapability);
        migrationCapability.setDispenseUnitDomainCapability(dispenseUnitDomainCapability);
        migrationCapability.setIngredientDomainCapability(ingredientDomainCapability);
        migrationCapability.setDrugClassDomainCapability(drugClassDomainCapability);
        migrationCapability.setGenericNameDomainCapability(genericNameDomainCapability);
        migrationCapability.setMigrationSynchFileCapability(migrationSynchFileCapability);
        migrationCapability.setMigrationErrorDomainCapability(migrationErrorDomainCapability);
        migrationCapability.setIntendedUseDomainCapability(intendedUseDomainCapability);
        migrationCapability.setDrugTextDomainCapability(drugTextDomainCapability);
        migrationCapability.setSpecialHandlingDomainCapability(specialHandlingDomainCapability);
        myService.setResetNationalDatabaseDomainCapability(resetNationalDatabaseDomainCapability);
        migrationCapability.init();

    }

    /**
     * setUp
     */
    @Override
    protected void setUp() {
        myService = new MockMigrationCSVService();
        myRequest = new MockMultipartHttpServletRequest();

    }

    /**
     * testValidateNDCCSVHeaderTest
     * @throws IOException IOException
     */
    public void testValidateNDCCSVHeaderTest() throws IOException {
        myRequest.addFile(new MockMultipartFile(NDCFILE, NDCCSVFILE, TEXT_PLAIN,
                                               MigrationTestData.NDC_CSV_FILE1.getBytes()));

        CSVResponseMessage responseMessage = myService.validateFileHeader(myRequest);
        assertFalse(responseMessage.getResponseMessage(), responseMessage.isError());
    }

    /**
     * testValidateOICSVHeaderTest
     * @throws IOException IOException
     */
    public void testValidateOICSVHeaderTest() throws IOException {
        myRequest.addFile(new MockMultipartFile(OIFILE, OICSVFILE, TEXT_PLAIN,
                                               MigrationTestData.ORDERABLE_ITEM_FILE.getBytes()));

        CSVResponseMessage responseMessage = myService.validateFileHeader(myRequest);
        assertFalse(responseMessage.getResponseMessage(), responseMessage.isError());
    }

    /**
     * testValidateProductCSVHeaderTest
     * @throws IOException IOException
     */
    public void testValidateProductCSVHeaderTest() throws IOException {
        myRequest.addFile(new MockMultipartFile("productFile", "ProductsFile.csv", TEXT_PLAIN,
                                               MigrationTestData.ONE_PRODUCT_CSV_FILE.getBytes()));

        CSVResponseMessage responseMessage = myService.validateFileHeader(myRequest);
        assertFalse(responseMessage.getResponseMessage(), responseMessage.isError());
    }

    /**
     * testValidateNDCandOIandProductCSVHeaderTest
     * @throws IOException IOException
     */
    public void testValidateNDCandOIandProductCSVHeaderTest() throws IOException {
        myRequest.addFile(new MockMultipartFile(NDCFILE, NDCCSVFILE, TEXT_PLAIN,
                                               MigrationTestData.NDC_CSV_FILE1.getBytes()));
        myRequest.addFile(new MockMultipartFile(OIFILE, OICSVFILE, TEXT_PLAIN,
                                               MigrationTestData.ORDERABLE_ITEM_FILE.getBytes()));

        CSVResponseMessage responseMessage = myService.validateFileHeader(myRequest);
        assertFalse(responseMessage.getResponseMessage(), responseMessage.isError());
    }

    /**
     * testValidateBadCSVHeaderTest
     */
    public void testValidateBadCSVHeaderTest() {
        myRequest.addFile(new MockMultipartFile(NDCFILE, "NdcCsvFile_Test-BAD.csv", TEXT_PLAIN,
                                               MigrationTestData.NDC_CSV_FILE_BAD.getBytes()));

        CSVResponseMessage responseMessage = myService.validateFileHeader(myRequest);
        assertTrue(responseMessage.getResponseMessage(), responseMessage.isError());
    }

    /**
     * testNdcImport
     * @throws MigrationException MigrationException
     */
    public void testNdcImport() throws MigrationException {
        loadSpringConfiguration();

        MigrationVariablesVo vo = null;

        migrationCapability.setNdcDomainCapability(ndcDomainCapability);
        migrationCapability.setManufacturerDomainCapability(manufacturerDomainCapability);
        migrationCapability.setPackageTypeDomainCapability(packageTypeDomainCapability);
        migrationCapability.setOrderUnitDomainCapability(orderUnitDomainCapability);
        migrationCapability.setProductDomainCapability(productDomainCapability);
        migrationCapability.setManagedItemCapability(managedItemCapability);
        myService.setManagedItemCapability(managedItemCapability);
        myService.setMigrationCapability(migrationCapability);
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);

        myRequest.addFile(new MockMultipartFile(NDCFILE, NDCCSVFILE, TEXT_PLAIN,
                                               MigrationTestData.NDC_CSV_FILE2.getBytes()));

        MockMultipartFile file = (MockMultipartFile) myRequest.getFile(NDCFILE);

        myService.setNdcMultipartFile(file);
        myService.openNdcFileForImport();

        vo = myService.migrateNDCs(ProcessDomainType.NDC_CSV_FILE_ACTIVE);
        assertNotNull("This Vo should not be null", vo);
    }

    /**
     * testOrderableItemsImport
     * @throws MigrationException MigrationException
     */
    public void testOrderableItemsImport() throws MigrationException {
        loadSpringConfiguration();
        MigrationVariablesVo vo = null;

        migrationCapability.setNdcDomainCapability(ndcDomainCapability);
        migrationCapability.setManufacturerDomainCapability(manufacturerDomainCapability);
        migrationCapability.setPackageTypeDomainCapability(packageTypeDomainCapability);
        migrationCapability.setOrderUnitDomainCapability(orderUnitDomainCapability);
        migrationCapability.setProductDomainCapability(productDomainCapability);
        migrationCapability.setManagedItemCapability(managedItemCapability);

        // testOrderableItemImport
        myService.setManagedItemCapability(managedItemCapability);
        myService.setMigrationCapability(migrationCapability);
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);

        myRequest.addFile(new MockMultipartFile(OIFILE, OICSVFILE, TEXT_PLAIN,
                                               MigrationTestData.ORDERABLE_ITEM_FILE.getBytes()));

        MockMultipartFile file = (MockMultipartFile) myRequest.getFile(OIFILE);
        myService.setOiMultipartFile(file);

        myService.openOiFileForImport();
        vo = myService.migrateOrderableItems(ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE);

        assertNotNull("This vo should not be null.", vo);

    }

    /**
     * testProductsImport
     * @throws MigrationException MigrationException
     */
    public void testProductsImport() throws MigrationException {
        loadSpringConfiguration();
        MigrationVariablesVo vo = null;

        migrationCapability.setNdcDomainCapability(ndcDomainCapability);
        migrationCapability.setManufacturerDomainCapability(manufacturerDomainCapability);
        migrationCapability.setPackageTypeDomainCapability(packageTypeDomainCapability);
        migrationCapability.setOrderUnitDomainCapability(orderUnitDomainCapability);
        migrationCapability.setProductDomainCapability(productDomainCapability);
        migrationCapability.setManagedItemCapability(managedItemCapability);

        myService.setManagedItemCapability(managedItemCapability);
        myService.setMigrationCapability(migrationCapability);
        migrationCapability.setNdcDomainCapability(ndcDomainCapability);

        myRequest.addFile(new MockMultipartFile(PRODUCTFILE, "productsCsvFile.csv", TEXT_PLAIN,
                                               MigrationTestData.ONE_PRODUCT_CSV_FILE.getBytes()));
        MockMultipartFile file = (MockMultipartFile) myRequest.getFile(PRODUCTFILE);

        assertTrue("FileSize should be greater than 0.", file.getSize() != 0);

        myService.setProductMultipartFile(file);
        myService.openProductFileForImport();

        vo = myService.migrateProducts("0", ProcessDomainType.VA_PRODUCT_ACTIVE);

        assertNotNull("Vo should not be null!", vo);

    }


    /**
     * MockMigrationCSVService
     *
     */
    public class MockMigrationCSVService extends MigrationCSVServiceImpl implements MigrationCSVService {

   
       
        private MigrationVariablesVo vo = new MigrationVariablesVo();
        private int iSuccess = 0;
        private int iFailure = 0;
        private int iDuplicate = 0;
        private String name = "";

        /**
         * migrateProducts
         * @param ien ien
         * @param pProcessDomainType pProcessDomainType
         * @return MigrationVariablesVo
         * 
         */
        @Override
        public MigrationVariablesVo migrateProducts(String ien, ProcessDomainType pProcessDomainType) {
           
            MigrationProductGroupVo groupVo =
                    migrationCapability.retriveProductsFromVista(ien, PPSConstants.I37, pProcessDomainType.getDomainState()
                            .getState());
            List<ProductVo> vistaList = groupVo.getProductList();

            for (ProductVo productVo : vistaList) {
                name = productVo.getVaProductName();
                ProductVo csvProductVo = null;
           
                
                try {
                    if (getProductMultipartFile() != null) {
                        csvProductVo = getProductVoMap().get(name);
                    }
                } catch (Exception e) {
                    LOG.debug(e.toString());
                }
                
     
                productVo = migrationCapability.mergeProducts(csvProductVo, productVo);
                
                try {
                    vo = migrationCapability.migrateProducts(productVo);
                }  catch (Exception e) {
                    LOG.debug("Uncaught exception migrating product " + name + ":" + e.getMessage());
                }
                
                iSuccess += vo.getISuccessfullyMigrated();
                iFailure += vo.getIErroredOnMigration();
                iDuplicate += vo.getIDuplicatesNotMigrated();
                LOG.debug("Migrated Product " + name + " and it was " + vo.getISuccessfullyMigrated() + ":"
                                   + vo.getIErroredOnMigration() + ":" + vo.getIDuplicatesNotMigrated());
            }
            
            // set the return variables 
            vo.setStrLastIEN(groupVo.getLastIEN());
            vo.setIErroredOnMigration(iFailure + groupVo.getNumberErroredOnRetrieval());
            vo.setIDuplicatesNotMigrated(iDuplicate);
            vo.setISuccessfullyMigrated(iSuccess);
            vo.setEndOfFile(groupVo.isEndOfFile());

            return vo;
        }
    }

}
