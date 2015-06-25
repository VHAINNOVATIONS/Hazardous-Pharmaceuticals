/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.test;


import java.math.BigInteger;
import java.util.HashMap;
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
 * Test Orderable item.
 */
public class OrderableItemConverterTest extends TestCase { 

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(OrderableItemConverterTest.class);
    
    /** differences */
    private Map<FieldKey, Difference> differences;

    /** orderableItemVo */
    private OrderableItemVo orderableItemVo;

    /**
     * Setup test.
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
     * Test adding an Orderable Item.
     */
    public void testAddOrderableItem() {
        OrderableItem orderableItem = OrderableItemConverter.toOrderableItem(orderableItemVo, differences, ItemAction.ADD,
            new DomainItem[] {}, true);
        orderableItem.setStatus(ItemStatus.APPROVED);
        orderableItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        LOG.debug(OrderableItemDocument.instance().marshal(orderableItem));
        assertEquals("could not add Orderable Item", orderableItemVo.getVistaOiName(), OrderableItemDocument.instance()
            .unmarshal(OrderableItemDocument.instance().marshal(orderableItem)).getPharmacyOrderableItemFile().getName()
            .getValue());
    }
}
