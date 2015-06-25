/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * ValueObject representing a user's chosen fields to display in a table.
 */
public class PrintTemplateVo extends ValueObject {

    private static final long serialVersionUID = 1L;
    private static final String ITEM_ID = "itemId";
    private static final String ITEM_TYPE = "itemType";
    private static final String EDIT = "edit";
    private static final String PTVO = "printTemplateVo";
    private static final String PRODUCT = "product";

    /**
     * Used as the primary key for the printTemplate
     */
    private String id;

    /**
     * The print template location in the current application
     */
    private TemplateLocation templateLocation;

    /**
     * Used to record the name of the template the user assigns
     */
    private String templateName;

    /**
     * used to record if the print template is a multiselect
     */
    private boolean isMultiSelect;

    /**
     * The Columns in the current print template
     */
    private List<Column> fields = new ArrayList<Column>();


    /**
     * Default constructor sets up a PrintTemplateVo for a table that does not include a column for selecting multiple rows.
     */
    public PrintTemplateVo() {
        this(false);
    }

    /**
     * Add a column to the PrintTemplateVo if the table should include a column for selecting multiple rows.
     * 
     * @param multiselect boolean if this table should include a column for selecting multiple rows
     */
    public PrintTemplateVo(boolean multiselect) {
        if (multiselect) {
            InputColumn selectColumn = new InputColumn(FieldKey.SELECT);
            selectColumn.setSortable(false);
            fields.add(selectColumn);
        }

        this.isMultiSelect = multiselect;
    }
    
    /**
     * Populates a PrintTemplateVo's Fields collection with a fresh set of fields. This clears any existing fields in the
     * collection.
     * 
     * This code was moved from class AdvancedSearchAction in presentation.
     * 
     * @param printTemplateVo printTemplateVo to populate, will clear any previous values.
     * @param fieldKeys field key collection used to populate the print template
     */
    public static void populatePrintFieldCollection(PrintTemplateVo printTemplateVo, List<FieldKey> fieldKeys) {
        
        if (printTemplateVo.getFields().size() > 0) {
            throw new CommonException(CommonException.ILLEGAL_ARGUMENT, PTVO);
        }


        int count = 0;

        for (FieldKey field : fieldKeys) {
            if (FieldKey.SELECT.equals(field)) {
                printTemplateVo.addInputField(field);
                count++;
            } else if (count == 1) {
                
                // if the count is 1 set the link parameters
                Map<String, Object> linkParameters = new HashMap<String, Object>();
                linkParameters.put(ITEM_ID, FieldKey.ID);
                linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
                printTemplateVo.addLinkField(field, EDIT, linkParameters);
                count++;
            } else {
                
                // otherwise add the test field directly
                printTemplateVo.addTextField(field);
            }
        }
    }
    
    /**
     * Populates a Personal All Fields Search PrintTemplateVo's Fields collection with a fresh set of fields. 
     * This clears any existing fields in the collection.
     * 
     * @param printTemplateVo printTemplateVo to populate, will clear any previous values.
     * @param fieldKeys field key collection used to populate the print template
     * @param templateType product, ndc, or oi
     */
    public static void populatePersonalPrintFieldCollection(PrintTemplateVo printTemplateVo, List<FieldKey> fieldKeys, 
        String templateType) {
        if (printTemplateVo.getFields().size() > 0) {
            throw new CommonException(CommonException.ILLEGAL_ARGUMENT, PTVO);
        }

        int count = 0;

        for (FieldKey field : fieldKeys) {
           
            if (FieldKey.SELECT.equals(field)) {
                printTemplateVo.addInputField(field);
                count++;
            } else if (field.equals(FieldKey.OI_NAME) && templateType.equalsIgnoreCase(PRODUCT)) {
                Map<String, Object> linkParameters = new HashMap<String, Object>();
                linkParameters.put(ITEM_ID, FieldKey.PARENT_ID);
                linkParameters.put(ITEM_TYPE, "orderable_item");
                printTemplateVo.addLinkField(field, EDIT, linkParameters);
                count++;
            } else if (field.equals(FieldKey.OI_NAME)) {
                Map<String, Object> linkParameters = new HashMap<String, Object>();
                linkParameters.put(ITEM_ID, FieldKey.ID);
                linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
                printTemplateVo.addLinkField(field, EDIT, linkParameters);
                count++;
            } else if (field.equals(FieldKey.VA_PRODUCT_NAME) && templateType.equalsIgnoreCase("ndc")) {
                Map<String, Object> linkParameters = new HashMap<String, Object>();
                linkParameters.put(ITEM_ID, FieldKey.PARENT_ID);
                linkParameters.put(ITEM_TYPE, PRODUCT);
                printTemplateVo.addLinkField(field, EDIT, linkParameters);
                count++;
            } else if (field.equals(FieldKey.VA_PRODUCT_NAME)) {
                Map<String, Object> linkParameters = new HashMap<String, Object>();
                linkParameters.put(ITEM_ID, FieldKey.ID);
                linkParameters.put(ITEM_TYPE, PRODUCT);
                printTemplateVo.addLinkField(field, EDIT, linkParameters);
                count++;
            } else if (field.equals(FieldKey.NDC_COUNT) || field.equals(FieldKey.PRODUCT_COUNT)) {
                Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
                childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
                childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
                childrenLinkParameters.put("tab", "children_tab");
                printTemplateVo.addLinkField(field, EDIT, childrenLinkParameters, false);
                count++;
            } else if (count == 1) {
                Map<String, Object> linkParameters = new HashMap<String, Object>();
                linkParameters.put(ITEM_ID, FieldKey.ID);
                linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
                printTemplateVo.addLinkField(field, EDIT, linkParameters);
                count++;
            } else {
                printTemplateVo.addTextField(field);
            }
        }
    }

