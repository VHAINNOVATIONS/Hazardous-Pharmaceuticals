/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility;


import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import gov.va.med.pharmacy.peps.common.utility.ClassUtility;


/**
 * Lookup a service in Spring's web application context.
 */
public class ApplicationContextUtility {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ApplicationContextUtility.class);


    /** 
     * Cannot instantiate
     */
    private ApplicationContextUtility() {
        super();
    }

    /**
     * Looks up a Spring managed bean by unqualified class name. If no Spring managed bean is found, null is returned.
     * 
     * @param <T> Type of the service to retrieve
     * @param servletContext ServletContext used to lookup the Spring WebApplicationContext
     * @param clazz interface Class of service to lookup
     * @return Object instance of service
     */
    public static <T> T getSpringBean(ServletContext servletContext, Class<T> clazz) {
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        String springBeanId = ClassUtility.getSpringBeanId(clazz);
        T bean = null;

        if (springContext.containsBean(springBeanId)) {
            LOG.trace("Getting Spring bean with ID '" + springBeanId + "' for " + clazz);

            bean = (T) springContext.getBean(springBeanId);
        } else {
            LOG.error("No Spring bean with ID '" + springBeanId + "' found for " + clazz);
        }

        return bean;
    }
}
