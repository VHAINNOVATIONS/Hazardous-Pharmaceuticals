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
    name="value"
    required="false"
    type="java.lang.String"
    description="Value String"%>  
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
    name="labelcssClass"
    required="false"
    type="java.lang.String"
    description="CSS class HTML attribute added to default PEPS classes"%>
<%@ attribute
    name="labelcssStyle"
    required="false"
    type="java.lang.String"
    description="CSS style HTML attribute added to default PEPS classes"%>
<%@ attribute
    name="fieldErrors"
    required="false"
    type="gov.va.med.pharmacy.peps.common.vo.validator.Errors"
    description="Any validiation errors which might have been thrown"%>     
<%@ attribute
    name="fieldAuthorization"
    required="false"
    type="gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo"
    description="The field authorization VO"%>
<%@ attribute
    name="dataField"
    required="false"
    type="gov.va.med.pharmacy.peps.common.vo.datafield.DataField"
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

String css = hasFieldErrors ? "controlGroup errorMessage2" : "controlGroup";

String idAttrib = id == null ? key : id;
forattrib = forattrib == null ? idAttrib : forattrib;

jspContext.setAttribute("id", idAttrib);
jspContext.setAttribute("labelposition", labelposition);
jspContext.setAttribute("css", css);
jspContext.setAttribute("forattrib", forattrib);
jspContext.setAttribute("displayNationalEditLink", displayNationalEditLink);
jspContext.setAttribute("hasFieldErrors", hasFieldErrors);
jspContext.setAttribute("errorsList", errorsList);



%>
<c:choose>
<c:when test="${labelposition == 'none'}">
        </span>
    </span>
</c:when>
<c:otherwise>
        </span> 
        <c:if test="${label != null && (labelposition == 'right' || labelposition == 'bottom')}">
            <c:choose>
                <c:when test="${labelposition == 'bottom'}">
                    <div class="${css}">
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
                    </div>
                </c:when>
                <c:otherwise>
                    <span class="${css}">
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
    </div>
    </c:otherwise>
</c:choose>