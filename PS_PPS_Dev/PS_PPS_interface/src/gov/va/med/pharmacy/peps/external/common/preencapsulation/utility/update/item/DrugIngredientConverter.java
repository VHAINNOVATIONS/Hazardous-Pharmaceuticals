/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl.MigrationSynchFileCapabilityImpl;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugingredientsyncrequest.DrugIngredientSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugingredientsyncrequest.EffectiveDateTime;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugingredientsyncrequest.ObjectFactory;


/**
 * DrugIngredientConverter's brief summary
 * 
 * Converts a Ingredient VO into a Drug Ingredient Document.
 *
 */
public class DrugIngredientConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(
        Arrays.asList(new FieldKey[] {
            FieldKey.VALUE, FieldKey.PRIMARY_INGREDIENT, FieldKey.INACTIVATION_DATE, FieldKey.ITEM_STATUS })));

    private static final Logger LOG = Logger.getLogger(MigrationSynchFileCapabilityImpl.class);

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private DrugIngredientConverter() {
    }
    
    /**
     * Convert IngredientVo to a Drug Ingredient Sync Document.
     * @param drugIngredientVo VA Ingredient Name
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return VA Generic Sync
     * 
     */
    public static DrugIngredientSyncRequest toDrugIngredientSyncRequest(IngredientVo drugIngredientVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {
        
        DrugIngredientSyncRequest drugIngredientSyncRequest = FACTORY.createDrugIngredientSyncRequest();
        
        if (RequestItemStatus.APPROVED.equals(drugIngredientVo.getRequestItemStatus()) 
                && hasNewOrModifiedFields(FIELDS, differences, itemAction)) {
            
            // IEN
            if ((drugIngredientVo.getNdfIngredientIen() == null) || drugIngredientVo.getNdfIngredientIen().isEmpty()) {
                drugIngredientSyncRequest.setIngredientIen(null);
            } else {
                drugIngredientSyncRequest.setIngredientIen(drugIngredientVo.getNdfIngredientIen());
            }
            
            // Ingredient Name
            drugIngredientSyncRequest.setDrugIngredientName(drugIngredientVo.getValue());
            
            // Primary Ingredient
            if (drugIngredientVo.getPrimaryIngredient() != null) {
                drugIngredientSyncRequest.setPrimaryIngredient(drugIngredientVo.getPrimaryIngredient().getValue());
            }
            
            // Inactivation Date
            if (drugIngredientVo.getInactivationDate() != null) {
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(drugIngredientVo.getInactivationDate());

                try {
                    drugIngredientSyncRequest.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                } catch (DatatypeConfigurationException e) {
                    LOG.error("Drug Ingredient Converter Inactivation Date mismatch" + drugIngredientVo.getValue());
                    drugIngredientSyncRequest.setInactivationDate(null);
                }
            }
            
           // Master Entry For VUID
            if (drugIngredientVo.getMasterEntryForVuid()) {
                drugIngredientSyncRequest.setMasterEntryForVuid("1");
                
            } else {
                drugIngredientSyncRequest.setMasterEntryForVuid("0");
            }
            
            //VUID
            if (drugIngredientVo.getVuid() != null) {
                drugIngredientSyncRequest.setVuid(drugIngredientVo.getVuid());
            }
            
            // Effective Date/Time
            if (drugIngredientVo.getEffectiveDates() == null) {
                drugIngredientSyncRequest.getEffectiveDateTimeRecord();
            } else {
                ArrayList<EffectiveDateTime> effectiveDateTimes = new ArrayList<EffectiveDateTime>(); 

                for (VuidStatusHistoryVo effectiveDate : drugIngredientVo.getEffectiveDates()) {
                    EffectiveDateTime effectiveDateTime = new EffectiveDateTime();
                    GregorianCalendar c = new GregorianCalendar();
                    c.setTime(effectiveDate.getEffectiveDateTime());

                    try {
                        effectiveDateTime.setEffectiveDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                    } catch (DatatypeConfigurationException e) {
                        LOG.error("Drug Ingredient Converter Effective Date Time mismatch" + drugIngredientVo.getValue());
                        effectiveDateTime.setEffectiveDateTime(null);
                    }

                    effectiveDateTime.setStatus(effectiveDate.getItemStatus().toString());
                    effectiveDateTimes.add(effectiveDateTime);
                }
                
                drugIngredientSyncRequest.getEffectiveDateTimeRecord().addAll(effectiveDateTimes);
            }
            
            // Action Type
            if (ItemAction.INACTIVATE.equals(itemAction)) {
                drugIngredientSyncRequest.setRequestType(ItemAction.MODIFY.toString());
            } else {
                drugIngredientSyncRequest.setRequestType(itemAction.toString());
            }
            
        }
        
        return drugIngredientSyncRequest;
    }
}
