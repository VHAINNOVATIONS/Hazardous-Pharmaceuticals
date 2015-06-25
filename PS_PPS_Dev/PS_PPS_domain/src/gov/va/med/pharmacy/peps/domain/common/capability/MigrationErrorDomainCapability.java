/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.MigrationErrorVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform CRUD operations on manufacturers
 */
public interface MigrationErrorDomainCapability {

    /**
     * Insert the given MigrationErrorVo.
     * 
     * @param migrationErrorVo value object for migration control
     * @param user {@link UserVo} performing the action
     * @return MigrationErrorVo inserted Vo with new ID
     */
    MigrationErrorVo create(MigrationErrorVo migrationErrorVo, UserVo user);
    
    /**
     * Insert the given MigrationErrorVo.
     * 
     * @return List<MigrationErrorVo> a list of errors created during the migration
     */
    List<MigrationErrorVo> retrieveMigrationError();
}
