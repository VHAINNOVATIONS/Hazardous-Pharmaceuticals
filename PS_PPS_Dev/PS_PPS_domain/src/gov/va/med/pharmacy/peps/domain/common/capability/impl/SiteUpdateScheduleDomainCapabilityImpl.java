/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.SiteUpdateScheduleDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplSiteUpdateScheduleDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplSiteUpdateScheduleDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.SiteUpdateScheduleConverter;


/**
 * Perform CRUD operations on SiteUpdateSchedules
 */
public class SiteUpdateScheduleDomainCapabilityImpl implements SiteUpdateScheduleDomainCapability {

    private EplSiteUpdateScheduleDao siteUpdateScheduleDao;
    private SiteUpdateScheduleConverter siteUpdateScheduleConverter;

    /**
     * Retrieve all SiteUpdateScheduleVo for a given site number.
     * 
     * @param siteNumber siteNumber
     * @return List<SiteUpdateScheduleVo>
     */
    public List<SiteUpdateScheduleVo> retrieveSiteUpdateSchedule(String siteNumber) {

        Criteria criteria = siteUpdateScheduleDao.getCriteria();
        criteria.add(Restrictions.eq(EplSiteUpdateScheduleDo.SITE_NUMBER, siteNumber));
        criteria.addOrder(Order.desc(EplSiteUpdateScheduleDo.SCHEDULE_START_DTM));
        List<EplSiteUpdateScheduleDo> eplDos = criteria.list();
        List<SiteUpdateScheduleVo> voList = siteUpdateScheduleConverter.convert(eplDos);

        return voList;
    }

    /**
     * Retrieve all SiteUpdateScheduleVo for a given site number.
     * 
     * @param siteNumber siteNumber
     * @param softwareUpdateType software update type
     * @return SiteUpdateScheduleVo
     */
    public SiteUpdateScheduleVo retrieveNextScheduleStart(String siteNumber, String softwareUpdateType) {

        Criteria criteria = siteUpdateScheduleDao.getCriteria();
        criteria.add(Restrictions.eq(EplSiteUpdateScheduleDo.SITE_NUMBER, siteNumber));
        criteria.add(Restrictions.eq(EplSiteUpdateScheduleDo.SOFTWARE_UPDATE_TYPE, softwareUpdateType));
        criteria.add(Restrictions.ge(EplSiteUpdateScheduleDo.SCHEDULE_START_DTM, Calendar.getInstance().getTime()));
        criteria.addOrder(Order.asc(EplSiteUpdateScheduleDo.SCHEDULE_START_DTM));

        // the first one returned by the collection should have the smallest date
        List<EplSiteUpdateScheduleDo> schedules = criteria.list();
        EplSiteUpdateScheduleDo schedule = null;

        if (schedules != null && !schedules.isEmpty()) {
            schedule = schedules.get(0);
        }

        return siteUpdateScheduleConverter.convert(schedule);
    }

    /**
     * Retrieve all SiteUpdateScheduleVo.
     * 
     * @return List<SiteUpdateScheduleVo>
     */
    public List<SiteUpdateScheduleVo> retrieveSiteUpdateSchedule() {
        List<EplSiteUpdateScheduleDo> eplDos = siteUpdateScheduleDao.retrieve();
        List<SiteUpdateScheduleVo> voList = siteUpdateScheduleConverter.convert(eplDos);

        return voList;
    }

    /**
     * Retrieve all SiteUpdateScheduleVo in progress
     * 
     * @return List<SiteUpdateScheduleVo>
     */
    public List<SiteUpdateScheduleVo> retrieveSiteUpdateScheduleInProgress() {
        List<Criterion> allCriterias = new ArrayList<Criterion>();
        Criterion crit = null;
        crit = Restrictions.ilike(EplSiteUpdateScheduleDo.IN_PROGRESS, "Y");
        allCriterias.add(crit);

        List<EplSiteUpdateScheduleDo> items = siteUpdateScheduleDao.retrieve(allCriterias);

        return siteUpdateScheduleConverter.convert(items);
    }

    /**
     * set the Local console info
     * 
     * @param dao EplSiteUpdateScheduleDao
     */
    public void setEplSiteUpdateScheduleDao(EplSiteUpdateScheduleDao dao) {
        this.siteUpdateScheduleDao = dao;
    }

    /**
     * Insert the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo inserted value object with new ID
     */
    public SiteUpdateScheduleVo create(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user) {
        EplSiteUpdateScheduleDo eplDo = siteUpdateScheduleConverter.convert(siteUpdateScheduleVo);
        EplSiteUpdateScheduleDo returnedConsoleDo = siteUpdateScheduleDao.insert(eplDo, user);

        return siteUpdateScheduleConverter.convert(returnedConsoleDo);
    }

    /**
     * Update the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo siteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo updated Vo
     */
    public SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user) {
        EplSiteUpdateScheduleDo eplDo = siteUpdateScheduleConverter.convert(siteUpdateScheduleVo);
        siteUpdateScheduleDao.update(eplDo, user);

        return siteUpdateScheduleVo;
    }

    /**
     * Deletes SiteUpdateScheduleVo.
     */
    public void deleteAll() {
        siteUpdateScheduleDao.executeQuery("DELETE FROM EplSiteUpdateScheduleDo");
    }

    /**
     * Description
     * 
     * @param siteUpdateScheduleConverter siteUpdateScheduleConverter property
     */
    public void setSiteUpdateScheduleConverter(SiteUpdateScheduleConverter siteUpdateScheduleConverter) {
        this.siteUpdateScheduleConverter = siteUpdateScheduleConverter;
    }
}
