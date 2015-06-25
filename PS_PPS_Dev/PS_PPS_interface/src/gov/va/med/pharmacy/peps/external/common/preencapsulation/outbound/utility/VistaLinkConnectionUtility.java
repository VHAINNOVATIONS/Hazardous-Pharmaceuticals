/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.vistalink.rpc.RpcResponse;


/**
 * Class used to send messages over vistalink
 */
public interface VistaLinkConnectionUtility {
  
    /** 
     * This is the Connector that uses the IEN of Vista File #200 
     */ 
    String DUZ_CONNECTION_SPEC = "DUZ";
    
    /**
     * This is the connector that uses the VA Personal Identifier connection string
     */
    String VPID_CONNECTION_SPEC = "VPID";
    
    /**
     * This is the connector that uses the Application Proxy connection string
     */
    String APP_PROXY_CONNECTION_SPEC = "APP PROXY";
    
    /**
     * Uses the connection to send a request to vista
     * @param rpcContext The VistALink RPC context
     * @param rpcName The VistALink RPC name
     * @param rpcParams The VistALink RPC parameters
     * @param user The user that is making this call
     * @return the vista response object
     * 
     * @see gov.va.med.pharmacy.external.vistalink.VistALink#sendRequest(gov.va.med.vistalink.rpc.RpcRequest)
     */
    RpcResponse sendRequest(String rpcContext, String rpcName, List rpcParams, UserVo user);
}
