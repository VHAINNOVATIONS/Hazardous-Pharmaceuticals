/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleAssocVo;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayAdministrationVo;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayFinishingAnOrderVo;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayOrderEntryVo;
import gov.va.med.pharmacy.peps.common.vo.OiRouteVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemType;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.SubCategory;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayAdministrationVo;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayFinishAnOrderVo;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayOrderEntryVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplLabDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiAdminSchedAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiAdminSchedAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiDrugTextLAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiDrugTextLAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiDrugTextNAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiDrugTextNAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiMedRouteAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiMedRouteAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVitalDo;


/**
 * Convert to/from {@link OrderableItemVo} and {@link EplOrderableItemDo}
 * 
 */
public class OrderableItemConverter extends Converter<OrderableItemVo, EplOrderableItemDo> {

    //  private static final Logger LOG = Logger.getLogger(OrderableItemConverter.class);

    private DataFieldsConverter dataFieldsConverter;
    private DrugTextConverter drugTextConverter;
    private DosageFormConverter dosageFormConverter;
    private StandardMedRouteConverter standardMedRouteConverter;
    private AdminScheduleConverter adminScheduleConverter;
    private LocalMedicationRouteConverter localMedicationRouteConverter;
    private OiScheduleTypeConverter oiScheduleTypeConverter;

    /**
     * setDrugTextConverter.
     * @param drugTextConverter drugTextConverter property
     */
    public void setDrugTextConverter(DrugTextConverter drugTextConverter) {
        this.drugTextConverter = drugTextConverter;
    }

    /**
     * setDataFieldsConverter.
     * @param dataFieldsConverter dataFieldsConverter property
     */
    public void setDataFieldsConverter(DataFieldsConverter dataFieldsConverter) {
        this.dataFieldsConverter = dataFieldsConverter;
    }

