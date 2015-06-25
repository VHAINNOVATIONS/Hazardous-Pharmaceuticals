/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.exception.BusinessRuleException;
import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.OiRouteVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderableItemDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;


/**
 * Enforce Orderable Item business rules
 */
public class OrderableItemRulesCapabilityImpl extends DefaultRulesCapabilityImpl<OrderableItemVo> {

    
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
    .getLogger(OrderableItemRulesCapabilityImpl.class);
    
    /**
     * Enforce additional Orderable Item rules beyond simple ValueObject validation.
     * <p>
     * If the OI is not a new instance, need to retrieve all products as the products on the OI could be a partial list
     * (paged from the presentation)
     * 
     * @param orderableItem {@link OrderableItemVo} to validate
     * @param user UserVo for which to validate
     * @param canPerformUpdate boolean indicating if the call is allowed to perform updates on other items
     * @throws ValueObjectValidationException if error validating VO
     * @throws BusinessRuleException if error in cross-logic business rule
     * @throws ValidationException if general validation error
     */
    @Override
    protected void handleEnforceRules(OrderableItemVo orderableItem, UserVo user, boolean canPerformUpdate)
        throws ValueObjectValidationException, BusinessRuleException, ValidationException {
        enforceDefaultOiRoute(orderableItem);

        List<ProductVo> allProducts = null;

        if (orderableItem.getId() != null) {
            allProducts = getManagedItemCapability().retrieveChildren(orderableItem.getId(), orderableItem.getEntityType());
        }

        enforcePepsOiNameRules(orderableItem);
        enforceFormularyStatusRules(orderableItem, allProducts);

        if (!orderableItem.isNewInstance()) {
            if (canPerformUpdate) {
                enforceInactivationRules(orderableItem, allProducts, user);
            }

            enforceLocalUseRules(orderableItem, allProducts);
        }

        // new items marked for local use are invalid regardless per rule 6 of DF112
        if (orderableItem.isNewInstance() && orderableItem.isLocalUse()) {
            Errors errors = new Errors();

            errors.addError(FieldKey.LOCAL_USE, ErrorKey.OI_LOCAL_USE);

            throw new ValueObjectValidationException(errors);
        }
    }

    /**
     * If there is only one {@link OiRouteVo}, mark it as default
     * 
     * @param orderableItem {@link OrderableItemVo}
     */
    private void enforceDefaultOiRoute(OrderableItemVo orderableItem) {
        if (orderableItem.getOiRoute() != null && orderableItem.getOiRoute().size() == 1) {
            orderableItem.getOiRoute().iterator().next().setDefaultRoute(true);
        }
    }

    /**
     * Enforces rules related to the local use field, specifically an OI must have at least one active product to be marked
     * for local use
     * 
     * @param orderableItem the OI to test
     * @param allProducts its children
     * @throws ValueObjectValidationException 
     */
    private void enforceLocalUseRules(OrderableItemVo orderableItem, List<ProductVo> allProducts)
        throws ValueObjectValidationException {

        if (orderableItem.isLocalUse()) {

            boolean hasActiveProduct = false;

            for (ProductVo product : allProducts) {
                hasActiveProduct |= product.getItemStatus().isActive();
            }

            if (!hasActiveProduct) {
                Errors errors = new Errors();

                errors.addError(FieldKey.LOCAL_USE, ErrorKey.OI_LOCAL_USE);

                throw new ValueObjectValidationException(errors);
            }
        }

    }

    /**
     * Enforces Orderable Item
     * <p>
     * Setting the Inactivation Date is handled by the super class: {@link #handleInactivation(OrderableItemVo)}.
     * 
     * @param orderableItem The OrderableItemVo to enforce
     * @param allProducts List<ProductVo> All products on this OI
     * @param user UserVo for which to update
     * @throws ValidationException 
     */
    private void enforceInactivationRules(OrderableItemVo orderableItem, List<ProductVo> allProducts, UserVo user)
        throws ValidationException {

        // Is this OI inactive, local and does it have products associated with it?
        if (ItemStatus.INACTIVE.equals(orderableItem.getItemStatus()) && orderableItem.isLocal() && allProducts != null
            && !allProducts.isEmpty()) {

            // Need to change the parent of the products to the NOI get NOI parent
            OrderableItemVo parent = orderableItem.getOrderableItemParent();

            for (ProductVo product : allProducts) {
                if (ItemStatus.ACTIVE.equals(product.getItemStatus())) {
                    product.setParent(parent);
                    product.setPreviousParent(orderableItem);
                    getManagedItemCapability().update(product, user);
                }
            }
        }
    }

