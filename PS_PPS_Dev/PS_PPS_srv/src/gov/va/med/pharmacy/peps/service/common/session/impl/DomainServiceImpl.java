/**
 * Source file created in 2007 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleTypeVo;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.ColorVo;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.HospitalLocationSelectionVo;
import gov.va.med.pharmacy.peps.common.vo.IfcapItemNumberVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.LabTestMonitorVo;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.OiScheduleTypeVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.common.vo.OutpatientSiteVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.PackageUsageVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.ShapeVo;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.common.vo.SpecimenTypeVo;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.WardGroupForAtcVo;
import gov.va.med.pharmacy.peps.common.vo.WardSelectionVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.domain.common.capability.AdministrationScheduleDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.AdministrationScheduleTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.CsFedScheduleDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DispenseUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DosageFormDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DoseUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugClassDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugClassTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugTextDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.GenericNameDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.IngredientDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.IntendedUseDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.LocalMedicationRouteDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdfSynchQueueDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OiScheduleTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OtcRxDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageUseageDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.RxConsultDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.SpecialHandlingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.StandardMedRouteDomainCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaAuthoritativeDomainCapability;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.VistaDomainName;
import gov.va.med.pharmacy.peps.service.common.capability.DataFieldsCapability;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;




/**
 * Handles the retrieval of the semi-static domain objects.
 */
public class DomainServiceImpl implements DomainService {

    private DataFieldsCapability dataFieldsCapability;
    private DosageFormDomainCapability dosageFormDomainCapability;
    private DrugClassDomainCapability drugClassDomainCapability;
    private DrugClassTypeDomainCapability drugClassTypeDomainCapability;
    private GenericNameDomainCapability genericNameDomainCapability;
    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;
    private IngredientDomainCapability ingredientDomainCapability;
    private ProductDomainCapability productDomainCapability;
    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;
    private OtcRxDomainCapability otcRxDomainCapability;
    private DrugUnitDomainCapability drugUnitDomainCapability;
    private CsFedScheduleDomainCapability csFedScheduleDomainCapability;
    private DispenseUnitDomainCapability dispenseUnitDomainCapability;
    private DoseUnitDomainCapability doseUnitDomainCapability;
    private IntendedUseDomainCapability intendedUseDomainCapability;
    private OrderUnitDomainCapability orderUnitDomainCapability;
    private SpecialHandlingDomainCapability specialHandlingDomainCapability;
    private PackageUseageDomainCapability packageUseageDomainCapability;
    private VistaAuthoritativeDomainCapability vistaAuthoritativeDomainCapability;
    private AdministrationScheduleDomainCapability administrationScheduleDomainCapability;
    private LocalMedicationRouteDomainCapability localMedicationRouteDomainCapability;
    private AdministrationScheduleTypeDomainCapability administrationScheduleTypeDomainCapability;
    private OiScheduleTypeDomainCapability oiScheduleTypeDomainCapability;
    private RxConsultDomainCapability rxConsultDomainCapability;
    private DrugTextDomainCapability drugTextDomainCapability;
    private DrugReferenceCapability drugReferenceCapability;
    private EnvironmentUtility environmentUtility;
    private NdfSynchQueueDomainCapability ndfSynchQueueDomainCapability;

    /**
     * setAdministrationScheduleDomainCapability.
     * @param administrationScheduleDomainCapability administrationScheduleDomainCapability property
     */
    public void setAdministrationScheduleDomainCapability(AdministrationScheduleDomainCapability 
        administrationScheduleDomainCapability) {
        this.administrationScheduleDomainCapability = administrationScheduleDomainCapability;
    }

    /**
     * getVistaLinkCapability
     * 
     * @return capability
     */
    public VistaAuthoritativeDomainCapability getVistaLinkCapability() {
        return vistaAuthoritativeDomainCapability;
    }

    /**
     * setVistaAuthoritativeDomainCapability
     * 
     * @param capability
     *            capability
     */
    public void setVistaAuthoritativeDomainCapability(VistaAuthoritativeDomainCapability capability) {
        this.vistaAuthoritativeDomainCapability = capability;
    }

    /**
     * Retrieve all possible Administration Schedules
     * 
     * @return List<AdministrationScheduleVo> list of admin schedule
     */
    @Override
    public List<AdministrationScheduleVo> getAdminSchedules() {
        return administrationScheduleDomainCapability.retrieve();
    }

