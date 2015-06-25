/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.MigrationControlVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationControlDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplMigrationControlDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplMigrationControlDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.MigrationControlConverter;


/**
 * Perform CRUD operations with local consoles (System Information)
 */
public class MigrationControlDomainCapabilityImpl implements
        MigrationControlDomainCapability {
    private EplMigrationControlDao migrationControlDao;
    private MigrationControlConverter migrationControlConverter;

    /**
     * Retrieve all MigrationControlVo.
     * 
     * @return MigrationControlVo
     */
    public MigrationControlVo retrieveMigrationControlInfo() {
        EplMigrationControlDo eplDo;
        MigrationControlVo migrationControlVo = new MigrationControlVo();

        List<EplMigrationControlDo> eplDos = migrationControlDao.retrieve();

        if (eplDos.size() > 0) {
            eplDo = eplDos.get(0);
            migrationControlVo = migrationControlConverter.convert(eplDo);
        }
        
        return migrationControlVo;
    }

    /**
     * set the Local console info
     * 
     * @param dao EplMigrationControlDao
     */
    public void setEplMigrationControlDao(EplMigrationControlDao dao) {
        this.migrationControlDao = dao;
    }

    /**
     * Insert the given MigrationControlVo.
     * 
     * @param migrationControlVo MigrationControlVo     *            
     * @param user {@link UserVo} performing the action
     * @return MigrationControlVo inserted Vo with new ID
     */
    public MigrationControlVo create(MigrationControlVo migrationControlVo, UserVo user) {
        EplMigrationControlDo migrationControlDo = migrationControlConverter.convert(migrationControlVo);
        EplMigrationControlDo returnedMigrationControlDo = migrationControlDao.insert(migrationControlDo, user);

        return migrationControlConverter.convert(returnedMigrationControlDo);
    }

    /**
     * Update the given MigrationControlVo.
     * 
     * @param migrationControlVo MigrationControlVo
     * @param user {@link UserVo} performing the action
     * @return MigrationControlVo upddated Vo
     */
    public MigrationControlVo update(MigrationControlVo migrationControlVo, UserVo user) {
        EplMigrationControlDo migrationControlDo = migrationControlConverter.convert(migrationControlVo);
        migrationControlDao.update(migrationControlDo, user);

        return migrationControlVo;
    }

    /**
     * Deletes MigrationControlVo.
     */
    public void deleteAll() {
        migrationControlDao.executeQuery("DELETE FROM EplMigrationControlDo");
    }

    /**
     * setMigrationControlConverter
     * @param migrationControlConverter
     *            migrationControlConverter property
     */
    public void setMigrationControlConverter(
            MigrationControlConverter migrationControlConverter) {
        this.migrationControlConverter = migrationControlConverter;
    }
}
