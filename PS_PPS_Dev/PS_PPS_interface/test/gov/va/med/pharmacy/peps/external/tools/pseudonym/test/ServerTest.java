/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import gov.va.med.pharmacy.peps.external.tools.pseudonym.Pseudonym;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.io.MumpsStream;

import junit.framework.TestCase;


/**
 * ServerTest starts the PseodonyM server then sends requests and records the server's response. 
 * If the server response matches the expected string the test passes.  
 */
public class ServerTest extends TestCase {

    /** PORT */
    public static final int PORT = 9001;

    /** socketPort */
    protected int socketPort = PORT;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Pseudonym server;

    static {
        System.setErr(System.out);
    }

    /**
     * This method starts the PseodonyM server and establishes a socket connection.
     * @throws Exception If an exception occurred
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception {

        this.server = new Pseudonym(PORT);
        server.start();
        socket = new Socket(InetAddress.getLocalHost(), socketPort);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    /**
     * This method closes the socket connection and stops the PseodonyM server.
     * @throws Exception If an exception occurred
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    public void tearDown() throws Exception {
        socket.close();
        server.stop();
    }

    /**
     * Sends request to server and compares server response with expected response.
     * @throws IOException 
     */
    public void testFatkaatGetUserInfo() throws IOException {
        String result;
        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><VistaLink messageType=\"gov.va.med.foundations.rpc.re"
            + "sponse\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocatio"
            + "n=\"rpcResponse.xsd\"><Response type=\"array\" ><![CDATA[520631272MANAGER,SYSTEMSystemManagerMANAGERSYSTEM0"
            + "^^^5000^noparentassociatedwithinputstationnumber5003060810.1516291500^CAMPMASTER^500^1]]></Response>"
            + "</VistaLink>";

        //Setup user session by making divisionGetViaProxy call 
        executeCommand("07XOB RPC05000031.5000031.500001000026XUS DIVISION GET VIA PROXY00025 rUk%Qf,&ff,%t=*rw%l*<*m'000036"
            + "0000008appproxy0000350100013authenticated00001100006string00001100021-zVBC#C?B5;@z*%%2qVu1");
        result = executeCommand("07XOB RPC05000031.5000031.500001000025XUS FATKAAT GET USER INFO000191ULO\"HY AYY \"B$v$c200"
            + "00360000002av0000350100016notauthenticated00031+(@wf9fHw*pg(m%%3<@ZgNpyV<V<VN!00001200006string00001100009127"
            + ".0.0.100007string,00001200014FATKAAT Sample");
        assertVistALinkEquals(expected, result);

    }

    /**
     * Sends request to server and compares server response with expected response. 
     * @throws IOException If an exception occurred
     */
    public void testXusGetUserInfo() throws IOException {
        String result;
        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><VistaLink messageType=\"gov.va.med.foundations.rpc.re"
            + "sponse\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocatio"
            + "n=\"rpcResponse.xsd\"><Response type=\"array\" ><![CDATA[520631272MANAGER,SYSTEMSystemManager500^CAMPMASTER^5"
            + "00MEDICALADMINISTRATION9999]]></Response></VistaLink>";

        //Setup user session by making divisionGetViaProxy call
        executeCommand("07XOB RPC05000031.5000031.500001000026XUS DIVISION GET VIA PROXY00025 rUk%Qf,&ff,%t=*rw%l*<*m'000036"
            + "0000008appproxy0000350100013authenticated00001100006string00001100021-zVBC#C?B5;@z*%%2qVu1");
        result = executeCommand("07XOB RPC05000031.5000031.500001000017XUS GET USER INFO00019\";7&+\"ohaooh+UvSv%10000360000"
            + "002av0000350100013authenticated000010");
        assertVistALinkEquals(expected, result);

    }

