/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymException;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymRuntimeException;


/**
 * AbstractCall adds another layer of abstraction for velocity templating
 */
public abstract class AbstractCall implements Call {
    
    /**
     * Valid
     */
    public static final String VALID = "Valid";
    
    /**
     * Invalid
     */
    public static final String INVALID = "Invalid";

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AbstractCall.class);
    private static final String PATH = "xml/";
    private static final String FILE_EXTENSION = ".xml";
    private static final Map<String, String> TEMPLATE_CACHE = new HashMap<String, String>(15, .9f);

    /**
     * Default constructor instantiates a Velocity Context
     */
    public AbstractCall() {

    }

    /**
     * This method combines the template data with run time values and returns a string
     * The template filename is resolved based on the class name
     * @param instance expects the filename of the template file
     * @return returns a string resulting from template processing
     * @throws PseudonymException If an exception occurred
     */
    public String processTemplate(Object instance) throws PseudonymException {
        return processTemplate(instance, null);
    }

    /**
     * This method combines the template data with run time values and returns a string
     * The template filename is resolved based on the class name and an additional string
     * @param instance expects the caller to pass it's instance
     * @param postfix the string to affix to the end of the filename
     * @return returns a string resulting from template processing
     * @throws PseudonymException If an exception occurred
     */
    public String processTemplate(Object instance, String postfix) throws PseudonymException {
        String filename = getTemplateName(instance, postfix);
        String template = createTemplate(filename);

        return escapeResponse(template);
    }

    /**
     * The eclispe editor ueses "\r\n" as an end of line delimiter, 
     * this had to be replaced with "\n" to be work with pseodonyM
     * @param response excects a string response
     * @return returns string without eclipse escape strings
     */
    protected String escapeResponse(String response) {
        return response.replaceAll("\r\n", "\n");
    }

    /**
     * This method caches the template files so they are loaded only one time
     * @param filename expects the filename of the template file
     * @return returns the template
     * @throws PseudonymException If an exception occurred
     */
    private synchronized String createTemplate(String filename) throws PseudonymException {
        LOG.debug("Creating template: " + filename);

        if (!TEMPLATE_CACHE.containsKey(filename)) {
            try {
                TEMPLATE_CACHE.put(filename, getTemplate(filename));
            } catch (Exception e) {
                throw new PseudonymException(e);
            }

        }

        return (String) TEMPLATE_CACHE.get(filename);
    }

    /**
     * This method finds the template filename based on the class instance and other strings
     * @param instance the object to get the template for
     * @param postfix what to affix to the end of the file name
     * @return the template name
     */
    private String getTemplateName(Object instance, String postfix) {
        StringBuffer filename = new StringBuffer();
        filename.append(PATH + instance.getClass().getName().replace('.', '/'));

        if (postfix != null) {
            filename.append(postfix);
        }

        filename.append(FILE_EXTENSION);

        return filename.toString();
    }

    /**
     * Load the given file and return its contents as a String
     * 
     * @param filename path and filename on the Thread's context classpath
     * @return String contents of file
     */
    private String getTemplate(String filename) {
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(filename)));

            StringBuffer contents = new StringBuffer();
            String l = inputStream.readLine();

            while (l != null) {
                contents.append(l);
                l = inputStream.readLine();

                if (l != null) {
                    contents.append("\n");
                }
            }

            inputStream.close();

            return contents.toString();
        } catch (IOException e) {
            throw new PseudonymRuntimeException("Unable to load response template from " + filename, e);
        }
    }
}
