/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import gov.va.med.pharmacy.peps.common.exception.CommonException;


/**
 * Provides utility methods for working with properties files.
 * <p>
 * A Class' individual properties values can be overridden by placing the same named properties in the
 * {@link #PROPERTY_OVERRIDE_FILE} file. This file must be in a folder on the current thread's class path.
 */
public class PropertyUtility {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PropertyUtility.class);
    private static final String PROPERTY_OVERRIDE_FILE = "gov.va.med.pharmacy.peps.siteConfig.properties";

    private static final Map<Class, Properties> PROPERTIES_CACHE = new HashMap<Class, Properties>();
    private static final Properties PROPERTIES_OVERRIDE = loadPropertiesOverride();

    
    /**
     * Cannot instantiate
     */
    private PropertyUtility() {
        super();
    }
    
    /**
     * Get the given property key from the {@value #PROPERTY_OVERRIDE_FILE} file.
     * 
     * @param key String property to retrieve
     * @return String property value, if the property is not found this will be null
     */
    public static String getOverriddenProperty(String key) {
        return PROPERTIES_OVERRIDE.getProperty(key);
    }

    /**
     * Get the given property key from the properties file for the given Class.
     * <p>
     * Property values can be overridden in the {@value #PROPERTY_OVERRIDE_FILE} file.
     * 
     * @param className Class corresponding to properties file from which to retrieve property
     * @param key String property to retrieve
     * @return String property value, if the property is not found this will be null.
     */
    public static String getProperty(Class className, String key) {
        try {
            return loadProperties(className).getProperty(key);
        } catch (IOException e) {
            throw new CommonException(e, CommonException.PROPERTY_FILE_UNAVAILABLE, createPath(className));
        }
    }

    /**
     * Load the properties file using a Class' name to generate the path.
     * <p>
     * Property values can be overridden in the {@value #PROPERTY_OVERRIDE_FILE} file.
     * 
     * @param className Class for properties file to load, the properties file is named after the fully qualified class name
     * @return loaded properties
     * @throws IOException if an I/O exception occurs or the properties file cannot be located
     */
    public static Properties loadProperties(Class className) throws IOException {
        synchronized (PROPERTIES_CACHE) {
            if (!PROPERTIES_CACHE.containsKey(className)) {
                Properties properties = load(className);
                overrideProperties(properties);
                PROPERTIES_CACHE.put(className, properties);
            }
        }

        return PROPERTIES_CACHE.get(className);
    }

    /**
     * Load the properties file using a Class' name to generate the path.
     * <p>
     * As opposed to {@link #loadProperties(Class)}, this method does NOT override properties with values in
     * {@value #PROPERTY_OVERRIDE_FILE}.
     * <p>
     * This method should only be used in special cases where the property values should never be able to be overridden via
     * the {@value #PROPERTY_OVERRIDE_FILE}. In other words, first use the {@link #loadProperties(Class)} method. If that
     * doesn't work, resort to using this method.
     * 
     * @param className Class for properties file to load, the properties file is named after the fully qualified class name
     * @return loaded properties
     * @throws IOException if an I/O exception occurs or the properties file cannot be located
     */
    public static Properties loadPropertiesWithoutOverride(Class className) throws IOException {
        return load(className);
    }

    /**
     * Set the value for a property to override values in an individual Class' properties file.
     * 
     * @param key String name of the property to override
     * @param value String value of the property to override
     */
    public static void overrideProperty(String key, String value) {

        // reset the value in PROPERTIES_OVERRIDE for future loaded properties files
        PROPERTIES_OVERRIDE.setProperty(key, value);

        // reset value in all cached properties files
        for (Properties properties : PROPERTIES_CACHE.values()) {
            properties.setProperty(key, value);
        }

    }

    /**
     * Attempt to load the {@value #PROPERTY_OVERRIDE_FILE} file with overridden site configuration properties.
     * <p>
     * If the properties file is not found, return an empty Properties instance.
     * 
     * @return overridden site configuration Properties
     */
    private static Properties loadPropertiesOverride() {
        Properties properties = new Properties();

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTY_OVERRIDE_FILE);

        if (inputStream == null) {
            LOG.debug("No " + PROPERTY_OVERRIDE_FILE
                      + " file on class path defined to override site configuration! Using defaults instead.");
        } else {
            try {
                try {
                    properties.load(inputStream);
                } finally {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new CommonException(e, CommonException.PROPERTY_FILE_UNAVAILABLE, PROPERTY_OVERRIDE_FILE);
            }
        }

        return properties;
    }

    /**
     * Create a properties path from a supplied class name. The format is: <b>property/[class name with forward
     * slashes].properties</b>
     * 
     * An example is:
     * 
     * gov/va/med/pharmacy/external/service/deliveryservice/DeliveryServiceCallbackFactory.java
     * 
     * property/gov/va/med/pharmacy/external/service/deliveryservice/DeliveryServiceCallbackFactory.properties
     * 
     * @param className class name
     * @return properties path
     */
    private static String createPath(Class className) {
        StringBuffer buff = new StringBuffer();
        buff.append("properties/");
        buff.append(className.getName().replace('.', '/'));
        buff.append(".properties");

        return buff.toString();
    }

    /**
     * 
     * Loads properties from a URL.
     * 
     * @param className Class for which to load properties file
     * @return Properties from URL
     * @throws IOException if unable to load properties from URL
     */
    private static Properties load(Class className) throws IOException {
        Properties properties = new Properties();

        String path = createPath(className);
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

        try {
            properties = new Properties();
            properties.load(stream);
        } finally {
            stream.close();
        }

        return properties;
    }

    /**
     * Override the given Properties with those supplied in the {@value #PROPERTY_OVERRIDE_FILE} file.
     * 
     * @param properties Properties to override
     */
    private static void overrideProperties(Properties properties) {
        for (Map.Entry<Object, Object> entry : PROPERTIES_OVERRIDE.entrySet()) {
            properties.setProperty((String) entry.getKey(), (String) entry.getValue());
        }
    }


}