    /**
     * Fully copies data from the given OrderableItemVo into a {@link DataObject}.
     * 
     * @param data OrderableItemVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplOrderableItemDo toDataObject(OrderableItemVo data) {
        EplOrderableItemDo orderableItemDo = new EplOrderableItemDo();
        orderableItemDo.setCreatedBy(data.getCreatedBy());
        orderableItemDo.setCreatedDtm(data.getCreatedDate());
        orderableItemDo.setLastModifiedBy(data.getModifiedBy());
        orderableItemDo.setLastModifiedDtm(data.getModifiedDate());
        orderableItemDo.setHighAlert(data.getHighAlert());
        orderableItemDo.setSpecialInstructions(data.getSpecialInstructions());

        if (data.getDosageForm() != null) {
            orderableItemDo.setEplDosageForm(dosageFormConverter.convertMinimal(data.getDosageForm()));
        }

        if (data.getStandardMedRoute() != null) {
            orderableItemDo.setEplStandardMedRoute(standardMedRouteConverter.convertMinimal(data.getStandardMedRoute()));
        }

        if ((data.getLocalDrugTexts() != null) && (!data.getLocalDrugTexts().isEmpty())) {
            Set<EplOiDrugTextLAssocDo> assocs = new HashSet<EplOiDrugTextLAssocDo>();

            for (DrugTextVo drug : data.getLocalDrugTexts()) {
                EplOiDrugTextLAssocDoKey key = new EplOiDrugTextLAssocDoKey();
                key.setDrugTextIdFk(Long.valueOf(drug.getId()));
                key.setEplIdOiFk(Long.valueOf(data.getId()));
                EplOiDrugTextLAssocDo assoc = new EplOiDrugTextLAssocDo();
                assoc.setKey(key);
                assocs.add(assoc);
            }

            orderableItemDo.setEplOiDrugTextLAssocs(assocs);
        }

        if ((data.getNationalDrugTexts() != null) && (!data.getNationalDrugTexts().isEmpty())) {
            Set<EplOiDrugTextNAssocDo> assocs = new HashSet<EplOiDrugTextNAssocDo>();

            for (DrugTextVo drug : data.getNationalDrugTexts()) {
                EplOiDrugTextNAssocDoKey key = new EplOiDrugTextNAssocDoKey();
                key.setDrugTextIdFk(Long.valueOf(drug.getId()));
                key.setEplIdOiFk(Long.valueOf(data.getId()));
                EplOiDrugTextNAssocDo assoc = new EplOiDrugTextNAssocDo();
                assoc.setKey(key);
                assocs.add(assoc);
            }

            orderableItemDo.setEplOiDrugTextNAssocs(assocs);

        }

        if (data.getOiRoute() != null) {
            Set<EplOiMedRouteAssocDo> routeAssocs = new HashSet<EplOiMedRouteAssocDo>();

            for (OiRouteVo oiRouteVo : data.getOiRoute()) {
                if ((oiRouteVo != null) && (oiRouteVo.getLocalMedicationRoute() != null)) {
                    EplOiMedRouteAssocDo assocs = toEplOiMedRouteAssocDo(oiRouteVo, data.getId());
                    routeAssocs.add(assocs);
                }

            }

            orderableItemDo.setEplOiMedRouteAssocs(routeAssocs);
        }

        if (data.getAdminSchedules() != null) {
            Set<EplOiAdminSchedAssocDo> scheduleAssocs = new HashSet<EplOiAdminSchedAssocDo>();

            for (AdministrationScheduleAssocVo drug : data.getAdminSchedules()) {
                EplOiAdminSchedAssocDo schedule = toEplOiAmdinScheduleAssocDo(drug, data.getId());
                scheduleAssocs.add(schedule);

            }

            orderableItemDo.setEplOiAdminSchedAssocs(scheduleAssocs);
        }

        orderableItemDo.setEplOiScheduleType(oiScheduleTypeConverter.convert(data.getOiScheduleType()));

        orderableItemDo.setNonVaMed(toYesOrNo(data.getNonVaMed()));
        orderableItemDo.setEplId(Long.valueOf(data.getId()));
        orderableItemDo.setOiName(data.getOiName());
        orderableItemDo.setVistaOiName(data.getVistaOiName());
        orderableItemDo.setInactivationDate(data.getInactivationDate());

        if (data.getRequestRejectionReason() != null) {
            orderableItemDo.setRequestRejectReason(data.getRequestRejectionReason().toString());
        }

        orderableItemDo.setNationalFormularyIndicator(toYesOrNo(data.getNationalFormularyIndicator()));

        orderableItemDo.setPrevMarkedForLocalUseYn(toYesOrNo(data.isLocalUse() || data.isPreviouslyMarkedForLocalUse()));

        if (data.isNational()) {
            orderableItemDo.setOiType("N");
        } else {
            orderableItemDo.setOiType("L");
        }

        orderableItemDo.setLocalUse(toYesOrNo(data.isLocalUse()));
        orderableItemDo.setRequestStatus(data.getRequestItemStatus().toString());
        orderableItemDo.setItemStatus(data.getItemStatus().toString());
        orderableItemDo.setRejectReasonText(data.getRejectionReasonText());
        orderableItemDo.setRevisionNumber(new Long(data.getRevisionNumber()));

        // need to set parent once the value object has it

        if (data.getOrderableItemParent() != null) {
            EplOrderableItemDo item = new EplOrderableItemDo();
            item.setEplId(new Long(data.getOrderableItemParent().getId()));
            item.setOiName(data.getOrderableItemParent().getOiName());
            orderableItemDo.setEplOrderableItem(item);
        }

        return toDataObject2(orderableItemDo, data);
    }

    /**
     * toDataObject2
     * @param orderableItemDo orderableItemDo
     * @param data data
     * @return EplOrderableItemDo
     */
    private EplOrderableItemDo toDataObject2(EplOrderableItemDo orderableItemDo, OrderableItemVo data) {

        if (!(data.getVaDataFields().isEmpty())) {
            EplVadfOwnerDo owners = dataFieldsConverter.convert(data.getVaDataFields(), orderableItemDo);
            Set<EplVadfOwnerDo> ownerDOs = new HashSet<EplVadfOwnerDo>();
            ownerDOs.add(owners);
            orderableItemDo.setEplVadfOwners(ownerDOs);
        }

        if (data.getLabDisplayAdministration() != null) {
            for (LabDisplayAdministrationVo labVo : data.getLabDisplayAdministration()) {
                EplLabDo caniDo = new EplLabDo();
                caniDo.setLabDisplayAdministration(labVo.getValue());
                caniDo.setLabDisplayFinishAnOrder(null);
                caniDo.setLabDisplayOrderEntry(null);
                caniDo.setEplOrderableItem(orderableItemDo);
                orderableItemDo.getEplLabs().add(caniDo);
            }
        }

        if (data.getLabDisplayOrderEntry() != null) {
            for (LabDisplayOrderEntryVo labVo : data.getLabDisplayOrderEntry()) {
                EplLabDo caniDo = new EplLabDo();
                caniDo.setLabDisplayAdministration(null);
                caniDo.setLabDisplayFinishAnOrder(null);
                caniDo.setLabDisplayOrderEntry(labVo.getValue());
                caniDo.setEplOrderableItem(orderableItemDo);
                orderableItemDo.getEplLabs().add(caniDo);
            }
        }

        if (data.getLabDisplayFinishingAnOrder() != null) {
            for (LabDisplayFinishingAnOrderVo labVo : data.getLabDisplayFinishingAnOrder()) {
                EplLabDo caniDo = new EplLabDo();
                caniDo.setLabDisplayAdministration(null);
                caniDo.setLabDisplayFinishAnOrder(labVo.getValue());
                caniDo.setLabDisplayOrderEntry(null);
                caniDo.setEplOrderableItem(orderableItemDo);
                orderableItemDo.getEplLabs().add(caniDo);
            }
        }

        if (data.getVitalsDisplayAdministration() != null) {
            for (VitalsDisplayAdministrationVo vitalsVo : data.getVitalsDisplayAdministration()) {
                EplVitalDo caniDo = new EplVitalDo();
                caniDo.setVitalDisplayAdministration(vitalsVo.getValue());
                caniDo.setVitalDisplayFinishAnOrder(null);
                caniDo.setVitalDisplayOrderEntry(null);
                caniDo.setEplOrderableItem(orderableItemDo);
                orderableItemDo.getEplVitals().add(caniDo);
            }
        }

        if (data.getVitalsDisplayOrderEntry() != null) {
            for (VitalsDisplayOrderEntryVo vitalsVo : data.getVitalsDisplayOrderEntry()) {
                EplVitalDo caniDo = new EplVitalDo();
                caniDo.setVitalDisplayAdministration(null);
                caniDo.setVitalDisplayFinishAnOrder(null);
                caniDo.setVitalDisplayOrderEntry(vitalsVo.getValue());
                caniDo.setEplOrderableItem(orderableItemDo);
                orderableItemDo.getEplVitals().add(caniDo);
            }
        }

        if (data.getVitalsDisplayFinishAnOrder() != null) {
            for (VitalsDisplayFinishAnOrderVo vitalsVo : data.getVitalsDisplayFinishAnOrder()) {
                EplVitalDo caniDo = new EplVitalDo();
                caniDo.setVitalDisplayAdministration(null);
                caniDo.setVitalDisplayFinishAnOrder(vitalsVo.getValue());
                caniDo.setVitalDisplayOrderEntry(null);
                caniDo.setEplOrderableItem(orderableItemDo);
                orderableItemDo.getEplVitals().add(caniDo);
            }
        }

        return toDataObject3(orderableItemDo, data);
    }

