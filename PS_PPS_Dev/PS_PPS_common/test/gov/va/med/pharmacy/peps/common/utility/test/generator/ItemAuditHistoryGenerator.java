/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;


/**
 * Generate ImprintVo
 */
public class ItemAuditHistoryGenerator extends VoGenerator<ItemAuditHistoryVo> {
    
    /**
     * Generate a list of ImprintVo
     * 
     * @return List<ImprintVo>
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    @Override
    public List<ItemAuditHistoryVo> generate() {
        List<ItemAuditHistoryVo> itemAuditHistoryVos = new ArrayList<ItemAuditHistoryVo>();

        itemAuditHistoryVos.add(psuedoRandom());
        itemAuditHistoryVos.add(psuedoRandom());
        itemAuditHistoryVos.add(psuedoRandom());
        itemAuditHistoryVos.add(psuedoRandom());
        itemAuditHistoryVos.add(psuedoRandom());

        return itemAuditHistoryVos;
    }

    /**
     * Generate a pseudo-random (not all fields are actually random) instance.
     * 
     * @return ItemAuditHistoryVo
     */
    public ItemAuditHistoryVo psuedoRandom() {
        
        // these are pre-populated database values
        return psuedoRandom("9991", EntityType.ORDERABLE_ITEM);
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
    
    /**
     * Generate a pseudo-random (not all fields are actually random) instance.
     * 
     * @param auditItemId vaDfOwnerId
     * @param entityType entityType (product, ndc, orderable item
     * 
     * @return ItemAuditHistoryVo
     */
    public ItemAuditHistoryVo psuedoRandom(String auditItemId, EntityType entityType) {
        ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
        
        audit.setEventCategory(EventCategory.APPROVED_REQUEST);
        audit.setAuditItemType(entityType);
        audit.setAuditItemId(auditItemId);
        audit.setSiteName(UUID.randomUUID().toString());
        audit.setReason(UUID.randomUUID().toString());

        return audit;
    }

}
