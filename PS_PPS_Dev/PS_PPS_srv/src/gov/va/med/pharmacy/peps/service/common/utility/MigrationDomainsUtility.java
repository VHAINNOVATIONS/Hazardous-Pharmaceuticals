/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationErrorVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationResponseVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.DispenseUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DosageFormValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DrugClassificationValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DrugUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.GenericNameValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.IngredientValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.domain.common.capability.CsFedScheduleDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DispenseUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DosageFormDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugClassDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.GenericNameDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.IngredientDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.IntendedUseDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.MigrationErrorDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderableItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ResetNationalDatabaseDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.SpecialHandlingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.StandardMedRouteDomainCapability;




/**
 * This utility class is used to move some of the complexity of the MigrationCapabiltyImpl file into another file.
 *
 */
public class MigrationDomainsUtility {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MigrationDomainsUtility.class);
    private static final String S999 = "999";
    private static final String MIGRATION = "Migration";
    private static final String CAUSE_WAS = " The cause was ";
    private static final String ALREADY_EXISTS = " because it already exists in the database.";
    private static final String TO = " to ";
    private static final String ACTIVE_DUP = " is an Active Duplicate.";
    private static final String INACT_DATE = "InactivationDate";
    private static final String SNULL = "null";
    private static final String N_A = "N/A";
    
    
    // these hashmaps are used to speed up the lookup time for the migration effort.
    private Map<String, String> specHandMap;
    private Map<String, String> intendedUseMap;
    private Map<String, String> orderUnitMap;
    private Map<String, ComplexIENStore> drugUnitMap;
    private Map<String, ComplexIENStore> dispenseUnitMap;
    private Map<String, String> csFedSchedMap;
    private Map<String, ComplexStore> productMap;
    private Map<String, ComplexStore> orderableMap;
    private Map<String, ComplexGenericStore> genericMap;
    private Map<String, ComplexIENStore> ingredientMap;
    private Map<String, ComplexIENStore> drugClassMap;
    private Map<String, ComplexIENStore> manufacturerMap;
    private Map<String, ComplexIENStore> packageTypeMap;
    private Map<String, ComplexIENStore> dosageFormMap;
    private Map<String, String> standardMedRouteMap;
    private Map<String, String> ndcMap;

    private SpecialHandlingDomainCapability specialHandlingDomainCapability;

    private IntendedUseDomainCapability intendedUseDomainCapability;

    private OrderUnitDomainCapability orderUnitDomainCapability;

    private DrugUnitDomainCapability drugUnitDomainCapability;

    private GenericNameDomainCapability genericNameDomainCapability;

    private DispenseUnitDomainCapability dispenseUnitDomainCapability;

    private CsFedScheduleDomainCapability csFedScheduleDomainCapability;

    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;

    private ProductDomainCapability productDomainCapability;

    private OrderableItemDomainCapability orderableItemDomainCapability;

    private IngredientDomainCapability ingredientDomainCapability;

    private PackageTypeDomainCapability packageTypeDomainCapability;

    private ManufacturerDomainCapability manufacturerDomainCapability;

    private DosageFormDomainCapability dosageFormDomainCapability;

    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;

    private DrugClassDomainCapability drugClassDomainCapability;

    private NdcDomainCapability ndcDomainCapability;

    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private MigrationErrorDomainCapability migrationErrorDomainCapability;
    

    /**
     * clear out the hashmaps to save memory after migration
     */
    public void clear() {

        try {
            if (specHandMap != null) {
                specHandMap.clear();
                specHandMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing special handling map! " + e.getMessage());
        }

        try {
            if (intendedUseMap != null) {
                intendedUseMap.clear();
                intendedUseMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Intended Use map! " + e.getMessage());
        }

        try {
            if (drugUnitMap != null) {
                drugUnitMap.clear();
                drugUnitMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Drug Unit map! " + e.getMessage());
        }

        try {
            if (genericMap != null) {
                genericMap.clear();
                genericMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Generic Name map! " + e.getMessage());
        }

        try {
            if (dispenseUnitMap != null) {
                dispenseUnitMap.clear();
                dispenseUnitMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Dispense Unit map! " + e.getMessage());
        }

        try {
            if (csFedSchedMap != null) {
                csFedSchedMap.clear();
                csFedSchedMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Cs Federal Schedule Map! " + e.getMessage());
        }

        try {
            if (productMap != null) {
                productMap.clear();
                productMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Product Map! " + e.getMessage());
        }

        try {
            if (orderableMap != null) {
                orderableMap.clear();
                orderableMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Orderable Item Map! " + e.getMessage());
        }

        try {
            if (ingredientMap != null) {
                ingredientMap.clear();
                ingredientMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Ingredient Map! " + e.getMessage());
        }

        clear2();
    }

    /** 
     * clear2 
     */
    private void clear2() {
        try {
            if (drugClassMap != null) {
                drugClassMap.clear();
                drugClassMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Drug Class Map! " + e.getMessage());
        }

        try {
            if (manufacturerMap != null) {
                manufacturerMap.clear();
                manufacturerMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Manufacturer Map! " + e.getMessage());
        }

        try {
            if (packageTypeMap != null) {
                packageTypeMap.clear();
                packageTypeMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Package Type Map! " + e.getMessage());
        }

        try {
            if (dosageFormMap != null) {
                dosageFormMap.clear();
                dosageFormMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing Dosage Form Map! " + e.getMessage());
        }

        try {
            if (standardMedRouteMap != null) {
                standardMedRouteMap.clear();
                standardMedRouteMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing standardMedRoute Map! " + e.getMessage());
        }

        try {
            if (ndcMap != null) {
                ndcMap.clear();
                ndcMap = null;
            }
        } catch (Exception e) {
            LOG.debug("Exception clearing NDC Map! " + e.getMessage());
        }
    }

    /**
     * init() method used to populate hasmaps.  Must be called before other methods are called
     */
    public void init() {
        clear();

        specHandMap = new HashMap<String, String>(PPSConstants.I30);
        List<SpecialHandlingVo> specHandList = specialHandlingDomainCapability.retrieve();

        for (SpecialHandlingVo vo : specHandList) {
            specHandMap.put(vo.getCode(), vo.getId());
        }

        intendedUseMap = new HashMap<String, String>(PPSConstants.I10);
        List<IntendedUseVo> intendedUseList = intendedUseDomainCapability.retrieveDomain();

        for (IntendedUseVo vo : intendedUseList) {
            intendedUseMap.put(vo.getIntendedUse(), vo.getId());
        }

        orderUnitMap = new HashMap<String, String>(PPSConstants.I100);
        List<OrderUnitVo> orderUnitList = orderUnitDomainCapability.retrieve();

        for (OrderUnitVo vo : orderUnitList) {
            orderUnitMap.put(vo.getAbbrev(), vo.getId());
        }

        drugUnitMap = new HashMap<String, ComplexIENStore>(PPSConstants.I100);
        List<DrugUnitVo> drugUnitList = drugUnitDomainCapability.retrieve();

        for (DrugUnitVo vo : drugUnitList) {
            ComplexIENStore drugUnitStore = new ComplexIENStore(vo.getId(), vo.getDrugUnitIen());
            drugUnitMap.put(vo.getValue(), drugUnitStore);
        }

        genericMap = new HashMap<String, ComplexGenericStore>(PPSConstants.I100);
        List<GenericNameVo> genericList = genericNameDomainCapability.retrieve();

        for (GenericNameVo vo : genericList) {
            ComplexGenericStore cGS = new ComplexGenericStore(vo.getId(), vo.getGenericIen());
            genericMap.put(vo.getValue(), cGS);
        }

        dispenseUnitMap = new HashMap<String, ComplexIENStore>(PPSConstants.I100);
        List<DispenseUnitVo> dispenseUnitList = dispenseUnitDomainCapability.retrieve();

        for (DispenseUnitVo vo : dispenseUnitList) {
            ComplexIENStore dispenseUnitStore = new ComplexIENStore(vo.getId(), vo.getDispenseUnitIen());
            dispenseUnitMap.put(vo.getValue(), dispenseUnitStore);
        }

        csFedSchedMap = new HashMap<String, String>(PPSConstants.I12);
        List<CsFedScheduleVo> csFedList = csFedScheduleDomainCapability.retrieveDomain();

        for (CsFedScheduleVo vo : csFedList) {
            csFedSchedMap.put(vo.getValue(), vo.getId());
        }

        productMap = new HashMap<String, ComplexStore>(PPSConstants.I1000);
        List<Long> productList = resetNationalDatabaseDomainCapability.getIds(EntityType.PRODUCT, false);

        for (Long productId : productList) {
            try {
                ProductVo vo1 = productDomainCapability.retrieve(productId.toString());
                ComplexStore type = new ComplexStore(vo1.getId(), vo1.getCategories(), vo1.getNdfProductIen().toString(), null);
                productMap.put(vo1.getVaProductName(), type);
            } catch (ItemNotFoundException e) {
                LOG.error("Exception adding product to Map for product " + productId + ". Product Exception is "
                    + e.getMessage());
            }
        }

        orderableMap = new HashMap<String, ComplexStore>(PPSConstants.I1000);
        List<Long> orderableList = resetNationalDatabaseDomainCapability.getIds(EntityType.ORDERABLE_ITEM, false);

        for (Long oiId : orderableList) {
            try {
                OrderableItemVo vo1 = orderableItemDomainCapability.retrieve(oiId.toString());

                ComplexStore type = new ComplexStore(vo1.getId(), vo1.getCategories(), null, vo1.getDosageForm());
                orderableMap.put(vo1.getOiName(), type);
            } catch (ItemNotFoundException e) {
                LOG.error("Exception adding OI to Map for OI " + oiId + ".  OI Exception is " + e.getMessage());
            }
        }

        ingredientMap = new HashMap<String, ComplexIENStore>(PPSConstants.I20);
        List<IngredientVo> ingredientList = this.ingredientDomainCapability.retrieve();

        for (IngredientVo vo : ingredientList) {
            ComplexIENStore ingredientStore = new ComplexIENStore(vo.getId(), vo.getNdfIngredientIen());
            ingredientMap.put(vo.getValue(), ingredientStore);
        }

        packageTypeMap = new HashMap<String, ComplexIENStore>(PPSConstants.I20);
        List<PackageTypeVo> packageList = this.packageTypeDomainCapability.retrieve();

        for (PackageTypeVo vo : packageList) {
            ComplexIENStore type = new ComplexIENStore(vo.getId(), vo.getPackagetypeIen());
            packageTypeMap.put(vo.getValue(), type);
        }

        manufacturerMap = new HashMap<String, ComplexIENStore>(PPSConstants.I20);
        List<ManufacturerVo> manList = this.manufacturerDomainCapability.retrieve();

        for (ManufacturerVo vo : manList) {
            ComplexIENStore type = new ComplexIENStore(vo.getId(), vo.getManufacturerIen());
            manufacturerMap.put(vo.getValue(), type);
        }

        dosageFormMap = new HashMap<String, ComplexIENStore>(PPSConstants.I100);
        List<DosageFormVo> dosageFormList = this.dosageFormDomainCapability.retrieve();

        for (DosageFormVo vo : dosageFormList) {
            ComplexIENStore dosageFormStore = new ComplexIENStore(vo.getId(), vo.getDosageFormIen());
            dosageFormMap.put(vo.getDosageFormName(), dosageFormStore);
        }

        standardMedRouteMap = new HashMap<String, String>(PPSConstants.I100);
        List<StandardMedRouteVo> standardMedRouteList = this.standardMedRouteDomainCapability.retrieve();

        for (StandardMedRouteVo vo : standardMedRouteList) {
            standardMedRouteMap.put(vo.getValue(), vo.getId());
        }

        drugClassMap = new HashMap<String, ComplexIENStore>(PPSConstants.I100);
        List<DrugClassVo> drugClassList = this.drugClassDomainCapability.retrieve();

        for (DrugClassVo vo : drugClassList) {
            ComplexIENStore drugClassStore = new ComplexIENStore(vo.getId(), vo.getDrugClassIen());
            drugClassMap.put(vo.getCode() + vo.getClassification(), drugClassStore);
        }

        ndcMap = new HashMap<String, String>(PPSConstants.I100);
        List<Long> ndcList = resetNationalDatabaseDomainCapability.getIds(EntityType.NDC, false);

        for (Long ndcId : ndcList) {
            try {
                NdcVo vo1 = ndcDomainCapability.retrieve(ndcId.toString());
                ndcMap.put(vo1.getNdc(), vo1.getId());
            } catch (ItemNotFoundException e) {
                LOG.error("Exception adding NDC to Map for NDC " + ndcId + ". Exception is " + e.getMessage());
            }
        }
    }
    
    /**
     * createItemAuditHistory record
     * @param type EntityType
     * @param id EPL_ID
     */
    public void createItemAuditHistory(EntityType type, String id) {
        try {
            ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
            audit.setReason("MIGRATED");
            audit.setAuditItemId(id);
            audit.setCreatedBy("SYSTEM STARTUP");
            audit.setEventCategory(EventCategory.SYSTEM_EVENT);
            audit.setAuditItemType(type);
            audit.setSiteName(S999);
            audit.setUsername(getUser().getUsername());
            itemAuditHistoryDomainCapability.create(audit, null, getUser());
        } catch (Exception e) {
            StringBuffer str = new StringBuffer();
            str.append("Exception with audit history record of type type " + type + " and id of " + id + " the exception is "
                + e.getMessage());

            if (e.getCause() != null) {
                str.append(e.getCause());
            }

            LOG.error(str.toString());
        }
    }
    
    /**
     * getUser
     * @return UserVo
     */
    public UserVo getUser() {
        UserVo user = new UserVo();
        user.setFirstName(MIGRATION);
        user.setLastName(MIGRATION);
        user.setStationNumber(S999);
        user.setUsername("MIGRATOR");
        user.setLocation(S999);
        user.setId(new Long("42"));

        user.addRole(Role.PSS_PPSN_MANAGER /*NATIONAL_SERVICE_MANAGER*/);

        return user;

    }
    
    /**
     * createErrorVo
     * @param file File
     * @param e Exception
     * @return MigrationErrorVo
     */
    public MigrationErrorVo createErrorVo(String file, MigrationException e) {

        return createErrorVo(file, e.getIen(), e.getField(), e.getfieldValue(), e.getItem(), e.getMessage());
    }
    
    /**
     * createErrorVo
     * @param file File
     * @param rowId RowId
     * @param fieldName FieldName
     * @param fieldValue FieldValue
     * @param uniqueName UniqueName
     * @param errorText ErrorText
     * @return MigrationErrorVo
     */
    public MigrationErrorVo createErrorVo(String file, String rowId, String fieldName, String fieldValue, String uniqueName,
        String errorText) {
        return createErrorVo(file, rowId, fieldName, fieldValue, uniqueName, errorText, "", "");
    }

    /**
     * createErrorVO
     * @param file File
     * @param rowId RowId
     * @param fieldName fieldName
     * @param fieldValue fieldValue
     * @param uniqueName uniqueName
     * @param errorText errorText
     * @param multiRowId MultiRowId
     * @param multiFieldName MultiFieldName
     * @return migrationErrorVo
     */
    private MigrationErrorVo createErrorVo(String file, String rowId, String fieldName, String fieldValue, String uniqueName,
        String errorText, String multiRowId, String multiFieldName) {
        MigrationErrorVo errorVo = new MigrationErrorVo();

        errorVo.setFileId(file);
        errorVo.setMigrationRowId(rowId);
        errorVo.setMigrationUniqueName(uniqueName);
        errorVo.setMigrationFieldName(fieldName);
        errorVo.setMigrationFieldValue(fieldValue);
        errorVo.setDetailedErrorText(errorText);
        errorVo.setMigrationMultiFieldName(multiFieldName);
        errorVo.setMigrationMultiRowId(multiRowId);
        errorVo.setProcessedDTM(new Date());
        errorVo.setCreatedDate(new Date());
        errorVo.setCreatedBy(MIGRATION);

        return errorVo;
    }
    
    /**
     * saveMigrationErrorMessage save the MigrationErrorMessage to the database
     * 
     * @param file Name of the File.
     * @param e the Exception that occurred
     * @return boolean
     */
    public boolean saveMigrationErrorMessage(String file, MigrationException e) {
        MigrationErrorVo errorVo = createErrorVo(file, e);

        return saveMigrationErrorMessage(errorVo);
    }

    /**
     * saveMigrationErrorMessage Save the migration error Vo to the database.
     * 
     * @param errorVo MigrationErrorVo
     * @return boolean
     */
    public boolean saveMigrationErrorMessage(MigrationErrorVo errorVo) {
        String str =
            "Migration Error: " + errorVo.getMigrationUniqueName() + ":" + errorVo.getFileId() + ":"
                + errorVo.getMigrationFieldName() + ":" + errorVo.getDetailedErrorText();

        LOG.debug(str);

        if (StringUtils.isBlank(errorVo.getMigrationRowId())) {
            errorVo.setMigrationRowId("0");
        }

        if (StringUtils.isBlank(errorVo.getMigrationFieldName())) {
            errorVo.setMigrationFieldName(N_A);
        }

        if (StringUtils.isBlank(errorVo.getMigrationFieldValue())) {
            errorVo.setMigrationFieldValue(N_A);
        }

        if (StringUtils.isBlank(errorVo.getMigrationMultiRowId())) {
            errorVo.setMigrationMultiRowId("0");
        }

        if (StringUtils.isBlank(errorVo.getMigrationUniqueName())) {
            errorVo.setMigrationUniqueName(N_A);
        }

        if (StringUtils.isBlank(errorVo.getMigrationMultiFieldName())) {
            errorVo.setMigrationMultiFieldName(N_A);
        }

        migrationErrorDomainCapability.create(errorVo, getUser());

        return true;
    }

    
    /**
     * Pulls the error messages from the database.  Cap any particular file at 500;
     * @param file File
     * @return A list of MigrationErrorVos
     */
    public List<MigrationErrorVo> retrieveMigrationErrorMessages(String file) {
        int counter = 0;
        List<MigrationErrorVo> list = retrieveMigrationErrorMessages();
        ArrayList<MigrationErrorVo> fileList = new ArrayList<MigrationErrorVo>();

        for (MigrationErrorVo vo : list) {
            if (vo.getFileId().equalsIgnoreCase(file)) {
                counter++;
                fileList.add(vo);

                if (counter >= PPSConstants.I100) {
                    return fileList;
                }
            }
        }

        return fileList;
    }
    
    /**
     * retrieveMigrationErrorMessage
     * @return List of MigrationErrorVos
     */
    public List<MigrationErrorVo> retrieveMigrationErrorMessages() {

        return migrationErrorDomainCapability.retrieveMigrationError();
    }
    
    /**
     * Migrate the Drug Units domain
     * @param migrationResponseVo : migrationRepsosne
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of drug units to migrate at a time.
     * @return A Vo containing information about the success of the migration
     */
    public MigrationVariablesVo migrateDrugUnits(MigrationResponseVo migrationResponseVo, String ien, int size) {


        MigrationVariablesVo varVo = new MigrationVariablesVo();
        int maxIEN = 0;
        
        if (migrationResponseVo == null) {
            varVo.setEndOfFile(true);
            Long lIEN = Long.valueOf(ien);
            lIEN += size;
            varVo.setStrLastIEN(String.valueOf(lIEN));
            String str =
                "An error occured in retrieving the xml for the Drug Unit file for IENs " + ien + TO + varVo.getStrLastIEN()
                    + ". Those drug units were not migrated.";
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_UNIT_FILE, ien, "", "", "", str));

            return varVo;
        }

        List<MigrationException> errorExc = migrationResponseVo.getMigrationExceptions();

        for (MigrationException me : errorExc) {
            varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_UNIT_FILE, me));
        }

        // Get the drugUnits from the migration file
        ArrayList<ManagedItemVo> drugUnitsList = (ArrayList<ManagedItemVo>) migrationResponseVo.getManagedItemsVos();

        DrugUnitValidator validator = new DrugUnitValidator();

        for (int index = 0; index < drugUnitsList.size(); index++) {
            DrugUnitVo vo = (DrugUnitVo) drugUnitsList.get(index);
            vo.setCreatedBy(MIGRATION);
            vo.setRequestItemStatus(RequestItemStatus.APPROVED);
            Errors errors = new Errors();
            validator.validate(vo, errors);

            try {
                int iIEN = Integer.valueOf(vo.getDrugUnitIen());

                if (iIEN > maxIEN) {
                    maxIEN = iIEN;
                }
            } catch (NumberFormatException e) {
                LOG.error(" Drug Unit VO, IEN Format exception: " + vo.getDrugUnitIen() + "." + e.getMessage());
            }

            if (errors.hasErrors()) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                List<ValidationError> errorList = errors.getErrors();
                String str = "";

                for (ValidationError e : errorList) {
                    str = e.getLocalizedError() + " ";
                }

                saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_UNIT_FILE, vo.getDrugUnitIen(), "", "",
                    vo.getValue(), str));
            } else {

                // load to the database
                try {
                    if (drugUnitMap.get(vo.getValue()) == null) {
                        vo = drugUnitDomainCapability.createWithoutDuplicateCheck(vo, getUser());
                        createItemAuditHistory(EntityType.DRUG_UNIT, vo.getId());
                        ComplexIENStore drugUnitStore = new ComplexIENStore(vo.getId(), vo.getDrugUnitIen());
                        drugUnitMap.put(vo.getValue(), drugUnitStore);
                        varVo.setISuccessfullyMigrated(varVo.getISuccessfullyMigrated() + 1);
                    } else if (vo.getInactivationDate() == null) { // Duplicates that have no inactivation date are errors
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str = "The Drug Unit " + vo.getValue() + ACTIVE_DUP;
                        LOG.error(str);
                        saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_UNIT_FILE, "", INACT_DATE, SNULL,
                            vo.getValue(), str));
                    } else {

                        // duplicates with inactivation dates are just counted as duplicates.
                        varVo.setIDuplicatesNotMigrated(varVo.getIDuplicatesNotMigrated() + 1);
                        LOG.error("DUPLICATE: Did Not Migrate Drug Unit: " + vo.getValue() + ALREADY_EXISTS);
                    }
                } catch (Exception e) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    StringBuffer str = new StringBuffer();
                    str.append("Exception saving Drug Unit: " + e.getMessage());

                    if (e.getCause() != null) {
                        str.append(CAUSE_WAS + e.getCause().getMessage());
                    }

                    saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_UNIT_FILE, vo.getDrugUnitIen(), "", "",
                        vo.getValue(), str.toString()));
                }
            }
        }

        // set the varVo for the return message for the Migrated Drug Units.
        varVo.setStrLastIEN(String.valueOf(maxIEN));
        varVo.setEndOfFile(migrationResponseVo.isEof());

        return varVo;
    }
    
    /**
     * Migrate the Dispense Units domain
     * @param migrationResponseVo : migrationRepsosne
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of Dispense units to migrate at a time.
     * @return A Vo containing information about the success of the migration
     */
    public MigrationVariablesVo migrateDispenseUnits(MigrationResponseVo migrationResponseVo, String ien, int size) {
        MigrationVariablesVo varVo = new MigrationVariablesVo();
        int maxIEN = 0;
        
        if (migrationResponseVo == null) {
            varVo.setEndOfFile(true);
            Long lIEN = Long.valueOf(ien);
            lIEN += size;
            varVo.setStrLastIEN(String.valueOf(lIEN));
            String str =
                "An error occured in retrieving the xml for the Dispense Unit file for IENs " + ien + TO
                    + varVo.getStrLastIEN() + ". Those dispense units were not migrated.";
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DISPENSE_UNIT_FILE, ien, "", "", "", str));

            return varVo;
        }

        List<MigrationException> errorExc = migrationResponseVo.getMigrationExceptions();

        for (MigrationException me : errorExc) {
            varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DISPENSE_UNIT_FILE, me));
        }

        ArrayList<ManagedItemVo> dispenseUnitsList = (ArrayList<ManagedItemVo>) migrationResponseVo.getManagedItemsVos();

        // run the validator and log errors if necessary and save the entry to the database
        DispenseUnitValidator validator = new DispenseUnitValidator();

        for (int index = 0; index < dispenseUnitsList.size(); index++) {
            Errors errors = new Errors();
            DispenseUnitVo vo = (DispenseUnitVo) dispenseUnitsList.get(index);
            vo.setCreatedBy(MIGRATION);
            vo.setRequestItemStatus(RequestItemStatus.APPROVED);
            validator.validate(vo, errors);

            try {
                int iIEN = Integer.valueOf(vo.getDispenseUnitIen());

                if (iIEN > maxIEN) {
                    maxIEN = iIEN;
                }
            } catch (NumberFormatException e) {
                LOG.error("Dispense Unit VO, IEN Format exception: " + vo.getDispenseUnitIen() + "." + e.getMessage());
            }

            if (errors.hasErrors()) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                List<ValidationError> errorList = errors.getErrors();
                String str = "";

                for (ValidationError e : errorList) {
                    str = e.getLocalizedError() + " ";
                }

                saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DISPENSE_UNIT_FILE, vo.getDispenseUnitIen(), "", "",
                    vo.getValue(), str));
            } else {

                // load to the database
                try {
                    if (dispenseUnitMap.get(vo.getValue()) == null) {
                        vo = dispenseUnitDomainCapability.createWithoutDuplicateCheck(vo, getUser());
                        createItemAuditHistory(EntityType.DISPENSE_UNIT, vo.getId());
                        ComplexIENStore dispenseUnitStore = new ComplexIENStore(vo.getId(), vo.getDispenseUnitIen());
                        dispenseUnitMap.put(vo.getValue(), dispenseUnitStore);
                        varVo.setISuccessfullyMigrated(varVo.getISuccessfullyMigrated() + 1);
                    } else if (vo.getInactivationDate() == null) { // Duplicates that have no inactivation date are errors
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str = "The Dispense Unit " + vo.getValue() + ACTIVE_DUP;
                        LOG.error(str);
                        saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DISPENSE_UNIT_FILE, "", INACT_DATE, SNULL,
                            vo.getValue(), str));
                    } else { // duplicates with inactivation dates are just counted as duplicates.
                        varVo.setIDuplicatesNotMigrated(varVo.getIDuplicatesNotMigrated() + 1);
                        LOG.error("DUPLICATE: Did Not Migrate Dispense Unit: " + vo.getValue() + ALREADY_EXISTS);
                    }
                } catch (Exception e) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    StringBuffer sb = new StringBuffer("Save Dispense Unit Exception: ");
                    sb.append(e.getMessage());

                    if (e.getCause() != null) {
                        sb.append(CAUSE_WAS).append(e.getCause().getMessage());
                    }

                    saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DISPENSE_UNIT_FILE, vo.getDispenseUnitIen(), "",
                        "", vo.getValue(), sb.toString()));
                }
            }
        }

        varVo.setStrLastIEN(String.valueOf(maxIEN));
        varVo.setEndOfFile(migrationResponseVo.isEof());

        return varVo;
    }
    
    
    /**
     * Migrate the VA Generic domain
     * @param migrationResponseVo : migrationRepsosne
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of Generics to migrate at a time.
     * @return A Vo containing information about the success of the migration
     */
    public MigrationVariablesVo migrateVAGeneric(MigrationResponseVo migrationResponseVo, String ien, int size) {
        MigrationVariablesVo varVo = new MigrationVariablesVo();
        int maxIEN = 0;
        
        if (migrationResponseVo == null) {
            varVo.setEndOfFile(true);
            Long lIEN = Long.valueOf(ien);
            lIEN += size;
            varVo.setStrLastIEN(String.valueOf(lIEN));
            String str =
                "An error occured in retrieving the xml for the Generic Name file for IENs " + ien + TO + varVo.getStrLastIEN()
                    + ". Those Generic Names were not migrated.";
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_GENERIC_NAME_FILE, ien, "", "", "", str));

            return varVo;
        }

        List<MigrationException> errorExc = migrationResponseVo.getMigrationExceptions();

        for (MigrationException me : errorExc) {
            varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_GENERIC_NAME_FILE, me));
        }

        // Set the end of file value and get the GenericName from the migration file
        ArrayList<ManagedItemVo> genericNameList = (ArrayList<ManagedItemVo>) migrationResponseVo.getManagedItemsVos();

        // run the validator and log errors if necessary and save the entry to the database
        GenericNameValidator validator = new GenericNameValidator();

        for (int index = 0; index < genericNameList.size(); index++) {
            GenericNameVo vo = (GenericNameVo) genericNameList.get(index);
            vo.setCreatedBy(MIGRATION);
            vo.setRequestItemStatus(RequestItemStatus.APPROVED);
            Errors errors = new Errors();
            validator.validate(vo, errors);

            try {
                int iIEN = Integer.valueOf(vo.getGenericIen());

                if (iIEN > maxIEN) {
                    maxIEN = iIEN;
                }
            } catch (NumberFormatException e) {
                LOG.error("Drug Unit ValueObject, IEN Format exception: " + vo.getGenericIen() + "." + e.getMessage());
            }

            if (errors.hasErrors()) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                List<ValidationError> errorList = errors.getErrors();
                String str = "";

                for (ValidationError e : errorList) {
                    str = e.getLocalizedError() + " ";
                }

                saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_GENERIC_NAME_FILE, vo.getGenericIen(), "", "",
                    vo.getValue(), str));
            } else {

                // load to the database
                try {
                    if (genericMap.get(vo.getValue()) == null) {
                        vo = genericNameDomainCapability.createWithoutDuplicateCheck(vo, getUser());
                        createItemAuditHistory(EntityType.GENERIC_NAME, vo.getId());
                        ComplexGenericStore cGS = new ComplexGenericStore(vo.getId(), vo.getGenericIen());
                        genericMap.put(vo.getValue(), cGS);
                        varVo.setISuccessfullyMigrated(varVo.getISuccessfullyMigrated() + 1);
                    } else if (vo.getInactivationDate() == null) { // Duplicates that have no inactivation date are errors
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str = "The Generic Name " + vo.getValue() + ACTIVE_DUP;
                        LOG.error(str);
                        saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_GENERIC_NAME_FILE, "", INACT_DATE, SNULL,
                            vo.getValue(), str));
                    } else { // duplicates with inactivation dates are just counted as duplicates.
                        varVo.setIDuplicatesNotMigrated(varVo.getIDuplicatesNotMigrated() + 1);
                        LOG.error("DUPLICATE: Did Not Migrate Generic Name: " + vo.getValue() + ALREADY_EXISTS);
                    }
                } catch (Exception e) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    StringBuffer sb = new StringBuffer("Uncaught Exception3: ");
                    sb.append(e.getMessage());

                    if (e.getCause() != null) {
                        sb.append(CAUSE_WAS).append(e.getCause().getMessage());
                    }

                    saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_GENERIC_NAME_FILE, vo.getGenericIen(), "", "",
                        vo.getValue(), sb.toString()));
                }
            }
        }

        // set the varVo for the Generic Drug return data;
        varVo.setStrLastIEN(String.valueOf(maxIEN));
        varVo.setEndOfFile(migrationResponseVo.isEof());

        return varVo;
    }
    
    /**
     * Migrate the VA DrugIngredient domain
     * @param migrationResponseVo : migrationRepsosne
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of Ingredients to migrate at a time.
     * @return A Vo containing information about the success of the migration
     */
    public MigrationVariablesVo migrateDrugIngredient(MigrationResponseVo migrationResponseVo, String ien, int size) {
        MigrationVariablesVo varVo = new MigrationVariablesVo();
        int maxIEN = 0;
        ArrayList<IngredientVo> noPrimaryList = new ArrayList<IngredientVo>();
        
        if (migrationResponseVo == null) {
            varVo.setEndOfFile(true);
            Long lIEN = Long.valueOf(ien);
            lIEN += size;
            varVo.setStrLastIEN(String.valueOf(lIEN));
            String str =
                "An error occured in retrieving the xml for the Drug Ingredients file for IENs " + ien + TO
                    + varVo.getStrLastIEN() + ". Those Drug Ingredients were not migrated.";
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_INGREDIENT_FILE, ien, "", "", "", str));

            return varVo;
        }

        List<MigrationException> errorExc = migrationResponseVo.getMigrationExceptions();

        for (MigrationException me : errorExc) {
            varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_INGREDIENT_FILE, me));
        }

        //Get the Ingredients from the migration file
        ArrayList<ManagedItemVo> ingredientNameList = (ArrayList<ManagedItemVo>) migrationResponseVo.getManagedItemsVos();

        // run the validator and log errors if necessary and save the entry to the database
        IngredientValidator validator = new IngredientValidator();

        for (int index = 0; index < ingredientNameList.size(); index++) {
            IngredientVo vo = (IngredientVo) ingredientNameList.get(index);
            vo.setRequestItemStatus(RequestItemStatus.APPROVED);

            try {
                int iIEN = Integer.valueOf(vo.getNdfIngredientIen());

                if (iIEN > maxIEN) {
                    maxIEN = iIEN;
                }
            } catch (NumberFormatException e) {
                LOG.error("Drug Unit VO, IEN Format exception: " + vo.getNdfIngredientIen() + "." + e.getMessage());
            }

            Errors errors = new Errors();
            validator.validate(vo, errors);
            boolean migrateIngredient = true;

            if (errors.hasErrors()) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                List<ValidationError> errorList = errors.getErrors();
                StringBuffer str = new StringBuffer("");

                for (ValidationError e : errorList) {
                    str.append(e.getLocalizedError()).append(" ");
                }

                saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_INGREDIENT_FILE, vo.getNdfIngredientIen(), "", "",
                    vo.getValue(), str.toString()));
                migrateIngredient = false;
            }

            if (migrateIngredient) {

                // If parent IEN exists then populate parent ID.
                if (vo.getPrimaryIngredient() != null) {
                    ComplexIENStore ingredientStore = ingredientMap.get(vo.getPrimaryIngredient().getValue());

                    if (ingredientStore == null) {
                        noPrimaryList.add(vo);
                        migrateIngredient = false;
                    } else {
                        vo.getPrimaryIngredient().setId(ingredientStore.getEplId());
                        vo.getPrimaryIngredient().setIngredientIen(ingredientStore.getNdfIen());
                    }
                }
            }

            if (migrateIngredient) {

                try {
                    if (ingredientMap.get(vo.getValue()) == null) {
                        
                        // create the ingredient
                        vo = ingredientDomainCapability.createWithoutDuplicateCheck(vo, getUser());
                        createItemAuditHistory(EntityType.INGREDIENT, vo.getId());
                        ComplexIENStore ingredientStore = new ComplexIENStore(vo.getId(), vo.getNdfIngredientIen());
                        ingredientMap.put(vo.getValue(), ingredientStore);
                        varVo.setISuccessfullyMigrated(varVo.getISuccessfullyMigrated() + 1);

                        // if there are errors check that the inactivation is not set
                    } else if (vo.getInactivationDate() == null) { // Duplicates that have no inactivation date are errors
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str = "This Ingredient " + vo.getValue() + ACTIVE_DUP;
                        LOG.error(str);
                        saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_INGREDIENT_FILE, "", INACT_DATE, SNULL,
                            vo.getValue(), str));
                    } else { // NDC duplicates with inactivation dates are just counted as duplicates.
                        varVo.setIDuplicatesNotMigrated(varVo.getIDuplicatesNotMigrated() + 1);
                        LOG.error("DUPLICATE: We did Not Migrate Ingredient: " + vo.getValue() + ALREADY_EXISTS);
                    }
                } catch (Exception e) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    StringBuffer str = new StringBuffer();
                    str.append("Uncaught Exception4: ");
                    str.append(e.getMessage());

                    if (e.getCause() != null) {
                        str.append(CAUSE_WAS);
                        str.append(e.getCause().getMessage());
                    }

                    // LOG.debug(str);
                    saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_INGREDIENT_FILE, vo.getNdfIngredientIen(), "",
                        "", vo.getValue(), str.toString()));
                }
            }
        }

        // set the varVo for the return message;
        varVo.setIngredientList(noPrimaryList);
        varVo.setStrLastIEN(String.valueOf(maxIEN));
        varVo.setEndOfFile(migrationResponseVo.isEof());

        return varVo;
    }
    
    /**
     * Migrate the VA DrugIngredient domain
     * @param ingredientList : migrationRepsosne
     * @return A Vo containing information about the success of the migration
     */
    public MigrationVariablesVo migrateDrugIngredient(List<IngredientVo> ingredientList) {
        
        MigrationVariablesVo varVo = new MigrationVariablesVo();
        LOG.debug("size of ingredient list is " + ingredientList.size());

        for (IngredientVo vo : ingredientList) {

            // If parent IEN exists then populate parent ID.
            boolean migrateIngredient = true;

            if (vo.getPrimaryIngredient() != null) {
                String parentVoName = "";

                try {
                    vo.getPrimaryIngredient().getValue();
                    IngredientVo primaryVo = (IngredientVo) vo.getPrimaryIngredient();
                    ComplexIENStore ingredientStore = ingredientMap.get(vo.getPrimaryIngredient().getValue());

                    if (ingredientStore == null) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        StringBuffer str =
                            new StringBuffer("The system Could not Migrate Ingredient ").append(vo.getValue())
                                .append(" due to a problem looking up its parent ").append(primaryVo.getValue()).append(".");
                        saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_INGREDIENT_FILE, vo.getNdfIngredientIen(),
                            "Primary Ingredient", parentVoName, vo.getValue(), str.toString()));
                        migrateIngredient = false;
                    } else {
                        vo.getPrimaryIngredient().setId(ingredientStore.getEplId());
                        vo.getPrimaryIngredient().setIngredientIen(ingredientStore.getNdfIen());
                    }

                } catch (Exception e) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    String str =
                        "Could not Migrate Ingredient " + vo.getValue() + " due to a problem looking up the parent "
                            + parentVoName + "." + " Exception is " + e.getMessage();
                    saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_INGREDIENT_FILE, vo.getNdfIngredientIen(),
                        "PrimaryIngredient", parentVoName, vo.getValue(), str));
                    migrateIngredient = false;
                }
            }

            if (migrateIngredient) {

                try {
                    if (ingredientMap.get(vo.getValue()) == null) {
                        vo = ingredientDomainCapability.createWithoutDuplicateCheck(vo, getUser());
                        createItemAuditHistory(EntityType.INGREDIENT, vo.getId());
                        ComplexIENStore ingredientStore = new ComplexIENStore(vo.getId(), vo.getNdfIngredientIen());
                        ingredientMap.put(vo.getValue(), ingredientStore);
                        varVo.setISuccessfullyMigrated(varVo.getISuccessfullyMigrated() + 1);
                    } else if (vo.getInactivationDate() == null) { // Duplicates that have no inactivation date are errors
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str = "The Ingredient " + vo.getValue() + ACTIVE_DUP;
                        LOG.error(str);
                        saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_INGREDIENT_FILE, "", INACT_DATE, SNULL,
                            vo.getValue(), str));
                    } else { // duplicates with inactivation dates are just counted as duplicates.
                        varVo.setIDuplicatesNotMigrated(varVo.getIDuplicatesNotMigrated() + 1);
                        LOG.error("DUPLICATE: Did Not Migrate Ingredient: " + vo.getValue() + ALREADY_EXISTS);
                    }
                } catch (Exception e) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    StringBuffer sb = new StringBuffer("Uncaught Exception5: ");
                    sb.append(e.getMessage());

                    if (e.getCause() != null) {
                        sb.append(CAUSE_WAS).append(e.getCause().getMessage());
                    }

                    LOG.debug(sb);
                    saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_INGREDIENT_FILE, vo.getNdfIngredientIen(), "",
                        "", vo.getValue(), sb.toString()));
                }
            }
        }

        varVo.setEndOfFile(true);
        LOG.debug("IngredientListMethod: Vo.success is " + varVo.getISuccessfullyMigrated());
        LOG.debug("IngredientListMethod: Vo.duplicates is " + varVo.getIDuplicatesNotMigrated());
        LOG.debug("IngredientListMethod: Vo.error is " + varVo.getIErroredOnMigration());

        return varVo;
    }
    
    /**
     * Migrate the VA Generic domain
     * @param migrationResponseVo : migrationRepsosne
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of Generics to migrate at a time.
     * @return A Vo containing information about the success of the migration
     */
    public MigrationVariablesVo migrateDrugClass(MigrationResponseVo migrationResponseVo, String ien, int size) {
        MigrationVariablesVo varVo = new MigrationVariablesVo();
        int maxIEN = 0;
        
        if (migrationResponseVo == null) {
            varVo.setEndOfFile(true);
            Long lIEN = Long.valueOf(ien);
            lIEN += size;
            varVo.setStrLastIEN(String.valueOf(lIEN));
            String str =
                "An error occured in retrieving the xml for the Drug Class file for IENs " + ien + TO + varVo.getStrLastIEN()
                    + ". Those Drug Classes were not migrated.";
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_CLASS_FILE, ien, "", "", "", str));

            return varVo;
        }

        List<MigrationException> errorExc = migrationResponseVo.getMigrationExceptions();

        for (MigrationException me : errorExc) {
            varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_CLASS_FILE, me));
        }

        //Get the Ingredients from the migration file
        ArrayList<ManagedItemVo> drugClassList = (ArrayList<ManagedItemVo>) migrationResponseVo.getManagedItemsVos();

        // run the validator and log errors if necessary and save the entry to the database
        DrugClassificationValidator validator = new DrugClassificationValidator();

        for (int index = 0; index < drugClassList.size(); index++) {
            DrugClassVo vo = (DrugClassVo) drugClassList.get(index);
            vo.setRequestItemStatus(RequestItemStatus.APPROVED);

            try {
                int iIEN = Integer.valueOf(vo.getDrugClassIen());

                if (iIEN > maxIEN) {
                    maxIEN = iIEN;
                }
            } catch (NumberFormatException e) {
                LOG.error("Ingredient IEN Format exception: " + vo.getDrugClassIen() + "." + e.getMessage());
            }

            Errors errors = new Errors();
            validator.validate(vo, errors);

            if (errors.hasErrors()) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                List<ValidationError> errorList = errors.getErrors();
                String str = "";

                for (ValidationError e : errorList) {
                    str = e.getLocalizedError() + " ";
                }

                saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_CLASS_FILE, vo.getDrugClassIen(), "", "",
                    vo.getCode() + ":" + vo.getClassification(), str));

            } else {

                // load to the database
                boolean migrateDrugClass = true;

                if (vo.getParentDrugClass() != null && StringUtils.isNotEmpty(vo.getParentDrugClass().getCode())) {
                    try {
                        ComplexIENStore drugClassStore =
                            drugClassMap.get(vo.getParentDrugClass().getCode() + vo.getParentDrugClass().getClassification());

                        if (drugClassStore == null) {
                            varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                            String str =
                                "When Migrating Drug Class " + vo.getCode() + ":" + vo.getClassification()
                                    + " the Drug Class Parent " + vo.getParentDrugClass().getCode() + ":"
                                    + vo.getParentDrugClass().getClassification() + " could not be found in the PPS database.";

                            // LOG.error(str);
                            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_CLASS_FILE, vo.getDrugClassIen(),
                                "Parent Class", vo.getParentDrugClass().getCode() + ":"
                                    + vo.getParentDrugClass().getClassification(), vo.getValue(), str));
                            migrateDrugClass = false;
                        } else {
                            vo.getParentDrugClass().setId(drugClassStore.getEplId());
                            vo.getParentDrugClass().setDrugClassIen(drugClassStore.getNdfIen());
                        }

                    } catch (Exception e) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str =
                            "Could not Migrate Drug Class " + vo.getCode() + ":" + vo.getClassification()
                                + " due to a problem looking up the Parent Drug Class " + vo.getParentDrugClass().getCode()
                                + ":" + vo.getParentDrugClass().getClassification() + ".  Exception is " + e.getMessage();
                        saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_CLASS_FILE, vo.getDrugClassIen(),
                            "ParentClass", vo.getParentDrugClass().getCode() + ":"
                                + vo.getParentDrugClass().getClassification(), vo.getValue(), str));
                        migrateDrugClass = false;
                    }
                }

                if (migrateDrugClass) {
                    try {
                        if (drugClassMap.get(vo.getCode() + vo.getClassification()) == null) {
                            vo = drugClassDomainCapability.createWithoutDuplicateCheck(vo, getUser());
                            createItemAuditHistory(EntityType.DRUG_CLASS, vo.getId());
                            varVo.setISuccessfullyMigrated(varVo.getISuccessfullyMigrated() + 1);
                            ComplexIENStore drugClassStore = new ComplexIENStore(vo.getId(), vo.getDrugClassIen());
                            drugClassMap.put(vo.getCode() + vo.getClassification(), drugClassStore);
                        } else if (vo.getInactivationDate() == null) { // Duplicates that have no inactivation date are errors
                            varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                            String str = "The Drug Class " + vo.getCode() + ":" + vo.getClassification() + ACTIVE_DUP;
                            LOG.error(str);
                            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_CLASS_FILE, "", INACT_DATE, SNULL,
                                vo.getValue(), str));
                        } else { // duplicates with inactivation dates are just counted as duplicates.
                            varVo.setIDuplicatesNotMigrated(varVo.getIDuplicatesNotMigrated() + 1);
                            LOG.error("DUPLICATE: Did Not Migrate Drug Class: " + vo.getCode() + ":" + vo.getClassification()
                                + ALREADY_EXISTS);
                        }
                    } catch (Exception e) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        StringBuffer sb = new StringBuffer("Uncaught Exception6: ");
                        sb.append(e.getMessage());

                        if (e.getCause() != null) {
                            sb.append(CAUSE_WAS).append(e.getCause().getMessage());
                        }

                        saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DRUG_CLASS_FILE, vo.getDrugClassIen(), "", "",
                            vo.getCode() + ":" + vo.getClassification(), sb.toString()));
                    }
                }
            }
        }

        // set the varVo for the migrated Drug Classes;
        varVo.setStrLastIEN(String.valueOf(maxIEN));
        varVo.setEndOfFile(migrationResponseVo.isEof());

        return varVo;
    }
    
    /**
     * Migrate the VA DosageFrom domain
     * @param migrationResponseVo : migrationRepsosne
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of DosageForms to migrate at a time.
     * @return A Vo containing information about the success of the migration
     */
    public MigrationVariablesVo migrateDosageForm(MigrationResponseVo migrationResponseVo, String ien, int size) {
        MigrationVariablesVo varVo = new MigrationVariablesVo();
        int maxIEN = 0;
        
        if (migrationResponseVo == null) {
            varVo.setEndOfFile(true);
            Long lIEN = Long.valueOf(ien);
            lIEN += size;
            varVo.setStrLastIEN(String.valueOf(lIEN));
            String str =
                    "An error occured in retrieving the xml for the Dosage Form file for IENs " + ien + TO
                        + varVo.getStrLastIEN() + ". Those Dosage Forms were not migrated.";
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DOSAGE_FORM_FILE, ien, "", "", "", str));

            return varVo;
        }

        List<MigrationException> errorExc = migrationResponseVo.getMigrationExceptions();

        for (MigrationException me : errorExc) {
            varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DOSAGE_FORM_FILE, me));
        }

        //Get the Dosage Forms from the migration file
        ArrayList<ManagedItemVo> dosageFormList = (ArrayList<ManagedItemVo>) migrationResponseVo.getManagedItemsVos();

        // run the validator and log errors if necessary and save the entry to the database
        DosageFormValidator validator = new DosageFormValidator();

        for (int index = 0; index < dosageFormList.size(); index++) {
            DosageFormVo vo = (DosageFormVo) dosageFormList.get(index);
            vo.setRequestItemStatus(RequestItemStatus.APPROVED);

            try {
                int iIEN = Integer.valueOf(vo.getDosageFormIen());

                if (iIEN > maxIEN) {
                    maxIEN = iIEN;
                }
            } catch (NumberFormatException e) {
                LOG.error("DosageFormVo, IEN Format exception: " + vo.getDosageFormIen() + "." + e.getMessage());
            }

            Errors errors = new Errors();
            validator.validate(vo, errors);
            boolean migrateDosageForm = true;

            if (errors.hasErrors()) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                List<ValidationError> errorList = errors.getErrors();
                String str = "";

                for (ValidationError e : errorList) {
                    str = e.getLocalizedError() + " ";
                }

                saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DOSAGE_FORM_FILE, vo.getDosageFormIen(), "", "",
                    vo.getDosageFormName(), str));
                migrateDosageForm = false;
            }

            if (migrateDosageForm) {

                // Need to populate the drug unit ID for the units multiple.
                String strDrugUnit = "";

                try {
                    for (DosageFormUnitVo dfuVo : vo.getDfUnits()) {
                        strDrugUnit = dfuVo.getDrugUnit().getValue();
                        ComplexIENStore drugUnitStore = drugUnitMap.get(strDrugUnit);

                        if (drugUnitStore == null) {
                            varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                            String str = "Drug Unit does not exist in the PPS-N EPL.";
                            saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DOSAGE_FORM_FILE, vo.getDosageFormIen(),
                                "Drug Unit", strDrugUnit, vo.getDosageFormName(), str));
                            migrateDosageForm = false;
                        } else {
                            dfuVo.getDrugUnit().setId(drugUnitStore.getEplId());
                            dfuVo.getDrugUnit().setDrugUnitIen(drugUnitStore.getNdfIen());
                        }
                    }
                } catch (Exception e) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    String str = "Exception Retrieving drug unit in the dosage form.  Exception is " + e.getMessage();
                    saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DOSAGE_FORM_FILE, vo.getDosageFormIen(),
                        "DrugUnit", strDrugUnit, vo.getDosageFormName(), str));
                    migrateDosageForm = false;
                }

            }

            if (migrateDosageForm) {

                try {
                    if (dosageFormMap.get(vo.getDosageFormName()) == null) {
                        vo = dosageFormDomainCapability.createWithoutDuplicateCheck(vo, getUser());
                        createItemAuditHistory(EntityType.DOSAGE_FORM, vo.getId());
                        varVo.setISuccessfullyMigrated(varVo.getISuccessfullyMigrated() + 1);
                        ComplexIENStore dosageFormStore = new ComplexIENStore(vo.getId(), vo.getDosageFormIen());
                        dosageFormMap.put(vo.getDosageFormName(), dosageFormStore);
                    } else if (vo.getInactivationDate() == null) { // Duplicates that have no inactivation date are errors
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str = "The Dosage Form " + vo.getDosageFormName() + ACTIVE_DUP;
                        LOG.error(str);
                        saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DOSAGE_FORM_FILE, "", INACT_DATE, SNULL,
                            vo.getDosageFormName(), str));
                    } else { // duplicates with inactivation dates are just counted as duplicates.
                        varVo.setIDuplicatesNotMigrated(varVo.getIDuplicatesNotMigrated() + 1);
                        LOG.error("DUPLICATE: Did Not Migrate the Dosage Form: " + vo.getDosageFormName() + ALREADY_EXISTS);
                    }
                } catch (Exception e) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    StringBuffer sb = new StringBuffer("Uncaught Exception7: ");
                    sb.append(e.getMessage());

                    if (e.getCause() != null) {
                        sb.append(CAUSE_WAS).append(e.getCause().getMessage());
                    }

                    saveMigrationErrorMessage(createErrorVo(PPSConstants.VA_DOSAGE_FORM_FILE, vo.getDosageFormIen(), "", "",
                        vo.getDosageFormName(), sb.toString()));
                }
            }

        }

        // set the varVo for the return message;
        varVo.setStrLastIEN(String.valueOf(maxIEN));
        varVo.setEndOfFile(migrationResponseVo.isEof());

        return varVo;
    }
    
    /**
     * setSpecialHandlingDomainCapability.
     * @param specialHandlingDomainCapability property
     */
    public void setSpecialHandlingDomainCapability(SpecialHandlingDomainCapability specialHandlingDomainCapability) {
        this.specialHandlingDomainCapability = specialHandlingDomainCapability;
    }
    
    /**
     * setIntendedUseDomainCapability.
     * @param intendedUseDomainCapability property
     */
    public void setIntendedUseDomainCapability(IntendedUseDomainCapability intendedUseDomainCapability) {
        this.intendedUseDomainCapability = intendedUseDomainCapability;
    }
    
    /**
     * setOrderUnitDomainCapability.
     * @param orderUnitDomainCapability property
     */
    public void setOrderUnitDomainCapability(OrderUnitDomainCapability orderUnitDomainCapability) {
        this.orderUnitDomainCapability = orderUnitDomainCapability;
    }
    
    /**
     * setDrugUnitDomainCapability
     * @param drugUnitDomainCapability property
     */
    public void setDrugUnitDomainCapability(DrugUnitDomainCapability drugUnitDomainCapability) {
        this.drugUnitDomainCapability = drugUnitDomainCapability;
    }
    
    /**
     *  setGenericNameDomainCapability
     * @param genericNameDomainCapability property
     */
    public void setGenericNameDomainCapability(GenericNameDomainCapability genericNameDomainCapability) {
        this.genericNameDomainCapability = genericNameDomainCapability;
    }
    
    /**
     * setDispenseUnitDomainCapability
     * @param dispenseUnitDomainCapability property
     */
    public void setDispenseUnitDomainCapability(DispenseUnitDomainCapability dispenseUnitDomainCapability) {
        this.dispenseUnitDomainCapability = dispenseUnitDomainCapability;
    }
    
    /**
     * setCsFedScheduleDomainCapability.
     * @param csFedScheduleDomainCapability property
     */
    public void setCsFedScheduleDomainCapability(CsFedScheduleDomainCapability csFedScheduleDomainCapability) {
        this.csFedScheduleDomainCapability = csFedScheduleDomainCapability;
    }
    
    /**
     * setResetNationalDatabaseDomainCapability.
     * @param resetNationalDatabaseDomainCapability property
     */
    public void setResetNationalDatabaseDomainCapability(ResetNationalDatabaseDomainCapability
        resetNationalDatabaseDomainCapability) {
        this.resetNationalDatabaseDomainCapability = resetNationalDatabaseDomainCapability;
    }
    
    /**
     * setProductDomainCapability
     * @param productDomainCapability property
     */
    public void setProductDomainCapability(ProductDomainCapability productDomainCapability) {
        this.productDomainCapability = productDomainCapability;
    }
    
    /**
     * setOrderableItemDomainCapability
     * @param orderableItemDomainCapability property
     */
    public void setOrderableItemDomainCapability(OrderableItemDomainCapability orderableItemDomainCapability) {
        this.orderableItemDomainCapability = orderableItemDomainCapability;
    }
    
    /**
     * setIngredientDomainCapability
     * @param ingredientDomainCapability property
     */
    public void setIngredientDomainCapability(IngredientDomainCapability ingredientDomainCapability) {
        this.ingredientDomainCapability = ingredientDomainCapability;
    }

    /**
     * setPackageTypeDomainCapability.
     * @param packageTypeDomainCapability property
     */
    public void setPackageTypeDomainCapability(PackageTypeDomainCapability packageTypeDomainCapability) {
        this.packageTypeDomainCapability = packageTypeDomainCapability;
    }
    
    /**
     * setManufacturerDomainCapability.
     * @param manufacturerDomainCapability property
     */
    public void setManufacturerDomainCapability(ManufacturerDomainCapability manufacturerDomainCapability) {
        this.manufacturerDomainCapability = manufacturerDomainCapability;
    }
    
    /**
     * setDosageFormDomainCapability
     * @param dosageFormDomainCapability property
     */
    public void setDosageFormDomainCapability(DosageFormDomainCapability dosageFormDomainCapability) {
        this.dosageFormDomainCapability = dosageFormDomainCapability;
    }
    
    /**
     * setStandardMedRouteDomainCapability.
     * @param standardMedRouteDomainCapability property
     */
    public void setStandardMedRouteDomainCapability(StandardMedRouteDomainCapability standardMedRouteDomainCapability) {
        this.standardMedRouteDomainCapability = standardMedRouteDomainCapability;
    }
    
    /**
     * setDrugClassDomainCapability
     * @param drugClassDomainCapability property
     */
    public void setDrugClassDomainCapability(DrugClassDomainCapability drugClassDomainCapability) {
        this.drugClassDomainCapability = drugClassDomainCapability;
    }
    
    /**
     * setNdcDomainCapability.
     * @param ndcDomainCapability property
     */
    public void setNdcDomainCapability(NdcDomainCapability ndcDomainCapability) {
        this.ndcDomainCapability = ndcDomainCapability;
    }
    
    /**
     * setItemAuditHistoryDomainCapability.
     * @param itemAuditHistoryDomainCapability property
     */
    public void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability) {
        this.itemAuditHistoryDomainCapability = itemAuditHistoryDomainCapability;
    }
    
    /**
     * setMigrationErrorDomainCapability.
     * @param migrationErrorDomainCapability property
     */
    public void setMigrationErrorDomainCapability(MigrationErrorDomainCapability migrationErrorDomainCapability) {
        this.migrationErrorDomainCapability = migrationErrorDomainCapability;
    }
    
    /**
     * getSpecHandMap
     * @return specHandMap
     */
    public Map<String, String> getSpecHandMap() {
        return specHandMap;
    }
    
    /**
     * getIntendedUseMap
     * @return intendedUseMap
     */
    public Map<String, String> getIntendedUseMap() {
        return intendedUseMap;
    }

    /**
     * getOrderUnitMap
     * @return orderUnitMap
     */
    public Map<String, String> getOrderUnitMap() {
        return orderUnitMap;
    }
    
    /**
     * getCsFedSchedMap
     * @return csFedSchedMap
     */
    public Map<String, String> getCsFedSchedMap() {
        return csFedSchedMap;
    }

    /**
     * getStandardMedRouteMap
     * @return standardMedRouteMap
     */
    public Map<String, String> getStandardMedRouteMap() {
        return standardMedRouteMap;
    }
    
    /**
     * getNdcMap
     * @return ndcMap
     */
    public Map<String, String> getNdcMap() {
        return ndcMap;
    }

    /**
     * getDrugUnitMap
     * @return getDrugUnitMap
     */
    public Map<String, ComplexIENStore> getDrugUnitMap() {
        return drugUnitMap;
    }
    
    /**
     * getDispenseUnitMap
     * @return dispenseUnitMap
     */
    public Map<String, ComplexIENStore> getDispenseUnitMap() {
        return dispenseUnitMap;
    }
    
    /**
     * getIngredientMap
     * @return ingredientMap
     */
    public Map<String, ComplexIENStore> getIngredientMap() {
        return ingredientMap;
    }
    
    /**
     * getDrugClassMap
     * @return drugClassMap
     */
    public Map<String, ComplexIENStore> getDrugClassMap() {
        return drugClassMap;
    }

    /**
     * getManufacturerMap
     * @return manufacturerMap
     */
    public Map<String, ComplexIENStore> getManufacturerMap() {
        return manufacturerMap;
    }
    
    /**
     * getPackageTypeMap
     * @return packageTypeMap
     */
    public Map<String, ComplexIENStore> getPackageTypeMap() {
        return packageTypeMap;
    }
    
    /**
     * getDosageFormMap
     * @return dosageFormMap
     */
    public Map<String, ComplexIENStore> getDosageFormMap() {
        return dosageFormMap;
    }
 
    /**
     * getProductMap
     * @return productMap
     */
    public Map<String, ComplexStore> getProductMap() {
        return productMap;
    }

    /**
     * getOrderableMap
     * @return orderableMap
     */
    public Map<String, ComplexStore> getOrderableMap() {
        return orderableMap;
    }
    
    /**
     * getGenericMap
     * @return genericMap
     */
    public Map<String, ComplexGenericStore> getGenericMap() {
        return genericMap;
    }
  
}
