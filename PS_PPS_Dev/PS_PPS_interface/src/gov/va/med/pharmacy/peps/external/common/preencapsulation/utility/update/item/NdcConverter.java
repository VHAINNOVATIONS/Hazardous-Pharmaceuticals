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

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl.MigrationSynchFileCapabilityImpl;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.ndcsyncrequest.Manufacturer;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.ndcsyncrequest.NdcSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.ndcsyncrequest.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.ndcsyncrequest.PackageType;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.ndcsyncrequest.Product;


/**
 * NdcConverter's brief summary
 * 
 * Converts a NDC VO to a NDC document.
 *
 */
public class NdcConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.NDC, FieldKey.UPC_UPN, FieldKey.MANUFACTURER, FieldKey.TRADE_NAME, FieldKey.PRODUCT,
            FieldKey.INACTIVATION_DATE, FieldKey.PACKAGE_SIZE, FieldKey.PACKAGE_TYPE, FieldKey.OTC_RX_INDICATOR,
            FieldKey.ITEM_STATUS })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
    .getLogger(MigrationSynchFileCapabilityImpl.class);

    /**
     * Hidden constructor.
     */
    private NdcConverter() {
    }

    /**
     * Convert NDC VO to NDC Sync Document
     * 
     * @param ndcVo NDC VO
     * @param differences Map
     * @param itemAction add/modify/inactivate
     * @return ndcSyncRequest Sync Request document
     */
    public static NdcSyncRequest toNdcSyncRequest(NdcVo ndcVo, Map<FieldKey, Difference> differences, ItemAction itemAction) {

        NdcSyncRequest ndcSyncRequest = FACTORY.createNdcSyncRequest();
        
        LOG.debug("Item Action is " + itemAction);
     
        if (hasNewOrModifiedFields(FIELDS, differences, itemAction)) {
            
            LOG.debug("hasNewOrModified fields.");
            
            //   IEN
            if ((ndcVo.getNdcIen() == null) || ndcVo.getNdcIen().isEmpty()) {
                ndcSyncRequest.setNdcIen(null);
            } else {
                ndcSyncRequest.setNdcIen((ndcVo.getNdcIen()));
            }
            
            //   Name - "0" is added to the name since VistA uses 12 digits
            ndcSyncRequest.setNdcName("0" + ndcVo.getNdc());
            
            //  UPN
            ndcSyncRequest.setUpn(ndcVo.getUpcUpn());
            
            // Manufacturer
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setManufacturerName(ndcVo.getManufacturer().getValue());
            manufacturer.setManufacturerIen(ndcVo.getManufacturer().getManufacturerIen());
            ndcSyncRequest.setManufacturer(manufacturer);
            
            // Trade Name
            if (ndcVo.getTradeName() != null) {
                ndcSyncRequest.setTradeName(ndcVo.getTradeName());
            }
            
            // vaProduct
            Product product = new Product();
            product.setProductName(ndcVo.getProduct().getValue());
            product.setProductIen(ndcVo.getProduct().getNdfProductIen().toString());
            ndcSyncRequest.setProduct(product);
            
            // Inactivation Date
            if (ndcVo.getInactivationDate() != null) {
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(ndcVo.getInactivationDate());
                
                try {
                    ndcSyncRequest.setInactivationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                } catch (DatatypeConfigurationException e) {
                    LOG.error("NDC Converter Inactivation Date mismatch" + ndcVo.getValue());
                    ndcSyncRequest.setInactivationDate(null);
                }
            }
            
            // Package Size
            if (ndcVo.getPackageSize() != null) {
                ndcSyncRequest.setPackageSize(ndcVo.getPackageSize().toString());
            }
            
            // Package Type
            if (ndcVo.getPackageType() != null) {
                PackageType packageType = new PackageType();
                packageType.setPackageTypeName(ndcVo.getPackageType().getValue());
                packageType.setPackageTypeIen(ndcVo.getPackageType().getPackagetypeIen());
                ndcSyncRequest.setPackageType(packageType);
            }
            
            // OtcRxIndicator
            if (ndcVo.getOtcRxIndicator() != null) {
                ndcSyncRequest.setOtcRxIndicator(ndcVo.getOtcRxIndicator().getValue());
            }
            
            // Action Type
            if (ItemAction.INACTIVATE.equals(itemAction)) {
                ndcSyncRequest.setRequestType(ItemAction.MODIFY.toString());
            } else {
                ndcSyncRequest.setRequestType(itemAction.toString());
            }
            
        }

        LOG.debug("Return " + ndcSyncRequest);
        return ndcSyncRequest;
    }
}

