/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test;


import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;

import junit.framework.TestCase;


/**
 * Test Fixture
 */
public class RandomGeneratorTest extends TestCase {

    private static final int CYCLE_END = 10000;
    
    /**
     * Test
     * 
     * This is per document tag found in the code.  The method 
     * originally returned RANDOM.nextInt(), while should return
     * both positive and negative values (based on Java API docs).
     * This test was used to help remedy this oversight.
     */
    public void testNextIntShouldNeverReturnValuesBelowZero() {

        for (int i = 0; i < CYCLE_END; i++) {
            assertTrue("should be greater >= zero!", RandomGenerator.nextInt() >= 0);
        }
    }
    
    /**
     * Test
     */
    public void testNextLongShouldNeverReturnValuesBelowZero() {
        long randValue;
        
        for (int i = 0; i < CYCLE_END; i++) {
            randValue = RandomGenerator.nextLong();
            assertTrue("should be greater >= zero", randValue >= 0);
        }
    }
    
    /**
     * Test
     */
    public void testNextLongShouldReturnValueBelowSpecifiedLimit() {
        long randValue;
        
        for (int i = 0; i < CYCLE_END; i++) {
            randValue = RandomGenerator.nextLong(CYCLE_END);
            assertTrue("should be less than limit", randValue < CYCLE_END);
        }
    }
}
