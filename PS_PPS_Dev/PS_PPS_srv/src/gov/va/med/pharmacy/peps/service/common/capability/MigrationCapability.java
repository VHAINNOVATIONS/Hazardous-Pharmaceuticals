/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationErrorVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationProductGroupVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
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


/**
 * Performs migration activites for the six migration domains */

public interface MigrationCapability {

    /**
     * init must be called before any other method
     */
    void init();

    /**
     * clear should be called after the migration is completed to free up the memory used by 
     * the hashmap process.
     */
    void clear();

    /**
     * This method will initiate the process to send a message to NDF to tell it to prepare to retrieve
     * NDC messages.
     * @return true if successful
     */
    boolean sendStartNDCMessage();
    
    /** 
     * updateCmopIdGenerator
     */
    void updateCmopIdGenerator();

    /**
     * This method will initiate the process to tell NDF that all NDC Synch messages have been sent.
     * @return true if successful
     */
    boolean sendStopNDCMessage();

    /**
     * Migrate the Drug Units domain
     * @param ien Starting IEN for the migration effort
     * @param size Number of elements to migrate at a time
     * @param type 1 for Active 0 for Inactive
     * @return migrationVariablesVo from the migrated file
     */
    MigrationVariablesVo migrateDrugUnits(String ien, int size, int type);

    /**
     * migrate NDCs 
     * @param ndcVo The list of NDCs to migrate
     * @return The MigrationVariablesVo
     */
    MigrationVariablesVo migrateNDCs(NdcVo ndcVo);

    /**
     * Migrate the Orderable Items
     * @param vo The OI to migrate
     * @return The MigrationVariablesVo containing the migration results
     * 
     */
    MigrationVariablesVo migrateOrderabeItems(OrderableItemVo vo);

    /**
     * Migrate the Product Items
     * @param productVO : The list of products to migrate
     * @return The MigrationVariablesVo containing the migration results
     */
    MigrationVariablesVo migrateProducts(ProductVo productVO);

    /**
     * Migrate the Dispense Units domain
     * @param ien Starting IEN for the migration effort
     * @param size Number of elements to migrate at a time
     * @param type 1 for Active 0 for Inactive
     * @return migrationVariablesVo from the migrated file
     */
    MigrationVariablesVo migrateDispenseUnits(String ien, int size, int type);

    /**
     * retriveProductsFromVista
     * @param ien Starting IEN for the migration effort
     * @param size Number of Products to retrieve at a time.
     * @param type Active or Inactive
     * @return an ArrayList of ProductVo     
     */
    MigrationProductGroupVo retriveProductsFromVista(String ien, int size, int type);

    /**
     * Migrate the GenericType domain
     * @param ien Starting IEN for the migration effort
     * @param size Number of elements to migrate at a time
     * @param type 1 for Active 0 for Inactive
     * @return migrationVariablesVo from the migrated file
     */
    MigrationVariablesVo migrateVAGeneric(String ien, int size, int type);

    /**
     * Migrate the Drug Ingredient domain
     * @param ien Starting IEN for the migration effort
     * @param size Number of elements to migrate at a time
     * @param type 1 for Active 0 for Inactive
     * @return migrationVariablesVo from the migrated file
     */
    MigrationVariablesVo migrateDrugIngredient(String ien, int size, int type);

    /**
     * Migrate the Drug Ingredient domain
     * @param ingredientList The ingredients that didn't have parents the first round of migration
     * @return migrationVariablesVo from the migrated file
     */
    MigrationVariablesVo migrateDrugIngredient(List<IngredientVo> ingredientList);

    /**
     * Migrate the Drug Class domain
     * @param ien Starting IEN for the migration effort
     * @param size Number of elements to migrate at a time
     * @param type 0 for type 0, 1 for type 1, 2 for type 2 and 3 for all others
     * @return migrationVariablesVo from the migrated file
     */
    MigrationVariablesVo migrateDrugClass(String ien, int size, int type);

    /**
     * Migrate the Dosage Form domain
     * @param ien Starting IEN for the migration effort
     * @param size Number of elements to migrate at a time
     * @param type 1 for Active 0 for Inactive
     * @return migrationVariablesVo from the migrated file
     */
    MigrationVariablesVo migrateDosageForm(String ien, int size, int type);

