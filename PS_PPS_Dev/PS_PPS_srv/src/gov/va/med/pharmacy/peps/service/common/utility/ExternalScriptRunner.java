/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.io.IOException;
import java.util.Properties;

import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;


/**
 * This class will be able to run scripts on the deployment platform outside of the WebLogic environment. The goal is to
 * allow the business services to call the COTS update script, FDB in this case.
 * 
 */
public class ExternalScriptRunner {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ExternalScriptRunner.class);

    private static final String FDB_LINUX = "fdb_linux";
    private static final String FDB_WINDOWS = "fdb_windows";

    private boolean windows;
    private Properties properties;

    /**
     * Basic constructor. Determines which OS we are running on and sets the windows flag correctly.
     * 
     * @param windows boolean true if running in MS Windows OS.
     * @throws IOException if cannot load properties file containing commands to run
     */
    public ExternalScriptRunner(boolean windows) throws IOException {
        this.properties = PropertyUtility.loadProperties(ExternalScriptRunner.class);
        this.windows = windows;
    }

    /**
     * Runs the external script specified by the key passed in.
     * 
     * @param scriptKey The key into the properties file.
     * @return A success value.
     */
    private boolean runExternalScript(String scriptKey) {
        String command = properties.getProperty(scriptKey);
        LOG.debug("Running Command: '" + command + "'");
        int exitValue = -1;

        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            exitValue = p.exitValue();
        } catch (IOException e) {
            LOG.error("IOException caught while running external script", e);

            return (false);
        } catch (InterruptedException e) {
            LOG.error("InterruptedException caught while running external script", e);

            return (false);
        }

        return (0 == exitValue);
    }

    /**
     * Runs the FDB update scripts based on the OS type.
     * 
     * @return Success value.
     */
    public boolean runFdbUpdateScript() {
        if (windows) {
            return (runExternalScript(FDB_WINDOWS));
        } else {
            return (runExternalScript(FDB_LINUX));
        }
    }
}
