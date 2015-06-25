/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.OiScheduleTypeVo;
import gov.va.med.pharmacy.peps.domain.common.capability.OiScheduleTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplOiScheduleTypeDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.OiScheduleTypeConverter;


/**
 * Perform CRUD operations on oi schedule types
 */
public class OiScheduleTypeDomainCapabilityImpl implements OiScheduleTypeDomainCapability {
    private EplOiScheduleTypeDao eplOiScheduleTypeDao;
    private OiScheduleTypeConverter oiScheduleTypeConverter;

    /**
     * Retrieve all oi schedule types
     * 
     * @return List of OiScheduleTypeVo
     * 
     */
    public List<OiScheduleTypeVo> retrieveDomain() {
        return oiScheduleTypeConverter.convert(eplOiScheduleTypeDao.retrieve());
    }

    /**
     * Retrieve an {@link OiScheduleTypeVo} by ID.
     * 
     * @param id String ID of {@link OiScheduleTypeVo} to retrieve
     * @return {@link OiScheduleTypeVo}
     */
    public OiScheduleTypeVo retrieve(String id) {
        return oiScheduleTypeConverter.convert(eplOiScheduleTypeDao.retrieve(Long.valueOf(id)));
    }

    /**
     * setEplOiScheduleTypeDao
     * @param eplOiScheduleTypeDao eplOiScheduleTypeDao property
     */
    public void setEplOiScheduleTypeDao(EplOiScheduleTypeDao eplOiScheduleTypeDao) {
        this.eplOiScheduleTypeDao = eplOiScheduleTypeDao;
    }

    /**
     * setOiScheduleTypeConverter
     * @param oiScheduleTypeConverter oiScheduleTypeConverter property
     */
    public void setOiScheduleTypeConverter(OiScheduleTypeConverter oiScheduleTypeConverter) {
        this.oiScheduleTypeConverter = oiScheduleTypeConverter;
    }
}
