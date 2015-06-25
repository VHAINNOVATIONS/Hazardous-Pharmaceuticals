/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplStandardMedRouteDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.StandardMedRouteConverter;

import junit.framework.TestCase;


/**
 * Test the {@link StandardMedRouteConverter}
 */
public class StandardMedicationRouteConverterTest extends TestCase {

    private static final String VALUE = "Field value here".toUpperCase();
    private static final Long EPL_ID = 9999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();
    private static final String ITEM_STATUS = "INACTIVE";
    private static final String REQUEST_STATUS = "REJECTED";
    private static final String REJECT_REASON_TEXT = "None";

    private EplStandardMedRouteDo dataDo;
    private StandardMedRouteVo objectVo;

    private StandardMedRouteConverter standardMedicationRouteConverter = new StandardMedRouteConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplStandardMedRouteDo createDo() {
        dataDo = new EplStandardMedRouteDo();

        dataDo.setStandardMedRouteName(VALUE);
        dataDo.setEplId(EPL_ID);
        dataDo.setInactivationDate(INACTIVATION_DATE);
        dataDo.setItemStatus(ITEM_STATUS);
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestStatus(REQUEST_STATUS);
        dataDo.setRevisionNumber(PPSConstants.L3);
        dataDo.setVuid("VUID");

        return dataDo;
    }

    /**
     * test setup for StandardMedicationRoutConverterTest.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {
        dataDo = createDo();
        objectVo = createVo();
    }

    /**
     * testToGenericNameVoHasAllAttributes
     */
    public void testToGenericNameVoHasAllAttributes() {
        objectVo = standardMedicationRouteConverter.convert(dataDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getStandardMedRouteName(), objectVo.getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRevisionNumber().longValue(), objectVo.getRevisionNumber());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getVuid(), objectVo.getVuid());

    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private StandardMedRouteVo createVo() {
        objectVo = new StandardMedRouteVo();

        objectVo.setId(String.valueOf(EPL_ID));
        objectVo.setValue(VALUE);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setItemStatus(ItemStatus.ACTIVE);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        objectVo.setRevisionNumber(PPSConstants.I3);
        objectVo.setVuid("vuid");

        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        dataDo = standardMedicationRouteConverter.convert(objectVo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getVuid(), objectVo.getVuid());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRevisionNumber().longValue(), objectVo.getRevisionNumber());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getStandardMedRouteName(), objectVo.getValue());

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
    }
}
