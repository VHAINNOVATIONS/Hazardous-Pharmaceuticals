/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSiteUpdateScheduleDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.SiteUpdateScheduleConverter;

import junit.framework.TestCase;


/**
 * Test {@link SiteUpdateScheduleConverter}
 */
public class SiteUpdateScheduleConverterTest extends TestCase {

    private static final Long ID = 9991L;
    private static final String SITE_NUMBER = "SiteNumber";
    private static final String SOFTWARE_UPDATE_TYPE = "SoftwareUpdateType";
    private static final String SOFTWARE_UPDATE_VERSION = "SoftwareUpdateVersion";
    private static final String IN_PROGRESS = "InProgress";
    private static final Date START_DTM = new Date();
    private static final Date END_DTM = new Date();
    private static final Date SCHEDULE_START_DTM = new Date();
    private static final String SCHEDULE_INTERVAL = "ScheduleInterval";
    private static final String MD5SUM = "Md5Sum";

    private EplSiteUpdateScheduleDo dataDo;
    private SiteUpdateScheduleVo objectVo;

    private SiteUpdateScheduleConverter siteUpdateScheduleConverter = new SiteUpdateScheduleConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplSiteUpdateScheduleDo createDo() {
        dataDo = new EplSiteUpdateScheduleDo();

        dataDo.setId(ID);
        dataDo.setSiteNumber(SITE_NUMBER);
        dataDo.setSoftwareUpdateType(SOFTWARE_UPDATE_TYPE);
        dataDo.setSoftwareUpdateVersion(SOFTWARE_UPDATE_VERSION);
        dataDo.setInProgress(IN_PROGRESS);
        dataDo.setStartDtm(START_DTM);
        dataDo.setEndDtm(END_DTM);
        dataDo.setScheduledStartDtm(SCHEDULE_START_DTM);
        dataDo.setScheduledInterval(SCHEDULE_INTERVAL);
        dataDo.setMd5sum(MD5SUM);

        return dataDo;
    }

    /**
     * test setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {
        dataDo = createDo();
        objectVo = createVo();
    }

    /**
     * Test
     */
    public void testToPharmacySystemVoHasAllAttributes() {
        objectVo = siteUpdateScheduleConverter.convert(dataDo);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SITE_NUMBER, objectVo.getSiteNumber());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SOFTWARE_UPDATE_TYPE, objectVo.getSoftwareUpdateType());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SOFTWARE_UPDATE_VERSION, objectVo.getSoftwareUpdateVersion());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, IN_PROGRESS, objectVo.getInProgress());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, START_DTM, objectVo.getStartDtm());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, END_DTM, objectVo.getEndDtm());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SCHEDULE_START_DTM, objectVo.getScheduleStartDtm());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SCHEDULE_INTERVAL, objectVo.getScheduleInterval());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, MD5SUM, objectVo.getMd5Sum());

    }

    /**
     * 
     * @return EplSiteUpdateScheduleVo
     */
    private SiteUpdateScheduleVo createVo() {
        objectVo = new SiteUpdateScheduleVo();

        objectVo.setId(ID);
        objectVo.setSiteNumber(SITE_NUMBER);
        objectVo.setSoftwareUpdateType(SOFTWARE_UPDATE_TYPE);
        objectVo.setSoftwareUpdateVersion(SOFTWARE_UPDATE_VERSION);
        objectVo.setInProgress(IN_PROGRESS);
        objectVo.setStartDtm(START_DTM);
        objectVo.setEndDtm(END_DTM);
        objectVo.setScheduleStartDtm(SCHEDULE_START_DTM);
        objectVo.setScheduleInterval(SCHEDULE_INTERVAL);
        objectVo.setMd5Sum(MD5SUM);

        return objectVo;
    }

    /**
     * Test
     */
    public void testToSiteUpdateScheduleVoHasAllAttributes() {
        dataDo = siteUpdateScheduleConverter.convert(objectVo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, ID, dataDo.getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SITE_NUMBER, dataDo.getSiteNumber());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SOFTWARE_UPDATE_TYPE, dataDo.getSoftwareUpdateType());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SOFTWARE_UPDATE_VERSION, dataDo.getSoftwareUpdateVersion());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, IN_PROGRESS, dataDo.getInProgress());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, START_DTM, dataDo.getStartDtm());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, END_DTM, dataDo.getEndDtm());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SCHEDULE_START_DTM, dataDo.getScheduledStartDtm());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SCHEDULE_INTERVAL, dataDo.getScheduledInterval());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, MD5SUM, dataDo.getMd5sum());

    }
}
