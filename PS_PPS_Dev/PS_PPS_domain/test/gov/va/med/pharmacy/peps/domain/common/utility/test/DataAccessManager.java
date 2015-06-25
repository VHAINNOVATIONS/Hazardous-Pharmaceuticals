/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * DataAccessManager
 */
public class DataAccessManager {
    
    /**
     * CALLBACK
     */
    public static final String CALLBACK = "classpath*:xml/spring/test/Callback.xml";
    
    /**
     * CONTEXT1
     */
    public static final String CONTEXT1 = "classpath*:xml/spring/test/*Context.xml";
    
    /**
     * CONTEXT2
     */
    public static final String CONTEXT2 = "classpath*:xml/local/spring/test/*Context.xml";
    
    /**
     * TRANSACTION
     */
    public static final String TRANSACTION = "classpath*:xml/spring/test/Transaction.xml";
    
    /**
     * LOCAL_ONE_SPRING_CONFIG
     */
    public static final String[] LOCAL_ONE_SPRING_CONFIG = {
        CALLBACK,
        CONTEXT1,
        CONTEXT2,
        "classpath*:xml/local/spring/test/CommonContext-Local-1.xml",
        "classpath*:xml/local/spring/test/DomainContext-Local-1.xml",
        TRANSACTION};
    
    /**
     * LOCAL_TWO_SPRING_CONFIG
     */
    public static final String[] LOCAL_TWO_SPRING_CONFIG = {
        CALLBACK,
        CONTEXT1,
        CONTEXT2,
        "classpath*:xml/local/spring/test/CommonContext-Local-2.xml",
        "classpath*:xml/local/spring/test/DomainContext-Local-2.xml",
        TRANSACTION};
    
    /**
     * NATIONAL_SPRING_CONFIG
     */
    public static final String[] NATIONAL_SPRING_CONFIG = {
        "classpath*:xml/spring/CommonContext.xml",
        "classpath*:xml/spring/PresentationContext.xml",
        CALLBACK,
        CONTEXT1,
        "classpath*:xml/national/spring/test/*Context.xml",
        TRANSACTION};


    
    private ApplicationContext localOneApplicationContext;
    private ApplicationContext localTwoApplicationContext;
    private ApplicationContext nationalApplicationContext;

    /**
     * Lazy load the Local-1 Spring ApplicationContext
     * 
     * @return Local-1 Spring ApplicationContext
     */
    public ApplicationContext getLocalOneApplicationContext() {
        if (localOneApplicationContext == null) {
            this.localOneApplicationContext = new ClassPathXmlApplicationContext(LOCAL_ONE_SPRING_CONFIG);
        }

        return localOneApplicationContext;
    }

    /**
     * Lazy load the Local-2 Spring ApplicationContext
     * 
     * @return Local-2 Spring ApplicationContext
     */
    public ApplicationContext getLocalTwoApplicationContext() {
        if (localTwoApplicationContext == null) {
            this.localTwoApplicationContext = new ClassPathXmlApplicationContext(LOCAL_TWO_SPRING_CONFIG);
        }

        return localTwoApplicationContext;
    }

    /**
     * Lazy load the National Spring ApplicationContext
     * 
     * @return National Spring ApplicationContext
     */
    public ApplicationContext getNationalApplicationContext() {
        if (nationalApplicationContext == null) {
            this.nationalApplicationContext = new ClassPathXmlApplicationContext(NATIONAL_SPRING_CONFIG);
        }

        return nationalApplicationContext;
    }
}
