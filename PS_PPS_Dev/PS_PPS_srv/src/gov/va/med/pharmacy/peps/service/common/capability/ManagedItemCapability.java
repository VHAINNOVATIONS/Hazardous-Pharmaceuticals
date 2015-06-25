/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ItemModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.MergeVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ModificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.PaginatedList;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedRequestVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.external.common.callback.ManagedItemCapabilityCallback;


/**
 * Perform operations concerning the ManagedItemVo
 */
public interface ManagedItemCapability extends ManagedItemCapabilityCallback {

    /**
     * The given user approves the given request for the given item.
     * For the ManagedItemCapability file.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to approve
     * @param modDifferences Collection of {@link ModDifferenceVo}
     * @param user UserVo rejecting request
     * @return RequestUpdateInformation Updated item and request information
     * 
     * @throws ValidationException if data does not validate
     */
    ProcessedRequestVo approveRequest(ManagedItemVo item, RequestVo request, Collection<ModDifferenceVo> modDifferences,
        UserVo user) throws ValidationException;

    /**
     * Commit the modifications to the ManagedItemVo and the ManagedItemVo itself.
     * 
     * @param modDifferences differences between the old and new ManagedItemVo
     * @param item original ManagedItemVo without changes detailed in modDifferences parameter
     * @param user UserVo performing commit
     * @return ProcessedItemVo with updated ManagedItemVo and warnings
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    ProcessedItemVo commitModifications(Collection<ModDifferenceVo> modDifferences, ManagedItemVo item, UserVo user)
        throws ValidationException;

    /**
     * Rescinds the previous rejection and puts the item back into a PENDING state.
     * 
     * @param item original ManagedItemVo without changes detailed in modDifferences parameter
     * @param user UserVo performing commit
     * @throws ValidationException if error validating data in ManagedItemVo
     * 
     * @return ProcessedItemVo
     */
    ProcessedItemVo commitRescindRejection(ManagedItemVo item, UserVo user) throws ValidationException;

    /**
     * Compare the old and updated ManagedItemVo for differences and check if there are any warnings.
     * 
     * @param oldItem original {@link ManagedItemVo}
     * @param updatedItem updated {@link ManagedItemVo}
     * @param user {@link UserVo} updating {@link ManagedItemVo}
     * @return {@link ModificationSummaryVo} with Collection of {@link ModDifferenceVo} and {@link Errors} as warnings
     * @throws ValidationException If an exception is thrown during the validation of the object. 
     */
    ModificationSummaryVo submitModifications(ManagedItemVo oldItem, ManagedItemVo updatedItem, UserVo user)
        throws ValidationException;

    /**
     * Applies the mod difference to all of the items in the list. Also retrieves the fully populated version of the times
     * 
     * @param modDifferences ModDifferenceVo
     * @param items items to apply the change to
     * @param user userVo
     * @throws ItemNotFoundException e
     * @return Collection<ItemModDifferenceVo>
     */
    Collection<ItemModDifferenceVo> submitAllModifications(Collection<ModDifferenceVo> modDifferences,
        Collection<ManagedItemVo> items, UserVo user) throws ItemNotFoundException;
    
    /**
     * Applies the grouplistdatafield mod difference to all of the items in the list. 
     * 
     * @param modDifferences ModDifferenceVo
     * @param items items to apply the change to
     * @param user userVo
     * @throws ItemNotFoundException e
     * @return Collection<ItemModDifferenceVo>
     */
    Collection<ItemModDifferenceVo> submitGroupModifications(Collection<ModDifferenceVo> modDifferences,
        Collection<ManagedItemVo> items, UserVo user) throws ItemNotFoundException;

    /**
     * Commit all the modifications to the given items for this File.
     * 
     * @param itemModDifferences List<ItemModDifferenceVo> differences between the old and new {@link ManagedItemVo}
     * @param user UserVo performing commit
     * @return updated ManagedItemVos
     * @throws ValidationException if error validating data in ManagedItemVo while validating.
     * @throws OptimisticLockingException if revision ID from ManagedItemVo in database is different
     */
    Collection<ProcessedItemVo> commitAllModifications(Collection<ItemModDifferenceVo> itemModDifferences, UserVo user)
        throws ValidationException, OptimisticLockingException;

