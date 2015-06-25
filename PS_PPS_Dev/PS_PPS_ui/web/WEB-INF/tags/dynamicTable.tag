<%@tag import="gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo"%>
<%@tag import="java.util.Date"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.datafield.GroupListDataField"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@tag import="java.util.ArrayList"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.ManagedDataVo"%>
<%@tag import="java.util.List"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.datafield.DataFields"%>
<%@tag import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.Selectable"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.datafield.MultitextDataField"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.datafield.DataField"%>
<%@ tag
    language="java"
    body-content="empty"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@ attribute
    name="key"
    required="true"
    type="java.lang.String"
    description="String form of FieldKey the field to be displayed"%>
<%@ attribute
    name="item"
    required="true"
    type="java.lang.Object"
    rtexprvalue="true"
    description="The item containing the datafield"%>
<%@ attribute
    name="label"
    required="true"
    type="java.lang.String"
    description="HTML label to provide for the field."%>
<%@ attribute
    name="title"
    required="true"
    type="java.lang.String"
    description="HTML title to provide for the field."%>
<%@ attribute
    name="disabled"
    required="true"
    type="java.lang.Boolean"
    description="True if the field should be disabled."%>
<%@ attribute
    name="labelPosition"
    required="true"
    type="java.lang.String"
    description="Position of the label."%>
<%@ attribute
    name="required"
    required="true"
    type="java.lang.Boolean"
    description="True if the field is required to be entered."%>
<%@ attribute
    name="cssStyle"
    required="false"
    type="java.lang.String"
    description="CSS style HTML attribute passed on to Struts tags."%>
<%@ attribute
    name="entityType"
    required="false"
    type="gov.va.med.pharmacy.peps.common.vo.EntityType"
    description="Optional EntityType for which to localize"%>
<%@ attribute
    name="managedDataAddLink"
    required="true"
    type="java.lang.Boolean"
    description="Optional boolean value to force ManagedDataVo select boxes to not display the 'Add' link. Defaults to true."%>
<%@ attribute
    name="addOnclick"
    required="false"
    type="java.lang.String"
    description="JavaScript to call when the add button is clicked, after the new row is added."%>
<%@ attribute
    name="removeOnclick"
    required="false"
    type="java.lang.String"
    description="JavaScript to call when a remove button is clicked, after the row is removed."%>
<%@ attribute
    name="readonly"
    required="false"
    type="java.lang.Boolean"
    description="Makes the HTML input field readonly - the user can still use scrollbars in textareas if this attribute has been set."%>
<%@ attribute
    name="onchange"
    required="false"
    type="java.lang.String"
    description="JavaScript to call the value is changed."%>
<%@ attribute
    name="onclick"
    required="false"
    type="java.lang.String"
    description="JavaScript to call the value is clicked."%>
<%

gov.va.med.pharmacy.peps.common.vo.FieldKey fieldKey = gov.va.med.pharmacy.peps.common.vo.FieldKey.getKey(key);
jspContext.setAttribute("camelCaseKey", fieldKey.fromDotsToCamelCase());
Object dF = gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, fieldKey);

if (label == null) {
    jspContext.setAttribute("label", gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedName(key,
        request.getLocale(), entityType));
}
%>
<peps:controlheader
    key="${key}"
    id="${key}"
    label=""
    name="${camelCaseKey}"
    labelposition="${labelPosition}"    
    dataField="<%=dF instanceof DataField ? (DataField)dF : null%>"
    fieldAuthorization="${fieldAuthorization}"/>
<%
if (!"none".equals(labelPosition)) {
%>
<fieldset>
<legend>
<peps:label
    key="${key}"
    required="${required}"
    entityType="${entityType}" />
</legend>
<%
}

int row=0;

