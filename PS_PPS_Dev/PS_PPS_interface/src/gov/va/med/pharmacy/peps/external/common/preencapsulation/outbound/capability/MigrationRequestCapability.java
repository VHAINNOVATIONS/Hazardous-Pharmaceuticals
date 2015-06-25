/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability;


import org.springframework.stereotype.Component;

import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.MigrationResponseVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.VistaLinkConnectionUtility;


/**
 * Sends Updates to Vista Link
 */
@Component("migrationRequestCapability")
public interface MigrationRequestCapability {

    /**
     * Handle the request
     * 
     * @param file name of VA file
     * @param ienStart Starting IEN Record
     * @param recordCount Number of records
     * @param activeState 0-3
     * @return MigrationResponseVO
     */
    MigrationResponseVo migrationRequest(String file, String ienStart,
            int recordCount, int activeState);

    /**
     * Sets the VistaLinkConnectionUtility.
     * 
     * @param connection class that will be used to communicate with VistA.
     */
    void setVistaLinkConnectionUtility(
            VistaLinkConnectionUtility connection);

    /**
     * setEnvironmentUtility
     * @param environmentUtility environmentUtility property
     */
    void setEnvironmentUtility(EnvironmentUtility environmentUtility);

    // void sendItemToVista(ManagedItemVo item, Collection<Difference>
    // differences, UserVo user, ItemAction action,
    // boolean isLocal);
    // void sendNewItemToVista(ManagedItemVo item, UserVo user);
    // void sendModifiedItemToVista(ManagedItemVo item, Collection<Difference>
    // differences, UserVo user);

}
