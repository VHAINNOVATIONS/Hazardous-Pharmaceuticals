/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.domain.common.model.EplManufacturerDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.ManufacturerConverter;

import junit.framework.TestCase;


/**
 * Test the {@link ManufacturerConverter}
 */
public class ManufacturerConverterTest extends TestCase {

    private static final String VALUE = "FIELD VALUE HERE";
    private static final Long EPL_ID = 9999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";
    private static final String ITEM_STATUS = "INACTIVE";
    private static final String REQUEST_STATUS = "REJECTED";
    private static final String REJECT_REASON_TEXT = "None";

    private ManufacturerConverter manufacturerConverter = new ManufacturerConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplManufacturerDo createDo() {
        EplManufacturerDo dataDo = new EplManufacturerDo();

        dataDo.setAddress(ADDRESS);
        dataDo.setName(VALUE);
        dataDo.setPhone(PHONE);
        dataDo.setEplId(EPL_ID);
        dataDo.setInactivationDate(INACTIVATION_DATE);
        dataDo.setItemStatus(ITEM_STATUS);
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestStatus(REQUEST_STATUS);
        dataDo.setRevisionNumber(PPSConstants.L3);

        return dataDo;
    }

    /**
     * Test conversion to value object
     */
    public void testToManufacturerVoHasAllAttributes() {
        EplManufacturerDo dataDo = createDo();
        ManufacturerVo objectVo = manufacturerConverter.convert(dataDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getName(), objectVo.getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), Long.valueOf(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getAddress(), objectVo.getAddress());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getPhone(), objectVo.getPhone());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
    }
    
    /**
     * 
     * @return EplSearchTemplateDo
     */
    private ManufacturerVo createVo() {
        ManufacturerVo objectVo = new ManufacturerVo();

        objectVo.setId(String.valueOf(EPL_ID));
        objectVo.setValue(VALUE);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setItemStatus(ItemStatus.ACTIVE);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        objectVo.setAddress(ADDRESS);
        objectVo.setValue(VALUE);
        objectVo.setPhone(PHONE);
        objectVo.setRevisionNumber(PPSConstants.L3);
        
        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        ManufacturerVo objectVo = createVo();
        EplManufacturerDo dataDo = manufacturerConverter.convert(objectVo);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRevisionNumber().longValue(), objectVo.getRevisionNumber());
        
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getName(), objectVo.getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getAddress(), objectVo.getAddress());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getPhone(), objectVo.getPhone());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
    }

}
