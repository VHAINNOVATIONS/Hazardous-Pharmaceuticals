/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplVaGenericNameDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaGenNameDo;


/**
 * EplVaGenericNameDaoImpl
 */
public class EplVaGenericNameDaoImpl extends DataAccessObjectImpl<EplVaGenNameDo, Long> implements EplVaGenericNameDao {

    /**
     * Inherited from the GenericDAO interface. Finds an all EplVaGenericNameDo object in ascending order from the database.
     * 
     * @return Collection of EplVaGenericNameDo objects
     */
    @Override
    public List<EplVaGenNameDo> retrieve() {
        return retrieveAscending(EplVaGenNameDo.NAME);
    }
}
