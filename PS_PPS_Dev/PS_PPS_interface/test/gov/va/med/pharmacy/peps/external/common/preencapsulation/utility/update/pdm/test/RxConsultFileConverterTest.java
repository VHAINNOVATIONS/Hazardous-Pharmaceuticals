/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.test;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RxConsultGenerator;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
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
public class RxConsultFileConverterTest extends TestCase {
    private final String text = "The little goat hopped over the fence.";

    private Map<FieldKey, Difference> differences;
    private RxConsultVo rxConsultVo;

    /**
     * Sets up tests.
     * 
     * @throws Exception exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        rxConsultVo = new RxConsultGenerator().getRandom();

        StringBuilder b = new StringBuilder();

        for (int i = 0; i <= PPSConstants.I270; i += text.length()) {
            b.append(text);
        }

        rxConsultVo.setConsultText(b.toString());
    }

    /**
     * Tests add functionality.
     */
    public void testAdd() {
        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(rxConsultVo, ItemAction.ADD,
            differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("Add NAME incorrect.", rxConsultVo.getValue(), PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getRxConsultFile().get(0).getName().getValue());
    }

    /**
     * Tests the 3 - 280 characters PEPS rule in relation to VistA's 3 - 40 character rule.
     */
    public void testBusinessRuleDifference() {
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < PPSConstants.I41; i++) {
            b.append("A");
        }

        rxConsultVo.setConsultText(b.toString());

        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(rxConsultVo, ItemAction.ADD,
            differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("Text block count incorrect.", 2, PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getRxConsultFile().get(0).getText().getTextFile().size());
    }

    /**
     * Tests modify functionality.
     */
    public void testModify() {

        String newName = "New NAME";

        differences.put(FieldKey.VALUE, new Difference(FieldKey.VALUE, rxConsultVo.getValue(), newName));
        rxConsultVo.setValue(newName);

        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(rxConsultVo,
            ItemAction.MODIFY, differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("NAME incorrect.", rxConsultVo.getValue(), PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getRxConsultFile().get(0).getName().getValue());

    }
}
