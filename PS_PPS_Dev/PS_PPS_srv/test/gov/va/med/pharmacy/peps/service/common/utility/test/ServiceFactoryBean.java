/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.test;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationUtility;
import gov.va.med.pharmacy.peps.common.utility.ClassUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * Spring FactoryBean class which retrieves instances of Spring managed beans from the {@link SpringTestConfigUtility}.
 */
public class ServiceFactoryBean extends AbstractFactoryBean {
    private static final String NATIONAL = "national";
    private static final String LOCAL_ONE = "localOne";
    private static final String LOCAL_TWO = "localTwo";

    private String environment = NATIONAL;
    private String beanId;
    private Class beanClass;

    /**
     * Get the Spring managed bean instance from the correct {@link #environment} ({@value #LOCAL_ONE}, {@value #LOCAL_TWO},
     * or {@value #NATIONAL}) using the {@link SpringTestConfigUtility}. Use the {@link #beanId} directly or derive the
     * beanId from the {@link #beanClass} set within the Spring configuration.
     * 
     * @return instance of the configured {@link #beanId} or {@link #beanClass}
     * 
     * @see org.springframework.beans.factory.config.AbstractFactoryBean#createInstance()
     * @see #setEnvironment(String)
     * @see #LOCAL_ONE
     * @see #LOCAL_TWO
     * @see #NATIONAL
     * @see #setBeanClass(Class)
     * @see #setBeanId(String)
     */
    @Override
    protected Object createInstance() {
        Object service;

        service = SpringTestConfigUtility.getNationalSpringBean(beanId);
        ProxyFactory factory = new ProxyFactory(service);
        factory.setInterfaces(service.getClass().getInterfaces());
        factory.addAdvice(new ServiceInterceptor());

        return factory.getProxy();
    }

    /**
     * getObjectType
     * @return {@link #beanClass} if it has been set, else null
     * 
     * @see org.springframework.beans.factory.config.AbstractFactoryBean#getObjectType()
     */
    @Override
    public Class getObjectType() {
        return beanClass;
    }

    /**
     * isLocalOneBean
     * @return boolean true if the {@link #environment} attribute is equal to {@value #LOCAL_ONE}.
     */
    public boolean isLocalOneBean() {
        return LOCAL_ONE.equals(environment);
    }

    /**
     * isLocalTwoBean
     * @return boolean true if the {@link #environment} attribute is equal to {@value #LOCAL_TWO}.
     */
    public boolean isLocalTwoBean() {
        return LOCAL_TWO.equals(environment);
    }

    /**
     * isNationalBean
     * @return boolean true if the {@link #environment} attribute is equal to {@value #NATIONAL}.
     */
    public boolean isNationalBean() {
        return NATIONAL.equals(environment);
    }

    /**
     * Set the environment from which to retrieve the provided {@link #beanId} or {@link #beanClass}. Possible values
     * include {@value #LOCAL_ONE}, {@value #LOCAL_TWO}, or {@value #NATIONAL}. The default value is {@value #NATIONAL}.
     * 
     * @param environment environment property
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * Set the ID of the Spring managed bean to retrieve. Either the {@link #beanId} or {@link #beanClass} are required. If
     * both are provided within the Spring configuration, the {@link #beanId} takes precedence.
     * 
     * @param beanId beanId property
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    /**
     * Set the {@link Class} from which the Spring managed bean ID will be derived via the
     * {@link ClassUtility#getSpringBeanId(Class)} method. Either the {@link #beanId} or {@link #beanClass} are required. If
     * both are provided within the Spring configuration, the {@link #beanId} takes precedence.
     * 
     * @param beanClass beanClass property
     */
    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;

        if (beanId == null || beanId.trim().length() == 0) {
            setBeanId(ClassUtility.getSpringBeanId(beanClass));
        }
    }


    /**
     * Execute the Service method in a blocking thread of its own. This better mimics EJB calls (which would run in a
     * separate WebLogic worker thread and potentially in on a completely separate machine) and also allows the
     * {@link TransactionSynchronizationUtility} to work with its ThreadLocal variable.
     */
    private class ServiceInterceptor implements MethodInterceptor {

        /**
         * Invoke the Service method in its own blocking thread.
         * 
         * @param methodInvocation {@link MethodInvocation} method to invoke
         * @return result from the method invocation
         * @throws Throwable if error
         * 
         * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
         */
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            ServiceCall serviceCall = new ServiceCall(methodInvocation);

            new Thread(serviceCall).start();

            while (!serviceCall.isFinished()) {
                Thread.sleep(PPSConstants.I100);
            }

            if (serviceCall.exceptionThrown()) {
                throw serviceCall.getThrowable();
            }

            return serviceCall.getResult();
        }
    }


    /**
     * Runnable used to invoke Service method. Includes the ability to know if the call is finished, if a Throwable was
     * thrown, and the return object.
     */
    private class ServiceCall implements Runnable {
        private boolean finished;
        private Object result;
        private MethodInvocation methodInvocation;
        private Throwable throwable;

        /**
         * Set the method to invoke.
         * 
         * @param methodInvocation {@link MethodInvocation}
         */
        public ServiceCall(MethodInvocation methodInvocation) {
            this.methodInvocation = methodInvocation;
        }

        /**
         * Execute the method. Store any Throwable caught and the return object. Finally, set finished to true.
         * 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                this.result = methodInvocation.proceed();
            } catch (Throwable t) {
                throwable = t;
            } finally {
                this.finished = true;
            }
        }

        /**
         * True if an exception was thrown during method invocation.
         * 
         * @return boolean true if an exception was thrown
         */
        public boolean exceptionThrown() {
            return throwable != null;
        }

        /**
         * 
         * @return finished property
         */
        public boolean isFinished() {
            return finished;
        }

        /**
         * 
         * @return result property
         */
        public Object getResult() {
            return result;
        }

        /**
         * 
         * @return throwable property
         */
        public Throwable getThrowable() {
            return throwable;
        }
    }
}
