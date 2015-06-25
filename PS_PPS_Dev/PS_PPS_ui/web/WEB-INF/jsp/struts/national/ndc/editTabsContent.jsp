<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction"%>
<%
String tabToDisplay = (String) request.getAttribute(ManagedItemAction.TAB_ELEMENT_ID);
%>
<% if (ManagedItemAction.NDCMAIN_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.NDCMAIN_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.NDCMAIN_TAB%>"
    style="<%=ManagedItemAction.NDCMAIN_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.NDCMAIN_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <%@ include file="main.jsp" %>
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
<% if (ManagedItemAction.NDCPRICE_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.NDCPRICE_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.NDCPRICE_TAB%>"
    style="<%=ManagedItemAction.NDCPRICE_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.NDCPRICE_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <%@ include file="price.jsp" %>
    </peps:model>
</div>
<% } %>