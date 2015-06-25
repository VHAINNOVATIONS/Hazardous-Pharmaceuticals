/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.message.bean;


import javax.ejb.MessageDrivenContext;
import javax.jms.MessageListener;

import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.ejb.support.AbstractMessageDrivenBean;

import gov.va.med.pharmacy.peps.common.utility.ClassUtility;


/**
 * Abstract class handling PPS Message Driven Beans
 * 
 * @param <T> Java Interface class this MDB implements
 */
public abstract class AbstractPepsMessageDrivenBean<T> extends AbstractMessageDrivenBean implements MessageListener {
    private static final long serialVersionUID = 1l;
    private T service;

    /**
     * Empty constructor
     */
    public AbstractPepsMessageDrivenBean() {
        super();
    }

    /**
     * Start Spring ApplicationContext
     * 
     * @param messageDrivenContext Set the MBD context
     * 
     * @see org.springframework.ejb.support.AbstractMessageDrivenBean#setMessageDrivenContext(javax.ejb.MessageDrivenContext)
     */
    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) {
        super.setMessageDrivenContext(messageDrivenContext);
        setBeanFactoryLocator(ContextSingletonBeanFactoryLocator.getInstance());
        setBeanFactoryLocatorKey("businessBeanFactory");
    }

    /**
     * Set the service interface to the Spring configured bean
     */
    protected void onEjbCreate() {
        Class generic = ClassUtility.getGenericType(getClass());
        String className = ClassUtility.getSpringBeanId(generic);

        if (getBeanFactory().containsBean(className)) {
            this.service = (T) getBeanFactory().getBean(className);
        }
    }

    /**
     * Find the service for this class as a Spring bean. The Spring bean must be named after the class name.
     * 
     * I.e., gov.va.med.pharmacy.peps.service.common.session.bean.DomainServiceBean must have a Spring bean defined as
     * "domainService".
     * 
     * @return Object service implementation class from Spring. If no Spring bean was found, return null.
     */
    protected T getService() {
        return service;
    }
}
