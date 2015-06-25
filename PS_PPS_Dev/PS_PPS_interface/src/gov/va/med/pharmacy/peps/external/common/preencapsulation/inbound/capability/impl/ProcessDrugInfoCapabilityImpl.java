/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.impl;


import java.util.Collection;

import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugInfoCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.ProcessDrugInfoCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.DrugInfoRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.DrugInfoResponseDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.drug.info.RequestConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.drug.info.ResponseConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.info.request.DrugInfoRequest;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.info.response.DrugInfoResponse;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugInfoResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugInfoVo;


/**
 * Retrieve the dose routes and dose types for a given list of GCN sequence numbers.
 */
public class ProcessDrugInfoCapabilityImpl implements ProcessDrugInfoCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(ProcessDrugInfoCapabilityImpl.class);

    private DrugInfoCapability drugInfoCapability;

    /**
     * Handle the XML request
     * 
     * @param xmlRequest request XML from VistA
     * @return response XML
     */
    public String handleRequest(String xmlRequest) {
        LOG.debug("Request received from VistA:");
        LOG.debug(xmlRequest);

        DrugInfoRequest request = DrugInfoRequestDocument.instance().unmarshal(xmlRequest);
        Collection<DrugInfoVo> drugs = RequestConverter.toDrugInfoVo(request);

        DrugInfoResultsVo results = drugInfoCapability.processDrugInfoRequest(drugs);
        
        DrugInfoResponse response = ResponseConverter.toDrugInfoResponse(results);
        String xmlResponse = DrugInfoResponseDocument.instance().marshal(response);

        // Prevent the XmlUtility from formatting the XML response with indents and the like if DEBUG is not turned on.
        // If this is not done, the expense of formatting will always be done. JAXB documentation recommends against
        // formatting in production code.
        if (LOG.isDebugEnabled()) {
            LOG.debug("Response sent back to VistA:");
            LOG.debug(DrugInfoResponseDocument.instance().toIndentedString(xmlResponse));
        }

        return xmlResponse;
    }

    /**
     * setDrugInfoCapability
     * @param drugInfoCapability drugInfoCapability property
     */
    public void setDrugInfoCapability(DrugInfoCapability drugInfoCapability) {
        this.drugInfoCapability = drugInfoCapability;
    }
}
