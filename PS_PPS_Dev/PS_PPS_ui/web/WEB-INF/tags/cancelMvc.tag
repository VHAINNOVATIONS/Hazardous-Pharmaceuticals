<%@ tag
    language="java"
    body-content="empty"%>
<%@ attribute
    name="id"
    required="false"
    type="java.lang.String"
    description="Optional HTML ID to give the button. If no ID is given, the ID will be the value of the key attribute plus '.button'"%>
<%@ attribute
    name="key"
    required="true"
    type="java.lang.String"
    description="Resource bundle key for displaying text in button."%>
<%@ attribute
    name="name"
    required="true"
    type="java.lang.String"
    description="HTML name of the button."%>
<%@ attribute
    name="onclick"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="JavaScript onclick event."%>
<%@ attribute
    name="disabled"
    required="false"
    type="java.lang.Boolean"
    description="Optional boolean value to disable the button. Defaults to false."%>
<%@ attribute
    name="cssStyle"
    required="false"
    type="java.lang.String"
    description="CSS style HTML attribute passed on to Struts tags."%>
<%@ attribute
    name="cssClass"
    required="false"
    type="java.lang.String"
    description="CSS class HTML attribute added to default PEPS classes."%>
<%
            if (id == null || id.trim().length() <= 0) {
                jspContext.setAttribute("id", key + ".button");
            }

            if (disabled == null || disabled) {
                jspContext.setAttribute("disabledValue", "");
            }
            else {
                jspContext.setAttribute("disabledValue", "disabled");
            }
            
            String buttonText = gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getText(request, key);                 

            String buttonCssClass;
            
            if (buttonText.length() > 21) {
                buttonCssClass = "wideButton3";
            }
            else if (buttonText.length() > 16) {
                buttonCssClass = "wideButton2";
            }
            else if (buttonText.length() > 11) {
                buttonCssClass = "wideButton";
            }
            else {
                buttonCssClass = "button";
            }

            // Set the submitButton JavaScript attribute on click. It is used by the peps:form's onsubmit event while 
            // disabling all submit buttons.
            if (onclick != null) {
                onclick = "submitButton = this.name; " + onclick;
                
                if (!onclick.endsWith(";")) {
                    onclick += ";";
                }
            }
            else {
                onclick = "submitButton = this.name;";
            }

            jspContext.setAttribute("onclick", onclick);   
           
            
%>
<input
    type="reset"
    id="${id}"
    name="<%=name%>"
    value="<%=buttonText%>"
    class="<%=buttonCssClass%> ${cssClass}"
    style="${cssStyle}"
    onclick="${onclick}"
    
<%= disabled != null && disabled ? "disabled='disabled'" : ""%> />