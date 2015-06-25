/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session.impl;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.DrugDataCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.DrugDataService;


/**
 * Retrieve the NDC and Products for a given list of NDC numbers and Vuid's.
 */
public class DrugDataServiceImpl implements DrugDataService {

    private DrugDataCapability drugDataCapability;

    /**
     * Retrieve the NDC and Products for the given XML request.
     * 
     * @param request request XML from VistA
     * @return response XML
     */
    public String retrieveDrugData(String request) {
        return drugDataCapability.handleRequest(request);
    }

    /**
     * Set capability.
     * 
     * @param drugDataCapability drugDataCapability property
     */
    public void setDrugDataCapability(DrugDataCapability drugDataCapability) {
        this.drugDataCapability = drugDataCapability;
    }
}
