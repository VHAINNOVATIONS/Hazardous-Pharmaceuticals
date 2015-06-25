/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.action;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermConjunction;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.ConversationScope;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.FlowScope;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;


/**
 * Define actions related to re-parenting Products and NDCs
 */
public class MoveChildrenAction extends PepsAction {
    private static final long serialVersionUID = 1L;

    @FlowScope
    private String searchType;

    @FlowScope
    private String itemId;

    @FlowScope
    private EntityType itemType;

    @ConversationScope
    private ManagedItemVo itemOld1;

    @ConversationScope
    private ManagedItemVo itemOld2;
        
    @ConversationScope
    private ManagedItemVo item;

    @ConversationScope
    private ManagedItemVo item2;

    @ConversationScope
    private SearchTemplateVo searchTemplate; // shared with the SearchAction

    private ManagedItemService managedItemService;

    /**
     * Move Products flow requires additional search criteria at local based upon the following rules:
     * <p>
     * If a local orderable item was selected first, search results are limited to local orderable items with the same parent
     * as the selected local orderable item.
     * <p>
     * If a national orderable item was selected first, search results will be limited to any local orderable items that have
     * the selected national orderable item as its parent.
     * 
     * @return SUCCESS
     */
    public String addMoveProductsCriteria() {
        if (isLocal() && item instanceof OrderableItemVo) {
            OrderableItemVo orderableItem = (OrderableItemVo) item;
            String id;

            if (orderableItem.isNational()) {
                id = orderableItem.getId();
            }
            else {
                id = orderableItem.getOrderableItemParent().getId();
            }

            SearchTermVo lois = new SearchTermVo(EntityType.ORDERABLE_ITEM, FieldKey.ORDERABLE_ITEM_PARENT, id,
                SearchType.EQUALS);
            lois.setSearchTermConjunction(SearchTermConjunction.AND);
            searchTemplate.getSearchCriteria().getSearchTerms().add(lois);
        }

        return SUCCESS;
    }

    /**
     * 
     * @return managedItemService property
     */
    public ManagedItemService getManagedItemService() {
        return managedItemService;
    }

    /**
     * 
     * @param managedItemService managedDataService property
     */
    public void setManagedItemService(ManagedItemService managedItemService) {
        this.managedItemService = managedItemService;
    }

    /**
     * 
     * @return searchType property
     */
    public String getSearchType() {
        return searchType;
    }

    /**
     * @param searchType searchType property
     */
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    /**
     * 
     * @return itemId property
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 
     * @return itemType property
     */
    public EntityType getItemType() {
        return itemType;
    }

    /**
     * 
     * @param itemId itemId property
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * 
     * @param itemType itemType property
     */
    public void setItemType(EntityType itemType) {
        this.itemType = itemType;
    }

    /**
     * 
     * @return item property
     */
    public ManagedItemVo getItem() {
        return item;
    }

    /**
     * 
     * @return item2 property
     */
    public ManagedItemVo getItem2() {
        return item2;
    }

    /**
     * 
     * @param item item property
     */
    public void setItem(ManagedItemVo item) {
        this.item = item;
    }

    /**
     * 
     * @param item2 item2 property
     */
    public void setItem2(ManagedItemVo item2) {
        this.item2 = item2;
    }

    /**
     * Retrieve the item using the provided {@link #itemId} and {@link ItemType}. Clone the result and set the cloned value
     * to {@link #oldItem}.
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     */
    public String retrieveItem1() throws ItemNotFoundException {
        setItem(managedItemService.retrieve(itemId, itemType));
        itemOld1 = item.copy();

        return SUCCESS;
    }

    /**
     * Retrieve item2 using the provided {@link #itemId} and {@link ItemType}.
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     */
    public String retrieveItem2() throws ItemNotFoundException {
        setItem2(managedItemService.retrieve(itemId, itemType));
        itemOld2 = item2.copy();

        return SUCCESS;
    }

    /**
     * Update parent/child relationships (ex. product\ndcs)
     * 
     * @return END
     * @throws ValidationException if error validating data in the ManagedItemVo {@link #item}
     * @throws OptimisticLockingException
     */
    public String updateParentChildRelationships() throws ValidationException, OptimisticLockingException {
        findAndUpdateChildrenRelationships(item, itemOld1);
        findAndUpdateChildrenRelationships(item2, itemOld2);
        
        // Retrieve the updated items from the DB
        if (item != null) {
            this.itemId = item.getId();
            this.itemType = item.getEntityType();
            this.retrieveItem1();
        }

        if (item2 != null) {
            this.itemId = item2.getId();
            this.itemType = item2.getEntityType();
            this.retrieveItem2();
        }

        return END;
    }

    
    /**
     * Code to find new children for a given item and update their parent/child relationships
     * 
     * Code pulled from updateParentChildRelationships to avoid duplicated code. 
     * 
     * @param newItem new item, with children 
     * @param oldItem Old Item, with children
     * @throws OptimisticLockingException 
     * @throws ValidationException
     */
    private void findAndUpdateChildrenRelationships(ManagedItemVo newItem, ManagedItemVo oldItem) throws OptimisticLockingException, ValidationException {
        List<ManagedItemVo> itemChildren = newItem.getChildren();

        List<ManagedItemVo> itemOldChildren = oldItem.getChildren();  
        
        
        for (ManagedItemVo child : itemChildren) {
            boolean bMatch = true;
            
            for (ManagedItemVo oldChild : itemOldChildren) {
                if (child.getId().equals(oldChild.getId())) {
                    bMatch = false;
                    break;
                }
            }

            if (bMatch) {
                managedItemService.updateParentChildRelationships(child, newItem, getUser());
            }
        }
    }
    
    
    /**
     * 
     * @return searchTemplate property
     */
    public SearchTemplateVo getSearchTemplate() {
        return searchTemplate;
    }

    /**
     * 
     * @param searchTemplate searchTemplate property
     */
    public void setSearchTemplate(SearchTemplateVo searchTemplate) {
        this.searchTemplate = searchTemplate;
    }
}