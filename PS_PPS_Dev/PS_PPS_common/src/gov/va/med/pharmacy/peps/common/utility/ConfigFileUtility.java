/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.springframework.stereotype.Component;


/**
 * The ConfigFileUtility class is used to setup the configuration file 
 *
 */
@Component
public class ConfigFileUtility {

    /** IMAGE_PATH */
    public static final String DEFAULT_FDB_IMAGE_PATH = "/FDB_Images/";

    /** DEFAULT_PORT */
    public static final int DEFAULT_PORT = 8001;

    /** DEFAULT_TIMEOUT */
    public static final int DEFAULT_TIMEOUT = 60;

    /** DEFAULT_PROXY */
    public static final long DEFAULT_PROXY = 10000000058l;

    /** DEFAULT_DIVISION */
    public static final int DEFAULT_DIVISION = 500;

    /** DEFAULT_CONNSPEC */
    public static final String DEFAULT_CONNSPEC = "DUZ";

    /** DEFAULT_RPCTIMEOUT */
    public static final int DEFAULT_RPCTIMEOUT = 60000;

//    private static final Logger LOG = Logger.getLogger(ConfigFileUtility.class);

    private static String START_PORT_TAG = "<NationalPort>";
    private static String STOP_PORT_TAG = "</NationalPort>";
    private static String START_TIMEOUT_TAG = "<TransactionTimeout>";
    private static String STOP_TIMEOUT_TAG = "</TransactionTimeout>";
    private static String START_PROXY_TAG = "<NDFProxyUserIEN>";
    private static String STOP_PROXY_TAG = "</NDFProxyUserIEN>";
    private static String START_DIV_TAG = "<NDFDivision>";
    private static String STOP_DIV_TAG = "</NDFDivision>";
    private static String START_CONNSPEC_TAG = "<ConnectionSpecName>";
    private static String STOP_CONNSPEC_TAG = "</ConnectionSpecName>";
    private static String START_RPCTIMEOUT_TAG = "<RPCTimeout>";
    private static String STOP_RPCTIMEOUT_TAG = "</RPCTimeout>";
    private static String START_SWRI_TAG = "<SWRI>";
    private static String STOP_SWRI_TAG = "</SWRI>";
    private static String START_FDBLOC_TAG = "<FDBImageLocation>";
    private static String STOP_FDBLOC_TAG = "</FDBImageLocation>";

    private boolean swri;
    private int port;
    private int timeout;
    private long ndfProxyUserDUZ;
    private int division;
    private int rpcTimeout;
    private String connectionSpecName;
    private String fdbImageLocation;

    /**
     * Default Constructor
     */
    public ConfigFileUtility() {

        init();
    }

    /**
     * getConnectionSpecName
     * @return connectionSpecName
     */
    public String getConnectionSpecName() {

        return connectionSpecName;
    }

    /**
     * setConnectionSpecName
     * @param connectionSpecName : connectionSpecName
     */
    public void setConnectionSpecName(String connectionSpecName) {

        this.connectionSpecName = connectionSpecName;
    }

    /**
     * getSwRI
     * @return swri
     */
    public boolean isSwri() {

        return swri;
    }

    /**
     * setSwRI
     * @param bswri : swri
     */
    public void setSwRI(boolean bswri) {

        this.swri = bswri;
    }

    /**
     * getRpcTimeout
     * @return rpcTimeout
     */
    public int getRpcTimeout() {

        return rpcTimeout;
    }

    /**
     * setRpcTimeout
     * @param rpcTimeout : rpcTimeout
     */
    public void setRpcTimeout(int rpcTimeout) {

        this.rpcTimeout = rpcTimeout;
    }

    /**
     * getPort
     * @return port
     */
    public int getPort() {

        return port;
    }

    /**
     * setPort
     * @param port : port
     */
    public void setPort(int port) {

        this.port = port;
    }

    /**
     * getTimeout    
     * @return : timeout
     */
    public int getTimeout() {

        return timeout;
    }

    /**
     * setTimeout
     * @param timeout : timeout
     */
    public void setTimeout(int timeout) {

        this.timeout = timeout;
    }

