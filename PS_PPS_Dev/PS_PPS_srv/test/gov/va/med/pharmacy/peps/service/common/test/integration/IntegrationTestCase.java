/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.test.integration;


import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.ejb.access.SimpleRemoteStatelessSessionProxyFactoryBean;
import org.springframework.jndi.JndiTemplate;
import org.springframework.util.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;

import junit.framework.TestCase;


/**
 * Loads Spring ApplicationContext and InitialContext properties for use in integration test cases.
 * 
 * Abstract so that the class does not get instantiated alone.
 */
public abstract class IntegrationTestCase extends TestCase {
  
    /**
     * MAX_SEARCH_RETRIEVE_FAIL
     */
    protected static final String MAX_SEARCH_RETRIEVE_FAIL = "Time elapsed exceeds maximum retrieval time.";
 
    /**
     *  protected static final String TOO_LONG_RETRIEVE_FAIL = "Time elapsed exceeds even the generous retrival time.";
     */
    protected static final String TOO_LONG_RETRIEVE_FAIL = "Time elapsed exceeds even the generous retrival time.";

    /**
     * PNM1
     */
    protected static final UserVo PNM1 = new UserGenerator().getNationalManagerOne();
    
    /**
     * PNM2
     */
    protected static final UserVo PNM2 = new UserGenerator().getNationalManagerTwo();
    
    private static final int MAX_SEARCH_RETRIEVE_TIME = 4000; // milliseconds
    private static final int TOO_LONG_RETRIEVE_TIME = 6000; // milliseconds
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(IntegrationTestCase.class);
    private HostProperties national = new HostProperties();
    private boolean outOfContainer = false;

