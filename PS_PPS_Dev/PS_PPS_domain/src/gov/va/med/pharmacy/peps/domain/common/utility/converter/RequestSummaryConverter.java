/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.SubCategory;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplRequestDo;


/**
 * Convert to/from {@link RequestSummaryVo} and {@link EplRequestDo}.
 */
public class RequestSummaryConverter extends Converter<RequestSummaryVo, EplRequestDo> {
    
    private DosageFormConverter dosageFormConverter;
    private GenericNameConverter genericNameConverter;
    private DrugUnitConverter drugUnitConverter;
    private DispenseUnitConverter dispenseUnitConverter;

    /**
     * setGenericNameConverter
     * @param genericNameConverter genericNameConverter property
     */
    public void setGenericNameConverter(GenericNameConverter genericNameConverter) {
        this.genericNameConverter = genericNameConverter;
    }

    /**
     * A {@link RequestSummaryVo} is never converted into a {@link EplRequestDo}, therfore this method returns null.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplRequestDo toDataObject(RequestSummaryVo data) {
        return null;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a RequestSummaryVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated RequestSummaryVo
     */
    @Override
    protected RequestSummaryVo toValueObject(EplRequestDo data) {
        RequestSummaryVo summary = new RequestSummaryVo();
        summary.setRequestId(data.getId().toString());
        summary.setCreatedDate(data.getCreatedDtm());

        if (isNotNull(data.getEplNdc())) {
            summary.setRequestItemId(data.getEplNdc().getEplId().toString());
            summary.setEntityType(EntityType.NDC);

            summary.setLocalUse(toBoolean(data.getEplNdc().getLocalDispense()));

            EplNdcDo ndc = data.getEplNdc();
            EplProductDo product = ndc.getEplProduct();
            summary.setItemName(ndc.getNdcNumber());

            if (isNotNull(product.getEplOrderableItem().getEplDosageForm())) {
                summary.setDosageForm(dosageFormConverter.convertMinimal(product.getEplOrderableItem().getEplDosageForm()));
            }

            setCategoriesAndSubCategoriesOnSummary(summary, product.getEplOrderableItem());

            summary.setGenericName(genericNameConverter.convertMinimal(product.getEplVaGenName()));
            summary.setProductStrength(product.getStrength());
            summary.setProductUnit(drugUnitConverter.convertMinimal(product.getEplDrugUnit()));
            summary.setItemStatus(ItemStatus.valueOf(ndc.getItemStatus()));
        } else if (isNotNull(data.getEplProduct())) {
            EplProductDo product = data.getEplProduct();
            EplOrderableItemDo orderableItem = product.getEplOrderableItem();

            summary.setRequestItemId(data.getEplProduct().getEplId().toString());
            summary.setEntityType(EntityType.PRODUCT);
            
            setCategoriesAndSubCategoriesOnSummary(summary, product.getEplOrderableItem());
            
            summary.setProductStrength(product.getStrength());
            
            if (isNotNull(product.getEplDrugUnit())) {
                summary.setProductUnit(drugUnitConverter.convertMinimal(product.getEplDrugUnit()));
            }
            
            //summary.setDisplayableIngredientStrength(product.getEplProdIngredientAssocs().iterator().next().getStrength());
            //summary.setDisplayableIngredientUnit(drugUnitConverter.convertMinimal(
              //  product.getEplProdIngredientAssocs().iterator().next().getEplDrugUnit()).getValue());
            summary.setOiName(orderableItem.getOiName());
            summary.setDosageForm(dosageFormConverter.convertMinimal(orderableItem.getEplDosageForm()));

            summary.setVaProductName(product.getVaProductName());
            summary.setItemStatus(ItemStatus.valueOf(product.getItemStatus()));
            summary.setGenericName(genericNameConverter.convertMinimal(product.getEplVaGenName()));
            summary.setItemName(product.getVaProductName());
            summary.setDispenseUnit(dispenseUnitConverter.convertMinimal(product.getEplVaDispenseUnit()));
        } else if (isNotNull(data.getEplOrderableItem())) {
            EplOrderableItemDo orderableItem = data.getEplOrderableItem();

            summary.setRequestItemId(orderableItem.getEplId().toString());
            summary.setEntityType(EntityType.ORDERABLE_ITEM);
            summary.setOiName(orderableItem.getOiName());
            summary.setItemStatus(ItemStatus.valueOf(orderableItem.getItemStatus()));
            summary.setItemName(orderableItem.getOiName());
            summary.setDosageForm(dosageFormConverter.convertMinimal(orderableItem.getEplDosageForm()));
            setCategoriesAndSubCategoriesOnSummary(summary, orderableItem);
        } else if (isNotNull(data.getDomainId())) {

            summary.setRequestItemId(data.getDomainId().toString());
            summary.setEntityType(EntityType.toEntityType(data.getItemType()));

        }

        summary.setRequestType(RequestType.valueOf(data.getRequestType()));
        summary.setRequestItemStatus(RequestItemStatus.valueOf(data.getNewItemRequestStatus()));
        summary.setRequestedBy(toUser(data.getRequesterName()));
        summary.setReviewedBy(toUser(data.getLastReviewerName()));
        summary.setUnderReview(toBoolean(data.getUnderReviewFlag()));
        summary.setPsrName(data.getPsrName());
        summary.setRequestState(RequestState.valueOf(data.getRequestStatus()));

        return summary;
    }
    
