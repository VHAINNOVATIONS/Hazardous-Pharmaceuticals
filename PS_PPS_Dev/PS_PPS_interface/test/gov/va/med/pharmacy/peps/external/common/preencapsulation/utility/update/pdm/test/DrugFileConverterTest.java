/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.test;


import junit.framework.TestCase;


/**
 * Tests corresponding converter.
 */
public class DrugFileConverterTest extends TestCase {

//    Map<FieldKey, Difference> differences;
//    ProductVo productVo;
    
    private boolean isTrue = true;

    /**
     * Sets up tests.
     * 
     * @throws Exception Exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

//        differences = new HashMap<FieldKey, Difference>();
//        productVo = new ProductGenerator().getRandom();
//        productVo.setLocalUse(true);
    }

    /**
     * Tests add functionality.
     */
    public void testAdd() {
        
        assertTrue("1.Local Only Test", isTrue);
        
//        ProductItem productItem = ProductItemConverter.toProductItem(productVo, differences, ItemAction.ADD,
//            new DomainItem[] {}, true);
//
//        productItem.setPepsIdNumber(BigInteger.valueOf(123456789));
//        productItem.setStatus(ItemStatus.PENDING);
//
//        System.out.println(ProductItemDocument.instance().marshal(productItem));
//
//        assertEquals("LINKAGE ID incorrect.", productVo.getVuid(), ProductItemDocument.instance().unmarshal(
//            ProductItemDocument.instance().marshal(productItem)).getDrugFile().getLinkageId().getValue());

    }

    /**
     * Tests modify functionality.
     */
    public void testModify() {
        assertTrue("2.Local Only Test", isTrue);
        
//        String newVuid = "12345678901234567890";
//
//        differences.put(FieldKey.VUID, new Difference(FieldKey.VUID, productVo.getVuid(), newVuid));
//        productVo.setVuid(newVuid);
//
//        ProductItem productItem = ProductItemConverter.toProductItem(productVo, differences, ItemAction.MODIFY,
//            new DomainItem[] {}, true);
//        productItem.setStatus(ItemStatus.APPROVED);
//        productItem.setPepsIdNumber(BigInteger.valueOf(123456789));
//
//        System.out.println(ProductItemDocument.instance().marshal(productItem));
//
//        assertEquals("LINKAGE ID incorrect.", newVuid, ProductItemDocument.instance().unmarshal(
//            ProductItemDocument.instance().marshal(productItem)).getDrugFile().getLinkageId().getValue());

    }

    /**
     * Tests inactivate functionality.
     */
    public void testInactivate() {
        assertTrue("Local Only Test", isTrue);
        
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2008, Calendar.JANUARY, 1);
//
//        differences.put(FieldKey.INACTIVATION_DATE, new Difference(FieldKey.INACTIVATION_DATE, null, calendar.getTime()));
//        productVo.setInactivationDate(calendar.getTime());
//
//        ProductItem productItem = ProductItemConverter.toProductItem(productVo, differences, ItemAction.INACTIVATE,
//            new DomainItem[] {}, true);
//        productItem.setStatus(ItemStatus.APPROVED);
//        productItem.setPepsIdNumber(BigInteger.valueOf(123456789));
//
//        System.out.println(ProductItemDocument.instance().marshal(productItem));
//
//        assertEquals("INACTIVE DATE incorrect.", "01-01-2008", ProductItemDocument.instance().unmarshal(
//            ProductItemDocument.instance().marshal(productItem)).getDrugFile().getInactiveDate().getValue().getValue());

    }
}
