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

import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl.MigrationSynchFileCapabilityImpl;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugclasssyncrequest.DrugClassRow;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugclasssyncrequest.DrugClassSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugclasssyncrequest.EffectiveDateTime;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugclasssyncrequest.ObjectFactory;


/**
 * DrugClassConverter's brief summary
 * 
 * Convert a Drug Class Vo into a Drug Class Document.
 *
 */
public class DrugClassConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.CODE, FieldKey.CLASSIFICATION, FieldKey.PARENT_DRUG_CLASS, FieldKey.CLASSIFICATION_TYPE,
            FieldKey.DESCRIPTION, FieldKey.ITEM_STATUS })));

    private static final Logger LOG = Logger.getLogger(MigrationSynchFileCapabilityImpl.class);

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private DrugClassConverter() {
    }
    
    /**
     * Convert Drug Class VO to Drug Class document.
     * 
     * @param drugClassVo VA Generic Name
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return VA Generic Sync
     */
    public static DrugClassSyncRequest toDrugClassSyncRequest(DrugClassVo drugClassVo, 
                                                                      Map<FieldKey, Difference> differences,
                                                                      ItemAction itemAction) {

        DrugClassSyncRequest drugClassSyncRequest = FACTORY.createDrugClassSyncRequest();
        
        if (RequestItemStatus.APPROVED.equals(drugClassVo.getRequestItemStatus()) 
                && hasNewOrModifiedFields(FIELDS, differences, itemAction)) {
            
            //   IEN
            if ((drugClassVo.getDrugClassIen() == null) || drugClassVo.getDrugClassIen().isEmpty()) {
                drugClassSyncRequest.setDrugClassIen(null);
            } else {
                drugClassSyncRequest.setDrugClassIen(drugClassVo.getDrugClassIen());
            }
            
            //   Code
            drugClassSyncRequest.setDrugClassCode(drugClassVo.getCode());
            
            // Classification
            if (drugClassVo.getClassification() != null) {
                drugClassSyncRequest.setDrugClassClassification(drugClassVo.getClassification());
            }
            
            // Parent Class
            if (drugClassVo.getParentDrugClass() != null) {
                DrugClassVo parentVoClass = drugClassVo.getParentDrugClass();
                DrugClassRow parentSyncClass = new DrugClassRow(); 
                parentSyncClass.setCode(parentVoClass.getCode());
                parentSyncClass.setClassification(parentVoClass.getClassification());
                parentSyncClass.setDrugClassIen(parentVoClass.getDrugClassIen());
                drugClassSyncRequest.setParentClass(parentSyncClass);
            }
            
            // Type
            if (drugClassVo.getClassificationType().getValue().equals("0-Major")) {
                drugClassSyncRequest.setType("0");
            } else if (drugClassVo.getClassificationType().getValue().equals("1-Minor")) {
                drugClassSyncRequest.setType("1");
            } else {
                drugClassSyncRequest.setType("2");
            }
            
            //Master Entry For VUID
            if (drugClassVo.isMasterEntryForVuid()) {
                drugClassSyncRequest.setMasterEntryForVuid("1");
            } else {
                drugClassSyncRequest.setMasterEntryForVuid("0");
            }
            
            // VUID
            if (drugClassVo.getVuid() == null) {
                drugClassSyncRequest.setVuid("");
            } else {
                drugClassSyncRequest.setVuid(drugClassVo.getVuid());
            }
            
            // Effective Date/Time
            if (drugClassVo.getEffectiveDates() == null) {
                drugClassSyncRequest.getEffectiveDateTimeRecord();
            } else {
                ArrayList<EffectiveDateTime> effectiveDateTimes = new ArrayList<EffectiveDateTime>(); 

                for (VuidStatusHistoryVo effectiveDate : drugClassVo.getEffectiveDates()) {
                    EffectiveDateTime effectiveDateTime = new EffectiveDateTime();
                    GregorianCalendar c = new GregorianCalendar();
                    c.setTime(effectiveDate.getEffectiveDateTime());

                    try {
                        effectiveDateTime.setEffectiveDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                    } catch (DatatypeConfigurationException e) {
                        LOG.error("Drug Class Converter Effective Date Time mismatch" + drugClassVo.getValue());
                        effectiveDateTime.setEffectiveDateTime(null);
                    }

                    effectiveDateTime.setStatus(effectiveDate.getItemStatus().toString());
                    effectiveDateTimes.add(effectiveDateTime);
                }
                
                drugClassSyncRequest.getEffectiveDateTimeRecord().addAll(effectiveDateTimes);
            }
            
            // Description
            if (drugClassVo.getDescription() != null) {
                drugClassSyncRequest.setDescription(drugClassVo.getDescription());
            }
            
            // Action Type
            if (ItemAction.INACTIVATE.equals(itemAction)) {
                drugClassSyncRequest.setRequestType(ItemAction.MODIFY.toString());
            } else {
                drugClassSyncRequest.setRequestType(itemAction.toString());
            }
            
        }
        
        return drugClassSyncRequest;
    }

}
