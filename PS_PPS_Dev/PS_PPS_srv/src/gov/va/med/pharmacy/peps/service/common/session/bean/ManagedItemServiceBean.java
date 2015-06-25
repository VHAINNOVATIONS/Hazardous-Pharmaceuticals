/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
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
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;


/**
 * Managed Item service to create, view, and update ManagedItemVo
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class ManagedItemServiceBean extends AbstractPepsStatelessSessionBean<ManagedItemService> implements
    ManagedItemService {

    private static final long serialVersionUID = 1L;

    /**
     * getPendingNonAffliliatedItems
     * @param item item
     * @param user user
     * @return a list of ManagedItemVos
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public List<ManagedItemVo> getPendingNonAffliliatedItems(ManagedItemVo item, UserVo user) {
        return getService().getPendingNonAffliliatedItems(item, user);
    }

    /**
     * The given user approves the given request for the given item.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to approve
     * @param modDifferences Collection of {@link ModDifferenceVo}
     * @param user UserVo rejecting request
     * @return RequestUpdateInformation Updated item and request information
     * 
     * @throws ValidationException if data does not validate
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ProcessedRequestVo approveRequest(ManagedItemVo item, RequestVo request, Collection<ModDifferenceVo> modDifferences,
        UserVo user)
        throws ValidationException {

        return getService().approveRequest(item, request, modDifferences, user);
    }

    /**
     * Commit the modifications to the ManagedItemVo and the ManagedItemVo itself.
     * 
     * @param modDifferences differences between the old and new ManagedItemVo
     * @param item ManagedItemVo to commit
     * @param user UserVo performing commit
     * @return ProcessedItemVo with updated ManagedItemVo and warnings
     * @throws ValidationException if error validating data in ManagedItemVo
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#commitModifications(java.util.Collection,
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo, gov.va.med.pharmacy.peps.common.vo.UserVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ProcessedItemVo commitModifications(Collection<ModDifferenceVo> modDifferences, ManagedItemVo item, UserVo user)
        throws ValidationException {
        return getService().commitModifications(modDifferences, item, user);
    }

    /**
     * Rescinds the previous rejection and puts the item back into a PENDING state.
     * 
     * @param item original ManagedItemVo without changes detailed in modDifferences parameter
     * @param user UserVo performing commit
     * @return ProcessedItemVo
     * @throws ValidationException if error validating data in ManagedItemVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ProcessedItemVo commitRescindRejection(ManagedItemVo item, UserVo user) throws ValidationException {
        return getService().commitRescindRejection(item, user);
    }

    /**
     * Compare the old and updated ManagedItemVo for differences and check if there are any warnings.
     * 
     * @param oldItem original {@link ManagedItemVo}
     * @param updatedItem updated {@link ManagedItemVo}
     * @param user {@link UserVo} updating {@link ManagedItemVo}
     * @return {@link ModificationSummaryVo} with Collection of {@link ModDifferenceVo} and {@link Errors} as warnings
     * @throws ValidationException if error validating updated {@link ManagedItemVo}
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ModificationSummaryVo submitModifications(ManagedItemVo oldItem, ManagedItemVo updatedItem, UserVo user)
        throws ValidationException {

        return getService().submitModifications(oldItem, updatedItem, user);
    }

    /**
     * Commit all the modifications to the given items.
     * 
     * @param itemModDifferences List<ItemModDifferenceVo> differences between the old and new {@link ManagedItemVo}
     * @param user UserVo performing commit
     * @return updated ManagedItemVos
     * @throws ValidationException if error validating data in ManagedItemVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public Collection<ProcessedItemVo> commitAllModifications(Collection<ItemModDifferenceVo> itemModDifferences, UserVo user)
        throws ValidationException {
        return getService().commitAllModifications(itemModDifferences, user);
    }

    /**
     * Applies the mod difference to all of the items in the list. Also retrieves the fully populated version of the times
     * 
     * @param modDifferences ModDifferenceVo
     * @param items items to apply the change to
     * @param user This is the value object containing the user information.
     * @throws ItemNotFoundException e
     * @return Collection<ItemModDifferenceVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public Collection<ItemModDifferenceVo> submitAllModifications(Collection<ModDifferenceVo> modDifferences,
        Collection<ManagedItemVo> items, UserVo user)
        throws ItemNotFoundException {

        return getService().submitAllModifications(modDifferences, items, user);
    }
    
    /**
     * Applies the mod difference to all of the items in the list. Also retrieves the fully populated version of the times
     * 
     * @param modDifferences ModDifferenceVo
     * @param items items to apply the change to
     * @param user userVo
     * @throws ItemNotFoundException e
     * @return Collection<ItemModDifferenceVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public Collection<ItemModDifferenceVo> submitGroupModifications(Collection<ModDifferenceVo> modDifferences,
        Collection<ManagedItemVo> items, UserVo user)
        throws ItemNotFoundException {

        return getService().submitGroupModifications(modDifferences, items, user);
    }

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
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#commitRequest(
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo,
     *      gov.va.med.pharmacy.peps.common.vo.RequestVo, java.util.Collection, gov.va.med.pharmacy.peps.common.vo.UserVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ProcessedRequestVo commitRequest(ManagedItemVo oldItem, RequestVo request, Collection<ModDifferenceVo> differences,
        UserVo user, boolean ignoreUserRule) throws ValidationException {
        return getService().commitRequest(oldItem, request, differences, user, ignoreUserRule);
    }

    /**
     * Submit the request, modifications to the ManagedItemVo, and the ManagedItemVo itself.
     * <p>
     * Does not commit the {@link RequestVo} to the database, rather it only checks the {@link RequestVo} for conflicts.
     * 
     * @param item ManagedItemVo to begin commit steps with, with the user's latest updates but not the accepted reqs.
     * @param request RequestVo to commit
     * @param differences Collection of {@link ModDifferenceVo} between the old and new ManagedItemVo
     * @param user UserVo performing commit
     * @return RequestUpdateInformation Updated item and request information
     * @throws ValidationException if error validating data in ManagedItemVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService commitRequest(
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo, gov.va.med.pharmacy.peps.common.vo.RequestVo,
     *      java.util.Collection, gov.va.med.pharmacy.peps.common.vo.UserVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ProcessedRequestVo submitRequest(ManagedItemVo item, RequestVo request, Collection<ModDifferenceVo> differences,
        UserVo user) throws ValidationException {
        return getService().submitRequest(item, request, differences, user);
    }

    /**
     * Apply the changes, in memory, specified in the given {@link RequestVo#getRequestDetails()}.
     * <p>
     * <strong>This method does not commit the changes to the database!</strong>
     * 
     * @param oldItem original {@link ManagedItemVo} (from the database, without changes) on which to apply changes
     * @param request {@link RequestVo} making the changes, including all of the recently accepted {@link ModDifferenceVo}
     * @param user {@link UserVo} making the request
     * @return Collection of {@link ItemAuditHistoryVo}
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo applyChanges(ManagedItemVo oldItem, RequestVo request, UserVo user) {
        return getService().applyChanges(oldItem, request, user);
    }

    /**
     * Create a new instance of the ManagedItemVo. Return the same ManagedItemVo with its new ID.
     * 
     * @param item ManagedItemVo to create
     * @param user UserVo performing create
     * @return created ManagedItemVo
     * @throws ValidationException if error validating data in ManagedItemVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#create(
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo,
     *      gov.va.med.pharmacy.peps.common.vo.UserVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ProcessedItemVo create(ManagedItemVo item, UserVo user) throws ValidationException {
        return getService().create(item, user);
    }

    /**
     * Delete the partially saved ManagedItemVo.
     * 
     * @param partialId of type String
     * @param itemType Type of partial item to retrieve
     * @return deleted ManagedItemVo
     * @throws ItemNotFoundException if cannot find the PartialSaveVo for the given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#deletePartial(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo deletePartial(String partialId, EntityType itemType) throws ItemNotFoundException {
        return getService().deletePartial(partialId, itemType);
    }

    /**
     * Delete the ManagedItemVo.
     * 
     * @param managedItem of type managedItem
     * @throws ValidationException if cannot find the PartialSaveVo for the given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#deletePartial(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void deleteItem(ManagedItemVo managedItem) throws ValidationException {
        getService().deleteItem(managedItem);
    }

    /**
     * The given user marks the given request under review for the given item.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to mark under review
     * @param user UserVo rejecting request
     * @return updated RequestVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public RequestVo markRequestUnderReview(ManagedItemVo item, RequestVo request, UserVo user) {
        return getService().markRequestUnderReview(item, request, user);
    }

    /**
     * The given user marks the given request as needing a PEPS second review for the given item.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to mark under review
     * @param user UserVo rejecting request
     * @return updated RequestVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public RequestVo markRequestForPsr(ManagedItemVo item, RequestVo request, UserVo user) {
        return getService().markRequestForPsr(item, request, user);
    }

    /**
     * The given user rejects the given problem report.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to reject
     * @param user UserVo rejecting request
     * @return updated RequestVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public RequestVo rejectProblemReport(ManagedItemVo item, RequestVo request, UserVo user) {
        return getService().rejectProblemReport(item, request, user);
    }

    /**
     * The given user rejects the given request for the given item.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to reject
     * @param newDifferences Collection<ModDifferenceVo> Newly-generated field-modifications.
     * @param user UserVo rejecting request
     * @return rejected RequestVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public RequestVo rejectRequest(ManagedItemVo item, RequestVo request, Collection<ModDifferenceVo> newDifferences,
        UserVo user) {
        return getService().rejectRequest(item, request, newDifferences, user);
    }

    /**
     * Get all ManagedDataVo of the given {@link EntityType}. Only applies to managed domains. This prevents returning a
     * large list of all orderable items, products, or NDCs.
     * 
     * @param itemType {@link EntityType}
     * @return full list of the given domain
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public List<ManagedItemVo> retrieve(EntityType itemType) {
        return getService().retrieve(itemType);
    }

    /**
     * Retrieve the ManaedItemVo with the given ID.
     * 
     * @param itemId String ID of the item to retrieve
     * @param itemType Type of item to retrieve
     * @return ManagedItemVo
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#retrieve(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo retrieve(String itemId, EntityType itemType) throws ItemNotFoundException {
        return getService().retrieve(itemId, itemType);
    }

    /**
     * Retrieve a minimally populated ManagedItemVo with the given ID.
     * <p>
     * The returned {@link ManagedItemVo} likely only has enough data for the {@link ManagedItemVo#toShortString()} and
     * {@link ManagedItemVo#getId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the {@link ManagedItemVo} in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * 
     * @param itemId String ID of the item to retrieve
     * @param itemType Type of item to retrieve
     * @return minimally populated ManagedItemVo
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo retrieveMinimal(String itemId, EntityType itemType) throws ItemNotFoundException {
        return getService().retrieveMinimal(itemId, itemType);
    }

    /**
     * Retrieve an blank template instance of the child to the current ManagedItemVo.
     * 
     * @param parentId ManagedItemVo ID set as the parent of the blank template
     * @param parentType EntityType of parent
     * @return blank template instance of ManagedItemVo
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#retrieveBlankChildTemplate(
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo retrieveBlankChildTemplate(String parentId, EntityType parentType) throws ItemNotFoundException {
        return getService().retrieveBlankChildTemplate(parentId, parentType);
    }

    /**
     * Retrieve an blank template instance of the local child to the current national ManagedItemVo. Only applies for
     * ManagedItemVo types that have a national/local relationship, which currently is only OrderableItemVo.
     * 
     * @param parentId ManagedItemVo ID set as the parent of the blank template
     * @param parentType EntityType of parent
     * @return blank template instance of ManagedItemVo
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#retrieveBlankLocalTemplate(
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo retrieveBlankLocalTemplate(String parentId, EntityType parentType) throws ItemNotFoundException {
        return getService().retrieveBlankLocalTemplate(parentId, parentType);
    }

    /**
     * Retrieve an blank template instance of the current ManagedItemVo type.
     * 
     * @param itemType Type of item template to retrieve
     * @return blank template instance of ManagedItemVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#retrieveBlankTemplate()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo retrieveBlankTemplate(EntityType itemType) {
        return getService().retrieveBlankTemplate(itemType);
    }

    /**
     * retrieve the collection of items associated with its parent item
     * 
     * @param managedItemId String
     * @param entityType EntityType to retrieve
     * @return List<ManagedItemVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public List<ManagedItemVo> retrieveChildren(String managedItemId, EntityType entityType) {
        return getService().retrieveChildren(managedItemId, entityType);
    }

    /**
     * Retrieve an local template instance of the current national ManagedItemVo. Only applies for ManagedItemVo types that
     * have a national/local relationship, which currently is only OrderableItemVo.
     * 
     * @param parentId ManagedItemVo ID set as the parent of the blank template
     * @param parentType EntityType of parent
     * @return template instance of ManagedItemVo
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#retrieveLocalTemplate(
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo retrieveLocalTemplate(String parentId, EntityType parentType) throws ItemNotFoundException {
        return getService().retrieveLocalTemplate(parentId, parentType);
    }

    /**
     * Retrieve the partially saved ManagedItemVo. Delete the PartialSaveVo from the database.
     * 
     * @param partialId String
     * @param itemType Type of partial item to retrieve
     * @return partially saved ManagedItemVo
     * @throws ValidationException if an optimistic locking error is found
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#retrievePartialItem(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo retrievePartialItem(String partialId, EntityType itemType) throws ValidationException {
        return getService().retrievePartialItem(partialId, itemType);
    }

    /**
     * Retrieve the partially saved ManagedItemVo via the PartialSaveVo. Do not delete the PartialSaveVo from the database.
     * 
     * @param partialId String
     * @param itemType Type of partial item to retrieve
     * @return PartialSaveVo
     * @throws ItemNotFoundException if cannot find the PartialSaveVo for the given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#retrievePartialSave(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public PartialSaveVo retrievePartialSave(String partialId, EntityType itemType) throws ItemNotFoundException {
        return getService().retrievePartialSave(partialId, itemType);
    }

    /**
     * Retrieve all partially saved {@link ManagedItemVo}.
     * 
     * @return List<PartialSaveVo> partially saved managed items
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#retrievePartialSave(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    @Override
    public List<PartialSaveVo> retrievePartialSaves() {
        return getService().retrievePartialSaves();
    }

    /**
     * Retrieve all partially saved {@link ManagedItemVo} for the given user.
     * 
     * @param user UserVo for which to retrieve partially saved work
     * @return List<PartialSaveVo> partially saved managed items
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public List<PartialSaveVo> retrievePartialSaves(UserVo user) {
        return getService().retrievePartialSaves(user);
    }

    /**
     * Creates a new ManagedItemVo template with the appropriate parent.
     * 
     * @param itemId String ID of the item to retrieve as a template
     * @param itemType Type of item to retrieve as a template
     * @return ManagedItemVo template
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#retrieveTemplate(java.lang.String,
     *      gov.va.med.pharmacy.peps.common.vo.EntityType)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo retrieveTemplate(String itemId, EntityType itemType) throws ItemNotFoundException {
        return getService().retrieveTemplate(itemId, itemType);
    }

    /**
     * Save the partially entered ManagedItemVo.
     * 
     * @param item ManagedItemVo
     * @param comment String
     * @param user UserVo
     * @return partially saved ManagedItmVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService#savePartial(
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo,
     *      java.lang.String, gov.va.med.pharmacy.peps.common.vo.UserVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo savePartial(ManagedItemVo item, String comment, UserVo user) {
        return getService().savePartial(item, comment, user);
    }

    /**
     * Save the problem report.
     * 
     * @param item ManagedItemVo
     * @param comment String contains the problem report text
     * @param user UserVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ManagedItemService submitProblemReport(
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo, java.lang.String, gov.va.med.pharmacy.peps.common.vo.UserVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void submitProblemReport(ManagedItemVo item, String comment, UserVo user) {
        getService().submitProblemReport(item, comment, user);
    }

    /**
     * Search for {@link ManagedItemVo} using the given {@link SearchCriteriaVo} as search criteria.
     * 
     * @param searchCriteria criteria for the search
     * @return List<ManagedItemVo> Managed items matching the given search criteria
     * @throws ValidationException if the given {@link SearchCriteriaVo} is invalid
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public List<ManagedItemVo> search(SearchCriteriaVo searchCriteria) throws ValidationException {
        return getService().search(searchCriteria);
    }

    /**
     * Retrieve the parent with the given itemId and itemType, then set the current item's (the child) parent.
     * 
     * @param child Child ManagedItemVo
     * @param parentId ManagedItemVo ID for the parent
     * @param parentType ManagedItemVo EntityType for the parent
     * @return child ManagedItemVo with parent set
     * @throws ItemNotFoundException if cannot find parent with given ID and EntityType
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo selectParent(ManagedItemVo child, String parentId, EntityType parentType)
        throws ItemNotFoundException {
        return getService().selectParent(child, parentId, parentType);
    }

    /**
     * Swaps the children between two ManagedItemVo.
     * 
     * @param child the child to update
     * @param parent the parent of the child
     * @param user the user doing the swap
     * @throws ValidationException 
     * @return ProcessedItemVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ProcessedItemVo updateParentChildRelationships(ManagedItemVo child, ManagedItemVo parent, UserVo user)
        throws ValidationException {
        return getService().updateParentChildRelationships(child, parent, user);
    }

    /**
     * Updates without needing a Collection of ModDifferenceVo and without creating a request. Enforces business rules,
     * updates ManagedItemVo, and sends it to local/national and VistA.
     * 
     * @param managedItem ManagedItemVo to update
     * @param user UserVo updating item
     * @return ManagedItemVo updated item
     * @throws ValidationException if error enforcing business rules
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo update(ManagedItemVo managedItem, UserVo user) throws ValidationException {
        return getService().update(managedItem, user);
    }

    /**
     * Computes differences in data fields between VOs from database and user.
     * 
     * @param item VOs with user changes
     * @param differences a collection of differences
     * @param user the user making the current changes
     * @return MergeVo VO with computed differences
     * @throws ItemNotFoundException 
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MergeVo computeMergeInformation(ManagedItemVo item, Collection<ModDifferenceVo> differences, UserVo user)
        throws ItemNotFoundException {
        return getService().computeMergeInformation(item, differences, user);
    }

    /**
     * Saves changes committed by user after reviewing differences.
     * 
     * @param mergeItem VO with differences
     * @param item VO with current values from database
     * @param user user logged in
     * @return VO with all merged values
     * @throws ValidationException if error in data
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemVo commitMergeModifications(MergeVo mergeItem, ManagedItemVo item, UserVo user)
        throws ValidationException {
        return getService().commitMergeModifications(mergeItem, item, user);
    }

    /**
     * Generate the VistA OI Name and OI name from the selected VA Generic name and dosage
     * 
     * @param item VOs with user changes
     * @return VO with generated values
     * 
     * @ejb.interface-method
     * @ejb.transaction type= "Required"
     */
    @Override
    public ManagedItemVo generateOINames(ManagedItemVo item) {
        return getService().generateOINames(item);
    }

    /**
     * Retrieve the ManaedItemVo with the given ID.
     * <p>
     * Use the {@link UserVo#getTableSizePreference()} to determine the page size for the children, using the default sort
     * order. Only the first {@link UserVo#getTableSizePreference()} children will be populated! Additional children must be
     * retrieved by calling {@link #retrieveChildren(String, EntityType, int, FieldKey, SortOrder, int)}
     * 
     * @param itemId String ID of the item to retrieve
     * @param itemType Type of item to retrieve
     * @param user {@link UserVo} from which to retrieve the page size
     * @return ManagedItemVo
     * 
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     * 
     * @ejb.interface-method
     * @ejb.transaction type= "Required"
     */
    @Override
    public ManagedItemVo retrieve(String itemId, EntityType itemType, UserVo user) throws ItemNotFoundException {
        return getService().retrieve(itemId, itemType, user);
    }

    /**
     * Retrieve a list of items for the given parent Item Id
     * 
     * @param parentItemId String ID of parent for which to retrieve children items
     * @param parentItemType EntityType of parent for which to retrieve children items
     * @param sortedFieldKey FieldKey representing field to sort by
     * @param sortOrder SortOrder representing ascending or descending sort
     * @param startRow integer index in full list of child items from which to start retrieving
     * @param pageSize integer number of rows to retrieve
     * 
     * @return PaginatedList<ManagedItemVo> child items
     * 
     * @ejb.interface-method
     * @ejb.transaction type= "Required"
     */
    @Override
    public PaginatedList<ManagedItemVo> retrieveChildren(String parentItemId, EntityType parentItemType,
        FieldKey sortedFieldKey, SortOrder sortOrder, int startRow, int pageSize) {
        return getService().retrieveChildren(parentItemId, parentItemType, sortedFieldKey, sortOrder, startRow, pageSize);
    }

    /**
     * Update the parent/child relationships between two items. The items supplied as parameters already have the children
     * moved to the appropriate parent.
     * 
     * @param one {@link ManagedItemVo}
     * @param two {@link ManagedItemVo}
     * @param user {@link UserVo} performing the operation
     * @throws ValidationException if the data is not valid
     * 
     * @ejb.interface-method
     * @ejb.transaction type= "Required"
     */
    @Override
    public void moveChildren(ManagedItemVo one, ManagedItemVo two, UserVo user) throws
        ValidationException {
        getService().moveChildren(one, two, user);
    }

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
     * 
     * @ejb.interface-method
     * @ejb.transaction type= "Required"
     */
    @Override
    public Errors checkForWarnings(ManagedItemVo item, Collection<ModDifferenceVo> modDifferences, boolean newAdd)
        throws ValidationException {
        return getService().checkForWarnings(item, modDifferences, newAdd);
    }

    /**
     * Returns full list of results for exporting
     * @param searchCriteria SearchCriteriaVo
     * @throws ValidationException exception
     * @return List
     * 
     * @ejb.interface-method
     * @ejb.transaction type= "Required"
     */
    @Override
    public List<ManagedItemVo> searchFullList(SearchCriteriaVo searchCriteria) throws ValidationException {
        return getService().searchFullList(searchCriteria);
    }

    /**
     * Updates the supply entry in the special handling field depending on the drug class changes
     *
     * @param updatedProduct The product    
     * @param originalProduct the original product
     * @param user the user logged in
     * @ejb.interface-method
     * @ejb.transaction type= "Required"
     */
    @Override
    public void updateSpecialHandlingBasedOnDrugClassChange(ProductVo updatedProduct, ProductVo originalProduct,
        UserVo user) {
        getService().updateSpecialHandlingBasedOnDrugClassChange(updatedProduct, originalProduct, user);
    }
}
