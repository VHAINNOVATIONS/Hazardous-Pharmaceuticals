/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.UploadItem;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;
import gov.va.med.pharmacy.peps.service.common.session.NationalSettingService;


/**
 * SystemSettingsController's brief summary
 * 
 * Details of SystemSettingsController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Controller("systemSettingsController")
public class SystemSettingsController extends PepsController {

    private static final String PAGE_EDIT_HOME_SETTINGS = "editHomePage.go";

    private static final Logger LOG = Logger.getLogger(SystemSettingsController.class);

    private static final String IMG_EXT_JPG = ".jpg";
    private static final String IMG_EXT_PNG = ".png";
    
    private static final String DEFAULT_IMG = "images/VeteransPoster.jpg";
    private static final String CURR_IMG_NAME = "HomePage-current";
    private static final String CURRENT_IMG = "uploaded/image/" + CURR_IMG_NAME;
    private static final String DEFAULT_LINK = "#";
    private static final String DEFAULT_LINK_TITLE = "Full Story...";
    private static final String DEFAULT_TILE = "admin.home.edit";
    private static final String FIELD_PREFERENCES = "preferences";

    private static List<ImageSelector> imageSelectChoices = Arrays.asList(ImageSelector.DEFAULT, ImageSelector.CURRENT,
        ImageSelector.UPLOAD_NEW);

    @Autowired
    private NationalSettingService nationalSettingService;

    /**
     * Description
     *
     * @return List
     */
    @ModelAttribute("imageSelectChoices")
    public List<ImageSelector> getImageSelectionList() {
        
//        LOG.debug("returning image selection list.");

        return imageSelectChoices;
    }

    /**
     * Return the current custom image.  If it doesn't exist on the server, the default image will be returned.
     *
     * @param request HttpServletRequest
     * @return ResponseEntity<byte[]>
     * @throws IOException exception
     */
    @RequestMapping(value = "/" + CURRENT_IMG + ".go", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getHomePageImage(HttpServletRequest request) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        String mt = MediaType.IMAGE_JPEG.toString();
        
        byte[] rv = null;
        
//        LOG.debug("executing getHomePageImage");
        
        NationalSettingVo mediaType = this.nationalSettingService.retrieve(NationalSetting.HOME_IMG_MEDIA_TYPE.toString());
        
        if (mediaType != null) {
            mt = mediaType.getStringValue();
        }
        
        if (MediaType.IMAGE_PNG.toString().equals(mt)) {
            headers.setContentType(MediaType.IMAGE_PNG);
        }            
        
        // try to get the current uploaded custom image
        try {
            File f = new File(getImagePath(CURR_IMG_NAME));
            rv = FileUtils.readFileToByteArray(f);
        } catch (Exception e) {
            LOG.warn("Unable to read current custom image file. " + e.getLocalizedMessage());
        }
        
        // if we don't have a current uploaded image, use the default one that is provided by the application 
        if (rv == null || rv.length == 0) {
            try {
                InputStream is = request.getSession().getServletContext().getResourceAsStream("/" + DEFAULT_IMG);

                if (is != null) {
                    rv = IOUtils.toByteArray(is);
                }
            } catch (Exception e) {
                LOG.warn("Unable to read default image file. " + e.getLocalizedMessage());
            }
        }

        return new ResponseEntity<byte[]>(rv, headers, HttpStatus.OK);
    }

    /**
     * Description
     *
     * @param fileName String
     * @return String
     */
    private static String getImagePath(String fileName) {
        StringBuffer path = new StringBuffer();
        path.append(System.getProperty(PPSConstants.USER_DIR));

        if (System.getProperty(PPSConstants.OS_NAME).contains("Win")) {
            path.append(PPSConstants.DEVELOPMENT_PATH);
        } else {
            path.append(PPSConstants.SERVER_PATH);
        }

        String rv = path.append(fileName).toString();
        
//        LOG.debug("File: " + rv);
        
        return rv;
    }

    /**
     * createPreferences
     *
     * @return SystemSettingsController.Preferences
     */
    @ModelAttribute("preferences")
    public SystemSettingsController.Preferences createPreferences() {
        Preferences preferences = new Preferences();

        if (getUser() == null) {
            return preferences;
        }

        preferences.setBody(null);
        preferences.setTitle(null);
        preferences.setLink(DEFAULT_LINK);
        preferences.setLinkTitle(DEFAULT_LINK_TITLE);
        preferences.setImagePath(DEFAULT_IMG);
        preferences.setImageSelected(ImageSelector.DEFAULT);

        NationalSettingVo body = this.nationalSettingService.retrieve(NationalSetting.HOME_ANNOUNCE_BODY.toString());
        NationalSettingVo title = this.nationalSettingService.retrieve(NationalSetting.HOME_ANNOUNCE_TITLE.toString());
        NationalSettingVo link = this.nationalSettingService.retrieve(NationalSetting.HOME_ANNOUNCE_LINK.toString());
        NationalSettingVo linkTitle =
            this.nationalSettingService.retrieve(NationalSetting.HOME_ANNOUNCE_LINK_TITLE.toString());
        NationalSettingVo imagePath = this.nationalSettingService.retrieve(NationalSetting.HOME_IMG_PATH.toString());
        NationalSettingVo imageSelected =
            this.nationalSettingService.retrieve(NationalSetting.HOME_IMG_SELECTED.toString());

        if (body != null && body.getStringValue() != null && !body.getStringValue().trim().isEmpty()) {
            preferences.setBody(body.getStringValue().trim());
        }

        if (title != null && title.getStringValue() != null && !title.getStringValue().trim().isEmpty()) {
            preferences.setTitle(title.getStringValue().trim());
        }

        if (link != null && link.getStringValue() != null && !link.getStringValue().trim().isEmpty()) {
            preferences.setLink(link.getStringValue().trim());
        }

        if (linkTitle != null && linkTitle.getStringValue() != null && !linkTitle.getStringValue().trim().isEmpty()) {
            preferences.setLinkTitle(linkTitle.getStringValue().trim());
        }

        if (imagePath != null && imagePath.getStringValue() != null && !imagePath.getStringValue().trim().isEmpty()) {
            preferences.setImagePath(imagePath.getStringValue().trim());
        }

        if (imageSelected != null && imageSelected.getStringValue() != null
            && !imageSelected.getStringValue().trim().isEmpty()) {
            preferences.setImageSelected(ImageSelector.valueOf(imageSelected.getStringValue().trim()));
        }

        return preferences;
    }

    /**
     * Description
     *
     * @param prefs Preferences 
     * @param model Model
     * @return String
     * @throws ValidationException exception
     */
    @RequestMapping(value = PAGE_EDIT_HOME_SETTINGS, method = RequestMethod.GET)
    @RoleNeeded(roles = { Role.PSS_PPSN_SUPERVISOR })
    public String getEditHomePage(
        @ModelAttribute(FIELD_PREFERENCES) Preferences prefs,
        Model model) throws ValidationException {

//        LOG.debug("edit the home page!");
        pageTrail.clearTrail();
        pageTrail.addPage("editHomePage", "Edit Home Page", true);

        String rv = DEFAULT_TILE;

        return rv;
    }

    /**
     * setEditHomePage
     *
     * @param prefs Preferences
     * @param file CommonsMultipartFile
     * @param model Model
     * @param request HttpServletRequest
     * @return String
     * @throws ValueObjectValidationException exception
     */
    @RequestMapping(value = PAGE_EDIT_HOME_SETTINGS, method = RequestMethod.POST)
    @RoleNeeded(roles = { Role.PSS_PPSN_SUPERVISOR })
    public String setEditHomePage(
        @ModelAttribute(FIELD_PREFERENCES) Preferences prefs,
        @RequestParam(value = "file", required = false) CommonsMultipartFile file,
        Model model,
        HttpServletRequest request) throws ValueObjectValidationException {

        pageTrail.clearTrail();
        pageTrail.addPage("editHomePage", "Edit Home Page", true);
        
        String rv = DEFAULT_TILE;

        if (prefs == null) {
            return rv;
        }

        Map<NationalSetting, NationalSettingVo> oldValues = new HashMap<NationalSetting, NationalSettingVo>();

        updateValue(oldValues, NationalSetting.HOME_ANNOUNCE_TITLE, prefs.getTitle());
        updateValue(oldValues, NationalSetting.HOME_ANNOUNCE_BODY, prefs.getBody());
        updateValue(oldValues, NationalSetting.HOME_ANNOUNCE_LINK_TITLE, prefs.getLinkTitle());
        updateValue(oldValues, NationalSetting.HOME_ANNOUNCE_LINK, prefs.getLink());

        if (ImageSelector.DEFAULT.equals(prefs.getImageSelected())) {
            
//            LOG.debug("DEFAULT Image Selector.");
            updateValue(oldValues, NationalSetting.HOME_IMG_SELECTED, ImageSelector.DEFAULT.toString());
            updateValue(oldValues, NationalSetting.HOME_IMG_PATH, DEFAULT_IMG);
        } else if (file != null && ImageSelector.UPLOAD_NEW.equals(prefs.getImageSelected())) {
            
//            LOG.debug("UPLOAD_NEW Image Selector.");
            String fileExt = getFileExt(file.getOriginalFilename());
            
            if (IMG_EXT_PNG.equals(fileExt)) {
                updateValue(oldValues, NationalSetting.HOME_IMG_MEDIA_TYPE, MediaType.IMAGE_PNG.toString());
            } else {
                updateValue(oldValues, NationalSetting.HOME_IMG_MEDIA_TYPE, MediaType.IMAGE_JPEG.toString());
            }
            
            UploadItem uploadItem = new UploadItem();
            uploadItem.setName(CURR_IMG_NAME);            
            uploadItem.setFileData(file);
            
//            LOG.debug("uploaded file: " + uploadItem.getName());
//            LOG.debug("uploaded file: " + uploadItem.getFileData());
            
            try {
                String filename = getImagePath(CURR_IMG_NAME);
                FileUtils.writeByteArrayToFile(new File(filename), uploadItem.getFileData().getBytes());
            } catch (IOException e) {
                LOG.error("Error writing image out.", e);
            }
            
            updateValue(oldValues, NationalSetting.HOME_IMG_SELECTED, ImageSelector.CURRENT.toString());
            updateValue(oldValues, NationalSetting.HOME_IMG_PATH, CURRENT_IMG);
        } else {
            
//            LOG.debug("CURRENT Image Selector.");
            updateValue(oldValues, NationalSetting.HOME_IMG_SELECTED, ImageSelector.CURRENT.toString());
            updateValue(oldValues, NationalSetting.HOME_IMG_PATH, CURRENT_IMG);
        }

        return "redirect:/" + PAGE_EDIT_HOME_SETTINGS;
    }

    /**
     * getFileExt
     *
     * @param filename String
     * @return String
     */
    private String getFileExt(String filename) {
        String rv = IMG_EXT_JPG;
        
        if (filename.trim().toLowerCase().endsWith(".png")) {
            rv = IMG_EXT_PNG;
        }
        
        return rv;
    }
    
    /**
     * updateValue
     *
     * @param oldValues Map
     * @param key NationalSetting
     * @param value String
     */
    private void updateValue(Map<NationalSetting, NationalSettingVo> oldValues, NationalSetting key, String value) {

//        LOG.debug("Retrieve: " + key.toString());
        NationalSettingVo setting = nationalSettingService.retrieve(key.toString());
        
//        LOG.debug("Setting object: " + setting.toString());

        if (setting == null) {
            LOG.error("Setting '" + key.toString() + "' does not exist.");

            return;
        }

        oldValues.put(key, setting);

        if (value != null && !value.trim().isEmpty()) {
            setting.setKeyName(key.toString());
            setting.setStringValue(value);
        }

        nationalSettingService.update(setting, getUser());
    }

    /**
     * ImageSelector's brief summary
     * 
     * Details of ImageSelector's operations, special dependencies
     * or protocols developers shall know about when using the class.
     *
     */
    public enum ImageSelector {

        /** DEFAULT */
        DEFAULT("Default System Image", DEFAULT_IMG),

        /** CURRENT */
        CURRENT("Current Custom Image", CURRENT_IMG),

        /** UPLOAD_NEW */
        UPLOAD_NEW("Upload New Image", CURRENT_IMG);

        private String displayValue;
        private String imagePath;
        private String value;

        /**
         * constructor
         *
         * @param displayVal String
         * @param val String
         */
        private ImageSelector(String displayVal, String val) {
            setDisplayValue(displayVal);
            setImagePath(val);
            this.setValue(this.toString());
        }

        /**
         * getter for displayValue value
         *
         * @return the displayValue
         */
        public String getDisplayValue() {
            return displayValue;
        }

        /**
         * setter for value of displayValue
         *
         * @param displayValue the displayValue to set
         */
        public void setDisplayValue(String displayValue) {
            this.displayValue = displayValue;
        }

        /**
         * Description
         *
         * @return String
         */
        public String getImagePath() {
            return this.imagePath;
        }

        /**
         * Description
         *
         * @param val String
         */
        public void setImagePath(String val) {
            this.imagePath = val;
        }

        /**
         * getter for value value
         *
         * @return the value
         */
        public final String getValue() {
            return value;
        }

        /**
         * setter for value of value
         *
         * @param value the value to set
         */
        public final void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * Preferences's brief summary
     * 
     * Details of Preferences's operations, special dependencies
     * or protocols developers shall know about when using the class.
     *
     */
    public static class Preferences {

        private String title;
        private String body;
        private String link;
        private String linkTitle;
        private ImageSelector imageSelected;
        private String imagePath;
        private String mediaType;

        /**
         * getter for title value
         *
         * @return the title
         */
        public final String getTitle() {
            return title;
        }

        /**
         * setter for value of title
         *
         * @param title the title to set
         */
        public final void setTitle(String title) {
            this.title = title;
        }

        /**
         * getter for body value
         *
         * @return the body
         */
        public final String getBody() {
            return body;
        }

        /**
         * setter for value of body
         *
         * @param body the body to set
         */
        public final void setBody(String body) {
            this.body = body;
        }

        /**
         * getter for link value
         *
         * @return the link
         */
        public final String getLink() {
            return link;
        }

        /**
         * setter for value of link
         *
         * @param link the link to set
         */
        public final void setLink(String link) {
            this.link = link;
        }

        /**
         * getter for linkTitle value
         *
         * @return the linkTitle
         */
        public final String getLinkTitle() {
            return linkTitle;
        }

        /**
         * setter for value of linkTitle
         *
         * @param linkTitle the linkTitle to set
         */
        public final void setLinkTitle(String linkTitle) {
            this.linkTitle = linkTitle;
        }

        /**
         * getter for imgSelected value
         *
         * @return the imageSelected
         */
        public final ImageSelector getImageSelected() {
            return imageSelected;
        }

        /**
         * setter for value of imageSelected
         *
         * @param imageSelected the imageSelected to set
         */
        public final void setImageSelected(ImageSelector imageSelected) {
            this.imageSelected = imageSelected;
        }

        /**
         * getter for imagePath value
         *
         * @return the imagePath
         */
        public final String getImagePath() {
            return imagePath;
        }

        /**
         * setter for value of imagePath
         *
         * @param imagePath the imagePath to set
         */
        public final void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        
        /**
         * getter for mediaType value
         *
         * @return the mediaType
         */
        public final String getMediaType() {
            return mediaType;
        }

        
        /**
         * setter for value of mediaType
         *
         * @param mediaType the mediaType to set
         */
        public final void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }
    }

    /**
     * getter for nationalSettingService value
     *
     * @return the nationalSettingService
     */
    public final NationalSettingService getNationalSettingService() {
        return nationalSettingService;
    }

    /**
     * setter for value of nationalSettingService
     *
     * @param nationalSettingService the nationalSettingService to set
     */
    public final void setNationalSettingService(NationalSettingService nationalSettingService) {
        this.nationalSettingService = nationalSettingService;
    }
}