    /**
     * Commit the request, modifications to the ManagedItemVo, and the ManagedItemVo itself.
     * 
     * @param oldItem original ManagedItemVo, without any modifications from the {@link RequestVo} or from the given
     *            Collection of {@link ModDifferenceVo}
     * @param request RequestVo to commit
     * @param differences Collection of {@link ModDifferenceVo} between the old and new ManagedItemVo
     * @param user UserVo performing commit
     * @param ignoreUserRule if true the system will approve the request even if the user making the request to apprve
     *          was the same user who did the last action on the request.
     * @return {@link ProcessedRequestVo} Updated item and request information
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    ProcessedRequestVo commitRequest(ManagedItemVo oldItem, RequestVo request, Collection<ModDifferenceVo> differences,
        UserVo user, boolean ignoreUserRule) throws ValidationException;

    /**
     * Submit the request, modifications to the ManagedItemVo, and the ManagedItemVo itself.
     * <p>
     * Enforce the business rules via {@link #enforceBusinessRules(ManagedItemVo, UserVo)} and check the {@link RequestVo}
     * and differences Collection for conflicts. Does not commit the {@link RequestVo} to the database. Finally, set the last
     * reviewer as the given {@link UserVo}.
     * <p>
     * Does not commit changes to the database!
     * 
     * @param item ManagedItemVo to begin commit steps with, with the user's latest updates but not the accepted reqs.
     * @param request RequestVo to commit to the database
     * @param differences Collection of {@link ModDifferenceVo} between the old and new ManagedItemVo
     * @param user UserVo performing commit
     * @return RequestUpdateInformation Updated item and request information
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    ProcessedRequestVo submitRequest(ManagedItemVo item, RequestVo request, Collection<ModDifferenceVo> differences,
        UserVo user) throws ValidationException;

    /**
     * Apply the changes, in memory, specified in the given {@link RequestVo#getRequestDetails()}.
     * <p>
     * <strong>This method does not commit the changes to the database for submitted requests!</strong>
     * 
     * @param oldItem original {@link ManagedItemVo} (from the database, without changes) on which to apply the changes
     * @param request {@link RequestVo} making the changes, including all of the recently accepted {@link ModDifferenceVo}
     * @param user {@link UserVo} making the request to apply the changes
     * @return Collection of {@link ItemAuditHistoryVo}
     */
    ManagedItemVo applyChanges(ManagedItemVo oldItem, RequestVo request, UserVo user);

    /**
     * Creates a new instance of the ManagedItemVo for the item being created. 
     * Return the same ManagedItemVo with its new ID.
     * 
     * @param item ManagedItemVo to create
     * @param user UserVo performing create
     * @return created ManagedItemVo
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    @Override
    ProcessedItemVo create(ManagedItemVo item, UserVo user) throws ValidationException;

    /**
     * Delete the partially saved ManagedItemVo because it is no longer needed.
     * 
     * @param partialId of type String
     * @param itemType Type of partial item to retrieve
     * @return deleted partial ManagedItemVo
     * @throws ItemNotFoundException if cannot find the PartialSaveVo for the given ID
     */
    ManagedItemVo deletePartial(String partialId, EntityType itemType) throws ItemNotFoundException;

    /**
     * Deletes an item from the database
     * 
     * @param item The item to delete
     * @throws ValidationException validationExcpetion
     */
    void deleteItem(ManagedItemVo item) throws ValidationException;

    /**
     * Swaps the children between two ManagedItemVo.
     * 
     * @param child the child to update
     * @param parent the parent of the child
     * @param user the user doing the swap
     * @throws ValidationException exception
     * @return ProcessedItemVo 
     */
    ProcessedItemVo updateParentChildRelationships(ManagedItemVo child, ManagedItemVo parent, UserVo user)
        throws ValidationException;

    /**
     * The given user marks the given request under review for the given item.
     * 
     * @param item ManagedItemVo that the user modified to make the request.
     * @param request RequestVo to mark under review
     * @param user UserVo rejecting request
     * @return updated RequestVo
     */
    RequestVo markRequestUnderReview(ManagedItemVo item, RequestVo request, UserVo user);

    /**
     * The given user marks the given request that was created as needing a PEPS second review for the given item.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to mark under review
     * @param user UserVo rejecting request
     * @return updated RequestVo
     */
    RequestVo markRequestForPsr(ManagedItemVo item, RequestVo request, UserVo user);

    /**
     * Reject a problem report.
     * 
     * @param item ManagedItemVo
     * @param request RequestVo to reject
     * @param user UserVo
     * @return rejected RequestVo
     */
    RequestVo rejectProblemReport(ManagedItemVo item, RequestVo request, UserVo user);

