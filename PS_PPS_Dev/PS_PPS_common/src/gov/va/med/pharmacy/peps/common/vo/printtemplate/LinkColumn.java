/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Link to a new instance of a Spring Web Flow.
 */
public class LinkColumn extends Column {
    private static final long serialVersionUID = 1L;
    private String event;
    private EntityType entityType;
    private Map<String, Object> parameters = new HashMap<String, Object>();

    /**
     * Set the FieldKey, EntityType, event, and parameters.
     * 
     * @param fieldKey
     *            FieldKey to display in this column
     * @param event
     *            String event to call in the link
     * @param parameters
     *            Map of String parameter name to FieldKey value of parameter
     */
    protected LinkColumn(FieldKey fieldKey, String event, Map<String, Object> parameters) {
        super(fieldKey);

        this.event = event;
        this.parameters = parameters;
    }

    /**
     * 
     * Set the FieldKey, EntityType, event (edit, save, etc.), parameters, and
     * if the column is sortable.
     * 
     * @param fieldKey
     *            FieldKey to display in this column
     * @param event
     *            String event to call in the link
     * @param parameters
     *            Map of String parameter name to FieldKey value of parameter
     * @param sortable
     *            boolean true if this column should be sortable
     */
    protected LinkColumn(FieldKey fieldKey, String event, Map<String, Object> parameters, boolean sortable) {
        super(fieldKey, sortable);

        this.event = event;
        this.parameters = parameters;
    }

    /**
     * getEvent.
     * @return event property
     */
    public String getEvent() {
        return event;
    }

    /**
     * setEvent.
     * @param event event property
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * getEntityType.
     * @return the entityType
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * setEntityType.
     * @param entityType entityType to set
     */
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    /**
     * getParameters for LinkColumn.
     * @return parameters property
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * setParameters  for LinkColumn.
     * @param parameters parameters property
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
