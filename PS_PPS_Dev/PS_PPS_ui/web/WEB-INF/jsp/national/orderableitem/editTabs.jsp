<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>

<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%
String tabToDisplay = (String) request.getAttribute(ControllerConstants.TAB_KEY);
%>

<li
    class="<%=ControllerConstants.DETAILS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "?"+ControllerConstants.TAB_KEY+"="+ ControllerConstants.DETAILS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.DETAILS_TAB%>')"
    title="<%=JspTagUtility.getText(request, "click.to.edit.details")%>"><spring:message code="details.tab" /></a></li>
<!-- <li -->
<%--     class="<%=ControllerConstants.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a --%>
<%--     href="<%= "#" + ControllerConstants.OPTIONS_TAB %>" --%>
<%--     onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.OPTIONS_TAB%>')" --%>
<%--     title="<%=JspTagUtility.getText(request, "click.to.edit.quickactions")%>"><spring:message code="options.tab" /></a></li> --%>
<li
    class="<%=ControllerConstants.SAFETY_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "?"+ControllerConstants.TAB_KEY+"="+ ControllerConstants.SAFETY_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.SAFETY_TAB%>')"
    title="<%=JspTagUtility.getText(request, "click.to.edit.safety")%>"><spring:message code="safety.tab" /></a></li>
<li
    class="<%=ControllerConstants.LABS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "?"+ControllerConstants.TAB_KEY+"="+ ControllerConstants.LABS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.LABS_TAB%>')"
    title="<%=JspTagUtility.getText(request, "click.to.edit.labsvitals")%>"><spring:message code="labs.tab" /></a></li>