    /**
     * isNotNull
     *
     * @param o Object
     * @return boolean
     */
    private static boolean isNotNull(Object o) {
        if (o == null) {
            
            return false;
        }
        
        return true;
    }

    /**
     * Sets the categories and sub categories on the summary
     * @param summary request summary
     * @param data data
     */
    private void setCategoriesAndSubCategoriesOnSummary(RequestSummaryVo summary, EplOrderableItemDo data) {

        List<Category> categories = new ArrayList<Category>();
        List<SubCategory> subCategories = new ArrayList<SubCategory>();

        //convert the categories
        if (data.getCatMedicFlag() != null) {
            if (data.getCatMedicFlag().equals("Y")) {
                categories.add(Category.MEDICATION);
            }
        }

      

        if (data.getCatCompoundFlag() != null) {
            if (data.getCatCompoundFlag().equals("Y")) {
                categories.add(Category.COMPOUND);
            }
        }
        
        if (data.getCatInvestFlag() != null) {
            if (data.getCatInvestFlag().equals("Y")) {
                categories.add(Category.INVESTIGATIONAL);
            }
        }

        if (data.getCatSupplyFlag() != null) {
            if (data.getCatSupplyFlag().equals("Y")) {
                categories.add(Category.SUPPLY);
            }
        }

        if (data.getSubcatChemoFlag() != null) {
            if (data.getSubcatChemoFlag().equals("Y")) {
                subCategories.add(SubCategory.CHEMOTHERAPY);
            }
        }
        
        //convert the sub-categories
        if (data.getSubcatHerbalFlag() != null) {
            if (data.getSubcatHerbalFlag().equals("Y")) {
                subCategories.add(SubCategory.HERBAL);
            }
        }

        if (data.getSubcatVeterFlag() != null) {
            if (data.getSubcatVeterFlag().equals("Y")) {
                subCategories.add(SubCategory.VETERINARY);
            }
        }

        if (data.getSubcatOtcFlag() != null) {
            if (data.getSubcatOtcFlag().equals("Y")) {
                subCategories.add(SubCategory.OTC);
            }
        }

      

        summary.setCategories(categories);
        summary.setSubCategories(subCategories);
    }

    /**
     * This method will set the DosageFormConverter
     * @param dosageFormConverter dosageFormConverter property
     */
    public void setDosageFormConverter(DosageFormConverter dosageFormConverter) {
        this.dosageFormConverter = dosageFormConverter;
    }

    /**
     * This method will set the DrugUnitConverter
     * @param drugUnitConverter drugUnitConverter property
     */
    public void setDrugUnitConverter(DrugUnitConverter drugUnitConverter) {
        this.drugUnitConverter = drugUnitConverter;
    }

    /**
     * This method will set the DispenseUnitConverter
     * @param dispenseUnitConverter dispenseUnitConverter property
     */
    public void setDispenseUnitConverter(DispenseUnitConverter dispenseUnitConverter) {
        this.dispenseUnitConverter = dispenseUnitConverter;
    }
}
