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

import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vadispenseunitsyncrequest.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vadispenseunitsyncrequest.VaDispenseUnitSyncRequest;


/**
 * VaDispenseUnitConverter's brief summary
 * 
 * Convert a Dispense Unit VO to a VA Dispense Unit Sync Request Document.
 *
 */
public class VaDispenseUnitConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] { FieldKey.VALUE, FieldKey.INACTIVATION_DATE, FieldKey.ITEM_STATUS })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    private static final Logger LOG = Logger.getLogger(VaDispenseUnitConverter.class);

    /**
     * Hidden constructor.
     */
    private VaDispenseUnitConverter() {
    }
    
    /**
     * Convert dispense Unit VO to VA Dispense Unit Sync document.
     * 
     * @param dispenseUnitVo dispense unit
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return va dispense unit Sync request
     */
    public static VaDispenseUnitSyncRequest toVaDispenseUnitSyncRequest(DispenseUnitVo dispenseUnitVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        VaDispenseUnitSyncRequest vaDispenseUnitSyncRequest = FACTORY.createVaDispenseUnitSyncRequest();
        
        if (RequestItemStatus.APPROVED.equals(dispenseUnitVo.getRequestItemStatus())
            && hasNewOrModifiedFields(FIELDS, differences, itemAction)) {
            
            //   IEN
            if ((dispenseUnitVo.getDispenseUnitIen() == null) || dispenseUnitVo.getDispenseUnitIen().isEmpty()) {
                vaDispenseUnitSyncRequest.setVaDispenseUnitIen(null);
            } else {
                vaDispenseUnitSyncRequest.setVaDispenseUnitIen(dispenseUnitVo.getDispenseUnitIen());
            }
            
            //   Name
            vaDispenseUnitSyncRequest.setVaDispenseUnitName(dispenseUnitVo.getValue());
        
            // Inactivation date
            if (dispenseUnitVo.getInactivationDate() != null) {
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(dispenseUnitVo.getInactivationDate());
                
                try {
                    vaDispenseUnitSyncRequest.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                } catch (DatatypeConfigurationException e) {
                    LOG.error("VA Dispense Unit Converter Inactivation Date mismatch" + dispenseUnitVo.getValue());
                    vaDispenseUnitSyncRequest.setInactivationDate(null);
                }
            }
            
            // Action Type
            if (ItemAction.INACTIVATE.equals(itemAction)) {
                vaDispenseUnitSyncRequest.setRequestType(ItemAction.MODIFY.toString());
            } else {
                vaDispenseUnitSyncRequest.setRequestType(itemAction.toString());
            }

        }

        return vaDispenseUnitSyncRequest;
    }
}
