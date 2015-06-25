/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.ProcessOrderChecksCapability;
import gov.va.med.pharmacy.peps.external.common.test.InterfaceTestCase;


/**
 * ProcessOrderChecksCapabilityTest
 */
public class ProcessOrderChecksCapabilityTest extends InterfaceTestCase {
    private ProcessOrderChecksCapability capability;

    /**
     * setUp
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {
        this.capability = getSpringBean(ProcessOrderChecksCapability.class);
    }

    /**
     * Test sending a valid XML message from M to Java
     * 
     * @throws Exception Exception
     */
    public void testSendXMLCall() throws Exception {
        String response = capability.handleRequest(readInputStream("xml/document/test/orderCheck1.xml"));

        assertTrue("Response XML is incorrect", response.indexOf("<PEPSResponse") != -1);
    }

    /**
     * Test sending an invalid XML message from M to Java
     * 
     * @throws Exception Exception
     */
    public void testSendXMLCallInvalidMessage1() throws Exception {
        try {
            capability.handleRequest(readInputStream("xml/document/test/drugCheckInputMissingAttribute.xml"));
            fail();
        } catch (InterfaceException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Test sending an invalid XML message from M to Java
     * 
     * @throws Exception Exception
     */
    public void testSendXMLCallInvalidMessage2() throws Exception {
        try {
            capability.handleRequest(readInputStream("xml/document/test/drugCheckInputMissingElement.xml"));
            fail();
        } catch (InterfaceException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Test sending an invalid XML message from M to Java
     * 
     * @throws Exception Exception
     */
    public void testSendXMLCallInvalidMessage3() throws Exception {
        try {
            capability.handleRequest(readInputStream("xml/document/test/drugCheckInputWrongEnumeration.xml"));
            fail();
        } catch (InterfaceException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Read the text from the given path to a file.
     * 
     * @param path String file path from which to read text
     * @return String text contained in the file represented by the file
     * @throws IOException if error reading from the file
     */
    private String readInputStream(String path) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer xmlRequest = new StringBuffer();
        String line = reader.readLine();

        while (line != null) {
            xmlRequest.append(line);
            line = reader.readLine();
        }

        inputStream.close();

        return xmlRequest.toString();
    }
}
