/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedRequestVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * national product service test
 */
public class ProductServiceTest extends IntegrationTestCase {

    /**
     * APP_PACKAGE_USE_DEFAULT
     */
    public static final String APP_PACKAGE_USE_DEFAULT = "O-OUTPATIENT";

    /**
     * AR_WS_AMIS_CATEGORY_DEFAULT
     */
    public static final String AR_WS_AMIS_CATEGORY_DEFAULT = "3-BLOOD PRODUCT";

    /**
     * RECALL_LEVEL_DEFAULT
     */
    public static final String RECALL_LEVEL_DEFAULT = "1-CLASS 1";

    private static final Logger LOG = Logger.getLogger(ProductServiceTest.class);
    private ManagedItemService managedItemService;
    private RequestService requestService;

    private String s9991 = "9991";

    /**
     * the set up method
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.debug("-----------------" + getName() + "---------------");

        this.managedItemService = getNationalService(ManagedItemService.class);
        this.requestService = getNationalService(RequestService.class);
    }

//    /**
//     * 
//     * 
//     * @param product the product
//     * @param oi the oi parent
//     * @param oneDrugClass the drug class
//     * @throws ItemNotFoundException
//     */
//    private void setupProduct(ProductVo product, boolean oi, boolean oneDrugClass) throws ItemNotFoundException {
//        ProductGenerator gen = new ProductGenerator();
//        gen.generateProductVo(product, false, oi, oneDrugClass);
//
//        // have to do this here because the generator can't use the managed item service
//        DataFields<DataField> vadfs = product.getVaDataFields();
//        //vadfs.getDataField(FieldKey.CORRESPONDING_OUTPATIENT_DRUG).addSelection(
//        //    (ProductVo) managedItemService.retrieve(s9991, EntityType.PRODUCT));
//        vadfs.getDataField(FieldKey.CORRESPONDING_OUTPATIENT_DRUG).setDefaultValue(s9991);
////        vadfs.getDataField(FieldKey.FORMULARY_ALTERNATIVE).addSelection(
////            (ProductVo) managedItemService.retrieve("9992", EntityType.PRODUCT));
//        vadfs.getDataField(FieldKey.FORMULARY_ALTERNATIVE).setDefaultValue("9992");
//    }

    /**
     * retrieve product by EPLId
     * 
     * @throws Exception Exception
     */
    public void testRetrieve() throws Exception {

        String id = s9991;

        ProductVo retrievedProduct = (ProductVo) managedItemService.retrieve(id, EntityType.PRODUCT);

        // check if any product is returned
        assertNotNull("No product returned for id " + id, retrievedProduct);

        // check if the product returned has the same id
        assertEquals("Retrieved product does not have same id", id, retrievedProduct.getId());

    }

    /**
     * test retrieving empty product template
     * 
     * @throws Exception Exception
     */
    public void testRetrieveEmptyProduct() throws Exception {

        ProductVo productVo = (ProductVo) managedItemService.retrieveBlankTemplate(EntityType.PRODUCT);

        assertNotNull("Retrieving Blank Product template failed!", productVo);

    }

