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

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl.MigrationSynchFileCapabilityImpl;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vagenericnamesyncrequest.EffectiveDateTime;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vagenericnamesyncrequest.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vagenericnamesyncrequest.VaGenericNameSyncRequest;


/**
 * Converts a VA Generic Name VO into a VA Generic Name Document
 *
 */
public class VaGenericNameConverter extends AbstractConverter {
    
    /**
     * FIELDS
     */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
            .asList(new FieldKey[] {FieldKey.GENERIC_NAME, FieldKey.INACTIVATION_DATE, FieldKey.ITEM_STATUS})));

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
    .getLogger(MigrationSynchFileCapabilityImpl.class);

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private VaGenericNameConverter() {
    }
    
    /**
     * Convert VA Generic Name VO to VA Generic Name Sync document.
     * 
     * @param vaGenericNameVo VA Generic Name
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return VA Generic Sync
     */
    public static VaGenericNameSyncRequest toVaGenericNameSyncRequest(GenericNameVo vaGenericNameVo, 
                                                                      Map<FieldKey, Difference> differences,
                                                                      ItemAction itemAction) {

        VaGenericNameSyncRequest vaGenericNameSyncRequest = FACTORY.createVaGenericNameSyncRequest();
        
        if (RequestItemStatus.APPROVED.equals(vaGenericNameVo.getRequestItemStatus()) 
                && hasNewOrModifiedFields(FIELDS, differences, itemAction)) {
            
            //   IEN
            if (StringUtils.isNotBlank(vaGenericNameVo.getGenericIen())) {
                vaGenericNameSyncRequest.setVaGenericNameIen(vaGenericNameVo.getGenericIen());
            } else {
                vaGenericNameSyncRequest.setVaGenericNameIen(null);
            }
            
            //   Name
            vaGenericNameSyncRequest.setVaGenericNameName(vaGenericNameVo.getValue());
        
            // Inactivation date
            if (vaGenericNameVo.getInactivationDate() != null) {
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(vaGenericNameVo.getInactivationDate());

                try {
                    vaGenericNameSyncRequest.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                } catch (DatatypeConfigurationException e) {
                    LOG.error("VA Generic Name Converter Inactivation Date mismatch" + vaGenericNameVo.getValue());
                    vaGenericNameSyncRequest.setInactivationDate(null);
                }            
            }
            
            // Master Entry For VUID
            if (vaGenericNameVo.getMasterEntryForVuid()) {
                vaGenericNameSyncRequest.setMasterEntryForVuid("1");                    
            } else {
                vaGenericNameSyncRequest.setMasterEntryForVuid("0");
            }
            
            // VUID
            if (StringUtils.isNotBlank(vaGenericNameVo.getVuid())) {
                vaGenericNameSyncRequest.setVuid((vaGenericNameVo.getVuid()));            
            } else {
                vaGenericNameSyncRequest.setVuid(null);
            }
            
            //Effective Date Time
            if (vaGenericNameVo.getEffectiveDates() == null) {
                vaGenericNameSyncRequest.getEffectiveDateTimeRecord();
            } else {
                ArrayList<EffectiveDateTime> effectiveDateTimes = new ArrayList<EffectiveDateTime>(); 

                for (VuidStatusHistoryVo effectiveDate : vaGenericNameVo.getEffectiveDates()) {
                    EffectiveDateTime effectiveDateTime = new EffectiveDateTime();
                    GregorianCalendar c = new GregorianCalendar();
                    c.setTime(effectiveDate.getEffectiveDateTime());

                    try {
                        effectiveDateTime.setEffectiveDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                    } catch (DatatypeConfigurationException e) {
                        LOG.error("VA Generic Name Converter Effective Date Time mismatch" + vaGenericNameVo.getValue());
                        effectiveDateTime.setEffectiveDateTime(null);
                    }

                    effectiveDateTime.setStatus(effectiveDate.getItemStatus().toString());
                    effectiveDateTimes.add(effectiveDateTime);
                }

                vaGenericNameSyncRequest.getEffectiveDateTimeRecord().addAll(effectiveDateTimes);
            }

             // Action Type
            if (ItemAction.INACTIVATE.equals(itemAction)) {
                vaGenericNameSyncRequest.setRequestType(ItemAction.MODIFY.toString());
            } else {
                vaGenericNameSyncRequest.setRequestType(itemAction.toString());
            }

        }

        return vaGenericNameSyncRequest;
    }


}
