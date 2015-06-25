/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryDetailsVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.external.common.fss.outbound.capability.FssInterfaceCapability;
import gov.va.med.pharmacy.peps.service.common.capability.FssCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.scheduler.ProcessStatus;


/**
 * StsCapability is the implementation of the STSCapabilty class
 *
 */
public class FssCapabilityImpl implements FssCapability {
    
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
    .getLogger(FssCapabilityImpl.class);
    private static final Long USER_ID = 42L;
    private static final String STATION = "999";
    private static final String NOVALUE = "No Value";
    private static final int TIMEOUT = 600;
    private static final String FSS_PROCESS = "FSS Process";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(
        "yyyy-MM-dd", Locale.US);
    private Date runDate;
    
    private FssInterfaceCapability fssInterfaceCapability;
    private NdcDomainCapability ndcDomainCapability;
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private NationalSettingDomainCapability nationalSettingDomainCapability;
    private ManagedItemCapability managedItemCapability;
    
    @Autowired
    private PlatformTransactionManager transactionManager;
    
    /**
     * synchronizedFSSUpdate.
     */
    public void synchronizedFSSUpdate() {
        runDate = new Date();
        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        
        try {
            
            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);
            String date = getRuntime();
            updateStatus(ProcessStatus.RUNNING);
            transactionManager.commit(status);
            
            Map<String, String> ndcMap = fssInterfaceCapability.getNdcsToUpdate(date);
            LOG.debug("There are " + ndcMap.size() + " NDCs to update.");
            int i = 0;
                
            for (Map.Entry<String, String> entry : ndcMap.entrySet()) {    
                String ndc = entry.getKey();
                

                try {
                    if (++i % PPSConstants.I100 == 0) {
                        LOG.debug("NDC-" + i + " of " + ndcMap.size());
                    }
                        
                    def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    def.setTimeout(TIMEOUT);
                    status = transactionManager.getTransaction(def);
                    NdcVo ndcVo = findNdc(ndc);
                    
          
                    if (ndcVo == null) {
                        LOG.debug("NDC: " + ndc + " updated in FSS but not in PPS.");
                    } else {
                        NdcVo fssVo = new NdcVo();
                        fssVo.setNdc(ndcVo.getNdc());
                        fssInterfaceCapability.getFssData(fssVo);

                        LOG.debug("Update NDC " + ndc + " in PPS.");
                        updateNdc(ndcVo, fssVo);
                    }
                } catch (Exception e) {
                    LOG.error(e.toString());
                } finally {
                    transactionManager.commit(status);
                } 
            }
        } catch (Exception e) {
            LOG.error("Ex1:" + e.getLocalizedMessage());
        } 
            