    /**
     * toDataObject3
     * @param orderableItemDo orderableItemDo
     * @param data data
     * @return EplOrderableItemDo
     */
    private EplOrderableItemDo toDataObject3(EplOrderableItemDo orderableItemDo, OrderableItemVo data) {

        // initialize categories
        orderableItemDo.setCatMedicFlag("N");
        orderableItemDo.setCatInvestFlag("N");
        orderableItemDo.setCatCompoundFlag("N");
        orderableItemDo.setCatSupplyFlag("N");

        // convert the categories
        if (data.getCategories() != null && data.getCategories().size() != 0) {
            for (int i = 0; i < data.getCategories().size(); i++) {

                //Set the categories
                if (Category.MEDICATION.equals(data.getCategories().get(i))) {
                    orderableItemDo.setCatMedicFlag("Y");
                } else if (Category.INVESTIGATIONAL.equals(data.getCategories().get(i))) {
                    orderableItemDo.setCatInvestFlag("Y");
                } else if (Category.COMPOUND.equals(data.getCategories().get(i))) {
                    orderableItemDo.setCatCompoundFlag("Y");
                } else if (Category.SUPPLY.equals(data.getCategories().get(i))) {
                    orderableItemDo.setCatSupplyFlag("Y");
                }
            }
        }

        // initialize categories
        orderableItemDo.setSubcatHerbalFlag("N");
        orderableItemDo.setSubcatChemoFlag("N");
        orderableItemDo.setSubcatOtcFlag("N");
        orderableItemDo.setSubcatVeterFlag("N");

        // convert the sSub-categories
        if (data.getSubCategories() != null && data.getSubCategories().size() != 0) {
            for (int i = 0; i < data.getSubCategories().size(); i++) {

                //Set the sub-categories
                if (SubCategory.HERBAL.equals(data.getSubCategories().get(i))) {
                    orderableItemDo.setSubcatHerbalFlag("Y");
                } else if (SubCategory.CHEMOTHERAPY.equals(data.getSubCategories().get(i))) {
                    orderableItemDo.setSubcatChemoFlag("Y");
                } else if (SubCategory.OTC.equals(data.getSubCategories().get(i))) {
                    orderableItemDo.setSubcatOtcFlag("Y");
                } else if (SubCategory.VETERINARY.equals(data.getSubCategories().get(i))) {
                    orderableItemDo.setSubcatVeterFlag("Y");
                }

            }
        }

        return orderableItemDo;
    }

