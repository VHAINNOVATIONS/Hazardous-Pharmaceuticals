/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
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
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.OiScheduleTypeVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.PackageUsageVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.ShapeVo;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;


/**
 * Handles the retrieval of the semi-static domain objects.
 */
public interface DomainService {

    /**
     * Retrieve all possible Administration Schedules
     * 
     * @return List<AdministrationScheduleVo> list of admin schedule
     */
    List<AdministrationScheduleVo> getAdminSchedules();

    /**
     * Retrieve all possible Administration Schedule Types
     * 
     * @return List<AdministrationScheduleTypeVo> list of admin schedule
     */
    List<AdministrationScheduleTypeVo> getAdminScheduleTypes();

    /**
     * Get all Colors for selection
     * 
     * @return List<Color>
     */
    List<ColorVo> getColors();

    /**
     * Get all VA DataFields for selection
     * 
     * @return DataFields
     */
    DataFields getDataFields();

    /**
     * Get all DosageForms for selection
     * 
     * @return List<DosageForm>
     */
    List<DosageFormVo> getDosageForms();

    /**
     * Get all DrugClasses for selection
     * 
     * @return List<DrugClassVo>
     */
    List<DrugClassVo> getDrugClasses();

    /**
     * Get all DrugClassificationTypes for selection
     * 
     * @return List<drugClassificationTypeVo>
     */
    List<DrugClassificationTypeVo> getDrugClassTypes();

    /**
     * Get all Ingredient names for selection
     * 
     * @return List<IngredientNameVo>
     */
    List<IngredientVo> getIngredientName();

    /**
     * Get all GenericNames for selection
     * 
     * @return List<GenericNameVo>
     */
    List<GenericNameVo> getGenericNames();

    /**
     * Get all Manufacturers for selection
     * 
     * @return List<Manufacturer>
     */
    List<ManufacturerVo> getManufacturers();

    /**
     * Get all PackageTypes for selection
     * 
     * @return List<PackageType>
     */
    List<PackageTypeVo> getPackageTypes();

    /**
     * Get all Routes for selection
     * 
     * @return List<Route>
     */
    List<StandardMedRouteVo> getRoutes();

    /**
     * Get all Shapes for selection
     * 
     * @return List<Shape>
     */
    List<ShapeVo> getShapes();

    /**
     * Get all Units for selection
     * 
     * @return List<Unit>
     */
    List<DrugUnitVo> getUnits();

    /**
     * Get all CsFedSchedules for selection
     * 
     * @return List<CsFedScheduleVo>
     */
    List<CsFedScheduleVo> getCsFedSchedule();

    /**
     * Get all VA Dispense Units for selection
     * 
     * @return List<VaDispenseUnitVo>
     */
    List<DispenseUnitVo> getDispenseUnit();

    /**
     * Get all Dose Units for selection
     * 
     * @return List<DoseUnitVo>
     */
    List<DoseUnitVo> getDoseUnit();

    /**
     * Get all OtcRx statuses for selection
     * 
     * @return List<OtcRxVo>
     * 
     */
    List<OtcRxVo> getOtcRx();

    /**
     * Get all corresponding inpatient drugs for selection. This list is not static and should not be cached!
     * 
     * @return List<ProductVo>
     */
    List<ProductVo> getCorrespondingInpatientDrugs();

    /**
     * Get all corresponding outpatient drugs for selection. This list is not static and should not be cached!
     * 
     * @return List<String>
     */
    List<ProductVo> getCorrespondingOutpatientDrugs();

    /**
     * Get all formulary alternatives for selection. This list is not static and should not be cached!
     * 
     * @return List<String>
     */
    List<ProductVo> getFormularyAlternatives();

    /**
     * Get all intended use values
     * 
     * @return List<IntendedUseVo>
     */
    List<IntendedUseVo> getIntendedUse();

    /**
     * Get all order unit values
     * 
     * @return List<OrderUnitVo>
     */
    List<OrderUnitVo> getOrderUnits();

    /**
     * Get all special handling values
     * 
     * @return List<SpecialHandlingVo>
     */
    List<SpecialHandlingVo> getSpecialHandlings();

    /**
     * Get all package usages values
     * 
     * @return List<PackageUseageVo>
     */
    List<PackageUsageVo> getPackageUsages();

    /**
     * 
     * get all OI Schedule Types
     * 
     * @return List<OiScheduleTypeVo>
     */
    List<OiScheduleTypeVo> getOiScheduleTypes();

    /**
     * Get VistA domain
     * 
     * @param fieldKey param
     * @return List of VistA domain
     */
    List<?> getVistaDomain(FieldKey fieldKey);

    /**
     * get local routes
     * 
     * @return local med routes
     */
    List<LocalMedicationRouteVo> getLocalRoutes();

    /**
     * Get all active, LOCAL and COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    List<RxConsultVo> getLocalRxConstult();

    /**
     * Get all active, NATIONAL and COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    List<RxConsultVo> getNationalRxConstult();

    /**
     * Get all active, drug texts (local and national)
     * 
     * @return List<RxConsultVo>
     */
    List<DrugTextVo> getLocalDrugText();

    /**
     * Get all active, national drug text
     * 
     * @return List<RxConsultVo>
     */
    List<DrugTextVo> getNationalDrugText();

