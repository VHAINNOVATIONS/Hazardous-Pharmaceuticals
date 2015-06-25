/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintFieldDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintFieldDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintTemplateDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.PrintTemplateConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.test.DomainTestHelper;

import junit.framework.TestCase;


/**
 * Test {@link PrintTemplateConverter}
 */
public class PrintTemplateConverterTest extends TestCase {
    private EplPrintTemplateDo printTemplateDo;
    private PrintTemplateVo printTemplateVo;
    
    private PrintTemplateConverter printTemplateConverter = new PrintTemplateConverter();

    /**
     * PrintTemplateConverterTest
     */
    public PrintTemplateConverterTest() {
        super();
    }
    
    /**
     * test setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {
        printTemplateVo = DomainTestHelper.SearchTemplates.createPrintTemplateVo();
        printTemplateDo = DomainTestHelper.SearchTemplates.createPrintTemplateDo();
    }

    /**
     * Test
     */
    public void testDoShouldNotThrowExceptionWhenIdIsNull() {
        printTemplateVo.setId(null);
        printTemplateDo = printTemplateConverter.convert(printTemplateVo);

        assertNull("This man shold be null", printTemplateDo.getEplId());
    }

    /**
     * Test
     */
    public void testDoShouldMapId() {
        Long testId = PPSConstants.L45;
        printTemplateVo.setId(testId.toString());

        printTemplateDo = printTemplateConverter.convert(printTemplateVo);

        assertEquals("Thiu Should be equal", testId, printTemplateDo.getEplId());
    }

    /**
     * Test
     */
    public void testDoShouldMapTemplateLocation() {
        printTemplateVo.setTemplateLocation(TemplateLocation.EDITABLE_MODIFICATION_SUMMARY);
        printTemplateDo = printTemplateConverter.convert(printTemplateVo);

        assertEquals("Summary Should be equal", TemplateLocation.EDITABLE_MODIFICATION_SUMMARY.toString(), 
                     printTemplateDo.getTemplateLocation());
    }

    /**
     * Test
     */
    public void testDoSetSequenceNumbers() {
        Long testId = PPSConstants.L45;
        EplPrintFieldDo[] printFields = new EplPrintFieldDo[2];

        printTemplateVo.setId(testId.toString());
        printTemplateVo.setTemplateLocation(TemplateLocation.EDITABLE_MODIFICATION_SUMMARY);
        printTemplateVo.addTextField(FieldKey.DOSAGE_FORM);
        printTemplateVo.addTextField(FieldKey.COMMENT);

        // Convert the printTemplateVo to a printTemplateDo
        printTemplateDo = printTemplateConverter.convert(printTemplateVo);
        
        printFields = printTemplateDo.getEplPrintFields().toArray(printFields);

        boolean foundNumber1 = false;
        boolean foundNumber2 = false;
        
        for (int i = 0; i < printFields.length; i++) {
            if (new Long(1L).equals(printFields[i].getSequenceNumber())) {
                foundNumber1 = true;
            } else if (new Long(2L).equals(printFields[i].getSequenceNumber())) {
                foundNumber2 = true;
            } else {
                fail("Unable to test these print field sequence numbers.");
            }
        }
        
        assertTrue("Didn't find the sequence number, 1.", foundNumber1);
        assertTrue("Didn't find the sequence number, 2.", foundNumber2);
    }

    /**
     * Test
     */
    public void testDoShouldMapFields() {
        Long testId = PPSConstants.L45;
        EplPrintFieldDo[] printFields = new EplPrintFieldDo[2];

        printTemplateVo.setId(testId.toString());
        printTemplateVo.setTemplateLocation(TemplateLocation.EDITABLE_MODIFICATION_SUMMARY);
        printTemplateVo.addTextField(FieldKey.DOSAGE_FORM);
        printTemplateVo.addTextField(FieldKey.COMMENT);

        printTemplateDo = printTemplateConverter.convert(printTemplateVo);
        
        printFields = printTemplateDo.getEplPrintFields().toArray(printFields);

        assertEquals("should have expected field count", 2, printFields.length);

        boolean foundDosage = false;
        boolean foundComment = false;
        
        for (int i = 0; i < printFields.length; i++) {
            if (FieldKey.DOSAGE_FORM.getKey().equals(printFields[i].getKey().getPrintFieldName())) {
                foundDosage = true;
            } else if (FieldKey.COMMENT.getKey().equals(printFields[i].getKey().getPrintFieldName())) {
                foundComment = true;
            } else {
                fail("Unable to properly test these print field sequence numbers.");
            }
        }
        
        assertTrue("Didn't find the dosage form field '" + FieldKey.DOSAGE_FORM.getKey() + "'", foundDosage);
        assertTrue("Didn't find the comment field '" + FieldKey.COMMENT.getKey() + "'", foundComment);
    }
    
    /**
     * Test
     */
    public void testVoShouldNotThrowExceptionWhenIdIsNull() {
        printTemplateDo.setEplId(null);
        
        printTemplateVo = printTemplateConverter.convert(printTemplateDo);
        
        assertNull("should map null ids", printTemplateVo.getId());
    }
    
    /**
     * Test
     */
    public void testVoShouldMapId() {
        Long testId = PPSConstants.L45;
        printTemplateDo.setEplId(testId);
        
        printTemplateVo = printTemplateConverter.convert(printTemplateDo);
        
        assertEquals("Test ID should be equal", testId.toString(), printTemplateVo.getId());
    }
    
    /**
     * Test
     */
    public void testVoShouldMapTemplateLocation() {
        printTemplateDo.setTemplateLocation("EDITABLE_MODIFICATION_SUMMARY");
        
        printTemplateVo = printTemplateConverter.convert(printTemplateDo);
        
        assertEquals("should be equal", TemplateLocation.EDITABLE_MODIFICATION_SUMMARY, 
            printTemplateVo.getTemplateLocation());
    }
    
    /**
     * Test
     */
    public void testVoShouldMapFields() {
        EplPrintFieldDoKey printFieldDoKey = new EplPrintFieldDoKey();
        EplPrintFieldDo printFieldDo = new EplPrintFieldDo();
        
        printFieldDoKey.setPrintFieldName(FieldKey.DOSAGE_FORM.getKey());
        printFieldDo.setKey(printFieldDoKey);     
        printTemplateDo.getEplPrintFields().add(printFieldDo);
        
        printTemplateVo = printTemplateConverter.convert(printTemplateDo);
        
        assertEquals("should have proper field count", 1, printTemplateVo.getFields().size());
        assertEquals("should have proper field key on field column", FieldKey.DOSAGE_FORM, 
            printTemplateVo.getFields().get(0).getFieldKey());
    }   

}