    /**
     * retrieve an existing product template by product id
     * 
     * @throws Exception Exception
     */
    public void testRetrieveExistingTemplate() throws Exception {

        ProductVo productVo = (ProductVo) managedItemService.retrieveTemplate(s9991, EntityType.PRODUCT);
        assertNull("Id should be null", productVo.getId());
        assertEquals("Item Status should be Active", productVo.getItemStatus(), ItemStatus.ACTIVE);

        for (ActiveIngredientVo activeIngredient : productVo.getActiveIngredients()) {
            assertEquals("Strength should be an empty string", activeIngredient.getStrength(), "");
        }

        assertEquals("Va Product Name should be an empty string", productVo.getVaProductName(), "");

        // assertEquals("GCN Sequence Number should be 0", productVo.getGcnSequenceNumber(), 0);

    }


//    /**
//     * test commitModifications
//     * 
//     * @throws Exception
//     */
//    public void testCommitModifications() throws Exception {
//
//        ProductVo product = (ProductVo) managedItemService.retrieve(s9991, EntityType.PRODUCT);
//
//        // set generic name
//        GenericNameVo genericName = new GenericNameVo();
//        genericName.setId("9995");
//        genericName.setVuid("4017420");
//        genericName.setValue("THYROID");
//        genericName.setItemStatus(ItemStatus.ACTIVE);
//        genericName.setRequestItemStatus(RequestItemStatus.APPROVED);
//        product.setGenericName(genericName);
//
//        // get the vadf for the product
//        DataFields<DataField> vaDfs = product.getDataFields();
//
//        Collection<ModDifferenceVo> colModDiff = new ArrayList<ModDifferenceVo>();
//
//        // editable va data fields
//        DataField<Boolean> oldDoNotMail = vaDfs.getDataField(FieldKey.DO_NOT_MAIL);
//
//        if (oldDoNotMail == null) {
//            oldDoNotMail = DataField.newInstance(FieldKey.DO_NOT_MAIL);
//            vaDfs.setDataField(oldDoNotMail);
//        }
//
//        DataField<Boolean> newDoNotMail = DataField.newInstance(FieldKey.DO_NOT_MAIL);
//        newDoNotMail.setDataFieldId(oldDoNotMail.getDataFieldId());
//        newDoNotMail.setDefaultValue(false);
//        newDoNotMail.selectValue(false);
//
//        Difference diff1 = new Difference(FieldKey.DO_NOT_MAIL, oldDoNotMail, newDoNotMail);
//        ModDifferenceVo modDiff1 = new ModDifferenceVo();
//        modDiff1.setDifference(diff1);
//        modDiff1.setModificationReason("test1");
//
//        // add to the collection
//        colModDiff.add(modDiff1);
//        ListDataField<String> oldAppPkgUse = vaDfs.getDataField(FieldKey.APPLICATION_PACKAGE_USE);
//
//        if (oldAppPkgUse == null) {
//            oldAppPkgUse = DataField.newInstance(FieldKey.APPLICATION_PACKAGE_USE);
//            vaDfs.setDataField(oldAppPkgUse);
//        }
//
//        ListDataField<String> newAppPkgUse = DataField.newInstance(FieldKey.APPLICATION_PACKAGE_USE);
//        newAppPkgUse.setDataFieldId(oldAppPkgUse.getDataFieldId());
//        newAppPkgUse.addSelection(APP_PACKAGE_USE_DEFAULT);
//        Difference diff2 = new Difference(FieldKey.APPLICATION_PACKAGE_USE, oldAppPkgUse, newAppPkgUse);
//        ModDifferenceVo modDiff2 = new ModDifferenceVo();
//        modDiff2.setDifference(diff2);
//        modDiff2.setModificationReason("test2");
//
//        // add to the collection
//        colModDiff.add(modDiff2);
//
//        DataField<String> oldDayNdOrDoseLimit = vaDfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);
//
//        if (oldDayNdOrDoseLimit == null) {
//            oldDayNdOrDoseLimit = DataField.newInstance(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);
//            vaDfs.setDataField(oldDayNdOrDoseLimit);
//        }
//
//        DataField<String> newDayNdOrDoseLimit = DataField.newInstance(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);
//        newDayNdOrDoseLimit.setDataFieldId(oldDayNdOrDoseLimit.getDataFieldId());
//        newDayNdOrDoseLimit.selectValue("50D");
//
//        Difference diff3 = new Difference(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT, oldDayNdOrDoseLimit, newDayNdOrDoseLimit);
//
//        ModDifferenceVo modDiff3 = new ModDifferenceVo();
//        modDiff3.setDifference(diff3);
//        modDiff3.setModificationReason("test3");
//        modDiff3.setAcceptChange(false);
//
//        // add to the collection
//        colModDiff.add(modDiff3);
//
//        // Before calling commit method, the collection should have 4 objects
//        assertEquals("Incorrect number of objects in the collection", 3, colModDiff.size());
//
//        ProductVo returnedProduct = (ProductVo) managedItemService.commitModifications(colModDiff, product, PLM1).getItem();
//
//        // get the data field values for returnedNdc
//        DataFields<DataField> retProductVaDfs = returnedProduct.getDataFields();
//        DataField<Boolean> retProdDoNotMail = retProductVaDfs.getDataField(FieldKey.DO_NOT_MAIL);
//        ListDataField<String> retProdAppPkgUse = retProductVaDfs.getDataField(FieldKey.APPLICATION_PACKAGE_USE);
//        DataField<String> retDayNdOrDoseNlLimit = retProductVaDfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);
//
//        // check the value of editable va data fields modified if change is accepted, while the values of non-editable
//        // va data fields are unchanged
//
//        // editable va data fields
//        assertEquals("DoNotMail Edit Failed!", diff1.getNewValue(), retProdDoNotMail);
//
//        assertEquals("Application Pkg Use Edit Failed!", diff2.getNewValue(), retProdAppPkgUse);
//
//        // non-editable va data fields
//        assertEquals("DayNdOrDoseNlLimit should not be modified!", diff3.getOldValue(), retDayNdOrDoseNlLimit);
//
//        // assertEquals("Patient Specific Label should not be modified!", diff4.getOldValue(), retPatientSpecificLabel);
//    }

//    /**
//     * test the product inactivation
//     * 
//     * @throws Exception
//     * 
//     * Re-enable this test as the item status requires two reviews...therefore need to move this to the request service test!
//     */
//    public void revisitTestInactivateProduct() throws Exception {
//        final String prodId = s9991;
//
//        // Ensure product is active.
//        ProductVo prepProduct = (ProductVo) managedItemService.retrieve(prodId, EntityType.PRODUCT);
//
//        if (!ItemStatus.ACTIVE.equals(prepProduct.getItemStatus())) {
//            prepProduct.setItemStatus(ItemStatus.ACTIVE);
//        }
//
//        ProductVo oldProduct = (ProductVo) managedItemService.retrieve(prodId, EntityType.PRODUCT);
//        ProductVo newProduct = (ProductVo) managedItemService.retrieve(prodId, EntityType.PRODUCT);
//        newProduct.inactivate();
//
//        Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>();
//        Collection<Difference> differences = oldProduct.diff(newProduct);
//
//        for (Difference diff : differences) {
//            ModDifferenceVo modDiff = new ModDifferenceVo();
//            modDiff.setDifference(diff);
//            modDiff.setModificationReason("test");
//            modDiff.setRequestToMakeEditable(false);
//            modDiff.setAcceptChange(true);
//
//            modDifferences.add(modDiff);
//        }
//
//        managedItemService.commitModifications(modDifferences, newProduct, PLM1);
//
//        // retrieve the product and make sure it is inactive
//        ProductVo retrievedProduct = (ProductVo) managedItemService.retrieve(prodId, EntityType.PRODUCT);
//
//        assertEquals("Inactivation of the Product Item Failed", ItemStatus.INACTIVE, retrievedProduct.getItemStatus());
//    }


//    /**
//     * National creates a new Product with an existing VA Print Name.
//     * <p>
//     * In this case, a warning message is generated.
//     * 
//     * @throws Exception if error
//     */
//    public void testAddProductWithExistingVAPrintName() throws Exception {
//        ProductVo existingProduct = (ProductVo) managedItemService.retrieve("9992", EntityType.PRODUCT);
//
//        ProductVo product = (ProductVo) managedItemService.retrieveTemplate(s9991, EntityType.PRODUCT);
//        makeUnique(product);
//
//        // set the VA Print name to the existing product's va print name
//        product.setVaPrintName(existingProduct.getVaPrintName());
//
//        // insert the product
//        ProcessedItemVo processedItem = managedItemService.create(product, PLM1);
//        Errors warnings = processedItem.getWarnings();
//
//        // check that warning message is generated
//        assertFalse("national add product generates warning", warnings.getErrors().isEmpty());
//        assertTrue("Warning should be generated for using an existing VA Print Name", warnings
//            .hasError(ErrorKey.CMOP_ID_EXISTS));
//    }

