/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session;


/**
 * Retrieve the dose routes and dose types for a given list of GCN sequence numbers.
 */
public interface DrugDataService {

    /**
     * Retrieve the dose routes and dose types for the given XML request.
     * 
     * @param request request XML from VistA
     * @return response XML
     */
    String retrieveDrugData(String request);
}
