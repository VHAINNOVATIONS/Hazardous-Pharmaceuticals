/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.test.integration;


import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.service.common.session.PrintTemplateService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * Test the PrintTemplateService
 */
public class PrintTemplateServiceTest extends IntegrationTestCase {
    private PrintTemplateService printTemplateService;

    /**
     * PrintTemplateServiceTest
     * @param name A name for the test.
     */
    public PrintTemplateServiceTest(String name) {
        super(name);
    }

    /**
     * setUp
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        this.printTemplateService = getNationalService(PrintTemplateService.class);
    }

    /**
     * Since this method is currently a stub, just test that the returned PrintTemplate is equal to the one we sent in.
     */
    public void testCreate() {
        UserVo user = new UserGenerator().getLocalManagerOne();
        PrintTemplateVo printTemplate = DefaultPrintTemplateFactory.defaultNdcSearch();
        printTemplate.setTemplateName("TEST NDC CREATE TEMPLATE");

        PrintTemplateVo result = printTemplateService.create(user, printTemplate);

        // The original print template will not match the new one because of ID, we are going to assign the ID and verify
        // they match
        printTemplate.setId(result.getId());

        assertEquals(
            "Since this method is currently a stub, the resulting print template should be equal to the one sent in. ",
            printTemplate, result);
    }

    /**
     * Test the retrieval of a NDC Search print template
     */
    public void testRetrieve() {
        UserVo user = new UserGenerator().getLocalManagerOne();
        PrintTemplateVo result = printTemplateService.retrieve(user, TemplateLocation.NDC_SEARCH);

        assertEquals("Should have PrintTemplateVo with NDC_SEARCH as its location", TemplateLocation.NDC_SEARCH, result
            .getTemplateLocation());
    }

    /**
     * Since this method is currently a stub, just test that the returned PrintTemplate is equal to the one we sent in.
     */
    public void testDelete() {
        PrintTemplateVo printTemplate = DefaultPrintTemplateFactory.defaultNdcSearch();

        PrintTemplateVo result = printTemplateService.delete(printTemplate);

        assertEquals(
            "Since this method is currently a stub, the resulting print template should be equal to the one sent in.",
            printTemplate, result);
    }
}
