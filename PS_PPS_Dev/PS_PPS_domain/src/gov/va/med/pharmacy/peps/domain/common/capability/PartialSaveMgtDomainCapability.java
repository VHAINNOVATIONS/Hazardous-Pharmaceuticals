/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform CRUD operations on PartialSaveMgmts
 */
public interface PartialSaveMgtDomainCapability {

    /**
     * Insert the given PartialSaveMgmtVo.
     * 
     * @param partialSaveMgmt PartialSaveMgmtVo
     * @param user {@link UserVo} performing the action
     * @return PartialSaveMgmtVo inserted PartialSaveMgmtVo with new ID
     */
    PartialSaveVo create(PartialSaveVo partialSaveMgmt, UserVo user);

    /**
     * Retrieves all PartialSaveVo
     * 
     * @return Collection of PartialSaveVo
     */
    List<PartialSaveVo> retrieveAll();

    /**
     * Retrieves all PartialSaveVo for the given {@link UserVo}.
     * 
     * @param user UserVo for which to retrieve partially saved items
     * @return Collection of PartialSaveVo
     */
    List<PartialSaveVo> retrieveAll(UserVo user);

    /**
     * Retrieve a partially saved item.
     * 
     * @param id String ID of the PartialSaveVo to retrieve
     * @return PartialSaveVo for the given ID
     * @throws ItemNotFoundException if cannot find the PartialSaveVo for the given ID
     */
    PartialSaveVo retrieve(String id) throws ItemNotFoundException;

    /**
     * Deletes row from database
     * 
     * @param id or row to be deleted
     */
    void delete(String id);

    /**
     * Deletes all partial saves for the given {@link ManagedItemVo}
     * 
     * @param item {@link ManagedItemVo} for which to delete partial saves
     */
    void delete(ManagedItemVo item);

}
