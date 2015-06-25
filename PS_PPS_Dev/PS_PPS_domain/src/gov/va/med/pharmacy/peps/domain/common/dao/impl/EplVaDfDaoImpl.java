/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


 import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplVaDfDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDfDo;


/**
 * This class makes avaliable the methods that may be used to access and 
 * modify the data referenced by the EplVaDfDao.
 */
public class EplVaDfDaoImpl extends DataAccessObjectImpl<EplVaDfDo, Long> implements EplVaDfDao {

    /**
     * Inherited from the GenericDAO interface. Finds an all VaDfDo object in ascending order from the database.
     * 
     * @return Collection of VaDf objects
     */
    @Override
    public List<EplVaDfDo> retrieve() {
        return retrieveAscending(EplVaDfDo.NAME);
    }
}
