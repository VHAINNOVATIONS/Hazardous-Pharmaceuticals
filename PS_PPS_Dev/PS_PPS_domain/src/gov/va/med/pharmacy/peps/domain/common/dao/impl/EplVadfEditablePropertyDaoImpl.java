/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplVadfEditablePropertyDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfEditablePropertyDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplVadfEditablePropertyDao.
 */
public class EplVadfEditablePropertyDaoImpl extends DataAccessObjectImpl<EplVadfEditablePropertyDo, Long> implements
    EplVadfEditablePropertyDao {

    /**
     * Inherited from the GenericDAO interface. Finds an all EplVadfEditablePropertyDo object in ascending order from the
     * database.
     * 
     * @return Collection of VaDf objects
     */
    @Override
    public List<EplVadfEditablePropertyDo> retrieve() {
        return retrieveAscending(EplVadfEditablePropertyDo.EDITABLE_YN);
    }
}
