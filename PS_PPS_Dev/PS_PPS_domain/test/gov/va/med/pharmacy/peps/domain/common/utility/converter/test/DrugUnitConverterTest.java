/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.sql.Timestamp;
import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DrugUnitConverter;

import junit.framework.TestCase;


/**
 * Test the {@link DrugUnitConverter}
 */
public class DrugUnitConverterTest extends TestCase {

    private static final String VALUE = "FIELD VALUE HERE";
    private static final Long EPL_ID = 9999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();
    private static final String ITEM_STATUS = "INACTIVE";
    private static final String REQUEST_STATUS = "REJECTED";
    private static final String REJECT_REASON_TEXT = "None";

    private DrugUnitConverter drugUnitConverter = new DrugUnitConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplDrugUnitDo createDo() {
        EplDrugUnitDo dataDo = new EplDrugUnitDo();

        dataDo.setName(VALUE);
        dataDo.setEplId(EPL_ID);
        dataDo.setInactivationDate(new Timestamp(INACTIVATION_DATE.getTime()));
        dataDo.setItemStatus(ITEM_STATUS);
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestStatus(REQUEST_STATUS);
        dataDo.setRevisionNumber(PPSConstants.L3);

        return dataDo;
    }

    /**
     * Test conversion to value object
     */
    public void testToDrugUnitVoHasAllAttributes() {
        EplDrugUnitDo dataDo = createDo();
        DrugUnitVo objectVo = drugUnitConverter.convert(dataDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getName(), objectVo.getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private DrugUnitVo createVo() {
        DrugUnitVo objectVo = new DrugUnitVo();

        objectVo.setId(EPL_ID.toString());
        objectVo.setValue(VALUE);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setItemStatus(ItemStatus.ACTIVE);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        objectVo.setRevisionNumber(PPSConstants.L3);

        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        DrugUnitVo objectVo = createVo();

        EplDrugUnitDo dataDo = drugUnitConverter.convert(objectVo);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRevisionNumber().longValue(), objectVo.getRevisionNumber());

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
    }
}
