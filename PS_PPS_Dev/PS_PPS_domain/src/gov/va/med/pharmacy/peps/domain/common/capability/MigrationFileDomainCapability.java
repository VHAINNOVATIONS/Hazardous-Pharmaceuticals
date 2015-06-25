/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.MigrationFileVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform CRUD operations on manufacturers
 */
public interface MigrationFileDomainCapability {

   /**
    * Creates the migration file
    * 
    * @param migrationFileVo migration file
    * @param user provides user information
    * @return MigrationFileVo migration file returned
    */
    MigrationFileVo create(MigrationFileVo migrationFileVo, UserVo user);
    
    /**
     * Retrieves a list of migration files
     * 
     * @return List<MigrationFileVo> list of migration files returned
     */
    List<MigrationFileVo> retrieveMigrationFile();
    
    /**
     * Updates the migration file
     * 
     * @param migrationFileVo migration file
     * @param user provides user information
     * @return MigrationFileVo migration file returned
     */
    MigrationFileVo update(MigrationFileVo migrationFileVo, UserVo user);
}
