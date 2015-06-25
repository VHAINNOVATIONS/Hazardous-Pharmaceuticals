/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import gov.va.med.pharmacy.peps.common.vo.PharmacySystemVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.PharmacySystemValidator;

import junit.framework.TestCase;


/**
 * PharmacySystemValidatorTest
 */
public class PharmacySystemValidatorTest extends TestCase {

    private PharmacySystemValidator myPharmacySystemValidator = new PharmacySystemValidator();
    
    /**
     * Test for null PharmacySystemVo
     */
    public void testPharmacySystemValidatorWithNullVo() {
        PharmacySystemVo target = null;
        Errors errors = new Errors();
        
        myPharmacySystemValidator.validate(target, errors);
        assertTrue("PharmacySystemVo can not be null", errors.hasErrors());
    }

    /**
     * Test for PharmacySystemVo with null SiteName (value)
     * Test for PharmacySystemVo with empty SiteName (value)
     */
    public void testPharmacySystemValidatorWithBadSiteName() {
        PharmacySystemVo target = new PharmacySystemVo();
        target.setId("9");
        Errors errors = new Errors();
        
        myPharmacySystemValidator.validate(target, errors);
        assertTrue("PharmacySystem SiteName can not be null", errors.hasErrors());
        
        PharmacySystemVo target2 = new PharmacySystemVo();
        target2.setValue("");
        Errors errors2 = new Errors();
        
        myPharmacySystemValidator.validate(target2, errors2);
        assertTrue("PharmacySystem SiteName can not be empty", errors2.hasErrors());

        PharmacySystemVo target3 = new PharmacySystemVo();
        target3.setValue("X");
        Errors errors3 = new Errors();
        
        final String siteName2to40 = "PharmacySystem SiteName must be 2-40 characters";
        myPharmacySystemValidator.validate(target3, errors3);
        assertTrue(siteName2to40, errors3.hasErrors());

        PharmacySystemVo target4 = new PharmacySystemVo();
        target4.setValue("1234567890123456789012345678901234567890X"); // 41 char
        Errors errors4 = new Errors();
        
        myPharmacySystemValidator.validate(target4, errors4);
        assertTrue(siteName2to40, errors4.hasErrors());
    }

    /**
     * Test for PharmacySystemVo with good SiteName (value)
     */
    public void testPharmacySystemValidatorWithGoodSiteName() {
        PharmacySystemVo target = new PharmacySystemVo();
        target.setPharmacySystem("this is my site name");
        target.setPsPmisPrinter("this is my printer");
        Errors errors = new Errors();
        
        myPharmacySystemValidator.validate(target, errors);
        assertFalse("PharmacySystem SiteName can not be null or empty and must be 2-40 char", errors.hasErrors());
    }

    /**
     * Test for PharmacySystemVo for string length in other fields (one only, not all!)
     */
    public void testPharmacySystemValidatorStringLengthCheck() {

        final String goodSiteName = "GOOD SITE NAME HERE";
        final String goodPrintName = "GOOD PRINTER NAME HERE";
        final String pharmSystemDays = "PharmacySystem Days is valid if null, empty or 1-15 char";

        PharmacySystemVo target = new PharmacySystemVo();
        target.setValue(goodSiteName);
        target.setPsPmisPrinter(goodPrintName);
        target.setPsDays(null);  // null - good as it is optional
        Errors errors = new Errors();
        
        myPharmacySystemValidator.validate(target, errors);
        assertFalse(pharmSystemDays, errors.hasErrors());

        PharmacySystemVo target1 = new PharmacySystemVo();
        target1.setValue(goodSiteName);
        target1.setPsPmisPrinter(goodPrintName);
        target1.setPsDays("");  // empty - good as it is optional
        Errors errors1 = new Errors();
        
        myPharmacySystemValidator.validate(target1, errors1);
        assertFalse(pharmSystemDays, errors1.hasErrors());

        PharmacySystemVo target2 = new PharmacySystemVo();
        target2.setValue(goodSiteName);
        target2.setPsPmisPrinter(goodPrintName);
        target2.setPsDays("D"); // 1 char good
        Errors errors2 = new Errors();
        
        myPharmacySystemValidator.validate(target2, errors2);
        assertFalse(pharmSystemDays, errors2.hasErrors());

        PharmacySystemVo target3 = new PharmacySystemVo();
        target3.setValue(goodSiteName);
        target3.setPsPmisPrinter(goodPrintName);
        target3.setPsDays("DAYS HAPPY"); // 10 char - good
        Errors errors3 = new Errors();
        
        myPharmacySystemValidator.validate(target3, errors3);
        assertFalse(pharmSystemDays, errors3.hasErrors());

        PharmacySystemVo target4 = new PharmacySystemVo();
        target4.setValue(goodSiteName);
        target4.setPsPmisPrinter(goodPrintName);
        target4.setPsDays("DAYS VERY HAPPY");  // 15 char - good
        Errors errors4 = new Errors();
        
        myPharmacySystemValidator.validate(target4, errors4);
        assertFalse(pharmSystemDays, errors4.hasErrors());

        PharmacySystemVo target5 = new PharmacySystemVo();
        target5.setValue(goodSiteName);
        target5.setPsPmisPrinter(goodPrintName);
        target5.setPsDays("DAYS ALL UNHAPPY"); // 16 char - bad
        Errors errors5 = new Errors();
        
        myPharmacySystemValidator.validate(target5, errors5);
        assertTrue(pharmSystemDays, errors5.hasErrors());
    }

}
