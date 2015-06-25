/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.test;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.DrugDataCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.DrugDataResponseDocument;
import gov.va.med.pharmacy.peps.external.common.stub.ManagedItemCapabilityStub;
import gov.va.med.pharmacy.peps.external.common.stub.ProductDomainCapabilityStub;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DrugData;

import junit.framework.TestCase;


/**
 * Tests the example XML documents.
 * 
 * 
 */
public class DrugDataTest extends TestCase { 
    private static final String RESPONSE = "etc/xml/document/drug/data/drugDataMultipleRequest.xml";
    private DrugDataCapability capability;

    /**
     * setUp
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        ManagedItemCapabilityStub.FOUND = true;
        ProductDomainCapabilityStub.FOUND = true;

        ApplicationContext context = new ClassPathXmlApplicationContext(
            new String[] {"classpath*:xml/spring/test/*Context.xml", "classpath*:xml/local/spring/test/*Context.xml",
                          "classpath*:xml/national/spring/test/CommonContext.xml",
                          "classpath*:xml/spring/test/Callback.xml"});

        this.capability = (DrugDataCapability) context.getBean("drugDataCapability");
    }

    /**
     * Test multiple request.
     * 
     * @throws Exception Exception
     */
    public void testMultipleRequest() throws Exception {
        String response = capability.handleRequest(readDocument(new File(
            RESPONSE)));

        DrugData data = DrugDataResponseDocument.instance().unmarshal(response);

        assertTrue("1.drug count is incorrect", data.getDrugDataFields().size() == PPSConstants.I4);
        assertNull("1.drugs not found count is incorrect", data.getDrugsNotFound());
    }

    /**
     * Test multiple request.
     * 
     * @throws Exception Exception
     */
    public void testNegativeMultipleRequest() throws Exception {
        ManagedItemCapabilityStub.FOUND = false;
        ProductDomainCapabilityStub.FOUND = false;

        String response = capability.handleRequest(readDocument(new File(
            RESPONSE)));

        DrugData data = DrugDataResponseDocument.instance().unmarshal(response);

        assertTrue("1. drug count is incorrect", data.getDrugDataFields().size() == 0);
        assertTrue("ndc not found count is incorrect", data.getDrugsNotFound().getNdc().size() == 2);
        assertTrue("vuid not found count is incorrect", data.getDrugsNotFound().getVuid().size() == 2);
    }

    /**
     * Test single ndc request.
     * 
     * @throws Exception Exception
     */
    public void testSingleNdcRequest() throws Exception {
        String response = capability.handleRequest(readDocument(new File(
            "etc/xml/document/drug/data/drugDataSingleNdcRequest.xml")));

        DrugData data = DrugDataResponseDocument.instance().unmarshal(response);

        assertTrue("2.drug count is incorrect", data.getDrugDataFields().size() == 1);
        assertNull("2.drugs not found count is incorrect", data.getDrugsNotFound());
    }

    /**
     * Test single vuid request.
     * 
     * @throws Exception Exception
     */
    public void testSingleVuidRequest() throws Exception {
        String response = capability.handleRequest(readDocument(new File(
            "etc/xml/document/drug/data/drugDataSingleVuidRequest.xml")));

        DrugData data = DrugDataResponseDocument.instance().unmarshal(response);

        assertTrue("drug count is incorrect", data.getDrugDataFields().size() == 1);
        assertNull("drugs not found count is incorrect", data.getDrugsNotFound());
    }

    /**
     * Read XML document.
     * 
     * @param file file to read
     * @return string
     * @throws IOException exception
     */
    private String readDocument(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            byte[] buffer = new byte[PPSConstants.I1024];

            for (int length = in.read(buffer); length > 0; length = in.read(buffer)) {
                out.write(buffer, 0, length);
            }
        } finally {
            in.close();
            out.close();
        }

        return new String(out.toByteArray());
    }
}
