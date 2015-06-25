<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>

	
<%@page import="gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>
<%
String tabToDisplay = (String) request.getAttribute(ManagedItemAction.TAB_ELEMENT_ID);
%>

<li
    class="<%=ManagedItemAction.DETAILS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.DETAILS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.DETAILS_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.details")%>"><%-- <s:text name="details.tab" /> --%><spring:message code="details.tab" /></a></li>
<!--  <li
    class="<%=ManagedItemAction.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.OPTIONS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.OPTIONS_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.quickactions")%>"><s:text name="options.tab" /></a></li>  -->
<li
    class="<%=ManagedItemAction.SAFETY_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.SAFETY_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.SAFETY_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.safety")%>"><%-- <s:text name="safety.tab" /> --%><spring:message code="safety.tab" /></a></li>
<li
    class="<%=ManagedItemAction.LABS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.LABS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.LABS_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.labsvitals")%>"><%-- <s:text name="labs.tab" /> --%><spring:message code="labs.tab" /></a></li>