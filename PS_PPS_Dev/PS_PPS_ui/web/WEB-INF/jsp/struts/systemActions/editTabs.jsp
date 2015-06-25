<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>    
    
<%@page import="gov.va.med.pharmacy.peps.presentation.common.command.ConsoleCommand"%>

<%
String tabToDisplay = (String) request.getAttribute(ConsoleCommand.TAB_ELEMENT_ID);
%>
<li
    class="<%=ConsoleCommand.SEND_DRUG_FILE.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ConsoleCommand.SEND_DRUG_FILE %>"
    onclick="submitOnFormChange('ConsoleCommandForm', '<%=ConsoleCommand.SEND_DRUG_FILE%>')"
	title="<spring:message code="click.to.send.drug.file"/>"><spring:message code="send.drug.file.tab" /></a></li>    