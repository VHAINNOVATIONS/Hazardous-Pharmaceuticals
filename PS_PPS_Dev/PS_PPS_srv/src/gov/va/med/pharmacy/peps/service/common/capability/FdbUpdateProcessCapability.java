/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionResultVo;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.FdbUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageTypeDomainCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceAutoCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;



/**
 * 
 * FdbUpdateProcessCapability
 * 
 */
public interface FdbUpdateProcessCapability {

    /**
     * Retrieves Package Drug pending list.
     * 
     * @param pNdc
     *            to retrieve
     * @param user UserVo            
     * @return returns FdbAddVo
     */
    FDBSearchOptionVo retrieveNdc(String pNdc, UserVo user);

    /**
     * retrieveFdbUpdateDate
     * @return fdbUpdateDate
     */
    String retrieveFdbUpdateDate();
    
    
    /**
     * Setter for Manufacturer
     * 
     * @param manufacturerDomainCapability
     *            the manufacturerDomainCapability to set
     */
    void setManufacturerDomainCapability(
            ManufacturerDomainCapability manufacturerDomainCapability);

    /**
     * Setter for packageTypeDomainCapability
     * 
     * @param packageTypeDomainCapability
     *            the packageTypeDomainCapabilityto set
     */
    void setPackageTypeDomainCapability(
            PackageTypeDomainCapability packageTypeDomainCapability);

    /**
     * Setter for OrderUnit
     * 
     * @param orderUnitDomainCapability
     *            the orderUnitDomainCapability to set
     */
    void setOrderUnitDomainCapability(
            OrderUnitDomainCapability orderUnitDomainCapability);

    /**
     * setter for the drugReferenceCapability
     * 
     * @param drugReferenceCapability
     *            the drugReferenceCapability to set
     */
    void setDrugReferenceCapability(
            DrugReferenceCapability drugReferenceCapability);

    /**
     * Add selected NDCs from Matching page to selected Product
     * 
     * @param pUser
     *            user Vo
     * @param pNdcsNumbers
     *            NDC ids
     * @param pProductId
     *            selected product
     * @return ProcessedItemVo
     * @throws ValidationException 
     */
    ProcessedItemVo addProductsToNdcs(UserVo pUser, String[] pNdcsNumbers,
            String pProductId) throws ValidationException;

    /**
     * 
     * Creates a blank template Product
     * 
     * @param user
     *            - user
     * @param selectedNdcs
     *            ndcs
     * @return returns a productVo with selected Ndcs
     */
    ProductVo createBlankTemplate(UserVo user, List<String> selectedNdcs);

    /**
     * 
     * Creates a Product from existing
     * 
     * @param user
     *            - user
     * @param pNdcsNumbers
     *            - ndcs that were selected
     * @param pProductSeqNo
     *            - the product that was selected
     * @return returns a productVo from selected Ndc
     */
    ProductVo createFromExisting(UserVo user, String[] pNdcsNumbers,
            String pProductSeqNo);

    /**
     * 
     * Setter for the managedItemCapability
     * 
     * @param managedItemCapability
     *            the managedItemCapability to set
     */
    void setManagedItemCapability(ManagedItemCapability managedItemCapability);

    /**
     * returns closest match products by GCNSequence Number
     * 
     * @param pSeqNos
     *            the selected Sequence Nos
     * @return closest match productVOs
     */
    List<ProductVo> getClosestMatchProducts(List<String> pSeqNos);

    /**
     * returns a ManagedItem
     * 
     * @param pId
     *            id of managed item
     * @param pEntityType
     *            managed Item type
     * @return Managed Item
     */
    ManagedItemVo retrieveManagedItem(String pId, EntityType pEntityType);

    /**
     * Creates NDC from FDB
     * 
     * @param ndcNo
     *            - NDC number
     * @param pUser
     *            - required user
     * @return returns Newly created NDC
     */
    NdcVo createNdcFromFdb(String ndcNo, UserVo pUser);

    /**
     * 
     * find Manufacturers by name
     * 
     * @param pManufacturerName
     *            name
     * @return ManufacturerVos
     */
    ManufacturerVo findManufacturerByName(String pManufacturerName);
    
    /**
     * 
     * find findPackageTypeByName by name
     * 
     * @param pPackageTypeName name
     * @return ManufacturerVos
     */
    PackageTypeVo findPackageTypeByName(String pPackageTypeName);

