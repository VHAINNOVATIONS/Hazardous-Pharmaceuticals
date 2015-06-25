/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.test;


import gov.va.med.pharmacy.peps.common.utility.test.generator.ProductGenerator;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;

import junit.framework.TestCase;


/**
 * Test common methods in {@link ValueObject} class.
 */
public class ValueObjectTest extends TestCase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ValueObjectTest.class);
    
    /**
     * setUp
     * @throws Exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.debug("---------------------" + getName() + "--------------------");
    }

    /**
     * Test that clone creates a new object that is != but is .equals(), print out timing info for reference.
     * 
     * @throws Exception If error
     */
    public void testClone() throws Exception {
        ProductGenerator productGenerator = new ProductGenerator();
        ProductVo product = productGenerator.getRandom();

        long start = System.currentTimeMillis();
        ProductVo clone = (ProductVo) product.clone();
        LOG.debug("Clone took " + (System.currentTimeMillis() - start) + " milliseconds");

        assertTrue("Cloned ValueObject must be != to original", product != clone);
        assertEquals("Cloned ValueObject must be .equals() to original", product, clone);
        
    }
}
