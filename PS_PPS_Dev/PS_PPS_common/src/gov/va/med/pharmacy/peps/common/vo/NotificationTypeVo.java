/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Comparator;


/**
 * Notification type
 */
public class NotificationTypeVo extends ValueObject implements Selectable {

    private static final long serialVersionUID = 1L;
    private static final String DELIM = "[.]";

    private String id;
    private String value;
    private String type;

    /**
     * the constructor
     */
    public NotificationTypeVo() {

        super();

    }

    /**
     * Returns a comparator to sort the notification types so that they end up in the order the presentation layer expects
     * them too, otherwise this becomes dependant on the database not changing.
     * 
     * @return a comparator
     */
    public static Comparator<NotificationTypeVo> getComparator() {

        return new Comparator<NotificationTypeVo>() {

            private String product = "product";
            private String oi = "oi";
            private String app = "approved";
            private String rej = "rejected";
            private String inac = "inactivated";
            private String mod = "modified";

            /**
             * OI before Product before NDC approved before Rejected before Modified before Inactived before Marked for local
             * use
             * @param o1
             * @param o2
             * @return
             */
            public int compare(NotificationTypeVo o1, NotificationTypeVo o2) {

                String[] value1 = o1.getValue().split(DELIM);
                String[] value2 = o2.getValue().split(DELIM);
                String type1 = "";
                String type2 = "";
                String action1 = "";
                String action2 = "";

                if (value1.length >= 1) {
                    type1 = value1[0];
                }

                if (value1.length >= 1) {
                    type2 = value2[0];
                }

                if (value1.length >= 2) {
                    action1 = value1[1];
                }

                if (value2.length >= 2) {
                    action2 = value2[1];
                }

                if (type1.equalsIgnoreCase(type2)) {
                    if (action1.equalsIgnoreCase(app)) {
                        return -1;
                    } else if (action2.equalsIgnoreCase(app)) {
                        return 1;
                    } else if (action1.equalsIgnoreCase(rej)) {
                        return -1;
                    } else if (action2.equalsIgnoreCase(rej)) {
                        return 1;
                    } else if (action1.equalsIgnoreCase(mod)) {
                        return -1;
                    } else if (action2.equalsIgnoreCase(mod)) {
                        return 1;
                    } else if (action1.equalsIgnoreCase(inac)) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else {
                    if (type1.equalsIgnoreCase(oi)) {
                        return -1;
                    } else if (type2.equalsIgnoreCase(oi)) {
                        return 1;
                    } else if (type1.equalsIgnoreCase(product)) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        };
    }

    /**
     * getId
     * 
     * @return id property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {

        return id;
    }

    /**
     * setId
     * 
     * @param id of String type
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * getValue
     * 
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {

        return value;
    }

    /**
     * setValue
     * 
     * @param value of String type
     */
    public void setValue(String value) {

        this.value = trimToEmpty(value);
    }

    /**
     * toShortString
     * 
     * @return short string value
     */
    @Override
    public String toShortString() {

        return value;
    }

    /**
     * getType
     * 
     * @return type property
     */
    public String getType() {

        return type;
    }

    /**
     * setType
     * 
     * @param type type property
     */
    public void setType(String type) {

        this.type = trimToEmpty(type);
    }

}