    /**
     * getNdfProxyUserDUZ
     * @return ndfProxyUserDUZ
     */
    public long getNdfProxyUserDUZ() {

        return ndfProxyUserDUZ;
    }

    /**
     * setNdfProxyUserDUZ
     * @param ndfProxyUserDUZ : ndfProxyUserDUZ
     */
    public void setNdfProxyUserDUZ(int ndfProxyUserDUZ) {

        this.ndfProxyUserDUZ = ndfProxyUserDUZ;
    }

    /**
     * getDivision
     * @return division
     */
    public int getDivision() {

        return division;
    }

    /**
     * setDivision
     * @param division division
     */
    public void setDivision(int division) {

        this.division = division;
    }

    /**
     * get the fdbImageLocation
     * 
     * @return the fdbImageLocation
     */
    public String getFdbImageLocation() {

        return fdbImageLocation;
    }

    /**
     * sets fdbImageLocation field
     * 
     * @param fdbImageLocation the fdbImageLocation to set
     */
    public void setFdbImageLocation(String fdbImageLocation) {

        this.fdbImageLocation = fdbImageLocation;
    }

    /**
     * init()
     */
    private void init() {

        port = DEFAULT_PORT;
        timeout = DEFAULT_TIMEOUT;
        ndfProxyUserDUZ = DEFAULT_PROXY;
        division = DEFAULT_DIVISION;
        connectionSpecName = DEFAULT_CONNSPEC;
        rpcTimeout = DEFAULT_RPCTIMEOUT;
        fdbImageLocation = DEFAULT_FDB_IMAGE_PATH;

        String s = new String();
        swri = false;

        try {
            String currentDir = System.getProperty("user.dir");
            log("Current Directory is " + currentDir);

            String osName = System.getProperty("os.name");
            log("Operating System is " + osName);

            String fileName = "";

            if (osName.contains("win") || osName.contains("Win")) {
                fileName = currentDir + "\\..\\PS_PPS_common\\etc\\xml\\PPS-NConfig.xml";
            } else {
                fileName = currentDir + "/config/PPS-NConfig.xml";
            }

            log("filename is " + fileName);

            FileReader fr = new FileReader(new File(fileName));
            BufferedReader br = new BufferedReader(fr);

            int iPort;
            int iTimeout;
            long iNDFProxy;
            int iDivision;
            int iRPCTimeout;
            boolean iSwRI;
            String iConnSpec;
            String iFdbLocation;

            while ((s = br.readLine()) != null) {
                try {
                    iPort = this.getPropertyInt(s, START_PORT_TAG, STOP_PORT_TAG, DEFAULT_PORT);
                    port = iPort;
                } catch (PropertyNotFoundInXmlLineException e) {
                    log(e.getMessage());
                }

                try {
                    iTimeout = this.getPropertyInt(s, START_TIMEOUT_TAG, STOP_TIMEOUT_TAG, DEFAULT_TIMEOUT);
                    timeout = iTimeout;
                } catch (PropertyNotFoundInXmlLineException e) {
                    log(e.getMessage());
                }

                try {
                    iNDFProxy = this.getPropertyLong(s, START_PROXY_TAG, STOP_PROXY_TAG, DEFAULT_PROXY);
                    ndfProxyUserDUZ = iNDFProxy;
                } catch (PropertyNotFoundInXmlLineException e) {
                    log(e.getMessage());
                }

                try {
                    iDivision = this.getPropertyInt(s, START_DIV_TAG, STOP_DIV_TAG, DEFAULT_DIVISION);
                    division = iDivision;
                } catch (PropertyNotFoundInXmlLineException e) {
                    log(e.getMessage());
                }

                try {
                    iRPCTimeout = this.getPropertyInt(s, START_RPCTIMEOUT_TAG, STOP_RPCTIMEOUT_TAG, DEFAULT_RPCTIMEOUT);
                    rpcTimeout = iRPCTimeout;
                } catch (PropertyNotFoundInXmlLineException e) {
                    log(e.getMessage());
                }

                try {
                    iConnSpec = getProperty(s, START_CONNSPEC_TAG, STOP_CONNSPEC_TAG, null);
                    connectionSpecName = iConnSpec;
                } catch (PropertyNotFoundInXmlLineException e) {
                    log(e.getMessage());
                }

                try {
                    iSwRI = Boolean.parseBoolean(getProperty(s, START_SWRI_TAG, STOP_SWRI_TAG, null));
                    swri = iSwRI;
                } catch (PropertyNotFoundInXmlLineException e) {
                    log(e.getMessage());
                }

                try {
                    iFdbLocation = getProperty(s, START_FDBLOC_TAG, STOP_FDBLOC_TAG, null);
                    fdbImageLocation = iFdbLocation;
                } catch (PropertyNotFoundInXmlLineException e) {
                    log(e.getMessage());
                }
            }

            br.close();
        } catch (Exception e) {
            log("Exception: " + e);
            log("Setting defaults port:" + port + " timeout:" + timeout + " ndfDUZ:" + ndfProxyUserDUZ + " division: "
                + division);
        }
    }

