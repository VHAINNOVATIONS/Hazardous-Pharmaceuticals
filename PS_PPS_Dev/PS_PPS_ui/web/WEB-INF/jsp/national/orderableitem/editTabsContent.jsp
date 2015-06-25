<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>


<%
String tabToDisplay = (String) request.getAttribute(ControllerConstants.TAB_KEY);
%>

<% if (ControllerConstants.DETAILS_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.DETAILS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.DETAILS_TAB%>"
    style="<%=ControllerConstants.DETAILS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.DETAILS_TAB%>"></a>
    
        <%@ include file="details.jsp" %>
    
</div>
<% } %>
<%-- <% if (ControllerConstants.OPTIONS_TAB.equals(tabToDisplay)) { %> --%>
<!-- <div -->
<%--     class="<%=ControllerConstants.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>" --%>
<%--     id="<%=ControllerConstants.OPTIONS_TAB%>" --%>
<%--     style="<%=ControllerConstants.OPTIONS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.OPTIONS_TAB%>"></a> --%>

<%--         <%@ include file="quickactions.jsp" %> --%>

<!-- </div> -->
<%-- <% } %> --%>
<% if (ControllerConstants.SAFETY_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.SAFETY_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.SAFETY_TAB%>"
    style="<%=ControllerConstants.SAFETY_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.SAFETY_TAB%>"></a>
    
        <%@ include file="safety.jsp" %>
    
</div>
<% } %>
<% if (ControllerConstants.LABS_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.LABS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.LABS_TAB%>"
    style="<%=ControllerConstants.LABS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.LABS_TAB%>"></a>
    
        <%@ include file="labs.jsp" %>
    
</div>
<% } %>