    /**
     * Sends request to server and compares server response with expected response. 
     * @throws IOException If an exception occurred
     */
    public void testInitializeSocket() throws IOException {
        String command = "<VistaLink messageType=\"gov.va.med.foundations.vistalink.system.request\" mode=\"singleton\" vers"
            + "ion=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"vlSimpleR"
            + "equest.xsd\"><Request environment=\"J2EE\" type=\"initializeSocket\"/></VistaLink>";

        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><VistaLink messageType=\"gov.va.med.foundations.vistal"
            + "ink.system.response\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespace"
            + "SchemaLocation=\"vlSimpleResponse.xsd\"><Response type=\"initializeSocket\" status=\"success\" rate=\"604800"
            + "\" mJob=\"666\" reAuthSessionTimeout=\"3600\" /></VistaLink>";
        String result = executeCommand(command);

        assertVistALinkEquals(expected, result);
    }

    /**
     * Sends request to server and compares server response with expected response. 
     * @throws IOException If an exception occurred
     */
    public void testSetupAndIntroText() throws IOException {
        String command = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<VistaLink messageType=\"gov.va.med.foundations.securi"
            + "ty.request\" mode=\"singleton\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:"
            + "noNamespaceSchemaLocation=\"secSetupIntroRequest.xsd\"><SecurityInfo version=\"1.0\"/><Request type=\"AV.Setu"
            + "pAndIntroText\"><productionInfo clientIsProduction=\"false\" clientPrimaryStation=\"501\"/></Request>"
            + "</VistaLink>";

        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><VistaLink messageType=\"gov.va.med.foundations.securi"
            + "ty.response\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLo"
            + "cation=\"secSetupIntroResponse.xsd\"><SecurityInfo version=\"1.0\" />\n    <Response type=\"AV.SetupAndIntroT"
            + "ext\" status=\"success\">\n        <SetupInfo serverName='PseudonyM' volume='simulated' uci='VISTA' device="
            + "'/simulated/simulated' numberAttempts='5' dtime='180'/>\n        <IntroText><![CDATA[This is the intro.<BR>"
            + "]]></IntroText>\n    </Response>\n</VistaLink>";
        String result = executeCommand(command);

        assertVistALinkEquals(expected, result);
    }

    /**
     * Sends request to server and compares server response with expected response. 
     * @throws IOException If an exception occurred
     */
    public void testLogon() throws IOException {
        String command = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<VistaLink messageType=\"gov.va.med.foundations.securi"
            + "ty.request\" mode=\"singleton\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:"
            + "noNamespaceSchemaLocation=\"secLogonRequest.xsd\"><SecurityInfo version=\"1.0\"/><Request type=\"AV.Logon\">"
            + "<avCodes><![CDATA[*(bPO*P[Yozl@2F2%Z(2Oz# ]]></avCodes></Request></VistaLink>";

        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><VistaLink messageType=\"gov.va.med.foundations.securi"
            + "ty.response\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLo"
            + "cation=\"secLogonResponse.xsd\"><SecurityInfo version=\"1.0\" />\n    <Response type=\"AV.Logon\" "
            + "status=\"success\">\n       <PostSignInText></PostSignInText>\n    </Response></VistaLink>";
        String result = executeCommand(command);

        assertVistALinkEquals(expected, result);
    }

    /**
     * Sends request to server and compares server response with expected response. 
     * @throws IOException If an exception occurred
     */
    public void testIntroMsg() throws IOException {
        String command = "07XOB RPC05000031.5000031.500001000013XUS INTRO MSG00025(DL<i_IeFIIein,vD=i%vov'#0000360000008appp"
            + "roxy0000350100016notauthenticated00013FATKAAT,PROXY000010";

        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><VistaLink messageType=\"gov.va.med.foundations.rpc.re"
            + "sponse\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocatio"
            + "n=\"rpcResponse.xsd\"><Response type=\"array\" ><![CDATA[You are connected to PseudonyM, a simulated VistA "
            + "server.Pseudonym is not a real VistA server.Pseudonym is only a simulation of responses returned from VistA."
            + "]]></Response></VistaLink>";
        String result = executeCommand(command);

        assertVistALinkEquals(expected, result);
    }

