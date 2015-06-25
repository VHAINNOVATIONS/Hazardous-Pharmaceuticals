/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.utility.test.generator.ProductGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.ProductValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;

import junit.framework.TestCase;


/**
 * ProductValidatorTest
 */
public class ProductValidatorTest extends TestCase {

    private ProductValidator validator;
    private Errors errors;
    private ProductVo product;
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
    protected void setUp() throws Exception {
        errors = new Errors();
        
        ProductGenerator gen = new ProductGenerator();
        product = gen.getFirst();
        product.setItemStatus(ItemStatus.ACTIVE);
        
        validator = new ProductValidator();
        
        user = new UserGenerator().getNationalManagerOne();

        env = Environment.NATIONAL;
    }

    
    /**
     * Validates a atc-choice selections.
     */
    public void testProductNameChoice() {
        product.setVaProductName("");
        validator.validate(product, user, env, errors);
        assertFalse("Product Name Cannot be empty.", hasErrorKey(errors, ErrorKey.EMPTY_SEARCH));       
       
    }

    /**
     * Tests if one or more of the specified error keys is found in the specified Errors objects.
     * 
     * @param errs The Errors list to search for matches to the specified errorKeys.
     * @param ek The errorKey to look for in the errors object.
     * @return boolean True if the error key was found.
     */
    public boolean hasErrorKey(Errors errs, ErrorKey ek) {
        if (!errs.hasErrors()) {
            return false;
        }
        
        for (ValidationError vErr : errs.getErrors()) {
            if (vErr.getErrorKey().equals(ek)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Tests if one or more of the specified error keys is found in the specified Errors objects.
     * 
     * @param errs The Errors list to search for matches to the specified errorKeys.
     * @param eks The collection of errorKeys to search for.
     * @return boolean True if one or more errorKeys are found in the errors object.
     */
    public boolean hasOneOrMoreErrorKeys(Errors errs, Collection<ErrorKey> eks) {
        if (!errs.hasErrors()) {
            return false;
        }
        
        for (ValidationError vErr : errs.getErrors()) {
            if (eks.contains(vErr.getErrorKey())) {
                return true;
            }
        }
        
        return false;
    }
}
