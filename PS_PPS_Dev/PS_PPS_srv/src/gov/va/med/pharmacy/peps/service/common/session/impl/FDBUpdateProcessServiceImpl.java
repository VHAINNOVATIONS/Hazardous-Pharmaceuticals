/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


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
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.capability.FdbUpdateProcessCapability;
import gov.va.med.pharmacy.peps.service.common.session.FDBUpdateProcessService;


/**
 * FDB update process Service
 */
public class FDBUpdateProcessServiceImpl implements FDBUpdateProcessService {

    private FdbUpdateProcessCapability fdbUpdateProcessCapability;

    /**
     * constructor for FDB Update Process Service
     */
    public FDBUpdateProcessServiceImpl() {
    }

    /**
     * Retrieves Package Drug pending list.
     * @param pNdc to retrieve
     * @param user UserVo
     * @return returns FdbAddVo
     */
    @Override
    public FDBSearchOptionVo retrieveNdc(String pNdc, UserVo user) {
        return fdbUpdateProcessCapability.retrieveNdc(pNdc, user);
    }

    /**
     * Retrieve the EPL pending list (items that were not auto added, but are
     * pending for user review).
     * 
     * @return list of FdbAddVo
     */
    @Override
    public List<FdbAddVo> retrieveEPLAddList() {
        List<FdbAddVo> fdbAddVos = fdbUpdateProcessCapability.retrieveEPLPendingList();

        return sortPendingListByGCNSeqNo(fdbAddVos);
    }

    /**
     * retrieveFdbUpdateDate
     */
    @Override
    public String retrieveFdbUpdateDate() {
        return fdbUpdateProcessCapability.retrieveFdbUpdateDate();
    }
    
    /**
     * Retrieve EPL Pending List By NDC
     * @param  ndcList - NDC numbers to retrieve
     * @return list of FdbAddVo
     */
    @Override
    public List<FdbAddVo> retrieveEPLPendingListByNdc(String[] ndcList) {
        
        return fdbUpdateProcessCapability.retrieveEPLPendingListByNdc(ndcList);
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
        
        return fdbUpdateProcessCapability.retrieveEPLPendingListByNdc(ndcList);
    }

    /**
     * retrieves items from the update list (queue)
     *
     * @return list of FdbUpdateVo
     */
    @Override
    public List<FdbUpdateVo> retrieveEPLUpdateList() {
        return fdbUpdateProcessCapability.retrieveEplUpdateList();
    }

    /**
     * retrieves items from the EPL_FDB_AUTO_UPDATE  table
     *
     * @return listo of FdbAutoUpdateVo
     */
    @Override
    public List<FdbAutoUpdateVo> retrieveEplAutoUpdateList() {
        return fdbUpdateProcessCapability.retrieveEplAutoUpdateList();
    }

    /**
     * retrieves items from the EPL_FDB_AUTO_ADD table (currently using mock data)
     *
     * @return FdbAutoAddVo
     */
    @Override
    public List<FdbAutoAddVo> retrieveEplAutoAddList() {
        return fdbUpdateProcessCapability.retrieveEplAutoAddList();
    }

    /**
     * returns a ManagedItem
     *
     * @param pId id of managed item
     * @param pEntityType managed Item type
     * @return Managed Item
     */
    @Override
    public ManagedItemVo retrieveManagedItem(String pId, EntityType pEntityType) {

        return fdbUpdateProcessCapability.retrieveManagedItem(pId, pEntityType);
    }

    /**
     * deletes items from the updateList
     * @param ids  ids to delete 
    */
    @Override
    public void deleteItemsFromAutoAddList(String[] ids) {

        for (String id : ids) {
            fdbUpdateProcessCapability.deleteItemsFromAutoAddList(id);
        }
    }

    /**
     * removes NDC from EPL_FDB_ADD table
     * 
     * @param ids  - pNdcNumbers to set
     */
    @Override
    public void deleteItemsFromAddList(String[] ids) {

        for (String ndcNo : ids) {
            fdbUpdateProcessCapability.deleteNdcFromEplPendingList(ndcNo);
        }
    }

    /**
     * deletes items from the updateList
     * @param ids  ids to delete 
    */
    @Override
    public void deleteItemsFromUpdateList(String[] ids) {

        for (String id : ids) {
            fdbUpdateProcessCapability.deleteItemsFromUpdateList(id);
        }
    }

    @Override
    public void deleteItemsFromAutoUpdateList(String[] ids) {

        for (String id : ids) {
            fdbUpdateProcessCapability.deleteItemsFromAutoUpdateList(id);
        }
    }

    /**
     * returns closest match products by GCNSequence Number
     * 
     * @param seqNos  the selected Sequence Nos
     * @return closest match productVOs
     */
    @Override
    public List<ProductVo> getClosestMatchProducts(List<String> seqNos) {
        return fdbUpdateProcessCapability.getClosestMatchProducts(seqNos);
    }