    /**
     * getPropertyInt - gets an integer property from the xml line
     *
     * @param s String
     * @param startTag String
     * @param stopTag String
     * @param defaultValue int
     * @return int
     * @throws PropertyNotFoundInXmlLineException exception
     */
    private int getPropertyInt(String s, String startTag, String stopTag, int defaultValue)
        throws PropertyNotFoundInXmlLineException {

        int rv = defaultValue;

        if (s.contains(startTag) && s.contains(stopTag)) {
            int beginIndex = s.indexOf(startTag) + startTag.length();
            int endIndex = s.indexOf(stopTag);
            String strPort = s.substring(beginIndex, endIndex);

            try {
                rv = Integer.valueOf(strPort).intValue();
            } catch (NumberFormatException nfe) {
                rv = defaultValue;
            }

            return rv;
        }

        throw new PropertyNotFoundInXmlLineException();
    }
    
    private long getPropertyLong(String s, String startTag, String stopTag, long defaultValue)
        throws PropertyNotFoundInXmlLineException {

        long rv = defaultValue;

        if (s.contains(startTag) && s.contains(stopTag)) {
            int beginIndex = s.indexOf(startTag) + startTag.length();
            int endIndex = s.indexOf(stopTag);
            String strPort = s.substring(beginIndex, endIndex);

            try {
                rv = Long.valueOf(strPort).longValue();
            } catch (NumberFormatException nfe) {
                rv = defaultValue;
            }

            return rv;
        }

        throw new PropertyNotFoundInXmlLineException();
    }


    /**
     * getProperty - gets a value from the xml line (a string)
     *
     * @param s String
     * @param startTag String
     * @param stopTag String
     * @param defaultValue String
     * @return String
     * @throws PropertyNotFoundInXmlLineException exception
     */
    private String getProperty(String s, String startTag, String stopTag, String defaultValue)
        throws PropertyNotFoundInXmlLineException {

        String rv = defaultValue;

        if (s.contains(startTag) && s.contains(stopTag)) {
            int beginIndex = s.indexOf(startTag) + startTag.length();
            int endIndex = s.indexOf(stopTag);
            rv = s.substring(beginIndex, endIndex);

            return rv;
        }

        throw new PropertyNotFoundInXmlLineException();
    }

    /**
     * log 
     * @param s : String to Log
     */
    private void log(String s) {

    }

    /**
     * this exception class is used to skip the assignment of a property.
     */
    private class PropertyNotFoundInXmlLineException extends Exception {

        /** serialVersionUID */
        private static final long serialVersionUID = 8015312492996833208L;
        private static final String DEFAULT_MSG = "Property not found in input string";

        /**
         * Constructor
         *
         */
        public PropertyNotFoundInXmlLineException() {

            super(DEFAULT_MSG);
        }

        /**
         * Constructor
         *
         * @param message String
         */
        @SuppressWarnings("unused")
        public PropertyNotFoundInXmlLineException(String message) {

            super(message);
        }

    }
}
