/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing a AdministrationScheduleAssocVos
 */
public class AdministrationScheduleAssocVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    private AdministrationScheduleVo administrationSchedule;

    private boolean defaultSchedule;

    /**
     * getAdministrationSchedule.
     * @return adminSchedule property
     */
    public AdministrationScheduleVo getAdministrationSchedule() {
        return administrationSchedule;
    }

    /**
     * setAdministrationSchedule.
     * 
     * @param administrationSchedule adminSchedule property
     */
    public void setAdministrationSchedule(AdministrationScheduleVo administrationSchedule) {
        this.administrationSchedule = administrationSchedule;
    }

    /**
     * isDefaultSchedule.
     * @return defaultSchedule property
     */
    public boolean isDefaultSchedule() {
        return defaultSchedule;
    }

    /**
     * setDefaultSchedule.
     * @param defaultSchedule defaultSchedule property
     */
    public void setDefaultSchedule(boolean defaultSchedule) {
        this.defaultSchedule = defaultSchedule;
    }
    
    /**
     * This is the admin schedule short string method
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {
        
        return administrationSchedule.toShortString();
    }

}
