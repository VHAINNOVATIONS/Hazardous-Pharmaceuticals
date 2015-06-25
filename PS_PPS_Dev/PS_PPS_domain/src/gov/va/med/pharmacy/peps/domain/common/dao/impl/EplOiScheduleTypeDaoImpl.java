/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplOiScheduleTypeDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiScheduleTypeDo;


/**
 * This Schedule makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplScheduleTypeDao.
 */
public class EplOiScheduleTypeDaoImpl extends DataAccessObjectImpl<EplOiScheduleTypeDo, Long> implements
    EplOiScheduleTypeDao {

    /**
     * Finds an all EplScheduleTypeDO object in ascending order from the database.
     * 
     * @return Collection of EplScheduleTypeDO objects
     */
    @Override
    public List<EplOiScheduleTypeDo> retrieve() {
        return retrieveAscending(EplOiScheduleTypeDo.CODE);
    }
}
