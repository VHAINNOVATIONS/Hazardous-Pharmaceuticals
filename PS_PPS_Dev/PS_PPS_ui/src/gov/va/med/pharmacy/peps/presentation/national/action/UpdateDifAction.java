/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.national.action;


import java.io.File;

import org.apache.commons.io.FileUtils;

import gov.va.med.pharmacy.peps.presentation.common.action.PepsAction;
import gov.va.med.pharmacy.peps.service.national.session.SiteUpdateScheduleService;


/**
 * National action to upload the DIF Updates file.
 */
public class UpdateDifAction extends PepsAction {
    private static final long serialVersionUID = 1L;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(UpdateDifAction.class);

    private SiteUpdateScheduleService siteUpdateScheduleService;

    private File upload;

    private String uploadContentType;

    private String uploadFileName;

    /**
     * Accesses the uploaded file and copies it so that it can be accessed and executed as part of the DIF update
     * 
     * @return SUCCESS
     */
    public String uploadFile() {
        if (upload != null) {

            // We have successfully received uploaded file, now copy to desired location
            try {
                byte[] bytes = FileUtils.readFileToByteArray(upload);
                siteUpdateScheduleService.saveUpdateFile(bytes);
            }
            catch (Exception e) {
                LOG.error("Error saving uploaded DIF update file!", e);
                
                return ERROR;
            }
        }

        return SUCCESS;
    }

    /**
     * Performs the update at national
     * 
     * @return SUCCESS
     */
    public String performDifUpdate() {
        if (siteUpdateScheduleService.performUpdate()) {
            return SUCCESS;
        }
        else {
            return ERROR;
        }
    }

    /**
     * Performs the update at national
     * 
     * @return SUCCESS
     */
    public String canPerformDifUpdate() {
        if (siteUpdateScheduleService.canPerformUpdate()) {
            return SUCCESS;
        }
        else {
            return ERROR;
        }
    }

    /**
     * Sets the upload file
     * 
     * @param upload file
     */
    public void setUpload(File upload) {
        this.upload = upload;
    }

    /**
     * There seems to be a bug in OGNL where it won't call setUpload(File), so I've made a new setUpload(File[]) and will
     * just get the first File in the array.
     * 
     * @param upload File array with File in first element of array to set
     */
    public void setUpload(File[] upload) {
        if (upload != null && upload.length > 0) {
            this.upload = upload[0];
        }
    }

    /**
     * Sets the upload file content type
     * 
     * @param uploadContentType type
     */
    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    /**
     * Sets the upload file name
     * 
     * @param uploadFileName name
     */
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    /**
     * 
     * @return managedItemService property
     */
    public SiteUpdateScheduleService getSiteUpdateScheduleService() {
        return siteUpdateScheduleService;
    }

    /**
     * 
     * @param siteUpdateScheduleService siteUpdateScheduleService property
     */
    public void setSiteUpdateScheduleService(SiteUpdateScheduleService siteUpdateScheduleService) {
        this.siteUpdateScheduleService = siteUpdateScheduleService;
    }

    /**
     * 
     * @return uploadContentType property
     */
    public String getUploadContentType() {
        return uploadContentType;
    }

    /**
     * 
     * @return uploadFileName property
     */
    public String getUploadFileName() {
        return uploadFileName;
    }
}