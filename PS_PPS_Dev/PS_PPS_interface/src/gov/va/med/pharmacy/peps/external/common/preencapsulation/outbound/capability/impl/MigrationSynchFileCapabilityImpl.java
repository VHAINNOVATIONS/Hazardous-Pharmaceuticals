/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl;


import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.MigrationManufacturerSyncResponseDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.MigrationNdcUpnSynchFileStatusDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.MigrationPackageTypeSyncResponseDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.utility.VistALinkResponseInfo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.MigrationSynchFileCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.ManufacturerMigrationSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.MigrationNdcUpnSynchFileDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.PackageTypeMigrationSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.VistaLinkConnectionUtility;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.manufacturer.response.ManufacturerMigrationSynchResponse;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.ndc.response.NdcMigrationSynchResponse;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.ndc.response.ResponseStatusType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.packagetype.response.PackageTypeMigrationSynchResponse;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.manufacturer.request.ManufacturerMigrationSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.ndc.request.ManufacturerRecord;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.ndc.request.NdcMigrationSynchRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.ndc.request.NdcUPNFieldsType;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.ndc.request.PackageTypeRecord;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.ndc.request.ProductRecord;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.ndc.request.StatusField;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.packagetype.request.PackageTypeMigrationSyncRequest;
import gov.va.med.vistalink.rpc.RpcResponse;


/**
 * MigrationSynchFileCapabilityImpl class
 * 
 */
