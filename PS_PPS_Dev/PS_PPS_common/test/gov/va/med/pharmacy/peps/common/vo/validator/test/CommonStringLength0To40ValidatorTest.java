/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.CommonStringLength0To40Validator;

import junit.framework.TestCase;


/**
 * CommonStringLength0To40ValidatorTest's unit test class
 *
 */
public class CommonStringLength0To40ValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(CommonStringLength0To40ValidatorTest.class);

    private CommonStringLength0To40Validator validator; // = new CommonStringLength0To40Validator();

//      private Errors errors;
//      private String test;

    private Errors errors;
    private NdcVo ndc;
    private UserVo user;
    private Environment env;

    /**
     * getErrors
     * 
     * @return errors property
     */
    public Errors getErrors() {

        return errors;
    }

    /**
     * Validates the validation cases for Common String
     */
    public void testNameValidation() {

        String test = null;

        errors = new Errors();
        test = null;
        DataField<String> previousUpcUpn = DataField.newInstance(FieldKey.PREVIOUS_UPC_UPN);
        previousUpcUpn.selectStringValue(test);
        ndc.getVaDataFields().setDataField(previousUpcUpn);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN), user, env, errors);
        LOG.info("Previous UPC/UPN: " + ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN).getValue());
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid null String.", errors.hasErrors());

        errors = new Errors();
        test = "";
        previousUpcUpn = DataField.newInstance(FieldKey.PREVIOUS_UPC_UPN);
        previousUpcUpn.selectStringValue(test);
        ndc.getVaDataFields().setDataField(previousUpcUpn);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN), user, env, errors);
        LOG.info("Previous UPC/UPN: "  + ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN).getValue());
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid empty String.", errors.hasErrors());

        errors = new Errors();
        test = "This name is extremely verbose. It is way too long to pass the character limit imposed on the field.";
        previousUpcUpn = DataField.newInstance(FieldKey.PREVIOUS_UPC_UPN);
        previousUpcUpn.selectStringValue(test);
        ndc.getVaDataFields().setDataField(previousUpcUpn);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN), user, env, errors);
        LOG.info("Previous UPC/UPN: " + ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN).getValue());
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long String.", errors.hasErrors());

        errors = new Errors();
        test = "12345678901234567890123456789012345678901";
        previousUpcUpn = DataField.newInstance(FieldKey.PREVIOUS_UPC_UPN);
        previousUpcUpn.selectStringValue(test);
        ndc.getVaDataFields().setDataField(previousUpcUpn);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN), user, env, errors);
        LOG.info("Previous UPC/UPN: " + ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN).getValue());
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long String.", errors.hasErrors());

        errors = new Errors();
        test = "1234567890123456789012345678901234567890";
        previousUpcUpn = DataField.newInstance(FieldKey.PREVIOUS_UPC_UPN);
        previousUpcUpn.selectStringValue(test);
        ndc.getVaDataFields().setDataField(previousUpcUpn);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN), user, env, errors);
        LOG.info("Previous UPC/UPN: " + ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN).getValue());
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid String of 40 characters.", errors.hasErrors());

        errors = new Errors();
        test = "A";
        previousUpcUpn = DataField.newInstance(FieldKey.PREVIOUS_UPC_UPN);
        previousUpcUpn.selectStringValue(test);
        previousUpcUpn.selectStringValue("B");
        ndc.getVaDataFields().setDataField(previousUpcUpn);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN), user, env, errors);
        LOG.info("Previous UPC/UPN: " + ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN).getValue());
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for multiple valid Strings of 1 character.", errors.hasErrors());

    }

    /**
     * setup method
     * 
     * @throws Exception 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {

        errors = new Errors();

        NdcGenerator gen = new NdcGenerator();
        ndc = gen.getFirst();
        ndc.setItemStatus(ItemStatus.ACTIVE);

        validator = new CommonStringLength0To40Validator();

        user = new UserGenerator().getNationalManagerOne();

        env = Environment.NATIONAL;

        LOG.info("In setUp method");

    }

}
