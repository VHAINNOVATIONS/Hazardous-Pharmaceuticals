/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet.test.integration;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.test.VistAWebServiceTestCase;


/**
 * Test requests to retrieve NDC and Products using NDC Numbers and Product Vuids
 */
public class DrugAccountabilityServletTest extends VistAWebServiceTestCase {

    /**
     * Verify a request with a single Add of an NDC
      * 
      * @throws Exception exception
     */ 
    public void testOneNew() throws Exception {
        assertActualResponseEqual("../Interface/etc/xml/document/drug/drugaccountability/DrugAccountabilityOneNew.xml",
            ".*<type>Success</type>.*");
    }

    /**
     * Verify a request with a single Update
     * 
     * @throws Exception exception
     */
    public void testOneUpdate() throws Exception {
        assertActualResponseEqual("../Interface/etc/xml/document/drug/drugaccountability/DrugAccountabilityOneUpdate.xml",
            ".*<type>Success</type>.*");
    }

    /**
     * Verify an Update Request with multiple adds and multiple updates
     * 
     * @throws Exception exception
     */
    public void testMultipleEntries() throws Exception {
        assertActualResponseEqual("../Interface/etc/xml/document/drug/drugaccountability/DrugAccountabilityMultipleEntries.xml",
            ".*<type>Success</type>.*<type>Success</type>.*");
    }

    /**
     * Verify an Update Request with Multiple Adds in it
     * 
     * @throws Exception exception
     */
    public void testMultipleAdds() throws Exception {
        String tag = ".*<type>Success</type>.*"
            + ".*<type>Success</type>.*"
            + ".*<type>Success</type>.*"
            + ".*<type>Success</type>.*";
        
        assertActualResponseEqual("../Interface/etc/xml/document/drug/drugaccountability/DrugAccountabilityMultipleAdds.xml",
            tag);
     
    }

    /**
     * Verify an Update Request with multiple Updates in it
     * 
     * @throws Exception exception
     */
    public void testMultipleUpdates() throws Exception {
        String tag = ".*<type>Success</type>.*"
            + ".*<type>Success</type>.*"
            + ".*<type>Success</type>.*"
            + ".*<type>Success</type>.*";
        
        assertActualResponseEqual("../Interface/etc/xml/document/drug/drugaccountability/DrugAccountabilityMultipleUpdates.xml",
            tag);
    }
 
    /**
     * Return URI.
     * 
     * @return URI
     */
    public String getRequestURL() {
        return "encapsulation/drugaccountability/data";
    }
}
