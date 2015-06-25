/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplPackageTypeDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplPackageTypeDo;


/**
 * This class makes avaliable the methods that may be used to access and 
 * modify the data referenced by the EplPackageTypeDao.
 */
public class EplPackageTypeDaoImpl extends DataAccessObjectImpl<EplPackageTypeDo, Long> implements EplPackageTypeDao {

    /**
     * Finds an all EplPackageTypeDo object in ascending order from the database.
     * 
     * @return Collection of EplPackageTypeDo objects
     */
    @Override
    public List<EplPackageTypeDo> retrieve() {
        return retrieveAscending(EplPackageTypeDo.NAME);
    }
}
