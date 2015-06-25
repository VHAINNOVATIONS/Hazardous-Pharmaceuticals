/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplVadfOwnerDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDfDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplVadfOwnerDao.
 */
public class EplVadfOwnerDaoImpl extends DataAccessObjectImpl<EplVadfOwnerDo, Long> implements EplVadfOwnerDao {

    /**
     * Inherited from the GenericDAO interface. Finds an all EplVadfOwnerDo object in ascending order from the database.
     * 
     * @return Collection of EplVadfOwnerDo objects
     */
    @Override
    public List<EplVadfOwnerDo> retrieve() {
        return retrieveAscending(EplVaDfDo.NAME);
    }

}