    /**
     * Retrieve all possible Administration Schedules
     * 
     * @return List<AdministrationScheduleTypeVo> list of admin schedule
     */
    @Override
    public List<AdministrationScheduleTypeVo> getAdminScheduleTypes() {
        return administrationScheduleTypeDomainCapability.retrieveDomain();
    }

    /**
     * Get all Colors for selection
     * 
     * @return List<Color>
     */
    @Override
    public List<ColorVo> getColors() {
        return drugReferenceCapability.getColors();
    }

    /**
     * Get all VA DataFields for selection
     * 
     * @return DataFields
     */
    @Override
    public DataFields getDataFields() {
        return dataFieldsCapability.retrieveDomain();

    }

    /**
     * Get all DosageForms for selection
     * 
     * @return List<DosageForm>
     */
    @Override
    public List<DosageFormVo> getDosageForms() {
        return dosageFormDomainCapability.retrieve();
    }

    /**
     * Get all DrugClasses for selection
     * 
     * @return List<DrugClass>
     */
    @Override
    public List<DrugClassVo> getDrugClasses() {
        return drugClassDomainCapability.retrieve();
    }

    /**
     * Get all DrugClassTypes for selection
     * 
     * @return List<DrugClassificationTypeVo>
     */
    @Override
    public List<DrugClassificationTypeVo> getDrugClassTypes() {
        return drugClassTypeDomainCapability.retrieveDomain();
    }

    /**
     * Get all Ingredient Names for selection
     * 
     * @return List<IngredientNameVo>
     */
    @Override
    public List<IngredientVo> getIngredientName() {
        return ingredientDomainCapability.retrieve();
    }

    /**
     * Get all GenericNames for selection
     * 
     * @return List<GenericNameVo>
     */
    @Override
    public List<GenericNameVo> getGenericNames() {
        return genericNameDomainCapability.retrieve();
    }

    /**
     * Get all Manufacturers for selection
     * 
     * @return List<Manufacturer>
     */
    @Override
    public List<ManufacturerVo> getManufacturers() {
        return manufacturerDomainCapability.retrieve();
    }

    /**
     * Get all PackageTypes for selection
     * 
     * @return List<PackageType>
     */
    @Override
    public List<PackageTypeVo> getPackageTypes() {
        return packageTypeDomainCapability.retrieve();
    }

    /**
     * Get all Routes for selection
     * 
     * @return List<Route>
     */
    @Override
    public List<StandardMedRouteVo> getRoutes() {
        return standardMedRouteDomainCapability.retrieve();
    }

    /**
     * Get all Routes for selection
     * 
     * @return List<Route>
     */
    @Override
    public List<LocalMedicationRouteVo> getLocalRoutes() {
        return localMedicationRouteDomainCapability.retrieve();
    }

    /**
     * Get all Shapes for selection
     * 
     * @return List<Shape>
     */
    @Override
    public List<ShapeVo> getShapes() {
        return drugReferenceCapability.getShapes();
    }

    /**
     * Get all Units for selection
     * 
     * @return List<Unit>
     */
    @Override
    public List<DrugUnitVo> getUnits() {
        return drugUnitDomainCapability.retrieve();
    }

    /**
     * Get all CsFedSchedules for selection
     * 
     * @return List<CsFedScheduleVo>
     */
    @Override
    public List<CsFedScheduleVo> getCsFedSchedule() {
        return csFedScheduleDomainCapability.retrieveDomain();
    }

    /**
     * Get all VaDispenseUnits for selection
     * 
     * @return List<VaDispenseUnitVo>
     * 
     * 
     */
    @Override
    public List<DispenseUnitVo> getDispenseUnit() {
        return dispenseUnitDomainCapability.retrieve();
    }

    /**
     * Get all Dose Units for selection
     * 
     * @return List<DoseUnitVo>
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#getDoseUnit()
     */
    @Override
    public List<DoseUnitVo> getDoseUnit() {
        return doseUnitDomainCapability.retrieve();
    }

    /**
     * Get all OtcRx statuses for selection
     * 
     * @return List<OtcRxVo>
     */
    @Override
    public List<OtcRxVo> getOtcRx() {
        return otcRxDomainCapability.retrieveDomain();
    }

