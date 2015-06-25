/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.fss.outbound.capability;


import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.NdcVo;


/**
 * FSS Interface Capability to perform operations on the FSS data source.
 */
public interface FssInterfaceCapability {

    /**
     * getFssData.
     * @param ndcVo ndcVo
     */
    void getFssData(NdcVo ndcVo);

    /**
     * This method 
     * @param lastRun lastRun
     * @return map
     */
    Map<String, String> getNdcsToUpdate(String lastRun);

}
