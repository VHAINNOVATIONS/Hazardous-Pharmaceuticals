/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.impl;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.external.common.callback.ManagedItemCapabilityCallback;
import gov.va.med.pharmacy.peps.external.common.callback.ProductDomainCapabilityCallback;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.DrugDataCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.DrugDataResponseDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.drug.data.NdcDataFieldTypeConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.drug.data.ProductDataFieldTypeConverter;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.request.DrugRequest;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DrugData;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ObjectFactory;


/**
 * Lookup the NDCs and Products for the given NDC numbers and Vuid's.
 */
public class DrugDataCapabilityImpl implements DrugDataCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DrugDataCapabilityImpl.class);
    private static final ObjectFactory FACTORY = new ObjectFactory();

    private ManagedItemCapabilityCallback managedItemCapability;
    private ProductDomainCapabilityCallback productDomainCapability;
    private EnvironmentUtility environmentUtility;

    /**
     * Handle the XML request
     * 
     * @param xmlRequest request XML from VistA
     * @return response XML
     */
    public String handleRequest(String xmlRequest) {
        LOG.debug("Request from VistA: " + xmlRequest);

        // unmarshall to DrugData request object
        gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.request.DrugData drugs =
            gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.DrugDataRequestDocument
                .instance().unmarshal(xmlRequest);

        // retrieve NDC/Product results in the form of DrugData response object
        DrugData results = processRequest(drugs);

        // marshall results to xml
        String xmlResponse = DrugDataResponseDocument.instance().marshal(results);

        LOG.debug("Response to VistA: " + xmlResponse);

        return xmlResponse;
    }

    /**
     * Lookup the NDCs and Products for the given NDC numbers and Vuid's.
     * 
     * @param drugs Collection of DrugDataVo
     * @return DrugData response containing drugs not found and drugs with does routes and types
     */
    public DrugData processRequest(gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.request.DrugData drugs) {
        DrugData results = new DrugData();

        DrugRequest request = drugs.getDrugRequest();

        // get NDCs by NDC numbers
        for (BigInteger ndcNumber : request.getNdc()) {
            SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.ADVANCED, environmentUtility
                .getEnvironment());
            searchCriteria.setSearchTerms(new ArrayList<SearchTermVo>());
            searchCriteria.getSearchTerms().add(
                new SearchTermVo(EntityType.NDC, FieldKey.NDC, ndcNumber.toString().replaceAll("-", "")));
            searchCriteria.setEntityType(EntityType.NDC);
            searchCriteria.setSortedFieldKey(FieldKey.NDC);
            searchCriteria.setAdvancedAndSearch(true);
            searchCriteria.setLocalUse(LocalUseSearchType.ALL_ITEMS);
            searchCriteria.setItemStatus(new ArrayList<ItemStatus>());
            searchCriteria.getItemStatus().add(ItemStatus.ACTIVE);
            searchCriteria.getItemStatus().add(ItemStatus.INACTIVE);
            searchCriteria.setRequestStatus(new ArrayList<RequestItemStatus>());
            searchCriteria.getRequestStatus().add(RequestItemStatus.APPROVED);
            searchCriteria.getRequestStatus().add(RequestItemStatus.REJECTED);
            searchCriteria.getRequestStatus().add(RequestItemStatus.PENDING);

            try {
                List<ManagedItemVo> searchResults = managedItemCapability.search(searchCriteria);

                if (searchResults.size() == 1) {
                    NdcVo ndc = (NdcVo) searchResults.get(0);
                    results.getDrugDataFields().add(NdcDataFieldTypeConverter.convertNdc(ndc));
                } else {
                    if (searchResults.size() > 1) {
                        LOG.error("The NDC Number returned more than one result");
                    }

                    if (!results.isSetDrugsNotFound()) {
                        results.setDrugsNotFound(FACTORY.createDrugsNotFoundType());
                    }

                    // add the number of the ndc not found
                    results.getDrugsNotFound().getNdc().add(ndcNumber);
                }
            } catch (ValidationException ve) {
                LOG.error(ve.getMessage());
            }
        }

        // get Products by Vuid's
        for (BigInteger vuid : request.getVuid()) {
            try {
                ProductVo product = productDomainCapability.retrieveByVuId(vuid.toString());
                results.getDrugDataFields().add(ProductDataFieldTypeConverter.convertProduct(product));
            } catch (ItemNotFoundException e) {
                if (!results.isSetDrugsNotFound()) {
                    results.setDrugsNotFound(FACTORY.createDrugsNotFoundType());
                }

                // add the vuid not found
                results.getDrugsNotFound().getVuid().add(vuid);
            }
        }

        return results;
    }

    /**
     * setManagedItemCapability
     * @param managedItemCapability managedItemCapability property
     */
    public void setManagedItemCapability(ManagedItemCapabilityCallback managedItemCapability) {
        this.managedItemCapability = managedItemCapability;
    }

    /**
     * setProductDomainCapability
     * @param productDomainCapability productDomainCapability property
     */
    public void setProductDomainCapability(ProductDomainCapabilityCallback productDomainCapability) {
        this.productDomainCapability = productDomainCapability;
    }

    /**
     * setEnvironmentUtility
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }
}
