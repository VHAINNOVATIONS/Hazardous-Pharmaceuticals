/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


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





/**
 * FDB update process Service
 */
public interface FDBUpdateProcessService {

    /**
     * Retrieves Package Drug pending list.
     * @param pNdc to retrieve
     * @param user UserVo
     * @return returns FdbAddVo
     */
    FDBSearchOptionVo retrieveNdc(String pNdc, UserVo user);


    /**
     * retrieve EPL Pending list
     * @return FdbAddVo
     */
    List<FdbAddVo> retrieveEPLAddList();

    /**
     * retrieves NDCs from FDB_ADD table that where selected for the Matching
     * page
     * 
     * @param pNdcList NDC number
     * @return list of FdbAddVo
     */
    List<FdbAddVo> retrieveEPLPendingListByNdc(String[] pNdcList);

    /**
     * retrieveFdbUpdateDate
     * @return updateDate
     */
    String retrieveFdbUpdateDate();
    
    /**
     * returns closest match products by GCNSequence Number
     * 
     * @param pSeqNos  the selected Sequence Nos
     * @return closest match productVOs
     */
    List<ProductVo> getClosestMatchProducts(List<String> pSeqNos);

    /**
     * Add selected NDCs from Matching page to selected Product
     * 
     * @param pUser user Vo
     * @param pNdcsNumbers NDC ids
     * @param pProductId selected product
     * @return ProcessedItemVo
     * @throws ValidationException 
     */
    ProcessedItemVo addProductToNdcs(UserVo pUser, String[] pNdcsNumbers, String pProductId) throws ValidationException;

    /**
     * Retrieve EPL pending list by  NDC numbers
     * 
     * @param ndcList list of Ndc Numbers
     * @return list of FdbAddVo 
     */
    List<FdbAddVo> retrieveEPLPendingListByNdc(List<String> ndcList);

    
    /**
     * removes NDC from EPL_FDB_ADD table
     * 
     * @param fdbItem - array of Items to delete
     */
    void deleteItemsFromAddList(String[] fdbItem);


    /**
     * searches EPL db by NDC Number
     * 
     * @param pNdcNumber NDC Number
     * @return list of NdcVos
     */
    List<NdcVo> searchNdcByNumber(String pNdcNumber);

    /**
     * 
     * Sorts list of FdbAddVos by Sequence Nos
     * 
     * @param pListToSort list to sort
     * @return sorted list
     */
    List<FdbAddVo> sortPendingListByGCNSeqNo(List<FdbAddVo> pListToSort);

    /**
     * 
     * gets the fdbItems that are selected and retrieve from the list.
     *
     * @param selectedNdcs - selected FDB ITEMS
     * @param pendingList list that contains search results
     * @return the fdbItem list.
     */
    List<FdbAddVo> getFdbAddVoItemsList(List<String> selectedNdcs, List<FdbAddVo> pendingList);
    
    /**
     * 
     * Creates a blank template Product
     *
     * @param pUser - user
     * @param pNdcNumbers ndcs 
     * @return returns a productVo with selected Ndcs
     */
    ProductVo createBlankTemplate(UserVo pUser, List<String> pNdcNumbers);

    /**
     * 
     * Creates a Product from existing
     *
     * @param user - user
     * @param pNdcsNumbers - ndcs that were selected
     * @param pProductSeqNo - the product that was selected
     * @return returns a productVo from selected Ndc
     */
    ProductVo createFromExisting(UserVo user,  String[] pNdcsNumbers, String pProductSeqNo);
    
    /**
     * setter for the FdbUpdateProcessCapability
     *
     * @param fdbUpdateProcessCapability fdbUpdateProcessCapability
     */
    void setFdbUpdateProcessCapability(FdbUpdateProcessCapability fdbUpdateProcessCapability);


    /**
     * 
     * retrieves items from the update list (queue)
     *
     * @return list of FdbUpdateVo
     */
    List<FdbUpdateVo> retrieveEPLUpdateList();

    
    /**
     * delete Items From Auto Add List
     * @param ids ids
     */
    void deleteItemsFromAutoAddList(String[] ids);

    /**
     * deletes items from the updateList
     * @param ids  ids to delete 
     */
    void deleteItemsFromUpdateList(String[] ids); 
    
    
    /**
     * deletes Items From Auto Update List
     *
     * @param ids ids to delete 
     */
    void deleteItemsFromAutoUpdateList(String[] ids);

    /**
     * returns a ManagedItem
     *
     * @param pId id of managed item
     * @param pEntityType managed Item type
     * @return Managed Item
     */
    ManagedItemVo retrieveManagedItem(String pId, EntityType pEntityType);

     /**
      * 
      * retrieves items from the EPL_FDB_AUTO_UPDATE  table
      *
      * @return listo of FdbAutoUpdateVo
      */
    List<FdbAutoUpdateVo> retrieveEplAutoUpdateList();

    /**
     * 
     * retrieves items from the EPL_FDB_AUTO_ADD table
     *
     * @return FdbAutoAddVo
     */
    List<FdbAutoAddVo> retrieveEplAutoAddList();
    
    /**
     * 
     * findManufacturer By Name
     *
     * @param pManufacturerName name
     * @return list of ManufacturerVos
     */
    ManufacturerVo findManufacturerByName(String pManufacturerName);
    
    /**
     * creates CSV File from list results
     *
     * @param searchResults results
     * @return StringBuilder
     */
    StringBuilder createFdbCSVFile(List<FDBSearchOptionResultVo> searchResults);

    /**
     * getRemovedExistingList
     *
     * @param ndcList v
     * @param pendingList pendingList
     * @param selected 
     * @return List<FdbAddVo> 
     */
    List<FdbAddVo> getRemovedIfExistList(List<String> ndcList, List<FdbAddVo> pendingList, boolean selected);

    /**
     * creates CSV File from list results
     *
     * @param pendingList pendingList
     * @return StringBuilder
     */
    StringBuilder createFdbAddCsvFile(List<FdbAddVo> pendingList);


    /**
     * creates CSV File from list results
     * @param updateList update results
     * @return StringBuilder
     */
    StringBuilder createFdbUpdateCsvFile(List<FdbUpdateVo> updateList);

    /**
     * creates CSV File from list results
     * @param autoAddList auto add list results
     * @return StringBuilder
     */
    StringBuilder createFdbAutoAddCsvFile(List<FdbAutoAddVo> autoAddList);

    /**
     * creates CSV File from list results
     * @param autoUpdateList auto update results
     * @return StringBuilder
     */
    StringBuilder createFdbAutoUpdateCsvFile(List<FdbAutoUpdateVo> autoUpdateList);
    
    
}