    /**
     * Setup ApplicaitonContext and properties
     */
    public IntegrationTestCase() {

        super();

        try {
            initialize();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Setup ApplicaitonContext and properties
     * 
     * @param name TestCase name
     */
    public IntegrationTestCase(String name) {

        super(name);

        try {
            initialize();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * underMaxWaitTime
     * @param start start
     * @param end end
     * @return boolean
     */
    protected static boolean underMaxWaitTime(final Date start, final Date end) {
        
        validateArguments(start, end);

        long diff = end.getTime() - start.getTime();
        
        return diff <= MAX_SEARCH_RETRIEVE_TIME;
    }

    /**
     * underTooLongTime
     * @param start start
     * @param end end
     * @return boolean
     */
    protected static boolean underTooLongTime(final Date start, final Date end) {
        validateArguments(start, end);
        long diff = end.getTime() - start.getTime();
        
        return diff <= TOO_LONG_RETRIEVE_TIME;
    }

    /**
     *     protected static void validateArguments(final Date start, final Date end) throws IllegalArgumentException {
     * @param start start
     * @param end end
     */
    protected static void validateArguments(final Date start, final Date end) {
        if (start == null) {
            throw new IllegalArgumentException("argument 'start' is null.");
        } else if (end == null) {
            throw new IllegalArgumentException("argument 'end' is null.");
        } else if (start.getTime() > end.getTime()) {
            throw new IllegalArgumentException("argument 'start' is occurs after argument 'end', switch them around.");
        }
    }

    /**
     * outputs the elapsed time in minutes and seconds
     *
     * @param start start
     * @param end end
     * @return String
     */
    protected static String showTimeDiff(final Date start, final Date end) {
    
        String rv = null;

        validateArguments(start, end);

        rv = "0:00.0";
        
        if (start.getTime() == end.getTime()) {
            return rv;
        }

        BigInteger diff = BigInteger.valueOf(end.getTime()).subtract(BigInteger.valueOf(start.getTime()));

        BigInteger millis = diff.mod(BigInteger.valueOf(PPSConstants.L1000));
        BigInteger seconds = BigInteger.valueOf(0);
        BigInteger minutes = BigInteger.valueOf(0);

        diff = diff.subtract(millis);
        diff = diff.divide(BigInteger.valueOf(PPSConstants.L1000)); 

        if (diff.intValue() > BigInteger.valueOf(PPSConstants.I60).intValue()) {
            seconds = diff.mod(BigInteger.valueOf(PPSConstants.I60));
            diff = diff.subtract(seconds);
            minutes = diff.divide(BigInteger.valueOf(PPSConstants.I60));
        } else {
            seconds = diff;
        }

        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb, Locale.US);
        f.format("%d:%02d.%03d", minutes, seconds, millis);

        return sb.toString();
    }

    /**
     * Read properties and instantiate ApplicationContext
     * @throws IOException  IOException
     */
    private void initialize() throws IOException {
       
        Properties properties = PropertyUtility.loadProperties(IntegrationTestCase.class);
        
        national.contextFactory = properties.getProperty("national.context.factory");
        national.host = properties.getProperty("national.host");

        String nationalPort = properties.getProperty("national.port");

        if (NumberUtils.isNumber(nationalPort)) {
            national.port = Integer.parseInt(nationalPort);
        }

        national.providerUrl = properties.getProperty("national.provider.url");

/*        localOne.contextFactory = properties.getProperty("local-1.context.factory");
        localOne.host = properties.getProperty("local-1.host");

        String localOnePort = properties.getProperty("local-1.port");

        if (NumberUtils.isNumber(localOnePort)) {
            localOne.port = Integer.parseInt(localOnePort);
        }

        localOne.providerUrl = properties.getProperty("local-1.provider.url");

        localTwo.contextFactory = properties.getProperty("local-2.context.factory");
        localTwo.host = properties.getProperty("local-2.host");

        String localTwoPort = properties.getProperty("local-2.port");

        if (NumberUtils.isNumber(localTwoPort)) {
            localTwo.port = Integer.parseInt(localTwoPort);
        }

        localTwo.providerUrl = properties.getProperty("local-2.provider.url");*/

        this.outOfContainer = Boolean.parseBoolean(properties.getProperty("out.of.container"));
    }

    /**
     * Create a Spring {@link JndiTemplate} for the given context factory and provider URL.
     * 
     * @param contextFactory String initial context factory
     * @param providerUrl String provider URL
     * @return JndiTemplate for the given context factory and provider URL
     */
    private JndiTemplate getJndiTemplate(String contextFactory, String providerUrl) {
        Properties environment = new Properties();
        environment.setProperty(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
        environment.setProperty(Context.PROVIDER_URL, providerUrl);

        return new JndiTemplate(environment);
    }

    /**
     * Create an {@link InitialContext} for the given context factory and provider URL.
     * 
     * @param contextFactory String initial context factory
     * @param providerUrl String provider URL
     * @return InitialContext for the given context factory and provider URL
     * @throws NamingException if error instantiating InitialContext
     */
    private InitialContext getInitialContext(String contextFactory, String providerUrl) throws NamingException {
        Hashtable<String, Object> environment = new Hashtable<String, Object>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
        environment.put(Context.PROVIDER_URL, providerUrl);

        return new InitialContext(environment);
    }


    /**
     * Get an instance of a national service.
     * 
     * @param <T> Type of service
     * @param service Class for service instance to get
     * @return service instance
     */
    public <T> T getNationalService(Class<T> service) {
        if (outOfContainer) {
            LOG.debug("Returning  " + getClassName(service) + " out of container");

            return (T) SpringTestConfigUtility.getNationalSpringBean(service);
        } else {
            return getService(service, getNationalJndiTemplate());
        }
    }

    /**
     * Get the Spring {@link JndiTemplate} for NationalPharmacyServer.
     * 
     * @return JndiTemplate to NationalPharmacyServer
     */
    public JndiTemplate getNationalJndiTemplate() {
        return getJndiTemplate(getNationalContextFactory(), getNationalProviderUrl());
    }

    /**
     * Get the {@link InitialContext} for NationalPharmacyServer.
     * 
     * @return InitialContext to NationalPharmacyServer
     * @throws NamingException If error getting InitialContext
     */
    public InitialContext getNationalInitialContext() throws NamingException {
        return getInitialContext(getNationalContextFactory(), getNationalProviderUrl());
    }

    /**
     * Return the class name without the package structure.
     * 
     * @param service Class of service
     * @return String class name without package
     */
    private String getClassName(Class service) {
        return StringUtils.unqualify(service.getName());
    }

    /**
     * Lookup a service in a server's JNDI tree.
     * 
     * @param <T> Class type of service
     * @param service Class of service to lookup
     * @param jndiTemplate Spring JndiTemplate to use for lookup
     * @return instance of service
     */
    private <T> T getService(Class<T> service, JndiTemplate jndiTemplate) {
        LOG.debug("Returning " + getClassName(service) + " in container");

        SimpleRemoteStatelessSessionProxyFactoryBean ejb = new SimpleRemoteStatelessSessionProxyFactoryBean();
        ejb.setBusinessInterface(service);
        ejb.setJndiName(getClassName(service));
        ejb.setJndiTemplate(jndiTemplate);
        ejb.setResourceRef(false);

        try {
            ejb.afterPropertiesSet();
        } catch (NamingException e) {
            LOG.error(e);
        }

        return (T) ejb.getObject();
    }

    
    /**
     * Context factory for remote lookup, e.g. weblogic.jndi.WLInitialContextFactory
     * 
     * @return String context factory
     */
    public String getNationalContextFactory() {
        return national.contextFactory;
    }

    /**
     * Provider URL, e.g. t3://localhost:8012
     * 
     * @return String provider URL
     */
    public String getNationalProviderUrl() {
        return national.providerUrl;
    }

    /**
     * String host name, e.g. localhost
     * 
     * @return String host name
     */
    public String getNationalHost() {
        return national.host;
    }

    /**
     * Port the server is running on, e.g. 8012
     * 
     * @return int port number
     */
    public int getNationalPort() {
        return national.port;
    }

    /**
     * If set to true, testing will occur against undeployed code. Note that in this case, only one local instance is used --
     * both {@link #getLocalOneService(Class)} and {@link #getLocalTwoService(Class)} methods will return a service that uses
     * the local one database!
     * 
     * @return outOfContainer property
     */
    public boolean isOutOfContainer() {
        return outOfContainer;
    }

    /**
     * Set if the integration test case should test against deployed code, or not.
     * 
     * If set to true, testing will occur against undeployed code. Note that in this case, only one local instance is used --
     * both {@link #getLocalOneService(Class)} and {@link #getLocalTwoService(Class)} methods will return a service that uses
     * the local one database!
     * 
     * @param outOfContainer outOfContainer property
     */
    public void setOutOfContainer(boolean outOfContainer) {
        this.outOfContainer = outOfContainer;
    }

    /**
     * Set the required and uniqueness fields on a {@link ProductVo} that was created from an existing template.
     * 
     * @param product {@link ProductVo}
     */
    protected void makeUnique(ProductVo product) {
        for (ActiveIngredientVo ingredient : product.getActiveIngredients()) {
            ingredient.setStrength(RandomGenerator.nextNumeric(PPSConstants.I3));
        }

        product.setVaPrintName(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        product.setVaProductName(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        product.setAtcMnemonic(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        product.setId(null);
    }

    /**
     * Set the required and uniqueness fields on a {@link DosageFormVo} that was created from an existing template.
     * 
     * @param dosageForm {@link DosageFormVo}
     */
    protected void makeUnique(DosageFormVo dosageForm) {
        dosageForm.setDosageFormName(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        dosageForm.setId(null);
    }

    /**
     * Set the required and uniqueness fields on a {@link DispenseUnitVo} that was created from an existing template.
     * 
     * @param dispenseUnit {@link DispenseUnitVo}
     */
    protected void makeUnique(DispenseUnitVo dispenseUnit) {
        dispenseUnit.setValue(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        dispenseUnit.setId(null);
    }

    /**
     * Set the required and uniqueness fields on a {@link DrugUnitVo} that was created from an existing template.
     * 
     * @param drugUnit {@link DrugUnitVo}
     */
    protected void makeUnique(DrugUnitVo drugUnit) {
        drugUnit.setValue(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        drugUnit.setId(null);
    }

    /**
     * Set the required and uniqueness fields on a {@link OrderableItemVo} that was created from an existing template.
     * 
     * @param orderableItem {@link OrderableItemVo}
     */
    protected void makeUnique(OrderableItemVo orderableItem) {
        orderableItem.setVistaOiName(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        orderableItem.setId(null);
    }

    /**
     * Set the required and uniqueness fields on a {@link DoseUnitVo} that was created from an existing template.
     * 
     * @param doseUnit {@link DoseUnitVo}
     */
    protected void makeUnique(DoseUnitVo doseUnit) {
        doseUnit.setDoseUnitName(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        doseUnit.setId(null);
    }

    /**
     * The two NOIs should be the "same" as in they have no differences.
     * 
     * @param expected {@link OrderableItemVo}
     * @param actual {@link OrderableItemVo}
     */
    protected void assertSameNoi(OrderableItemVo expected, OrderableItemVo actual) {
        assertNotNull("Expected NOI cannot be null", expected);
        assertNotNull("Actual NOI cannot be null", actual);
        assertNull("NOI should not have parent", actual.getOrderableItemParent());
        assertTrue("NOI should have National type", actual.isNational());
        assertEquals("NOI New Item Request should be PENDING", RequestItemStatus.PENDING, actual.getRequestItemStatus());
        assertEquals("OI Name incorrect ", actual.getOiName(), expected.getOiName());

        Collection<Difference> differences = expected.diff(actual);
        LOG.info("Differneces:  " + differences);
        assertTrue("There should be no differences ", differences.isEmpty());
    }

    /**
     * The two LOIs should be the "same" as in they have no differences.
     * 
     * @param expected {@link OrderableItemVo}
     * @param actual {@link OrderableItemVo}
     */
    protected void assertSameLoi(OrderableItemVo expected, OrderableItemVo actual) {
        assertNotNull("Expected LOI cannot be null", expected);
        assertNotNull("Actual LOI cannot be null", actual);
        assertTrue("LOI should have Local type", actual.isLocal());
        assertEquals("LOI New Item Request should be APPROVED", RequestItemStatus.APPROVED,
                     actual.getRequestItemStatus());
        assertEquals("OI Name incorrect", actual.getOiName(), expected.getOiName());

        Collection<Difference> differences = expected.diff(actual);
        LOG.info("Differneces: " + differences);
        assertTrue("There should be no differences", differences.isEmpty());
    }

    /**
     * Holds properties for each host. Supports test cases to iterate over all locals in a for loop.
     */
    public class HostProperties {
    
        /**
         * contextFactory
         */
        private String contextFactory;
        
        /**
         * providerUrl
         */
        private String providerUrl;
        
        /**
         * host
         */
        private String host;
        
        /**
         * port
         */
        private int port;

        /**
         * Empty constructor
         */
        public HostProperties() {
            super();
        }
    }
}
