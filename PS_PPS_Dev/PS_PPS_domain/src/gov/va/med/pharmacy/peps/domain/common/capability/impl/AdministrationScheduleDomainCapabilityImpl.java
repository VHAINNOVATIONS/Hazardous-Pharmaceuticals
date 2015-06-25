/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.AdministrationScheduleDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplAdminScheduleDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplHospitalLocationDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplWardDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplAdminScheduleDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.AdminScheduleConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;


/**
 * perform CRUD operation on administration schedule
 */
public class AdministrationScheduleDomainCapabilityImpl extends
    ManagedDataDomainCapabilityImpl<AdministrationScheduleVo, EplAdminScheduleDo> implements
    AdministrationScheduleDomainCapability {

    private static String ADMIN_SCHEDULE_EPL_ID = "eplAdminSchedule.eplId";
    private EplAdminScheduleDao eplAdminScheduleDao;
    private EplHospitalLocationDao eplHospitalLocationDao;
    private EplWardDao eplWardDao;
    private AdminScheduleConverter adminScheduleConverter;

    /**
     * Insert the given AdministrationScheduleVo.
     * 
     * @param administrationSchedule AdministrationScheduleVo
     * @param user {@link UserVo} performing the action
     * @return AdministrationScheduleVo with new ID
     */
    @Override
    public AdministrationScheduleVo createWithoutDuplicateCheck(AdministrationScheduleVo administrationSchedule, UserVo user) {
        if (administrationSchedule.getId() == null) {
            administrationSchedule.setId(getSeqNumDomainCapability()
                .generateId(administrationSchedule.getEntityType(), user));
        }

        EplAdminScheduleDo obejctDo = adminScheduleConverter.convert(administrationSchedule);

        eplAdminScheduleDao.insert(obejctDo, user);

        if (obejctDo.getEplHospitalLocations() != null && obejctDo.getEplHospitalLocations().size() > 0) {
            eplHospitalLocationDao.insert(obejctDo.getEplHospitalLocations(), user);
        }

        if (obejctDo.getEplWards() != null && obejctDo.getEplWards().size() > 0) {
            eplWardDao.insert(obejctDo.getEplWards(), user);
        }

        return administrationSchedule;
    }

    /**
     * update the given AdministrationScheduleVo.
     * 
     * @param data AdministrationScheduleVo
     * @param user {@link UserVo} performing the action
     * @return AdministrationScheduleVo
     */
    @Override
    public AdministrationScheduleVo update(AdministrationScheduleVo data, UserVo user) {

        EplAdminScheduleDo object = adminScheduleConverter.convert(data);

        // delete all existing assocs and add the ones from the new Vo
        eplHospitalLocationDao.delete(ADMIN_SCHEDULE_EPL_ID, object.getEplId());
        eplHospitalLocationDao.insert(object.getEplHospitalLocations(), user);

        // delete all existing assocs and add the ones from the new Vo
        eplWardDao.delete(ADMIN_SCHEDULE_EPL_ID, object.getEplId());
        eplWardDao.insert(object.getEplWards(), user);

        eplAdminScheduleDao.update(object, user);

        return data;
    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(AdministrationScheduleVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplAdminScheduleDo.NAME, item.getValue()));

        return criteria;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplAdminScheduleDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public AdminScheduleConverter getConverter() {
        return adminScheduleConverter;
    }

    /**
     * setEplAdminScheduleDao
     * @param eplAdminScheduleDao eplAdminScheduleDao property
     */
    public void setEplAdminScheduleDao(EplAdminScheduleDao eplAdminScheduleDao) {
        this.eplAdminScheduleDao = eplAdminScheduleDao;
    }

    /**
     * setEplHospitalLocationDao
     * @param eplHospitalLocationDao eplHospitalLocationDao property
     */
    public void setEplHospitalLocationDao(EplHospitalLocationDao eplHospitalLocationDao) {
        this.eplHospitalLocationDao = eplHospitalLocationDao;
    }

    /**
     * setEplWardDao
     * @param eplWardDao eplWardDao property
     */
    public void setEplWardDao(EplWardDao eplWardDao) {
        this.eplWardDao = eplWardDao;
    }

    /**
     * setAdminScheduleConverter
     * @param adminScheduleConverter adminScheduleConverter property
     */
    public void setAdminScheduleConverter(AdminScheduleConverter adminScheduleConverter) {
        this.adminScheduleConverter = adminScheduleConverter;
    }

}
