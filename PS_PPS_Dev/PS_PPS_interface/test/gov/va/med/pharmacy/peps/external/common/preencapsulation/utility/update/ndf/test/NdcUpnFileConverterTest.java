/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf.test;


import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.NdcItemDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.NdcItemConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemStatus;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.ndcitem.NdcItem;

import junit.framework.TestCase;


/**
 * Tests corresponding converter.
 */
public class NdcUpnFileConverterTest extends TestCase {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(VaDrugClassFileConverterTest.class);
    private Map<FieldKey, Difference> differences;
    private NdcVo ndcVo;

    /**
     * Sets up tests.
     * 
     * @throws Exception exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        ndcVo = new NdcGenerator().getRandom();
    }

    /**
     * Tests add functionality.
     */
    public void testAdd() {

        NdcItem productItem = NdcItemConverter.toNdcItem(ndcVo, differences, ItemAction.ADD, new DomainItem[] {});

        productItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));
        productItem.setStatus(ItemStatus.PENDING);

        LOG.debug(NdcItemDocument.instance().marshal(productItem));

        assertEquals("TRADE NAME incorrect.", ndcVo.getTradeName(), NdcItemDocument.instance().unmarshal(
            NdcItemDocument.instance().marshal(productItem)).getNdcUpnFile().getTradeName().getValue().getValue());

    }

    /**
     * Tests modify functionality.
     */
    public void testModify() {

        String newTradeName = "123456789012";

        differences.put(FieldKey.TRADE_NAME, new Difference(FieldKey.TRADE_NAME, ndcVo.getTradeName(), newTradeName));
        ndcVo.setTradeName(newTradeName);

        NdcItem productItem = NdcItemConverter.toNdcItem(ndcVo, differences, ItemAction.MODIFY, new DomainItem[] {});
        productItem.setStatus(ItemStatus.APPROVED);
        productItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        LOG.debug(NdcItemDocument.instance().marshal(productItem));

        assertEquals("New TRADE NAME incorrect.", newTradeName, NdcItemDocument.instance().unmarshal(
            NdcItemDocument.instance().marshal(productItem)).getNdcUpnFile().getTradeName().getValue().getValue());

    }

    /**
     * Tests inactivate functionality.
     */
    public void testInactivate() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(PPSConstants.I2008, Calendar.JANUARY, 1);

        differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, null,
            calendar.getTime()));
        ndcVo.setInactivationDate(calendar.getTime());

        NdcItem ndcItem = NdcItemConverter.toNdcItem(ndcVo, differences, ItemAction.INACTIVATE, new DomainItem[] {});
        ndcItem.setStatus(ItemStatus.APPROVED);
        ndcItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        LOG.debug(NdcItemDocument.instance().marshal(ndcItem));

        assertEquals("INACTIVATION DATE incorrect.", "01-01-2008", NdcItemDocument.instance().unmarshal(
            NdcItemDocument.instance().marshal(ndcItem)).getNdcUpnFile().getInactivationDate().getValue().getValue());

    }
}
