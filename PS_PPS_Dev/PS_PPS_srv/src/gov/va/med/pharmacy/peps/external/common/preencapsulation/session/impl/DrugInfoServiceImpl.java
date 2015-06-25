/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session.impl;


import gov.va.med.pharmacy.peps.external.common.document.ExceptionDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.ProcessDrugInfoCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.DrugInfoService;


/**
 * Retrieve the dose routes and dose types for a given list of GCN sequence numbers.
 */
public class DrugInfoServiceImpl implements DrugInfoService {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DrugInfoServiceImpl.class);
    private ProcessDrugInfoCapability processDrugInfoCapability;

    /**
     * Retrieve the dose routes and dose types for the given XML request.
     * 
     * @param request request XML from VistA
     * @return response XML
     */
    public String retrieveDrugInformation(String request) {
        try {
            return processDrugInfoCapability.handleRequest(request);
        } catch (Throwable t) {
            String xmlError = ExceptionDocument.instance().createXmlErrorMessage(t);
            LOG.error("Error message sent to VistA: " + xmlError);

            return xmlError;
        }
    }

    /**
     * description
     * @param processDrugInfoCapability processDrugInfoCapability property
     */
    public void setProcessDrugInfoCapability(ProcessDrugInfoCapability processDrugInfoCapability) {
        this.processDrugInfoCapability = processDrugInfoCapability;
    }
}
