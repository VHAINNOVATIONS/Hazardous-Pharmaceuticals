/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.AtcChoice;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayAdministrationVo;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayFinishingAnOrderVo;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayOrderEntryVo;
import gov.va.med.pharmacy.peps.common.vo.LabTestMonitorVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesAutoCreate;
import gov.va.med.pharmacy.peps.common.vo.ProductPackage;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;
import gov.va.med.pharmacy.peps.common.vo.SpecimenTypeVo;
import gov.va.med.pharmacy.peps.common.vo.SubCategory;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayAdministrationVo;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayFinishAnOrderVo;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayOrderEntryVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugClassAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugTextLAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugTextLAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugTextNAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugTextNAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdIngredientAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductLabDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductVitalDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;
import gov.va.med.pharmacy.peps.domain.common.utility.DataFieldPersistenceHelper;


/**
 * Convert to/from {@link ProductVo} and {@link EplProductDo}
 */
public class ProductConverter extends Converter<ProductVo, EplProductDo> {

//    private static final Logger LOG = Logger.getLogger(ProductConverter.class);

    private OrderableItemConverter orderableItemConverter;
    private DataFieldsConverter dataFieldsConverter;
    private ActiveIngredientConverter activeIngredientConverter;
    private AtcCanisterConverter atcCanisterConverter;
    private DrugClassGroupConverter drugClassGroupConverter;
    private LocalPossibleDosagesConverter localPossibleDosagesConverter;
    private CsFedScheduleConverter csFedScheduleConverter;
    private IfcapItemNumberConverter ifcapItemNumberConverter;
    private NdcByOutpatientSiteNdcConverter ndcByOutpatientSiteNdcConverter;
    private SynonymConverter synonymConverter;
    private DrugTextConverter drugTextConverter;
    private OrderUnitConverter orderUnitConverter;
    private GenericNameConverter genericNameConverter;
    private DoseUnitConverter doseUnitConverter;
    private DrugUnitConverter drugUnitConverter;
    private DispenseUnitConverter dispenseUnitConverter;
    private DosageFormConverter dosageFormConverter;
    private ReducedCopayConverter reducedCopayConverter;

    /**
     * Convert a Collection of EplProductDos into a List of fully populated ProductVos.
     * <p>
     * The default implementation should be sufficient for most scenarios, but
     * sub classes can override this method if necessary.
     * 
     * @param data Collection of EplProductDo's to convert
     * @return List of fully populated {@link ValueObject}
     * 
     * @see #convert(DataObject)
     */
    public List<ProductVo> convertAllSearch(Collection<EplProductDo> data) {
        List<ProductVo> values = new ArrayList<ProductVo>();

        if (data != null) {
            for (EplProductDo current : data) {
                ProductVo value = toSimpleSearchValueObject(current);

                if (value != null) {
                    values.add(value);
                }
            }
        }

        return values;
    }

    /**
     * Fully copies data from the given ProductVo into a
     * {@link DataObject}.
     * 
     * @param data ProductVo
     * @return fully populated {@link DataObject}
     * 
     */
    @Override
    protected EplProductDo toDataObject(ProductVo data) {
        EplProductDo productDo = new EplProductDo();

        productDo.setEplId(Long.valueOf(data.getId()));
        productDo.setCreatedBy(data.getCreatedBy());
        productDo.setCreatedDtm(data.getCreatedDate());
        productDo.setLastModifiedBy(data.getModifiedBy());
        productDo.setStrength(data.getProductStrength());
        productDo.setCmopId(data.getCmopId());
        productDo.setLastModifiedDtm(data.getModifiedDate());
        productDo.setAtcChoice((data.getAtcChoice() == null ? "" : data.getAtcChoice().toString()));
        productDo.setAtcMnemonic(data.getAtcMnemonic());
        productDo.setOneAtcCanister(data.getOneAtcCanister());
        productDo.setProductDispenseUnitsPerOrd(data.getProductDispenseUnitsPerOrd());

        if (data.getCreatePossibleDosage()) {
            productDo.setCreatePossibleDosage("Y");
        } else {
            productDo.setCreatePossibleDosage("N");
        }

        if (data.isExcludeDrgDrgInteractionCheck()) {
            productDo.setExcludeInteractionCheck("Y");
        } else {
            productDo.setExcludeInteractionCheck("N");
        }

        productDo.setFdaMedGuide(data.getFdaMedGuide());

        productDo.setNdfProductIen(data.getNdfProductIen());

        if (data.getServiceCode() == null) {
            if (productDo.getNdfProductIen() != null) {
                productDo.setServiceCode(productDo.getNdfProductIen() + PPSConstants.L600000);
            }

        } else {
            productDo.setServiceCode(Long.valueOf(data.getServiceCode()));
        }

        

        if (data.isMasterEntryForVuid()) {
            productDo.setMasterEntryForVuid("1");
        } else if (!data.isMasterEntryForVuid()) {
            productDo.setMasterEntryForVuid("0");
        }

        if (data.getDispenseUnit() != null) {
            if (StringUtils.isNotBlank(data.getDispenseUnit().getValue())) {
                productDo.setEplVaDispenseUnit(dispenseUnitConverter.convertMinimal(data.getDispenseUnit()));
            }
        }

        if (data.getProductUnit() != null) {
            if (StringUtils.isNotBlank(data.getProductUnit().getValue())) {
                productDo.setEplDrugUnit(drugUnitConverter.convertMinimal(data.getProductUnit()));
            }
        }

        productDo.setEplAtcCanisters(atcCanisterConverter.convert(data.getAtcCanisters(), productDo));
        productDo.setEplSynonyms(synonymConverter.convert(data.getSynonyms(), productDo));
        productDo
            .setEplLocalPossibleDosages(localPossibleDosagesConverter.convert(data.getLocalPossibleDosages(), productDo));

        productDo.setPrevMarkedForLocalUseYn(toYesOrNo(data.isLocalUse() || data.isPreviouslyMarkedForLocalUse()));
        productDo.setEplIfcapItemNumbers(ifcapItemNumberConverter.convert(data.getIfcapItemNumber(), productDo));
        productDo.setEplReducedCopay(reducedCopayConverter.convert(data.getReducedCopay(), productDo));
        productDo.setRejectReasonText(data.getRejectionReasonText());

        if ((data.getGcnSequenceNumber() != null) && (data.getGcnSequenceNumber().trim().length() > 0)) {
            productDo.setGcnSeqno(Long.valueOf(data.getGcnSequenceNumber()));
        }

        productDo.setTallmanLettering(data.getTallManLettering());
        productDo.setVaPrintName(data.getVaPrintName());
        productDo.setInactivationDate(data.getInactivationDate());
        productDo.setLocalPrintName(data.getLocalPrintName());
        productDo.setLocalSpecialHandling(data.getLocalSpecialHandling());

        if (data.getLabTestMonitor() != null) {
            productDo.setLabTestMonitor(data.getLabTestMonitor().getValue());
        }

        productDo.setIen(data.getIen());

        if (data.getRequestRejectionReason() != null) {
            productDo.setRequestRejectReason(data.getRequestRejectionReason().name());
        }

        if (data.getSpecimenType() != null) {
            productDo.setSpecimenType(data.getSpecimenType().getValue());
        }

        if (data.getGenericName() != null) {
            productDo.setEplVaGenName(genericNameConverter.convertMinimal(data.getGenericName()));
        }

        if (data.getItemStatus() != null) {
            productDo.setItemStatus(data.getItemStatus().name());
        }

        productDo.setLocalUse(toYesOrNo(data.isLocalUse()));

        if (data.getDoseUnitVo() != null) {
            productDo.setEplDoseUnit(doseUnitConverter.convert(data.getDoseUnitVo()));
        }

        productDo.setEplCsFedSchedule(csFedScheduleConverter.convert(data.getCsFedSchedule()));
        productDo.setEplProdDrugClassAssocs(drugClassGroupConverter.convert(data.getDrugClasses(), productDo));

        productDo = toDataObject2(productDo, data);
        productDo = toDataObject3(productDo, data);

        return productDo;
    }

