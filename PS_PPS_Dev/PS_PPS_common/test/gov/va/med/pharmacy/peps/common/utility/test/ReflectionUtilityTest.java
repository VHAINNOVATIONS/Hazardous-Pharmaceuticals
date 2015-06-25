/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test;


import java.util.Date;
import java.util.List;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.DateFormatUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.ReflectionUtility;
import gov.va.med.pharmacy.peps.common.utility.test.generator.GenericNameGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.OiRouteGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.OrderableItemGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.ProductGenerator;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OiRouteVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;

import junit.framework.TestCase;


/**
 * Verify {@link ReflectionUtility} can get/set values on a ValueObject.
 */
public class ReflectionUtilityTest extends TestCase {

    /** SUCCESS */
    public static final String SUCCESS = "Should be successful";

    /**
     * Verify setting an OI's parent OI.
     */
    public void testSetOrderalbeItemParent() {

        OrderableItemVo orderableItem = new OrderableItemGenerator().getFirst();
        OrderableItemVo orderableItemParent = new OrderableItemGenerator().getFirst();

        boolean successful = ReflectionUtility.setValue(orderableItem, FieldKey.ORDERABLE_ITEM_PARENT, orderableItemParent);

        assertTrue(SUCCESS, successful);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, orderableItemParent, orderableItem.getOrderableItemParent());
    }

    /**
     * Verify setting a product's parent OI.
     */
    public void testSetProductOrderableItemParent() {

        ProductVo product = new ProductGenerator().getFirst();
        OrderableItemVo orderableItem = new OrderableItemGenerator().getFirst();

        boolean successful = ReflectionUtility.setValue(product, FieldKey.ORDERABLE_ITEM, orderableItem);

        assertTrue(SUCCESS, successful);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, orderableItem, product.getOrderableItem());
    }

    /**
     * Verify setting a NDC's parent product.
     */
    public void testSetProductParent() {

        NdcVo ndc = new NdcGenerator().getFirst();
        ProductVo product = new ProductGenerator().getFirst();

        boolean successful = ReflectionUtility.setValue(ndc, FieldKey.PRODUCT, product);

        assertTrue(SUCCESS, successful);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, product, ndc.getProduct());
    }

    /**
     * Verify setting an external data field.
     */
    public void testSetExternalField() {

        ProductVo product = new ProductGenerator().getFirst();
        String vaProductName = "My VA Product Name Test";

        boolean successful = ReflectionUtility.setValue(product, FieldKey.VA_PRODUCT_NAME, vaProductName);

        assertTrue(SUCCESS, successful);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, vaProductName.toUpperCase(Locale.US), product.getVaProductName());
    }

    /**
     * Verify setting a ManagedDataVo.
     */
    public void testSetManagedDomain() {

        ProductVo product = new ProductGenerator().getFirst();
        GenericNameVo genericName = new GenericNameGenerator().getFirst();

        boolean successful = ReflectionUtility.setValue(product, FieldKey.GENERIC_NAME, genericName);

        assertTrue(SUCCESS, successful);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, genericName, product.getGenericName());
    }

    /**
     * Verify setting OI Route group list data field.
     */
    public void testSetGroupListDataField() {

        OrderableItemVo orderableItem = new OrderableItemGenerator().getFirst();
        List<OiRouteVo> oiRoutes = new OiRouteGenerator().getList();

        boolean successful = ReflectionUtility.setValue(orderableItem, FieldKey.OI_ROUTE, oiRoutes);

        assertTrue(SUCCESS, successful);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, oiRoutes, orderableItem.getOiRoute());
    }

    /**
     * Verify setting Item Status enumeration.
     */
    public void testSetEnumeration() {

        OrderableItemVo orderableItem = new OrderableItemGenerator().getFirst();
        ItemStatus itemStatus;

        if (ItemStatus.ACTIVE.equals(orderableItem.getItemStatus())) {
            itemStatus = ItemStatus.INACTIVE;
        } else {
            itemStatus = ItemStatus.ACTIVE;
        }

        boolean successful = ReflectionUtility.setValue(orderableItem, FieldKey.ITEM_STATUS, itemStatus);

        assertTrue(SUCCESS, successful);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, itemStatus, orderableItem.getItemStatus());
    }

    /**
     * Test the two ways to set a date: 1) a Long value, 2) a valid formatted date String
     */
    public void testSetDate() {

        DataField<Date> proposedInactivationDate = DataField.newInstance(FieldKey.PROPOSED_INACTIVATION_DATE);
        proposedInactivationDate.selectValue(null);

        Date now = new Date();
        String longValue = String.valueOf(now.getTime());

        try {
            proposedInactivationDate.selectStringValue(longValue);
        } catch (Exception e) {
            fail("Should not throw exception! ");
        }

        assertEquals("Selected value should be equal", now, proposedInactivationDate.getValue());

        proposedInactivationDate.selectValue(null);
        String formattedValue = DateFormatUtility.format(now, "M/dd/yyyy", Locale.getDefault());

        try {
            proposedInactivationDate.selectStringValue(formattedValue);
        } catch (Exception e) {
            fail("Should not throw exception!");
        }

        assertNotNull("Selected value should not be null", proposedInactivationDate.getValue());
    }
}
