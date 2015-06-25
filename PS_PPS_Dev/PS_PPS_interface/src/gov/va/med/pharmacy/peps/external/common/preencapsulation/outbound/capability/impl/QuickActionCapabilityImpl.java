/**
 * Source file created in 2006 by Southwest Research Institute
 * 
 * This is a local only class and will be needed for PPS-L so for now just comment it out.
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl;


import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.callback.InterfaceCounterDomainCapabilityCallback;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.QuickActionCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.VistaLinkConnectionUtility;


/**
 * Sends updates to VistaLink
 */
public class QuickActionCapabilityImpl implements QuickActionCapability {
 
    //  private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuickActionCapabilityImpl.class);

//    private String quickActionRpcContext;
//    private String quickActionRpcName;
//    private VistaLinkConnectionUtility vistaLinkConnectionUtility;
//    private InterfaceCounterDomainCapabilityCallback interfaceCounter;

    /**
     * The constructor sets some base properties
     */
    public QuickActionCapabilityImpl() {

        //        try {
//            Properties properties = PropertyUtility.loadProperties(QuickActionCapabilityImpl.class);
//            quickActionRpcContext = properties.getProperty("quickActionRpcContext");
//            quickActionRpcName = properties.getProperty("quickActionRpc");
//        } catch (IOException e) {
//            LOG.error("unable to load properties file", e);
//            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
//        }
    }

    /**
     * This method is used to set the VistaLink connection
     * 
     * @param connection The Vista Link Connection Utility
     */
    public void setVistaLinkConnectionUtility(VistaLinkConnectionUtility connection) {

        //       this.vistaLinkConnectionUtility = connection;
    }

    /**
     * This method is used to tell Vista send the Drug file to the external interface
     * 
     * @param user The user who initiated the request
     * 
     * @see gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.
     *        QuickActionCapability#sendDrugFileToExternalInterface(gov.va.med.pharmacy.peps.common.vo.UserVo)
     */
    public void sendDrugFileToExternalInterface(UserVo user) {

        //        DrugFileToExternalInterface drugFileToExternalInterface = DrugFileToExternalInterfaceConverter
//            .toDrugFileToExternalInterface();
//
//        drugFileToExternalInterface.setPepsIdNumber(BigInteger.valueOf(interfaceCounter.incrementCounter(
//            InterfaceCounterDomainCapabilityCallback.INTERFACE_VISTA_SYNCHRONIZATION,
//            InterfaceCounterDomainCapabilityCallback.COUNTER_MESSAGE_ID, user)));
//
//        String xml = DrugFileToExternalInterfaceDocument.instance().marshal(drugFileToExternalInterface);
//        List<String> params = new LinkedList<String>();
//        params.add(xml);

        // Send Item is removed for PRE 1.0 National since it is local functionality
//        sendItem(params, user);
    }

    /**
     * Callback.
     * 
     * @param counter counter
     */
    public void setInterfaceCounterDomainCapability(InterfaceCounterDomainCapabilityCallback counter) {

        //   this.interfaceCounter = counter;
    }

//    /**
//     * This method is used to send a single quick action update.
//     * 
//     * @param params The list of parameters
//     * @param user The user
//     */
//    private void sendItem(List params, UserVo user) {
//        RpcResponse rpcResponse = vistaLinkConnectionUtility.sendRequest(quickActionRpcContext, quickActionRpcName, params,
//            user);
// 
//        Response response = ResponseDocument.instance().unmarshal(rpcResponse.getResults());
// 
//        if (ResponseStatusType.FAILURE.equals(response.getResponse().getStatus())) {
//            throw new InterfaceException(InterfaceException.INTERFACE_VISTA_FAILURE, quickActionRpcName, response
//                .getResponse().getValue());
//        }
//        else if (ResponseStatusType.QUEUED.equals(response.getResponse().getStatus())) { // shouldn't happen
//            LOG.debug("VistA Quick Action queued: " + response.getResponse().getValue());
//        }
//        else if (ResponseStatusType.SUCCESS.equals(response.getResponse().getStatus())) {
//            LOG.debug("VistA Quick Action successful: " + response.getResponse().getValue());
//        }
//    }

}
