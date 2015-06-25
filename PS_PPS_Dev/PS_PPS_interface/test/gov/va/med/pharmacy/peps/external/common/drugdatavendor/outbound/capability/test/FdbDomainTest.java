/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.test;


import java.util.List;

import org.apache.log4j.Logger;


import gov.va.med.pharmacy.peps.common.vo.ColorVo;
import gov.va.med.pharmacy.peps.common.vo.ShapeVo;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.test.InterfaceTestCase;




/**
 * FdbDomainTest
 *
 */
public class FdbDomainTest extends InterfaceTestCase {
    private static final Logger LOG = Logger.getLogger(FdbDomainTest.class);
    private DrugReferenceCapability drugReferenceCapability;
  
    
    /**
     * Start the Spring ApplicationContext to get the FDBDataManager
     * 
     */
    public void setUp() {
        drugReferenceCapability = this.getSpringBean(DrugReferenceCapability.class);
    }

    /**
     * testGetColors
     */
    public void testGetColors() {
        List<ColorVo> list = drugReferenceCapability.getColors();
        assertNotNull("Color List should not be null", list);
        assertTrue("Color List should contain values!", list.size() > 0);

        try {
            for (ColorVo vo : list) {
                LOG.info("Color is " + vo.getValue());
            }
        } catch (Exception e) {
            fail("Error throws in getColors: " + e.getMessage());
        }
    }
    
    /**
     * testGetColors
     */
    public void testGetShape() {
        List<ShapeVo> list = drugReferenceCapability.getShapes();
        assertNotNull("Shape List should not be null", list);
        assertTrue("Shape List should contain values!", list.size() > 0);

        try {
            for (ShapeVo vo : list) {
                LOG.info("Shape is " + vo.getValue());
            }
        } catch (Exception e) {
            fail("Error throws in getShapes: " + e.getMessage());
        }
    }
}
