/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplManufacturerDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplManufacturerDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplManufacturerDao.
 */
public class EplManufacturerDaoImpl extends DataAccessObjectImpl<EplManufacturerDo, Long> implements EplManufacturerDao {

    /**
     * Finds an all EplManufacturerDo object in ascending order from the database.
     * 
     * @return Collection of EplManufacturerDo objects
     */
    @Override
    public List<EplManufacturerDo> retrieve() {
        return retrieveAscending(EplManufacturerDo.NAME);
    }
}
