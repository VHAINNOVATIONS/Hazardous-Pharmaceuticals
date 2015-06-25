/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplDrugTextDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugTextDo;


/**
 * This class makes avaliable the methods that may be used to access and 
 * modify the data referenced by the EplDrugTextDao.
 */
public class EplDrugTextDaoImpl extends DataAccessObjectImpl<EplDrugTextDo, Long> implements EplDrugTextDao {

    /**
     * Finds an all EplDoseUnitDo object in ascending order from the database.
     * 
     * @return Collection of EplDoseUnitDo objects
     */
    @Override
    public List<EplDrugTextDo> retrieve() {
        return retrieveAscending(EplDrugTextDo.DRUG_TEXT_NAME);
    }
}
