/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;

import junit.framework.TestCase;


/**
 * Test the ability to load properties files and override the property values.
 */
public class PropertyUtilityTest extends TestCase {
    private static final Class PROPERTY_FILE_CLASS = CommonException.class;
    private static final String PROPERTY_KEY = CommonException.ILLEGAL_ARGUMENT.getKey();

    /**
     * Test that a file can override the properties.
     * 
     * @throws Exception if error
     */
    public void testPropertiesOverrideFile() throws Exception {
        File overrideFile = new File("etc", "gov.va.med.pharmacy.peps.siteConfig.properties");
        overrideFile.deleteOnExit();
        overrideFile.createNewFile();

        String testValue = "This is a file test";
        Properties properties = new Properties();
        properties.setProperty(PROPERTY_KEY, testValue);
        FileOutputStream fileOutputStream = new FileOutputStream(overrideFile);
        properties.store(fileOutputStream, "Test file");
        fileOutputStream.close();

        String value = PropertyUtility.getProperty(PROPERTY_FILE_CLASS, PROPERTY_KEY);
        assertEquals("Property value should equal overridden value.", testValue, value);
    }

    /**
     * Test that the PropertyUtility can successfully load a properties file.
     * <p>
     * Also test that the properties file has an expected value in it.
     * 
     * @throws Exception if error
     */
    public void testLoadProperties() throws Exception {
        Properties properties = PropertyUtility.loadProperties(PROPERTY_FILE_CLASS);
        assertNotNull("Properties file should exist", properties);
        assertFalse("Properties file should have values in it", properties.isEmpty());

        String value = properties.getProperty(PROPERTY_KEY);
        assertNotNull("Value should not be null!", value);
    }

    /**
     * Test that the properties file has an expected value in it.
     */
    public void testGetProperty() {
        String value = PropertyUtility.getProperty(PROPERTY_FILE_CLASS, PROPERTY_KEY);
        assertNotNull("Value should not be null", value);
    }

    /**
     * Test that a property value can be overridden by calling {@link PropertyUtility#overrideProperty(String, String)}
     */
    public void testSetProperty() {
        String testValue = "This is a test.";
        PropertyUtility.overrideProperty(PROPERTY_KEY, testValue);

        String value = PropertyUtility.getProperty(PROPERTY_FILE_CLASS, PROPERTY_KEY);
        assertEquals("Property value should equal overridden value", testValue, value);
    }

    /**
     * Test that a property is not overridden when calling {@link PropertyUtility#loadPropertiesWithoutOverride(Class)}.
     * 
     * @throws Exception if error
     */
    public void testWithoutOverride() throws Exception {
        String testValue = "This is a test";
        PropertyUtility.overrideProperty(PROPERTY_KEY, testValue);

        Properties properties = PropertyUtility.loadPropertiesWithoutOverride(PROPERTY_FILE_CLASS);
        String value = properties.getProperty(PROPERTY_KEY);
        assertFalse("Value should not be equal, therefor not overridden.", testValue.equals(value));
    }
}
