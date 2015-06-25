/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.LocalConsoleInfoVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform CRUD operations with local consoles info NOTE: This is only used at the National site for the National Status
 * Console NOTE: This uses a delete function!
 */
public interface ConsoleDomainCapability {

    /**
     * Retrieve all LocalConsoleInfoVo.
     * 
     * @return List<LocalConsoleInfoVo>
     */
    List<LocalConsoleInfoVo> retrieveLocalConsoleInfo();

    /**
     * Insert the given LocalConsoleInfoVo.
     * 
     * @param localConsoleInfoVo LocalConsoleInfoVo
     * @param user {@link UserVo} performing the action
     * @return LocalConsoleInfoVo inserted Vo with new ID
     */
    LocalConsoleInfoVo create(LocalConsoleInfoVo localConsoleInfoVo, UserVo user);

    /**
     * Update the given LocalConsoleInfoVo.
     * 
     * @param localConsoleInfoVo LocalConsoleInfoVo
     * @param user {@link UserVo} performing the action
     * @return LocalConsoleInfoVo updatedVo
     */
    LocalConsoleInfoVo update(LocalConsoleInfoVo localConsoleInfoVo, UserVo user);

    /**
     * Deletes LocalConsoleInfoVo.
     */
    void deleteAll();

}
