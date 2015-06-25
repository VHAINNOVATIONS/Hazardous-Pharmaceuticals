/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.DomainException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryDetailsVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.domain.common.capability.IntendedUseDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;


/**
 * Enforce NDC business rules
 */
public class NdcRulesCapabilityImpl extends DefaultRulesCapabilityImpl<NdcVo> {
    private static final String NDC_DELIMITER = "-";

    private IntendedUseDomainCapability intendedUseDomainCapability;

    /**
     * Enforce additional NDC rules beyond simple ValueObject validation.
     * 
     * @param ndc {@link NdcVo} to validate
     * @param user UserVo for which to validate
     * @param canPerformUpdate boolean indicating if the call is allowed to perform updates on other items     
     * @throws ValidationException if general validation error
     */
    @Override
    protected void handleEnforceRules(NdcVo ndc, UserVo user, boolean canPerformUpdate)
        throws ValidationException {

        Errors errors = new Errors();

        ndc.setNdc(padNdcWithLeadingZero(NDC_DELIMITER, ndc.getNdc()));
        padPreviousNdc(ndc);
        handleNdcPricingFields(ndc);

        ProductVo product = retrieveParentProduct(ndc);
        
        if (ndc.getRequestItemStatus().isApproved() && product.getRequestItemStatus().isPending()) {
            errors.addError(ErrorKey.PRODUCT_PENDING_NDC_APPROVED);
            throw new ValueObjectValidationException(errors);
        }
        
        try {
            if (ndc.getProduct().getItemStatus().isInactive() && ndc.getItemStatus().isActive()) {
                errors.addError(ErrorKey.PRODUCT_INACTIVE_NDC_ACTIVE);
                throw new ValueObjectValidationException(errors);
            }
        } catch (Exception e) {
     
            // do nothing if there is an exception.
        }
        
        if (canPerformUpdate) {
            
            // Only create a new synonym if the NDC is Active
            if (ndc.getItemStatus().equals(ItemStatus.ACTIVE)) {
                updateProductSynonym(ndc, user);
            }
        }
        
    }

    /**
     * Retrieves the parent product of the NDC
     *
     * @param ndc ndc
     * @return parent product
     * @throws ItemNotFoundException ItemNotFoundException
     */
    private ProductVo retrieveParentProduct(NdcVo ndc) throws ItemNotFoundException {
        ProductVo product = (ProductVo) getManagedItemCapability().retrieve(ndc.getParentId(), ndc.getParent().getEntityType());

        return product;
    }

    /**
     * Pad the NDC value with leading zeros if any of its segments are not already of the correct length.
     * <p>
     * DF144: NDC
     * <p>
     * Rule 3: Provide leading zeros in each segment of the NDC when a segment does not have the correct number of digits.
     * 
     * @param ndc {@link NdcVo}
     */
    private void padPreviousNdc(NdcVo ndc) {
        DataFields<DataField> vadf = ndc.getVaDataFields();
        DataField<String> previousNdc = vadf.getDataField(FieldKey.PREVIOUS_NDC);

        if ((previousNdc != null) && (previousNdc.getValue() != null) && (!previousNdc.getValue().isEmpty())) {

            if ((previousNdc.getValue() != null) && (previousNdc.getValue().trim().length() > 0)) {
                previousNdc.selectStringValue(padNdcWithLeadingZero(NDC_DELIMITER, previousNdc.getValue()));

            }

            
        }
    }


    /**
     * When the Unit Price or NDC Price Per Order Unit fields are empty, enter zero for the field value before saving the NDC
     * item.
     * <p>
     * For DF200: NDC Price Per Dispense Unit, enforce rules 3-5:
     * <ul>
     * <li>Rule 3: This field is calculated by dividing NDC Price per Order Unit (DF202) by NDC Dispense Unit per Order Unit
     * (DF331).</li>
     * <li>Rule 4: If NDC Price per Order Unit (DF202) is Empty or "0.0", then NDC Price per Dispense Unit must be set to
     * "0.00".</li>
     * Rule 5: If NDC Dispense Unit per Order Unit (DF331) is Empty or "0.0", then NDC Price per Dispense Unit must be set to
     * "0.00".</li>
     * </ul>
     * 
     * @param ndc NdcVo
     */
    private void handleNdcPricingFields(NdcVo ndc) {
        DataFields<DataField> dataFields = ndc.getVaDataFields();

        DataField<Double> unitPrice = dataFields.getDataField(FieldKey.UNIT_PRICE);

        if (unitPrice == null) {
            unitPrice = DataField.newInstance(FieldKey.UNIT_PRICE);
            dataFields.setDataField(unitPrice);
        }

        if (unitPrice.getValue() == null) {
            unitPrice.selectValue(Double.valueOf(0));
        }

        DataField<Double> ndcPricePerOrderUnit = dataFields.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT);

        if (ndcPricePerOrderUnit == null) {
            ndcPricePerOrderUnit = DataField.newInstance(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
            dataFields.setDataField(ndcPricePerOrderUnit);
        }

        if (ndcPricePerOrderUnit.getValue() == null) {
            ndcPricePerOrderUnit.selectValue(Double.valueOf(0));
        }

        DataField<Double> ndcPricePerDispenseUnit = dataFields.getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);

