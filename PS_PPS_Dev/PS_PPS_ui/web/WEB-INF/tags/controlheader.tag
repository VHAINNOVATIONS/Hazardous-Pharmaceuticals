<%@tag import="java.util.ArrayList"%>
<%@tag import="java.util.List"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.validator.ValidationError"%>
<%@ tag
    language="java"
    body-content="tagdependent"%>    
<%@ taglib  
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib  
    prefix="s"
    uri="http://www.springframework.org/tags"%> 
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ attribute
    name="id"
    required="true"
    type="java.lang.String"
    rtexprvalue="true"
    description="Unique HTML ID to give the renedered label. Defaults to the key."%>
<%@ attribute
    name="key"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="String form of DataFieldKey the field to be displayed"%>
<%@ attribute
    name="required"
    required="false"
    type="java.lang.Boolean"
    rtexprvalue="true"
    description="True/false if the field for this label is required. Default is Boolean.FALSE"%>
<%@ attribute
    name="abbreviation"
    type="java.lang.Boolean"
    rtexprvalue="true"
    description="True/false if the label should display the localized abbreviation or the localized name. Default is false (to display the localized name)."%>
<%@ attribute
    name="labelposition"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="Optional position of the label"%>
<%@ attribute
    name="value"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="Value String"%>  
<%@ attribute
    name="name"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="String form of DataFieldKey the field to be displayed"%>
<%@ attribute
    name="label"
    required="true"
    type="java.lang.String"
    rtexprvalue="true"
    description="The label text to be displayed"%>
<%@ attribute
    name="forattrib"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="The value for the label for attribute"%>
<%@ attribute
    name="title"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="The value for the label title attribute"%>
<%@ attribute
    name="tooltip"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="The value for the label tooltip attribute"%>
<%@ attribute
    name="tooltipDelay"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="The value for the tooltip delay"%>
<%@ attribute
    name="tooltipToggle"
    required="false"
    type="java.lang.Boolean"
    rtexprvalue="true"
    description="The value for the label delay attribute"%>
<%@ attribute
    name="toggleDuration"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="How long the tool tip is toggled for"%>
<%@ attribute
    name="jsTooltipEnabled"
    required="false"
    type="java.lang.Boolean"
    rtexprvalue="true"
    description="The value for the label delay attribute"%>
<%@ attribute
    name="accesskey"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="The value for the label accesskey attribute"%>       
<%@ attribute
    name="labelcssClass"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="CSS class HTML attribute added to default PEPS classes"%>
<%@ attribute
    name="labelcssStyle"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="CSS style HTML attribute added to default PEPS classes"%>
<%@ attribute
    name="fieldErrors"
    required="false"
    type="gov.va.med.pharmacy.peps.common.vo.validator.Errors"
    rtexprvalue="true"
    description="Any validiation errors which might have been thrown"%>     
<%@ attribute
    name="fieldAuthorization"
    required="false"
    type="gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo"
    rtexprvalue="true"
    description="The field authorization VO"%>
<%@ attribute
    name="dataField"
    required="false"
    type="gov.va.med.pharmacy.peps.common.vo.datafield.DataField"
    rtexprvalue="true"
    description="The dataField value"%>

<%
List<ValidationError> errorsList = new ArrayList<ValidationError>();

if(fieldErrors != null) {
	if(key != null)
	    errorsList = fieldErrors.getErrors(FieldKey.getKey(key)).getErrors();
	else
        errorsList = fieldErrors.getErrors(FieldKey.getKey(id)).getErrors();
}

boolean hasFieldErrors = !errorsList.isEmpty();

boolean displayNationalEditLink = request.getAttribute("displayNationalEditLink") != null ? (Boolean) request.getAttribute("displayNationalEditLink") : false;

String css = hasFieldErrors ? " controlGroup errorMessage2" : " controlGroup" ;

labelposition = labelposition == null ? "left" : labelposition; 

String idAttrib = id == null ? key : id;

forattrib = forattrib == null ? idAttrib : forattrib;

jspContext.setAttribute("id", idAttrib);
jspContext.setAttribute("labelposition", labelposition);
jspContext.setAttribute("css", css);
jspContext.setAttribute("forattrib", forattrib);
jspContext.setAttribute("displayNationalEditLink", displayNationalEditLink);
jspContext.setAttribute("hasFieldErrors", hasFieldErrors);
jspContext.setAttribute("errorsList", errorsList);



boolean showLockIcon = (fieldAuthorization != null 
        && fieldAuthorization.isEnabled(FieldKey.getKey(idAttrib)))
        && (displayNationalEditLink)
        && (!idAttrib.equals("searchTemplate_templateName")) 
        && (!idAttrib.equals("searchTemplate_notes"))
        && (!idAttrib.equals("searchTemplate_templateType"));


