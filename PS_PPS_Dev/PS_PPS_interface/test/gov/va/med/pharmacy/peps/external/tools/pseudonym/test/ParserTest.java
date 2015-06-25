/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.test;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Parser;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request;

import junit.framework.TestCase;


/**
 * Junit class used to test the parser
 */
public class ParserTest extends TestCase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
        ParserTest.class);
    
    private Parser parser;
   
    static {
        System.setErr(System.out);
    }

    /**
     * called before each test is run to setup the test case to run
     * @throws Exception exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception {
        this.parser = new Parser();
    }

    /**
     * Tests the parsing of the division get via proxy call
     */
    public void testDivisionGetViaProxy() {
        parser.setText("07XOB RPC05000031.5000031.500001000026XUS DIVISION GET VIA PROXY00025)Xwz:}QtOQQt:q%iXf:giSi5/"
            + "0000360000008appproxy0000350100013authenticated00001100006string00001100021(mP7>$>j7#dQmU**CkP(#");
        Request expected = new Request();
        expected.setVlv("1.5");
        expected.setRpc("XUS DIVISION GET VIA PROXY");
        expected.setRcx("XUS FATKAAT PROXY LOGON");
        expected.setRto("600");
        expected.setSec("appproxy");
        expected.setDiv("501");
        expected.setRas("authenticated");
        expected.getParameters().add(new Request.StringParameter("(mP7>$>j7#dQmU**CkP(#"));

        Request request = parser.parse();

        assertVistALinkStrings(expected, request);
    }

    /**
     * tests the parsing of the xus key check call
     */
    public void testKeyCheck() {
        parser
            .setText("07XOB RPC05000031.5000031.500001000013XUS KEY CHECK00019!GJqC>K3FKK3Cc`i`$*0000360000002av0000350100"
                + "013authenticated00001100005array00001100001200001100016XUFATKAAT_SAMPLE00001200025AUTHENTICATED_KAAJE"
                + "E_USER");
        Request expected = new Request();
        expected.setVlv("1.5");
        expected.setRpc("XUS KEY CHECK");
        expected.setRcx("XUS FATKAAT LOGON");
        expected.setRto("600");
        expected.setSec("av");
        expected.setDiv("501");
        expected.setRas("authenticated");
        List list = new ArrayList();
        list.add("XUFATKAAT_SAMPLE");
        list.add("AUTHENTICATED_KAAJEE_USER");
        expected.getParameters().add(new Request.ArrayParameter(list));

        Request request = parser.parse();

        assertVistALinkStrings(expected, request);
    }

    /**
     * tests the parsing of the xhd put parameter calls
     */
    public void testPutParameter() {
        parser.setText("07XOB RPC05000031.5000031.500001000017XHD PUT PARAMETER00015\"a!Ia#)I8.[>ml2000023000003duz00003501"
            + "00013authenticated00001200006string00001100054HealtheVet Desktop UI Settings^520631272;VA(200,^365^100"
            + "005array000012000021800001100017<DesktopSettings>00001200017 <WindowSettings>00001300014  <WindowSize>"
            + "00001400010   <width>00001500008    103200001600011   </width>00001700011   <height>00001800007    746"
            + "00001900012   </height>000021000006   <x>000021100007    170000021200007   </x>000021300006   <y>00002"
            + "1400006    85000021500007   </y>000021600015  </WindowSize>000021700018 </WindowSettings>000021800018<"
            + "/DesktopSettings>");
        Request expected = new Request();
        expected.setVlv("1.5");
        expected.setRpc("XHD PUT PARAMETER");
        expected.setRcx("XHDXC DESKTOP");
        expected.setRto("30");
        expected.setSec("duz");
        expected.setDiv("501");
        expected.setRas("authenticated");
        expected.getParameters().add(new Request.StringParameter("HealtheVet Desktop UI Settings^520631272;VA(200,^365^1"));

        List list = new ArrayList();
        list.add("<DesktopSettings>");
        list.add(" <WindowSettings>");
        list.add("  <WindowSize>");
        list.add("   <width>");
        list.add("    1032");
        list.add("   </width>");
        list.add("   <height>");
        list.add("    746");
        list.add("   </height>");
        list.add("   <x>");
        list.add("    170");
        list.add("   </x>");
        list.add("   <y>");
        list.add("    85");
        list.add("   </y>");
        list.add("  </WindowSize>");
        list.add(" </WindowSettings>");
        list.add("</DesktopSettings>");
        expected.getParameters().add(new Request.ArrayParameter(list));
        Request request = parser.parse();

        assertVistALinkStrings(expected, request);
    }

    /**
     * tests the parsing of xus get user info calls
     */
    public void testGetUserInfo() {
        parser.setText("07XOB RPC05000031.5000031.500001000017XUS GET USER INFO00019%Dn\"awhIrhhIai[*[V#0000360000002av0000"
            + "350100013authenticated000010");
        Request expected = new Request();
        expected.setVlv("1.5");
        expected.setRpc("XUS GET USER INFO");
        expected.setRcx("XUS FATKAAT LOGON");
        expected.setRto("600");
        expected.setSec("av");
        expected.setDiv("501");
        expected.setRas("authenticated");

        Request request = parser.parse();

        assertVistALinkStrings(expected, request);
    }

    /**
     * tests the parsing of xhdx versrv calls
     */
    public void testVersrv() {
        parser
            .setText("07XOB RPC05000031.5000031.500001000011XHDX VERSRV000151t[0td|0mp$koK+000023000003duz0000350100016not"
                + "authenticated0000952063127200001100005array00001100001100001100013XHDXC DESKTOP");
        Request expected = new Request();
        expected.setVlv("1.5");
        expected.setRpc("XHDX VERSRV");
        expected.setRcx("XHDXC DESKTOP");
        expected.setRto("30");
        expected.setSec("duz");
        expected.setSecParam("520631272");
        expected.setDiv("501");
        expected.setRas("notauthenticated");
        List list = new ArrayList();
        list.add("XHDXC DESKTOP");
        expected.getParameters().add(new Request.ArrayParameter(list));

        Request request = parser.parse();

        LOG.debug(request);
        assertVistALinkStrings(expected, request);
    }

    /**
     * tests the parsing of xus fatkaat get user info calls
     */
    public void testFatkaatGetUserInfo() {
        parser
            .setText("07XOB RPC05000031.5000031.500001000025XUS FATKAAT GET USER INFO00019-);Y6?bwgbbw6/2}2Z00000360000002"
                + "av0000350100016notauthenticated00031&#zap;pMac@d#-%%3!z\\dg@OC!C!Cg000001200006string00001100009127.0.0"
                + ".100007string,00001200014FATKAAT Sample");
        Request expected = new Request();
        expected.setVlv("1.5");
        expected.setRpc("XUS FATKAAT GET USER INFO");
        expected.setRcx("XUS FATKAAT LOGON");
        expected.setRto("600");
        expected.setSec("av");
        expected.setSecParam("&#zap;pMac@d#-%%3!z\\dg@OC!C!Cg0");
        expected.setDiv("501");
        expected.setRas("notauthenticated");
        expected.getParameters().add(new Request.StringParameter("127.0.0.1"));
        expected.getParameters().add(new Request.StringParameter("FATKAAT Sample"));

        Request request = parser.parse();

        LOG.debug(request);
        assertVistALinkStrings(expected, request);
    }

    /**
     * Used to determine if two request objects are equal
     * @param expected the expected request
     * @param actual the actual request
     */
    private void assertVistALinkStrings(Request expected, Request actual) {
        String ex = expected.toString().replaceAll("@[a-f0-9]+", ""); //removes object addresses
        String ac = actual.toString().replaceAll("@[a-f0-9]+", "");

        assertEquals("The objects did not match", ex, ac);
    }
}
