/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.FieldKeyUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.AtcCanisterVo;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormNounVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.NationalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.WardGroupForAtcVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;


/**
 * Enforce Product business rules
 */
public class ProductRulesCapabilityImpl extends DefaultRulesCapabilityImpl<ProductVo> {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ProductRulesCapabilityImpl.class);
    private ProductDomainCapability productDomainCapability;
    private DrugUnitDomainCapability drugUnitDomainCapability;

    /**
     * Enforce additional Product rules beyond simple ValueObject validation.
     * 
     * @param product {@link ProductVo} to validate
     * @param user UserVo for which to validate
     * @param canPerformUpdate boolean indicating if the call is allowed to perform updates on other items
     * @throws ValidationException ValidationException
     */
    @Override
    protected void handleEnforceRules(ProductVo product, UserVo user, boolean canPerformUpdate)
        throws ValidationException {
        checkForOneDrugClass(product);
        
        if (ItemStatus.ACTIVE.equals(product.getItemStatus())) {
            enforceDomainActiveRules(product);
        }
        
        if (canPerformUpdate) {
            enforceInactivationRules(product, user);
        }

        computeFields(product, user);

        // local possible dosages are automatically created only at local
        if (getEnvironment().isLocal() && (product.getId() == null)) {
            createLocalPossibleDosagesAutomatically(product);
        }

        if (getEnvironment().isLocal() && canPerformUpdate) {
            enforceNonVAMedRules(product, user);
        }

        // Enforce that no other product has the ATC Mnemonic value this product has.
        enforceUniqueAtcMnemonic(product);

    }

    
    /**
     * enforceDomainActivRules is used to ensure all the product domains being used are still active.
     * 
     * 
     * @param product the product
     * @throws ValidationException ValidationException
     */
    public void enforceDomainActiveRules(ProductVo product)
        throws ValidationException {
        Errors errors = new Errors();
        
        
        // if any of the domains in the active ingredient are inactive it is an error.
        if (product.getActiveIngredients() != null && product.getActiveIngredients().size() > 0) {
            for (ActiveIngredientVo aiVo : product.getActiveIngredients()) {
                if (ItemStatus.INACTIVE.equals(aiVo.getIngredient().getItemStatus())) {
                    errors.addError(ErrorKey.DOMAIN_MUST_BE_ACTIVE, "Ingredient", aiVo.getIngredient().getValue());
                }
                
                DrugUnitVo drugUnitVo = null;
                
                if (aiVo.getDrugUnit() != null) {
                    drugUnitVo = drugUnitDomainCapability.retrieve(aiVo.getDrugUnit().getId());
                }
                
                if (drugUnitVo != null && ItemStatus.INACTIVE.equals(drugUnitVo.getItemStatus())) {
                    errors.addError(ErrorKey.DOMAIN_MUST_BE_ACTIVE, "Ingredient Drug Unit", aiVo.getDrugUnit().getValue());
                }

            }
            
            ArrayList<String> ingNameList = new ArrayList<String>();
            
            for (ActiveIngredientVo aiVo : product.getActiveIngredients()) {
                if (ingNameList.contains(aiVo.getIngredient().getValue())) {
                    errors.addError(ErrorKey.DUPLICATE_INGREDIENT, aiVo.getIngredient().getValue());
                } else {
                    ingNameList.add(aiVo.getIngredient().getValue());
                }
            }
            
        }
        
        ArrayList<String> drugClassNameList = new ArrayList<String>();
      
        if (product.getDrugClasses() != null && product.getDrugClasses().size() > 0) {
            for (DrugClassGroupVo dcVo : product.getDrugClasses()) {
                String unique = dcVo.getDrugClass().getCode() + ":" + dcVo.getDrugClass().getClassification();

                if (drugClassNameList.contains(unique)) {
                    errors.addError(ErrorKey.DUPLICATE_DRUGCLASS, unique);
                } else {
                    drugClassNameList.add(unique);
                }
            }
        }
        
        
        // if the Generic Name is inactive it is an error.
        if (product.getGenericName() != null) {
            if (ItemStatus.INACTIVE.equals(product.getGenericName().getItemStatus())) {
                errors.addError(ErrorKey.DOMAIN_MUST_BE_ACTIVE, "Generic Name", product.getGenericName().getValue());
            }
        }
        
        // if the Dispense Unit is inactive it is an error.
        if (product.getDispenseUnit() != null) {
            if (ItemStatus.INACTIVE.equals(product.getDispenseUnit().getItemStatus())) {
                errors.addError(ErrorKey.DOMAIN_MUST_BE_ACTIVE, "Dispense Unit", product.getDispenseUnit().getValue());
            }
        }
        
        // if the Product Unit is inactive it is an error.
        if (product.getProductUnit() != null) {
            if (ItemStatus.INACTIVE.equals(product.getProductUnit().getItemStatus())) {
                errors.addError(ErrorKey.DOMAIN_MUST_BE_ACTIVE, "Drug Unit", product.getProductUnit().getValue());
            }
        }
        
        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
    }
    
    /**
     * Enforce additional Product rules beyond simple ValueObject validation.
     * 
     * @param product {@link ProductVo} to validate
     * @param user UserVo for which to validate
     * @param modDifference the collection of mod differences
     * @param canPerformUpdate boolean indicating if the call is allowed to perform updates on other items     
     * @throws ValidationException ValidationException
     */
    @Override
    protected void handleEnforceRules(ProductVo product, UserVo user, Collection<ModDifferenceVo> modDifference,
                                      boolean canPerformUpdate) throws
        ValidationException {
        handleEnforceRules(product, user, canPerformUpdate);
        enforcePossibleDosagesRules(product, modDifference);
    }

    /**
     * this method validates if the modification to the possible dosages is applicable
     * 
     * @param product the product
     * @param modDifferences the collection of mod differences
     * @return boolean
     * @throws ValidationException ValidationException
     */
    private boolean validatePossibleDosageMod(ProductVo product, Collection<ModDifferenceVo> modDifferences)
        throws ValidationException {

        // if the possible dosages has been changed find out if the package field has been changed
        for (ModDifferenceVo modDiff : modDifferences) {

            if (FieldKey.NATIONAL_POSSIBLE_DOSAGES.equals(modDiff.getDifference().getFieldKey())) {
                Collection<NationalPossibleDosagesVo> colOld =
                        (Collection<NationalPossibleDosagesVo>) (modDiff.getDifference().getOldValue());
                Collection<NationalPossibleDosagesVo> colNew =
                        (Collection<NationalPossibleDosagesVo>) (modDiff.getDifference().getNewValue());

                for (NationalPossibleDosagesVo oldVal : colOld) {

                    for (NationalPossibleDosagesVo newVal : colNew) {

                        if ((newVal.getId() != null) && (newVal.getId().equals(oldVal.getId()))) {

                            // now check if the package field is has been modified

                            if (!(newVal.getPossibleDosagePackage().equals(oldVal.getPossibleDosagePackage()))) {

                                // The system allows a PEPS Local Manager to modify the value of the Package field for
                                // an existing Possible Dosages entry when the modified value exists as a value
                                // in the Package field (of an existing entry in Units multiple)
                                DosageFormUnitVo dosageFormUnitMatch = productIngredientUnitOIDosageFormUnitMatch(product);

                                if (dosageFormUnitMatch.getPackages().containsAll(newVal.getPossibleDosagePackage())) {
                                    return true;
                                }

                                return false;
                            }
                        }

                    }
                }
            }
        }

        return true;
    }

    /**
     * Enforces product
     * <p>
     * Setting the Inactivation Date is handled by the super class: {@link #handleInactivation(OrderableItemVo)}.
     * 
     * @param product The ProductVo to enforce
     * @param user UserVo for which to update
     * @throws ValidationException exception
     */
    private void enforceInactivationRules(ProductVo product, UserVo user) throws ValidationException {
        if (ItemStatus.INACTIVE.equals(product.getItemStatus())) {
            try {

                // PEPS 1406
                for (NdcVo partialNdc : product.getNdcs()) {

                    NdcVo ndc = (NdcVo) getManagedItemCapability().retrieve(partialNdc.getId(), partialNdc.getEntityType());

                    if (ndc.getItemStatus().isActive()) {
                        LOG.debug("Product inactivated, inactivating Child NDC " + ndc.getNdc());
                        ndc.inactivate();
                        getManagedItemCapability().update(ndc, user);
                        getManagedItemCapability().createInactivationItemAuditHistory(ndc, user,
                                                                                      "Parent Product was Inactivated");
                    }
                }

                OrderableItemVo oi =
                        (OrderableItemVo) getManagedItemCapability().retrieve(product.getOrderableItem().getId(),
                                                                              product.getOrderableItem().getEntityType());

                if (isLastActiveProductOnOi(oi, product.getId())) {
                    LOG.debug("Last Product inactivated, therefore inactivating parent OI");
                    oi.inactivate();
                    getManagedItemCapability().update(oi, user);
                    getManagedItemCapability().createInactivationItemAuditHistory(oi, user,
                                                                                  "Last Child Product was Inactivated");
                } else {
                    LOG.debug("Last Product NOT inactivated, therefore OI status left active");
                }
            } catch (ItemNotFoundException e) {
                LOG.error(e);
            }
        }
    }

    /**
     * Compute fields that rely on information outside of the ProductVo itself.
     * 
     * @param product The ProductVo to compute fields upon.
     * @param user The User authorized to perform this operation.
     * @throws ValidationException exception
     */
    private void computeFields(ProductVo product, UserVo user) throws ValidationException {

        //if (product.isNewInstance()) {
        //    product.setSource(ProductDataSourceType.VA);
        //}

        // If the ATC choice is one-ATC mode, then auto-populate the ATC Canisters table.
        if (getEnvironment().isLocal() && product.isAtcChoiceOne()) {

            Long oneAtcCanister = product.getOneAtcCanister(); // Will be stamped on all ATC MDFs rows.

            Collection<AtcCanisterVo> atcCanisters = new ArrayList<AtcCanisterVo>();

            List<?> wardGroupsFromVista = getDomainService().getVistaDomain(FieldKey.WARD_GROUP_FOR_ATC);

            if (wardGroupsFromVista != null && !wardGroupsFromVista.isEmpty()) {

                Iterator iterWards = wardGroupsFromVista.iterator();

                while (iterWards.hasNext()) {
                    WardGroupForAtcVo nextWg = (WardGroupForAtcVo) iterWards.next();

                    AtcCanisterVo atc = new AtcCanisterVo();
                    atc.setAtcCanister(oneAtcCanister);
                    atc.setWardGroupForAtc(nextWg);

                    atcCanisters.add(atc);
                }// end while

                product.setAtcCanisters(atcCanisters);
            }// end if
        }// end if

        // compute dose field for possible dosages
        computeDoseForPossibleDosagesField(product);
    }

    /**
     * This method auto calculates the dose field of for each possible dosage in Possible Dosages MDF dose field is
     * calculated by multiplying the ingredient strength of the single-ingredient product with the dispense unit per dose of
     * the possible dose
     * 
     * 
     * @param product the product
     */
    private void computeDoseForPossibleDosagesField(ProductVo product) {
        if (product.getActiveIngredients().size() > 0) {
           
            // if possible dosages is present auto-calculate the dose field for each row of this MDF
            // dose field is
            // get the ingredient strength of the product
            List<ActiveIngredientVo> lstActiveIngs = new ArrayList<ActiveIngredientVo>();
            lstActiveIngs.addAll(product.getActiveIngredients());
            ActiveIngredientVo activeIng = lstActiveIngs.get(0);

            if (activeIng.getStrength() != null) {
                if (activeIng.getStrength().matches("-?\\d+(.\\d+)?")) { 

                    Double strength = Double.parseDouble(activeIng.getStrength());
                    
                    Collection<NationalPossibleDosagesVo> colPossibleDosages = product.getNationalPossibleDosages();

                    if ((colPossibleDosages != null) && (!colPossibleDosages.isEmpty())) {
                        for (NationalPossibleDosagesVo possibleDosage : colPossibleDosages) {
                            Double dose = possibleDosage.getPossibleDosagesDispenseUnitsPerDose() * strength;
                            possibleDosage.setDose(dose);
                        }
                    }
                }
             
            }
        }

    }

    /**
     * 
     * 
     * @param oi orderableItemVo
     * @param inactivatedProductId inactivated product id
     * @return true if there are no
     */
    private boolean isLastActiveProductOnOi(OrderableItemVo oi, String inactivatedProductId) {
        if (oi.getProductCount() > 0) {
            ManagedItemDomainCapability domainCapability = getManagedItemDomainCapability(EntityType.PRODUCT);
            Collection<ProductVo> products = domainCapability.retrieveMinimalChildren(oi.getId());

            for (ProductVo oiProduct : products) {
                if (ItemStatus.ACTIVE.equals(oiProduct.getItemStatus()) && !(oiProduct.getId().equals(inactivatedProductId))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * If there is only one drug class, makes it primary.
     * 
     * @param product The product to check.
     */
    private void checkForOneDrugClass(ProductVo product) {
        if (product.getDrugClasses().size() == 1) {
            product.getDrugClasses().iterator().next().setPrimary(true);
        }
        
       
        
    }

    /**
     * Check the given <ManagedItemVo> for warnings.
     * <p>
     * There are two types of warnings handled via this method: warnings that are triggered only when a specific field(s) is
     * changed, and warnings triggered by field settings regardless of if these fields have been changed or not. Clients
     * should provide a difference collection of fields changed when they submit their changes. When they commit their
     * changes however, they should pass a null difference collection (so only non-field-change-triggered warnings are
     * checked for).
     * <p>
     * Checks if the VA Print Name is already mapped to an existing CMOP ID.
     * 
     * @param item <ManagedItemVo>
     * @param modDifferences Collection of modification differences already applied to the specified item, may be null.
     * @param newAdd this boolean is true when the item is a newly added item. this will be true in create method and false
     *            in other methods
     * @return {@link Errors} representing warnings
     * @throws ValidationException exception
     */
    @Override
    public Errors checkForWarnings(ProductVo item, Collection<ModDifferenceVo> modDifferences, boolean newAdd)
        throws ValidationException {
        Errors warnings = super.checkForWarnings(item, modDifferences, newAdd);

        // check for possible dosages related warnings while saving a new product item
        if (newAdd && item.isSingleIngredient() && (productIngredientUnitOIDosageFormUnitMatch(item) == null)) {

            // add warning
            warnings.addError(ErrorKey.PRODUCT_INGREDIENTUNIT_OI_DOSAGEFORMUNIT_MISMATCH);
        }

        // The VA Print Name warning message should only be showed in four specific situations.
        //
        // 1. When adding a new PENDING item and the VA Print Name already exists in the old VA Print Name table.
        // 2. When approving a new PENDING item request and the VA Print Name already exists in the old VA Print Name table.
        // 3. When making a national modification to a product and AND one of the fields being modified is the VA Print Name 
        // or the Dispense Unit AND the new VA Print Name/Dispense Unit already exists in the old VA Print Name table.
        // 4. When approving a modification request for a product AND one of the fields being modified is the VA Print Name or
        // dipsense unit AND the new VA Print Name already exists in the old VA Print Name table.
        ModDifferenceVo vaPrintNameMod = FieldKeyUtility.getAcceptedModDifference(FieldKey.VA_PRINT_NAME, modDifferences);
        ModDifferenceVo dispenseUnitMod = FieldKeyUtility.getAcceptedModDifference(FieldKey.DISPENSE_UNIT, modDifferences);
        
        String vaPrintName;
        String dispenseUnit;

        if (vaPrintNameMod == null) {
            vaPrintName = item.getVaPrintName();
        } else {
            vaPrintName = (String) vaPrintNameMod.getDifference().getNewValue();
        }
        
        if (dispenseUnitMod == null) {
            if (item.getDispenseUnit() == null) {
                dispenseUnit = null;
            } else {
                dispenseUnit = item.getDispenseUnit().getValue();
            }
        } else {
            dispenseUnit = ((DispenseUnitVo) dispenseUnitMod.getDifference().getNewValue()).getValue();
        }

        if (RequestItemStatus.APPROVED.equals(item.getRequestItemStatus()) 
            && (vaPrintNameMod != null || dispenseUnitMod != null)) {
            if (cmopIdExists(vaPrintName, dispenseUnit)) {
                warnings.addError(ErrorKey.CMOP_ID_EXISTS, vaPrintName + ":" + dispenseUnit, getCmopId(vaPrintName, 
                    item.getDispenseUnit().getValue()));
            }
        } else if (RequestItemStatus.PENDING.equals(item.getRequestItemStatus())
            || (getEnvironment().isNational() && (vaPrintNameMod != null) || dispenseUnitMod != null)) {
            
            // Print name may be null if item is pending
            if (vaPrintName != null && dispenseUnit != null) {
                if (cmopIdExists(vaPrintName, dispenseUnit)) {
                    warnings.addError(ErrorKey.CMOP_ID_EXISTS, vaPrintName + ":" + dispenseUnit, getCmopId(vaPrintName, 
                        item.getDispenseUnit().getValue()));
                }
            }
        }
    

//        // Check for ATC-related warnings.
//        // Warning shown: The ATC Choice (under Application Data -> Inpatient - Unit Dose) is
//        // selected but Product is not marked for Local Use and/or the Unit Dose package use.
//        // See 'PEPS1511: Provide Indication When ATC Required Fields Are Missing Values - Mulitple ATCs'.
//        if (getEnvironment().isLocal()) {
//
//            // If not marked for local use and/or not marked for unit dose package use, and
//            // the ATC choice is selected, warn user.
//            if (!item.isMarkedForUnitDose() && !item.isAtcChoiceNotSelected()) {
//                warnings.addError(ErrorKey.ATC_MODE_SELECTED_WITHOUT_MARKING);
//            }
//
//        }
//
//        checkLocalUse(item, warnings);
        
        if (item.getNdcs() != null && item.getNdcs().size() > 0 && item.getNdcs().get(0) != null 
            && item.getNdcs().get(0).getFdbNdcVo() != null) {
            if (item.getNdcs().get(0).getFdbNdcVo().getGcnSeqno() != null && item.getGcnSequenceNumber() != null) {
                if (!(item.getNdcs().get(0).getFdbNdcVo().getGcnSeqno().equals(new Long(item.getGcnSequenceNumber())))) {
                    Object[] args = { item.getNdcs().get(0).getFdbNdcVo().getGcnSeqno(), item.getGcnSequenceNumber()};
                    warnings.addError(ErrorKey.GCN_FDBSEQNUM_NOMATCH, args);
                }
            }
        }
        
        if (item.getPrimaryDrugClass() == null
            || (item.getPrimaryDrugClass().getCode().startsWith("X") && !item.getCategories().contains(Category.SUPPLY))
            || (!item.getPrimaryDrugClass().getCode().startsWith("X") && item.getCategories().contains(Category.SUPPLY))) {
            Object[] args = { item.getCategories(),
                (item.getPrimaryDrugClass() == null ? "NONE" : item.getPrimaryDrugClass().toShortString()) };
            warnings.addError(ErrorKey.CATEGORY_PRIMARY_DRUG_CLASS_SUPPLY_MISMATCH, args);
        }

        return warnings;
    }

    /**
     * If the parent OI is not marked for local use, but the product is, create a warning message.
     * 
     * @param item {@link ProductVo}
     * @param warnings current list of warnings on which to add new warning, if applicable
     */
    private void checkLocalUse(ProductVo item, Errors warnings) {

        // Assuming that the parent item has local use populated
        OrderableItemVo parent = item.getOrderableItem();
        boolean parentLocalUse = parent.isLocalUse();
        boolean productLocalUse = item.isLocalUse();

        if (!parentLocalUse && productLocalUse) {
            warnings.addError(ErrorKey.OI_ALREADY_MAKRED_FOR_LOCAL_USE, parent.getId(), parent.getOiName());
        }
    }

    /**
     * Update the given <ManagedItemVo> as necessary when a request completes.
     * <p>
     * This method only updates the ValueObject instance, not the database!
     * <p>
     * If a CMOP ID does not already exist for the given Product, create one and set it on the returned {@link ProductVo}.
     * Also, check the rules for auto creation of Possible dosages If the criteria are met, then create possible dosages for
     * the product item
     * 
     * @param request {@link RequestVo}
     * @param item <ManagedItemVo>
     * @param modDifferences Collection of {@link ModDifferenceVo}
     * @param user {@link UserVo} performing the action
     * @return updated <ManagedItemVo>
     * 
     * @throws ValidationException ValidationException
     */
    @Override
    public ProductVo processCompletedRequest(RequestVo request, ProductVo item, Collection<ModDifferenceVo> modDifferences,
                                             UserVo user) throws ValidationException {

        ProductVo updatedItem = super.processCompletedRequest(request, item, modDifferences, user);

        if (FieldKeyUtility.isFieldModified(FieldKey.VA_PRINT_NAME, modDifferences) 
            || FieldKeyUtility.isFieldModified(FieldKey.DISPENSE_UNIT, modDifferences) 
            || (item.getCmopId() == null)
            || (item.getCmopId().trim().length() == 0)) {
            
            // only check if the item does not have an inactivation date.
            DataField<Date> proposedInativationDate =
                item.getVaDataFields().getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);
            
            if (item.getInactivationDate() == null && proposedInativationDate.getValue() == null) {
                String vaPrintName = updatedItem.getVaPrintName();
                String dispenseUnit = updatedItem.getDispenseUnit().getValue();
                String cmopId;
    
                if (cmopIdExists(vaPrintName, dispenseUnit)) {
                    cmopId = getCmopId(vaPrintName, dispenseUnit);
                } else {
                    cmopId = createCmopId(vaPrintName, dispenseUnit, user, item.getCategories());
                }
    
                updatedItem.setCmopId(cmopId);
            }
        }

        // check for the criteria to automatically add possible dosages
        // possible dosages is automatically added by the system when the item is active, approved, and a new item
        // In addition to the above defined criteria there are also other criteria that are defined in the
        // method checkPossibleDosagesAutoCreationCondition
        if ((RequestType.ADDITION.equals(request.getRequestType()))
            && (RequestItemStatus.APPROVED.equals(updatedItem.getRequestItemStatus()))
            && (ItemStatus.ACTIVE.equals(updatedItem.getItemStatus()))) {
            updatedItem = checkPossibleDosagesAutoCreationConditions(updatedItem);
        }

        return updatedItem;
    }

    /**
     * this method was made public to test the auto creation of possible dosages
     * 
     * automatically creates PossibleDosages when the following criteria hold product item is approved, active, and single
     * ingredient item DispenseUnit per dose multiple of the associated Oi's dosage form has an entry and package field of
     * that entry has a value
     * 
     * @param product the product item
     * @return ProductVo
     * @throws ValidationException exception
     */
    public ProductVo checkPossibleDosagesAutoCreationConditions(ProductVo product) throws ValidationException {

        // retrieve the full orderable parent of the product
        OrderableItemVo oi =
                (OrderableItemVo) getManagedItemCapability().retrieve(product.getOrderableItem().getId(),
                                                                      EntityType.ORDERABLE_ITEM);
        DosageFormVo dosageForm =
                (DosageFormVo) getManagedItemCapability().retrieve(oi.getDosageForm().getId(), EntityType.DOSAGE_FORM);

        if (RequestItemStatus.APPROVED.equals(product.getRequestItemStatus())
            && (ItemStatus.ACTIVE.equals(product.getItemStatus())) && product.isSingleIngredient()) {

            // check if the ingredient unit field of the product matches with the any dosage form
            // units entry of the units multiple
            // of the associated OI's dosageform vo
            // if find a match, then the matching dosage form unit should have a value for package field
            // also.....DispenseUnit per dose multiple of the associated Oi's dosage form has an entry and package field
            // of that entry has a value
            DosageFormUnitVo dosageFormUnitMatch = productIngredientUnitOIDosageFormUnitMatch(product);

            if ((dosageFormUnitMatch != null) && (!dosageFormUnitMatch.getPackages().isEmpty())
                && !findAllDispUnitPerDoseWithPackage(dosageForm).isEmpty()) {

                // create the possible dosages
                createPossibleDosages(product, findAllDispUnitPerDoseWithPackage(dosageForm), dosageFormUnitMatch);
            }

        }

        return product;
    }

    /**
     * checks if the criteria for manual creation of possible dosages are met
     * 
     * @param product the product item
     * @return boolean
     * @throws ValidationException exception
     */
    private boolean checkPossibleDosagesManualCreationConditions(ProductVo product) throws ValidationException {
        if (RequestItemStatus.APPROVED.equals(product.getRequestItemStatus())
            && (ItemStatus.ACTIVE.equals(product.getItemStatus())) && product.isSingleIngredient()) {

            // check if the ingredient unit field of the product matches with the any dosage form
            // units entry of the units multiple
            // of the associated OI's dosageform vo
            // if find a match, then the matching dosage form unit should have a value for package field
            DosageFormUnitVo dosageFormUnitMatch = productIngredientUnitOIDosageFormUnitMatch(product);

            if ((dosageFormUnitMatch != null) && (!dosageFormUnitMatch.getPackages().isEmpty())) {

                return true;
            }// end if

        }// end if

        return false;
    }

    /**
     * Insert the new product from National into Local.
     * <p>
     * Default implementation sets local use to false, enforces business rules, and inserts the National item.
     * 
     * @param nationalItem {@link ProductVo} from National
     * @param user {@link UserVo} performing operation
     * @return inserted {@link ProductVo}
     * @throws ValidationException if error validating data during update
     * 
     * this method calls all the statements in enforceRules and handleEnforceRules I had to break these methods apart to
     * allow the automatic addition of local possible dosages to the product vo when it is inserted from national
     */
    @Override
    public ProductVo insertFromNational(ProductVo nationalItem, UserVo user) throws ValidationException {
        nationalItem.setLocalUse(false);
        nationalItem.setPreviouslyMarkedForLocalUse(false);

        nationalItem.defaultLocalOnlyFields();

        nationalItem.validate(user, getEnvironment());
        enforceParentChildRelationships(nationalItem, user);
        handleInactivation(nationalItem);

        checkForOneDrugClass(nationalItem);
        enforceInactivationRules(nationalItem, user);
        computeFields(nationalItem, user);
        createLocalPossibleDosagesAutomatically(nationalItem);
        createLabsAndVitalsAutomatically(nationalItem);

        return (ProductVo) getManagedItemCapability().insertItem(nationalItem, user);
    }

    /**
     * DF149: Non-VA Med
     * <ul>
     * <li>Rule 2: The value of the Non-VA Med must be set to "Y" if at least one "Active" product item associated with the
     * orderable item has Application Package Use of "Non-VA Med".</li>
     * <li>Rule 3: The value of the Non-VA Med must be set to "N" if all "Active" product item(s) associated with the
     * orderable item do not have Application Package Use of "Non-VA Med".</li>
     * </ul>
     * 
     * @param product the Product
     * @param user {@link UserVo} performing operation
     * 
     * @throws ValidationException exception
     */
    private void enforceNonVAMedRules(ProductVo product, UserVo user) throws ValidationException {
        boolean nonVaMed = hasActiveNonVaMedSiblings(product);

        OrderableItemVo orderableItem =
                (OrderableItemVo) getManagedItemCapability().retrieve(product.getOrderableItem().getId(),
                                                                      EntityType.ORDERABLE_ITEM, user);
        orderableItem.setNonVaMed(nonVaMed);
        getManagedItemCapability().update(orderableItem, user);
    }

    /**
     * If this product or one of its "siblings" has Application Package Use set to "X-NON-VA MED", return true.
     * <p>
     * A "sibling" is any product on the parent OI.
     * 
     * @param product ProductVo being added/modified
     * @return boolean
     */
    private boolean hasActiveNonVaMedSiblings(ProductVo product) {
        for (ProductVo sibling : getSiblings(product)) {
            if (isActiveNonVaMed(sibling)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieve the product's "siblings", replacing the product with the same ID as the one modified with the given product.
     * <p>
     * A "sibling" is any product on the parent OI.
     * 
     * @param product added/modified ProductVo
     * @return Collection of "sibling" {@link ProductVo}
     */
    private Collection<ProductVo> getSiblings(ProductVo product) {
        Collection<ProductVo> siblings =
                getManagedItemCapability().retrieveChildren(product.getOrderableItem().getId(), EntityType.ORDERABLE_ITEM);

        // If this is a new Product, it won't be in the list of children. Otherwise, replace the retrieved Product with the
        // one modified.
        if (product.isNewInstance()) {
            siblings.add(product);
        } else {
            Map<String, ProductVo> filtered = new HashMap<String, ProductVo>(siblings.size());

            for (ProductVo sibling : siblings) {
                filtered.put(sibling.getId(), sibling);
            }

            filtered.put(product.getId(), product);
            siblings = filtered.values();
        }

        return siblings;
    }

    /**
     * this method checks if the application package use field of the product is non-va med
     * 
     * @param product the Product
     * @return boolean value
     */
    private boolean isActiveNonVaMed(ProductVo product) {
        ListDataField<String> applicationPkgUse = product.getVaDataFields().getDataField(FieldKey.APPLICATION_PACKAGE_USE);

        if (ItemStatus.ACTIVE.equals(product.getItemStatus()) && applicationPkgUse != null
            && applicationPkgUse.getValue() != null && !applicationPkgUse.getValue().isEmpty()
            && applicationPkgUse.getValue().contains("X-NON-VA MED")) {

            return true;
        }

        return false;
    }

    /**
     * If the ATC Mnemonic value is specified, and another Product is found with this same value, then throw a validation
     * exception.
     * 
     * @param product the Product
     * @throws ValidationException if error
     */
    private void enforceUniqueAtcMnemonic(ProductVo product) throws ValidationException {
        String atcMnemonic = product.getAtcMnemonic();

        if (atcMnemonic != null && atcMnemonic.trim().length() > 0) {
            boolean hasDuplicate = productDomainCapability.hasDuplicateAtcMnemonic(atcMnemonic.trim(), product.getId());

            if (hasDuplicate) {
                Errors errors = new Errors();

                errors.addError(FieldKey.ATC_MNEMONIC, ErrorKey.ATC_MNEMONIC_DUPLICATE_FOUND, atcMnemonic.trim());

                throw new ValueObjectValidationException(errors);
            }
        }
    }

    /**
     * this method was made public so that it can be tested
     * 
     * this method enforces all the rules for manual creation of possible dosages at this time we are not doing the duplicate
     * check b/c there is no requirement for this
     * 
     * @param product the product
     * @param modDifferences the collection of ModDifferenceVo
     * @throws ValidationException ValidationException
     */
    public void enforcePossibleDosagesRules(ProductVo product, Collection<ModDifferenceVo> modDifferences)
        throws ValidationException {
        Errors errors = new Errors();

        // if new possible dosage has been added check the possible dosage manual creation rules
        if (possibleDosageAdded(product)) {
            if (!checkPossibleDosagesManualCreationConditions(product)) {
                errors.addError(FieldKey.NATIONAL_POSSIBLE_DOSAGES, ErrorKey.NATIONAL_POSSIBLE_DOSAGES_ADD);
            }

        }// end if

        // if possible dosages is modified check the mod is valid. this is a modification
        if ((modDifferences != null) && (!modDifferences.isEmpty()) && !validatePossibleDosageMod(product, modDifferences)) {
            errors.addError(FieldKey.NATIONAL_POSSIBLE_DOSAGES, ErrorKey.NATIONAL_POSSIBLE_DOSAGES_MOD);
        }

        
        
        
        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
    }

    /**
     * This method figures out if a new possible dosage row has been added
     * 
     * @param product the product
     * @return boolean
     */
    private boolean possibleDosageAdded(ProductVo product) {
        boolean blnAdded = false;
        Collection<NationalPossibleDosagesVo> colPossibleDosages = product.getNationalPossibleDosages();

        if ((colPossibleDosages != null) && (!colPossibleDosages.isEmpty())) {

            // iterate through the list of possible dosages
            // if find a possible dose with null id, then that is manually added
            for (NationalPossibleDosagesVo possibleDosage : colPossibleDosages) {
                if (possibleDosage.getId() == null) {
                    blnAdded = true;
                    break;
                } // end if
            }// end for

        }// end if

        return blnAdded;
    }

    /**
     * this method finds all the entries of dispense unit per dose multiple of the Dosage form of the associated OI that have
     * values for package field
     * 
     * @param dosageForm the dosage form of the orderable item parent
     * @return Collection<DispenseUnitPerDoseVo>
     */
    private Collection<DispenseUnitPerDoseVo> findAllDispUnitPerDoseWithPackage(DosageFormVo dosageForm) {
        Collection<DispenseUnitPerDoseVo> dispUnitsPerDose = dosageForm.getDfDispenseUnitsPerDose();
        List<DispenseUnitPerDoseVo> lstDispUnitPerDoseWithPackage = new ArrayList<DispenseUnitPerDoseVo>();

        for (DispenseUnitPerDoseVo dispUnitPerDose : dispUnitsPerDose) {
            if ((dispUnitPerDose.getPackages() != null) && !dispUnitPerDose.getPackages().isEmpty()) {
                lstDispUnitPerDoseWithPackage.add(dispUnitPerDose);
            }
        }

        return lstDispUnitPerDoseWithPackage;
    }

    /**
     * this method finds all the entries of nouns multiple of the Dosage form of the associated OI that have values for
     * package field
     * 
     * @param dosageForm the dosage form of the OrderableItem Parent
     * @return Collection<DosageFormNounVo>
     * @throws ValidationException exception
     */
    private Collection<DosageFormNounVo> findAllNounsWithPackage(DosageFormVo dosageForm) throws ValidationException {
        Collection<DosageFormNounVo> dfNouns = dosageForm.getDfNouns();
        List<DosageFormNounVo> lstDfNounsWithPackage = new ArrayList<DosageFormNounVo>();

        // iterate through the nouns collection and find all the nouns that have value in the
        // package field

        for (DosageFormNounVo dfNoun : dfNouns) {
            if ((dfNoun.getPackages() != null) && !dfNoun.getPackages().isEmpty()) {
                lstDfNounsWithPackage.add(dfNoun);
            }
        }

        return lstDfNounsWithPackage;
    }

    /**
     * this method automatically creates local possible dosages when the auto creation criteria are met the local possible
     * dosages are automatically created when a new product item is added
     * 
     * @param product the product
     * @throws ValidationException exception
     */
    public void createLocalPossibleDosagesAutomatically(ProductVo product) throws ValidationException {
        Collection<LocalPossibleDosagesVo> colLocalPossibleDosages = new ArrayList<LocalPossibleDosagesVo>();
        OrderableItemVo oi =
                (OrderableItemVo) getManagedItemCapability().retrieve(product.getOrderableItem().getId(),
                                                                      EntityType.ORDERABLE_ITEM);
        DosageFormVo dosageForm =
                (DosageFormVo) getManagedItemCapability().retrieve(oi.getDosageForm().getId(), EntityType.DOSAGE_FORM);

        if ((product.getLocalPossibleDosages() != null) && (!product.getLocalPossibleDosages().isEmpty())) {
            colLocalPossibleDosages.addAll(product.getLocalPossibleDosages());
        }

        if ((!findAllNounsWithPackage(dosageForm).isEmpty()) && (!findAllDispUnitPerDoseWithPackage(dosageForm).isEmpty())) {

            colLocalPossibleDosages.addAll(populateLocalPossibleDosages(findAllNounsWithPackage(dosageForm),
                                                                        findAllDispUnitPerDoseWithPackage(dosageForm)));
        }

        product.setLocalPossibleDosages(colLocalPossibleDosages);
    }

    /**
     * this method automatically creates labs and vitals from the orderable items labs and vitals
     * 
     * @param product the product
     */
    private void createLabsAndVitalsAutomatically(ProductVo product) {

        // get the orderable item for product

        try {
            OrderableItemVo oi =
                    (OrderableItemVo) getManagedItemCapability().retrieve(product.getOrderableItem().getId(),
                                                                          product.getOrderableItem().getEntityType());
            product.setLabDisplayAdministration(oi.getLabDisplayAdministration());
            product.setLabDisplayFinishingAnOrder(oi.getLabDisplayFinishingAnOrder());
            product.setLabDisplayOrderEntry(oi.getLabDisplayOrderEntry());
            product.setVitalsDisplayOrderEntry(oi.getVitalsDisplayOrderEntry());
            product.setVitalsDisplayFinishAnOrder(oi.getVitalsDisplayFinishAnOrder());
            product.setVitalsDisplayAdministration(oi.getVitalsDisplayAdministration());

        } catch (ItemNotFoundException e) {
            LOG.error(e);
        }

    }

    /**
     * this method populates the local possible dosages from noun and dispense unit per dose MDFs
     * 
     * a new entry in the Local Possible Dosage field (of the Local Possible Dosage multiple) is created with the value of
     * the Dispense Units per Dose field (of each entry in Dispense Units per Dose multiple) concatenated with the value of
     * the Noun field (of each entry in the Noun multiple) of the associated orderable item’s Dosage Form when the the auto
     * creation criteria are met
     * 
     * Each entry in the Dispense Unit per Dose multiple will be combined with each entry in the Noun multiple. For example,
     * if Dispense Units per Dose multiple has 2 valid entries of “1” and “2”and the Noun multiple has 3 valid entries, then
     * 6 entries of local possible dose will be creates
     * 
     * @param dfNouns the nouns multiple
     * @param dfDispUnits the dispense units per dose multiple
     * @return Collection<LocalPossibleDosagesVo>
     */
    private Collection<LocalPossibleDosagesVo> populateLocalPossibleDosages(Collection<DosageFormNounVo> dfNouns,
                                                                            Collection<DispenseUnitPerDoseVo> dfDispUnits) {
        Collection<LocalPossibleDosagesVo> colLocalPossibleDosages = new ArrayList<LocalPossibleDosagesVo>();

        for (DosageFormNounVo noun : dfNouns) {
            for (DispenseUnitPerDoseVo dispUnit : dfDispUnits) {
                LocalPossibleDosagesVo localPossibleDosage = new LocalPossibleDosagesVo();
                localPossibleDosage.setLocalPossibleDosage(makeLocalPossibleDosageField(dispUnit.getStrDispenseUnitPerDose(),
                                                                                        noun.getNoun()));
                localPossibleDosage.setPossibleDosagePackage(noun.getPackages());
                colLocalPossibleDosages.add(localPossibleDosage);
            }
        }

        return colLocalPossibleDosages;
    }

    /**
     * this method populates the local possible dosage field for each row in the Local Possible Dosages MDF local possible
     * dosage is the combination of dispense unit per dose and noun drop the "(S)" or "(s)" from the value of the Noun field
     * used in the Local Possible Dosage field (of the Local Possible Dosage multiple) to create a singular value when the
     * following criteria are met: The value of Noun field (of the Noun multiple) ends in a (S)" or "(s)"; and The value of
     * Dispense Units per Dose field (of the Dispense Units per Dose multiple) is 1 or less. when the value of dispense unit
     * per dose is greater than 1, then drop the parenthesis around "s"
     * 
     * @param dispUnitPerDose the dispense units per dose in Dispense Units Per Dose MDF
     * @param noun in Noun MDF
     * @return local possible dosage field
     */
    private String makeLocalPossibleDosageField(String dispUnitPerDose, String noun) {

        double dblDispUnitsPerDose = Double.parseDouble(dispUnitPerDose);
        StringBuffer bufNoun = new StringBuffer(noun.trim());
        String strNoun = "";

        // use regular expression to find out is the noun ends with "(s)" or "(S)"
        Pattern p = Pattern.compile("\\([sS]\\)$");
        Matcher matcher = p.matcher(noun.trim());
        boolean matchFound = matcher.find();

        // if the value of dispense units per dose is less than equal to 1 and value of noun field ends in (s) or (S), then
        // delete "(s)". when the value of dispense units per dose is greater than 1, drop the parenthesis around s

        if (matchFound) {
            if (dblDispUnitsPerDose > 1) {
                bufNoun.deleteCharAt(bufNoun.length() - 1);
                bufNoun.deleteCharAt(bufNoun.length() - 2);
                strNoun = bufNoun.toString();
            } else {
                strNoun = bufNoun.substring(0, bufNoun.length() - PPSConstants.I3);
            }
        }

        String localPossibleDosageField = dispUnitPerDose + " " + strNoun;

        return localPossibleDosageField;
    }

    /**
     * This method automatically creates the Possible dosages entry for the product when certain criteria are met
     * 
     * @param product the product item
     * @param dispUnitsPerDose the collection of dispense units per dose from the dispense unit per dose multiple of the
     *            associated OI's dosage form
     * @param dosageFormUnit the matching dosage form unit from the units multiple of the associated OI's dosage form
     */
    private void createPossibleDosages(ProductVo product, Collection<DispenseUnitPerDoseVo> dispUnitsPerDose,
                                       DosageFormUnitVo dosageFormUnit) {

        // this list contains all the existing possible dosages
        List<NationalPossibleDosagesVo> lstPossibleDosages = new ArrayList<NationalPossibleDosagesVo>();

        // create a possible dose entry for each entry of dispense unit per dose in the
        // dispense units per dose list
        for (DispenseUnitPerDoseVo dispUnitPerDose : dispUnitsPerDose) {
            NationalPossibleDosagesVo possibleDose = new NationalPossibleDosagesVo();
            possibleDose
                    .setPossibleDosagesDispenseUnitsPerDose(Double.parseDouble(dispUnitPerDose.getStrDispenseUnitPerDose()));
            possibleDose.setPossibleDosagePackage(dosageFormUnit.getPackages());
            lstPossibleDosages.add(possibleDose);

        }

        product.setNationalPossibleDosages(lstPossibleDosages);
    }

    /**
     * this method checks is the ingredient unit field of the single-ingredient product matches with any of the dosage form
     * unit of the units multiple of the associated OI's dosageform
     * 
     * @param product the product item
     * @return DosageFormUnitVo
     * @throws ValidationException ValidationException
     */
    private DosageFormUnitVo productIngredientUnitOIDosageFormUnitMatch(ProductVo product) throws ValidationException {
        DosageFormVo dosageForm =
                (DosageFormVo) getManagedItemCapability().retrieve(product.getOrderableItem().getDosageForm().getId(),
                                                                   EntityType.DOSAGE_FORM);
        Collection<DosageFormUnitVo> dfUnits = dosageForm.getDfUnits();
        List<ActiveIngredientVo> activeIngs = new ArrayList<ActiveIngredientVo>();
        activeIngs.addAll(product.getActiveIngredients());
        
        DosageFormUnitVo matchingDosageFormUnit = null;
        
        if (dfUnits.isEmpty() || activeIngs.isEmpty()) {
            return null;
        }
        
        for (ActiveIngredientVo activeIng : activeIngs) {
            if (activeIng.getDrugUnit() != null) {
                for (DosageFormUnitVo dfUnit : dfUnits) {
                    if (dfUnit.getDrugUnit().getValue().equals(activeIng.getDrugUnit().getValue())) {
                        matchingDosageFormUnit = dfUnit;
                        break;
                    }
                }
            }
        }


        return matchingDosageFormUnit;

    }

    /**
     * Create a CMOP ID for a VA Print Name that does not yet have a CMOP ID.
     * <p>
     * Be sure the {@link #cmopIdExists(String)} returns false before invoking this method!
     * 
     * @param vaPrintName String
     * @param dispenseUnit String
     * @param user {@link UserVo} performing the action
     * @param categories categories of the product
     * @return CMOP ID
     */
    private String createCmopId(String vaPrintName, String dispenseUnit, UserVo user, List<Category> categories) {
        return productDomainCapability.createCmopId(vaPrintName, dispenseUnit, user, categories);
    }

    /**
     * Call the {@link ProductDomainCapability} to get the CMOP ID for the given VA Print Name.
     * 
     * @param vaPrintName String
     * @param dispenseUnit String
     * @return CMOP ID
     */
    private String getCmopId(String vaPrintName, String dispenseUnit) {
        return productDomainCapability.getCmopIdForVaPrintName(vaPrintName, dispenseUnit);
    }

    /**
     * Return true if the given VA Print Name has a non-null and non-zero length CMOP ID.
     * 
     * @param vaPrintName String
     * @param dispenseUnit String
     * @return boolean
     */
    private boolean cmopIdExists(String vaPrintName, String dispenseUnit) {
        String cmopId = productDomainCapability.getCmopIdForVaPrintName(vaPrintName, dispenseUnit);

        return cmopId != null && cmopId.trim().length() > 0;
    }

    /**
     * setProductDomainCapability
     * @param productDomainCapability productDomainCapability property
     */
    public void setProductDomainCapability(ProductDomainCapability productDomainCapability) {
        this.productDomainCapability = productDomainCapability;
    }
    
    /**
     * setDrugUnitDomainCapability
     * @param drugUnitDomainCapability drugUnitDomainCapability property
     */
    public void setDrugUnitDomainCapability(DrugUnitDomainCapability drugUnitDomainCapability) {
        this.drugUnitDomainCapability = drugUnitDomainCapability;
    }
}
