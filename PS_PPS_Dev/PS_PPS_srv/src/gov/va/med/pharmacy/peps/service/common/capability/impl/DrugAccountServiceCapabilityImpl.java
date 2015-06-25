/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.domain.common.capability.IntendedUseDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.DrugAccountabilityCapability;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.request.DrugAccountabilityRequest;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.request.RequestAdd;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.request.RequestUpdate;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.response.DrugAccountabilityResponse;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.response.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.response.ResponseType;
import gov.va.med.pharmacy.peps.service.common.capability.DrugAccountServiceCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;


/**
 * This class is used to process the drug accountability input objects from Vista
 */
public class DrugAccountServiceCapabilityImpl implements DrugAccountServiceCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(DrugAccountServiceCapabilityImpl.class);
    
 //   private static final String PPOU_NAME = "Price Per Order Unit";
 //   private static final String PPDU_NAME = "Price Per Dispense Unit";
 //   private static final String DUPOU_NAME = "Dispense Unit Per Order Unit";
 
    //   private static final String OU_NAME = "Order Unit";
 //   private static final String OTC_NAME = "OTC Rx Indicator";
 //   private static final String SYNONYM_NAME = "Synonym";
 
    //   private static final String MANUFACTURER_NAME = "Manufacturer";
 //   private static final String VENDOR_NAME = "Vendor";
 //   private static final String VSN_NAME = "Vendor Stock Number";
 //   private static final String CURRENTINV_NAME = "Current Inventory";
 //   private static final String IFCAP_NAME = "IFCAP Item Number";
 //   private static final String OU_COLON = "Order Unit: ";
 //   private static final String DNE = " does not exist.";

    // private static final ObjectFactory FACTORY = new ObjectFactory();

    private DrugAccountabilityCapability drugAccountabilityCapability;
 
    //  private DrugAccountabilityService drugAccountabilityService;

    /**
     * Lookup the NDCs and Products for the given NDC numbers and Vuid's.
     * 
     * @param requestXML Collection of drug accountability objects
     * @return The response messages. One per drug.
     */
    public String processRequest(String requestXML) {

        DrugAccountabilityRequest drugs = drugAccountabilityCapability.handleRequest(requestXML);

        DrugAccountabilityResponse response = null;

        List<RequestAdd> requestAdd = drugs.getRequestAdd();
        List<RequestUpdate> requestUpdate = drugs.getRequestUpdate();

        LOG.debug("Drugaccountability.ProcessRequest: Add request size = " + requestAdd.size());
        LOG.debug("Drugaccountability.ProcessRequest: Update request size = " + requestUpdate.size());

        if (requestAdd.size() == 0 && requestUpdate.size() == 0) {

            return null;
        }

        // create response object
        ObjectFactory objectFactory = new ObjectFactory();
        response = objectFactory.createDrugAccountabilityResponse();

        // I need to create a new transaction for each of the items in the message. Therefore, I
        // need to call back into the service layer so I go back through the bean and get a new
        // transaction. This way if any of the individual transactions fail, I can can just log
        // the error message in the response message and let the other transactions succeed.

        // Process each New request
//        for (int requestIndex = 0; requestIndex < requestAdd.size(); requestIndex++) {
//            ResponseType addResponse;
//
//            try {
//                addResponse = drugAccountabilityService.processNewRequest(requestAdd.get(requestIndex));
//            } catch (Exception e) {
//                LOG.error("failed to process new drug accountability NDC", e);
//
//                addResponse = this.addResponse(false, requestAdd.get(requestIndex).getNdc(), e.toString());
//            }
//
//            response.getResponseType().add(addResponse);
//        }
//
//        // Process each Update request
//        for (int requestIndex = 0; requestIndex < requestUpdate.size(); requestIndex++) {
//            ResponseType updateResponse;
//
//            try {
//                updateResponse = drugAccountabilityService.processUpdateRequest(requestUpdate.get(requestIndex));
//            } catch (Exception e) {
//                LOG.error("failed to process drug accountability NDC update", e);
//
//                updateResponse = this.addResponse(false, requestUpdate.get(requestIndex).getNdc(), e.toString());
//            }
//
//            response.getResponseType().add(updateResponse);
//        }

        return drugAccountabilityCapability.handleResponse(response);
    } // end of processRequest

    /**
     * Add a new one.
     * 
     * @param request Collection of DrugDataVo
     * @return ResponseType response containing drugs not found and drugs with does routes and types
     */
    public ResponseType processNewRequest(RequestAdd request) {
        
        return null;
    }
    
    