    /**
     * Converts AdministrationScheduleAssocVo to EplOiAdminSchedAssocDo
     * 
     * @param data AdministrationScheduleAssocVo
     * @param orderableItemId String
     * @return EplOiAdminSchedAssocDo
     */
    private EplOiAdminSchedAssocDo toEplOiAmdinScheduleAssocDo(AdministrationScheduleAssocVo data, String orderableItemId) {
        EplOiAdminSchedAssocDo schedule = new EplOiAdminSchedAssocDo();
        EplOiAdminSchedAssocDoKey key = new EplOiAdminSchedAssocDoKey();

        key.setEplIdAdminSchedFk(new Long(data.getAdministrationSchedule().getId()));
        key.setEplIdOiFk(new Long(orderableItemId));
        schedule.setKey(key);

        schedule.setDefaultScheduleYn(toYesOrNo(data.isDefaultSchedule()));

        return schedule;
    }

    /**
     * Converts OiRouteVo to EplOiMedRouteAssocDo
     * 
     * @param data of type OiRouteVo
     * @param id of type String
     * @return EplOiMedRouteAssocDo
     */
    private EplOiMedRouteAssocDo toEplOiMedRouteAssocDo(OiRouteVo data, String id) {
        EplOiMedRouteAssocDo route = new EplOiMedRouteAssocDo();
        EplOiMedRouteAssocDoKey key = new EplOiMedRouteAssocDoKey();

        if ((data != null) && (data.getLocalMedicationRoute() != null)) {
            key.setMedRouteIdFk(new Long(data.getLocalMedicationRoute().getId()));
            key.setEplIdOiFk(new Long(id));
            route.setKey(key);

            route.setDefaultYn(toYesOrNo(data.isDefaultRoute()));
        }

        return route;
    }