    /**
     * This method is used to merge two products
     * @param csvVo  The product retrieved from the CSV read
     * @param vistaVo The product retrieved from Vista
     * @return the Merged product
     */
    ProductVo mergeProducts(ProductVo csvVo, ProductVo vistaVo);

    /**
     * This method is used to save the Migration Error Message
     * @param file The Number of the file to save
     * @param e The exception
     * @return true if successful
     */
    boolean saveMigrationErrorMessage(String file, MigrationException e);
    
    /**
     * saveMigrationErrorMessage
     * @param errorVo errorVo
     * @return boolean
     */
    boolean saveMigrationErrorMessage(MigrationErrorVo errorVo);

    
    /**
     * retrieveMigrationErrorMessages
     * @param files files
     * @return List<MigrationErrorVo>
     */
    List<MigrationErrorVo> retrieveMigrationErrorMessages(String files);

    /**
     * retrieveMigrationErrorMessages
     * @return  List<MigrationErrorVo>
     */
    List<MigrationErrorVo> retrieveMigrationErrorMessages();


    /**
     * setDrugUnitDomainCapability
     * @param drugUnitDomainCapability drugUnitDomainCapability
     */
    void setDrugUnitDomainCapability(DrugUnitDomainCapability drugUnitDomainCapability);

    /**
     * setDispenseUnitDomainCapability
     * @param dispenseUnitDomainCapability dispenseUnitDomainCapability
     */
    void setDispenseUnitDomainCapability(DispenseUnitDomainCapability dispenseUnitDomainCapability);

    /**
     * setDosageFormDomainCapability
     * @param dosageFormDomainCapability dosageFormDomainCapability
     */
    void setDosageFormDomainCapability(DosageFormDomainCapability dosageFormDomainCapability);

    /**
     * setIngredientDomainCapability
     * @param ingredientDomainCapability ingredientDomainCapability
     */
    void setIngredientDomainCapability(IngredientDomainCapability ingredientDomainCapability);

    /**
     * setDrugClassDomainCapability
     * @param drugClassDomainCapability drugClassDomainCapability
     */
    void setDrugClassDomainCapability(DrugClassDomainCapability drugClassDomainCapability);

    /**
     * setGenericNameDomainCapability
     * @param genericNameDomainCapability genericNameDomainCapability
     */
    void setGenericNameDomainCapability(GenericNameDomainCapability genericNameDomainCapability);
    
    /**
     * setStandardMedRouteDomainCapability
     * @param standardMedRouteDomainCapability standardMedRouteDomainCapability
     */
    void setStandardMedRouteDomainCapability(StandardMedRouteDomainCapability standardMedRouteDomainCapability);

    /**
     * setMigrationRequestCapability
     * @param migrationRequestCapablity migrationRequestCapablity
     */
    void setMigrationRequestCapability(MigrationRequestCapability migrationRequestCapablity);

    /**
     * setOrderableItemDomainCapability
     * @param orderableItemDomainCapability orderableItemDomainCapability
     */
    void setOrderableItemDomainCapability(OrderableItemDomainCapability orderableItemDomainCapability);

    
    /**
     * setProductDomainCapability
     * @param productDomainCapabililty productDomainCapabililty
     */
    void setProductDomainCapability(ProductDomainCapability productDomainCapabililty);

    /**
     * setNdcDomainCapability
     * @param ndcDomainCapability ndcDomainCapability
     */
    void setNdcDomainCapability(NdcDomainCapability ndcDomainCapability);

    
    /**
     * setManufacturerDomainCapability
     * @param manufacturerDomainCapability manufacturerDomainCapability
     */
    void setManufacturerDomainCapability(ManufacturerDomainCapability manufacturerDomainCapability);

    /**
     * setManagedItemCapability
     * @param managedItemCapability managedItemCapability
     */
    void setManagedItemCapability(ManagedItemCapability managedItemCapability);

    /**
     * setPackageTypeDomainCapability
     * @param packageTypeDomainCapability packageTypeDomainCapability
     */
    void setPackageTypeDomainCapability(PackageTypeDomainCapability packageTypeDomainCapability);

