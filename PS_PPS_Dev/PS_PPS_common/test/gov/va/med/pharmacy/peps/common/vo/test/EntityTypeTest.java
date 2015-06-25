/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.test;


import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.ClassUtils;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedDataVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;

import junit.framework.TestCase;


/**
 * Test Fixture
 */
public class EntityTypeTest extends TestCase {
    private EntityType testEntityType;

    /**
     * Test
     */
    public void testIsWhenNull() {
        testEntityType = EntityType.NDC;

        assertFalse("should not blow up when null", testEntityType.is(null));
    }

    /**
     * Test
     */
    public void testIsWhenOrderableItem() {
        testEntityType = EntityType.ORDERABLE_ITEM;

        assertTrue("should pass on match.", testEntityType.is(EntityType.ORDERABLE_ITEM));
        assertFalse("should fail, no match.", testEntityType.is(EntityType.PRODUCT));
        assertFalse(" should fail, no match", testEntityType.is(EntityType.NDC));
    }

    /**
     * Test
     */
    public void testIsWhenProduct() {
        testEntityType = EntityType.PRODUCT;

        assertFalse("should fail, no match ", testEntityType.is(EntityType.ORDERABLE_ITEM));
        assertTrue("should pass on match ", testEntityType.is(EntityType.PRODUCT));
        assertFalse("should fail, no match  ", testEntityType.is(EntityType.NDC));
    }

    /**
     * Test
     */
    public void testIsWhenNDC() {
        testEntityType = EntityType.NDC;

        assertFalse("should fail,  no match", testEntityType.is(EntityType.ORDERABLE_ITEM));
        assertFalse("should  fail, no match", testEntityType.is(EntityType.PRODUCT));
        assertTrue("should pass on match", testEntityType.is(EntityType.NDC));
    }

    /**
     * Test
     */
    public void testCreateFromStringShouldUseDotStrings() {
        String entityTypeDotString = "dose.unit";
        EntityType actual = null;

        actual = EntityType.createFromString(entityTypeDotString);

        assertEquals("should be equal", EntityType.DOSE_UNIT, actual);
    }

    /**
     * Test that all ManagedItemVo have a matching EntityType by our naming convention where CamelCaseVo turns into
     * CAMEL_CASE.
     * 
     * @throws Exception if error
     */
    public void testClassToEntityType() throws Exception {

        // Find all of the ManagedItemVo
        File voPackage = new File("src/gov/va/med/pharmacy/peps/common/vo");
        String[] classFiles = voPackage.list();
        Set<String> valueObjects = new HashSet<String>();

        // ClearCase adds .contrib and .keep files -- ignore these
        for (String string : classFiles) {
            if (!string.contains("contrib") && !string.contains("keep")) {
                int dot = string.indexOf(".");

                if (dot != -1) { // ignore sub packages for now
                    valueObjects.add(string.substring(0, string.indexOf(".")));
                }
            }
        }

        List<Class> managedItemTypes = new ArrayList<Class>();

        for (String valueObject : valueObjects) {
            if (!(valueObject.equalsIgnoreCase("FdbSearchMethods") 
                || valueObject.equalsIgnoreCase("FdbPhoneticSearch")
                || valueObject.equalsIgnoreCase("Category"))) {
                Class clazz = Class.forName("gov.va.med.pharmacy.peps.common.vo." + valueObject);

                if (ManagedItemVo.class.isAssignableFrom(clazz) && !ManagedItemVo.class.equals(clazz)
                    && !ManagedDataVo.class.equals(clazz)) {
                    managedItemTypes.add(clazz);
                }
            }
        }

        StringBuffer notFound = new StringBuffer(0);

        for (Class managedItem : managedItemTypes) {
            EntityType entityType = EntityType.toEntityType(managedItem);
            
            // ignore the NationalProductVo since it is an abstract class
            if (!(ClassUtils.getShortName(managedItem).equals("NationalProductVo"))) {
                if (entityType == null) {
                    notFound.append(ClassUtils.getShortName(managedItem));
                    notFound.append(", ");
                }
            }
        }

        assertTrue("All ManagedItemVo should have a corresponding EntityType with matching names. " + notFound.toString()
            + " were not found.", notFound.length() == 0);
    }

    /**
     * All EntityTypes should have FieldKeys to match
     */
    public void testEntityTypeToFieldKey() {
        StringBuffer buffer = new StringBuffer();

        for (EntityType entityType : EntityType.values()) {
            FieldKey fieldKey = FieldKey.getKey(entityType.toFieldKey());
            
            if (!(EntityType.CATEGORY.equals(entityType) 
                || EntityType.VUID_STATUS_HISTORY.equals(entityType))) {
                if (fieldKey == null) {
                    buffer.append(entityType);
                    buffer.append(", ");
                }
            }
        }

        assertTrue("All EntityTypes should have a corresponding FieldKey with matching names. " + buffer.toString()
            + " do not. ", buffer.toString().length() == 0);
    }

    /**
     * Test that all EntityTypes have a matching ValueObject by naming convention. So ORDERABLE_ITEM should have an
     * OrderableItemVo.
     */
    public void testToValueObjectClass() {
        EntityType[] entityTypes = EntityType.values();

        StringBuffer buffer = new StringBuffer();

        for (EntityType entityType : entityTypes) {
            if (!(EntityType.CATEGORY.equals(entityType))) {
                Class valueObject = entityType.toValueObjectClass();

                if (valueObject == null) {
                    buffer.append(entityType.toString());
                    buffer.append(", ");
                }
            }
        }

        assertTrue("All EntityTypes should have a corresponding ValueObject with matching names. " + buffer.toString()
            + " do not.", buffer.toString().length() == 0);
    }
}
