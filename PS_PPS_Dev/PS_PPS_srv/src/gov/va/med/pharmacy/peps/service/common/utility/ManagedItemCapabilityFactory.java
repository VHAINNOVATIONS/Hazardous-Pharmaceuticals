/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.domain.common.utility.ManagedItemDomainCapabilityFactory;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.RulesCapability;


/**
 * Interface for service, domain, and rule factories.
 */
public interface ManagedItemCapabilityFactory extends ManagedItemDomainCapabilityFactory {

    /**
     * Return the {@link RulesCapability} for the given {@link EntityType}.
     * 
     * @param <T> type of {@link RulesCapability}
     * @param entityType {@link EntityType} used to create Spring bean ID for {@link RulesCapability}
     * @return {@link RulesCapability} for the given {@link EntityType}
     */
    <T extends RulesCapability> T getRulesCapability(EntityType entityType);

    /**
     * Return the {@link ManagedItemCapability} for the given {@link EntityType}.
     * 
     * @param <T> type of {@link ManagedItemCapability}
     * @param entityType EntityType used to create Spring bean ID for {@link ManagedItemCapability}
     * @return {@link ManagedItemCapability} for the given {@link EntityType}
     */
    <T extends ManagedItemCapability> T getServiceCapability(EntityType entityType);
}
