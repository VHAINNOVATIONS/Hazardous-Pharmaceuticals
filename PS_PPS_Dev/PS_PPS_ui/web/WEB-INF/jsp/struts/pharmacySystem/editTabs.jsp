<%@ taglib
    prefix="s"
    uri="/struts-tags"%> 
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
	    
<%@page import="gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>
<%
String tabToDisplay = (String) request.getAttribute(ManagedItemAction.TAB_ELEMENT_ID);
%>
<li
    class="<%=ManagedItemAction.PHARMACY_SYSTEM_PARAMETERS.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.PHARMACY_SYSTEM_PARAMETERS %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.PHARMACY_SYSTEM_PARAMETERS%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.pharmacy.system.parameters")%>"><spring:message code="pharmacy.system.parameters.tab" /></a></li>
<li
    class="<%=ManagedItemAction.PHARMACY_SYSTEM_OHTER_LANGUAGE.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.PHARMACY_SYSTEM_OHTER_LANGUAGE %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.PHARMACY_SYSTEM_OHTER_LANGUAGE%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.pharmacy.system.other.language")%>"><spring:message code="pharmacy.system.other.language.tab" /></a></li>