/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplVaDispenseUnitDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDispenseUnitDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplVaDispenseUnitDao.
 */
public class EplVaDispenseUnitDaoImpl extends DataAccessObjectImpl<EplVaDispenseUnitDo, Long> implements
    EplVaDispenseUnitDao {

    /**
     * Inherited from the GenericDAO interface. Finds an all EplVaDispenseUnitDo object in ascending order from the database.
     * 
     * @return Collection of EplVaDispenseUnitDo objects
     */
    @Override
    public List<EplVaDispenseUnitDo> retrieve() {
        return retrieveAscending(EplVaDispenseUnitDo.DISPENSE_UNIT_NAME);
    }
}
