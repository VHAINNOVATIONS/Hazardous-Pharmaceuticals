<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
 
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
	    

<%
String tabToDisplay = (String) request.getAttribute(ControllerConstants.TAB_KEY);
%>
<li
    class="<%=ControllerConstants.PHARMACY_SYSTEM_PARAMETERS.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ControllerConstants.PHARMACY_SYSTEM_PARAMETERS %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.PHARMACY_SYSTEM_PARAMETERS%>')"
    title="<%=JspTagUtility.getText(request, "click.to.edit.pharmacy.system.parameters")%>"><spring:message code="pharmacy.system.parameters.tab" /></a></li>
<li
    class="<%=ControllerConstants.PHARMACY_SYSTEM_OHTER_LANGUAGE.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ControllerConstants.PHARMACY_SYSTEM_OHTER_LANGUAGE %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.PHARMACY_SYSTEM_OHTER_LANGUAGE%>')"
    title="<%=JspTagUtility.getText(request, "click.to.edit.pharmacy.system.other.language")%>"><spring:message code="pharmacy.system.other.language.tab" /></a></li>