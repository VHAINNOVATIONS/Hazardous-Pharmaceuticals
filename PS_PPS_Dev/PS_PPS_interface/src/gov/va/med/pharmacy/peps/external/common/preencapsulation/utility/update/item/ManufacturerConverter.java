/**
 * Source file created in 2011 by Southwest Research Institute
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

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.manufacturersyncrequest.ManufacturerSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.manufacturersyncrequest.ObjectFactory;


/**
 * ManufacturerConverter's brief summary
 * 
 * Converts a Manufacturer VO to a Manufacturer Document.
 *
 */
public class ManufacturerConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] { FieldKey.VALUE, FieldKey.INACTIVATION_DATE, FieldKey.ITEM_STATUS })));
    
    private static final ObjectFactory FACTORY = new ObjectFactory();

    private static final Logger LOG = Logger.getLogger(ManufacturerConverter.class);

    /**
     * Hidden constructor.
     */
    private ManufacturerConverter() {
        
    }
    
    /**
     *  Converts Manufacturer VO to Sync Document.
     *
     * @param manufacturerVo ManufacturerVo
     * @param differences Map of differences
     * @param itemAction add/modify/inactivate
     * @return manufacturerSyncRequest Sync request document
     * 
     */
    public static ManufacturerSyncRequest toManufacturerSyncRequest(ManufacturerVo manufacturerVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        ManufacturerSyncRequest manufacturerSyncRequest = FACTORY.createManufacturerSyncRequest();
        
        if (RequestItemStatus.APPROVED.equals(manufacturerVo.getRequestItemStatus())
            && hasNewOrModifiedFields(FIELDS, differences, itemAction)) {
            
            // IEN
            if ((manufacturerVo.getManufacturerIen() == null) || manufacturerVo.getManufacturerIen().isEmpty()) {
                manufacturerSyncRequest.setManufacturerIen(null);
            } else {
                manufacturerSyncRequest.setManufacturerIen(manufacturerVo.getManufacturerIen());
            }
            
            // Name
            manufacturerSyncRequest.setManufacturerName(manufacturerVo.getValue());
            
            // Inactivation Date
            if (manufacturerVo.getInactivationDate() == null) {
                manufacturerSyncRequest.setInactivationDate(null);
            } else {
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(manufacturerVo.getInactivationDate());
                
                try {
                    manufacturerSyncRequest.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                } catch (DatatypeConfigurationException e) {
                    LOG.error("Manufacturer Converter Inactivation Date mismatch" + manufacturerVo.getValue());
                    manufacturerSyncRequest.setInactivationDate(null);
                }
            }
            
            // Action Type
            if (ItemAction.INACTIVATE.equals(itemAction)) {
                manufacturerSyncRequest.setRequestType(ItemAction.MODIFY.toString());
            } else {
                manufacturerSyncRequest.setRequestType(itemAction.toString());
            }
        }
        
        return manufacturerSyncRequest;
    }
}
