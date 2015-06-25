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

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl.MigrationSynchFileCapabilityImpl;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugunitsyncrequest.DrugUnitSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugunitsyncrequest.ObjectFactory;


/**
 * Converts a Drug Unit VO to a Drug Unit document.
 *
 */
public class DrugUnitConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] { FieldKey.VALUE, FieldKey.INACTIVATION_DATE, FieldKey.ITEM_STATUS })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    private static final Logger LOG = Logger.getLogger(MigrationSynchFileCapabilityImpl.class);

    /**
     * Hidden constructor.
     */
    private DrugUnitConverter() {
    }
    
    /**
     * Convert Drug Unit VO to Drug Unit Sync document.
     * 
     * @param drugUnitVo drug unit
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return drug unit Sync
     */
    public static DrugUnitSyncRequest toDrugUnitSyncRequest(DrugUnitVo drugUnitVo, Map<FieldKey, Difference> differences,
        ItemAction itemAction) {

        DrugUnitSyncRequest drugUnitSyncRequest = FACTORY.createDrugUnitSyncRequest();
        
        if (RequestItemStatus.APPROVED.equals(drugUnitVo.getRequestItemStatus())
            && hasNewOrModifiedFields(FIELDS, differences, itemAction)) {
            
            //   IEN
            if ((drugUnitVo.getDrugUnitIen() == null) || drugUnitVo.getDrugUnitIen().isEmpty()) {
                drugUnitSyncRequest.setDrugUnitIen(null);
            } else {
                drugUnitSyncRequest.setDrugUnitIen(drugUnitVo.getDrugUnitIen());
            }
            
            //   Name
            drugUnitSyncRequest.setDrugUnitName(drugUnitVo.getValue());
        
            // Inactivation date
            if (drugUnitVo.getInactivationDate() != null) {
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(drugUnitVo.getInactivationDate());
                
                try {
                    drugUnitSyncRequest.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                } catch (DatatypeConfigurationException e) {
                    LOG.error("Drug Unit Converter Inactivation Date mismatch" + drugUnitVo.getValue());
                    drugUnitSyncRequest.setInactivationDate(null);
                }            
            }
            
            // Action Type
            if (ItemAction.INACTIVATE.equals(itemAction)) {
                drugUnitSyncRequest.setRequestType(ItemAction.MODIFY.toString());
            } else {
                drugUnitSyncRequest.setRequestType(itemAction.toString());
            }
        }

        return drugUnitSyncRequest;
    }

}
