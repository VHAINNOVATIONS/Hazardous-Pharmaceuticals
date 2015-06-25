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
    class="<%=ManagedItemAction.NDCMAIN_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.NDCMAIN_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.NDCMAIN_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.main")%>"><s:text name="ndcmain.tab" /></a></li>
<!-- <li
    class="<%=ManagedItemAction.OPTIONS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.OPTIONS_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.OPTIONS_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.quickactions")%>"><s:text name="options.tab" /></a></li>  -->
<li
    class="<%=ManagedItemAction.NDCPRICE_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
    href="<%= "#" + ManagedItemAction.NDCPRICE_TAB %>"
    onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.NDCPRICE_TAB%>')"
    title="<%=OgnlUtility.getText(request, "click.to.edit.price")%>"><s:text name="ndcprice.tab" /></a></li>