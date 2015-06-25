/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbAutoAddDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbAutoAddDo;


/**
 * EplFdbAutoAddDaoImpl's brief summary
 * 
 * Details of EplFdbAutoAddDaoImpl's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class EplFdbAutoAddDaoImpl extends DataAccessObjectImpl<EplFdbAutoAddDo, Long> implements EplFdbAutoAddDao {

    /**
     * Inherited from the AutoAddDAO interface. Finds an all EplFileDo object in ascending order from the database.
     * 
     * @return Collection of EplFileDo objects
     */
    @Override
    public List<EplFdbAutoAddDo> retrieve() {
        return retrieveAscending(EplFdbAutoAddDo.NDC);
    }
}