%>
<c:choose>
<c:when test="${labelposition == 'none'}">
    <span  id="${id}.controlGroup" class="${css}">
        <c:if test="${hasFieldErrors}">
            <ul class="errorMessage">
            <c:forEach var="error" items="${errorsList}">
                <li>${error.localizedError}</li>
            </c:forEach>
            </ul>
        </c:if>        
        <span class="control">
</c:when>
<c:otherwise>
    <div  id="${id}.controlGroup" class="${css}">
        <c:if test="${hasFieldErrors}">
            <ul class="errorMessage">
            <c:forEach var="error" items="${errorsList}">
                <li>${error.localizedError}</li>
            </c:forEach>
            </ul>
        </c:if>
        <c:if test="${labelposition != 'none'}">            
            <c:choose>
            <c:when test="<%=showLockIcon%>" >
				<span class="editable">
				<input type="hidden"
				    id="${id}.editable"
				    name="${name}.editable"
				    value="<%=dataField == null ? true : dataField.isEditable()%>"/>
				<a href="#" onclick="swapImage('${id}', '<c:url value="/" />'); return false;">				
				<c:choose>
				    <c:when test="<%=fieldAuthorization != null && fieldAuthorization.isNonEditable(FieldKey.getKey(idAttrib))%>">        
				       <img id="${id}.editable.image" src="<c:url value="/images/nonEditable.gif" />" alt="locked" title="Click image to unlock the field" />
				    </c:when>
				    <c:otherwise>
					   <img id="${id}.editable.image" src="<c:url value="/images/editable.gif" />" alt="unlocked" title="Click image to lock the field" />
					</c:otherwise>
				</c:choose>
				</a></span>
				</c:when>
				<c:otherwise>
				    <c:if test="${labelcssClass != 'readonlynational' && labelcssClass != 'editheader' && labelcssClass != 'editheader2' && labelcssClass != 'producteditheader' && labelcssClass != 'ndceditheader'}">				    
				    <span class="editable">
				        <img width="16" height="16" alt="" src="<c:url value="/images/blank.gif" />"/>
			        </span>
			        </c:if>
				</c:otherwise>
				</c:choose>
<%-- 				<c:if test="<%=dataField != null && (idAttrib != null) && !(displayNationalEditLink)%>"> --%>
<%-- 				    <span class="editable"><img alt="" src="/images/blank.gif" height="16px;" width="16px;" /></span> --%>
<%-- 				</c:if> --%>
				<c:if test="<%=dataField != null && idAttrib != null && (fieldAuthorization != null && fieldAuthorization.isDisabled(idAttrib)) && displayNationalEditLink%>" >
				    <span class="editable"><img alt="" src="<c:url value="/images/blank.gif" />" height="16px;" width="16px;" /></span>
			    </c:if>
        </c:if>
        <c:if test="${(label != null && !empty label) && (labelposition == 'left' || labelposition == 'top')}">
		    <c:choose>
			    <c:when test="${labelposition == 'top'}">
			      <div class="controlLabel">
			          <peps:controllabel 
			          id="${id}"
			          label="${label}"
			          abbreviation="${abbreviation}"
			          accesskey="${accesskey}"
			          cssClass="${labelcssClass}"
			          cssStyle="${labelcssStyle}"
			          hasFieldErrors="<%=hasFieldErrors%>"
			          forattrib="${forattrib}"
			          jsTooltipEnabled="${jsTooltipEnabled}"
			          labelposition="${labelposition}"
			          key="${key}"
			          name="${name}"
			          required="${required}"
			          title="${title}"
			          toggleDuration="${toggleDuration}"
			          tooltip="${tooltip}"
			          tooltipDelay="${tooltipDelay}"
			          tooltipToggle="${tooltipToggle}"			          
			          />
			      </div>
			    </c:when>
			    <c:otherwise>
				    <span class="controlLabel">
				        <peps:controllabel 
	                      id="${id}"
	                      label="${label}"
	                      abbreviation="${abbreviation}"
	                      accesskey="${accesskey}"
	                      cssClass="${labelcssClass}"
	                      cssStyle="${labelcssStyle}"
	                      hasFieldErrors="${hasFieldErrors}"
	                      forattrib="${forattrib}"
	                      jsTooltipEnabled="${jsTooltipEnabled}"
	                      labelposition="${labelposition}"
	                      key="${key}"
	                      name="${name}"
	                      required="${required}"
	                      title="${title}"
	                      toggleDuration="${toggleDuration}"	                      
	                      tooltip="${tooltip}"
	                      tooltipDelay="${tooltipDelay}"
	                      tooltipToggle="${tooltipToggle}"	                      
	                      /> 
				    </span>
			    </c:otherwise>
		    </c:choose>		    
        </c:if>
        <span class="control">            
</c:otherwise>
</c:choose>