public class MigrationSynchFileCapabilityImpl implements MigrationSynchFileCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(MigrationSynchFileCapabilityImpl.class);

    private static String RPC_NAME = "PPS NDFMS MIGR SYNC";
    private static String RPC_CONTEXT = "XOBV VISTALINK TESTER";
    private VistaLinkConnectionUtility vistaLinkConnectionUtility;

    /**
     * sendStartNDCMessage
     * 
     * @return true if successfuly
     */
    public boolean sendStartNDCMessage() {
        NdcMigrationSynchRequest request = new NdcMigrationSynchRequest();
        StatusField status = new StatusField();
        status.setStatus("Start");
        request.setMigrationStatus(status);

        String xml = MigrationNdcUpnSynchFileDocument.instance().marshal(request);
        List<String> params = new LinkedList<String>();
        params.add(xml);

        LOG.debug("Sent NDC Start Message xml sent is " + xml);

        try {

            // Send the request and get the response
            RpcResponse response = vistaLinkConnectionUtility.sendRequest(RPC_CONTEXT, RPC_NAME, params, null);
            String xmlResponse = response.getResults();
            LOG.debug("Response to StartNDCMessage is " + xmlResponse);
        } catch (Exception E) {
            LOG.error("NDC Synch Start Message has an exception:" + E.getMessage());

            return false;
        }

        return true;
    }

    /**
     * sendStopNDCMessage
     * 
     * @return true if successful
     */
    public boolean sendStopNDCMessage() {
        NdcMigrationSynchRequest request = new NdcMigrationSynchRequest();
        StatusField status = new StatusField();
        status.setStatus("Stop");
        request.setMigrationStatus(status);

        String xml = MigrationNdcUpnSynchFileDocument.instance().marshal(request);
        List<String> params = new LinkedList<String>();
        params.add(xml);

        LOG.debug("Sent NDC Stop Message xml sent is " + xml);

        try {

            // Send the request and get the response
            RpcResponse response = vistaLinkConnectionUtility.sendRequest(RPC_CONTEXT, RPC_NAME, params, null);

            String xmlResponse = response.getResults();
            LOG.debug("Sent NDC Stop Message xml Response is " + xmlResponse);
        } catch (Exception E) {
            LOG.error("NDC Synch Stop Message has an exception:" + E.getMessage());

            return false;
        }

        return true;
    }

    /**
     * sendItemToVista
     * 
     * @param item
     *            Item to send to Vista
     * @return VistALinkResponseInfo
     */
    public VistALinkResponseInfo sendItemToVista(NdcVo item) {

        VistALinkResponseInfo rsp = new VistALinkResponseInfo();
        NdcMigrationSynchRequest convertedItem = toNdcItem(item);

        String xml = MigrationNdcUpnSynchFileDocument.instance().marshal(convertedItem);
        xml.replace("\n", "");
        List<String> params = new LinkedList<String>();
        params.add(xml);

        NdcMigrationSynchResponse ndcMigrationSynchResponse = null;

        try {

            RpcResponse rpcResponse = vistaLinkConnectionUtility.sendRequest(RPC_CONTEXT, RPC_NAME, params, null);

            String xmlResponse = rpcResponse.getResults();

            try {
                ndcMigrationSynchResponse = MigrationNdcUpnSynchFileStatusDocument.instance().unmarshal(xmlResponse);

                if (ndcMigrationSynchResponse.getResponseType().getStatus().equals(ResponseStatusType.FAILURE)) {
                    LOG.error("*****************NDC Failed System Message Repsonse*************");
                    LOG.error("NDC Request is " + xml);
                    LOG.error("NDC Response is " + xmlResponse);
                    LOG.error("NDC Reponse Message is : " + ndcMigrationSynchResponse.getResponseType().getMessage());
                    rsp.setErrorResponseString(ndcMigrationSynchResponse.getResponseType().getMessage());

                    return rsp;
                } else {
                    if (ndcMigrationSynchResponse.isSetNdcIen()) {
                        rsp.setIen(ndcMigrationSynchResponse.getNdcIen());
                    }
                }
            } catch (Exception e) {
                LOG.error("NDC Sync Response is " + xmlResponse);
                LOG.error("NDC Error parsing vista response to Stop Message: " + e.getMessage());
                rsp.setErrorResponseString(e.getMessage());

                return rsp;
            }

        } catch (Exception E) {
            LOG.error("The NDC Synch Message has an exception for " + item.getNdc() 
                + " and the exception is " + E.getMessage());
        }

        return rsp;
    }

    /**
     * sendManufacturerToVista
     * 
     * @param item
     *            Item to send to Vista
     * @return VistALinkResponseInfo
     */
    public VistALinkResponseInfo sendItemToVista(ManufacturerVo item) {

        VistALinkResponseInfo rsp = new VistALinkResponseInfo();
        ManufacturerMigrationSyncRequest convertedItem = toManufacturerItem(item);

        String xml = ManufacturerMigrationSyncRequestDocument.instance().marshal(convertedItem);
        xml.replace("\n", "");
        List<String> params = new LinkedList<String>();
        params.add(xml);

        ManufacturerMigrationSynchResponse manufacturerMigrationSynchResponse = null;

        try {
            LOG.debug("sendItemToVista Manufacturer request is " + xml);
            RpcResponse rpcResponse = vistaLinkConnectionUtility.sendRequest(RPC_CONTEXT, RPC_NAME, params, null);
            String xmlResponse = rpcResponse.getResults();
            LOG.debug("sendItemToVista Manufacturer response is " + xmlResponse);
            
            try {
                manufacturerMigrationSynchResponse =
                    MigrationManufacturerSyncResponseDocument.instance().unmarshal(xmlResponse);

                if (manufacturerMigrationSynchResponse.getResponseType().getStatus().equals(ResponseStatusType.FAILURE)) {
                    LOG.error("*****************Manufacturer Failed System Message Repsonse*************");
                    LOG.error("Manufacturer Request is " + xml);
                    LOG.error("Manufacturer Response is " + xmlResponse);
                    LOG.error("Manufacturer Reponse Message is : " 
                        + manufacturerMigrationSynchResponse.getResponseType().getMessage());
                    rsp.setErrorResponseString(manufacturerMigrationSynchResponse.getResponseType().getMessage());

                    return rsp;
                }
            } catch (Exception e) {
                LOG.error("Manufacturer Sync Response is " + xmlResponse);
                LOG.error("Manufacturer Error parsing vista response to Stop Message: " + e.getMessage());
                rsp.setErrorResponseString(e.getMessage());

                return rsp;
            }

        } catch (Exception E) {
            LOG.error("Manufacturer Synch Message has an exception for " + item.getValue() 
                + " : exception is " + E.getMessage());
        }

        if (manufacturerMigrationSynchResponse.getManufacturerIen() != null) {
            rsp.setIen(manufacturerMigrationSynchResponse.getManufacturerIen());
        }

        return rsp;

    }

    /**
     * sendManufacturerToVista
     * 
     * @param item
     *            Item to send to Vista
     * @return null or ErrorMessage
     */
    public VistALinkResponseInfo sendManufacturerToVista(ManufacturerVo item) {

        ManufacturerMigrationSyncRequest convertedItem = toManufacturerItem(item);
        VistALinkResponseInfo rsp = new VistALinkResponseInfo();

        String xml = ManufacturerMigrationSyncRequestDocument.instance().marshal(convertedItem);
        xml.replace("\n", "");
        List<String> params = new LinkedList<String>();
        params.add(xml);

        try {
            LOG.debug("Manufacturer request is " + xml);
            RpcResponse rpcResponse = vistaLinkConnectionUtility.sendRequest(RPC_CONTEXT, RPC_NAME, params, null);
            String xmlResponse = rpcResponse.getResults();
            ManufacturerMigrationSynchResponse manufacturerMigrationSynchResponse = null;
            LOG.debug("Manufacturer response is " + xmlResponse);
            
            
            try {
                manufacturerMigrationSynchResponse =
                    MigrationManufacturerSyncResponseDocument.instance().unmarshal(xmlResponse);

                if (manufacturerMigrationSynchResponse.getResponseType().getStatus().equals(ResponseStatusType.FAILURE)) {
                    LOG.error("*****************Manufacturer1 Failed System Message Repsonse*************");
                    LOG.error("Manufacturer1 Request is " + xml);
                    LOG.error("Manufacturer1 Response is " + xmlResponse);
                    LOG.error("Manufacturer1 Reponse Message is : " 
                        + manufacturerMigrationSynchResponse.getResponseType().getMessage());

                    rsp.setErrorResponseString(manufacturerMigrationSynchResponse.getResponseType().getMessage());

                    return rsp;
                } else {
                    if (manufacturerMigrationSynchResponse.isSetManufacturerIen()) {
                        rsp.setIen(manufacturerMigrationSynchResponse.getManufacturerIen());
                    }
                }
            } catch (Exception e) {
                LOG.error("Manufacturer1 Sync Response is " + xmlResponse);
                LOG.error("Manufacturer1 Error parsing vista response to Manufacturer Sync message: " + e.getMessage());

                rsp.setErrorResponseString("Error parsing VistA response for the Manufacturer " + e.getMessage());

                return rsp;
            }

        } catch (Exception E) {
            LOG.error("Manufacturer1 Sync Message has an exception for " + item.getValue() 
                + ". Exception is " + E.getMessage());
        }

        return rsp;

    }

    /**
     * sendPackageTypeToVista
     * 
     * @param item
     *            Item to send to Vista
     * @return null or ErrorMessage
     */
    public VistALinkResponseInfo sendPackageTypeToVista(PackageTypeVo item) {

        PackageTypeMigrationSyncRequest convertedItem = toPackageTypeItem(item);
        VistALinkResponseInfo rsp = new VistALinkResponseInfo();

        String xml = PackageTypeMigrationSyncRequestDocument.instance().marshal(convertedItem);
        xml.replace("\n", "");
        List<String> params = new LinkedList<String>();
        params.add(xml);

        try {

            LOG.debug("Package Type request is " + xml);
            RpcResponse rpcResponse = vistaLinkConnectionUtility.sendRequest(RPC_CONTEXT, RPC_NAME, params, null);
            String xmlResponse = rpcResponse.getResults();
            LOG.debug("Package Type response is " + xmlResponse);
            PackageTypeMigrationSynchResponse packageTypeMigrationSynchResponse = null;

            try {
                packageTypeMigrationSynchResponse =
                    MigrationPackageTypeSyncResponseDocument.instance().unmarshal(xmlResponse);

                
                
                if (packageTypeMigrationSynchResponse.getResponseType().getStatus().equals(ResponseStatusType.FAILURE)) {
                    LOG.error("*****************Failed System Message Repsonse*************");
                    LOG.error("Request is " + xml);
                    LOG.error("Response is " + xmlResponse);
                    LOG.error("Reponse Message is : " + packageTypeMigrationSynchResponse.getResponseType().getMessage());

                    rsp.setErrorResponseString(packageTypeMigrationSynchResponse.getResponseType().getMessage());

                    return rsp;
                } else {
                    if (packageTypeMigrationSynchResponse.isSetPackageTypeIen()) {
                        rsp.setIen(packageTypeMigrationSynchResponse.getPackageTypeIen());
                    }
                }
            } catch (Exception e) {
                LOG.error("Sync Response is " + xmlResponse);
                LOG.error("Error parsing vista response to Package Type Sync message: " + e.getMessage());

                rsp.setErrorResponseString("Error parsing VistA response to Package Type " + e.getMessage());

                return rsp;
            }

        } catch (Exception E) {
            LOG.error("Package Type Sync Message has an exception for " + item.getValue() + " exception is " + E.getMessage());
        }

        return rsp;

    }

    /**
     * convert NdcVo to JaxB Version
     * 
     * @param vo
     *            NdcVo
     * @return NdcMigrationSynchRequst
     */
    private NdcMigrationSynchRequest toNdcItem(NdcVo vo) {
        NdcMigrationSynchRequest request = new NdcMigrationSynchRequest();
        NdcUPNFieldsType type = new NdcUPNFieldsType();
        String ndcNum = vo.getNdc().replace("-", "");
        type.setNdc("0" + ndcNum);

        if ((vo.getUpcUpn() == null) || (vo.getUpcUpn().equals(""))) {
            type.setUpn(null);
        } else {
            type.setUpn(vo.getUpcUpn().replace("-", ""));
        }

        if (vo.getInactivationDate() == null) {
            type.setInactivationDate(null);
        } else {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(vo.getInactivationDate());

            try {
                type.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
            } catch (DatatypeConfigurationException e) {
                LOG.error("NDC Synch Message Send:  Inactivation Date mismatch with " + vo.getNdc());
                type.setInactivationDate(null);
            }
        }

        if (vo.getManufacturer().getValue() == null) {
            LOG.error("NDC does not have a manufacturer. " + vo.getNdc());
        } else {
            ManufacturerRecord manufacturer = new ManufacturerRecord();
            manufacturer.setManufacturer(vo.getManufacturer().getValue());

            if (vo.getManufacturer().getManufacturerIen() != null) {
                manufacturer.setManufacturerIen(vo.getManufacturer().getManufacturerIen());
            }

            type.setManufacturerRecord(manufacturer);
        }

        type.setOtcRxIndicator(vo.getOtcRxIndicator().getValue());
        type.setPackageSize(vo.getPackageSize().toString());

        if (vo.getPackageType().getValue() == null) {
            type.setPackageTypeRecord(null);
        } else {
            PackageTypeRecord packageType = new PackageTypeRecord();
            packageType.setPackageType(vo.getPackageType().getValue());

            if (vo.getPackageType().getPackagetypeIen() != null) {
                packageType.setPackageTypeIen(vo.getPackageType().getPackagetypeIen());
            }

            type.setPackageTypeRecord(packageType);
        }

        type.setTradeName(vo.getTradeName());

        if (vo.getProduct() == null) {
            type.setVaProduct(null);
            LOG.error("NDC does not have a Product. " + vo.getNdc());
        } else {
            ProductRecord product = new ProductRecord();
            product.setProductName(vo.getProduct().getVaProductName());

            if (vo.getProduct().getNdfProductIen() != null) {
                product.setProductIen(vo.getProduct().getNdfProductIen().toString());
            }

            type.setVaProduct(product);
        }

        // OTC Rx Indicator
        if (vo.getOtcRxIndicator() != null) {
            type.setOtcRxIndicator(vo.getOtcRxIndicator().getValue());
        }

        request.setNdcUPN(type);

        return request;
    }

    /**
     * convert Manufacturer to JaxB Version
     * 
     * @param vo
     *            ManufacturerVo
     * @return NdcMigrationSynchRequst
     */
    private ManufacturerMigrationSyncRequest toManufacturerItem(ManufacturerVo vo) {
        ManufacturerMigrationSyncRequest request = new ManufacturerMigrationSyncRequest();
        request.setManufacturer(vo.getValue());

        if (vo.getInactivationDate() == null) {
            request.setInactivationDate(null);
        } else {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(vo.getInactivationDate());

            try {
                request.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
            } catch (DatatypeConfigurationException e) {
                LOG.error("Manufacturer Synch Message Send:  Inactivation Date mismatch with " + vo.getValue());
                request.setInactivationDate(null);
            }
        }

        return request;
    }

    /**
     * convert Package Type to JaxB Version
     * 
     * @param vo
     *            PackageTypeVo
     * @return PackageTypeMigrationSyncRequst
     */
    private PackageTypeMigrationSyncRequest toPackageTypeItem(PackageTypeVo vo) {
        PackageTypeMigrationSyncRequest request = new PackageTypeMigrationSyncRequest();
        request.setPackageType(vo.getValue());

        if (vo.getInactivationDate() == null) {
            request.setInactivationDate(null);
        } else {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(vo.getInactivationDate());

            try {
                request.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
            } catch (DatatypeConfigurationException e) {
                LOG.error("Package Type Synch Message Send:  Inactivation Date mismatch with " + vo.getValue());
                request.setInactivationDate(null);
            }
        }

        return request;
    }

    /**
     * Sets the VistaLinkConnectionUtility.
     * 
     * @param vistaLinkConnectionUtility
     *            VistaLinkConnectionUtility Class that will be used to communicate with VistA.
     */
    public void setVistaLinkConnectionUtility(VistaLinkConnectionUtility vistaLinkConnectionUtility) {
        this.vistaLinkConnectionUtility = vistaLinkConnectionUtility;
    }

}