    /**
     * DF67: Formulary Status
     * <p>
     * Rule 4: An orderable item must be marked as "Formulary" if there is at least one "Active" product item associated to
     * the orderable item has the Local Non-Formulary value of "N".
     * <p>
     * Rule 5: An orderable item must be marked as "Non-Formulary" if all "Active" product items associated to the orderable
     * item have the Local Non-Formulary values of "Y".
     * 
     * @param orderableItem OrderableItemVo
     * @param allProducts List<ProductVo> All products on this OI
     * @throws ValueObjectValidationException if error validating the OI data
     */
    private void enforceFormularyStatusRules(OrderableItemVo orderableItem, List<ProductVo> allProducts)
        throws ValueObjectValidationException {

        // At National Instance the value of the Formulary Status field is determined
        // by the value of National Formulary Indicator
        if (this.getEnvironment().isLocal()) {
            if (allProducts != null) {

                // for local environment the formulary status is set to "formulary" if there is at least one active product
                // item
                // that has local non-formulary set to "No".
                // the formulary status is set to "non-formulary" if all the active products associated with the orderable
                // item
                // that have local non-formulary set to "Yes"

                boolean rule4 = false;
                boolean rule5 = !allProducts.isEmpty();

                for (ProductVo product : allProducts) {
                    boolean active = ItemStatus.ACTIVE.equals(product.getItemStatus());

                    DataField<Boolean> localNonFormulary = product.getVaDataFields()
                        .getDataField(FieldKey.LOCAL_NON_FORMULARY);
                    boolean localNonFormularyValue = false;

                    if (localNonFormulary != null && localNonFormulary.getValue() != null) {
                        localNonFormularyValue = localNonFormulary.getValue().booleanValue();
                    }

                    if (!rule4) {
                        rule4 = active && !localNonFormularyValue;
                    }

                    if (active) {
                        rule5 = rule5 && localNonFormularyValue;
                    }
                }
            }// end if

        }// end if

    }

    /**
     * this method sets PEPS OI Name = vista oi name + dosage form
     * 
     * @param orderableItem the orderable item
     * @throws DuplicateItemException if the OI is a duplicate after updating the OI Name
     */
    private void enforcePepsOiNameRules(OrderableItemVo orderableItem) throws DuplicateItemException {

        //CEW: 1/9/2012 commenting out this line because when we are submitting a new dosage form athat already
        // has the name fo generic name + dosage from it is adding it again.
        //orderableItem.updateOiName();

        ManagedItemDomainCapability domainCapability = getManagedItemDomainCapability(EntityType.ORDERABLE_ITEM);

        if (domainCapability.existsByUniquenessFields(orderableItem)) {
            throw new DuplicateItemException(DuplicateItemException.OI_DUPLICATE_ITEM);
        }
    }

    /**
     * Update the existing Local item (by ID) with the data contained in the given item from National.
     * <p>
     * Calls the {@link DefaultRulesCapabilityImpl#updateFromNational(ManagedItemVo, UserVo)} and then if the National item
     * is inactive, inactivates LOIs on the NOI.
     * 
     * @param nationalItem {@link ManagedItemVo} from National
     * @param user {@link UserVo} performing operation
     * @return updated {@link ManagedItemVo}
     * @throws ValidationException if error validating data during update
     * @throws OptimisticLockingException if an item was already updated before this method could update it
     * 
     * @see gov.va.med.pharmacy.peps.service.common.capability.impl.DefaultRulesCapabilityImpl#updateFromNational(
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo,
     *      gov.va.med.pharmacy.peps.common.vo.UserVo)
     */
    @Override
    public OrderableItemVo updateFromNational(OrderableItemVo nationalItem, UserVo user) throws ValidationException,
        OptimisticLockingException {

        OrderableItemVo updatedItem = super.updateFromNational(nationalItem, user);

        if (ItemStatus.INACTIVE.equals(nationalItem.getItemStatus())) {
            ManagedItemDomainCapability domainCapability = getManagedItemCapabilityFactory().getDomainCapability(
                nationalItem.getEntityType());

            List<ManagedItemVo> locals = domainCapability.retrieveChildren(nationalItem.getId());

            for (ManagedItemVo localOi : locals) {
                localOi.inactivate();
                domainCapability.update(localOi, user);
            }
        }

        return updatedItem;
    }

