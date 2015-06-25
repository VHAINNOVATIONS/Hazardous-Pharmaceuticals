/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;




/**
 * Hold data concerning the session preferences.
 */
public class SessionPreferenceVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String prefName;
    private String prefValue;

    /**
     * Description
     * 
     * @return prefName property
     */
    public String getPrefName() {
        return prefName;
    }

    /**
     * Description
     * 
     * @param prefName prefName property
     */
    public void setPrefName(String prefName) {
        this.prefName = prefName;
    }

    /**
     * Description
     * 
     * @return prefValue property
     */
    public String getPrefValue() {
        return prefValue;
    }

    /**
     * Description
     * 
     * @param prefValue prefValue property
     */
    public void setPrefValue(String prefValue) {
        this.prefValue = prefValue;
    }

}
