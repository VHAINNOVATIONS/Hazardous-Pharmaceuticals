/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.sql.Timestamp;
import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaGenNameDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.GenericNameConverter;

import junit.framework.TestCase;


/**
 * Test the {@link GenericNameConverter}
 */
public class GenericNameConverterTest extends TestCase {

    private static final String ERROR_MSG = "should be equal";
    private static final String VALUE = "FIELD VALUE HERE";
    private static final Long EPL_ID = 9999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();
    private static final String VUID = "vuid";
    private static final String ITEM_STATUS = "INACTIVE";
    private static final String REQUEST_STATUS = "REJECTED";
    private static final String REJECT_REASON_TEXT = "None";
    private static final RequestRejectionReason REQUEST_REJECT_REASON = RequestRejectionReason.INAPPROPRIATE_REQUEST;

    private GenericNameConverter genericNameConverter = new GenericNameConverter();

    /**
     * GenericNameConverterTest
     */
    public GenericNameConverterTest() {
        super();
    }
    
    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplVaGenNameDo createDo() {
        EplVaGenNameDo dataDo = new EplVaGenNameDo();

        dataDo.setGenericName(VALUE);
        dataDo.setEplId(EPL_ID);
        dataDo.setInactivationDate(new Timestamp(INACTIVATION_DATE.getTime()));
        dataDo.setItemStatus(ITEM_STATUS);
        dataDo.setVuid(VUID);
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestStatus(REQUEST_STATUS);
        dataDo.setRequestRejectReason(REQUEST_REJECT_REASON.toString());
        dataDo.setRevisionNumber(PPSConstants.L3);

        return dataDo;
    }

    /**
     * Test conversion to value object
     */
    public void testToGenericNameVoHasAllAttributes() {
        EplVaGenNameDo dataDo = createDo();
        GenericNameVo objectVo = genericNameConverter.convert(dataDo);

        assertEquals(ERROR_MSG, dataDo.getGenericName(), objectVo.getValue());
        assertEquals(ERROR_MSG, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(ERROR_MSG, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(ERROR_MSG, dataDo.getVuid(), objectVo.getVuid());
        assertEquals(ERROR_MSG, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(ERROR_MSG, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(ERROR_MSG, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
        assertEquals(ERROR_MSG, REQUEST_REJECT_REASON, objectVo.getRequestRejectionReason());
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private GenericNameVo createVo() {
        GenericNameVo objectVo = new GenericNameVo();

        objectVo.setId(EPL_ID.toString());
        objectVo.setValue(VALUE);
        objectVo.setValue(VUID);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setItemStatus(ItemStatus.ACTIVE);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestRejectionReason(REQUEST_REJECT_REASON);
        objectVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        objectVo.setRevisionNumber(PPSConstants.L3);

        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        GenericNameVo objectVo = createVo();
        EplVaGenNameDo dataDo = genericNameConverter.convert(objectVo);
        assertEquals(ERROR_MSG, dataDo.getRevisionNumber().longValue(), objectVo.getRevisionNumber());

        assertEquals(ERROR_MSG, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(ERROR_MSG, dataDo.getVuid(), objectVo.getVuid());
        assertEquals(ERROR_MSG, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(ERROR_MSG, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(ERROR_MSG, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(ERROR_MSG, dataDo.getRequestRejectReason(), objectVo.getRequestRejectionReason().toString());
        assertEquals(ERROR_MSG, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
    }

}
