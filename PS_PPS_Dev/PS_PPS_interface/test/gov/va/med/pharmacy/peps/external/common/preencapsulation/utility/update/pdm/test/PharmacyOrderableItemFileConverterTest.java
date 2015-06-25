/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.test;


import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.OrderableItemGenerator;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.OrderableItemDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.OrderableItemConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemStatus;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.orderableitem.OrderableItem;

import junit.framework.TestCase;


/**
 * Tests corresponding converter.
 */
public class PharmacyOrderableItemFileConverterTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(PharmacyOrderableItemFileConverterTest.class);

    /** differences */
    private Map<FieldKey, Difference> differences;

    /** orderableItemVo */
    private OrderableItemVo orderableItemVo;

    /**
     * Sets up tests.
     * 
     * @throws Exception exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        orderableItemVo = new OrderableItemGenerator().getRandom();
    }

    /**
     * Tests add functionality.
     */
    public void testAdd() {
        OrderableItem orderableItem = OrderableItemConverter.toOrderableItem(orderableItemVo, differences, ItemAction.ADD,
            new DomainItem[] {}, true);
        orderableItem.setStatus(ItemStatus.APPROVED);
        orderableItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        LOG.info(OrderableItemDocument.instance().marshal(orderableItem));
        assertEquals("could not add Orderable Item", orderableItemVo.getVistaOiName(), OrderableItemDocument.instance()
            .unmarshal(OrderableItemDocument.instance().marshal(orderableItem)).getPharmacyOrderableItemFile().getName()
            .getValue());
    }

    /**
     * Tests modify functionality.
     */
    public void testModify() {

        //2012-04-03  OI names are now all capitalized.
        String newOiName = "New NAME".toUpperCase(Locale.getDefault()); 

        differences.put(FieldKey.VISTA_OI_NAME, new Difference(FieldKey.VISTA_OI_NAME, orderableItemVo.getVistaOiName(),
            newOiName));
        orderableItemVo.setVistaOiName(newOiName);

        OrderableItem orderableItem =
            OrderableItemConverter.toOrderableItem(orderableItemVo, differences, ItemAction.MODIFY, new DomainItem[] {}, true);
        orderableItem.setStatus(ItemStatus.APPROVED);
        orderableItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        LOG.info(OrderableItemDocument.instance().marshal(orderableItem));

        assertEquals("NAME incorrect.", newOiName,
            OrderableItemDocument.instance().unmarshal(OrderableItemDocument.instance().marshal(orderableItem))
                .getPharmacyOrderableItemFile().getName().getValue());

    }

    /**
     * Tests inactivate functionality for the pharmacy orderable item.
     */
    public void testInactivate() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(PPSConstants.I2008, Calendar.JANUARY, 1);

        differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, null, calendar.getTime()));
        orderableItemVo.setInactivationDate(calendar.getTime());

        OrderableItem orderableItem =
            OrderableItemConverter.toOrderableItem(orderableItemVo, differences, ItemAction.INACTIVATE, new DomainItem[] {},
                true);
        orderableItem.setStatus(ItemStatus.APPROVED);
        orderableItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        LOG.info(OrderableItemDocument.instance().marshal(orderableItem));

        assertEquals("INACTIVE DATE incorrect.", "01-01-2008", OrderableItemDocument.instance().unmarshal(
            OrderableItemDocument.instance().marshal(orderableItem)).getPharmacyOrderableItemFile().getInactiveDate()
            .getValue().getValue());
    }
}