    /**
     * Get all ward groups for selection
     * 
     * @return List<WardGroupForAtcVo>
     */
    public List<ValueObject> getWardGroupForAtc() {
        List<ValueObject> lstWardGroupForAtc = new ArrayList<ValueObject>();

        for (int i = 1; i <= PPSConstants.I10; i++) {
            WardGroupForAtcVo wardGroup = new WardGroupForAtcVo();
            wardGroup.setValue("Ward Group " + i);
            lstWardGroupForAtc.add(wardGroup);
        }

        return lstWardGroupForAtc;
    }

    /**
     * Get all package usages values
     * 
     * @return List<PackageUseageVo>
     */
    @Override
    public List<PackageUsageVo> getPackageUsages() {
        return packageUseageDomainCapability.retrieveDomain();
    }

    /**
     * Get all corresponding inpatient drugs for selection. This list is not
     * static and should not be cached!
     * 
     * @return List<ProductVo>
     */
    @Override
    public List<ProductVo> getCorrespondingInpatientDrugs() {
        return productDomainCapability.getAllUnitDoseAndIvProducts();
    }

    /**
     * Get all corresponding outpatient drugs for selection. This list is not
     * static and should not be cached!
     * 
     * @return List<String>
     */
    @Override
    public List<ProductVo> getCorrespondingOutpatientDrugs() {
        return productDomainCapability.getAllProducts();
    }

    /**
     * Get all formulary alternatives for selection. This list is not static and
     * should not be cached!
     * 
     * @return List<String>
     */
    @Override
    public List<ProductVo> getFormularyAlternatives() {
        return productDomainCapability.getAllFormularyProducts();
    }

    /**
     * getIntendedUse
     * 
     * @return List<IntendedUseVo>
     * 
     */
    @Override
    public List<IntendedUseVo> getIntendedUse() {
        return intendedUseDomainCapability.retrieveDomain();
    }

    /**
     * getOrderUnits
     * 
     * @return List<OrderUnitVo>
     */
    @Override
    public List<OrderUnitVo> getOrderUnits() {
        return orderUnitDomainCapability.retrieve();
    }

    /**
     * getSpecialHandlings
     * 
     * @return List<SpecialHandlingVo>
     */
    @Override
    public List<SpecialHandlingVo> getSpecialHandlings() {
        return specialHandlingDomainCapability.retrieve();
    }

    /**
     * getOiScheduleTypes
     * 
     * @return List<OiScheduleTypeVo>
     */
    @Override
    public List<OiScheduleTypeVo> getOiScheduleTypes() {
        return oiScheduleTypeDomainCapability.retrieveDomain();
    }

    /**
     * Get all active, LOCAL and COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    @Override
    public List<RxConsultVo> getLocalRxConstult() {
        return rxConsultDomainCapability.getLocalRxConsult();
    }

    /**
     * Get all active, NATIONAL and COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    @Override
    public List<RxConsultVo> getNationalRxConstult() {
        return rxConsultDomainCapability.getNationalRxConsult();
    }

    /**
     * Get all active, LOCAL and national drug text
     * 
     * @return List<RxConsultVo>
     */
    @Override
    public List<DrugTextVo> getLocalDrugText() {
        return drugTextDomainCapability.getLocalDrugText();
    }

    /**
     * Get all active, NATIONAL and COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    @Override
    public List<DrugTextVo> getNationalDrugText() {
        return drugTextDomainCapability.getNationalDrugText();
    }

    /**
     * Get all active, COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    @Override
    public List<RxConsultVo> getCotsRxConsult() {
        return rxConsultDomainCapability.getCotsRxConsult();
    }

    /**
     * setDataFieldsCapability
     * @param dataFieldsCapability
     *            dataFieldsCapability property
     */
    public void setDataFieldsCapability(DataFieldsCapability dataFieldsCapability) {
        this.dataFieldsCapability = dataFieldsCapability;
    }

    /**
     * setDosageFormDomainCapability
     * @param dosageFormCapability
     *            dosageFormCapability property
     */
    public void setDosageFormDomainCapability(DosageFormDomainCapability dosageFormCapability) {
        this.dosageFormDomainCapability = dosageFormCapability;
    }

    /**
     * setDrugClassDomainCapability
     * @param drugClassCapability
     *            drugClassCapability property
     */
    public void setDrugClassDomainCapability(DrugClassDomainCapability drugClassCapability) {
        this.drugClassDomainCapability = drugClassCapability;
    }

