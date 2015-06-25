/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item;


import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl.MigrationSynchFileCapabilityImpl;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.dosageformsyncrequest.DispenseUnitsPerDose;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.dosageformsyncrequest.DosageFormSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.dosageformsyncrequest.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.dosageformsyncrequest.Units;


/**
 * DosageFormConverter's brief summary
 * 
 * Converts a Dosage Form VO into a Dosage Form Document.
 *
 */
public class DosageFormConverter extends AbstractConverter {

    /**
     * FIELDS.
     */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
            .asList(new FieldKey[] {FieldKey.DOSAGE_FORM_NAME, FieldKey.LOCAL_MED_ROUTES, FieldKey.LOCAL_MEDICATION_ROUTE,
                                    FieldKey.VERB, FieldKey.OTHER_LANGUAGE_VERB, FieldKey.PREPOSITION,
                                    FieldKey.OTHER_LANGUAGE_PREPOSITION, FieldKey.DF_NOUNS, FieldKey.INACTIVATION_DATE,
                                    FieldKey.DF_UNITS, FieldKey.DF_DISPENSE_UNITS_PER_DOSE, FieldKey.EXCLUDE_FROM_DOSAGE_CHKS,
                                    FieldKey.CONJUNCTION, FieldKey.ITEM_STATUS})));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
    .getLogger(MigrationSynchFileCapabilityImpl.class);
    private static final String I_INPATIENT = "I-Inpatient";

    /**
     * Hidden constructor.
     */
    private DosageFormConverter() {
        
    }

    
    /**
     *  Convert Dosage Form Vo to Dosage Form Sync Document
     *  
     * @param dosageFormVo VA Generic Name
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return VA Generic Sync
     * 
     */
    public static DosageFormSyncRequest toDosageFormSyncRequest(DosageFormVo dosageFormVo,
            Map<FieldKey, Difference> differences,
            ItemAction itemAction) {
        
        DosageFormSyncRequest dosageFormSyncRequest = FACTORY.createDosageFormSyncRequest();
        
        if (RequestItemStatus.APPROVED.equals(dosageFormVo.getRequestItemStatus()) 
                && hasNewOrModifiedFields(FIELDS, differences, itemAction)) {
            
            //   IEN
            if (StringUtils.isNotEmpty(dosageFormVo.getDosageFormIen())) {
                dosageFormSyncRequest.setDosageFormIen(dosageFormVo.getDosageFormIen());
            } else {
                dosageFormSyncRequest.setDosageFormIen(null);
            }
            
            //   Name
            dosageFormSyncRequest.setDosageFormName(dosageFormVo.getDosageFormName());
            
            // Inactivation date
            if (dosageFormVo.getInactivationDate() != null) {
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(dosageFormVo.getInactivationDate());

                try {
                    dosageFormSyncRequest.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                } catch (DatatypeConfigurationException e) {
                    LOG.error("Dosage Form Converter Inactivation Date mismatch" + dosageFormVo.getDosageFormName());
                    dosageFormSyncRequest.setInactivationDate(null);
                }            
            }
            
            //Units
            if (dosageFormVo.getDfUnits() != null) {
          
                //     ArrayList<Units> syncUnitCollection = new ArrayList<Units>();
                
                for (DosageFormUnitVo dfUnit : dosageFormVo.getDfUnits()) {
                    Units dfSyncunit = 
                        new Units();
                    dfSyncunit.setUnits(dfUnit.getDrugUnit().getValue());
                    dfSyncunit.setUnitsIen(dfUnit.getDrugUnit().getDrugUnitIen());

                    if (dfUnit.getPackages().size() == 2) {
                        dfSyncunit.setPackage("IO");
                    } else if (dfUnit.getPackages().size() == 1 
                            && dfUnit.getPackages().iterator().next().getValue().contains(I_INPATIENT)) {
                        dfSyncunit.setPackage("I");
                    } else if (dfUnit.getPackages().size() == 1) {
                        dfSyncunit.setPackage("O");
                    }
                    
                    dosageFormSyncRequest.getUnitsRecord().add(dfSyncunit);
                }
            }

            //Dispense Units
            if (dosageFormVo.getDfDispenseUnitsPerDose() != null) {
                
                // ArrayList<DispenseUnitsPerDose> syncDispenseUnitsPerDoseCollection = new ArrayList<DispenseUnitsPerDose>();
                
                for (DispenseUnitPerDoseVo dispenseUnitPerDose : dosageFormVo.getDfDispenseUnitsPerDose()) {
                    DispenseUnitsPerDose dfSyncunit = new DispenseUnitsPerDose();
                    dfSyncunit.setDispenseUnitsPerDoseNumber(dispenseUnitPerDose.getStrDispenseUnitPerDose());

                    if (dispenseUnitPerDose.getPackages().size() == 2) {
                        dfSyncunit.setPackage("IO");
                    } else if (dispenseUnitPerDose.getPackages().size() == 1 
                            && dispenseUnitPerDose.getPackages().iterator().next().getValue().contains(I_INPATIENT)) {
                        dfSyncunit.setPackage("I");
                    } else if (dispenseUnitPerDose.getPackages().size() == 1) {
                        dfSyncunit.setPackage("O");
                    }
                    
                    dosageFormSyncRequest.getDispenseUnitsPerDose().add(dfSyncunit);
                }
            }

            // Exclude From Dosage Checks
            if (dosageFormVo.getExcludeFromDosageChks() != null) {
                dosageFormSyncRequest.setExcludeFromDosageChecks(dosageFormVo.getExcludeFromDosageChks().toString());
            }
            
            //Action Type
            if (ItemAction.INACTIVATE.equals(itemAction)) {
                dosageFormSyncRequest.setRequestType(ItemAction.MODIFY.toString());
            } else {
                dosageFormSyncRequest.setRequestType(itemAction.toString());
            }
            
        }

        return dosageFormSyncRequest;
    }
        
}
