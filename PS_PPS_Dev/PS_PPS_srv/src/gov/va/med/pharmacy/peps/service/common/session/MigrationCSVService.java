/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import java.io.File;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderableItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ResetNationalDatabaseDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.MigrationCapability;
import gov.va.med.pharmacy.peps.service.common.migration.CSVResponseMessage;
import gov.va.med.pharmacy.peps.service.common.migration.ExportCSVFileData;
import gov.va.med.pharmacy.peps.service.common.migration.ProcessDomainType;
import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationExportState;
import gov.va.med.pharmacy.peps.service.common.utility.NdcCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.OiCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ProductCsvFile;




/**
 * MigrationCSVService
 */
public interface MigrationCSVService {

    /**
     * This method validates the CSV Header File param list of csvFiles
     * @param pRequest request
     * @return returns true/false - false if validation failed, otherwise true
     */
    CSVResponseMessage validateFileHeader(HttpServletRequest pRequest);

    /**
     * migrates the NDCs
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo
     */
    MigrationVariablesVo migrateNDCs(ProcessDomainType pProcessDomainType);

    /**
     * opens the Ndc File For Import
     * @return true/false
     * @throws MigrationException MigrationException
     */
    boolean openNdcFileForImport() throws MigrationException;

    /**
     * open Oi File For Import
     * @return true/false
     * @throws MigrationException MigrationException
     */
    boolean openOiFileForImport() throws MigrationException;

    /**
     * opens the Product File For Import
     * @return true/false
     * @throws MigrationException MigrationException
     */
    boolean openProductFileForImport() throws MigrationException;
    
    /**
     * gets the ManagedItemCapability
     * @return ManagedItemCapability
     */
    ManagedItemCapability getManagedItemCapability();

    /**
     * sets the ManagedItemCapability
     * @param pManagedItemCapability pManagedItemCapability
     */
    void setManagedItemCapability(
            ManagedItemCapability pManagedItemCapability);

    /**
     * setter for the MigrationCapability
     * @param pMigrationCapability pMigrationCapability
     */
    void setMigrationCapability(MigrationCapability pMigrationCapability);

    /**
     * gets the MigrationCapability
     * @return MigrationCapability
     */
    MigrationCapability getMigrationCapability();

    /**
     * get Ndc MultipartFile
     * @return MultipartFile
     */
    MultipartFile getNdcMultipartFile();

    /**
     * setter for the Ndc MultipartFile
     * @param pNdcMultipartFile pNdcMultipartFile
     */
    void setNdcMultipartFile(MultipartFile pNdcMultipartFile);

    /**
     * getter for the Oi MultipartFile
     * @return MultipartFile
     */
    MultipartFile getOiMultipartFile();

    /**
     * set Oi MultipartFile
     * @param pOiMultipartFile pOiMultipartFile
     */
    void setOiMultipartFile(MultipartFile pOiMultipartFile);

    /**
     * sets the Product MultipartFile
     * @param pProductMultipartFile pProductMultipartFile
     */
    void setProductMultipartFile(MultipartFile pProductMultipartFile);

    /**
     * gets the Product MultipartFile
     * @return MultipartFile
     */
    MultipartFile getProductMultipartFile();

    /**
     * migrates the OrderableItems
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo
     */
    MigrationVariablesVo migrateOrderableItems(
            ProcessDomainType pProcessDomainType);

    /**
     * gets the Total Rows Count
     * @return row count
     */
    long getTotalRowsCount();

    /**
     * setter for the total row count
     * @param pTotalRowsCount row count
     */
    void setTotalRowsCount(long pTotalRowsCount);

    /**
     * migrate the Products
     * @param pIen IEN
     * @param pOrderableItemsCsvFileActive pOrderableItemsCsvFileActive
     * @return MigrationVariablesVo
     */
    MigrationVariablesVo migrateProducts(String pIen,
            ProcessDomainType pOrderableItemsCsvFileActive);

    /**
     * gets the Export File 
     * @param pExportData pExportData
     * @return File
     */
    File getExportFile(ExportCSVFileData pExportData);

    /**
     * gets the NationalDatabaseDomainCapability
     * @return ResetNationalDatabaseDomainCapability
     */
    ResetNationalDatabaseDomainCapability getResetNationalDatabaseDomainCapability();

    /**
     * setter for the ResetNationalDatabaseDomainCapability
     * @param resetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability
     */
    void setResetNationalDatabaseDomainCapability(
            ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability);

    /**
     * gets the ProductDomainCapability
     * @return ProductDomainCapability
     */
    ProductDomainCapability getProductDomainCapability();

    /**
     * sets the ProductDomainCapability
     * @param pProductDomainCapability pProductDomainCapability
     */
    void setProductDomainCapability(
            ProductDomainCapability pProductDomainCapability);

    /**
     * gets the Ndc DomainCapability
     * @return NdcDomainCapability
     */
    NdcDomainCapability getNdcDomainCapability();

    /**
     * setter for the NdcDomainCapability
     * @param pNdcDomainCapability pNdcDomainCapability
     */
    void setNdcDomainCapability(NdcDomainCapability pNdcDomainCapability);

    /**
     * gets the OrderableItemDomainCapability
     * @return OrderableItemDomainCapability
     */
    OrderableItemDomainCapability getOrderableItemDomainCapability();

    /**
     * sets the OrderableItemDomainCapability
     * @param pOrderableItemDomainCapability pOrderableItemDomainCapability
     */
    void setOrderableItemDomainCapability(
            OrderableItemDomainCapability pOrderableItemDomainCapability);

    /**
     * gets the ExportState
     * @return MigrationExportState
     */
    MigrationExportState getExportState();

    /**
     * sets the ExportState
     * @param pExportState pExportState
     */
    void setExportState(MigrationExportState pExportState);

    /**
     * export the ProductCSVFile
     * @param list list of ids
     */
    void exportProductCSVFile(List<Long> list);

    /**
     * export the NDC CSV File
     * @param list the list of ids
     */
    void exportNDCCSVFile(List<Long> list);

    /**
     * export OrderableItems CSV File
     * @param list the list of ids
     */
    void exportOrderableItemsCSVFile(List<Long> list);

    /**
     * gets the Ids
     * @param entityType entity Type
     * @return list of ids
     */
    List<Long> getIds(EntityType entityType);

    /**
     * open File For Export
     * @param entityType entity Type
     * @return true/false
     */
    boolean openFileForExport(EntityType entityType);

    /**
     * close File For Export
     * @param entityType entity type
     */
    void closeFileForExport(EntityType entityType);

    /**
     * reset Export Values
     */
    void resetExportValues();

    /**
     * gets the ProductCsv File
     * @return ProductCsvFile
     */
    ProductCsvFile getProductCsvFile();

    /**
     * gets the Oi Csv File
     * @return OiCsvFile
     */
    OiCsvFile getOiCsvFile();

    /**
     * getsNdc Csv File
     * @return NdcCsvFile
     */
    NdcCsvFile getNdcCsvFile();

    /**
     * gets the Export Files Names If Exist
     * @return List of ExportCSVFileData
     */
    List<ExportCSVFileData> getExportFilesNamesIfExist();

    /**
     * gets the ProductVo Map
     * @return Map
     */
    Map<String, ProductVo> getProductVoMap();

    /**
     * sets the ProductVo Map
     * @param productVoMap productVoMap
     */
    void setProductVoMap(Map<String, ProductVo> productVoMap);

    /**
     * creates the ProductVo Map
     * @param pProcessDomainType pProcessDomainType
     */
    void createProductVoMap(ProcessDomainType pProcessDomainType);

}
