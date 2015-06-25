/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.test.integration;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


/**
 * Loads Spring ApplicationContext and InitialContext properties for use in integration test cases.
 */
public abstract class IntegrationTestCase extends 
    gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase { 
    private static final String PROPERTIES_FILE = 
        "properties/gov/va/med/pharmacy/peps/common/test/integration/IntegrationTestCase.properties";
    private static final Logger LOG = Logger.getLogger(IntegrationTestCase.class);
    
    private String localContextFactory;
    private String localHost;
    private int localPort;
    private String localProviderUrl;
    private String fdbDifJdbcUrl;
    private String fdbDifUser;
    private String fdbDifPassword;
    private String fdbDifDriver;

    /**
     * Setup properties
     */
    public IntegrationTestCase() {

        super();

        initialize();
    }

    /**
     * Setup properties
     * 
     * @param name TestCase name
     */
    public IntegrationTestCase(String name) {

        super(name);

        initialize();
    }

    /**
     * Read properties and instantiate ApplicationContext
     */
    private void initialize() {
        Properties properties = new Properties();

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            LOG.debug(e); 
        }

        this.localContextFactory = properties.getProperty("local.context.factory");
        this.localHost = properties.getProperty("local.host");
        this.localPort = Integer.parseInt(properties.getProperty("local.port"));
        this.localProviderUrl = properties.getProperty("local.provider.url");

        this.fdbDifJdbcUrl = properties.getProperty("local.fdb.dif.jdbc.url");
        this.fdbDifUser = properties.getProperty("local.fdb.dif.user");
        this.fdbDifPassword = properties.getProperty("local.fdb.dif.password");
        this.fdbDifDriver = properties.getProperty("local.fdb.dif.driver");
    }

    /**
     * Context factory for remote lookup, e.g. weblogic.jndi.WLInitialContextFactory
     * 
     * @return String context factory
     */
    public String getLocalContextFactory() {
        return localContextFactory;
    }

    /**
     * Provider URL, e.g. t3://localhost:8010
     * 
     * @return String provider URL
     */
    public String getLocalProviderUrl() {
        return localProviderUrl;
    }

    /**
     * String host name, e.g. localhost
     * 
     * @return String host name
     */
    public String getLocalHost() {
        return localHost;
    }

    /**
     * Port the server is running on, e.g. 8010
     * 
     * @return int port number
     */
    public int getLocalPort() {
        return localPort;
    }

    /**
     * JDBC URL for FDB DIF
     * 
     * @return fdbDifJdbcUrl property
     */
    public String getFdbDifJdbcUrl() {
        return fdbDifJdbcUrl;
    }

    /**
     * Password for FDB DIF user
     * 
     * @return fdbDifPassword property
     */
    public String getFdbDifPassword() {
        return fdbDifPassword;
    }

    /**
     * FDB DIF user name
     * 
     * @return fdbDifUser property
     */
    public String getFdbDifUser() {
        return fdbDifUser;
    }

    /**
     * FDB DIF JDB driver class name
     * 
     * @return fdbDifDriver property
     */
    public String getFdbDifDriver() {
        return fdbDifDriver;
    }

    /**
     * Create a new DataSource to the FDB DIF database
     * 
     * @return DataSource pointing to FDB DIF database
     */
    public DataSource getFdbDifDataSource() {
        return new DriverManagerDataSource(getFdbDifDriver(), getFdbDifJdbcUrl(), getFdbDifUser(), getFdbDifPassword());
    }

    /**
     * Read the text from the given path to a file.
     * 
     * @param path String file path from which to read text
     * @return String text contained in the file represented by the file
     * @throws IOException if error reading from the file
     */
    public String readInputStream(String path) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer xmlRequest = new StringBuffer();
        String line = reader.readLine();

        while (line != null) {
            xmlRequest.append(line);
            line = reader.readLine();
        }

        inputStream.close();

        return xmlRequest.toString();
    }
}
