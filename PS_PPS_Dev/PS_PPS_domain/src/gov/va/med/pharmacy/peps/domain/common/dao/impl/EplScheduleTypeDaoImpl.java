/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplScheduleTypeDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplScheduleTypeDo;


/**
 * This Schedule makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplScheduleTypeDao.
 */
public class EplScheduleTypeDaoImpl extends DataAccessObjectImpl<EplScheduleTypeDo, Long> implements EplScheduleTypeDao {

    /**
     * Finds an all EplScheduleTypeDO object in ascending order from the database.
     * 
     * @return Collection of EplScheduleTypeDO objects
     */
    @Override
    public List<EplScheduleTypeDo> retrieve() {
        return retrieveAscending(EplScheduleTypeDo.NAME);
    }
}
