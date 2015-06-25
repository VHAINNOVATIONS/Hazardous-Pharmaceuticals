/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import org.springframework.context.ApplicationContext;

import gov.va.med.pharmacy.peps.common.utility.ClassUtility;
import gov.va.med.pharmacy.peps.common.utility.impl.test.EnvironmentUtilityStub;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.utility.test.DataAccessManager;

import junit.framework.TestCase;


/**
 * Loads Spring ApplicationContexts for use in Domain unit test cases.
 * 
 * Abstract so that the class does not get instantiated alone.
 */
public abstract class DomainCapabilityTestCase extends TestCase { 
    private DataAccessManager dataAccessManager = new DataAccessManager();

//    /**
//     * Find the local-1 Spring bean for the given capability class.
//     * 
//     * Also calls the
//     * {@link EnvironmentUtilityStub#setLocalOne(ApplicationContext)} method to
//     * set the site number for
//     * local-1.
//     * 
//     * @param <T>
//     *            Type of class
//     * @param capability
//     *            Capability Class
//     * @return instance of capability configured in Spring
//     *         //
//     */
////    protected <T> T getLocalOneCapability(Class<T> capability) {
////        ApplicationContext appContext = dataAccessManager.getLocalOneApplicationContext();
//        return (T) appContext.getBean(ClassUtility.getSpringBeanId(capability));
//    }

//    /**
//     * Find the local-2 Spring bean for the given capability class.
//     * 
//     * Also calls the {@link EnvironmentUtilityStub#setLocalTwo(ApplicationContext)} method to set the site number for
//     * local-2.
//     * 
//     * @param <T> Type of class
//     * @param capability Capability Class
//     * @return instance of capability configured in Spring
//     */
//    protected <T> T getLocalTwoCapability(Class<T> capability) {
//        ApplicationContext appContext = dataAccessManager.getLocalTwoApplicationContext();
//
//        return (T) appContext.getBean(ClassUtility.getSpringBeanId(capability));
//    }

    /**
     * Find the national Spring bean for the given capability class.
     * 
     * Also calls the {@link EnvironmentUtilityStub#setNational(ApplicationContext)} method to set the site number for
     * national.
     * 
     * @param <T> Type of class
     * @param capability Capability Class
     * @return instance of capability configured in Spring
     */
    protected <T> T getNationalCapability(Class<T> capability) {
        ApplicationContext appContext = dataAccessManager.getNationalApplicationContext();

        return (T) appContext.getBean(ClassUtility.getSpringBeanId(capability));
    }
    
    /**
     * getTestUser
     * @return UserVo used during testing
     */
    protected UserVo getTestUser() {
        UserVo user = new UserGenerator().getNationalManagerOne();
        user.setFirstName("Domain Test Case First");
        user.setLastName("Domain Test Case Last");
        user.setLocation("Domain Test Case Location");
        user.setStationNumber("666");
        user.setUsername("domainTestCase");
        
        return user;
    }
}
