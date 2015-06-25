/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate.test;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;

import junit.framework.TestCase;


/**
 * Test Fixture
 */
public class PrintTemplateVoTest extends TestCase {

    private PrintTemplateVo printTemplateVo;

    /**
     * test setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {

        printTemplateVo = new PrintTemplateVo();
    }

    /**
     * Test
     */
    public void testPopulatePrintFieldCollection() {

        assertEquals("Wrong size", 0, printTemplateVo.getFields().size());

        List<FieldKey> fields = new ArrayList<FieldKey>();

        fields.add(FieldKey.getKey("generic.name"));
        fields.add(FieldKey.getKey("displayable.ingredient.strength"));
        fields.add(FieldKey.getKey("displayable.ingredient.unit"));

        PrintTemplateVo.populatePrintFieldCollection(printTemplateVo, fields);
        final int num3 = 3;
        assertEquals("fields collection size should be " + num3, num3, printTemplateVo.getFields().size());
        assertEquals("field name should be equal", "generic.name",
            printTemplateVo.getFields().get(0).getFieldKey().getKey());
    }

    /**
     * Test
     */
    public void testPopulatePrintFieldCollectionShouldThrowExceptionWhenCurrentFieldsCollectionIsNotEmpty() {

        printTemplateVo.addTextField(FieldKey.FIELD_NAME);
        assertEquals("fields collection size should be 1", 1, printTemplateVo.getFields().size());

        List<FieldKey> fields = new ArrayList<FieldKey>();

        try {
            PrintTemplateVo.populatePrintFieldCollection(printTemplateVo, fields);
            fail("Expected exception not thrown");
        } catch (CommonException ce) {
            assertTrue("should be an illegal argument exception",
                ce.getMessage().contains("argument passed is incompatible"));
            assertTrue("exception message should contain additional info",
                ce.getMessage().contains("printTemplateVo"));
        }
    }
}
