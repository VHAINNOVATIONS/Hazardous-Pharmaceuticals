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
import gov.va.med.pharmacy.peps.common.utility.test.generator.ProductGenerator;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.ProductItemDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.ProductItemConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemStatus;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.productitem.ProductItem;

import junit.framework.TestCase;


/**
 * Tests corresponding converter.
 */
public class VaProductFileConverterTest extends TestCase {

    
    private static final Logger LOG = org.apache.log4j.Logger.getLogger(VaProductFileConverterTest.class);
    private Map<FieldKey, Difference> differences;
    private ProductVo productVo;

    /**
     * Sets up tests.
     * 
     * @throws Exception exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        productVo = new ProductGenerator().getRandom();

        productVo.setRequestItemStatus(RequestItemStatus.APPROVED);
    }

    /**
     * Tests add functionality.
     */
    public void testAdd() {

        // Need to set PENDING before the request so only the File 50 entry is populated.
        productVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        ProductItem productItem = ProductItemConverter.toProductItem(productVo, differences, ItemAction.ADD,
            new DomainItem[] {}, true);

        productItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));
        productItem.setStatus(ItemStatus.APPROVED);

        LOG.debug(ProductItemDocument.instance().marshal(productItem));

        assertEquals("Add VUID incorrect.", productVo.getVuid(), ProductItemDocument.instance().unmarshal(
            ProductItemDocument.instance().marshal(productItem)).getVaProductFile().getVuid().getValue());

    }

    /**
     * Tests modify functionality.
     */
    public void testModify() {
        String newVuid = "12345678901234567890";

        differences.put(FieldKey.VUID, new Difference(FieldKey.VUID, productVo.getVuid(), newVuid));
        productVo.setVuid(newVuid);
        productVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        ProductItem productItem = ProductItemConverter.toProductItem(productVo, differences, ItemAction.MODIFY,
            new DomainItem[] {}, true);
        productItem.setStatus(ItemStatus.APPROVED);
        productItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        LOG.debug(ProductItemDocument.instance().marshal(productItem));

        assertEquals("VUID incorrect.", newVuid, ProductItemDocument.instance().unmarshal(
            ProductItemDocument.instance().marshal(productItem)).getVaProductFile().getVuid().getValue());

    }

    /**
     * Tests inactivate functionality.
     */
    public void testInactivate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(PPSConstants.I2008, Calendar.JANUARY, 1);

        differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, null, calendar.getTime()));
        productVo.setInactivationDate(calendar.getTime());

        ProductItem productItem = ProductItemConverter.toProductItem(productVo, differences, ItemAction.INACTIVATE,
            new DomainItem[] {}, true);
        productItem.setStatus(ItemStatus.APPROVED);
        productItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        LOG.debug(ProductItemDocument.instance().marshal(productItem));

        assertEquals("INACTIVATION DATE incorrect.", "01-01-2008", ProductItemDocument.instance().unmarshal(
            ProductItemDocument.instance().marshal(productItem)).getVaProductFile().getInactivationDate().getValue()
            .getValue());

    }
}
