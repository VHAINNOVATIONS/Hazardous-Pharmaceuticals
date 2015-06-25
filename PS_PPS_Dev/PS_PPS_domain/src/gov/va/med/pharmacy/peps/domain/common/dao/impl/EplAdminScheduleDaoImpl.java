/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplAdminScheduleDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplAdminScheduleDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplAdminScheduleDao.
 */
public class EplAdminScheduleDaoImpl extends DataAccessObjectImpl<EplAdminScheduleDo, Long> implements EplAdminScheduleDao {

    /**
     * Finds an all EplAdminScheduleDo object in ascending order from the database.
     * 
     * @return Collection of EplAdminScheduleDo objects
     */
    @Override
    public List<EplAdminScheduleDo> retrieve() {
        return retrieveAscending(EplAdminScheduleDo.NAME);
    }
}
