/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.va.med.pharmacy.peps.common.vo.diff.IgnoreDifference;


/**
 * Data representing a CS Federal Schedule
 */
public class CsFedScheduleVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String value;
    private Date inactivationDate;
    private ItemStatus itemStatus;
    
    @IgnoreDifference
    private RequestItemStatus requestItemStatus;
    
    private String rejectionReasonText;
    private long revisionNumber;
    private String requestRejectReason;

    /**
     * constructor
     */
    public CsFedScheduleVo() {
        super();
    }

    /**
     * The CsFedScheduleVo getId method
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * The CsFedScheduleVo setId method     
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * The CsFedScheduleVo getValue method.
     * 
     * @return value property
     */
    public String getValue() {
        return value;
    }

    /**
     * The CsFedScheduleVo setValue method.
     * @param value value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * The CsFedScheduleVo toShortString method.
     * @return short string value
     */
    @Override
    public String toShortString() {
        return value;
    }

    /**
     * getItemStatus.
     * @return itemStatus property
     */
    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    /**
     * setItemStatus.
     * @param itemStatus itemStatus property
     */
    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    /**
     * getRequestItemStatus.
     * @return requestItemStatus property
     */
    public RequestItemStatus getRequestItemStatus() {
        return requestItemStatus;
    }

    /**
     * setRequestItemStatus.
     * @param requestItemStatus requestItemStatus property
     */
    public void setRequestItemStatus(RequestItemStatus requestItemStatus) {
        this.requestItemStatus = requestItemStatus;
    }

    /**
     * getRejectionReasonText.
     * @return rejectionReasonText property
     */
    public String getRejectionReasonText() {
        return rejectionReasonText;
    }

    /**
     * setRejectionReasonText.
     * @param rejectionReasonText rejectionReasonText property
     */
    public void setRejectionReasonText(String rejectionReasonText) {
        this.rejectionReasonText = trimToEmpty(rejectionReasonText);
    }

    /**
     * getInactivationDate.
     * @return inactivationDate property
     */
    public Date getInactivationDate() {
        return inactivationDate;
    }

    /**
     * setInactivationDate.
     * @param inactivationDate inactivationDate property
     */
    public void setInactivationDate(Date inactivationDate) {
        this.inactivationDate = inactivationDate;
    }

    /**
     * getRevisionNumber.
     * @return revisionNumber property
     */
    public long getRevisionNumber() {
        return revisionNumber;
    }

    /**
     * setRevisionNumber.
     * @param revisionNumber revisionNumber property
     */
    public void setRevisionNumber(long revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    /**
     * getRequestRejectReason.
     * @return requestRejectReason property
     */
    public String getRequestRejectReason() {
        return requestRejectReason;
    }

    /**
     * setRequestRejectReason.
     * @param requestRejectReason requestRejectReason property
     */
    public void setRequestRejectReason(String requestRejectReason) {
        this.requestRejectReason = trimToEmpty(requestRejectReason);
    }
    
    
    /**
     * Method used to populate the deaSchedule dropdown on a product.
     * 
     * 
     * @return collection of dea schedules
     */
    public Collection<String> getDeaSchedule() {
        Collection<String> ret = new ArrayList<String>();
               
        String zero = "0-Unscheduled";
        String one = "1-Schedule 1 Item";
        String two = "2-Schedule 2 Item";
        String three = "3-Schedule 3 Item";
        String four = "4-Schedule 4 Item";
        String five = "5-Schedule 5 Item";
        String six = "6-Legend Item";
        String nine = "9-Over the Counter";
        
 //       String csFed = getValue().split(" ")[0];
        

//        if ("0".equals(csFed)) {
        ret.add(zero);
        ret.add(one);
        ret.add(two);
        ret.add(three);
        ret.add(four);
        ret.add(five);
        ret.add(six);
        ret.add(nine);

//        }
//        
//        if ("1".equals(csFed)) {
//            ret.add(one);
//        }
//        
//        if ("2".equals(csFed) || "2N".equalsIgnoreCase(csFed)) {
//            ret.add(two);
//        }
//        
//        if ("3".equals(csFed) || "3N".equalsIgnoreCase(csFed)) {
//            ret.add(two);
//            ret.add(three);
//        }
//        
//        if ("4".equals(csFed)) {
//            ret.add(two);
//            ret.add(three);
//            ret.add(four);
//        }
//        
//        if ("5".equals(csFed)) {
//            ret.add(two);
//            ret.add(three);
//            ret.add(four);
//            ret.add(five);
//        }
        
        return ret;
    }
}