    /**
     * setCategories
     * @param item item
     * @param data data
     */
    private void setCategories(OrderableItemVo item, EplOrderableItemDo data) {

        List<Category> categories = new ArrayList<Category>();
        List<SubCategory> subCategories = new ArrayList<SubCategory>();

        //convert the categories
        if (data.getCatMedicFlag().equals("Y")) {
            categories.add(Category.MEDICATION);
        }

        if (data.getCatInvestFlag().equals("Y")) {
            categories.add(Category.INVESTIGATIONAL);
        }

        if (data.getCatCompoundFlag().equals("Y")) {
            categories.add(Category.COMPOUND);
        }

        if (data.getCatSupplyFlag().equals("Y")) {
            categories.add(Category.SUPPLY);
        }

        //convert the sub-categories
        if (data.getSubcatHerbalFlag().equals("Y")) {
            subCategories.add(SubCategory.HERBAL);
        }

        if (data.getSubcatChemoFlag().equals("Y")) {
            subCategories.add(SubCategory.CHEMOTHERAPY);
        }

        if (data.getSubcatOtcFlag().equals("Y")) {
            subCategories.add(SubCategory.OTC);
        }

        if (data.getSubcatVeterFlag().equals("Y")) {
            subCategories.add(SubCategory.VETERINARY);
        }

        item.setCategories(categories);
        item.setSubCategories(subCategories);

    }