    /**
     * The given user has rejected the given request for the given item.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to reject
     * @param newDifferences Collection<ModDifferenceVo> Newly-generated field-modifications.
     * @param user UserVo rejecting request
     * @return rejected RequestVo
     */
    RequestVo rejectRequest(ManagedItemVo item, RequestVo request, Collection<ModDifferenceVo> newDifferences, UserVo user);

    /**
     * Get all ManagedDataVo of the given Entitytype. Only applies to managed domains. This will prevent returning a
     * large list of all orderable items, products, or NDCs.
     * 
     * @param itemType {@link EntityType}
     * @return full list of the given domain
     */
    List<ManagedItemVo> retrieve(EntityType itemType);

    /**
     * Retrieve the ManagedItemVo with the given ID for ManqgedItemCapability
     * 
     * @param itemId String ID of the item to retrieve
     * @param itemType Type of item to retrieve
     * @return ManagedItemVo this is the ManagedItemVo to reutrn
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     */
    ManagedItemVo retrieve(String itemId, EntityType itemType) throws ItemNotFoundException;

    /**
     * Retrieve a minimally populated ManagedItemVo with the given ID.
     * <p>
     * The returned ManagedItemVo likely only has enough data for the toShortString() and
     * {@link ManagedItemVo#getId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the {@link ManagedItemVo} in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * 
     * @param itemId String ID of the item to retrieve
     * @param itemType Type of item to retrieve
     * @return minimally populated ManagedItemVo with just partial data
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     */
    ManagedItemVo retrieveMinimal(String itemId, EntityType itemType) throws ItemNotFoundException;

    /**
     * Retrieve an blank template instance of the child to the current ManagedItemVo.
     * 
     * @param parentId ManagedItemVo This is the ID set as the parent of the blank template
     * @param parentType This is the EntityType of parent
     * @return  This is the blank template instance of ManagedItemVo
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    ManagedItemVo retrieveBlankChildTemplate(String parentId, EntityType parentType) throws ItemNotFoundException;

    /**
     * Retrieve an blank template instance of the local child to the current national ManagedItemVo. Only applies for
     * ManagedItemVo types that have a national/local relationship, which currently is only OrderableItemVo.
     * 
     * @param parentId ManagedItemVo ID set as the parent of the blank template
     * @param parentType EntityType of parent
     * @return blank template instance of ManagedItemVo
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    ManagedItemVo retrieveBlankLocalTemplate(String parentId, EntityType parentType) throws ItemNotFoundException;

    /**
     * Retrieve an blank template instance of the current ManagedItemVo type.
     * 
     * @param itemType Type of item template to retrieve
     * @return blank template instance of ManagedItemVo
     */
    @Override
    ManagedItemVo retrieveBlankTemplate(EntityType itemType);

    /**
     * Retrieve a list of items for the given parent Item Id
     * 
     * @param <T> type of ManagedItemVo
     * @param parentItemId String ID of parent for which to retrieve children items
     * @param parentItemType EntityType of parent to for which retrieve children items
     * 
     * @return List<ManagedItemVo> child items
     */
    <T extends ManagedItemVo> List<T> retrieveChildren(String parentItemId, EntityType parentItemType);

    /**
     * Retrieve an local template instance of the current national ManagedItemVo. Only applies for ManagedItemVo types that
     * have a national/local relationship, which currently is only OrderableItemVo.
     * 
     * @param parentId ManagedItemVo ID set as the parent of the blank template
     * @param parentType EntityType of parent
     * @return template instance of ManagedItemVo
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    ManagedItemVo retrieveLocalTemplate(String parentId, EntityType parentType) throws ItemNotFoundException;

    /**
     * Retrieve the partially saved ManagedItemVo. Delete the PartialSaveVo from the database.
     * 
     * @param partialId String
     * @param itemType Type of partial item to retrieve
     * @return partially saved ManagedItemVo
     * @throws ItemNotFoundException if cannot find the PartialSaveVo for the given ID
     */
    ManagedItemVo retrievePartialItem(String partialId, EntityType itemType) throws ItemNotFoundException;

    /**
     * Retrieve all partially saved {@link ManagedItemVo}.
     * 
     * @return List<PartialSaveVo> partially saved managed items
     */
    List<PartialSaveVo> retrievePartialSaves();