    /**
     * toDataObject2
     * @param dataObject dataObject
     * @param data data
     * @return EplProductDo
     */
    private EplProductDo toDataObject2(EplProductDo dataObject, ProductVo data) {
        
        EplProductDo productDo = dataObject;
        
        if ((data.getLocalDrugTexts() != null) && (!data.getLocalDrugTexts().isEmpty())) {
            Set<EplProdDrugTextLAssocDo> assocs = new HashSet<EplProdDrugTextLAssocDo>();

            for (DrugTextVo drug : data.getLocalDrugTexts()) {
                EplProdDrugTextLAssocDoKey key = new EplProdDrugTextLAssocDoKey();
                key.setDrugTextIdFk(Long.valueOf(drug.getId()));
                key.setEplIdProdFk(Long.valueOf(data.getId()));
                EplProdDrugTextLAssocDo assoc = new EplProdDrugTextLAssocDo();
                assoc.setKey(key);
                assocs.add(assoc);
            }

            productDo.setEplProdDrugTextLAssocs(assocs);
        }

        if ((data.getNationalDrugTexts() != null) && (!data.getNationalDrugTexts().isEmpty())) {
            Set<EplProdDrugTextNAssocDo> assocs = new HashSet<EplProdDrugTextNAssocDo>();

            for (DrugTextVo drug : data.getNationalDrugTexts()) {
                EplProdDrugTextNAssocDoKey key = new EplProdDrugTextNAssocDoKey();
                key.setDrugTextIdFk(Long.valueOf(drug.getId()));
                key.setEplIdProdFk(Long.valueOf(data.getId()));
                EplProdDrugTextNAssocDo assoc = new EplProdDrugTextNAssocDo();
                assoc.setKey(key);
                assocs.add(assoc);
            }

            productDo.setEplProdDrugTextNAssocs(assocs);

        }

        productDo.setEplProdIngredientAssocs(activeIngredientConverter.convert(data.getActiveIngredients(), productDo));

        if (data.getRequestItemStatus() != null) {
            productDo.setRequestStatus(data.getRequestItemStatus().name());
        }

        productDo.setVaProductName(data.getVaProductName());
        productDo.setNationalFormularyName(data.getNationalFormularyName());
        productDo.setLocalSpecialHandling(data.getLocalSpecialHandling());
        productDo.setNationalFormularyIndicator(toYesOrNo(data.getNationalFormularyIndicator()));
        productDo.setOverrideDfDoseChkExclusn(toYesOrNo(data.isOverrideDfDoseChkExclusn()));
        productDo.setCmopDispenseYn(toYesOrNo(data.getCmopDispense()));
        productDo.setRevisionNumber(Long.valueOf(data.getRevisionNumber()));

        if (data.getMaxDosePerDay() != null) {
            productDo.setMaxDosePerDay(data.getMaxDosePerDay().shortValue());
        }

        if (data.getOrderUnit() != null) {
            productDo.setEplOrderUnit(orderUnitConverter.convert(data.getOrderUnit()));
        }

        productDo.setEplNdcByOutpatientSiteNdcs(ndcByOutpatientSiteNdcConverter.convert(data.getNdcByOutpatientSiteNdc(),
                                                                                        productDo));

        if (data.getSingleMultiSourceProduct() != null) {
            productDo.setSingleMultiSourceProduct(data.getSingleMultiSourceProduct().getValue());
        }

        productDo.setVuid(data.getVuid());
        productDo.setEplId(Long.valueOf(data.getId()));

//        if (data.getLabDisplayAdministration() != null) {
//            for (LabDisplayAdministrationVo labVo : data.getLabDisplayAdministration()) {
//                if (labVo != null) {
//                    EplProductLabDo caniDo = new EplProductLabDo();
//                    caniDo.setLabDisplayAdministration(labVo.getValue());
//                    caniDo.setLabDisplayFinishAnOrder(null);
//                    caniDo.setLabDisplayOrderEntry(null);
//                    caniDo.setEplProduct(productDo);
//                    productDo.getEplProductLabs().add(caniDo);
//                }
//            }
//        }
//
//        if (data.getLabDisplayOrderEntry() != null) {
//            for (LabDisplayOrderEntryVo labVo : data.getLabDisplayOrderEntry()) {
//                if (labVo != null) {
//                    EplProductLabDo caniDo = new EplProductLabDo();
//                    caniDo.setLabDisplayAdministration(null);
//                    caniDo.setLabDisplayFinishAnOrder(null);
//                    caniDo.setLabDisplayOrderEntry(labVo.getValue());
//                    caniDo.setEplProduct(productDo);
//                    productDo.getEplProductLabs().add(caniDo);
//                }
//            }
//        }
//
//        if (data.getLabDisplayFinishingAnOrder() != null) {
//            for (LabDisplayFinishingAnOrderVo labVo : data.getLabDisplayFinishingAnOrder()) {
//                if (labVo != null) {
//                    EplProductLabDo caniDo = new EplProductLabDo();
//                    caniDo.setLabDisplayAdministration(null);
//                    caniDo.setLabDisplayFinishAnOrder(labVo.getValue());
//                    caniDo.setLabDisplayOrderEntry(null);
//                    caniDo.setEplProduct(productDo);
//                    productDo.getEplProductLabs().add(caniDo);
//                }
//            }
//        }
//
//        if (data.getVitalsDisplayAdministration() != null) {
//            for (VitalsDisplayAdministrationVo vitalsVo : data.getVitalsDisplayAdministration()) {
//                if (vitalsVo != null) {
//                    EplProductVitalDo caniDo = new EplProductVitalDo();
//                    caniDo.setVitalDisplayAdministration(vitalsVo.getValue());
//                    caniDo.setVitalDisplayFinishAnOrder(null);
//                    caniDo.setVitalDisplayOrderEntry(null);
//                    caniDo.setEplProduct(productDo);
//                    productDo.getEplProductVitals().add(caniDo);
//                }
//            }
//        }
//
//        if (data.getVitalsDisplayOrderEntry() != null) {
//            for (VitalsDisplayOrderEntryVo vitalsVo : data.getVitalsDisplayOrderEntry()) {
//                if (vitalsVo != null) {
//                    EplProductVitalDo caniDo = new EplProductVitalDo();
//                    caniDo.setVitalDisplayAdministration(null);
//                    caniDo.setVitalDisplayFinishAnOrder(null);
//                    caniDo.setVitalDisplayOrderEntry(vitalsVo.getValue());
//                    caniDo.setEplProduct(productDo);
//                    productDo.getEplProductVitals().add(caniDo);
//                }
//            }
//        }


        
        return productDo;
    }