        if (ndcPricePerDispenseUnit == null) {
            ndcPricePerDispenseUnit = DataField.newInstance(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);
            dataFields.setDataField(ndcPricePerDispenseUnit);
        }

        if (ndcPricePerOrderUnit.getValue() == 0 || ndc.getNdcDispUnitsPerOrdUnit() == null
            || ndc.getNdcDispUnitsPerOrdUnit() == 0) {
            ndcPricePerDispenseUnit.selectValue(Double.valueOf(0));
        } else {
            
            // calculate the NDC PPOU and round/format it to four decimal digits
            double calculatedValue = ndcPricePerOrderUnit.getValue() / ndc.getNdcDispUnitsPerOrdUnit();
            double roundedValue = new BigDecimal(calculatedValue)
                .setScale(PPSConstants.I4, BigDecimal.ROUND_HALF_UP).doubleValue();
            DecimalFormat format = new DecimalFormat("0.####");
            String formatted = format.format(roundedValue);
            ndcPricePerDispenseUnit.selectValue(Double.valueOf(formatted));
        }
    }

    /**
     * Updates the synonyms table for the NDC's parent product
     * 
     * @param ndc NdcVo
     * @param user UserVo for which to update
     * @throws ValidationException exception
     */
    private void updateProductSynonym(NdcVo ndc, UserVo user) throws ValidationException {
        String newSynonym = ndc.getTradeName().toUpperCase(Locale.US);

        // Retrieve NDC's parent product
        ProductVo product = (ProductVo) getManagedItemCapability().retrieve(ndc.getProduct().getId(), EntityType.PRODUCT);

        // Get current list of synonyms
        Collection<SynonymVo> synonyms = product.getSynonyms();

        // get the ndc vadfs
        DataFields<DataField> vadfs = ndc.getVaDataFields();

        // Check if NDC's trade name is already in synonyms
        for (SynonymVo synonym : synonyms) {
            if (synonym.getSynonymName() != null && synonym.getSynonymName().equals(newSynonym)) {
                newSynonym = null;
                break;
            }
        }

        // If Trade name was not found in synonyms table
        if (newSynonym != null && newSynonym.length() != 0) {

            // Set synonym's properties
            SynonymVo synonymVo = new SynonymVo();
            synonymVo.setSynonymName(newSynonym);

            synonymVo.setNdcCode(ndc.getNdc());

            // vendor
            String vendor = ndc.getVendor();

            if ((vendor != null) && (vendor.trim().length() > 0)) {
                synonymVo.setSynonymVendor(vendor);
            }

            // vendor stock number
            String vsn = ndc.getVendorStockNumber();

            if ((vsn != null) && (vsn.trim().length() > 0)) {
                synonymVo.setSynonymVsn(vsn);
            }

            // order unit
            if (ndc.getOrderUnit() != null) {
                synonymVo.setSynonymOrderUnit(ndc.getOrderUnit());
            }

            // price per order unit
            DataField<Double> ndcPPOU = vadfs.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT);

            if ((ndcPPOU != null) && (ndcPPOU.getValue() != null) && (ndcPPOU.getValue().doubleValue() > 0)) {
                synonymVo.setSynonymPricePerOrderUnit(ndcPPOU.getValue());
            }

            // price per dispense unit
            DataField<Double> ndcPPDU = vadfs.getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);

            if ((ndcPPDU != null) && (ndcPPDU.getValue() != null) && (ndcPPDU.getValue().doubleValue() > 0)) {
                synonymVo.setSynonymPricePerDispenseUnit(ndcPPDU.getValue());
            }

            // NDC's disp units per ord unit is an optional field, make sure that the value is not null
            if (ndc.getNdcDispUnitsPerOrdUnit() != null) {
                synonymVo.setSynonymDispenseUnitPerOrderUnit(new Double(ndc.getNdcDispUnitsPerOrdUnit().longValue()));
            }

            synonymVo.setSynonymIntendedUse(getIntendedUse());

            // Insert trade name into synonyms table
