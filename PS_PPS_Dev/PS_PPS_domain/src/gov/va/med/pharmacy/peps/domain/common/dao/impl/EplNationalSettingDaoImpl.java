/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplNationalSettingDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplNationalSettingDo;


/**
 * EplNationalSettingDaoImpl
 */
public class EplNationalSettingDaoImpl extends DataAccessObjectImpl<EplNationalSettingDo, Long> 
            implements EplNationalSettingDao {

    /**
     * Inherited from the AutoAddDAO interface. Finds an all EplFileDo object in ascending order from the database.
     * 
     * @return Collection of EplFileDo objects
     */
    @Override
    public List<EplNationalSettingDo> retrieve() {
        return retrieveAscending(EplNationalSettingDo.ID);
    }
}
