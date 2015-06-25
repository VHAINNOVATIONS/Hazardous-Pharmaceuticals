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
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.packagetypesyncrequest.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.packagetypesyncrequest.PackageTypeSyncRequest;


/**
 * PackageTypeConverter's brief summary
 * 
 * Converts a Package Type VO to a Package Type Document.
 *
 */
public class PackageTypeConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] { FieldKey.VALUE, FieldKey.INACTIVATION_DATE, FieldKey.ITEM_STATUS })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    private static final Logger LOG = Logger.getLogger(PackageTypeConverter.class);

    /**
     * Hidden constructor.
     */
    private PackageTypeConverter() {
    }
    
    /**
     *  Converts PackageType VO to Sync Document.
     *
     * @param packageTypeVo PackageTypeVo
     * @param differences Map of differences
     * @param itemAction add/modify/inactivate
     * @return packageTypeSyncRequest Sync request document
     * 
     */
    public static PackageTypeSyncRequest toPackageTypeSyncRequest(PackageTypeVo packageTypeVo, 
            Map<FieldKey, Difference> differences, ItemAction itemAction) {

        PackageTypeSyncRequest packageTypeSyncRequest = FACTORY.createPackageTypeSyncRequest();
        
        if (RequestItemStatus.APPROVED.equals(packageTypeVo.getRequestItemStatus()) 
                && hasNewOrModifiedFields(FIELDS, differences, itemAction)) {
            
            // IEN
            if ((packageTypeVo.getPackagetypeIen() == null) || packageTypeVo.getPackagetypeIen().isEmpty()) {
                packageTypeSyncRequest.setPackageTypeIen(null);
            } else {
                packageTypeSyncRequest.setPackageTypeIen(packageTypeVo.getPackagetypeIen());
            }
            
            //Name
            packageTypeSyncRequest.setPackageTypeName(packageTypeVo.getValue());
            
            // Inactivation Date 
            if (packageTypeVo.getInactivationDate() != null) {
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(packageTypeVo.getInactivationDate());
                
                try {
                    packageTypeSyncRequest.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                } catch (DatatypeConfigurationException e) {
                    LOG.error("Package Type Converter Inactivation Date mismatch" + packageTypeVo.getValue());
                    packageTypeSyncRequest.setInactivationDate(null);
                }
            }
            
            // Action Type
            if (ItemAction.INACTIVATE.equals(itemAction)) {
                packageTypeSyncRequest.setRequestType(ItemAction.MODIFY.toString());
            } else {
                packageTypeSyncRequest.setRequestType(itemAction.toString());
            }
            
        }
            
        return packageTypeSyncRequest;
    }
}