    /**
     * setGenericNameDomainCapability
     * @param genericNameCapability
     *            genericNameCapability property
     */
    public void setGenericNameDomainCapability(GenericNameDomainCapability genericNameCapability) {
        this.genericNameDomainCapability = genericNameCapability;
    }


    /**
     * setManufacturerDomainCapability
     * @param manufacturerCapability
     *            manufacturerCapability property
     */
    public void setManufacturerDomainCapability(ManufacturerDomainCapability manufacturerCapability) {
        this.manufacturerDomainCapability = manufacturerCapability;
    }

    /**
     * setPackageTypeDomainCapability
     * @param packageTypeCapability
     *            packageTypeCapability property
     */
    public void setPackageTypeDomainCapability(PackageTypeDomainCapability packageTypeCapability) {
        this.packageTypeDomainCapability = packageTypeCapability;
    }

    /**
     * setDrugUnitDomainCapability
     * @param drugUnitDomainCapability
     *            unitCapability property
     */
    public void setDrugUnitDomainCapability(DrugUnitDomainCapability drugUnitDomainCapability) {
        this.drugUnitDomainCapability = drugUnitDomainCapability;
    }

    /**
     * setCsFedScheduleDomainCapability
     * 
     * @param csFedScheduleDomainCapability
     *            property
     */
    public void setCsFedScheduleDomainCapability(CsFedScheduleDomainCapability csFedScheduleDomainCapability) {
        this.csFedScheduleDomainCapability = csFedScheduleDomainCapability;
    }

    /**
     * setDispenseUnitDomainCapability
     * 
     * @param vaDispenseUnitDomainCapability
     *            property
     */
    public void setDispenseUnitDomainCapability(DispenseUnitDomainCapability vaDispenseUnitDomainCapability) {
        this.dispenseUnitDomainCapability = vaDispenseUnitDomainCapability;
    }

    /**
     * setOtcRxDomainCapability
     * 
     * @param otcRxCapability
     *            property
     */
    public void setOtcRxDomainCapability(OtcRxDomainCapability otcRxCapability) {
        this.otcRxDomainCapability = otcRxCapability;
    }

    /**
     * setProductDomainCapability
     * @param productDomainCapability
     *            productDomainCapability property
     */
    public void setProductDomainCapability(ProductDomainCapability productDomainCapability) {
        this.productDomainCapability = productDomainCapability;
    }

    /**
     * setIntendedUseDomainCapability
     * 
     * @param intendedUseDomainCapability
     *            property
     */
    public void setIntendedUseDomainCapability(IntendedUseDomainCapability intendedUseDomainCapability) {
        this.intendedUseDomainCapability = intendedUseDomainCapability;
    }

    /**
     * setOrderUnitDomainCapability
     * 
     * @param orderUnitDomainCapability
     *            property
     */
    public void setOrderUnitDomainCapability(OrderUnitDomainCapability orderUnitDomainCapability) {
        this.orderUnitDomainCapability = orderUnitDomainCapability;
    }

    /**
     * setIngredientDomainCapability
     * @param ingredientDomainCapability
     *            ingredientDomainCapability property
     */
    public void setIngredientDomainCapability(IngredientDomainCapability ingredientDomainCapability) {
        this.ingredientDomainCapability = ingredientDomainCapability;
    }

    /**
     * setStandardMedRouteDomainCapability
     * @param standardMedRouteDomainCapability
     *            standardMedRouteDomainCapability property
     */
    public void setStandardMedRouteDomainCapability(StandardMedRouteDomainCapability standardMedRouteDomainCapability) {
        this.standardMedRouteDomainCapability = standardMedRouteDomainCapability;
    }

    /**
     * setDrugClassTypeDomainCapability
     * @param drugClassTypeDomainCapability
     *            drugClassTypeDomainCapability property
     */
    public void setDrugClassTypeDomainCapability(DrugClassTypeDomainCapability drugClassTypeDomainCapability) {
        this.drugClassTypeDomainCapability = drugClassTypeDomainCapability;
    }

    /**
     * getDoseUnitDomainCapability
     * @return doseUnitDomainCapability property
     */
    public DoseUnitDomainCapability getDoseUnitDomainCapability() {
        return doseUnitDomainCapability;
    }

