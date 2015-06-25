/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import java.util.Collection;
import java.util.Locale;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.NdcValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;

import junit.framework.TestCase;


/**
 * NDCValidatorTest.
 */
public class NdcValidatorTest extends TestCase {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(NdcValidatorTest.class);  
    
    private NdcValidator validator;
    private Errors errors;
    private NdcVo ndc;
    private UserVo user;
    private Environment env;
    

    /**
     * setup method
     * 
     * @throws Exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        errors = new Errors();
        
        NdcGenerator gen = new NdcGenerator();
        ndc = gen.getFirst();
        ndc.setItemStatus(ItemStatus.ACTIVE);
        
        validator = new NdcValidator();
        
        user = new UserGenerator().getNationalManagerOne();

        env = Environment.NATIONAL;
       
    }

    /**
     * testNullNdc
     */
    public void testNullNdc() {
        NdcVo testNdc = null;
        
        errors = new Errors();
        validator.validate(testNdc, user, env, errors);
        LOG.debug("NdcVo: <null>");
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertTrue("Expected error did not occur for null NdcVo.", errors.hasErrors());
        
    }
    
    /**
     * this is the testMandatoryOtcRxIndicator
     */
    public void testMandatoryOtcRxIndicator() {
     
        OtcRxVo otcRx = new OtcRxVo();
        otcRx.setId(PPSConstants.OVER_THE_COUNTER.toLowerCase(Locale.US));
        otcRx.setValue(PPSConstants.OVER_THE_COUNTER);
        ndc.setOtcRxIndicator(otcRx);
        validator.validate(ndc, user, env, errors);
        LOG.debug("OTCRxIndicator: is " + ndc.getOtcRxIndicator().getValue());       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertFalse("Error occurred for valid OTCRxIndicator.", errors.hasErrors());
        
        ndc.getOtcRxIndicator().setValue(null);
        errors = new Errors();
        validator.validate(ndc, user, env, errors);
        LOG.debug("OTCRxIndicator is empty.");       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertTrue("Expected error did not occur for empty OtcRx.", errors.hasErrors());
 
        errors = new Errors();
        ndc.setOtcRxIndicator(null);
        validator.validate(ndc, user, env, errors);
        LOG.debug("OtcRxIndicator is NULL");       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertTrue("Expected error did not occur for NULL Value.", errors.hasErrors());
 
       
    }
    
    /**
     * testNullProduct
     */
    public void testNullProduct() {
        ndc.setProduct(null);
        
        errors = new Errors();
        validator.validate(ndc, user, env, errors);
        LOG.debug("Product: <null>");
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertTrue("Expected error did not occur for null Product association.", errors.hasErrors());
        
    }
    
     /**
     * Validates the validation cases for NDC Item Status
     */
    public void testItemStatusValidation() {

        errors = new Errors();
        ndc.setItemStatus(null);
        validator.validate(ndc, user, env, errors);
        LOG.debug("Item Status: <null>");       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertTrue("Expected error did not occur for null Item Status.", errors.hasErrors());
    
        errors = new Errors();
        ndc.setItemStatus(ItemStatus.INACTIVE);
        validator.validate(ndc, user, env, errors);
        LOG.debug("Item Status: INACTIVE");       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertFalse("Error occurred for valid Item Status of ARCHIVED.", errors.hasErrors());

    }
    
    /**
     * Validates the validation cases for NDC Package Type
     */
    public void testNullPackageType() {

        errors = new Errors();
        ndc.setPackageType(null);
        validator.validate(ndc, user, env, errors);
        LOG.debug("Package Type: <null>");       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertTrue("Expected error did not occur for null Package Type.", errors.hasErrors());
    
    }
    
    /**
     * Validates the validation cases for NDC Manufacturer
     */
    public void testNullManufacturer() {

        errors = new Errors();
        ndc.setManufacturer(null);
        validator.validate(ndc, user, env, errors);
        LOG.debug("Manufacturer: <null>");       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertTrue("Expected error did not occur for null Manufacturer.", errors.hasErrors());
    
    }
    
    /**
     * Validates the validation cases for Trade Name
     */
    public void testTradeNameValidation() {

        String test = null;
        
        errors = new Errors();
        test = null;
        ndc.setTradeName(test);
        validator.validate(ndc, user, env, errors);
        LOG.debug("Trade Name1: <null>");       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertTrue("Expected error did not occur for null Trade Name.", errors.hasErrors());

        errors = new Errors();
        test = "";
        ndc.setTradeName(test);
        validator.validate(ndc, user, env, errors);
        LOG.debug("Trade Name2: <empty>");       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertTrue("Expected error did not occur for empty Trade Name.", errors.hasErrors());
 
        errors = new Errors();
        test = "This name is extremely verbose. It is way too long to pass the character limit imposed on the field.";
        ndc.setTradeName(test);
        validator.validate(ndc, user, env, errors);
        LOG.debug("Trade Name3: " + test);       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertTrue("Expected error did not occur for too long Trade Name.", errors.hasErrors());
 
        errors = new Errors();
        test = "12345678901234567890123456789012345678901";
        ndc.setTradeName(test);
        validator.validate(ndc, user, env, errors);
        LOG.debug("Trade Name4: " + test);       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertTrue("Expected error did not occur for this too long Trade Name.", errors.hasErrors());

        errors = new Errors();
        test = "1234567890123456789012345678901234567890";
        ndc.setTradeName(test);
        validator.validate(ndc, user, env, errors);
        LOG.debug("Trade Name5: " + test);       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertFalse("Error occurred for valid Trade Name of 40 characters.", errors.hasErrors());
 
        errors = new Errors();
        test = "A";
        ndc.setTradeName(test);
        validator.validate(ndc, user, env, errors);
        LOG.debug("Trade Name6: " + test);       
        LOG.debug(getErrors().getLocalizedErrorsAsString());       
        assertFalse("Error occurred for valid Trade Name of 1 character.", errors.hasErrors());
  
    }
    
    /**
     * Tests if one or more of the specified error keys is found in the specified Errors objects.
     * 
     * @param errorsIn The Errors list to search for matches to the specified errorKeys.
     * @param errorKeyIn The errorKey to look for in the errors object.
     * @return boolean True if the error key was found.
     */
    public boolean hasErrorKey(Errors errorsIn, ErrorKey errorKeyIn) {
        if (!errorsIn.hasErrors()) {
            return false;
        }
        
        for (ValidationError vErr : errorsIn.getErrors()) {
            if (vErr.getErrorKey().equals(errorKeyIn)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Tests if one or more of the specified error keys is found in the specified Errors objects.
     * 
     * @param errorsIn The Errors list to search for matches to the specified errorKeys.
     * @param errorKeys The collection of errorKeys to search for.
     * @return boolean True if one or more errorKeys are found in the errors object.
     */
    public boolean hasOneOrMoreErrorKeys(Errors errorsIn, Collection<ErrorKey> errorKeys) {
        if (!errorsIn.hasErrors()) {
            return false;
        }
        
        for (ValidationError vErr : errorsIn.getErrors()) {
            if (errorKeys.contains(vErr.getErrorKey())) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * getErrors
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }
    
}
