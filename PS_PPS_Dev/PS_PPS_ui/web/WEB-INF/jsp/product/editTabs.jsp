<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib  
    prefix="spring"
    uri="http://www.springframework.org/tags"%>     
    
<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>

<%
String tabToDisplay = (String) request.getAttribute(ControllerConstants.TAB_ELEMENT_ID);
%>
<li
    id="<%=ControllerConstants.NATIONAL_VIEW_TAB%>"
    class="<%=ControllerConstants.NATIONAL_VIEW_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%="#" + ControllerConstants.NATIONAL_VIEW_TAB%>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.NATIONAL_VIEW_TAB%>')"
    title="<spring:message code="click.to.edit.nationalview"/>"><spring:message code="national.view.tab" /></a></li>       
<li id="<%=ControllerConstants.DRUG_DATA_TAB%>"
    class="<%=ControllerConstants.DRUG_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#"+ControllerConstants.DRUG_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.DRUG_DATA_TAB%>')"
    title="<spring:message code="click.to.edit.drugdata"/>"><spring:message code="drug.data.tab" /></a></li>
<li id="<%=ControllerConstants.OPTIONS_TAB%>"
    class="<%=ControllerConstants.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ControllerConstants.OPTIONS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.OPTIONS_TAB%>')"
    title="<spring:message code="click.to.edit.quickactions"/>"><spring:message code="options.tab" /></a></li>
<li id="<%=ControllerConstants.APPLICATION_DATA_TAB%>"
    class="<%=ControllerConstants.APPLICATION_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ControllerConstants.APPLICATION_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.APPLICATION_DATA_TAB%>')"
    title="<spring:message code="click.to.edit.applicationdata"/>"><spring:message code="application.data.tab" /></a></li>
<li id="<%=ControllerConstants.DISPENSE_DATA_TAB%>"
    class="<%=ControllerConstants.DISPENSE_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ControllerConstants.DISPENSE_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.DISPENSE_DATA_TAB%>')"
    title="<spring:message code="click.to.edit.dispensedata"/>"><spring:message code="dispense.data.tab" /></a></li>
<li id="<%=ControllerConstants.ADMINISTRATION_DATA_TAB%>"
    class="<%=ControllerConstants.ADMINISTRATION_DATA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ControllerConstants.ADMINISTRATION_DATA_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.ADMINISTRATION_DATA_TAB%>')"
    title="<spring:message code="click.to.edit.admindata"/>"><spring:message code="administration.data.tab" /></a></li>
