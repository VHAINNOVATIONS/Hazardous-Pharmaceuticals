/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Date;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.domain.common.model.EplDoseUnitDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DoseUnitConverter;

import junit.framework.TestCase;


/**
 * Test the {@link DoseUnitConverter}
 */
public class DoseUnitConverterTest extends TestCase {

    private static final String FDB_DOSE = "First database dose unit";
    private static final String VALUE = "Field value here";
    private static final Long EPL_ID = 9999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();
    private static final String ITEM_STATUS = "INACTIVE";
    private static final String REQUEST_STATUS = "REJECTED";
    private static final String REJECT_REASON_TEXT = "None";
    private static final RequestRejectionReason REQUEST_REJECT_REASON = RequestRejectionReason.INAPPROPRIATE_REQUEST;

    private DoseUnitConverter doseUnitConverter = new DoseUnitConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplDoseUnitDo createDo() {
        EplDoseUnitDo dataDo = new EplDoseUnitDo();

        dataDo.setDoseUnitName(VALUE);
        dataDo.setEplId(EPL_ID);
        dataDo.setInactivationDate(INACTIVATION_DATE);
        dataDo.setItemStatus(ITEM_STATUS);
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestStatus(REQUEST_STATUS);
        dataDo.setRevisionNumber(PPSConstants.L3);
        dataDo.setRequestRejectReason(REQUEST_REJECT_REASON.toString());

        return dataDo;
    }

    /**
     * Test conversion to value object
     */
    public void testToDoseUnitVoHasAllAttributes() {
        EplDoseUnitDo dataDo = createDo();
        DoseUnitVo objectVo = doseUnitConverter.convert(dataDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getDoseUnitName().toUpperCase(Locale.US), 
            objectVo.getDoseUnitName());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REQUEST_REJECT_REASON, objectVo.getRequestRejectionReason());
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private DoseUnitVo createVo() {
        DoseUnitVo objectVo = new DoseUnitVo();

        objectVo.setId(EPL_ID.toString());
        objectVo.setDoseUnitName(VALUE);
        objectVo.setFdbDoseUnit(FDB_DOSE);
        objectVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        objectVo.setItemStatus(ItemStatus.ACTIVE);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setRequestRejectionReason(REQUEST_REJECT_REASON);
        objectVo.setRevisionNumber(PPSConstants.L3);

        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        DoseUnitVo objectVo = createVo();
        EplDoseUnitDo dataDo = doseUnitConverter.convert(objectVo);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, objectVo.getRevisionNumber(), dataDo.getRevisionNumber().longValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, new Long(objectVo.getId()), dataDo.getEplId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, objectVo.getDoseUnitName(), dataDo.getDoseUnitName());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, objectVo.getFdbDoseUnit(), dataDo.getFirstDatabankDoseUnit());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, objectVo.getRequestItemStatus().name(), dataDo.getRequestStatus());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, objectVo.getItemStatus().name(), dataDo.getItemStatus());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, objectVo.getRejectionReasonText(), dataDo.getRejectReasonText());
        assertTrue(PPSConstants.SHOULD_BE_EQUAL, objectVo.getInactivationDate().equals(dataDo.getInactivationDate()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, (Long) objectVo.getRevisionNumber(), dataDo.getRevisionNumber());
        assertEquals("REQUEST_REJECT_REASON", REQUEST_REJECT_REASON.toString(), dataDo.getRequestRejectReason());
    }
}
