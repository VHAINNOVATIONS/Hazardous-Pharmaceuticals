/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplCsFedScheduleDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplCsFedScheduleDo;


/**
 * This class makes avaliable the methods that may be used to access and 
 * modify the data referenced by the EplCsFedScheduleDao.
 */
public class EplCsFedScheduleDaoImpl  
     extends DataAccessObjectImpl<EplCsFedScheduleDo, Long> 
     implements EplCsFedScheduleDao {

    /**
     * Finds an all EplCsFedScheduleDO object in ascending order from the database.
     * 
     * @return Collection of EplCsFedScheduleDO objects
     */
    @Override
    public List<EplCsFedScheduleDo> retrieve() {
        return retrieveAscending(EplCsFedScheduleDo.SCHEDULE_NAME);
    }
}