//        String elevenDigitNDCWithDashes = request.getNdc().substring(1);
//        String elevenDigitNDCWithOutDashes = elevenDigitNDCWithDashes.replaceAll("-", "");
//
//        NdcVo ndcVo = null;
//
//        // first see if the NDC exists, if it does then return error.
//        List<ManagedItemVo> ndcVoList = searchForNDC(elevenDigitNDCWithOutDashes);
//
//        if (ndcVoList.size() == 0) {
//
//            // No NDC Exists so create new NDC so we can populate it
//            ndcVo = (NdcVo) managedItemCapability.retrieveBlankTemplate(EntityType.NDC);
//        } else if (ndcVoList.size() == 1) {
//
//            // The one I am searching for exists so this is an error
//            return (addResponse(false, request.getNdc(), "NDC Already Exists"));
//        } else {
//
//            // There are more than one meeting my results. This is a large error
//            return (addResponse(false, request.getNdc(), "Multiple NDCs exist with this number!"));
//        }
//
//        DataFields<DataField> ndcVadf = ndcVo.getVaDataFields();
//
//        ndcVo.setNdc(elevenDigitNDCWithDashes);
//        ndcVo.setNdcPart1(elevenDigitNDCWithDashes.substring(0, PPSConstants.I5));
//        ndcVo.setNdcPart2(elevenDigitNDCWithDashes.substring(PPSConstants.I5, PPSConstants.I9));
//        ndcVo.setNdcPart3(elevenDigitNDCWithDashes.substring(PPSConstants.I9, PPSConstants.I11));
//
//        // Get the OrderUnit Vo
//        OrderUnitVo orderUnitVo = null;
//
//        orderUnitVo = searchForOrderUnit(request.getOrderUnit());
//
//        // If orderUnit is not in the order Unit list, then populate error response;
//
//        if (orderUnitVo == null) {
//            return addResponse(false, request.getNdc(), OU_COLON + request.getOrderUnit() + DNE);
//        } else {
//            ndcVo.setOrderUnit(orderUnitVo);
//        }
//
//        // Mandatory Field: Add NDC Price Per Order Unit to both of these fields
//        ndcVadf.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT).selectValue(request.getPricePerOrderUnit());
//        ndcVadf.getDataField(FieldKey.UNIT_PRICE).selectValue(request.getPricePerOrderUnit());
//
//        // Mandatory Field: Add NDC Price Per Dispense Unit.
//        ndcVadf.getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT).selectValue(request.getPricePerDispenseUnit());
//
//        AddNDCFields nDCFields = request.getAddNDCFields();
//
//        if (nDCFields != null) {
//            ManufacturerVo manVo = null;
//
//            // If Manufacturer exists in request object then get the Manufacturer VO and set it
//            if (nDCFields.getManufacturer() != null && nDCFields.getManufacturer().length() > 1) {
//                manVo = searchForManufacturer(nDCFields.getManufacturer());
//
//                // Manufacturer provided was not in the PEPS list so populate error response.
//
//                if (manVo == null) {
//                    return addResponse(false, request.getNdc(), "Manufacturer " + nDCFields.getManufacturer()
//                        + " does not exist in PEPS.");
//                } else {
//                    ndcVo.setManufacturer(manVo);
//                }
//            }
//
//            // 1 is OTC and blank or 2 is starts with one isn't correct.
//            OtcRxVo otc = new OtcRxVo();
//
//            if (nDCFields.getOtcRXIndicator() != null && nDCFields.getOtcRXIndicator().startsWith("1")) {
//                otc.setId(PPSConstants.OVER_THE_COUNTER.toLowerCase());
//                otc.setValue(PPSConstants.OVER_THE_COUNTER);
//            } else {
//                otc.setId(PPSConstants.PRESCRIPTION.toLowerCase());
//                otc.setValue(PPSConstants.PRESCRIPTION);
//            }
//
//            ndcVo.setOtcRxIndicator(otc);
//
//            // Add NDC Dispense Unit Per Order Unit.
//            ndcVo.setNdcDispUnitsPerOrdUnit(nDCFields.getDispenseUnitsPerOrderUnit());
//
//            // Add Vendor
//            if (nDCFields.getVendor() != null) {
//                ndcVo.setVendor(nDCFields.getVendor());
//            }
//
//            // Add Vendor Stock Number
//            if (nDCFields.getVsn() != null) {
//                ndcVo.setVendorStockNumber(nDCFields.getVsn());
//            }
//
//        }
//
//        ProductVo productVo = searchForProduct(request.getVuid().toString());
//
//        if (productVo == null) {
//
//            return addResponse(false, request.getNdc(), "Could not find product that matches the vuid: " + request.getVuid());
//        }
//
//        ndcVo.setParent(productVo);
//
//        // set the product type to the product type of the parent
//        DataFields<DataField> dfields = productVo.getVaDataFields();
//        ListDataField<String> productTypeDataField = dfields.getDataField(FieldKey.CATEGORIES);
//
//        DataFields<DataField> productVadf = productVo.getVaDataFields();
//        ndcVo.getVaDataFields().setDataField(productTypeDataField);
//
//        // Set default fields
//        ndcVo.setSource(NdcSourceType.VA);
//        ndcVo.setUpcUpn("");
//        ndcVo.setPackageSize(new Double(0));
//
//        ndcVo.setVaDataFields(ndcVadf);
//
//        // Now run NDC validation
//        Errors errors = ndcVo.checkForErrors();
//
//        if (errors.getErrors().size() > 0) {
//            List<ValidationError> validationErrors = errors.getErrors();
//            StringBuffer errorMessage = new StringBuffer();
//
//            for (ValidationError validationError : validationErrors) {
//                errorMessage.append(validationError.getLocalizedError());
//            }
//
//            return addResponse(false, request.getNdc(), errorMessage.toString());
//        }
//
//        // PROCESS Product Portion of the add
//        AddProductFields pFields = request.getAddProductFields();
//
//        ItemAuditHistoryVo productAudit = new ItemAuditHistoryVo();
//
//        productAudit.setReason("Drug Accountability Update!");
//        productAudit.setAuditItemId(productVo.getId());
//        productAudit.setCreatedBy("Drug Accountability!");
//        productAudit.setEventCategory(EventCategory.LOCAL_MODIFICATION);
//        productAudit.setAuditItemType(EntityType.PRODUCT);
//        productAudit.setSiteName(environmentUtility.getSiteNumber());
//        productAudit.setUsername(" Drug Accountability ");
//
//        if (pFields != null) {
//
//            // Update Current Inventory
//            if (pFields.getCurrentInventory() != null) {
//
//                try {
//                    Long oldl = productVadf.getDataField(FieldKey.CURRENT_INVENTORY).getValue();
//                    Long newl = new Long(pFields.getCurrentInventory());
//
//                    if (oldl == null) {
//                        oldl = 0l;
//                    } else {
//                        oldl.toString();
//                    }
//
//                    if (oldl.equals(newl)) {
//                        productAudit.getDetails().add(createIAHDetail(CURRENTINV_NAME, oldl.toString(), newl.toString()));
//                        productVadf.getDataField(FieldKey.CURRENT_INVENTORY).selectValue(newl);
//                    }
//                } catch (Exception e) {
//                    return addResponse(false, request.getNdc(), "The Current Inventory provided "
//                        + pFields.getCurrentInventory() + " is not numeric.");
//                }
//            }
//
//            // Set the pricing fields for the product if provided
//            if (pFields.getDispenseUnitPerOrderUnit() != null) {
//
//                Double d = productVo.getVaDataFields().getDataField(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT).getValue();
//                String oldValue = "";
//
//                if (d == null) {
//                    oldValue = "";
//                } else {
//                    oldValue = d.toString();
//                }
//
//                if (!pFields.getDispenseUnitPerOrderUnit().equals(d)) {
//                    productAudit.getDetails().add(
//                        this.createIAHDetail(DUPOU_NAME, oldValue, pFields.getDispenseUnitPerOrderUnit().toString()));
//                    productVo.getVaDataFields().getDataField(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT).selectValue(
//                        pFields.getDispenseUnitPerOrderUnit());
//                }
//            }
//
//            // If the IFCAP doesn't already exist in the product then add it.
//            List<String> ifcapList = pFields.getIfcapItemNumber();
//
//            for (String ifcap : ifcapList) {
//
//                boolean bIfcapExists = false;
//
//                Collection<IfcapItemNumberVo> ifcapCollection = productVo.getIfcapItemNumber();
//
//                for (IfcapItemNumberVo ifcapItemNumberVo : ifcapCollection) {
//                    if (ifcapItemNumberVo.getValue().equals(ifcap)) {
//                        bIfcapExists = true;
//                        break;
//                    }
//                }
//
//                if (!bIfcapExists) {
//                    String oldIfcapCollection = this.createIfcapIAH(ifcapCollection);
//                    IfcapItemNumberVo ifcapItemNumberVo = new IfcapItemNumberVo();
//                    ifcapItemNumberVo.setValue(ifcap);
//                    ifcapCollection.add(ifcapItemNumberVo);
//                    productVo.setIfcapItemNumber(ifcapCollection);
//
//                    productAudit.getDetails().add(
//                        this.createIAHDetail(IFCAP_NAME, oldIfcapCollection, createIfcapIAH(ifcapCollection)));
//
//                }
//            }
//        }
//
//        AddSynonymFields sFields = request.getAddSynonymFields();
//
//        if (sFields != null) {
//
//            // Get current list of synonyms
//            Collection<SynonymVo> synonyms = productVo.getSynonyms();
//
//            boolean bNeedToPopulateSynonym = true;
//
//            // Check if NDC already exists as drug accountability and if so just skip it.
//            for (SynonymVo synonym : synonyms) {
//                if (synonym.getSynonymName() != null && synonym.getSynonymName().equals(elevenDigitNDCWithOutDashes)
//                    && synonym.getSynonymIntendedUse().getValue().startsWith("D")) {
//
//                    bNeedToPopulateSynonym = false;
//                    break;
//                }
//            }
//
//            // Need to create a new synonym multiple and add to the collection.
//            if (bNeedToPopulateSynonym) {
//
//                // Set synonym's properties
//                SynonymVo synonymVo = new SynonymVo();
//
//                synonymVo.setSynonymName(elevenDigitNDCWithOutDashes);
//
//                // Intended use Domain Capability
//                IntendedUseVo useVo = searchForIntendedUse("D");
//                synonymVo.setSynonymIntendedUse(useVo);
//
//                synonymVo.setNdcCode(elevenDigitNDCWithDashes);
//                synonymVo.setSynonymOrderUnit(orderUnitVo);
//
//                synonymVo.setSynonymPricePerOrderUnit(request.getPricePerOrderUnit());
//                synonymVo.setSynonymPricePerDispenseUnit(request.getPricePerDispenseUnit());
//
//                if (sFields.getDispenseUnitsPerOrderUnit() != null) {
//                    synonymVo.setSynonymDispenseUnitPerOrderUnit(sFields.getDispenseUnitsPerOrderUnit());
//                }
//
//                if (sFields.getVendor() != null) {
//                    synonymVo.setSynonymVendor(sFields.getVendor());
//                }
//
//                if (sFields.getVsn() != null) {
//                    synonymVo.setSynonymVsn(sFields.getVsn());
//                }
//
//                // Insert this synonym entry into synonymVO
//                productVo.getSynonyms().add(synonymVo);
//
//                productVo.setSynonyms(synonyms);
//
//                productAudit.getDetails().add(this.createIAHDetail(SYNONYM_NAME, "", synonymVo.toIAHString()));
//            }
//        }
//
//        productVo.setVaDataFields(productVadf);
//
//        // Create the Item Audit History Records
//        ItemAuditHistoryDetailsVo detail = new ItemAuditHistoryDetailsVo();
//        detail.setColumnName("Add NDC Request");
//        detail.setDetailReason("New NDC Request");
//        detail.setEventCategory(EventCategory.REQUEST_TO_ADD);
//
//        ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
//        audit.setReason("Drug Accountability Update");
//        audit.addDetail(detail);
//        audit.setCreatedBy("Drug Accountability ");
//        audit.setEventCategory(EventCategory.REQUEST_TO_ADD);
//        audit.setAuditItemType(EntityType.NDC);
//        audit.setSiteName(environmentUtility.getSiteNumber());
//        audit.setUsername("Drug Accountability  ");
//
//        // The NDC will always be sent, only update the product if fields were populated.
//        try {
//
//            // If product data was provided but didn't result in an update then no need to
//            // create an IAH record.
//            if (productAudit.getDetails().isEmpty()) {
//                productAudit = null;
//            }
//
////            // if there was data in either the synonym or the product update fields update the product
////            if (pFields != null || sFields != null) {
////
////                managedItemCapability.processFromLocalVista(ndcVo, audit, true, productVo, productAudit);
////            }
////            else {
////                managedItemCapability.processFromLocalVista(ndcVo, audit, true, null, null);
////            }
//
//        } catch (Exception e) {
//            LOG.error("failed to process drug accountability NDC", e);
//
//            return addResponse(false, request.getNdc(), e.toString());
//        }
//
//        return addResponse(true, request.getNdc(), "");
//    }

    /**
     * This method is used to add an audit history record to the NDC Update
     */
    public void addNDCAuditHistoryRecord() {

    }

    /**
     * Private method used to process the updateRequest *
     * 
     * @param request The request to be processed
     * @return ResponseType The response object
     */
    public ResponseType processUpdateRequest(RequestUpdate request) {
        
        return null;
    }
    
