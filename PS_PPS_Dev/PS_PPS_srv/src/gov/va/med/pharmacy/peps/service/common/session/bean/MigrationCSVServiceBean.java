/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
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
import gov.va.med.pharmacy.peps.service.common.session.MigrationCSVService;
import gov.va.med.pharmacy.peps.service.common.utility.NdcCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.OiCsvFile;
import gov.va.med.pharmacy.peps.service.common.utility.ProductCsvFile;


/**
 * Migration CSV Service Bean
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject"
 *                local-extends="javax.ejb.EJBLocalObject"
 */
public class MigrationCSVServiceBean extends
        AbstractPepsStatelessSessionBean<MigrationCSVService> implements
        MigrationCSVService {

    private static final long serialVersionUID = 1L;

    /**
     * validates the File Header
     *
     * @param pRequest Request
     * 
     * @return CSVResponseMessage
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public CSVResponseMessage validateFileHeader(HttpServletRequest pRequest) {
        return this.getService().validateFileHeader(pRequest);
    }

    /**
     * open Ndc File For Import
     * 
     * @return true/false
     * 
     * @throws MigrationException MigrationException
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public boolean openNdcFileForImport() throws MigrationException {
        return this.getService().openNdcFileForImport();
    }

    /**
     * migrate NDCs
     * 
     * @param pProcessDomainType pProcessDomainType
     * 
     * @return MigrationVariablesVo 
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MigrationVariablesVo migrateNDCs(ProcessDomainType pProcessDomainType) {
        return this.getService().migrateNDCs(pProcessDomainType);
    }

    /**
     * gets the ManagedItemCapability
     * 
     * @return ManagedItemCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ManagedItemCapability getManagedItemCapability() {
        return this.getService().getManagedItemCapability();
    }

    /**
     * sets the ManagedItemCapability
     * 
     * @param pManagedItemCapability pManagedItemCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setManagedItemCapability(
            ManagedItemCapability pManagedItemCapability) {
        this.getService().setManagedItemCapability(pManagedItemCapability);
    }

    /**
     * gets the MigrationCapability
     * 
     * @return MigrationCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MigrationCapability getMigrationCapability() {
        return this.getService().getMigrationCapability();
    }

    /**
     * get Ndc MultipartFile
     * @return MultipartFile
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MultipartFile getNdcMultipartFile() {
        return this.getService().getNdcMultipartFile();
    }

    /**
     * setter for the Ndc MultipartFile
     * @param pNdcMultipartFile pNdcMultipartFile
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setNdcMultipartFile(MultipartFile pNdcMultipartFile) {
        this.getService().setNdcMultipartFile(pNdcMultipartFile);

    }

    /**
     * getter for the Oi MultipartFile
     * @return MultipartFile
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MultipartFile getOiMultipartFile() {
        return this.getService().getOiMultipartFile();
    }

    /**
     * set Oi MultipartFile
     * @param pOiMultipartFile pOiMultipartFile
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setOiMultipartFile(MultipartFile pOiMultipartFile) {
        this.getService().setOiMultipartFile(pOiMultipartFile);
    }

    /**
     * sets the MigrationCapability
     * @param pMigrationCapability pMigrationCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setMigrationCapability(MigrationCapability pMigrationCapability) {
        this.getService().setMigrationCapability(pMigrationCapability);

    }

    /**
     * open Oi File For Import
     * 
     * @return true/false
     * 
     * @throws MigrationException MigrationException
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public boolean openOiFileForImport() throws MigrationException {
        return this.getService().openOiFileForImport();
    }

    /**
     * migrates the OrderableItems
     * 
     * @param pProcessDomainType pProcessDomainType
     * 
     * @return MigrationVariablesVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MigrationVariablesVo migrateOrderableItems(ProcessDomainType pProcessDomainType) {
        return this.getService().migrateOrderableItems(pProcessDomainType);
    }

    /**
     * gets the Total Rows Count
     * 
     * @return row count
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public long getTotalRowsCount() {
        return this.getService().getTotalRowsCount();
    }

    /**
     * setter for the total row count
     * @param pTotalRowsCount row count
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setTotalRowsCount(long pTotalRowsCount) {
        this.getService().setTotalRowsCount(pTotalRowsCount);

    }

    /**
     * opens the Product File For Import
     * 
     * @return true/false
     * 
     * @throws MigrationException MigrationException
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public boolean openProductFileForImport() throws MigrationException {
        return this.getService().openProductFileForImport();
    }

    /**
     * sets the Product MultipartFile
     * @param pProductMultipartFile pProductMultipartFile
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setProductMultipartFile(MultipartFile pProductMultipartFile) {
        this.getService().setProductMultipartFile(pProductMultipartFile);
    }

    /**
     * gets the Product MultipartFile
     * @return MultipartFile
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MultipartFile getProductMultipartFile() {
        return this.getService().getProductMultipartFile();
    }

    /**
     * migrate the Products
     * 
     * @param ien IEN
     * 
     * @param pProcessDomainType pOrderableItemsCsvFileActive
     * 
     * @return MigrationVariablesVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MigrationVariablesVo migrateProducts(String ien,
            ProcessDomainType pProcessDomainType) {
        return this.getService().migrateProducts(ien, pProcessDomainType);
    }

    /**
     * gets the NationalDatabaseDomainCapability
     * 
     * @return ResetNationalDatabaseDomainCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ResetNationalDatabaseDomainCapability getResetNationalDatabaseDomainCapability() {
        return this.getService().getResetNationalDatabaseDomainCapability();
    }

    /**
     * setter for the ResetNationalDatabaseDomainCapability
     * 
     * @param resetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setResetNationalDatabaseDomainCapability(
            ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability) {
        this.getService().setResetNationalDatabaseDomainCapability(
                resetNationalDatabaseDomainCapability);

    }

    /**
     * gets the ProductDomainCapability
     * @return ProductDomainCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ProductDomainCapability getProductDomainCapability() {
        return this.getService().getProductDomainCapability();
    }

    /**
     * sets the ProductDomainCapability
     * 
     * @param pProductDomainCapability pProductDomainCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setProductDomainCapability(
            ProductDomainCapability pProductDomainCapability) {
        this.getService().setProductDomainCapability(pProductDomainCapability);
    }

    /**
     * gets the Ndc DomainCapability
     * 
     * @return NdcDomainCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public NdcDomainCapability getNdcDomainCapability() {
        return this.getService().getNdcDomainCapability();
    }

    /**
     * setter for the NdcDomainCapability
     * 
     * @param pNdcDomainCapability pNdcDomainCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setNdcDomainCapability(NdcDomainCapability pNdcDomainCapability) {
        this.getService().setNdcDomainCapability(pNdcDomainCapability);
    }

    /**
     * gets the OrderableItemDomainCapability
     * 
     * @return OrderableItemDomainCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public OrderableItemDomainCapability getOrderableItemDomainCapability() {
        return this.getService().getOrderableItemDomainCapability();
    }

    /**
     * sets the OrderableItemDomainCapability
     * 
     * @param pOrderableItemDomainCapability pOrderableItemDomainCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setOrderableItemDomainCapability(
            OrderableItemDomainCapability pOrderableItemDomainCapability) {
        this.getService().setOrderableItemDomainCapability(
                pOrderableItemDomainCapability);
    }

    /**
     * gets the get ExportFile
     * 
     * @return File
     * 
     * @param pExportData pExportData
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public File getExportFile(ExportCSVFileData pExportData) {
        return this.getService().getExportFile(pExportData);
    }

    /**
     * gets the ExportState
     * 
     * @return MigrationExportState
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public MigrationExportState getExportState() {
        return this.getService().getExportState();
    }

    /**
     * sets the ExportState
     * 
     * @param pExportState pExportState
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setExportState(MigrationExportState pExportState) {
        this.getService().setExportState(pExportState);

    }

    /**
     * export the ProductCSVFile
     * 
     * @param list list of ids
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void exportProductCSVFile(List<Long> list) {
        getService().exportProductCSVFile(list);
    }

    /**
     * export the NDC CSV File
     * 
     * @param list the list of ids
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void exportNDCCSVFile(List<Long> list) {
        getService().exportNDCCSVFile(list);
    }

    /**
     * export OrderableItems CSV File
     * 
     * @param list the list of ids
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void exportOrderableItemsCSVFile(List<Long> list) {
        getService().exportOrderableItemsCSVFile(list);
    }

    /**
     * gets the Ids
     * 
     * @param entityType entity Type
     * 
     * @return list of ids
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public List<Long> getIds(EntityType entityType) {
        return getService().getIds(entityType);
    }

    /**
     * open File For Export
     * 
     * @param entityType entity Type
     * 
     * @return true/false
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public boolean openFileForExport(EntityType entityType) {
        return getService().openFileForExport(entityType);
    }

    /**
     * close File For Export
     * 
     * @param entityType entity type
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void closeFileForExport(EntityType entityType) {
        getService().closeFileForExport(entityType);

    }

    /**
     *  reset Export Values
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void resetExportValues() {
        getService().resetExportValues();
    }

    /**
     * gets the ProductCsv File
     * 
     * @return ProductCsvFile
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public ProductCsvFile getProductCsvFile() {
        return getService().getProductCsvFile();
    }

    /**
     * gets the Oi Csv File
     * 
     * @return OiCsvFile
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public OiCsvFile getOiCsvFile() {
        return getService().getOiCsvFile();
    }

    /**
     * getsNdc Csv File
     * 
     * @return NdcCsvFile
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public NdcCsvFile getNdcCsvFile() {
        return getService().getNdcCsvFile();
    }

    /**
     * gets the Export Files Names If Exist
     * 
     * @return List of ExportCSVFileData
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public List<ExportCSVFileData> getExportFilesNamesIfExist() {
        return getService().getExportFilesNamesIfExist();
    }

    /**
     * gets the ProductVo Map
     * 
     * @return Map
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public Map<String, ProductVo> getProductVoMap() {
        return getService().getProductVoMap();
    }

    /**
     * creates the ProductVo Map
     * 
     * @param pProcessDomainType pProcessDomainType
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void createProductVoMap(ProcessDomainType pProcessDomainType) {
        getService().createProductVoMap(pProcessDomainType);
    }

    /**
     * sets the ProductVo Map
     * 
     * @param pProductVoMap productVoMap
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setProductVoMap(Map<String, ProductVo> pProductVoMap) {
        getService().setProductVoMap(pProductVoMap);

    }

}
