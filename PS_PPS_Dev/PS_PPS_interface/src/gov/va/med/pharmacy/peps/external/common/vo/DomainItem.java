/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo;


import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;


/**
 * Encapsulated a VO and an action.
 */
public class DomainItem extends ValueObject {

    private static final long serialVersionUID = 1L;
    
    private ManagedItemVo item;
    private ItemAction action;
    private Map<FieldKey, Difference> difference;

    /**
     * Construct item.
     * 
     * @param item VO
     * @param action Action
     * @param difference set difference
     */
    public DomainItem(ManagedItemVo item, ItemAction action, Map<FieldKey, Difference> difference) {
        this.item = item;
        this.action = action;
        this.difference = difference;
    }

    /**
     * Get VO.
     * 
     * @return VO
     */
    public ManagedItemVo getItem() {
        return item;
    }

    /**
     * Get Action.
     * 
     * @return Action
     */
    public ItemAction getAction() {
        return action;
    }

    /**
     * Get set difference.
     * 
     * @return differences
     */
    public Map<FieldKey, Difference> getDifference() {
        return difference;
    }
}
