/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.ColorVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NdcSourceType;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.ShapeVo;
import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;
import gov.va.med.pharmacy.peps.common.vo.SubCategory;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;


/**
 * Convert to/from {@link NdcVo} and {@link EplNdcDo}.
 * 
 * 
 */
public class NdcConverter extends Converter<NdcVo, EplNdcDo> {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(NdcConverter.class);
    
    private ProductConverter productConverter;
    private DataFieldsConverter dataFieldsConverter;
    private OrderUnitConverter orderUnitConverter;
    private ManufacturerConverter manufacturerConverter;
    private PackageTypeConverter packageTypeConverter;

    /**
     * remove dashes from ndc to store in ndcNodashes
     * <p>
     * 000182-0141-01 to 000182014101
     * 
     * @param ndc String
     * @return String
     */
    public String createNdcNoDashes(String ndc) {
        String[] temp = null;
        temp = ndc.split("-");
        StringBuffer ndcNoDashes = new StringBuffer();

        for (int i = 0; i < temp.length; i++) {
            ndcNoDashes.append(temp[i]);
        }

        return ndcNoDashes.toString();
    }

    /**
     * setDataFieldsConverter.
     * @param dataFieldsConverter dataFieldsConverter property
     */
    public void setDataFieldsConverter(DataFieldsConverter dataFieldsConverter) {
        this.dataFieldsConverter = dataFieldsConverter;
    }

    /**
     * setProductConverter.
     * @param productConverter productConverter property
     */
    public void setProductConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    /**
     * Converts String color to ColorVo
     * 
     * @param color String
     * @return ColorVo
     */
    private ColorVo toColor(String color) {
        ColorVo colorVo = null;

        if (color != null && color.trim().length() > 0) {
            colorVo = new ColorVo();
            colorVo.setValue(color);
            colorVo.setId(color.toLowerCase(Locale.US));
        }

        return colorVo;
    }

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     */
    @Override
    protected EplNdcDo toDataObject(NdcVo data) {
        EplNdcDo ndcDo = new EplNdcDo();

        ndcDo.setCreatedBy(data.getCreatedBy());
        ndcDo.setCreatedDtm(data.getCreatedDate());
        ndcDo.setLastModifiedBy(data.getModifiedBy());
        ndcDo.setLastModifiedDtm(data.getModifiedDate());
        ndcDo.setTenDigitFormatIndication(data.getTenDigitFormatIndication());
        ndcDo.setTenDigitNdc(data.getTenDigitNdc());
        ndcDo.setFssI(data.getFssI());
  
        if (data.isFssPv()) {
            ndcDo.setFssPv("Y");
        } else {
            ndcDo.setFssPv("N");
        }
        
        ndcDo.setFssCntNo(data.getFssCntNo());
        ndcDo.setFssEndDate(data.getFssFssEnd());
        ndcDo.setFssPrice(data.getFssFssPrice());
        ndcDo.setFssVaPrice(data.getFssVaPrice());
        ndcDo.setFssBig4Price(data.getFssBig4Price());
        ndcDo.setFssBpaPrice(data.getFssBpaPrice());
        
        if (data.isFssBpaAvail()) {
            ndcDo.setFssBpaAvail("Y");
        } else {
            ndcDo.setFssBpaAvail("N");
        }
        
        ndcDo.setFssNcPrice(data.getFssNcPrice());
        
        if (data.getSingleMultiSourceProduct() != null) {
            ndcDo.setSingleMultiSource(data.getSingleMultiSourceProduct().getValue());
        }
     
        if (data.getNdcIen() != null) {
            ndcDo.setNdfNdcIen(new Long(data.getNdcIen()));
        }
        
        if (data.getColor() != null) {
            ndcDo.setColor(data.getColor().getValue());
        }

        ndcDo.setEplId(new Long(data.getId()));
        ndcDo.setVendor(data.getVendor());
        ndcDo.setVsn(data.getVendorStockNumber());
        ndcDo.setEplManufacturer(manufacturerConverter.convert(data.getManufacturer()));

        if (data.getNdc() != null) {
            ndcDo.setNdcNoDashes(createNdcNoDashes(data.getNdc()));
        }

        ndcDo.setItemStatus(data.getItemStatus().toString());
        ndcDo.setNdcNumber(data.getNdc());
        ndcDo.setRejectReasonText(data.getRejectionReasonText());

        if (data.getRequestRejectionReason() != null) {
            ndcDo.setRequestRejectReason(data.getRequestRejectionReason().toString());
        }

        ndcDo.setNdcDispUnitsPerOrdUnit(data.getNdcDispUnitsPerOrdUnit());

        if (data.getOtcRxIndicator() != null) {
            ndcDo.setOtcRx(data.getOtcRxIndicator().getValue());
        }

        ndcDo.setLocalDispense(toYesOrNo(data.isLocalDispense()));
        ndcDo.setEplOrderUnit(orderUnitConverter.convert(data.getOrderUnit()));
        ndcDo.setRevisionNumber(Long.valueOf(data.getRevisionNumber()));
        ndcDo.setSequenceNum(Long.valueOf(data.getSequenceNumber()));
        ndcDo.setInactivationDate(data.getInactivationDate());
        ndcDo.setEplPackageType(packageTypeConverter.convert(data.getPackageType()));
        
        ndcDo.setImprint(data.getImprint());
        ndcDo.setImprint2(data.getImprint2());
        
        if (data.getShape() != null) {
            ndcDo.setShape(data.getShape().getValue());
        }

        ndcDo.setPackageSize(data.getPackageSize());
        ndcDo.setTradeName(data.getTradeName());

        if (data.getRequestItemStatus() != null) {
            ndcDo.setRequestStatus(data.getRequestItemStatus().toString());
        }

        ndcDo.setUpcUpn(data.getUpcUpn());
        ndcDo.setSource(data.getSource().name());

        if (!(data.getVaDataFields().isEmpty())) {
            EplVadfOwnerDo owners = dataFieldsConverter.convert(data.getVaDataFields(), ndcDo);
            Set<EplVadfOwnerDo> ownerDOs = new HashSet<EplVadfOwnerDo>();
            ownerDOs.add(owners);
            ndcDo.setEplVadfOwners(ownerDOs);
        }

        // initialize categories
        ndcDo.setCatMedicFlag("N");
        ndcDo.setCatInvestFlag("N");
        ndcDo.setCatCompoundFlag("N");
        ndcDo.setCatSupplyFlag("N");
        
        // convert the categories
        if (data.getCategories() != null && data.getCategories().size() != 0) {
            for (int i = 0; i < data.getCategories().size(); i++) {
                
                if (Category.MEDICATION.equals(data.getCategories().get(i))) {
                    ndcDo.setCatMedicFlag("Y");
                } else if (Category.INVESTIGATIONAL.equals(data.getCategories().get(i))) {
                    ndcDo.setCatInvestFlag("Y");
                } else if (Category.COMPOUND.equals(data.getCategories().get(i))) {
                    ndcDo.setCatCompoundFlag("Y");
                } else if (Category.SUPPLY.equals(data.getCategories().get(i))) {
                    ndcDo.setCatSupplyFlag("Y");
                }
            }
        }
        
        ndcDo = toDataObjectCategory(ndcDo, data);
        
        // NDC must have a foreign key to product
        EplProductDo prod = new EplProductDo();
        prod.setEplId(new Long(data.getProduct().getId()));
        ndcDo.setEplProduct(prod);

        return ndcDo;
    }
    