    /**
     * Get all active, COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    List<RxConsultVo> getCotsRxConsult();

    /**
     * Retrieve an Administration Schedule
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return AdministrationScheduleVo
     */
    AdministrationScheduleVo retrieveAdminSchedule(String id) throws ItemNotFoundException;

    /**
     * Retrieve an Administration Schedule Type
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return AdministrationScheduleTypeVo 
     */
    AdministrationScheduleTypeVo retrieveAdminScheduleType(String id) throws ItemNotFoundException;

    /**
     * Get a Color
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return Color
     */
    ColorVo retrieveColor(String id) throws ItemNotFoundException;

    /**
     * Get a VA DataField
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return DataFields
     */
    DataFields retrieveDataField(String id) throws ItemNotFoundException;

    /**
     * Get a DosageForm
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return DosageForm
     */
    DosageFormVo retrieveDosageForm(String id) throws ItemNotFoundException;

    /**
     * Get a DrugClass
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return DrugClassVo
     */
    DrugClassVo retrieveDrugClass(String id) throws ItemNotFoundException;

    /**
     * Get a DrugClassificationType
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return drugClassificationTypeVo
     */
    DrugClassificationTypeVo retrieveDrugClassificationType(String id) throws ItemNotFoundException;

    /**
     * Get a Ingredient name
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return IngredientNameVo
     */
    IngredientVo retrieveIngredientName(String id) throws ItemNotFoundException;

    /**
     * Get a GenericName
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return GenericNameVo
     */
    GenericNameVo retrieveGenericName(String id) throws ItemNotFoundException;

    /**
     * Get a Manufacturer
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return Manufacturer
     */
    ManufacturerVo retrieveManufacturer(String id) throws ItemNotFoundException;

    /**
     * Get a PackageType
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return PackageType
     */
    PackageTypeVo retrievePackageType(String id) throws ItemNotFoundException;

    /**
     * Get a Route
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return Route
     */
    StandardMedRouteVo retrieveStandardMedRoute(String id) throws ItemNotFoundException;

    /**
     * Get a Shape
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return Shape
     */
    ShapeVo retrieveShape(String id) throws ItemNotFoundException;

    /**
     * Get a Unit
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return Unit
     */
    DrugUnitVo retrieveDrugUnit(String id) throws ItemNotFoundException;

    /**
     * Get a CsFedSchedule
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return CsFedScheduleVo
     */
    CsFedScheduleVo retrieveCsFedSchedule(String id) throws ItemNotFoundException;

    /**
     * Get a VA Dispense Unit
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return VaDispenseUnitVo
     */
    DispenseUnitVo retrieveDispenseUnit(String id) throws ItemNotFoundException;

    /**
     * Get a Dose Unit
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return DoseUnitVo
     */
    DoseUnitVo retrieveDoseUnit(String id) throws ItemNotFoundException;

    /**
     * Get a OtcRx status
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return OtcRxVo
     * 
     */
    OtcRxVo retrieveOtcRx(String id) throws ItemNotFoundException;

    /**
     * Get a intended use
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return IntendedUseVo
     */
    IntendedUseVo retrieveIntendedUse(String id) throws ItemNotFoundException;

    /**
     * Get a order unit
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return OrderUnitVo
     */
    OrderUnitVo retrieveOrderUnit(String id) throws ItemNotFoundException;

    /**
     * Get a special handling
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return SpecialHandlingVo
     */
    SpecialHandlingVo retrieveSpecialHandling(String id) throws ItemNotFoundException;

    /**
     * Get a package usage
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return PackageUseageVo
     */
    PackageUsageVo retrievePackageUsage(String id) throws ItemNotFoundException;

    /**
     * Get n OI Schedule Type
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return OiScheduleTypeVo
     */
    OiScheduleTypeVo retrieveOiScheduleType(String id) throws ItemNotFoundException;

    /**
     * get local med route 
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return local med routes
     */
    LocalMedicationRouteVo retrieveLocalMedicationRoute(String id) throws ItemNotFoundException;

    /**
     * Get a active, LOCAL and COTS RX Consult.
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return RxConsultVo
     */
    RxConsultVo retrieveLocalRxConsult(String id) throws ItemNotFoundException;

    /**
     * Get a active, NATIONAL and COTS RX Consult.
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return RxConsultVo
     */
    RxConsultVo retrieveNationalRxConsult(String id) throws ItemNotFoundException;

    /**
     * Get a active, drug texts (local and national)
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return RxConsultVo
     */
    DrugTextVo retrieveLocalDrugText(String id) throws ItemNotFoundException;

    /**
     * Get a active, national drug text
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return RxConsultVo
     */
    DrugTextVo retrieveNationalDrugText(String id) throws ItemNotFoundException;

    /**
     * Get a active, COTS RX Consult.
     * 
     * @param id String
     * @exception ItemNotFoundException exception
     * 
     * @return RxConsultVo
     */
    RxConsultVo retrieveCotsRxConsult(String id) throws ItemNotFoundException;
    
    /**
     * getDrugReferenceCapability
     * @return drugReferenceCapability
     */
    DrugReferenceCapability getDrugReferenceCapability();

}
