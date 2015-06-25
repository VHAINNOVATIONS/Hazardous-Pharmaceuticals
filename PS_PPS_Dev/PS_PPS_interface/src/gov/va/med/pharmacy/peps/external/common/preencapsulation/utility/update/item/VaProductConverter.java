/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesAutoCreate;
import gov.va.med.pharmacy.peps.common.vo.ProductPackage;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl.MigrationSynchFileCapabilityImpl;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vaproductsyncrequest.ActiveIngredientsType;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vaproductsyncrequest.DosageFormType;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vaproductsyncrequest.EffectiveDateTimeType;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vaproductsyncrequest.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vaproductsyncrequest.PrimaryVaDrugClassType;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vaproductsyncrequest.VaDispenseUnitType;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vaproductsyncrequest.VaGenericNameType;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vaproductsyncrequest.VaProductSyncRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.lang.StringUtils;


/**
 * VaProductConverter's brief summary
 * 
 * Converts a VA Product VO into a VA Product Document.
 *
 */
public class VaProductConverter extends AbstractConverter {

    /**
     * FIELDS
     */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.VA_PRODUCT_NAME, FieldKey.GENERIC_NAME, FieldKey.DOSAGE_FORM, FieldKey.PRODUCT_STRENGTH,
            FieldKey.PRODUCT_UNIT, FieldKey.TRANSMIT_TO_CMOP,
            FieldKey.ACTIVE_INGREDIENT, FieldKey.NATIONAL_FORMULARY_NAME, FieldKey.VA_PRINT_NAME,
            FieldKey.CMOP_ID, FieldKey.CMOP_DISPENSE, FieldKey.DISPENSE_UNIT,
            FieldKey.GCN_SEQUENCE_NUMBER, FieldKey.DRUG_CLASSES,
            FieldKey.NATIONAL_FORMULARY_INDICATOR, FieldKey.CS_FED_SCHEDULE,
            FieldKey.SINGLE_MULTISOURCE_PRODUCT, FieldKey.INACTIVATION_DATE,
            FieldKey.EXCLUDE_DRG_DRG_INTERACTION_CHECK,
            FieldKey.OVERRIDE_DF_DOSE_CHK_EXCLUSN, FieldKey.CREATE_POSSIBLE_DOSAGE,
            FieldKey.PRODUCT_PACKAGE, FieldKey.FDA_MED_GUIDE, FieldKey.POSSIBLE_DOSAGES_AUTO_CREATE,
            FieldKey.HAZARDOUS_TO_DISPOSE, FieldKey.HAZARDOUS_TO_HANDLE, FieldKey.PRIMARY_EPA_CODE, FieldKey.WASTE_SORT_CODE,FieldKey.DOT_SHIPPING_NAME,
            FieldKey.ORDERABLE_ITEM,    //  In order to get modifications to Dosage Form caused by changing Orderable Item.
            FieldKey.ITEM_STATUS })));
    private static final ObjectFactory FACTORY = new ObjectFactory();

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
    .getLogger(MigrationSynchFileCapabilityImpl.class);

    /**
     * Hidden constructor.
     */
    private VaProductConverter() {
    }
    
    /**
     * Convert VA Product Vo to a VA Product Sync Request
     * @param vaProductVo VA Product 
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return VaProductSyncRequestDocument the returned document
     * 
     */
    public static VaProductSyncRequest toVaProductSyncRequest(ProductVo vaProductVo,  
        Map<FieldKey, Difference> differences, ItemAction itemAction) {
        
        VaProductSyncRequest vaProductSyncRequest = FACTORY.createVaProductSyncRequest();
        ItemAction productFileAction = toProductFileAction(differences, itemAction);
        
        if (RequestItemStatus.APPROVED.equals(vaProductVo.getRequestItemStatus()) 
                && hasNewOrModifiedFields(FIELDS, differences, productFileAction)) {
            
            vaProductSyncRequest.setRequestType(productFileAction.toString());
            vaProductSyncRequest.setVaProductName(vaProductVo.getVaProductName());
            
            if (vaProductVo.getNdfProductIen() != null) {
                vaProductSyncRequest.setVaProductIen(vaProductVo.getNdfProductIen().toString());
            }
            
            if (vaProductVo.getGenericName() != null) {
                VaGenericNameType vaGNT = new VaGenericNameType();
                vaGNT.setVaGenericNameName(vaProductVo.getGenericName().getValue());
                vaGNT.setVaGenericIen(vaProductVo.getGenericName().getGenericIen());
                vaProductSyncRequest.setVaGenericNameRecord(vaGNT);
            }
            
            //Set Hazardous to Dispose, Hazardous to Handle fields
            if(vaProductVo.getVaDataFields().containsFieldKey(FieldKey.HAZARDOUS_TO_DISPOSE)){
               DataField<Boolean> hazToDispose = vaProductVo.getVaDataFields().getDataField(FieldKey.HAZARDOUS_TO_DISPOSE);
               Boolean hazToDisposeValue =  hazToDispose.getValue();
               if((hazToDisposeValue!=null)&&(hazToDisposeValue.booleanValue())){
                   //if the haz to dispose value passed in is true ie the checkbox is checked on the UI, set the flag on the VaProductSync
                   //and pass in the rest of the fields (primary epa code, dot shipping name and waste sort code)
                   vaProductSyncRequest.setHazardToDispose(true);
                   
                   //Now set the Primary EPA code
                   if(vaProductVo.getVaDataFields().containsFieldKey(FieldKey.PRIMARY_EPA_CODE)){
                      DataField<String> primaryEpaCode =  vaProductVo.getVaDataFields().getDataField(FieldKey.PRIMARY_EPA_CODE);
                      if(primaryEpaCode!=null){
                          vaProductSyncRequest.setPrimaryEpaCode(primaryEpaCode.getValue());
                      }                     
                   }
                   
                   //Now set the Waste Sort code
                   if(vaProductVo.getVaDataFields().containsFieldKey(FieldKey.WASTE_SORT_CODE)){
                      DataField<String> wasteSortCode =  vaProductVo.getVaDataFields().getDataField(FieldKey.WASTE_SORT_CODE);
                      if(wasteSortCode!=null){
                          vaProductSyncRequest.setWasteSortCode(wasteSortCode.getValue());
                      }                     
                   }
                   
                   //Now set the DOT Shipping Label code
                   if(vaProductVo.getVaDataFields().containsFieldKey(FieldKey.DOT_SHIPPING_NAME)){
                      DataField<String> dotShippingLabel =  vaProductVo.getVaDataFields().getDataField(FieldKey.DOT_SHIPPING_NAME);
                      if(dotShippingLabel!=null){
                          vaProductSyncRequest.setDotShippingName(dotShippingLabel.getValue());
                      }                     
                   }
               
               }else{
                   //if the haz to dispose value passed in is false ie the checkbox is NOT checked on the UI, set the false flag on the VaProductSync
                   //and pass in the rest of the fields (primary epa code, dot shipping name and waste sort code) as NULL
                   vaProductSyncRequest.setHazardToDispose(false);
                   vaProductSyncRequest.setPrimaryEpaCode("");
                   vaProductSyncRequest.setWasteSortCode("");
                   vaProductSyncRequest.setDotShippingName("");
                   
               }
            }
            
            if(vaProductVo.getVaDataFields().containsFieldKey(FieldKey.HAZARDOUS_TO_HANDLE)){
                DataField<Boolean> hazToHandle = vaProductVo.getVaDataFields().getDataField(FieldKey.HAZARDOUS_TO_HANDLE);
                Boolean hazToHandleValue =  hazToHandle.getValue();
                if((hazToHandleValue!=null)&&(hazToHandleValue.booleanValue())){
                    //if the haz to handle value passed in is true ie the checkbox is checked on the UI
                    vaProductSyncRequest.setHazardToHandle(true);
                }else{
                    vaProductSyncRequest.setHazardToHandle(false);
                }
            }
            
            // Dosage Form Record
            if (vaProductVo.getDosageForm() != null) {
                DosageFormType dFT = new DosageFormType();
                dFT.setDosageFormName(vaProductVo.getDosageForm().getDosageFormName());
                dFT.setDosageFormIen(vaProductVo.getDosageForm().getDosageFormIen());
                vaProductSyncRequest.setDosageFormRecord(dFT);
            }
            
            if (vaProductVo.getProductStrength() != null) {
                vaProductSyncRequest.setStrength(vaProductVo.getProductStrength());
            }
            
            if (vaProductVo.getProductUnit() != null) {
                vaProductSyncRequest.setUnits(vaProductVo.getProductUnit().getValue());
            }
            
            // National Formulary Name
            if ((vaProductVo.getNationalFormularyName() != null) && !vaProductVo.getNationalFormularyName().isEmpty()) {
                vaProductSyncRequest.setNationalFormularyName(vaProductVo.getNationalFormularyName());
            }
            
            // VA Print Name
            if ((vaProductVo.getVaPrintName() != null) && !vaProductVo.getVaPrintName().isEmpty()) {
                vaProductSyncRequest.setVaPrintName(vaProductVo.getVaPrintName());
            }
            
            // VA Product Identifier (from CMOP ID)
            if ((vaProductVo.getCmopId() != null) && !vaProductVo.getCmopId().isEmpty()) {
                vaProductSyncRequest.setVaProductIdentifier(vaProductVo.getCmopId());
            }
            
            // Transmit To CMOP (from Cmop Dispense)
            if (vaProductVo.getCmopDispense()) {
                vaProductSyncRequest.setTransmitToCmop("1");
            } else {
                vaProductSyncRequest.setTransmitToCmop("0");
            }
            
            // VA Dispense Unit
            if (vaProductVo.getDispenseUnit() != null) {
                if (StringUtils.isNotEmpty(vaProductVo.getDispenseUnit().getValue())) {
                    VaDispenseUnitType dUT = new VaDispenseUnitType();
                    dUT.setVaDispenseUnitName(vaProductVo.getDispenseUnit().getValue());
                    dUT.setVaDispenseUnitIen(vaProductVo.getDispenseUnit().getDispenseUnitIen());
                    vaProductSyncRequest.setVaDispenseUnitRecord(dUT);
                }
            }
            
            // Active Ingredients
            if (vaProductVo.getActiveIngredients().size() != 0) {
                Collection<ActiveIngredientsType> activeIngredientList = new ArrayList<ActiveIngredientsType>();

                for (ActiveIngredientVo ai : vaProductVo.getActiveIngredients()) {
                    ActiveIngredientsType ait = new ActiveIngredientsType();
                    ait.setActiveIngredientsName(ai.getIngredient().getValue());
                    ait.setActiveIngredientsIen(ai.getIngredient().getNdfIngredientIen());
                    ait.setActiveIngredientsStrength(ai.getStrength());

                    if (ai.getDrugUnit() != null) {
                        ait.setActiveIngredientsUnitsName(ai.getDrugUnit().getValue());
                        ait.setActiveIngredientsUnitsIen(ai.getDrugUnit().getDrugUnitIen());
                    }

                    activeIngredientList.add(ait);
                }

                vaProductSyncRequest.getActiveIngredientsRecord().addAll(activeIngredientList);
            }
            
            // GCN Sequence Number
            if ((vaProductVo.getGcnSequenceNumber() != null) && !vaProductVo.getGcnSequenceNumber().isEmpty()) {
                vaProductSyncRequest.setGcnSeqNo(vaProductVo.getGcnSequenceNumber());
            }
            
            // Primary VA Drug Class Record
            if ((vaProductVo.getPrimaryDrugClass().getCode() != null) 
                    && !vaProductVo.getPrimaryDrugClass().getCode().isEmpty()) {
                PrimaryVaDrugClassType drugClass = new PrimaryVaDrugClassType();
                DrugClassVo dcVo = vaProductVo.getPrimaryDrugClass();
                drugClass.setPrimaryVaDrugClassCode(dcVo.getCode());
                drugClass.setPrimaryVaDrugClassClassification(dcVo.getClassification());
                drugClass.setPrimaryVaDrugClassIen(dcVo.getDrugClassIen());
                vaProductSyncRequest.setPrimaryVaDrugClassRecord(drugClass);
            }
            
            // National Formulary Indicator
            if (vaProductVo.getNationalFormularyIndicator()) {
                vaProductSyncRequest.setNationalFormularyIndicator("1");
            } else {
                vaProductSyncRequest.setNationalFormularyIndicator("0");
            }
            

        }   
        
        vaProductSyncRequest = 
            toVaProductSyncRequest1(vaProductVo, differences, itemAction, productFileAction, vaProductSyncRequest);
        
        vaProductSyncRequest = 
            toVaProductSyncRequest2(vaProductVo, differences, itemAction, productFileAction, vaProductSyncRequest);
        
        return vaProductSyncRequest;
        
    }
        
    /**
     * toVaProductSyncRequest1
     * @param vaProductVo vaProductVo
     * @param differences differences
     * @param itemAction itemAction
     * @param productFileAction productFileAction
     * @param vaProductSyncRequest vaProductSyncRequest
     * @return VaProductSyncRequest
     */
    private static VaProductSyncRequest toVaProductSyncRequest1(ProductVo vaProductVo,  
        Map<FieldKey, Difference> differences,
        ItemAction itemAction, ItemAction productFileAction, VaProductSyncRequest vaProductSyncRequest) {
        
        if (RequestItemStatus.APPROVED.equals(vaProductVo.getRequestItemStatus()) 
            && hasNewOrModifiedFields(FIELDS, differences, productFileAction)) {
            
            // CS Federal Schedule
            if (vaProductVo.getCsFedSchedule() != null) {
                if (vaProductVo.getCsFedSchedule().getValue().startsWith("0", 0)) {
                    vaProductSyncRequest.setCsFederalSchedule("0");
                } else if (vaProductVo.getCsFedSchedule().getValue().startsWith("1", 0)) {
                    vaProductSyncRequest.setCsFederalSchedule("1");
                } else if (vaProductVo.getCsFedSchedule().getValue().startsWith("2n", 0)) {
                    vaProductSyncRequest.setCsFederalSchedule("2n");
                } else if (vaProductVo.getCsFedSchedule().getValue().startsWith("2", 0)) {
                    vaProductSyncRequest.setCsFederalSchedule("2");
                } else if (vaProductVo.getCsFedSchedule().getValue().startsWith("3n", 0)) {
                    vaProductSyncRequest.setCsFederalSchedule("3n");
                } else if (vaProductVo.getCsFedSchedule().getValue().startsWith("3", 0)) {
                    vaProductSyncRequest.setCsFederalSchedule("3");
                } else if (vaProductVo.getCsFedSchedule().getValue().startsWith("4", 0)) {
                    vaProductSyncRequest.setCsFederalSchedule("4");
                } else {
                    vaProductSyncRequest.setCsFederalSchedule("5");
                }
            }
            
            // Single/Multisource Product
            if (vaProductVo.getSingleMultiSourceProduct() != null) {
                if (vaProductVo.getSingleMultiSourceProduct().getValue().equals(PPSConstants.MULTISOURCE_MULTI)) {
                    vaProductSyncRequest.setSingleMultiSourceProduct("M");
                } else if (vaProductVo.getSingleMultiSourceProduct().getValue().equals(PPSConstants.MULTISOURCE_BOTH)) {
                    vaProductSyncRequest.setSingleMultiSourceProduct("M");
                } else if (vaProductVo.getSingleMultiSourceProduct().getValue().equals(PPSConstants.MULTISOURCE_SINGLE)) {
                    vaProductSyncRequest.setSingleMultiSourceProduct("S");
                } 
            }
        }
        
        return vaProductSyncRequest;   
    }
    
    
    /**
     * toVaProductSyncRequest2
     * @param vaProductVo vaProductVo
     * @param differences differences
     * @param itemAction itemAction
     * @param productFileAction productFileAction
     * @param vaProductSyncRequest vaProductSyncRequest
     * @return VaProductSyncRequest
     */
    private static VaProductSyncRequest toVaProductSyncRequest2(ProductVo vaProductVo,  
        Map<FieldKey, Difference> differences,
        ItemAction itemAction, ItemAction productFileAction, VaProductSyncRequest vaProductSyncRequest) {
            
        if (RequestItemStatus.APPROVED.equals(vaProductVo.getRequestItemStatus()) 
            && hasNewOrModifiedFields(FIELDS, differences, productFileAction)) {
        
           
            
            // Inactivation Date
            if (vaProductVo.getInactivationDate() != null) {
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(vaProductVo.getInactivationDate());

                try {
                    vaProductSyncRequest.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                } catch (DatatypeConfigurationException e) {
                    LOG.error("VA Product Converter Inactivation Date mismatch" + vaProductVo.getValue());
                    vaProductSyncRequest.setInactivationDate(null);
                }
            }
            
            // Override DF Dose Check Exclusion
            if (vaProductVo.isOverrideDfDoseChkExclusn()) {
                vaProductSyncRequest.setOverrideDfDoseChkExclusion("1");
            } else {
                vaProductSyncRequest.setOverrideDfDoseChkExclusion("0");
            }
            
            // Exclude Drug Drug Interaction
            if (vaProductVo.isExcludeDrgDrgInteractionCheck()) {
                vaProductSyncRequest.setExcludeDrugDrugInteraction("1");
            } else {
                vaProductSyncRequest.setExcludeDrugDrugInteraction("0");
            }
            
            // Create Possible Dosage
            if (vaProductVo.getCreatePossibleDosage()) {
                vaProductSyncRequest.setCreatePossibleDosage("YES");
            } else {
                vaProductSyncRequest.setCreatePossibleDosage("NO");
            }
            
            // Product Package
            if (vaProductVo.getProductPackage() != null) {
                if (vaProductVo.getProductPackage().equals(ProductPackage.INPATIENT)) {
                    vaProductSyncRequest.setProductPackage("I");
                } else if (vaProductVo.getProductPackage().equals(ProductPackage.OUTPATIENT)) {
                    vaProductSyncRequest.setProductPackage("O");
                } else {
                    vaProductSyncRequest.setProductPackage("IO");
                }
            }

                // Master Entry For VUID
            if (vaProductVo.isMasterEntryForVuid()) {
                vaProductSyncRequest.setMasterEntryForVuid("1");
            } else {
                vaProductSyncRequest.setMasterEntryForVuid("0");
            }
            
            // VUID
            if ((vaProductVo.getVuid() != null) && !vaProductVo.getVuid().isEmpty()) {
                vaProductSyncRequest.setVuid(vaProductVo.getVuid());
            }
            
            // Effective Date Time Records
            if (vaProductVo.getEffectiveDates() != null) {
                Collection<EffectiveDateTimeType> dtList = new ArrayList<EffectiveDateTimeType>();

                for (VuidStatusHistoryVo eDT : vaProductVo.getEffectiveDates()) {
                    EffectiveDateTimeType eDTType = new EffectiveDateTimeType();
                    GregorianCalendar c = new GregorianCalendar();
                    c.setTime(eDT.getEffectiveDateTime());

                    try {
                        eDTType.setEffectiveDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                    } catch (DatatypeConfigurationException e) {
                        LOG.error("VA Product Converter Effective Date Time mismatch" + vaProductVo.getValue());
                        eDTType.setEffectiveDateTime(null);
                    }

                    eDTType.setEffectiveDateTimeStatus(eDT.getEffectiveDtmStatus().name());
                    dtList.add(eDTType);
                }

                vaProductSyncRequest.getEffectiveDateTimeRecord().addAll(dtList);
            }
            
            // FDA Med Guide
            if ((vaProductVo.getFdaMedGuide() != null) && !vaProductVo.getFdaMedGuide().isEmpty()) {
                vaProductSyncRequest.setFdaMedGuide(vaProductVo.getFdaMedGuide());
            }
            
            // Service Code
            if (vaProductVo.getServiceCode() != null) {
                vaProductSyncRequest.setServiceCode(vaProductVo.getServiceCode().toString());
            }
            
            //Possible Dosages To Create
            if (vaProductVo.getPossibleDosagesAutoCreate() != null) {
                if (vaProductVo.getPossibleDosagesAutoCreate().equals(PossibleDosagesAutoCreate.NO_POSSIBLE_DOSAGES)) {
                    vaProductSyncRequest.setPossibleDosagesToCreate("N");
                } else if (vaProductVo.getPossibleDosagesAutoCreate().
                        equals(PossibleDosagesAutoCreate.ONE_X_POSSIBLE_DOSAGES)) {
                    vaProductSyncRequest.setPossibleDosagesToCreate("O");
                } else {
                    vaProductSyncRequest.setPossibleDosagesToCreate("B");
                }
            }
            
        }
                    
        return vaProductSyncRequest;
    }

    /**
     * Get product file action.
     * 
     * @param differences differences
     * @param itemAction default action
     * @return action
     */
    private static ItemAction toProductFileAction(Map<FieldKey, Difference> differences, ItemAction itemAction) {

//        if (RequestItemStatus.PENDING.equals(getOldValue(FieldKey.REQUEST_ITEM_STATUS, differences))) {
//            return ItemAction.ADD;
//        }

        if (ItemAction.INACTIVATE.equals(itemAction) && !hasOldValue(FieldKey.ITEM_STATUS, differences)) {
            return ItemAction.MODIFY; // override disingenuous inactivation
        }

        if (ItemStatus.INACTIVE.equals(getOldValue(FieldKey.ITEM_STATUS, differences))
            && ItemStatus.ACTIVE.equals(getNewValue(FieldKey.ITEM_STATUS, differences))) { // reactivate

            differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, new Date(), null));
        }

        return itemAction;
    }


}
