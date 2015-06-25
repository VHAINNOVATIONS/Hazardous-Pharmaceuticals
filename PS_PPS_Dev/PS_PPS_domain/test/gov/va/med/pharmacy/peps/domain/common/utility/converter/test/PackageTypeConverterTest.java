/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.domain.common.model.EplPackageTypeDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.PackageTypeConverter;

import junit.framework.TestCase;


/**
 * Test the {@link PackageTypeConverter}
 */
public class PackageTypeConverterTest extends TestCase {

    private static final String NAME = "Field value here".toUpperCase();
    private static final Long EPL_ID = 9999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();
    private static final String REJECT_REASON_TEXT = "None";
    private static final ItemStatus ITEM_STATUS = ItemStatus.INACTIVE;
    private static final RequestItemStatus REQUEST_ITEM_STATUS = RequestItemStatus.PENDING;
    private static final RequestRejectionReason REQUEST_REJECT_REASON = RequestRejectionReason.INAPPROPRIATE_REQUEST;
    private static final Long REVISION_NUMBER = 3L;

    private PackageTypeConverter packageTypeConverter = new PackageTypeConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplPackageTypeDo createDo() {
        EplPackageTypeDo dataDo = new EplPackageTypeDo();

        dataDo.setPackageTypeName(NAME);
        dataDo.setEplId(EPL_ID);

        dataDo.setInactivationDate(INACTIVATION_DATE);
        dataDo.setItemStatus(ITEM_STATUS.name());
        dataDo.setRequestStatus(REQUEST_ITEM_STATUS.name());
        dataDo.setRequestRejectReason(REQUEST_REJECT_REASON.toString());
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestStatus(REQUEST_ITEM_STATUS.name());
        dataDo.setRevisionNumber(REVISION_NUMBER);

        return dataDo;
    }

    /**
     * Test
     */
    public void testToPackageTypeVoHasAllAttributes() {
        EplPackageTypeDo dataDo = createDo();
        PackageTypeVo objectVo = packageTypeConverter.convert(dataDo);

        assertEquals("EPL_ID ", EPL_ID.toString(), objectVo.getId());
        assertEquals("NAME ", NAME, objectVo.getValue());
        assertEquals("INACTIVATION_DATE ", INACTIVATION_DATE, objectVo.getInactivationDate());
        assertEquals("ITEM_STATUS ", ITEM_STATUS, objectVo.getItemStatus());
        assertEquals("REQUEST_ITEM_STATUS ", REQUEST_ITEM_STATUS, objectVo.getRequestItemStatus());
        assertEquals("REJECT_REASON_TEXT ", REJECT_REASON_TEXT, objectVo.getRejectionReasonText());
        assertEquals("REQUEST_REJECT_REASON ", REQUEST_REJECT_REASON, objectVo.getRequestRejectionReason());
        assertEquals("REVISION_NUMBER ", REVISION_NUMBER.longValue(), objectVo.getRevisionNumber());
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private PackageTypeVo createVo() {
        PackageTypeVo objectVo = new PackageTypeVo();

        objectVo.setId(String.valueOf(EPL_ID));
        objectVo.setValue(NAME);
        objectVo.setItemStatus(ITEM_STATUS);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestRejectionReason(REQUEST_REJECT_REASON);
        objectVo.setRequestItemStatus(REQUEST_ITEM_STATUS);
        objectVo.setRevisionNumber(REVISION_NUMBER);

        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoHasAttributes() {
        PackageTypeVo objectVo = createVo();
        EplPackageTypeDo dataDo = packageTypeConverter.convert(objectVo);

        assertEquals("EPL_ID", EPL_ID, dataDo.getEplId());
        assertEquals("DISP_UNIT_NAME", NAME, dataDo.getPackageTypeName());
        assertEquals("INACTIVATION_DATE", INACTIVATION_DATE, dataDo.getInactivationDate());
        assertEquals("ITEM_STATUS", ITEM_STATUS.name(), dataDo.getItemStatus());
        assertEquals("REQUEST_ITEM_STATUS", REQUEST_ITEM_STATUS.name(), dataDo.getRequestStatus());
        assertEquals("REJECT_REASON_TEXT", REJECT_REASON_TEXT, dataDo.getRejectReasonText());
        assertEquals("REQUEST_REJECT_REASON", REQUEST_REJECT_REASON.toString(), dataDo.getRequestRejectReason());
        assertEquals("REVISION_NUMBER", REVISION_NUMBER, dataDo.getRevisionNumber());
    }
}
