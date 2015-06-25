/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.BusinessRuleException;
import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Capability which enforces business rules for a particular VO.
 * 
 * @param <T> type of {@link ManagedItemVo}
 */
public interface RulesCapability<T extends ManagedItemVo> {

    /**
     * Validate the Managed Item and enforce other business rules.
     * 
     * @param managedItem ManagedItemVo to validate
     * @param user UserVo for which to validate
     * @param canPerformUpdate indicating if this call is allowed to perform updates on other items
     * @throws ValueObjectValidationException if error in validation
     * @throws BusinessRuleException if error is business rules
     * @throws ValidationException if general validation error
     */
    void enforceRules(T managedItem, UserVo user, boolean canPerformUpdate) throws ValueObjectValidationException,
        BusinessRuleException, ValidationException;

    /**
     * Validate the Managed Item and enforce other business rules.
     * 
     * @param managedItem ManagedItemVo to validate
     * @param user UserVo for which to validate
     * @param modDifference collection of mod differences
     * @param canPerformUpdate boolean indicating if the call is allowed to perform updates on other items
     * @throws ValueObjectValidationException if error in validation
     * @throws BusinessRuleException if error is business rules
     * @throws ValidationException if general validation error
     */
    void enforceRules(T managedItem, UserVo user, Collection<ModDifferenceVo> modDifference, boolean canPerformUpdate)
        throws ValueObjectValidationException, BusinessRuleException, ValidationException;

    /**
     * Check the given {@link ManagedItemVo} for warnings.
     * <p>
     * There are two types of warnings handled via this method: warnings that are triggered only when a specific field(s) is
     * changed, and warnings triggered by field settings regardless of if these fields have been changed or not. Clients
     * should provide a difference collection of fields changed when they submit their changes. When they commit their
     * changes however, they should pass a null difference collection (so only non-field-change-triggered warnings are
     * checked for).
     * <p>
     * Default implementation returns an empty {@link Errors} object. Sub classes may override this method to add
     * item-specific warnings.
     * 
     * @param item {@link ManagedItemVo}
     * @param modDifferences Collection of modification differences already applied to the specified item, may be null.
     * @param newAdd this boolean is true when the item is a newly added item. this will be true in create method and false
     *            in other methods
     * @return {@link Errors} representing warnings
     * @throws ValidationException exception
     */
    Errors checkForWarnings(T item, Collection<ModDifferenceVo> modDifferences, boolean newAdd) throws ValidationException;

    /**
     * Update the given {@link ManagedItemVo} as necessary when a request completes.
     * <p>
     * This method only updates the ValueObject instance, not the database!
     * 
     * @param request {@link RequestVo}
     * @param item {@link ManagedItemVo}
     * @param modDifferences Collection of {@link ModDifferenceVo}
     * @param user {@link UserVo} performing the action
     * @return updated {@link ManagedItemVo}
     * @throws ValidationException exception
     */
    T processCompletedRequest(RequestVo request, T item, Collection<ModDifferenceVo> modDifferences, UserVo user)
        throws ValidationException;

    /**
     * Update the existing item (by ID) with the data contained in the given item from National.
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
    T updateFromNational(T nationalItem, UserVo user) throws ValidationException, OptimisticLockingException;

    /**
     * Merge the existing Local item that matches the given National item by uniqueness fields.
     * <p>
     * Default implementation inactivates existing Local items and inserts item from National.
     * 
     * @param nationalItem {@link ManagedItemVo} from National
     * 
     * @see #insertFromNational(ManagedItemVo)
     * @param user {@link UserVo} performing operation
     * @return merged {@link ManagedItemVo}
     * @throws ValidationException if error validating data during update
     * @throws OptimisticLockingException if an item was already updated before this method could update it
     */
    T mergeFromNational(T nationalItem, UserVo user) throws ValidationException, OptimisticLockingException;

    /**
     * Insert the new item from National into Local.
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
    T insertFromNational(T nationalItem, UserVo user) throws ValidationException;
}
