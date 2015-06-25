/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDispenseUnitDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DispenseUnitConverter;

import junit.framework.TestCase;


/**
 * Test the {@link DispenseUnitConverter}
 */
public class DispenseUnitConverterTest extends TestCase {

    private static final String NAME = "Field value here".toUpperCase();
    private static final Long EPL_ID = 999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();
    private static final String REJECT_REASON_TEXT = "None";
    private static final ItemStatus ITEM_STATUS = ItemStatus.INACTIVE;
    private static final RequestItemStatus REQUEST_ITEM_STATUS = RequestItemStatus.PENDING;
    private static final RequestRejectionReason REQUEST_REJECT_REASON = RequestRejectionReason.INAPPROPRIATE_REQUEST;     
    private static final Long REVISION_NUMBER = 3L;

    private DispenseUnitConverter dispenseUnitConverter = new DispenseUnitConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplVaDispenseUnitDo createDo() {
        EplVaDispenseUnitDo dataDo = new EplVaDispenseUnitDo();

        dataDo.setDispenseUnitName(NAME);
        dataDo.setEplId(EPL_ID);
        dataDo.setInactivationDate(INACTIVATION_DATE);
        dataDo.setItemStatus(ITEM_STATUS.name());
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestStatus(REQUEST_ITEM_STATUS.name());
        dataDo.setRequestRejectReason(REQUEST_REJECT_REASON.toString());
        dataDo.setRevisionNumber(Long.valueOf("3"));
        
        return dataDo;
    }

    /**
     * Test conversion to value object
     */
    public void testToDispenseUnitVoHasAllAttributes() {
        EplVaDispenseUnitDo dataDo = createDo();
        DispenseUnitVo objectVo = dispenseUnitConverter.convert(dataDo);

        assertEquals(" EPL_ID", EPL_ID.toString(), objectVo.getId());
        assertEquals(" DISP_UNIT_NAME", NAME, objectVo.getValue());
        assertEquals(" INACTIVATION_DATE", INACTIVATION_DATE, objectVo.getInactivationDate());
        assertEquals(" ITEM_STATUS", ITEM_STATUS, objectVo.getItemStatus());
        assertEquals(" REQUEST_ITEM_STATUS", REQUEST_ITEM_STATUS, objectVo.getRequestItemStatus());
        assertEquals(" REJECT_REASON_TEXT", REJECT_REASON_TEXT, objectVo.getRejectionReasonText());
        assertEquals(" REQUEST_REJECT_REASON", REQUEST_REJECT_REASON, objectVo.getRequestRejectionReason());
        assertEquals(" REVISION_NUMBER", REVISION_NUMBER.longValue(), objectVo.getRevisionNumber());
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private DispenseUnitVo createVo() {
        DispenseUnitVo objectVo = new DispenseUnitVo();

        objectVo.setId(EPL_ID.toString());
        objectVo.setValue(NAME);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestRejectionReason(REQUEST_REJECT_REASON);
        
        objectVo.setDispenseUnitIen("23423");
        objectVo.setItemStatus(ITEM_STATUS);
        objectVo.setRequestItemStatus(REQUEST_ITEM_STATUS);
        objectVo.setRevisionNumber(REVISION_NUMBER);
        
        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        DispenseUnitVo objectVo = createVo();
        EplVaDispenseUnitDo dataDo = dispenseUnitConverter.convert(objectVo);
        assertEquals("EPL_ID", EPL_ID, dataDo.getEplId());
        assertEquals("DISP_UNIT_NAME", NAME, dataDo.getDispenseUnitName());
        assertEquals("INACTIVATION_DATE", INACTIVATION_DATE, dataDo.getInactivationDate());
        assertEquals("ITEM_STATUS", ITEM_STATUS.name(), dataDo.getItemStatus());
        assertEquals("REQUEST_ITEM_STATUS", REQUEST_ITEM_STATUS.name(), dataDo.getRequestStatus());
        assertEquals("REJECT_REASON_TEXT", REJECT_REASON_TEXT, dataDo.getRejectReasonText());
        assertEquals("REQUEST_REJECT_REASON", REQUEST_REJECT_REASON.toString(), dataDo.getRequestRejectReason());
        assertEquals("REVISION_NUMBER", REVISION_NUMBER, dataDo.getRevisionNumber());
    }

}
