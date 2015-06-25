/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaFileSynchCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.RulesCapability;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.utility.ManagedItemCapabilityFactory;


/**
 * Default implementation of RulesCapability which simply validates the ValueObject. Sub classes can override
 * {@link #handleEnforceRules(ManagedItemVo)} to enforce additional rules.
 * 
 * @param <T> type of {@link ManagedItemVo}
 */
public class DefaultRulesCapabilityImpl<T extends ManagedItemVo> implements RulesCapability<T> {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DefaultRulesCapabilityImpl.class);

    private ManagedItemCapability managedItemCapability;
    private EnvironmentUtility environmentUtility;
    private ManagedItemCapabilityFactory managedItemCapabilityFactory;
    private DomainService domainService;
    private VistaFileSynchCapability vistaFileSynchCapability;

    /**
     * Empty constructor
     */
    public DefaultRulesCapabilityImpl() {
        super();
    }

    /**
     * Handle common ManagedItem parent/child relationships.
     * 
     * Handles the case where the parent has been changed. Makes sure that the child is removed from the previous parent
     * item's list of children.
     * 
     * @param managedItem ManagedItemVo to verify
     * @param user UserVo performing operation
     * @throws ValidationException if error
     */
    protected void enforceParentChildRelationships(T managedItem, UserVo user) throws ValidationException {
        if (managedItem.hasPreviousParent() && !managedItem.getParent().equals(managedItem.getPreviousParent())
            && StringUtils.isNotEmpty(managedItem.getId())) {
            
//            // Retrieve both parents
//            ManagedItemVo previousParent = getManagedItemCapability().retrieve(managedItem.getPreviousParent().getId(),
//                managedItem.getPreviousParent().getEntityType());
//            ManagedItemVo parent = getManagedItemCapability().retrieve(managedItem.getParent().getId(),
//                managedItem.getParent().getEntityType());
//
//            // Remove the child from the previous parent.
//            // Must do this by ID since other attributes have changed (hence the update).
//            Iterator<ManagedItemVo> children = previousParent.getChildren().iterator();
//
//            while (children.hasNext()) {
//                ManagedItemVo child = children.next();
//
//                if (managedItem != null && child != null && managedItem.getId() != null
//                    && managedItem.getId().trim().length() > 0 && managedItem.getId().equals(child.getId())) {
//
//                    // Remove the specified child from the previous parent's list, and update the previous parent.
//                    children.remove();
//                    getManagedItemCapability().update(previousParent, user);
//
//                    break;
//                }
//            }
//
//            // Add the child to the new parent (after first making sure that the child isn't already on it).
//            // Must do this by ID since other attributes have changed (hence the update).
//            boolean addChild = true;
//            children = parent.getChildren().iterator();
//
//            while (children.hasNext()) {
//                ManagedItemVo child = children.next();
//
//                // If the product is already there, flag it.
//                if (managedItem != null && child != null && managedItem.getId() != null
//                    && managedItem.getId().trim().length() > 0 && managedItem.getId().equals(child.getId())) {
//                    addChild = false;
//                    break;
//                }
//            }
//
//            if (addChild) {
//                parent.getChildren().add(managedItem);
//                getManagedItemCapability().update(parent, user);
//            }
        }
    }

    /**
     * Validate the Managed Item and enforce other business rules.
     * 
     * Forces all Managed Items to be validated prior to enforcing additional business rules defined in sub classes. Also
     * enforces parent/child relationships.
     * 
     * @param managedItem ManagedItemVo to validate
     * @param user UserVo for which to validate
     * @param canPerformUpdate boolean indicating if the call is allowed to perform updates on other items
     * @throws ValidationException if general validation error
     * 
     * @see ValueObject#validate(UserVo, Environment)
     * @see #enforceParentChildRelationships(ManagedItemVo, UserVo)
     * @see #handleEnforceRules(ManagedItemVo, UserVo)
     */
    public final void enforceRules(T managedItem, UserVo user, boolean canPerformUpdate)
        throws ValidationException {
        managedItem.validate(user, getEnvironment());

        if (canPerformUpdate) {
            enforceParentChildRelationships(managedItem, user);
        }

        handleInactivation(managedItem);
        handleEnforceRules(managedItem, user, canPerformUpdate);
    }

    /**
     * enforceRules.
     * 
     * @param managedItem to validate
     * @param user to validate
     * @param modDifference changes
     * @param canPerformUpdate boolean indicating if the call is allowed to perform updates on other items
     * @throws ValidationException ValidationException
     */
    public final void enforceRules(T managedItem, UserVo user, Collection<ModDifferenceVo> modDifference,
                                   boolean canPerformUpdate) throws ValidationException {
        managedItem.validate(user, getEnvironment());

        if (canPerformUpdate) {
            enforceParentChildRelationships(managedItem, user);
        }

        handleInactivation(managedItem);
        handleEnforceRules(managedItem, user, modDifference, canPerformUpdate);
    }

    /**
     * If the {@link ManagedItemVo} is inactive, and the inactivation date is unset, set the inactivation date to now,
     * otherwise set the inactivation date to null.
     * 
     * @param managedItem ManagedItemVo to validate
     */
    protected void handleInactivation(T managedItem) {
        if (ItemStatus.INACTIVE.equals(managedItem.getItemStatus())) {
            if (managedItem.getInactivationDate() == null) {
                managedItem.setInactivationDate(new Date());
            }

        } else {
            managedItem.setInactivationDate(null);
        }
    }

    /**
     * Check the given {@link ManagedItemVo} for warnings.
     * <p>
     * There are two types of warnings handled via this method: they are warnings that are triggered only 
     * when a specific field(s) is changed, and warnings triggered by field settings regardless of if these fields 
     * have been changed or not. Clients should provide a difference collection of fields changed when they submit 
     * their changes. When they commit their changes however, they should pass a null difference collection (so 
     * only non-field-change-triggered warnings are checked for).
     * <p>
     * Default implementation returns an empty {@link Errors} object. Sub classes may override this method to add
     * item-specific warnings.
     * 
     * @param item {@link ManagedItemVo} the item that is being checked.
     * @param modDifferences Collection of modification differences already applied to the specified item, may be null.
     * @param newAdd this boolean is true when the item is a newly added item. this will be true in create method and false
     *            in other methods
     * @return {@link Errors} representing warnings
     * @throws ValidationException ValidationException
     */
    public Errors checkForWarnings(T item, Collection<ModDifferenceVo> modDifferences, boolean newAdd)
        throws ValidationException {
        return new Errors();
    }

    /**
     * Update the given {@link ManagedItemVo} as necessary when a request completes.
     * <p>
     * This method only updates the ValueObject instance, not the database!
     * <p>
     * Default implementation does nothing; simply returns the item provided as a parameter.
     * 
     * @param request {@link RequestVo}
     * @param item {@link ManagedItemVo}
     * @param modDifferences Collection of {@link ModDifferenceVo}
     * @param user {@link UserVo} performing the action
     * @return updated {@link ManagedItemVo}
     * @throws ValidationException ValidationException
     */
    public T processCompletedRequest(RequestVo request, T item, Collection<ModDifferenceVo> modDifferences, UserVo user)
        throws ValidationException {
        return item;
    }

    /**
     * Get the {@link ManagedItemDomainCapability} for the given {@link EntityType} using the
     * {@link ManagedItemCapabilityFactory}.
     * 
     * @param <T> Type of {@link ManagedItemDomainCapability}
     * @param entityType {@link EntityType}
     * @return {@link ManagedItemDomainCapability}
     */
    @SuppressWarnings("hiding")
    protected <T extends ManagedItemDomainCapability> T getManagedItemDomainCapability(EntityType entityType) {
        return (T) getManagedItemCapabilityFactory().getDomainCapability(entityType);
    }

    /**
     * Use the {@link EnvironmentUtility} to get the {@link Environment}. Helper method to make getting the
     * {@link Environment} slightly easier.
     * 
     * @return {@link Environment}
     */
    public final Environment getEnvironment() {
        return environmentUtility.getEnvironment();
    }

    /**
     * getEnvironmentUtility
     * @return environmentUtility property
     */
    public final EnvironmentUtility getEnvironmentUtility() {
        return environmentUtility;
    }

    /**
     * getManagedItemCapability
     * @return managedItemCapability property
     */
    public final ManagedItemCapability getManagedItemCapability() {
        return managedItemCapability;
    }

    /**
     * getManagedItemCapabilityFactory
     * @return managedItemCapabilityFactory property
     */
    public final ManagedItemCapabilityFactory getManagedItemCapabilityFactory() {
        return managedItemCapabilityFactory;
    }

    /**
     * getDomainService
     * @return domainService The domain service instance.
     */
    public final DomainService getDomainService() {
        return domainService;
    }

    /**
     * Sub classes can override this method to enforce additional rules beyond simple ValueObject validation.
     * 
     * @param managedItem ManagedItemVo to validate
     * @param user UserVo for which to validate
     * @param canPerformUpdate boolean indicating if the call is allowed to perform updates on other items
     * @throws ValidationException if general validation error
     */
    protected void handleEnforceRules(T managedItem, UserVo user, boolean canPerformUpdate)
        throws ValidationException {
    }

    /**
     * Sub classes can override this method to enforce additional rules beyond simple ValueObject validation.
     * 
     * @param managedItem ManagedItemVo to validate
     * @param user UserVo for which to validate
     * @param modDifference the collection of Mod differences
     * @param canPerformUpdate boolean indicating if the call is allowed to perform updates on other items
     * @throws ValidationException if general validation error
     */
    protected void handleEnforceRules(T managedItem, UserVo user, Collection<ModDifferenceVo> modDifference,
                                      boolean canPerformUpdate) throws ValidationException {
        handleEnforceRules(managedItem, user, canPerformUpdate);
    }

    /**
     * Update the existing Local item (by ID) with the data contained in the given item from National.
     * <p>
     * Default implementation ignores PENDING items. All other types have the VA data fields updated and merged appropriately
     * prior to saving in the database.
     * 
     * @param nationalItem {@link ManagedItemVo} from National
     * @param user {@link UserVo} performing operation
     * @return updated {@link ManagedItemVo}
     * @throws ValidationException if error validating data during update
     * @throws OptimisticLockingException if an item was already updated before this method could update it
     */
    public T updateFromNational(T nationalItem, UserVo user) throws ValidationException, OptimisticLockingException {
        T updatedItem = nationalItem;

        if (!RequestItemStatus.PENDING.equals(nationalItem.getRequestItemStatus())) {
            ManagedItemDomainCapability domainCapability = getManagedItemCapabilityFactory().getDomainCapability(
                nationalItem.getEntityType());
            ManagedItemVo existingItem = domainCapability.retrieve(nationalItem.getId());
            nationalItem.mergeLocalFields(existingItem);

            updatedItem = (T) getManagedItemCapability().update(nationalItem, user);
        }

        return updatedItem;
    }

    /**
     * Merge the existing Local item that matches the given National item by uniqueness fields.
     * <p>
     * Default implementation inactivates existing Local items and inserts item from National.
     * @param nationalItem {@link ManagedItemVo} from National
     * @param user {@link UserVo} performing operation
     * @return merged {@link ManagedItemVo}
     * 
     * @throws ValidationException if error validating data during update
     * @throws OptimisticLockingException if an item was already updated before this method could update it
     * 
     * @see #insertFromNational(ManagedItemVo, UserVo)
     */
    public T mergeFromNational(T nationalItem, UserVo user) throws ValidationException, OptimisticLockingException {
        try {
            ManagedItemDomainCapability domainCapability = getManagedItemCapabilityFactory().getDomainCapability(
                nationalItem.getEntityType());
            List<ManagedItemVo> existingItems = domainCapability.retrieveDuplicates(nationalItem);

            for (ManagedItemVo existingItem : existingItems) {
                existingItem.inactivate();
                getManagedItemCapability().update(existingItem, user);
            }
        } catch (ValidationException e) {
            LOG.error("Unable to inactivate existing items, inserting new national one anyway!", e);
        }

        return insertFromNational(nationalItem, user);
    }

    /**
     * Insert the new item from National into Local.
     * <p>
     * Default implementation sets local use to false, enforces business rules, and inserts the National item.
     * <p>
     * @param nationalItem {@link ManagedItemVo} from National
     * @param user {@link UserVo} performing operation
     * @return inserted {@link ManagedItemVo}
     * @throws ValidationException if error validating data during update
     * 
     * @see #enforceRules(ManagedItemVo, UserVo)
     */
    public T insertFromNational(T nationalItem, UserVo user) throws ValidationException {
        prepareInsert(nationalItem, user);

        T inserted = (T) getManagedItemCapability().insertItem(nationalItem, user);
        
//        The following call was removed since it changed and will need to be reevaluated for local use.
//        vistaFileSynchronizationCapability.sendNewItemToVista(nationalItem, user, true);

        return inserted;
    }

    /**
     * Insert the new item sent from National into the Local database.
     * <p>
     * Default implementation sets local use to false, enforces business rules, and inserts the National item.
     * 
     * @param nationalItem {@link ManagedItemVo} from National
     * @param user {@link UserVo} performing operation
     * @return inserted {@link ManagedItemVo}
     * @throws ValidationException if error validating data during update
     * 
     * @see #enforceRules(ManagedItemVo, UserVo)
     */
    protected T insertFromNationalWithoutDuplicateCheck(T nationalItem, UserVo user) throws ValidationException {
        prepareInsert(nationalItem, user);
        ManagedItemDomainCapability domainCapability = getManagedItemDomainCapability(nationalItem.getEntityType());

        T inserted = (T) domainCapability.createWithoutDuplicateCheck(nationalItem, user);

        // Removed for Version 1.0 since there is no Local - DWV
//        vistaFileSynchronizationCapability.sendNewItemToVista(nationalItem, user, true);

        return inserted;
    }

    /**
     * Prepare the National {@link ManagedItemVo} for insertion.
     * 
     * @param nationalItem {@link ManagedItemVo} from National
     * @param user {@link UserVo} performing operation
     * @throws ValidationException if error validating data
     */
    private void prepareInsert(T nationalItem, UserVo user) throws ValidationException {
        nationalItem.setLocalUse(false);
        nationalItem.setPreviouslyMarkedForLocalUse(false);
        nationalItem.defaultLocalOnlyFields();
        enforceRules(nationalItem, user, true);
    }


    /**
     * Utility method, moved to the default rules because Product and NDC both use this method
     * 
     * @param delimiter the delimiter
     * @param value the string to be padded with zeros
     * @return String
     */
    protected String padNdcWithLeadingZero(String delimiter, String value) {
        final char ZERO = '0';
        
        if (value.indexOf(delimiter.charAt(0)) == -1 && value.length() == PPSConstants.I11) {
            
            String padded = value.substring(0, PPSConstants.I5) + "-"
                + value.substring(PPSConstants.I5, PPSConstants.I9) + "-"
                + value.substring(PPSConstants.I9, PPSConstants.I11);
            
            return padded;
        }
        
        String[] split = value.split(delimiter, -1);

        split[0] = StringUtils.leftPad(split[0].trim(), PPSConstants.I5, ZERO);
        split[1] = StringUtils.leftPad(split[1].trim(), PPSConstants.I4, ZERO);
        split[2] = StringUtils.leftPad(split[2].trim(), PPSConstants.I2, ZERO);

        StringBuffer padded = new StringBuffer();
        padded.append(split[0]);
        padded.append(delimiter);
        padded.append(split[1]);
        padded.append(delimiter);
        padded.append(split[2]);

        return padded.toString();
    }

    /**
     * setEnvironmentUtility
     * @param environmentUtility environmentUtility property
     */
    public final void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

    /**
     * Setter for Spring
     * 
     * @param managedItemCapability managedItemCapability property
     */
    public final void setManagedItemCapability(ManagedItemCapability managedItemCapability) {
        this.managedItemCapability = managedItemCapability;
    }

    /**
     * setManagedItemCapabilityFactory
     * @param managedItemCapabilityFactory managedItemCapabilityFactory property
     */
    public final void setManagedItemCapabilityFactory(ManagedItemCapabilityFactory managedItemCapabilityFactory) {
        this.managedItemCapabilityFactory = managedItemCapabilityFactory;
    }

    /**
     * setDomainService
     * @param domainService The domain service instance.
     */
    public final void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }

    /**
     * getVistaFileSynchCapability
     * @return vistaFileSynchCapability property
     */
    public final VistaFileSynchCapability getVistaFileSynchCapability() {
        return vistaFileSynchCapability;
    }

    /**
     * setVistaFileSynchCapability
     * @param vistaFileSynchCapability vistaFileSynchCapability property
     */
    public final void setVistaFileSynchCapability(
        VistaFileSynchCapability vistaFileSynchCapability) {
        this.vistaFileSynchCapability = vistaFileSynchCapability;
    }
}
