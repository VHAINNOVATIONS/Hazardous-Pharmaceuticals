/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
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
import gov.va.med.pharmacy.peps.service.common.session.DomainService;


/**
 * Provide all possible values ("domain") for the various fields.
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class DomainServiceBean extends AbstractPepsStatelessSessionBean<DomainService> implements DomainService {
    private static final long serialVersionUID = 1;

    /**
     * get admin schedules
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<AdministrationScheduleVo> getAdminSchedules() {
        return getService().getAdminSchedules();
    }

    /**
     * get admin schedule types
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<AdministrationScheduleTypeVo> getAdminScheduleTypes() {
        return getService().getAdminScheduleTypes();
    }

    /**
     * get colors (of the pills)
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<ColorVo> getColors() {
        return getService().getColors();
    }

    /**
     * get data fields
     * 
     * @return DataFields
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public DataFields getDataFields() {
        return getService().getDataFields();
    }

    /**
     * get dosage forms
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<DosageFormVo> getDosageForms() {
        return getService().getDosageForms();
    }

    /**
     * get drug classes
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<DrugClassVo> getDrugClasses() {
        return getService().getDrugClasses();
    }

    /**
     * get drug class types
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<DrugClassificationTypeVo> getDrugClassTypes() {
        return getService().getDrugClassTypes();
    }

    /**
     * get ingredient name list
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<IngredientVo> getIngredientName() {
        return getService().getIngredientName();
    }

    /**
     * get generic names
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<GenericNameVo> getGenericNames() {
        return getService().getGenericNames();
    }

    /**
     * get manufacturers
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<ManufacturerVo> getManufacturers() {
        return getService().getManufacturers();
    }

    /**
     * get package types
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<PackageTypeVo> getPackageTypes() {
        return getService().getPackageTypes();
    }

    /**
     * get routes
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<StandardMedRouteVo> getRoutes() {
        return getService().getRoutes();
    }

    /**
     * get local routes
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<LocalMedicationRouteVo> getLocalRoutes() {
        return getService().getLocalRoutes();
    }

    /**
     * get shapes (of the pills)
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<ShapeVo> getShapes() {
        return getService().getShapes();
    }

    /**
     * get units
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<DrugUnitVo> getUnits() {
        return getService().getUnits();
    }

    /**
     * get CS FED schedule
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<CsFedScheduleVo> getCsFedSchedule() {
        return getService().getCsFedSchedule();
    }

    /**
     * get dispense unit
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<DispenseUnitVo> getDispenseUnit() {
        return getService().getDispenseUnit();
    }

    /**
     * get dose unit
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<DoseUnitVo> getDoseUnit() {
        return getService().getDoseUnit();
    }

    /**
     * Get OTC prescriptions
     * 
     * @return List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<OtcRxVo> getOtcRx() {
        return getService().getOtcRx();
    }

    /**
     * Get all corresponding inpatient drugs for selection. This list is not static and should not be cached!
     * 
     * @return List<ProductVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<ProductVo> getCorrespondingInpatientDrugs() {
        return getService().getCorrespondingInpatientDrugs();
    }

    /**
     * Get all corresponding outpatient drugs for selection. This list is not static and should not be cached!
     * 
     * @return List<String>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<ProductVo> getCorrespondingOutpatientDrugs() {
        return getService().getCorrespondingOutpatientDrugs();
    }

    /**
     * Get all formulary alternatives for selection. This list is not static and should not be cached!
     * 
     * @return List<String>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<ProductVo> getFormularyAlternatives() {
        return getService().getFormularyAlternatives();
    }

    /**
     * Get all intended use values
     * 
     * @return List<IntendedUseVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<IntendedUseVo> getIntendedUse() {
        return getService().getIntendedUse();
    }

    /**
     * Get all order unit values
     * 
     * @return List<OrderUnitVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<OrderUnitVo> getOrderUnits() {
        return getService().getOrderUnits();
    }

    /**
     * Get all order unit values
     * 
     * @return List<SpecialHandlingVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<SpecialHandlingVo> getSpecialHandlings() {
        return getService().getSpecialHandlings();
    }

    /**
     * Get all package Usage values
     * 
     * @return List<PackageUsageVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<PackageUsageVo> getPackageUsages() {
        return getService().getPackageUsages();
    }

    /**
     * Get all OI Schedule types
     * 
     * @return List<OiScheduleTypeVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<OiScheduleTypeVo> getOiScheduleTypes() {
        return getService().getOiScheduleTypes();
    }

    /**
     * Get VistA domain
     * 
     * @param fieldKey param
     * @return List of VistA domain
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<?> getVistaDomain(FieldKey fieldKey) {
        return getService().getVistaDomain(fieldKey);
    }

    /**
     * Get all active, LOCAL and COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<RxConsultVo> getLocalRxConstult() {
        return getService().getLocalRxConstult();
    }

    /**
     * Get all active, NATIONAL and COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<RxConsultVo> getNationalRxConstult() {
        return getService().getNationalRxConstult();
    }

    /**
     * Get all active, LOCAL and national drug text
     * 
     * @return List<DrugTextVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<DrugTextVo> getLocalDrugText() {
        return getService().getLocalDrugText();
    }

    /**
     * Get all active, NATIONAL drug text
     * 
     * @return List<DrugTextVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<DrugTextVo> getNationalDrugText() {
        return getService().getNationalDrugText();
    }

    /**
     * Get all active, COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<RxConsultVo> getCotsRxConsult() {
        return getService().getCotsRxConsult();
    }

    /**
     * get admin schedule via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveAdminSchedule(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public AdministrationScheduleVo retrieveAdminSchedule(String id) throws ItemNotFoundException {
        return getService().retrieveAdminSchedule(id);
    }

    /**
     * get admin schedule type via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveAdminScheduleType(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public AdministrationScheduleTypeVo retrieveAdminScheduleType(String id) throws ItemNotFoundException {

        return getService().retrieveAdminScheduleType(id);
    }

    /**
     * get color via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveColor(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ColorVo retrieveColor(String id) throws ItemNotFoundException {

        return getService().retrieveColor(id);
    }

    /**
     * get data field via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDataField(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public DataFields retrieveDataField(String id) throws ItemNotFoundException {

        return getService().retrieveDataField(id);
    }

    /**
     * get dosage form via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDosageForm(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public DosageFormVo retrieveDosageForm(String id) throws ItemNotFoundException {

        return getService().retrieveDosageForm(id);
    }

    /**
     * get drug class via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDrugClass(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public DrugClassVo retrieveDrugClass(String id) throws ItemNotFoundException {

        return getService().retrieveDrugClass(id);
    }

    /**
     * get drug classification type via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDrugClassificationType(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public DrugClassificationTypeVo retrieveDrugClassificationType(String id) throws ItemNotFoundException {

        return getService().retrieveDrugClassificationType(id);
    }

    /**
     * get ingredient name via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveIngredientName(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public IngredientVo retrieveIngredientName(String id) throws ItemNotFoundException {

        return getService().retrieveIngredientName(id);
    }

    /**
     * get generic name via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveGenericName(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public GenericNameVo retrieveGenericName(String id) throws ItemNotFoundException {

        return getService().retrieveGenericName(id);
    }

    /**
     * get manufacturer via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveManufacturer(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManufacturerVo retrieveManufacturer(String id) throws ItemNotFoundException {

        return getService().retrieveManufacturer(id);
    }

    /**
     * get package type via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrievePackageType(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public PackageTypeVo retrievePackageType(String id) throws ItemNotFoundException {

        return getService().retrievePackageType(id);
    }

    /**
     * get standard med route via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveStandardMedRoute(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public StandardMedRouteVo retrieveStandardMedRoute(String id) throws ItemNotFoundException {

        return getService().retrieveStandardMedRoute(id);
    }

    /**
     * get shape via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveShape(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ShapeVo retrieveShape(String id) throws ItemNotFoundException {

        return getService().retrieveShape(id);
    }

    /**
     * get drug unit via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDrugUnit(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public DrugUnitVo retrieveDrugUnit(String id) throws ItemNotFoundException {

        return getService().retrieveDrugUnit(id);
    }

    /**
     * Description
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveCsFedSchedule(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public CsFedScheduleVo retrieveCsFedSchedule(String id) throws ItemNotFoundException {

        return getService().retrieveCsFedSchedule(id);
    }

    /**
     * get dispense unit via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDispenseUnit(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public DispenseUnitVo retrieveDispenseUnit(String id) throws ItemNotFoundException {

        return getService().retrieveDispenseUnit(id);
    }

    /**
     * get dose unit via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveDoseUnit(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public DoseUnitVo retrieveDoseUnit(String id) throws ItemNotFoundException {

        return getService().retrieveDoseUnit(id);
    }

    /**
     * get OTC Rx via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveOtcRx(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public OtcRxVo retrieveOtcRx(String id) throws ItemNotFoundException {

        return getService().retrieveOtcRx(id);
    }

    /**
     * get intended use via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveIntendedUse(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public IntendedUseVo retrieveIntendedUse(String id) throws ItemNotFoundException {

        return getService().retrieveIntendedUse(id);
    }

    /**
     * get order unit via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveOrderUnit(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public OrderUnitVo retrieveOrderUnit(String id) throws ItemNotFoundException {

        return getService().retrieveOrderUnit(id);
    }

    /**
     * get special handling via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveSpecialHandling(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public SpecialHandlingVo retrieveSpecialHandling(String id) throws ItemNotFoundException {

        return getService().retrieveSpecialHandling(id);
    }

    /**
     * get package usage via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrievePackageUsage(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public PackageUsageVo retrievePackageUsage(String id) throws ItemNotFoundException {

        return getService().retrievePackageUsage(id);
    }

    /**
     * get OI schedule type via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveOiScheduleType(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public OiScheduleTypeVo retrieveOiScheduleType(String id) throws ItemNotFoundException {

        return getService().retrieveOiScheduleType(id);
    }

    /**
     * get local medication route via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveLocalMedicationRoute(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public LocalMedicationRouteVo retrieveLocalMedicationRoute(String id) throws ItemNotFoundException {

        return getService().retrieveLocalMedicationRoute(id);
    }

    /**
     * get local Rx consult via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveLocalRxConsult(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public RxConsultVo retrieveLocalRxConsult(String id) throws ItemNotFoundException {

        return getService().retrieveLocalRxConsult(id);
    }

    /**
     * get national Rx consult via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveNationalRxConsult(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public RxConsultVo retrieveNationalRxConsult(String id) throws ItemNotFoundException {

        return getService().retrieveNationalRxConsult(id);
    }

    /**
     * get local drug text via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveLocalDrugText(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public DrugTextVo retrieveLocalDrugText(String id) throws ItemNotFoundException {

        return getService().retrieveLocalDrugText(id);
    }

    /**
     * get national drug text via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveNationalDrugText(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public DrugTextVo retrieveNationalDrugText(String id) throws ItemNotFoundException {

        return getService().retrieveNationalDrugText(id);
    }

    /**
     * get COTS Rx consult via id
     * 
     * @param id String
     * @return value
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#retrieveCotsRxConsult(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public RxConsultVo retrieveCotsRxConsult(String id) throws ItemNotFoundException {

        return getService().retrieveCotsRxConsult(id);
    }

    /**
     * getDrugReferenceCapability
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.DomainService#getDrugReferenceCapability()
     * 
     * @return DrugReferenceCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type= "Required"
     */
    public DrugReferenceCapability getDrugReferenceCapability() {

        return this.getService().getDrugReferenceCapability();
    }
}
