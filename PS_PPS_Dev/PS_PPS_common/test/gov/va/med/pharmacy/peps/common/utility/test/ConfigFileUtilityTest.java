/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.ConfigFileUtility;

import junit.framework.TestCase;


/**
 * ConfigFileUtilityTest's brief summary
 * 
 * Details of ConfigFileUtilityTest's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class ConfigFileUtilityTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(ConfigFileUtilityTest.class);

    private ConfigFileUtility cfu = new ConfigFileUtility();

    /**
     * setUp
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        LOG.debug("-------------------- " + getName() + " -------------------");

    }

    /**
     * testPort
     *
     */
    public void testPort() {

        assertEquals("Config port specification", ConfigFileUtility.DEFAULT_PORT, cfu.getPort());
    }

    /**
     * testTimeout
     *
     */
    public void testTimeout() {

        assertEquals("Config timeout specification", ConfigFileUtility.DEFAULT_TIMEOUT, cfu.getTimeout());
    }

    /**
     * testDivision
     *
     */
    public void testDivision() {

        assertEquals("Config division specification", ConfigFileUtility.DEFAULT_DIVISION, cfu.getDivision());
    }

    /**
     * testProxy
     *
     */
    public void testProxy() {

        assertEquals("Config NDFProxyUserIEN specification", ConfigFileUtility.DEFAULT_PROXY, cfu.getNdfProxyUserDUZ());
    }

    /**
     * testConnSpec
     *
     */
    public void testConnSpec() {

        assertEquals("Config connection spec name specification", ConfigFileUtility.DEFAULT_CONNSPEC,
            cfu.getConnectionSpecName());
    }

    /**
     * testRpcTimeout
     *
     */
    public void testRpcTimeout() {

        assertEquals("Config RPC Timeout specification", ConfigFileUtility.DEFAULT_RPCTIMEOUT, cfu.getRpcTimeout());
    }

    /**
     * testSwri
     *
     */
    public void testSwri() {

        assertTrue("Config swri specification", cfu.isSwri());
    }

    /**
     * testFdbLocation
     *
     */
    public void testFdbLocation() {

        assertEquals("Config FDB Image location specification", ConfigFileUtility.DEFAULT_FDB_IMAGE_PATH,
            cfu.getFdbImageLocation());
    }

    /**
     * testShowAll
     *
     */
    public void testShowAll() {

        assertTrue("Display everything.", Boolean.TRUE);

        String eol = "\n";

        StringBuilder sb = new StringBuilder(eol);
        sb.append("port: ....... ").append(cfu.getPort()).append(eol);
        sb.append("timeout: .... ").append(cfu.getTimeout()).append(eol);
        sb.append("division: ... ").append(cfu.getDivision()).append(eol);
        sb.append("proxy: ...... ").append(cfu.getNdfProxyUserDUZ()).append(eol);
        sb.append("conn spec: .. ").append(cfu.getConnectionSpecName()).append(eol);
        sb.append("rpc timeout:  ").append(cfu.getRpcTimeout()).append(eol);
        sb.append("swri: ....... ").append(cfu.isSwri()).append(eol);
        sb.append("fdb location: ").append(cfu.getFdbImageLocation()).append(eol);
        LOG.debug(sb);
    }
}
