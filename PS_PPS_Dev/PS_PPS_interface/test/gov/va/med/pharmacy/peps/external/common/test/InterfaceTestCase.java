/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import gov.va.med.pharmacy.peps.common.utility.ClassUtility;

import junit.framework.TestCase;


/**
 * Instantiate the Spring ApplicationContext for sub classes to use and provide methods to retrieve Spring managed beans from
 * the ApplicationContext.
 */
public class InterfaceTestCase extends TestCase { 

    /** SPRING_CONFIG */
    protected static final String[] SPRING_CONFIG = {
        "classpath*:xml/spring/test/*Context.xml", "classpath*:xml/local/spring/test/*Context.xml",
        "classpath*:xml/local/spring/test/CommonContext-Local-1.xml", "classpath*:xml/spring/test/Callback.xml" };

    private static final ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext(SPRING_CONFIG);

    /**
     * InterfaceTestCase
     */
    public InterfaceTestCase() {
        super();
    }
    
    /**
     * Retrieve the Spring bean with the given ID from the ApplicationContext.
     * <p>
     * Convert the Class name into a Spring bean ID and call {@link #getSpringBean(String)}
     * 
     * @param <T> Type of class the bean implements
     * @param clazz Class of bean to retrieve
     * @return instance from Spring ApplicationContext
     */
    protected <T> T getSpringBean(Class<T> clazz) {
        String beanId = ClassUtility.getSpringBeanId(clazz);

        return (T) getSpringBean(beanId);
    }

    /**
     * Retrieve the Spring bean with the given ID from the ApplicationContext.
     * 
     * @param <T> Type of class the bean implements
     * @param beanId Spring bean ID
     * @return instance from Spring ApplicationContext
     */
    protected <T> T getSpringBean(String beanId) {
        return (T) APPLICATION_CONTEXT.getBean(beanId);
    }
    
    /**
     * oneTest
     * @return 
     */
    public void testOne() {
        boolean isTrue = true;
        assertTrue("testing!", isTrue);
    }
}
