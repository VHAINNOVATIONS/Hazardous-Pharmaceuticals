/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplVaDrugClassDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDrugClassDo;


/**
 * This class makes avaliable the methods that may be used to access and modify the data referenced by the
 * EplVaDrugClassDao.
 */
public class EplVaDrugClassDaoImpl extends DataAccessObjectImpl<EplVaDrugClassDo, Long> implements
    EplVaDrugClassDao {

    /**
     * Inherited from the GenericDAO interface. Finds an all EplVaDrugClassDo object in ascending order by description.
     * 
     * @return Collection of EplVaDrugClassDo objects
     */
    @Override
    public List<EplVaDrugClassDo> retrieve() {
        return retrieveAscending(EplVaDrugClassDo.CLASSIFICATION_NAME);
    }
}
