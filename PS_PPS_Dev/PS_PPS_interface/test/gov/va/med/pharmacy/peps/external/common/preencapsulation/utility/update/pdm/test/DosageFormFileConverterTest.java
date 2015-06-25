/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.test;


import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.DosageFormGenerator;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.PdmDomainDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.domain.PdmDomainConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.pdm.PdmDomain;

import junit.framework.TestCase;


/**
 * Tests corresponding converter.
 */
public class DosageFormFileConverterTest extends TestCase {

    private Map<FieldKey, Difference> differences;
    private DosageFormVo dosageFormVo;

    /**
     * Sets up tests.
     * 
     * @throws Exception Exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        dosageFormVo = new DosageFormGenerator().getRandom();
    }

    /**
     * Tests add functionality.
     */
    public void testAdd() {

        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(dosageFormVo, ItemAction.ADD,
            differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("NAME incorrect. ", dosageFormVo.getDosageFormName(), PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getDosageFormFile().get(0).getName().getValue());

    }

    /**
     * Tests modify functionality.
     */
    public void testModify() {

        String newName = "New NAME";

        differences.put(FieldKey.DOSAGE_FORM_NAME, new Difference(FieldKey.DOSAGE_FORM_NAME, dosageFormVo
            .getDosageFormName(), newName));
        dosageFormVo.setDosageFormName(newName);

        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(dosageFormVo,
            ItemAction.MODIFY, differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("NAME incorrect.", dosageFormVo.getDosageFormName(), PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getDosageFormFile().get(0).getName().getValue());

    }

    /**
     * Tests inactivate functionality.
     */
    public void testInactivate() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(PPSConstants.I2008, Calendar.JANUARY, 1);

        differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, null, calendar.getTime()));
        dosageFormVo.setInactivationDate(calendar.getTime());

        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(dosageFormVo,
            ItemAction.INACTIVATE, differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("INACTIVATION DATE incorrect.", "01-01-2008", PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getDosageFormFile().get(0).getInactivationDate().getValue()
            .getValue());

    }

}
