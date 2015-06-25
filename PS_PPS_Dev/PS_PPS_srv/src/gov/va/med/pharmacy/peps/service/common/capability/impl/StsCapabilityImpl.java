/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.utility.ConfigFileUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryDetailsVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.StandardMedRouteDomainCapability;
import gov.va.med.pharmacy.peps.external.common.sts.outbound.capability.StsInterfaceCapabilityStub;
import gov.va.med.pharmacy.peps.external.common.sts.outbound.capability.impl.StsInterfaceCapabilityImpl;
import gov.va.med.pharmacy.peps.service.common.capability.StsCapability;
import gov.va.med.pharmacy.peps.service.common.scheduler.ProcessStatus;


/**
 * StsCapability is the implementation of the STSCapabilty class
 *
 */
public class StsCapabilityImpl implements StsCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(AutoAddUpdateCapabilityImpl.class);
    private static final Long USER_ID = 42L;
    private static final String STATION = "999";
    private static final int TIMEOUT = 600;

    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private NationalSettingDomainCapability nationalSettingDomainCapability;

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * synchronizedFDBUpdate.
     */
    public void synchronizedFDBUpdate() {

        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;

        try {

            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);

            this.updateStatus(ProcessStatus.RUNNING);

            ConfigFileUtility configFile = new ConfigFileUtility();

            List<StandardMedRouteVo> stsList;

            // At SwRI, there is no network access to the interface web service so populate the list from the stub
            if (configFile.isSwri()) {
                StsInterfaceCapabilityStub interfaceCapability = new StsInterfaceCapabilityStub();
                stsList = interfaceCapability.getStsData();
            } else {
                StsInterfaceCapabilityImpl interfaceCapability = new StsInterfaceCapabilityImpl();
                stsList = interfaceCapability.getStsData();
            }

            List<StandardMedRouteVo> ppsList = standardMedRouteDomainCapability.retrieve();

            // Now that I have both lists I need to iterate through the stsList to ensure each item on that list is in PPS
            if (stsList != null && stsList.size() > 0) {
                for (StandardMedRouteVo stsVo : stsList) {
                    boolean itemFound = false;

                    for (StandardMedRouteVo ppsVo : ppsList) {

                        // we have a match. If the values are the same check the current status
                        if (stsVo.getValue().equals(ppsVo.getValue())) {
                            itemFound = true;

                            // If the item status is different, we need to update
                            if (stsVo.getItemStatus() != ppsVo.getItemStatus()) {
                                updateItemStatus(stsVo, ppsVo);
                            }

                            // if the item has a different VUID then updateVUID
                            if (stsVo.getVuid() != null) {
                                if (!(stsVo.getVuid().equals(ppsVo.getVuid()))) {
                                    updateItemVuid(stsVo, ppsVo);
                                }
                            }

                            break;
                        }
                    }

                    // If this item isn't currently in PPS. add it.
                    if (!itemFound) {
                        addItem(stsVo);
                    }
                }
            }

            updateStatus(ProcessStatus.COMPLETED);
            updateRuntime();

            transactionManager.commit(status);

        } catch (TransactionException e) {
            LOG.error("TransactionException, in StsCapabilityImpl.synchronizedFDBUpdate()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("The Underlying cause is " + e.getCause().getMessage());
            }

            LOG.error("TransactionException, in StsCapabilityImpl.synchronizedFDBUpdate()");

        }
    }

    /**
     * The updateRuntime method will update the Inactivation Check runtime date. 
     * @param processStatus ProcessStatus
     */
    private void updateStatus(ProcessStatus processStatus) {

        List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : settingsList) {
            if (NationalSetting.STS_RUN_STATE.toString().equals(vo.getKeyName())) {
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
            if (NationalSetting.STS_UPDATE_LAST_RUN.toString().equals(vo.getKeyName())) {
                vo.setDateValue(new Date());
                nationalSettingDomainCapability.update(vo, getUser());
            }
        }
    }

    /**
     * updateItemStatus. 
     * @param stsVo stsVo
     * @param ppsVo ppsVo
     */
    public void updateItemStatus(StandardMedRouteVo stsVo, StandardMedRouteVo ppsVo) {

        // Not really sure if this method is needed.  If STS inactivates a standard med route then, I probably
        // should inactivate in PPS-N, however, this functionality will wait till PPS-N 2.0.
    }

    /**
     * updateItemVUID. 
     * @param stsVo stsVo
     * @param ppsVo ppsVo
     */
    public void updateItemVuid(StandardMedRouteVo stsVo, StandardMedRouteVo ppsVo) {
        String oldValue = ppsVo.getVuid();
        LOG.debug("Update vuid of med route " + ppsVo.getValue() + " from " + ppsVo.getVuid()
                  + " to " + stsVo.getVuid());
        ppsVo.setVuid(stsVo.getVuid());
        ppsVo.setRevisionNumber(ppsVo.getRevisionNumber() + 1);
        ppsVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        if (StringUtils.isEmpty(ppsVo.getFirstDatabankMedRoute())) {
            ppsVo.setFirstDatabankMedRoute("No FDB Route");
        }

        try {
            StandardMedRouteVo returned = standardMedRouteDomainCapability.update(ppsVo, getUser());
            createItemAuditHistory(EntityType.STANDARD_MED_ROUTE, returned.getId(), "VUID updated.",
                oldValue, ppsVo.getVuid());
        } catch (DuplicateItemException e) {
            LOG.error("Could not update standard med route. " + ppsVo.getValue());
        }
    }

    /**
     * addItem. 
     * @param stsVo stsVo
     */
    public void addItem(StandardMedRouteVo stsVo) {

        LOG.debug("add med route " + stsVo.getValue() + "  " + stsVo.getVuid());
        stsVo.setMasterEntryForVuid(true);
        stsVo.setFirstDatabankMedRoute("UNK");
        stsVo.setItemStatus(ItemStatus.ACTIVE);
        stsVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        stsVo.setRevisionNumber(1);

        VuidStatusHistoryVo vuidHistoryVo = new VuidStatusHistoryVo();
        vuidHistoryVo.setEffectiveDateTime(new Date(System.currentTimeMillis()));
        vuidHistoryVo.setVuid(Long.valueOf(stsVo.getVuid()));
        vuidHistoryVo.setItemStatus(ItemStatus.ACTIVE);
        vuidHistoryVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        vuidHistoryVo.setRevisionNumber(1);
        List<VuidStatusHistoryVo> effectiveDates = new ArrayList<VuidStatusHistoryVo>();
        effectiveDates.add(vuidHistoryVo);
        stsVo.setEffectiveDates(effectiveDates);

        try {
            StandardMedRouteVo returnedVo = standardMedRouteDomainCapability.create(stsVo, getUser());
            createItemAuditHistory(EntityType.STANDARD_MED_ROUTE, returnedVo.getId(),
                "Created from STS Service.", "", "");
        } catch (DuplicateItemException e) {
            LOG.error("Could not create standard med route. " + stsVo.getValue());
        }
    }

    /**
     * Retrieves the default User VO for STS Updates
     * @return UserVo
     */
    private UserVo getUser() {

        UserVo user = new UserVo();
        user.setFirstName("MedRoute");
        user.setLastName("Update");
        user.setStationNumber(STATION);
        
        // set the user name to something memorable
        user.setUsername("StdMedRouteUpdate");
        user.setLocation(STATION);
        user.setId(USER_ID);

        user.addRole(Role.PSS_PPSN_MANAGER /*NATIONAL_SERVICE_MANAGER*/);

        return user;

    }

    /**
     * createItemAuditHistory record
     * @param type EntityType
     * @param id EPL_ID
     * @param message message.
     * @param oldValue oldValue.
     * @param newValue newValue.
     */
    private void createItemAuditHistory(EntityType type, String id, String message, String oldValue, String newValue) {
        try {

            ItemAuditHistoryVo audit = new ItemAuditHistoryVo();

            if (StringUtils.isNotBlank(oldValue) && (StringUtils.isNotBlank(newValue))) {
                ItemAuditHistoryDetailsVo detail = new ItemAuditHistoryDetailsVo();
                Collection<ItemAuditHistoryDetailsVo> details = new ArrayList<ItemAuditHistoryDetailsVo>();
                detail.setColumnName("VUID");
                detail.setDetailReason(message);
                detail.setEventCategory(EventCategory.SYSTEM_EVENT);
                detail.setNewValue(newValue);
                detail.setOldValue(oldValue);
                detail.setCreatedBy("STS Update");
                details.add(detail);
                audit.setDetails(details);
            }

            audit.setReason(message);
            audit.setAuditItemId(id);
            audit.setCreatedBy("MEDROUTE UPDATE");
            audit.setEventCategory(EventCategory.SYSTEM_EVENT);
            audit.setAuditItemType(type);
            audit.setUsername(getUser().getUsername());
            audit.setSiteName(STATION);
            itemAuditHistoryDomainCapability.create(audit, null, getUser());
        } catch (Exception e) {
            String str =
                    "Exception with audit history record of type type " + type + " and id of " + id + " the exception is "
                            + e.getMessage();
            LOG.debug(str);
        }
    }

    /**
     * setItemAuditHistoryDomainCapability.
     * @param itemAuditHistoryDomainCapability the itemAuditHistoryDomainCapability to set
     */
    public void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability) {
        this.itemAuditHistoryDomainCapability = itemAuditHistoryDomainCapability;
    }

    /**
     * setStandardMedRouteDomainCapability.
     * @param standardMedRouteDomainCapability the standardMedRouteDomainCapability to set
     */
    public void setStandardMedRouteDomainCapability(StandardMedRouteDomainCapability
        standardMedRouteDomainCapability) {
        this.standardMedRouteDomainCapability = standardMedRouteDomainCapability;
    }

    /**
     * setNationalSettingDomainCapability for StsCapabilityImpl.
     * @param nationalSettingDomainCapability the nationalSettingDomainCapability to set
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability
                                                   nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }

    /**
     * gets the TransactionManager for StsCapabilityImpl.
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * sets the TransactionManager for StsCapabilityImpl.
     * @param transactionManager the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
