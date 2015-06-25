/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.domain.common.model.EplCsFedScheduleDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.CsFedScheduleConverter;

import junit.framework.TestCase;


/**
 * Test fixture
 */
public class CsFedScheduleConverterTest extends TestCase {

    private static final String VALUE = "Field value here";
    private static final Long EPL_ID = 999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();
    private static final RequestItemStatus REQUEST_STATUS = RequestItemStatus.APPROVED;
    private static final String REJECT_REASON_TEXT = "None";
    private static final String REQUEST_REJECT_REASON = "INAPPROPRIATE REQUEST";

    private static final ItemStatus ITEM_STATUS = ItemStatus.INACTIVE;
    private static final long REVISION_NUMBER = 3L;

    private CsFedScheduleConverter csFedScheduleConverter = new CsFedScheduleConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private static CsFedScheduleVo createVo() {
        CsFedScheduleVo objectVo = new CsFedScheduleVo();

        objectVo.setId(EPL_ID.toString());
        objectVo.setValue(VALUE);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setItemStatus(ITEM_STATUS);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestRejectReason(REQUEST_REJECT_REASON);
        objectVo.setRequestItemStatus(REQUEST_STATUS);
        objectVo.setRevisionNumber(REVISION_NUMBER);

        return objectVo;
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private static EplCsFedScheduleDo createDo() {
        EplCsFedScheduleDo dataDo = new EplCsFedScheduleDo();

        dataDo.setScheduleName(VALUE);
        dataDo.setEplId(EPL_ID);
        dataDo.setInactivationDate(INACTIVATION_DATE);
        dataDo.setItemStatus(ITEM_STATUS.name());
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestStatus(REQUEST_STATUS.name());
        dataDo.setRequestRejectReason(REQUEST_REJECT_REASON);
        dataDo.setRevisionNumber(REVISION_NUMBER);

        return dataDo;
    }

    /**
     * Test
     */
    public void testToGenericNameVoHasAllAttributes() {
        CsFedScheduleVo objectVo = csFedScheduleConverter.convert(createDo());

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, VALUE, objectVo.getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, EPL_ID, new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, INACTIVATION_DATE, objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, ITEM_STATUS.name(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REQUEST_STATUS.name(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REJECT_REASON_TEXT, objectVo.getRejectionReasonText());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REQUEST_REJECT_REASON, objectVo.getRequestRejectReason().toString());
    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        EplCsFedScheduleDo dataDo = csFedScheduleConverter.convert(createVo());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRevisionNumber().longValue(), REVISION_NUMBER);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getScheduleName(), VALUE);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), EPL_ID);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getInactivationDate(), INACTIVATION_DATE);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), ITEM_STATUS.name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), REQUEST_STATUS.name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestRejectReason(), REQUEST_REJECT_REASON);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), REJECT_REASON_TEXT);
    }
}
