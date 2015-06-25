/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ColorVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionResultVo;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.FdbUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.NdcSourceType;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.NdfSynchQueueVo;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.ShapeVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAddDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAutoAddDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAutoUpdateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbNdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbUpdateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdfSynchQueueDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceAutoCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.fss.outbound.capability.FssInterfaceCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaFileSynchCapability;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.service.common.capability.FdbUpdateProcessCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.utility.MigrationItemsUtility;

import firstdatabank.database.FDBException;


/**
 * FdbUpdateProcessCapabilityImpl.
 */
public class FdbUpdateProcessCapabilityImpl implements FdbUpdateProcessCapability {

    /**
     * PRODUCT TYPE
     */
    public static final String PRODUCT = "PRODUCT";

    /**
     * NDC TYPE
     */
    public static final String NDC = "NDC";

    /**
     *  CSV DELIMITER
     */
    public static final String CSV_DELIMITER = "^";

    /**
     * FDB SEARCH RESULT HEADER
     */
    public static final String FDB_SEARCH_RESULT_HEADER = "Ndc" + CSV_DELIMITER + "NdcFormatCode" + CSV_DELIMITER + "BrandName"
        + CSV_DELIMITER + "ManuDistrib" + CSV_DELIMITER + "LabelName" + CSV_DELIMITER + "LabelName25" + CSV_DELIMITER
        + "GenericName" + CSV_DELIMITER + "DrugStrengthDescription" + CSV_DELIMITER + "DoseFormDescription" + CSV_DELIMITER
        + "UnitDoseIndicator" + CSV_DELIMITER + "GCNSeqNo" + CSV_DELIMITER + "DrugFormCode" + CSV_DELIMITER + "DrugClass"
        + CSV_DELIMITER + "DuplicateTherapyClasses" + CSV_DELIMITER + "Color" + CSV_DELIMITER + "Shape" + CSV_DELIMITER
        + "Flavor" + CSV_DELIMITER + "Top200Rank" + CSV_DELIMITER + "Top50GenRank" + CSV_DELIMITER + "ReplacementNDC"
        + CSV_DELIMITER + "PreviousNDC" + CSV_DELIMITER + "ObsoleteDate" + CSV_DELIMITER + "PackageSize" + CSV_DELIMITER
        + "PackageDescription" + CSV_DELIMITER + "AdditionalDescription" + CSV_DELIMITER + "DEACode" + CSV_DELIMITER
        + "OTC/RXIndicator" + CSV_DELIMITER;

    /**
     * FDB ADD RESULT HEADER
     */
    public static final String FDB_ADD_RESULT_HEADER = "Ndc" + CSV_DELIMITER + "GCNSEQNO" + CSV_DELIMITER + "Package Size"
        + CSV_DELIMITER + "Package Type" + CSV_DELIMITER + "Manufacturer" + CSV_DELIMITER + "Fdb Generic Name" + CSV_DELIMITER
        + "Label Name" + CSV_DELIMITER + "Additional Description" + CSV_DELIMITER + "Creation Date";

    /**
     * FDB UPDATE RESULT HEADER
     */
    private static final Object FDB_UPDATE_RESULT_HEADER = "NDC" + CSV_DELIMITER + "VA Product Name" + CSV_DELIMITER
        + "Fdb Generic Name" + CSV_DELIMITER + "GCNSEQNO" + CSV_DELIMITER + "RX Message" + CSV_DELIMITER + "Date Submitted";

    /**
     * FDB ADD REPORT HEADER
     */
    private static final Object FDB_ADD_REPORT_HEADER = "Ndc" + CSV_DELIMITER + "Package Size" + CSV_DELIMITER + "Package Type"
        + CSV_DELIMITER + "Additional Description" + CSV_DELIMITER + "GCNSEQNO" + CSV_DELIMITER + "VA Product Name"
        + CSV_DELIMITER + "Label Name" + CSV_DELIMITER + "Fdb Generic Name" + CSV_DELIMITER + "Creation Date";

    /**
     * FDB UPDATE REPORT HEADER
     */
    private static final Object FDB_UPDATE_REPORT_HEADER = "NDC" + CSV_DELIMITER + "VA Product Name" + CSV_DELIMITER
        + "Fdb Generic Name" + CSV_DELIMITER + "GCNSEQNO" + CSV_DELIMITER + "RX Message" + CSV_DELIMITER + "Creation Date";

    private static final Logger LOG = Logger.getLogger(FdbUpdateProcessCapabilityImpl.class);

    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;
    private OrderUnitDomainCapability orderUnitDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private DrugReferenceCapability drugReferenceCapability;
    private DrugReferenceAutoCapability drugReferenceAutoCapability;
    private FdbAddDomainCapability fdbAddDomainCapability;
    private FdbUpdateDomainCapability fdbUpdateDomainCapability;
    private FdbAutoUpdateDomainCapability fdbAutoUpdateDomainCapability;
    private FdbAutoAddDomainCapability fdbAutoAddDomainCapability;
    private NdcDomainCapability ndcDomainCapability;
    private VistaFileSynchCapability vistaFileSynchCapability;
    private NationalSettingDomainCapability nationalSettingDomainCapability;
    private FdbNdcDomainCapability fdbNdcDomainCapability;
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private ProductDomainCapability productDomainCapability;
    private FssInterfaceCapability fssInterfaceCapability;
    private NdfSynchQueueDomainCapability ndfSynchQueueDomainCapability;

    private RequestDomainCapability requestDomainCapability;

