/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplLocalMedRouteDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalMedRouteDo;


/**
 * This class makes avaliable the methods that may be used to access and 
 * modify the data referenced by the EplLocalMedRouteDao.
 */
public class EplLocalMedRouteDaoImpl extends DataAccessObjectImpl<EplLocalMedRouteDo, Long> implements EplLocalMedRouteDao {

    /**
     * Finds an all EplLocalMedRouteDo object in ascending order from the database.
     * 
     * @return Collection of EplLocalMedRouteDo objects
     */
    @Override
    public List<EplLocalMedRouteDo> retrieve() {
        return retrieveAscending(EplLocalMedRouteDo.NAME);
    }
}
