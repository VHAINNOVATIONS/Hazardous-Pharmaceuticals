/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplSynonymDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplSynonymDo;


/**
 * This class makes avaliable the methods that may be used to access and 
 * modify the data referenced by the EplSynonymDao.
 */
public class EplSynonymDaoImpl extends DataAccessObjectImpl<EplSynonymDo, Long> implements EplSynonymDao {

    /**
     * Finds an all EplSynonymDo object in ascending order from the database.
     * 
     * @return Collection of EplSynonymDo objects
     */
    @Override
    public List<EplSynonymDo> retrieve() {
        return retrieveAscending(EplSynonymDo.SYNONYM_NAME);
    }
}
