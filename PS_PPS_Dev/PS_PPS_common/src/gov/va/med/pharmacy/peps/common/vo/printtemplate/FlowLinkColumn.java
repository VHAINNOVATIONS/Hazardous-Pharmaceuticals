/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Link to a new instance of a Spring Web Flow.
 */
public class FlowLinkColumn extends Column {
    private static final long serialVersionUID = 1L;
    private String flowId;
    private Map<String, Object> parameters = new HashMap<String, Object>();

    /**
     * Set the FieldKey, flow ID, and parameters.
     * 
     * @param fieldKey FieldKey to display in this column
     * @param flowId String flow to call in the link
     * @param parameters Map of String parameter name to FieldKey value of parameter
     */
    protected FlowLinkColumn(FieldKey fieldKey, String flowId, Map<String, Object> parameters) {
        super(fieldKey);

        this.flowId = flowId;
        this.parameters = parameters;
    }

    /**
     * Set the FieldKey, flow ID, parameters, and if the column is sortable.
     * 
     * @param fieldKey FieldKey to display in this column
     * @param flowId String flow to call in the link
     * @param parameters Map of String parameter name to FieldKey value of parameter
     * @param sortable boolean true if this column should be sortable
     */
    protected FlowLinkColumn(FieldKey fieldKey, String flowId, Map<String, Object> parameters, boolean sortable) {
        super(fieldKey, sortable);

        this.flowId = flowId;
        this.parameters = parameters;
    }

    /**
     * getFlowId.
     * @return flowId property
     */
    public String getFlowId() {
        return flowId;
    }

    /**
     * setFlowId.
     * @param flowId flowId property
     */
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    /**
     * getParameters.
     * @return parameters property
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * setParameters.
     * @param parameters parameters property
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