//        String elevenDigitNDCWithDashes = request.getNdc().substring(1);
//        String elevenDigitNDCWithOutDashes = elevenDigitNDCWithDashes.replaceAll("-", "");
//
//        NdcVo ndcVo = null;
//        ItemAuditHistoryVo ndcAudit = new ItemAuditHistoryVo();
//        ItemAuditHistoryVo productAudit = new ItemAuditHistoryVo();
//
//        // Check the NDC Fields for update information
//        UpdateNDCFields nFields = request.getUpdateNDCFields();
//
//        if (nFields != null) {
//
//            // If this search criteria matches zero NDCs or One NDC this is an error.
//            List<ManagedItemVo> ndcVoList = searchForNDC(elevenDigitNDCWithDashes);
//
//            if (ndcVoList.size() == 0) {
//
//                // The NDC does not exist so return an error.
//                return (addResponse(false, request.getNdc(), "NDC Does not exist"));
//            } else if (ndcVoList.size() == 1) {
//
//                // The one I am searching for exists so set the NDC.
//                ndcVo = (NdcVo) ndcVoList.get(0);
//            } else {
//
//                // There are more than one meeting my results. This is an error
//                return (addResponse(false, request.getNdc(), "Multiple NDCs exist with this number"));
//            }
//
//            ndcAudit.setReason("Drug Accountability Update");
//            ndcAudit.setCreatedBy("Drug Accountability");
//            ndcAudit.setEventCategory(EventCategory.LOCAL_MODIFICATION);
//            ndcAudit.setAuditItemType(EntityType.NDC);
//            ndcAudit.setSiteName(environmentUtility.getSiteNumber());
//            ndcAudit.setUsername("Drug Accountability");
//            ndcAudit.setAuditItemId(ndcVo.getId());
//
//            DataFields<DataField> ndcVadf = ndcVo.getVaDataFields();
//
//            OrderUnitVo orderUnitVo = null;
//
//            // Update the OrderUnit Vo if given and different
//            if (nFields.getOrderUnit() != null) {
//
//                if (ndcVo.getOrderUnit().getValue().equals(nFields.getOrderUnit()) == false) {
//
//                    orderUnitVo = searchForOrderUnit(nFields.getOrderUnit());
//
//                    // If orderUnit provided is not in the order Unit list, then populate error response;
//
//                    if (orderUnitVo == null) {
//                        return addResponse(false, request.getNdc(), OU_COLON + nFields.getOrderUnit()
//                            + DNE);
//                    }
//                    else {
//                        ndcAudit.getDetails().add(
//                            this.createIAHDetail(OU_NAME, ndcVo.getOrderUnit().getValue(), nFields.getOrderUnit()));
//                        ndcVo.setOrderUnit(orderUnitVo);
//
//                    }
//                }
//            }
//
//            // 1 is OTC and blank or else set to Prescription
//            if (nFields.getOtcRXIndicator() != null) {
//
//                String strPrescriptionValue = PPSConstants.PRESCRIPTION;
//                String strPrescriptionId = PPSConstants.PRESCRIPTION.toLowerCase();
//                String strOTCValue = PPSConstants.OVER_THE_COUNTER;
//                String strOTCId = PPSConstants.OVER_THE_COUNTER.toLowerCase();
//                String oldValue = "";
//                
//                boolean bUpdate = false;
//
//                if (ndcVo.getOtcRxIndicator() == null) {
//                    bUpdate = true;
//                    oldValue = "";
//                }
//                else {
//                    if (nFields.getOtcRXIndicator().equals("1")) {
//                        if (strOTCId.equals(ndcVo.getOtcRxIndicator().getValue())) {
//                            bUpdate = true;
//                            oldValue = strOTCValue;
//                        }
//                    }
//                    else {
//                        if (strPrescriptionId.equals(ndcVo.getOtcRxIndicator().getValue())) {
//                            bUpdate = true;
//                            oldValue = strPrescriptionValue;
//                        }
//                    }
//                }
//
//                if (bUpdate) {
//                    OtcRxVo otc = new OtcRxVo();
//
//                    if (nFields.getOtcRXIndicator().startsWith("1")) {
//                        otc.setId(strOTCId);
//                        otc.setValue(strOTCValue);
//                    }
//                    else {
//                        otc.setId(strPrescriptionId);
//                        otc.setValue(strPrescriptionValue);
//                    }
//
//                    ndcAudit.getDetails().add(createIAHDetail(OTC_NAME, oldValue, otc.getValue()));
//                    ndcVo.setOtcRxIndicator(otc);
//                }
//            }
//
//            // Update the NDC Price Per Dispense Unit.
//            if (nFields.getPricePerDispenseUnit() != null) {
//
//                Double d = ndcVadf.getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT).getValue();
//                String oldValue;
//
//                if (d == null) {
//                    oldValue = "";
//                }
//                else {
//                    oldValue = d.toString();
//                }
//
//                if (nFields.getPricePerDispenseUnit().equals(d) == false) {
//                    ndcAudit.getDetails().add(
//                        this.createIAHDetail(PPDU_NAME, oldValue, nFields.getPricePerDispenseUnit().toString()));
//
//                    ndcVadf.getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT)
//                        .selectValue(nFields.getPricePerDispenseUnit());
//                }
//            }
//
//            // Update the NDC Price Per Order Unit.
//            if (nFields.getPricePerOrderUnit() != null) {
//
//                Double d = ndcVadf.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT).getValue();
//                String oldValue;
//
//                if (d == null) {
//                    oldValue = "";
//                }
//                else {
//                    oldValue = d.toString();
//                }
//
//                if (nFields.getPricePerOrderUnit().equals(d) == false) {
//                    ndcAudit.getDetails().add(
//                        this.createIAHDetail(PPOU_NAME, oldValue, nFields.getPricePerOrderUnit().toString()));
//                    ndcVadf.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT).selectValue(nFields.getPricePerOrderUnit());
//                }
//            }
//
//            // Add NDC Dispense Unit Per Order Unit.
//            if (nFields.getDispenseUnitsPerOrderUnit() != null) {
//
//                Double d = ndcVo.getNdcDispUnitsPerOrdUnit();
//                String oldValue;
//
//                if (d == null) {
//                    oldValue = "";
//                }
//                else {
//                    oldValue = d.toString();
//                }
//
//                if (nFields.getDispenseUnitsPerOrderUnit().equals(d) == false) {
//                    ndcAudit.getDetails().add(
//                        this.createIAHDetail(DUPOU_NAME, oldValue, nFields.getDispenseUnitsPerOrderUnit().toString()));
//                    ndcVo.setNdcDispUnitsPerOrdUnit(nFields.getDispenseUnitsPerOrderUnit());
//                }
//
//            }
//
//            // Add Vendor
//            if (nFields.getVendor() != null && nFields.getVendor().length() > 0) {
//
//                if (ndcVo.getVendor() == null) {
//                    ndcAudit.getDetails().add(createIAHDetail(VENDOR_NAME, "", nFields.getVendor()));
//                }
//                else {
//                    if (nFields.getVendor().equals(ndcVo.getVendor()) == false) {
//                        ndcAudit.getDetails().add(this.createIAHDetail(VENDOR_NAME, ndcVo.getVendor(), nFields.getVendor()));
//                    }
//                }
//
//                ndcVo.setVendor(nFields.getVendor());
//            }
//
//            // Add Vendor Stock Number
//            if (nFields.getVsn() != null && nFields.getVsn().length() > 0) {
//
//                if (ndcVo.getVendorStockNumber() == null) {
//                    ndcAudit.getDetails().add(createIAHDetail(VSN_NAME, "", nFields.getVsn()));
//                }
//                else {
//                    if (nFields.getVendor().equals(ndcVo.getVendor()) == false) {
//                        ndcAudit.getDetails()
//                            .add(this.createIAHDetail(VSN_NAME, ndcVo.getVendorStockNumber(), nFields.getVsn()));
//                    }
//                }
//
//                ndcVo.setVendorStockNumber(nFields.getVsn());
//            }
//
//            if (nFields.getManufacturer() != null && nFields.getManufacturer().length() > 0) {
//
//                String oldValue = "";
//                boolean bUpdate = true;
//
//                if (ndcVo.getManufacturer() != null) {
//                    if (nFields.getManufacturer().equals(ndcVo.getManufacturer().getValue())) {
//
//                        // No need to update since the old manufacturer is same as the one provided
//                        bUpdate = false;
//                    }
//                    else {
//                        oldValue = ndcVo.getManufacturer().getValue();
//                    }
//                }
//
//                ManufacturerVo manVo = null;
//
//                // If Manufacturer is provided and different from the one in the NdcVo then update it.
//                if (bUpdate) {
//                    manVo = searchForManufacturer(nFields.getManufacturer());
//
//                    if (manVo == null) {
//                        return addResponse(false, request.getNdc(), "Manufacturer " + nFields.getManufacturer()
//                            + " does not exist in PEPS.");
//                    }
//                    else {
//
//                        ndcAudit.getDetails().add(
//                            this.createIAHDetail(MANUFACTURER_NAME, oldValue, nFields.getManufacturer()));
//                        ndcVo.setManufacturer(manVo);
//                    }
//                }
//            }
//
//            ndcVo.setVaDataFields(ndcVadf);
//        }
//
//        // PROCESS Product Portion of the add
//        UpdateProductFields pFields = request.getUpdateProductFields();
//        UpdateSynonymFields sFields = request.getUpdateSynonymFields();
//        ProductVo productVo = null;
//
//        if (nFields != null || pFields != null || sFields != null) {
//            productVo = searchForProduct(request.getVuid().toString());
//
//            if (productVo == null) {
//
//                return addResponse(false, request.getNdc(), "Could not find product that matches the vuid: "
//                    + request.getVuid());
//            }
//        }
//
//        if (nFields != null) {
//            ndcVo.setParent(productVo);
//
//            // Now run NDC validation
//            Errors errors = ndcVo.checkForErrors();
//
//            if (errors.getErrors().size() > 0) {
//                List<ValidationError> validationErrors = errors.getErrors();
//                String errorMessage = null;
//
//                for (ValidationError validationError : validationErrors) {
//                    errorMessage += validationError.getLocalizedError();
//                }
//
//                return addResponse(false, request.getNdc(), errorMessage);
//            }
//        }
//
//        if (pFields != null || sFields != null) {
//            DataFields<DataField> productVadf = productVo.getVaDataFields();
//
//            productAudit.setReason("Drug Accountability Update");
//            productAudit.setCreatedBy("Drug Accountability");
//            productAudit.setEventCategory(EventCategory.LOCAL_MODIFICATION);
//            productAudit.setAuditItemType(EntityType.PRODUCT);
//            productAudit.setAuditItemId(productVo.getId());
//            productAudit.setSiteName(environmentUtility.getSiteNumber());
//            productAudit.setUsername("Drug Accountability");
//
//            if (pFields != null) {
//
//                OrderUnitVo productOrderUnitVo = null;
//
//                // Update the OrderUnit Vo if given and different
//                if (pFields.getOrderUnit() != null) {
//                    boolean bUpdate = true;
//                    String oldValue = "";
//
//                    if (productVo.getOrderUnit() != null) {
//                        oldValue = productVo.getOrderUnit().getValue();
//
//                        if (pFields.getOrderUnit().equals(productVo.getOrderUnit().getValue())) {
//                            bUpdate = false;
//                        }
//                    }
//
//                    // Only update if the Order unit given is different from what is already in the VO
//                    if (bUpdate) {
//                        productOrderUnitVo = searchForOrderUnit(pFields.getOrderUnit());
//
//                        // If orderUnit provided is not in the order Unit list, then populate error response;
//
//                        if (productOrderUnitVo == null) {
//                            return addResponse(false, request.getNdc(), "The Product Order Unit: " + pFields.getOrderUnit()
//                                + DNE);
//                        }
//                        else {
//                            productAudit.getDetails().add(this.createIAHDetail(OU_NAME, oldValue, pFields.getOrderUnit()));
//                            productVo.setOrderUnit(productOrderUnitVo);
//                        }
//                    }
//                }
//
//                // Set the pricing fields for the product if provided
//                if (pFields.getDispenseUnitsPerOrderUnit() != null) {
//
//                    Double d = productVo.getVaDataFields().getDataField(
//                         FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT).getValue();
//                    String oldValue = "";
//
//                    if (d != null) {
//                        oldValue = d.toString();
//                    }
//
//                    if (pFields.getDispenseUnitsPerOrderUnit().equals(d) == false) {
//                        productAudit.getDetails().add(
//                            this.createIAHDetail(DUPOU_NAME, oldValue, pFields.getDispenseUnitsPerOrderUnit().toString()));
//                        productVo.getVaDataFields().getDataField(FieldKey.
//    PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT).selectValue(pFields.getDispenseUnitsPerOrderUnit());
//                    }
//                }
//
//                // Update the Product Price Per Dispense Unit.
//                if (pFields.getPricePerDispenseUnit() != null) {
//
//                    Double d = productVadf.getDataField(FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT).getValue();
//                    String oldValue = "";
//
//                    if (d != null) {
//                        oldValue = d.toString();
//                    }
//
//                    if (pFields.getPricePerDispenseUnit().equals(d) == false) {
//                        productAudit.getDetails().add(
//                            this.createIAHDetail(PPDU_NAME, oldValue, pFields.getPricePerDispenseUnit().toString()));
//                        productVadf.getDataField(FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT).selectValue(
//                            pFields.getPricePerDispenseUnit());
//                    }
//                }
//
//                // Update the Product Price Per Order Unit.
//                if (pFields.getPricePerOrderUnit() != null) {
//
//                    Double d = productVadf.getDataField(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT).getValue();
//                    String oldValue = "";
//
//                    if (d != null) {
//                        oldValue = d.toString();
//                    }
//
//                    if (pFields.getPricePerOrderUnit().equals(d) == false) {
//                        productAudit.getDetails().add(
//                            createIAHDetail(PPOU_NAME, oldValue, pFields.getPricePerOrderUnit().toString()));
//                        productVadf.getDataField(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT).selectValue(
//                            pFields.getPricePerOrderUnit());
//                    }
//                }
//
//                // Add Current Inventory
//                if (pFields.getCurrentInventory() != null) {
//
//                    try {
//                        Long oldl = productVadf.getDataField(FieldKey.CURRENT_INVENTORY).getValue();
//                        Long newl = new Long(pFields.getCurrentInventory());
//                        String oldValue = "";
//
//                        if (oldl != null) {
//                            oldValue = oldl.toString();
//                        }
//
//                        if (oldl.equals(newl) == false) {
//                            productAudit.getDetails().add(createIAHDetail(CURRENTINV_NAME, oldValue, newl.toString()));
//                            productVadf.getDataField(FieldKey.CURRENT_INVENTORY).selectValue(newl);
//                        }
//                    }
//                    catch (Exception e) {
//                        return addResponse(false, request.getNdc(), "The Current Inventory provided "
//                            + pFields.getCurrentInventory() + " is not numeric.");
//                    }
//                }
//
//                // If the IFCAP doesn't already exist in the product then add it.
//                if (pFields.getIfcapItemNumber() != null) {
//                    List<String> ifcapList = pFields.getIfcapItemNumber();
//
//                    for (String ifcap : ifcapList) {
//
//                        boolean bIfcapExists = false;
//
//                        Collection<IfcapItemNumberVo> ifcapCollection = productVo.getIfcapItemNumber();
//
//                        for (IfcapItemNumberVo ifcapItemNumberVo : ifcapCollection) {
//                            if (ifcapItemNumberVo.getValue().equals(ifcap)) {
//                                bIfcapExists = true;
//                                break;
//                            }
//                        }
//
//                        if (bIfcapExists == false) {
//                            String oldIfcapCollection = this.createIfcapIAH(ifcapCollection);
//
//                            IfcapItemNumberVo ifcapItemNumberVo = new IfcapItemNumberVo();
//                            ifcapItemNumberVo.setValue(ifcap);
//                            ifcapCollection.add(ifcapItemNumberVo);
//                            productVo.setIfcapItemNumber(ifcapCollection);
//
//                            productAudit.getDetails().add(
//                                this.createIAHDetail(IFCAP_NAME, oldIfcapCollection, createIfcapIAH(ifcapCollection)));
//
//                        }
//                    }
//                }
//            }
//
//            if (sFields != null) {
//
//                // Get current list of synonyms
//                Collection<SynonymVo> synonyms = productVo.getSynonyms();
//                SynonymVo synonymVo = null;
//
//                boolean bNeedToPopulateSynonym = true;
//
//                // Check if NDC already exists as drug accountability and if so just update it otherwise Add One
//                for (SynonymVo synonym : synonyms) {
//                    if (synonym.getSynonymName() != null && synonym.getSynonymName().equals(elevenDigitNDCWithOutDashes)
//                        && synonym.getSynonymIntendedUse().getValue().startsWith("D")) {
//                        bNeedToPopulateSynonym = false;
//                        synonyms.remove(synonym); // remove the object, I will add it back later after the update
//                        synonymVo = synonym;
//                        break;
//                    }
//                }
//
//                // Need to create a new synonym multiple and add to the collection.
//                if (bNeedToPopulateSynonym) {
//
//                    // Set synonym's properties
//                    synonymVo = new SynonymVo();
//
//                    synonymVo.setSynonymName(elevenDigitNDCWithOutDashes);
//
//                    // Intended use Domain Capability
//                    IntendedUseVo useVo = searchForIntendedUse("D");
//                    synonymVo.setSynonymIntendedUse(useVo);
//
//                    synonymVo.setNdcCode(elevenDigitNDCWithDashes);
//
//                    if (sFields.getOrderUnit() != null) {
//
//                        OrderUnitVo synonymOrderUnitVo = searchForOrderUnit(sFields.getOrderUnit());
//
//                        // If orderUnit provided is not in the order Unit list, then populate error response;
//
//                        if (synonymOrderUnitVo == null) {
//                            return addResponse(false, request.getNdc(), "The Product Order Unit: " + sFields.getOrderUnit()
//                                + DNE);
//                        }
//                        else {
//                            synonymVo.setSynonymOrderUnit(synonymOrderUnitVo);
//                        }
//                    }
//
//                    if (sFields.getPricePerDispenseUnit() != null) {
//                        synonymVo.setSynonymPricePerOrderUnit(sFields.getPricePerDispenseUnit());
//                    }
//
//                    if (sFields.getPricePerOrderUnit() != null) {
//                        synonymVo.setSynonymPricePerDispenseUnit(sFields.getPricePerDispenseUnit());
//                    }
//
//                    if (sFields.getDispenseUnitsPerOrderUnit() != null) {
//                        synonymVo.setSynonymDispenseUnitPerOrderUnit(sFields.getDispenseUnitsPerOrderUnit());
//                    }
//
//                    if (sFields.getVendor() != null) {
//                        synonymVo.setSynonymVendor(sFields.getVendor());
//                    }
//
//                    if (sFields.getVsn() != null) {
//                        synonymVo.setSynonymVsn(sFields.getVsn());
//                    }
//
//                    // Insert this synonym entry into synonymVO
//                    productVo.getSynonyms().add(synonymVo);
//
//                    productVo.setSynonyms(synonyms);
//
//                    productAudit.getDetails().add(this.createIAHDetail(SYNONYM_NAME, "", synonymVo.toIAHString()));
//
//                }
//
//                // Else need to update the one that currently exists
//                else {
//
//                    String oldSynonym = synonymVo.toIAHString();
//                    boolean bNeedToUpdate = false;
//
//                    // Not going to update the Name, Intended use or code fields.
//                    if (sFields.getOrderUnit() != null) {
//
//                        boolean bUpdate = true;
//
//                        if (synonymVo.getSynonymOrderUnit() != null) {
//                            if (sFields.getOrderUnit().equals(synonymVo.getSynonymOrderUnit().getValue())) {
//                                bUpdate = false;
//                            }
//                        }
//
//                        // Only update if the Order unit given is different from what is already in the VO
//                        if (bUpdate) {
//
//                            OrderUnitVo synonymOrderUnitVo = searchForOrderUnit(sFields.getOrderUnit());
//
//                            // If orderUnit provided is not in the order Unit list, then populate error response;
//
//                            if (synonymOrderUnitVo == null) {
//                                return addResponse(false, request.getNdc(), "The Product Synonym Order Unit: "
//                                    + sFields.getOrderUnit() + DNE);
//                            }
//                            else {
//                                synonymVo.setSynonymOrderUnit(synonymOrderUnitVo);
//                            }
//
//                            bNeedToUpdate = true;
//                        }
//                    }
//
//                    if (sFields.getPricePerDispenseUnit() != null) {
//                        Double d = new Double(0);
//
//                        if (synonymVo.getSynonymPricePerDispenseUnit() != null) {
//
//                            try {
//                                d = new Double(synonymVo.getSynonymPricePerDispenseUnit());
//                            }
//                            catch (Exception e) {
//                                d = new Double(0);
//                            }
//                        }
//
//                        if (sFields.getPricePerDispenseUnit().equals(d) == false) {
//                            synonymVo.setSynonymPricePerDispenseUnit(sFields.getPricePerDispenseUnit());
//                            bNeedToUpdate = true;
//                        }
//                    }
//
//                    if (sFields.getPricePerOrderUnit() != null) {
//                        Double d = new Double(0);
//
//                        if (synonymVo.getSynonymPricePerOrderUnit() != null) {
//
//                            try {
//                                d = new Double(synonymVo.getSynonymPricePerOrderUnit());
//                            }
//                            catch (Exception e) {
//                                d = new Double(0);
//                            }
//                        }
//
//                        if (sFields.getPricePerOrderUnit().equals(d) == false) {
//                            synonymVo.setSynonymPricePerOrderUnit(sFields.getPricePerOrderUnit());
//                            bNeedToUpdate = true;
//                        }
//                    }
//
//                    if (sFields.getDispenseUnitsPerOrderUnit() != null) {
//                        Double d = new Double(0);
//
//                        if (synonymVo.getSynonymDispenseUnitPerOrderUnit() != null) {
//                            d = synonymVo.getSynonymDispenseUnitPerOrderUnit();
//                        }
//
//                        if (sFields.getDispenseUnitsPerOrderUnit().equals(d) == false) {
//                            synonymVo.setSynonymDispenseUnitPerOrderUnit(sFields.getDispenseUnitsPerOrderUnit());
//                            bNeedToUpdate = true;
//                        }
//                    }
//
//                    if (sFields.getVendor() != null) {
//
//                        if (sFields.getVendor().equals(synonymVo.getSynonymVendor()) == false) {
//                            synonymVo.setSynonymVendor(sFields.getVendor());
//                            bNeedToUpdate = true;
//                        }
//                    }
//
//                    if (sFields.getVsn() != null) {
//
//                        if (sFields.getVsn().equals(synonymVo.getSynonymVsn()) == false) {
//                            synonymVo.setSynonymVsn(sFields.getVsn());
//                            bNeedToUpdate = true;
//                        }
//                    }
//
//                    // Already removed the vo so I have to add it back in even if it didn't change.
//                    synonyms.add(synonymVo);
//
//                    productVo.setSynonyms(synonyms);
//
//                    // only need an audit record if the vo changed
//                    if (bNeedToUpdate) {
//                        productAudit.getDetails().add(
//                            this.createIAHDetail(SYNONYM_NAME, oldSynonym, synonymVo.toIAHString()));
//                    }
//
//                }
//
//            }
//
//            productVo.setVaDataFields(productVadf);
//        }
//
//        // Go ahead and update the NDC if NFields was populated
//        try {
//
//            // These are updates so if there are no details then I didn't update anything so no
//            // reason to put in a details record.
//            if (ndcAudit.getDetails().isEmpty()) {
//                ndcAudit = null;
//            }
//
//            if (productAudit.getDetails().isEmpty()) {
//                productAudit = null;
//            }
//
////            if (nFields != null) {
////
////                if (pFields != null || sFields != null) {
////                    managedItemCapability.processFromLocalVista(ndcVo, ndcAudit, false, productVo, productAudit);
////                }
////                else {
////                    managedItemCapability.processFromLocalVista(ndcVo, ndcAudit, false, null, null);
////                }
////            }
////            else {
////                if (pFields != null || sFields != null) {
////                    managedItemCapability.processFromLocalVista(null, null, false, productVo, productAudit);
////                }
////                else {
////                    return addResponse(false, request.getNdc(),
////                        "Only the NDC and VUID were provided.  There was no data to update.");
////                }
////            }
//        }
//        catch (Exception e) {
//            LOG.error("failed to process drug accountability update", e);
//
//            return addResponse(false, request.getNdc(), e.toString());
//        }
//
//        return addResponse(true, request.getNdc(), "");
//        
//    }

    /**
     * Turn the IFCAP collection into something that would look right in the Audit History Table
     * 
     * @param ifcapCollection The collection of Icap Item numbers
     * @return String
     */
    