    /**
     * Retrieve all partially saved {@link ManagedItemVo} for the given user.
     * 
     * @param user UserVo for which to retrieve partially saved work
     * @return List<PartialSaveVo> partially saved managed items
     */
    List<PartialSaveVo> retrievePartialSaves(UserVo user);

    /**
     * Retrieve the partially saved ManagedItemVo via the PartialSaveVo. 
     * Do not delete the PartialSaveVo from the database in this method.
     * 
     * @param partialId String
     * @param itemType Type of partial item to retrieve
     * @return PartialSaveVo
     * @throws ItemNotFoundException if cannot find the PartialSaveVo for the given ID
     */
    PartialSaveVo retrievePartialSave(String partialId, EntityType itemType) throws ItemNotFoundException;

    /**
     * Creates a new ManagedItemVo template with the appropriate parent.
     * 
     * @param itemId String ID of the item to retrieve to use as the template.
     * @param itemType Type of item to retrieve as a template
     * @return ManagedItemVo template
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    ManagedItemVo retrieveTemplate(String itemId, EntityType itemType) throws ItemNotFoundException;

    /**
     * Save the partially entered ManagedItemVo.
     * 
     * @param item ManagedItemVo
     * @param comment String
     * @param user UserVo
     * @return partially saved ManagedItmVo
     */
    ManagedItemVo savePartial(ManagedItemVo item, String comment, UserVo user);

    /**
    * Performs the actions to create an Item Audit History record for each audit record using the notification to group the
    * records together for future retrieval.
    * 
    * @param itemAudit IAH record to save
    * @param user {@link UserVo} performing the action
    */
    void saveItemAuditHistoryRecords(ItemAuditHistoryVo itemAudit, UserVo user);

    /**
     * Save a problem report.
     * 
     * @param item ManagedItemVo
     * @param comment String
     * @param user UserVo
     */
    void submitProblemReport(ManagedItemVo item, String comment, UserVo user);

    /**
     * Search for the appropriate managed items based on the searchCriteria.
     * 
     * @param searchCriteria criteria for the search
     * @return List<ManagedItemVo> Managed items matching the given search criteria
     * @throws ValidationException if the given {@link SearchCriteriaVo} is invalid
     */
    @Override
    List<ManagedItemVo> search(SearchCriteriaVo searchCriteria) throws ValidationException;
    
    /**
     * Search for {@link ManagedItemVo} using the given {@link SearchCriteriaVo} as search criteria.
     * Returns full list of items.
     * 
     * @param searchCriteria criteria for the search
     * @return List<ManagedItemVo> Managed items matching the given search criteria
     * @throws ValidationException if the given {@link SearchCriteriaVo} is invalid
     */
    List<ManagedItemVo> searchFullList(SearchCriteriaVo searchCriteria) throws ValidationException;

    /**
     * Retrieve the parent with the given itemId and itemType, then set the current item's (the child) parent.
     * 
     * @param child Child ManagedItemVo
     * @param parentId ManagedItemVo ID for the parent of the child.
     * @param parentType ManagedItemVo EntityType for the parent
     * @return child ManagedItemVo with parent set
     * @throws ItemNotFoundException if cannot find parent with given ID and EntityType
     */
    ManagedItemVo selectParent(ManagedItemVo child, String parentId, EntityType parentType) throws ItemNotFoundException;

    /**
     * Save an updated ManagedItemVo.
     * 
     * @param managedItem to be updated
     * @param user UserVo
     * @return ManagedItemVo 
     * @throws ValidationException 
     */
    @Override
    ManagedItemVo update(ManagedItemVo managedItem, UserVo user) throws ValidationException;

    /**
     * Calculates the differences in field's values between user object and database object
     * 
     * @param item user object
     * @param differences a collection of differences
     * @param user UserVo
     * @return value object with differences and revision number
     * @throws ItemNotFoundException when product object not found in database
     */
    MergeVo computeMergeInformation(ManagedItemVo item, Collection<ModDifferenceVo> differences, UserVo user)
        throws ItemNotFoundException;

    
    /**
     * getPendingNonAffliliatedItems
     * @param item item
     * @param user user
     * @return a list of ManagedItemVos
     */
    List<ManagedItemVo> getPendingNonAffliliatedItems(ManagedItemVo item, UserVo user);
    
    
    /**
     * Commit changes into database.
     * 
     * @param mergeItem value object with merged differences
     * @param item product value object
     * @param user account
     * @return ManaagedItemVo
     * @throws ValidationException 
     * @throws OptimisticLockingException if revision ID from ManagedItemVo in database is different
     */
    ManagedItemVo commitMergeModifications(MergeVo mergeItem, ManagedItemVo item, UserVo user) throws ValidationException,
        OptimisticLockingException;

