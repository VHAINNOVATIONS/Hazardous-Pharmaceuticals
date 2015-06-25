/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplIngredientDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplIngredientDo;


/**
 * This class makes avaliable the methods that may be used to access and 
 * modify the data referenced by the EplIngredientDao.
 */
public class EplIngredientDaoImpl extends DataAccessObjectImpl<EplIngredientDo, Long> implements EplIngredientDao {

    /**
     * Inherited from the GenericDAO interface. Finds an all EplIngredientDo object in ascending order from the database.
     * 
     * @return Collection of EplIngredientDo objects
     */
    @Override
    public List<EplIngredientDo> retrieve() {
        return retrieveAscending(EplIngredientDo.NAME);
    }
}
