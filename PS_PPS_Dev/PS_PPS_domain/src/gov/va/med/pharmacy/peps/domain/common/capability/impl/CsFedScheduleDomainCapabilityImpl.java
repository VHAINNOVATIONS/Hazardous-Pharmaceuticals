/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import org.hibernate.ObjectNotFoundException;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.domain.common.capability.CsFedScheduleDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplCsFedScheduleDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplCsFedScheduleDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.CsFedScheduleConverter;


/**
 * Perform CRUD operations on cs federal schedule
 */
public class CsFedScheduleDomainCapabilityImpl implements CsFedScheduleDomainCapability {

    private EplCsFedScheduleDao eplCsFedScheduleDao;

    private CsFedScheduleConverter csFedScheduleConverter;

    /**
     * Retrieve all possible fed schedules
     * 
     * @return List of fed schedules
     * 
     */
    public List<CsFedScheduleVo> retrieveDomain() {
        return csFedScheduleConverter.convert(eplCsFedScheduleDao.retrieve());
    }

    /**
     * Retrieve specified CsFedScheduleVo
     * 
     * @param id of unit to retrieve
     * @return CsFedScheduleVo
     * @throws ItemNotFoundException ItemNotFoundException
     * 
     */
    public CsFedScheduleVo retrieve(String id) throws ItemNotFoundException {
        EplCsFedScheduleDo unitDo = null;

        try {
            unitDo = eplCsFedScheduleDao.retrieve(new Long(id));

            return csFedScheduleConverter.convert(unitDo);
        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(ItemNotFoundException.ITEM_NOT_FOUND, new Object[] {id});

        }
    }

    /**
     * set the CS Federal Schedule property
     * 
     * @param eplCsFedScheduleDao eplCsFedScheduleDao property
     */
    public void setEplCsFedScheduleDao(EplCsFedScheduleDao eplCsFedScheduleDao) {
        this.eplCsFedScheduleDao = eplCsFedScheduleDao;
    }

    /**
     * setCsFedScheduleConverter
     * @param csFedScheduleConverter csFedScheduleConverter property
     */
    public void setCsFedScheduleConverter(CsFedScheduleConverter csFedScheduleConverter) {
        this.csFedScheduleConverter = csFedScheduleConverter;
    }

}
