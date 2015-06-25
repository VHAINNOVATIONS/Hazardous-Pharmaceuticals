/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplDrugUnitDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;


/**
 * This class makes avaliable the methods that may be used to access and 
 * modify the data referenced by the EplDrugUnitDao.
 */
public class EplDrugUnitDaoImpl extends DataAccessObjectImpl<EplDrugUnitDo, Long> implements EplDrugUnitDao {

    /**
     * Finds an all EplDrugUnitDo object in ascending order from the database.
     * 
     * @return Collection of EplDrugUnitDo objects
     */
    @Override
    public List<EplDrugUnitDo> retrieve() {
        return retrieveAscending(EplDrugUnitDo.NAME);
    }
}