    /**
     * setDoseUnitDomainCapability
     * @param doseUnitDomainCapability
     *            doseUnitDomainCapability property
     */
    public void setDoseUnitDomainCapability(DoseUnitDomainCapability doseUnitDomainCapability) {
        this.doseUnitDomainCapability = doseUnitDomainCapability;
    }

    /**
     * setPackageUseageDomainCapability
     * @param packageUseageDomainCapability
     *            packageUseageDomainCapability property
     */
    public void setPackageUseageDomainCapability(PackageUseageDomainCapability packageUseageDomainCapability) {
        this.packageUseageDomainCapability = packageUseageDomainCapability;
    }

    /**
     * getVistaDomain
     * 
     * @param fieldKey
     *            FieldKey representing the desired data field.
     * @return list of ValueObject
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService
     *      #getVistaDomain(gov.va.med.pharmacy.peps.common.vo.FieldKey)
     */
    @Override
    public List<?> getVistaDomain(FieldKey fieldKey) {
        ArrayList results = new ArrayList();

        if (environmentUtility.isNational()) {
            return results;
        }

        if (fieldKey == FieldKey.WARD_SELECTION) {
            List<?> list = vistaAuthoritativeDomainCapability.retrieveVistADomains(new VistaDomainName[] { 
                VistaDomainName.WARDS }, null).get(VistaDomainName.WARDS);

            for (int i = 0; i < list.size(); i++) {
                WardSelectionVo wardSelection = new WardSelectionVo();
                wardSelection.setValue((String) list.get(i));

                results.add(wardSelection);
            }
        } else if (fieldKey == FieldKey.WARD_GROUP_FOR_ATC) {
            List<?> list =
                    vistaAuthoritativeDomainCapability.retrieveVistADomains(new VistaDomainName[] { VistaDomainName.WARDS },
                                                                            null).get(VistaDomainName.WARDS);

            for (int i = 0; i < list.size(); i++) {
                WardGroupForAtcVo vo = new WardGroupForAtcVo();

                vo.setValue((String) list.get(i));
                results.add(vo);
            }
        } else if (fieldKey == FieldKey.HOSPITAL_LOCATION_SELECTION) {
            List<?> list =
                    vistaAuthoritativeDomainCapability
                            .retrieveVistADomains(new VistaDomainName[] { VistaDomainName.HOSPITAL_LOCATIONS }, null)
                            .get(VistaDomainName.HOSPITAL_LOCATIONS);

            for (int i = 0; i < list.size(); i++) {
                HospitalLocationSelectionVo vo = new HospitalLocationSelectionVo();

                vo.setValue((String) list.get(i));
                results.add(vo);
            }
        } else if (fieldKey == FieldKey.IFCAP_ITEM_NUMBER) {
            List<?> list =
                    vistaAuthoritativeDomainCapability
                            .retrieveVistADomains(new VistaDomainName[] { VistaDomainName.IFCAP_ITEM_NUMBERS }, null)
                            .get(VistaDomainName.IFCAP_ITEM_NUMBERS);

            for (int i = 0; i < list.size(); i++) {
                IfcapItemNumberVo numVo = new IfcapItemNumberVo();
                numVo.setValue(((BigInteger) list.get(i)).toString());
                results.add(numVo);
            }
        } else if (fieldKey == FieldKey.SPECIMEN_TYPE) {
            List<?> list =
                    vistaAuthoritativeDomainCapability
                            .retrieveVistADomains(new VistaDomainName[] { VistaDomainName.SPECIMEN_TYPES }, null)
                            .get(VistaDomainName.SPECIMEN_TYPES);

            for (int i = 0; i < list.size(); i++) {
                SpecimenTypeVo specVo = new SpecimenTypeVo();
                specVo.setValue((String) list.get(i));
                results.add(specVo);
            }
        } else if (fieldKey == FieldKey.OUTPATIENT_SITE) {
            List<?> list =
                    vistaAuthoritativeDomainCapability
                            .retrieveVistADomains(new VistaDomainName[] { VistaDomainName.OUTPATIENT_SITES }, null)
                            .get(VistaDomainName.OUTPATIENT_SITES);

            for (int i = 0; i < list.size(); i++) {
                OutpatientSiteVo outpatientSite = new OutpatientSiteVo();
                outpatientSite.setValue((String) list.get(i));
                results.add(outpatientSite);
            }
        } else if (fieldKey == FieldKey.LAB_TEST_MONITOR) {

            // List<?> list = vistaAuthoritativeDomainCapability.retrieveVistADomains(
            // new VistaDomainName[] {VistaDomainName.SPECIMEN_TYPES}, null).get(VistaDomainName.SPECIMEN_TYPES);

            for (int i = 0; i < PPSConstants.I10; i++) {
                LabTestMonitorVo labTestMonitor = new LabTestMonitorVo();
                labTestMonitor.setValue("Lab Test Monitor " + i);
                results.add(labTestMonitor);
            }
        } else {
            return null;
        }

        return results;
    }