    /**
     * 
     * retrieves all items from the EPL_FDB_ADD table
     * 
     * @return FdbAddVo
     */
    List<FdbAddVo> retrieveEPLPendingList();

    /**
     * 
     * deletes NDC from the EPL pending list
     * 
     * @param ndcNo
     *            ndc number
     */
    void deleteNdcFromEplPendingList(String ndcNo);

    /**
     * retrieves all items in the EPL_FDB_AUTO_UPDATE table
     * 
     * @return FdbAutoUpdateVo
     */
    List<FdbAutoUpdateVo> retrieveEplAutoUpdateList();

    /**
     * 
     * retrieves all items in the EPL_FDB_UPDATE table
     * 
     * @return FdbUpdateVo
     */
    List<FdbUpdateVo> retrieveEplUpdateList();

    /**
     * retrieve all items in the Epl_Auto_Add table
     * 
     * @return FdbAutoAddVo
     */
    List<FdbAutoAddVo> retrieveEplAutoAddList();

    /**
     * deletes Items From UpdateList
     * @param id id
     */
    void deleteItemsFromUpdateList(String id);

    /**
     * 
     * deletes Items From AutoAdd List
     *
     * @param id String
     */
    void deleteItemsFromAutoAddList(String id);
    
    /**
     * deletes Items From Auto Update List
     * @param id id to remove
     */
    void deleteItemsFromAutoUpdateList(String id);
    
    /**
     * create Fdb CSV File
     * 
     * @param results results
     * @return StringBuilder String
     */
    StringBuilder createFdbCsvFile(List<FDBSearchOptionResultVo> results);
    
    /**
     * createFdbAddCsvFile
     * @param results results list
     * @return StringBuilder
     */
    StringBuilder createFdbAddCsvFile(List<FdbAddVo> results);
    
    /**
     * createFdbUpdateCsvFile
     * @param updateList update results list
     * @return StringBuilder String
     */
    StringBuilder createFdbUpdateCsvFile(List<FdbUpdateVo> updateList);

    /**
     * createFdbAutoAddCsvFile
     * @param autoAddList auto add results list
     * @return StringBuilder String
     */
    StringBuilder createFdbAutoAddCsvFile(List<FdbAutoAddVo> autoAddList);
    
    /**
     * createFdbAutoUpdateCsvFile
     * @param autoUpdateList auto update results list
     * @return StringBuilder String
     */
    StringBuilder createFdbAutoUpdateCsvFile(List<FdbAutoUpdateVo> autoUpdateList);

    /**
     * searchNdcByNumber
     *
     * @param pNdcNumber pNdcNumber
     * @return   List<NdcVo>
     */
    List<NdcVo> searchNdcByNumber(String pNdcNumber);
    
    /**
     * get FdbAdd VoItems List
     *
     * @param selectedNdcs selectedNdcs
     * @param pendingList pendingList
     * @return List<FdbAddVo>
     */
    List<FdbAddVo> getFdbAddVoItemsList(List<String> selectedNdcs, List<FdbAddVo> pendingList);
    
    /**
     * 
     * sortPendingListByGCNSeqNo
     *
     * @param pPendingList pPendingList
     * @return List<FdbAddVo>
     */
    List<FdbAddVo> sortPendingListByGCNSeqNo(List<FdbAddVo> pPendingList);
    
    /**
     * retrieves NDCs from FDB_ADD table that where selected for the Matching
     * page
     * 
     * @param ndcList NDC number
     * @return list of FdbAddVo
     */
    List<FdbAddVo> retrieveEPLPendingListByNdc(List<String> ndcList);
    
    /**
     * Retrieve EPL Pending List By NDC
     * @param  ndcList - NDC numbers to retrieve
     * @return list of FdbAddVo
     */
    List<FdbAddVo> retrieveEPLPendingListByNdc(String[] ndcList);

    /**
     * 
     * getRemovedExistingList
     *
     * @param ndcList ndcList
     * @param pendingList pendingList
     * @param pSelected selected
     * @return  List<FdbAddVo>
     */
    List<FdbAddVo> getRemovedIfExistList(List<String> ndcList,
            List<FdbAddVo> pendingList, boolean pSelected);

    /**
     * setDrugReferenceAutoCapability
     * @param drugReferenceAutoCapability drugReferenceAutoCapability
     */
    void setDrugReferenceAutoCapability(DrugReferenceAutoCapability drugReferenceAutoCapability);
    
}