    /**
     * toDataObject3
     * @param dataObject dataObject
     * @param data data
     * @return EplProductDo
     */
    private EplProductDo toDataObject3(EplProductDo dataObject, ProductVo data) {
        
        EplProductDo productDo = dataObject;
        
//        if (data.getVitalsDisplayFinishAnOrder() != null) {
//            for (VitalsDisplayFinishAnOrderVo vitalsVo : data.getVitalsDisplayFinishAnOrder()) {
//                if (vitalsVo != null) {
//                    EplProductVitalDo caniDo = new EplProductVitalDo();
//                    caniDo.setVitalDisplayAdministration(null);
//                    caniDo.setVitalDisplayFinishAnOrder(vitalsVo.getValue());
//                    caniDo.setVitalDisplayOrderEntry(null);
//                    caniDo.setEplProduct(productDo);
//                    productDo.getEplProductVitals().add(caniDo);
//                }
//            }
//        }
        
        if (data.getPossibleDosagesAutoCreate() != null) {
            if (data.getPossibleDosagesAutoCreate().equals(PossibleDosagesAutoCreate.NO_POSSIBLE_DOSAGES)) {
                productDo.setPossibleDosagesToCreate(PPSConstants.NO_POSSIBLE_DOSAGES);
            } else if (data.getPossibleDosagesAutoCreate().equals(PossibleDosagesAutoCreate.ONE_X_POSSIBLE_DOSAGES)) {
                productDo.setPossibleDosagesToCreate(PPSConstants.ONE_X_POSSIBLE_DOSAGES);
            } else if (data.getPossibleDosagesAutoCreate().equals(PossibleDosagesAutoCreate.TWO_X_POSSIBLE_DOSAGES)) {
                productDo.setPossibleDosagesToCreate(PPSConstants.TWO_X_POSSIBLE_DOSAGES);
            }
        }

        if (data.getProductPackage() != null) {
            if (data.getProductPackage().equals(ProductPackage.INPATIENT)) {
                productDo.setProductPackage(PPSConstants.INPATIENT);
            } else if (data.getProductPackage().equals(ProductPackage.OUTPATIENT)) {
                productDo.setProductPackage(PPSConstants.OUTPATIENT);
            } else if (data.getProductPackage().equals(ProductPackage.BOTH)) {
                productDo.setProductPackage(PPSConstants.BOTH);
            }
        }
        
        if (!(data.getVaDataFields().isEmpty())) {
            DataFieldPersistenceHelper.removeAllFieldsNotReadyToPersist(data.getVaDataFields());
            EplVadfOwnerDo owners = dataFieldsConverter.convert(data.getVaDataFields(), productDo);
            Set<EplVadfOwnerDo> ownerDOs = new HashSet<EplVadfOwnerDo>();
            ownerDOs.add(owners);
            productDo.setEplVadfOwners(ownerDOs);
        }

        // only set the the Orderable for the the foreign key to Orderable item
        EplOrderableItemDo order = new EplOrderableItemDo();
        order.setEplId(Long.valueOf(data.getOrderableItem().getId()));
        productDo.setEplOrderableItem(order);

        // initialize categories
        productDo.setCatMedicFlag("N");
        productDo.setCatInvestFlag("N");
        productDo.setCatCompoundFlag("N");
        productDo.setCatSupplyFlag("N");

        // convert the categories
        if (data.getCategories() != null && data.getCategories().size() != 0) {
            for (int i = 0; i < data.getCategories().size(); i++) {

                //Set the categories
                if (Category.MEDICATION.equals(data.getCategories().get(i))) {
                    productDo.setCatMedicFlag("Y");
                } else if (Category.INVESTIGATIONAL.equals(data.getCategories().get(i))) {
                    productDo.setCatInvestFlag("Y");
                } else if (Category.COMPOUND.equals(data.getCategories().get(i))) {
                    productDo.setCatCompoundFlag("Y");
                } else if (Category.SUPPLY.equals(data.getCategories().get(i))) {
                    productDo.setCatSupplyFlag("Y");
                }
            }
        }

        // initialize categories
        productDo.setSubcatHerbalFlag("N");
        productDo.setSubcatChemoFlag("N");
        productDo.setSubcatOtcFlag("N");
        productDo.setSubcatVeterFlag("N");

        // convert the sSub-categories
        if (data.getSubCategories() != null && data.getSubCategories().size() != 0) {
            for (int i = 0; i < data.getSubCategories().size(); i++) {

                //Set the sub-categories
                if (SubCategory.HERBAL.equals(data.getSubCategories().get(i))) {
                    productDo.setSubcatHerbalFlag("Y");
                } else if (SubCategory.CHEMOTHERAPY.equals(data.getSubCategories().get(i))) {
                    productDo.setSubcatChemoFlag("Y");
                } else if (SubCategory.OTC.equals(data.getSubCategories().get(i))) {
                    productDo.setSubcatOtcFlag("Y");
                } else if (SubCategory.VETERINARY.equals(data.getSubCategories().get(i))) {
                    productDo.setSubcatVeterFlag("Y");
                }
            }
        }
        
        return productDo;
    }
    
