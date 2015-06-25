/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.sts.outbound.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;


/**
 * STS Web Service Capability to perform operations on standard managed domain items.
 */
public interface StsInterfaceCapability {

    /**
     * getStsData.
     * @return  List<StandardMedicationRouteVo> dataList
     */
    List<StandardMedRouteVo> getStsData();
    
    
   
}
