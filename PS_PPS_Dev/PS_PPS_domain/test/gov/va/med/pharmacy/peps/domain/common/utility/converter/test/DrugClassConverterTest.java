/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.domain.common.model.EplClassTypeDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDrugClassDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DrugClassConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DrugClassificationTypeConverter;

import junit.framework.TestCase;


/**
 * Test {@link DrugClassConverter}
 */
public class DrugClassConverterTest extends TestCase {
    private static final String DESCRIPTION = "Field value here";
    private static final Long EPL_ID = 9999L;
    private static final String ITEM_STATUS = "INACTIVE";
    private static final String REQUEST_STATUS = "REJECTED";
    private static final String REJECT_REASON_TEXT = "None";
    private static final String CLASSIFICATION_NAME = "test";
    private static final String CODE = "code";
    private static final Long REVISION_NUMBER = 1L;

    private DrugClassConverter drugClassConverter = new DrugClassConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplVaDrugClassDo createDo() {
        EplVaDrugClassDo dataDo = new EplVaDrugClassDo();

        EplVaDrugClassDo parent = new EplVaDrugClassDo();
        parent.setEplId(EPL_ID);
        parent.setNdfClassIen(new Long("232"));

        EplClassTypeDo type = new EplClassTypeDo();
        type.setId(new Long("55"));

        dataDo.setRevisionNumber(REVISION_NUMBER);
        dataDo.setClassificationName(CLASSIFICATION_NAME);
        dataDo.setCode(CODE);
        dataDo.setNdfClassIen(new Long("23334"));
        dataDo.setDescription(DESCRIPTION);
        dataDo.setEplClassType(type);
        dataDo.setEplVaDrugClass(parent);
        dataDo.setEplId(EPL_ID);
        dataDo.setItemStatus(ITEM_STATUS);
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestStatus(REQUEST_STATUS);

        return dataDo;
    }

    /**
     * test setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {
        drugClassConverter.setDrugClassificationTypeConverter(new DrugClassificationTypeConverter());
    }

    /**
     * Test
     */
    public void testToVoHasAllAttributes() {
        EplVaDrugClassDo dataDo = createDo();
        DrugClassVo objectVo = drugClassConverter.convert(dataDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getClassificationName().toUpperCase(Locale.US), 
            objectVo.getClassification());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getCode().toUpperCase(Locale.US), objectVo.getCode());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getDescription(), objectVo.getDescription());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplClassType().getId().toString(), objectVo.getClassificationType()
            .getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplClassType().getId().toString(), objectVo.getClassificationType()
            .getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplVaDrugClass().getEplId().toString(), 
            objectVo.getParentDrugClass().getId());

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRevisionNumber().longValue(), objectVo.getRevisionNumber());

    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private DrugClassVo createVo() {
        DrugClassVo objectVo = new DrugClassVo();

        DrugClassVo parent = new DrugClassVo();
        parent.setId("9991");

        DrugClassificationTypeVo type = new DrugClassificationTypeVo();
        type.setId("55");
        type.setId("3");

        objectVo.setRevisionNumber(REVISION_NUMBER);
        objectVo.setClassification(CLASSIFICATION_NAME);
        objectVo.setCode(CODE);
        objectVo.setValue(DESCRIPTION);
        objectVo.setClassificationType(type);
        objectVo.setParentDrugClass(parent);
        objectVo.setId(String.valueOf(EPL_ID));
        objectVo.setItemStatus(ItemStatus.ACTIVE);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoHasAttributes() {
        DrugClassVo objectVo = createVo();
        EplVaDrugClassDo dataDo = drugClassConverter.convert(objectVo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getClassificationName(), objectVo.getClassification());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplId(), new Long(objectVo.getId()));
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getItemStatus(), objectVo.getItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRequestStatus(), objectVo.getRequestItemStatus().name());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRejectReasonText(), objectVo.getRejectionReasonText());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getCode(), objectVo.getCode());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getDescription(), objectVo.getDescription());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplClassType().getId().toString(), objectVo.getClassificationType()
            .getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplClassType().getClassType(), 
                objectVo.getClassificationType().getValue());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getEplVaDrugClass().getEplId().toString(), 
                objectVo.getParentDrugClass().getId());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, dataDo.getRevisionNumber().longValue(), objectVo.getRevisionNumber());
    }
}
