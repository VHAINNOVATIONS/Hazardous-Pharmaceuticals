/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleTypeVo;
import gov.va.med.pharmacy.peps.domain.common.capability.AdministrationScheduleTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplScheduleTypeDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.AdminScheduleTypeConverter;


/**
 * Perform CRUD operations with Administration Schedule Types
 */
public class AdministrationScheduleTypeDomainCapabilityImpl implements AdministrationScheduleTypeDomainCapability {

    private EplScheduleTypeDao eplScheduleTypeDao;
    private AdminScheduleTypeConverter adminScheduleTypeConverter;

    /**
     * Retrieve all administration schedule types
     * 
     * @return List of Administration schedule type
     * 
     */
    public List<AdministrationScheduleTypeVo> retrieveDomain() {

        return adminScheduleTypeConverter.convert(eplScheduleTypeDao.retrieve());
    }

    /**
     * setEplScheduleTypeDao
     * @param eplScheduleTypeDao eplScheduleTypeDao property
     */
    public void setEplScheduleTypeDao(EplScheduleTypeDao eplScheduleTypeDao) {
        this.eplScheduleTypeDao = eplScheduleTypeDao;
    }

    /**
     * setAdminScheduleTypeConverter
     * @param adminScheduleTypeConverter adminScheduleTypeConverter property
     */
    public void setAdminScheduleTypeConverter(AdminScheduleTypeConverter adminScheduleTypeConverter) {
        this.adminScheduleTypeConverter = adminScheduleTypeConverter;
    }

}