    /**
     * getLocalMedicationRouteDomainCapability
     * @return localMedicationRouteDomainCapability property
     */
    public LocalMedicationRouteDomainCapability getLocalMedicationRouteDomainCapability() {
        return localMedicationRouteDomainCapability;
    }

    /**
     * setDrugReferenceCapability
     * @param drugReferenceCapability drugReferenceCapability
     */
    public void setDrugReferenceCapability(DrugReferenceCapability drugReferenceCapability) {
        this.drugReferenceCapability = drugReferenceCapability;
    }

    /**
     * setLocalMedicationRouteDomainCapability
     * @param localMedicationRouteDomainCapability localMedicationRouteDomainCapability property
     */
    public void setLocalMedicationRouteDomainCapability(LocalMedicationRouteDomainCapability 
        localMedicationRouteDomainCapability) {
        this.localMedicationRouteDomainCapability = localMedicationRouteDomainCapability;
    }

    /**
     * setAdministrationScheduleTypeDomainCapability
     * @param administrationScheduleTypeDomainCapability
     *            administrationScheduleTypeDomainCapability property
     */
    public void setAdministrationScheduleTypeDomainCapability(AdministrationScheduleTypeDomainCapability 
        administrationScheduleTypeDomainCapability) {
        this.administrationScheduleTypeDomainCapability = administrationScheduleTypeDomainCapability;
    }

    /**
     * setOiScheduleTypeDomainCapability
     * 
     * @param oiScheduleTypeDomainCapability
     *            property
     */
    public void setOiScheduleTypeDomainCapability(OiScheduleTypeDomainCapability oiScheduleTypeDomainCapability) {
        this.oiScheduleTypeDomainCapability = oiScheduleTypeDomainCapability;
    }

    /**
     * setEnvironmentUtility
     * @param environmentUtility environmentUtility property
     */
    public final void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

    /**
     * getRxConsultDomainCapability
     * @return rxConsultDomainCapability property
     */
    public RxConsultDomainCapability getRxConsultDomainCapability() {
        return rxConsultDomainCapability;
    }

    /**
     * setRxConsultDomainCapability
     * @param rxConsultDomainCapability
     *            rxConsultDomainCapability property
     */
    public void setRxConsultDomainCapability(RxConsultDomainCapability rxConsultDomainCapability) {
        this.rxConsultDomainCapability = rxConsultDomainCapability;
    }

    /**
     * setSpecialHandlingDomainCapability
     * @param specialHandlingDomainCapability
     *            specialHandlingDomainCapability property
     */
    public void setSpecialHandlingDomainCapability(SpecialHandlingDomainCapability specialHandlingDomainCapability) {
        this.specialHandlingDomainCapability = specialHandlingDomainCapability;
    }

    /**
     * setDrugTextDomainCapability
     * @param drugTextDomainCapability
     *            drugTextConsultDomainCapability property
     */
    public void setDrugTextDomainCapability(DrugTextDomainCapability drugTextDomainCapability) {
        this.drugTextDomainCapability = drugTextDomainCapability;
    }

    /**
     * getNdfSynchQueueDomainCapability
     * @return the ndfSynchQueueDomainCapability
     */
    public NdfSynchQueueDomainCapability getNdfSynchQueueDomainCapability() {
        return ndfSynchQueueDomainCapability;
    }

    /**
     * setNdfSynchQueueDomainCapability
     * @param ndfSynchQueueDomainCapability the ndfSynchQueueDomainCapability to set
     */
    public void setNdfSynchQueueDomainCapability(NdfSynchQueueDomainCapability ndfSynchQueueDomainCapability) {
        this.ndfSynchQueueDomainCapability = ndfSynchQueueDomainCapability;
    }

