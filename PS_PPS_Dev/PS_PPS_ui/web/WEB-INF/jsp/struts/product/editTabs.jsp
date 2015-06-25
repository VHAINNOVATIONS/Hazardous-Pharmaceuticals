<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>
<%
String tabToDisplay = (String) request.getAttribute(ManagedItemAction.TAB_ELEMENT_ID);
%>
<li
    id="<%=ManagedItemAction.NATIONAL_VIEW_TAB%>tab"
    class="<%=ManagedItemAction.NATIONAL_VIEW_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.NATIONAL_VIEW_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.NATIONAL_VIEW_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.nationalview")%>"><s:text name="national.view.tab" /></a></li>       
<li id="<%=ManagedItemAction.DRUG_DATA_TAB%>tab"
    class="<%=ManagedItemAction.DRUG_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.DRUG_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.DRUG_DATA_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.drugdata")%>"><s:text name="drug.data.tab" /></a></li>
<li id="<%=ManagedItemAction.OPTIONS_TAB%>tab"
    class="<%=ManagedItemAction.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.OPTIONS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.OPTIONS_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.quickactions")%>"><s:text name="options.tab" /></a></li>
<li id="<%=ManagedItemAction.APPLICATION_DATA_TAB%>tab"
    class="<%=ManagedItemAction.APPLICATION_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.APPLICATION_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.APPLICATION_DATA_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.applicationdata")%>"><s:text name="application.data.tab" /></a></li>
<li id="<%=ManagedItemAction.DISPENSE_DATA_TAB%>tab"
    class="<%=ManagedItemAction.DISPENSE_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.DISPENSE_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.DISPENSE_DATA_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.dispensedata")%>"><s:text name="dispense.data.tab" /></a></li>
<li id="<%=ManagedItemAction.ADMINISTRATION_DATA_TAB%>tab"
    class="<%=ManagedItemAction.ADMINISTRATION_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.ADMINISTRATION_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.ADMINISTRATION_DATA_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.admindata")%>"><s:text name="administration.data.tab" /></a></li>
<li id="<%=ManagedItemAction.WARNING_LABELS_TAB%>tab"
    class="<%=ManagedItemAction.WARNING_LABELS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.WARNING_LABELS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.WARNING_LABELS_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.warning.labels")%>"><s:text name="warning.labels.tab" /></a></li>