    /**
     * setCategories
     * @param product product
     * @param data data
     */
    private void setCategories(ProductVo product, EplProductDo data) {

        List<Category> categories = new ArrayList<Category>();
        List<SubCategory> subCategories = new ArrayList<SubCategory>();

        //convert the categories
        if (data.getCatMedicFlag() != null) {
            if (data.getCatMedicFlag().equals("Y")) {
                categories.add(Category.MEDICATION);
            }
        }

        if (data.getCatInvestFlag() != null) {
            if (data.getCatInvestFlag().equals("Y")) {
                categories.add(Category.INVESTIGATIONAL);
            }
        }

        if (data.getCatCompoundFlag() != null) {
            if (data.getCatCompoundFlag().equals("Y")) {
                categories.add(Category.COMPOUND);
            }
        }

        if (data.getCatSupplyFlag() != null) {
            if (data.getCatSupplyFlag().equals("Y")) {
                categories.add(Category.SUPPLY);
            }
        }

        //convert the sub-categories
        if (data.getSubcatHerbalFlag() != null) {
            if (data.getSubcatHerbalFlag().equals("Y")) {
                subCategories.add(SubCategory.HERBAL);
            }
        }

        if (data.getSubcatChemoFlag() != null) {
            if (data.getSubcatChemoFlag().equals("Y")) {
                subCategories.add(SubCategory.CHEMOTHERAPY);
            }
        }

        if (data.getSubcatOtcFlag() != null) {
            if (data.getSubcatOtcFlag().equals("Y")) {
                subCategories.add(SubCategory.OTC);
            }
        }

        if (data.getSubcatVeterFlag() != null) {
            if (data.getSubcatVeterFlag().equals("Y")) {
                subCategories.add(SubCategory.VETERINARY);
            }
        }

        product.setCategories(categories);
        product.setSubCategories(subCategories);
    }

    /**
     * Minimally copies data from the given {@link ValueObject} into a EplProductDo
     * <p>
     * The returned EplProductDo likely only has the primary key data
     * populated.
     * <p>
     * Default implementation calls {@link #toDataObject(ValueObject)}.
     * 
     * @param data The ProductVo to convert
     * @return minimally populated {@link DataObject}
     */
    @Override
    protected EplProductDo toMinimalDataObject(ProductVo data) {
        EplProductDo productDo = new EplProductDo();
        productDo.setEplId(Long.valueOf(data.getId()));

        return productDo;
    }

