<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction"%>
<%
String tabToDisplay = (String) request.getAttribute(ManagedItemAction.TAB_ELEMENT_ID);
%>
<% if (ManagedItemAction.DETAILS_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.DETAILS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.DETAILS_TAB%>"
    style="<%=ManagedItemAction.DETAILS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.DETAILS_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <%@ include file="details.jsp" %>
    </peps:model>
</div>
<% } %>
<!-- <% if (ManagedItemAction.OPTIONS_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.OPTIONS_TAB%>"
    style="<%=ManagedItemAction.OPTIONS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.OPTIONS_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <%@ include file="quickactions.jsp" %>
    </peps:model>
</div>
<% } %> -->
<% if (ManagedItemAction.SAFETY_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.SAFETY_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.SAFETY_TAB%>"
    style="<%=ManagedItemAction.SAFETY_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.SAFETY_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <%@ include file="safety.jsp" %>
    </peps:model>
</div>
<% } %>
<% if (ManagedItemAction.LABS_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.LABS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.LABS_TAB%>"
    style="<%=ManagedItemAction.LABS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.LABS_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <%@ include file="labs.jsp" %>
    </peps:model>
</div>
<% } %>