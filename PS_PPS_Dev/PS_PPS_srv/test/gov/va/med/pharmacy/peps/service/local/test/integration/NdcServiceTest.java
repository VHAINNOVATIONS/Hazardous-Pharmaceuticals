/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ModificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * test class for NDC service
 */
public class NdcServiceTest extends IntegrationTestCase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(NdcServiceTest.class);
    private static final String I9993 = "9993";
    private static final String I9992 = "9992";
    private ManagedItemService managedItemService;
   
    /**
     * NdcServiceTest
     * 
     * @param name String
     */
    public NdcServiceTest(String name) {
        super(name);
    }
  
    /**
     * sets up the ndc
     * 
     * @param ndc the Ndc
     * @param existingNdc the existing ndc
     * @throws ItemNotFoundException ItemNotFoundException
     */
    private void setupNdc(NdcVo ndc, NdcVo existingNdc) throws ItemNotFoundException {
        NdcGenerator gen = new NdcGenerator();
        gen.generateNdcVo(ndc, true, existingNdc);
    }

    /**
     * setUp
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        LOG.info(getName());
        setOutOfContainer(true);
        managedItemService = getNationalService(ManagedItemService.class);
    }

    /**
     * Add new NDC from existing template
     * 
     * @throws Exception Exception
     */
    public void testAddFromExistingTemplate() throws Exception {
        NdcVo ndcVo1 = (NdcVo) managedItemService.retrieve(I9993, EntityType.NDC);
        ndcVo1.setId(null);

        setupNdc(ndcVo1, null);

        // insert the NDC
        ProcessedItemVo processedItem = managedItemService.create(ndcVo1, buildUser());
        NdcVo insertedNdc = (NdcVo) processedItem.getItem();
        assertNotNull("ID should be always be set by create method", insertedNdc.getId());

        // retrieve the ndc then check if everything was persisted
        NdcVo retrievedVo = (NdcVo) managedItemService.retrieve(insertedNdc.getId(), EntityType.NDC);

        assertEquals("This Source should be VA", "VA", retrievedVo.getSource().name());

        // go through all the va data fields for ndc and check that the values set match the values retrieved
        Collection<FieldKey> fields = FieldKey.getNdcDataFields();

        // iterate through the fields for testAddFromExistingTemplate
        for (FieldKey key : fields) {
            LOG.info(key);

            if (key.isListDataField()) {
                ListDataField list = (ListDataField) insertedNdc.getVaDataFields().getDataField(key);
                ListDataField savedList = (ListDataField) retrievedVo.getVaDataFields().getDataField(key);

                // check that the selected values of the list data fields are correct
                if (list != null && savedList != null) {
                    for (Object selection : list.getValue()) {
                        assertTrue("This is an incorrect list data field " + key, savedList.contains((String) selection));
                    }

                    // check that the editable property of the list data fields are correct
                    assertEquals("This is an incorrect editable value of list data field " + key, list.isEditable(), savedList
                        .isEditable());
                }
            } else {
                if ((insertedNdc.getVaDataFields().getDataField(key) != null)
                    && (retrievedVo.getVaDataFields().getDataField(key) != null)) {
                    assertEquals(" data field " + key + "  incorrect", insertedNdc.getVaDataFields().getDataField(key)
                        .getValue(), retrievedVo.getVaDataFields().getDataField(key).getValue());
                }

            }
        }
    }

    /**
     * Add new NDC from existing inactive template
     * 
     * @throws Exception Exception
     */
    public void testAddFromExistingInactiveTemplate() throws Exception {
        NdcVo ndcVo1 = (NdcVo) managedItemService.retrieve(I9993, EntityType.NDC);
        ndcVo1.setId(null);
        setupNdc(ndcVo1, null);
        ndcVo1.inactivate();

        // insert the NDC
        ProcessedItemVo processedItem = managedItemService.create(ndcVo1, buildUser());
        NdcVo insertedNdc = (NdcVo) processedItem.getItem();
        assertNotNull("The Id should be set by create method", insertedNdc.getId());

        ndcVo1 = (NdcVo) managedItemService.retrieve(insertedNdc.getId(), EntityType.NDC);
        ndcVo1.setId(null);
        setupNdc(ndcVo1, null);

        // insert the NDC
        processedItem = managedItemService.create(ndcVo1, buildUser());
        insertedNdc = (NdcVo) processedItem.getItem();
        assertNotNull("ID should be set by create method", insertedNdc.getId());

        // retrieve the ndc then check if everything was persisted
        NdcVo retrievedVo = (NdcVo) managedItemService.retrieve(insertedNdc.getId(), EntityType.NDC);

        assertEquals(" Source should be VA", "VA", retrievedVo.getSource().name());

        // go through all the va data fields for ndc and check that the values set match the values retrieved
        Collection<FieldKey> fields = FieldKey.getNdcDataFields();

        // iterate through the field keys for testAddFromExistingInactiveTemplate
        for (FieldKey key : fields) {
            LOG.info(key);

            if (key.isListDataField()) {
                ListDataField list = (ListDataField) insertedNdc.getVaDataFields().getDataField(key);
                ListDataField savedList = (ListDataField) retrievedVo.getVaDataFields().getDataField(key);

                // check that the selected values of the list data fields are correct
                if (list != null && savedList != null) {
                    for (Object selection : list.getValue()) {
                        assertTrue("  incorrect list data field " + key, savedList.contains((String) selection));
                    }

                    // check that the editable property of the list data fields are correct
                    assertEquals("  incorrect editable value of list data field " + key, list.isEditable(), savedList
                        .isEditable());
                }
            } else {
                if ((insertedNdc.getVaDataFields().getDataField(key) != null)
                    && (retrievedVo.getVaDataFields().getDataField(key) != null)) {
                    assertEquals("data field  " + key + " incorrect ", insertedNdc.getVaDataFields().getDataField(key)
                        .getValue(), retrievedVo.getVaDataFields().getDataField(key).getValue());
                }

            }
        }
    }

    /**
     * Add new NDC from blank template
     * 
     * @throws Exception Exception
     */
    public void testAddFromBlankTemplate() throws Exception {
        NdcVo existingNdc = (NdcVo) managedItemService.retrieve(I9993, EntityType.NDC);
        NdcVo ndcVo1 = (NdcVo) managedItemService.retrieveBlankTemplate(EntityType.NDC);

        setupNdc(ndcVo1, existingNdc);

        // insert the NDC
        ProcessedItemVo processedItem = managedItemService.create(ndcVo1, buildUser());
        NdcVo insertedNdc = (NdcVo) processedItem.getItem();
        assertNotNull("id should be set by create method", insertedNdc.getId());

        // retrieve the ndc then check if everything was persisted
        NdcVo retrievedVo = (NdcVo) managedItemService.retrieve(insertedNdc.getId(), EntityType.NDC);

        assertEquals("Source should be VA", "VA", retrievedVo.getSource().name());

        // go through all the va data fields for ndc and check that the values set match the values retrieved
        Collection<FieldKey> fields = FieldKey.getNdcDataFields();

        for (FieldKey key : fields) {
            LOG.info(key);

            if (key.isListDataField()) {
                ListDataField list = (ListDataField) insertedNdc.getVaDataFields().getDataField(key);
                ListDataField savedList = (ListDataField) retrievedVo.getVaDataFields().getDataField(key);

                // check that the selected values of the list data fields are correct
                if (list != null && savedList != null) {
                    for (Object selection : list.getValue()) {
                        assertTrue("incorrect list data field " + key, savedList.contains((String) selection));
                    }

                    // check that the editable property of the list data fields are correct
                    assertEquals("incorrect editable value of list data field " + key, list.isEditable(), savedList
                        .isEditable());
                }
            } else {
                if ((insertedNdc.getVaDataFields().getDataField(key) != null)
                    && (retrievedVo.getVaDataFields().getDataField(key) != null)) {
                    assertEquals("data field " + key + " incorrect", insertedNdc.getVaDataFields().getDataField(key)
                        .getValue(), retrievedVo.getVaDataFields().getDataField(key).getValue());
                }

            }
        }
    }

    /**
     * retrieves the NDC item by ID
     * 
     * @throws Exception Exception
     */
    public void testRetrieveNdcItemDetail() throws Exception {

        NdcVo ndc = (NdcVo) managedItemService.retrieve(I9992, EntityType.NDC);

        LOG.info("Retrieved NDC: " + ndc);
        assertNotNull("No Item exists for this Id", ndc);

    }

    /**
     * retrieves the collection of NDC for a given item
     */
    public void testRetrieveNdcCollection() {

        List<ManagedItemVo> ndcList = managedItemService.retrieveChildren(I9992, EntityType.PRODUCT);

        assertNotNull("ndcList was null", ndcList);
        assertTrue("There should be  NDCs for this productId", ndcList.size() > 0);

    }

    /**
     * retrieves empty NDC Template
     * 
     * @throws Exception Exception
     */
    public void testRetrieveEmpty() throws Exception {
        NdcVo empty = (NdcVo) managedItemService.retrieveBlankTemplate(EntityType.NDC);

        // template is not null
        assertNotNull("The template is Null", empty);

        // package type is null
        assertNull("Package Type ID is not null", empty.getPackageType());

    }

    /**
     * retrieve existing template by Id
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public void testRetrieveExistingTemplate() throws ItemNotFoundException {

        String ndcId = "9995";
        NdcVo existingTemplate = (NdcVo) managedItemService.retrieveTemplate(ndcId, EntityType.NDC);

        // id and ndc are null
        assertNull("The NDC ID of the template should be set to null", existingTemplate.getId());
        assertEquals("The NDC number ot the template should be set to empty string", "", existingTemplate.getNdc());

        // other fields are same
        // test that the package type is the same in NDC item and the NDC template
        NdcVo ndc = (NdcVo) managedItemService.retrieve(ndcId, EntityType.NDC);
        assertEquals("Package Types do not match", ndc.getPackageType(), existingTemplate.getPackageType());

    }
    
    /**
     * when no value is enterer for ndc price per order unit field, this field is auto-populated with "0.0"
     * 
     * @throws Exception Exception
     */
    public void testNdcPricePerOrderUnitAutoPopulateBlankFieldWithDefaultValue() throws Exception {
        NdcVo ndc = (NdcVo) managedItemService.retrieve(I9993, EntityType.NDC);
        NdcVo newNdc = ndc.copy();
        DataFields<DataField> vadf = ndc.getVaDataFields();
        DataField<Double> ndcPricePerOrdUnit = vadf.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
        ndcPricePerOrdUnit.selectValue(null);

        ProcessedItemVo processedNdc = managedItemService.commitModifications(ndc.compareDifferences(newNdc), ndc,
            new UserGenerator().getLocalManagerOne());

        NdcVo updatedNdc = (NdcVo) processedNdc.getItem();
        DataFields<DataField> updatedVaDfs = updatedNdc.getVaDataFields();
        DataField<Double> updatedNdcPricePerOrdUnit = updatedVaDfs.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
        
        assertEquals("Auto-population of the blank field failed! ", 0.0, updatedNdcPricePerOrdUnit.getValue()
            .doubleValue());
    }

    /**
     * In the following test ndc price per order unit is modified to a new value that is outside the acceptable range for
     * this field. A validation error is expected to be thrown.
     * 
     * @throws Exception Exception
     */
    public void testModifyNdcPricePerOrderUnitOutSideMinMaxRange() throws Exception {
        NdcVo ndc = (NdcVo) managedItemService.retrieve(I9993, EntityType.NDC);
        NdcVo newNdc = ndc.copy();
        DataFields<DataField> vadf = newNdc.getVaDataFields();
        DataField<Double> ndcPricePerOrdUnit = vadf.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
        ndcPricePerOrdUnit.selectValue(new Double("99999999"));

        try {
            ModificationSummaryVo modSummary = managedItemService.submitModifications(ndc, newNdc, new UserGenerator()
                .getLocalManagerOne());
            assertNull("mod summary should not be null.",  modSummary);
                
        } catch (ValueObjectValidationException ve) {
            boolean blnValidationError = false;
            
            if (ve.getErrors() != null) {
                for (ValidationError verr : ve.getErrors().getErrors()) {
                    if ((verr.getErrorKey().equals(ErrorKey.COMMON_MAX_MIN_VALUE_INCLUSIVE))
                        && (verr.getFieldKey().equals(FieldKey.NDC_PRICE_PER_ORDER_UNIT))) {
                        blnValidationError = true;
                        break;
                    }
                }

                assertTrue("There  should have been range check validation error!", blnValidationError);
            }

        }// end catch
    }

    /**
     * In the following test ndc price per order unit is modified to a new value that contains five decimal digits. A
     * validation error is expected to be thrown.
     * 
     * @throws Exception v
     */
    public void testModifyNdcPricePerOrderUnitToThreeDecimal() throws Exception {
        NdcVo ndc = (NdcVo) managedItemService.retrieve(I9993, EntityType.NDC);
        NdcVo newNdc = ndc.copy();
        DataFields<DataField> vadf = newNdc.getVaDataFields();
        DataField<Double> ndcPricePerOrdUnit = vadf.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
        ndcPricePerOrdUnit.selectValue(PPSConstants.D1POINT98);

        try {
            ModificationSummaryVo modSummary = managedItemService.submitModifications(ndc, newNdc, new UserGenerator()
                .getLocalManagerOne());
            assertNull("The Mod summary should not be null.",  modSummary);
        } catch (ValueObjectValidationException ve) {
            boolean blnValidationError = false;

            if (ve.getErrors() != null) {
                for (ValidationError verr : ve.getErrors().getErrors()) {
                    if (verr.getErrorKey().equals(ErrorKey.COMMON_INCORRECT_DECIMAL_DIGITS)) {
                        blnValidationError = true;
                        break;
                    }
                }

                assertTrue("There should have been range check validation error", blnValidationError);
            }

        }// end catch
    }
    
    /**
     * when no value is enterer for unit price field, this field is auto-populated with "0.0"
     * 
     * @throws Exception Exception
     */
    public void testUnitPriceAutoPopulateBlankFieldWithDefaultValue() throws Exception {
        NdcVo ndc = (NdcVo) managedItemService.retrieve(I9993, EntityType.NDC);
        NdcVo newNdc = ndc.copy();
        DataFields<DataField> vadf = ndc.getVaDataFields();
        DataField<Double> unitPrice = vadf.getDataField(FieldKey.UNIT_PRICE);
        unitPrice.selectValue(null);

        ProcessedItemVo processedNdc = managedItemService.commitModifications(ndc.compareDifferences(newNdc), ndc,
            new UserGenerator().getLocalManagerOne());

        NdcVo updatedNdc = (NdcVo) processedNdc.getItem();
        DataFields<DataField> updatedVaDfs = updatedNdc.getVaDataFields();
        DataField<Double> updatedUnitPrice = updatedVaDfs.getDataField(FieldKey.UNIT_PRICE);
        
        assertEquals("Auto-population of the blank field failed!", 0.0,  updatedUnitPrice.getValue()
            .doubleValue());
    }

    /**
     * In the following test unit price is modified to a new value that is outside the acceptable range for
     * this field. A validation error is expected to be thrown.
     * 
     * @throws Exception Exception
     */
    public void testModifyUnitPriceOutSideMinMaxRange() throws Exception {
        NdcVo ndc = (NdcVo) managedItemService.retrieve(I9993, EntityType.NDC);
        NdcVo newNdc = ndc.copy();
        DataFields<DataField> vadf = newNdc.getVaDataFields();
        DataField<Double> unitPrice = vadf.getDataField(FieldKey.UNIT_PRICE);
        unitPrice.selectValue(new Double("99999999"));

        try {
            ModificationSummaryVo modSummary = managedItemService.submitModifications(ndc, newNdc, new UserGenerator()
                .getLocalManagerOne());
            assertNull("mod summary should not be null!",  modSummary);
        } catch (ValueObjectValidationException ve) {
            boolean blnValidationError = false;

            if (ve.getErrors() != null) {
                for (ValidationError verr : ve.getErrors().getErrors()) {
                    if ((verr.getErrorKey().equals(ErrorKey.COMMON_MAX_MIN_VALUE_INCLUSIVE))
                        && (verr.getFieldKey().equals(FieldKey.UNIT_PRICE))) {
                        blnValidationError = true;
                        break;
                    }
                }

                assertTrue("There should have been range check validation error.", blnValidationError);
            }

        }// end catch
    }

    /**
     * In the following test unit price is modified to a new value that contains five decimal digits. A
     * validation error is expected to be thrown.
     * 
     * @throws Exception Exception
     */
    public void testModifyUnitPriceToThreeDecimal() throws Exception {
        NdcVo ndc = (NdcVo) managedItemService.retrieve(I9993, EntityType.NDC);
        NdcVo newNdc = ndc.copy();
        DataFields<DataField> vadf = newNdc.getVaDataFields();
        DataField<Double> unitPrice = vadf.getDataField(FieldKey.UNIT_PRICE);
        unitPrice.selectValue(new Double("1.999"));

        try {
            ModificationSummaryVo modSummary = managedItemService.submitModifications(ndc, newNdc, new UserGenerator()
                .getLocalManagerOne());
            assertNull("modSummary should not be null.",  modSummary);
        } catch (ValueObjectValidationException ve) {
            boolean blnValidationError = false;

            if (ve.getErrors() != null) {
                for (ValidationError verr : ve.getErrors().getErrors()) {
                    if (verr.getErrorKey().equals(ErrorKey.COMMON_INCORRECT_DECIMAL_DIGITS)) {
                        blnValidationError = true;
                        break;
                    }
                }

                assertTrue("Thre should have been range check validation error", blnValidationError);
            }

        }// end catch
    }

    /**
     * Makes a user
     * 
     * @return UserVo
     */
    private UserVo buildUser() {
        return new UserGenerator().getLocalManagerOne();
    }

    /**
     * test testPartialSave
     * 
     * @throws Exception Exception
     */
    public void testPartialSave() throws Exception {

        NdcVo ndcVo;
        UserVo userVo;

        userVo = new UserVo();
        ndcVo = new NdcVo();

        ndcVo.setNdc("12345678901");
        ndcVo.setId(null);
        userVo.setUsername("User1");

        managedItemService = getNationalService(ManagedItemService.class);

        ndcVo = (NdcVo) managedItemService.savePartial(ndcVo, "A test comment", userVo);

        assertNotNull("Value null", ndcVo);
    }

    /**
     *  testModifyNDCDispenseUnitsPerOrderUnit
     * 
     * @throws Exception Exception
     */
    public void testModifyNDCDispenseUnitsPerOrderUnit() throws Exception {
        String id = I9993;
        NdcVo newVo = (NdcVo) managedItemService.retrieve(id, EntityType.NDC);
        NdcVo oldVo = (NdcVo) managedItemService.retrieve(id, EntityType.NDC);
        double ndcDispUnits;

        ndcDispUnits = Math.max(1, RandomGenerator.nextLong(PPSConstants.I10));
        newVo.setNdcDispUnitsPerOrdUnit(ndcDispUnits);

        Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>();
        Collection<Difference> differences = oldVo.diff(newVo);

        for (Difference diff : differences) {
            ModDifferenceVo modDiff = new ModDifferenceVo();
            modDiff.setDifference(diff);
            modDiff.setModificationReason("TEST reason");
            modDifferences.add(modDiff);
        }

        managedItemService.commitModifications(modDifferences, oldVo, buildUser());

        NdcVo retrievedVo = (NdcVo) managedItemService.retrieve(id, EntityType.NDC);

        assertEquals("NDC Dispense Units Per Order Unit was wrong", ndcDispUnits, retrievedVo.getNdcDispUnitsPerOrdUnit());
    }

    /**
     * Modify various fields to verify interface integration.
     * 
     * @throws Exception Exception
     */
    public void testModifyUpn() throws Exception {
        String id = I9993;
        NdcVo newVo = (NdcVo) managedItemService.retrieve(id, EntityType.NDC);
        NdcVo oldVo = (NdcVo) managedItemService.retrieve(id, EntityType.NDC);

        String newUpn = Integer.toString(Math.abs(new Random().nextInt()));
        newVo.setUpcUpn(newUpn);

        Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>();
        Collection<Difference> differences = oldVo.diff(newVo);

        for (Difference diff : differences) {
            ModDifferenceVo modDiff = new ModDifferenceVo();
            modDiff.setDifference(diff);
            modDiff.setModificationReason("test reason");
            modDifferences.add(modDiff);
        }

        managedItemService.commitModifications(modDifferences, oldVo, buildUser());

        NdcVo retrievedVo = (NdcVo) managedItemService.retrieve(id, EntityType.NDC);

        assertEquals("UpcUpnn is not correct", newUpn, retrievedVo.getUpcUpn());
    }
}
