/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
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
 * Update FDB Process.
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 * 
 */
public class FDBUpdateProcessServiceBean extends AbstractPepsStatelessSessionBean<FDBUpdateProcessService> 
    implements FDBUpdateProcessService {
    private static final long serialVersionUID = 1L;

    /**
     * Retrieves the NDC
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param pNdc ndc number
     * @param user UserVo
     * 
     * @return FDBSearchOptionVo FDBSearchOptionVo
     */
    @Override
    public FDBSearchOptionVo retrieveNdc(String pNdc, UserVo user) {
        return this.getService().retrieveNdc(pNdc, user);
    }
    
    /**
     * retrieves FDB Update Date
     * 
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @return fdbUpdateDate
     */
    @Override
    public String retrieveFdbUpdateDate() {
        return this.getService().retrieveFdbUpdateDate();
    }

    
    /**
     * Retrieve the Package Drug Pending List
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @return list of FdbAddvos
     */
    @Override
    public List<FdbAddVo> retrieveEPLAddList() {
        return this.getService().retrieveEPLAddList();
    }


    /**
     * deletes item from the EPL_FDB_ADD table
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param fdbItem  array of fdbItems
     */
    @Override
    public void deleteItemsFromAddList(String[] fdbItem) {
        this.getService().deleteItemsFromAddList(fdbItem);
        
    }

    /**
     * Retrieve the pendinglist by NDCs
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param ndcList list of ndcs numbers
     * @return List<FdbAddVo> list of fdbAddVos 
     * 
     * 
     */
    @Override
    public List<FdbAddVo> retrieveEPLPendingListByNdc(String[] ndcList) {
        return this.getService().retrieveEPLPendingListByNdc(ndcList);
    }

    /**
     * Retrieve the pendinglist by NDCs
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * 
     * @param pSeqNos - sequence numbers
     * @return List<FdbAddVo> list of fdbAddVos  
     */
    @Override
    public List<ProductVo> getClosestMatchProducts(List<String> pSeqNos) {
        return this.getService().getClosestMatchProducts(pSeqNos);
    }


    /**
     * retrieves EPL Pending list by NDC numbers
     * 
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param ndcList list of ndc numbers
     * 
     * @return list of FdbAddVos
     */
    @Override
    public List<FdbAddVo> retrieveEPLPendingListByNdc(List<String> ndcList) {
        return this.getService().retrieveEPLPendingListByNdc(ndcList);
    }




    /**
     * search NdcByNumber
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * 
     * @param pNdcNumber ndc number
     * 
     * @return list of NDCs
     */
    @Override
    public List<NdcVo> searchNdcByNumber(String pNdcNumber) {
        return this.getService().searchNdcByNumber(pNdcNumber);
    }

    /**
     * sort PendingList ByGCNSeqNo
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param pListToSort - list to sort
     * @return sorted list
     * 
     */
    @Override
    public List<FdbAddVo> sortPendingListByGCNSeqNo(List<FdbAddVo> pListToSort) {
        return this.getService().sortPendingListByGCNSeqNo(pListToSort);
    }

    /**
     * get FdbAddVo Items List
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param selectedNdcs selected ndcs
     * @param pendingList pending list
     * @return List of FdbAddVo
     */
    @Override
    public List<FdbAddVo> getFdbAddVoItemsList(List<String> selectedNdcs, List<FdbAddVo> pendingList) {
        return this.getService().getFdbAddVoItemsList(selectedNdcs, pendingList);
    }

    /**
     * createBlankTemplate
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param user user
     * @param selectedNdcs selected ndcs
     * @return productVo
     */
    @Override
    public ProductVo createBlankTemplate(UserVo user, List<String> selectedNdcs) {
        return this.getService().createBlankTemplate(user, selectedNdcs);
    }

    /**
     * 
     * create From Existing
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param pProductSeqNo product seq no
     * 
     * @param pNdcsNumbers ndc numbers
     * 
     * @param user - user
     * 
     * @return - return productVo
     */
    @Override
    public ProductVo createFromExisting(UserVo user, String[] pNdcsNumbers, String pProductSeqNo) {
        return this.getService().createFromExisting(null, pNdcsNumbers, pProductSeqNo);
    }

    /**
     * addProductToNdcs
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param pProductId product pProductId
     * 
     * @param pNdcsNumbers ndc numbers
     * 
     * @param pUser - user
     * @return ProcessedItemVo
     * @throws ValidationException 
     * 
     */
    @Override
    public ProcessedItemVo addProductToNdcs(UserVo pUser, String[] pNdcsNumbers, String pProductId) throws ValidationException {
        return getService().addProductToNdcs(pUser, pNdcsNumbers, pProductId);
        
    }

    /**
     * setFdbUpdateProcessCapability
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param fdbUpdateProcessCapability fdbUpdateProcessCapability
     * 
     */
    @Override
    public void setFdbUpdateProcessCapability(FdbUpdateProcessCapability fdbUpdateProcessCapability) {
        this.getService().setFdbUpdateProcessCapability(fdbUpdateProcessCapability);
        
    }

    /**
     * 
     * retrieveEPLUpdateList
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"   
     *
     * @return list of FdbUpdateVo
     */
    @Override
    public List<FdbUpdateVo> retrieveEPLUpdateList() {
        return this.getService().retrieveEPLUpdateList();
    }

    
    /**
     * deletes items from the updateList
     *
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"   
     * 
     * @param ids items array
     * 
     */
    @Override
    public void deleteItemsFromUpdateList(String[] ids) {
        this.getService().deleteItemsFromUpdateList(ids);
        
    }

    /**
     * retrieveManagedItem managed item
     *
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"   
     * 
     * @param pId - managedItem id,
     * 
     * @param pEntityType type.
     * 
     * @return managedItemVo
     * 
     */
    @Override
    public ManagedItemVo retrieveManagedItem(String pId, EntityType pEntityType) {
        return this.getService().retrieveManagedItem(pId, pEntityType);
    }


    /**
     * 
     * retrieves items from the EPL_FDB_AUTO_UPDATE  table
     *
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     *      
     * @return listo of FdbAutoUpdateVo
     */
    @Override
    public List<FdbAutoUpdateVo> retrieveEplAutoUpdateList() {
        return this.getService().retrieveEplAutoUpdateList();
    }

  


    /**
    * 
    * retrieves items from the EPL_FDB_AUTO_ADD table
    *
    * @return FdbAutoAddVo list of FdbAutoAddVo
    *
    * @ejb.interface-method
    * 
    * @ejb.transaction type = "Required"   
    * 
    */
    @Override
    public List<FdbAutoAddVo> retrieveEplAutoAddList() {
        return this.getService().retrieveEplAutoAddList();
    }


    /**
     * 
     * finds the Manufacturers By Name
     *
     * @param pManufacturerName ManufacturerName
     * 
     * @return ManufacturerVo ManufacturerVo
     *
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"   
     * 
     */
    @Override
    public ManufacturerVo findManufacturerByName(String pManufacturerName) {
        return this.getService().findManufacturerByName(pManufacturerName);
    }

    /**
     * 
     * creates CSV File
     *
     * @param searchResults searchResults
     * 
     * @return StringBuilder StringBuilder
     *
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"   
     * 
     */
    @Override
    public StringBuilder createFdbCSVFile(List<FDBSearchOptionResultVo> searchResults) {
        
        return getService().createFdbCSVFile(searchResults);
    }
    
    /**
     * 
     * creates CSV File
     *
     * @param pendingList pending list results
     * 
     * @return StringBuilder StringBuilder
     *
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"   
     * 
     */
    @Override
    public StringBuilder createFdbAddCsvFile(List<FdbAddVo> pendingList) {
        return getService().createFdbAddCsvFile(pendingList);
    }  

    /**
     * 
     * creates CSV File
     *
     * @param updateList update list results
     * 
     * @return StringBuilder StringBuilder
     *
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"   
     * 
     */
    @Override
    public StringBuilder createFdbUpdateCsvFile(List<FdbUpdateVo> updateList) {
        return getService().createFdbUpdateCsvFile(updateList);
    }
    
    /**
     * 
     * creates CSV File
     *
     * @param autoAddList add list results
     * 
     * @return StringBuilder StringBuilder
     *
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"   
     * 
     */
    @Override
    public StringBuilder createFdbAutoAddCsvFile(List<FdbAutoAddVo> autoAddList) {        
        return getService().createFdbAutoAddCsvFile(autoAddList);
    }
    
    /**
     * 
     * creates CSV File
     *
     * @param autoUpdateList auto update list results
     * 
     * @return StringBuilder StringBuilder
     *
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"   
     * 
     */
    @Override
    public StringBuilder createFdbAutoUpdateCsvFile(List<FdbAutoUpdateVo> autoUpdateList) {
        return getService().createFdbAutoUpdateCsvFile(autoUpdateList);
    }

    /**
     * delete Items From Auto Add List
     *
     * @param ids ids
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"   
     * 
     */
    @Override
    public void deleteItemsFromAutoAddList(String[] ids) {
        getService().deleteItemsFromAutoAddList(ids);
        
    }


    /**
     * delete Items From Auto Update List
     *
     * @param ids ids
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"   
     * 
     */
    @Override
    public void deleteItemsFromAutoUpdateList(String[] ids) {
        getService().deleteItemsFromAutoUpdateList(ids);
        
    }

    /**
     * getRemovedExistingList
     *
     * @param ndcList v
     * @param pendingList pendingList
     * @param pSelected selected
     * @return List<FdbAddVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     *    
     */
    @Override
    public List<FdbAddVo> getRemovedIfExistList(List<String> ndcList,
            List<FdbAddVo> pendingList, boolean pSelected) {
        return getService().getRemovedIfExistList(ndcList, pendingList, pSelected);

    }


}