    /**
     * toDataObjectCategory
     * @param dataObject dataObject
     * @param data data
     * @return EplNdcDo
     */
    private EplNdcDo toDataObjectCategory(EplNdcDo dataObject, NdcVo data) {
        
        EplNdcDo ndcDo = dataObject;
        
        // initialize categories
        ndcDo.setSubcatHerbalFlag("N");
        ndcDo.setSubcatChemoFlag("N");
        ndcDo.setSubcatOtcFlag("N");
        ndcDo.setSubcatVeterFlag("N");
        
        // convert the Sub-categories
        if (data.getSubCategories() != null && data.getSubCategories().size() != 0) {
            for (int i = 0; i < data.getSubCategories().size(); i++) {

                if (SubCategory.HERBAL.equals(data.getSubCategories().get(i))) {
                    ndcDo.setSubcatHerbalFlag("Y");
                } else if (SubCategory.CHEMOTHERAPY.equals(data.getSubCategories().get(i))) {
                    ndcDo.setSubcatChemoFlag("Y");
                } else if (SubCategory.OTC.equals(data.getSubCategories().get(i))) {
                    ndcDo.setSubcatOtcFlag("Y");
                } else if (SubCategory.VETERINARY.equals(data.getSubCategories().get(i))) {
                    ndcDo.setSubcatVeterFlag("Y");
                }
            }
        }
        
        return ndcDo;
    }