if (disabled) {
%>
<table
    frame="box"
    rules="cols"
    class="pepsTable"
    id="${key}_table"
    disabled="disabled">
<%
            }
            else {
%>
<table
    frame="box"
    rules="cols"
    class="pepsTable"
    id="${key}_table">
<%
}
%>
<thead>
<tr>
<%

            gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo fieldAuthorization = (gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo) jspContext.getAttribute("fieldAuthorization");
            
            if(fieldAuthorization == null)
                fieldAuthorization = (gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo) request.getAttribute("fieldAuthorization");

            java.util.Collection collection;
            String attributeName = fieldKey.fromDotsToCamelCase();

            if (dF instanceof gov.va.med.pharmacy.peps.common.vo.datafield.GroupListDataField) {
                collection = ((gov.va.med.pharmacy.peps.common.vo.datafield.GroupListDataField) dF).getValue();
                
            }
            else if (dF instanceof gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField) {
                collection = ((gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField) dF).getValue();
            }            
            else {
                collection = (java.util.Collection) (dF instanceof java.util.Collection || dF instanceof java.util.List ? dF : new ArrayList());
            }

            String escapedAttributeName = org.apache.commons.lang.StringEscapeUtils.escapeJavaScript(attributeName);

            java.util.List<gov.va.med.pharmacy.peps.common.vo.FieldKey> columnFieldKeys;

            if (fieldKey.isGroupListDataField()) {
                columnFieldKeys = fieldKey.getGroupedFields();
            }
            else {
                columnFieldKeys = new java.util.ArrayList<gov.va.med.pharmacy.peps.common.vo.FieldKey>();
                columnFieldKeys.add(fieldKey);
            }

            for (int i = 0; i < columnFieldKeys.size(); i++) {
                String newkey = columnFieldKeys.get(i).getKey(); 
                String thwidth = gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedWidth(newkey, request.getLocale());
%>
<th style="<%=thwidth%>">
<peps:label
    key="<%=newkey%>"
    abbreviation="true" />
</th>
<%
}
%>
<th style="width: 80px;">
<label
    id="remove_header"
    title="<spring:message code='button.remove' />">
