/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.reports;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * ReportExportType
 */
public enum ReportExportType {

    /** CAPTURE_NDF_DATA */
    CAPTURE_NDF_DATA("Capture NDF Data", "ImportCaptureNDF.csv", "ExportCaptureNDF.csv", PPSConstants.DEVELOPMENT_PATH,
        PPSConstants.SERVER_PATH),

    /** ACTIVE_PRODUCTS_WITH_PROPOSED_INACTIVATION */
    ACTIVE_PRODUCTS_WITH_PROPOSED_INACTIVATION("Active Products with proposed inactivation", "ImportProductInactivation.csv",
        "ExportProductInactivation.csv", PPSConstants.DEVELOPMENT_PATH,
        PPSConstants.SERVER_PATH),

    /** LIST_INGREDIENTS */
    LIST_INGREDIENTS("List Ingredients", "ImportListIngredients.csv", "ExportListIngredients.csv",
        PPSConstants.DEVELOPMENT_PATH, PPSConstants.SERVER_PATH);

    private static final String OS_NAME = "os.name";
    private static final String USER_DIR = "user.dir";
    private static final String WIN1 = "win";
    private static final String WIN2 = "Win";
    
    private String description;
    private String exportFileName;
    private String inportFileName;

    private String devPath;
    private String serverPath;

    /**
     * ReportExportType
     * @param pFileDescription File Description
     * @param pInportFileName Import File Name
     * @param pExportFileName Export File Name
     * @param pDevPath Development Path
     * @param pServerPath Server Path
     */
    ReportExportType(String pFileDescription, String pInportFileName, String pExportFileName, String pDevPath,
        String pServerPath) {
        this.description = pFileDescription;
        this.exportFileName = pExportFileName;
        this.inportFileName = pInportFileName;
        this.devPath = pDevPath;
        this.serverPath = pServerPath;
    }

    /**
     * getFileDescription for ReportExportType.
     * @return description
     */
    public String getFileDescription() {
        return this.description;
    }

    /**
     * getExportFileName
     * @return exportFileName
     */
    public String getExportFileName() {
        return this.exportFileName;
    }

    /**
     * getInportFileName
     * @return importFileName
     */
    public String getInportFileName() {
        return this.inportFileName;
    }

    /**
     * getDevPath
     * @return devPath
     */
    public String getDevPath() {
        return this.devPath;
    }

    /**
     * getServerPath for the ReportExportType file.
     * @return serverPath
     */
    public String getServerPath() {
        return this.serverPath;
    }

    /**
     * getFullExportPath
     * @return full export path
     */
    public String getFullExportPath() {
        String path = "";

        if (System.getProperty(OS_NAME).contains(WIN1) || System.getProperty(OS_NAME).contains(WIN2)) {
            path = System.getProperty(USER_DIR).concat(this.getDevPath());
        } else {
            path = System.getProperty(USER_DIR).concat(this.getServerPath());
        }

        return path.concat(this.getExportFileName());
    }

    /**
     * getFullInportPath
     * @return full import path
     */
    public String getFullInportPath() {
        String path = "";

        if (System.getProperty(OS_NAME).contains(WIN1) || System.getProperty(OS_NAME).contains(WIN2)) {
            path = System.getProperty(USER_DIR).concat(this.getDevPath());
        } else {
            path = System.getProperty(USER_DIR).concat(this.getServerPath());
        }

        return path.concat(this.getInportFileName());
    }

}
