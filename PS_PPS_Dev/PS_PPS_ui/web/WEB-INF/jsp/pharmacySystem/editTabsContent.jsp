<%-- 
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction"%>
<%
String tabToDisplay = (String) request.getAttribute(ManagedItemAction.TAB_ELEMENT_ID);
%>
<% if (ManagedItemAction.PHARMACY_SYSTEM_PARAMETERS.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.PHARMACY_SYSTEM_PARAMETERS.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.PHARMACY_SYSTEM_PARAMETERS%>"
    style="<%=ManagedItemAction.PHARMACY_SYSTEM_PARAMETERS.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.PHARMACY_SYSTEM_PARAMETERS%>"></a>
    <peps:model ognlPrefix="item">
        <%@ include file="parameters.jsp" %>
    </peps:model>
</div>
<% } %>
<% if (ManagedItemAction.PHARMACY_SYSTEM_OHTER_LANGUAGE.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.PHARMACY_SYSTEM_OHTER_LANGUAGE.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.PHARMACY_SYSTEM_OHTER_LANGUAGE%>"
    style="<%=ManagedItemAction.PHARMACY_SYSTEM_OHTER_LANGUAGE.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.PHARMACY_SYSTEM_OHTER_LANGUAGE%>"></a>
    <peps:model ognlPrefix="item">
        <%@ include file="otherLanguageTranslation.jsp" %>
    </peps:model>
</div>
<% } %>
 --%>
<!-- Spring  -->

<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
    
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>    
    
<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>

<%
String tabToDisplay = (String) request.getAttribute(ControllerConstants.TAB_ELEMENT_ID);
%>

<c:set var="tabToDisplay" value="${ControllerConstants.tabElementId}" />
<c:out value="${tabToDisplay}"/>

<% if (ControllerConstants.PHARMACY_SYSTEM_PARAMETERS.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.PHARMACY_SYSTEM_PARAMETERS.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.PHARMACY_SYSTEM_PARAMETERS%>"
    style="<%=ControllerConstants.PHARMACY_SYSTEM_PARAMETERS.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.PHARMACY_SYSTEM_PARAMETERS%>"></a>    
    <%@ include file="parameters.jsp" %>    
</div>
<% } %>
<% if (ControllerConstants.PHARMACY_SYSTEM_OHTER_LANGUAGE.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.PHARMACY_SYSTEM_OHTER_LANGUAGE.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.PHARMACY_SYSTEM_OHTER_LANGUAGE%>"
    style="<%=ControllerConstants.PHARMACY_SYSTEM_OHTER_LANGUAGE.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.PHARMACY_SYSTEM_OHTER_LANGUAGE%>"></a>
    <%@ include file="otherLanguageTranslation.jsp" %>
</div>
<% } %>

