/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl;


import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaAuthoritativeDomainCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.LocalVistADomainRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.LocalVistADomainResponseDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.VistaLinkConnectionUtility;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.VistaDomainName;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.facility.vista.request.VistaDomainsRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.facility.vista.request.VistaDomainsRequest.Domain;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.facility.vista.response.VistaDomainsResponse;
import gov.va.med.vistalink.rpc.RpcResponse;


/**
 * Sends updates to VistaLink
 */
public class VistaAuthoritativeDomainCapabilityImpl implements VistaAuthoritativeDomainCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(VistaAuthoritativeDomainCapabilityImpl.class);

    private String sendItemRpcContext;
    private String requestDomainRpc;
    private VistaLinkConnectionUtility vistaLinkConnectionUtility;

    /**
     * Constructor
     */
    public VistaAuthoritativeDomainCapabilityImpl() {
        try {
            Properties properties = PropertyUtility.loadProperties(VistaAuthoritativeDomainCapabilityImpl.class);
            sendItemRpcContext = properties.getProperty("sendItemRpcContext");
            requestDomainRpc = properties.getProperty("requestDomainsRpc");
        } catch (IOException e) {
            LOG.error("unable to load the properties file", e);
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        }
    }

    /**
     * Retrieve one or more domain value(s) from VistA.
     * 
     * @param domainNames list of domains to retrieve
     * @param user User making this call
     * @return map of domain values
     */
    public Map<VistaDomainName, List<?>> retrieveVistADomains(VistaDomainName[] domainNames, UserVo user) {
        gov.va.med.pharmacy.peps.external.common.vo.outbound.facility.vista.request.ObjectFactory factory =
            new gov.va.med.pharmacy.peps.external.common.vo.outbound.facility.vista.request.ObjectFactory();

        // request
        VistaDomainsRequest request = factory.createVistaDomainsRequest();

        for (VistaDomainName domain : domainNames) {
            Domain domainRequest = factory.createVistaDomainsRequestDomain();
            domainRequest.setName(domain);

            request.getDomain().add(domainRequest);
        }

        // VistA RPC
        String xml = LocalVistADomainRequestDocument.instance().marshal(request);
        List<String> params = new LinkedList<String>();
        params.add(xml);
        RpcResponse rpcResponse = vistaLinkConnectionUtility.sendRequest(sendItemRpcContext, requestDomainRpc, params, user);

        // response
        VistaDomainsResponse response = LocalVistADomainResponseDocument.instance().unmarshal(rpcResponse.getResults());
        Map<VistaDomainName, List<?>> domainsMap = new LinkedHashMap<VistaDomainName, List<?>>();

        for (Object domain : response.getStringDomainOrBooleanDomainOrIntegerDomain()) {
            if (domain instanceof VistaDomainsResponse.StringDomain) {
                VistaDomainsResponse.StringDomain stringDomain = (VistaDomainsResponse.StringDomain) domain;

                domainsMap.put(stringDomain.getName(), Arrays.asList(new String[] { stringDomain.getValue() }));
            } else if (domain instanceof VistaDomainsResponse.BooleanDomain) {
                VistaDomainsResponse.BooleanDomain booleanDomain = (VistaDomainsResponse.BooleanDomain) domain;

                domainsMap.put(booleanDomain.getName(), Arrays.asList(new Boolean[] { booleanDomain.isValue() }));
            } else if (domain instanceof VistaDomainsResponse.IntegerDomain) {
                VistaDomainsResponse.IntegerDomain integerDomain = (VistaDomainsResponse.IntegerDomain) domain;

                domainsMap.put(integerDomain.getName(), Arrays.asList(new Integer[] { integerDomain.getValue().intValue() }));
            } else if (domain instanceof VistaDomainsResponse.ListDomain) {
                VistaDomainsResponse.ListDomain listDomain = (VistaDomainsResponse.ListDomain) domain;

                domainsMap.put(listDomain.getName(), listDomain.getStringItemOrBooleanItemOrIntegerItem());
            }
        }

        return domainsMap;
    }

    /**
     * Sets the VistaLinkConnectionUtility.
     * 
     * @param connection Class that will be used to communicate with VistA.
     */
    public void setVistaLinkConnectionUtility(VistaLinkConnectionUtility connection) {
        this.vistaLinkConnectionUtility = connection;
    }
}
