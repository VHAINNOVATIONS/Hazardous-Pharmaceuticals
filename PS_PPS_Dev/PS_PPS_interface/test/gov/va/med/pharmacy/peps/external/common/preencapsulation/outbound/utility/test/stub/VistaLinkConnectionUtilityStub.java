/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.test.stub;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.VistaLinkConnectionUtility;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.Command;
import gov.va.med.vistalink.adapter.record.VistaLinkResponseVO;
import gov.va.med.vistalink.adapter.spi.EMAdapterEnvironment;
import gov.va.med.vistalink.adapter.spi.EMReAuthState;
import gov.va.med.vistalink.adapter.spi.VistaLinkJ2SEConnSpec;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;
import gov.va.med.vistalink.rpc.RpcResponse;
import gov.va.med.vistalink.rpc.RpcResponseFactory;


/**
 * Stub implementation of {@link VistaLinkConnectionUtility} that calls directly into PseudonyM instead of calling through
 * VistaLink.
 */
public class VistaLinkConnectionUtilityStub implements VistaLinkConnectionUtility {

    /**
     * Empty constructor
     */
    public VistaLinkConnectionUtilityStub() {
        super();
    }

    /**
     * Stub implementation of {@link VistaLinkConnectionUtility} that calls directly into PseudonyM instead of calling
     * through VistaLink.
     * 
     * @param rpcContext The VistALink RPC context
     * @param rpcName The VistALink RPC name
     * @param rpcParams The VistALink RPC parameters
     * @param user The user that is making this call
     * @return the vista response object
     * 
     * @see gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.VistaLinkConnectionUtility#sendRequest
     *      (gov.va.med.vistalink.rpc.RpcRequest)
     */
    @SuppressWarnings("rawtypes")
    public RpcResponse sendRequest(String rpcContext, String rpcName, List rpcParams, UserVo user) {
        RpcResponseFactory factory = new RpcResponseFactory();

        VistaLinkResponseVO responseVo = null;

        try {
            RpcRequest request = RpcRequestFactory.getRpcRequest();
            request.setRpcContext(rpcContext);
            request.setRpcName(rpcName);
            request.setParams(rpcParams);

            request.setReAuthenticateInfo(new VistaLinkJ2SEConnSpec(), EMReAuthState.AUTHENTICATED,
                EMAdapterEnvironment.J2SE);

            String response = new Command().getResponse(request.getRequestString());

            responseVo = factory.handleResponse(response, request);

            return (RpcResponse) responseVo;
        } catch (Exception e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        }
    }
}
