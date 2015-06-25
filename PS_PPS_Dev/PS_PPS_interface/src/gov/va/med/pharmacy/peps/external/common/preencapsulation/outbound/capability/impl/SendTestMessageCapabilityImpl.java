/**
 * Source file created in 2005 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl;


import java.util.LinkedList;
import java.util.List;

import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.SendTestMessageCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.VistaLinkConnectionUtility;
import gov.va.med.vistalink.rpc.RpcResponse;


/**
 * Sends a test message over VistAlink..
 */
public class SendTestMessageCapabilityImpl implements SendTestMessageCapability {
    private static final String RPC_NAME = "XOBV TEST STRING";

    private VistaLinkConnectionUtility vistaLinkConnectionUtility;

    /**
     * Empty constructor
     */
    public SendTestMessageCapabilityImpl() {
        super();
    }

    /**
     * Runs the command
     * 
     * @see gov.va.med.pharmacy.common.command.Command#execute()
     */
    public void execute() {

    }

    /**
     * Send VistALink test message
     * 
     * @param testMessage String message
     * @return String returned by VistALink
     */
    public String sendTestMessage(String testMessage) {

        List<String> params = new LinkedList<String>();
        params.add(testMessage);

        RpcResponse response = vistaLinkConnectionUtility.sendRequest("XOBV VISTALINK TESTER", RPC_NAME, params, null);

        return response.getResults();
    }

    /**
     * description
     * @param connection connection property
     */
    public void setVistaLinkConnectionUtility(VistaLinkConnectionUtility connection) {
        this.vistaLinkConnectionUtility = connection;
    }
}
