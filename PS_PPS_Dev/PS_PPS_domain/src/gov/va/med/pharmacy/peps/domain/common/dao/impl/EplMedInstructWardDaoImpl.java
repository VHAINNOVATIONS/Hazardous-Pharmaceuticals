/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplMedInstructWardDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplMedInstructWardDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplMedInstructWardDao.
 */
public class EplMedInstructWardDaoImpl extends DataAccessObjectImpl<EplMedInstructWardDo, Long> implements
    EplMedInstructWardDao {

    /**
     * Finds an all EplMedInstructWardDo object in ascending order from the database.
     * 
     * @return Collection of EplMedInstructWardDo objects
     */
    @Override
    public List<EplMedInstructWardDo> retrieve() {
        return retrieveAscending(EplMedInstructWardDo.NAME);
    }
}
