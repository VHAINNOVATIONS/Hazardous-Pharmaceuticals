/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplWarnLabelDo;


/**
 * Perform CRUD operations with rx consult
 */
public interface RxConsultDomainCapability extends ManagedDataDomainCapability<RxConsultVo, EplWarnLabelDo> {

    /**
     * Get all active, LOCAL, NATIONAL, and COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    List<RxConsultVo> getLocalRxConsult();

    /**
     * Get all active, NATIONAL and COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    List<RxConsultVo> getNationalRxConsult();

    /**
     * Get all active, COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    List<RxConsultVo> getCotsRxConsult();

    /**
     * Re-associates all items using the existing item to the new item.
     * <p>
     * Deletes existing item after re-associating products with the new item.
     * 
     * @param existingItem existing Local {@link RxConsultVo} to associate Products with
     * @param nationalItem new {@link RxConsultVo} to associate Products with
     * @param user {@link UserVo} performing the action
     */
    void reassociateExistingItems(RxConsultVo existingItem, RxConsultVo nationalItem, UserVo user);
}
