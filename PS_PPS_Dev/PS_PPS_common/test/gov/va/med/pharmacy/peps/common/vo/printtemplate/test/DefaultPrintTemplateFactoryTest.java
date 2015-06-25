/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate.test;


import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;

import junit.framework.TestCase;


/**
 * Test Fixture
 */
public class DefaultPrintTemplateFactoryTest extends TestCase {

    private static final int COLL_SIZE = 46;
    
    /**
     * Test
     */
    public void testPossibleFields() {
        assertNotNull("PossibleFields collection should never be null", DefaultPrintTemplateFactory.getPossibleFields());
        assertEquals("PossibleFields collection was not expected size", 
            COLL_SIZE, DefaultPrintTemplateFactory.getPossibleFields().size());
    }
    
    
    /**
     * Test
     */
    public void testGetDefaultPrintTemplate() {
        PrintTemplateVo printTemplate;
        
        printTemplate = DefaultPrintTemplateFactory.getDefaultPrintTemplate(TemplateLocation.EDITABLE_MODIFICATION_SUMMARY);
        
        assertEquals("did not return expected field key", 
            TemplateLocation.EDITABLE_MODIFICATION_SUMMARY, 
            printTemplate.getTemplateLocation());
    } 
    
    /**
     * Test
     */
    public void testDefaultEditableModificationSummary() {
        assertEquals("was not expected field key!", 
            TemplateLocation.EDITABLE_MODIFICATION_SUMMARY, 
            DefaultPrintTemplateFactory.defaultEditableModificationSummary().getTemplateLocation());
    }    
    
    /**
     * Test
     */
    public void testDefaultNoneditableModificationSummary() {
        assertEquals("was not expected field key", 
            TemplateLocation.NONEDITABLE_MODIFICATION_SUMMARY, 
            DefaultPrintTemplateFactory.defaultNoneditableModificationSummary().getTemplateLocation());
    }
}
