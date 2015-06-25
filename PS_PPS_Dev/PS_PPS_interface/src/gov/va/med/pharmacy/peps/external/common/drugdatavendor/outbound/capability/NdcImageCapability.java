/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability;


import java.io.File;


/**
 * Retrieve the NDC's image file name from FDB DIF.
 */
public interface NdcImageCapability {

    /**
     * Retrieve the NDC's image file name from FDB DIF.
     * <p>
     * If the search results in more than one PackagedDrug found, return the image from the first in the list.
     * <p>
     * If the search results in no PacakagedDrugs found, then return null.
     * 
     * @param ndc String NDC number, with dashes in either 5-4-2 or 6-4-2 format
     * @return String image file name
     */
    String getImageFileName(String ndc);

    /**
     * Update the images stored in the FDB DIF NDC images WAR file.
     * 
     * @param file FDB DIF ZIP update file
     */
    void updateNdcImages(File file);
}
