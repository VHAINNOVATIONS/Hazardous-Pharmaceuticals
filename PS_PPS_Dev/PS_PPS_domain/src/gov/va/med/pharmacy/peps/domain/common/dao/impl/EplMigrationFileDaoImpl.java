/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.domain.common.dao.EplMigrationFileDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplMigrationFileDo;


/**
 * EplMigrationFileDaoImpl
 */
public class EplMigrationFileDaoImpl extends DataAccessObjectImpl<EplMigrationFileDo, Long> implements EplMigrationFileDao {

    /**
     * Inherited from the MigrationFileDAO interface. Finds an all EplFileDo object in ascending order from the database.
     * 
     * @return Collection of EplFileDo objects
     */
    @Override
    public List<EplMigrationFileDo> retrieve() {
        return retrieveAscending(EplMigrationFileDo.FILE_ID);
    }
}