    /**
     * Add selected NDCs from Matching page to selected Product.
     * 
     * @param pUser user Vo
     * @param pNdcsNumbers NDC ids
     * @param pProductId Sequence Numbers
     * @return ProcessedItemVo
     * @throws ValidationException 
     */
    @Override
    public ProcessedItemVo addProductsToNdcs(UserVo pUser, String[] pNdcsNumbers, String pProductId)
        throws ValidationException {

        ProductVo product = (ProductVo) managedItemCapability.retrieve(pProductId, EntityType.PRODUCT);
        Errors errors = new Errors();
        ProcessedItemVo processedItem = new ProcessedItemVo();
        long beginTime = System.currentTimeMillis();
        
        for (String ndcNo : pNdcsNumbers) {
            long startTime = System.currentTimeMillis();
            LOG.debug((System.currentTimeMillis() - startTime) + ":Add NDC: " + ndcNo);
            NdcVo ndcVo = createNdcFromFdb(ndcNo, pUser);
            LOG.debug((System.currentTimeMillis() - startTime) + "After createNdcFromFdb: " + ndcNo);
        
            if (ndcVo == null) {
                continue;
            }

            ndcVo.setProduct(product);
            ndcVo.setCategories(product.getCategories());
            RequestVo request = null;
            

            
            fssInterfaceCapability.getFssData(ndcVo);
            ndcVo.setItemStatus(product.getItemStatus());
            ndcVo = ndcDomainCapability.create(ndcVo, pUser);
            ndcVo.getFdbNdcVo().setNdcIdFk(Long.valueOf(ndcVo.getId()));

            fdbNdcDomainCapability.create(ndcVo.getFdbNdcVo(), pUser);
            
            
            if (RequestItemStatus.APPROVED.equals(product.getRequestItemStatus())) {
                
                if (vistaFileSynchCapability.sendNewItemToVista(ndcVo, pUser,  okToSendToVista(), catchingUp())) {
                    addToQueue(ndcVo, ndcVo.getEntityType(), ItemAction.ADD, pUser);
                    LOG.debug((System.currentTimeMillis() - startTime) + "Item added to queue: " + ndcNo);
                } else {
                    LOG.debug((System.currentTimeMillis() - startTime) 
                        + "vistaFileSynchCapability.sendNewItemToVista: " + ndcNo);
                }
            } else {
                ndcVo.setRequestItemStatus(RequestItemStatus.PENDING);
                request =
                    new RequestVo(ndcVo.getId(), ndcVo.getEntityType(), RequestType.ADDITION,
                                  RequestState.PENDING_SECOND_REVIEW, pUser, pUser, RequestItemStatus.APPROVED, false, false,
                                  null);
                requestDomainCapability.create(request, pUser);
              
            }
            
            ndcDomainCapability.update(ndcVo, pUser);
            
            Collection<SynonymVo> synonyms = productDomainCapability.retrieveSynonyms(ndcVo.getProduct().getId());
            boolean updateSynonym = true;
            
            if (synonyms.size() > 0) {
                for (SynonymVo synonymVo : synonyms) {
                    if (synonymVo.getSynonymName().equals(ndcVo.getTradeName())) {
                        updateSynonym = false;
                        break;
                    }
                }
            }

            if (updateSynonym) {
                SynonymVo synonymVo = new SynonymVo();
                synonymVo.setSynonymName(ndcVo.getTradeName());
                IntendedUseVo useVo = new IntendedUseVo();
                useVo.setId("9991");  // this is the id of Trade Name.
                synonymVo.setSynonymIntendedUse(useVo);
                synonymVo.setNdcCode(ndcVo.getNdc());
                synonymVo.setSynonymOrderUnit(ndcVo.getOrderUnit());
                synonymVo.setSynonymPricePerOrderUnit(0.0);
                synonymVo.setSynonymPricePerDispenseUnit(0.0);
                synonymVo.setSynonymDispenseUnitPerOrderUnit(ndcVo.getNdcDispUnitsPerOrdUnit());
                synonymVo.setSynonymVendor(ndcVo.getVendor());
                synonymVo.setSynonymVsn(ndcVo.getVendorStockNumber());
    
                productDomainCapability.addSingleSynonym(synonymVo, ndcVo.getProduct(), pUser);
            }
            
            LOG.debug((System.currentTimeMillis() - startTime) + "After updateSynonym: " + updateSynonym);
            
            ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
            audit.setAuditItemId(ndcVo.getId());
            audit.setEventCategory(EventCategory.NATIONAL_MODIFICATION);
            audit.setDetailCategory(EventCategory.NATIONAL_MODIFICATION);
            audit.setReason("Added via FDB Match Process.");
            audit.setAuditItemType(EntityType.NDC);
            audit.setSiteName("National");
            audit.setUsername(pUser.getUsername());
            audit.setCreatedBy(pUser.getUsername());
            itemAuditHistoryDomainCapability.create(audit, null, pUser);
            LOG.debug((System.currentTimeMillis() - startTime) + "After itemAuditHistoryDomainCapability.create: " + ndcNo);
            
         //   processedItem = managedItemCapability.create(ndcVo, pUser);
         //   LOG.debug((System.currentTimeMillis() - startTime) + "After managedItemCapability.create: " + ndcNo);
         //   addErrorCheckForDuplicate(errors, processedItem.getWarnings());
        }
        
        LOG.debug("Total Time is " + (System.currentTimeMillis() - beginTime) + " or " 
            + (System.currentTimeMillis() - beginTime) / pNdcsNumbers.length);

        processedItem.setWarnings(errors);

        return processedItem;

    }

    /**
     * addErrorCheckForDuplicate
     * @param master master
     * @param newErrors newErrors
     */
    private void addErrorCheckForDuplicate(Errors master, Errors newErrors) {
        List<ValidationError> list = master.getErrors();
        List<ValidationError> newList = newErrors.getErrors();

        for (int i = 0; i < newList.size(); i++) {
            boolean addMessage = true;

            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getLocalizedError().equalsIgnoreCase(newList.get(i).getLocalizedError())) {
                    addMessage = false;
                }
            }

