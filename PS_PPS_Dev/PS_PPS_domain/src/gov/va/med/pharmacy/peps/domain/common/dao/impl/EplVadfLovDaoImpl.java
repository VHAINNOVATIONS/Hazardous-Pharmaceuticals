/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplVadfLovDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfLovDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplVadfLovDao.
 */
public class EplVadfLovDaoImpl extends DataAccessObjectImpl<EplVadfLovDo, Long> implements EplVadfLovDao {

    /**
     * Inherited from the GenericDAO interface. Finds an all EplVadfLovDo object in ascending order from the database.
     * 
     * @return Collection of VaDf objects
     */
    @Override
    public List<EplVadfLovDo> retrieve() {
        return retrieveAscending(EplVadfLovDo.DEFAULT_VALUE);
    }
}
