/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.impl;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.ClassUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.utility.ManagedItemDomainCapabilityFactory;


/**
 * Implementation for ManagedItemDomainCapabilityFactory
 */
public class ManagedItemDomainCapabilityFactoryImpl implements ManagedItemDomainCapabilityFactory, ApplicationContextAware {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(ManagedItemDomainCapabilityFactoryImpl.class);

    private static final String DOMAIN_CAPABILITY_SUFFIX = "DomainCapability";

    private ApplicationContext applicationContext;

    /**
     * Empty constructor
     */
    public ManagedItemDomainCapabilityFactoryImpl() {
        super();
    }

    /**
     * Return the {@link ManagedItemCapability} for the given {@link EntityType}.
     * 
     * @param <T> type of {@link ManagedItemCapability}
     * @param entityType EntityType used to create Spring bean ID for {@link ManagedItemCapability}
     * @return {@link ManagedItemCapability} for the given {@link EntityType}
     */
    public <T extends ManagedItemDomainCapability> T getDomainCapability(EntityType entityType) {
        return (T) getCapability(getDomainSpringBeanId(entityType));
    }

    /**
     * Return the Spring managed instance for the given {@link DataAccessObject}.
     * 
     * @param <T> type of {@link DataAccessObject}
     * @param clazz {@link DataAccessObject}
     * @return {@link DataAccessObject}
     */
    public <T extends DataAccessObject> T getDataAccessObject(Class<T> clazz) {
        return (T) getCapability(ClassUtility.getSpringBeanId(clazz));
    }
    
    /**
     * Return the {@link ManagedItemCapability} for the given Spring bean ID.
     * 
     * @param <T> type of {@link ManagedItemCapability}
     * @param beanId {@link String} Spring bean ID to retrieve
     * @return {@link ManagedItemCapability} for the given Spring bean ID
     */
    protected final <T> T getCapability(String beanId) {
        T managedItemCapability = null;

        if (applicationContext.containsBean(beanId)) {
            managedItemCapability = (T) applicationContext.getBean(beanId);
        } else {
            LOG.debug("Unable to get Spring managed capability with bean ID '" + beanId + "'. Returnning null.");
        }

        return managedItemCapability;
    }

    /**
     * Create the Domain Spring bean ID from the given {@link EntityType}.
     * 
     * @param entityType {@link EntityType} from which to create Spring bean ID
     * @return {@link String} Spring bean ID
     */
    private String getDomainSpringBeanId(EntityType entityType) {
        return toSpringBeanId(entityType) + DOMAIN_CAPABILITY_SUFFIX;
    }

    /**
     * Convert the given {@link EntityType} into a Spring bean ID, minus the {@link #DOMAIN_CAPABILITY_SUFFIX} or
     * {@link #SERVICE_CAPABILITY_SUFFIX}.
     * 
     * @param entityType {@link EntityType} to convert
     * @return {@link String} Spring bean ID
     */
    protected final String toSpringBeanId(EntityType entityType) {
        String[] words = entityType.toString().toLowerCase().split("\\_");
        StringBuffer entityTypeString = new StringBuffer();

        for (String string : words) {
            entityTypeString.append(StringUtils.capitalize(string));
        }

        return StringUtils.uncapitalize(entityTypeString.toString());
    }

    /**
     * getApplicationContext
     * 
     * @return applicationContext property
     */
    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Set Spring ApplicationContext, used to retrieve capability classes.
     * 
     * Called by Spring as this class is instantiated.
     * 
     * @param applicationContext Spring ApplicationContext
     * 
     * @see org.springframework.context.ApplicationContextAware
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