            if (addMessage) {
                master.addError(newList.get(i));
            }
        }
    }

    /**
     * returns a ManagedItem.
     *
     * @param pId id of managed item
     * @param pEntityType managed Item type
     * @return Managed Item
     */
    @Override
    public ManagedItemVo retrieveManagedItem(String pId, EntityType pEntityType) {

        try {
            return managedItemCapability.retrieve(pId, pEntityType);
        } catch (ItemNotFoundException e) {

            LOG.error("Error  ItemNotFoundException! " + e.getMessage());
        }

        return null;

    }

    /**
     * Creates a blank template Product.
     *
     * @param pUser - user
     * @param pNdcNumbers ndcs 
     * @return returns a productVo with selected Ndcs
     */
    @Override
    public ProductVo createBlankTemplate(UserVo pUser, List<String> pNdcNumbers) {

        List<NdcVo> ndcs = new ArrayList<NdcVo>();
        String gcnSeqNo = "";

        for (String ndcNo : pNdcNumbers) {
            NdcVo ndcVo = createNdcFromFdb(ndcNo, pUser);

            if (ndcVo == null) {
                continue;
            } else {
                if (ndcVo.getFdbNdcVo().getGcnSeqno() != null) {
                    gcnSeqNo = ndcVo.getFdbNdcVo().getGcnSeqno().toString();
                }
            }

            ndcs.add(ndcVo);
        }

        ProductVo productVo = (ProductVo) managedItemCapability.retrieveBlankTemplate(EntityType.PRODUCT);
        productVo.setNdcs(ndcs);

        // set the GcnSeqNo add leading zeros if less than 6 digits
        String target = gcnSeqNo;
        target = StringUtils.leftPad(target, PPSConstants.I6, '0');
        productVo.setGcnSequenceNumber(target);

        return productVo;
    }

    /**
     * Creates a Product from existing.
     *
     * @param pUser - user
     * @param pNdcsNumbers - ndcs that were selected
     * @param pItemId - the product that was selected
     * @return returns a productVo from selected Ndc
     */
    @Override
    public ProductVo createFromExisting(UserVo pUser, String[] pNdcsNumbers, String pItemId) {

        List<NdcVo> ndcs = new ArrayList<NdcVo>();
        ProductVo product = null;

        try {
            product = (ProductVo) managedItemCapability.retrieveTemplate(pItemId, EntityType.PRODUCT);

            for (String ndcNo : pNdcsNumbers) {
                NdcVo ndcVo = createNdcFromFdb(ndcNo, pUser);

                if (ndcVo == null) {
                    continue;
                }

                ndcs.add(ndcVo);
            }

            product.setNdcs(ndcs);

        } catch (ItemNotFoundException e) {
            LOG.error("Error ItemNotFoundException! " + e.getMessage());
        }

        return product;
    }

    /**
     * retrieveFdbUpdateDate
     * @return fdbUpdateDate
     */
    public String retrieveFdbUpdateDate() {
        return this.drugReferenceAutoCapability.getFdbUpdateDate();
    }
    
    
    /**
     * Retrieves Package Drug from FDB.
     * @param pNdc to retrieve
     * @param user UserVo
     * @return returns FdbAddVo
     */
    @Override
    public FDBSearchOptionVo retrieveNdc(String pNdc, UserVo user) {

        FDBSearchOptionVo fdbSearchOptionVo = new FDBSearchOptionVo();

        // set type of search
        fdbSearchOptionVo.setFdbSearchOptionType(FDBSearchOptionType.NDC_SEARCH);
        fdbSearchOptionVo.setFdbSearchString(pNdc);

        FDBSearchOptionVo fdbSearchOption = drugReferenceCapability.getOneNdc(fdbSearchOptionVo);

        return fdbSearchOption;
    }

    /**
     * retrieves all items in the EPL_FDB_UPDATE table
     * @return FdbUpdateVo
     */
    @Override
    public List<FdbUpdateVo> retrieveEplUpdateList() {

        List<FdbUpdateVo> fdbUpdateVoList = fdbUpdateDomainCapability.retrieve();

        return formatDates(fdbUpdateVoList);
    }

    /**
     * formatDates
     * @param fdbUpdateList fdbUpdateList
     * @return list of FdbUpdateVo
     */
    private List<FdbUpdateVo> formatDates(List<FdbUpdateVo> fdbUpdateList) {
        List<FdbUpdateVo> formattedDateList = new ArrayList<FdbUpdateVo>();

        for (FdbUpdateVo fdbUpdateVo : fdbUpdateList) {
            Date formatedDate = formatDate(fdbUpdateVo.getCreatedDate());
            fdbUpdateVo.setCreatedDate(formatedDate);
            formattedDateList.add(fdbUpdateVo);
        }

        return formattedDateList;
    }

    /**
     * formatDates
     * @param fdbAddList List
     * @return list of FdbUpdateVo
     */
    private List<FdbAddVo> formatDatesAdd(List<FdbAddVo> fdbAddList) {
        List<FdbAddVo> formattedDateList = new ArrayList<FdbAddVo>();

        for (FdbAddVo fdbAddVo : fdbAddList) {
            Date formatedDate = formatDate(fdbAddVo.getCreatedDate());
            fdbAddVo.setCreatedDate(formatedDate);
            formattedDateList.add(fdbAddVo);
        }

        return formattedDateList;
    }

    /**
     * formats a date to yyyyMMdd
     *
     * @param pDate pDate
     * @return formated date
     */
    private Date formatDate(Date pDate) {
        Date createdDate = null;

        try {
            DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
            createdDate = formatter.parse(formatter.format(pDate));

        } catch (ParseException e) {
            LOG.error("formatDate:  " + e.getMessage());
        }

        return createdDate;
    }

    /**
     * retrieves items from the Epl_FDB_Auto_Update table
     * @return FdbAutoUpdateVo
     */
    @Override
    public List<FdbAutoUpdateVo> retrieveEplAutoUpdateList() {
        return fdbAutoUpdateDomainCapability.retrieve();
    }

    /**
     * retrieve all items in the Epl_Auto_Add table
     * @return FdbAutoAddVo
     */
    @Override
    public List<FdbAutoAddVo> retrieveEplAutoAddList() {
        return fdbAutoAddDomainCapability.retrieve();
    }

    /**
     * retrieves all items from the EPL_FDB_ADD table.
     *
     * @return FdbAddVos
     */
    @Override
    public List<FdbAddVo> retrieveEPLPendingList() {
        return fdbAddDomainCapability.retrieve();
    }

    /**
     * 
     * deletes NDC from the EPL pending list.
     *
     * @param ndcNo ndc number
     */
    @Override
    public void deleteNdcFromEplPendingList(String ndcNo) {
        fdbAddDomainCapability.deleteByNdcNumber(ndcNo);
    }

    /**
     * deletes Items From UpdateList
     * @param id id
     */
    @Override
    public void deleteItemsFromUpdateList(String id) {
        fdbUpdateDomainCapability.deleteItemById(id);

    }

    /**
     * deletes Items From Auto Update List
     * @param id id to remove
     */
    @Override
    public void deleteItemsFromAutoUpdateList(String id) {
        fdbAutoUpdateDomainCapability.deleteItemById(id);
    }

    /**
     * deletes Items From Auto Add List
     * @param ndcNo ndcNo to remove
     */
    @Override
    public void deleteItemsFromAutoAddList(String ndcNo) {
        fdbAutoAddDomainCapability.deleteByNdcNumber(ndcNo);
    }

    /**
     * returns closest match products by GCNSequence Number.
     * 
     * @param seqNos  the selected Sequence Nos
     * @return closest match productVOs
     */
    @Override
    public List<ProductVo> getClosestMatchProducts(List<String> seqNos) {

        List<ProductVo> closestMatchProducts = new ArrayList<ProductVo>();

        for (String seqNo : seqNos) {

            List<ManagedItemVo> products = findProductsByGncSequenceNo(seqNo);

            if (products == null) {
                return closestMatchProducts;
            }

            for (ManagedItemVo managedItemVo : products) {
                ProductVo prod = (ProductVo) managedItemVo;

                if (!closestMatchProducts.contains(prod)) {
                    closestMatchProducts.add(prod);
                }
            }
        }

        return closestMatchProducts;
    }

    /**
     * finds Products by GNC Sequence Number.
     * 
     * @param pGncSequenceNo GNC no to find
     * @return returns list of matching products
     */
    private List<ManagedItemVo> findProductsByGncSequenceNo(
            String pGncSequenceNo) {

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.ADVANCED, Environment.NATIONAL);
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();

        // Item status of inactive and active
        searchCriteria.setItemStatus(new ArrayList<ItemStatus>());
        ItemStatus itemStatusActive = ItemStatus.valueOf("ACTIVE");
        ItemStatus itemStatusInactive = ItemStatus.valueOf("INACTIVE");
        searchCriteria.getItemStatus().add(itemStatusActive);
        searchCriteria.getItemStatus().add(itemStatusInactive);

        // Request status of approved, rejected or pending
        searchCriteria.setRequestStatus(new ArrayList<RequestItemStatus>());
        RequestItemStatus requestItemStatusApproved = RequestItemStatus.valueOf("APPROVED");
        RequestItemStatus requestItemStatusRejected = RequestItemStatus.valueOf("REJECTED");
        RequestItemStatus requestItemStatusPending = RequestItemStatus.valueOf("PENDING");
        searchCriteria.getRequestStatus().add(requestItemStatusRejected);
        searchCriteria.getRequestStatus().add(requestItemStatusApproved);
        searchCriteria.getRequestStatus().add(requestItemStatusPending);

        // Not only local use
        searchCriteria.setLocalUse(LocalUseSearchType.ALL_ITEMS);

        // Use the and search
        searchCriteria.setAdvancedAndSearch(true);

        // add the search terms
        searchTerms.add(new SearchTermVo(EntityType.PRODUCT, FieldKey.GCN_SEQUENCE_NUMBER, pGncSequenceNo));
        searchCriteria.setSearchTerms(searchTerms);
        searchCriteria.setEntityType(EntityType.PRODUCT);
        searchCriteria.setSortedFieldKey(FieldKey.PRODUCT);

        try {
            return managedItemCapability.search(searchCriteria);
        } catch (Exception e) {
            LOG.error("findProductsByGncSequenceNo:  " + e.getMessage());
        }

        return null;
    }

    /**
     * Finds manufacturer by Name.
     * @param pManufacturerName name of Manufacturer
     * @return returns list of matching manufacturers
     */
    @Override
    public ManufacturerVo findManufacturerByName(String pManufacturerName) {

        Collection<ManufacturerVo> manufacturers = manufacturerDomainCapability.retrieveByName(pManufacturerName);
        ManufacturerVo manufacturerVo = null;

        if (manufacturers == null || manufacturers.size() < 1) {
            return manufacturerVo;
        }

        for (ManufacturerVo vo : manufacturers) {
            String name = vo.getValue();

            if (name.toUpperCase(Locale.US).equals(pManufacturerName.toUpperCase(Locale.US))) {
                manufacturerVo = vo;
                LOG.debug("--> found match: " + name);
                LOG.debug("--> Found match Manufacturer ID: " + manufacturerVo.getId());
                break;
            }
        }

        return manufacturerVo;
    }

    /**
     * Creates NDC from FDB.
     * 
     * @param ndcNo - NDC number
     * @param pUser - required user
     * @return returns Newly created NDC
     */
    public NdcVo createNdcFromFdb(String ndcNo, UserVo pUser) {

        OtcRxVo otcRxVo = new OtcRxVo();
        ShapeVo shapeVo = new ShapeVo();
        ColorVo colorVo = new ColorVo();

        NdcVo ndcVo = (NdcVo) managedItemCapability.retrieveBlankTemplate(EntityType.NDC);
        String noDashesNdc = ndcNo.replace("-", "");

        FDBSearchOptionVo fDBSearchOptionVos = this.retrieveNdc(noDashesNdc, pUser);
        List<FDBSearchOptionResultVo> fdbSearchList =
            (List<FDBSearchOptionResultVo>) fDBSearchOptionVos.getSearchOptionResults();

        if (fdbSearchList.size() == 0) {
            return null;
        }

        FDBSearchOptionResultVo fdbSearchOptionResultVo = fdbSearchList.get(0);

        ndcVo.setSingleMultiSourceProduct(fdbSearchOptionResultVo.getSingleMultiSourceProductVo());

        // Check to see if manufacturer exist in EPL DB
        ManufacturerVo manufacturerVo = findManufacturerByName(fdbSearchOptionResultVo.getManufacturerDistributorName());

        if (manufacturerVo == null) {
            ManufacturerVo newManufacturer =
                createManufacturer(fdbSearchOptionResultVo.getManufacturerDistributorName(), pUser);
            ndcVo.setManufacturer(newManufacturer);
        } else {
            ndcVo.setManufacturer(manufacturerVo);
        }

        ndcVo.setSequenceNumber(Long.valueOf(fdbSearchOptionResultVo.getGCNSeqNo()));

        String fdbNdcNo = fdbSearchOptionResultVo.getNDC();
        LOG.debug("fdbNdcNo is " + fdbSearchOptionResultVo.getNDC());

        if (fdbNdcNo.contains("-")) {
            ndcVo.setNdc(fdbNdcNo);
        } else {
            ndcVo.setNdc(fdbNdcNo.substring(0, PPSConstants.I5) 
                + "-" + fdbNdcNo.substring(PPSConstants.I5, PPSConstants.I9) + "-" 
                + fdbNdcNo.substring(PPSConstants.I9, PPSConstants.I11));
        }

        ndcVo.setPackageSize(Double.valueOf(fdbSearchOptionResultVo.getPackageSize()));

        PackageTypeVo packageTypeVo = findPackageTypeByName(fdbSearchOptionResultVo.getPackageDescription());

        if (packageTypeVo == null) {
            PackageTypeVo newPackageTypeVo = createPackageType(fdbSearchOptionResultVo.getPackageDescription(), pUser);
            ndcVo.setPackageType(newPackageTypeVo);
        } else {
            ndcVo.setPackageType(packageTypeVo);
        }

        ndcVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        ndcVo.setTradeName(fdbSearchOptionResultVo.getStrTradeName());
        ndcVo.setSource(NdcSourceType.COTS); // only VA or COTS
        ndcVo.setItemStatus(ItemStatus.ACTIVE);

        if ("Prescription required".equalsIgnoreCase(fdbSearchOptionResultVo.getOTCIndicator())) {
            otcRxVo.setValue(PPSConstants.PRESCRIPTION);
            otcRxVo.setId(PPSConstants.PRESCRIPTION);
        } else {
            otcRxVo.setValue(PPSConstants.OVER_THE_COUNTER);
            otcRxVo.setId(PPSConstants.PRESCRIPTION);
        }

        ndcVo.setOtcRxIndicator(otcRxVo);
        ndcVo.setRejectionReasonText("");

        shapeVo.setValue(fdbSearchOptionResultVo.getShape());
        shapeVo.setId("0");
        ndcVo.setShape(shapeVo);
        colorVo.setValue(fdbSearchOptionResultVo.getColor());
        ndcVo.setColor(colorVo);
        ndcVo.setImprint(fdbSearchOptionResultVo.getStrImprint1());
        ndcVo.setImprint2(fdbSearchOptionResultVo.getStrImprint2());

        ndcVo.setTenDigitNdc(fdbSearchOptionResultVo.getStrTenDigitNdc());
        ndcVo.setTenDigitFormatIndication(fdbSearchOptionResultVo.getNDCFormatCode());

        // DF215 set scored, need to add Scored to the Vo.
        if (StringUtils.isNotBlank(fdbSearchOptionResultVo.getStrScored())) {
            ndcVo.getVaDataFields().getDataField(FieldKey.SCORED).addSelection("0.5");
        }

        ndcVo.setNdcDispUnitsPerOrdUnit(new Double("00.0"));
        ndcVo.getVaDataFields().getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT).selectValue(new Double("0.0"));
        ndcVo.getVaDataFields().getDataField(FieldKey.PROTECT_FROM_LIGHT).selectValue(false);
        ndcVo.getVaDataFields().getDataField(FieldKey.PREVIOUS_NDC).selectStringValue(fdbSearchOptionResultVo.getPreviousNDC());

        if ((ndcVo.getTenDigitFormatIndication() != null) && ndcVo.getTenDigitFormatIndication().contains("UPC")) {
            ndcVo.getVaDataFields().getDataField(FieldKey.PREVIOUS_UPC_UPN)
                .selectStringValue(fdbSearchOptionResultVo.getPreviousNDC());
            ndcVo.setUpcUpn(fdbSearchOptionResultVo.getNDC());
        }

        try {
            ndcVo = drugReferenceCapability.populateFdbNdcFields(ndcVo);
        } catch (FDBException e) {
            LOG.debug("Couldn't get the NDC fields.");
        }

        return ndcVo;
    }

    /**
     * Creates the PackageType
     * @param pPackageDescription pPackageDescription
     * @param pUser user
     * @return PackageTypeVo PackageTypeVo
     */
    private PackageTypeVo createPackageType(String pPackageDescription, UserVo pUser) {

        PackageTypeVo resultPackageType = null;
        PackageTypeVo packageTypeVo = new PackageTypeVo();
        packageTypeVo.setValue(pPackageDescription);
        packageTypeVo.setItemStatus(ItemStatus.ACTIVE);
        packageTypeVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        try {
            if (vistaFileSynchCapability.sendNewItemToVista(packageTypeVo, pUser, okToSendToVista(), catchingUp())) {
                addToQueue(packageTypeVo, packageTypeVo.getEntityType(), ItemAction.ADD, pUser);
            }
            resultPackageType = packageTypeDomainCapability.create(packageTypeVo, pUser);
        } catch (DuplicateItemException e) {
            LOG.error("Error DuplicateItemException! " + e.getMessage());
        } catch (ValidationException e) {
            LOG.error("Validation Excpetion " + e.getMessage());
        }

        return resultPackageType;
    }

    /**
     * finds the package drug by name in EPL db 
     *
     * @param packageDescription packageDescription
     * @return PackageTypeVo PackageTypeVo
     */
    @Override
    public PackageTypeVo findPackageTypeByName(String packageDescription) {

        Collection<PackageTypeVo> results = packageTypeDomainCapability.retrieveByName(packageDescription);
        PackageTypeVo packageTypeVo = null;

        if (results == null || results.size() < 1) {
            return packageTypeVo;
        }

        for (PackageTypeVo vo : results) {
            String name = vo.getValue();

            if (name.equals(packageDescription)) {
                LOG.debug("-->found match: " + name);
                LOG.debug("-->Found match packageType ID: " + vo.getId());
                packageTypeVo = vo;
                break;
            }
        }

        return packageTypeVo;
    }

    /**
     * Create Manufacturer.
     *
     * @param pManufactureName pManufactureName
     * @param pUser user
     * @return ManufacturerVo ManufacturerVo
     */
    private ManufacturerVo createManufacturer(String pManufactureName, UserVo pUser) {

        ManufacturerVo resultManufacturerVo = null;
        ManufacturerVo newManufacturerVo = new ManufacturerVo();

        newManufacturerVo.setItemStatus(ItemStatus.ACTIVE);
        newManufacturerVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        newManufacturerVo.setValue(pManufactureName);

        try {
            if (vistaFileSynchCapability
                    .sendNewItemToVista(newManufacturerVo, pUser, okToSendToVista(), catchingUp())) {
                addToQueue(newManufacturerVo, newManufacturerVo.getEntityType(), ItemAction.ADD, pUser);
            }

            resultManufacturerVo = manufacturerDomainCapability.create(newManufacturerVo, pUser);

        } catch (DuplicateItemException e) {
            LOG.error("Error DuplicateItemException! " + e.getMessage());
        } catch (ValidationException e) {
            LOG.error("Validation Excpetion " + e.getMessage());
        }

        return resultManufacturerVo;
    }

    /**
     * 
     * getFdbAddVoItemsList
     *
     * @param selectedNdcs selectedNdcs
     * @param pendingList pendingList
     * @return List<FdbAddVo>
     */
    @Override
    public List<FdbAddVo> getFdbAddVoItemsList(List<String> selectedNdcs, List<FdbAddVo> pendingList) {

        List<FdbAddVo> resultList = new ArrayList<FdbAddVo>();

        // look for a match, then return list with the matches.

        for (String ndc : selectedNdcs) {

            for (FdbAddVo fdbAddVo : pendingList) {
                if (fdbAddVo.getNdc().equals(ndc)) {
                    if (doesEPLNdcExist(ndc)) {
                        fdbAddVo.setNdcExist(true);
                    }

                    resultList.add(fdbAddVo);
                }
            }
        }

        return resultList;
    }
    

    /**
     * Searches the EPL_NDC table by NDC number
     *
     * @param pNdcNumber  ndc number 
     * @return true if ndc exist in db, otherwise false
     */
    public boolean doesEPLNdcExist(String pNdcNumber) {
        List<NdcVo> ndcVos = this.searchNdcByNumber(pNdcNumber);

        if (ndcVos.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * searchNdcByNumber
     * @param pNdcNumber pNdcNumber
     * @return  List<NdcVo>
     */
    @Override
    public List<NdcVo> searchNdcByNumber(String pNdcNumber) {

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);
        searchCriteria.getCategories().clear();

        String replacedValue = pNdcNumber.replace("-", "");
        SearchTermVo searchTerm = new SearchTermVo(EntityType.NDC, FieldKey.NDC, replacedValue);

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);
        searchCriteria.setSearchTerms(searchTerms);

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        itemStatus.add(ItemStatus.INACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        searchCriteria.setEntityType(EntityType.NDC);
        searchCriteria.setSortedFieldKey(FieldKey.NDC);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PPSConstants.I10);

        List<NdcVo> items = ndcDomainCapability.search(searchCriteria);

        return items;

    }

    /**
     * Sorts the pending list by GCN Sequence No.
     * 
     * @param pPendingList  list to sort
     * @return sorted list
     */
    @Override
    public List<FdbAddVo> sortPendingListByGCNSeqNo(List<FdbAddVo> pPendingList) {
        Collections.sort(pPendingList, new Comparator<Object>() {

            @Override
            public int compare(Object o1, Object o2) {
                FdbAddVo p1 = (FdbAddVo) o1;
                FdbAddVo p2 = (FdbAddVo) o2;

                // return 0 if they are equal.
                return p1.getGcnSeqno().compareTo(p2.getGcnSeqno());
            }
        });

        return pPendingList;
    }

    /**
     * retrieves NDCs from FDB_ADD table that where selected for the Matching
     * page
     * 
     * @param ndcList NDC number
     * @return list of FdbAddVo
     */
    @Override
    public List<FdbAddVo> retrieveEPLPendingListByNdc(List<String> ndcList) {
        List<FdbAddVo> resultList = new ArrayList<FdbAddVo>();

        // get pendingList form EPL_FDB_ADD table
        List<FdbAddVo> pendingList = this.retrieveEPLAddList();

        // look for a match, then return list with the matches.
        for (String ndc : ndcList) {
            for (FdbAddVo vo : pendingList) {
                if (vo.getNdc().equals(ndc)) {
                    if (doesEPLNdcExist(ndc)) {
                        vo.setNdcExist(true);
                    }

                    resultList.add(vo);
                }
            }
        }

        return resultList;
    }

    /**
     * Retrieve EPL Pending List By NDC
     * @param  ndcList - NDC numbers to retrieve
     * @return list of FdbAddVo
     */
    @Override
    public List<FdbAddVo> retrieveEPLPendingListByNdc(String[] ndcList) {
        List<FdbAddVo> resultList = new ArrayList<FdbAddVo>();

        // get pendingList form EPL_FDB_ADD table
        List<FdbAddVo> pendingList = this.retrieveEPLAddList();

        // look for a match, then return list with the matches.
        for (String ndc : ndcList) {
            for (FdbAddVo fdbAddVo : pendingList) {
                if (fdbAddVo.getNdc().equals(ndc)) {
                    resultList.add(fdbAddVo);
                }
            }
        }

        return resultList;
    }

    /**
     * retrieve EPL Add List
     * 
     * @return List<FdbAddVo>
    */
    protected List<FdbAddVo> retrieveEPLAddList() {
        List<FdbAddVo> fdbAddVos = retrieveEPLPendingList();

        fdbAddVos = formatDatesAdd(fdbAddVos);

        return sortPendingListByGCNSeqNo(fdbAddVos);
    }

    /**
     * 
     * getRemovedExistingList
     *
     * @param selectedNdcs selectedNdcs
     * @param pendingList pendingList
     * @param pSelected pSelected
     * @return  List<FdbAddVo>
     */
    @Override
    public List<FdbAddVo> getRemovedIfExistList(List<String> selectedNdcs,
            List<FdbAddVo> pendingList, boolean pSelected) {

        List<FdbAddVo> resultList = new ArrayList<FdbAddVo>();

        //if (!pSelected)  {
        resultList = checkAllItems(pendingList);

        //} else {
        //    resultList = checkSelected(selectedNdcs, pendingList);
        //}

        pendingList.removeAll(resultList);

        return pendingList;
    }

    /**
     * checkAllItems
     *
     * @param pendingList pendingList
     * @return List<FdbAddVo>
     */
    private List<FdbAddVo> checkAllItems(List<FdbAddVo> pendingList) {

        List<FdbAddVo> resultList = new ArrayList<FdbAddVo>();
        LOG.debug("doing all the list: ");

        for (FdbAddVo fdbAddVo : pendingList) {
            String ndc = fdbAddVo.getNdc();

            if (ndcDomainCapability.ndcExists(ndc)) {
                LOG.debug("ndc exist: " + ndc);
                fdbAddVo.setNdcExist(true);
                resultList.add(fdbAddVo);
            }
        }

        return resultList;
    }

    /**
     * This method is used to convert the NDC Formatted code into a human.
     * readable string
     * 
     * @param strCode The FDB Format code
     * @return The human readable string
     */
    public String setNDCFormatCode(String strCode) {
        if (strCode == null) {
            return "";
        } else if ("0".equals(strCode)) {
            return "5-4-2 PIN";
        } else if ("1".equals(strCode)) {
            return "04-4-2 NDC";
        } else if ("2".equals(strCode)) {
            return "5-03-2 NDC";
        } else if ("3".equals(strCode)) {
            return "5-4-01 NDC";
        } else if ("4".equals(strCode)) {
            return "5-03-2 UPC";
        } else if ("5".equals(strCode)) {
            return "5-4-01 UPC";
        } else if ("6".equals(strCode)) {
            return "5-4-10 UPC";
        } else if ("7".equals(strCode)) {
            return "04-4-2 HRI";
        } else {
            return "";
        }
    }

    /**
     * create Fdb CSV File
     *
     * @param results results
     * @return StringBuilderS
     */
    @Override
    public StringBuilder createFdbCsvFile(List<FDBSearchOptionResultVo> results) {

        StringBuilder sb = new StringBuilder();
        sb.append(FDB_SEARCH_RESULT_HEADER);
        sb.append("\n");

        for (FDBSearchOptionResultVo vo : results) {

            sb.append(vo.getNDC())
                .append(CSV_DELIMITER)
                .append(vo.getNDCFormatCode())
                .append(CSV_DELIMITER)
                .append(vo.getBrandName())
                .append(CSV_DELIMITER)
                .append(vo.getManufacturerDistributorName())
                .append(CSV_DELIMITER)
                .append(vo.getLabelName())
                .append(CSV_DELIMITER)
                .append(vo.getLabelName25())
                .append(CSV_DELIMITER)
                .append(vo.getGenericName())
                .append(CSV_DELIMITER)
                .append(vo.getDrugStrengthDescription())
                .append(CSV_DELIMITER)
                .append(vo.getDosageFormDescription())
                .append(CSV_DELIMITER)
                .append(vo.getUnitDoseIndicator())
                .append(CSV_DELIMITER)
                .append(vo.getGCNSeqNo())
                .append(CSV_DELIMITER)
                .append(vo.getDrugFormCode())
                .append(CSV_DELIMITER)
                .append(vo.getDrugClassification())
                .append(CSV_DELIMITER)
                .append(vo.getDuplicateTherapies())
                .append(CSV_DELIMITER)
                .append(vo.getColor())
                .append(CSV_DELIMITER)
                .append(vo.getShape())
                .append(CSV_DELIMITER)
                .append(vo.getFlavor())
                .append(CSV_DELIMITER)
                .append(vo.getTop200Rank())
                .append(CSV_DELIMITER)
                .append(vo.getTop50GenRank())
                .append(CSV_DELIMITER)
                .append(vo.getReplacementNDC())
                .append(CSV_DELIMITER)
                .append(vo.getPreviousNDC())
                .append(CSV_DELIMITER)
                .append(vo.getObsoleteDate())
                .append(CSV_DELIMITER)
                .append(vo.getPackageSize())
                .append(CSV_DELIMITER)
                .append(vo.getPackageDescription())
                .append(CSV_DELIMITER)
                .append(vo.getAdditionalDescriptor())
                .append(CSV_DELIMITER)
                .append(vo.getDEACode())
                .append(CSV_DELIMITER)
                .append(vo.getOTCIndicator())
                .append("\n");
        }

        return sb;
    }

    /**
     * create Fdb CSV File
     *
     * @param results results
     * @return StringBuilderS
     */
    @Override
    public StringBuilder createFdbAddCsvFile(List<FdbAddVo> results) {

        StringBuilder sb = new StringBuilder();
        sb.append(FDB_ADD_RESULT_HEADER);
        sb.append("\n");

        for (FdbAddVo vo : results) {

            sb.append(vo.getNdc())
                .append(CSV_DELIMITER)
                .append(vo.getGcnSeqno())
                .append(CSV_DELIMITER)
                .append(vo.getPackageSize())
                .append(CSV_DELIMITER)
                .append(vo.getPackageType())
                .append(CSV_DELIMITER)
                .append(vo.getManufacturer())
                .append(CSV_DELIMITER)
                .append(vo.getFdbProductName())
                .append(CSV_DELIMITER)
                .append(vo.getLabelName())
                .append(CSV_DELIMITER)
                .append(vo.getAddDesc())
                .append(CSV_DELIMITER)
                .append(vo.getFdbCreationDate())
                .append("\n");
        }

        return sb;
    }

    /**
     * create Fdb Auto Add CSV File
     *
     * @param autoAddList results list
     * @return StringBuilderS
     */
    @Override
    public StringBuilder createFdbAutoAddCsvFile(List<FdbAutoAddVo> autoAddList) {
        StringBuilder sb = new StringBuilder();
        sb.append(FDB_ADD_REPORT_HEADER);
        sb.append("\n");

        for (FdbAutoAddVo vo : autoAddList) {

            sb.append(vo.getNdc())
                .append(CSV_DELIMITER)
                .append(vo.getPackageSize())
                .append(CSV_DELIMITER)
                .append(vo.getPackageType())
                .append(CSV_DELIMITER)
                .append(vo.getAddDesc())
                .append(CSV_DELIMITER)
                .append(vo.getGcnSeqno())
                .append(CSV_DELIMITER)
                .append(vo.getVaProductName())
                .append(CSV_DELIMITER)
                .append(vo.getLabelName())
                .append(CSV_DELIMITER)
                .append(vo.getFdbProductName())
                .append(CSV_DELIMITER)
                .append(vo.getCreatedDate())
                .append("\n");
        }

        return sb;
    }

    @Override
    public StringBuilder createFdbUpdateCsvFile(List<FdbUpdateVo> updateList) {
        StringBuilder sb = new StringBuilder();
        sb.append(FDB_UPDATE_RESULT_HEADER);
        sb.append("\n");

        for (FdbUpdateVo vo : updateList) {

            sb.append(vo.getNdc())
                .append(CSV_DELIMITER).append(vo.getVaProductName())
                .append(CSV_DELIMITER).append(vo.getFdbProductName())
                .append(CSV_DELIMITER).append(vo.getGcnSeqno())
                .append(CSV_DELIMITER).append(vo.getMessage())
                .append(CSV_DELIMITER).append(vo.getCreatedDate())
                .append("\n");
        }

        return sb;
    }

    @Override
    public StringBuilder createFdbAutoUpdateCsvFile(List<FdbAutoUpdateVo> autoUpdateList) {
        StringBuilder sb = new StringBuilder();
        sb.append(FDB_UPDATE_REPORT_HEADER);
        sb.append("\n");

        for (FdbAutoUpdateVo vo : autoUpdateList) {

            sb.append(vo.getNdc())
                .append(CSV_DELIMITER)
                .append(vo.getVaProductName())
                .append(CSV_DELIMITER)
                .append(vo.getFdbProductName())
                .append(CSV_DELIMITER)
                .append(vo.getGcnSeqno())
                .append(CSV_DELIMITER)
                .append(vo.getMessage())
                .append(CSV_DELIMITER)
                .append(vo.getCreatedDate())
                .append("\n");
        }

        return sb;
    }
    
    /**
     * Add Current Request to the VistA Queue
     *
     * @param managedItem Item to be added (by reference) 
     * @param itemType The item's type
     * @param action The action - Add or Modify
     * @param user - The current user
     */
    protected void addToQueue(ManagedItemVo managedItem, EntityType itemType, ItemAction action, UserVo user) {
        NdfSynchQueueVo ndfSynchQueueVo = new NdfSynchQueueVo();
        ndfSynchQueueVo.setIdFk(managedItem.getId());
        ndfSynchQueueVo.setItemType(itemType.toString());
        ndfSynchQueueVo.setActionType(action.value());

        //        NdfSynchQueueDomainCapability 
//        ndfSynchQueueDomainCapability = 
//            managedItemCapabilityFactory.getDomainCapability(EntityType.NDF_SYNCH_QUEUE);
        
        ndfSynchQueueDomainCapability.createWithoutDuplicateCheck(ndfSynchQueueVo, user);
        List<NationalSettingVo> settingList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo setting : settingList) {

            if (setting.getKeyName().equalsIgnoreCase(NationalSetting.NUM_MSG_QUEUE.toString())) {
                setting.setIntegerValue(setting.getIntegerValue() + 1);
                nationalSettingDomainCapability.update(setting, user);
                break;
            }
        }
        
        
    }

    /**
     * Used to deterimine if the sendToVistaFlat is on.
     * @return true if messaging is turned off, false otherwise
     */
    protected boolean okToSendToVista() {

        List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : settingsList) {
            if (NationalSetting.MESSAGE_STATUS.toString().equals(vo.getKeyName())) {
                return vo.getBooleanValue();
            }
        }

        return false; // didn't have the message, don't send.
    }

    /**
     * Used to determine if the MESSAGE_QUEUE_IN_PROCESS flag is on.
     * @return true if queue is being emptied, false otherwise
     */
    protected boolean catchingUp() {

        List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : settingsList) {
            if (NationalSetting.MESSAGE_QUEUE_IN_PROGRESS.toString().equals(vo.getKeyName())) {
                return vo.getBooleanValue();
            }
        }

        return false; // didn't have the key, assume we are not sending.
    }

    /**
     * se tManufacturerDomainCapability
     * @param manufacturerDomainCapability the manufacturerDomainCapability to set.
     */
    @Override
    public void setManufacturerDomainCapability(ManufacturerDomainCapability manufacturerDomainCapability) {
        this.manufacturerDomainCapability = manufacturerDomainCapability;
    }

    /**
     * get OrderUnitDomainCapability
     * @return the orderUnitDomainCapability.
     */
    public OrderUnitDomainCapability getOrderUnitDomainCapability() {
        return orderUnitDomainCapability;
    }

    /**
     * set OrderUnitDomainCapability
     * @param orderUnitDomainCapability the orderUnitDomainCapability to set.
     */
    @Override
    public void setOrderUnitDomainCapability(
            OrderUnitDomainCapability orderUnitDomainCapability) {
        this.orderUnitDomainCapability = orderUnitDomainCapability;
    }

    /**
     * set PackageTypeDomainCapability
     * @param packageTypeDomainCapability the packageTypeDomainCapability to set.
     */
    @Override
    public void setPackageTypeDomainCapability(PackageTypeDomainCapability packageTypeDomainCapability) {
        this.packageTypeDomainCapability = packageTypeDomainCapability;
    }

    /**
     * set DrugReferenceCapability
     * @param drugReferenceCapability the drugReferenceCapability to set.
     */
    @Override
    public void setDrugReferenceCapability(DrugReferenceCapability drugReferenceCapability) {
        this.drugReferenceCapability = drugReferenceCapability;
    }
    
    /**
     * set DrugReferenceAutoCapability
     * @param drugReferenceAutoCapability the drugReferenceAutoCapability to set.
     */
    @Override
    public void setDrugReferenceAutoCapability(DrugReferenceAutoCapability drugReferenceAutoCapability) {
        this.drugReferenceAutoCapability = drugReferenceAutoCapability;
    }

    /**
     * set ManagedItemCapability
     * @param managedItemCapability the managedItemCapability to set.
     */
    @Override
    public void setManagedItemCapability(ManagedItemCapability managedItemCapability) {
        this.managedItemCapability = managedItemCapability;
    }

    /**
     * get FdbAddDomainCapability
     * @return the fdbAddDomainCapability.
     */
    public FdbAddDomainCapability getFdbAddDomainCapability() {
        return fdbAddDomainCapability;
    }

    /**
     * set FdbAddDomainCapability
     * @param fdbAddDomainCapability the fdbAddDomainCapability to set.
     */
    public void setFdbAddDomainCapability(
        FdbAddDomainCapability fdbAddDomainCapability) {
        this.fdbAddDomainCapability = fdbAddDomainCapability;
    }
    
    /**
     * set FssInterfaceCapability
     * @param fssInterfaceCapabilityIn the fssInterfaceCapability to set.
     */
    public void setFssInterfaceCapability(
        FssInterfaceCapability fssInterfaceCapabilityIn) {
        this.fssInterfaceCapability = fssInterfaceCapabilityIn;
    }

    /**
     * set FdbUpdateDomainCapability
     * @param fdbUpdateDomainCapability the fdbUpdateDomainCapability to set
     */
    public void setFdbUpdateDomainCapability(
            FdbUpdateDomainCapability fdbUpdateDomainCapability) {
        this.fdbUpdateDomainCapability = fdbUpdateDomainCapability;
    }

    /**
     * set FdbAutoUpdateDomainCapability
     * @param fdbAutoUpdateDomainCapability the fdbAutoUpdateDomainCapability to set
     */
    public void setFdbAutoUpdateDomainCapability(
            FdbAutoUpdateDomainCapability fdbAutoUpdateDomainCapability) {
        this.fdbAutoUpdateDomainCapability = fdbAutoUpdateDomainCapability;
    }

    /**
     * set FdbAutoAddDomainCapability
     * @param fdbAutoAddDomainCapability the fdbAutoAddDomainCapability to set
     */
    public void setFdbAutoAddDomainCapability(
            FdbAutoAddDomainCapability fdbAutoAddDomainCapability) {
        this.fdbAutoAddDomainCapability = fdbAutoAddDomainCapability;
    }

    /**
     * Setter for ndcDomainCapability
     * @param ndcDomainCapability the ndcDomainCapability to set
     */
    public void setNdcDomainCapability(NdcDomainCapability ndcDomainCapability) {
        this.ndcDomainCapability = ndcDomainCapability;
    }

    /**
     * set vistaFileSynchCapability
     * @param vistaFileSynchCapability the vistaFileSynchCapability to set
     */
    public void setVistaFileSynchCapability(
        VistaFileSynchCapability vistaFileSynchCapability) {
        this.vistaFileSynchCapability = vistaFileSynchCapability;
    }

    /**
     * Setter for nationalSettingDomainCapability
     * @param nationalSettingDomainCapability the nationalSettingDomainCapability to set
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }
    
    /**
     * setFdbNdcDomainCapability.
     * @param fdbNdcDomainCapability fdbNdcDomainCapability
     */
    public void setFdbNdcDomainCapability(FdbNdcDomainCapability fdbNdcDomainCapability) {
        this.fdbNdcDomainCapability = fdbNdcDomainCapability;
    }
    
    /**
     * setItemAuditHistoryDomainCapability.
     * @param itemAuditHistoryDomainCapability the itemAuditHistoryDomainCapability to set
     */
    public void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability) {
        this.itemAuditHistoryDomainCapability = itemAuditHistoryDomainCapability;
    }
    
    /**
     * setProductDomainCapability.
     * @param productDomainCapability the productDomainCapability to set
     */
    public void setProductDomainCapability(ProductDomainCapability productDomainCapability) {
        this.productDomainCapability = productDomainCapability;
    }
    
    /**
     * setRequestDomainCapability.
     * @param requestDomainCapability the productDomainCapability to set
     */
    public void setRequestDomainCapability(RequestDomainCapability requestDomainCapability) {
        this.requestDomainCapability = requestDomainCapability;
    }
    
    /**
     * getNdfSynchQueueDomainCapability
     * @return the ndfSynchQueueDomainCapability
     */
    public NdfSynchQueueDomainCapability getNdfSynchQueueDomainCapability() {
        return ndfSynchQueueDomainCapability;
    }

    
    /**
     * setNdfSynchQueueDomainCapability
     * @param ndfSynchQueueDomainCapability the ndfSynchQueueDomainCapability to set
     */
    public void setNdfSynchQueueDomainCapability(NdfSynchQueueDomainCapability ndfSynchQueueDomainCapability) {
        this.ndfSynchQueueDomainCapability = ndfSynchQueueDomainCapability;
    }

}
