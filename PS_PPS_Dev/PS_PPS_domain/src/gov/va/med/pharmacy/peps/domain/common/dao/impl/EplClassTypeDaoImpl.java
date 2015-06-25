/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplClassTypeDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplClassTypeDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the EplClassTypeDao.
 */
public class EplClassTypeDaoImpl extends DataAccessObjectImpl<EplClassTypeDo, Long> implements EplClassTypeDao {

    /**
     * Finds an all EplClassTypeDO object in ascending order from the database.
     * 
     * @return Collection of EplClassTypeDO objects
     */
    @Override
    public List<EplClassTypeDo> retrieve() {
        return retrieveAscending(EplClassTypeDo.CLASS_TYPE);
    }
}
