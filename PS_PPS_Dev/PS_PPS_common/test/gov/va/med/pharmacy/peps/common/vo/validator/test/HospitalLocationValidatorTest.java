/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.HospitalLocationSelectionVo;
import gov.va.med.pharmacy.peps.common.vo.HospitalLocationVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.HospitalLocationValidator;

import junit.framework.TestCase;


/**
 * HospitalLocationValidatorTest
 */
public class HospitalLocationValidatorTest extends TestCase {
    private HospitalLocationValidator validator = new HospitalLocationValidator();
    private Errors errors;
    private Collection<HospitalLocationVo> testCollection;
    private HospitalLocationVo locationVo;

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
        testCollection = new ArrayList<HospitalLocationVo>();
        locationVo = new HospitalLocationVo();
        HospitalLocationSelectionVo locationSelection = new HospitalLocationSelectionVo();
        locationSelection.setValue("TEST");
        locationVo.setHospitalLocationSelection(locationSelection);
    }

    /**
     * Tests the required fields
     */
    public void testRequiredFields() {

        // Test Hospital Location
        locationVo.setHospitalLocationSelection(null);
        testCollection.add(locationVo);

        validator.validate(testCollection, errors);

        assertTrue("Null Hospital Location does not return error", errors.hasErrors());
    }

    /**
     * make sure the the field length and range checks are passing
     * 
     * @throws Exception 
     */
    public void testFieldLengthRangeRestrictions() throws Exception {
        locationVo.setShifts("M-T-W-T-F-S-S-M");

        testCollection.add(locationVo);

        validator.validate(testCollection, errors);

        assertTrue("StandardShift of incorrect length doesn't return error", errors.hasErrors());
    }

    /**
     * Tests to make sure the format of Shifts is correct
     */
    public void testShiftsFormat() {

        locationVo.setShifts("M-");
        testCollection.add(locationVo);

        validator.validate(testCollection, errors);

        assertTrue("Shifts in incorrect format doesn't return error", errors.hasErrors());
    }

}
