/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf.test;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.DrugClassGenerator;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.NdfDomainDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.domain.NdfDomainConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.ndf.NdfDomain;

import junit.framework.TestCase;


/**
 * Tests corresponding converter.
 */
public class VaDrugClassFileConverterTest extends TestCase {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(VaDrugClassFileConverterTest.class);
    private Map<FieldKey, Difference> differences;
    private DrugClassVo drugClassVo;

    /**
     * Sets up tests.
     * 
     * @throws Exception exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        drugClassVo = new DrugClassGenerator().getRandom().getDrugClass();
    }

    /**
     * Tests add functionality.
     */
    public void testAdd() {

        NdfDomain ndfDomain = NdfDomainConverter.toNdfDomain(new DomainItem[] {new DomainItem(drugClassVo, ItemAction.ADD,
            differences)});

        ndfDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        LOG.debug(NdfDomainDocument.instance().marshal(ndfDomain));

        assertEquals("Add CODE incorrect.", drugClassVo.getCode(), NdfDomainDocument.instance().unmarshal(
            NdfDomainDocument.instance().marshal(ndfDomain)).getVaDrugClassFile().get(0).getCode().getValue());

    }

    /**
     * Tests modify functionality.
     */
    public void testModify() {

        String newCode = "ZZ000";

        differences.put(FieldKey.CODE, new Difference(FieldKey.CODE, drugClassVo.getCode(), newCode));
        drugClassVo.setCode(newCode);

        NdfDomain ndfDomain = NdfDomainConverter.toNdfDomain(new DomainItem[] {new DomainItem(drugClassVo,
            ItemAction.MODIFY, differences)});

        ndfDomain.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        LOG.debug(NdfDomainDocument.instance().marshal(ndfDomain));

        assertEquals("CODE incorrect.", drugClassVo.getCode(), NdfDomainDocument.instance().unmarshal(
            NdfDomainDocument.instance().marshal(ndfDomain)).getVaDrugClassFile().get(0).getCode().getValue());

    }
}
