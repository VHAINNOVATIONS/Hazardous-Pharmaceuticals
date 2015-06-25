/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * MigrationTestSuite
 *
 */
@RunWith(value = Suite.class)
@SuiteClasses(value =   {
                            MigrationCSVServiceTest.class,
                            MigrationExportProcessTest.class,
                            MigrationServiceTest.class
                        }
)
    
public class MigrationTestSuite {

}
