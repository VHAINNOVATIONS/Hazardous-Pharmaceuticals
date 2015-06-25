/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.UrlPathHelper;

import gov.va.med.pharmacy.peps.common.vo.EntityType;


/**
 * Provide convenience methods for forming Spring Web Flow URLs.
 */
public class UrlUtility {

    /** constant */
    public static final String EXT_STRUTS = ".struts";

    /** constant */
    public static final String EXT_MVC = ".go"; // non-struts flows too.

    /** constant */
    public static final String EVENT_ID_PARAMETER = "_eventId";

    /** constant */
    public static final String EXECUTION_KEY_PARAMETER = "execution";

    /** constant */
    public static final UrlPathHelper URL_PATH_HELPER = new UrlPathHelper();

    private static final String URL_ENCODING_SCHEME = "UTF-8";

    /**
     * Cannot instantiate.
     */
    private UrlUtility() {
        super();
    }

    /**
     * Create a URL that when addressed will submit the form to an MVC controller.
     * 
     * @param request the current request
     * @return the MVC execution URL
     */
    public static String createMvcExecutionUrl(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
        url.append(URL_PATH_HELPER.getOriginatingRequestUri(request));

        if (request.getParameterMap() != null && !request.getParameterMap().isEmpty()) {
            url.append("?");
            appendQueryParameters(url, request.getParameterMap());
        }

        return url.toString();
    }

    /**
     * Create a URL that when addressed will execute an MVC handler method
     * 
     * @param request the current request
     * @param input the input to pass the flow execution
     * @return the mvc execution URL
     */
    public static String createMvcExecutionUrl(HttpServletRequest request, Map<String, Object> input) {
        StringBuffer url = new StringBuffer(createMvcExecutionUrl(request));

        if (input != null && !input.isEmpty()) {

            if (request.getParameterMap().isEmpty()) {
                url.append("?");
            } else {
                url.append("&");
            }

            appendQueryParameters(url, input);
        }

        return url.toString();
    }

    /**
     * Create a URL that when addressed will execute an MVC handler method
     * 
     * @param request the current request
     * @param entityType of the item
     * @param itemId of the item
     * @param event MVC event to execute minus the .go
     * @param input the input to pass the mvc execution
     * @return the mvc execution URL
     */
    public static String createMvcExecutionUrl(HttpServletRequest request, EntityType entityType, String itemId,
        String event,
        Map<String, Object> input) {

        StringBuffer url = new StringBuffer(createMvcExecutionUrl(request));

        url.append(entityType.toString().toLowerCase() + "/" + itemId + "/" + event + EXT_MVC + "/");

        if (input != null && !input.isEmpty()) {
            StringBuffer parameters = new StringBuffer(url);
            appendQueryParameters(parameters, input);

            url.append(parameters);
        }

        return url.toString();
    }

    /**
     * Add all parameters to the given URL StringBuffer.
     * 
     * @param url StringBuffer
     * @param parameters Map
     */
    public static void appendQueryParameters(StringBuffer url, Map<String, Object> parameters) {
        Iterator<Map.Entry<String, Object>> entries = parameters.entrySet().iterator();

        while (entries.hasNext()) {

            Map.Entry<String, Object> entry = entries.next();

            //If the URL already has a tab set, don't add another
            if ("tab".equalsIgnoreCase(entry.getKey()) && url.indexOf("tab") > -1) {
                break;
            } else {
                appendQueryParameter(url, entry.getKey(), entry.getValue());
            }

            if (entries.hasNext()) {
                url.append("&");
            }
        }
    }

    /**
     * Add a parameter to the given URL StringBuffer.
     * 
     * @param url
     *            StringBuffer with URL
     * @param key
     *            Object
     * @param value
     *            Object
     */
    private static void appendQueryParameter(StringBuffer url, Object key, Object value) {
        String encodedKey = encode(key);
        String encodedValue = null;

        //revised the following to correctly add all selected filters to the flowUrl
        if (value instanceof String[]) {
            int len = Array.getLength(value);

            for (int a = 0; a < len; a++) {
                encodedValue = encode(((String[]) value)[a]);
                url.append(encodedKey).append('=').append(encodedValue);

                if (a < len - 1) {
                    url.append("&");
                }
            }
        } else {
            encodedValue = encode(value);
            url.append(encodedKey).append('=').append(encodedValue);
        }

    }

    /**
     * If the given value is not null, call {@link #urlEncode(String)}. If the
     * value is null, return an empty String.
     * 
     * @param value
     *            Object
     * @return encoded object
     * 
     */
    private static String encode(Object value) {
        return value == null ? "" : urlEncode(String.valueOf(value));
    }

    /**
     * Encode the given String for use in an URL.
     * 
     * @param value
     *            String
     * @return encoded value
     */
    private static String urlEncode(String value) {
        try {
            return URLEncoder.encode(String.valueOf(value), URL_ENCODING_SCHEME);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Cannot url encode " + value);
        }
    }

}
