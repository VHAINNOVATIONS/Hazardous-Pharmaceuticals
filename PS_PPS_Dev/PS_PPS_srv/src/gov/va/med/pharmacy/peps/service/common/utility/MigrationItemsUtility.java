/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationVariablesVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.NdcValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbNdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.fss.outbound.capability.FssInterfaceCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.utility.VistALinkResponseInfo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.MigrationSynchFileCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;


/**
 * This method used to hold some of the complexity in migrating the 3 main item types.
 *
 */
public class MigrationItemsUtility {
    
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MigrationItemsUtility.class);
    private static final String CAUSE_WAS = " The cause was ";
    private static final String INACT_DATE = "InactivationDate";
    private static final String SNULL = "null";
    private static final String ACTIVE_DUP = " is an Active Duplicate.";
    private static final String NOT_NDC = "Could not Migrate the NDC ";
    
    private MigrationDomainsUtility domainsUtility;
    private ManagedItemCapability managedItemCapability;

    private ProductDomainCapability productDomainCapability;

    private DrugReferenceCapability drugReferenceCapability;

    private MigrationSynchFileCapability migrationSynchFileCapability;
    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;
    private FssInterfaceCapability fssInterfaceCapability;
    private NdcDomainCapability ndcDomainCapability;
    private FdbNdcDomainCapability fdbNdcDomainCapability;

    
    /**
     * ComplexStore holds multiple data for the hashmaps
     *
     */
    public class NdcValues {

        private NdcVo ndcVo;
        private boolean migrateNDC;
        private Errors errors;
        private MigrationVariablesVo varVo;

        /**
         * This is the only constructor for this object.
         * 
         * @param ndc NdcVo
         * @param migrate A boolean used to determine if the migration should continue.
         * @param e An errorList for reporting errors.
         * @param var The MigraitonVariablesVo.
         */
        public NdcValues(NdcVo ndc, boolean migrate, Errors e, MigrationVariablesVo var) {
            ndcVo = ndc;
            migrateNDC = migrate;
            errors = e;
            varVo = var;
        }
    };
    
    /**
     * This method is used to merge two products
     * @param csvVo  The product retrieved from the CSV read
     * @param vistaVo The product retrieved from Vista
     * @return the Merged product
     */
    public ProductVo mergeProducts(ProductVo csvVo, ProductVo vistaVo) {

        ProductVo tempVo = csvVo;

        if (tempVo == null) {
            tempVo = (ProductVo) managedItemCapability.retrieveBlankTemplate(EntityType.PRODUCT);
            tempVo.getDrugClasses().clear();
        }

        //tempVo.setSource(ProductDataSourceType.VA); - removed from Product
        tempVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        // copy the Vita values to the newVo
        tempVo.setVaProductName(vistaVo.getVaProductName());
        tempVo.setGenericName(vistaVo.getGenericName());
        tempVo.setMigratedDosageFormName(vistaVo.getMigratedDosageFormName());
        tempVo.setNationalFormularyName(vistaVo.getNationalFormularyName());
        tempVo.setVaPrintName(vistaVo.getVaPrintName());
        tempVo.setProductStrength(vistaVo.getProductStrength());
        tempVo.setProductUnit(vistaVo.getProductUnit());
        tempVo.setCmopId(vistaVo.getCmopId());
        tempVo.setExcludeDrgDrgInteractionCheck(vistaVo.isExcludeDrgDrgInteractionCheck());

        tempVo.setDispenseUnit(vistaVo.getDispenseUnit());
        tempVo.setGcnSequenceNumber(vistaVo.getGcnSequenceNumber());
        tempVo.setActiveIngredients(vistaVo.getActiveIngredients());

        tempVo.setNationalFormularyIndicator(vistaVo.getNationalFormularyIndicator());
        tempVo.setCsFedSchedule(vistaVo.getCsFedSchedule());
        tempVo.setSingleMultiSourceProduct(vistaVo.getSingleMultiSourceProduct());

        if (vistaVo.getItemStatus() == null) {
            tempVo.setItemStatus(ItemStatus.ACTIVE);
        } else {
            if (vistaVo.getItemStatus().equals(ItemStatus.ACTIVE)) {
                tempVo.setItemStatus(ItemStatus.ACTIVE);
            } else {
                tempVo.setItemStatus(ItemStatus.INACTIVE);
                tempVo.setInactivationDate(vistaVo.getInactivationDate());
            }
        }

        tempVo.setOverrideDfDoseChkExclusn(vistaVo.isOverrideDfDoseChkExclusn());
        tempVo.setNdfProductIen(vistaVo.getNdfProductIen());
        tempVo.setPossibleDosagesAutoCreate(vistaVo.getPossibleDosagesAutoCreate());
        tempVo.setProductPackage(vistaVo.getProductPackage());
        tempVo.setMasterEntryForVuid(vistaVo.isMasterEntryForVuid());
        tempVo.setVuid(vistaVo.getVuid());
        tempVo.setEffectiveDates(vistaVo.getEffectiveDates());
        tempVo.setFdaMedGuide(vistaVo.getFdaMedGuide());
        tempVo.setServiceCode(vistaVo.getServiceCode());
        tempVo.setCreatePossibleDosage(vistaVo.getCreatePossibleDosage());
        tempVo.setCmopDispense(vistaVo.getCmopDispense());

        for (DrugClassGroupVo dcgVo : vistaVo.getDrugClasses()) {
            tempVo.getDrugClasses().add(dcgVo);
        }

        return tempVo;
    }
    
    /**
     *  findProduct is used to find a product based on the input vo
     *  This section is necessary to get the product id.  This gets a little complicated.
     *  If (productName valid)
     *      Find Product in EPL
     *      If product found then set product on the NDCVo and break  else continue
     *      If (VUID valid)
     *          search for any products with that VUID
     *          If found then set product to the first one in the list and break else continue
     *      IF (GCNSeqNo Valid)
     *          Search for any products with that GCNSeqNo
     *          if found then set product to the first one in the list and break else continue
     * No products exists for this NDC so return NULL  
     * @param vo the incoming ProductVo
     * @return the populate ProductVo or null if unsuccesfull
     */
    private ProductVo findProduct(ProductVo vo) {

        ComplexStore type = domainsUtility.getProductMap().get(vo.getVaProductName());

        if (type != null) {
            vo.setId(type.getEplId());
            vo.setCategories(type.getCategory());
            vo.setNdfProductIen(Long.valueOf(type.getNdfIen()));

            return vo;
        }

        LOG.debug("PRODUCTLOOKUP error: Could not find product " + vo.getVaProductName()
                  + " in map. Attempting to locate by GCNSeqNo:" + vo.getGcnSequenceNumber());

        if (vo.getGcnSequenceNumber() != null && vo.getGcnSequenceNumber().length() > 0) {

            //Check if Product exists
            SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.ADVANCED, Environment.NATIONAL);
            List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();

            // Item status of inactive and active in MigrationCapablityImpl.findProduct
            searchCriteria.setItemStatus(new ArrayList<ItemStatus>());
            ItemStatus itemStatusActive = ItemStatus.valueOf("ACTIVE");
            ItemStatus itemStatusInactive = ItemStatus.valueOf("INACTIVE");
            searchCriteria.getItemStatus().add(itemStatusActive);
            searchCriteria.getItemStatus().add(itemStatusInactive);

            // Request status of approved, rejected or pending in MigrationCapablityImpl.findProduct
            searchCriteria.setRequestStatus(new ArrayList<RequestItemStatus>());
            RequestItemStatus requestItemStatusApproved = RequestItemStatus.valueOf("APPROVED");
            RequestItemStatus requestItemStatusRejected = RequestItemStatus.valueOf("REJECTED");
            RequestItemStatus requestItemStatusPending = RequestItemStatus.valueOf("PENDING");
            searchCriteria.getRequestStatus().add(requestItemStatusRejected);
            searchCriteria.getRequestStatus().add(requestItemStatusApproved);
            searchCriteria.getRequestStatus().add(requestItemStatusPending);

            // Not only local use in MigrationCapablityImpl.findProduct
            searchCriteria.setLocalUse(LocalUseSearchType.ALL_ITEMS);

            // Use the and search in MigrationCapablityImpl.findProduct
            searchCriteria.setAdvancedAndSearch(true);

            // add the search terms in MigrationCapablityImpl.findProduct
            searchTerms.add(new SearchTermVo(EntityType.PRODUCT, FieldKey.GCN_SEQUENCE_NUMBER, vo.getGcnSequenceNumber()));
            searchCriteria.setSearchTerms(searchTerms);
            searchCriteria.setEntityType(EntityType.PRODUCT);
            searchCriteria.setSortedFieldKey(FieldKey.PRODUCT);

            try {
                List<ManagedItemVo> searchResults = managedItemCapability.search(searchCriteria);

                if (searchResults.size() > 0) {
                    return productDomainCapability.retrieve(searchResults.get(0).getId());
                }
            } catch (Exception e) {
                LOG.debug("MigrateNDC: FindProduct " + e.getMessage());
            }
        }

        return null;
    }

    /**
     * updateSynonym.
     * @param ndcVo ndcVo
     */
    private void updateSynonym(NdcVo ndcVo) {

        try {
            Collection<SynonymVo> synonyms = productDomainCapability.retrieveSynonyms(ndcVo.getProduct().getId());

            if (synonyms.size() > 0) {
                for (SynonymVo synonymVo : synonyms) {
                    if (synonymVo.getSynonymName().equals(ndcVo.getTradeName())) {
                        return;
                    }
                }
            }

            // Set synonym's properties
            SynonymVo synonymVo = new SynonymVo();

            synonymVo.setSynonymName(ndcVo.getTradeName());
            String id = domainsUtility.getIntendedUseMap().get("0-TRADE NAME");
            IntendedUseVo useVo = new IntendedUseVo();
            useVo.setId(id);
            synonymVo.setSynonymIntendedUse(useVo);
            synonymVo.setNdcCode(ndcVo.getNdc());
            synonymVo.setSynonymOrderUnit(ndcVo.getOrderUnit());
            synonymVo.setSynonymPricePerOrderUnit(0.0);
            synonymVo.setSynonymPricePerDispenseUnit(0.0);
            synonymVo.setSynonymDispenseUnitPerOrderUnit(ndcVo.getNdcDispUnitsPerOrdUnit());
            synonymVo.setSynonymVendor(ndcVo.getVendor());
            synonymVo.setSynonymVsn(ndcVo.getVendorStockNumber());

            productDomainCapability.addSingleSynonym(synonymVo, ndcVo.getProduct(), domainsUtility.getUser());
        } catch (Exception e) {
            String str = "Error occured updating the trade name synonym " + ndcVo.getTradeName();
            LOG.debug(str);
        }

    }
    
    /**
     * Migrate the NDC Items
     * @param ndcVoIn : The list of products to migrate
     * @return The MigrationVariablesVo containing the migration results  
     */
    public MigrationVariablesVo migrateNDCs(NdcVo ndcVoIn) {

        NdcVo ndcVo = ndcVoIn;
        MigrationVariablesVo varVo = new MigrationVariablesVo();

        // Create the validator and error list
   
        Errors errors = new Errors();

        boolean migrateNDC = true;
        ndcVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        
        NdcValues ndcValues = new NdcValues(ndcVo, migrateNDC, errors, varVo);
        ndcValues = migrateNDC2(ndcValues);
        ndcValues = migrateNDC3(ndcValues);
        ndcValues = migrateNDC4(ndcValues);

        ndcVo = ndcValues.ndcVo;
        migrateNDC = ndcValues.migrateNDC;
        errors = ndcValues.errors;
        varVo = ndcValues.varVo;

        // set the fssData
        if (migrateNDC) {
            try {
                fssInterfaceCapability.getFssData(ndcVo);
            } catch (Exception e) {
                LOG.debug("Exception getting FSS data for " + ndcVo.getNdc() + ". Exception is " + e.getMessage());
            }
        }

        if (migrateNDC) {

            // Check for Duplicates
            try {
                if (domainsUtility.getNdcMap().get(ndcVo.getNdc()) != null) {

                    // Active Duplicates are errors
                    if (ndcVo.getInactivationDate() == null) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        String str = "This NDC " + ndcVo.getNdc() + ACTIVE_DUP;
                        domainsUtility.saveMigrationErrorMessage(
                            domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", INACT_DATE, SNULL, ndcVo.getNdc(), str));
                        migrateNDC = false;
                    } else {
                        varVo.setIDuplicatesNotMigrated(varVo.getIDuplicatesNotMigrated() + 1);
                        LOG.error("Duplicate NDC: " + ndcVo.getNdc() + " was not migrated.");
                        migrateNDC = false;
                    }
                } 
            } catch (Exception e) {
                migrateNDC = false;
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                StringBuffer sb = new StringBuffer("The NationalDrugCode ");
                sb.append(ndcVo.getNdc()).append(":").append(ndcVo.getUpcUpn())
                    .append(" could not be successfully sent to VistA because: ").append(e.getMessage());

                if (e.getCause() != null) {
                    sb.append(CAUSE_WAS).append(e.getCause().getMessage());
                }
            }

        }

        // Send NDC to VistA and load the NDC to the database
        if (migrateNDC) {

            // Send to VistA and get back IENs
            try {
                VistALinkResponseInfo rsp = migrationSynchFileCapability.sendItemToVista(ndcVo);

                if (rsp.isError()) {
                    String str =
                        "An error occured processing the NDC in NDFMS. The error was " + rsp.getErrorResponseString();
                    domainsUtility.saveMigrationErrorMessage(
                        domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "", "", ndcVo.getNdc(), str));
                    varVo.setIErroredOnMigration(varVo.getIErroredOnMigration() + 1);
                    migrateNDC = false;
                } else {

                    if (rsp.isIen()) { // Success
                        ndcVo.setNdcIen(rsp.getIen());
                    } else {
                        String str = "An error occurred - No IEN was returned for the NDC: " + ndcVo.getNdc();
                        domainsUtility.saveMigrationErrorMessage(
                            domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "", "", ndcVo.getNdc(), str));
                        varVo.setIErroredOnMigration(varVo.getIErroredOnMigration() + 1);
                        migrateNDC = false;
                    }
                }
            } catch (Exception e) {
                migrateNDC = false;
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                StringBuffer sb = new StringBuffer("The National Drug Code ");
                sb.append(ndcVo.getNdc()).append(":").append(ndcVo.getUpcUpn())
                .append(" could not be sent to VistA because: ").append(e.getMessage());

                if (e.getCause() != null) {
                    sb.append(CAUSE_WAS).append(e.getCause().getMessage());
                }
            }
        }
        
        if (migrateNDC) {

            try {

                // Save the NDC, including the FDB values if this item has a gcnseqNo and put in the Map.
                ndcVo = ndcDomainCapability.create(ndcVo, domainsUtility.getUser());
                domainsUtility.createItemAuditHistory(EntityType.NDC, ndcVo.getId());

                try {
                    if (ndcVo.getFdbNdcVo() != null) {
                        ndcVo.getFdbNdcVo().setNdcIdFk(Long.valueOf(ndcVo.getId()));
                        fdbNdcDomainCapability.create(ndcVo.getFdbNdcVo(), domainsUtility.getUser());
                    }
                } catch (Exception e) {
                    LOG.error("Could not add the FDB NDC part of " + ndcVo.getNdc() + " during migration.");
                }

                // Put the NDC in the map
                domainsUtility.getNdcMap().put(ndcVo.getNdc(), ndcVo.getId());
                varVo.setISuccessfullyMigrated(varVo.getISuccessfullyMigrated() + 1);

            } catch (Exception e) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                StringBuffer sb = new StringBuffer("The NDC ");
                sb.append(ndcVo.getNdc()).append(":").append(ndcVo.getUpcUpn())
                    .append(" could not be inserted into the database because: ").append(e.getMessage());

                if (e.getCause() != null) {
                    sb.append(CAUSE_WAS).append(e.getCause().getMessage());
                }

                LOG.error(sb);
                domainsUtility.saveMigrationErrorMessage(
                    domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "", "", ndcVo.getNdc(), sb.toString()));
            }

            updateSynonym(ndcVo);

        }

        return varVo;
    }
    
    /**
     * Migrate the NDC Items
     * @param ndcValues : The list of products to migrate
     * @return The NdcValues containing the migration results  
     */
    private NdcValues migrateNDC2(NdcValues ndcValues) {
    
        NdcVo ndcVo = ndcValues.ndcVo;
        Errors errors = ndcValues.errors;
        MigrationVariablesVo varVo = ndcValues.varVo;
        boolean migrateNDC = ndcValues.migrateNDC;
        NdcValidator validator = new NdcValidator();
        

        //      Need to remove following line when Not null constraint is removed.
        //      ndcVo.setUpcUpn(ndcVo.getNdc());

        // Set the product
        if (migrateNDC) {
            ProductVo productVo = findProduct(ndcVo.getProduct());

            if (productVo == null) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                StringBuffer sb = new StringBuffer(NOT_NDC);
                sb.append(ndcVo.getNdc()).append(" because the Product is not in the PPS-N EPL. VA Product Name is '")
                    .append(ndcVo.getProduct().getVaProductName()).append("'. GCNSeqNum is '")
                    .append(ndcVo.getProduct().getGcnSequenceNumber()).append("'.");
                domainsUtility.saveMigrationErrorMessage(
                    domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "", "", ndcVo.getNdc(), sb.toString()));

                migrateNDC = false;
            } else {
                ndcVo.setProduct(productVo);
                ndcVo.setCategories(productVo.getCategories());
                ndcVo.setSubCategories(productVo.getSubCategories());
            }
        }
        
        if (migrateNDC) {
            validator.validate(ndcVo, domainsUtility.getUser(), Environment.NATIONAL, errors);

            if (errors.hasErrors()) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                List<ValidationError> errorList = errors.getErrors();
                StringBuffer sb = new StringBuffer("NDC Validation Error: ");

                for (ValidationError e : errorList) {
                    sb.append(e.getLocalizedError()).append(" ");
                }

                domainsUtility.saveMigrationErrorMessage(
                    domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "", "", ndcVo.getNdc(), sb.toString()));

                migrateNDC = false;
            }
        }
        
        if (migrateNDC) {
            try {
                ndcVo = drugReferenceCapability.populateFdbNdcFields(ndcVo);

                if (ndcVo.getFdbNdcVo() != null) {
                    ndcVo.getManufacturer().setValue(ndcVo.getFdbNdcVo().getLabelerName());
                    ndcVo.getPackageType().setValue(ndcVo.getFdbNdcVo().getPackageDescription());
                    ndcVo.setPackageSize(ndcVo.getFdbNdcVo().getPackageSize());
                    ndcVo.setTradeName(ndcVo.getFdbNdcVo().getTradeName());
                }
            } catch (Exception e) {
                String str = "drugReferenceCapability.PopulateFdbNdcFilds threw an exception. e = " + e.getMessage();

                LOG.debug(str);
                StackTraceElement[] ste = e.getStackTrace();

                for (StackTraceElement ee : ste) {
                    LOG.debug(ee.toString());
                }

                migrateNDC = false;
            }
        }
        
        if (migrateNDC) {

            if (ndcVo.getOrderUnit() != null) {
                String orderUnitId = domainsUtility.getOrderUnitMap().get(ndcVo.getOrderUnit().getAbbrev());

                if (orderUnitId == null) {
                    varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                    String str =
                        NOT_NDC + ndcVo.getNdc() + " due to a problem Retrieving the Order Unit of "
                            + ndcVo.getOrderUnit().getAbbrev() + ".";
                    domainsUtility.saveMigrationErrorMessage(
                        domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "OrderUnit", ndcVo.getOrderUnit()
                            .getAbbrev(), ndcVo.getNdc(), str));
                    migrateNDC = false;
                } else {
                    ndcVo.getOrderUnit().setId(orderUnitId);
                }
            }
        }
        
        NdcValues newNdcValues = new NdcValues(ndcVo, migrateNDC, errors, varVo);
        
        return newNdcValues;
    }
    
    /**
     * Migrate the NDC Items
     * @param ndcValues : The list of products to migrate
     * @return The NdcValues containing the migration results  
     */
    private NdcValues migrateNDC3(NdcValues ndcValues) {
    
        NdcVo ndcVo = ndcValues.ndcVo;
        Errors errors = ndcValues.errors;
        MigrationVariablesVo varVo = ndcValues.varVo;
        boolean migrateNDC = ndcValues.migrateNDC;

        // set the manufacturer
        if (migrateNDC) {
            if (ndcVo.getManufacturer() == null) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                String str =
                        NOT_NDC + ndcVo.getNdc() + " due to the manufacturer not being in the Value Object. ";
                domainsUtility.saveMigrationErrorMessage(
                    domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "Drug Manufacturer", "", ndcVo.getNdc(), str));
                migrateNDC = false;
            } else {
                ComplexIENStore manufacturerStore = 
                    domainsUtility.getManufacturerMap().get(ndcVo.getManufacturer().getValue());

                if (manufacturerStore == null) {

                    ndcVo.getManufacturer().setItemStatus(ItemStatus.ACTIVE);
                    ndcVo.getManufacturer().setRequestItemStatus(RequestItemStatus.APPROVED);

                    //For New Manufacturers, send the Manufacturer to VistA
                    try {
                        VistALinkResponseInfo rsp =
                                migrationSynchFileCapability.sendManufacturerToVista(ndcVo.getManufacturer());

                        if (rsp.isError()) {
                            String str =
                                    "An error occured processing the Manufacturer for the NDC in NDFMS! The error was "
                                            + rsp.getErrorResponseString();
                            domainsUtility.saveMigrationErrorMessage(
                                domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "", "", ndcVo.getNdc(), str));
                            varVo.setIErroredOnMigration(varVo.getIErroredOnMigration() + 1);
                        }

                        if (rsp.isIen()) {
                            ndcVo.getManufacturer().setManufacturerIen(rsp.getIen());
                        }
                    } catch (Exception e) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        StringBuffer sb = new StringBuffer("An exception occurred while sending the Manufacturer ");
                        sb.append(ndcVo.getManufacturer().getValue()).append(" For the NDC: ").append(ndcVo.getNdc())
                            .append(" The message returned is: ").append(e.getMessage());
                        LOG.error(sb);
                        domainsUtility.saveMigrationErrorMessage(
                            domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "", "", ndcVo.getNdc(),
                                   sb.toString()));
                    }

                    // For each of the New Manufacturers, put the manufacturer in EPL
                    try {
                        ndcVo.setManufacturer(manufacturerDomainCapability.create(ndcVo.getManufacturer(), 
                            domainsUtility.getUser()));
                        domainsUtility.createItemAuditHistory(EntityType.MANUFACTURER, ndcVo.getManufacturer().getId());
                        varVo.setINumManufacturersMigrated((varVo.getINumManufacturersMigrated() + 1));

                        ComplexIENStore store = 
                            new ComplexIENStore(ndcVo.getManufacturer().getId(), 
                                ndcVo.getManufacturer().getManufacturerIen());
                        domainsUtility.getManufacturerMap().put(ndcVo.getManufacturer().getValue(), store);
                        
                    } catch (Exception e) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        LOG.error("Create Manufacturer Exception in migrate NDC: " + e.getMessage());
                        StackTraceElement[] ste = e.getStackTrace();

                        for (StackTraceElement ee : ste) {
                            LOG.error(ee.toString());
                        }

                        String str =
                               NOT_NDC + ndcVo.getNdc() + " due to a problem createing the Manufacturer of "
                                        + ndcVo.getManufacturer().getValue() + ":  Exception is " + e.getMessage();
                        domainsUtility.saveMigrationErrorMessage(
                            domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "Manufacturer", ndcVo
                                .getManufacturer().getValue(), ndcVo.getNdc(), str));
                        migrateNDC = false;
                    }
                } else {
                    ndcVo.getManufacturer().setId(manufacturerStore.getEplId());
                    ndcVo.getManufacturer().setManufacturerIen(manufacturerStore.getNdfIen());
                }
            }
        }
        
        NdcValues ndcValues2 = new NdcValues(ndcVo, migrateNDC, errors, varVo);
        
        return ndcValues2;
    }
    
    /**
     * Migrate the NDC Items
     * @param ndcValues : The list of products to migrate
     * @return The NdcValues containing the migration results  
     */
    private NdcValues migrateNDC4(NdcValues ndcValues) {
        NdcVo ndcVo = ndcValues.ndcVo;
        Errors errors = ndcValues.errors;
        MigrationVariablesVo varVo = ndcValues.varVo;
        boolean migrateNDC = ndcValues.migrateNDC;
    
     // set the PackageType
        if (migrateNDC) {
            if (ndcVo.getPackageType() == null) {
                varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                String str =
                    "Could not Migrate NDC " + ndcVo.getNdc() + " due to the PackageType not being in the Value Object. ";
                domainsUtility.saveMigrationErrorMessage(
                    domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "PackageType", "", ndcVo.getNdc(), str));
                migrateNDC = false;
            } else {
                
                ComplexIENStore packageTypeStore = domainsUtility.getPackageTypeMap().get(ndcVo.getPackageType().getValue());

                if (packageTypeStore == null) {
                    ndcVo.getPackageType().setItemStatus(ItemStatus.ACTIVE);
                    ndcVo.getPackageType().setRequestItemStatus(RequestItemStatus.APPROVED);

                    //For New Package Types, send the Package Type to VistA
                    try {
                        VistALinkResponseInfo rsp = migrationSynchFileCapability.sendPackageTypeToVista(ndcVo.getPackageType());

                        if (rsp.isError()) {
                            String str =
                                "An error occured processing the Package Type for the NDC in NDFMS." + " The error was "
                                    + rsp.getErrorResponseString();
                            domainsUtility.saveMigrationErrorMessage(
                                domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "", "", ndcVo.getNdc(), str));
                            varVo.setIErroredOnMigration(varVo.getIErroredOnMigration() + 1);
                        }

                        if (rsp.isIen()) {
                            ndcVo.getPackageType().setPackagetypeIen(rsp.getIen());
                        }
                    } catch (Exception e) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        StringBuffer sb = new StringBuffer("An exception occurred while sending the Package Type ");
                        sb.append(ndcVo.getPackageType().getValue()).append(" For the NDC ").append(ndcVo.getNdc())
                            .append(" The message returned was: ").append(e.getMessage());

                        if (e.getCause() != null) {
                            sb.append(CAUSE_WAS).append(e.getCause().getMessage());
                        }

                        LOG.error(sb);
                        domainsUtility.saveMigrationErrorMessage(
                            domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "", "", ndcVo.getNdc(),
                                sb.toString()));
                    }

                    // Add the new Package type to the EPL
                    try {
                        ndcVo.setPackageType(packageTypeDomainCapability.create(ndcVo.getPackageType(), 
                            domainsUtility.getUser()));
                        domainsUtility.createItemAuditHistory(EntityType.PACKAGE_TYPE, ndcVo.getPackageType().getId());
                        varVo.setINumPackageTypesMigrated(varVo.getINumPackageTypesMigrated() + 1);
                        domainsUtility.getPackageTypeMap().put(ndcVo.getPackageType().getValue(), 
                            new ComplexIENStore(ndcVo.getPackageType().getId(), ndcVo.getPackageType().getPackagetypeIen()));
                    } catch (Exception e) {
                        varVo.setIErroredOnMigration((varVo.getIErroredOnMigration() + 1));
                        LOG.error("Create PackageType Exception in migrate NDC: " + e.getMessage());
                        StackTraceElement[] ste = e.getStackTrace();

                        for (StackTraceElement ee : ste) {
                            LOG.error(ee.toString());
                        }

                        String str =
                            NOT_NDC + ndcVo.getNdc() + " due to a problem creating the PackageType of "
                                + ndcVo.getPackageType().getValue() + ": Excpetion is " + e.getMessage();
                        domainsUtility.saveMigrationErrorMessage(
                            domainsUtility.createErrorVo(PPSConstants.VA_NDC_FILE, "", "Package Type", ndcVo
                                .getPackageType().getValue(), ndcVo.getNdc(), str));
                        migrateNDC = false;
                    }
                } else {
                    ndcVo.getPackageType().setId(packageTypeStore.getEplId());
                    ndcVo.getPackageType().setPackagetypeIen(packageTypeStore.getNdfIen());
                }
            }
        }
        
        NdcValues ndcValues2 = new NdcValues(ndcVo, migrateNDC, errors, varVo);
    
        return ndcValues2;
    }
    
    

    /**
     * setMigrationErrorDomainCapability.
     * @param migrationDomainsUtility property
     */
    public void setMigrationDomainsUtility(MigrationDomainsUtility migrationDomainsUtility) {
        domainsUtility = migrationDomainsUtility;
    }
    
    /**
     * setManagedItemCapability
     * @param managedItemCapability property
     */
    public void setManagedItemCapability(ManagedItemCapability managedItemCapability) {
        this.managedItemCapability = managedItemCapability;
    }
    
    /**
     * setProductDomainCapability
     * @param productDomainCapability property
     */
    public void setProductDomainCapability(ProductDomainCapability productDomainCapability) {
        this.productDomainCapability = productDomainCapability;
    }

    /**
     * setDrugReferenceCapability.
     * @param drugReferenceCapability property
     */
    public void setDrugReferenceCapability(DrugReferenceCapability drugReferenceCapability) {
        this.drugReferenceCapability = drugReferenceCapability;
    }
    
    /**
     * setMigrationSynchFileCapability.
     * @param migrationSynchFileCapability property
     */
    public void setMigrationSynchFileCapability(MigrationSynchFileCapability migrationSynchFileCapability) {
        this.migrationSynchFileCapability = migrationSynchFileCapability;
    }
    
    /**
     * setManufacturerDomainCapability.
     * @param manufacturerDomainCapability property
     */
    public void setManufacturerDomainCapability(ManufacturerDomainCapability manufacturerDomainCapability) {
        this.manufacturerDomainCapability = manufacturerDomainCapability;
    }
    
    /**
     * setPackageTypeDomainCapability.
     * @param packageTypeDomainCapability property
     */
    public void setPackageTypeDomainCapability(PackageTypeDomainCapability packageTypeDomainCapability) {
        this.packageTypeDomainCapability = packageTypeDomainCapability;
    }
    
    /**
     * setFssInterfaceCapability.
     * @param fssInterfaceCapability property
     */
    public void setFssInterfaceCapability(FssInterfaceCapability fssInterfaceCapability) {
        this.fssInterfaceCapability = fssInterfaceCapability;
    }
    
    /**
     * setNdcDomainCapability.
     * @param ndcDomainCapability property
     */
    public void setNdcDomainCapability(NdcDomainCapability ndcDomainCapability) {
        this.ndcDomainCapability = ndcDomainCapability;
    }
    
    /**
     * setFdbNdcDomainCapability.
     * @param fdbNdcDomainCapability property
     */
    public void setFdbNdcDomainCapability(FdbNdcDomainCapability fdbNdcDomainCapability) {
        this.fdbNdcDomainCapability = fdbNdcDomainCapability;
    }
}
