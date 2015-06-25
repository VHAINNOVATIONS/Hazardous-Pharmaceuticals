/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplWarnLabelDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplWarnLabelDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the EplWarnLabelDao.
 */
public class EplWarnLabelDaoImpl extends DataAccessObjectImpl<EplWarnLabelDo, Long> implements EplWarnLabelDao {

    /**
     * Inherited from the EplWarnLabelDao interface. Finds an all EplWarnLabelDo object in ascending order from the database.
     * 
     * @return Collection of EplWarnLabelDo objects
     */
    @Override
    public List<EplWarnLabelDo> retrieve() {
        return retrieveAscending(EplWarnLabelDo.RX_CONSULT_NAME);
    }

}