<spring:message code="button.remove" />
</label>
</th>
</tr>
</thead>
<tbody>
<%
            int numRows = 0;
            boolean isHidden = false;

            if (collection != null) {
                numRows = collection.size();
            }
            
            
            if (numRows == 0 && required == true) { //added this to show a blank row and the addmanageddataaddlink
                numRows = 1;
            }

            // the last row will be hidden and disabled for the dynamicTable.js to copy from for adds
            for (row = 0; row <= numRows; row++) {
                if (row == numRows) {
                    isHidden = true;
                    jspContext.setAttribute("disabled", Boolean.TRUE);
                    jspContext.setAttribute("selectedValues", "");                             
                   	jspContext.setAttribute("rowItem", "");  
                    jspContext.setAttribute("nameValue", "");   
                    if (row % 2 == 0) {
%>
<tr
    class="odd"
    style="display: none; visibility: hidden;">
<%
                    }
                    else {
%>
<tr
    class="even"
    style="display: none; visibility: hidden;">
<%
                    }
                } //end if
                else {
                    isHidden = false;
                    if (row % 2 == 0) {
%>
<tr class="odd">
<%
                    }
                    else {
%>
<tr class="even">
<%
                    }                    
                } //end else                
                jspContext.setAttribute("isHidden", isHidden);
                
                for (int col = 0; col < columnFieldKeys.size(); col++) {
                    String ognlSuffix = null;
                    String columnKey = columnFieldKeys.get(col).getKey();
                    DataField rowItem = null;

                    jspContext.setAttribute("selectedValues", null);
                    
                    if(row < collection.size()){
	                    if (dF instanceof gov.va.med.pharmacy.peps.common.vo.datafield.GroupListDataField) {
	                        columnKey = columnFieldKeys.get(col).getKey();
	                        rowItem = ((DataField)((GroupListDataField)dF).getGroup(row).getGroupedField(fieldKey));	                        
	                        jspContext.setAttribute("rowItem", rowItem);	                        
	                    }
	                    else if (fieldKey.isMultitextDataField()) {  
	                        columnKey = fieldKey.getKey() + ".multitext";                        
	                        jspContext.setAttribute("rowItem", ((MultitextDataField)dF).getValue().get(row));
	                    }
	                    else if (dF instanceof gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField) {
	                        columnKey = fieldKey.getKey();    
	                        Object rI = ((ListDataField)dF).getValue().get(row);	                        
	                        if(rI instanceof Selectable) {
	                            jspContext.setAttribute("rowItem", ((Selectable)rI).getId());
	                        }
	                        else if (rI instanceof List) {                             
                                ArrayList<String> selectedValues = new ArrayList<String>();
                                for(Object i : (ArrayList<Object>)rI) {                             
                                    if(rI instanceof Selectable) {
                                        selectedValues.add(((Selectable)i).getId());
                                    }
                                    else {
                                        selectedValues.add(i.toString());
                                    }
                                }
                                jspContext.setAttribute("selectedValues", selectedValues);
                            }
	                        else{
	                            jspContext.setAttribute("rowItem", rI);    
	                        }	                        
	                    }
	                    	                    
	                    else if (dF instanceof List){    
	                    	jspContext.setAttribute("parentFieldKey", fieldKey);	                        
	                    	Object rI = JspTagUtility.getFieldValue(((List)dF).get(row), columnFieldKeys.get(col));	                    	
                            
                            if (fieldKey.isMultiSelectField() && rI == null) {
                                rI = ((List)dF).get(row);
                            }
                            if (rI instanceof Selectable) {
                                jspContext.setAttribute("rowItem", ((Selectable)rI).getId());
                                jspContext.setAttribute("nameValue", ((Selectable)rI).getValue());                            	
                            } else if (rI instanceof List) {                            	
                            	ArrayList<String> selectedValues = new ArrayList<String>();
                            	for(Selectable i : (ArrayList<Selectable>)rI) {                            	
                            		selectedValues.add(i.getId());                            		
                            	}
                            	jspContext.setAttribute("selectedValues", selectedValues);
                            }
                            else
                                if(rI != null && rI instanceof Date) {
                                    jspContext.setAttribute("rowItem", rI);
                                }
                                else {
                                    jspContext.setAttribute("rowItem", rI != null ? rI.toString() : "");    
                                }                           
	                    }
	//                     else if(dF instanceof gov.va.med.pharmacy.peps.common.vo.datafield.DataFields){
	//                         Object rI = null;
	//                         Object tempValue = ((gov.va.med.pharmacy.peps.common.vo.datafield.DataFields) dF).getDataField(columnFieldKeys.get(col));
	//                         if(tempValue != null){	                            
	// 	                            if(((DataField)tempValue).getValue() != null){
	// 	                                rI = ((DataField)tempValue).toShortString();
	// 	                            }else{
	// 	                                rI = "";
	// 	                            }
	//                             }
	//                         jspContext.setAttribute("rowItem", rI);
	//                         columnKey = columnFieldKeys.get(col).getKey();
	//                     }
	
	                    else {                    	
	                        jspContext.setAttribute("rowItem", JspTagUtility.getFieldValue(dF, columnFieldKeys.get(col)));
	                        columnKey = columnFieldKeys.get(col).getKey();
	                    }
                    }
                    jspContext.setAttribute("id", columnKey + "_" + row);
                    
                    
                    
                    FieldKey columnFieldKey = columnFieldKeys.get(col);
                    if(columnFieldKey.isMultiSelectListDataField() 
                            || columnFieldKey.isMultiSelectPrimaryKeyDataField() 
                            || columnFieldKey.isMultitextDataField()) {
                        jspContext.setAttribute("name", fieldKey.fromDotsToCamelCase()+ "_" + row);
                    } else {
                        jspContext.setAttribute("name", fieldKey.fromDotsToCamelCase()+"."+columnFieldKey.fromDotsToCamelCase() + "_" + row);
                    }
                    
                    // added to prevent cssStyle from being assigned to dropdown lists with the addsign
                    if(columnFieldKey.isSingleSelectField() || columnFieldKey.isSingleSelectPrimaryKeyDataField()) {
                        jspContext.setAttribute("cssStyle","");
                    } else {
                    	jspContext.setAttribute("cssStyle",gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedWidth(columnKey,request.getLocale()));
                    }
                    
                    boolean disabledColumn = (Boolean) jspContext.getAttribute("disabled") || (fieldAuthorization != null && fieldAuthorization.isDisabledModifyMultipleDataField(fieldKey)) && isHidden;
                    
%>
<td>
<%
                    if (dF instanceof gov.va.med.pharmacy.peps.common.vo.datafield.GroupListDataField) {           
%>
<peps:input
    id="${id}"
    name="${name}"
    item="${rowItem}"
    key="<%=columnKey%>"
    labelPosition="none"
    disabled="<%=disabledColumn%>"
    readonly="${readonly}"
    maxlength="${maxlength}"
    required="${required}"
    size="${size}"
    value="<%=rowItem.getDefaultValue().toString()%>"
    cssClass="minwidth"
    cssStyle="${cssStyle}"
    managedDataAddLink="${managedDataAddLink}"
    isHidden="<%=isHidden%>"
    jsOnChange="${onchange}"
    jsOnClick="${onclick}"  />
<%
                    }
                    else if (dF instanceof gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField && !fieldKey.isGroupListDataField()) {
                    	
                        
%>
<peps:input
    id="${id}"
    name="${name}"    
    key="<%=columnKey%>"
    item="${item}"
    labelPosition="none"
    disabled="<%=disabledColumn%>"
    readonly="${readonly}"
    maxlength="${maxlength}"
    required="${required}"
    size="${size}"
    cssClass="minwidth"
    cssStyle="${cssStyle}"
    value="${rowItem}"
    managedDataAddLink="${managedDataAddLink}"
    isHidden="<%=isHidden%>"
    jsOnChange="${onchange}"
    jsOnClick="${onclick}"   />
<%
                    }
                    else {
%>

<peps:input
    id="${id}"
    name="${name}"
    key="<%=columnKey%>"
    item="${item}"
    labelPosition="none"
    disabled="<%=disabledColumn%>"
    readonly="${readonly}"
    maxlength="${maxlength}"
    required="${required}"
    size="${size}"
    cssClass="minwidth"
    cssStyle="${cssStyle}"
    value="${rowItem}"
    managedDataAddLink="${managedDataAddLink}"
    selectedValues="${selectedValues}"
    isHidden="${isHidden}"
    nameValue="${nameValue}"
    parentFieldKey="${parentFieldKey}"
    jsOnChange="${onchange}"
    jsOnClick="${onclick}" />
<%
}
%>
</td>
<%
}
%>
<td>
<%
if (disabled || (fieldAuthorization != null && fieldAuthorization.isDisabledRemoveMultipleDataField(fieldKey))) {
%>
<button
    type="button"
    id="${key}_remove_<%=row%>"
    disabled="disabled"
    class="button"
    onclick=""
    title="<spring:message code="button.remove.row" />"><spring:message code="button.remove.row" /></button>
<%
                }
                else {
%>
<button
    type="button"
    id="${key}_remove_<%=row%>"
    class="button"
    onclick="removeRow(this,'<%=escapedAttributeName%>', '${key}_removedRows', '<%=row%>'); ${removeOnclick}"
    title="<spring:message code='button.remove.row' />"><spring:message code="button.remove" /></button>

<%
}
%>

</td>
</tr>
<%
}
            
%>
</tbody>
</table>
<%
if (!(disabled || (fieldAuthorization != null && fieldAuthorization.isDisabledAddMultipleDataField(fieldKey)))) {
%>
<button
    type="button"
    id="${key}_add"
    class="wideButton"
    onclick="addRow(this,'<%=escapedAttributeName%>'); ${addOnclick}"
    title="<spring:message code='button.add.new.row' />"><spring:message code="button.add.new.row" /></button>
<%
}
%>

<%
if (!"none".equals(labelPosition)) {
%>
</fieldset>
<%
}
%>
<span class="hidden">
<input
    type="hidden"
    id="${key}_removedRows"
    name="${camelCaseKey}_removedRows" />
</span>
<!--  key = ${key} -->
<peps:controlfooter
    key="${key}"
    id="${key}"
    label=""
    name="${key}"
    labelposition="${labelPosition}"    
    dataField="<%=dF instanceof DataField ? (DataField)dF : null%>"
    fieldAuthorization="${fieldAuthorization}"/>