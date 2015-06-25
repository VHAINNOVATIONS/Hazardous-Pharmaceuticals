/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility;


import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;


/**
 * Interface for {@link ManagedItemDomainCapability} factory implementation.
 */
public interface ManagedItemDomainCapabilityFactory {

    /**
     * Return the {@link ManagedItemCapability} for the given {@link EntityType}.
     * 
     * @param <T> type of {@link ManagedItemCapability}
     * @param entityType EntityType used to create Spring bean ID for {@link ManagedItemCapability}
     * @return {@link ManagedItemCapability} for the given {@link EntityType}
     */
    <T extends ManagedItemDomainCapability> T getDomainCapability(EntityType entityType);

    /**
     * Return the Spring managed instance for the given {@link DataAccessObject}.
     * 
     * @param <T> type of {@link DataAccessObject}
     * @param clazz {@link DataAccessObject}
     * @return {@link DataAccessObject}
     */
    <T extends DataAccessObject> T getDataAccessObject(Class<T> clazz);
}
