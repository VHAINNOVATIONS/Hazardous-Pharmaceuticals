/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.test;


import java.math.BigInteger;
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
 * Test Product item.
 */
public class ProductItemConverterTest extends TestCase { 

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(ProductItemConverterTest.class);
    
    /** differences */
    private Map<FieldKey, Difference> differences;

    /** productVo */
    private ProductVo productVo;

    /**
     * Setup test.
     * 
     * @throws Exception exception
     */
    protected void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        productVo = new ProductGenerator().getRandom();
        productVo.setRequestItemStatus(RequestItemStatus.APPROVED);
    }

    /**
     * Test add Product item.
     */
    public void testAdd() {
        ProductItem productItem = ProductItemConverter.toProductItem(productVo, differences, ItemAction.ADD,
            new DomainItem[] {}, true);
        productItem.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));
        productItem.setStatus(ItemStatus.PENDING);

        LOG.debug(ProductItemDocument.instance().marshal(productItem));
        assertEquals("bad product name", productVo.getVaProductName(), ProductItemDocument.instance().unmarshal(
            ProductItemDocument.instance().marshal(productItem)).getVaProductFile().getName().getValue());
    }
}
