/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPartialSaveMgtDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;


/**
 * Convert to/from {@link PartialSaveVo} and {@link EplPartialSaveMgtDo}.
 */
public class PartialSaveConverter extends Converter<PartialSaveVo, EplPartialSaveMgtDo> {
    private OrderableItemConverter orderableItemConverter;
    private ProductConverter productConverter;
    private NdcConverter ndcConverter;

    /**
     * getNdcConverter
     * @return ndcConverter property
     */
    public NdcConverter getNdcConverter() {
        return ndcConverter;
    }

    /**
     * getOrderableItemConverter
     * @return orderableItemConverter property
     */
    public OrderableItemConverter getOrderableItemConverter() {
        return orderableItemConverter;
    }

    /**
     * getProductConverter
     * @return productConverter property
     */
    public ProductConverter getProductConverter() {
        return productConverter;
    }

    /**
     * setNdcConverter
     * @param ndcConverter ndcConverter property
     */
    public void setNdcConverter(NdcConverter ndcConverter) {
        this.ndcConverter = ndcConverter;
    }

    /**
     * setOrderableItemConverter
     * @param orderableItemConverter orderableItemConverter property
     */
    public void setOrderableItemConverter(OrderableItemConverter orderableItemConverter) {
        this.orderableItemConverter = orderableItemConverter;
    }

    /**
     * setProductConverter
     * @param productConverter productConverter property
     */
    public void setProductConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    /**
     * Fully copies data from the given PartialSaveVo into a {@link DataObject}.
     * 
     * @param data PartialSaveVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplPartialSaveMgtDo toDataObject(PartialSaveVo data) {
        EplPartialSaveMgtDo save = new EplPartialSaveMgtDo();
        save.setComments(data.getComment());
        save.setFileName(data.getFileName());
        save.setEntityType(data.getEntityType().name());
        save.setItemRevisionNumber(data.getItemRevisionNumber());
        save.setRequestType(data.getRequestType().toString());
        save.setLastModifiedBy(data.getModifiedBy());
        save.setLastModifiedDtm(data.getModifiedDate());

        if (data.getNdcVo() != null) {
            EplNdcDo ndc = new EplNdcDo();
            ndc.setEplId(new Long(data.getNdcVo().getId()));
            save.setEplNdc(ndc);
        }

        if (data.getOrderableItemVo() != null) {
            EplOrderableItemDo item = new EplOrderableItemDo();
            item.setEplId(new Long(data.getOrderableItemVo().getId()));
            save.setEplOrderableItem(item);
        }

        if (data.getProductVo() != null) {
            EplProductDo ndc = new EplProductDo();
            ndc.setEplId(new Long(data.getProductVo().getId()));
            save.setEplProduct(ndc);
        }

        return save;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a PartialSaveVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated PartialSaveVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected PartialSaveVo toValueObject(EplPartialSaveMgtDo data) {
        PartialSaveVo vo = new PartialSaveVo();
        vo.setComment(data.getComments());
        vo.setFileName(data.getFileName());
        vo.setId(data.getId().toString());
        vo.setItemRevisionNumber(data.getItemRevisionNumber());
        vo.setModifiedBy(data.getLastModifiedBy());
        vo.setModifiedDate(data.getLastModifiedDtm());
        vo.setRequestType(RequestType.valueOf(data.getRequestType()));
        vo.setEntityType(EntityType.valueOf(data.getEntityType()));

        if (data.getEplNdc() != null) {
            vo.setNdcVo(ndcConverter.convertMinimal(data.getEplNdc()));
        } 
        
        if (data.getEplOrderableItem() != null) {
            vo.setOrderableItemVo(orderableItemConverter.convertMinimal(data.getEplOrderableItem()));
        } 
        
        if (data.getEplProduct() != null) {
            vo.setProductVo(productConverter.convertMinimal(data.getEplProduct()));
        }

        return vo;
    }

}
