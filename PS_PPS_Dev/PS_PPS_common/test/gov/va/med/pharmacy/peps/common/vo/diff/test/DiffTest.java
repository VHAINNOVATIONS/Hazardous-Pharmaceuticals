/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.diff.test;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.utility.test.generator.ProductGenerator;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Diff;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;

import junit.framework.TestCase;


/**
 * Verify that a diff between two ValueObjects works
 */
public class DiffTest extends TestCase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DiffTest.class);


    private ProductVo oldProduct;
    private ProductVo newProduct;
    private ProductGenerator productGenerator = new ProductGenerator();

    /**
     * setUp
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()  {
        
        LOG.debug("\n--------------------" + getName() + "--------------------");

        this.oldProduct = productGenerator.pseudoRandom();
        this.newProduct = productGenerator.pseudoRandom();
    }

    /**
     * Test two unequal ProductVo
     */
    public void testUnequalProducts() {
        Collection<Difference> differences = oldProduct.diff(newProduct);
        LOG.debug("Differences1: \n" + differences);
        assertTrue("ProductVo should have differences", differences.size() > 0);
    }

    /**
     * Test two equal ProductVo (both "old" value)
     */
    public void testEqualProducts() {
        Collection<Difference> differences = oldProduct.diff(oldProduct);
        LOG.debug("Differences2: \n" + differences);
        assertTrue("ProductVo should not have differences!", differences.size() == 0);
    }

    /**
     * Test that a difference between VA data fields can be found
     */
    public void testUnequalVaDataField() {
        DataField<String> oldValue = DataField.newInstance(FieldKey.MAX_DISPENSE_LIMIT);
        oldValue.selectValue("OLD1");
        oldProduct.getVaDataFields().setDataField(oldValue);

        DataField<String> newValue = DataField.newInstance(FieldKey.MAX_DISPENSE_LIMIT);
        newValue.selectValue("NEW1");
        newProduct.getVaDataFields().setDataField(newValue);

        Collection<Difference> differences = oldProduct.diff(newProduct);
        LOG.debug(differences);
        Difference difference = new Difference(FieldKey.MAX_DISPENSE_LIMIT, oldValue, newValue);

        assertTrue("VA DataField should not be equal", differences.contains(difference));
    }

    /**
     * Test that a difference between String external fields can be found
     */
    public void testUnequalExternalString() {
        String old2 = "OLD2";
        String new2 = "NEW2";
        oldProduct.setVaProductName(old2);
        newProduct.setVaProductName(new2);

        Collection<Difference> differences = oldProduct.diff(newProduct);
        LOG.debug("Differences3: \n" + differences);
        Difference difference = new Difference(FieldKey.VA_PRODUCT_NAME, old2, new2);
        assertTrue("Generic Names should not be equal", differences.contains(difference));
    }

    /**
     * Test that a difference between a contained object can be found
     */
    public void testUnequalObject() {
        DrugUnitVo oldRoute = new DrugUnitVo();
        oldRoute.setId("OLD3");
        oldRoute.setValue("OLD4");
        oldProduct.setProductUnit(oldRoute);

        DrugUnitVo newRoute = new DrugUnitVo();
        newRoute.setId("NEW3");
        newRoute.setValue("NEW4");
        newProduct.setProductUnit(newRoute);

        Collection<Difference> differences = oldProduct.diff(newProduct);
        LOG.debug("Differences4: \n" + differences);
        Difference difference = new Difference(FieldKey.PRODUCT_UNIT, oldRoute, newRoute);
        assertTrue("Routes should not be equal", differences.contains(difference));
    }

    /**
     * Test that a difference between an enum can be found.
     */
    public void testUnequalEnum() {
        oldProduct.setItemStatus(ItemStatus.ACTIVE);
        newProduct.setItemStatus(ItemStatus.INACTIVE);

        Collection<Difference> differences = oldProduct.diff(newProduct);
        LOG.debug("Differences5: \n" + differences);
        Difference difference = new Difference(FieldKey.ITEM_STATUS, ItemStatus.ACTIVE, ItemStatus.INACTIVE);
        assertTrue("Item status should not be equal", differences.contains(difference));
    }

    /**
     * There was a to-do in {@link Diff#attributesNotEqual(Object, Object)} where there was a problem of an inequality
     * reported between two ManagedItemVo attributes even though they were the "same" (should have been equal, but were two
     * different memory references).
     * <p>
     * Test that two ProductVo clones are shown as equal (they have no differences).
     */
    public void testEqualClones() {
        ProductVo product = new ProductGenerator().getFirst();
        ProductVo productCopy = product.copy();

        assertFalse("ProductVo should not be the same instance", product == productCopy);
        assertFalse("VA Dispense Unit should not be the same instance", product.getDispenseUnit() == productCopy
            .getDispenseUnit());

        Collection<Difference> differences = product.diff(productCopy);
        LOG.debug("Differences6: \n" + differences);
        assertTrue("ProductVo should not have differences", differences.size() == 0);
    }
}
