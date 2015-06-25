/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.WardMultipleVo;
import gov.va.med.pharmacy.peps.common.vo.WardSelectionVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.WardValidator;

import junit.framework.TestCase;


/**
 * Test case for the WardValidator
 */
public class WardValidatorTest extends TestCase {

    private WardValidator validator = new WardValidator();
    private Errors errors;
    private Collection<WardMultipleVo> testCollection;
    private WardMultipleVo wardVo;

    /**
     * setUp
     * 
     * @throws Exception 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {

        errors = new Errors();
        testCollection = new ArrayList<WardMultipleVo>();
        wardVo = new WardMultipleVo();
        WardSelectionVo wardSelection = new WardSelectionVo();
        wardSelection.setValue("TETS");
        wardVo.setWardSelection(wardSelection);
    }

    /**
     * test empty ward has errors
     */
    public void testEmptyAdministrationSchedule() {

        testCollection.clear();
        wardVo = new WardMultipleVo();
        testCollection.add(wardVo);

        validator.validate(testCollection, errors);

        assertTrue("Empty Ward does not return error", errors.hasErrors());
    }

    /**
     * Tests the required fields
     * @throws Exception 
     */
    public void testRequiredFields() throws Exception {

        // Test Ward      
        wardVo.setWardAdminTimes("01-02");
        wardVo.setWardSelection(null);
        testCollection.add(wardVo);

        validator.validate(testCollection, errors);

        assertTrue("Null ward does not return error", errors.hasErrors());

        //Test WardAdminTimes
        setUp();
        wardVo.setWardAdminTimes(null);
        testCollection.add(wardVo);

        validator.validate(testCollection, errors);

        assertTrue("Null WardAdminTimes does not return error", errors.hasErrors());
    }

    /**
     * make sure the the field length and range checks are passing
     * @throws Exception 
     */
    public void testFieldLengthRangeRestrictions() throws Exception {

        //Test WardAdminTimes Length    
        wardVo.setWardAdminTimes("1");
        testCollection.add(wardVo);

        validator.validate(testCollection, errors);

        assertTrue("WardAdminTimes of incorrect length doesn't return error", errors.hasErrors());

        setUp();
        wardVo.setWardAdminTimes("0000-0100-0200-0300-0400-0500-0600-0700-0800-0900-1000-1100-"
            + "1200-1300-1400-1500-1600-1700-1800-1900-2000-2100-2200-2300-2400");
        testCollection.add(wardVo);

        validator.validate(testCollection, errors);

        assertTrue("WardAdminTimes of incorrect length doesn't return error", errors.hasErrors());
    }

    /**
     * Tests to make sure the format of WardAdminTimes is correct
     * @throws Exception 
     */
    public void testWardAdminTimesFormat() throws Exception {

        wardVo.setWardAdminTimes("26");
        testCollection.add(wardVo);

        validator.validate(testCollection, errors);

        assertTrue("WardAdminTimes not in miltary time doesn't return error", errors.hasErrors());

        setUp();
        wardVo.setWardAdminTimes("23-24-");
        testCollection.add(wardVo);

        validator.validate(testCollection, errors);

        assertTrue("WardAdminTimes in incorrect format doesn't return error", errors.hasErrors());

        setUp();
        wardVo.setWardAdminTimes("01-02-15-06");
        testCollection.add(wardVo);

        validator.validate(testCollection, errors);

        assertTrue("WardAdminTimes not in ascending order doesn't return error", errors.hasErrors());

        setUp();
        wardVo.setWardAdminTimes("2369");
        testCollection.add(wardVo);

        validator.validate(testCollection, errors);

        assertTrue("WardAdminTimes not in miltary time doesn't return error", errors.hasErrors());

        setUp();
        wardVo.setWardAdminTimes("0100-1500-");
        testCollection.add(wardVo);

        validator.validate(testCollection, errors);

        assertTrue("WardAdminTimes in incorrect format doesn't return error", errors.hasErrors());

        setUp();
        wardVo.setWardAdminTimes("0100-0200-1500-0600");
        testCollection.add(wardVo);

        validator.validate(testCollection, errors);

        assertTrue("WardAdminTimes not in ascending order doesn't return error", errors.hasErrors());
    }

}
