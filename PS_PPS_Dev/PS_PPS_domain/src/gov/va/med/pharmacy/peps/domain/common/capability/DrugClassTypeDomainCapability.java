/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;


/**
 * Perform CRUD operations with drug classification types
 */
public interface DrugClassTypeDomainCapability {

    /**
     * Retrieve all DrugClassificationTypeVo.
     * 
     * @return List<DrugClassificationTypeVo>
     */
    List<DrugClassificationTypeVo> retrieveDomain();

    /**
     * 
     * Retrieve a DrugClassificationTypeVo
     *
     * @param id of the DrugClassificationTypeVo
     * @return DrugClassificationTypeVo
     */
    DrugClassificationTypeVo retrieve(String id);

}
