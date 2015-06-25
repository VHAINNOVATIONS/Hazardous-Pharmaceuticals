/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.domain.common.capability.PrintTemplateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.UserDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.utility.test.DomainTestHelper;


/**
 *PrintTemplateDomainCapabilityTest
 */
public class PrintTemplateDomainCapabilityTest extends DomainCapabilityTestCase {

    private PrintTemplateDomainCapability printTemplateDomainCapability;
    private PrintTemplateVo printTemplateVo;
    private UserVo currentUser;
    
    /**
     * isTrue
     */
    public void testSkipped() {
        boolean isTrue = true;
        assertTrue("Entire class is skipped.", isTrue);
    }

    
   
 
    /**
     * setUp
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        printTemplateDomainCapability = getPrintTemplateDomainCapability();
        currentUser = createInsertedUser();
        printTemplateVo = DomainTestHelper.SearchTemplates.createPrintTemplateVo();
    }
    
    /**
     * PrintTemplateDomainCapability
     * @return PrintTemplateDomainCapability
     */
    protected PrintTemplateDomainCapability getPrintTemplateDomainCapability() {
        return getNationalCapability(PrintTemplateDomainCapability.class);
    }
    

    /**
     * getUserDomainCapability
     * @return UserDomainCapability
     */
    protected UserDomainCapability getUserDomainCapability() {
        return getNationalCapability(UserDomainCapability.class);
    }

    /**
     * creates and inserts a new test user.
     * 
     * @return user
     */
    protected UserVo createInsertedUser() {
        UserVo user = new UserGenerator().pseudoRandom();
        
        user = getUserDomainCapability().create(user);
        
        return user;
    }
    
    /**
     * Test
     * 
     */
    public void testCreate() {
        Long printTemplateId = null;
        String templateName;
        TemplateLocation templateLocation;
        
        templateName = printTemplateVo.getTemplateName();
        templateLocation = printTemplateVo.getTemplateLocation();
        
        printTemplateId = 
            Long.parseLong(
                printTemplateDomainCapability.create(currentUser, printTemplateVo).getId());

        assertNotNull("This id should not be null after save", printTemplateId);
        assertTrue("This id should be greater that 0", printTemplateId > 0);
        
        printTemplateVo = printTemplateDomainCapability.retrieve(printTemplateId);
        
        assertEquals("This template name should match", templateName, printTemplateVo.getTemplateName());
        assertEquals("This template location should match", templateLocation, printTemplateVo.getTemplateLocation());
    }
    
    /**
     * Test
     * 
     */
    public void testCreateWithPrintFields() {
        Long printTemplateId = null;
        String templateName;
        TemplateLocation templateLocation;
        
        templateName = printTemplateVo.getTemplateName();
        templateLocation = printTemplateVo.getTemplateLocation();
        
        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put("itemId", FieldKey.ID);
        linkParameters.put("itemType", FieldKey.ENTITY_TYPE);
        printTemplateVo.addResumeFlowLinkField(FieldKey.NDC, "retrieve", linkParameters);
        printTemplateVo.addInputField(FieldKey.SELECT);
        
        printTemplateId = 
            Long.parseLong(
                printTemplateDomainCapability.create(currentUser, printTemplateVo).getId());

        assertNotNull("The id should not be null after save", printTemplateId);
        assertTrue("The id should be greater that 0", printTemplateId > 0);
        
        printTemplateVo = printTemplateDomainCapability.retrieve(printTemplateId);
        
        assertEquals("template name should match", templateName, printTemplateVo.getTemplateName());
        assertEquals("template location should match", templateLocation, printTemplateVo.getTemplateLocation());
        assertEquals("should match ndc", FieldKey.NDC, printTemplateVo.getFields().get(0).getFieldKey());
        assertEquals("should match select", FieldKey.SELECT, printTemplateVo.getFields().get(1).getFieldKey());
    }

    /**
     * Test
     * 
     */
    public void testUpdate() {
         
        //create
        PrintTemplateVo template = printTemplateDomainCapability.create(currentUser, printTemplateVo);
        PrintTemplateVo retrieved = printTemplateDomainCapability.retrieve(new Long(template.getId()));
        retrieved.setTemplateName("mynewTEmplate");
        retrieved.addTextField(FieldKey.ACCEPT_CHANGE);
        retrieved.addTextField(FieldKey.ABBREVIATION);
        
        printTemplateDomainCapability.update(currentUser, retrieved);
        assertNotNull("An id should not be null after save", printTemplateVo.getId());
 
    }
   
    /**
     * Test
     * 
     */
    public void testRetrieveShouldOrderBySequenceNumber() {
        String printTemplateId = null;
        
        printTemplateVo.addTextField(FieldKey.DOSAGE_FORM);
        printTemplateVo.addTextField(FieldKey.COMMENT);
        assertEquals("should be expected size", 2, printTemplateVo.getFields().size());
        
        printTemplateDomainCapability.create(currentUser, printTemplateVo);
        printTemplateId = printTemplateVo.getId();
        
        printTemplateVo = printTemplateDomainCapability.retrieve(Long.parseLong(printTemplateId));

        assertNotNull("should never return null criteria object", printTemplateVo);
        assertNotNull("id should not be null after save", printTemplateVo.getId());
        assertTrue("id should be greater that 0", Long.parseLong(printTemplateId) > 0);
        assertEquals("We Should have field at expected location in returned collection", 
            FieldKey.DOSAGE_FORM.getKey(), printTemplateVo.getFields().get(0).getFieldKey().getKey());
        assertEquals("should have field at expected location in returned collection", 
            FieldKey.COMMENT.getKey(), printTemplateVo.getFields().get(1).getFieldKey().getKey());
    }

 
}
