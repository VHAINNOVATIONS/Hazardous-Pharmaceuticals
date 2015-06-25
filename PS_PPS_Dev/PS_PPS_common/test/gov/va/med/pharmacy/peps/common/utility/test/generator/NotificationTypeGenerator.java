/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.UUID;

import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.NotificationTypeVo;


/**
 * Generate ImprintVo
 */
public class NotificationTypeGenerator extends VoGenerator<NotificationTypeVo> {
    
    /**
     * Generate a list of ImprintVo
     * 
     * @return List<ImprintVo>
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    @Override
    public List<NotificationTypeVo> generate() {
        List<NotificationTypeVo> notificationTypeVo = new ArrayList<NotificationTypeVo>();

        notificationTypeVo.add(pseudoRandom());
        notificationTypeVo.add(pseudoRandom());
        notificationTypeVo.add(pseudoRandom());
        notificationTypeVo.add(pseudoRandom());
        notificationTypeVo.add(pseudoRandom());

        return notificationTypeVo;
    }

    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
    
    /**
     * Per PEPS539 notification types enumerated in NotificationType class
     * 
     * @return List<NotificationTypeVo>
     */
    public List<NotificationTypeVo> loadData() {
        long id = 1;
        List<NotificationTypeVo> notificationTypes = new ArrayList<NotificationTypeVo>();
        
        NotificationType[] notifications = NotificationType.values();
        List<String> notificationFieldKeys = Arrays.asList("ndc.approved", "ndc.rejected", "ndc.inactivated", 
            "ndc.modified", "oi.approved", "oi.rejected", 
            "oi.inactivated", "oi.modified", "product.approved", 
            "product.rejected", "product.inactivated", "product.modified", 
            "product.modified.not.marked.local.use",
            "product.modified.marked.local.use");

        int i = 0;
        
        for (NotificationType type : notifications) {            
            NotificationTypeVo notification = new NotificationTypeVo();
            notification.setId(Long.toString(id++));
            notification.setType(notificationFieldKeys.get(i++));
            notification.setValue(type.toString());
            notificationTypes.add(notification);
        }
        
        return notificationTypes;
    }
    
    /**
     * Generate a pseudo-random (not all fields are actually random) instance.
     * 
     * @return NotificationTypeVo
     */
    public NotificationTypeVo pseudoRandom() {
        NotificationTypeVo notificationType = new NotificationTypeVo();

        notificationType.setValue(UUID.randomUUID().toString());
        notificationType.setType(UUID.randomUUID().toString());

        return notificationType;        
    }
}
