<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %> 
	
<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%
String tabToDisplay = (String) request.getAttribute(ControllerConstants.TAB_ELEMENT_ID);
%>
<li
    class="<%=ControllerConstants.NDCMAIN_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "?"+ControllerConstants.TAB_KEY+"=" + ControllerConstants.NDCMAIN_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.NDCMAIN_TAB%>')"
    title="<spring:message code="click.to.edit.main" />"><spring:message code="ndcmain.tab" /></a></li>
<%--   <li
    class="<%=ControllerConstants.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "?"+ControllerConstants.TAB_KEY+"=" + ControllerConstants.OPTIONS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.OPTIONS_TAB%>')"
    title="<spring:message code="click.to.edit.quickactions" />"><spring:message code="options.tab" /></a></li>  --%>
<li
    class="<%=ControllerConstants.NDCPRICE_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "?"+ControllerConstants.TAB_KEY+"=" + ControllerConstants.NDCPRICE_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.NDCPRICE_TAB%>')"
    title='<spring:message code="click.to.edit.price" />'><spring:message code="ndcprice.tab" /></a></li>