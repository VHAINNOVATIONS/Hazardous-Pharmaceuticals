/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.impl;


import org.springframework.context.ApplicationContext;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.domain.common.utility.impl.ManagedItemDomainCapabilityFactoryImpl;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.RulesCapability;
import gov.va.med.pharmacy.peps.service.common.utility.ManagedItemCapabilityFactory;


/**
 * Spring bean factory class which retrieves instances of Spring managed beans from the Spring {@link ApplicationContext}.
 */
public class ManagedItemCapabilityFactoryImpl extends ManagedItemDomainCapabilityFactoryImpl implements
    ManagedItemCapabilityFactory {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(ManagedItemCapabilityFactoryImpl.class);
    private static final String DEFAULT_RULES_CAPABILITY_SPRING_BEAN = "rulesCapability";
    private static final String RULES_CAPABILITY_SUFFIX = "RulesCapability";
    private static final String SERVICE_CAPABILITY_SUFFIX = "Capability";

    /**
     * Empty constructor
     */
    public ManagedItemCapabilityFactoryImpl() {
        super();
    }

    /**
     * Return the {@link RulesCapability} for the given {@link EntityType}.
     * 
     * @param <T> type of {@link RulesCapability}
     * @param entityType {@link EntityType} used to create Spring bean ID for {@link RulesCapability}
     * @return {@link RulesCapability} for the given {@link EntityType}
     */
    public <T extends RulesCapability> T getRulesCapability(EntityType entityType) {
        T rulesCapability = (T) getCapability(getRulesSpringBeanId(entityType));

        if (rulesCapability == null) {
            LOG.warn("No RulesCapability found in Spring ApplicationContext for EntityType '" + entityType
                + "'. Returning the DefaultRulesCapability instead.");
            rulesCapability = (T) getCapability(DEFAULT_RULES_CAPABILITY_SPRING_BEAN);
        }

        return rulesCapability;
    }

    /**
     * Return the {@link ManagedItemCapability} for the given {@link EntityType}.
     * 
     * @param <T> type of {@link ManagedItemCapability}
     * @param entityType EntityType used to create Spring bean ID for {@link ManagedItemCapability}
     * @return {@link ManagedItemCapability} for the given {@link EntityType}
     */
    public <T extends ManagedItemCapability> T getServiceCapability(EntityType entityType) {
        return (T) getCapability(getServiceSpringBeanId(entityType));
    }

    /**
     * Create the Reuls Capability Spring bean ID from the given {@link EntityType}.
     * 
     * @param entityType {@link EntityType} from which to create Spring bean ID
     * @return {@link String} Spring bean ID
     */
    private String getRulesSpringBeanId(EntityType entityType) {
        return toSpringBeanId(entityType) + RULES_CAPABILITY_SUFFIX;
    }

    /**
     * Create the Service Spring bean ID from the given {@link EntityType}.
     * 
     * @param entityType {@link EntityType} from which to create Spring bean ID
     * @return {@link String} Spring bean ID
     */
    private String getServiceSpringBeanId(EntityType entityType) {
        return toSpringBeanId(entityType) + SERVICE_CAPABILITY_SUFFIX;
    }
}