    /**
     * Copies data from the given {@link DataObject} into a ProductVo
     * populated with enough data to be displayed in the table of child
     * {@link ValueObject}.
     * <p>
     * Default implementation calls {@link #toMinimalValueObject(DataObject)}.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated ProductVo
     */
    @Override
    protected ProductVo toChildValueObject(EplProductDo data) {
        ProductVo product = new ProductVo();
        product.setId(String.valueOf(data.getEplId()));
        product.setVaProductName(data.getVaProductName());
        product.setVaPrintName(data.getVaPrintName());
        product.setInactivationDate(data.getInactivationDate());
        product.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        product.setGenericName(genericNameConverter.convertMinimal(data.getEplVaGenName()));
        product.setNdcCount(data.getEplNdcs().size());
        product.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        product.setPreviouslyMarkedForLocalUse(toBoolean(data.getPrevMarkedForLocalUseYn()));

        
        if (data.getNationalFormularyIndicator() != null) {
            product.setNationalFormularyIndicator(toBoolean(data.getNationalFormularyIndicator()));
        }
        
        // set the DrugClasses
        if (data.getEplProdDrugClassAssocs() != null) {
            for (EplProdDrugClassAssocDo drugClass : data.getEplProdDrugClassAssocs()) {
                if (toBoolean(drugClass.getPrimaryYn())) {
                    DrugClassGroupVo drugClassGroup = drugClassGroupConverter.convert(drugClass);
                    product.getDrugClasses().add(drugClassGroup);
                }
            }
        }

        // set the Ingredients
        if (data.getEplProdIngredientAssocs() != null && !data.getEplProdIngredientAssocs().isEmpty()) {
            for (EplProdIngredientAssocDo assocsDo : data.getEplProdIngredientAssocs()) {
                if (toBoolean(assocsDo.getActiveYn())) {
                    ActiveIngredientVo activeIngredient = activeIngredientConverter.convert(assocsDo);
                    product.getActiveIngredients().add(activeIngredient);
                }
            }
        }

        List<FieldKey> vaDataFields = new ArrayList<FieldKey>();
        vaDataFields.add(FieldKey.APPLICATION_PACKAGE_USE);
        vaDataFields.add(FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT);
        vaDataFields.add(FieldKey.TRANSMIT_TO_CMOP);
        vaDataFields.add(FieldKey.FORMULARY);
        product.setVaDataFields(dataFieldsConverter.convertFirst(data.getEplVadfOwners(), vaDataFields));

        return product;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a
     * {@link ValueObject}.
     * <p>
     * The ID, VA Product Name, VA Print Name, Generic Name, Primary Drug Class,
     * Active Ingredient, CMOP, and FORMULARY fields are populated.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toMinimalValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected ProductVo toMinimalValueObject(EplProductDo data) {
        ProductVo product = new ProductVo();
        product.setId(String.valueOf(data.getEplId()));
        product.setVaProductName(data.getVaProductName());
        product.setCmopId(data.getCmopId());

        if (data.getCmopDispenseYn() != null) {
            product.setCmopDispense(toBoolean(data.getCmopDispenseYn()));
        }

        if (data.getNationalFormularyIndicator() != null) {
            product.setNationalFormularyIndicator(toBoolean(data.getNationalFormularyIndicator()));
        }
        
        product.setProductStrength(data.getStrength());
        product.setProductUnit(drugUnitConverter.convertMinimal(data.getEplDrugUnit()));
        product.setGenericName(genericNameConverter.convertMinimal(data.getEplVaGenName()));
        product.setDrugClasses(drugClassGroupConverter.convert(data.getEplProdDrugClassAssocs()));
        product.setNdfProductIen(data.getNdfProductIen());
        product.setPreviouslyMarkedForLocalUse(toBoolean(data.getPrevMarkedForLocalUseYn()));

        if (data.getGcnSeqno() == null) {
            product.setGcnSequenceNumber(null);
        } else {

            // add leading zeros if less than 6 digits
            String target = String.valueOf(data.getGcnSeqno());
            target = StringUtils.leftPad(target, PPSConstants.I6, '0');
            
            if ("000000".equals(target)) {
                product.setGcnSequenceNumber(null);
            } else {
                product.setGcnSequenceNumber(target);
            }
        }

        if (data.getItemStatus() != null) {
            product.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        }

        if (data.getRequestStatus() != null) {
            product.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        }

        setCategories(product, data);

        return product;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a
     * ProductVo.
     * <p>
     * The returned {@link ValueObject} likely only has enough data for the
     * {@link ValueObject#toShortString()}, {@link ValueObject#getUniqueId()}
     * methods to be called without getting nulls, and whatever additional data
     * is required for display and processing of a parent item.
     * <p>
     * First calls {@link #toMinimalValueObject(EplProductDo)}, and then also
     * populates the VA Print Name and VA Dispense Unit.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated ProductVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toParentValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected ProductVo toParentValueObject(EplProductDo data) {
        ProductVo product = toMinimalValueObject(data);
        product.setVaPrintName(data.getVaPrintName());
        product.setDispenseUnit(dispenseUnitConverter.convertMinimal(data.getEplVaDispenseUnit()));

        return product;
    }

    /**
     * toSearchValueObject Copies data from the given EplProductDo into a ProductVo
     * populated with enough data to be displayed in the search results table.
     * 
     * @param data EplProductDo to convert
     * @return minimally populated ProductVo
     */
    @Override
    protected ProductVo toSearchValueObject(EplProductDo data) {
        ProductVo product = new ProductVo();
        product.setId(String.valueOf(data.getEplId()));
        product.setVaProductName(data.getVaProductName());
        product.setLocalPrintName(data.getLocalPrintName());
        product.setVaPrintName(data.getVaPrintName());
        product.setProductStrength(data.getStrength());
        product.setCmopId(data.getCmopId());
       
        if (data.getCmopDispenseYn() != null) {
            product.setCmopDispense(toBoolean(data.getCmopDispenseYn()));
        }
        
        if (data.getNationalFormularyIndicator() != null) {
            product.setNationalFormularyIndicator(toBoolean(data.getNationalFormularyIndicator()));
        }
        
        product.setLocalUse(toBoolean(data.getLocalUse()));
        product.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        product.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        product.setNdcCount(data.getEplNdcs().size());
        product.setPreviouslyMarkedForLocalUse(toBoolean(data.getPrevMarkedForLocalUseYn()));
        product.setProductDispenseUnitsPerOrd(data.getProductDispenseUnitsPerOrd());

        if (data.getExcludeInteractionCheck() == null) {
            product.setExcludeDrgDrgInteractionCheck(false);
        } else {
            if (data.getExcludeInteractionCheck().equals("Y")) {
                product.setExcludeDrgDrgInteractionCheck(true);
            } else {
                product.setExcludeDrgDrgInteractionCheck(false);
            }
        }
        
        if (data.getCreatePossibleDosage() == null) {
            product.setCreatePossibleDosage(false);
        } else {
            if (data.getCreatePossibleDosage().equals("Y")) {
                product.setCreatePossibleDosage(true);
            } else {
                product.setCreatePossibleDosage(false);
            }
        }
        
        // added because  the closest product matches uses this converter.
        if (data.getGcnSeqno() == null || data.getGcnSeqno() == 0) {
            product.setGcnSequenceNumber(null);
        } else {
            product.setGcnSequenceNumber(data.getGcnSeqno().toString());
        }

        product.setFdaMedGuide(data.getFdaMedGuide());
        product.setNdfProductIen(data.getNdfProductIen());

        product.setProductDispenseUnitsPerOrd(data.getProductDispenseUnitsPerOrd());

        if (StringUtils.isNotEmpty(data.getPossibleDosagesToCreate())) {

            if (data.getPossibleDosagesToCreate().equals(PPSConstants.NO_POSSIBLE_DOSAGES)) {
                product.setPossibleDosagesAutoCreate(PossibleDosagesAutoCreate.NO_POSSIBLE_DOSAGES);
            } else if (data.getPossibleDosagesToCreate().equals(PPSConstants.ONE_X_POSSIBLE_DOSAGES)) {
                product.setPossibleDosagesAutoCreate(PossibleDosagesAutoCreate.ONE_X_POSSIBLE_DOSAGES);
            } else if (data.getPossibleDosagesToCreate().equals(PPSConstants.TWO_X_POSSIBLE_DOSAGES)) {
                product.setPossibleDosagesAutoCreate(PossibleDosagesAutoCreate.TWO_X_POSSIBLE_DOSAGES);
            }
        }

        // if the ProductPackage is not empty set the values.
        if (StringUtils.isNotEmpty(data.getProductPackage())) {
            if (data.getProductPackage().equals(PPSConstants.BOTH)) {
                product.setProductPackage(ProductPackage.BOTH);
            } else if (data.getProductPackage().equals(PPSConstants.INPATIENT)) {
                product.setProductPackage(ProductPackage.INPATIENT);
            } else if (data.getProductPackage().equals(PPSConstants.OUTPATIENT)) {
                product.setProductPackage(ProductPackage.OUTPATIENT);
            }
        }

        // set the service code if it isn't null,  if it is null then it should stay null.
        if (data.getServiceCode() != null) {
            product.setServiceCode(data.getServiceCode().toString());
        }

        product.setOrderableItem(orderableItemConverter.convertMinimal(data.getEplOrderableItem()));
        product.getOrderableItem().setDosageForm(dosageFormConverter.convertMinimal(data.getEplOrderableItem()
                                                         .getEplDosageForm()));

        product.setGenericName(genericNameConverter.convertMinimal(data.getEplVaGenName()));

        product.setProductUnit(drugUnitConverter.convertMinimal(data.getEplDrugUnit()));

        if (data.getEplProdDrugClassAssocs() != null) {
            for (EplProdDrugClassAssocDo drugClass : data.getEplProdDrugClassAssocs()) {
                if (toBoolean(drugClass.getPrimaryYn())) {
                    DrugClassGroupVo drugClassGroup = drugClassGroupConverter.convert(drugClass);
                    product.getDrugClasses().add(drugClassGroup);
                }
            }
        }

        if (data.getEplProdIngredientAssocs() != null && !data.getEplProdIngredientAssocs().isEmpty()) {
            for (EplProdIngredientAssocDo assocsDo : data.getEplProdIngredientAssocs()) {

                ActiveIngredientVo activeIngredient = activeIngredientConverter.convert(assocsDo);
                product.getActiveIngredients().add(activeIngredient);

            }
        }

        setCategories(product, data);
        product.setSynonyms(synonymConverter.convert(data.getEplSynonyms()));
        List<FieldKey> vaDataFields = new ArrayList<FieldKey>();

        vaDataFields.add(FieldKey.TRANSMIT_TO_CMOP);
        vaDataFields.add(FieldKey.FORMULARY);
        product.setVaDataFields(dataFieldsConverter.convertFirst(data.getEplVadfOwners(), vaDataFields));
        product.setDispenseUnit(dispenseUnitConverter.convert(data.getEplVaDispenseUnit()));

        return product;
    }

    /**
     * toSimpleSearchValueObject
     * Sets only the fields necessary for simple search to work. 
     * 
     * @param data {@link DataObject} to convert
     * @return minimal value object
     */
    private ProductVo toSimpleSearchValueObject(EplProductDo data) {

        ProductVo product = new ProductVo();
        product.setId(data.getEplId().toString());
        product.setVaPrintName(data.getVaPrintName());
        
        if (data.getNationalFormularyIndicator() != null) {
            product.setNationalFormularyIndicator(toBoolean(data.getNationalFormularyIndicator()));
        }
        
        product.setSynonyms(synonymConverter.convert(data.getEplSynonyms()));
        product.setDispenseUnit(dispenseUnitConverter.convertMinimal(data.getEplVaDispenseUnit()));
        product.setGenericName(genericNameConverter.convertMinimal(data.getEplVaGenName()));
        product.setOrderableItem(orderableItemConverter.convertParent(data.getEplOrderableItem()));
        product.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        product.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        product.setNationalFormularyName(data.getNationalFormularyName());
        product.setVaProductName(data.getVaProductName());
        product.setProductStrength(data.getStrength());
        product.setCmopId(data.getCmopId());
        product.setDrugClasses(drugClassGroupConverter.convert(data.getEplProdDrugClassAssocs()));
        product.setActiveIngredients(activeIngredientConverter.convert(data.getEplProdIngredientAssocs()));
        product.setProductUnit(drugUnitConverter.convertMinimal(data.getEplDrugUnit()));
        product.setNdcCount(data.getEplNdcs().size());

        //Set the categories
        setCategories(product, data);

        if (data.getCmopDispenseYn() != null) {
            product.setCmopDispense(toBoolean(data.getCmopDispenseYn()));
        }
        
        return product;
    }

    /**
     * The toValueObject method Fully copies data from the given {@link DataObject} into a
     * ProductVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data EplProductDo to convert to value object
     * @return fully populated ProductVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected ProductVo toValueObject(EplProductDo data) {
        ProductVo product = new ProductVo();
        initializeProduct(data, product);

        if (data.getGcnSeqno() == null) {
            product.setGcnSequenceNumber(null);
        } else {

            // add leading zeros if less than 6 digits
            String target = String.valueOf(data.getGcnSeqno());
            target = StringUtils.leftPad(target, PPSConstants.I6, '0');
            
            if ("000000".equals(target)) {
                product.setGcnSequenceNumber(null);
            } else {
                product.setGcnSequenceNumber(target);
            }
        }

        
        if (data.getCreatePossibleDosage() == null) {
            product.setCreatePossibleDosage(false);
        } else {
            product.setCreatePossibleDosage("Y".equals(data.getCreatePossibleDosage()));
        }

        if (data.getExcludeInteractionCheck() == null) {
            product.setExcludeDrgDrgInteractionCheck(false);
        } else {
            product.setExcludeDrgDrgInteractionCheck("Y".equals(data.getExcludeInteractionCheck()));
        }

        product.setFdaMedGuide(data.getFdaMedGuide());
        product.setNdfProductIen(data.getNdfProductIen());

        if (data.getPossibleDosagesToCreate() != null) {
            if (data.getPossibleDosagesToCreate().equals(PPSConstants.NO_POSSIBLE_DOSAGES)) {
                product.setPossibleDosagesAutoCreate(PossibleDosagesAutoCreate.NO_POSSIBLE_DOSAGES);
            } else if (data.getPossibleDosagesToCreate().equals(PPSConstants.ONE_X_POSSIBLE_DOSAGES)) {
                product.setPossibleDosagesAutoCreate(PossibleDosagesAutoCreate.ONE_X_POSSIBLE_DOSAGES);
            } else if (data.getPossibleDosagesToCreate().equals(PPSConstants.TWO_X_POSSIBLE_DOSAGES)) {
                product.setPossibleDosagesAutoCreate(PossibleDosagesAutoCreate.TWO_X_POSSIBLE_DOSAGES);
            }
        }

        if (StringUtils.isNotEmpty(data.getProductPackage())) {
            if (data.getProductPackage().equals(PPSConstants.BOTH)) {
                product.setProductPackage(ProductPackage.BOTH);
            } else if (data.getProductPackage().equals(PPSConstants.INPATIENT)) {
                product.setProductPackage(ProductPackage.INPATIENT);
            } else if (data.getProductPackage().equals(PPSConstants.OUTPATIENT)) {
                product.setProductPackage(ProductPackage.OUTPATIENT);
            }
        }

        if (data.getServiceCode() != null) {
            product.setServiceCode(data.getServiceCode().toString());
        }

        if (data.getMasterEntryForVuid() != null) {
            product.setMasterEntryForVuid("1".equals(data.getMasterEntryForVuid()));
        }

        product.setOverrideDfDoseChkExclusn(toBoolean(data.getOverrideDfDoseChkExclusn()));

        if (data.getCmopDispenseYn() != null) {
            product.setCmopDispense(toBoolean(data.getCmopDispenseYn()));
        }

        if (data.getNationalFormularyIndicator() != null) {
            product.setNationalFormularyIndicator(toBoolean(data.getNationalFormularyIndicator()));
        }
        
        product.setPreviouslyMarkedForLocalUse(toBoolean(data.getPrevMarkedForLocalUseYn()));

        SpecimenTypeVo spec = new SpecimenTypeVo();
        spec.setValue(data.getSpecimenType());
        product.setSpecimenType(spec);

        product.setAtcCanisters(atcCanisterConverter.convert(data.getEplAtcCanisters()));
        product.setIfcapItemNumber(ifcapItemNumberConverter.convert(data.getEplIfcapItemNumbers()));
        product.setSynonyms(synonymConverter.convert(data.getEplSynonyms()));

        product.setLocalPossibleDosages(localPossibleDosagesConverter.convert(data.getEplLocalPossibleDosages()));
        product.setNdcByOutpatientSiteNdc(ndcByOutpatientSiteNdcConverter.convert(data.getEplNdcByOutpatientSiteNdcs()));

//        product.setNationalPossibleDosages(nationalPossibleDosagesConverter.convert(data.getEplNationalPossibleDosages()));
        product.setReducedCopay(reducedCopayConverter.convert(data.getEplReducedCopay()));
        product.setCsFedSchedule(csFedScheduleConverter.convert(data.getEplCsFedSchedule()));
        product.setDoseUnitVo(doseUnitConverter.convertMinimal(data.getEplDoseUnit()));
        product.setDispenseUnit(dispenseUnitConverter.convertMinimal(data.getEplVaDispenseUnit()));
        product.setGenericName(genericNameConverter.convertMinimal(data.getEplVaGenName()));

        extractDrugText(data, product);

        product.setOrderableItem(orderableItemConverter.convertParent(data.getEplOrderableItem()));
        DataFieldPersistenceHelper.addProductFieldsNotYetPersisted(product.getVaDataFields());

        if (data.getGcnSeqno() == null) {
            product.setGcnSequenceNumber(null);
        } else {
            String target = String.valueOf(data.getGcnSeqno());
            target = StringUtils.leftPad(target, PPSConstants.I6, '0'); // add leading zeros if less than 6 digits
            product.setGcnSequenceNumber(target);
        }

        product.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        product.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        product.setNationalFormularyName(data.getNationalFormularyName());

        if (data.getRequestRejectReason() != null) {
            product.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));
        }

        if (data.getMaxDosePerDay() != null) {
            product.setMaxDosePerDay(data.getMaxDosePerDay().longValue());
        }

        product.setOrderUnit(orderUnitConverter.convertMinimal(data.getEplOrderUnit()));

        if (StringUtils.isNotEmpty(data.getSingleMultiSourceProduct())) {
            SingleMultiSourceProductVo singleMultiSourceProduct = new SingleMultiSourceProductVo();
            singleMultiSourceProduct.setValue(data.getSingleMultiSourceProduct());
            product.setSingleMultiSourceProduct(singleMultiSourceProduct);
        } else {
            product.setSingleMultiSourceProduct(null);
        }

        product.setVuid(data.getVuid());
        product.setVaProductName(data.getVaProductName());
        product.setRevisionNumber(data.getRevisionNumber().longValue());
        product.setLocalUse(toBoolean(data.getLocalUse()));
        product.setProductStrength(data.getStrength());
        product.setCmopId(data.getCmopId());
        product.setDrugClasses(drugClassGroupConverter.convert(data.getEplProdDrugClassAssocs()));
        product.setActiveIngredients(activeIngredientConverter.convert(data.getEplProdIngredientAssocs()));
        product.setProductUnit(drugUnitConverter.convertMinimal(data.getEplDrugUnit()));

        extractProductLabs(data, product);
        extractProductVitals(data, product);

        product.setNdcCount(data.getEplNdcs().size());

//        product.setLocalWarningLabels(localRxConsultAssociationConverter.convert(data.getEplProdWarnLabelLAssocs()));
//        product.setNationalWarningLabels(nationalRxConsultAssociationConverter.convert(data.getEplProdWarnLabelNAssocs()));

        // product.setDataFields(dataFieldsDomainCapability.retrieve(data.getEplId(),
        // EntityType.PRODUCT));
        product.setVaDataFields(dataFieldsConverter.convertFirst(data.getEplVadfOwners(), EntityType.PRODUCT));

        //Set the categories
        setCategories(product, data);

        return product;
    }

    /**
     * initializeProduct
     *
     * @param data EplProductDo
     * @param product ProductVo
     */
    private void initializeProduct(EplProductDo data, ProductVo product) {
        product.setId(data.getEplId().toString());
        product.setVaPrintName(data.getVaPrintName());
        product.setLocalSpecialHandling(data.getLocalSpecialHandling());
        product.setAtcChoice((data.getAtcChoice() != null 
            && data.getAtcChoice().trim().length() > 0 ? AtcChoice.valueOf(data.getAtcChoice()) : null));
        product.setAtcMnemonic(data.getAtcMnemonic());
        product.setOneAtcCanister(data.getOneAtcCanister());
        product.setTallManLettering(data.getTallmanLettering());
        product.setRejectionReasonText(data.getRejectReasonText());
        product.setCreatedBy(data.getCreatedBy());
        product.setCreatedDate(data.getCreatedDtm());
        product.setModifiedBy(data.getLastModifiedBy());
        product.setLocalPrintName(data.getLocalPrintName());
        product.setModifiedDate(data.getLastModifiedDtm());
        product.setInactivationDate(data.getInactivationDate());
        LabTestMonitorVo labTestMonitor = new LabTestMonitorVo();
        labTestMonitor.setValue(data.getLabTestMonitor());
        product.setLabTestMonitor(labTestMonitor);
        product.setIen(data.getIen());
        product.setProductDispenseUnitsPerOrd(data.getProductDispenseUnitsPerOrd());
    }
    
    /**
     * extractProductLabs
     *
     * @param data EplProductDo
     * @param product ProductVo
     */
    private void extractProductLabs(EplProductDo data, ProductVo product) {
        if (data.getEplProductLabs() != null) {
            for (EplProductLabDo labDo : data.getEplProductLabs()) {
                if (labDo.getLabDisplayAdministration() != null) {
                    LabDisplayAdministrationVo lab = new LabDisplayAdministrationVo();
                    lab.setValue(labDo.getLabDisplayAdministration());
                    product.getLabDisplayAdministration().add(lab);
                }

                if (labDo.getLabDisplayOrderEntry() != null) {
                    LabDisplayOrderEntryVo lab = new LabDisplayOrderEntryVo();
                    lab.setValue(labDo.getLabDisplayOrderEntry());
                    product.getLabDisplayOrderEntry().add(lab);
                }

                if (labDo.getLabDisplayFinishAnOrder() != null) {
                    LabDisplayFinishingAnOrderVo lab = new LabDisplayFinishingAnOrderVo();
                    lab.setValue(labDo.getLabDisplayFinishAnOrder());
                    product.getLabDisplayFinishingAnOrder().add(lab);
                }
            }
        }
    }
    
    /**
     * extractProductVitals
     *
     * @param data EplProductDo
     * @param product ProductVo
     */
    private void extractProductVitals(EplProductDo data, ProductVo product) {
        if (data.getEplProductVitals() != null) {
            for (EplProductVitalDo vitalDo : data.getEplProductVitals()) {
                if (vitalDo.getVitalDisplayAdministration() != null) {
                    VitalsDisplayAdministrationVo vitals = new VitalsDisplayAdministrationVo();
                    vitals.setValue(vitalDo.getVitalDisplayAdministration());
                    product.getVitalsDisplayAdministration().add(vitals);
                }

                if (vitalDo.getVitalDisplayOrderEntry() != null) {
                    VitalsDisplayOrderEntryVo vitals = new VitalsDisplayOrderEntryVo();
                    vitals.setValue(vitalDo.getVitalDisplayOrderEntry());
                    product.getVitalsDisplayOrderEntry().add(vitals);
                }

                if (vitalDo.getVitalDisplayFinishAnOrder() != null) {
                    VitalsDisplayFinishAnOrderVo vitals = new VitalsDisplayFinishAnOrderVo();
                    vitals.setValue(vitalDo.getVitalDisplayFinishAnOrder());
                    product.getVitalsDisplayFinishAnOrder().add(vitals);
                }
            }
        }
    }
    
    /**
     * extractDrugText
     *
     * @param data EplProductDo
     * @param product ProductVo
     */
    private void extractDrugText(EplProductDo data, ProductVo product) {
        if (data.getEplProdDrugTextLAssocs() != null && data.getEplProdDrugTextLAssocs().size() > 0) {
            List<DrugTextVo> drugs = new ArrayList<DrugTextVo>();

            for (EplProdDrugTextLAssocDo text : data.getEplProdDrugTextLAssocs()) {

                if (text.getEplDrugText() != null) {
                    DrugTextVo drug = drugTextConverter.convertMinimal(text.getEplDrugText());
                    drugs.add(drug);
                }
            }

            product.setLocalDrugTexts(drugs);
        }

        if (data.getEplProdDrugTextNAssocs() != null && data.getEplProdDrugTextNAssocs().size() > 0) {
            List<DrugTextVo> drugs = new ArrayList<DrugTextVo>();

            for (EplProdDrugTextNAssocDo text : data.getEplProdDrugTextNAssocs()) {

                if (text.getEplDrugText() != null) {
                    DrugTextVo drug = drugTextConverter.convertMinimal(text.getEplDrugText());
                    drugs.add(drug);
                }
            }

            product.setNationalDrugTexts(drugs);
        }
    }
    
    /**
     * setDataFieldsConverter
     * @param dataFieldsConverter
     *            dataFieldsConverter property
     */
    public void setDataFieldsConverter(DataFieldsConverter dataFieldsConverter) {
        this.dataFieldsConverter = dataFieldsConverter;
    }

    /**
     * setOrderableItemConverter
     * @param orderableItemConverter
     *            orderableItemConverter property
     */
    public void setOrderableItemConverter(OrderableItemConverter orderableItemConverter) {
        this.orderableItemConverter = orderableItemConverter;
    }

    /**
     * setActiveIngredientConverter
     * @param activeIngredientConverter
     *            activeIngredientConverter property
     */
    public void setActiveIngredientConverter(ActiveIngredientConverter activeIngredientConverter) {
        this.activeIngredientConverter = activeIngredientConverter;
    }

    /**
     * setAtcCanisterConverter
     * @param atcCanisterConverter
     *            atcCanisterConverter property
     */
    public void setAtcCanisterConverter(AtcCanisterConverter atcCanisterConverter) {
        this.atcCanisterConverter = atcCanisterConverter;
    }

    /**
     * setDrugClassGroupConverter
     * @param drugClassGroupConverter
     *            drugClassGroupConverter property
     */
    public void setDrugClassGroupConverter(DrugClassGroupConverter drugClassGroupConverter) {
        this.drugClassGroupConverter = drugClassGroupConverter;
    }

    /**
     * setLocalPossibleDosagesConverter
     * @param localPossibleDosagesConverter
     *            localPossibleDosagesConverter property
     */
    public void setLocalPossibleDosagesConverter(LocalPossibleDosagesConverter localPossibleDosagesConverter) {
        this.localPossibleDosagesConverter = localPossibleDosagesConverter;
    }

    /**
     * setCsFedScheduleConverter
     * @param csFedScheduleConverter
     *            csFedScheduleConverter property
     */
    public void setCsFedScheduleConverter(CsFedScheduleConverter csFedScheduleConverter) {
        this.csFedScheduleConverter = csFedScheduleConverter;
    }

    /**
     * setIfcapItemNumberConverter
     * @param ifcapItemNumberConverter
     *            ifcapItemNumberConverter property
     */
    public void setIfcapItemNumberConverter(IfcapItemNumberConverter ifcapItemNumberConverter) {
        this.ifcapItemNumberConverter = ifcapItemNumberConverter;
    }

    /**
     * setNdcByOutpatientSiteNdcConverter
     * @param ndcByOutpatientSiteNdcConverter
     *            ndcByOutpatientSiteNdcConverter property
     */
    public void setNdcByOutpatientSiteNdcConverter(NdcByOutpatientSiteNdcConverter ndcByOutpatientSiteNdcConverter) {
        this.ndcByOutpatientSiteNdcConverter = ndcByOutpatientSiteNdcConverter;
    }

    /**
     * setSynonymConverter for ProductConverter.
     * @param synonymConverter
     *            synonymConverter property
     */
    public void setSynonymConverter(SynonymConverter synonymConverter) {
        this.synonymConverter = synonymConverter;
    }

    /**
     * setDrugTextConverter
     * @param drugTextConverter
     *            drugTextConverter property
     */
    public void setDrugTextConverter(DrugTextConverter drugTextConverter) {
        this.drugTextConverter = drugTextConverter;
    }

    /**
     * setOrderUnitConverter
     * @param orderUnitConverter
     *            orderUnitConverter property
     */
    public void setOrderUnitConverter(OrderUnitConverter orderUnitConverter) {
        this.orderUnitConverter = orderUnitConverter;
    }

    /**
     * setGenericNameConverter
     * @param genericNameConverter
     *            genericNameConverter property
     */
    public void setGenericNameConverter(GenericNameConverter genericNameConverter) {
        this.genericNameConverter = genericNameConverter;
    }

    /**
     * setDoseUnitConverter
     * @param doseUnitConverter
     *            doseUnitConverter property
     */
    public void setDoseUnitConverter(DoseUnitConverter doseUnitConverter) {
        this.doseUnitConverter = doseUnitConverter;
    }

    /**
     * setDispenseUnitConverter
     * @param dispenseUnitConverter
     *            dispenseUnitConverter property
     */
    public void setDispenseUnitConverter(DispenseUnitConverter dispenseUnitConverter) {
        this.dispenseUnitConverter = dispenseUnitConverter;
    }

    /**
     * setDosageFormConverter
     * @param dosageFormConverter dosageFormConverter property
     */
    public void setDosageFormConverter(DosageFormConverter dosageFormConverter) {
        this.dosageFormConverter = dosageFormConverter;
    }

    /**
     * setDrugUnitConverter
     * @param drugUnitConverter drugUnitConverter property
     */
    public void setDrugUnitConverter(DrugUnitConverter drugUnitConverter) {
        this.drugUnitConverter = drugUnitConverter;
    }

    /**
     * setReducedCopayConverter
     * @param reducedCopayConverter reducedCopayConverter property
     */
    public void setReducedCopayConverter(ReducedCopayConverter reducedCopayConverter) {
        this.reducedCopayConverter = reducedCopayConverter;
    }
}
