/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.test;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gov.va.med.pharmacy.peps.external.common.utility.test.XmlDocumentUtility;


/**
 * Send a request to the drug info servlet.
 * 
 * This super class of web service (servlet) test cases should not be instantiated and has no test methods, therefore it is
 * marked as abstract.
 */
public abstract class DrugInfoTestCase extends VistAWebServiceTestCase { 

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(DrugInfoTestCase.class);
    
    /**
     * Empty constructor
     */
    public DrugInfoTestCase() {
        super();
    }

    /**
     * Give the test case a name
     * 
     * @param name String
     */
    public DrugInfoTestCase(String name) {
        super(name);
    }

    /**
     * Return 'druginfo'
     * 
     * @return String 'druginfo'
     */
    public String getRequestURL() {
        return "druginfo";
    }

    /**
     * Send a request to the PRE Encapsulation servlet.
     * 
     * @param requestPath String file path to request
     * @param responsePath String file path to expected response
     * @throws IOException IOException
     */
    public void assertActualResponseEqual(String requestPath, String responsePath) throws IOException {
        String request = readInputStream(requestPath);
        LOG.debug("Sending request to: " + getUrl());
        LOG.debug("XML Request:");
        LOG.debug(request);
        long start = System.currentTimeMillis();
        String actual = sendRequest(request);
        long stop = System.currentTimeMillis();
        LOG.debug("Actual XML Response:");
        LOG.debug(XmlDocumentUtility.prettyPrintDrugInfoResponse(actual));
        String expected = readInputStream(responsePath);
        LOG.debug("Expected XML Response:");
        LOG.debug(expected);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy h:mm:ss.SSS a z", Locale.US);
        LOG.debug("Start Time: " + dateFormat.format(new Date(start)));
        LOG.debug("Stop Time: " + dateFormat.format(new Date(stop)));
        LOG.debug("Execution Time: " + (stop - start) + " milliseconds");

        assertTrue("Response XML is not correct", XmlDocumentUtility.drugInfoResponseEquals(expected, actual));
    }
}
