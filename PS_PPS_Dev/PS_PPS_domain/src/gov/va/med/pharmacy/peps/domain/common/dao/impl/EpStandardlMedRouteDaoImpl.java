/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplStandardMedRouteDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplStandardMedRouteDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplStandardMedRouteDao.
 */
public class EpStandardlMedRouteDaoImpl extends DataAccessObjectImpl<EplStandardMedRouteDo, Long> implements
    EplStandardMedRouteDao {

    /**
     * Finds an all EplStandardMedRouteDo object in ascending order from the database.
     * 
     * @return Collection of EplStandardMedRouteDo objects
     */
    @Override
    public List<EplStandardMedRouteDo> retrieve() {
        return retrieveAscending(EplStandardMedRouteDo.NAME);
    }
}
