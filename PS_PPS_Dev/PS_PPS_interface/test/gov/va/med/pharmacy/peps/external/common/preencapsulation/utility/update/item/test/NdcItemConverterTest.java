/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.test;


import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
 * Test the NdcItemConverter
 */
public class NdcItemConverterTest extends TestCase { 

    /** differences */
    private Map<FieldKey, Difference> differences;

    /** ndcVo */
    private NdcVo ndcVo;

    /**
     * Creates instances of ndcItem and ndcVo
     * 
     * @throws Exception if error
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        ndcVo = new NdcGenerator().getRandom();
    }

    /**
     * Test adding an NDC Item.
     */
    public void testAddNdc() {
        NdcItem ndcItem = NdcItemConverter.toNdcItem(ndcVo, differences, ItemAction.ADD, new DomainItem[] {});
        ndcItem.setStatus(ItemStatus.APPROVED);
        ndcItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("could not add NDC", "0" + ndcVo.getNdc().replaceAll("-", ""), NdcItemDocument.instance().unmarshal(
            NdcItemDocument.instance().marshal(ndcItem)).getNdcUpnFile().getNdc().getValue());
    }

    /**
     * Test modifying an NDC Item.
     */
    public void testModifyNdc() {
        String newNdc = "23456-7890-12";

        // create the ndc to test the marshalled message against.
        String newNDCNoDashes = newNdc.replace("-", "");
        newNDCNoDashes = "0" + newNDCNoDashes;

        differences.put(FieldKey.NDC, new Difference(FieldKey.NDC, ndcVo.getNdc(), newNdc));
        ndcVo.setNdc(newNdc);

        NdcItem ndcItem = NdcItemConverter.toNdcItem(ndcVo, differences, ItemAction.MODIFY, new DomainItem[] {});
        ndcItem.setStatus(ItemStatus.APPROVED);
        ndcItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("could not modify NDC", newNDCNoDashes, NdcItemDocument.instance().unmarshal(
            NdcItemDocument.instance().marshal(ndcItem)).getNdcUpnFile().getNdc().getValue());
    }

    /**
     * Test unsetting an UPN.
     */
    public void testUnsetUpcUpn() {
        String newUpn = null;

        differences.put(FieldKey.UPC_UPN, new Difference(FieldKey.UPC_UPN, ndcVo.getUpcUpn(), newUpn));
        ndcVo.setUpcUpn(newUpn);

        NdcItem ndcItem = NdcItemConverter.toNdcItem(ndcVo, differences, ItemAction.MODIFY, new DomainItem[] {});
        ndcItem.setStatus(ItemStatus.APPROVED);
        ndcItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        String xml = NdcItemDocument.instance().marshal(ndcItem);
        assertTrue("UPN should be nil", xml.contains("upn number=\"2.0\" xsi:nil=\"true\""));
    }

    /**
     * Test inactivating an NDC.
     */
    public void testInactivate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(PPSConstants.I2008, Calendar.JANUARY, 1);

        ndcVo.setInactivationDate(calendar.getTime());
        differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, null, calendar.getTime()));

        NdcItem ndcItem = NdcItemConverter.toNdcItem(ndcVo, differences, ItemAction.INACTIVATE, new DomainItem[] {});
        ndcItem.setStatus(ItemStatus.APPROVED);
        ndcItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        assertEquals("bad inactivation date", "01-01-2008", NdcItemDocument.instance().unmarshal(
            NdcItemDocument.instance().marshal(ndcItem)).getNdcUpnFile().getInactivationDate().getValue().getValue());
    }

    /**
     * Test activating a previously inactivated NDC.
     */
    public void testUnsetInactivate() {
        differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.UPC_UPN, new Date(), null));

        NdcItem ndcItem = NdcItemConverter.toNdcItem(ndcVo, differences, ItemAction.MODIFY, new DomainItem[] {});
        ndcItem.setStatus(ItemStatus.APPROVED);
        ndcItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        String xml = NdcItemDocument.instance().marshal(ndcItem);
        assertTrue("Inactivation Date should be nil", xml.contains("inactivationDate number=\"7.0\" xsi:nil=\"true\""));
    }

    /**
     * Execute multiple threads to check thread safety.
     * 
     * @throws Exception exception
     */
    public void testThreadSafety() throws Exception {
        final NdcItem ndcItem = NdcItemConverter.toNdcItem(ndcVo, differences, ItemAction.ADD, new DomainItem[] {});
        ndcItem.setStatus(ItemStatus.APPROVED);
        ndcItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        final String baselineXML = NdcItemDocument.instance().marshal(ndcItem);

        Thread[] threads = new Thread[PPSConstants.I10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread("thread #" + (i + 1)) {

                /**
                 * Execute the marshal/unmarshal procedure in a new thread.
                 */
                public void run() {
                    if (!baselineXML.equals(NdcItemDocument.instance().marshal(ndcItem))) {
                        fail(getName() + " failed!");
                    }
                }
            };

            threads[i].start();
        }

        for (Thread t : threads) {
            t.join(PPSConstants.I10000);
        }
    }
}
