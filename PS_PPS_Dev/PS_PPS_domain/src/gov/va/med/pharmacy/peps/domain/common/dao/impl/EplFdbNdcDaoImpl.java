/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbNdcDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbNdcDo;


/**
 * EplFdbNdcDaoImpl.
 */
public class EplFdbNdcDaoImpl extends DataAccessObjectImpl<EplFdbNdcDo, Long> implements EplFdbNdcDao {

    /**
     * Inherited from the FdbNdcDAO interface. Finds an all EplFileDo object in ascending order from the database.
     * 
     * @return Collection of EplFileDo objects
     */
    @Override
    public List<EplFdbNdcDo> retrieve() {
        return retrieveAscending(EplFdbNdcDo.NDC_ID_FK);
    }
}
