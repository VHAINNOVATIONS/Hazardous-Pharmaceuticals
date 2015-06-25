/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleTypeVo;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.HospitalLocationVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.WardMultipleVo;
import gov.va.med.pharmacy.peps.common.vo.WardSelectionVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplAdminScheduleDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplScheduleTypeDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.AdminScheduleConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.AdminScheduleTypeConverter;

import junit.framework.TestCase;


/**
 * Test the {@link AdminScheduleConverterConverter}
 */
public class AdminScheduleConverterTest extends TestCase {

    private static final Long EPL_ID = 999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();
    private static final String REJECT_REASON_TEXT = "None";
    private static final RequestRejectionReason REQUEST_REJECT_REASON = RequestRejectionReason.INAPPROPRIATE_REQUEST;
    private static final Long FREQUENCY_IN_MINUTES = 1L;
    private static final String ITEM_STATUS = "INACTIVE";
    private static final String REQUEST_STATUS = "REJECTED";
    private static final String OTHER_LANGUAGE_EXPANSION = "test1";
    private static final String OUTPATIENT_EXPANSION = "test2";
    private static final String STANDARD_ADMINISTRATION_TIMES = "test3";
    private static final String STANDARD_SHIFTS = "test4";
    private static final String PACKAGE_PREFIX = "test5";
    private static final String SCHEDULE_NAME = "SCHEDULE_NAME";
    private static final Long REVISION_NUMBER = 1L;
    private static final String CODE = "code";
    private static final String NAME = "name";

    private AdminScheduleConverter adminScheduleConverter = new AdminScheduleConverter();

    /**
     * setUp
     * 
     * @throws Exception 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        adminScheduleConverter.setAdminScheduleTypeConverter(new AdminScheduleTypeConverter());
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplAdminScheduleDo createDo() {
        EplAdminScheduleDo dataDo = new EplAdminScheduleDo();

        EplScheduleTypeDo type = new EplScheduleTypeDo();
        type.setCode(CODE);
        final long id = 3L;
        type.setId(id);
        type.setScheduleTypeName(NAME);

        dataDo.setEplScheduleType(type);
        dataDo.setFrequencyInMinutes(FREQUENCY_IN_MINUTES);

        dataDo.setOtherLanguageExpansion(OTHER_LANGUAGE_EXPANSION);
        dataDo.setPackagePrefix(PACKAGE_PREFIX);
        dataDo.setScheduleName(SCHEDULE_NAME);
        dataDo.setScheduleOutpatientExpansion(OUTPATIENT_EXPANSION);
        dataDo.setRevisionNumber(REVISION_NUMBER);
        dataDo.setStandardAdministrationTimes(STANDARD_ADMINISTRATION_TIMES);
        dataDo.setStandardShifts(STANDARD_SHIFTS);
        dataDo.setEplId(EPL_ID);
        dataDo.setInactivationDate(INACTIVATION_DATE);
        dataDo.setItemStatus(ITEM_STATUS);
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestStatus(REQUEST_STATUS);
        dataDo.setRequestRejectReason(REQUEST_REJECT_REASON.toString());

        return dataDo;
    }

    /**
     * Test conversion to value object
     */
    public void testToAdminScheduleConverterVoHasAllAttributes() {
        EplAdminScheduleDo dataDo = createDo();
        AdministrationScheduleVo objectVo = adminScheduleConverter.convert(dataDo);

        assertNotNull("ID should not be null", objectVo.getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, EPL_ID.toString(), objectVo.getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getScheduleName(), objectVo.getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestRejectReason(), objectVo.getRequestRejectionReason()
            .toString());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId().toString(), objectVo.getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplScheduleType().getCode(), objectVo.getAdminScheduleType()
            .getCode());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplScheduleType().getId().toString(), objectVo
            .getAdminScheduleType().getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplScheduleType().getCode(), objectVo.getAdminScheduleType()
            .getCode());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getFrequencyInMinutes(), objectVo.getFrequency());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getOtherLanguageExpansion(), objectVo.getOtherLanguageExpansion());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getPackagePrefix(), objectVo.getPackagePrefix());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRevisionNumber().longValue(), objectVo.getRevisionNumber());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getScheduleOutpatientExpansion(),
            objectVo.getScheduleOutpatientExpansion());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getStandardAdministrationTimes(),
            objectVo.getStandardAdministrationTimes());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getStandardShifts(), objectVo.getStandardShifts());
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private AdministrationScheduleVo createVo() {
        AdministrationScheduleVo objectVo = new AdministrationScheduleVo();

        AdministrationScheduleTypeVo scheduleType = new AdministrationScheduleTypeVo();
        scheduleType.setCode(CODE);
        scheduleType.setId("3");
        scheduleType.setValue(NAME);

        objectVo.setAdminScheduleType(scheduleType);
        objectVo.setFrequency(FREQUENCY_IN_MINUTES);

        objectVo.setOtherLanguageExpansion(OTHER_LANGUAGE_EXPANSION);
        objectVo.setPackagePrefix(PACKAGE_PREFIX);
        objectVo.setValue(SCHEDULE_NAME);
        objectVo.setScheduleOutpatientExpansion(OUTPATIENT_EXPANSION);
        objectVo.setRevisionNumber(REVISION_NUMBER);
        objectVo.setStandardAdministrationTimes(STANDARD_ADMINISTRATION_TIMES);
        objectVo.setStandardShifts(STANDARD_SHIFTS);
        objectVo.setId(EPL_ID.toString());
        objectVo.setItemStatus(ItemStatus.ACTIVE);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        objectVo.setRequestRejectionReason(REQUEST_REJECT_REASON);

        WardMultipleVo ward = new WardMultipleVo();

        WardSelectionVo wardSelection = new WardSelectionVo();
        wardSelection.setValue("ward");

        ward.setWardSelection(wardSelection);
        ward.setWardAdminTimes("wardAdminTimes");

        Collection<WardMultipleVo> wards = new ArrayList<WardMultipleVo>();
        wards.add(ward);
        objectVo.setWardMultiple(wards);

        HospitalLocationVo hosp = new HospitalLocationVo();
        Collection<HospitalLocationVo> hosps = new ArrayList<HospitalLocationVo>();
        hosps.add(hosp);

        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        AdministrationScheduleVo objectVo = createVo();
        EplAdminScheduleDo dataDo = adminScheduleConverter.convert(objectVo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getScheduleName(), objectVo.getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestRejectReason(), objectVo.getRequestRejectionReason()
            .toString());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId().toString(), objectVo.getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplScheduleType().getId().toString(), objectVo
            .getAdminScheduleType().getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getFrequencyInMinutes(), objectVo.getFrequency());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getOtherLanguageExpansion(), objectVo.getOtherLanguageExpansion());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getPackagePrefix(), objectVo.getPackagePrefix());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRevisionNumber().longValue(), objectVo.getRevisionNumber());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getScheduleOutpatientExpansion(),
            objectVo.getScheduleOutpatientExpansion());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getStandardAdministrationTimes(),
            objectVo.getStandardAdministrationTimes());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getStandardShifts(), objectVo.getStandardShifts());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplWards().size(), objectVo.getWardMultiple().size());

    }

}