    /**
     * Sets, sorts, and returns possible fields for print template
     * 
     * @return List of FieldKeys
     */
    public static List<FieldKey> getPossibleFields() {

        return DefaultPrintTemplateFactory.getPossibleFields();
    }


    /**
     * Add the field to be displayed as an input box (text field, radio button, check box, drop down, etc.)
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param fieldKey FieldKey
     */
    public void addInputField(FieldKey fieldKey) {
        fields.add(new InputColumn(fieldKey));
    }

    /**
     * Add the field to be displayed as a plain text.
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param fieldKey FieldKey
     */
    public void addTextField(FieldKey fieldKey) {
        addTextField(fieldKey, true);
    }

    /**
     * Add the field to be displayed as plain text with the given sortable value.
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param fieldKey {@link FieldKey}
     * @param sortable boolean true if the column should be sortable
     */
    public void addTextField(FieldKey fieldKey, boolean sortable) {
        fields.add(new TextColumn(fieldKey, sortable));
    }

    /**
     * Add the field to be displayed as a link to a new instance of a Spring Web Flow, but first check if the current form
     * has changed. The text displayed as the link is the value of the given FieldKey.
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param fieldKey FieldKey
     * @param flowId String name of the Spring Web Flow to link to
     * @param parameters Map of String parameter name to FieldKey to get the value from
     */
    public void addRedirectFlowLinkField(FieldKey fieldKey, String flowId, Map<String, Object> parameters) {
        addRedirectFlowLinkField(fieldKey, flowId, parameters, true);
    }

    /**
     * Add the field to be displayed as a link to a new instance of a Spring Web Flow, but first check if the current form
     * has changed. The text displayed as the link is the value of the given FieldKey.
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param fieldKey FieldKey
     * @param flowId String name of the Spring Web Flow to link to
     * @param parameters Map of String parameter name to FieldKey to get the value from
     * @param sortable boolean true if the column should be sortable
     */
    public void addRedirectFlowLinkField(FieldKey fieldKey, String flowId, Map<String, Object> parameters, boolean sortable) {
        fields.add(new RedirectFlowLinkColumn(fieldKey, flowId, parameters, sortable));
    }

    /**
     * Add localized text as a link back to the current instance of a Spring Web Flow, sending the given event.
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param key to localize
     * @param eventId Spring Web Flow event to trigger when link is clicked
     * @param parameters Map of String parameter name to FieldKey to get the value from
     */
    public void addLocalizableResumeFlowLinkField(String key, String eventId, Map<String, Object> parameters) {
        addLocalizableResumeFlowLinkField(key, eventId, parameters, true);
    }

    /**
     * Add localized text as a link back to the current instance of a Spring Web Flow, sending the given event.
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param key to localize
     * @param eventId Spring Web Flow event to trigger when link is clicked
     * @param parameters Map of String parameter name to FieldKey to get the value from
     * @param sortable boolean true if the column should be sortable
     */
    public void addLocalizableResumeFlowLinkField(String key, String eventId, Map<String, Object> parameters,
                                                  boolean sortable) {
        fields.add(new LocalizableResumeFlowLinkColumn(key, eventId, parameters, sortable));
    }

    /**
     * Add the field to be displayed as a link back to the current instance of a Spring Web Flow, sending the given event.
     * The text displayed as the link is the value of the given FieldKey.
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param fieldKey FieldKey
     * @param eventId Spring Web Flow event to trigger when link is clicked
     * @param parameters Map of String parameter name to FieldKey to get the value from
     */
    public void addResumeFlowLinkField(FieldKey fieldKey, String eventId, Map<String, Object> parameters) {
        addResumeFlowLinkField(fieldKey, eventId, parameters, true);
    }

