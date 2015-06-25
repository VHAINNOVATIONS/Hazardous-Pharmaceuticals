/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;



/**
 * DrugReferenceAutoCapability encapsulates the FDB behavior for the auto update code.
 * It has a method for the newly added entries in FDB and a method for the newly updated entries.
 *
 */
public interface DrugReferenceAutoCapability {

    /**
     * getFdbAddedItems
     * @param date date
     * @return List<String>
     */
    List<FdbAutoAddVo> getFdbAddedItems(Date date);


    /**
     * getFdbUpdatedItems
     * @param startDate startDate
     * @return List<String>
     */
    List<FdbAutoUpdateVo> getFdbUpdatedItems(Date startDate);
    
    
    /**
     * getFdbUpdateDate
     * @return String UpdateDate
     */
    String getFdbUpdateDate();
    
}
