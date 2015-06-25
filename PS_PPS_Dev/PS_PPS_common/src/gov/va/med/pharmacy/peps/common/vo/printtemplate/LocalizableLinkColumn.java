/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


import java.util.Map;


/**
 * Display localizable text as the link
 */
public class LocalizableLinkColumn extends LinkColumn {
    private static final long serialVersionUID = 1L;

    private String key;

    /**
     * Set the key to localize, the action to link to, and the parameters.
     * 
     * @param key String key in Struts default resource bundle to localize as the text displayed in the link
     * @param event Spring Web Flow event to trigger
     * @param parameters Map for String to FieldKey parameters to set on the link
     */
    public LocalizableLinkColumn(String key, String event, Map<String, Object> parameters) {
        super(null, event, parameters);

        this.key = key;
    }

    /**
     * Set the key to localize, the action to link to, the parameters, and if the column should be sortable.
     * 
     * @param key String key in Struts default resource bundle to localize as the text displayed in the link
     * @param event Spring Web Flow event to trigger
     * @param parameters Map of String parameter name to FieldKey value of parameter
     * @param sortable boolean true if this column should be sortable
     */
    public LocalizableLinkColumn(String key, String event, Map<String, Object> parameters, boolean sortable) {
        super(null, event, parameters, sortable);

        this.key = key;
    }

    /**
     * getKey.
     * @return key property
     */
    public String getKey() {
        return key;
    }

    /**
     * setKey.
     * @param key key property
     */
    public void setKey(String key) {
        this.key = key;
    }
}
