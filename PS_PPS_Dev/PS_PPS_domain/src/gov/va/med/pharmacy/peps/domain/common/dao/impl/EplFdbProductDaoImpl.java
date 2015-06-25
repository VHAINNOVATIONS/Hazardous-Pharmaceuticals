/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbProductDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbProductDo;


/**
 * EplFdbProductDaoImpl.
 */
public class EplFdbProductDaoImpl extends DataAccessObjectImpl<EplFdbProductDo, Long> implements EplFdbProductDao {

    /**
     * Inherited from the FdbProductDAO interface. Finds an all EplFileDo object in ascending order from the database.
     * 
     * @return Collection of EplFileDo objects
     */
    @Override
    public List<EplFdbProductDo> retrieve() {
        return retrieveAscending(EplFdbProductDo.PRODUCT_ID_FK);
    }
}
