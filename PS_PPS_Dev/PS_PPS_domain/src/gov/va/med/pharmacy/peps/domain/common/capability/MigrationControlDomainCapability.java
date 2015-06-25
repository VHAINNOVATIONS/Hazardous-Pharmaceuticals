/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.MigrationControlVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;




/**
 * Perform CRUD operations on manufacturers
 */
public interface MigrationControlDomainCapability {

    /**
     * Insert the given MigrationControlVo.
     * 
     * @param migrationControlVo migration control value object
     * @param user {@link UserVo} performing the action
     * @return MigrationControlVo inserted Vo with new ID
     */
    MigrationControlVo create(MigrationControlVo migrationControlVo, UserVo user);
    
    /**
     * Retrieve all MigrationControlVo.
     * 
     * @return List<MigrationControlVo>
     */
    MigrationControlVo retrieveMigrationControlInfo();

    /**
     * Update the given MigrationControlVo.
     * 
     * @param migrationControlVo value object for migration
     * @param user {@link UserVo} performing the action
     * @return MigrationControlVo upddated Vo
     */
    MigrationControlVo update(MigrationControlVo migrationControlVo, UserVo user);
    
    /**
     * Deletes MigrationControlVo.
     */
    void deleteAll();
}