    /**
     * Copies data from the given {@link DataObject} into a NdcVo populated with enough data to be displayed in
     * the table of child NdcVo.
     * <p>
     * Default implementation calls {@link #toMinimalValueObject(DataObject)}.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated NdcVo
     */
    protected NdcVo toChildValueObject(EplNdcDo data) {
        NdcVo ndc = new NdcVo();
        ndc.setId(String.valueOf(data.getEplId()));
        ndc.setNdc(data.getNdcNumber());
        ndc.setUpcUpn(data.getUpcUpn());
        ndc.setTradeName(data.getTradeName());
        ndc.setPackageSize(data.getPackageSize());
        ndc.setVendorStockNumber(data.getVsn());
        ndc.setManufacturer(manufacturerConverter.convertMinimal(data.getEplManufacturer()));
        ndc.setPackageType(packageTypeConverter.convertMinimal(data.getEplPackageType()));
        ndc.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));

        
        
        List<FieldKey> vaDataFields = new ArrayList<FieldKey>();
        vaDataFields.add(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);
        vaDataFields.add(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
        ndc.setVaDataFields(dataFieldsConverter.convertFirst(data.getEplVadfOwners(), vaDataFields));

        return ndc;
    }

    /**
     * toMinimalValueObject
     * @param data is the eplNdcDo
     * @return NdcVo the minimal value object
    */
    @Override
    protected NdcVo toMinimalValueObject(EplNdcDo data) {
        NdcVo ndc = new NdcVo();
        ndc.setId(String.valueOf(data.getEplId()));
        ndc.setNdc(data.getNdcNumber());
        ndc.setUpcUpn(data.getUpcUpn());

        if (data.getNdfNdcIen() != null) {
            ndc.setNdcIen(data.getNdfNdcIen().toString());
        }

        return ndc;
    }

    /**
     * Copies data from the given EplNdcDo into a NdcVo populated with enough data to be displayed in
     * the search results table.
     * Default implementation calls toSearchValueObject
     * 
     * @param data EplNdcDo to convert
     * @return minimally populated {@link ValueObject}
     */
    @Override
    protected NdcVo toSearchValueObject(EplNdcDo data) {
        NdcVo ndc = new NdcVo();
        ndc.setId(String.valueOf(data.getEplId()));
        ndc.setNdc(data.getNdcNumber());
        ndc.setUpcUpn(data.getUpcUpn());
        ndc.setTradeName(data.getTradeName());
        ndc.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));

        if (data.getItemStatus() != null) {
            ndc.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        }

        ndc.setPackageSize(data.getPackageSize());
        ndc.setManufacturer(manufacturerConverter.convertMinimal(data.getEplManufacturer()));
        ndc.setPackageType(packageTypeConverter.convertMinimal(data.getEplPackageType()));
        ndc = convertCategories(data, ndc);

        ndc.setProduct(productConverter.convertSearch(data.getEplProduct()));

        return ndc;
    }
    
    /**
     * convertCategories
     * @param eplNdcDo eplNdcDo
     * @param ndc data
     * @return NdcVo
     */
    private NdcVo convertCategories(EplNdcDo eplNdcDo, NdcVo ndc) {

        List<Category> categories = new ArrayList<Category>();
        List<SubCategory> subCategories = new ArrayList<SubCategory>();
            
        //convert the categories
        if (eplNdcDo.getCatMedicFlag().equals("Y")) {
            categories.add(Category.MEDICATION);
        }

        if (eplNdcDo.getCatInvestFlag().equals("Y")) {
            categories.add(Category.INVESTIGATIONAL);
        }

        if (eplNdcDo.getCatCompoundFlag().equals("Y")) {
            categories.add(Category.COMPOUND);
        }

        if (eplNdcDo.getCatSupplyFlag().equals("Y")) {
            categories.add(Category.SUPPLY);
        }

        //convert the sub-categories
        if (eplNdcDo.getSubcatHerbalFlag().equals("Y")) {
            subCategories.add(SubCategory.HERBAL);
        }

        if (eplNdcDo.getSubcatChemoFlag().equals("Y")) {
            subCategories.add(SubCategory.CHEMOTHERAPY);
        }

        if (eplNdcDo.getSubcatOtcFlag().equals("Y")) {
            subCategories.add(SubCategory.OTC);
        }

        if (eplNdcDo.getSubcatVeterFlag().equals("Y")) {
            subCategories.add(SubCategory.VETERINARY);
        }

        ndc.setCategories(categories);
        ndc.setSubCategories(subCategories);
        
        return ndc;
    }

    /**
     * Converts String otc to OtcRxVo
     * 
     * @param otc needed to be converted
     * @return OtcRxVo
     */
    private OtcRxVo toOtcRx(String otc) {
        OtcRxVo otcRx = null;

        if (otc != null && otc.trim().length() > 0) {
            otcRx = new OtcRxVo();
            otcRx.setValue(otc);
            otcRx.setId(otc.toLowerCase(Locale.US));
        }

        return otcRx;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a NdcVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated NdcVo
     * 
     */
    @Override
    protected NdcVo toValueObject(EplNdcDo data) {
        NdcVo ndc = new NdcVo();

        ndc.setId(String.valueOf(data.getEplId()));
        ndc.setVendor(data.getVendor());
        ndc.setVendorStockNumber(data.getVsn());
        ndc.setColor(toColor(data.getColor()));
        ndc.setImprint(data.getImprint());
        ndc.setImprint2(data.getImprint2());
        ndc.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        ndc.setInactivationDate(data.getInactivationDate());
        ndc.setManufacturer(manufacturerConverter.convertMinimal(data.getEplManufacturer()));
        ndc.setTenDigitFormatIndication(data.getTenDigitFormatIndication());
        ndc.setTenDigitNdc(data.getTenDigitNdc());
        ndc.setNdcDispUnitsPerOrdUnit(data.getNdcDispUnitsPerOrdUnit());
        ndc.setNdc(data.getNdcNumber());
        ndc.setSequenceNumber(data.getSequenceNum().longValue());
        ndc.setOtcRxIndicator(toOtcRx(data.getOtcRx()));
        ndc.setRejectionReasonText(data.getRejectReasonText());
        ndc.setFssI(data.getFssI());
        
        if (data.getFssPv() == null) {
            ndc.setFssPv(false);
        } else {
            if (data.getFssPv().equals("Y")) {
                ndc.setFssPv(true);
            } else {
                ndc.setFssPv(false);
            }
        }
        
        ndc.setFssCntNo(data.getFssCntNo());
        ndc.setFssFssEnd(data.getFssEndDate());
        ndc.setFssFssPrice(data.getFssPrice());
        ndc.setFssVaPrice(data.getFssVaPrice());
        ndc.setFssBig4Price(data.getFssBig4Price());
        ndc.setFssBpaPrice(data.getFssBpaPrice());
        
        if (data.getFssBpaAvail() == null) {
            ndc.setFssBpaAvail(false);
        } else {
            if (data.getFssBpaAvail().equals("Y")) {
                ndc.setFssBpaAvail(true);
            } else {
                ndc.setFssBpaAvail(false);
            }
        }
        
        ndc.setFssNcPrice(data.getFssNcPrice());

        if (StringUtils.isNotEmpty(data.getSingleMultiSource())) {
            SingleMultiSourceProductVo singleMultiSourceProduct = new SingleMultiSourceProductVo();
            singleMultiSourceProduct.setValue(data.getSingleMultiSource());
            ndc.setSingleMultiSourceProduct(singleMultiSourceProduct);
        } else {
            ndc.setSingleMultiSourceProduct(null);
        }
        
        if (data.getNdfNdcIen() != null) {
            ndc.setNdcIen(data.getNdfNdcIen().toString());
        }
        
        
        if (data.getRequestRejectReason() != null) {
            ndc.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));
        }

        ndc.setPackageSize(data.getPackageSize().doubleValue());
        ndc.setPackageType(packageTypeConverter.convertMinimal(data.getEplPackageType()));

        ndc.setProduct(productConverter.convertParent(data.getEplProduct()));
        ndc.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        ndc.setOrderUnit(orderUnitConverter.convertMinimal(data.getEplOrderUnit()));

        if (data.getShape() != null) {
            ShapeVo shapeVo = new ShapeVo();
            shapeVo.setValue(data.getShape());
            shapeVo.setId(data.getShape().toLowerCase());
            ndc.setShape(shapeVo);
        }

        ndc.setUpcUpn(data.getUpcUpn());
        ndc.setLocalDispense(toBoolean(data.getLocalDispense()));
        ndc.setRevisionNumber(data.getRevisionNumber().longValue());
        ndc.setCreatedBy(data.getCreatedBy());
        ndc.setCreatedDate(data.getCreatedDtm());
        ndc.setModifiedBy(data.getLastModifiedBy());
        ndc.setModifiedDate(data.getLastModifiedDtm());
        ndc.setTradeName(data.getTradeName());
        ndc.setSource(NdcSourceType.valueOf(data.getSource()));

        // ndc.setDataFields(dataFieldsDomainCapability.retrieve(data.getEplId(), EntityType.NDC));
        ndc.setVaDataFields(dataFieldsConverter.convertFirst(data.getEplVadfOwners(), EntityType.NDC));
        ndc = convertCategories(data, ndc);
               
        return ndc;
    }

    /**
     * setOrderUnitConverter.
     * @param orderUnitConverter orderUnitConverter property
     */
    public void setOrderUnitConverter(OrderUnitConverter orderUnitConverter) {
        this.orderUnitConverter = orderUnitConverter;
    }

    /**
     * setManufacturerConverter.
     * @param manufacturerConverter manufacturerConverter property
     */
    public void setManufacturerConverter(ManufacturerConverter manufacturerConverter) {
        this.manufacturerConverter = manufacturerConverter;
    }

    /**
     * setPackageTypeConverter.
     * @param packageTypeConverter packageTypeConverter property
     */
    public void setPackageTypeConverter(PackageTypeConverter packageTypeConverter) {
        this.packageTypeConverter = packageTypeConverter;
    }

}
