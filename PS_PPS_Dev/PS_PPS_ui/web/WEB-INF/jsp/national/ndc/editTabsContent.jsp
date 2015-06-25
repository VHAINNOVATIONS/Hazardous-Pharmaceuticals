<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>

<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%
String tabToDisplay = (String) request.getAttribute(ControllerConstants.TAB_ELEMENT_ID);
%>
<% if (ControllerConstants.NDCMAIN_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.NDCMAIN_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.NDCMAIN_TAB%>"
    style="<%=ControllerConstants.NDCMAIN_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.NDCMAIN_TAB%>"></a>
        <%@ include file="main.jsp" %>

</div>
<% } %>
<!-- <% if (ControllerConstants.OPTIONS_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.OPTIONS_TAB%>"
    style="<%=ControllerConstants.OPTIONS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.OPTIONS_TAB%>"></a>
        <%@ include file="quickactions.jsp" %>

</div >
<% } %> -->
<% if (ControllerConstants.NDCPRICE_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.NDCPRICE_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.NDCPRICE_TAB%>"
    style="<%=ControllerConstants.NDCPRICE_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.NDCPRICE_TAB%>"></a>
        <%@ include file="price.jsp" %>
</div>
<% } %>