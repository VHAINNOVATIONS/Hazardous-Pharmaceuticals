/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Class to manage boolean display on the GUI
 */
public class BooleanOptionGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Label for option group on the GUI
     */
    private String label;
    
    /**
     * Map of options for option group
     */
    private Map<Boolean, String> options = new HashMap<Boolean, String>();

    /**
     * Constructor
     * 
     * @param label property
     * @param trueOption property
     * @param falseOption property
     */
    public BooleanOptionGroup(String label, String trueOption, String falseOption) {
        this.label = label;
        options.put(true, trueOption);
        options.put(false, falseOption);
    }

    /**
     * getOptions
     * @return options property
     */
    public Map<Boolean, String> getOptions() {
        return options;
    }

    /**
     * Sets the property
     * 
     * @param options property
     */
    public void setOptions(Map<Boolean, String> options) {
        this.options = options;
    }

    /**
     * getLabel
     * @return label property
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the property
     * 
     * @param label property
     */
    public void setLabel(String label) {
        this.label = label;
    }

}
