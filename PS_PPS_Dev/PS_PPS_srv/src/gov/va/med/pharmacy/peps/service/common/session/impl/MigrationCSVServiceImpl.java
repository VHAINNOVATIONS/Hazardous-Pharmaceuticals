/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.MigrationProductGroupVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
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
 * MigrationCSVServiceImpl
 */
public class MigrationCSVServiceImpl implements MigrationCSVService {

    // references elsewhere -  Move to a common static class
    
 
    private static final Logger LOG = Logger.getLogger(MigrationCSVServiceImpl.class);
    private static final String NDCFILE = "ndcFile";
    private static final String OIFILE = "oiFile";
    private static final String PRODUCTFILE = "productFile";
    
    private MigrationCapability migrationCapability;
    private ManagedItemCapability managedItemCapability;
    private MultipartFile ndcMultipartFile;
    private MultipartFile oiMultipartFile;
    private MultipartFile productMultipartFile;
    private long totalRowsCount;
    private NdcCsvFile ndcCsvFile;
    private OiCsvFile oiCsvFile;
    private ProductCsvFile productCsvFile;
    private Map<String, ProductVo> productVoMap;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private ProductDomainCapability productDomainCapability;
    private NdcDomainCapability ndcDomainCapability;
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private PlatformTransactionManager transactionManager;
    private MigrationExportState exportState;

  

    private int ndcFailure = 0;
    private int ndcSuccess = 0;
    private int productFailure = 0;
    private int productSuccess = 0;
    private int oiSuccess = 0;
    private int oiFailure = 0;

    /**
    * sends file contents for CSV files from the presentation layer.
    * 
    * @param iFile 0 = OI 1 = Product 2 = NDC - should make an ENUM
    * @param pInputStream InputStream
    * @return InputStream pInputStream
    * @throws MigrationException MigrationException
    */
    public InputStream saveFileData(int iFile, InputStream pInputStream) throws MigrationException {
        try {

            String fileName = "";

            if (iFile == 0) {
                fileName = getFullPath(PPSConstants.IMPORT_OI_FILE_NAME);
            } else if (iFile == 1) {
                fileName = getFullPath(PPSConstants.IMPORT_PRODUCT_FILE_NAME);
            } else if (iFile == 2) {
                fileName = getFullPath(PPSConstants.IMPORT_NDC_FILE_NAME);
            }

            LOG.debug("filename is " + fileName);
            BufferedInputStream bis = new BufferedInputStream(pInputStream);

            // if newFIle is false then we want to be in append mode
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);

            byte[] contents = new byte[PPSConstants.I1024];
            int bytesRead = 0;
            String strFileContents;

            while ((bytesRead = bis.read(contents)) != -1) {
                strFileContents = new String(contents, 0, bytesRead);
                strFileContents.replace("\"", "");
                bw.write(strFileContents);

            }
            
            bw.close();

        } catch (FileNotFoundException e) {
            LOG.debug("File not found " + e);
            throw new MigrationException(e.getMessage());

        } catch (IOException ioe) {
            LOG.debug("Exception while reading the file  " + ioe);
            throw new MigrationException(ioe.getMessage());
        } catch (Exception e) {
            LOG.debug("#################################################");
            LOG.debug("**** Error : " + e.getMessage());
            LOG.debug("oFile is  " + iFile);
            throw new MigrationException(e.getMessage());
        }
        