//    private String createIfcapIAH(Collection<IfcapItemNumberVo> ifcapCollection) {
//        String strIAH = "";
//
//        for (IfcapItemNumberVo ifcapItemNumberVo : ifcapCollection) {
//            strIAH = strIAH + ifcapItemNumberVo.getValue() + "<p>";
//        }
//
//        if (strIAH.length() > PPSConstants.I4) {
//            strIAH.substring(0, strIAH.length() - PPSConstants.I4);
//        }
//
//        return strIAH;
//    }

//    /**
//     * Create a response type object
//     * 
//     * @param type is a boolean saying whether or not it was successful
//     * @param ndc is the NDC value
//     * @param message is the error message
//     * @return ResponseType is the response object.
//     */
//    private ResponseType addResponse(boolean type, String ndc, String message) {
//
//        ObjectFactory objectFactory = new ObjectFactory();
//        ResponseType responseType = objectFactory.createResponseType();
//        responseType.setNDC(ndc);
//
//        if (type) {
//            responseType.setType("Success");
//        } else {
//            responseType.setType("Failure");
//        }
//
//        responseType.setErrorMessage(message);
//
//        LOG.debug("ResponseType is " + type + ":" + ndc + ":" + message);
//
//        return responseType;
//    }

//    /**
//     * Does the NDC exist.
//     * 
//     * @param strNDC The string of the NDC to search for and see if it exists
//     * @return NdcVo The NDC if it exists and null otherwise
//     */
//    private List<ManagedItemVo> searchForNDC(String strNDC) {
//
//        String strNDCNoDashes = strNDC.replaceAll("-", "");
//
//        // Check if NDC exists
//        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.ADVANCED, environmentUtility.getEnvironment());
//        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
//
//        // Item status of inactive and active
//        searchCriteria.setItemStatus(new ArrayList<ItemStatus>());
//        ItemStatus itemStatusActive = ItemStatus.valueOf("ACTIVE");
//        searchCriteria.getItemStatus().add(itemStatusActive);
//        ItemStatus itemStatusInactive = ItemStatus.valueOf("INACTIVE");
//        searchCriteria.getItemStatus().add(itemStatusInactive);
//
//        // Request status of approved, rejected or pending
//        searchCriteria.setRequestStatus(new ArrayList<RequestItemStatus>());
//        RequestItemStatus requestItemStatusApproved = RequestItemStatus.valueOf("APPROVED");
//        searchCriteria.getRequestStatus().add(requestItemStatusApproved);
//        RequestItemStatus requestItemStatusRejected = RequestItemStatus.valueOf("REJECTED");
//        searchCriteria.getRequestStatus().add(requestItemStatusRejected);
//        RequestItemStatus requestItemStatusPending = RequestItemStatus.valueOf("PENDING");
//        searchCriteria.getRequestStatus().add(requestItemStatusPending);
//
//        // Not only local use
//        searchCriteria.setLocalUse(LocalUseSearchType.ALL_ITEMS);
//
//        // Use the and search
//        searchCriteria.setAdvancedAndSearch(true);
//
//        // add the search terms
//        searchTerms.add(new SearchTermVo(EntityType.NDC, FieldKey.NDC, strNDCNoDashes));
//        searchCriteria.setSearchTerms(searchTerms);
//        searchCriteria.setEntityType(EntityType.NDC);
//        searchCriteria.setSortedFieldKey(FieldKey.NDC);
//
//        try {
//            List<ManagedItemVo> searchResults = managedItemCapability.search(searchCriteria);
//
//            return searchResults;
//        } catch (ValidationException ve) {
//            LOG.error(ve.getMessage());
//        }
//
//        return null;
//    }
//
//    /**
//     * create Item Audit History Detail Record
//     * 
//     * @param colName The column
//     * @param oldValue The replaced value
//     * @param newValue the replacement value
//     * @return itemAuditHistoryDeatialsVo
//     */
//    private ItemAuditHistoryDetailsVo createIAHDetail(String colName, String oldValue, String newValue) {
//
//        ItemAuditHistoryDetailsVo detail = new ItemAuditHistoryDetailsVo();
//        detail.setColumnName(colName);
//        detail.setDetailReason("");
//        detail.setEventCategory(EventCategory.SYSTEM_EVENT);
//        detail.setNewValue(newValue);
//        detail.setOldValue(oldValue);
//        detail.setCreatedBy("Drug Accountability");
//
//        return detail;
//    }
//
//    /**
//     * Search for a product method
//     * 
//     * @param strVUID The string of the VUID to search for and see if it exists
//     * @return ProductVo The Product if it exists and null otherwise
//     */
//    private ProductVo searchForProduct(String strVUID) {
//
//        // get Products by using the vuid's
//        ProductVo productVo = null;
//
//        try {
//            productVo = productDomainCapability.retrieveByVuId(strVUID);
//        } catch (ItemNotFoundException e) {
//
//            // Its OK if the item isn't found. I should go ahead and return the null productVo
//            LOG.debug("SearchForProduct:" + e.toString());
//        }
//
//        return productVo;
//    }
//
//    /**
//     * Search for a manufacturer
//     * 
//     * @param strManufacturer The name of the manufacturer to search for and see if it exists
//     * @return ManufacturerVo The ManufacturerVo if it exists and null otherwise
//     */
//    private ManufacturerVo searchForManufacturer(String strManufacturer) {
//
//        ManufacturerVo manVo = null;
//
//        try {
//            List<ManufacturerVo> manufacturersVo = manufacturerDomainCapability.retrieve();
//
//            for (ManufacturerVo manufacturerVo : manufacturersVo) {
//
//                if (manufacturerVo.getValue().equals(strManufacturer)) {
//                    manVo = manufacturerVo;
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            LOG.debug("searchForManufacturer:" + e.toString());
//        }
//
//        return manVo;
//    }
//
//    /**
//     * Search for the Intended Use VO
//     * 
//     * @param strIntendedUse The name of the Intended use object to search for and see if it exists
//     * @return IntendedUseVo The IntendedUseVo if it exists and null otherwise
//     */
//    private IntendedUseVo searchForIntendedUse(String strIntendedUse) {
//
//        IntendedUseVo useVo = null;
//
//        try {
//            List<IntendedUseVo> intendedUsesVo = intendedUseDomainCapability.retrieveDomain();
//
//            for (IntendedUseVo intendedUseVo : intendedUsesVo) {
//
//                if (intendedUseVo.getValue().startsWith(strIntendedUse)) {
//                    useVo = intendedUseVo;
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            LOG.debug("searchForIntendedUse:" + e.toString());
//        }
//
//        return useVo;
//    }
//
//    /**
//     * Search for an order Unit
//     * 
//     * @param strOrderUnit The name of the order unit to search for and see if it exists
//     * @return OrderUnitVo The OrderUnitVo if it exists and null otherwise
//     */
//    private OrderUnitVo searchForOrderUnit(String strOrderUnit) {
//
//        OrderUnitVo orderUnitVo = null;
//
//        try {
//            List<OrderUnitVo> orderUnitsVo = orderUnitDomainCapability.retrieve();
//
//            for (OrderUnitVo orderUnit : orderUnitsVo) {
//                String str = orderUnit.getAbbrev();
//
//                if (str != null && str.equals(strOrderUnit)) {
//                    orderUnitVo = orderUnit;
//                    break;
//                }
//            }
//        } catch (Exception e) {
//
//            // Its OK if the item isn't found. I should go ahead and return the null
//            // orderUnitVo and deal with it in the calling method
//            LOG.debug("SearchForOrderUnit: " + e.toString());
//        }
//
//        return orderUnitVo;
//    }

    /**
     * setManagedItemCapability
     * @param managedItemCapability managedItemCapability property
     */
    public void setManagedItemCapability(ManagedItemCapability managedItemCapability) {
    }

    /**
     * setProductDomainCapability
     * @param productDomainCapability productDomainCapability property
     */
    public void setProductDomainCapability(ProductDomainCapability productDomainCapability) {
    }

    /**
     * setManufacturerDomainCapability
     * @param manufacturerDomainCapability manufacturerDomainCapability property
     */
    public void setManufacturerDomainCapability(ManufacturerDomainCapability manufacturerDomainCapability) {
    }

    /**
     * setOrderUnitDomainCapability
     * @param orderUnitDomainCapability manufacturerDomainCapability property
     */
    public void setOrderUnitDomainCapability(OrderUnitDomainCapability orderUnitDomainCapability) {
    }

    /**
     * setIntendedUseDomainCapability
     * @param intendedUseDomainCapability IntendedUseDomainCapability property
     */
    public void setIntendedUseDomainCapability(IntendedUseDomainCapability intendedUseDomainCapability) {
    }

    /**
     * setEnvironmentUtility
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
    }

    /**
     * Set capability.
     * 
     * @param drugAccountabilityCapability property
     */
    public void setDrugAccountabilityCapability(DrugAccountabilityCapability drugAccountabilityCapability) {
        this.drugAccountabilityCapability = drugAccountabilityCapability;
    }

//    /**
//     * setDrugAccountabilityService
//     * @param drugAccountabilityService drugAccountability property
//     */
////    public void setDrugAccountabilityService(DrugAccountabilityService drugAccountabilityService) {
////        this.drugAccountabilityService = drugAccountabilityService;
////    }

}
