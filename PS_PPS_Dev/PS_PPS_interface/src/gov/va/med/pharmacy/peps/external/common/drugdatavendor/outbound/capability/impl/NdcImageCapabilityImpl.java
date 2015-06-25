/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.impl;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.NdcImageCapability;

import firstdatabank.dif.DrugSearchFilter;
import firstdatabank.dif.Navigation;
import firstdatabank.dif.PackagedDrugs;


/**
 * Retrieve the NDC's image file name from FDB DIF.
 */
public class NdcImageCapabilityImpl implements NdcImageCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(NdcImageCapabilityImpl.class);

    private static final String LINUX_FDB_IMAGES_WAR_PROPERTY = "linux.fdb.images.war.path";
    private static final String WINDOWS_FDB_IMAGES_WAR_PROPERTY = "windows.fdb.images.war.path";
    private static final String JPG_FILE_EXTENSION = ".JPG";

    private Navigation navigation;
    private EnvironmentUtility environmentUtility;

    /**
     * Retrieve the NDC's image file name from FDB DIF.
     * <p>
     * If the search results in more than one PackagedDrug found, return the image from the first in the list.
     * <p>
     * If the search results in no PacakagedDrugs found, then return null.
     * 
     * @param ndc String NDC number, with dashes in either 5-4-2 or 6-4-2 format
     * @return String image file name, null if the NDC could not be found or if there is not an image
     * 
     * @see gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.NdcImageCapability#getImageFileName(
     *      java.lang.String)
     */
    public String getImageFileName(String ndc) {
        String stripped = ndc;

        if (stripped.length() == PPSConstants.I14) { // 6 + 4 + 2 + 2 dashes = 14 characters
            stripped = stripped.substring(1);
        }

        String fileName = null;

        try {
            PackagedDrugs packagedDrugs = navigation.NDCSearch(stripped, new DrugSearchFilter());

            if (packagedDrugs.count() > 0) {
                fileName = packagedDrugs.item(0).getImageFileName();
            }
        } catch (Exception e) {
            LOG.error("Unable to retrieve NDC image file name from FDB DIF. Using default 'no image found' image instead.",
                e);
        }

        return fileName;
    }

    /**
     * Update the images stored in the FDB DIF NDC images WAR file.
     * 
     * @param file FDB DIF ZIP update file
     */
    public void updateNdcImages(File file) {
        try {
            ZipFile zipFile = new ZipFile(file);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.toUpperCase(Locale.US).endsWith(JPG_FILE_EXTENSION)) {
                    InputStream in = zipFile.getInputStream(entry);
                    File warFile = new File(getFdbImagesWarPath(), name);
                    FileOutputStream out = new FileOutputStream(warFile);
                    IOUtils.copy(in, out);
                }
            }

            zipFile.close();
        } catch (Exception e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.DRUG_DATA_VENDOR);
        }
    }

    /**
     * Determine if we're running in Windows or Linux and return the appropriate File path for the FDB DIF NDC images WAR
     * folder.
     * 
     * @return String path to FDB DIF NDC Images exploded WAR folder
     */
    protected String getFdbImagesWarPath() {
        String filePath;

        if (environmentUtility.isWindows()) {
            filePath = PropertyUtility.getProperty(NdcImageCapabilityImpl.class, WINDOWS_FDB_IMAGES_WAR_PROPERTY);
        } else {
            filePath = PropertyUtility.getProperty(NdcImageCapabilityImpl.class, LINUX_FDB_IMAGES_WAR_PROPERTY);
        }

        return filePath;
    }

    /**
     * setNavigation
     * @param navigation property
     */
    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    /**
     * setEnvironmentUtility
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }
}