    /**
     * PNM1 modifies the va print name of an existing product to an existing va print name. Warning is generated
     * 
     * @throws Exception Exception
     */
    public void testModifyVAPrintNameToExisting() throws Exception {
        ProductVo existingProduct = (ProductVo) managedItemService.retrieve("9992", EntityType.PRODUCT);

        ProductVo oldProduct = (ProductVo) managedItemService.retrieve(s9991, EntityType.PRODUCT);
        ProductVo newProduct = oldProduct.copy();

        newProduct.setVaPrintName(existingProduct.getVaPrintName());

        ProcessedItemVo processedItem = managedItemService.commitModifications(oldProduct.compareDifferences(newProduct),
            oldProduct, PNM1);

        assertNotNull("Warnings must be generated for existing VA Print Name", processedItem.getWarnings());
        assertTrue("Warnings for existing VA Print Name can not be empty", processedItem.hasWarnings());
        assertTrue("Warning should be generated for using an existing VA Print Name", processedItem.getWarnings().hasError(
            ErrorKey.CMOP_ID_EXISTS));
    }

    /**
     * Modifying a Product without changing the VA Print Name should not generate a warning message.
     * 
     * @throws Exception if error
     */
    public void testVaPrintNameMessageWithoutModifyingVaPrintName() throws Exception {
        ProductVo oldProduct = (ProductVo) managedItemService.retrieve("9993", EntityType.PRODUCT);
        ProductVo newProduct = oldProduct.copy();

        newProduct.getVaDataFields().getDataField(FieldKey.MESSAGE).
            selectStringValue(RandomGenerator.nextAlphabetic(PPSConstants.I5));

        ProcessedItemVo processedItem = managedItemService.commitModifications(oldProduct.compareDifferences(newProduct),
            oldProduct, PNM1);

        assertNotNull("Warnings instance must not be null", processedItem.getWarnings());
        assertFalse("Warning should not be generated for using an existing VA Print Name", processedItem.getWarnings()
            .hasError(ErrorKey.CMOP_ID_EXISTS));
    }

