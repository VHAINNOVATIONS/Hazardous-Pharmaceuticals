/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
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
  
    private static final Logger LOG = Logger.getLogger(NdcServiceTest.class);
    private String i9993 = "9993";
    private String i9991 = "9991";
    private String va = "VA";
    private ManagedItemService managedItemService;

    /**
     * This is the constructor for the NdcServiceTest
     * 
     * @param name String
     */
    public NdcServiceTest(String name) {
        super(name);
    }

    /**
     * setUp
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp()  {
        LOG.debug("----------------" + getName() + "---------------");
        managedItemService = getNationalService(ManagedItemService.class);
    }

    /**
     * Setup a NDC for the test case
     * 
     * @param ndc NDC which to update
     * @param existingNdc Existing NDC
     * @throws ItemNotFoundException if not found
     */
    private void setupNdc(NdcVo ndc, NdcVo existingNdc) throws ItemNotFoundException {
        NdcGenerator gen = new NdcGenerator();
        gen.generateNdcVo(ndc, false, existingNdc);
    }

    /**
     * add new Ndc
     * 
     * @throws Exception Exception
     */
    public void testCreate() throws Exception {

        NdcVo ndcVo1 = (NdcVo) managedItemService.retrieveTemplate(i9993, EntityType.NDC);
        setupNdc(ndcVo1, null);
        ProcessedItemVo processItem2 = managedItemService.create(ndcVo1, buildUser());
        NdcVo ndcVo2 = (NdcVo) processItem2.getItem();

        assertNotNull(new String("Null returned."), managedItemService.retrieve(i9993, EntityType.NDC));
        assertNotNull(new String("Null returned"), ndcVo2);
    }

    /**
     * Add new NDC from existing template
     * 
     * @throws Exception Exception
     */
    public void testAddFromExistingTemplate() throws Exception {
        NdcVo ndcVo1 = (NdcVo) managedItemService.retrieve(i9993, EntityType.NDC);

        ndcVo1.setId(null);
        setupNdc(ndcVo1, null);

        // insert the NDC
        ProcessedItemVo insertedProcessItem = managedItemService.create(ndcVo1, buildUser());
        NdcVo insertedNdc = (NdcVo) insertedProcessItem.getItem();
        assertNotNull(" Id should be set by create method.", insertedNdc.getId());

        // retrieve the product then check if everything was persisted
        NdcVo retrievedVo = (NdcVo) managedItemService.retrieve(insertedNdc.getId(), EntityType.NDC);

        assertEquals("Source should be VA.", va, retrievedVo.getSource().name());

        // go through all the va data fields for product and check that the values set match the values retrieved
        Collection<FieldKey> fields = FieldKey.getNdcDataFields();

        // iterated throught the fields for testAddFromExistingTemplate
        for (FieldKey key : fields) {
            LOG.debug(key);

            if (key.isListDataField()) {
                ListDataField list = (ListDataField) insertedNdc.getVaDataFields().getDataField(key);
                ListDataField savedList = (ListDataField) retrievedVo.getVaDataFields().getDataField(key);

                // check that the selected values of the list data fields are correct
                if (list != null && savedList != null) {
                    for (Object selection : list.getValue()) {
                        assertTrue("Incorrect list data field " + key, savedList.contains((String) selection));
                    }

                    // check that the editable property of the list data fields are correct
                    assertEquals("Incorrect editable value of list data field " + key, list.isEditable(), savedList
                        .isEditable());
                }
            } else {
                if ((insertedNdc.getVaDataFields().getDataField(key) != null)
                    && (retrievedVo.getVaDataFields().getDataField(key) != null)) {
                    assertEquals("Data field " + key + " not correct", insertedNdc.getVaDataFields().getDataField(key)
                        .getValue(), retrievedVo.getVaDataFields().getDataField(key).getValue());
                }

            }
        }
    }

    /**
     * testAddFromBlankTemplate:  Adds a new NDC from blank template 
     * 
     * @throws Exception Exception
     */
    public void testAddFromBlankTemplate() throws Exception {
        NdcVo existingNdc = (NdcVo) managedItemService.retrieve(i9993, EntityType.NDC);
        NdcVo ndcVo1 = (NdcVo) managedItemService.retrieveBlankTemplate(EntityType.NDC);

        setupNdc(ndcVo1, existingNdc);

        // insert the NDC
        ProcessedItemVo insertedProcessItem = managedItemService.create(ndcVo1, buildUser());
        NdcVo insertedNdc = (NdcVo) insertedProcessItem.getItem();
        assertNotNull("Id should be set by create method", insertedNdc.getId());

        // retrieve the product then check if everything was persisted
        NdcVo retrievedVo = (NdcVo) managedItemService.retrieve(insertedNdc.getId(), EntityType.NDC);

        assertEquals("Source should be VA!", va, retrievedVo.getSource().name());

        // go through all the va data fields for product and check that the values set match the values retrieved
        Collection<FieldKey> fields = FieldKey.getNdcDataFields();

        // iterate through the fields for testAddFromBlankTemplate
        for (FieldKey key : fields) {
            LOG.debug(key);

            if (key.isListDataField()) {
                ListDataField list = (ListDataField) insertedNdc.getVaDataFields().getDataField(key);
                ListDataField savedList = (ListDataField) retrievedVo.getVaDataFields().getDataField(key);

                // check that the selected values of the list data fields are correct
                if (list != null && savedList != null) {
                    for (Object selection : list.getValue()) {
                        assertTrue("incorrect list data field - " + key, savedList.contains((String) selection));
                    }

                    // check that the editable property of the list data fields are correct
                    assertEquals("incorrect editable value of list data field - " + key, list.isEditable(), savedList
                        .isEditable());
                }
            } else {
                if ((insertedNdc.getVaDataFields().getDataField(key) != null)
                    && (retrievedVo.getVaDataFields().getDataField(key) != null)) {
                    assertEquals("data field :" + key + " incorrect!", insertedNdc.getVaDataFields().getDataField(key)
                        .getValue(), retrievedVo.getVaDataFields().getDataField(key).getValue());
                }

            }
        }
    }

    /**
     * retrieves the NDC item by ID
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public void testRetrieve() throws ItemNotFoundException {

        String ndcId = i9993;
        NdcVo retrievedNdc = (NdcVo) managedItemService.retrieve(ndcId, EntityType.NDC);

        LOG.debug("Retrieved NDC: " + retrievedNdc);
        assertNotNull("No Item exists for this Id", retrievedNdc);

    }

    /**
     * retrieves the collection of NDC for a given item
     */
    public void testRetrieveNDCCollection() {

        String productId = "9997";
        List<ManagedItemVo> ndcList = managedItemService.retrieveChildren(productId, EntityType.PRODUCT);

        assertNotNull("ndcList was null", ndcList);
        assertTrue("There should be  NDCs for this productId", ndcList.size() > 0);

    }

    /**
     * retrieves empty NDC Template
     * 
     * @throws Exception if error
     */
    public void testRetrieveEmpty() throws Exception {

        NdcVo empty = (NdcVo) managedItemService.retrieveBlankTemplate(EntityType.NDC);

        // template is not null
        assertNotNull("The template is Null", empty);

        // package type ID is null
        assertNull("Package Type ID is not null", empty.getPackageType());

    }

    /**
     * retrieve existing template by Id
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public void testRetrieveExistingTemplate() throws ItemNotFoundException {

        String ndcId = i9993;
        NdcVo existingTemplate = (NdcVo) managedItemService.retrieveTemplate(ndcId, EntityType.NDC);

        // id and ndc are null
        assertNull("The NDC ID of the template should be set to null!", existingTemplate.getId());
        assertEquals("The NDC number ot the template should be set to empty string!", "", existingTemplate.getNdc());

        // other fields are same
        // test that the package type is the same in NDC item and the NDC template
        NdcVo ndc = (NdcVo) managedItemService.retrieve(ndcId, EntityType.NDC);
        assertEquals("Package Types do not match!", ndc.getPackageType(), existingTemplate.getPackageType());

    }

    /**
     * retrieve existing template by passing object
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public void testRetrieveTemplate() throws ItemNotFoundException {

        String ndcId = i9993;
        NdcVo ndc = (NdcVo) managedItemService.retrieve(ndcId, EntityType.NDC);

        // retrieve NDC template for the NDC Vo
        NdcVo template = (NdcVo) managedItemService.retrieveTemplate(ndcId, EntityType.NDC);

        // id and ndc are null
        assertNull("The NDC ID of the template should be set to null", template.getId());
        assertEquals("The NDC number ot the template should be set to empty string", "", template.getNdc());

        // other fields are same
        // test that the package type is the same in NDC item and the NDC template
        assertEquals("Package Types do not match", ndc.getPackageType(), template.getPackageType());

    }

    /**
     * testChangeProductParent
     * 
     * @throws ValidationException ValidationException
     */
    public void testChangeProductParent() throws ValidationException {
        String ndcId = i9993;
        String productId = i9991;

        NdcVo ndcOld = (NdcVo) managedItemService.retrieve(ndcId, EntityType.NDC);
        NdcVo ndcNew = (NdcVo) managedItemService.retrieve(ndcId, EntityType.NDC);
        ProductVo productNew = (ProductVo) managedItemService.retrieve(productId, EntityType.PRODUCT);

        ndcNew.setProduct(productNew);
        Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>();
        Collection<Difference> differences = ndcOld.diff(ndcNew);

        for (Difference diff : differences) {
            ModDifferenceVo modDiff = new ModDifferenceVo();
            modDiff.setDifference(diff);
            modDiff.setModificationReason("test reason!");
            modDiff.setAcceptChange(true);
            modDifferences.add(modDiff);
        }

        managedItemService.commitModifications(modDifferences, ndcOld, buildUser());

        NdcVo retrievedVo = (NdcVo) managedItemService.retrieveTemplate(ndcId, EntityType.NDC);

        assertEquals("Product parent did not change correctly", productId, retrievedVo.getProduct().getId());
    }

    /**
     * testModifyNDCDispenseUnitsPerOrderUnit
     * 
     * @throws Exception Exception
     */
    public void testModifyNDCDispenseUnitsPerOrderUnit() throws Exception {
        String id = i9991;
        NdcVo newVo = (NdcVo) managedItemService.retrieve(id, EntityType.NDC);
        NdcVo oldVo = (NdcVo) managedItemService.retrieve(id, EntityType.NDC);
        double ndcDispUnits;

        ndcDispUnits = RandomGenerator.nextLong(PPSConstants.I10);
        newVo.setNdcDispUnitsPerOrdUnit(ndcDispUnits);

        Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>();
        Collection<Difference> differences = oldVo.diff(newVo);

        for (Difference diff : differences) {
            
            // create the ModDifferenceVo
            ModDifferenceVo modDiff = new ModDifferenceVo();
            modDiff.setDifference(diff);
            modDiff.setModificationReason("test reason");
            modDifferences.add(modDiff);
        }

        managedItemService.commitModifications(modDifferences, oldVo, buildUser());

        NdcVo retrievedVo = (NdcVo) managedItemService.retrieve(id, EntityType.NDC);

        assertEquals("NDC Dispense Units Per Order Unit was wrong", ndcDispUnits, retrievedVo.getNdcDispUnitsPerOrdUnit());

    }

    /**
     * Add new NDC from existing inactive template
     * 
     * @throws Exception Exception
     */
    public void testAddFromExistingInactiveTemplate() throws Exception {
        NdcVo ndcVo1 = (NdcVo) managedItemService.retrieve(i9993, EntityType.NDC);
        ndcVo1.setId(null);
        setupNdc(ndcVo1, null);
        ndcVo1.inactivate();

        // insert the NDC
        ProcessedItemVo processedItem = managedItemService.create(ndcVo1, buildUser());
        NdcVo insertedNdc = (NdcVo) processedItem.getItem();
        assertNotNull("id should be set by create method.", insertedNdc.getId());

        ndcVo1 = (NdcVo) managedItemService.retrieve(insertedNdc.getId(), EntityType.NDC);
        ndcVo1.setId(null);
        setupNdc(ndcVo1, null);

        // insert the NDC
        processedItem = managedItemService.create(ndcVo1, buildUser());
        insertedNdc = (NdcVo) processedItem.getItem();
        assertNotNull("id should be set by create method", insertedNdc.getId());

        // retrieve the product then check if everything was persisted
        NdcVo retrievedVo = (NdcVo) managedItemService.retrieve(insertedNdc.getId(), EntityType.NDC);

        assertEquals("Source should be VA", va, retrievedVo.getSource().name());

        // go through all the va data fields for product and check that the values set match the values retrieved
        Collection<FieldKey> fields = FieldKey.getNdcDataFields();

        for (FieldKey key : fields) {
            LOG.debug(key);

            if (key.isListDataField()) {
                ListDataField list = (ListDataField) insertedNdc.getVaDataFields().getDataField(key);
                ListDataField savedList = (ListDataField) retrievedVo.getVaDataFields().getDataField(key);

                // check that the selected values of the list data fields are correct
                if (list != null && savedList != null) {
                    for (Object selection : list.getValue()) {
                        assertTrue("This is the incorrect list data field " + key, savedList.contains((String) selection));
                    }

                    // check that the editable property of the list data fields are correct
                    assertEquals("This is the incorrect editable value of list data field " + key, list.isEditable(), savedList
                        .isEditable());
                }
            } else {
                
                // if neither of the keys are null
                if ((insertedNdc.getVaDataFields().getDataField(key) != null)
                    && (retrievedVo.getVaDataFields().getDataField(key) != null)) {
                    assertEquals("data field " + key + " incorrect", insertedNdc.getVaDataFields().getDataField(key)
                        .getValue(), retrievedVo.getVaDataFields().getDataField(key).getValue());
                }

            }
        }
    }

    /**
     * when no value is enterer for ndc price per dispense unit field, this field is auto-populated with "0.0"
     * 
     * @throws Exception Exception
     */
    public void testNdcPricePerDispenseUnitAutoPopulateBlankFieldWithDefaultValue() throws Exception {
        NdcVo ndc = (NdcVo) managedItemService.retrieve(i9993, EntityType.NDC);
        NdcVo newNdc = ndc.copy();
        DataFields<DataField> vadf = ndc.getVaDataFields();
        DataField<Double> ndcPricePerDispUnit = vadf.getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);
        ndcPricePerDispUnit.selectValue(null);

        ProcessedItemVo processedNdc = managedItemService.commitModifications(ndc.compareDifferences(newNdc), ndc,
            new UserGenerator().getNationalManagerOne());

        NdcVo updatedNdc = (NdcVo) processedNdc.getItem();
        DataFields<DataField> updatedVaDfs = updatedNdc.getVaDataFields();
        DataField<Double> updatedNdcPricePerDispUnit = updatedVaDfs.getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);
        
        assertEquals("Auto-population of the blank field failed!", 0.0, updatedNdcPricePerDispUnit.getValue()
            .doubleValue());
    }

    /**
     * In the following test ndc price per dispense unit is modified to a new value that is outside the acceptable range for
     * this field. A validation error is expected to be thrown.
     * 
     * @throws Exception Exception
     */
    public void testModifyNdcPricePerDispenseUnitOutSideMinMaxRange() throws Exception {
        NdcVo ndc = (NdcVo) managedItemService.retrieve(i9993, EntityType.NDC);
        NdcVo newNdc = ndc.copy();
        DataFields<DataField> vadf = newNdc.getVaDataFields();
        DataField<Double> ndcPricePerDispUnit = vadf.getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);
        ndcPricePerDispUnit.selectValue(new Double("99999999"));

        try {
            managedItemService.submitModifications(ndc, newNdc, new UserGenerator()
                .getNationalManagerOne());
        } catch (ValueObjectValidationException ve) {
            boolean blnValidationError = false;

            if (ve.getErrors() != null) {
                for (ValidationError verr : ve.getErrors().getErrors()) {
                    if ((verr.getErrorKey().equals(ErrorKey.COMMON_MAX_MIN_VALUE_INCLUSIVE))
                        && (verr.getFieldKey().equals(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT))) {
                        blnValidationError = true;
                        break;
                    }
                }

                assertTrue("There should have been range check validation error recorded!", blnValidationError);
            }

        }// end catch
    }

    /**
     * In the following test ndc price per dispense unit is modified to a new value that contains five decimal digits. A
     * validation error is expected to be thrown.
     * 
     * @throws Exception Exception
     */
    public void testModifyNdcPricePerDispenseUnitToFiveDecimal() throws Exception {
        NdcVo ndc = (NdcVo) managedItemService.retrieve(i9993, EntityType.NDC);
        NdcVo newNdc = ndc.copy();
        DataFields<DataField> vadf = newNdc.getVaDataFields();
        DataField<Double> ndcPricePerDispUnit = vadf.getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);
        ndcPricePerDispUnit.selectValue(new Double("1.99999"));

        try {
            managedItemService.submitModifications(ndc, newNdc, new UserGenerator()
                .getNationalManagerOne());
        } catch (ValueObjectValidationException ve) {
            boolean blnValidationError = false;

            if (ve.getErrors() != null) {
                for (ValidationError verr : ve.getErrors().getErrors()) {
                    if (verr.getErrorKey().equals(ErrorKey.COMMON_INCORRECT_DECIMAL_DIGITS)) {
                        blnValidationError = true;
                        break;
                    }
                }
                
                // assert if there were not validation errors.
                assertTrue("Thre should have been range check validation error", blnValidationError);
            }

        }// end catch
    }

    /**
     * Builds a user
     * 
     * @return userVo
     */
    private UserVo buildUser() {
        return new UserGenerator().getLocalManagerOne();
    }
}
