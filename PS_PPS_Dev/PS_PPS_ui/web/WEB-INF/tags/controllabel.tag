<%@tag import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@ tag
    language="java"
    body-content="empty"%>    
<%@ taglib  
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib  
    prefix="s"
    uri="http://www.springframework.org/tags"%> 
<%@ attribute
    name="id"
    required="true"
    type="java.lang.String"
    description="Unique HTML ID to give the renedered label. Defaults to the key."%>
<%@ attribute
    name="key"
    required="false"
    type="java.lang.String"
    description="String form of DataFieldKey the field to be displayed"%>
<%@ attribute
    name="required"
    required="false"
    type="java.lang.Boolean"
    description="True/false if the field for this label is required. Default is Boolean.FALSE"%>
<%@ attribute
    name="abbreviation"
    type="java.lang.Boolean"
    description="True/false if the label should display the localized abbreviation or the localized name. Default is false (to display the localized name)."%>
<%@ attribute
    name="labelposition"
    required="false"
    type="java.lang.String"
    description="Optional position of the label"%>  
<%@ attribute
    name="name"
    required="false"
    type="java.lang.String"
    description="String form of DataFieldKey the field to be displayed"%>
<%@ attribute
    name="label"
    required="true"
    type="java.lang.String"
    description="The label text to be displayed"%>
<%@ attribute
    name="forattrib"
    required="false"
    type="java.lang.String"
    description="The value for the label for attribute"%>
<%@ attribute
    name="title"
    required="false"
    type="java.lang.String"
    description="The value for the label title attribute"%>
<%@ attribute
    name="tooltip"
    required="false"
    type="java.lang.String"
    description="The value for the label tooltip attribute"%>
<%@ attribute
    name="tooltipDelay"
    required="false"
    type="java.lang.String"
    description="The value for the tooltip delay"%>
<%@ attribute
    name="tooltipToggle"
    required="false"
    type="java.lang.Boolean"
    description="The value for the label delay attribute"%>
<%@ attribute
    name="toggleDuration"
    required="false"
    type="java.lang.String"
    description="How long the tool tip is toggled for"%>
<%@ attribute
    name="jsTooltipEnabled"
    required="false"
    type="java.lang.Boolean"
    description="The value for the label delay attribute"%>
<%@ attribute
    name="accesskey"
    required="false"
    type="java.lang.String"
    description="The value for the label accesskey attribute"%>       
<%@ attribute
    name="cssClass"
    required="false"
    type="java.lang.String"
    description="CSS class HTML attribute added to default PEPS classes"%>
<%@ attribute
    name="cssStyle"
    required="false"
    type="java.lang.String"
    description="CSS style HTML attribute added to default PEPS classes"%>
<%@ attribute
    name="hasFieldErrors"
    required="false"
    type="java.lang.Boolean"
    description="Does this field have errors?"%> 

<%
jsTooltipEnabled = jsTooltipEnabled != null ? jsTooltipEnabled : false;
required = required != null ? required : false;
tooltipToggle = tooltipToggle != null ? tooltipToggle : false;



hasFieldErrors = hasFieldErrors != null ? hasFieldErrors : false;

if(label != null || !label.isEmpty()){
	if(labelposition != null && labelposition.equals("left")){
	    if(cssClass == null)
	        cssClass ="";
	    
		if (hasFieldErrors)
		    cssClass = "errorLabel left "+cssClass;
		else 
		    cssClass = "label left "+cssClass;
	}
	else {
	    if(hasFieldErrors)
	        cssClass="errorLabel"; 
	    else
	        cssClass="label";
	}
}
 
if(forattrib == null && id != null)
    forattrib = id;

boolean showColon = false;

if((label != null && !label.isEmpty()) 
        && (labelposition != null 
        && (labelposition.equals("left") || labelposition.equals("top"))))
    showColon = true;

jspContext.setAttribute("showColon", showColon);
jspContext.setAttribute("cssClass", cssClass);
jspContext.setAttribute("forattrib", forattrib);
jspContext.setAttribute("jsTooltipEnabled", jsTooltipEnabled);
jspContext.setAttribute("required", required);
jspContext.setAttribute("tooltipToggle", tooltipToggle);
jspContext.setAttribute("hasFieldErrors", hasFieldErrors);

%>

<label id="label_${id}" title="${title}" for="${forattrib}" accesskey="${accesskey}" class="${cssClass}" style="${cssStyle}">${label}
<%-- <c:choose> --%>
<%--     <c:when test="${tooltip != null || !empty tooltip}"> --%>
<%--         <c:choose> --%>
<%-- 	        <c:when test="${tooltipIcon !=null || !empty tooltipIcon}"> --%>
<%-- 	           <img id="_tt<%=tooltip.hashCode()%>"  src='<s:url value="${tooltipIcon}"  htmlEscape="true"/>'  --%>
<%-- 	               alt="${tooltip}" title="${tooltip}" >  --%>
<%-- 	        </c:when> --%>
<%-- 	        <c:otherwise> --%>
<%-- 	           <img id="_tt<%=tooltip.hashCode()%>"  src='<s:url value="/web/images/tooltip.gif"  htmlEscape="true"/>'  --%>
<%--                    alt="${tooltip}" title="${tooltip}" >  --%>
<%-- 	        </c:otherwise>         --%>
<%--         </c:choose> --%>
        <c:if test="${jsTooltipEnabled}">
               <span dojoType="tooltip" connectId="_tt<%=tooltip.hashCode()%>" toggle="${tooltipToggle}"
                    toggleDuration="${toggleDuration}" delay="${tooltipDelay}" caption="${tooltip}"></span>
        </c:if>        
<%--     </c:when>     --%>
<%-- </c:choose> --%>
<c:if test="${required}">
    <span class="required">*</span>
</c:if>
<c:if test="${showColon}">
    :&nbsp;&nbsp;&nbsp;&nbsp;
</c:if>
</label>



