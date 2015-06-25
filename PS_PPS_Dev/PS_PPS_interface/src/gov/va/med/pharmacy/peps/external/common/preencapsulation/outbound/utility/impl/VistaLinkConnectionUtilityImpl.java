/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.impl;


import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.ResourceException;

import gov.va.med.exception.FoundationsException;
import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.utility.ConfigFileUtility;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl.VistaFileSynchCapabilityImpl;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.VistaLinkConnectionUtility;
import gov.va.med.vistalink.adapter.cci.VistaLinkAppProxyConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionFactory;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkVpidConnectionSpec;
import gov.va.med.vistalink.institution.InstitutionMapNotInitializedException;
import gov.va.med.vistalink.institution.InstitutionMappingDelegate;
import gov.va.med.vistalink.institution.InstitutionMappingNotFoundException;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;
import gov.va.med.vistalink.rpc.RpcResponse;


/**
 * This class provides VistA connectivity. It uses VistALink to execute RPC's on the remote VistA and retrieve the results.
 */
public class VistaLinkConnectionUtilityImpl implements VistaLinkConnectionUtility {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(VistaFileSynchCapabilityImpl.class);

    private static final String DUZ_CONNECTION_SPEC = "DUZ";
    private static final String VPID_CONNECTION_SPEC = "VPID";
    private static final String APP_PROXY_CONNECTION_SPEC = "APP PROXY";

//    private static final String DIVISION_PROPERTY = "vistalink.default.division";
    //  private static final String USER_ID_PROPERTY = "vistalink.default.user.id";
    //   private static final String CONNECTION_SPEC_NAME_PROPERTY = "vistalink.connection.spec.name";
    //   private static final String RPC_TIMEOUT_PROPERTY = "vistalink.rpc.timeout";

//    private static final String DEFAULT_DIVISION = PropertyUtility.getProperty(VistaLinkConnectionUtilityImpl.class,
    //      DIVISION_PROPERTY);
    //private static final String DEFAULT_USER_ID = PropertyUtility.getProperty(VistaLinkConnectionUtilityImpl.class,
    //  USER_ID_PROPERTY);
    // private static final String CONNECTION_SPEC_NAME = PropertyUtility.getProperty(VistaLinkConnectionUtilityImpl.class,
    //   CONNECTION_SPEC_NAME_PROPERTY);
    //private static final int RPC_TIMEOUT = Integer.parseInt(PropertyUtility.getProperty(
    //  VistaLinkConnectionUtilityImpl.class, RPC_TIMEOUT_PROPERTY));

    /**
     * Executes an RPC call on the VistA.
     * 
     * @param rpcContext The context this RPC will be called in.
     * @param rpcName The name of the RPC.
     * @param rpcParams Parameter(s) that will be passed to the VistA.
     * @param user The user making the RPC call. If the user is null, default user will be used.
     * @return The RpcResponse.
     */
    public RpcResponse sendRequest(String rpcContext, String rpcName, List rpcParams, UserVo user) {
        ConfigFileUtility configFile = new ConfigFileUtility();

        LOG.debug("VistA request [" + rpcContext + ", " + rpcName + "] with parameters: " + rpcParams);

        VistaLinkConnection connection = getConnection(user);

        try {
            RpcRequest rpcRequest = RpcRequestFactory.getRpcRequest();

            rpcRequest.setRpcContext(rpcContext);
            rpcRequest.setRpcName(rpcName);
            rpcRequest.setRpcClientTimeOut(configFile.getRpcTimeout());
            rpcRequest.setUseProprietaryMessageFormat(true);
            rpcRequest.setParams(rpcParams);

            RpcResponse response = connection.executeRPC(rpcRequest);

            LOG.debug("VistA response [" + rpcContext + ", " + rpcName + "]: " + response.getResults());

            return response;
        } catch (FoundationsException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_VISTA_FAILURE, new Object[] { rpcName,
                                                                                                      e.getMessage() });
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (ResourceException e) {
                    LOG.error("Error closing VistALink connection.", e);
                }
            }
        }
    }

    /**
     * Retrieves VistALink connection.
     * 
     * @param user UserVo requesting a connection
     * @return A VistaLinkConnection.
     */
    private VistaLinkConnection getConnection(UserVo user) {

        ConfigFileUtility configFile = new ConfigFileUtility();
        String division;
        String userId;

        if ((user != null) && (user.getId() != -1l)) {
            division = user.getStationNumber();
            userId = String.valueOf(user.getId());
        } else {
            division = String.valueOf(configFile.getDivision());
            userId = String.valueOf(configFile.getNdfProxyUserDUZ());
        }

        LOG.debug("Connecting to VistAlink as: " + userId + ", " + division);

        Context ic = null;

        try {
            VistaLinkConnectionSpec connectionSpec = null;

            if (DUZ_CONNECTION_SPEC.equals(configFile.getConnectionSpecName())) {
                connectionSpec = new VistaLinkDuzConnectionSpec(division, userId);
            } else if (VPID_CONNECTION_SPEC.equals(configFile.getConnectionSpecName())) {
                connectionSpec = new VistaLinkVpidConnectionSpec(division, userId);
            } else if (APP_PROXY_CONNECTION_SPEC.equals(configFile.getConnectionSpecName())) {
                connectionSpec = new VistaLinkAppProxyConnectionSpec(division, userId);
            }

            LOG.debug("Connection Spec: " + configFile.getConnectionSpecName());

            ic = new InitialContext();
            String jndiName = InstitutionMappingDelegate.getJndiConnectorNameForInstitution(division);
            VistaLinkConnectionFactory cf = (VistaLinkConnectionFactory) ic.lookup(jndiName);
            VistaLinkConnection connection = (VistaLinkConnection) cf.getConnection(connectionSpec);

            return connection;
        } catch (InstitutionMapNotInitializedException e) {
            LOG.error("VistALink InsitutionMapNotInitialized.", e);
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        } catch (InstitutionMappingNotFoundException e) {
            LOG.error("VistALink unable to find institution mapping for division " + division, e);
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        } catch (NamingException e) {
            LOG.error("VistALink unable to lookup VistALink connection factory.", e);
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        } catch (ResourceException e) {
            LOG.error("VistALink resource exception.", e);
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        } catch (Exception e) {
            LOG.error("VistALink error while retrieving connection.", e);
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        } finally {
            try {
                ic.close();
            } catch (NamingException e) {
                LOG.error("Error closing intial context.");
            }
        }
    }
}
