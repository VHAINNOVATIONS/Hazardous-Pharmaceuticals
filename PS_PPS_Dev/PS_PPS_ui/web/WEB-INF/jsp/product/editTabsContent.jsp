<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%
String tabToDisplay = (String)request.getAttribute(ControllerConstants.TAB_KEY);
ManagedItemVo item = (ManagedItemVo) request.getAttribute(ControllerConstants.ITEM_KEY);
EntityType itemType = item.getEntityType();
%>
<c:if test="${tab eq 'national_view_tab'}">
<div
    class="TabbedPanelsContent TabbedPanelsContentVisible"
    id="<%=ControllerConstants.NATIONAL_VIEW_TAB%>"><a name="<%=ControllerConstants.NATIONAL_VIEW_TAB%>"></a>
    
    <tiles:insertAttribute name="national.view" />
</div> 
</c:if>
<% if (ControllerConstants.DRUG_DATA_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.DRUG_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.DRUG_DATA_TAB%>"
    style="<%=ControllerConstants.DRUG_DATA_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.DRUG_DATA_TAB%>"></a>
    
    <tiles:insertAttribute name="drug.data" />    
</div> 
<% } %>
<% if (ControllerConstants.OPTIONS_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.OPTIONS_TAB%>"
    style="<%=ControllerConstants.OPTIONS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.OPTIONS_TAB%>"></a>
    
    <tiles:insertAttribute name="quick.actions" />
</div> 
<% } %>
<% if (ControllerConstants.APPLICATION_DATA_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.APPLICATION_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.APPLICATION_DATA_TAB%>"
    style="<%=ControllerConstants.APPLICATION_DATA_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.APPLICATION_DATA_TAB%>"></a>
    
    <tiles:insertAttribute name="application.data" />
</div>
<% } %>
<% if (ControllerConstants.DISPENSE_DATA_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.DISPENSE_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.DISPENSE_DATA_TAB%>"
    style="<%=ControllerConstants.DISPENSE_DATA_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.DISPENSE_DATA_TAB%>"></a>
    
    <tiles:insertAttribute name="dispense.data" />
</div>
<% } %>
<% if (ControllerConstants.ADMINISTRATION_DATA_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.ADMINISTRATION_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.ADMINISTRATION_DATA_TAB%>"
    style="<%=ControllerConstants.ADMINISTRATION_DATA_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.ADMINISTRATION_DATA_TAB%>"></a>
    
    <tiles:insertAttribute name="administration.data" />
</div>
<% } %>