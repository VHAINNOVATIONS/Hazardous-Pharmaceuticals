/**
 * Source file created in 2006 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability;


import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.utility.VistALinkResponseInfo;


/**
 * MigrationSynchFileCapability
 *
 */
public interface MigrationSynchFileCapability {

    /**
     * Sends a single NDC item to vista
     * @param item item
     * @return VistALinkResponseInfo.  
     */
    VistALinkResponseInfo sendItemToVista(NdcVo item);
    
    /**
     * Sends a single Manufacturer item to vista
     * @param item item
     * @return VistALinkResponseInfo.  
     */
    VistALinkResponseInfo sendManufacturerToVista(ManufacturerVo item);
    
    /**
     * Sends a single Package Type item to vista
     * @param item item
     * @return VistALinkResponseInfo.  
     */
    VistALinkResponseInfo sendPackageTypeToVista(PackageTypeVo item);
    
    /**
     * sendStartNDCMessage
     * @return true if successful
     */
    boolean sendStartNDCMessage();
    
    /**
     * sendStopNDCMessage
     * @return true if successful
     */
    boolean sendStopNDCMessage();
    
    
}
