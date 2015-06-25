<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%>
<%
String tabToDisplay = (String) request.getAttribute(ControllerConstants.TAB_ELEMENT_ID);
%>
<% if (ControllerConstants.SEND_DRUG_FILE.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.SEND_DRUG_FILE.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.SEND_DRUG_FILE%>"
    style="<%=ControllerConstants.SEND_DRUG_FILE.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.SEND_DRUG_FILE%>"></a>
    
    <peps:model ognlPrefix="item">
        <%@ include file="sendFile.jsp" %>
    </peps:model>
    
</div>
<% } %>