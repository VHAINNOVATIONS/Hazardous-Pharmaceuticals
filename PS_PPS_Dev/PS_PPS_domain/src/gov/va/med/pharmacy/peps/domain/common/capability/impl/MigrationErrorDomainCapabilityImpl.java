/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.MigrationErrorVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationErrorDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplMigrationErrorDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplMigrationErrorDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.MigrationErrorConverter;


/**
 * Perform CRUD operations with local consoles (System Information)
 */
public class MigrationErrorDomainCapabilityImpl implements MigrationErrorDomainCapability {
    private EplMigrationErrorDao migrationErrorDao;
    private MigrationErrorConverter migrationErrorConverter;

    /**
     * Default Retrieve.  Will return all MigrationErrorVo.
     * 
     * @return List<MigrationErrorVo>
     */
    public List<MigrationErrorVo> retrieveMigrationError() {
        List<EplMigrationErrorDo> eplDos = migrationErrorDao.retrieve();
        List<MigrationErrorVo> migrationErrorVos = migrationErrorConverter.convert(eplDos);

        return migrationErrorVos;
    }

    /**
     * Retrieve File specific MigrationErrorVo.
     * 
     * @param fileNumber String
     * 
     * @return List<MigrationErrorVo>
     */
    public List<MigrationErrorVo> retrieveMigrationError(String fileNumber) {
        List<EplMigrationErrorDo> eplDos = migrationErrorDao.retrieveAscending(fileNumber);
        List<MigrationErrorVo> migrationErrorVos = migrationErrorConverter.convert(eplDos);

        return migrationErrorVos;
    }

    /**
     * Retrieve File specific MigrationErrorVo.
     * @param fileNumber fileNumber
     * @param beginInx begin
     * @param numRecords number of Records
     *  
     * @return List<MigrationErrorVo>
     */
    public List<MigrationErrorVo> retrieveMigrationError(String fileNumber, int beginInx, int numRecords) {
        List<EplMigrationErrorDo> eplDos = migrationErrorDao.retrieveAscending(fileNumber, beginInx, numRecords);
        List<MigrationErrorVo> migrationErrorVos = migrationErrorConverter.convert(eplDos);

        return migrationErrorVos;
    }

    /**
     * set the Migration Error info
     * 
     * @param dao EplMigrationErrorDao
     */
    public void setEplMigrationErrorDao(EplMigrationErrorDao dao) {
        this.migrationErrorDao = dao;
    }

    /**
     * Insert the given MigrationErrorVo.
     * 
     * @param migrationErrorVo MigrationErrorVo 
     * @param user {@link UserVo} performing the action
     * @return MigrationErrorVo inserted Vo with new ID
     */
    public MigrationErrorVo create(MigrationErrorVo migrationErrorVo, UserVo user) {
        EplMigrationErrorDo migrationErrorDo = migrationErrorConverter.convert(migrationErrorVo);
        EplMigrationErrorDo returnedMigrationErrorDo = migrationErrorDao.insert(migrationErrorDo, user);

        return migrationErrorConverter.convert(returnedMigrationErrorDo);
    }

    /**
     * Update the given MigrationErrorVo.
     * 
     * @param migrationErrorVo MigrationErrorVo 
     * @param user {@link UserVo} performing the action
     * @return MigrationErrorVo upddated Vo
     */
    public MigrationErrorVo update(MigrationErrorVo migrationErrorVo, UserVo user) {
        EplMigrationErrorDo migrationErrorDo = migrationErrorConverter.convert(migrationErrorVo);
        migrationErrorDao.update(migrationErrorDo, user);

        return migrationErrorVo;
    }

    /**
     * Deletes MigrationErrorVo.
     */
    public void deleteAll() {
        migrationErrorDao.executeQuery("DELETE FROM EplMigrationErrorDo");
    }

    /**
     * setMigrationErrorConverter
     * @param migrationErrorConverter migrationErrorConverter property
     */
    public void setMigrationErrorConverter(MigrationErrorConverter migrationErrorConverter) {
        this.migrationErrorConverter = migrationErrorConverter;
    }
}
