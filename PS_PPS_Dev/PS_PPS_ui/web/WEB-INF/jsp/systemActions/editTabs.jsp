<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>    

<%
String tabToDisplay = (String) request.getAttribute(ControllerConstants.TAB_ELEMENT_ID);
%>
<li
    class="<%=ControllerConstants.SEND_DRUG_FILE.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ControllerConstants.SEND_DRUG_FILE %>"
    onclick="submitOnFormChange('ConsoleCommandForm', '<%=ControllerConstants.SEND_DRUG_FILE%>')"
	title="<spring:message code="click.to.send.drug.file"/>"><spring:message code="send.drug.file.tab" /></a></li>    