    /**
     * Add selected NDCs from Matching page to selected Product
     * 
     * @param pUser user Vo
     * @param pNdcsNumbers NDC ids
     * @param pProductId Sequence Numbers
     * @return ProcessedItemVo
     * @throws ValidationException 
     */
    @Override
    public ProcessedItemVo addProductToNdcs(UserVo pUser, String[] pNdcsNumbers, String pProductId) throws ValidationException {

        return fdbUpdateProcessCapability.addProductsToNdcs(pUser, pNdcsNumbers, pProductId);
    }

    /**
     * Creates a blank template Product
     *
     * @param pUser - user
     * @param pNdcNumbers ndcs 
     * @return returns a productVo with selected Ndcs
     */
    @Override
    public ProductVo createBlankTemplate(UserVo pUser, List<String> pNdcNumbers) {
        return fdbUpdateProcessCapability.createBlankTemplate(pUser, pNdcNumbers);
    }

    /**
     * Creates a Product from existing
     *
     * @param pUser - user
     * @param pNdcsNumbers - ndcs that were selected
     * @param pItemId - the product that was selected
     * @return returns a productVo from selected Ndc
     */
    @Override
    public ProductVo createFromExisting(UserVo pUser, String[] pNdcsNumbers, String pItemId) {
        return fdbUpdateProcessCapability.createFromExisting(pUser, pNdcsNumbers, pItemId);
    }

    /**
     * Find Manufacturer By Name
     *
     * @param pManufacturerName name
     * @return list of ManufacturerVos
     */
    @Override
    public ManufacturerVo findManufacturerByName(String pManufacturerName) {
        return fdbUpdateProcessCapability.findManufacturerByName(pManufacturerName);

    }

    /**
     * searches EPL_NDC table by NDC Number
     * 
     * @param pNdcNumber NDC Number
     * @return list of NdcVos
     */
    @Override
    public List<NdcVo> searchNdcByNumber(String pNdcNumber) {

        return fdbUpdateProcessCapability.searchNdcByNumber(pNdcNumber);
    }

    /**
     * Sorts the pending list by GCN Sequence No.
     * 
     * @param pPendingList  list to sort
     * @return sorted list
     */
    @Override
    public List<FdbAddVo> sortPendingListByGCNSeqNo(List<FdbAddVo> pPendingList) {
        
        return fdbUpdateProcessCapability.sortPendingListByGCNSeqNo(pPendingList);
    }

    /**
     * checks to see if NDC exist in EPL_NDC table,
     * then sets a flag if exist and returns the fdbItems 
     * that are selected and retrieve from the list.
     *
     * @param selectedNdcs - selected FDB ITEMS
     * @param pendingList list that contains search results
     * @return the fdbItem list.
     */
    @Override
    public List<FdbAddVo> getFdbAddVoItemsList(List<String> selectedNdcs, List<FdbAddVo> pendingList) {

        return fdbUpdateProcessCapability.getFdbAddVoItemsList(selectedNdcs, pendingList);
       
    }

    /**
     * getRemovedExistingList
     *
     * @param ndcList v
     * @param pendingList pendingList
     * @param pSelected selected 
     * @return List<FdbAddVo> 
     */
    @Override
    public List<FdbAddVo> getRemovedIfExistList(List<String> ndcList,
            List<FdbAddVo> pendingList, boolean pSelected) {
        
        return fdbUpdateProcessCapability.getRemovedIfExistList(ndcList, pendingList, pSelected);
    }

    

    /**
     * creates CSV File from list results
     *
     * @param results results
     * @return StringBuilder
     */
    @Override
    public StringBuilder createFdbCSVFile(List<FDBSearchOptionResultVo> results) {
        return fdbUpdateProcessCapability.createFdbCsvFile(results);
    }

  

    /**
     * setFdbUpdateProcessCapability
     * @param fdbUpdateProcessCapability the fdbUpdateProcessCapability to set
     */
    @Override
    public void setFdbUpdateProcessCapability(FdbUpdateProcessCapability fdbUpdateProcessCapability) {
        this.fdbUpdateProcessCapability = fdbUpdateProcessCapability;
    }

    @Override
    public StringBuilder createFdbAddCsvFile(List<FdbAddVo> results) {
        return fdbUpdateProcessCapability.createFdbAddCsvFile(results);
    }

    @Override
    public StringBuilder createFdbUpdateCsvFile(List<FdbUpdateVo> updateList) {
        return fdbUpdateProcessCapability.createFdbUpdateCsvFile(updateList);
    }

    @Override
    public StringBuilder createFdbAutoAddCsvFile(List<FdbAutoAddVo> autoAddList) {
        return fdbUpdateProcessCapability.createFdbAutoAddCsvFile(autoAddList);
    }
    
    @Override
    public StringBuilder createFdbAutoUpdateCsvFile(List<FdbAutoUpdateVo> autoUpdateList) {
        return fdbUpdateProcessCapability.createFdbAutoUpdateCsvFile(autoUpdateList);
    }
   
}
