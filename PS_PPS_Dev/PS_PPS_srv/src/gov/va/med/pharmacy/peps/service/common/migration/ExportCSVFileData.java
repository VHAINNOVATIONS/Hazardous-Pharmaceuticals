/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * 
 * ExportCSVFileData
 *
 */
public enum ExportCSVFileData {
    
    /**
     * DOMAIN_MAPPING
     */
    DOMAIN_MAPPING("Domain Mapping File", PPSConstants.DOMAIN_MAPPING, PPSConstants.DOMAIN_MAPPING, 
        PPSConstants.MAPPING_PATH, PPSConstants.MAPPING_PATH),

    /**
     * NDC
     */
    NDC("NDC File", "ImportNdc.csv", "ExportNdc.csv", PPSConstants.DEVELOPMENT_PATH, PPSConstants.SERVER_PATH),

    /**
     * ORDERABLE_ITEMS
     */
    ORDERABLE_ITEMS("Orderable Item File", "ImportOi.csv", "ExportOi.csv", PPSConstants.DEVELOPMENT_PATH,
        PPSConstants.SERVER_PATH),

    /**
     * PRODUCTS
     */
    PRODUCT("Product File", "ImportProduct.csv", "ExportProduct.csv", PPSConstants.DEVELOPMENT_PATH, PPSConstants.SERVER_PATH);

    private String description;
    private String exportFileName;
    private String inportFileName;

    private String devPath;
    private String serverPath;

    /**
     * ExportCSVFileData
     * @param pFileDescription pFileDescription
     * @param pInportFileName pInportFileName
     * @param pExportFileName pExportFileName
     * @param pDevPath pDevPath
     * @param pServerPath pServerPath
     */
    ExportCSVFileData(String pFileDescription, String pInportFileName,
                          String pExportFileName, String pDevPath, String pServerPath) {
        this.description = pFileDescription;
        this.exportFileName = pExportFileName;
        this.inportFileName = pInportFileName;
        this.devPath = pDevPath;
        this.serverPath = pServerPath;
    }

    /**
     * getFileDescription
     * @return description
     */
    public String getFileDescription() {
        return description;
    }

    /**
     * getExportFileName
     * @return exportFileName
     */
    public String getExportFileName() {
        return exportFileName;
    }

    /**
     * exportFileName
     * @return inportFileName
     */
    public String getInportFileName() {
        return inportFileName;
    }

    /**
     * getDevPath
     * @return devPath
     */
    public String getDevPath() {
        return devPath;
    }

    /**
     * getServerPath
     * @return serverPath
     */
    public String getServerPath() {
        return this.serverPath;
    }

    /**
     * getFullExportPath
     * @return fullExportPath
     */
    public String getFullExportPath() {
        String path = "";

        if (System.getProperty("os.name").contains("win") || System.getProperty(PPSConstants.OS_NAME).contains("Win")) {
            path = System.getProperty(PPSConstants.USER_DIR).concat(this.getDevPath());
        } else {
            path = System.getProperty(PPSConstants.USER_DIR).concat(this.getServerPath());
        }

        return path.concat(this.getExportFileName());
    }

    /**
     * getFullInportPath
     * @return fullImportPath
     */
    public String getFullInportPath() {
        String path = "";

        if (System.getProperty(PPSConstants.OS_NAME).contains(PPSConstants.WIN)) {
            path = System.getProperty(PPSConstants.USER_DIR).concat(this.getDevPath());
        } else {
            path = System.getProperty(PPSConstants.USER_DIR).concat(this.getServerPath());
        }

        return path.concat(this.getInportFileName());
    }
}
