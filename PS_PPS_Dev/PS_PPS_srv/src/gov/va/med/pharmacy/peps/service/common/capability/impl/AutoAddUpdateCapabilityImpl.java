/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.FdbProductVo;
import gov.va.med.pharmacy.peps.common.vo.FdbUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryDetailsVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.NdfSynchQueueVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAddDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAutoAddDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAutoUpdateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbNdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbUpdateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdfSynchQueueDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceAutoCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaFileSynchCapability;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.service.common.capability.AutoAddUpdateCapability;
import gov.va.med.pharmacy.peps.service.common.capability.FdbUpdateProcessCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;


/**
 * AutoAddUpdateCapabilityImpl.
 *
 */
public class AutoAddUpdateCapabilityImpl implements AutoAddUpdateCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(AutoAddUpdateCapabilityImpl.class);

    private static final Long USER_ID = 42L;
    private static final int TIMEOUT = 600;
    private static final String TO = " to ";

    private SimpleDateFormat dateFormat = new SimpleDateFormat(
        "yyyyMMdd", Locale.US);

    private DrugReferenceCapability drugReferenceCapability;
    private DrugReferenceAutoCapability drugReferenceAutoCapability;
    private ManagedItemCapability managedItemCapability;
    private NdcDomainCapability ndcDomainCapability;
    private ProductDomainCapability productDomainCapability;
    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private VistaFileSynchCapability vistaFileSynchCapability;

    private FdbUpdateProcessCapability fdbUpdateProcessCapablity;
    private FdbAutoUpdateDomainCapability fdbAutoUpdateDomainCapability;
    private FdbAutoAddDomainCapability fdbAutoAddDomainCapability;
    private FdbAddDomainCapability fdbAddDomainCapability;
    private FdbUpdateDomainCapability fdbUpdateDomainCapability;
    private NationalSettingDomainCapability nationalSettingDomainCapability;

    private FdbNdcDomainCapability fdbNdcDomainCapability;
    private FdbProductDomainCapability fdbProductDomainCapability;
    private NdfSynchQueueDomainCapability ndfSynchQueueDomainCapability;

    private PlatformTransactionManager transactionManager;

    /**
     * default constructor
     */
    public AutoAddUpdateCapabilityImpl() {

    }

    /**
     * getUser
     * @return UserVo
     */
    private UserVo getUser() {
        UserVo user = new UserVo();
        user.setFirstName("FDBAuto");
        user.setLastName("AddProcess");
        user.setStationNumber("999");
        user.setUsername("FdbAutoAddProcess");
        user.setLocation("NationalSite");
        user.setId(USER_ID);
        user.addRole(Role.PSS_PPSN_MANAGER);

        return user;

    }

    /**
     * getStartDate.
     * @param dateType dateType
     * @param runType runType
     * @return Date
     */
    private Date getStartDate(NationalSetting dateType, NationalSetting runType) {

        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        Date date = null;

        try {

            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);
            List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

            for (NationalSettingVo vo : settingsList) {
                if (dateType.toString().equals(vo.getKeyName())) {
                    date = vo.getDateValue();
                }

                if (runType.toString().equals(vo.getKeyName())) {
                    vo.setStringValue("RUNNING"); 
                    nationalSettingDomainCapability.update(vo, getUser());
                }

            }
            
            transactionManager.commit(status);

        } catch (TransactionException e) {
            LOG.error("TransactionException, in getStartDateNationalSettings()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("The Underlying cause is  " + e.getCause().getMessage());
            }
        } catch (Exception e) {
            LOG.error("Exception getting National Settings for  " + dateType
                   + ":" + e.getMessage());
        }
        
       

        return date;

    }

    /**
     * setStartDate.
     * @param dateType dateType dateType
     * @param runType runType
     * @param date date
     */
    private void setStartDate(NationalSetting dateType, Date date, NationalSetting runType) {
        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;

        try {
            def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            status = transactionManager.getTransaction(def);

            List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

            for (NationalSettingVo vo : settingsList) {
                if (dateType.toString().equals(vo.getKeyName())) {
                    vo.setDateValue(date);
                    nationalSettingDomainCapability.update(vo, getUser());
                }

                if (runType.toString().equals(vo.getKeyName())) {
                    vo.setStringValue("COMPLETED");
                    nationalSettingDomainCapability.update(vo, getUser());
                }

            }

            transactionManager.commit(status);

        } catch (TransactionException e) {
            LOG.error("TransactionException, in setStartDate()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error(" The Underlying cause is " + e.getCause().getMessage());
            }
        } catch (Exception e) {
            LOG.error(" Exception getting National Settings for " + dateType
                   + ":" + e.getMessage());
        }

    }

    /**
     * updateLatestDate.
     * @param fdbStringDate fdbStringDate
     * @param latestDate date
     * @return date
     */
    private Date updateLatestDate(Date latestDate, String fdbStringDate) {

        try {
            Date fdbDate = dateFormat.parse(fdbStringDate);

            if (fdbDate.after(latestDate)) {
                return fdbDate;
            }
        } catch (Exception e) {
            LOG.error("Exception parsing FDB Date: " + e.getMessage());
        }

        return latestDate;
    }

    /**
     * autoAddProcess
     */
    public void synchronizeFdbAdd() {
        
        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        
        removeAddLaterThan90Days();
        Date startDate = getStartDate(NationalSetting.FDB_ADD_LAST_RUN, NationalSetting.FDB_ADD_RUN_STATE);
        Date latestDate = new Date(startDate.getTime());

        LOG.error("Start synchronizeFdbAdd from " + startDate.toString());

        List<FdbAutoAddVo> list = drugReferenceAutoCapability.getFdbAddedItems(startDate);
        int total = list.size();
        int i = 0;

        // for each item in the list, we will either add it to the database if it is not already in the 
        // database
        for (FdbAutoAddVo autoAddVo : list) {

            try {

                latestDate = updateLatestDate(latestDate, autoAddVo.getAddDate());

                LOG.error("Process NDC " + autoAddVo.getNdc() + " is  " + (++i) + " of  " + total);

                def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setTimeout(TIMEOUT);
                status = transactionManager.getTransaction(def);
                NdcVo ndcVo = findNdc(autoAddVo.getNdc());

                // if ndcVo is null then the NDC is not in the database so add it.
                if (ndcVo == null) {
                    
                 
                    
                    List<ProductVo> productList = findProduct(String.valueOf(autoAddVo.getGcnSeqno()), false);

                    FdbAddVo fdbAddVo = drugReferenceCapability.populateFdbAddVoFields(autoAddVo.getNdc());

                    // If the productVo is null then there is no single match so we need to add
                    // the fdb item to the Queue, otherwise add it to the database and the report.
                    if (productList == null || productList.size() == 0 || productList.size() > 1) {

                        // This FDB item cannot be auto added to the update queue because either there
                        // are no matches or there are mulitple matches and we can't figure out which one.
                        // so add it to the FDB Add Queue.
                        
                        fdbAddVo = this.fdbAddDomainCapability.create(fdbAddVo, getUser());
                    } else {
                        for (ProductVo productVo : productList) {
                            
                            LOG.debug("product found:" + productVo.getVaProductName() + ":" + productVo.getId());

                            // We have a new item from FDB we can link to a product so
                            // add to NDC table , and add it to the report

                            // This call will add the FDB item 
                            ndcVo = fdbUpdateProcessCapablity.createNdcFromFdb(autoAddVo.getNdc(), getUser());

                            if (ndcVo == null) {
                                LOG.debug("Could not create NDC() " + autoAddVo.getNdc());
                            } else {
                                ndcVo.setProduct(productVo);
                                ndcVo.setCategories(productVo.getCategories());
                                ndcVo.setSubCategories(productVo.getSubCategories());

                                if (vistaFileSynchCapability.sendNewItemToVista(ndcVo, getUser(), 
                                    okToSendToVista(), catchingUp())) {
                                    addToQueue(ndcVo, ndcVo.getEntityType(), ItemAction.ADD, getUser());
                                }
                                
                                ndcVo = ndcDomainCapability.create(ndcVo, getUser());
                                ndcVo.getFdbNdcVo().setNdcIdFk(Long.valueOf(ndcVo.getId()));

                                fdbNdcDomainCapability.create(ndcVo.getFdbNdcVo(), getUser());

                                createItemAuditHistory(EntityType.NDC, ndcVo.getId(),
                                    "Created by AutoAdd process.", "", "", "");
                                autoAddVo.setVaProductName(productVo.getVaProductName());
                                autoAddVo.setFdbProductName(fdbAddVo.getFdbProductName());
                                autoAddVo.setLabelName(fdbAddVo.getLabelName());
                                autoAddVo.setAddDesc(fdbAddVo.getAddDesc());
                                autoAddVo.setNdcIdFk(Long.valueOf(ndcVo.getId()));
                                autoAddVo.setPackageSize(ndcVo.getPackageSize());
                                autoAddVo.setPackageType(ndcVo.getPackageType().getValue());

                                autoAddVo = fdbAutoAddDomainCapability.create(autoAddVo, getUser());
                            }
                        }
                    }
                } else {
                    LOG.error("NDC already in database");
                }

            } catch (TransactionException e) {
                LOG.error("TransactionException, in synchronizeFdbAdd()  " + e.getMessage());

                if (e.getCause() != null) {
                    LOG.error("The Underlying cause is " + e.getCause().getMessage());
                }

                LOG.error("TransactionException, in synchronizeFdbAdd()");

            } catch (Exception e) {
                LOG.error("Exception, in synchronizeFdbAdd()  " + e.getMessage());

                if (e.getCause() != null) {
                    LOG.error("This Underlying cause is " + e.getCause().getMessage());
                }

            } finally {
                transactionManager.commit(status);
            }
        }

        setStartDate(NationalSetting.FDB_ADD_LAST_RUN, latestDate, NationalSetting.FDB_ADD_RUN_STATE);

    }

    /**
     * removeAddLaterThan90Days
     */
    private void removeAddLaterThan90Days() {
        
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            TransactionStatus status = transactionManager.getTransaction(def);
            
            fdbAutoAddDomainCapability.deleteOver90();
            fdbAddDomainCapability.deleteOver90();

            transactionManager.commit(status);

        } catch (TransactionException e) {
            LOG.error("TransactionException, in getStartDateRmoveOver90()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("The Underlying cause is  " + e.getCause().getMessage());
            }
        } catch (Exception e) {
            LOG.error("Exception Removing the old files for  removeAddLaterThan90Days:" 
                + e.getMessage());
          
            if (e.getCause() != null) {
                LOG.error("The Underlying cause is  " + e.getCause().getMessage());
            }
        }
    }
      

    /**
     * removeUpdateLaterThan90Days
     */
    private void removeUpdateLaterThan90Days() {
        
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(TIMEOUT);
            TransactionStatus status = transactionManager.getTransaction(def);
            
            fdbAutoUpdateDomainCapability.deleteOver90();
            fdbUpdateDomainCapability.deleteOver90();


            transactionManager.commit(status);

        } catch (TransactionException e) {
            LOG.error("TransactionException, in getStartDateRmoveOver90()  " + e.getMessage());

            if (e.getCause() != null) {
                LOG.error("The Underlying cause is  " + e.getCause().getMessage());
            }
        } catch (Exception e) {
            LOG.error("Exception Removing the old files for  removeUpdateLaterThan90Days:" 
                + e.getMessage());
          
            if (e.getCause() != null) {
                LOG.error("The Underlying cause is  " + e.getCause().getMessage());
            }
        }
    }

    /**
     * synchronizeFdbUpdate.
     */
    public void synchronizeFdbUpdate() {
        int iNDCNotInPPSN = 0;
       
        DefaultTransactionDefinition def = null;
        TransactionStatus status = null;
        
        removeUpdateLaterThan90Days();
        
        Map<String, String> productMap = new HashMap<String, String>();
        Date startDate = getStartDate(NationalSetting.FDB_UPDATE_LAST_RUN, NationalSetting.FDB_UPDATE_RUN_STATE);
        Date latestDate = new Date(startDate.getTime());

        List<FdbAutoUpdateVo> list = drugReferenceAutoCapability.getFdbUpdatedItems(startDate);
        int i = 0;

        for (FdbAutoUpdateVo fdbVo : list) {
            LOG.debug("Process NDC Updates " + fdbVo.getNdc() + " is " + (++i) + " of " + list.size());
            latestDate = updateLatestDate(latestDate, fdbVo.getUpdateDate());

            try {
                def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setTimeout(TIMEOUT);
                status = transactionManager.getTransaction(def);

                NdcVo ndcVo = findNdc(fdbVo.getNdc());

                if (ndcVo == null) {
                    iNDCNotInPPSN++;
                    LOG.debug(" NDC " + fdbVo.getNdc() + " is the " + iNDCNotInPPSN + " not in PPS-N.");
                } else {
                    ndcVo.setFdbNdcVo(fdbNdcDomainCapability.retrieve(Long.valueOf(ndcVo.getId())));
                    NdcVo fdbNdcVo = (NdcVo) managedItemCapability.retrieveBlankTemplate(EntityType.NDC);

                    fdbNdcVo.setNdc(ndcVo.getNdc());
                    fdbNdcVo = drugReferenceCapability.populateFdbNdcFields(fdbNdcVo);

                    // if the FDB data could not be retrieved then this is an error and log it.
                    // Good FDB data and NO PPS Data then add the fdb data to pps data and continue. No update
                    // Good FDB Good PPS then go ahead and process it.
                    if (fdbNdcVo.getFdbNdcVo() == null) {
                        LOG.debug("Updating NDC " + ndcVo.getNdc()
                                  + " cannot be performed because the app could not retrieve it from the API");
                    } else {
                        doAutoUpdateProcessNdc(fdbNdcVo, ndcVo);
                        doNonAutoUpdateProcessNdc(fdbNdcVo, ndcVo);
                    }
                }

                if (!(productMap.containsKey(fdbVo.getGcnSequenceNumber()))) {
                    List<ProductVo> productList = findProduct(fdbVo.getGcnSequenceNumber(), true);

                    if (productList != null && productList.size() > 0) { // If the item is in the EPL

                        // only update each set of products once.
                        productMap.put(fdbVo.getGcnSequenceNumber(), fdbVo.getGcnSequenceNumber());

                        for (ManagedItemVo managedItemVo : productList) {
                            ProductVo productVo = (ProductVo) managedItemVo;
                            LOG.debug("Update product " + productVo.getVaProductName());
                            doUpdateProcessProduct(productVo);
                        }
                    } else {
                        LOG.debug("No product found to update for " + fdbVo.getGcnSequenceNumber());
                    }
                }
            } catch (TransactionException e) {
                LOG.error("TransactionException, in synchronizeFdbUpdate()  " + e.getMessage());

                if (e.getCause() != null) {
                    LOG.error("TransactionException Underlying cause is " + e.getCause().getMessage());
                }

                LOG.error("TransactionException, in synchronizeFdbUpdate()");

            } catch (Exception e) {
                LOG.error("Exception, in synchronizeFdbUpdate()  " + e.getMessage());

                if (e.getCause() != null) {
                    LOG.error("Exception Underlying cause is  " + e.getCause().getMessage());
                }
            } finally {
                transactionManager.commit(status);
            }
        }

        productMap.clear();
        setStartDate(NationalSetting.FDB_UPDATE_LAST_RUN, latestDate, NationalSetting.FDB_UPDATE_RUN_STATE);
    }

    /**
     * shouldUpdate
     * @param myObject myObject
     * @param incomingObject incomingObject
     * @return true if set
     */
    private boolean shouldUpdate(String myObject, String incomingObject) {

        // return false if they are nullish.
        if (StringUtils.isBlank(incomingObject) || "0".equals(incomingObject)) {
            return (!(StringUtils.isBlank(myObject) || "0".equals(myObject)));
        } else {
            if (StringUtils.isBlank(myObject) || "0".equals(myObject)) {
                return true;
            } else {
                return (!(myObject.equalsIgnoreCase(incomingObject)));
            }
        }
    }

    /**
     * shouldUpdate
     * @param myObject myObject
     * @param incomingObject incomingObject
     * @return true
     */
    private boolean shouldUpdate(Double myObject, Double incomingObject) {

        if (incomingObject == null) {
            return false;
        } else {
            if (myObject == null) {
                return true;
            } else {
                
                // if neither are null return the comparison 
                return (!(myObject.equals(incomingObject)));
            }
        }
    }

    /**
     * shouldUpdate
     * @param myObject myObject
     * @param incomingObject incomingObject
     * @return true if needed update
     */
    private boolean shouldUpdate(Long myObject, Long incomingObject) {

        if (incomingObject == null) {
            return false;
        } else {
            if (myObject == null) {
                return true;
            } else {
                return (!(myObject.equals(incomingObject)));
            }
        }
    }

    /**
     * doAutoUpdateProcessNdc.  If these fields are updated then just update it in the FDB.
     *     FederalLegendCode
     *     FormatCode
     *     LabelerName
     *     FormatIndicator
     *     NDCFormatted
     *     ObsoleteDate
     *     PackageDescription
     *     PackageSize
     *     PreviousNDC
     *     TradeName
     * @param fdbVo fdbVo
     * @param ppsVo ppsVo
     */
    private void doAutoUpdateProcessNdc(NdcVo fdbVo, NdcVo ppsVo) {
        try {
            StringBuffer updated = new StringBuffer();

            if (fdbVo.getFdbNdcVo() == null || ppsVo.getFdbNdcVo() == null) {
                return;
            }

            Collection<Difference> differences = new ArrayList<Difference>();

            // If the federalLegendCode is different.
            if (shouldUpdate(ppsVo.getFdbNdcVo().getFederalLegendCode(), fdbVo.getFdbNdcVo().getFederalLegendCode())) {
                if (ppsVo.getFdbNdcVo().getFederalLegendCode() != null) {
                    createItemAuditHistory(EntityType.NDC, ppsVo.getId(), "FDB Federal Legend Code was updated.",
                        "FederalLegendCode", ppsVo.getFdbNdcVo().getFederalLegendCode(), fdbVo.getFdbNdcVo()
                            .getFederalLegendCode());
                }

                ppsVo.getOtcRxIndicator().setValue(fdbVo.getFdbNdcVo().getFederalLegendCode());
                ppsVo.getFdbNdcVo().setFederalLegendCode(fdbVo.getFdbNdcVo().getFederalLegendCode());
                updated.append(" FDB Federal Legend Code updated to " + ppsVo.getOtcRxIndicator().getValue());
            }

            // If the PackageSize is different.
            if (shouldUpdate(ppsVo.getFdbNdcVo().getPackageSize(), fdbVo.getFdbNdcVo().getPackageSize())) {
                if (ppsVo.getFdbNdcVo().getPackageSize() != null) {
                    createItemAuditHistory(EntityType.NDC, ppsVo.getId(), "FDB Package Size was updated.", "PackageSize", ppsVo
                        .getFdbNdcVo().getPackageSize().toString(), fdbVo.getFdbNdcVo().getPackageSize().toString());
                }
                
                differences.add(new Difference(FieldKey.PACKAGE_SIZE, ppsVo.getPackageSize(), fdbVo.getFdbNdcVo()
                    .getPackageSize()));
                ppsVo.setPackageSize(fdbVo.getFdbNdcVo().getPackageSize());
                ppsVo.getFdbNdcVo().setPackageSize(fdbVo.getFdbNdcVo().getPackageSize());
                updated.append(" Package Size updated to " + ppsVo.getPackageSize() + ".");
            }

            // If the NDCFormat is different.
            if (shouldUpdate(ppsVo.getFdbNdcVo().getFormatCode(), fdbVo.getFdbNdcVo().getFormatCode())) {
                if (ppsVo.getFdbNdcVo().getFormatCode() != null) {
                    createItemAuditHistory(EntityType.NDC, ppsVo.getId(), "FDB Format Code was updated.", "NDCFormat", ppsVo
                        .getFdbNdcVo().getFormatCode(), fdbVo.getFdbNdcVo().getFormatCode());
                }

                ppsVo.setTenDigitFormatIndication(fdbVo.getFdbNdcVo().getFormatCode());
                ppsVo.getFdbNdcVo().setFormatCode(fdbVo.getFdbNdcVo().getFormatCode());
                updated.append(" Format Code was updated to " + ppsVo.getTenDigitFormatIndication() + ".");
            }


            // If the 10 digit is different.
            if (shouldUpdate(ppsVo.getTenDigitNdc(), fdbVo.getTenDigitNdc())) {
                if (ppsVo.getTenDigitNdc() != null) {
                    this.createItemAuditHistory(EntityType.NDC, ppsVo.getId(), "FDB TenDigitNdc was updated.", "TenDigitNdc",
                        ppsVo.getTenDigitNdc(), fdbVo.getTenDigitNdc());
                }

                ppsVo.setTenDigitFormatIndication(fdbVo.getTenDigitFormatIndication());
                ppsVo.setTenDigitNdc(fdbVo.getTenDigitNdc());
                updated.append(" TenDigitNdc was updated.");
            }


            // If the getPackageDescription is different.
            if (shouldUpdate(ppsVo.getFdbNdcVo().getPackageDescription(), fdbVo.getFdbNdcVo().getPackageDescription())) {
                if (ppsVo.getFdbNdcVo().getPackageDescription() != null) {
                    createItemAuditHistory(EntityType.NDC, ppsVo.getId(), "FDB Package Description was updated.",
                        "PackageDescription", ppsVo.getFdbNdcVo().getPackageDescription(), fdbVo.getFdbNdcVo()
                            .getPackageDescription());
                }

                differences.add(new Difference(FieldKey.PACKAGE_TYPE, ppsVo.getPackageType(), fdbVo.getFdbNdcVo()
                    .getPackageDescription()));

                ppsVo.getPackageType().setValue(fdbVo.getFdbNdcVo().getPackageDescription());
                ppsVo.getFdbNdcVo().setPackageDescription(fdbVo.getFdbNdcVo().getPackageDescription());
                setPackageType(ppsVo);
                updated.append(" Package Description was updated to " + ppsVo.getPackageType().getValue() + ".");
            }


            // If the getLabelerName is different.
            if (shouldUpdate(ppsVo.getFdbNdcVo().getLabelerName(), fdbVo.getFdbNdcVo().getLabelerName())) {
                if (ppsVo.getFdbNdcVo().getLabelerName() != null) {
                    createItemAuditHistory(EntityType.NDC, ppsVo.getId(), "FDB Labeler (Manufacturer) was updated.",
                        "LabelerName", ppsVo.getFdbNdcVo().getLabelerName(), fdbVo.getFdbNdcVo().getLabelerName());
                }

                differences.add(new Difference(FieldKey.MANUFACTURER, ppsVo.getManufacturer().getValue(), fdbVo.getFdbNdcVo()
                    .getLabelerName()));
                
                ppsVo.getManufacturer().setValue(fdbVo.getFdbNdcVo().getLabelerName());
                ppsVo.getFdbNdcVo().setLabelerName(fdbVo.getFdbNdcVo().getLabelerName());
                setManufacturer(ppsVo);
                updated.append(" Labeler (Manufacturer) updated to " + fdbVo.getFdbNdcVo().getLabelerName());
            }


            // If the TradeName is different.  Note trade name is only stored on the ppsVO and not on the ppsVo.getFdbNdcVo
            if (shouldUpdate(ppsVo.getTradeName(), fdbVo.getFdbNdcVo().getTradeName())) {
                if (ppsVo.getTradeName() != null) {
                    createItemAuditHistory(EntityType.NDC, ppsVo.getId(), "Trade Name was updated.", "TradeName",
                        ppsVo.getTradeName(), fdbVo.getFdbNdcVo().getTradeName());
                }
                
                differences.add(new Difference(FieldKey.TRADE_NAME, ppsVo.getTradeName(), fdbVo.getFdbNdcVo().getTradeName()));
                
                ppsVo.setTradeName(fdbVo.getFdbNdcVo().getTradeName());
                updated.append(" Trade Name updated to " + fdbVo.getFdbNdcVo().getTradeName());
            }


            updated = doAutoUpdateProcessNdc2(fdbVo, ppsVo, updated, differences);


            // if the message exists then something was updated so process the update
            if (updated.length() > 0) {


                // update the NDC
                ndcDomainCapability.update(ppsVo, getUser());
                ppsVo.getFdbNdcVo().setNdcIdFk(Long.valueOf(ppsVo.getId()));
                fdbNdcDomainCapability.update(ppsVo.getFdbNdcVo(), getUser());
                

                if (vistaFileSynchCapability.sendModifiedItemToVista(ppsVo, differences, getUser(), 
                    okToSendToVista(), catchingUp())) {
                    addToQueue(ppsVo, ppsVo.getEntityType(), ItemAction.MODIFY, getUser());
                }
                

                // add the auto update record to the database.
                FdbAutoUpdateVo fdbAutoUpdateVo = new FdbAutoUpdateVo();
                fdbAutoUpdateVo.setNdc(ppsVo.getNdc());
                fdbAutoUpdateVo.setMessage(updated.toString());
                fdbAutoUpdateVo.setGcnSeqno(fdbVo.getFdbNdcVo().getGcnSeqno());
                fdbAutoUpdateVo.setVaProductName(ppsVo.getProduct().getVaProductName());
                fdbAutoUpdateVo.setFdbProductName(fdbVo.getFdbProductName());
                fdbAutoUpdateVo.setItemType("NDC");
                fdbAutoUpdateVo.setFormatIndicator(ppsVo.getTenDigitFormatIndication());
                fdbAutoUpdateVo.setNdcIdFk(Long.valueOf(ppsVo.getId()));

                this.fdbAutoUpdateDomainCapability.create(fdbAutoUpdateVo, getUser());
            }

        } catch (Exception e) {
            LOG.error("doAutoUpdateProcessNdc: Exception occurred" + e.toString());

            if (e.getCause() != null) {
                LOG.error(e.getCause().getMessage());
            }
        }
    }
    
    /**
     * doAutoUpdateProcessNdc2
     * @param fdbVo fdbVo
     * @param ppsVo ppsVo
     * @param updated2 updated2
     * @param differences differences
     * @return StringBuffer
     * @throws ParseException ParseException
     */
    private StringBuffer doAutoUpdateProcessNdc2(NdcVo fdbVo, NdcVo ppsVo, StringBuffer updated2, 
        Collection<Difference> differences) throws ParseException {
        
        StringBuffer updated = updated2;
        

        // If the PreviousNDC is different.
        if (shouldUpdate(ppsVo.getFdbNdcVo().getPreviousNdc(), fdbVo.getFdbNdcVo().getPreviousNdc())) {
            if (ppsVo.getFdbNdcVo().getPreviousNdc() != null) {
                createItemAuditHistory(EntityType.NDC, ppsVo.getId(), "The FDB Previous NDC was updated.", "PreviousNDC",
                    ppsVo.getFdbNdcVo().getPreviousNdc(), fdbVo.getFdbNdcVo().getPreviousNdc());
            }

            ppsVo.getVaDataFields().getDataField(FieldKey.PREVIOUS_NDC).selectValue(fdbVo.getFdbNdcVo().getPreviousNdc());
            ppsVo.getFdbNdcVo().setPreviousNdc(fdbVo.getFdbNdcVo().getPreviousNdc());
            updated.append(" Previous Ndc");
        }

      
        // If the Obsolete Date is different.
        if (shouldUpdate(ppsVo.getFdbNdcVo().getObsoleteDate(), fdbVo.getFdbNdcVo().getObsoleteDate())) {

            ppsVo.getFdbNdcVo().setObsoleteDate(fdbVo.getFdbNdcVo().getObsoleteDate());

            // If the PPS Item is Active and the FDB Item obsolete date was set then inactive it in PPS
            if (StringUtils.isNotBlank(fdbVo.getFdbNdcVo().getObsoleteDate())
                && ItemStatus.ACTIVE.equals(ppsVo.getItemStatus())) {
                ppsVo.setItemStatus(ItemStatus.INACTIVE);
                
                differences.add(new Difference(FieldKey.ITEM_STATUS, ItemStatus.ACTIVE, ItemStatus.INACTIVE)); 
                differences.add(new Difference(FieldKey.INACTIVATION_DATE, null, ppsVo.getInactivationDate()));
                createItemAuditHistory(EntityType.NDC, ppsVo.getId(), 
                    "Item Inactivated because FDB Obsolete date set in the FDB Update.", "ItemStatus", "", 
                    dateFormat.format(ppsVo.getInactivationDate()));

                updated.append(" Obsolete date was updated to " + fdbVo.getFdbNdcVo().getObsoleteDate());

                // if PPS Item is INACTIVE and the FDB Item obsolete date was set to null then activate it in PPS)
            } else if (StringUtils.isBlank(fdbVo.getFdbNdcVo().getObsoleteDate()) 
                && ItemStatus.INACTIVE.equals(ppsVo.getItemStatus())) {
                String oldValue = dateFormat.format(ppsVo.getInactivationDate());
                ppsVo.setItemStatus(ItemStatus.ACTIVE);
                
                differences.add(new Difference(FieldKey.ITEM_STATUS, ItemStatus.ACTIVE, ItemStatus.INACTIVE)); 
                differences.add(new Difference(FieldKey.INACTIVATION_DATE, ppsVo.getInactivationDate(), null));
                createItemAuditHistory(EntityType.NDC, ppsVo.getId(), 
                    "FDB Obsolete date was rescinded so PPS VO was activated.", "ItemStatus",
                    oldValue, "");
                updated.append(" Obsolete date was updated to null.");
            } 
        }
        
        return updated;
    }
    

    /**
     * setPackageType
     * @param ppsVo ppsVo
     * @return null if successful, otherwise an error message.
     */
    private String setPackageType(NdcVo ppsVo) {
        List<PackageTypeVo> packageTypeList = null;

        if (packageTypeList == null) {
            packageTypeList = packageTypeDomainCapability.retrieve();
        }

        for (PackageTypeVo vo : packageTypeList) {

            if (vo.getValue().equals(ppsVo.getPackageType().getValue())) {
                ppsVo.getPackageType().setId(vo.getId());

                return null; // this means success, no error message
            }
        }

        ppsVo.getPackageType().setItemStatus(ItemStatus.ACTIVE);
        ppsVo.getPackageType().setRequestItemStatus(RequestItemStatus.APPROVED);

        // Add the new Package type to the EPL
        try {
            ppsVo.setPackageType(packageTypeDomainCapability.create(ppsVo.getPackageType(), getUser()));
            createItemAuditHistory(EntityType.PACKAGE_TYPE, ppsVo.getPackageType().getId(), "Added by FDBAUTOADD", "", "", "");
            packageTypeList.add(ppsVo.getPackageType());
        } catch (Exception e) {
            String errorString = "Could not Create Package Type "
                + ppsVo.getPackageType().getValue() + ": Excpetion is shown as " + e.getMessage();
            LOG.error(errorString);

            return errorString;

        }

        //For New Package Types, send the Package Type to VistA
        try {
            if (vistaFileSynchCapability.sendNewItemToVista(ppsVo.getPackageType(), getUser(), okToSendToVista(), catchingUp())) {
                addToQueue(ppsVo.getPackageType(), ppsVo.getPackageType().getEntityType(), 
                    ItemAction.ADD, getUser());
            }
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("An exception occurred while sending the Package Type "
                + ppsVo.getPackageType().getValue()
                + "  For the NDC " + ppsVo.getNdc()
                + "  The message returned was: " + e.getMessage());

            if (e.getCause() != null) {
                stringBuffer.append("  The cause was listed as - ").append(e.getCause().getMessage());
            }

            LOG.error(stringBuffer.toString());

            return stringBuffer.toString();
        }

        return null;
    }

    /**
     * setManufacturer.
     * @param ppsVo ppsVo
     * @return null if true.
     */
    private String setManufacturer(NdcVo ppsVo) {
        List<ManufacturerVo> manufacturerList = null;

        if (manufacturerList == null) {
            manufacturerList = manufacturerDomainCapability.retrieve();
        }

        for (ManufacturerVo vo : manufacturerList) {

            if (vo.getValue().equals(ppsVo.getManufacturer().getValue())) {
                ppsVo.getManufacturer().setId(vo.getId());

                return null; // this means success, no error message
            }
        }

        // Add the new Manufacturer to the EPL
        try {
            ppsVo.setManufacturer((manufacturerDomainCapability.create(ppsVo.getManufacturer(), getUser())));
            createItemAuditHistory(EntityType.MANUFACTURER, ppsVo.getManufacturer().getId(), "Added by FDB Auto Add",
                                   "", "", "");
            manufacturerList.add(ppsVo.getManufacturer());
        } catch (Exception e) {
            return ("Could not Create Manufacturer "
                + ppsVo.getManufacturer().getValue() + ": Exception is " + e.getMessage());
        }

        ppsVo.getManufacturer().setItemStatus(ItemStatus.ACTIVE);
        ppsVo.getManufacturer().setRequestItemStatus(RequestItemStatus.APPROVED);

        //For New ManufacturersTypes, send the Manufacturer to VistA
        try {
            if (vistaFileSynchCapability.sendNewItemToVista(ppsVo.getManufacturer(), getUser(), okToSendToVista(), catchingUp())) {
                addToQueue(ppsVo.getManufacturer(), ppsVo.getManufacturer().getEntityType(), 
                    ItemAction.ADD, getUser());
            }
        } catch (Exception e) {
            StringBuffer str = new StringBuffer();
            str.append("An exception occurred while sending the Manufacturer "
                + ppsVo.getManufacturer().getValue()
                + " For the NDC " + ppsVo.getNdc()
                + " The message returned was: " + e.getMessage());

            if (e.getCause() != null) {
                str.append(" The underlying cause was ").append(e.getCause().getMessage());
            }

            LOG.error(str.toString());
        }

        return null;
    }

    /**
     * createItemAuditHistory record
     * @param type EntityType
     * @param id EPL_ID
     * @param message message.
     * @param column column
     * @param oldValue oldVAlue
     * @param newValue newValue
     */
    private void createItemAuditHistory(EntityType type, String id, String message, String column,
                                        String oldValue, String newValue) {
        try {
            ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
            audit.setReason(message);

            if (StringUtils.isNotEmpty(column)) {
                Collection<ItemAuditHistoryDetailsVo> collection = new ArrayList<ItemAuditHistoryDetailsVo>();
                ItemAuditHistoryDetailsVo detail = new ItemAuditHistoryDetailsVo();
                detail.setColumnName(column);
                detail.setNewValue(newValue);
                detail.setEventCategory(EventCategory.SYSTEM_EVENT);
                detail.setDetailReason(message);

                if (oldValue == null) {
                    detail.setOldValue("");
                } else {
                    detail.setOldValue(oldValue);
                }

                collection.add(detail);
                audit.setDetails(collection);
            }

            audit.setAuditItemId(id);
            audit.setCreatedBy("FDBAUTO SYSTEM");
            audit.setEventCategory(EventCategory.SYSTEM_EVENT);
            audit.setDetailCategory(EventCategory.NATIONAL_MODIFICATION);
            audit.setAuditItemType(type);
            audit.setSiteName("National");
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
     * check the current ndc values versus those from FDB.
     *      PEPS2162  The system shall add a row to the PPS-N FDB Update
     *      Pending List with a description of the field modified if one 
     *      or more of the following FDB Packaged Drug attributes was
     *       modified since the last auto updated process was executed:
     *      DoseForm
     *      GCNSeqNo
     *      StrengthNumeric
     *      StrengthUnit
     * @param fdbVo ndcVo
     * @param ppsVo ndcVo
     */
    private void doNonAutoUpdateProcessNdc(NdcVo fdbVo, NdcVo ppsVo) {

        try {

            StringBuffer message = new StringBuffer();

            LOG.debug("doNonAutoUpdateProcessNdc: Step1 " + ppsVo.getNdc());
  
            // If the federalLegendCode is different.
            if (shouldUpdate(ppsVo.getFdbNdcVo().getDosageForm(), fdbVo.getFdbNdcVo().getDosageForm())) {
                message.append("FDB Dosage Form was updated from " + ppsVo.getFdbNdcVo().getDosageForm()
                               + TO + fdbVo.getFdbNdcVo().getDosageForm() + ". ");
                ppsVo.getFdbNdcVo().setDosageForm(fdbVo.getFdbNdcVo().getDosageForm());
            }
            
            LOG.debug("doNonAutoUpdateProcessNdc: Step2");
            
            // If the GcenSeqNo is different.
            if (shouldUpdate(ppsVo.getFdbNdcVo().getGcnSeqno(), fdbVo.getFdbNdcVo().getGcnSeqno())) {
                message.append("FDB GcnSeqNo was updated from " + ppsVo.getFdbNdcVo().getGcnSeqno()
                               + TO + fdbVo.getFdbNdcVo().getGcnSeqno() + ". ");
                ppsVo.getFdbNdcVo().setGcnSeqno(fdbVo.getFdbNdcVo().getGcnSeqno());
            }

            LOG.debug("doNonAutoUpdateProcessNdc: Step3");
           
            // If the StrengthNumeric is different.
            if (shouldUpdate(ppsVo.getFdbNdcVo().getStrengthNumeric(), fdbVo.getFdbNdcVo().getStrengthNumeric())) {
                message.append("FDB Strength Numeric was updated from " + ppsVo.getFdbNdcVo().getStrengthNumeric()
                               + TO + fdbVo.getFdbNdcVo().getStrengthNumeric() + ". ");
                ppsVo.getFdbNdcVo().setStrengthNumeric(fdbVo.getFdbNdcVo().getStrengthNumeric());
            }

            LOG.debug("doNonAutoUpdateProcessNdc: Step4");
          
            // If the StrengthUnit is different.
            if (shouldUpdate(ppsVo.getFdbNdcVo().getStrengthUnit(), fdbVo.getFdbNdcVo().getStrengthUnit())) {
                message.append("FDB Strength Unit was updated from " + ppsVo.getFdbNdcVo().getStrengthUnit()
                               + TO + fdbVo.getFdbNdcVo().getStrengthUnit() + ". ");
                ppsVo.getFdbNdcVo().setStrengthUnit(fdbVo.getFdbNdcVo().getStrengthUnit());
            }

            // if the message exists then something was updated so add it to the fdb (Non-Auto) Update List.
            if (message.length() > 1) {
                LOG.debug("doNonAutoUpdateProcessNdc: Step5");
            
                // update the fdbNdcVO portion of the NDC but not the actual NDC.
                fdbNdcDomainCapability.update(ppsVo.getFdbNdcVo(), getUser());
                LOG.debug("doNonAutoUpdateProcessNdc: Step6");
                
                // add the non-auto update record
                FdbUpdateVo fdbUpdateVo = new FdbUpdateVo();
                fdbUpdateVo.setNdc(ppsVo.getNdc());
                fdbUpdateVo.setMessage(message.toString());
                fdbUpdateVo.setVaProductName(ppsVo.getProduct().getVaProductName());
                fdbUpdateVo.setProductFk(Long.valueOf(ppsVo.getProduct().getId()));
                fdbUpdateVo.setNdcIdFk(Long.valueOf(ppsVo.getId()));
                fdbUpdateVo.setFdbProductName(fdbVo.getFdbProductName());
                fdbUpdateVo.setGcnSeqno(Long.valueOf(ppsVo.getProduct().getGcnSequenceNumber()));
                LOG.debug("doNonAutoUpdateProcessNdc: Step7");
                this.fdbUpdateDomainCapability.create(fdbUpdateVo, getUser());
            } else {
                LOG.debug("doNonAutoUpdateProcessNdc: No update for NDC " + ppsVo.getNdc());
            }

        } catch (Exception e) {
            LOG.error("doUpdateProcessNdc: Exeption occurred" + e.toString());
        }
    }

    /**
     * The system shall add a row to the to the PPS-N FDB Update Pending List with a description of the
     *  field modified if one or more of the following FDB Dispensable Generic attributes
     *   was modified since the last auto updated process was executed.
     *   GenericDoseForm
     *   GenericDrugName
     *   HasPackagedDrugs
     *   ID
     *   Route
     *   SingleIngredient
     *   Strength
     *   
     *   The system shall add a row to the PPS-N FDB Update Pending List with a 
     *   description of the field modified if one or more of the following FDB Dispensable
     *    Drug attributes was modified since the last auto updated process was executed:
     *   ConceptType
     *   DoseForm
     *   GCNSeqNo
     *   HasPackagedDrugs
     *   MultiSourceCode
     *   Name
     *   ObsoleteDate
     *   Replaced
     *   Route
     *   StatusCode
     *   Strength
     *   StrengthUnit
     * @param productVo productVo
     */
    private void doUpdateProcessProduct(ProductVo productVo) {
        try {

            FdbProductVo ppsVo = productVo.getFdbProductVo();
            boolean needToUpdate = false;

            if (ppsVo == null) {
                ppsVo = new FdbProductVo();
                ppsVo.setProductIdFk(new Long(productVo.getId()));
            }

            FdbProductVo fdbProductVo = drugReferenceCapability.populateFdbProductFields(productVo.getGcnSequenceNumber());
            
            // if nothing to compare
            if (fdbProductVo == null) {
                LOG.debug("Could not retrieve GCN " + productVo.getGcnSequenceNumber() + " from FDB.");
              
                return;
            }

            // If the GenericDoseForm is different.
            if (shouldUpdate(ppsVo.getDgGenericDosageForm(), fdbProductVo.getDgGenericDosageForm())) {
                if (ppsVo.getDgGenericDosageForm() != null) {
                    addFdbUpdate("FDB Generic Dosage Form was updated from " + ppsVo.getDgGenericDosageForm()
                        + TO + fdbProductVo.getDgGenericDosageForm(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDgGenericDosageForm(fdbProductVo.getDgGenericDosageForm());
                needToUpdate = true;
            }

            // If the GenericDrugName is different.
            if (shouldUpdate(ppsVo.getDgGenericDrugName(), fdbProductVo.getDgGenericDrugName())) {
                if (ppsVo.getDgGenericDrugName() != null) {
                    addFdbUpdate("FDB Generic Drug Name was updated from " + ppsVo.getDgGenericDrugName()
                        + TO + fdbProductVo.getDgGenericDrugName(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDgGenericDrugName(fdbProductVo.getDgGenericDrugName());
                needToUpdate = true;
            }

            // If the Generic HasPackagedDrugs is different.
            if (shouldUpdate(ppsVo.getDgHasPackagedDrugs(), fdbProductVo.getDgHasPackagedDrugs())) {
                if (ppsVo.getDgHasPackagedDrugs() != null) {
                    addFdbUpdate("FDB Generic HasPackagedDrugs was updated from " + ppsVo.getDgHasPackagedDrugs()
                        + TO + fdbProductVo.getDgHasPackagedDrugs(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDgHasPackagedDrugs(fdbProductVo.getDgHasPackagedDrugs());
                needToUpdate = true;
            }

            // If the Generic ID is different.
            if (shouldUpdate(ppsVo.getDgGenericDrugId(), fdbProductVo.getDgGenericDrugId())) {
                if (ppsVo.getDgGenericDrugId() != null) {
                    addFdbUpdate("FDB Generic Id was updated from " + ppsVo.getDgGenericDrugId()
                        + TO + fdbProductVo.getDgGenericDrugId(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDgGenericDrugId(fdbProductVo.getDgGenericDrugId());
                needToUpdate = true;
            }

            // If the Generic Route is different.
            if (shouldUpdate(ppsVo.getDgRoute(), fdbProductVo.getDgRoute())) {
                if (ppsVo.getDgRoute() != null) {
                    addFdbUpdate("FDB Generic Route was updated from " + ppsVo.getDgRoute()
                        + TO + fdbProductVo.getDgRoute(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDgRoute(fdbProductVo.getDgRoute());
                needToUpdate = true;
            }

            // If the Generic Single Ingredient is different.
            if (shouldUpdate(ppsVo.getDgSingleIngredient(), fdbProductVo.getDgSingleIngredient())) {
                if (ppsVo.getDgSingleIngredient() != null) {
                    addFdbUpdate("FDB Generic Single Ingredient was updated from " + ppsVo.getDgSingleIngredient()
                        + TO + fdbProductVo.getDgSingleIngredient(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDgSingleIngredient(fdbProductVo.getDgSingleIngredient());
                needToUpdate = true;
            }

            // If the Generic Strength is different.
            if (shouldUpdate(ppsVo.getDgStrength(), fdbProductVo.getDgStrength())) {
                if (ppsVo.getDgStrength() != null) {
                    addFdbUpdate("FDB Generic Strength was updated from " + ppsVo.getDgStrength()
                        + TO + fdbProductVo.getDgStrength(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDgStrength(fdbProductVo.getDgStrength());
                needToUpdate = true;
            }

            // If the Dispensible ConceptType is different.
            if (shouldUpdate(ppsVo.getDdConceptType(), fdbProductVo.getDdConceptType())) {
                if (ppsVo.getDdConceptType() != null) {
                    addFdbUpdate("FDB Dispensible ConceptType was updated from " + ppsVo.getDdConceptType()
                        + TO + fdbProductVo.getDdConceptType(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdConceptType(fdbProductVo.getDdConceptType());
                needToUpdate = true;
            }

            // If the Dispensible Dosage Form is different.
            if (shouldUpdate(ppsVo.getDdDosageForm(), fdbProductVo.getDdDosageForm())) {
                if (ppsVo.getDdDosageForm() != null) {
                    addFdbUpdate("FDB Dispensible Dosage Form was updated from " + ppsVo.getDdDosageForm()
                        + TO + fdbProductVo.getDdDosageForm(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdDosageForm(fdbProductVo.getDdDosageForm());
                needToUpdate = true;
            }

            doUpdateProcessProduct2(productVo, ppsVo, fdbProductVo, needToUpdate);
        } catch (Exception e) {
            LOG.debug(e.toString());
        }
    }

    /**
     *    doUpdateProcessProduct2
     * @param productVo productVo
     * @param ppsVo ppsVo
     * @param fdbProductVo fdbProductVo
     * @param needToUpdateIn needToUpdate
     */
    private void doUpdateProcessProduct2(ProductVo productVo, FdbProductVo ppsVo,
        FdbProductVo fdbProductVo, boolean needToUpdateIn) {

        boolean needToUpdate = needToUpdateIn;

        try {

            // If the Dispensible GcnSeqNo is different.
            if (shouldUpdate(ppsVo.getDdGcnSeqno(), fdbProductVo.getDdGcnSeqno())) {
                if (ppsVo.getDdGcnSeqno() != null) {
                    addFdbUpdate("FDB Dispensible GcnSeqNo was updated from " + ppsVo.getDdGcnSeqno()
                        + TO + fdbProductVo.getDdGcnSeqno(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdGcnSeqno(fdbProductVo.getDdGcnSeqno());
                needToUpdate = true;
            }

            // If the Dispensible HasPackagedDrugs is different.
            if (shouldUpdate(ppsVo.getDdHasPackagedDrugs(), fdbProductVo.getDdHasPackagedDrugs())) {
                if (ppsVo.getDdHasPackagedDrugs() != null) {
                    addFdbUpdate("FDB Dispensible HasPackagedDrugs was updated from " + ppsVo.getDdHasPackagedDrugs()
                        + TO + fdbProductVo.getDdHasPackagedDrugs(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdHasPackagedDrugs(fdbProductVo.getDdHasPackagedDrugs());
                needToUpdate = true;
            }

            // If the Dispensible MultiSourceCode is different.
            if (shouldUpdate(ppsVo.getDdMultisource(), fdbProductVo.getDdMultisource())) {
                if (ppsVo.getDdMultisource() != null) {
                    addFdbUpdate("FDB Dispensible Multi-SourceCode was updated from " + ppsVo.getDdMultisource()
                        + TO + fdbProductVo.getDdMultisource(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdMultisource(fdbProductVo.getDdMultisource());
                needToUpdate = true;
            }

            // If the Dispensible Drug Name is different.
            if (shouldUpdate(ppsVo.getDdDispenseDrugName(), fdbProductVo.getDdDispenseDrugName())) {
                if (ppsVo.getDdDispenseDrugName() != null) {
                    addFdbUpdate("FDB Dispensible Drug Name was updated from " + ppsVo.getDdDispenseDrugName()
                        + TO + fdbProductVo.getDdDispenseDrugName(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdDispenseDrugName(fdbProductVo.getDdDispenseDrugName());
                needToUpdate = true;
            }

            // If the Dispensible ObsoleteDate is different.
            if (shouldUpdate(ppsVo.getDdObsoleteDate(), fdbProductVo.getDdObsoleteDate())) {
                if (ppsVo.getDdObsoleteDate() != null) {
                    addFdbUpdate("FDB Dispensible ObsoleteDate was updated from " + ppsVo.getDdObsoleteDate()
                        + TO + fdbProductVo.getDdObsoleteDate(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdObsoleteDate(fdbProductVo.getDdObsoleteDate());
                needToUpdate = true;
            }

            // If the Dispensible Replaced is different.
            if (shouldUpdate(ppsVo.getDdReplaced(), fdbProductVo.getDdReplaced())) {
                if (ppsVo.getDdReplaced() != null) {
                    addFdbUpdate("FDB Dispensible Replaced was updated from " + ppsVo.getDdReplaced()
                        + TO + fdbProductVo.getDdReplaced(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdReplaced(fdbProductVo.getDdReplaced());
                needToUpdate = true;
            }

            // If the Dispensible Route is different.
            if (shouldUpdate(ppsVo.getDdRoute(), fdbProductVo.getDdRoute())) {
                if (ppsVo.getDdRoute() != null) {
                    addFdbUpdate("FDB Dispensible Route was updated from " + ppsVo.getDdRoute()
                        + TO + fdbProductVo.getDdRoute(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdRoute(fdbProductVo.getDdRoute());
                needToUpdate = true;
            }

            // If the Dispensible Status Code is different.
            if (shouldUpdate(ppsVo.getDdStatusCode(), fdbProductVo.getDdStatusCode())) {
                if (ppsVo.getDdStatusCode() != null) {
                    addFdbUpdate("FDB Dispensible Status Code was updated from " + ppsVo.getDdStatusCode()
                        + TO + fdbProductVo.getDdStatusCode(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdStatusCode(fdbProductVo.getDdStatusCode());
                needToUpdate = true;
            }

            // If the Dispensible Strength is different.
            if (shouldUpdate(ppsVo.getDdStrength(), fdbProductVo.getDdStrength())) {
                if (ppsVo.getDdStrength() != null) {
                    addFdbUpdate("FDB Dispensible Strength was updated from " + ppsVo.getDdStrength()
                        + TO + fdbProductVo.getDdStrength(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdStrength(fdbProductVo.getDdStrength());
                needToUpdate = true;
            }

            // If the Dispensible StrengthUnit is different.
            if (shouldUpdate(ppsVo.getDdStrengthUnit(), fdbProductVo.getDdStrengthUnit())) {
                if (ppsVo.getDdStrengthUnit() != null) {
                    addFdbUpdate("FDB Dispensible StrengthUnit was updated from " + ppsVo.getDdStrengthUnit()
                        + TO + fdbProductVo.getDdStrengthUnit(),
                        productVo, fdbProductVo);
                }

                ppsVo.setDdStrengthUnit(fdbProductVo.getDdStrengthUnit());
                needToUpdate = true;
            }

            // if the message exists then something was updated so process the update
            if (needToUpdate) {
                productVo.setFdbProductVo(ppsVo);
                fdbProductDomainCapability.update(productVo.getFdbProductVo(), getUser());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * addFdbUpdate
     * @param message message
     * @param productVo productVo
     * @param fdbProductVo fdbProductVo
     * @throws PharmacyException PharmacyException
     */
    private void addFdbUpdate(String message, ProductVo productVo, FdbProductVo fdbProductVo) throws PharmacyException {

        // add the auto update record
        FdbUpdateVo fdbUpdateVo = new FdbUpdateVo();
        fdbUpdateVo.setProductFk(Long.valueOf(productVo.getId()));
        fdbUpdateVo.setMessage(message);
        fdbUpdateVo.setGcnSeqno(Long.valueOf(productVo.getGcnSequenceNumber()));
        fdbUpdateVo.setVaProductName(productVo.getVaProductName());
        fdbUpdateVo.setFdbProductName(fdbProductVo.getDgGenericDrugName());
        fdbUpdateDomainCapability.create(fdbUpdateVo, getUser());
    }

    /**
     *  findNdcis used to find an NDC based on the input from FDB 
     * @param ndc the incoming ProductVo
     * @return the populate ProductVo or null if unsuccessful
     */
    private NdcVo findNdc(String ndc) {

        try {
            long eplId = ndcDomainCapability.getIdFromNDC(ndc);

            if (eplId == 0) {
                LOG.error("No records found in PPS-N for " + ndc);
               
            } else {
                NdcVo ndcVo = ndcDomainCapability.retrieve(String.valueOf(eplId));

                return ndcVo;
            }

        } catch (Exception e) {
            LOG.debug("AutoAddUpdateCapabilityImpl: FindNdc: " + ndc + ".  Exception is " + e.getMessage());
        }
        
        return null;
       
//        if (StringUtils.isBlank(ndc)) {
//            LOG.debug("ERROR: AutoAddUpdateCapabilityImpl ndc is blank.");
//        }
//
//        // Check if NDC exists
//        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.ADVANCED, Environment.NATIONAL);
//
//        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
//
//        // Use the and search
//        searchCriteria.setAdvancedAndSearch(true);
//
//        // add the search terms
//        searchTerms.add(new SearchTermVo(EntityType.NDC, FieldKey.NDC, ndc));
//        searchCriteria.setSearchTerms(searchTerms);
//        searchCriteria.setEntityType(EntityType.NDC);
//        searchCriteria.setSortedFieldKey(FieldKey.NDC);
//
//        try {
//            List<ManagedItemVo> searchResults = managedItemCapability.search(searchCriteria);
//
//            if (searchResults.size() == 1) {
//                NdcVo ndcVo = (NdcVo) searchResults.get(0);
//                ndcVo = ndcDomainCapability.retrieve(ndcVo.getId());
//
//                return ndcVo;
//            } else if (searchResults.size() == 0) {
//                LOG.error("No records found in PPS-N for " + ndc);
//            } else {
//                LOG.error("Found " + searchResults.size() + " ndcs for ndc " + ndc);
//            }
//
//        } catch (Exception e) {
//            LOG.debug("AutoAddUpdateCapabilityImpl: FindNdc: " + ndc + ".  Exception is " + e.getMessage());
//        }
//
//        return null;
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
     *  findProduct is used to find a product based on the input vo 
     * @param gcnSeqNo the incoming ProductVo
     * @param update True if in the update process
     * @return the populate ProductVo or null if unsuccesfull
     */
    private List<ProductVo> findProduct(String gcnSeqNo, boolean update) {

        return productDomainCapability.getAllProductswithGcn(new Long(gcnSeqNo));
    }

    /**
     * setManagedItemCapability.
     * @param managedItemCapability the managedItemCapability to set
     */
    public void setManagedItemCapability(ManagedItemCapability managedItemCapability) {
        this.managedItemCapability = managedItemCapability;
    }

    /**
     * setFdbUpdateProcessCapability.
     * @param pFdbUpdateProcessCapablity the fdbUpdateProcessCapablity to set
     */
    public void setFdbUpdateProcessCapability(FdbUpdateProcessCapability pFdbUpdateProcessCapablity) {
        this.fdbUpdateProcessCapablity = pFdbUpdateProcessCapablity;
    }

    /**
     * setFdbAutoUpdateDomainCapability.
     * @param fdbAutoUpdateDomainCapability the fdbAutoUpdateDomainCapability to set
     */
    public void setFdbAutoUpdateDomainCapability(FdbAutoUpdateDomainCapability fdbAutoUpdateDomainCapability) {
        this.fdbAutoUpdateDomainCapability = fdbAutoUpdateDomainCapability;
    }

    /**
     * setFdbAutoAddDomainCapability.
     * @param fdbAutoAddDomainCapability the fdbAutoAddDomainCapability to set
     */
    public void setFdbAutoAddDomainCapability(FdbAutoAddDomainCapability fdbAutoAddDomainCapability) {
        this.fdbAutoAddDomainCapability = fdbAutoAddDomainCapability;
    }

    /**
     * setFdbAddDomainCapability.
     * @param fdbAddDomainCapability the fdbAddDomainCapability to set
     */
    public void setFdbAddDomainCapability(FdbAddDomainCapability fdbAddDomainCapability) {
        this.fdbAddDomainCapability = fdbAddDomainCapability;
    }

    /**
     * setFdbUpdateDomainCapability.
     * @param fdbUpdateDomainCapability the drugReferenceCapability to set
     */
    public void setFdbUpdateDomainCapability(FdbUpdateDomainCapability fdbUpdateDomainCapability) {
        this.fdbUpdateDomainCapability = fdbUpdateDomainCapability;
    }

    /**
     * setDrugReferenceAutoCapability.
     * @param drugReferenceAutoCapability the drugReferenceAutoCapability to set
     */
    public void setDrugReferenceAutoCapability(DrugReferenceAutoCapability drugReferenceAutoCapability) {
        this.drugReferenceAutoCapability = drugReferenceAutoCapability;
    }
    
    /**
     * setDrugReferenceCapability.
     * @param drugReferenceCapability the drugReferenceCapability to set
     */
    public void setDrugReferenceCapability(DrugReferenceCapability drugReferenceCapability) {
        this.drugReferenceCapability = drugReferenceCapability;
    }

    /**
     * setNdcDomainCapability.
     * @param ndcDomainCapability the ndcDomainCapability to set
     */
    public void setNdcDomainCapability(NdcDomainCapability ndcDomainCapability) {
        this.ndcDomainCapability = ndcDomainCapability;
    }
    
    /**
     * setProductDomainCapability.
     * @param productDomainCapability the ProductDomainCapability to set
     */
    public void setProductDomainCapability(ProductDomainCapability productDomainCapability) {
        this.productDomainCapability = productDomainCapability;
    }

    /**
     * setPackageTypeDomainCapability.
     * @param packageTypeDomainCapability the packageTypeDomainCapability to set
     */
    public void setPackageTypeDomainCapability(PackageTypeDomainCapability packageTypeDomainCapability) {
        this.packageTypeDomainCapability = packageTypeDomainCapability;
    }

    /**
     * setVistaFileSynchCapability
     * @param vistaFileSynchCapability the vistaFileSynchCapability to set
     */
    public void setVistaFileSynchCapability(VistaFileSynchCapability vistaFileSynchCapability) {
        this.vistaFileSynchCapability = vistaFileSynchCapability;
    }


    /**
     * setManufacturerDomainCapability.
     * @param manufacturerDomainCapability the manufacturerDomainCapability to set
     */
    public void setManufacturerDomainCapability(ManufacturerDomainCapability manufacturerDomainCapability) {
        this.manufacturerDomainCapability = manufacturerDomainCapability;
    }

    /**
     * setItemAuditHistoryDomainCapability.
     * @param itemAuditHistoryDomainCapability the itemAuditHistoryDomainCapability to set
     */
    public void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability) {
        this.itemAuditHistoryDomainCapability = itemAuditHistoryDomainCapability;
    }

    /**
     * setFdbNdcDomainCapability.
     * @param fdbNdcDomainCapability fdbNdcDomainCapability
     */
    public void setFdbNdcDomainCapability(FdbNdcDomainCapability fdbNdcDomainCapability) {
        this.fdbNdcDomainCapability = fdbNdcDomainCapability;
    }

    /**
     * setFdbProductDomainCapability.
     * @param fdbProductDomainCapability the fdbProductDomainCapability to set
     */
    public void setFdbProductDomainCapability(FdbProductDomainCapability fdbProductDomainCapability) {
        this.fdbProductDomainCapability = fdbProductDomainCapability;
    }

    /**
     * setNationalSettingDomainCapability.
     * @param nationalSettingDomainCapability the nationalSettingDomainCapability to set
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }

    /**
     * getTransactionManager
     * @return PlatformTransactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * set Transaction Manager
     * @param pTransactionManager pTransactionManager
     */
    public void setTransactionManager(PlatformTransactionManager pTransactionManager) {
        this.transactionManager = pTransactionManager;
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