//            synonyms.add(synonymVo);
//            product.setSynonyms(synonyms);

            // Update the parent product on this site only

         //   getManagedItemCapability().update(product, user);
            ProductDomainCapability productDomainCapbility = 
                (ProductDomainCapability) this.getManagedItemDomainCapability(EntityType.PRODUCT);
            productDomainCapbility.addSingleSynonym(synonymVo, product, user);
            
            ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
            audit.setReason("NDC Added with a new Trade Name");
            Collection<ItemAuditHistoryDetailsVo> collection = new ArrayList<ItemAuditHistoryDetailsVo>();
            ItemAuditHistoryDetailsVo detail = new ItemAuditHistoryDetailsVo();
            detail.setColumnName("Synonym");
            detail.setNewValue(synonymVo.toShortString());
            detail.setEventCategory(EventCategory.NATIONAL_MODIFICATION);
            detail.setDetailReason("NDC Added with new Trade Name");
            detail.setOldValue("");
            collection.add(detail);
            
            
            audit.setAuditItemId(product.getId());
            audit.setCreatedBy(user.getUsername());
            audit.setEventCategory(EventCategory.NATIONAL_MODIFICATION);
            audit.setDetailCategory(EventCategory.NATIONAL_MODIFICATION);
            audit.setAuditItemType(EntityType.PRODUCT);
            audit.setSiteName(user.getLocation());
            audit.setUsername(user.getUsername());
            
           
            
            if (StringUtils.isBlank(product.getGcnSequenceNumber())) {
                if (ndc.getFdbNdcVo() != null && ndc.getFdbNdcVo().getGcnSeqno() != null) {
                    product.setGcnSequenceNumber(ndc.getFdbNdcVo().getGcnSeqno().toString());
                    ndc.getParent().setGcnSequenceNumber(product.getGcnSequenceNumber());
                    ItemAuditHistoryDetailsVo detail2 = new ItemAuditHistoryDetailsVo();
                    detail2.setColumnName("GcnSeqNo");
                    detail2.setNewValue(product.getGcnSequenceNumber());
                    detail2.setEventCategory(EventCategory.NATIONAL_MODIFICATION);
                    detail2.setDetailReason("NDC Added that was matched to FDB Packaged Drug with GCN.");
                    detail2.setOldValue("");
                    collection.add(detail2);
                   
                    productDomainCapbility.updateGcnSeqNo(product, user);
                }
            }
            
            audit.setDetails(collection);
            getManagedItemCapability().saveItemAuditHistoryRecords(audit, user);
            
            
            
            


        }

    }

    @Override
    public Errors checkForWarnings(NdcVo item, Collection<ModDifferenceVo> modDifferences, boolean newAdd)
        throws ValidationException {

        Errors warnings = super.checkForWarnings(item, modDifferences, newAdd);

        ProductVo product = retrieveParentProduct(item);

        if (item.getRequestItemStatus().isPending() && product.getRequestItemStatus().isPending()) {
            warnings.addError(ErrorKey.PRODUCT_AND_NDC_PENDING);
        }
        
        
//REMOVED this code on 4/3 becaue the VA doesn't want this warning message.  
//Left it here in case they change their mind.
        
//        if (modDifferences != null && modDifferences.size() > 0) {
//            Iterator itr = modDifferences.iterator();
//       
//            while (itr.hasNext()) {
//              
//                ModDifferenceVo modDifferenceVo = (ModDifferenceVo) (itr.next());
//                Difference difference = modDifferenceVo.getDifference();
//            
//                if (difference.getFieldKey().equals(FieldKey.PRODUCT)) {
//                    ProductVo newValue = (ProductVo) difference.getNewValue();
//                    ProductVo oldValue = (ProductVo) difference.getOldValue();
//                    
//                    if (oldValue != null && newValue != null && oldValue.getGcnSequenceNumber() != null 
//                        && newValue.getGcnSequenceNumber() != null) {
//                        
//                        if (!(oldValue.getGcnSequenceNumber().equalsIgnoreCase(newValue.getGcnSequenceNumber()))) {
//                            Object[] args = { oldValue.getGcnSequenceNumber(), newValue.getVaProductName(), 
//                                newValue.getGcnSequenceNumber() };
//                            warnings.addError(ErrorKey.GCN_SEQNUM_NOMATCH, args);
//                        }
//                    }
//                }
//            }
//        }


        if (item.getParent().getGcnSequenceNumber() != null  
            && item.getFdbNdcVo() != null) {
            if (!(item.getFdbNdcVo().getGcnSeqno().equals(new Long(item.getParent().getGcnSequenceNumber())))) {
                String name = ((ProductVo) item.getParent()).getVaProductName();
                Object[] args = { item.getFdbNdcVo().getGcnSeqno(), name, item.getParent().getGcnSequenceNumber()};
                warnings.addError(ErrorKey.GCN_FDBSEQNUM_NOMATCH, args);
            }
        }

        return warnings;
    }

    /**
     * Get Intended user for Trade Name
     * 
     * 
     * @return IntendedUseVo
     * @throws ItemNotFoundException when intended use for trade name not found
     */
    private IntendedUseVo getIntendedUse() throws ItemNotFoundException {

        IntendedUseVo intendedUseVo = null;
        List<IntendedUseVo> intendedUseList = intendedUseDomainCapability.retrieveDomain();

        for (IntendedUseVo intendedUse : intendedUseList) {
            if (intendedUse.isTradeName()) {
                intendedUseVo = intendedUse;
                break;
            }
        }

        // Throw exception if item not found
        if (intendedUseVo == null) {
            throw new DomainException(DomainException.DATA_NOT_FOUND, new Object[] {"'Trade Name' Intended Use selection"});
        }

        return intendedUseVo;
    }

    /**
     * setIntendedUseDomainCapability
     * @param intendedUseDomainCapability intendedUseDomainCapability property
     */
    public void setIntendedUseDomainCapability(IntendedUseDomainCapability intendedUseDomainCapability) {
        this.intendedUseDomainCapability = intendedUseDomainCapability;
    }
}
