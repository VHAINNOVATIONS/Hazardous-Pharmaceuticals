/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.MigrationFileVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationFileDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplMigrationFileDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplMigrationFileDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.MigrationFileConverter;


/**
 * Perform CRUD operations with local consoles (System Information)
 */
public class MigrationFileDomainCapabilityImpl implements MigrationFileDomainCapability {
    private EplMigrationFileDao migrationFileDao;
    private MigrationFileConverter migrationFileConverter;

    /**
     * retrieveMigrationFile
     * @return list of MigrationFileVo
     */
    public List<MigrationFileVo> retrieveMigrationFile() {
        List<EplMigrationFileDo> eplDos = migrationFileDao.retrieve();
        List<MigrationFileVo> migrationFileVos = migrationFileConverter.convert(eplDos);

        return migrationFileVos;
    }

    /**
     * create
     * @param migrationFileVo migrationFileVo
     * @param user user
     * @return MigrationFileVo
     */
    public MigrationFileVo create(MigrationFileVo migrationFileVo, UserVo user) {
        EplMigrationFileDo migrationFileDo = migrationFileConverter.convert(migrationFileVo);
        EplMigrationFileDo returnedMigrationFileDo = migrationFileDao.insert(migrationFileDo, user);

        return migrationFileConverter.convert(returnedMigrationFileDo);
    }
    
    
    /**
     * set the setEplMigrationFileDao
     * 
     * @param migrationFileDaoIn EplMigrationFileDao
     */
    public void setEplMigrationFileDao(EplMigrationFileDao migrationFileDaoIn) {
        this.migrationFileDao = migrationFileDaoIn;
    }

    /**
     * Update the given MigrationFileVo.
     * 
     * @param migrationFileVo migrationFileVo
     * @param user {@link UserVo} performing the action
     * @return MigrationFileVo upddated Vo
     */
    public MigrationFileVo update(MigrationFileVo migrationFileVo, UserVo user) {
        EplMigrationFileDo migrationFileDo = migrationFileConverter.convert(migrationFileVo);
        migrationFileDao.update(migrationFileDo, user);

        return migrationFileVo;
    }

    /**
     * Deletes MigrationFileVo.
     */
    public void deleteAll() {
        migrationFileDao.executeQuery("DELETE FROM EplLocalConsoleInfoDo");
    }

    /**
     * setMigrationFileConverter
     * @param migrationFileConverter migrationFileConverter property
     */
    public void setMigrationFileConverter(MigrationFileConverter migrationFileConverter) {
        this.migrationFileConverter = migrationFileConverter;
    }
}
