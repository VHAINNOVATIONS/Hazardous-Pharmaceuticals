<%@tag import="gov.va.med.pharmacy.peps.presentation.common.utility.UrlUtility"%>
<%@ tag
    language="java"
    body-content="empty"
    dynamic-attributes="dynamicParameters"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ attribute
    name="flowId"
    required="true"
    type="java.lang.String"
    description="Spring Web Flow ID to create a URL for"%>
<%@ attribute
    name="text"
    required="false"
    type="java.lang.String"
    description="Text to display as the link. Either text or key/ognl/ognlPrefix are required."%>
<%@ attribute
    name="item"
    required="false"
    type="java.lang.Object"
    description="The object containing the data to display"%>
<%@ attribute
    name="key"
    required="false"
    type="java.lang.String"
    description="String form of DataFieldKey the field to be displayed. Either text or key/ognl/ognlPrefix are required."%>
<%@ attribute
    name="cssClass"
    required="false"
    type="java.lang.String"
    description="CSS Class(es) to use for the link"%>
<%@ attribute
    name="id"
    required="false"
    type="java.lang.String"
    description="HTML ID for the link. Defaults to the flowId suffixed with '_link'. For example, 'search_link'."%>
<%@ attribute
    name="input"
    required="false"
    type="java.util.Map"
    description="Map of String to Object key/value pairs of addition request parameters for the link."%>
<%@ attribute
    name="label"
    required="false"
    type="java.lang.Boolean"
    description="Boolean true or false to display a label with this link. Defaults to false."%>
<%@ attribute
    name="tooltip"
    required="false"
    type="java.lang.String"
    description="Optional text to display as the tooltip (HTML title attribute) for the rendered link. Defaults to the FieldKey's localized description, if the key is given. Otherwise it defaults to the given text."%>
<%@ attribute
    name="onclick"
    required="false"
    type="java.lang.String"
    description="JavaScript method invocation to call when the link is clicked."%>

<%
            if (id == null || id.trim().length() <= 0) {
                jspContext.setAttribute("id", flowId + "Link");
            }


            java.util.Map<String, Object> parameters;

            if (input == null) {
                parameters = new java.util.HashMap<String, Object>();
            }
            else {
                parameters = input;
            }

            java.util.Map dynamicParameters = (java.util.Map) jspContext.getAttribute("dynamicParameters");

            if (dynamicParameters != null) {
                parameters.putAll(dynamicParameters);
            }

            gov.va.med.pharmacy.peps.common.vo.EntityType itemType = null;
            gov.va.med.pharmacy.peps.common.vo.FieldKey fieldKey = null;
            
            if (key != null) {
                fieldKey = gov.va.med.pharmacy.peps.common.vo.FieldKey.getKey(key);
                itemType = gov.va.med.pharmacy.peps.common.vo.EntityType.toEntityType(fieldKey.getFieldClass());
            }

            // If the fieldClass didn't translate into an EntityType, try getting it from the action directly
            if (itemType == null && item !=null) {
                itemType = (gov.va.med.pharmacy.peps.common.vo.EntityType)gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, "entityType");
            }

            jspContext.setAttribute("itemType", itemType);

            if (tooltip == null || tooltip.trim().length() <= 0) {
                if (key != null && key.trim().length() > 0) {
                    tooltip = gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedDescription(key, request.getLocale(),
                        itemType);
                }
                else {
                    tooltip = text;
                }

                jspContext.setAttribute("tooltip", tooltip);
            }

            if (label == null) {
                label = Boolean.FALSE;
            }

            if (label) {
                String labelText = gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedName(key, request.getLocale(),
                    itemType);
                jspContext.setAttribute("labelText", labelText);
%>
<peps:controlheader
    id="${id}"    
    label="${labelText}"
    title="${tooltip}"
    labelcssClass="${cssClass}" />
<%
}
            String linkHref = null;
            
            if(UrlUtility.URL_PATH_HELPER.getOriginatingRequestUri(request).contains(".go"))                
                linkHref = UrlUtility.createFlowDefinitionUrl(flowId, request, parameters);
            else
                linkHref = UrlUtility.createFlowDefinitionUrl(flowId, request, parameters);            
            
            //System.out.println("linkHref: "+ linkHref);
            
            jspContext.setAttribute("linkHref", linkHref);
            String setMenuCookie = "";
            
            if (linkHref.indexOf("manageItem") != -1) {
                setMenuCookie = "setTabIndex('Enter / Edit Items');";
            }
            
            if (onclick != null && onclick.trim().length() > 0) {
                jspContext.setAttribute("onclick", onclick + "; " +  setMenuCookie);
            }
            else {
                jspContext.setAttribute("onclick", setMenuCookie);
            }
%><a
    id="${id}"
    class="${cssClass}"
    href="${linkHref}"
    onclick="${onclick}"
    title="${tooltip}">
<%
if (text == null && key != null) {
	
%>
<peps:text    
    item="<%=item%>"
    key="${key}" />
<%
            }
else if (text == null && item != null && fieldKey != null) {
    String display = gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, fieldKey).toString();
    jspContext.setAttribute("display", display);
%>
<c:out value="${display}"></c:out>
<%
}
            else {
%>${text}<%
}
%></a><%
if (label) {
%>
<peps:controlfooter
    id="${id}"    
    label="${labelText}"
    title="${tooltip}"
    labelcssClass="${cssClass}" />
<%
}
%>