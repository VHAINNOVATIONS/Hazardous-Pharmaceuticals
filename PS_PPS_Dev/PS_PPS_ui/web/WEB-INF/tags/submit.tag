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
    name="action"
    required="false"
    type="java.lang.String"
    description="The optional action to set the form to. If not set, will default to the action on the form."%>
<%@ attribute
    name="method"
    required="false"
    type="java.lang.String"
    description="The optional method to set the form to. If not set, will default to the method on the form."%>
<%@ attribute
    name="onclick"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="JavaScript onclick event."%>
<%@ attribute
    name="tabindex"
    required="false"
    type="java.lang.Integer"
    description="Used to set the tab order of page elements."%>
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
    description="CSS class HTML attribute added to default PEPS classes passed on to Struts tags."%>
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
            
            if (buttonText.length() > 26) {
                buttonCssClass = "wideButton4";
            }            
            else if (buttonText.length() > 21) {
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
            
            String name ="";
            if (method != null || action != null) {
                name = (method != null ? method : "") + ":" + (action != null ? action : "");
            }
%>
<input 
	title="<%=buttonText%>"
    type="submit"
    id="${id}"
    name="<%=name%>"
    value="<%=buttonText%>"
    class="<%=buttonCssClass%> ${cssClass}"
    style="${cssStyle}"
    onclick="${onclick}"
    tabindex="${tabindex}"
<%= disabled != null && disabled ? "disabled='disabled'" : ""%> />