    /**
     * setOrderUnitDomainCapability
     * @param orderUnitDomainCapability orderUnitDomainCapability
     */
    void setOrderUnitDomainCapability(OrderUnitDomainCapability orderUnitDomainCapability);

    /**
     * setItemAuditHistoryDomainCapability
     * @param itemAuditHistoryDomainCapability itemAuditHistoryDomainCapability
     */
    void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability);

    /**
     * setDrugReferenceCapability
     * @param drugReferenceCapability drugReferenceCapability
     */
    void setDrugReferenceCapability(DrugReferenceCapability drugReferenceCapability);

    
    /**
     * setCsFedScheduleDomainCapability
     * @param csFedScheduleDomainCapability csFedScheduleDomainCapability
     */
    void setCsFedScheduleDomainCapability(CsFedScheduleDomainCapability csFedScheduleDomainCapability);

    /**
     * setMigrationSynchFileCapability
     * @param migrationSynchFileCapability migrationSynchFileCapability
     */
    void setMigrationSynchFileCapability(MigrationSynchFileCapability migrationSynchFileCapability);

    /**
     * setMigrationErrorDomainCapability
     * @param migrationErrorDomainCapability migrationErrorDomainCapability
     */
    void setMigrationErrorDomainCapability(MigrationErrorDomainCapability migrationErrorDomainCapability);

    /**
     * setIntendedUseDomainCapability
     * @param intendedUseDomainCapability intendedUseDomainCapability
     */
    void setIntendedUseDomainCapability(IntendedUseDomainCapability intendedUseDomainCapability);

    /**
     * setDrugTextDomainCapability
     * @param drugTextDomainCapability drugTextDomainCapability
     */
    void setDrugTextDomainCapability(DrugTextDomainCapability drugTextDomainCapability);

    /**
     * setSpecialHandlingDomainCapability
     * @param specialHandlingDomainCapability specialHandlingDomainCapability
     */
    void setSpecialHandlingDomainCapability(SpecialHandlingDomainCapability specialHandlingDomainCapability);

    

    /**
     * setResetNationalDatabaseDomainCapability
     * @param resetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability
     */
    void setResetNationalDatabaseDomainCapability(ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability);

    
    /**
     * setFdbDrugClassDomainCapability.
     * @param fdbDrugClassDomainCapability the FdbDrugClassDomainCapability to set
     */
    void setFdbDrugClassDomainCapability(FdbDrugClassDomainCapability fdbDrugClassDomainCapability);
    
    
    /**
     * setFdbDrugUnitDomainCapability.
     * @param fdbDrugUnitDomainCapability the fdbDrugUnitDomainCapability to set
     */
    void setFdbDrugUnitDomainCapability(FdbDrugUnitDomainCapability fdbDrugUnitDomainCapability);
    
    /**
     * setFdbDosageFormDomainCapability.
     * @param fdbDosageFormDomainCapability the fdbDosageFormDomainCapability to set
     */
    void setFdbDosageFormDomainCapability(FdbDosageFormDomainCapability fdbDosageFormDomainCapability);
    
    /**
     * setFdbDrugIngredientDomainCapability.
     * @param fdbDrugIngredientDomainCapability the fdbDrugIngredientDomainCapability to set
     */
    void setFdbDrugIngredientDomainCapability(FdbDrugIngredientDomainCapability fdbDrugIngredientDomainCapability);
        
    /**
     * setFdbGenericNameDomainCapability.
     * @param fdbGenericNameDomainCapability the fdbGenericNameDomainCapability to set
     */
    void setFdbGenericNameDomainCapability(FdbGenericNameDomainCapability fdbGenericNameDomainCapability);
    
    /**
     * setFdbNdcDomainCapability.
     * @param fdbNdcDomainCapability property
     */
    void setFdbNdcDomainCapability(FdbNdcDomainCapability fdbNdcDomainCapability);
    
    /**
     * setFdbProductDomainCapability.
     * @param fdbProductDomainCapability property
     */
    void setFdbProductDomainCapability(FdbProductDomainCapability fdbProductDomainCapability);
    
    /**
     * setFssInterfaceCapability.
     * @param fssInterfaceCapability property
     */
    void setFssInterfaceCapability(FssInterfaceCapability fssInterfaceCapability);
    

}
