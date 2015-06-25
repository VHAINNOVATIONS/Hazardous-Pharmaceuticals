/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FdbDosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.FdbDrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.FdbDrugIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.FdbDrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FdbGenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.FdbProductVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationErrorVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationProductGroupVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationResponseVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.common.vo.SubCategory;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.OrderableItemValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ProductValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.domain.common.capability.CsFedScheduleDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DispenseUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DosageFormDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugClassDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugTextDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDosageFormDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugClassDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugIngredientDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbGenericNameDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbNdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbProductDomainCapability;
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
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.fss.outbound.capability.FssInterfaceCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.MigrationRequestCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.MigrationSynchFileCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaFileSynchCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.MigrationCapability;
import gov.va.med.pharmacy.peps.service.common.utility.ComplexGenericStore;
import gov.va.med.pharmacy.peps.service.common.utility.ComplexIENStore;
import gov.va.med.pharmacy.peps.service.common.utility.ComplexStore;
import gov.va.med.pharmacy.peps.service.common.utility.MigrationDomainsUtility;
import gov.va.med.pharmacy.peps.service.common.utility.MigrationItemsUtility;


/**
 * MigrationCapabilityImpl is the implementation class that conrols the migration effort
 *
 */
public class MigrationCapabilityImpl implements MigrationCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MigrationCapabilityImpl.class);
    private static final String TO = " to ";
    private static final String MIGRATION = "Migration";
    private static final String ACTIVE_DUP = " is an Active Duplicate.";
    private static final String INACT_DATE = "InactivationDate";
    private static final String SNULL = "null";
    private static final String ALREADY_EXISTS = " because it already exists in the database.";
    private static final String CAUSE_WAS = " The cause was ";
    private static final String SIZE_IS = " Size is ";
    private static final String DOSEAGE_FORM = "DosageForm";
    private static final String NOT_FOUND = " could not be found in the PPS database.";
    private static final String DRUG_TEXT = "Drug Text";
    private static final String DEA_2 = "2-Schedule 2 Item";
    private static final String DEA_3 = "3-Schedule 3 Item";
        

    private MigrationRequestCapability migrationRequestCapablity;
    private ManagedItemCapability managedItemCapability;
    private DrugReferenceCapability drugReferenceCapability;
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private ProductDomainCapability productDomainCapability;
    private MigrationSynchFileCapability migrationSynchFileCapability;
    private DrugTextDomainCapability drugTextDomainCapability;
    private FdbProductDomainCapability fdbProductDomainCapability;
    private FdbDrugClassDomainCapability fdbDrugClassDomainCapability;
    private FdbDrugUnitDomainCapability fdbDrugUnitDomainCapability;
    private FdbDosageFormDomainCapability fdbDosageFormDomainCapability;
    private FdbDrugIngredientDomainCapability fdbDrugIngredientDomainCapability;
    private FdbGenericNameDomainCapability fdbGenericNameDomainCapability;
    private VistaFileSynchCapability vistaFileSynchCapability;

   
    
    private MigrationDomainsUtility domainsUtility = new MigrationDomainsUtility();
    private MigrationItemsUtility itemsUtility = new MigrationItemsUtility();

    
    /**
     * OIValues holds multiple data for the hashmaps
     *
     */
    public class OIValues {

        private OrderableItemVo oiVo;
        private boolean migrateOI;
        private Errors errors;
        private MigrationVariablesVo varVo;

        /**
         * default constructor
         * @param oi EPL_ID
         * @param migrate The ProductType ListDataField
         * @param e The IEN
         * @param var DosageFormVo
         */
        public OIValues(OrderableItemVo oi, boolean migrate, Errors e, MigrationVariablesVo var) {
            oiVo = oi;
            migrateOI = migrate;
            errors = e;
            varVo = var;
        }
    };
    
    /**
     * ProductValues holds multiple data for the hashmaps
     *
     */
    public class ProductValues {

        private ProductVo productVo;
        private boolean migrateProduct;
        private MigrationVariablesVo varVo;
        private boolean sendSynchMessage;

        /**
         * default constructor
         * @param vo EPL_ID
         * @param migrate The ProductType ListDataField
         * @param var DosageFormVo
         * @param sendSynch The synchronization sending value
         */
        public ProductValues(ProductVo vo, boolean migrate, MigrationVariablesVo var, boolean sendSynch) {
            productVo = vo;
            migrateProduct = migrate;
            varVo = var;
            sendSynchMessage = sendSynch;
        }
    };
    
    
    /** 
     * default constructor
     */
    public MigrationCapabilityImpl() {

    }

   
    /**
     * clear out the hashmaps to save memory after migration
     */
    public void clear() {
        domainsUtility.clear();
    }

   

    /**
     * init() method used to populate hasmaps.  Must be called before other methods are called
     */
    public void init() {
        domainsUtility.init();
        itemsUtility.setMigrationDomainsUtility(domainsUtility);
    }

    /**
     * Pulls the error messages from the database.  Cap any particular file at 500;
     * @param file File
     * @return A list of MigrationErrorVos
     */
    public List<MigrationErrorVo> retrieveMigrationErrorMessages(String file) {
        return domainsUtility.retrieveMigrationErrorMessages(file);
    }

    /**
     * retrieveMigrationErrorMessage
     * @return List of MigrationErrorVos
     */
    public List<MigrationErrorVo> retrieveMigrationErrorMessages() {
        return domainsUtility.retrieveMigrationErrorMessages();
    }



    /**
     * Migrate the Drug Units domain
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of drug units to migrate at a time.
     * @param type : 0 for Inactive, 1 for Active
     * @return A Vo containing information about the success of the migration
     */
    public MigrationVariablesVo migrateDrugUnits(String ien, int size, int type) {
        LOG.error("Migrate Drug Units IEN = " + ien + " Drug Units Size is " + size + " Drug Units type is " + type);
        
        //Call the migrationRequest Capability to perform the migration
        MigrationResponseVo migrationResponseVo =
                migrationRequestCapablity.migrationRequest(PPSConstants.VA_DRUG_UNIT_FILE, ien, size, type);

        return domainsUtility.migrateDrugUnits(migrationResponseVo, ien, size);
    }

    /**
     * Migrate the Dispense Units domain
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of Dispense units to migrate at a time.
     * @param type : 0 for Inactive 1 for Active
     * @return last IEN from the migrated file
     */
    public MigrationVariablesVo migrateDispenseUnits(String ien, int size, int type) {
        LOG.debug("Migrate Dispense Units IEN = " + ien + SIZE_IS + size);

        //Call the migrationRequest Capability to perform the migration
        MigrationResponseVo migrationResponseVo =
                migrationRequestCapablity.migrationRequest(PPSConstants.VA_DISPENSE_UNIT_FILE, ien, size, type);

        return domainsUtility.migrateDispenseUnits(migrationResponseVo, ien, size);
    }

    /**
     * Migrate the VA Generic domain
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of VA Generic ANames to migrate at a time.
     * @param type : 0 is is inactive, 1 is active
     * @return last IEN from the migrated file
     */
    public MigrationVariablesVo migrateVAGeneric(String ien, int size, int type) {
        LOG.debug("Migrate VA Generic Units: IEN = " + ien + SIZE_IS + size);

        //Call the migrationRequest Capability to perform the migration
        MigrationResponseVo migrationResponseVo =
            migrationRequestCapablity.migrationRequest(PPSConstants.VA_GENERIC_NAME_FILE, ien, size, type);

        return domainsUtility.migrateVAGeneric(migrationResponseVo, ien, size);
       
    }

    /**
     * Migrate the Drug Ingredient domain
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of drug ingredients to migrate at a time.
     * @param type : 1 for active, 0 for inactive
     * @return last IEN from the migrated file
     */
    public MigrationVariablesVo migrateDrugIngredient(String ien, int size, int type) {
        LOG.debug("Migrate Drug Ingredient: IEN = " + ien + SIZE_IS + size);
        
        //Call the migrationRequest Capability to perform the migration
        MigrationResponseVo migrationResponseVo =
                migrationRequestCapablity.migrationRequest(PPSConstants.VA_DRUG_INGREDIENT_FILE, ien, size, type);

        return domainsUtility.migrateDrugIngredient(migrationResponseVo, ien, size);
        
     
    }

    /**
     * Migrate the Drug Ingredient domain
     * @param ingredientList : The list of ingredients to migrate.
     * @return MigrationVariableVo
     */
    public MigrationVariablesVo migrateDrugIngredient(List<IngredientVo> ingredientList) {

        return domainsUtility.migrateDrugIngredient(ingredientList);
    }

    /**
     * Migrate the Drug Class domain
     * 
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of drug classes to migrate at a time.
     * @param type : 1 is for Active ingredients, 0 is for inactive ingredients
     * @return last IEN from the migrated file
     */
    public MigrationVariablesVo migrateDrugClass(String ien, int size, int type) {
        LOG.debug("Migrate DrugClass: IEN = " + ien + SIZE_IS + size);
        
        //Call the migrationRequest Capability to perform the migration
        MigrationResponseVo migrationResponseVo =
                migrationRequestCapablity.migrationRequest(PPSConstants.VA_DRUG_CLASS_FILE, ien, size, type);

        return domainsUtility.migrateDrugClass(migrationResponseVo, ien, size);
        
    }

    /**
     * Migrate the Dosage Form domain
     * @param ien : Starting IEN for the migration effort
     * @param size : Number of dosage forms to migrate at a time.
     * @param type : 1 for Active and 0 for inactive
     * @return last IEN from the migrated file
     */
    public MigrationVariablesVo migrateDosageForm(String ien, int size, int type) {
        

        //Call the migrationRequest Capability to perform the migration
        MigrationResponseVo migrationResponseVo =
            migrationRequestCapablity.migrationRequest(PPSConstants.VA_DOSAGE_FORM_FILE, ien, size, type);

        return domainsUtility.migrateDosageForm(migrationResponseVo, ien, size);
    }

    /**
     * retrieveProductsFromVista
     * @param ien : The starting IEN
     * @param size : The number of records to rerieve
     * @param type : 1 for Active and 0 for Inactive
     * @return MigrationProductGroupVo
     */
    public MigrationProductGroupVo retriveProductsFromVista(String ien, int size, int type) {

        MigrationProductGroupVo groupVo = new MigrationProductGroupVo();

        //Call the migrationRequest Capability to perform the migration
        MigrationResponseVo migrationResponseVo =
            migrationRequestCapablity.migrationRequest(PPSConstants.VA_PRODUCT_FILE, ien, size, type);

        if (migrationResponseVo == null) {
            Long lIEN = Long.valueOf(ien);
            lIEN += size;
            groupVo.setLastIEN(String.valueOf(lIEN));
            groupVo.setProductList(new ArrayList<ProductVo>(0));
            groupVo.setEndOfFile(true);
            String str =
                    "An error occured in retrieving the xml for the PRODUCT file for IENs " + ien + TO + groupVo.getLastIEN()
                        + ". Those Products were not migrated.";
            domainsUtility.saveMigrationErrorMessage(
                domainsUtility.createErrorVo(PPSConstants.VA_PRODUCT_FILE, ien, "", "", "", str));

            return groupVo;
        }

        ArrayList<ManagedItemVo> managedItemList = (ArrayList<ManagedItemVo>) migrationResponseVo.getManagedItemsVos();
        ArrayList<ProductVo> productList = new ArrayList<ProductVo>(managedItemList.size());
        long maxIEN = 0;

        for (int index = 0; index < managedItemList.size(); index++) {
            ProductVo vo = (ProductVo) managedItemList.get(index);

            try {
                long iIEN = vo.getNdfProductIen().longValue();

                if (iIEN > maxIEN) {
                    maxIEN = iIEN;
                }
            } catch (NumberFormatException e) {
                LOG.error("DProduct Vo, IEN Format exception: " + vo.getNdfProductIen() + "." + e.getMessage());
            }

            productList.add(vo);
        }

        List<MigrationException> errorExc = migrationResponseVo.getMigrationExceptions();

        for (MigrationException me : errorExc) {

            try {
                long iIEN = Long.valueOf(me.getIen()).longValue();

                if (iIEN > maxIEN) {
                    maxIEN = iIEN;
                }
            } catch (NumberFormatException e) {
                LOG.error("ProductV Vo, IEN Format exception: " + me.getmIen() + "." + e.getMessage());
            }

            domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(PPSConstants.VA_PRODUCT_FILE, me));
            groupVo.setNumberErroredOnRetrieval(groupVo.getNumberErroredOnRetrieval() + 1);
        }

        groupVo.setLastIEN(String.valueOf(maxIEN));
        groupVo.setEndOfFile(migrationResponseVo.isEof());
        groupVo.setProductList(productList);

        return groupVo;

    }

    /**
     * Migrate the Orderable Items
     * @param inputVo : The oi to migrate
     * @return The MigrationVariablesVo containing the migration results
     * 
     */
    public MigrationVariablesVo migrateOrderabeItems(OrderableItemVo inputVo) {

        OrderableItemVo vo = inputVo;
        MigrationVariablesVo varVo = new MigrationVariablesVo();

        // run the validator and log errors if necessary and save the entry to the database
        OrderableItemValidator validator = new OrderableItemValidator();

        boolean migrateOI = true;
        Errors errors = new Errors();
        validator.validate(vo, domainsUtility.getUser(), Environment.NATIONAL, errors);

        if (errors.hasErrors()) {
            varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
            List<ValidationError> errorList = errors.getErrors();
            String str = "Error Validating Orderable Item " + vo.getOiName() + " ";

            for (ValidationError e : errorList) {
                str = e.getLocalizedError() + " ";
            }

            domainsUtility.saveMigrationErrorMessage(
                domainsUtility.createErrorVo(PPSConstants.VA_ORDERABLE_ITEM_FILE, "", "", "", vo.getOiName(), str));
            migrateOI = false;
        }

        OIValues oiValues = new OIValues(vo, migrateOI, errors, varVo);
        oiValues = oiMigratorHelperMethod(oiValues);
        vo = oiValues.oiVo;
        migrateOI = oiValues.migrateOI;
        errors = oiValues.errors;
        varVo = oiValues.varVo;
        

        if (migrateOI) {
            try {
                ComplexStore type = domainsUtility.getOrderableMap().get(vo.getOiName());

                if (type == null) {
                    vo = orderableItemDomainCapability.create(vo, domainsUtility.getUser());
                    domainsUtility.createItemAuditHistory(EntityType.ORDERABLE_ITEM, vo.getId());
                    varVo.setISuccessfullyMigrated(varVo.getISuccessfullyMigrated() + 1);
                    domainsUtility.getOrderableMap()
                        .put(vo.getOiName(), new ComplexStore(vo.getId(), vo.getCategories(), null, vo.getDosageForm()));
                } else {
                    if (vo.getInactivationDate() == null) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str = "The Orderable Item " + vo.getOiName() + ACTIVE_DUP;
                        LOG.error(str);
                        domainsUtility.saveMigrationErrorMessage(
                            domainsUtility.createErrorVo(PPSConstants.VA_ORDERABLE_ITEM_FILE, "", INACT_DATE, SNULL,
                                vo.getOiName(), str));
                    } else {
                        varVo.setIDuplicatesNotMigrated(varVo.getIDuplicatesNotMigrated() + 1);
                        LOG.error("DUPLICATE: Did Not Migrate the Orderable Itme: " + vo.getOiName() + ALREADY_EXISTS);
                    }
                }
            } catch (Exception e) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                StringBuffer sb = new StringBuffer("Uncaught Exception8: ");
                sb.append(e.getMessage());

                if (e.getCause() != null) {
                    sb.append(CAUSE_WAS).append(e.getCause().getMessage());
                }

                LOG.debug(sb);
                domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                    PPSConstants.VA_ORDERABLE_ITEM_FILE, "", "", "", vo.getOiName(),
                        sb.toString()));
            }
        } // end if migrateOI

        return varVo;
    }

    
    /**
     * Migrate the Orderable Items
     * @param oiValues : The oiValues to migrate
     * @return The OIValues containing the migration results
     * 
     */
    private OIValues oiMigratorHelperMethod(OIValues oiValues) {
        
        OrderableItemVo vo = oiValues.oiVo;
        Errors errors = oiValues.errors;
        MigrationVariablesVo varVo = oiValues.varVo;
        boolean migrateOI = oiValues.migrateOI;
        
        if (migrateOI) {
            try {
                ComplexIENStore dosageFormStore = 
                    domainsUtility.getDosageFormMap().get(vo.getDosageForm().getDosageFormName());

                if (dosageFormStore == null) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    String str =
                            "When Migrating this Orderable Item " + vo.getOiName() + " the associated Dosage Form : "
                                + vo.getDosageForm().getDosageFormName() + NOT_FOUND;
                    LOG.error(str);
                    domainsUtility.saveMigrationErrorMessage(
                        domainsUtility.createErrorVo(PPSConstants.VA_ORDERABLE_ITEM_FILE, "", DOSEAGE_FORM, vo
                            .getDosageForm().getDosageFormName(), vo.getOiName(), str));
                    migrateOI = false;
                } else {
                    vo.getDosageForm().setId(dosageFormStore.getEplId());
                    vo.getDosageForm().setDosageFormIen(dosageFormStore.getNdfIen());
                }
            } catch (Exception e) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                LOG.error("Retrieve DosageForm Exception in migrate Product: " + e.getMessage());
                StackTraceElement[] ste = e.getStackTrace();

                for (StackTraceElement ee : ste) {
                    LOG.error(ee.toString());
                }

                String str =
                        "The SystemCould not Migrate OrderableItem " + vo.getOiName() 
                        + " due to a problem linking to the dosage Form " 
                        + vo.getDosageForm().getDosageFormName() + ". Exception is " + e.getMessage();
                domainsUtility.saveMigrationErrorMessage(
                    domainsUtility.createErrorVo(PPSConstants.VA_ORDERABLE_ITEM_FILE, "", DOSEAGE_FORM, vo
                        .getDosageForm().getDosageFormName(), vo.getOiName(), str));
                migrateOI = false;
            }
        }

        // Set the standardMedRoute if it there.
        if (migrateOI) {
            if (vo.getStandardMedRoute() != null) {
                try {
                    String standardMedRouteId = domainsUtility.getStandardMedRouteMap().get(
                        vo.getStandardMedRoute().getValue());

                    if (standardMedRouteId == null) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str =
                            "When Migrating the Orderable Item " + vo.getOiName() + " the Standard Med Route : "
                                + vo.getStandardMedRoute().getValue() + NOT_FOUND;
                        LOG.error(str);
                        domainsUtility.saveMigrationErrorMessage(
                            domainsUtility.createErrorVo(PPSConstants.VA_ORDERABLE_ITEM_FILE, "", "Standard Medication Route", 
                                vo.getStandardMedRoute().getValue(), vo.getOiName(), str));
                        migrateOI = false;
                    } else {
                        vo.getStandardMedRoute().setId(standardMedRouteId);
                    }
                } catch (Exception e) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    LOG.error("Retrieve StandardMedRoute Exception in migrate OI: " + e.getMessage());
                    StackTraceElement[] ste = e.getStackTrace();

                    for (StackTraceElement ee : ste) {
                        LOG.error(ee.toString());
                    }

                    String str =
                        "We could not Migrate OrderableItem " + vo.getOiName()
                            + " due to a problem linking to the Standard Med Route " + vo.getStandardMedRoute().getValue()
                            + "!  The Exception is " + e.getMessage();
                    domainsUtility.saveMigrationErrorMessage(
                        domainsUtility.createErrorVo(PPSConstants.VA_ORDERABLE_ITEM_FILE, "", "Standard MedRoute", vo
                            .getStandardMedRoute().getValue(), vo.getOiName(), str));
                    migrateOI = false;
                }
            }
        }

        if (migrateOI) {
            String dtName = "";

            try {
                List<DrugTextVo> drugTextList = vo.getNationalDrugTexts();
                boolean bNotFound = false;

                // iterate through the drugTest lists
                for (DrugTextVo dtVo : drugTextList) {
                    dtName = dtVo.getValue();
                    bNotFound = true;
                    List<DrugTextVo> dupDrugTextList = drugTextDomainCapability.retrieveDuplicates(dtVo);

                    // Check for duplicate Drug Text entries in the list.
                    for (int i = 0; i < dupDrugTextList.size(); i++) {
                        if (dtName.equalsIgnoreCase(dupDrugTextList.get(i).getValue())) {
                            dtVo.setId(dupDrugTextList.get(i).getId());
                            bNotFound = false;
                            break;
                        }
                    }
                }
                
                // If not found then log the error.
                if (bNotFound) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    String str =
                        "When Migrating Orderable Item " + vo.getOiName() + ", the Drug Text : " + dtName
                            + NOT_FOUND;
                    LOG.error(str);
                    domainsUtility.saveMigrationErrorMessage(
                        domainsUtility.createErrorVo(PPSConstants.VA_ORDERABLE_ITEM_FILE, "", DRUG_TEXT, dtName,
                            vo.getOiName(), str));
                    migrateOI = false;
                }
            } catch (Exception e) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                LOG.error("Retrieve DrugText Exception in migrate OI: " + e.getMessage());
                StackTraceElement[] ste = e.getStackTrace();

                for (StackTraceElement ee : ste) {
                    LOG.error(ee.toString());
                }

                String str =
                    "Could not Migrate OrderableItem " + vo.getOiName() + ", due to a problem linking to the Drug Text "
                        + dtName + "! Exception is " + e.getMessage();
                domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                    PPSConstants.VA_ORDERABLE_ITEM_FILE, "", DRUG_TEXT, dtName,
                        vo.getOiName(), str));
                migrateOI = false;
            }
        }
        
        OIValues oiValues2 = new OIValues(vo, migrateOI, errors, varVo);
        
        return oiValues2;
        
    }
    
    /**
     * Migrate the Product Items
     * @param voIn The product to migrate
     * @return The MigrationVariablesVo containing the migration results
     * 
     */
    public MigrationVariablesVo migrateProducts(ProductVo voIn) {

        ProductVo vo = voIn;
        MigrationVariablesVo varVo = new MigrationVariablesVo();
        boolean migrateProduct = true;
        boolean sendSyncMessage = false;
        
        ProductValues productValues = new ProductValues(vo, migrateProduct, varVo, sendSyncMessage);
        productValues = productMigratorHelperMethod1(productValues);
        productValues = productMigratorHelperMethod2(productValues);
        productValues = productMigratorHelperMethod3(productValues);
        productValues = productMigratorHelperMethod4(productValues);
        productValues = productMigratorHelperMethod5(productValues);
        productValues = productMigratorHelperMethod6(productValues);
        
        vo = productValues.productVo;
        migrateProduct = productValues.migrateProduct;
        varVo = productValues.varVo;
        sendSyncMessage = productValues.sendSynchMessage;

        if (migrateProduct) {
            try {
                ComplexStore type = domainsUtility.getProductMap().get(vo.getVaProductName());

                if (type == null) {
                    vo = productDomainCapability.createWithoutDuplicateCheck(vo, domainsUtility.getUser());
                    domainsUtility.createItemAuditHistory(EntityType.PRODUCT, vo.getId());
                    domainsUtility.getProductMap().put(vo.getVaProductName(), new ComplexStore(vo.getId(), 
                        vo.getCategories(), vo.getNdfProductIen().toString(), null));

                    try {
                        if (StringUtils.isNotBlank(vo.getGcnSequenceNumber())) {
                            vo.setFdbProductVo(drugReferenceCapability.populateFdbProductFields(vo.getGcnSequenceNumber()));
                            
                            if (vo.getFdbProductVo() != null) { 
                                vo.getFdbProductVo().setProductIdFk(Long.valueOf(vo.getId()));
                                fdbProductDomainCapability.create(vo.getFdbProductVo(), domainsUtility.getUser());
                                addFdbMapping(vo.getFdbProductVo());
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("Could not add the FDB Product part of " + vo.getVaProductName() + " during migration.");
                    }

                    varVo.setISuccessfullyMigrated(varVo.getISuccessfullyMigrated() + 1);
                    
                    // If the boolean is set, send the sync message here.
                    if (sendSyncMessage) {
                        List<Difference> diffList = new ArrayList<Difference>();
                        Difference diff = new Difference(FieldKey.INACTIVATION_DATE, "", "");
                        diffList.add(diff);
                        vistaFileSynchCapability.sendModifiedItemToVista(vo, diffList, domainsUtility.getUser(), true, true);

                    }

                } else {
                    migrateProduct = false;

                    if (vo.getInactivationDate() == null) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str = "This Product " + vo.getVaProductName() + ACTIVE_DUP;
                        LOG.error(str);
                        domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                            PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                            INACT_DATE, SNULL, vo.getVaProductName(), str));
                    } else {
                        varVo.setIDuplicatesNotMigrated(varVo.getIDuplicatesNotMigrated() + 1);
                        LOG.error("DUPLICATE: We Did Not Migrate the Product: " + vo.getVaProductName()
                                  + ALREADY_EXISTS);
                    }
                }
            } catch (Exception e) {
                migrateProduct = false;
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                StringBuffer sb = new StringBuffer("Product ");
                sb.append(vo.getVaProductName()).append(" had an Uncaught Exception which was ").append(e.getMessage())
                    .append(": ").append(e.toString());

                if (e.getCause() != null) {
                    sb.append(CAUSE_WAS).append(e.getCause().getMessage());
                }

                LOG.error(sb);
                Collection<DrugClassGroupVo> dcgvo = vo.getDrugClasses();
                Iterator iterator = dcgvo.iterator();

                if (iterator.hasNext()) {
                    DrugClassGroupVo vo1 = (DrugClassGroupVo) iterator.next();
                    LOG.error("DrugClass id is : " + vo1.getDrugClass().getId());
                }

                Collection<ActiveIngredientVo> aivo = vo.getActiveIngredients();
                Iterator ingIterator = aivo.iterator();

                if (ingIterator.hasNext()) {
                    ActiveIngredientVo vo1 = (ActiveIngredientVo) ingIterator.next();
                    LOG.error("Ingredient id is : " + vo1.getIngredient().getId());

                    if (vo1.getDrugUnit() != null) {
                        LOG.error("Drug Class id is : " + vo1.getDrugUnit().getId());
                    }
                }

                LOG.error("Generic Name Id is : " + vo.getGenericName().getId());
                LOG.error("Orderable Item Id is : " + vo.getOrderableItem().getId());

                for (Category cat : vo.getCategories()) {
                    LOG.error("Category is " + cat);
                }

                for (SubCategory subCat : vo.getSubCategories()) {
                    LOG.error("SubCategory is " + subCat);
                }

                domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                    PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(), "", "",
                    vo.getVaProductName(), sb.toString()));
            }

            if (migrateProduct) {

                if (StringUtils.isNotEmpty(vo.getVaPrintName()) && vo.getDispenseUnit() != null
                    && StringUtils.isNotEmpty(vo.getDispenseUnit().getValue())) {
                    
                    String cmopId = "";
                  
                    try {
                        cmopId = productDomainCapability.getCmopIdForVaPrintName(vo.getVaPrintName(), 
                            vo.getDispenseUnit().getValue());
                    } catch (Exception e) {
                        cmopId = "";
                    }

                    try {
                        if (cmopId == null || cmopId.length() < 1) {
                            productDomainCapability.addCmopIdHistory(vo.getCmopId(), domainsUtility.getUser(), 
                                vo.getVaPrintName(), vo.getDispenseUnit().getValue());
                        }
                    } catch (Exception e) {
                        LOG.error("Error adding cmop id for " + vo.getVaPrintName() + " : " + e.toString());
                    }
                }
            }
        }

        return varVo;
    }
    
    
    /**
     * productMigratorHelperMethod1. Migrate the Products Items
     * @param productValues : The oiValues to migrate
     * @return The ProductValues containing the migration results
     * 
     */
    private ProductValues productMigratorHelperMethod1(ProductValues productValues) {
            
        ProductVo vo = productValues.productVo;
        MigrationVariablesVo varVo = productValues.varVo;
        boolean migrateProduct = productValues.migrateProduct;
        boolean sendSyncMessage = productValues.sendSynchMessage;
            
        // set NationalFormularyFields
        if (vo.getVaDataFields().getDataField(FieldKey.FORMULARY).getValue().size() == 0) {
            if (vo.getNationalFormularyIndicator()) {
                vo.getVaDataFields().getDataField(FieldKey.FORMULARY).addStringSelection("FORMULARY");
            } else {
                vo.getVaDataFields().getDataField(FieldKey.FORMULARY).addStringSelection("NON-FORMULARY");
            }
        }
        
        try {
            ComplexStore type = domainsUtility.getProductMap().get(vo.getVaProductName());

            if (type != null) {
                if (vo.getInactivationDate() == null) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    String str = "The Product " + vo.getVaProductName() + ACTIVE_DUP;
                    LOG.error(str);
                    domainsUtility.saveMigrationErrorMessage(
                        domainsUtility.createErrorVo(PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                            INACT_DATE, SNULL, vo.getVaProductName(), str));
                } else {
                    varVo.setIDuplicatesNotMigrated(varVo.getIDuplicatesNotMigrated() + 1);
                    LOG.error("DUPLICATE: Did Not Migrate the Product: " + vo.getVaProductName() + ALREADY_EXISTS);
                }

                migrateProduct = false;
            }
        } catch (Exception e) {

            LOG.error("An error occureed when determining if a product " + vo.getVaProductName() + ":" + vo.getNdfProductIen()
                + " is a duplicate.");

        }

            
        ProductValues productValues2 = new ProductValues(vo, migrateProduct, varVo, sendSyncMessage);
            
        return productValues2;
    }
    
    
    /**
     * productMigratorHelperMethod1. Migrate the Products Items
     * @param productValues : The oiValues to migrate
     * @return The ProductValues containing the migration results
     * 
     */
    private ProductValues productMigratorHelperMethod2(ProductValues productValues) {
            
        ProductVo vo = productValues.productVo;
        MigrationVariablesVo varVo = productValues.varVo;
        boolean migrateProduct = productValues.migrateProduct;
        boolean sendSyncMessage = productValues.sendSynchMessage;
        boolean createOI = true;
        

        if (migrateProduct) {
            if (vo.getOrderableItem() == null || StringUtils.isEmpty(vo.getOrderableItem().getOiName())) {
                createOI = true;
            } else {

                //try and retrieve the OI, if it isn't there then we need to create one
                ComplexStore type = domainsUtility.getOrderableMap().get(vo.getOrderableItem().getOiName());

                if (type != null) {
                    if (type.getEplId() == null) {
                        LOG.error("Error 1");
                    }

                    vo.getOrderableItem().setId(type.getEplId());
                    vo.setCategories(type.getCategory());
                    vo.getOrderableItem().setDosageForm(type.getDosageForm());
                    createOI = false;
                }
            }
        }

        if (migrateProduct) {

            OrderableItemVo oiVo = null;

            if (createOI) {
                oiVo = (OrderableItemVo) managedItemCapability.retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);
                
                if (vo.getGenericName().getValue().length() > PPSConstants.I40) {
                    oiVo.setVistaOiName(vo.getGenericName().getValue().substring(0, PPSConstants.I40));
                } else {
                    oiVo.setVistaOiName(vo.getGenericName().getValue());
                }
                
                oiVo.setOiName(oiVo.getVistaOiName() + " " + vo.getMigratedDosageFormName());

                ComplexStore type = domainsUtility.getOrderableMap().get(oiVo.getOiName());

                if (type != null) {
                    if (type.getEplId() == null) {
                        LOG.error("Error 2");
                    }

                    oiVo.setId(type.getEplId());
                    oiVo.setDosageForm(type.getDosageForm());
                    vo.setCategories(type.getCategory());
                    vo.setOrderableItem(oiVo);
                    createOI = false;
                }

            }

            if (createOI) {
                oiVo.setCreatedBy(MIGRATION);
                oiVo.setRequestItemStatus(RequestItemStatus.APPROVED);
                oiVo.setItemStatus(ItemStatus.ACTIVE);
                oiVo.setNational();
                oiVo.setLocalUse(false);
                oiVo.setRevisionNumber(1);

                if (vo.getNationalFormularyIndicator()) {
                    oiVo.setNationalFormularyIndicator(true);
                } else {
                    oiVo.setNationalFormularyIndicator(false);
                }

                oiVo.setNonVaMed(false);
                oiVo.setPreviouslyMarkedForLocalUse(false);

                for (DrugClassGroupVo dcgVo : vo.getDrugClasses()) {
                    List<Category> category = new ArrayList<Category>();
                    
                    if (dcgVo.getDrugClass().getCode().startsWith("X")) {
                        category.add(Category.SUPPLY);
                    } else {
                        category.add(Category.MEDICATION);
                    }
                    
                    oiVo.setCategories(category);
                    vo.setCategories(category);

                }

                // This section deals with the dosage form to ensure it exists
                try {
                    DosageFormVo dosageFormVo = new DosageFormVo();
                    dosageFormVo.setDosageFormName(vo.getMigratedDosageFormName());
                    ComplexIENStore dosageFormStore = domainsUtility.getDosageFormMap().get(vo.getMigratedDosageFormName());

                    if (dosageFormStore == null) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str =
                            "The Dosage Form " + vo.getMigratedDosageFormName()
                                + " could not be found in the PPS-N EPL database.";
                        LOG.error(str);
                        domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                            PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                                DOSEAGE_FORM, vo.getMigratedDosageFormName(), vo.getVaProductName(), str));
                        migrateProduct = false;
                    } else {
                        dosageFormVo.setId(dosageFormStore.getEplId());
                        dosageFormVo.setDosageFormIen(dosageFormStore.getNdfIen());
                        oiVo.setDosageForm(dosageFormVo);
                    }
                } catch (Exception e) {
                    String str = "Uncaught Exception while linking to the dosage Form. Exception is " + e.getMessage();
                    domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                        PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                            DOSEAGE_FORM, vo.getMigratedDosageFormName(), vo.getVaProductName(), str));
                    migrateProduct = false;
                }

                if (migrateProduct) {
                    try {
                        oiVo = orderableItemDomainCapability.create(oiVo, domainsUtility.getUser());
                        domainsUtility.createItemAuditHistory(EntityType.ORDERABLE_ITEM, oiVo.getId());
                        vo.setOrderableItem(oiVo);
                        domainsUtility.getOrderableMap().put(oiVo.getOiName(), new ComplexStore(oiVo.getId(), 
                            oiVo.getCategories(), null, oiVo.getDosageForm()));
                    } catch (Exception e) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        LOG.error("Create OIVo Exception in migrate Product: " + e.getMessage());
                        String str =
                            "PPSN could not Migrate Product " + vo.getProductLongName()
                                + " due to a problem creating the Orderable Item: Exception is " + e.getMessage();
                        domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                            PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                                "Orderable Item", oiVo.getOiName(), vo.getVaProductName(), str));
                        migrateProduct = false;
                    }
                }
            }
        } // end if migrateProduct
            
        ProductValues productValues2 = new ProductValues(vo, migrateProduct, varVo, sendSyncMessage);
            
        return productValues2;
    }
        

    /**
     * Migrate the NDC Items
     * @param ndcVoIn : The list of products to migrate
     * @return The MigrationVariablesVo containing the migration results  
     */
    public MigrationVariablesVo migrateNDCs(NdcVo ndcVoIn) {
        
        return itemsUtility.migrateNDCs(ndcVoIn);
    }



    /**
     * This method will add the FDB Mapping to the database if it exists;
     * 
     * @param fdbProductVo fdb product vo
     * @return error messages if any
     */
    public String addFdbMapping(FdbProductVo fdbProductVo) {

        String str = ":";

        if (fdbProductVo.getMappedDrugUnit() != null) {
            try {
                FdbDrugUnitVo vo = new FdbDrugUnitVo();
                vo.setFdbDrugStrengthunits(fdbProductVo.getMappedDrugUnit().toUpperCase(Locale.US));
                fdbDrugUnitDomainCapability.create(vo, domainsUtility.getUser());
            } catch (Exception e) {
                str = e.toString();
            }
        }

        if (fdbProductVo.getMappedDosageForm() != null) {
            try {
                FdbDosageFormVo vo = new FdbDosageFormVo();
                vo.setDrugDosageFormDesc(fdbProductVo.getMappedDosageForm().toUpperCase(Locale.US));
                fdbDosageFormDomainCapability.create(vo, domainsUtility.getUser());
            } catch (Exception e) {
                str = e.toString();
            }

        }

        if (fdbProductVo.getMappedGeneric() != null) {
            try {
                FdbGenericNameVo vo = new FdbGenericNameVo();
                vo.setFdbGenericDrugname(fdbProductVo.getMappedDosageForm().toUpperCase(Locale.US));
                fdbGenericNameDomainCapability.create(vo, domainsUtility.getUser());
            } catch (Exception e) {
                str = e.toString();
            }
        }

        if (fdbProductVo.getMappedIngredients() != null && fdbProductVo.getMappedIngredients().size() > 0) {
            for (String stringLower : fdbProductVo.getMappedIngredients()) {
                try {
                    FdbDrugIngredientVo vo = new FdbDrugIngredientVo();
                    vo.setFdbDrugIngredient(stringLower.toUpperCase(Locale.US));
                    fdbDrugIngredientDomainCapability.create(vo, domainsUtility.getUser());
                } catch (Exception e) {
                    str = e.toString();
                }
            }
        }

        if (fdbProductVo.getMappedDrugClasses() != null && fdbProductVo.getMappedDrugClasses().size() > 0) {
            for (String stringLower : fdbProductVo.getMappedDrugClasses()) {
                try {
                    FdbDrugClassVo vo = new FdbDrugClassVo();
                    vo.setFdbDrugClass(stringLower.toUpperCase(Locale.US));
                    fdbDrugClassDomainCapability.create(vo, domainsUtility.getUser());
                } catch (Exception e) {
                    str = e.toString();
                }
            }
        }

        return str;
    }
    
    /**
     * productMigratorHelperMethod3. Migrate the Products Items
     * @param productValues : The oiValues to migrate
     * @return The ProductValues containing the migration results
     * 
     */
    private ProductValues productMigratorHelperMethod3(ProductValues productValues) {
        ProductVo vo = productValues.productVo;
        MigrationVariablesVo varVo = productValues.varVo;
        boolean migrateProduct = productValues.migrateProduct;
        boolean sendSyncMessage = productValues.sendSynchMessage;
            
        // Set SingleMulti-Source
        if (migrateProduct) {
            SingleMultiSourceProductVo tmpSMSPV = new SingleMultiSourceProductVo();
            tmpSMSPV = vo.getSingleMultiSourceProduct();
            vo = drugReferenceCapability.retrieveMultiSource(vo);
            
            if (tmpSMSPV == null) {
                sendSyncMessage = true;
            } else {
                
                // If not equal send a message.
                if (!tmpSMSPV.getValue().equals(vo.getSingleMultiSourceProduct().getValue())) {
                    sendSyncMessage = true;
                }
            }
        }

        if (migrateProduct) {

            //Ingredients
            Collection<ActiveIngredientVo> holdAiVo = new ArrayList<ActiveIngredientVo>();

            for (ActiveIngredientVo aiVo : vo.getActiveIngredients()) {
                if (aiVo.getIngredient() != null && StringUtils.isNotEmpty(aiVo.getIngredient().getValue())) {
                    ComplexIENStore ingredientStore = domainsUtility.getIngredientMap().get(aiVo.getIngredient().getValue());

                    if (ingredientStore == null) {
                        String str = "Ingredient " + aiVo.getIngredient().getValue() + " was not in map!";
                        LOG.error(str);
                        domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                            PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                            "Drug Ingredient", aiVo.getIngredient().getValue(), vo.getVaProductName(), str));
                        migrateProduct = false;
                    } else {
                        aiVo.getIngredient().setId(
                            domainsUtility.getIngredientMap().get(aiVo.getIngredient().getValue()).getEplId());
                        aiVo.getIngredient().setIngredientIen(
                            domainsUtility.getIngredientMap().get(aiVo.getIngredient().getValue()).getNdfIen());
                    }

                } else {
                    String str = "The Active Ingredient has no ingredient.";
                    LOG.error(str);
                    domainsUtility.saveMigrationErrorMessage(
                        domainsUtility.createErrorVo(PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                            "Ingredient", aiVo.getIngredient().getValue(), vo.getVaProductName(), str));
                    migrateProduct = false;

                }

                if (aiVo.getDrugUnit() != null && StringUtils.isNotEmpty(aiVo.getDrugUnit().getValue())) {
                    ComplexIENStore drugUnitStore = domainsUtility.getDrugUnitMap().get(aiVo.getDrugUnit().getValue());

                    if (drugUnitStore == null) {
                        String str = "Drug Unit " + aiVo.getDrugUnit().getValue() + " was not in map";
                        LOG.error(str);
                        domainsUtility.saveMigrationErrorMessage(
                            domainsUtility.createErrorVo(PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                                "DrugUnit", aiVo.getDrugUnit().getValue(), vo.getVaProductName(), str));
                        migrateProduct = false;
                    } else {
                        aiVo.getDrugUnit().setId(drugUnitStore.getEplId());
                        aiVo.getDrugUnit().setDrugUnitIen(drugUnitStore.getNdfIen());
                    }
                }

                holdAiVo.add(aiVo);
            }

            vo.setActiveIngredients(holdAiVo);
        }

        if (migrateProduct) {

            ComplexGenericStore gCS = domainsUtility.getGenericMap().get(vo.getGenericName().getValue());

            if (gCS == null) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                String str = "Could not Migrate Product becaue the Generic Name was not in the PPS-N EPL.";
                domainsUtility.saveMigrationErrorMessage(
                    domainsUtility.createErrorVo(PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                        "Generic Name", vo.getGenericName().getValue(), vo.getVaProductName(), str));
                migrateProduct = false;
            } else {
                
                vo.getGenericName().setId(gCS.getEplId());
                vo.getGenericName().setGenericIen(gCS.getNdfIen());
            }

        }

        if (migrateProduct) {
            if (vo.getProductUnit() != null) {
                ComplexIENStore drugUnitStore = domainsUtility.getDrugUnitMap().get(vo.getProductUnit().getValue());

                if (drugUnitStore == null) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    String str = "Could not Migrate Product due to a problem with the Drug Unit Field.";
                    domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                        PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                        "Drug Unit", vo.getProductUnit().getValue(), vo.getVaProductName(), str));
                    migrateProduct = false;
                } else {
                    vo.getProductUnit().setId(drugUnitStore.getEplId());
                    vo.getProductUnit().setDrugUnitIen(drugUnitStore.getNdfIen());
                }
            }
        }

        if (migrateProduct) {

            //Dispense Unit
            ComplexIENStore dispenseUnitStore = domainsUtility.getDispenseUnitMap().get(vo.getDispenseUnit().getValue());

            if (dispenseUnitStore != null) {
                vo.getDispenseUnit().setId(dispenseUnitStore.getEplId());
                vo.getDispenseUnit().setDispenseUnitIen(dispenseUnitStore.getNdfIen());
            }
        }

        if (migrateProduct) {

            //Order Unit (Order Unit is optional)
            if (vo.getOrderUnit() != null) {
                String orderUnitId = domainsUtility.getOrderUnitMap().get(vo.getOrderUnit().getAbbrev());

                if (orderUnitId == null) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    String str = "Could not Migrate Product due to a problem with the Order Unit Field.";
                    domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                        PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                        "Order Unit", vo.getOrderUnit().getAbbrev(), vo.getVaProductName(), str));
                    migrateProduct = false;
                } else {
                    vo.getOrderUnit().setId(orderUnitId);
                }
            }
        }

            
        ProductValues productValues2 = new ProductValues(vo, migrateProduct, varVo, sendSyncMessage);
            
        return productValues2;
    }
    
    /**
     * productMigratorHelperMethod4. Migrate the Products Items
     * @param productValues : The oiValues to migrate
     * @return The ProductValues containing the migration results
     * 
     */
    private ProductValues productMigratorHelperMethod4(ProductValues productValues) {
            
        ProductVo vo = productValues.productVo;
        MigrationVariablesVo varVo = productValues.varVo;
        boolean migrateProduct = productValues.migrateProduct;
        boolean sendSyncMessage = productValues.sendSynchMessage;
            
        if (migrateProduct) {
            String dtName = "";

            try {
                List<DrugTextVo> drugTextList = vo.getNationalDrugTexts();
                boolean bNotFound = false;

                for (DrugTextVo dtVo : drugTextList) {
                    dtName = dtVo.getValue();
                    bNotFound = true;
                    List<DrugTextVo> dupDrugTextList = drugTextDomainCapability.retrieveDuplicates(dtVo);

                    for (int i = 0; i < dupDrugTextList.size(); i++) {
                        if (dtName.equalsIgnoreCase(dupDrugTextList.get(i).getValue())) {
                            dtVo.setId(dupDrugTextList.get(i).getId());
                            bNotFound = false;
                            break;
                        }
                    }
                }

                if (bNotFound) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    String str =
                            "When Migrating Product Item " + vo.getVaProductName() + " the Drug Text : " + dtName
                                    + NOT_FOUND;
                    LOG.error(str);
                    domainsUtility.saveMigrationErrorMessage(
                        domainsUtility.createErrorVo(PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                            DRUG_TEXT, dtName, vo.getVaProductName(), str));
                    migrateProduct = false;
                }
            } catch (Exception e) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                LOG.error("Retrieve DrugText Exception in migrate Product: " + e.getMessage());
                String str =
                        "The System could not Migrate Product " + vo.getVaProductName() 
                        + " due to a problem linking to the Drug Text "
                        + dtName + " Exception is " + e.getMessage();
                domainsUtility.saveMigrationErrorMessage(
                    domainsUtility.createErrorVo(PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                        DRUG_TEXT, dtName, vo.getVaProductName(), str));
                migrateProduct = false;
            }

        }

        // Check the synonym field and poplulate the EPL Ids
        if (migrateProduct) {

            for (SynonymVo synonymVo : vo.getSynonyms()) {

                // Check Synonym Intended Use
                boolean intendedUseNotFound = true;
                String id = domainsUtility.getIntendedUseMap().get(synonymVo.getSynonymIntendedUse().getIntendedUse());

                if (StringUtils.isNotEmpty(id)) {
                    synonymVo.getSynonymIntendedUse().setId(id);
                    intendedUseNotFound = false;
                }

                if (intendedUseNotFound) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    String str = "Could not Migrate Product due to a problem with the Synonym Intended Use Field.";
                    domainsUtility.saveMigrationErrorMessage(
                        domainsUtility.createErrorVo(PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                            "SynonymIntendedUSe", synonymVo.getSynonymIntendedUse()
                                .getIntendedUse(), vo.getVaProductName(), str));
                    migrateProduct = false;
                    break;
                }

                // Check Synonym Order Unit
                //Order Unit (Order Unit is optional)
                if (synonymVo.getSynonymOrderUnit() != null) {
                    String orderUnitId = domainsUtility.getOrderUnitMap().get(synonymVo.getSynonymOrderUnit().getAbbrev());

                    if (orderUnitId == null) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str = "Could not Migrate Product due to a problem with the Synonym Order Unit Field.";
                        domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                            PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                            "SynonymOrderUnit", synonymVo.getSynonymOrderUnit().getAbbrev(), vo.getVaProductName(), str));
                        migrateProduct = false;
                        break;
                    } else {
                        synonymVo.getSynonymOrderUnit().setId(orderUnitId);
                    }
                }
            }
        }
        
        ProductValues productValues2 = new ProductValues(vo, migrateProduct, varVo, sendSyncMessage);
            
        return productValues2;
    }
    
    /**
     * productMigratorHelperMethod5. Migrate the Products Items
     * @param productValues : The oiValues to migrate
     * @return The ProductValues containing the migration results
     * 
     */
    private ProductValues productMigratorHelperMethod5(ProductValues productValues) {
            
        ProductVo vo = productValues.productVo;
        MigrationVariablesVo varVo = productValues.varVo;
        boolean migrateProduct = productValues.migrateProduct;
        boolean sendSyncMessage = productValues.sendSynchMessage;
            
        if (migrateProduct) {

            //CSFederalSchedule && SPECIAL Handling are interwoven
            if (vo.getCsFedSchedule() == null || StringUtils.isBlank(vo.getCsFedSchedule().getValue())) {
                CsFedScheduleVo csvo = new CsFedScheduleVo();
                csvo.setValue("0");
                vo.setCsFedSchedule(csvo);
                sendSyncMessage = true;
                
            }

            ArrayList<SpecialHandlingVo> specialHandlings = new ArrayList<SpecialHandlingVo>();
            String str = vo.getCsFedSchedule().getValue();
            String deaStr = "";

            if ("0".equalsIgnoreCase(str)) {
                str = "0 - UNSCHEDULED";
                deaStr = "0-Unscheduled";
            } else if ("1".equalsIgnoreCase(str)) {
                str = "1 - SCHEDULE I";
                deaStr = "1-Schedule 1 Item";
            } else if ("2".equalsIgnoreCase(str)) {
                str = "2 - SCHEDULE II";
                deaStr = DEA_2;

                // implement PEPS2124
                SpecialHandlingVo vo1 = new SpecialHandlingVo();
                vo1.setCode("A");
                vo1.setId(domainsUtility.getSpecHandMap().get("A"));
                specialHandlings.add(vo1);
                SpecialHandlingVo vo2 = new SpecialHandlingVo();
                vo2.setCode("W");
                vo2.setId(domainsUtility.getSpecHandMap().get("W"));
                specialHandlings.add(vo2);
                SpecialHandlingVo vo3 = new SpecialHandlingVo();
                vo3.setCode("F");
                vo3.setId(domainsUtility.getSpecHandMap().get("F"));
                specialHandlings.add(vo3);
            } else if ("2n".equalsIgnoreCase(str)) {
                str = "2n - SCHEDULE II NON-NARCOTICS";
                deaStr = DEA_2;

                // implement PEPS2125
                SpecialHandlingVo vo1 = new SpecialHandlingVo();
                vo1.setCode("C");
                vo1.setId(domainsUtility.getSpecHandMap().get("C"));
                specialHandlings.add(vo1);
                SpecialHandlingVo vo2 = new SpecialHandlingVo();
                vo2.setCode("W");
                vo2.setId(domainsUtility.getSpecHandMap().get("W"));
                specialHandlings.add(vo2);
                SpecialHandlingVo vo3 = new SpecialHandlingVo();
                vo3.setCode("F");
                vo3.setId(domainsUtility.getSpecHandMap().get("F"));
                specialHandlings.add(vo3);
            } else if ("3".equalsIgnoreCase(str)) {
                str = "3 - SCHEDULE III";
                deaStr = DEA_3;

                // PEPS 2126
                SpecialHandlingVo vo1 = new SpecialHandlingVo();
                vo1.setCode("A");
                vo1.setId(domainsUtility.getSpecHandMap().get("A"));
                specialHandlings.add(vo1);

            } else if ("3n".equalsIgnoreCase(str)) {

                // 2127
                str = "3n - SCHEDULE III NON-NARCOTICS";
                deaStr = DEA_3;
                SpecialHandlingVo vo1 = new SpecialHandlingVo();
                vo1.setCode("C");
                vo1.setId(domainsUtility.getSpecHandMap().get("C"));
                specialHandlings.add(vo1);
            } else if ("4".equalsIgnoreCase(str)) {
                str = "4 - SCHEDULE IV";
                deaStr = "4-Schedule 4 Item";
            } else if ("5".equalsIgnoreCase(str)) {
                str = "5 - SCHEDULE V";
                deaStr = "5-Schedule 5 Item";
            }

            String csFedId = domainsUtility.getCsFedSchedMap().get(str);

            if (csFedId == null) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                String str2 =
                        "Could not Migrate Product " + vo.getProductLongName()
                                + " due to a problem with the CsFederalScheudule." + " The CSFederalSchedule of '"
                                + vo.getCsFedSchedule().getValue() + "' was not in the PPS-N EPL.";
                domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                    PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                    "CS Federal Schedule", vo.getCsFedSchedule().getValue(),
                    vo.getVaProductName(), str2));
                migrateProduct = false;
            } else {
                vo.getCsFedSchedule().setId(csFedId);

                if (specialHandlings.size() > 0) {
                    vo.getVaDataFields().getDataField(FieldKey.SPECIAL_HANDLINGS).unselect();
                    vo.getVaDataFields().getDataField(FieldKey.SPECIAL_HANDLINGS).selectValue(specialHandlings);
                }

                // if the dea field was set in the csv spreadsheet call, leave it alone.
                if (vo.getVaDataFields().getDataField(FieldKey.DEA_SCHEDULE).getValue().size() == 0) {
                    vo.getVaDataFields().getDataField(FieldKey.DEA_SCHEDULE).addStringSelection(deaStr);
                }
            }
        }

        ProductValues productValues2 = new ProductValues(vo, migrateProduct, varVo, sendSyncMessage);
            
        return productValues2;
    }
    
    /**
     * productMigratorHelperMethod6. Migrate the Products Items
     * @param productValues : The oiValues to migrate
     * @return The ProductValues containing the migration results
     * 
     */
    private ProductValues productMigratorHelperMethod6(ProductValues productValues) {
            
        ProductVo vo = productValues.productVo;
        MigrationVariablesVo varVo = productValues.varVo;
        boolean migrateProduct = productValues.migrateProduct;
        boolean sendSyncMessage = productValues.sendSynchMessage;
            
        //Drug Class
        if (migrateProduct) {

            //DrugClassesc
            Collection<DrugClassGroupVo> holddcgVo = new ArrayList<DrugClassGroupVo>();

            for (DrugClassGroupVo dcgVo : vo.getDrugClasses()) {
                ComplexIENStore drugClassStore = domainsUtility.getDrugClassMap().get(
                    dcgVo.getDrugClass().getCode() + dcgVo.getDrugClass().getClassification());

                if (drugClassStore == null) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    String str = "Could not Migrate Product because the Drug Class was not in the PPS-N EPL.";
                    domainsUtility.saveMigrationErrorMessage(domainsUtility.createErrorVo(
                        PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(),
                        "DrugClass", dcgVo.getDrugClass().getCode() + ":" + dcgVo.getDrugClass().getClassification(),
                        vo.getVaProductName(), str));
                    migrateProduct = false;
                    break;
                } else {
                    dcgVo.getDrugClass().setId(drugClassStore.getEplId());
                    dcgVo.getDrugClass().setDrugClassIen(drugClassStore.getNdfIen());
                }

                holddcgVo.add(dcgVo);
            }

            vo.setDrugClasses(holddcgVo);
        }

        // set the special handling to supply if this is a supply object. 
        if (migrateProduct) {
            
            if (vo.getCategories().contains(Category.SUPPLY)) {
                List<SpecialHandlingVo> list = vo.getVaDataFields().getDataField(FieldKey.SPECIAL_HANDLINGS).getValue();

                if (list == null) {
                    list = new ArrayList<SpecialHandlingVo>();
                }
            
                SpecialHandlingVo vo1 = new SpecialHandlingVo();
                vo1.setCode("S");
                vo1.setId(domainsUtility.getSpecHandMap().get("S"));
                list.add(vo1);
                vo.getVaDataFields().getDataField(FieldKey.SPECIAL_HANDLINGS).selectValue(list);
            }
            
        }
        
        if (migrateProduct) {

            // run the validator and log errors if necessary and save the entry to the database
            ProductValidator validator = new ProductValidator();
            Errors errors = new Errors();
            validator.validateMigration(vo, domainsUtility.getUser(), Environment.NATIONAL, errors);

            if (errors.hasErrors()) {

                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                List<ValidationError> errorList = errors.getErrors();
                String str = "Validation Error on " + vo.getVaProductName();

                for (ValidationError e : errorList) {
                    str = e.getLocalizedError() + " ";
                }

                domainsUtility.saveMigrationErrorMessage(
                    domainsUtility.createErrorVo(PPSConstants.VA_PRODUCT_FILE, vo.getNdfProductIen().toString(), "", "",
                        vo.getVaProductName(), str));
                migrateProduct = false;
            }
        }
        
        ProductValues productValues2 = new ProductValues(vo, migrateProduct, varVo, sendSyncMessage);
            
        return productValues2;
    }
    

    /** 
     * updateCmopIdGenerator
     */
    public void updateCmopIdGenerator() {
        productDomainCapability.setCmopIdGenerator(domainsUtility.getUser());

    }

    /** 
     * sendStartNDCMessage
     * @return true if send successful
     */
    public boolean sendStartNDCMessage() {
        return migrationSynchFileCapability.sendStartNDCMessage();

    }

    /**
     * sendStopNDCMessage
     * @return true if send successful
     */
    public boolean sendStopNDCMessage() {
        return migrationSynchFileCapability.sendStopNDCMessage();
    }

  
    /**
     * setManagedItemCapability
     * @param managedItemCapability property
     */
    public void setManagedItemCapability(ManagedItemCapability managedItemCapability) {
        itemsUtility.setManagedItemCapability(managedItemCapability);
        this.managedItemCapability = managedItemCapability;
    }

    /**
     * setDrugUnitDomainCapability
     * @param drugUnitDomainCapability property
     */
    public void setDrugUnitDomainCapability(DrugUnitDomainCapability drugUnitDomainCapability) {
        domainsUtility.setDrugUnitDomainCapability(drugUnitDomainCapability);
    }

    /**
     * setDispenseUnitDomainCapability
     * @param dispenseUnitDomainCapability property
     */
    public void setDispenseUnitDomainCapability(DispenseUnitDomainCapability dispenseUnitDomainCapability) {
        domainsUtility.setDispenseUnitDomainCapability(dispenseUnitDomainCapability);
    }

    /**
     * setDosageFormDomainCapability
     * @param dosageFormDomainCapability property
     */
    public void setDosageFormDomainCapability(DosageFormDomainCapability dosageFormDomainCapability) {
        domainsUtility.setDosageFormDomainCapability(dosageFormDomainCapability);
    }

    /**
     * setIngredientDomainCapability
     * @param ingredientDomainCapability property
     */
    public void setIngredientDomainCapability(IngredientDomainCapability ingredientDomainCapability) {
        domainsUtility.setIngredientDomainCapability(ingredientDomainCapability);
    }

    /**
     * setDrugClassDomainCapability
     * @param drugClassDomainCapability property
     */
    public void setDrugClassDomainCapability(DrugClassDomainCapability drugClassDomainCapability) {
        domainsUtility.setDrugClassDomainCapability(drugClassDomainCapability);
    }

    /**
     *  setGenericNameDomainCapability
     * @param genericNameDomainCapability property
     */
    public void setGenericNameDomainCapability(GenericNameDomainCapability genericNameDomainCapability) {
        domainsUtility.setGenericNameDomainCapability(genericNameDomainCapability);
    }

    /**
     * setProductDomainCapability
     * @param productDomainCapability property
     */
    public void setProductDomainCapability(ProductDomainCapability productDomainCapability) {
        domainsUtility.setProductDomainCapability(productDomainCapability);
        itemsUtility.setProductDomainCapability(productDomainCapability);
        this.productDomainCapability = productDomainCapability;
    }

    /**
     * setMigrationRequestCapability
     * @param migReqCap property
     */
    public void setMigrationRequestCapability(MigrationRequestCapability migReqCap) {
        this.migrationRequestCapablity = migReqCap;
    }

    /**
     * setOrderableItemDomainCapability
     * @param orderableItemDomainCapability property
     */
    public void setOrderableItemDomainCapability(OrderableItemDomainCapability orderableItemDomainCapability) {
        domainsUtility.setOrderableItemDomainCapability(orderableItemDomainCapability);
        this.orderableItemDomainCapability = orderableItemDomainCapability;
    }

    /**
     * setNdcDomainCapability.
     * @param ndcDomainCapability property
     */
    public void setNdcDomainCapability(NdcDomainCapability ndcDomainCapability) {
        domainsUtility.setNdcDomainCapability(ndcDomainCapability);
        itemsUtility.setNdcDomainCapability(ndcDomainCapability);
    }

    /**
     * setManufacturerDomainCapability.
     * @param manufacturerDomainCapability property
     */
    public void setManufacturerDomainCapability(ManufacturerDomainCapability manufacturerDomainCapability) {
        domainsUtility.setManufacturerDomainCapability(manufacturerDomainCapability);
        itemsUtility.setManufacturerDomainCapability(manufacturerDomainCapability);
    }

    /**
     * setPackageTypeDomainCapability.
     * @param packageTypeDomainCapability property
     */
    public void setPackageTypeDomainCapability(PackageTypeDomainCapability packageTypeDomainCapability) {
        domainsUtility.setPackageTypeDomainCapability(packageTypeDomainCapability);
        itemsUtility.setPackageTypeDomainCapability(packageTypeDomainCapability);
    }

    /**
     * setOrderUnitDomainCapability.
     * @param orderUnitDomainCapability property
     */
    public void setOrderUnitDomainCapability(OrderUnitDomainCapability orderUnitDomainCapability) {
        domainsUtility.setOrderUnitDomainCapability(orderUnitDomainCapability);
    }

    /**
     * setItemAuditHistoryDomainCapability.
     * @param itemAuditHistoryDomainCapability property
     */
    public void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability) {
        domainsUtility.setItemAuditHistoryDomainCapability(itemAuditHistoryDomainCapability);
    }

    /**
     * setDrugReferenceCapability.
     * @param drugReferenceCapability property
     */
    public void setDrugReferenceCapability(DrugReferenceCapability drugReferenceCapability) {
        itemsUtility.setDrugReferenceCapability(drugReferenceCapability);
        this.drugReferenceCapability = drugReferenceCapability;
    }

    /**
     * setCsFedScheduleDomainCapability.
     * @param csFedScheduleDomainCapability property
     */
    public void setCsFedScheduleDomainCapability(CsFedScheduleDomainCapability csFedScheduleDomainCapability) {
        domainsUtility.setCsFedScheduleDomainCapability(csFedScheduleDomainCapability);
    }

    /**
     * setMigrationSynchFileCapability.
     * @param migrationSynchFileCapability property
     */
    public void setMigrationSynchFileCapability(MigrationSynchFileCapability migrationSynchFileCapability) {
        itemsUtility.setMigrationSynchFileCapability(migrationSynchFileCapability);
        this.migrationSynchFileCapability = migrationSynchFileCapability;
    }

    /**
     * setMigrationErrorDomainCapability.
     * @param migrationErrorDomainCapability property
     */
    public void setMigrationErrorDomainCapability(MigrationErrorDomainCapability migrationErrorDomainCapability) {
        domainsUtility.setMigrationErrorDomainCapability(migrationErrorDomainCapability);
    }

    /**
     * setFdbProductDomainCapability.
     * @param fdbProductDomainCapability property
     */
    public void setFdbProductDomainCapability(FdbProductDomainCapability fdbProductDomainCapability) {
        this.fdbProductDomainCapability = fdbProductDomainCapability;
    }

    /**
     * setFdbNdcDomainCapability.
     * @param fdbNdcDomainCapability property
     */
    public void setFdbNdcDomainCapability(FdbNdcDomainCapability fdbNdcDomainCapability) {
        itemsUtility.setFdbNdcDomainCapability(fdbNdcDomainCapability);
    }

    /**
     * setIntendedUseDomainCapability.
     * @param intendedUseDomainCapability property
     */
    public void setIntendedUseDomainCapability(IntendedUseDomainCapability intendedUseDomainCapability) {
        this.domainsUtility.setIntendedUseDomainCapability(intendedUseDomainCapability);
    }

    /**
     * setDrugTextDomainCapability.
     * @param drugTextDomainCapability property
     */
    public void setDrugTextDomainCapability(DrugTextDomainCapability drugTextDomainCapability) {
        this.drugTextDomainCapability = drugTextDomainCapability;
    }

    /**
     * setSpecialHandlingDomainCapability.
     * @param specialHandlingDomainCapability property
     */
    public void setSpecialHandlingDomainCapability(SpecialHandlingDomainCapability specialHandlingDomainCapability) {
        this.domainsUtility.setSpecialHandlingDomainCapability(specialHandlingDomainCapability);
    }

    /**
     * setStandardMedRouteDomainCapability.
     * @param standardMedRouteDomainCapability property
     */
    public void setStandardMedRouteDomainCapability(StandardMedRouteDomainCapability standardMedRouteDomainCapability) {
        domainsUtility.setStandardMedRouteDomainCapability(standardMedRouteDomainCapability);
    }

    /**
     * setFdbDrugClassDomainCapability.
     * @param fdbDrugClassDomainCapability the FdbDrugClassDomainCapability to set
     */
    public void setFdbDrugClassDomainCapability(FdbDrugClassDomainCapability fdbDrugClassDomainCapability) {
        this.fdbDrugClassDomainCapability = fdbDrugClassDomainCapability;
    }

    /**
     * setFdbDrugUnitDomainCapability.
     * @param fdbDrugUnitDomainCapability the fdbDrugUnitDomainCapability to set
     */
    public void setFdbDrugUnitDomainCapability(FdbDrugUnitDomainCapability fdbDrugUnitDomainCapability) {
        this.fdbDrugUnitDomainCapability = fdbDrugUnitDomainCapability;
    }

    /**
     * setFdbDosageFormDomainCapability.
     * @param fdbDosageFormDomainCapability the fdbDosageFormDomainCapability to set
     */
    public void setFdbDosageFormDomainCapability(FdbDosageFormDomainCapability fdbDosageFormDomainCapability) {
        this.fdbDosageFormDomainCapability = fdbDosageFormDomainCapability;
    }

    /**
     * setFdbDrugIngredientDomainCapability.
     * @param fdbDrugIngredientDomainCapability the fdbDrugIngredientDomainCapability to set
     */
    public void setFdbDrugIngredientDomainCapability(FdbDrugIngredientDomainCapability fdbDrugIngredientDomainCapability) {
        this.fdbDrugIngredientDomainCapability = fdbDrugIngredientDomainCapability;
    }

    /**
     * setFdbGenericNameDomainCapability.
     * @param fdbGenericNameDomainCapability the fdbGenericNameDomainCapability to set
     */
    public void setFdbGenericNameDomainCapability(FdbGenericNameDomainCapability fdbGenericNameDomainCapability) {
        this.fdbGenericNameDomainCapability = fdbGenericNameDomainCapability;
    }

    /**
     * setResetNationalDatabaseDomainCapability.
     * @param resetNationalDatabaseDomainCapability property
     */
    public void setResetNationalDatabaseDomainCapability(ResetNationalDatabaseDomainCapability
        resetNationalDatabaseDomainCapability) {
        domainsUtility.setResetNationalDatabaseDomainCapability(resetNationalDatabaseDomainCapability);
    }

    /**
     * setFssInterfaceCapability.
     * @param fssInterfaceCapability property
     */
    public void setFssInterfaceCapability(FssInterfaceCapability fssInterfaceCapability) {
        itemsUtility.setFssInterfaceCapability(fssInterfaceCapability);
    }

    
    /**
     * getVistaFileSynchCapability
     * @return the vistaFileSynchCapability
     */
    public VistaFileSynchCapability getVistaFileSynchCapability() {
        return vistaFileSynchCapability;
    }

    
    /**
     * setVistaFileSynchCapability
     * @param vistaFileSynchCapability the vistaFileSynchCapability to set
     */
    public void setVistaFileSynchCapability(VistaFileSynchCapability vistaFileSynchCapability) {
        this.vistaFileSynchCapability = vistaFileSynchCapability;
    }


    @Override
    public ProductVo mergeProducts(ProductVo csvVo, ProductVo vistaVo) {
        return itemsUtility.mergeProducts(csvVo, vistaVo);
    }

    /**
     * This method is used to save a Migration Error Message to the database. The domainsUtilty is 
     * used for the actual save.
     * 
     * @param file File
     * @param e Exception
     * @return true if successful
     */
    @Override
    public boolean saveMigrationErrorMessage(String file, MigrationException e) {
        return domainsUtility.saveMigrationErrorMessage(file, e);
    }


    /**
     * This method is used to save a Migration Error Message to the database. The domainsUtilty is 
     * used for the actual save.
     * 
     * @param errorVo The value object with the error information.
     * @return true if successful.
     */
    @Override
    public boolean saveMigrationErrorMessage(MigrationErrorVo errorVo) {
        return domainsUtility.saveMigrationErrorMessage(errorVo);
    }
}