    /**
     * Minimally copies data from the given {@link DataObject} into a {@link ValueObject}.
     * <p>
     * Populate the ID, OI Name, and Dosage Form
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     */
    @Override
    protected OrderableItemVo toMinimalValueObject(EplOrderableItemDo data) {

        return toValueObject(data);

//        OrderableItemVo item = new OrderableItemVo();
//        item.setId(String.valueOf(data.getEplId()));
//        item.setOiName(data.getOiName());
//        item.setVistaOiName(data.getVistaOiName());
//        setCategories(item, data);
//        
//        return item;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a OrderableItemVo.
     * <p>
     * The returned {@link ValueObject} likely only has enough data for the {@link ValueObject#toShortString()},
     * {@link ValueObject#getUniqueId()} methods to be called without getting nulls, and whatever additional data is required
     * for display and processing of a parent item.
     * <p>
     * Default implementation calls {@link #toMinimalValueObject(DataObject)}.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated OrderableItemVo
     */
    @Override
    protected OrderableItemVo toParentValueObject(EplOrderableItemDo data) {
        OrderableItemVo item = convertMinimal(data);

        item.setOrderableItemType(toOrderableItemType(data.getOiType()));
        item.setDosageForm(dosageFormConverter.convertMinimal(data.getEplDosageForm()));
        item.setLocalUse(toBoolean(data.getLocalUse()));

        return item;
    }

    /**
     * toSearchValueObject
     * 
     * @param data EplOrderableItemDo
     * @return OrderableItemVo
     */
    @Override
    protected OrderableItemVo toSearchValueObject(EplOrderableItemDo data) {
        OrderableItemVo item = new OrderableItemVo();
        item.setId(String.valueOf(data.getEplId()));

        item.setLocalUse(toBoolean(data.getLocalUse()));
        item.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));

        item.setDosageForm(dosageFormConverter.convertMinimal(data.getEplDosageForm()));

        List<Category> categories = new ArrayList<Category>();
        List<SubCategory> subCategories = new ArrayList<SubCategory>();

        //convert the categories
        if (data.getCatMedicFlag().equals("Y")) {
            categories.add(Category.valueOf("MEDICATION"));
        }

        if (data.getCatInvestFlag().equals("Y")) {
            categories.add(Category.valueOf("INVESTIGATIONAL"));
        }

        if (data.getCatCompoundFlag().equals("Y")) {
            categories.add(Category.valueOf("COMPOUND"));
        }

        if (data.getCatSupplyFlag().equals("Y")) {
            categories.add(Category.valueOf("SUPPLY"));
        }

        //convert the sub-categories
        if (data.getSubcatHerbalFlag().equals("Y")) {
            subCategories.add(SubCategory.valueOf("HERBAL"));
        }

        if (data.getSubcatChemoFlag().equals("Y")) {
            subCategories.add(SubCategory.valueOf("CHEMOTHERAPY"));
        }

        if (data.getSubcatOtcFlag().equals("Y")) {
            subCategories.add(SubCategory.valueOf("OTC"));
        }

        if (data.getSubcatVeterFlag().equals("Y")) {
            subCategories.add(SubCategory.valueOf("VETERINARY"));
        }

        item.setCategories(categories);
        item.setSubCategories(subCategories);

        item.setOiName(data.getOiName());
        item.setOrderableItemType(toOrderableItemType(data.getOiType()));
        item.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        item.setProductCount(data.getEplProducts().size());

        List<FieldKey> vaDataFields = new ArrayList<FieldKey>();

        vaDataFields.add(FieldKey.ORDERABLE_ITEM_SYNONYM);
        item.setVaDataFields(dataFieldsConverter.convertFirst(data.getEplVadfOwners(), vaDataFields));

        return item;
    }

    /**
     * Converts RouteDo to RouteVo
     * 
     * @param data domain route needed to be converted
     * @return RouteVo
     */
    private OiRouteVo toOiRoute(EplOiMedRouteAssocDo data) {
        OiRouteVo oiRoute = new OiRouteVo();

        oiRoute.setLocalMedicationRoute(localMedicationRouteConverter.convertMinimal(data.getEplLocalMedRoute()));
        oiRoute.setDefaultRoute(toBoolean(data.getDefaultYn()));

        return oiRoute;
    }

    /**
     * Convert the String "L" or "N" to {@link OrderableItemType}.
     * 
     * @param oiType {@link OrderableItemType}
     * @return {@link OrderableItemType}
     */
    private OrderableItemType toOrderableItemType(String oiType) {
        if ("L".equals(oiType)) {
            return OrderableItemType.LOCAL;
        } else {
            return OrderableItemType.NATIONAL;
        }
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a OrderableItemVo.
     * <p>
     * The returned {@link ValueObject} likely only has enough data for the {@link ValueObject#toShortString()} and
     * {@link ValueObject#getUniqueId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the OrderableItemVo in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated OrderableItemVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected OrderableItemVo toValueObject(EplOrderableItemDo data) {
        OrderableItemVo item = new OrderableItemVo();
        item.setId(String.valueOf(data.getEplId()));

        if (data.getEplDosageForm() != null) {
            item.setDosageForm(dosageFormConverter.convertMinimal(data.getEplDosageForm()));
        }

        if (data.getEplStandardMedRoute() != null) {
            item.setStandardMedRoute(standardMedRouteConverter.convertMinimal(data.getEplStandardMedRoute()));
        }

        item.setPreviouslyMarkedForLocalUse(toBoolean(data.getPrevMarkedForLocalUseYn()));

// LOCAL ONLY DATA
//       if (data.getEplOiScheduleType() == null) {
//            OiScheduleTypeVo oiScheduleTypeVo = new OiScheduleTypeVo();
//            oiScheduleTypeVo.setId("5");
//            oiScheduleTypeVo.setCode("C");
//            oiScheduleTypeVo.setType("Continuous");
//            item.setOiScheduleType(oiScheduleTypeVo);
//        } else {

        item.setOiScheduleType(oiScheduleTypeConverter.convert(data.getEplOiScheduleType()));

        //}

        item.setNonVaMed(toBoolean(data.getNonVaMed()));
        item.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        item.setOiName(data.getOiName());
        item.setVistaOiName(data.getVistaOiName());
        item.setLocalUse(toBoolean(data.getLocalUse()));
        item.setRevisionNumber(data.getRevisionNumber().longValue());
        item.setRejectionReasonText(data.getRejectReasonText());
        item.setCreatedBy(data.getCreatedBy());
        item.setCreatedDate(data.getCreatedDtm());
        item.setModifiedBy(data.getLastModifiedBy());
        item.setModifiedDate(data.getLastModifiedDtm());
        item.setInactivationDate(data.getInactivationDate());
        item.setHighAlert(data.getHighAlert());
        item.setSpecialInstructions(data.getSpecialInstructions());

// LOCAL ONLY DATA       
//        if (data.getEplLabs() != null) {
//            for (EplLabDo labDo : data.getEplLabs()) {
//                if (labDo.getLabDisplayAdministration() != null) {
//                    LabDisplayAdministrationVo lab = new LabDisplayAdministrationVo();
//                    lab.setValue(labDo.getLabDisplayAdministration());
//                    item.getLabDisplayAdministration().add(lab);
//                }
//
//                if (labDo.getLabDisplayOrderEntry() != null) {
//                    LabDisplayOrderEntryVo lab = new LabDisplayOrderEntryVo();
//                    lab.setValue(labDo.getLabDisplayOrderEntry());
//                    item.getLabDisplayOrderEntry().add(lab);
//                }
//
//                if (labDo.getLabDisplayFinishAnOrder() != null) {
//                    LabDisplayFinishingAnOrderVo lab = new LabDisplayFinishingAnOrderVo();
//                    lab.setValue(labDo.getLabDisplayFinishAnOrder());
//                    item.getLabDisplayFinishingAnOrder().add(lab);
//                }
//            }
//        }

        return toValueObject2(item, data);
    }

    /**
     * toValueObject2
     * @param item item
     * @param data data
     * @return OrderableItemVo
     */
    protected OrderableItemVo toValueObject2(OrderableItemVo item, EplOrderableItemDo data) {

        if (data.getEplVitals() != null) {
            for (EplVitalDo vitalDo : data.getEplVitals()) {
                if (vitalDo.getVitalDisplayAdministration() != null) {
                    VitalsDisplayAdministrationVo vitals = new VitalsDisplayAdministrationVo();
                    vitals.setValue(vitalDo.getVitalDisplayAdministration());
                    item.getVitalsDisplayAdministration().add(vitals);
                }

                if (vitalDo.getVitalDisplayOrderEntry() != null) {
                    VitalsDisplayOrderEntryVo vitals = new VitalsDisplayOrderEntryVo();
                    vitals.setValue(vitalDo.getVitalDisplayOrderEntry());
                    item.getVitalsDisplayOrderEntry().add(vitals);
                }

                if (vitalDo.getVitalDisplayFinishAnOrder() != null) {
                    VitalsDisplayFinishAnOrderVo vitals = new VitalsDisplayFinishAnOrderVo();
                    vitals.setValue(vitalDo.getVitalDisplayFinishAnOrder());
                    item.getVitalsDisplayFinishAnOrder().add(vitals);
                }
            }
        }

        item.setOrderableItemType(toOrderableItemType(data.getOiType()));

        if (data.getEplOiAdminSchedAssocs() != null && data.getEplOiAdminSchedAssocs().size() != 0) {
            Collection<AdministrationScheduleAssocVo> adminSchedules = new ArrayList<AdministrationScheduleAssocVo>();

            for (EplOiAdminSchedAssocDo drug : data.getEplOiAdminSchedAssocs()) {

                if (drug.getEplAdminSchedule() != null) {
                    AdministrationScheduleVo admin = adminScheduleConverter.convertMinimal(drug.getEplAdminSchedule());
                    AdministrationScheduleAssocVo assoc = new AdministrationScheduleAssocVo();
                    assoc.setDefaultSchedule(drug.getDefaultScheduleYn().equals("Y"));
                    assoc.setAdministrationSchedule(admin);

                    adminSchedules.add(assoc);
                }
            }

            item.setAdminSchedules(adminSchedules);
        }

        item.setOrderableItemParent(convertParent(data.getEplOrderableItem()));
        item.setProductCount(data.getEplProducts().size());

        if (data.getRequestRejectReason() != null) {
            item.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));
        }

        item.setNationalFormularyIndicator(toBoolean(data.getNationalFormularyIndicator()));

        if (data.getEplOiMedRouteAssocs() != null && data.getEplOiMedRouteAssocs().size() > 0) {
            Collection<OiRouteVo> routes = new ArrayList<OiRouteVo>();

            for (EplOiMedRouteAssocDo route : data.getEplOiMedRouteAssocs()) {
                OiRouteVo routeVo = toOiRoute(route);
                routes.add(routeVo);
            }

            item.setOiRoute(routes);
        }

        if (data.getEplOiDrugTextLAssocs() != null && data.getEplOiDrugTextLAssocs().size() > 0) {
            List<DrugTextVo> drugs = new ArrayList<DrugTextVo>();

            for (EplOiDrugTextLAssocDo text : data.getEplOiDrugTextLAssocs()) {
                DrugTextVo drug = drugTextConverter.convert(text.getEplDrugText());
                drugs.add(drug);
            }

            item.setLocalDrugTexts(drugs);
        }

        if (data.getEplOiDrugTextNAssocs() != null && data.getEplOiDrugTextNAssocs().size() > 0) {
            List<DrugTextVo> drugs = new ArrayList<DrugTextVo>();

            for (EplOiDrugTextNAssocDo text : data.getEplOiDrugTextNAssocs()) {
                DrugTextVo drug = drugTextConverter.convert(text.getEplDrugText());
                drugs.add(drug);
            }

            item.setNationalDrugTexts(drugs);
        }

        item.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));

        // item.setDataFields(dataFieldsDomainCapability.retrieve(data.getEplId(), EntityType.ORDERABLE_ITEM));
        item.setVaDataFields(dataFieldsConverter.convertFirst(data.getEplVadfOwners(), EntityType.ORDERABLE_ITEM));

        setCategories(item, data);

        return item;
    }

