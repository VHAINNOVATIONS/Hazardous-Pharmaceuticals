/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.controller;


import gov.va.med.pharmacy.peps.service.common.migration.process.MigrationProcessState;


/**
 * MigrationTestUtils
 *
 */
public class MigrationTestUtils {

    /**
     * MigrationTestUtils
     */
    private MigrationTestUtils() {
        
    }
    
    /**
     * getMigrationProcessState
     * @return MigrationProcessState
     */
    public static MigrationProcessState getMigrationProcessState() {
        MigrationProcessState state = new MigrationProcessState();
        
        return state;
    }

}
