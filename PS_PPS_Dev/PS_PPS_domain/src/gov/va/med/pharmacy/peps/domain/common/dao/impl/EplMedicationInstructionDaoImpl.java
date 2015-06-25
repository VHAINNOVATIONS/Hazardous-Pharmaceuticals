/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplMedicationInstructionDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplMedicationInstructionDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplMedicationInstructionDao.
 */
public class EplMedicationInstructionDaoImpl extends DataAccessObjectImpl<EplMedicationInstructionDo, Long> implements
    EplMedicationInstructionDao {

    /**
     * Finds an all EplMedicationInstructionDo object in ascending order from the database.
     * 
     * @return Collection of EplMedicationInstructionDo objects
     */
    @Override
    public List<EplMedicationInstructionDo> retrieve() {
        return retrieveAscending(EplMedicationInstructionDo.NAME);
    }
}
