/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.domain.common.capability.SiteUpdateScheduleDomainCapability;


/**
 * SiteUpdateScheduleDomainCapabilityTest
 */
public class SiteUpdateScheduleDomainCapabilityTest extends DomainCapabilityTestCase {
    
    private static final String IN_PROGRESS = "Y";
    private static final Date END_DTM = new Date();
    private static final Date SCHEDULE_START_DTM = new Date();
    private static final String SCHEDULE_INTERVAL = "ScheduleInterval";
    private static final String MD5SUM = "Md5Sum";
    private static final String COTS = "COTS";
    private static final String UPDATE = "updateVersion1.0";
    private static final String S600 = "600";
    private static final String S999 = "999";
    
    private SiteUpdateScheduleDomainCapability nationalSiteUpdateScheduleDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        this.nationalSiteUpdateScheduleDomainCapability = getNationalCapability(SiteUpdateScheduleDomainCapability.class);
    }

    /**
     * This method buidlsVO
     * 
     * @param siteNumber String
     * @param dayOfMonth int
     * @param updateType String
     * @param updateVersion String
     * @return VaSiteUpdateScheduleVo
     */
    private SiteUpdateScheduleVo buildVo(String siteNumber, int dayOfMonth, String updateType, String updateVersion) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date startDate = cal.getTime();

        
        
        SiteUpdateScheduleVo dataVo = new SiteUpdateScheduleVo();

        dataVo.setSiteNumber(siteNumber);
        dataVo.setSoftwareUpdateType(updateType);
        dataVo.setSoftwareUpdateVersion(updateVersion);
        dataVo.setInProgress(IN_PROGRESS);
        dataVo.setStartDtm(startDate);
        dataVo.setEndDtm(END_DTM);
        
        if (updateType.equals(COTS)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1); 
            dataVo.setScheduleStartDtm(calendar.getTime());
        } else {
            dataVo.setScheduleStartDtm(SCHEDULE_START_DTM);
        }
        
        dataVo.setScheduleInterval(SCHEDULE_INTERVAL);
        dataVo.setMd5Sum(MD5SUM);

        return dataVo;
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllSiteUpdateScheduleNational() throws Exception {

        List<SiteUpdateScheduleVo> rCollection = nationalSiteUpdateScheduleDomainCapability.retrieveSiteUpdateSchedule();
        SiteUpdateScheduleVo dataVo = buildVo("655", PPSConstants.I15, COTS, UPDATE);
        nationalSiteUpdateScheduleDomainCapability.create(dataVo, getTestUser());

        assertTrue("Collection returned correct number", rCollection.size() + 1 > rCollection.size());
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testCreateSiteUpdateSchedule() throws Exception {

        SiteUpdateScheduleVo vo = buildVo(S600, PPSConstants.I15, "updateType1", UPDATE);
        SiteUpdateScheduleVo returnedVo = nationalSiteUpdateScheduleDomainCapability.create(vo, getTestUser());
        assertNotNull("Returned with site Number", returnedVo.getSiteNumber());
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testUpdateSiteUpdateScheduleNational() throws Exception {

        List<SiteUpdateScheduleVo> schedules = nationalSiteUpdateScheduleDomainCapability.retrieveSiteUpdateSchedule();

        schedules.get(0).setInProgress("N");

        nationalSiteUpdateScheduleDomainCapability.update(schedules.get(0), getTestUser());

        List<SiteUpdateScheduleVo> retrievedUpdated = nationalSiteUpdateScheduleDomainCapability
            .retrieveSiteUpdateSchedule();

        int numInProgress = 0;

        for (SiteUpdateScheduleVo schedule : retrievedUpdated) {

            if (schedule.getInProgress().equals("Y")) {
                numInProgress++;
            }
        }

        assertEquals("Should be equal", 1, numInProgress);

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testRetrieveSiteUpdateScheduleSiteNumber() throws Exception {

        SiteUpdateScheduleVo vo = buildVo(S600, PPSConstants.I18, "updateType2", UPDATE);
        nationalSiteUpdateScheduleDomainCapability.create(vo, getTestUser());

        List<SiteUpdateScheduleVo> rCollection = nationalSiteUpdateScheduleDomainCapability.retrieveSiteUpdateSchedule();
        assertEquals("1.Should be 3 records total", rCollection.size(), PPSConstants.I3);

        List<SiteUpdateScheduleVo> returnedVo = nationalSiteUpdateScheduleDomainCapability.retrieveSiteUpdateSchedule(S600);
        assertEquals("Should be 2 records with site number 600", returnedVo.size(), 2);

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testRetrieveSiteUpdateScheduleInProgress() throws Exception {

        List<SiteUpdateScheduleVo> rCollection = nationalSiteUpdateScheduleDomainCapability.retrieveSiteUpdateSchedule();
        assertEquals("Should be 3 records total", rCollection.size(), PPSConstants.I3);

        List<SiteUpdateScheduleVo> returnedVos = nationalSiteUpdateScheduleDomainCapability
            .retrieveSiteUpdateScheduleInProgress();
        assertEquals("Should be 2 record in progress", returnedVos.size(), 2);

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testRetrieveNextFutureRow() throws Exception {
        SiteUpdateScheduleVo vo = buildVo(S999, PPSConstants.I18, COTS, UPDATE);
        nationalSiteUpdateScheduleDomainCapability.create(vo, getTestUser());

        SiteUpdateScheduleVo retrieved = nationalSiteUpdateScheduleDomainCapability.retrieveNextScheduleStart(S999, COTS);
        assertEquals("Should be COTS", retrieved.getSoftwareUpdateType(), COTS);

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testDeleteAllSiteUpdateSchedule() throws Exception {

        nationalSiteUpdateScheduleDomainCapability.deleteAll();

        List<SiteUpdateScheduleVo> names = nationalSiteUpdateScheduleDomainCapability.retrieveSiteUpdateSchedule();
        assertEquals("NO rows in database", names.size(), 0);

    }

}
