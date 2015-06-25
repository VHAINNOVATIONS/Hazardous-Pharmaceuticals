/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.LocalMedicationRouteDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplLocalMedRouteDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplPackageUseAssocDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalMedRouteDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.LocalMedicationRouteConverter;


/**
 * Perform CRUD operations on local medication routes
 */
public class LocalMedicationRouteDomainCapabilityImpl extends
    ManagedDataDomainCapabilityImpl<LocalMedicationRouteVo, EplLocalMedRouteDo> implements
    LocalMedicationRouteDomainCapability {

    private EplLocalMedRouteDao eplLocalMedRouteDao;
    private EplPackageUseAssocDao eplPackageUseAssocDao;
    private LocalMedicationRouteConverter localMedicationRouteConverter;

    /**
     * Insert the given LocalMedicationRouteVo.
     * 
     * @param route LocalMedicationRouteVo
     * @param user {@link UserVo} performing the action
     * @return LocalMedicationRouteVo with new ID
     */
    @Override
    public LocalMedicationRouteVo createWithoutDuplicateCheck(LocalMedicationRouteVo route, UserVo user) {
        if (route.getId() == null) {
            route.setId(getSeqNumDomainCapability().generateId(route.getEntityType(), user));
        }

        EplLocalMedRouteDo routeDo = localMedicationRouteConverter.convert(route);

        eplLocalMedRouteDao.insert(routeDo, user);

        if (routeDo.getEplPackageUseAssocs() != null && routeDo.getEplPackageUseAssocs().size() > 0) {
            eplPackageUseAssocDao.insert(routeDo.getEplPackageUseAssocs(), user);
        }

        return route;
    }

    /**
     * Update the given LocalMedicationRouteVo.
     * 
     * @param route LocalMedicationRouteVo
     * @param user {@link UserVo} performing the action
     * @return route LocalMedicationRouteVo
     */
    @Override
    public LocalMedicationRouteVo update(LocalMedicationRouteVo route, UserVo user) {
        EplLocalMedRouteDo routeDo = localMedicationRouteConverter.convert(route);

        // delete all existing assocs and add the ones from the new Vo
        eplPackageUseAssocDao.delete("key.eplIdLocalMedRouteFk", routeDo.getEplId());

        if (routeDo.getEplPackageUseAssocs() != null && routeDo.getEplPackageUseAssocs().size() > 0) {
            eplPackageUseAssocDao.insert(routeDo.getEplPackageUseAssocs(), user);
        }

        eplLocalMedRouteDao.update(routeDo, user);

        return route;

    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(LocalMedicationRouteVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplLocalMedRouteDo.NAME, item.getValue()));

        return criteria;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplLocalMedRouteDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public LocalMedicationRouteConverter getConverter() {
        return localMedicationRouteConverter;
    }

    /**
     * setEplLocalMedRouteDao
     * @param eplLocalMedRouteDao eplLocalMedRouteDao property
     */
    public void setEplLocalMedRouteDao(EplLocalMedRouteDao eplLocalMedRouteDao) {
        this.eplLocalMedRouteDao = eplLocalMedRouteDao;
    }

    /**
     * setEplPackageUseAssocDao
     * @param eplPackageUseAssocDao eplPackageUseAssocDao property
     */
    public void setEplPackageUseAssocDao(EplPackageUseAssocDao eplPackageUseAssocDao) {
        this.eplPackageUseAssocDao = eplPackageUseAssocDao;
    }

    /**
     * setLocalMedicationRouteConverter
     * @param localMedicationRouteConverter localMedicationRouteConverter property
     */
    public void setLocalMedicationRouteConverter(LocalMedicationRouteConverter localMedicationRouteConverter) {
        this.localMedicationRouteConverter = localMedicationRouteConverter;
    }
}
