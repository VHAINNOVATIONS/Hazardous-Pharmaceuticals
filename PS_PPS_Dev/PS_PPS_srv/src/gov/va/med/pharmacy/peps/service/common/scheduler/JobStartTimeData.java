/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * 
 * JobStartTimeData 
 *
 */
public class JobStartTimeData {

    private Integer[] dayOfWweek;
    private Integer[] month;
    private Integer[] dayOfMonth;
    private Integer[] hours;
    private Integer[] mins;

    /**
     * getDayOfWweek
     * @return the dayOfWweek
     */
    public Integer[] getDayOfWweek() {
        
        dayOfWweek = new Integer[PPSConstants.I7];
        
        for (int i = 0; i < PPSConstants.I7; i++) {
            dayOfWweek[i] = i;
        }    
        
        return dayOfWweek;
    }

    /**
     * getMonth
     * @return the month
     */
    public Integer[] getMonth() {
        
        month = new Integer[PPSConstants.I12];
        
        for (int i = 0; i < PPSConstants.I12; i++) {
            month[i] = i + 1;
        }
        
        return month;
    }

    /**
     * getDayOfMonth
     * @return the dayOfMonth
     */
    public Integer[] getDayOfMonth() {
        dayOfMonth = new Integer[PPSConstants.I31];
        
        for (int i = 0; i < PPSConstants.I31; i++) {
            dayOfMonth[i] = i + 1;
        }
        
        return dayOfMonth;
    }

    /**
     * getHours
     * @return the hours
     */
    public Integer[] getHours() {
        hours = new Integer[PPSConstants.I24];
        
        for (int i = 0; i < PPSConstants.I24; i++) {
            hours[i] = i;
        }
        
        return hours;
    }

    /**
     * getMins
     * @return the mins
     */
    public Integer[] getMins() {
        mins = new Integer[PPSConstants.I60];
        
        for (int i = 0; i < PPSConstants.I60; i++) {
            mins[i] = i;
        }
        
        return mins;
    }
    
    
    
}
