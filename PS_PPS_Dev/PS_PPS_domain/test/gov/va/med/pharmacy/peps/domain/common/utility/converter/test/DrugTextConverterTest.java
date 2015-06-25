/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugTextType;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugTextDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DrugTextConverter;

import junit.framework.TestCase;


/**
 * Test fixture
 */
public class DrugTextConverterTest extends TestCase {

    private static final String VALUE = "FIELD VALUE HERE";
    private static final Long EPL_ID = 999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();
    private static final RequestItemStatus REQUEST_STATUS = RequestItemStatus.APPROVED;
    private static final String REJECT_REASON_TEXT = "None";
    private static final RequestRejectionReason REQUEST_REJECT_REASON = RequestRejectionReason.INAPPROPRIATE_REQUEST;
    private static final long REVISION_NUMBER = 3L;
    private static final String DRUG_TEXT = "Drug text here";
    private static final ItemStatus ITEM_STATUS = ItemStatus.INACTIVE;
    private static final DrugTextType DRUG_TEXT_TYPE = DrugTextType.NATIONAL;
    private DrugTextConverter drugTextConverter = new DrugTextConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private static DrugTextVo createVo() {
        DrugTextVo objectVo = new DrugTextVo();
        
        objectVo.setItemStatus(ITEM_STATUS);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setRequestItemStatus(REQUEST_STATUS);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestRejectionReason(REQUEST_REJECT_REASON);
        objectVo.setValue(VALUE);
        objectVo.setTextLocal(DRUG_TEXT);
        objectVo.setTextNational(DRUG_TEXT);
        objectVo.setId(EPL_ID.toString());
        objectVo.setRevisionNumber(REVISION_NUMBER);

        return objectVo;
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private static EplDrugTextDo createDo() {
        EplDrugTextDo dataDo = new EplDrugTextDo();

        dataDo.setInactivationDate(INACTIVATION_DATE);
        dataDo.setItemStatus(ITEM_STATUS.toString());
        dataDo.setRequestStatus(REQUEST_STATUS.toString());
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestRejectReason(REQUEST_REJECT_REASON.toString());
        dataDo.setDrugTextName(VALUE);
        dataDo.setDrugTextLocal(DRUG_TEXT);
        dataDo.setDrugTextType(DRUG_TEXT_TYPE.toString());
        dataDo.setDrugTextName(VALUE);
        dataDo.setEplId(EPL_ID);
        dataDo.setRevisionNumber(REVISION_NUMBER);

        return dataDo;
    }

    /**
     * Test
     */
    public void testToDrugTextVoHasAllAttributes() {
        DrugTextVo objectVo = drugTextConverter.convert(createDo());

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, INACTIVATION_DATE, objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, ITEM_STATUS, objectVo.getItemStatus());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REQUEST_STATUS, objectVo.getRequestItemStatus());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REJECT_REASON_TEXT, objectVo.getRejectionReasonText());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REQUEST_REJECT_REASON, objectVo.getRequestRejectionReason());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, VALUE, objectVo.getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, DRUG_TEXT, objectVo.getTextLocal());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, DRUG_TEXT_TYPE, objectVo.getDrugTextType());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, VALUE, objectVo.getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, EPL_ID, new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REVISION_NUMBER, objectVo.getRevisionNumber());

    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        EplDrugTextDo dataDo = drugTextConverter.convert(createVo());

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, INACTIVATION_DATE, dataDo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, ITEM_STATUS.toString(), dataDo.getItemStatus());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REQUEST_STATUS.toString(), dataDo.getRequestStatus());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REJECT_REASON_TEXT, dataDo.getRejectReasonText());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REQUEST_REJECT_REASON.toString(), dataDo.getRequestRejectReason());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, EPL_ID, new Long(dataDo.getEplId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, REVISION_NUMBER, dataDo.getRevisionNumber().longValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, VALUE, dataDo.getDrugTextName());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, DRUG_TEXT, dataDo.getDrugTextLocal());
    }
}
