/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.MigrationErrorVo;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationErrorDomainCapability;



/**
 * MigrationErrorDomainCapabilityTest.
 */
public class MigrationErrorDomainCapabilityTest extends DomainCapabilityTestCase {
    

    private static String ASSERT_STR = "File Numbers should be equal.";
    
    private MigrationErrorDomainCapability migrationErrorDomainCapability;
    
    /**
     * MigrationErrorDomainCapabilityTest
     */
    public MigrationErrorDomainCapabilityTest() {
        super();
    }
    
    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        this.migrationErrorDomainCapability = getNationalCapability(MigrationErrorDomainCapability.class);
    }

  
    /**
     * This method should create three errors in the database
     * 
     * @throws Exception Exception
     */
    public void testCreateMigrationError() throws Exception {


        
        
        MigrationErrorVo vo = createVo(PPSConstants.VA_PRODUCT_FILE, "1", "Dispense Unit Per Dose", 
            "OI", "Product Name", "Product Name Invalid.");
        MigrationErrorVo returnedVo = migrationErrorDomainCapability.create(vo, getTestUser());
        assertEquals(ASSERT_STR, PPSConstants.VA_PRODUCT_FILE, returnedVo.getFileId());

        vo = createVo(PPSConstants.VA_DOSAGE_FORM_FILE, "24", "DosageForm", "0.1MG", "TAB", "Dosage Form Name Invalid.");
        returnedVo = migrationErrorDomainCapability.create(vo, getTestUser());
        assertEquals(ASSERT_STR, PPSConstants.VA_DOSAGE_FORM_FILE, returnedVo.getFileId());

        vo = createVo(PPSConstants.VA_DRUG_CLASS_FILE, "24", "DrugClass", "ERRVAL2", 
            "DrugClass Name", "DrugClass Name Invalid.");
        returnedVo = migrationErrorDomainCapability.create(vo, getTestUser());
        assertEquals(ASSERT_STR, PPSConstants.VA_DRUG_CLASS_FILE, returnedVo.getFileId());

        vo = createVo(PPSConstants.VA_DRUG_INGREDIENT_FILE, "24", "Ingredient", "ERRVAL3", 
            "BILBERRY", "Ingredient Name Invalid.");
        returnedVo = migrationErrorDomainCapability.create(vo, getTestUser());
        assertEquals(ASSERT_STR, PPSConstants.VA_DRUG_INGREDIENT_FILE, returnedVo.getFileId());

        vo = createVo(PPSConstants.VA_DRUG_UNIT_FILE, "24", "Drug Unit", "Drug Unit error", "DU", "Drug Unit Name Invalid.");
        returnedVo = migrationErrorDomainCapability.create(vo, getTestUser());
        assertEquals(ASSERT_STR, PPSConstants.VA_DRUG_UNIT_FILE, returnedVo.getFileId());
        
        vo = createVo(PPSConstants.VA_DISPENSE_UNIT_FILE, "24", "VA Dispense Unit", "dispensing", 
            "DIspense Name", "Dispenee Name Invalid.");
        returnedVo = migrationErrorDomainCapability.create(vo, getTestUser());
        assertEquals(ASSERT_STR, PPSConstants.VA_DISPENSE_UNIT_FILE, returnedVo.getFileId());
        
        vo = createVo(PPSConstants.VA_GENERIC_NAME_FILE, "24", "VA Generic Name", "genericin", 
            "Generic", "Generic Name Invalid.");
        returnedVo = migrationErrorDomainCapability.create(vo, getTestUser());
        assertEquals(ASSERT_STR, PPSConstants.VA_GENERIC_NAME_FILE, returnedVo.getFileId());

        vo = createVo(PPSConstants.VA_ORDERABLE_ITEM_FILE, "24", "OrderableItem Name", "oi ing", 
            "OrderableItem", "OrderableItem Name Invalid.");
        returnedVo = migrationErrorDomainCapability.create(vo, getTestUser());
        assertEquals(ASSERT_STR, PPSConstants.VA_ORDERABLE_ITEM_FILE, returnedVo.getFileId());

        vo = createVo(PPSConstants.VA_NDC_FILE, "24", "NDC Name", "NDC ing", "NDCItem", "NDC Name Invalid.");
        returnedVo = migrationErrorDomainCapability.create(vo, getTestUser());
        assertEquals(ASSERT_STR, PPSConstants.VA_NDC_FILE, returnedVo.getFileId());

        vo = createVo(PPSConstants.VA_MANUFACTURES_FILE, "24", "Manufacturer Name", "Manufacturer ing", 
            "Manufacturer", "Manufacturer Name Invalid.");
        returnedVo = migrationErrorDomainCapability.create(vo, getTestUser());
        assertEquals(ASSERT_STR, PPSConstants.VA_MANUFACTURES_FILE, returnedVo.getFileId());
        
        vo = createVo(PPSConstants.VA_PACKAGE_TYPE_FILE, "24", "Package Type Name", "Package Type ing", 
            "Package Type", "Package Type Name Invalid.");
        returnedVo = migrationErrorDomainCapability.create(vo, getTestUser());
        assertEquals(ASSERT_STR, PPSConstants.VA_PACKAGE_TYPE_FILE, returnedVo.getFileId());
       
    }

    /**
     * Used to Create a Vo
     * @param fileId fileId
     * @param rowId rowId
     * @param fieldName fieldName
     * @param fieldValue fieldValue
     * @param uniqueName uniqueName
     * @param detailedError detailed Error
     * @return MigrationErrorVo
     */
    private MigrationErrorVo createVo(String fileId, String rowId, String fieldName, String fieldValue, 
            String uniqueName, String detailedError) {
        MigrationErrorVo vo = new MigrationErrorVo();
        
        vo.setFileId(fileId);
        vo.setMigrationFieldName(fieldName);
        vo.setMigrationFieldValue(fieldValue);
        vo.setMigrationMultiRowId("23");
        vo.setMigrationMultiFieldName("MULTI_NAME");
        vo.setMigrationRowId(rowId);
        vo.setMigrationUniqueName(uniqueName);
        vo.setDetailedErrorText(detailedError);
        vo.setProcessedDTM(new Date());
        vo.setCreatedBy("Patrick");
        vo.setCreatedDate(new Date());
        
        return vo;
    }
    
    /**
     * This method gets all the FdbMfg in the db.
     *  
     * @throws Exception Exception
     */
    public void testFindAllMigrationError() throws Exception {
        List<MigrationErrorVo> rCollection;

        rCollection = migrationErrorDomainCapability.retrieveMigrationError();
        
        assertTrue("There should be some records in the migration error list", rCollection.size() != 0);

    }

   
}