        return null;
    }

    /**
     * Validates File header
     * @param pRequest pRequest
     * @return CSVResponseMessage response message
     */
    @Override
    public CSVResponseMessage validateFileHeader(HttpServletRequest pRequest) {
        CSVResponseMessage responseMessage = new CSVResponseMessage();
        Map<String, InputStream> csvFileMap = null;

        if (!(pRequest instanceof MultipartHttpServletRequest)) {
            responseMessage.setError(true);
            responseMessage.setResponseMessage("Failed to upload CVS file (No MultipartFile found!)");
            
            return responseMessage;
        }

        String fileName = "";

        MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) pRequest;
        
        try {
            csvFileMap = getMultipartFileFromRequest(mpRequest);
            
            if (csvFileMap.isEmpty()) {
                responseMessage.setResponseMessage("No Files found to start migration!");
                responseMessage.setError(true);

            } else {
                for (Entry<String, InputStream> entry : csvFileMap.entrySet()) {
                    LOG.debug(entry.getKey() + "/" + entry.getValue());

                    fileName = entry.getKey();
                    InputStream inputStream = entry.getValue();
                    LOG.debug("filename: " + fileName);

                    validateFile(fileName, inputStream);
                }

                responseMessage.setResponseMessage("");
                responseMessage.setError(false);
            }
        } catch (MigrationException e) {
            responseMessage.setResponseMessage("File: " + fileName + ", error message: " + e.getMessage());
            responseMessage.setError(true);
        }

        
        return responseMessage;
    }

    /**
     * Validates the header of the imported file.
     * @param pFileName file name
     * @param pInputStream pInputStream
     * @return true/false
     * @throws MigrationException MigrationException
     */
    private boolean validateFile(String pFileName, InputStream pInputStream) throws MigrationException {
        try {
            if (pFileName.equals(NDCFILE)) {
                if (pInputStream == null) {
                    LOG.debug("No NDC File to validate");
                } else {
                    NdcCsvFile ndcFile = new NdcCsvFile();
                    ndcFile.openForImport(pInputStream);
                    ndcFile.closeImport();

                    if (this.getNdcMultipartFile() != null) {
                        InputStream in = this.getNdcMultipartFile().getInputStream();
                        saveFileData(2, in);
                    }

                }
            } else if (pFileName.equals(OIFILE)) {
                if (pInputStream == null) {
                    LOG.debug("No OI File to validate");
                } else {

                    OiCsvFile oiFile = new OiCsvFile();
                    oiFile.openForImport(pInputStream);
                    oiFile.closeImport();

                    if (this.getOiMultipartFile() != null) {
                        InputStream in = this.getOiMultipartFile().getInputStream();
                        saveFileData(0, in);
                    }
                }

            } else if (pFileName.equals(PRODUCTFILE)) {
                if (pInputStream == null) {
                    LOG.debug("No Product File to validate");
                } else {
                    ProductCsvFile productFile = new ProductCsvFile();
                    productFile.openForImport(pInputStream);
                    productFile.closeImport();

                    if (this.getProductMultipartFile() != null) {
                        InputStream in = this.getProductMultipartFile().getInputStream();
                        saveFileData(1, in);
                    }

                }
            }
        } catch (MigrationException e) {
            throw new MigrationException(e.getMessage());
        } catch (IOException e) {
            throw new MigrationException(e.getMessage());
        }

        return true;
    }

    /**
     * Retrieves all the imported files as multipart files from the request
     * @param pRequest the request to extract the Multipart file
     * @return a map
     * @throws MigrationException 
     */
    private Map<String, InputStream> getMultipartFileFromRequest(MultipartHttpServletRequest pRequest)
        throws MigrationException {
        
        Map<String, InputStream> csvFilesMap = new HashMap<String, InputStream>();

        try {
            for (Iterator<String> iterator = pRequest.getFileNames(); iterator.hasNext();) {
                String filename = iterator.next();
                LOG.debug("file: " + filename);

                MultipartFile multipartFile = pRequest.getFile(filename);
                
                if (multipartFile.getSize() == 0) {
                    continue;
                }

                if (filename.equals(NDCFILE)) {
                    this.setNdcMultipartFile(multipartFile);

                } else if (filename.equals(OIFILE)) {
                    this.setOiMultipartFile(multipartFile);
                } else if (filename.equals(PRODUCTFILE)) {
                    this.setProductMultipartFile(multipartFile);
                }

                String name = multipartFile.getOriginalFilename();
                File file = new File(name);

                InputStream inputStream = multipartFile.getInputStream();

                LOG.debug("path: " + file.getAbsolutePath());
                LOG.debug("name: " + file.getName());

                csvFilesMap.put(filename, inputStream);
            }
        } catch (IllegalStateException e) {
            LOG.error("==>getFileFromMultipartFile(): IllegalStateExeption " + e.getMessage());
        } catch (IOException e) {
            LOG.error("==>getFileFromMultipartFile(): IOExeption " + e.getMessage());
        }

        return csvFilesMap;
    }

    /**
     * migrates the NDCs
     * @param pProcessDomainType process type
     * @return MigrationVariablesVo MigrationVariablesVo
     */
    public MigrationVariablesVo migrateNDCs(ProcessDomainType pProcessDomainType) {
        MigrationVariablesVo vo = new MigrationVariablesVo();
        NdcVo ndcVo2 = null;
        int iSuccess = 0;
        int iFailure = 0;
        int iDuplicate = 0;
        int iPackages = 0;
        int iManufacturers = 0;

        // get opened ndcCsvFile
        NdcCsvFile myNdcCsvFile = getNdcCsvFile();

        //loop through only specified amount of record we want to retrieve at a time
        for (int i = 0; i < pProcessDomainType.getRecordFetchQty(); i++) {
            NdcVo ndcVo = (NdcVo) managedItemCapability.retrieveBlankTemplate(EntityType.NDC);
            
            try {
                ndcVo2 = myNdcCsvFile.getNextNdc(ndcVo);
                
                if (ndcVo2 == null) {
                    vo = new MigrationVariablesVo();
                    vo.setEndOfFile(true);
                    break;
                }
                
                vo = migrationCapability.migrateNDCs(ndcVo2);

                iFailure += vo.getIErroredOnMigration();
                iDuplicate += vo.getIDuplicatesNotMigrated();
                iSuccess += vo.getISuccessfullyMigrated();
                iManufacturers += vo.getINumManufacturersMigrated();
                iPackages += vo.getINumPackageTypesMigrated();
            } catch (MigrationException me) {
                LOG.error("migrateNDCs()==>>: IllegalStateExeption " + me.getMessage());
                
                try {
                    migrationCapability.saveMigrationErrorMessage(String.valueOf(pProcessDomainType.getFileNumber()),
                                                                  me);
                } catch (Exception e) {
                    LOG.error("ERROR writing NDC error message " + e.getMessage());
                }
                
                iFailure++;
            }
        }

        vo.setIErroredOnMigration(iFailure);
        vo.setIDuplicatesNotMigrated(iDuplicate);
        vo.setISuccessfullyMigrated(iSuccess);
        vo.setINumManufacturersMigrated(iManufacturers);
        vo.setINumPackageTypesMigrated(iPackages);

        if (vo.isEndOfFile()) {
            LOG.debug("Close NDC File for Import becasue EOF is true!");
            
            try {
                myNdcCsvFile.closeImport();
            } catch (MigrationException me) {
                LOG.error("Failed closing Product file for import : " + me.getMessage());
            }
        }

        return vo;
    }

    /**
     * openOiFileForImport
     * @return true/false
     * @exception MigrationException MigrationException
     */
    @Override
    public boolean openOiFileForImport() throws MigrationException {

        String fileName = getFullPath(PPSConstants.IMPORT_OI_FILE_NAME);
        
        
        try {

            boolean exists = (new File(fileName)).exists();

            if (exists) {
                oiCsvFile = new OiCsvFile();
                FileInputStream iStreamLines = new FileInputStream(fileName);

                long totalRowCount = oiCsvFile.getTotalRowsInfile(iStreamLines);
                LOG.debug("openOiMultipartFileForImport()==>> total records in file:  " + totalRowCount);
                this.setTotalRowsCount(totalRowCount);
                iStreamLines.close();

                FileInputStream iStream = new FileInputStream(fileName);
                oiCsvFile.openForImport(iStream);
            } else {
                LOG.debug("Orderable Item Input File " + fileName + "  doesn't exist.");
                
                return false;
            }

        } catch (FileNotFoundException e) {
            LOG.debug("File not found   " + e);
            throw new MigrationException(e.getMessage());
        } catch (IOException ioe) {
            LOG.debug(" Exception while reading the file " + ioe);
            throw new MigrationException(ioe.getMessage());
        } catch (IllegalStateException e) {
            LOG.debug(" migrateOrderableItems()==>>: IllegalStateExeption " + e.getMessage());
            throw new MigrationException(e.getMessage());
        } catch (Exception e) {
            LOG.debug("######################################################");
            LOG.debug(" *** Error : " + e.getMessage());
            throw new MigrationException(e.getMessage());
        }

        this.setOiCsvFile(oiCsvFile);
        
        return true;
    }

    
    /**
     * opens Ndc File For Import
     * @return true/false
     * @throws MigrationException MigrationException
     */
    @Override
    public boolean openNdcFileForImport() throws MigrationException {

        String fileName = getFullPath(PPSConstants.IMPORT_NDC_FILE_NAME);
       
        
        try {

            boolean exists = (new File(fileName)).exists();

            if (exists) {
                ndcCsvFile = new NdcCsvFile();
                FileInputStream iStreamLines = new FileInputStream(fileName);

                long totalRowCount = ndcCsvFile.getTotalRowsInfile(iStreamLines);
                LOG.debug("openNDCFileForImport()==>> total records in file:  " + totalRowCount);
                this.setTotalRowsCount(totalRowCount);
                iStreamLines.close();

                FileInputStream iStream = new FileInputStream(fileName);
                ndcCsvFile.openForImport(iStream);
            } else {
                LOG.debug("NDC Input File " + fileName + " doesn't exist.  ");
                
                return false;
            }

        } catch (FileNotFoundException e) {
            LOG.debug("File not found" + e);
            throw new MigrationException(e.getMessage());
        } catch (IOException ioe) {
            LOG.debug("Exception while  reading the file " + ioe);
            throw new MigrationException(ioe.getMessage());
        } catch (IllegalStateException e) {
            LOG.error("migrateNDCItems()==>>: IllegalStateExeption " + e.getMessage());
            throw new MigrationException(e.getMessage());
        } catch (Exception e) {
            LOG.debug("##################################################");
            LOG.debug("*** Error  : " + e.getMessage());
            throw new MigrationException(e.getMessage());
        }

        this.setNdcCsvFile(ndcCsvFile);
        
        return true;
    }

    @Override
    public boolean openProductFileForImport() throws MigrationException {
        String fileName = getFullPath(PPSConstants.IMPORT_PRODUCT_FILE_NAME);
    
        FileInputStream iStream = null;
        
        try {
            boolean exists = (new File(fileName)).exists();

            if (exists) {
                productCsvFile = new ProductCsvFile();
                FileInputStream iStreamLines = new FileInputStream(fileName);

                long totalRowCount = productCsvFile.getTotalRowsInfile(iStreamLines);
                LOG.debug("openProductFileForImport()==>> total records in file:  " + totalRowCount);
                this.setTotalRowsCount(totalRowCount);
                iStreamLines.close();

                iStream = new FileInputStream(fileName);
                productCsvFile.openForImport(iStream);
            } else {
                LOG.debug("Product Input File " + fileName + " doesn't exist.");
                
                return false;
            }

        } catch (FileNotFoundException e) {
            LOG.debug("File not found:" + e);
            throw new MigrationException(e.getMessage());
        } catch (IOException ioe) {
            LOG.debug("Exception while reading the file " + ioe);
            throw new MigrationException(ioe.getMessage());
        } catch (IllegalStateException e) {
            LOG.error("openProductFileForImport()==>>: IllegalStateExeption " + e.getMessage());
            throw new MigrationException(e.getMessage());
        } catch (Exception e) {
            LOG.debug("################################################");
            LOG.debug("*** Error : " + e.getMessage());

            throw new MigrationException(e.getMessage());
        }

        this.setProductCsvFile(productCsvFile);
        
        return true;
    }

    @Override
    public MigrationVariablesVo migrateOrderableItems(ProcessDomainType pProcessDomainType) {
        MigrationVariablesVo vo = new MigrationVariablesVo();
        OrderableItemVo oiVo2 = null;
        int iSuccess = 0;
        int iFailure = 0;
        int iDuplicate = 0;
        
        for (int i = 0; i < pProcessDomainType.getRecordFetchQty(); i++) {
            OrderableItemVo oiVo =
                    (OrderableItemVo) managedItemCapability.retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);
            
            try {
                oiVo2 = oiCsvFile.getNextOi(oiVo);
                
                if (oiVo2 == null) {
                    vo = new MigrationVariablesVo();
                    vo.setEndOfFile(true);
                    break;
                }
                
                vo = migrationCapability.migrateOrderabeItems(oiVo2);

                iFailure += vo.getIErroredOnMigration();
                iDuplicate += vo.getIDuplicatesNotMigrated();
                iSuccess += vo.getISuccessfullyMigrated();
            } catch (MigrationException me) {
                LOG.error("migrateOrderableItems()==>>: IllegalStateExeption " + me.getMessage());
                migrationCapability.saveMigrationErrorMessage(String.valueOf(pProcessDomainType.getFileNumber()), me);
                iFailure++;
            }
        }
        
        vo.setIErroredOnMigration(iFailure);
        vo.setIDuplicatesNotMigrated(iDuplicate);
        vo.setISuccessfullyMigrated(iSuccess);

        if (vo.isEndOfFile()) {
            try {
                oiCsvFile.closeImport();
            } catch (MigrationException me) {
                LOG.error("Failed closing OI file for import : " + me.getMessage());
            }
        }

        return vo;
    }

    
    /**
     * migrate the Products
     * @param ien ien
     * @param pProcessDomainType pProcessDomainType
     * @return MigrationVariablesVo MigrationVariablesVo
     */
    @Override
    public MigrationVariablesVo migrateProducts(String ien, ProcessDomainType pProcessDomainType) {
        MigrationVariablesVo vo = new MigrationVariablesVo();
        int iSuccess = 0;
        int iFailure = 0;
        int iDuplicate = 0;
        String name = "";

        MigrationProductGroupVo groupVo =
                migrationCapability.retriveProductsFromVista(ien, pProcessDomainType.getRecordFetchQty(),
                                                             pProcessDomainType.getDomainState().getState());
        List<ProductVo> vistaList = groupVo.getProductList();

        for (ProductVo productVo : vistaList) {
            name = productVo.getVaProductName();
            ProductVo csvProductVo = null;
            LOG.debug("MigrationCSVService before csvmap call");
            
            try {
                if (getProductMultipartFile() != null) {
                    csvProductVo = getProductVoMap().get(name);
                }
            } catch (Exception e) {
                LOG.error("Error with Multipart File get for " + name + ":" + e.getMessage());
            }
            
            LOG.debug("MigrationCSVService after csvmap call");
            productVo = migrationCapability.mergeProducts(csvProductVo, productVo);

            try {
                vo = migrationCapability.migrateProducts(productVo);
            } catch (Exception e) {
                LOG.error("Uncaught exception migrating product " + name + ":" + e.getMessage());
            }

            iSuccess += vo.getISuccessfullyMigrated();
            iFailure += vo.getIErroredOnMigration();
            iDuplicate += vo.getIDuplicatesNotMigrated();
            LOG.debug("Migrated Product " + name + " and it was " + vo.getISuccessfullyMigrated() + ":"
                      + vo.getIErroredOnMigration() + ":" + vo.getIDuplicatesNotMigrated());
        }
        
        vo.setStrLastIEN(groupVo.getLastIEN());
        vo.setIErroredOnMigration(iFailure + groupVo.getNumberErroredOnRetrieval());
        vo.setIDuplicatesNotMigrated(iDuplicate);
        vo.setISuccessfullyMigrated(iSuccess);
        vo.setEndOfFile(groupVo.isEndOfFile());

        return vo;
    }

    
    /**
     * create the product Vo map
     * @param pProcessDomainType pProcessDomainType
     */
    public void createProductVoMap(ProcessDomainType pProcessDomainType) {

        productVoMap = new HashMap<String, ProductVo>();
        boolean done = false;
        ProductVo prodVo2 = null;
        ProductCsvFile mProductCsvFile = this.getProductCsvFile();

        int counter = 0;
        
        while (!done) {
            DefaultTransactionDefinition def = null;
            TransactionStatus status = null;

            try {
                def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setTimeout(PPSConstants.I60);
                status = transactionManager.getTransaction(def);

                ProductVo prodVo = (ProductVo) managedItemCapability.retrieveBlankTemplate(EntityType.PRODUCT);
                prodVo2 = mProductCsvFile.getNextProduct(prodVo);
                
                if (prodVo2 == null) {
                    done = true;
                    break;
                }
                
                LOG.debug("createProductVoMap()==>> adding record to Map : product name: " + prodVo2.getVaProductName()
                          + " record count: " + counter++);
                productVoMap.put(prodVo2.getVaProductName(), prodVo2);
            } catch (MigrationException me) {

                LOG.error("createProductVoMap()==>>: MigrationException " + me.getMessage());
                migrationCapability.saveMigrationErrorMessage(String.valueOf(pProcessDomainType.getFileNumber()), me);

            } catch (Exception e) {
                LOG.error("Error creating product Map " + e.getMessage());
            }
            
            transactionManager.commit(status);
        }
        
        setProductVoMap(productVoMap);

    }

    /**
     * gets the Ids
     * @param entityType entityType
     * @return list of Ids
     */
    public List<Long> getIds(EntityType entityType) {
        return resetNationalDatabaseDomainCapability.getIds(entityType, false);
    }

    /**
     * open File For Export
     * @param entityType entityType
     * @return true/false
     */
    public boolean openFileForExport(EntityType entityType) {

        boolean success = true;

        if (entityType.equals(EntityType.PRODUCT)) {
            String filepath = getFullPath(PPSConstants.EXPORT_PRODUCT_FILE_NAME);
            productCsvFile = new ProductCsvFile();
            
            try {
                productCsvFile.openForExport(filepath);
            } catch (Exception e) {
                LOG.error("Cound not open NDC  file for export. Error is " + e.getMessage());
                success = false;
            }
        } else if (entityType.equals(EntityType.NDC)) {
            String filepath = getFullPath(PPSConstants.EXPORT_NDC_FILE_NAME);
            ndcCsvFile = new NdcCsvFile();
            
            try {
                ndcCsvFile.openForExport(filepath);
            } catch (Exception e) {
                LOG.error("Cound not open NDC file for export. Error is " + e.getMessage());
                success = false;
            }
        } else if (entityType.equals(EntityType.ORDERABLE_ITEM)) {
            String filepath = getFullPath(PPSConstants.EXPORT_OI_FILE_NAME);
            oiCsvFile = new OiCsvFile();
            
            try {
                oiCsvFile.openForExport(filepath);
            } catch (Exception e) {
                LOG.error("Cound not open Orderable Item file for export. Error is " + e.getMessage());
                success = false;
            }
        } else {
            LOG.error("Invalid EntityType in OpenFileForExpoert.  " + entityType.toString());
            success = false;
        }
        
        return success;
    }

    
    /**
     * cloase file for export
     * @param entityType entityType
     */
    public void closeFileForExport(EntityType entityType) {
        if (entityType.equals(EntityType.PRODUCT)) {
            try {
                productCsvFile.closeExport();
            } catch (Exception e) {
                LOG.error("Cound not closethe Product file for export. Error is " + e.getMessage());
            }
        } else if (entityType.equals(EntityType.NDC)) {
            try {
                ndcCsvFile.closeExport();
            } catch (Exception e) {
                LOG.error("Cound not close NDC file for export. Error is " + e.getMessage());
            }
        } else if (entityType.equals(EntityType.ORDERABLE_ITEM)) {
            try {
                oiCsvFile.closeExport();
            } catch (Exception e) {
                LOG.error("Cound not close Orderable Item file for export. Error is " + e.getMessage());
            }
        } else {
            LOG.error("Invalid EntityType in OpenFileForExpoert. " + entityType.toString());
        }
    }

    /**
     * exports product data to CSV file
     * @param list list
     */
    public void exportProductCSVFile(List<Long> list) {
        String productName = "";

        for (Long eplId : list) {

            try {
                ProductVo productVo = (ProductVo) managedItemCapability.retrieveBlankTemplate(EntityType.PRODUCT);
                LOG.debug("Getting Product row with id " + eplId);
                productVo = this.resetNationalDatabaseDomainCapability.getProduct(eplId, productVo);
                productName = productVo.getVaProductName();
                productCsvFile.putNextProduct(productVo);
                productSuccess++;
                this.getExportState().setProductRecordCounter(productSuccess);
            } catch (Exception e) {
                LOG.error("Uncaught exception on putting row  " + productName + ".  Exception is " + e.getMessage());
                productFailure++;
                this.getExportState().setFailureCounter(productFailure);
            }
        }
    }

    /**
     * exports NDC data to CSV file
     * @param list list
     */
    public void exportNDCCSVFile(List<Long> list) {
        String ndc = "";

        for (Long eplId : list) {
            NdcVo ndcVo = (NdcVo) managedItemCapability.retrieveBlankTemplate(EntityType.NDC);
            
            try {

                ndcVo = this.resetNationalDatabaseDomainCapability.getNdcs(eplId, ndcVo);
                ndc = ndcVo.getNdc();
                ndcCsvFile.putNextNdc(ndcVo);
                ndcSuccess++;
                this.getExportState().setNdcRecordCounter(ndcSuccess);
            } catch (Exception e) {
                LOG.error("  Uncaught exception on putting row " + ndc + " . Exception is " + e.getMessage());
                ndcFailure++;
                this.getExportState().setFailureCounter(ndcFailure);
            }
        }

    }

    /**
     * exports orderable Items data to CSV file
     * @param list list
     */
    public void exportOrderableItemsCSVFile(List<Long> list) {

        String oi = "";

        for (Long eplId : list) {
            try {
                OrderableItemVo oiVo =
                        (OrderableItemVo) managedItemCapability.retrieveBlankTemplate(EntityType.ORDERABLE_ITEM);
                LOG.debug("Getting OI row with id " + eplId);
                oiVo = this.resetNationalDatabaseDomainCapability.getOrderableItem(eplId, oiVo);
                oi = oiVo.getOiName();
                oiCsvFile.putNextOi(oiVo);
                oiSuccess++;
                this.getExportState().setOiRecordCounter(oiSuccess);
            } catch (Exception e) {
                LOG.error("Uncaught exception on putting row " + oi + ". Exception is " + e.getMessage());
                oiFailure++;
                this.getExportState().setFailureCounter(oiFailure);
            }
        }
    }

    /**
     * returns a file ptr to the opened file.
     * @param pExportData pExportData
     * @return File File
     */
    public File getExportFile(ExportCSVFileData pExportData) {
        File file = new File(pExportData.getFullExportPath());

        return file;
    }

    /**
     * gets the Full Path
     *
     * @param pFilePath pFilePath
     * @return the path
     */
    private String getFullPath(String pFilePath) {
        String path = "";
        
        if (System.getProperty(PPSConstants.OS_NAME).contains("win") 
            || System.getProperty(PPSConstants.OS_NAME).contains("Win")) {
            path = System.getProperty(PPSConstants.USER_DIR).concat(PPSConstants.DEVELOPMENT_PATH);
        } else {
            path = System.getProperty(PPSConstants.USER_DIR).concat(PPSConstants.SERVER_PATH);
        }
        
        return path.concat(pFilePath);
    }

    /**
     * This method checks to see if the export file exist,
     * if so, it returns it in a list. 
     * @return list of ExportCSVFileData
     */
    public List<ExportCSVFileData> getExportFilesNamesIfExist() {
        List<ExportCSVFileData> exportFiles = new ArrayList<ExportCSVFileData>();

        if ((new File(ExportCSVFileData.DOMAIN_MAPPING.getFullExportPath())).exists()) {
            exportFiles.add(ExportCSVFileData.DOMAIN_MAPPING);
        }
        
        if ((new File(ExportCSVFileData.NDC.getFullExportPath())).exists()) {
            exportFiles.add(ExportCSVFileData.NDC);
        }

        if ((new File(ExportCSVFileData.ORDERABLE_ITEMS.getFullExportPath())).exists()) {
            exportFiles.add(ExportCSVFileData.ORDERABLE_ITEMS);
        }

        if ((new File(ExportCSVFileData.PRODUCT.getFullExportPath())).exists()) {
            exportFiles.add(ExportCSVFileData.PRODUCT);
        }        

        return exportFiles;
    }

    /**
     * reset Export Values
     */
    public void resetExportValues() {
        this.ndcFailure = 0;
        this.ndcSuccess = 0;
        this.productFailure = 0;
        this.productSuccess = 0;
        this.oiSuccess = 0;
        this.oiFailure = 0;
    }

    /**
     * getter for  MigrationCapability
     * @return MigrationCapability MigrationCapability
     */
    public MigrationCapability getMigrationCapability() {
        return migrationCapability;
    }

    /**
     * setter for the MigrationCapability
     * @param pMigrationCapability MigrationCapability
     */
    public void setMigrationCapability(MigrationCapability pMigrationCapability) {
        this.migrationCapability = pMigrationCapability;
    }

    /**
     * get ManagedItemCapability
     * @return ManagedItemCapability ManagedItemCapability
     */
    public ManagedItemCapability getManagedItemCapability() {
        return managedItemCapability;
    }

    /**
     * setter for ManagedItemCapability
     * @param pManagedItemCapability pManagedItemCapability
     */
    public void setManagedItemCapability(ManagedItemCapability pManagedItemCapability) {
        this.managedItemCapability = pManagedItemCapability;
    }

    
    /**
     * gets the Ndc MultipartFile
     * @return MultipartFile
     */
    public MultipartFile getNdcMultipartFile() {
        return ndcMultipartFile;
    }

    /**
     * set Ndc MultipartFile 
     * @param pNdcMultipartFile pNdcMultipartFile
     */
    public void setNdcMultipartFile(MultipartFile pNdcMultipartFile) {
        this.ndcMultipartFile = pNdcMultipartFile;
    }

    /**
     * gets the OiMultipartFile
     * @return MultipartFile
     */
    public MultipartFile getOiMultipartFile() {
        return oiMultipartFile;
    }

    
    /**
     * sets the Oi MultipartFile
     * @param pOiMultipartFile pOiMultipartFile
     */
    public void setOiMultipartFile(MultipartFile pOiMultipartFile) {
        this.oiMultipartFile = pOiMultipartFile;
    }

    /**
     * gets the Total RowsCount
     * @return rows count
     */
    public long getTotalRowsCount() {
        return totalRowsCount;
    }

    /**
     * sets the Total Rows Count
     * @param pTotalRowsCount total rows count 
     */
    public void setTotalRowsCount(long pTotalRowsCount) {
        this.totalRowsCount = pTotalRowsCount;
    }

    /**
     * gets the Ndc Csv File
     * @return NdcCsvFile NdcCsvFile
     */
    public NdcCsvFile getNdcCsvFile() {
        return ndcCsvFile;
    }

    /**
     * sets the Ndc Csv File
     * @param pNdcCsvFile pNdcCsvFile
     */
    public void setNdcCsvFile(NdcCsvFile pNdcCsvFile) {
        this.ndcCsvFile = pNdcCsvFile;
    }

    
    /**
     * gets the Oi CsvFile
     * @return OiCsvFile OiCsvFile
     * 
     */
    public OiCsvFile getOiCsvFile() {
        return oiCsvFile;
    }

    /**
     * sets the Oi CsvFile
     * @param pOiCsvFile pOiCsvFile
     */
    public void setOiCsvFile(OiCsvFile pOiCsvFile) {
        this.oiCsvFile = pOiCsvFile;
    }

    /**
     * gets the Product Csv File
     * @return ProductCsvFile ProductCsvFile
     */
    public ProductCsvFile getProductCsvFile() {
        return productCsvFile;
    }

    /**
     * sets t he ProductCsvFile
     *
     * @param pProductCsvFile pProductCsvFile
     */
    public void setProductCsvFile(ProductCsvFile pProductCsvFile) {
        this.productCsvFile = pProductCsvFile;
    }

    /**
     * gets the ProductMultipartFile
     * @return MultipartFile MultipartFile
     */
    public MultipartFile getProductMultipartFile() {
        return productMultipartFile;
    }

    /**
     * sets the ProductMultipartFile
     * @param pProductMultipartFile pProductMultipartFile
     */
    public void setProductMultipartFile(MultipartFile pProductMultipartFile) {
        this.productMultipartFile = pProductMultipartFile;
    }

    
    /**
     * gets the ProductVoMap
     *@return map
     */
    public Map<String, ProductVo> getProductVoMap() {
        return productVoMap;
    }

    /**
     * sets the  ProductVoMa
     * @param productVoMap productVoMaps
     */
    public void setProductVoMap(Map<String, ProductVo> productVoMap) {
        this.productVoMap = productVoMap;
    }

    /**
     * gets theResetNationalDatabaseDomainCapability
     * @return ResetNationalDatabaseDomainCapability ResetNationalDatabaseDomainCapability
     */
    public ResetNationalDatabaseDomainCapability getResetNationalDatabaseDomainCapability() {
        return resetNationalDatabaseDomainCapability;
    }

    /**
     * sets the ResetNationalDatabaseDomainCapability for the MigrationCsvServiceImpl class.
     * @param pResetNationalDatabaseDomainCapability pResetNationalDatabaseDomainCapability
     */
    public void setResetNationalDatabaseDomainCapability(ResetNationalDatabaseDomainCapability
            pResetNationalDatabaseDomainCapability) {
        this.resetNationalDatabaseDomainCapability = pResetNationalDatabaseDomainCapability;
    }

    /**
     * gets the ProductDomainCapability
     * @return ProductDomainCapability ProductDomainCapability
     */
    public ProductDomainCapability getProductDomainCapability() {
        return productDomainCapability;
    }

    /**
     * sets the ProductDomainCapability
     * @param pProductDomainCapability pProductDomainCapability
     */
    public void setProductDomainCapability(ProductDomainCapability pProductDomainCapability) {
        this.productDomainCapability = pProductDomainCapability;
    }

    /**
     * gets the NdcDomainCapability
     * @return NdcDomainCapability NdcDomainCapability
     */
    public NdcDomainCapability getNdcDomainCapability() {
        return ndcDomainCapability;
    }

    /**
     * sets the NdcDomainCapability
     * @param pNdcDomainCapability pNdcDomainCapability
     */
    public void setNdcDomainCapability(NdcDomainCapability pNdcDomainCapability) {
        this.ndcDomainCapability = pNdcDomainCapability;
    }

    /**
     * gets the OrderableItemDomainCapability
     * @return OrderableItemDomainCapability
     */
    public OrderableItemDomainCapability getOrderableItemDomainCapability() {
        return orderableItemDomainCapability;
    }

    /**
     * sets the OrderableItemDomainCapability
     * 
     * @param pOrderableItemDomainCapability pOrderableItemDomainCapability
     */
    public void setOrderableItemDomainCapability(OrderableItemDomainCapability pOrderableItemDomainCapability) {
        this.orderableItemDomainCapability = pOrderableItemDomainCapability;
    }

    /**
     * gets the ExportState
     * @return MigrationExportState
     */
    public MigrationExportState getExportState() {
        return exportState;
    }

    /**
     * sets the sExportState
     * @param pExportState pExportState
     */
    public void setExportState(MigrationExportState pExportState) {
        this.exportState = pExportState;
    }

    /**
     * gets the TransactionManager
     * @return PlatformTransactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * sets the TransactionManager in the MigrationCsvServiceImpl.
     *
     * @param pTransactionManager pTransactionManager
     */
    public void setTransactionManager(PlatformTransactionManager pTransactionManager) {
        this.transactionManager = pTransactionManager;
    }

}
