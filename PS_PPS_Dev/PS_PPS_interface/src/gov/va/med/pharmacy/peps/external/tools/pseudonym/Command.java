/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.Call;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Parser;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Response;


/**
 * This class handles incoming requests to the server. It also caches the properties file for quicker access to class names, and
 * monitors the file for changes to allow for it to reload on the fly if needed. Also handles loading the classes as they are
 * needed and invoking the required methods on them.
 */
public class Command {

    private static final Logger LOG = Logger.getLogger(Command.class);
    private static final Pattern MUMPS_COMMAND = Pattern
            .compile("[0-9]{2}XOB RPC.*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Map<Pattern, Call> COMMANDS = loadCommands();

    private String secParam;

    /**
     * Empty constructor
     */
    public Command() {
        super();
    }

    /**
     * Set the secParam that was cached from a previous command.
     * 
     * @param secParam
     *            String secParam
     */
    public Command(String secParam) {
        this.secParam = secParam;
    }

    /**
     * This method is used to load the command to handling class mapping from the properties file into memory.
     * 
     * @return Map<Pattern, Call> Commands that can be called
     */
    private static Map<Pattern, Call> loadCommands() {
        Map<Pattern, Call> patterns = new HashMap<Pattern, Call>();

        try {
            Properties commands = PropertyUtility.loadPropertiesWithoutOverride(Command.class);

            for (Map.Entry entry : commands.entrySet()) {
                LOG.debug("loading pattern: " + entry.getKey());

                if ("".equals(entry.getValue())) {
                    LOG.warn("the value for \"" + entry.getKey() + "\" is empty. Ignoring this command!");
                } else {
                    Pattern pattern = Pattern.compile((String) entry.getKey(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Call call = loadCall((String) entry.getValue());

                    patterns.put(pattern, call);
                }
            }
        } catch (Exception e) {
            throw new PseudonymRuntimeException("Error loading PseudonyM commands!", e);
        }

        return patterns;
    }

    /**
     * This method is used to find and instantiate a class based on the name of the class that is passed to it. It also verifies
     * that the class passed implements the Call class to make sure it is able to be used to handle an incoming command.
     * 
     * @param name
     *            the name of the class to load
     * @return instance of the class loaded
     * @throws PseudonymException
     *             exception that occurs if the class that was loaded does not correctly implement the call interface
     */
    private static Call loadCall(String name) throws PseudonymException {
        try {
            @SuppressWarnings("rawtypes")
            Class c = Thread.currentThread().getContextClassLoader().loadClass(name);

            if (!Call.class.isAssignableFrom(c)) {
                throw new PseudonymException(name + " should implement " + Call.class.getName());
            }

            return (Call) c.newInstance();
        } catch (Exception e) {
            throw new PseudonymException(e);
        }
    }

    /**
     * This method is capable of handling both generic WistALink calls as well as mumps calls. The method will determine which
     * type of call was passed to it and take the appropriate action to return the response.
     * 
     * @param text
     *            The request to respond to
     * @return String representing the appropriate response for this call
     * @throws PseudonymException
     *             throws any exception that may have occurred while making the call to the getResponse method in the class that
     *             was loaded
     */
    public String getResponse(String text) throws PseudonymException {
        if (MUMPS_COMMAND.matcher(text).matches()) {
            return getMumpsResponse(text);
        }

        try {
            return lookupCall(text).getResponse(null).toString();
        } catch (Exception e) {
            throw new PseudonymException(e);
        }
    }

    /**
     * Used to handle mumps commands. This first parses the command and creates a request object. Next it determines which
     * method handles this type of call and passes the request object to that method and retrieves the Response object which it
     * then returns.
     * 
     * @param text
     *            the command to parse and return a result for
     * @return String representing the response to the call
     * @throws PseudonymException
     *             throws any exception that may have occurred while making the call to the getResponse method in the class that
     *             was loaded or any exceptions that may have occurred while loading the class
     */
    public String getMumpsResponse(String text) throws PseudonymException {
        Parser parser = new Parser(text);
        Request request = parser.parse();
        Response response;
        String data = "";

        // Section dealing with Migration Requests from the Service Tests
        if ("PPS NDFMS MIGR SYNC".equals(request.getRpc())) {
            ServiceTestSampleMigrationXML sample = new ServiceTestSampleMigrationXML();
            response = new Response(request, sample.getMigrSyncResponse(text));

            return response.toString();
        }

        if ("PPS NDFMS MIGR".equals(request.getRpc())) {
            if (text.contains("<numElements>37</numElements>")) {
                ServiceTestSampleMigrationXML sample = new ServiceTestSampleMigrationXML();
                response = new Response(request, sample.getXML(text));

                return response.toString();
            }

            response = new Response(request, data);
        }

        if ("PPS NDFMS SYNC".equals(request.getRpc())) {
            if (text.contains("gov/va/med/pharmacy/peps/external/common/vo/outbound/sync")) {
                ServiceTestSampleMigrationXML sample = new ServiceTestSampleMigrationXML();
                response = new Response(request, sample.getSyncXML(text));

                return response.toString();
            }

            response = new Response(request, data);
        }

        if (request.getSecParam() == null) {
            request.setSecParam(getSecParam());
        } else {
            setSecParam(request.getSecParam());
        }

        try {
            response = lookupCall(formatCommand(request)).getResponse(request);
        } catch (Exception e) {
            throw new PseudonymException(e);
        }

        return response.toString();
    }

    /**
     * Used when a response is needed for a specific command. The method scans through the commands that were loaded into memory
     * and finds the correct class.
     * 
     * @param command
     *            the command to get a response for
     * @return the class that is specified to handle this type of command
     * @throws PseudonymException
     *             thrown if the command parameter was not able to be matched to any known command
     */
    private Call lookupCall(String command) throws PseudonymException {
        for (Map.Entry<Pattern, Call> entry : COMMANDS.entrySet()) {
            Pattern pattern = entry.getKey();
            Call call = entry.getValue();

            if (pattern.matcher(command).matches()) {
                return call;
            }
        }

        throw new PseudonymException("unknown mumps command: " + command);
    }

    /**
     * Formats the request object into a format such that the request can be correctly mapped to the proper handling class.
     * 
     * @param request
     *            parsed command to format
     * @return string representation of the command formatted for easy lookup
     */
    private String formatCommand(Request request) {
        LOG.debug("mumps request: " + request);

        StringBuffer buff = new StringBuffer(PPSConstants.I128);
        buff.append(request.getRpc()).append(",").append(request.getRcx()).append(",").append(request.getRas());

        return buff.toString();
    }

    /**
     * If the request for this command included the secParam, the value is set here for subsequent calls to use. It is assumed
     * that an external class retrieves the value and caches it for other commands.
     * 
     * @return secParam property
     */
    public String getSecParam() {
        return secParam;
    }

    /**
     * If the request for this command included the secParam, the value is set here for subsequent calls to use. It is assumed
     * that an external class retrieves the value and caches it for other commands.
     * 
     * @param secParam
     *            secParam property
     */
    public void setSecParam(String secParam) {
        this.secParam = secParam;
    }
}
