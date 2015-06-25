/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplDoseUnitDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDoseUnitDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the EplDoseUnitao.
 */
public class EplDoseUnitDaoImpl extends DataAccessObjectImpl<EplDoseUnitDo, Long> implements EplDoseUnitDao {

    /**
     * Finds an all EplDoseUnitDo object in ascending order from the database.
     * 
     * @return Collection of EplDoseUnitDo objects
     */
    @Override
    public List<EplDoseUnitDo> retrieve() {
        return retrieveAscending(EplDoseUnitDo.NAME);
    }
}