    /**
     * Add the field to be displayed as a link back to the current instance of a Spring Web Flow, sending the given event.
     * The text displayed as the link is the value of the given FieldKey.
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param fieldKey FieldKey
     * @param eventId Spring Web Flow event to trigger when link is clicked
     * @param parameters Map of String parameter name to FieldKey to get the value from
     * @param sortable boolean true if the column should be sortable
     */
    public void addResumeFlowLinkField(FieldKey fieldKey, String eventId, Map<String, Object> parameters, boolean sortable) {
        fields.add(new ResumeFlowLinkColumn(fieldKey, eventId, parameters, sortable));
    }
    
    /**
     * Add the fields to be displayed as a link
     * The text displayed as the link is the value of the given FieldKey.
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param fieldKey FieldKeys
     * @param event Spring MVC event (e.g. save, edit) to trigger when link is clicked
     * @param parameters This isMap of String parameter name to FieldKey to get the value from
     */
    public void addLinkField(FieldKey fieldKey, String event, Map<String, Object> parameters) {
        fields.add(new LinkColumn(fieldKey, event, parameters));
    }

    
    /**
     * Add this field to be displayed as a link
     * The text displayed as the link is the value of the given FieldKey.
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param fieldKey FieldKey
     * @param event Spring MVC event (e.g. save, edit) to trigger when link is clicked
     * @param parameters Map of String parameter name to FieldKey to get the value from
     * @param sortable boolean true if the column should be sortable
     */
    public void addLinkField(FieldKey fieldKey, String event, Map<String, Object> parameters, boolean sortable) {
        fields.add(new LinkColumn(fieldKey, event, parameters, sortable));
    }

    /**
     * Add localized text as a link.
     * 
     * The order in which columns are added matters -- this order added will become the order displayed.
     * 
     * @param key to localize
     * @param eventId Spring MVC event (e.g. save, edit) to trigger when link is clicked
     * @param parameters Map of String parameter name to FieldKey to get the value from
     */
    public void addLocalizableLinkField(String key, String eventId, Map<String, Object> parameters) {
        addLocalizableLinkField(key, eventId, parameters, true);
    }

    /**
     * Add localized text as a link
     * 
     * The order in which columns are added matters -- the order added will become the order displayed.
     * 
     * @param key to localize
     * @param eventId Spring MVC event (e.g. save, edit) to trigger when link is clicked
     * @param parameters Map of String parameter name to FieldKey to get the value from
     * @param sortable boolean true if the column should be sortable
     */
    public void addLocalizableLinkField(String key, String eventId, Map<String, Object> parameters, boolean sortable) {
        fields.add(new LocalizableLinkColumn(key, eventId, parameters, sortable));
    }

    /**
     * Remove the field in the columns (fields) displayed.
     * 
     * @param fieldKey FieldKey to remove
     */
    public void removeField(FieldKey fieldKey) {
        for (Column column : fields) {
            if (column.getFieldKey().equals(fieldKey)) {
                fields.remove(column);
            }
        }
    }

    /**
     * Get an unmodifiable Set of the fields
     * 
     * @return fields property
     */
    public List<Column> getFields() {
        return Collections.unmodifiableList(fields);
    }

    /**
     * getTemplateLocation.
     * @return templateLocation property
     */
    public TemplateLocation getTemplateLocation() {
        return templateLocation;
    }

    /**
     * setTemplateLocation.
     * @param templateLocation templateLocation property
     */
    public void setTemplateLocation(TemplateLocation templateLocation) {
        this.templateLocation = templateLocation;
    }

    /**
     * setId.
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getId.
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setTemplateName.
     * @param templateName templateName property
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * getTemplateName.
     * @return templateName property
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * getIsMultiSelect.
     * 
     * @return boolean isMultiSelect property
     */
    public boolean getIsMultiSelect() {
        return isMultiSelect;
    }

    /**
     * Add a new pop-up link column
     * 
     * @param displayed FieldKey to display
     * @param popupsUp FieldKey
     */
    public void addPopupLink(FieldKey displayed, FieldKey popupsUp) {
        fields.add(new PopupLinkColumn(displayed, popupsUp));
    }

    /**
     * getInputColumn
     *
     * @return InputColumn
     */
    public InputColumn getInputColumn() {

        InputColumn rv = null;

        if (this.isMultiSelect) {
            List<Column> columns = this.getFields();

            for (Column c : columns) {
                if (c.isInputColumn() && FieldKey.SELECT.equals(c.getFieldKey())) {
                    rv = (InputColumn) c;
                }
            }
        }

        return rv;
    }
}