        def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        def.setTimeout(TIMEOUT);
        status = transactionManager.getTransaction(def);
        updateStatus(ProcessStatus.COMPLETED);
        updateRuntime();
        transactionManager.commit(status);
    }        
    
    /**
     * The updateRuntime method will update the Inactivation Check runtime date. 
     * @param processStatus ProcessStatus
     */
    private void updateStatus(ProcessStatus processStatus) {
       
        List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : settingsList) {
            if (NationalSetting.FSS_RUN_STATE.toString().equals(vo.getKeyName())) {
                vo.setStringValue(processStatus.toString());
                nationalSettingDomainCapability.update(vo, getUser());
            }
        }
    }
    
    /**
     * The updateRuntime method will update the Inactivation Check runtime date. 
     */
    private void updateRuntime() {
       
        
        List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : settingsList) {
            if (NationalSetting.FSS_UPDATE_LAST_RUN.toString().equals(vo.getKeyName())) {
                vo.setDateValue(runDate);
                nationalSettingDomainCapability.update(vo, getUser());
            }
        }
    }
    

      /**
     * The updateRuntime method will update the Inactivation Check runtime date. 
     * @return runTime
     */
    private String getRuntime() {
        List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : settingsList) {
            if (NationalSetting.FSS_UPDATE_LAST_RUN.toString().equals(vo.getKeyName())) {
                return dateFormat.format(vo.getDateValue());
            }
        }

        return dateFormat.format(new Date());
    }
    
    
    /**
     * updateNdc. 
     * @param fssVo fssVo
     * @param ndcVo ndcVo
     */
    public void updateNdc(NdcVo ndcVo, NdcVo fssVo) {
        
        LOG.debug("UpdateNdc " +  fssVo.getNdc());
        LOG.debug(ndcVo.getNdc() + ":" + ndcVo.getFssI() + ":" + ndcVo.getFssCntNo() + ":"
            + ndcVo.getFssBig4Price() + ":" + ndcVo.getFssFssPrice() + ":" + ndcVo.getFssVaPrice()
            + ":" + ndcVo.isFssPv() + ":" + ndcVo.getFssFssEnd());
        LOG.debug(fssVo.getNdc() + ":" + fssVo.getFssI() + ":" + fssVo.getFssCntNo() + ":"
            + fssVo.getFssBig4Price() + ":" + fssVo.getFssFssPrice() + ":" + fssVo.getFssVaPrice()
            + ":" + fssVo.isFssPv() + ":" + fssVo.getFssFssEnd());

        
       
        Collection<ItemAuditHistoryDetailsVo> details = new ArrayList<ItemAuditHistoryDetailsVo>();
        
        // If the FssBpaAvail is different.
        if (ndcVo.isFssBpaAvail() != fssVo.isFssBpaAvail()) {
            details.add(createIAHDetails("FssBpaAvail", FSS_PROCESS, String.valueOf(ndcVo.isFssBpaAvail()), 
                                         String.valueOf(fssVo.isFssBpaAvail())));
            ndcVo.setFssBpaAvail(fssVo.isFssBpaAvail());
        }
        
        
        // If the FssPv is different.
        if (ndcVo.isFssPv() != fssVo.isFssPv()) {
            details.add(createIAHDetails("FssPv", FSS_PROCESS, String.valueOf(ndcVo.isFssPv()), 
                                         String.valueOf(fssVo.isFssPv())));
            ndcVo.setFssPv(fssVo.isFssPv());
        }
        
        
        // If the FssBig4Price is different.
        if (ndcVo.getFssBig4Price() == null) {
            if (fssVo.getFssBig4Price() != null) {
                details.add(createIAHDetails("FssBig4Price ", FSS_PROCESS, NOVALUE, 
                                             String.valueOf(fssVo.getFssBig4Price())));
                ndcVo.setFssBig4Price(fssVo.getFssBig4Price());
            }
        } else if (!(ndcVo.getFssBig4Price().equals(fssVo.getFssBig4Price()))) {
            details.add(createIAHDetails("FssBig4Price", FSS_PROCESS, String.valueOf(ndcVo.getFssBig4Price()), 
                                         String.valueOf(fssVo.getFssBig4Price())));
            ndcVo.setFssBig4Price(fssVo.getFssBig4Price());
        }
        
        // If the FssBpaPrice is different.
        if (ndcVo.getFssBpaPrice() == null) {
            if (fssVo.getFssBpaPrice() != null) {
                details.add(createIAHDetails("FssBpaPrice ", FSS_PROCESS, NOVALUE, 
                                             String.valueOf(fssVo.getFssBpaPrice())));
                ndcVo.setFssBpaPrice(fssVo.getFssBpaPrice());
            }
        } else if (!(ndcVo.getFssBpaPrice().equals(fssVo.getFssBpaPrice()))) {
            details.add(createIAHDetails("FssBpaPrice", FSS_PROCESS, String.valueOf(ndcVo.getFssBpaPrice()), 
                                         String.valueOf(fssVo.getFssBpaPrice())));
            ndcVo.setFssBpaPrice(fssVo.getFssBpaPrice());
        }
        
        // If the FssCntNo is different.
        if (ndcVo.getFssCntNo() == null) {
            if (fssVo.getFssCntNo() != null) {
                details.add(createIAHDetails("FssCntNo ", FSS_PROCESS, NOVALUE, 
                                             String.valueOf(fssVo.getFssCntNo())));
                ndcVo.setFssCntNo(fssVo.getFssCntNo());
            }
        } else if (!(ndcVo.getFssCntNo().equals(fssVo.getFssCntNo()))) {
            details.add(createIAHDetails("FssCntNo", FSS_PROCESS, String.valueOf(ndcVo.getFssCntNo()), 
                                         String.valueOf(fssVo.getFssCntNo())));
            ndcVo.setFssCntNo(fssVo.getFssCntNo());
        }
        
        // If the FssEndDate is different.
        if (ndcVo.getFssFssEnd() == null) {
            if (fssVo.getFssFssEnd() != null) {
                details.add(createIAHDetails("FssEndDate ", FSS_PROCESS, NOVALUE, 
                                             String.valueOf(fssVo.getFssFssEnd())));
                ndcVo.setFssFssEnd(fssVo.getFssFssEnd());
            }
        } else if (!(ndcVo.getFssFssEnd().equals(fssVo.getFssFssEnd()))) {
            details.add(createIAHDetails("FssEndDate", FSS_PROCESS, String.valueOf(ndcVo.getFssFssEnd()), 
                                         String.valueOf(fssVo.getFssFssEnd())));
            ndcVo.setFssFssEnd(fssVo.getFssFssEnd());
        }
        
        updateNdc2(fssVo, ndcVo, details);
    }
        
    /**
     * updateNdc. 
     * @param fssVo fssVo
     * @param ndcVo ndcVo
     * @param details detalis
     */
    public void updateNdc2(NdcVo fssVo, NdcVo ndcVo,  Collection<ItemAuditHistoryDetailsVo> details) {
        
        
        // If the FssFssPrice is different.
        if (ndcVo.getFssFssPrice() == null) {
            if (fssVo.getFssFssPrice() != null) {
                details.add(createIAHDetails("FssFssPrice ", FSS_PROCESS, NOVALUE, 
                                             String.valueOf(fssVo.getFssFssPrice())));
                ndcVo.setFssFssPrice(fssVo.getFssFssPrice());
            }
        } else if (!(ndcVo.getFssFssPrice().equals(fssVo.getFssFssPrice()))) {
            details.add(createIAHDetails("FssFssPrice", FSS_PROCESS, String.valueOf(ndcVo.getFssFssPrice()), 
                                         String.valueOf(fssVo.getFssFssPrice())));
            ndcVo.setFssFssPrice(fssVo.getFssFssPrice());
        }
        
        // If the FssI is different.
        if (ndcVo.getFssI() == null) {
            if (fssVo.getFssI() != null) {
                details.add(createIAHDetails("FssI ", FSS_PROCESS, NOVALUE, 
                                             String.valueOf(fssVo.getFssI())));
                ndcVo.setFssI(fssVo.getFssI());
            }
        } else if (!(ndcVo.getFssI().equals(fssVo.getFssI()))) {
            details.add(createIAHDetails("FssI", FSS_PROCESS, String.valueOf(ndcVo.getFssI()), 
                                         String.valueOf(fssVo.getFssI())));
            ndcVo.setFssI(fssVo.getFssI());
        }
        
        // If the FssNcPrice is different.
        if (ndcVo.getFssNcPrice() == null) {
            if (fssVo.getFssNcPrice() != null) {
                details.add(createIAHDetails("FssNcPrice ", FSS_PROCESS, NOVALUE, 
                                             String.valueOf(fssVo.getFssNcPrice())));
                ndcVo.setFssNcPrice(fssVo.getFssNcPrice());
            }
        } else if (!(ndcVo.getFssNcPrice().equals(fssVo.getFssNcPrice()))) {
            details.add(createIAHDetails("FssNcPrice", FSS_PROCESS, String.valueOf(ndcVo.getFssNcPrice()), 
                                         String.valueOf(fssVo.getFssNcPrice())));
            ndcVo.setFssNcPrice(fssVo.getFssNcPrice());
        }

        
        // If the FssVaPrice is different.
        if (ndcVo.getFssVaPrice() == null) {
            if (fssVo.getFssVaPrice() != null) {
                details.add(createIAHDetails("FssVaPrice ", FSS_PROCESS, NOVALUE, 
                                             String.valueOf(fssVo.getFssVaPrice())));
                ndcVo.setFssVaPrice(fssVo.getFssVaPrice());
            }
        } else if (!(ndcVo.getFssVaPrice().equals(fssVo.getFssVaPrice()))) {
            details.add(createIAHDetails("FssVaPrice", FSS_PROCESS, String.valueOf(ndcVo.getFssVaPrice()), 
                                         String.valueOf(fssVo.getFssVaPrice())));
            ndcVo.setFssVaPrice(fssVo.getFssVaPrice());
        }

        if (details.size() > 0) {
            try {
                NdcVo returnedVo = ndcDomainCapability.update(ndcVo, getUser());
                createItemAuditHistory(EntityType.NDC, returnedVo.getId(), details); 
                    
            } catch (DuplicateItemException e) {
                LOG.error("Could not update the FSS data for " + ndcVo.getValue());
            }
        }
    }
    

    /**
     * createIAHDetails record
     * @param columnName columnName
     * @param message message
     * @param oldValue oldValue.
     * @param newValue newValue.
     * @return ItemAuditHistoryDetailsVo
     */
    private ItemAuditHistoryDetailsVo createIAHDetails(String columnName, String message, String oldValue, String newValue) {


        ItemAuditHistoryDetailsVo detail = new ItemAuditHistoryDetailsVo();
        detail.setColumnName(columnName);
        detail.setDetailReason(message);
        detail.setEventCategory(EventCategory.SYSTEM_EVENT);
        detail.setNewValue(newValue);
        detail.setOldValue(oldValue);
        detail.setCreatedBy(FSS_PROCESS);
        
        return detail;

    }


    /**
     * createItemAuditHistory record
     * @param type EntityType
     * @param id EPL_ID
     * @param details Collection<ItemAuditHistoryDetailsVo>
     */
    private void createItemAuditHistory(EntityType type, String id,  
                                        Collection<ItemAuditHistoryDetailsVo> details) {
        try {

            ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
            audit.setDetails(details);
            audit.setReason("FSS-UPDATE");
            audit.setAuditItemId(id);
            audit.setCreatedBy("FSS UPDATE");
            audit.setEventCategory(EventCategory.SYSTEM_EVENT);
            audit.setAuditItemType(type);
            audit.setSiteName(STATION);
            audit.setUsername(getUser().getUsername());
            itemAuditHistoryDomainCapability.create(audit, null, getUser());
        } catch (Exception e) {
            String str =
                    "Exception with audit history record of type type " + type + " and id of " + id + " the exception is "
                            + e.getMessage();
            LOG.debug(str);
        }
    }
    
    
    /**
     * getUser
     * @return UserVo
     */
    private UserVo getUser() {
        
        UserVo user = new UserVo();
        user.setFirstName("FSS");
        user.setLastName("Update");
        user.setStationNumber(STATION);
        user.setUsername("FSS");
        user.setLocation(STATION);
        user.setId(USER_ID);

        user.addRole(Role.PSS_PPSN_MANAGER /*NATIONAL_SERVICE_MANAGER*/);

        return user;

    }
    
    /**
     *  findNdcis used to find an NDC based on the input from FDB 
     * @param ndc the incoming ProductVo
     * @return the populate ProductVo or null if unsuccessful
     */
    private NdcVo findNdc(String ndc) {
        
        if (StringUtils.isBlank(ndc)) {
            LOG.debug("ERROR: AutoAddUpdateCapability.findPNdc ndc is blank.");
        }

        // Check if NDC exists
        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.ADVANCED, Environment.NATIONAL);

        // Create the SearchTems
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();

        //  Find NDC Item status of inactive and active
        searchCriteria.setItemStatus(new ArrayList<ItemStatus>());
        ItemStatus itemStatusActive = ItemStatus.valueOf(PPSConstants.ACTIVE);
        ItemStatus itemStatusInactive = ItemStatus.valueOf(PPSConstants.INACTIVE);
        searchCriteria.getItemStatus().add(itemStatusActive);
        searchCriteria.getItemStatus().add(itemStatusInactive);

        // Find NDC Request status of approved, rejected or pending
        searchCriteria.setRequestStatus(new ArrayList<RequestItemStatus>());
        RequestItemStatus requestItemStatusApproved = RequestItemStatus.valueOf(PPSConstants.APPROVED);
        RequestItemStatus requestItemStatusRejected = RequestItemStatus.valueOf(PPSConstants.REJECTED);
        RequestItemStatus requestItemStatusPending = RequestItemStatus.valueOf(PPSConstants.PENDING);
        searchCriteria.getRequestStatus().add(requestItemStatusRejected);
        searchCriteria.getRequestStatus().add(requestItemStatusApproved);
        searchCriteria.getRequestStatus().add(requestItemStatusPending);

        //  FssCapability.FindNDC Not only local use
        searchCriteria.setLocalUse(LocalUseSearchType.ALL_ITEMS);

        // Use the and search
        searchCriteria.setAdvancedAndSearch(true);

        //  FssCapability.FindNDC add the search terms
        searchTerms.add(new SearchTermVo(EntityType.NDC, FieldKey.NDC, ndc));
        searchCriteria.setSearchTerms(searchTerms);
        searchCriteria.setEntityType(EntityType.NDC);
        searchCriteria.setSortedFieldKey(FieldKey.NDC);

        try {
            if (managedItemCapability == null) {
                LOG.debug("managedItemCapability is null!!!!");
            }
            
            
            List<ManagedItemVo> searchResults = managedItemCapability.search(searchCriteria);

            if (searchResults == null || searchResults.size() < 1) {
                LOG.debug("NDC " + ndc + " not found in PPS.");
            } else if (searchResults.size() == 1) {
                LOG.debug("1 NDC found.");
                NdcVo ndcVo = (NdcVo) searchResults.get(0);
                ndcVo = ndcDomainCapability.retrieve(ndcVo.getId());

                return ndcVo;
            } 

        } catch (Exception e) {
            LOG.debug("FindNdc Exception for ndc " + ndc + ". Error is " + e.toString());
            StackTraceElement[] stack = e.getStackTrace();
          
            if (stack == null) {
                LOG.debug("Stack is NULL.");
            } else {
                for (int i = 0; i < stack.length; i++) {
                    LOG.debug(stack[i].toString());
                }
            }
        }

        LOG.debug("No NDC found!!!");

        return null;
    }
    
    /**
     * setItemAuditHistoryDomainCapability.
     * @param itemAuditHistoryDomainCapability the itemAuditHistoryDomainCapability to set
     */
    public void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability) {
        this.itemAuditHistoryDomainCapability = itemAuditHistoryDomainCapability;
    }
    
    /**
     * setFssInterfaceCapability.
     * @param fssInterfaceCapability the fssInterfaceCapability to set
     */
    public void setFssInterfaceCapability(FssInterfaceCapability fssInterfaceCapability) {
        this.fssInterfaceCapability = fssInterfaceCapability;
    }
    
    /**
     * setNdcDomainCapability.
     * @param ndcDomainCapability the ndcDomainCapability to set
     */
    public void setNdcDomainCapability(NdcDomainCapability 
                                                           ndcDomainCapability) {
        this.ndcDomainCapability = ndcDomainCapability;
    }

   
    /**
     * setManagedItemCapability.
     * @param managedItemCapability the managedItemCapability to set
     */
    public void setManagedItemCapability(ManagedItemCapability 
                                         managedItemCapability) {
        this.managedItemCapability = managedItemCapability;
    }
    
    /**
     * setNationalSettingDomainCapability.
     * @param nationalSettingDomainCapability the nationalSettingDomainCapability to set
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability 
                                                   nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }
    
    /**
     * gets the TransactionManager
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * sets the TransactionManager
     * @param transactionManager the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
}