    /**
     * Generate the VistA OI Name and OI name from the selected VA Generic name and dosage
     * 
     * @param item VOs with user changes
     * @return SUCCESS
     */
    ManagedItemVo generateOINames(ManagedItemVo item);

    /**
     * Insert the ManagedItemVo into the database.
     * 
     * @param managedItem ManagedItemVo to insert
     * @param user UserVo inserting item
     * @return inserted item
     * 
     * @throws DuplicateItemException if this item is a duplicate of another according to its rules
     */
    ManagedItemVo insertItem(ManagedItemVo managedItem, UserVo user) throws DuplicateItemException;

    /**
     * Retrieve the ManaedItemVo with the given ID.
     * <p>
     * Use the {@link UserVo#getTableSizePreference()} to determine the page size for the children, using the default sort
     * order. Only the first {@link UserVo#getTableSizePreference()} children will be populated! Additional children must be
     * retrieved by calling {@link #retrieveChildren(String, EntityType, int, FieldKey, SortOrder, int)}
     * 
     * @param itemId String ID of the item to retrieve.  This is the EterpriseProduct (EPL) Id.
     * @param itemType Type of item to retrieve
     * @param user {@link UserVo} from which to retrieve the page size
     * @return ManagedItemVo
     * 
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     */
    ManagedItemVo retrieve(String itemId, EntityType itemType, UserVo user) throws ItemNotFoundException;

    /**
     * Retrieve a list of items for the given parent Item Id.
     * <p>
     * The returned PaginatedList contains a partial list of all the children for the given parent. The first row returned is
     * startRow with pageSize total rows returned. The list will be sorted by sortedFieldKey in sortOrder to limit the
     * potential for different result sets each call.
     * 
     * @param parentItemId String ID of parent for which to retrieve children items
     * @param parentItemType EntityType of parent for which to retrieve children items
     * @param sortedFieldKey FieldKey representing field to sort by
     * @param sortOrder SortOrder representing ascending or descending sort
     * @param startRow integer index in the full list of child items from which to start retrieving
     * @param pageSize integer number of rows to retrieve
     * 
     * @return PaginatedList<ManagedItemVo> child items
     */
    PaginatedList<ManagedItemVo> retrieveChildren(String parentItemId, EntityType parentItemType, FieldKey sortedFieldKey,
                                                  SortOrder sortOrder, int startRow, int pageSize);

    /**
     * Update the parent/child relationships between two items. The items supplied as parameters already have the children
     * moved to the appropriate parent.
     * 
     * @param one {@link ManagedItemVo} one
     * @param two {@link ManagedItemVo} two
     * @param user {@link UserVo} performing the operation
     * @throws OptimisticLockingException if an item has already been updated
     * @throws ValidationException if the data is not valid
     */
    void moveChildren(ManagedItemVo one, ManagedItemVo two, UserVo user) throws OptimisticLockingException, ValidationException;

    /**
     * A method to generate IAH entries for an inactivated item. For use in the RulesCapability when we inactivate products
     * 
     * @param managedItem inactivated item
     * @param user user spawning this
     * @param reasonText Reason text
     */
    void createInactivationItemAuditHistory(ManagedItemVo managedItem, UserVo user, String reasonText);

    /**
     * 
     * Checks for warnings on a ManagedItemVo
     *
     * @param item {@link ManagedItemVo}
     * @param modDifferences Collection of modification differences already applied to the specified item, may be null.
     * @param newAdd this boolean is true when the item is a newly added item. this will be true in create method and false
     *            in other methods
     * @return {@link Errors} representing warnings
     * @throws ValidationException exception
     */
    Errors checkForWarnings(ManagedItemVo item, Collection<ModDifferenceVo> modDifferences, boolean newAdd)
        throws ValidationException;

    /**
     * Updates the supply entry in the special handling field depending on the drug class changes
     *
     * @param updatedProduct The product    
     * @param originalProduct the original product
     * @param user the user logged in
     */
    void updateSpecialHandlingBasedOnDrugClassChange(ProductVo updatedProduct, ProductVo originalProduct,
        UserVo user);
}
