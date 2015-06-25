<%@tag import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>
<%@tag import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@tag import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%@ tag
    language="java"
    body-content="empty"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>
<%@ attribute
    name="id"
    required="true"
    type="java.lang.String"
    description="Unique ID for the element"%>
<%@ attribute
    name="name"
    required="true"
    type="java.lang.String"
    description="HTML name attribute for the hidden input field submitted to the server."%>
<%@ attribute
    name="value"
    required="true"
    type="java.util.Date"
    description="Current java.util.Date value of the field."%>
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
    name="maxlength"
    required="true"
    type="java.lang.String"
    description="Maximum length for the displayed field."%>
<%@ attribute
    name="size"
    required="true"
    type="java.lang.String"
    description="Size for the displayed field."%>
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
    description="CSS style HTML attribute passed on to the controlheader tag."%>
<%@ attribute
    name="cssClass"
    required="false"
    type="java.lang.String"
    description="CSS class HTML attribute passed on to the controlheader tag."%>
<%@ attribute
    name="headerFooterRequired"
    required="false"
    type="java.lang.Boolean"
    description="Flag to turn the controlheader/footer tags on and off."%>   
<%@ attribute
    name="tabindex"
    required="false"
    type="java.lang.Integer"
    description="Tab index of input"%>      
<%@ attribute
    name="readonly"
    required="false"
    type="java.lang.Boolean"
    description="Makes the HTML input field readonly - the user can still use scrollbars in textareas if this attribute has been set."%>    
<%
    		UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
            String dateFormat = userContext.getUser().getDateFormatPreference();

           
                if (value != null) {
                jspContext.setAttribute("hiddenValue", value.getTime());

                jspContext.setAttribute("displayValue",
                    gov.va.med.pharmacy.peps.presentation.common.utility.DateFormatUtility
                        .format(value, dateFormat, request.getLocale()));

            }
            else {
                jspContext.setAttribute("hiddenValue", "");
                jspContext.setAttribute("displayValue", "");
            }

            if (dateFormat == null) {
                jspContext.setAttribute("displayFormat",
                    gov.va.med.pharmacy.peps.presentation.common.utility.DateFormatUtility.getDatejsFormat(request
                        .getLocale()));
                jspContext.setAttribute("timeFormat", gov.va.med.pharmacy.peps.presentation.common.utility.DateFormatUtility
                    .get12or24(request.getLocale()));
            }
            else {
                jspContext.setAttribute("displayFormat",
                    gov.va.med.pharmacy.peps.presentation.common.utility.DateFormatUtility.getDatejsFormat(dateFormat, request
                        .getLocale()));
                jspContext.setAttribute("timeFormat", gov.va.med.pharmacy.peps.presentation.common.utility.DateFormatUtility
                    .get12or24(dateFormat, request.getLocale()));
            }
%>

<c:if test="${headerFooterRequired}">    
<peps:controlheader    
     id="${id}" 
     name="${name}" 
     label="${label}" 
     title="${title}" 
     labelposition="${labelPosition}"
     required="${required}"     
     labelcssClass="${cssClass}" />
</c:if>
<input type="text"
    id="${id}"
    name="${name}"
    value="${displayValue}"
    title="${title}"    
    maxlength="${maxlength}"
    size="${size}"    
    tabindex="${tabindex}"
    class="${cssClass}"
    style=""
    <%=disabled ? "disabled" : "" %>
    <c:if test="${readonly}">readonly="readonly"</c:if>/>
&nbsp;
<span id="${id}_display" ></span>
<spring:message
    scope="page"
    var="invalidDateMessage"
    code="date.invalid.message" />
<%-- <spring:message --%>
<!--     scope="page" -->
<!--     var="promptDateMessage" -->
<!--     code="date.prompt.message" /> -->
<script type="text/javascript">
    	
        function initDateInput() {
    		var message = "${invalidDateMessage}";
    		var input = document.getElementById('${id}');
            var displayField = document.getElementById('${id}_display');            
            var date = null;
    		var input_empty = empty_string = "";
    		
            if (input.value.length == 0) {
                input.value = input_empty;
                displayField.innerHTML = empty_string;
                //outputField.value = empty_string;
            } else {
                date = Date.parse(input.value);
                        
                if (date != null) {
                    displayField.innerHTML = date.toString("${displayFormat}");
                }
            }
            
    		input.onkeyup = function() {
    				
    				if (input.value.length > 0) {
    					date = Date.parse(input.value);
    					
    					if (date != null) {
    						displayField.innerHTML = date.toString("${displayFormat}");
    					} else {
    						displayField.innerHTML = message;                            
    					}
    				} else {
    					displayField.innerHTML = empty_string;                        
    				}
    			};
    		input.onfocus = 
    			function (e) {
    				if (input.value == input_empty) {
    					input.value = "";
    				}
    			};
    		input.onblur = 
    			function (e) {
    				if (input.value == "") {
    					input.value = input_empty;
    				}
    			};
        }
    
        initDateInput();
</script>
<c:if test="${headerFooterRequired}">    
<peps:controlfooter    
     id="${id}" 
     name="${name}" 
     label="${label}" 
     title="${title}" 
     labelposition="${labelPosition}"
     required="${required}"     
     labelcssClass="${cssClass}" />
</c:if>