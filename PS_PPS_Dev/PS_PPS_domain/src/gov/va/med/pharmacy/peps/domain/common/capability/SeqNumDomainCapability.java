/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform CRUD operations on SeqNums
 */
public interface SeqNumDomainCapability {

    /**
     * SeqNumDomainCapability
     * Get next available ID for the given {@link EntityType}.
     * <p>
     * Maps the {@link EntityType} to the appropriate table name.
     * <p>
     * Assumes that the given {@link EntityType} is not null!
     * 
     * @param entityType {@link EntityType} for which to generate an ID
     * @param user {@link UserVo} performing the action
     * @return String ID
     */
    String generateId(EntityType entityType, UserVo user);

    /**
     * Get next available id for epl_cs_fed_schedules table
     * 
     * @param user {@link UserVo} performing the action
     * @return String
     */
    String generateFedScheduleId(UserVo user);

    /**
     * Generate Notification Id
     * 
     * @param user {@link UserVo} performing the action
     * @return String generated epl_notifications_id
     */
    String generateNotificationId(UserVo user);

    /**
     * Generate Item audit history id
     * 
     * @param user {@link UserVo} performing the action
     * @return String generated epl_item_audit_history id
     */
    String generateItemAuditHistoryId(UserVo user);

    /**
     * Generates the owner id using site and unique key
     * 
     * @param user {@link UserVo} performing the action
     * @return String with generated ownerId
     */
    String generatedOwnerId(UserVo user);

    /**
     * Generate search template id
     * 
     * @param user {@link UserVo} performing the action
     * @return Long
     */
    Long generateSearchTemplateId(UserVo user);

    /**
     * Generate CMOP ID
     * 
     * @param seed String
     * @param user {@link UserVo} performing the action
     * @return String
     */
    String generateCmopId(StringBuffer seed, UserVo user);

    /**
     * setCmopIdGeneratorValue
     * @param seed seed
     * @param value value
     * @param user user
     */
    void setCmopIdGeneratorValue(String seed, String value, UserVo user);

    /**
     * generate print template id
     * 
     * @param user {@link UserVo} performing the action
     * @return Long
     */
    Long generatePrintTemplateId(UserVo user);
}
