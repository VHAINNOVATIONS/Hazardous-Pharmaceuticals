/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.test;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.OrderUnitGenerator;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
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
public class OrderUnitFileConverterTest extends TestCase {

    private Map<FieldKey, Difference> differences;
    private OrderUnitVo orderUnitVo;

    /**
     * Sets up tests.
     * 
     * @throws Exception Exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        orderUnitVo = new OrderUnitGenerator().getRandom();
    }

    /**
     * Tests add functionality.
     */
    public void testAdd() {

        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(orderUnitVo, ItemAction.ADD,
            differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("ABBREVIATION incorrect.", orderUnitVo.getValue(), PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getOrderUnitFile().get(0).getAbbreviation().getValue());

    }

    /**
     * Tests inactivate functionality.
     */
    public void testModify() {

        String newAbbreviation = "New";

        differences.put(FieldKey.VALUE, new Difference(FieldKey.VALUE, orderUnitVo.getValue(), newAbbreviation));
        orderUnitVo.setValue(newAbbreviation);

        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(orderUnitVo,
            ItemAction.MODIFY, differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("1.ABBREVIATION incorrect.", orderUnitVo.getValue(), PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getOrderUnitFile().get(0).getAbbreviation().getValue());

    }

}
