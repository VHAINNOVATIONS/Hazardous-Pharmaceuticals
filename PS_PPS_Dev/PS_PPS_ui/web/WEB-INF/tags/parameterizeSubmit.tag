<%@ tag
    language="java"
    body-content="empty"%>
<%@ taglib  
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ attribute
    name="id"
    required="true"
    type="java.lang.String"
    description="HTML ID for the button."%>
<%@ attribute
    name="event"
    required="true"
    type="java.lang.String"
    description="The 'event' the link is supposed to trigger (e.g. save, edit, openTemplate/add)"%>
<%@ attribute
    name="itemType"
    required="true"
    type="java.lang.String"
    description="The entityType of the item (e.g. EntityType.DRUG_CLASS)."%>
<%@ attribute
    name="formElementName"
    required="true"
    type="java.lang.String"    
    description="The name of the form element this button is associated with"%>     
<%@ attribute
    name="key"
    required="true"
    type="java.lang.String"
    description="Localizable key to use as the label. If the key isn't found, the key itself is used as the label."%>
<%@ attribute
    name="disabled"
    required="false"
    type="java.lang.Boolean"
    description="True/false if the button should be disabled. Defaults to false."%>
<%@ attribute
    name="cssClass"
    required="false"
    type="java.lang.String"
    description="CSS Class(es) to use for the button"%>
<%@ attribute
    name="cssStyle"
    required="false"
    type="java.lang.String"
    description="CSS Style(s) to use for the button"%>
<%
            if (disabled == null) {
                disabled = Boolean.FALSE;
                jspContext.setAttribute("disabled", disabled);
            }
            
            
            jspContext.setAttribute("label", gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getText(
                request, key));
%>
<input
    id="${id}"
    type="image"
    src="<c:url value="/images/addsign.jpg" />"
    title="Add a new item to the list"
    name="${id}"
    onclick="submitButton = this.name; parameterizeSubmit('${event}.go', '${itemType}', '${formElementName}');"
    class="addsign"
    style="${cssStyle}" 
    <%=disabled ? "disabled=\"disabled\"" : "" %> />