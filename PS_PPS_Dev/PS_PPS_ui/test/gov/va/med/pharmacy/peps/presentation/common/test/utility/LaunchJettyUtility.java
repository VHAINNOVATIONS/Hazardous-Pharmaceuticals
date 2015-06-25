/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.test.utility;


import org.apache.log4j.Logger;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;


/**
 * Start Jetty, setting the port, context, web app directory, and web xml file. This allows us to run PRE V. 1.0 without
 * deploying to WebLogic.
 */
public class LaunchJettyUtility {

    private static final Logger LOG = Logger.getLogger(LaunchJettyUtility.class);

    private static final String PRE_CONTEXT_PATH = "/PRE";
    private static final String PRE_WAR = "web";
    private static final String FDB_IMAGES_CONTEXT_PATH = "/FDB_Images";
    private static final int EXIT_CODE_BAD = 100;
    
    /**
     * Description here
     *
     */
    private LaunchJettyUtility() {

    }

    /**
     * Run PRE V. 1.0 in Jetty. Override the EvironmentUtility's set environment depending on what web.xml file is used
     * (local or national).
     * 
     * This method expects four command line arguments in the order and type specified. No verification that the arguments
     * are valid is done. If improper arguments are set, expect NullPointerExceptions, IOExceptions, String parsing
     * exceptions, or that the server started simply does not operate as expected.
     * 
     * @param args [0] (int) port, [1] (String) PRE web.xml file
     *            path, [2] (String) FDB Images web application directory
     */
    public static void main(String[] args) {
        Server server = new Server(Integer.parseInt(args[0]));
        
        WebAppContext pre = new WebAppContext();
        
        /* 
         * the default configuration classes marked with a 'default' comment
         * here we are replacing the default configuration with a custom one,
         * however we still need the default configurations, they are also
         * included in this new configuration.
         * see http://docs.codehaus.org/display/JETTY/JNDI  for additional info
         */

//        String[] plusConfiguration = new String[]{
//            "org.mortbay.jetty.webapp.WebInfConfiguration", // default
//            "org.mortbay.jetty.webapp.WebXmlConfiguration", // default
//            "org.mortbay.jetty.plus.webapp.Configuration", // added for JNDI
//            "org.mortbay.jetty.plus.webapp.EnvConfiguration", // added for JNDI
//            "org.mortbay.jetty.webapp.JettyWebXmlConfiguration", // default
//            "org.mortbay.jetty.webapp.TagLibConfiguration" // default
//        };
        
        // set the new configuration
        //pre.setConfigurationClasses(plusConfiguration);
        
        // this may need to be added to the National launch debug configuration
        // -Djava.naming.factory.initial=org.mortbay.naming.InitialContextFactory
        
        pre.setContextPath(PRE_CONTEXT_PATH);
        pre.setWar(PRE_WAR);
        pre.setOverrideDescriptor(args[1]);

//        String[] confClasses = pre.getConfigurationClasses();
//        for(String i : confClasses) {
//            System.out.println(i + "\n");
//        }
        server.addHandler(pre);
        
        WebAppContext fdbImages = new WebAppContext();
        fdbImages.setContextPath(FDB_IMAGES_CONTEXT_PATH);
        fdbImages.setWar(args[2]);
        server.addHandler(fdbImages);

        try {
            server.start();

            LOG.info("Jetty server started on port " + args[0]);
        } catch (Exception e) {
            LOG.fatal(e);
            System.exit(EXIT_CODE_BAD);
        }
    }
}
