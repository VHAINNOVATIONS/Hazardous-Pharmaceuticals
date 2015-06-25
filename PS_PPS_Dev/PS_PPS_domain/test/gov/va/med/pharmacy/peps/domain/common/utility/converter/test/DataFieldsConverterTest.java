/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfNonlistValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DataFieldsConverter;

import junit.framework.TestCase;


/**
 * Test fixture
 */
public class DataFieldsConverterTest extends TestCase {

    private DataFields datafieldsVo;
    private EplVadfOwnerDo eplVadfOwnerDo;
    private DataFieldsConverter dataFieldsConverter = new DataFieldsConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private static DataFields createVo() {

        DataFields vaDatafields = new DataFields<DataField>();

        DataField<Boolean> chemoMed = DataField.newInstance(FieldKey.PATIENT_SPECIFIC_LABEL);
        chemoMed.setDataFieldId(new Long("12"));
        chemoMed.selectValue(false);
        chemoMed.setEditable(true);
        vaDatafields.setDataField(chemoMed);

        return vaDatafields;

    }

    /**
     * test setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {
        datafieldsVo = createVo();

    }

    /**
     * Test
     */
    public void testForCoversion() {

        EplNdcDo ndc = new EplNdcDo();
        ndc.setEplId(new Long("9991"));

        // iterate through dataFields
        List<DataField> values = new ArrayList<DataField>();
        values.addAll(datafieldsVo.getDataFields().values());

        for (DataField field : values) {
            assertNotNull("DataFieldValue is null", field.getDataFieldId());
        }
        
        eplVadfOwnerDo = dataFieldsConverter.convert(datafieldsVo, ndc);

        Set nonListValues = eplVadfOwnerDo.getEplVadfNonlistValues();
        java.util.Iterator<EplVadfNonlistValueDo> iterator = nonListValues.iterator();

        while (iterator.hasNext()) {

            EplVadfNonlistValueDo value = (EplVadfNonlistValueDo) iterator.next();
            assertNotNull("cannot be null", value.getKey().getVadfIdFk());
        }

    }

}
