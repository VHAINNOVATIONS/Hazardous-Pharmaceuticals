<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>     
    
<%@page import="gov.va.med.pharmacy.peps.presentation.common.command.ManagedItemCommand"%>

<%
String tabToDisplay = (String) request.getAttribute(ManagedItemCommand.TAB_ELEMENT_ID);
%>
<li
    id="<%=ManagedItemCommand.NATIONAL_VIEW_TAB%>tab"
    class="<%=ManagedItemCommand.NATIONAL_VIEW_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemCommand.NATIONAL_VIEW_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.NATIONAL_VIEW_TAB%>')"
    title="<spring:message code="click.to.edit.nationalview"/>"><spring:message code="national.view.tab" /></a></li>       
<li id="<%=ManagedItemCommand.DRUG_DATA_TAB%>tab"
    class="<%=ManagedItemCommand.DRUG_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemCommand.DRUG_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.DRUG_DATA_TAB%>')"
    title="<spring:message code="click.to.edit.drugdata"/>"><spring:message code="drug.data.tab" /></a></li>
<li id="<%=ManagedItemCommand.OPTIONS_TAB%>tab"
    class="<%=ManagedItemCommand.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemCommand.OPTIONS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.OPTIONS_TAB%>')"
    title="<spring:message code="click.to.edit.quickactions"/>"><spring:message code="options.tab" /></a></li>
<li id="<%=ManagedItemCommand.APPLICATION_DATA_TAB%>tab"
    class="<%=ManagedItemCommand.APPLICATION_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemCommand.APPLICATION_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.APPLICATION_DATA_TAB%>')"
    title="<spring:message code="click.to.edit.applicationdata"/>"><spring:message code="application.data.tab" /></a></li>
<li id="<%=ManagedItemCommand.DISPENSE_DATA_TAB%>tab"
    class="<%=ManagedItemCommand.DISPENSE_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemCommand.DISPENSE_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.DISPENSE_DATA_TAB%>')"
    title="<spring:message code="click.to.edit.dispensedata"/>"><spring:message code="dispense.data.tab" /></a></li>
<li id="<%=ManagedItemCommand.ADMINISTRATION_DATA_TAB%>tab"
    class="<%=ManagedItemCommand.ADMINISTRATION_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemCommand.ADMINISTRATION_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.ADMINISTRATION_DATA_TAB%>')"
    title="<spring:message code="click.to.edit.admindata"/>"><spring:message code="administration.data.tab" /></a></li>
<li id="<%=ManagedItemCommand.WARNING_LABELS_TAB%>tab"
    class="<%=ManagedItemCommand.WARNING_LABELS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemCommand.WARNING_LABELS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.WARNING_LABELS_TAB%>')"
    title="<spring:message code="click.to.edit.warning.labels"/>"><spring:message code="warning.labels.tab" /></a></li>
