<%@ tag
    language="java"
    body-content="empty"%>    
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ attribute
    name="id"
    required="false"
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
    name="entityType"
    required="false"
    type="gov.va.med.pharmacy.peps.common.vo.EntityType"
    description="Optional EntityType for which to localize"%>
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
    name="cssClass"
    required="false"
    type="java.lang.String"
    description="CSS class HTML attribute added to default PEPS classes"%>  
<%
            if (id == null || id.trim().length() <= 0) {
                jspContext.setAttribute("id", key);
            }

            if (required == null) {
                jspContext.setAttribute("required", Boolean.FALSE);
            }

            if (abbreviation != null && abbreviation) {
                jspContext.setAttribute("label", gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedAbbreviation(key,
                    request.getLocale(), entityType));
            }
            else {
                jspContext.setAttribute("label", gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedName(key, request
                    .getLocale(), entityType));
            }

            jspContext.setAttribute("tooltip", gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedDescription(key,
                request.getLocale(), entityType));
%>
<label id="${id}" title="${tooltip}" class="${cssClass}" ><c:out value="${label}"/></label>
	<%
		if (required != null && required == true)
		{
	%>
			<span class="required">*</span>	
	<%	
		}
	%>