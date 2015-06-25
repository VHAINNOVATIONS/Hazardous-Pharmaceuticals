/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.ProductNumberValidator;

import junit.framework.TestCase;


/**
 * ProductNumberValidatorTest
 *
 */
public class ProductNumberValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(ProductNumberValidatorTest.class);
    private static final String PROD_NUM = "Product Number: ";
    private ProductNumberValidator validator; // = new ProductNumberValidator();

//    private Errors errors;
//    private String test;

//    NdcValidator validator;
    private Errors errors;
    private NdcVo ndc;

//    private UserVo user;
//    private Environment env;

    /**
     * setup method for the ProductNumberValidatorTests
     * 
     * @throws Exception 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        errors = new Errors();

        // generate the NDC
        NdcGenerator gen = new NdcGenerator();
        ndc = gen.getFirst();
        ndc.setItemStatus(ItemStatus.ACTIVE);

        validator = new ProductNumberValidator();

//        user = new UserGenerator().getNationalManagerOne();
//
//        env = Environment.NATIONAL;

        LOG.debug("In setUp method");

    }

    /**
     * Validates the validation cases for Product Number
     */
    public void testNameValidation() {

        String test = null;

        DataField<String> productNumber = DataField.newInstance(FieldKey.PRODUCT_NUMBER);

        errors = new Errors();
        test = null;
        productNumber.selectStringValue(test);
        ndc.getVaDataFields().setDataField(productNumber);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER), errors);
        LOG.debug(PROD_NUM + ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER).getValue());
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Product Number.", errors.hasErrors());

        errors = new Errors();
        test = "";
        productNumber.selectStringValue(test);
        ndc.getVaDataFields().setDataField(productNumber);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER), errors);
        LOG.debug(PROD_NUM + ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER).getValue());
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid empty Product Number.", errors.hasErrors());

        errors = new Errors();
        test = "This name is extremely verbose. It is way too long to pass the character limit imposed on the field.";
        productNumber.selectStringValue(test);
        ndc.getVaDataFields().setDataField(productNumber);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER), errors);
        LOG.debug(PROD_NUM + ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER).getValue());
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Product Number!", errors.hasErrors());

        errors = new Errors();
        test = "12345678901234567890123456789012345678901";
        productNumber.selectStringValue(test);
        ndc.getVaDataFields().setDataField(productNumber);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER), errors);
        LOG.debug(PROD_NUM + ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER).getValue());
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Product Number.", errors.hasErrors());

        errors = new Errors();
        test = "1234567890123456789012345678901234567890";
        productNumber.selectStringValue(test);
        ndc.getVaDataFields().setDataField(productNumber);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER), errors);
        LOG.debug(PROD_NUM + ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER).getValue());
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Product Number of 40 characters.", errors.hasErrors());

        errors = new Errors();
        test = "A";
        productNumber.selectStringValue(test);
        ndc.getVaDataFields().setDataField(productNumber);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER), errors);
        LOG.debug(PROD_NUM + ndc.getVaDataFields().getDataField(FieldKey.PRODUCT_NUMBER).getValue());
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Product Number of 1 character.", errors.hasErrors());

    }

    /**
     * getErrors
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }

    /**
     * Generates a default drug class with the bare minimum filled in
     * 
     * @return ProductNumber
     */

    /*
    private ProductNumberVo generateValidProductNumber() {
     ProductNumberVo ret = new ProductNumberVo();
     
     ret.setValue("TEST");
     
     return ret;
    }*/
}
