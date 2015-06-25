/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Link to a new instance of a Spring Web Flow, but check that the current form has not changed first.
 */
public class RedirectFlowLinkColumn extends FlowLinkColumn {
    private static final long serialVersionUID = 1L;

    /**
     * Set the FieldKey, flow ID, and parameters.
     * 
     * @param fieldKey FieldKey to display in this column
     * @param flowId String flow to call in the link
     * @param parameters Map of String parameter name to FieldKey value of parameter
     */
    public RedirectFlowLinkColumn(FieldKey fieldKey, String flowId, Map<String, Object> parameters) {
        super(fieldKey, flowId, parameters);
    }
    
    /**
     * Set the FieldKey, flow ID, parameters, and if the column is sortable.
     * 
     * @param fieldKey FieldKey to display in this column
     * @param flowId String flow to call in the link
     * @param parameters Map of String parameter name to FieldKey value of parameter
     * @param sortable boolean true if this column should be sortable
     */
    public RedirectFlowLinkColumn(FieldKey fieldKey, String flowId, Map<String, Object> parameters, boolean sortable) {
        super(fieldKey, flowId, parameters, sortable);
    }    
}
