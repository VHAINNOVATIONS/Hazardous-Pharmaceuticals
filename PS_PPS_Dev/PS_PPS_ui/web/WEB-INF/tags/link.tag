<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@tag import="java.util.Set"%>
<%@ tag
    language="java"
    body-content="empty"
    dynamic-attributes="dynamicParameters"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
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
    name="requestParameter"
    required="false"
    type="java.lang.String"
    description="If set, the given value is appended to the URL string as a request parameter using this attribute as the value and the id of the link as the parameter name. Use the input attribute for mulitple parameters"%>
<%@ attribute
    name="input"
    required="false"
    type="java.util.Map"
    description="Map of String to Object key/value pairs of addition request parameters for the link."%>
<%@ attribute
    name="id"
    required="false"
    type="java.lang.String"
    description="HTML ID for the link. Defaults to the flowId suffixed with '_link'. For example, 'search_link'."%>
<%@ attribute
    name="event"
    required="false"
    type="java.lang.String"
    description="The 'event' the link is supposed to trigger (e.g. save, edit)"%>
<%@ attribute
    name="label"
    required="false"
    type="java.lang.Boolean"
    description="Boolean true or false to display a label with this link"%>
<%@ attribute
    name="tabindex"
    required="false"
    type="java.lang.Integer"
    description="Boolean true or false to display a label with this link"%>
<%@ attribute
    name="onclick"
    required="false"
    type="java.lang.String"
    description="JavaScript method invocation to call when the link is clicked."%>
<%@ attribute
    name="tooltip"
    required="false"
    type="java.lang.String"
    description="Optional text to display as the tooltip (HTML title attribute) for the rendered link. Defaults to the FieldKey's localized description, if the key is given. Otherwise it defaults to the given text."%>
<%
            if(StringUtils.isEmpty(onclick)){
                jspContext.setAttribute("onclick", "");
            } else {
            	jspContext.setAttribute("onclick", onclick.replaceAll("'", "\\\\'"));
            }
            java.util.Map<String, Object> parameters;
            
            if(requestParameter !=null && input == null){
                parameters = new java.util.HashMap<String, Object>();
                parameters.put(id, requestParameter);
            }
            else if (input == null) {
                parameters = new java.util.HashMap<String, Object>();
            }
            else {
                parameters = input;
            }
            
            java.util.Map dynamicParameters = (java.util.Map) jspContext.getAttribute("dynamicParameters");

            if (dynamicParameters != null) {
                parameters.putAll(dynamicParameters);
            }

            // added to properly sort linked column items
            java.util.Map<String, Object> sortParams = new java.util.HashMap<String, Object>();
            sortParams = parameters;
            jspContext.setAttribute("sortParams", sortParams.toString());
            
            parameters.remove("_sort");
            String itemTypeString = parameters.get("itemType") != null ? parameters.get("itemType").toString().toLowerCase()+"/" : "";
            parameters.remove("itemType");
            jspContext.setAttribute("itemTypeString", itemTypeString);
            String itemId = (String)parameters.get("itemId") != null ? (String)parameters.get("itemId") +"/" : "";
            parameters.remove("itemId");
            jspContext.setAttribute("itemId", itemId);
            String requestId = (String)parameters.get("requestId") != null ? "request/"+(String)parameters.get("requestId") +"/" : "";
            parameters.remove("requestId");
            jspContext.setAttribute("requestId", requestId);

            Set<String> paramKeys = parameters.keySet();
            StringBuffer paramList = new StringBuffer(paramKeys.size() > 0? "?" :"");
            int i = 0;
            for(String parameter : paramKeys) {
                paramList.append(parameter+"="+parameters.get(parameter));
                i++;
                if(i < paramKeys.size()) 
                    paramList.append("&");
                
            }
            
            jspContext.setAttribute("paramList", paramList.toString());

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

%><a
    id="${id}"
    class="${cssClass}"
    sort="${sortParams}"
    href="/PRE/${itemTypeString}${itemId}${requestId}${event}.go${paramList}"
    onclick="return navigateTo(this, '/PRE/${itemTypeString}${itemId}${requestId}${event}.go${paramList}', '${onclick}')"
    title="${tooltip}"
    tabindex="${tabindex}">
<%

            if (text == null && key != null) {
%><peps:text
    item="${item}"    
    key="${key}" />
<%
            }
            else if (text == null && item != null) {
                String display = gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, fieldKey).toString();
                jspContext.setAttribute("display", display);
%><c:out value="${display}"></c:out>
<%
            }
            else {
%>${text}<%
}
%></a>
<%
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