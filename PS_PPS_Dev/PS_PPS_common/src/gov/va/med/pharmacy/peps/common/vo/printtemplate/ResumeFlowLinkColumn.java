/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Link back to the current Spring Web Flow execution.
 */
public class ResumeFlowLinkColumn extends FlowLinkColumn {
    private static final long serialVersionUID = 1L;

    private String eventId;

    /**
     * Set the FieldKey and parameters. Set the {@link FlowLinkColumn} flowId to null since it can be derived from the
     * current request. Set the event trigger when the link is clicked.
     * 
     * @param fieldKey FieldKey to display in this column
     * @param eventId Spring Web Flow event to trigger when link is clicked
     * @param parameters Map of String parameter name to FieldKey value of parameter
     */
    public ResumeFlowLinkColumn(FieldKey fieldKey, String eventId, Map<String, Object> parameters) {
        super(fieldKey, null, parameters);

        this.eventId = eventId;
    }
    
    /**
     * Set the FieldKey and parameters. Set the {@link FlowLinkColumn} flowId to null since it can be derived from the
     * current request. Set the event trigger when the link is clicked. Also set if the column should be sortable.
     * 
     * @param fieldKey FieldKey to display in this column
     * @param eventId Spring Web Flow event to trigger when link is clicked
     * @param parameters Map of String parameter name to FieldKey value of parameter
     * @param sortable boolean true if this column should be sortable
     */
    public ResumeFlowLinkColumn(FieldKey fieldKey, String eventId, Map<String, Object> parameters, boolean sortable) {
        this(fieldKey, eventId, parameters);

        setSortable(sortable);        
    }

    /**
     * getEventId.
     * @return eventId property
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * setEventId.
     * @param eventId eventId property
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
