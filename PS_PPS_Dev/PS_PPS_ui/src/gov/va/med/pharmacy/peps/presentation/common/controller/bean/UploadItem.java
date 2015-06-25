/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.bean;


import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * UploadItem's brief summary
 * 
 * Details of UploadItem's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class UploadItem {

    private String name;
    private CommonsMultipartFile fileData;

    /**
     * plain constructor
     *
     */
    public UploadItem() {

    }

    /**
     * getter for name value
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for value of name
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for fileData value
     *
     * @return the fileData
     */
    public CommonsMultipartFile getFileData() {
        return fileData;
    }

    /**
     * setter for value of fileData
     *
     * @param fileData the fileData to set
     */
    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }

}
