/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import gov.va.med.pharmacy.peps.common.utility.ClassUtility;


/**
 * Provide test cases and stubs the ability to load a Spring ApplicationContext without having to know the location of the
 * Spring configuration files and having to know how to initialize Spring.
 */
public class SpringTestConfigUtility {
    private static final String[] NATIONAL_SPRING_CONFIG = {"classpath*:xml/spring/test/*Context.xml",
                                                            "classpath*:xml/national/spring/test/*Context.xml"};

    private static ApplicationContext NATIONAL_APPLICATION_CONTEXT;

    /**
     * Cannot instantiate
     */
    private SpringTestConfigUtility() {
        super();
    }


    /**
     * Get an instance of a Spring managed class for the National test configuration.
     * 
     * @param <T> return type
     * @param clazz Class of the bean to get, the name of the bean must be the initialCamelCase of the Class provided
     * @return Spring managed bean
     */
    public static <T> T getNationalSpringBean(Class<T> clazz) {
        return (T) getNationalSpringBean(ClassUtility.getSpringBeanId(clazz));
    }

    /**
     * Get an instance of a Spring managed class for the National test configuration.
     * 
     * @param <T> return type
     * @param beanId String ID of the Spring bean to get
     * @return Spring managed bean
     */
    public static <T> T getNationalSpringBean(String beanId) {
        return (T) getNationalApplicationContext().getBean(beanId);
    }

    

    /**
     * Get that National Spring ApplicationContext. This context uses the NationalEPL databsae.
     * 
     * @return National's Spring ApplicationContext
     */
    public static synchronized ApplicationContext getNationalApplicationContext() {
        if (NATIONAL_APPLICATION_CONTEXT == null) {
            NATIONAL_APPLICATION_CONTEXT = new ClassPathXmlApplicationContext(NATIONAL_SPRING_CONFIG);
        }

        return NATIONAL_APPLICATION_CONTEXT;
    }

}