    /**
     * setDosageFormConverter
     * @param dosageFormConverter dosageFormConverter property
     */
    public void setDosageFormConverter(DosageFormConverter dosageFormConverter) {
        this.dosageFormConverter = dosageFormConverter;
    }

    /**
     * setStandardMedRouteConverter
     * @param standardMedRouteConverter standardMedRouteConverter property
     */
    public void setStandardMedRouteConverter(StandardMedRouteConverter standardMedRouteConverter) {
        this.standardMedRouteConverter = standardMedRouteConverter;
    }

    /**
     * setAdminScheduleConverter
     * @param adminScheduleConverter adminScheduleConverter property
     */
    public void setAdminScheduleConverter(AdminScheduleConverter adminScheduleConverter) {
        this.adminScheduleConverter = adminScheduleConverter;
    }

    /**
     * setLocalMedicationRouteConverter
     * @param localMedicationRouteConverter localMedicationRouteConverter property
     */
    public void setLocalMedicationRouteConverter(LocalMedicationRouteConverter localMedicationRouteConverter) {
        this.localMedicationRouteConverter = localMedicationRouteConverter;
    }

    /**
     * setOiScheduleTypeConverter
     * @param oiScheduleTypeConverter oiScheduleTypeConverter property
     */
    public void setOiScheduleTypeConverter(OiScheduleTypeConverter oiScheduleTypeConverter) {
        this.oiScheduleTypeConverter = oiScheduleTypeConverter;
    }
}
