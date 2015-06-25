<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction"%>
<%
String tabToDisplay = (String) request.getAttribute(ManagedItemAction.TAB_ELEMENT_ID);
%>
<% if (ManagedItemAction.NATIONAL_VIEW_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.NATIONAL_VIEW_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.NATIONAL_VIEW_TAB%>"
    style="<%=ManagedItemAction.NATIONAL_VIEW_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.NATIONAL_VIEW_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <tiles:insertAttribute name="national.view" />
    </peps:model>
</div> 
<% } %>
<% if (ManagedItemAction.DRUG_DATA_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.DRUG_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.DRUG_DATA_TAB%>"
    style="<%=ManagedItemAction.DRUG_DATA_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.DRUG_DATA_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <tiles:insertAttribute name="drug.data" />
    </peps:model>
</div> 
<% } %>
<% if (ManagedItemAction.OPTIONS_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.OPTIONS_TAB%>"
    style="<%=ManagedItemAction.OPTIONS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.OPTIONS_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <tiles:insertAttribute name="quick.actions" />
    </peps:model>
</div> 
<% } %>
<% if (ManagedItemAction.APPLICATION_DATA_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.APPLICATION_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.APPLICATION_DATA_TAB%>"
    style="<%=ManagedItemAction.APPLICATION_DATA_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.APPLICATION_DATA_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <tiles:insertAttribute name="application.data" />
    </peps:model>
</div>
<% } %>
<% if (ManagedItemAction.DISPENSE_DATA_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.DISPENSE_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.DISPENSE_DATA_TAB%>"
    style="<%=ManagedItemAction.DISPENSE_DATA_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.DISPENSE_DATA_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <tiles:insertAttribute name="dispense.data" />
    </peps:model>
</div>
<% } %>
<% if (ManagedItemAction.ADMINISTRATION_DATA_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.ADMINISTRATION_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.ADMINISTRATION_DATA_TAB%>"
    style="<%=ManagedItemAction.ADMINISTRATION_DATA_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.ADMINISTRATION_DATA_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <tiles:insertAttribute name="administration.data" />
    </peps:model>
</div>
<% } %>
<% if (ManagedItemAction.WARNING_LABELS_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.WARNING_LABELS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.WARNING_LABELS_TAB%>"
    style="<%=ManagedItemAction.WARNING_LABELS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.WARNING_LABELS_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <tiles:insertAttribute name="warning.labels" />
    </peps:model>
</div>
<% } %>