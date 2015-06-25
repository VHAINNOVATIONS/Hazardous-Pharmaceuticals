/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.domain.common.model.EplIngredientDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.IngredientConverter;

import junit.framework.TestCase;


/**
 * Test the {@link IngredientConverter}
 */
public class IngredientConverterTest extends TestCase {

    private static final String VALUE = "FIELD VALUE HERE";
    private static final Long EPL_ID = 9999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();
    private static final String VUID = "vuid";
    private static final String ITEM_STATUS = "INACTIVE";
    private static final String REQUEST_STATUS = "REJECTED";
    private static final String REJECT_REASON_TEXT = "None";
    private static final Long REVISION_NUMBER = 3L;
    private static final String S9991 = "9991";

    private IngredientConverter ingredientConverter = new IngredientConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplIngredientDo createDo() {
        EplIngredientDo dataDo = new EplIngredientDo();

        EplIngredientDo parent = new EplIngredientDo();
        parent.setEplId(new Long(S9991));
        parent.setName("TEST");

        dataDo.setEplIngredient(parent);
        dataDo.setName(VALUE);
        dataDo.setEplId(EPL_ID);
        dataDo.setItemStatus(ITEM_STATUS);
        dataDo.setInactivationDate(INACTIVATION_DATE);
        dataDo.setVuid(VUID);
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestStatus(REQUEST_STATUS);
        dataDo.setRevisionNumber(REVISION_NUMBER);

        return dataDo;
    }

    /**
     * Test conversion to value object
     */
    public void testToIngredientVoHasAllAttributes() {
        EplIngredientDo dataDo = createDo();
        IngredientVo objectVo = ingredientConverter.convert(dataDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getName(), objectVo.getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getVuid(), objectVo.getVuid());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplIngredient().getEplId().toString(), 
            objectVo.getPrimaryIngredient().getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplIngredient().getName(), 
            objectVo.getPrimaryIngredient().getValue());
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private IngredientVo createVo() {
        IngredientVo objectVo = new IngredientVo();
        IngredientVo primaryVo = new IngredientVo();

        primaryVo.setId(S9991);
        primaryVo.setValue("PRIMARY_NAME");
        objectVo.setId(EPL_ID.toString());
        objectVo.setValue(VALUE);
        objectVo.setVuid(VUID);
        objectVo.setPrimaryIngredient(primaryVo);
        objectVo.setItemStatus(ItemStatus.ACTIVE);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        IngredientVo objectVo = createVo();
        EplIngredientDo dataDo = ingredientConverter.convert(objectVo);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getVuid(), objectVo.getVuid());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getInactivationDate(), objectVo.getInactivationDate());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplIngredient().getEplId().toString(), 
            objectVo.getPrimaryIngredient().getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplIngredient().getName(), 
            objectVo.getPrimaryIngredient().getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
    }

}