    /**
     * Merge the existing Local item that matches the given National item by uniqueness fields.
     * <ol>
     * <li>Move the products associated to the Local OI back to the National OI associated to the LOI. Only updates the NOI
     * since the LOI is being deleted anyway.</li>
     * <li>Delete the Local OI (because the EPL Id is incorrect.)</li>
     * <li>Copy all the local only fields from the Local OI you deleted to the new National OI.</li> *
     * <li>Add the National OI entry.</li>
     * </ol>
     * 
     * @param nationalItem {@link ManagedItemVo} from National
     * 
     * @see #insertFromNational(ManagedItemVo)
     * @param user {@link UserVo} performing operation
     * @return merged {@link ManagedItemVo}
     * @throws ValidationException if error validating data during update
     * @throws OptimisticLockingException if the
     *             {@link ManagedItemCapability#updateParentChildRelationships(ManagedItemVo, ManagedItemVo, UserVo)} fails
     * 
     * @see gov.va.med.pharmacy.peps.service.common.capability.impl.DefaultRulesCapabilityImpl#mergeFromNational(
     *      gov.va.med.pharmacy.peps.common.vo.ManagedItemVo,
     *      gov.va.med.pharmacy.peps.common.vo.UserVo)
     */
    @Override
    public OrderableItemVo mergeFromNational(OrderableItemVo nationalItem, UserVo user)
        throws ValidationException, OptimisticLockingException {
        OrderableItemDomainCapability domainCapability = getManagedItemDomainCapability(EntityType.ORDERABLE_ITEM);
        List<OrderableItemVo> existingItems = domainCapability.retrieveDuplicates(nationalItem);

        for (OrderableItemVo existingItem : existingItems) {

            // LOCAL (tstavenger:May 27, 2009) M5P2 should handle merging local-only data that can cause validation errors
            // nationalItem.mergeLocalFields(existingItem);

            // "Archived" items are ignored during duplicate checks, which is required to insert the NOI and update the
            // parent-child relationships.
            existingItem.archive();
            domainCapability.update(existingItem, user);
        }

        enforceRules(nationalItem, user, true);
        nationalItem.archive();
        OrderableItemVo merged = domainCapability.createWithoutDuplicateCheck(nationalItem, user);

        for (OrderableItemVo existingItem : existingItems) {
            OrderableItemVo existingParent = existingItem.getOrderableItemParent();

            if (existingParent != null) {
                List<ProductVo> existingChildren = getManagedItemCapability().retrieveChildren(existingItem.getId(),
                    EntityType.ORDERABLE_ITEM);
                List<ProductVo> nationalChildren = getManagedItemCapability().retrieveChildren(existingParent.getId(),
                    EntityType.ORDERABLE_ITEM);

                Set<ProductVo> allChildren = new HashSet<ProductVo>();
                allChildren.addAll(nationalChildren);
                allChildren.addAll(existingChildren);
                nationalItem.setProducts(allChildren);

                for (ProductVo child : allChildren) {
                    getManagedItemCapability().updateParentChildRelationships(child, existingParent, user);
                }
            }

            domainCapability.reassociateAndDelete(existingItem, nationalItem, user);
        }

        merged.activate();
        domainCapability.update(merged, user);

        LOG.error("THIS SHOULD NOT BE HAPPENING BECAUSE IT IS LOCAL ONLY");
       // Local only capability, reevaluate when Local is back in vogue
       // getVistaFileSynchCapability().sendNewItemToVista(merged, user, true, false);

        return merged;
    }
}
