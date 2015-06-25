/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.test;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.regex.Pattern;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.test.integration.IntegrationTestCase;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.test.stub.VistAWebServiceCallStub;


/**
 * Send a request to the PRE Encapsulation servlet and verify the actual response is equal to the expected response.
 * 
 * This super class of web service (servlet) test cases should not be instantiated and has no test methods, therefor it is
 * marked as abstract.
 */
public abstract class VistAWebServiceTestCase extends IntegrationTestCase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(VistAWebServiceTestCase.class);
    
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private VistAWebServiceCallStub call;

    /**
     * Empty constructor
     */
    public VistAWebServiceTestCase() {
        super();
    }

    /**
     * Give the test case a name
     * 
     * @param name String
     */
    public VistAWebServiceTestCase(String name) {
        super(name);
    }

    /**
     * initialize MockVistACall
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {
        LOG.debug("\n------------------------- " + getName() + " -------------------------\n");
        initializeUrl();
    }

    /**
     * Setup the URL and instantiate the call stub
     */
    public void initializeUrl() {
        String url = "http://" + getLocalHost() + ":" + getLocalPort() + "/PRE/" + getRequestURL();

        try {
            this.call = new VistAWebServiceCallStub(url);
        } catch (MalformedURLException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        }
    }

    /**
     * Return the end of the URL for a given Vista web service call (e.g., 'ordercheck' or 'druginfo')
     * 
     * @return String end of URL
     */
    public abstract String getRequestURL();

    /**
     * getUrl
     * @return the URL this call will send a request to
     */
    public String getUrl() {
        return call.getUrl();
    }
    
    /**
     * Send a request to the PRE Encapsulation servlet.
     * 
     * @param requestPath String file path to request
     * @param responseTag Tag to search for the response
     * @throws IOException IOExceptoin
     */
    public void assertActualResponseEqual(String requestPath, String responseTag) throws IOException {
        String request = readInputStream(requestPath);
        LOG.debug("XML Request:");
        LOG.debug(request);

        String xmlResponse = sendRequest(request);
        LOG.debug("XML Response:");
        LOG.debug(xmlResponse);

        Pattern pattern = Pattern.compile(responseTag, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        assertTrue("Response does not match the expected tag: " + responseTag, pattern.matcher(xmlResponse).matches());
    }

    /**
     * Verify the response is not an exception
     * 
     * @param result XML string representing the result of the test case
     */
    public void assertNotError(String result) {
        assertFalse("Received error", result.substring(result.indexOf("<")).trim().startsWith("<exception"));
    }

    /**
     * Read the text from the given path to a file.
     * 
     * @param path String file path from which to read text
     * @return String text contained in the file represented by the file
     */
    public String readInputStream(String path) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            
            if (inputStream == null) {
                inputStream = new File(path).toURL().openStream();
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer xmlRequest = new StringBuffer();
            String line = reader.readLine();

            while (line != null) {
                xmlRequest.append(line);
                xmlRequest.append(LINE_SEPARATOR);
                line = reader.readLine();
            }

            inputStream.close();

            return xmlRequest.toString();
        } catch (IOException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        }
    }

    /**
     * Send a request to the servlet
     * 
     * @param xmlRequest String XML request to send
     * @return String response from servlet
     * @throws IOException if error sending request
     */
    public String sendRequest(String xmlRequest) throws IOException {
        return call.sendRequest(xmlRequest);
    }
}
