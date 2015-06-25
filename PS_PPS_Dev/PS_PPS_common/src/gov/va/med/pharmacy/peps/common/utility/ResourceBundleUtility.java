/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility;


import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Perform common operations with a {@link ResourceBundle}.
 */
public class ResourceBundleUtility {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ResourceBundleUtility.class);
    
    // must be "properties/" and not "properties" + File.separator or else a Windows WebLogic deployment won't work
    private static final String PROPERTIES_FOLDER = "properties/"; 
    private static final String UNABLE_TO_LOAD = "Unable to load key '";
    private static final String FROM_RESOURCE = "' from ResourceBundle with base name '";
    private static final String FOR_LOCALE = "' for Locale '";
    
    /**
     * Cannot instantiate
     */
    private ResourceBundleUtility() {
        super();
    }
    
    /**
     * Get the {@link ResourceBundle} for the given {@link Class} at the given {@link Locale}. The {@link ResourceBundle} is
     * loaded from a properties file located on the current thread's context class loader class path under a folder named
     * {@value #PROPERTIES_FOLDER} and with then under sub folders following the fully qualified class name of the given
     * class. For example, the default English {@link ResourceBundle} properties file for {@link ValueObject} would be found
     * at 'properties/gov/va/med/pharmacy/peps/common/vo/ValueObject.properties' on the current thread's context class loader
     * class path.
     * 
     * @param clazz {@link Class} for which to get the {@link ResourceBundle}
     * @param locale {@link Locale} of localization
     * @return {@link ResourceBundle} for the given {@link Class} at the given {@link Locale}
     * 
     * @see #getResourceBundle(String, Locale)
     */
    public static ResourceBundle getResourceBundle(Class clazz, Locale locale) {
        return getResourceBundle(PROPERTIES_FOLDER + clazz.getName(), locale);
    }

    /**
     * Get the {@link ResourceBundle} for the given baseName at the given {@link Locale}.
     * 
     * @param baseName String path to the base name for the {@link ResourceBundle}
     * @param locale {@link Locale} of localization
     * @return {@link ResourceBundle} for the given baseName at the given {@link Locale}
     * 
     * @see ResourceBundle#getBundle(String, Locale, ClassLoader)
     */
    public static ResourceBundle getResourceBundle(String baseName, Locale locale) {
        return ResourceBundle.getBundle(baseName, locale, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Get the localized value for the given key from the given Class' {@link ResourceBundle} for the given {@link Locale}.
     * If the given key is not found, return the key itself.
     * 
     * @param clazz {@link Class} for which to get the {@link ResourceBundle}
     * @param key {@link String} key to look up in {@link ResourceBundle}
     * @param suffix {@link String} suffix to append to the key
     * @param locale {@link Locale} of localization
     * @return String value of key in {@link ResourceBundle} or if not found, the key itself
     * 
     * @see #getResourceBundle(Class, Locale)
     */
    public static String getResourceBundleValue(Class clazz, String key, String suffix, Locale locale) {
        try {
            return getResourceBundle(clazz, locale).getString(key + suffix);
        } catch (Exception e) {
            LOG.warn(UNABLE_TO_LOAD + key + suffix + FROM_RESOURCE + PROPERTIES_FOLDER + clazz.getName()
                     + FOR_LOCALE + locale.getDisplayName() + "'. Trying to load the key without the suffix.");

            try {
                return getResourceBundle(clazz, locale).getString(key);
            } catch (Exception ex) {
                LOG.warn(UNABLE_TO_LOAD + key + FROM_RESOURCE + PROPERTIES_FOLDER + clazz.getName()
                         + FOR_LOCALE + locale.getDisplayName() + "'.  Returning key as localized value.");

                return key;
            }
        }
    }

    /**
     * Get the localized value for the given key from the given Class' {@link ResourceBundle} for the given {@link Locale}.
     * If the given key is not found, return the key itself.
     * 
     * @param clazz {@link Class} for which to get the {@link ResourceBundle}
     * @param key {@link String} key to look up in {@link ResourceBundle}
     * @param locale {@link Locale} of localization
     * @return String value of key in {@link ResourceBundle} or if not found, the key itself
     * 
     * @see #getResourceBundle(Class, Locale)
     */
    public static String getResourceBundleValue(Class clazz, String key, Locale locale) {
        try {
            return getResourceBundle(clazz, locale).getString(key);
        } catch (Exception e) {
            LOG.warn(UNABLE_TO_LOAD + key + FROM_RESOURCE + PROPERTIES_FOLDER
                + clazz.getName() + FOR_LOCALE + locale.getDisplayName() + "'. Returning key as localized value!");

            return key;
        }
    }

    /**
     * Get the localized value for the given key from the {@link ResourceBundle} at the given baseName for the given
     * {@link Locale}. If the given key is not found, return the key itself.
     * 
     * @param baseName String path to the base name for the {@link ResourceBundle}
     * @param key {@link String} key to look up in {@link ResourceBundle}
     * @param locale {@link Locale} of localization
     * @return String value of key in {@link ResourceBundle} or if not found, the key itself
     * 
     * @see #getResourceBundle(String, Locale)
     */
    public static String getResourceBundleValue(String baseName, String key, Locale locale) {
        try {
            return getResourceBundle(baseName, locale).getString(key);
        } catch (Exception e) {
            LOG.warn(UNABLE_TO_LOAD + key + FROM_RESOURCE + baseName + FOR_LOCALE
                + locale.getDisplayName() + "'. Returning key as localized value.");

            return key;
        }
    }
}
