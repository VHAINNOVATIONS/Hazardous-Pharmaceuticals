<%@tag import="gov.va.med.pharmacy.peps.common.vo.datafield.DataField"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.FieldKey.FieldSubType"%>
<%@ tag
    language="java"
    body-content="empty"%>
<%@ taglib
    prefix="spring"
    uri="http://www.springframework.org/tags"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ attribute
    name="multiselect"
    required="false"
    type="java.lang.Boolean"
    description="Optional boolean value to force recursion of multi-select 'dynamic table' fields to stop. Defaults to true."%>
<%@ include file="input.tagf"%>
<%
            if (multiselect == null) {
                multiselect = Boolean.TRUE;
            }

            if ((fieldKey.isMultiSelectPrimaryKeyDataField() || fieldKey.isMultiSelectListDataField()
                || fieldKey.isGroupListDataField() || fieldKey.isMultitextDataField() || fieldKey.isMultiSelectField())
                && multiselect && !FieldSubType.LIST_CHECKBOX_DATA_FIELD.equals(fieldKey.getFieldSubType())) {
%>
<peps:dynamicTable
    key="${key}"
    item="${item}"
    entityType="${itemType}"
    disabled="${disabled}"
    readonly="${readonly}"
    required="${required}"
    title="${tooltip}"
    label="${label}"
    labelPosition="${labelPosition}"
    managedDataAddLink="${managedDataAddLink}"
    addOnclick="<%= jsOnClick == null ? null : jsOnClick %>"
    removeOnclick="<%= jsOnClick == null ? null : jsOnClick %>"
    onchange="${jsOnChange}"
    onclick="${jsOnClick}" />
<%
            }
            else if (fieldKey.isGroupDataField()) {
%>


<fieldset>
<legend>
<peps:label
    key="${key}"
    required="<%= required == null ? null : required %>"
    entityType="${itemType}" />
</legend>
<%
                int groupSize = fieldKey.getGroupedFields().size();

                for (int i = 0; i < groupSize; i++) {
                	FieldKey groupedKey = ((gov.va.med.pharmacy.peps.common.vo.FieldKey) fieldKey
                            .getGroupedFields().get(i));
                    jspContext.setAttribute("groupedKey", groupedKey.getKey());
                    if(fieldKey.isVaDataField()) {
                    	GroupDataField groupDf = (GroupDataField)dataField;
                    	DataField dF = ((DataField)groupDf.getGroupedField(groupedKey));
                    	jspContext.setAttribute("value", dF.getValue());
                    }
%><peps:input
    key="${groupedKey}"
    item="<%= item == null ? null : item %>"
    disabled="<%= disabled == null ? null : disabled %>"
    readonly="${readonly}"    
    maxlength="<%= maxlength == null ? null : maxlength %>"
    size="<%= size == null ? null : size %>"
    labelPosition="${labelPosition}"
    required="<%= required == null ? null : required %>"
    cssStyle="<%= cssStyle == null ? null : cssStyle %>"
    cssClass="<%= cssClass == null ? null : cssClass %>"
    jsOnClick="<%= jsOnClick == null ? null : jsOnClick %>"
    jsOnKeyUp="<%= jsOnKeyUp == null ? null : jsOnKeyUp %>"
    jsOnChange="<%= jsOnChange == null ? null : jsOnChange %>"
    value="${value}"
    tabindex="${tabindex}" />
<%
}
%></fieldset>
<%
            }
            else {
%><peps:input
    id="<%= id == null ? null : id %>"
    key="${key}"
    item="<%= item == null ? null : item %>"
    disabled="<%= disabled == null ? null : disabled %>"
    readonly="${readonly}"
    maxlength="<%= maxlength == null ? null : maxlength %>"
    size="<%= size == null ? null : size %>"
    tabindex="${tabindex}"
    labelPosition="${labelPosition}"
    required="<%= required == null ? null : required %>"
    value="<%= value == null ? null : value %>"
    name="<%= name == null ? null : name %>"
    cols="<%= cols == null ? null : cols %>"
    rows="<%= rows == null ? null : rows %>"
    isAdvancedSearchContext="<%= isAdvancedSearchContext == null ? null : isAdvancedSearchContext %>"
    cssStyle="<%= cssStyle == null ? null : cssStyle %>"
    cssClass="<%= cssClass == null ? null : cssClass %>"
    abbreviation="<%= abbreviation == null ? null : abbreviation %>"
    description="<%= description == null ? null : description %>"
    postValue="<%= postValue == null ? null : postValue %>"
    jsOnClick="<%= jsOnClick == null ? null : jsOnClick %>"
    jsOnKeyUp="<%= jsOnKeyUp == null ? null : jsOnKeyUp %>"
    jsOnChange="<%= jsOnChange == null ? null : jsOnChange %>"
    managedDataAddLink="<%= managedDataAddLink == null ? null : managedDataAddLink %>" />
<%
}
%>