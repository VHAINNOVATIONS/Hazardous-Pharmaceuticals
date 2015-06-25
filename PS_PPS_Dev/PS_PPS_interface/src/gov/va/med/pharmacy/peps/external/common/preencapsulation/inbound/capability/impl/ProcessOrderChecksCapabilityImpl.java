/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.impl;


import gov.va.med.pharmacy.peps.common.exception.InterfaceValidationException;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.PerformDrugChecksCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.VersionCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.ProcessOrderChecksCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.DrugCheckRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.DrugCheckResponseDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.drug.check.RequestConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.drug.check.ResponseConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.request.PEPSRequest;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.PEPSResponse;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckVo;


/**
 * Parse the XML message and call the drug data vendor to perform the order checks.
 */
public class ProcessOrderChecksCapabilityImpl implements ProcessOrderChecksCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(ProcessOrderChecksCapabilityImpl.class);
    private PerformDrugChecksCapability performDrugChecksCapability;
    private VersionCapability versionCapability;

    /**
     * Empty constructor
     */
    public ProcessOrderChecksCapabilityImpl() {
        super();
    }

    /**
     * Parse the XML message and call the drug data vendor to perform the order checks.
     * 
     * @param xmlRequest Request from VistA
     * @return String XML response
     */
    public String handleRequest(String xmlRequest) {
        LOG.debug("Request received from VistA:");
        LOG.debug(xmlRequest);

        PEPSRequest request = DrugCheckRequestDocument.instance().unmarshal(xmlRequest);
        OrderCheckVo orderCheck = RequestConverter.toOrderCheckVo(request);
        LOG.debug(orderCheck);

        PEPSResponse response;

        // perform the order check if there are drugs to screen, even if this is a ping request (just to be nice)
        if (orderCheck.getHeader().isPingOnly() && !orderCheck.hasDrugsToScreen()) {
            response = ResponseConverter.createPingResponse(orderCheck.getHeader(), versionCapability
                .retrieveDrugDataVendorVersion());
        } else {

            // at least one order check type is required
            if (!orderCheck.isDrugDoseCheck() && !orderCheck.isDrugDrugCheck() && !orderCheck.isDrugTherapyCheck()) {
                throw new InterfaceValidationException(InterfaceValidationException.ORDER_CHECK_REQUERED);
            }

            // if not a ping request, there must be drugs to screen
            if (!orderCheck.hasDrugsToScreen()) {
                throw new InterfaceValidationException(InterfaceValidationException.DRUGS_TO_SCREEN_REQUIRED);
            }

            OrderCheckResultsVo orderCheckResults = performDrugChecksCapability.performDrugChecks(orderCheck);
            orderCheckResults.setHeader(orderCheck.getHeader());
            orderCheckResults.setDrugDataVendorVersion(versionCapability.retrieveDrugDataVendorVersion());

            response = ResponseConverter.toPepsResponse(orderCheckResults);
        }

        String xmlResponse = DrugCheckResponseDocument.instance().marshal(response);

        // Prevent the XmlUtility from formatting the XML response with indents and the like if DEBUG is not turned on.
        // If this is not done, the expense of formatting will always be done. JAXB documentation recommends against
        // formatting in production code.
        if (LOG.isDebugEnabled()) {
            LOG.debug("Response sent back to VistA:");
            LOG.debug(DrugCheckResponseDocument.instance().toIndentedString(xmlResponse));
        }

        return xmlResponse;
    }

    /**
     * setVersionCapability
     * @param versionCapability versionCapability property
     */
    public void setVersionCapability(VersionCapability versionCapability) {
        this.versionCapability = versionCapability;
    }

    /**
     * setPerformDrugChecksCapability
     * @param performDrugChecksCapability performDrugChecksCapability property
     */
    public void setPerformDrugChecksCapability(PerformDrugChecksCapability performDrugChecksCapability) {
        this.performDrugChecksCapability = performDrugChecksCapability;
    }
}