    /**
     * Sends request to server and compares server response with expected response. 
     * @throws IOException If an exception occurred
     */
    public void testFatkaatServerInfo() throws IOException {
        String command = "07XOB RPC05000031.5000031.500001000022XUS FATKAAT SERVERINFO00025,763Hs?$(??$H\"C`7>Hw`u`!10000360"
            + "000008appproxy0000350100013authenticated000010";

        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><VistaLink messageType=\"gov.va.med.foundations.rpc.re"
            + "sponse\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocatio"
            + "n=\"rpcResponse.xsd\"><Response type=\"array\" ><![CDATA[SIMULATED SIMULATED VISTA //./nul:10212 ]]>"
            + "</Response></VistaLink>";
        String result = executeCommand(command);

        assertVistALinkEquals(expected, result);
    }

    /**
     * Sends request to server and compares server response with expected response. 
     * @throws IOException If an exception occurred
     */
    public void testInvalidDivisionGetViaProxy() throws IOException {

        //testing for invalid access code and password
        String command = "07XOB RPC05000031.5000031.500001000026XUS DIVISION GET VIA PROXY00025 rUk%Qf,&ff,%t=*rw%l*<*m'0000"
            + "360000008appproxy0000350100013authenticated00001100006string00001100021-zVBC#C?d5;@z*%%4qVu1";

        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><VistaLink messageType=\"gov.va.med.foundations.rpc.re"
            + "sponse\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocatio"
            + "n=\"rpcResponse.xsd\"><Response type=\"array\" ><![CDATA[0 1 0 Not a valid ACCESS CODE/VERIFY CODE pair. 0 0 "
            + "1 501^NEW MEXICO HCS^501^1 ]]></Response></VistaLink>";
        String result = executeCommand(command);

        assertVistALinkEquals(expected, result);
    }

    /**
     * Sends request to server and compares server response with expected response. 
     * @throws IOException If an exception occurred
     */
    public void testDivisionGetViaProxy() throws IOException {

        String command = "07XOB RPC05000031.5000031.500001000026XUS DIVISION GET VIA PROXY00025 rUk%Qf,&ff,%t=*rw%l*<*m'0000"
            + "360000008appproxy0000350100013authenticated00001100006string00001100021-zVBC#C?B5;@z*%%2qVu1";
        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><VistaLink messageType=\"gov.va.med.foundations.rpc.re"
            + "sponse\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocatio"
            + "n=\"rpcResponse.xsd\"><Response type=\"array\" ><![CDATA[1000000005600001500^CAMPMASTER^500^1Wedon'tknowwheny"
            + "oulastsignedon]]></Response></VistaLink>";

        String result = executeCommand(command);
        assertVistALinkEquals(expected, result);
    }

    /**
     * Sends request to server and compares server response with expected response. 
     * @throws IOException If an exception occurred
     */
    public void testPersel() throws IOException {
        String command = "07XOB RPC05000031.5000031.500001000011XHDX PERSEL00015.d29dJj9/c!Yax0000023000003duz0000350100013"
            + "authenticated000010";

        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><VistaLink messageType=\"gov.va.med.foundations.rpc."
            + "response\" version=\"1.5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLoca"
            + "tion=\"rpcResponse.xsd\"><Response type=\"array\" ><![CDATA[localpresentation-1"
            + "localpresentation-2"
            + "nationalpresentation\n]]></Response></VistaLink>";

        String result = executeCommand(command);

        assertVistALinkEquals(expected, result);
    }

    /**
     * Executes an request on the server
     * @param command the command to be executed
     * @return a response from the server
     * @throws IOException If an IO Exception occurred while attempting to read from or write to the mumps stream
     */
    protected String executeCommand(String command) throws IOException {

        MumpsStream.write(out, command);

        return MumpsStream.read(in);

    }

    /**
     * Compares the server response with an expected string
     * @param expected a string containing the expected response
     * @param actual a string containing the server response
     */
    public void assertVistALinkEquals(String expected, String actual) {
        String strippedExpected = expected.replaceAll("\\s", "");
        String strippedActual = actual.replaceAll("\\s", "");

        assertEquals("The expected and actual did not match", strippedExpected, strippedActual);
    }

}
