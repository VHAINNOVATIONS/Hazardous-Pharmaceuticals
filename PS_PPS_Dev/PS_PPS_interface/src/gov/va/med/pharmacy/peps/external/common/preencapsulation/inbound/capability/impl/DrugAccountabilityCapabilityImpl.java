/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.impl;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.DrugAccountabilityCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.DrugAccountabilityResponseDocument;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.request.DrugAccountabilityRequest;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.response.DrugAccountabilityResponse;


/**
 * Lookup the NDCs and Products for the given NDC numbers and Vuid's.
 */
public class DrugAccountabilityCapabilityImpl implements DrugAccountabilityCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(DrugAccountabilityCapabilityImpl.class);

    /**
     * Handle the XML request
     * 
     * @param xmlRequest request XML from VistA
     * @return response XML
     */
    public DrugAccountabilityRequest handleRequest(String xmlRequest) {
        LOG.debug("Drug Accountability Request from VistA: " + xmlRequest);

        // un marshall to DrugaccountabilityRequest object
        gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.request.DrugAccountabilityRequest drugs =
            gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.DrugAccountabilityRequestDocument
                .instance().unmarshal(xmlRequest);

        return drugs;

    }

    /**
     * Handle the XML request
     * 
     * @param response Drug Accountability Response 
     * @return response XML
     */
    public String handleResponse(DrugAccountabilityResponse response) {

        // marshall results to xml
        String xmlResponse = DrugAccountabilityResponseDocument.instance().marshal(response);

        LOG.debug("Response to VistA: " + xmlResponse);

        return xmlResponse;
    }
}