    /**
     * When all active products on an OI are inactivated, the oi should also be inactivated.
     * 
     * @throws Exception if error
     */
    public void testInactivateAllProducts() throws Exception {
        UserGenerator userGenerator = new UserGenerator();
        UserVo pnm1 = userGenerator.getNationalManagerOne();
        UserVo pnm2 = userGenerator.getNationalManagerTwo();

        // Create and approve an NOI
        OrderableItemVo national = (OrderableItemVo) managedItemService.retrieve("99950000", EntityType.ORDERABLE_ITEM);
        national.setId(null);
        national.setProducts(Collections.EMPTY_LIST);
        national.setNational();
        national.setVistaOiName(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        national.setNationalFormularyIndicator(false);
        national = (OrderableItemVo) managedItemService.create(national, pnm1).getItem();
        LOG.debug(" NOI '" + national.getVistaOiName() + "'  created with ID '" + national.getId() + "'");

        RequestVo request = requestService.retrieve(national.getId(), EntityType.ORDERABLE_ITEM).iterator().next();

        ProcessedRequestVo processedRequest = managedItemService.approveRequest(national, request, Collections.EMPTY_LIST,
            pnm2);
        national = (OrderableItemVo) processedRequest.getItem();
        request = processedRequest.getRequest();

        national = (OrderableItemVo) managedItemService.commitRequest(national, request, Collections.EMPTY_LIST, pnm2, false)
            .getItem();

        LOG.debug("NOI '" + national.getVistaOiName() + "' approved with ID '" + national.getId() + "'");

        // Create and approve an Product on the NOI
        ProductVo product = (ProductVo) managedItemService.retrieve(s9991, EntityType.PRODUCT);
        product.setId(null);
        product.setPreviousOrderableItem(null);
        product.setOrderableItem(national);
        product.setAtcMnemonic(null);
        product.setVaProductName(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        product = (ProductVo) managedItemService.create(product, pnm1).getItem();
        LOG.debug("Product  '" + product.getVaProductName() + "' created with ID '" + product.getId() + "'");

        request = requestService.retrieve(product.getId(), EntityType.PRODUCT).iterator().next();
        processedRequest = managedItemService.approveRequest(product, request, Collections.EMPTY_LIST, pnm2);
        product = (ProductVo) processedRequest.getItem();
        request = processedRequest.getRequest();
        product = (ProductVo) managedItemService.commitRequest(product, request, Collections.EMPTY_LIST, pnm2, false).getItem();

        // Inactivate all of the Products
        List<ManagedItemVo> products = managedItemService.retrieveChildren(national.getId(), EntityType.ORDERABLE_ITEM);

        for (ManagedItemVo item : products) {
            ProductVo inactive = item.copy();
            inactive.inactivate();
            managedItemService.commitModifications(item.compareDifferences(inactive),
                inactive, pnm1).getItem();
            Collection<RequestVo> requests = requestService.retrieve(inactive.getId(), EntityType.PRODUCT);

            for (RequestVo requestVo : requests) {
                if (!RequestState.COMPLETED.equals(requestVo.getRequestState())) {
                    request = requestVo;
                    break;
                }
            }

            processedRequest = managedItemService.commitRequest(inactive, request, Collections.EMPTY_LIST, pnm2, false);
            inactive = (ProductVo) processedRequest.getItem();
            request = processedRequest.getRequest();

            LOG.debug("Product '" + inactive.getVaProductName() + "' inactivated with ID '" + inactive.getId()
                + "'");
        }

        // The NOI should now be inactive
        national = (OrderableItemVo) managedItemService.retrieve(national.getId(), EntityType.ORDERABLE_ITEM);

        assertEquals("NOI should be inactive", ItemStatus.INACTIVE, national.getItemStatus());
    }

    /**
     * This test method modifies a product item by adding a new synonym to it. For the new synonym only the required field
     * "synonym name" is populated
     * 
     * @throws Exception Exception
     */
    public void testModifyProductAddSynonym() throws Exception {

        String newSyn = "New Synonym";

        // retrieve a product
        ProductVo oldProduct = (ProductVo) managedItemService.retrieve("9997", EntityType.PRODUCT);
        ProductVo newProduct = oldProduct.copy();

        // get the Synonyms for the testModifyProductAddSynonym method.
        Collection<SynonymVo> productSynonyms = newProduct.getSynonyms();
        List<SynonymVo> newSynonyms = new ArrayList<SynonymVo>();

        if ((productSynonyms != null) && (!productSynonyms.isEmpty())) {
            newSynonyms.addAll(productSynonyms);
        }

        // create a new Synonym and add the required field only
        SynonymVo newSynonym = new SynonymVo();
        newSynonym.setSynonymName(newSyn);

        // add this synonym to the synonyms list
        newSynonyms.add(newSynonym);
        newProduct.setSynonyms(newSynonyms);

        // commit modification
        ProcessedItemVo processedItem = managedItemService.commitModifications(oldProduct.compareDifferences(newProduct),
            oldProduct, PNM1);
        ProductVo updatedProduct = (ProductVo) processedItem.getItem();
        Collection<SynonymVo> updatedProductSynonyms = updatedProduct.getSynonyms();
        boolean synonymModified = false;

        if ((updatedProductSynonyms != null) && (!updatedProductSynonyms.isEmpty())) {
            for (SynonymVo synonym : updatedProductSynonyms) {
                if (synonym.getSynonymName().equals(newSyn)) {
                    synonymModified = true;
                }
            }
        }

        assertTrue("Synonym modification fails! ", synonymModified);
    }

    /**
     * modify a product by adding a synonym. In this case populate all the fields of synonym except the required field
     * "synonym name". "required field empty" validation error is thrown.
     * 
     * @throws Exception Exception
     */
    public void testAddSynonymWithoutRequiredField() throws Exception {

        // retrieve a product
        ProductVo oldProduct = (ProductVo) managedItemService.retrieve(s9991, EntityType.PRODUCT);
        ProductVo newProduct = oldProduct.copy();

        // get the Synonyms
        Collection<SynonymVo> productSynonyms = newProduct.getSynonyms();
        List<SynonymVo> newSynonyms = new ArrayList<SynonymVo>();

        if ((productSynonyms != null) && (!productSynonyms.isEmpty())) {
            newSynonyms.addAll(productSynonyms);
        }

        // create a new Synonym and add the required field only
        SynonymVo newSynonym = new SynonymVo();

        // intended use
        IntendedUseVo intendedUse = new IntendedUseVo();
        intendedUse.setIntendedUse("0-TRADE NAME");
        newSynonym.setSynonymIntendedUse(intendedUse);

        // ndc code
        newSynonym.setNdcCode("11111-1111-11");

        // price per order unit
        newSynonym.setSynonymPricePerOrderUnit(PPSConstants.D1POINT98);

        // price per dispense unit
        newSynonym.setSynonymPricePerDispenseUnit(PPSConstants.D1POINT98);

        // synonym vendor
        newSynonym.setSynonymVendor("New Vendor");

        // synonym vsn
        newSynonym.setSynonymVsn("New VSN");

        // synonym order unit
        OrderUnitVo ordUnit = new OrderUnitVo();
        ordUnit.setId(s9991);
        ordUnit.setAbbrev("AM");
        ordUnit.setExpansion("AMPUL");
        newSynonym.setSynonymOrderUnit(ordUnit);

        // add this synonym to the synonyms list
        newSynonyms.add(newSynonym);
        newProduct.setSynonyms(newSynonyms);

        try {

            // submit modification
            managedItemService.submitModifications(oldProduct, newProduct, PNM1);
        } catch (ValueObjectValidationException ve) {
            boolean blnValidationerr = false;

            if ((ve.getErrors() != null) && (ve.getErrors().getErrors() != null)) {
                for (ValidationError verr : ve.getErrors().getErrors()) {
                    if (verr.getErrorKey().equals(ErrorKey.SYNONYM_REQ_FIELD_EMPTY)) {
                        blnValidationerr = true;
                        break;
                    }// end if
                }// end for

            }// end if

            assertTrue("Valdiation Error--'Synonym Required field empty'-- should have been thrown", blnValidationerr);
        }// end catch
    }
}
