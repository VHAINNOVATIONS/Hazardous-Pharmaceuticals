/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplDosageFormDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDosageFormDo;


/**
 * This class makes avaliable the methods that may be used to access and 
 * modify the data referenced by the EplDosageFormDao.
 */
public class EplDosageFormDaoImpl extends DataAccessObjectImpl<EplDosageFormDo, Long> implements EplDosageFormDao {

    /**
     * Finds an all EplDosageFormDo object in ascending order from the database.
     * 
     * @return Collection of EplDosageFormDo objects
     */
    @Override
    public List<EplDosageFormDo> retrieve() {
        return retrieveAscending(EplDosageFormDo.DF_NAME);
    }
}