    /**
     * retrieveAdminSchedule
     * @param id Id
     * @return AdministrationScheduleVo
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveAdminSchedule(java.lang.String)
     */
    @Override
    public AdministrationScheduleVo retrieveAdminSchedule(String id) throws ItemNotFoundException {
        return administrationScheduleDomainCapability.retrieve(id);
    }

    /**
     * retrieveAdminScheduleType
     * @param id Id
     * @return AdministrationScheduleTypeVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveAdminScheduleType(java.lang.String)
     */
    @Override
    public AdministrationScheduleTypeVo retrieveAdminScheduleType(String id) {
        return null;
    }

    /**
     * retrieveColor
     * @param id Id
     * @return colorVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveColor(java.lang.String)
     */
    @Override
    public ColorVo retrieveColor(String id) {
        for (ColorVo color : getColors()) {
            if (color.getId().equals(id)) {
                return color;
            }
        }

        return null;
    }

    /**
     * retrieveDataField
     * @param id Id
     * @return DataFields
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDataField(java.lang.String)
     */
    @Override
    public DataFields retrieveDataField(String id) {

        return null;
    }

    /**
     * retrieveDosageForm
     * @param id Id
     * @return DosageFormVo
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDosageForm(java.lang.String)
     */
    @Override
    public DosageFormVo retrieveDosageForm(String id) throws ItemNotFoundException {

        return dosageFormDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveDrugClass
     * @param id Id
     * @return DrugClassVo
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDrugClasse(java.lang.String)
     */
    @Override
    public DrugClassVo retrieveDrugClass(String id) throws ItemNotFoundException {

        return drugClassDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveDrugClassificationType
     * @param id Id
     * @return DrugClassificationTypeVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDrugClassificationType(java.lang.String)
     */
    @Override
    public DrugClassificationTypeVo retrieveDrugClassificationType(String id) {

        return drugClassTypeDomainCapability.retrieve(id);
    }

    /**
     * retrieveIngredientName
     * 
     * @param id Id
     * @return IngredientVo
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveIngredientName(java.lang.String)
     */
    @Override
    public IngredientVo retrieveIngredientName(String id) throws ItemNotFoundException {

        return ingredientDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveGenericName
     * @param id Id
     * @return GenericNameVo
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveGenericName(java.lang.String)
     */
    @Override
    public GenericNameVo retrieveGenericName(String id) throws ItemNotFoundException {

        return genericNameDomainCapability.retrieveMinimal(id);
    }


    /**
     * retrieveManufacturer
     * @param id Id
     * @return  ManufacturerVo
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveManufacturer(java.lang.String)
     */
    @Override
    public ManufacturerVo retrieveManufacturer(String id) throws ItemNotFoundException {

        return manufacturerDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrievePackageType
     * @param id Id
     * @return PackageTypeVo
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrievePackageType(java.lang.String)
     */
    @Override
    public PackageTypeVo retrievePackageType(String id) throws ItemNotFoundException {

        return packageTypeDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveStandardMedRoute
     * @param id Id
     * @return  StandardMedRouteVo
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveStandardMedicationRoute(java.lang.String)
     */
    @Override
    public StandardMedRouteVo retrieveStandardMedRoute(String id) throws ItemNotFoundException {

        return standardMedRouteDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveShape
     * @param id Id
     * @return  ShapeVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveShape(java.lang.String)
     */
    @Override
    public ShapeVo retrieveShape(String id) {
        for (ShapeVo shape : getShapes()) {
            if (shape.getId().equals(id)) {
                return shape;
            }
        }

        return null;
    }

    /**
     * retrieveDrugUnit
     * @param id Id
     * @return DrugUnitVo
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDrugUnit(java.lang.String)
     */
    @Override
    public DrugUnitVo retrieveDrugUnit(String id) throws ItemNotFoundException {

        return drugUnitDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveCsFedSchedule
     * @param id Id
     * @return CsFedScheduleVo
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveCsFedSchedule(java.lang.String)
     */
    @Override
    public CsFedScheduleVo retrieveCsFedSchedule(String id) throws ItemNotFoundException {

        return csFedScheduleDomainCapability.retrieve(id);
    }

    /**
     * retrieveDispenseUnit
     * @param id Id
     * @return CsFedScheduleVo
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDispenseUnit(java.lang.String)
     */
    @Override
    public DispenseUnitVo retrieveDispenseUnit(String id) throws ItemNotFoundException {

        return dispenseUnitDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveDoseUnit
     * @param id id
     * @return DoseUnitVo 
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDoseUnit(java.lang.String)
     */
    @Override
    public DoseUnitVo retrieveDoseUnit(String id) throws ItemNotFoundException {

        return doseUnitDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveOtcRx
     * @param id id
     * @return OtcRxVo 
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveOtcRx(java.lang.String)
     */
    @Override
    public OtcRxVo retrieveOtcRx(String id) {
        OtcRxVo returnVal = null;

        for (OtcRxVo vo : otcRxDomainCapability.retrieveDomain()) {
            if (vo.getId().equals(id)) {
                returnVal = vo;
                break;
            }
        }

        return returnVal;
    }

    /**
     * retrieveIntendedUse
     * @param id id
     * @return OtcRxVo  
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveIntendedUse(java.lang.String)
     */
    @Override
    public IntendedUseVo retrieveIntendedUse(String id) throws ItemNotFoundException {

        return intendedUseDomainCapability.retrieve(id);
    }

    /**
     * retrieveOrderUnit
     * @param id id
     * @return OtcRxVo  
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveOrderUnit(java.lang.String)
     */
    @Override
    public OrderUnitVo retrieveOrderUnit(String id) throws ItemNotFoundException {

        return orderUnitDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveSpecialHandling
     * @param id id
     * @return SpeciealHandlingVO
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveSpecialHandling(java.lang.String)
     */
    @Override
    public SpecialHandlingVo retrieveSpecialHandling(String id) throws ItemNotFoundException {

        return specialHandlingDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrievePackageUsage
     * @param id id
     * @return PackageUsageVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrievePackageUsage(java.lang.String)
     */
    @Override
    public PackageUsageVo retrievePackageUsage(String id) {

        return null;
    }

    /**
     * retrieveOiScheduleType
     * 
     * @param id id
     * @return OiScheduleTypeVo
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveOiScheduleType(java.lang.String)
     */
    @Override
    public OiScheduleTypeVo retrieveOiScheduleType(String id) {

        return oiScheduleTypeDomainCapability.retrieve(id);
    }

    /**
     * retrieveLocalMedicationRoute
     * @param id id
     * @return LocalMedicationRouteVo
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveLocalMedicationRoute(java.lang.String)
     */
    @Override
    public LocalMedicationRouteVo retrieveLocalMedicationRoute(String id) throws ItemNotFoundException {

        return localMedicationRouteDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveLocalRxConsult
     * 
     * @param id id
     * @return RxConsultVo
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveLocalRxConsult(java.lang.String)
     */
    @Override
    public RxConsultVo retrieveLocalRxConsult(String id) throws ItemNotFoundException {

        return rxConsultDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveNationalRxConsult
     * 
     * @param id id
     * @return RxConsultVo
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveNationalRxConsult(java.lang.String)
     */
    @Override
    public RxConsultVo retrieveNationalRxConsult(String id) throws ItemNotFoundException {

        return rxConsultDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveLocalDrugText
     * 
     * @param id id
     * @return DrugTextVo
     * 
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveLocalDrugText(java.lang.String)
     */
    @Override
    public DrugTextVo retrieveLocalDrugText(String id) throws ItemNotFoundException {

        return drugTextDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveNationalDrugText
     * @param id id
     * @return DrugTextVo
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveNationalDrugText(java.lang.String)
     */
    @Override
    public DrugTextVo retrieveNationalDrugText(String id) throws ItemNotFoundException {

        return drugTextDomainCapability.retrieveMinimal(id);
    }

    /**
     * retrieveCotsRxConsult
     * @param id id
     * @return RxConsultVo
     * 
     * @throws ItemNotFoundException ItemNotFoundException
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveCotsRxConsult(java.lang.String)
     */
    @Override
    public RxConsultVo retrieveCotsRxConsult(String id) throws ItemNotFoundException {

        return rxConsultDomainCapability.retrieveMinimal(id);
    }

    /**
     * getDrugReferenceCapability
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#getDrugReferenceCapability()
     * @return drugReferenceCapability
     */
    @Override
    public DrugReferenceCapability getDrugReferenceCapability() {
        return drugReferenceCapability;
    }

}
