/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;




/**
 * Hospital Location
 */
public class HospitalLocationVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String administrationTimes;
    private HospitalLocationSelectionVo hospitalLocationSelection;
    private String shifts;

    /**
     * getAdministrationTimes.
     * @return administrationTimes property
     */
    public String getAdministrationTimes() {
        return administrationTimes;
    }

    /**
     * setAdministrationTimes.
     * @param administrationTimes administrationTimes property
     */
    public void setAdministrationTimes(String administrationTimes) {
        this.administrationTimes = trimToEmpty(administrationTimes);
    }

    /**
     * getHospitalLocationSelection.
     * @return hospitalLocation property
     */
    public HospitalLocationSelectionVo getHospitalLocationSelection() {
        return hospitalLocationSelection;
    }

    /**
     * setHospitalLocationSelection.
     * @param hospitalLocation hospitalLocation property
     */
    public void setHospitalLocationSelection(HospitalLocationSelectionVo hospitalLocation) {
        this.hospitalLocationSelection = hospitalLocation;
    }

    /**
     * getShifts.
     * @return shifts property
     */
    public String getShifts() {
        return shifts;
    }

    /**
     * setShifts.
     * @param shifts shifts property
     */
    public void setShifts(String shifts) {
        this.shifts = trimToEmpty(shifts);
    }

    /**
     * getId.
     * @return id property
     */
    public Long getId() {
        return id;
    }

    /**
     * setId.
     * @param id id property
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * toShortString returns toString unless overridden in the HospitalLocationVo.
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    public String toShortString() {
        
        
        String s1 = FieldKey.getLocalizedName(FieldKey.HOSPITAL_LOCATION_MULTIPLE, Locale.getDefault());
        String s2 = FieldKey.getLocalizedName(FieldKey.SHIFTS, Locale.getDefault());
        String s3 = FieldKey.getLocalizedName(FieldKey.WARD_ADMIN_TIMES, Locale.getDefault());
        
        StringBuffer value = new StringBuffer(s1 + ": " 
            + (hospitalLocationSelection == null ? PPSConstants.NOT_SPECIFIED : hospitalLocationSelection.toShortString()) 
            + PPSConstants.P_TAG + s2 + ": " 
            + (shifts == null ? PPSConstants.NOT_SPECIFIED : shifts) + PPSConstants.P_TAG 
            + s3 + ": " 
            + (administrationTimes == null ? PPSConstants.NOT_SPECIFIED : administrationTimes));

        value.append(PPSConstants.P_TAG);
        
        return value.toString();
    }
}
