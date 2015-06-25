/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.test;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.StandardMedicationRouteGenerator;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
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
public class StandardMedicationRoutesFileConverterTest extends TestCase {

    private Map<FieldKey, Difference> differences;
    private StandardMedRouteVo standardMedicationRouteVo;

    /**
     * Sets up tests.
     * 
     * @throws Exception exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        standardMedicationRouteVo = new StandardMedicationRouteGenerator().getRandom();
    }

    /**
     * Tests add functionality.
     */
    public void testAdd() {

        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(standardMedicationRouteVo,
            ItemAction.ADD, differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("Add NAME incorrect.", standardMedicationRouteVo.getValue(), PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getStandardMedicationRoutesFile().get(0).getName().getValue());

    }

    /**
     * Tests modify functionality.
     */
    public void testModify() {

        String newName = "New NAME";

        differences.put(FieldKey.VALUE, new Difference(FieldKey.VALUE, standardMedicationRouteVo.getValue(), newName));
        standardMedicationRouteVo.setValue(newName);

        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(standardMedicationRouteVo,
            ItemAction.MODIFY, differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("NAME incorrect.", standardMedicationRouteVo.getValue(), PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getStandardMedicationRoutesFile().get(0).getName().getValue());

    }
}
