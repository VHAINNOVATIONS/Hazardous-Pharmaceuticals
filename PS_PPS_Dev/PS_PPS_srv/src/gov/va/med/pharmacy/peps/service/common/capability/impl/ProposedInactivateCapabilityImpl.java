/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderableItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ResetNationalDatabaseDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ProposedInactivateCapability;
import gov.va.med.pharmacy.peps.service.common.scheduler.ProcessStatus;


/**
 * StsCapability is the implementation of the STSCapabilty class
 *
 */
public class ProposedInactivateCapabilityImpl implements ProposedInactivateCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(ProposedInactivateCapabilityImpl.class);
    private static final int TIMEOUT = 600;
    private static final Long USER_ID = 42L;
    private static final String STATION = "999";

    private ManagedItemCapability managedItemCapability;
    private NdcDomainCapability ndcDomainCapability;
    private ProductDomainCapability productDomainCapability;
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private RequestDomainCapability requestDomainCapability;
    private NationalSettingDomainCapability nationalSettingDomainCapability;
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * synchronizedFDBUpdate.
     */
    public void synchronizedProposedInactivationDate() {

        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;

        try {

            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);

            updateStatus(ProcessStatus.RUNNING);

            List<Long> ndcList = resetNationalDatabaseDomainCapability.getIdsPropInact(EntityType.NDC);
            
            if (ndcList == null) {
                LOG.debug("There are no ndcs to inactivate.");
            } else {
                LOG.debug("There are " + ndcList.size() + " ndcs. ");
            }
            
            for (Long ndc : ndcList) {
                inactivateNdc(ndc);
            }

            transactionManager.commit(status);
            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);
            
            List<Long> productList = resetNationalDatabaseDomainCapability.getIdsPropInact(EntityType.PRODUCT);
            
            if (productList == null) {
                LOG.debug("There are no products to inactivate.");
            } else {
                LOG.debug("There are " + productList.size() + " products. ");
            }

            for (Long product : productList) {
                inactivateProduct(product);
            }

            transactionManager.commit(status);
            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);
            
            List<Long> orderableList = resetNationalDatabaseDomainCapability.getIdsPropInact(EntityType.ORDERABLE_ITEM);

            if (orderableList == null) {
                LOG.debug("There are no OIS to inactivate.");
            } else {
                LOG.debug("There are " + orderableList.size() + " OIS. ");
            }
            
            for (Long orderable : orderableList) {
                inactivateOrderable(orderable);
            }
            
            transactionManager.commit(status);
            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);

            LOG.debug("Before delete completed requests");
            
            // as part of the proposed inactivation task we will also clear out completed requested.
            requestDomainCapability.deleteCompletedRequest();
            LOG.debug("after delete completed requests");
            
            transactionManager.commit(status);
            LOG.debug("after delete completed commit.");
            
        } catch (TransactionException e) {
            LOG.error("TransactionException, in StsCapabilityImpl.synchronizedFDBUpdate()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("The Underlying cause is " + e.getCause().getMessage());
            }

            LOG.error("TransactionException, in StsCapabilityImpl.synchronizedFDBUpdate()");
        } catch (Exception e) {
            LOG.debug("Nont transaction exception in ProposedInactivation: " + e.toString());
        }
        
        try {
            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            
            status = transactionManager.getTransaction(def);
            
            updateRuntime();
            updateStatus(ProcessStatus.COMPLETED);

            transactionManager.commit(status);

        } catch (TransactionException e) {
            LOG.error(" TransactionException, in StsCapabilityImpl.synchronizedFDBUpdate()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error(" The Underlying cause is " + e.getCause().getMessage());
            }

            LOG.error(" TransactionException, in StsCapabilityImpl.synchronizedFDBUpdate()");
        }

    }

    /**
     * The updateRuntime method will update the Inactivation Check runtime date. 
     * @param processStatus ProcessStatus
     */
    private void updateStatus(ProcessStatus processStatus) {

        List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : settingsList) {
            if (NationalSetting.INACTIVATION_RUN_STATE.toString().equals(vo.getKeyName())) {
                vo.setStringValue(processStatus.toString());
                nationalSettingDomainCapability.update(vo, getUser());
            }
        }
    }

    /**
     * inactivateNdc. 
     * @param ndc ndc
     */
    private void inactivateNdc(Long ndc) {

        // Inactivate the NDC.
        try {
            NdcVo ndcVo = ndcDomainCapability.retrieve(ndc.toString());

            DataField<Date> proposedInativationDate = 
                ndcVo.getVaDataFields().getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);
            
            if (proposedInativationDate.getValue() == null || 
                proposedInativationDate.getValue().before(new Date())) {
            
                ndcVo.setInactivationDate(new Date());
                ndcVo.setItemStatus(ItemStatus.INACTIVE);
                ndcVo.getVaDataFields().getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(null);
                ndcDomainCapability.update(ndcVo, getUser());
    
                createItemAuditHistory(EntityType.NDC, ndc.toString(),
                    "NDC Inactivated based on proposed Inacativation being reached!", "", "");
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

    }

    /**
     * inactivateProduct. 
     * @param product product
     */
    private void inactivateProduct(Long product) {

        // Inactivate the product.
        try {

            // 1. need to check if product is already on the pending list, if is then ignore this and do nothing.
            Collection<RequestVo> requestList = requestDomainCapability.retrieve(product.toString(), EntityType.PRODUCT);

            if (requestList == null || requestList.size() > 0) {

                return;
            }

            ProductVo productVo = productDomainCapability.retrieve(product.toString());
            ProductVo oldProductVo = productVo.copy();

            
            DataField<Date> proposedInativationDate = 
                productVo.getVaDataFields().getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);
            
            if (proposedInativationDate.getValue() == null || 
                proposedInativationDate.getValue().before(new Date())) {
            
                // 2. Now, update the product by adding it to the pending request list properly.    
    
                productVo.setInactivationDate(new Date());
                productVo.setItemStatus(ItemStatus.INACTIVE);
                productVo.getVaDataFields().getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(null);
    
                Collection<ModDifferenceVo> modDiffs = oldProductVo.compareDifferences(productVo);
    
                for (ModDifferenceVo modDiff : modDiffs) {
                    modDiff.setAcceptChange(true);
                    modDiff.setComments("Proposed Inactivation Date reached");
                    modDiff.setModificationReason("Proposed Inactivation Date has been reached.");
                }
    
                managedItemCapability.commitModifications(modDiffs, oldProductVo, getUser());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

    }

    /**
     * inactivateOrderable. 
     * @param orderable orderable
     */
    private void inactivateOrderable(Long orderable) {

        // Inactivate the product.
        try {

            // 1. need to check if product is already on the pending list, if is then ignore this and do nothing.
            Collection<RequestVo> requestList = requestDomainCapability.retrieve(orderable.toString(),
                                                                                 EntityType.ORDERABLE_ITEM);

            if (requestList == null || requestList.size() > 0) {

                return;
            }

            // 2. Now, update the product by adding it to the pending request list properly.    
            OrderableItemVo orderableItemVo = orderableItemDomainCapability.retrieve(orderable.toString());
            OrderableItemVo oldOrderableItemVo = orderableItemVo.copy();

            DataField<Date> proposedInativationDate = 
                orderableItemVo.getVaDataFields().getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);
            
            if (proposedInativationDate.getValue() == null || 
                proposedInativationDate.getValue().before(new Date())) {
            
                orderableItemVo.setInactivationDate(new Date());
                orderableItemVo.setItemStatus(ItemStatus.INACTIVE);
                orderableItemVo.getVaDataFields().getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(null);
    
                Collection<ModDifferenceVo> modDiffs = oldOrderableItemVo.compareDifferences(orderableItemVo);
    
                for (ModDifferenceVo modDiff : modDiffs) {
                    modDiff.setAcceptChange(true);
                    modDiff.setComments("Proposed Inactivation Date reached");
                    modDiff.setModificationReason("Proposed Inactivation Date has been reached.");
                }
    
                managedItemCapability.commitModifications(modDiffs, oldOrderableItemVo, getUser());
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

    }

    /**
     * The updateRuntime method will update the Inactivation Check runtime date. 
     */
    private void updateRuntime() {

        List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : settingsList) {
            if (NationalSetting.INACTIVATION_CHECK_LAST_RUN.toString().equals(vo.getKeyName())) {
                vo.setDateValue(new Date());
                nationalSettingDomainCapability.update(vo, getUser());
            }
        }
    }

    /**
     * getUser
     * @return UserVo
     */
    private UserVo getUser() {

        UserVo user = new UserVo();
        user.setFirstName("PropInact");
        user.setLastName("Update");
        user.setStationNumber(STATION);
        user.setUsername("ProposedInactivationReached");
        user.setLocation(STATION);
        user.setId(USER_ID);

        user.addRole(Role.PSS_PPSN_MANAGER /*NATIONAL_SERVICE_MANAGER*/);

        return user;

    }

    /**
     * createItemAuditHistory record with the ProposedInactiveCapablityImpl.java
     * @param type EntityType
     * @param id EPL_ID
     * @param message message.
     * @param oldValue oldValue.
     * @param newValue newValue.
     */
    private void createItemAuditHistory(EntityType type, String id, String message, String oldValue, String newValue) {
        try {
            ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
            audit.setReason(message);
            audit.setAuditItemId(id);
            audit.setCreatedBy("MEDROUTE UPDATE");
            audit.setEventCategory(EventCategory.SYSTEM_EVENT);
            audit.setAuditItemType(type);
            audit.setOldValue(oldValue);
            audit.setNewValue(newValue);
            audit.setSiteName(STATION);
            audit.setUsername(getUser().getUsername());
            itemAuditHistoryDomainCapability.create(audit, null, getUser());
        } catch (Exception e) {
            String str =
                    "Exception with audit history record of type type " + type + " and id of " + id + " the exception is "
                            + e.getMessage();
            LOG.debug("ProposedInactiveCapabilityImp: " + str);
        }
    }

    /**
     * setItemAuditHistoryDomainCapability in ProposedInactivateCapabilityImpl.
     * @param itemAuditHistoryDomainCapability the itemAuditHistoryDomainCapability to set
     */
    public void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability) {
        this.itemAuditHistoryDomainCapability = itemAuditHistoryDomainCapability;
    }

    /**
     * setManagedItemCapability.
     * @param managedItemCapability the managedItemCapability to set
     */
    public void setManagedItemCapability(ManagedItemCapability managedItemCapability) {
        this.managedItemCapability = managedItemCapability;
    }

    /**
     * setNationalSettingDomainCapability in ProposedInactivateCapabilityImpl.
     * @param nationalSettingDomainCapability the nationalSettingDomainCapability to set
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability
                                                   nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }

    /**
     * setNdcDomainCapability in ProposedInactivateCapabilityImpl.
     * @param ndcDomainCapability the ndcDomainCapability to set
     */
    public void setNdcDomainCapability(NdcDomainCapability
                                       ndcDomainCapability) {
        this.ndcDomainCapability = ndcDomainCapability;
    }

    /**
     * setRequestDomainCapability.
     * @param requestDomainCapability the requestDomainCapability to set
     */
    public void setRequestDomainCapability(RequestDomainCapability
                                           requestDomainCapability) {
        this.requestDomainCapability = requestDomainCapability;
    }

    /**
     * setProductDomainCapability.
     * @param productDomainCapability the productDomainCapability to set
     */
    public void setProductDomainCapability(ProductDomainCapability
                                           productDomainCapability) {
        this.productDomainCapability = productDomainCapability;
    }

    /**
     * setOrderableItemDomainCapability.
     * @param orderableItemDomainCapability the orderableItemDomainCapability to set
     */
    public void setOrderableItemDomainCapability(OrderableItemDomainCapability
                                                 orderableItemDomainCapability) {
        this.orderableItemDomainCapability = orderableItemDomainCapability;
    }

    /**
    * setResetNationalDatabaseDomainCapability.
    * @param resetNationalDatabaseDomainCapability the resetNationalDatabaseDomainCapability to set
    */
    public void setResetNationalDatabaseDomainCapability(ResetNationalDatabaseDomainCapability
                                                         resetNationalDatabaseDomainCapability) {
        this.resetNationalDatabaseDomainCapability = resetNationalDatabaseDomainCapability;
    }

    /**
     * gets the TransactionManager for the ProposedInactivateCapabilityImpl.
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * sets the TransactionManager for the ProposedInactivateCapabilityImpl.
     * @param transactionManager the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
