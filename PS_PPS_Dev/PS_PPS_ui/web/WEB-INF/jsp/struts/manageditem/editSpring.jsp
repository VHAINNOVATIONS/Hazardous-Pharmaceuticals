

<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@taglib 
	prefix="fmt" 
	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>

<%@page import="java.util.List"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.utility.FieldKeyUtility"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.command.ManagedItemCommand"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.UrlUtility"%>gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtilitylity.JspTagUtility"%>
<script
    src="/PRE/javascript/formDiff.js"
    type="text/javascript"></script>
<form
    id="managedItemForm"
    method="post"
    action="<%=UrlUtility.createMvcExecutionUrl(request)%>"
    onsubmit="selectAllTheOptions('selectedWarningLabels'); return disableSubmit(this.id);">
<%
            ManagedItemVo managedItem = (ManagedItemVo)request.getAttribute("item");
			EntityType itemType = managedItem.getEntityType();
            String tabToDisplay = (String) request.getAttribute(ManagedItemCommand.TAB_ELEMENT_ID);
            
%>

<c:set
    scope="page"
    var="itemId"
    value="<%= managedItem.getId() %>" />
<c:set
    scope="page"
    var="itemTypeString"
    value="<%=itemType.toFieldKey()%>" />
<!-- Tabs for domains don't get a grey background -->

<c:if test="<%=itemType.isDomainType() && !itemType.isPharmacySystem()%>">
<H1><c:out value="${ titleEdit }" />&nbsp;<c:out value="${ itemValue }" /><peps:label key="${itemTypeString}" /></H1>
<div class="horizontalspacer"></div>

</c:if>

<div
    id="TabbedPanels1"
    class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">

	<tiles:insertAttribute name="tabs.list" />
    
    <c:if test="<%=managedItem.hasChildren()%>">
        <li class="<%=ManagedItemCommand.CHILDREN_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
            href="<%= "#" + ManagedItemCommand.CHILDREN_TAB %>"
            onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.CHILDREN_TAB%>')"
            title="<%= JspTagUtility.getText(request, "click.to.edit.associated") + " " + FieldKeyUtility.getChildFieldKey(managedItem.getEntityType()).getLocalizedName(request.getLocale(), managedItem.getEntityType())%>">
        <%=FieldKeyUtility.getChildFieldKey(managedItem.getEntityType()).getLocalizedName(request.getLocale(),
                    managedItem.getEntityType())%></a></li>
    </c:if>
    
    <c:if test="<%=itemType.isEntityType() || itemType.isPharmacySystem() || itemType.isDomainType() %>">
        <li class="<%=ManagedItemCommand.HISTORY_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
            href="<%= "#" + ManagedItemCommand.HISTORY_TAB %>"
            onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.HISTORY_TAB%>')"
            title="<%= JspTagUtility.getText(request, "click.to.review.history") %>"><spring:message code="history.tab" /></a></li>
    </c:if>
    
    <c:if test="${mainRequest != null}">
        <c:if test="${mainRequestSize > 0}">
            <li class="<%=ManagedItemCommand.REQUESTS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
                href="<%= "#" + ManagedItemCommand.REQUESTS_TAB %>"
                onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.REQUESTS_TAB%>')"
                title="<%= JspTagUtility.getText(request, "click.to.review.requests") %>"><peps:localizeName
                key="pending.modification" /></a></li>
         </c:if>
    
        <!--  Blank problem reports have default text of 'Additional Information', so let those be suppressed -->
        <c:if test="${mainRequest.note != 'Additional Information'}">
            <li class="<%=ManagedItemCommand.REPORTS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
                href="<%= "#" + ManagedItemCommand.REPORTS_TAB %>"
                onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.REPORTS_TAB%>')"
                title="<%= JspTagUtility.getText(request, "click.to.review.reports") %>"><peps:localizeName
                key="problem.reports" /></a></li>
        </c:if>
    </c:if>
    
    <c:if test="<%=itemType.isEntityType() || itemType.isPharmacySystem() || itemType.isDomainType()%>">
    <li id="<%=ManagedItemCommand.ALPHA_TAB%>tab"
    	class="<%=ManagedItemCommand.ALPHA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
        href="<%= "#" + ManagedItemCommand.ALPHA_TAB %>"
        onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemCommand.ALPHA_TAB%>')"
        title="<%= JspTagUtility.getText(request, "click.to.edit.fields") + " A - Z"%>"><spring:message code="alpha.tab" /></a></li>
    </c:if>
</ul>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<c:if test="<%=itemType.isProduct()%>">
	<script type="text/javascript">hideAll("<%=tabToDisplay%>");</script>
		<a id="showLink" href="" onclick="return switchVisability('<%=tabToDisplay%>');">Show All Tabs</a>
		<a id="hideLink" href="" onclick="return switchVisability('<%=tabToDisplay%>');" style="display: none">Hide  All Tabs </a>

      <%-- XXX fix this
      <c:if test="<%=((Integer)request.getAttribute("isFirst")) > 2%>">
            <script type="text/javascript">switchVisability("<%=tabToDisplay%>");</script>
      </c:if> --%>
</c:if>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font> = required fields
<div
    class="TabbedPanelsContentGroup"
    id="tabs">
    <tiles:insertAttribute name="tabs.content" />

<% if (ManagedItemCommand.CHILDREN_TAB.equals(tabToDisplay)) { %>
 
<c:if test="<%=managedItem.hasChildren()%>">
    <div
        class="<%=ManagedItemCommand.CHILDREN_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
        id="<%=ManagedItemCommand.CHILDREN_TAB%>"
        style="<%=ManagedItemCommand.CHILDREN_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemCommand.CHILDREN_TAB%>"></a>

        <tiles:insertAttribute name="children" />

    </div>
</c:if>
<% } %>


    <c:if test="<%=itemType.isEntityType() || itemType.isPharmacySystem() || itemType.isDomainType() %>">
<% if (ManagedItemCommand.HISTORY_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemCommand.HISTORY_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemCommand.HISTORY_TAB%>"
    style="<%=ManagedItemCommand.HISTORY_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemCommand.HISTORY_TAB%>"></a>

        <tiles:insertAttribute name="history" />

</div>
<% } %>

    </c:if>
<% if (ManagedItemCommand.REQUESTS_TAB.equals(tabToDisplay)) { %>

 
<c:if test="${mainRequest != null}">
    <c:if test="${mainRequestSize>0}">
        <div
            class="<%=ManagedItemCommand.REQUESTS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
            id="<%=ManagedItemCommand.REQUESTS_TAB%>"
            style="<%=ManagedItemCommand.REQUESTS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemCommand.REQUESTS_TAB%>"></a>
                <tiles:insertAttribute name="requests" />
        </div>
    </c:if>
</c:if>

<% } %>
<% if (ManagedItemCommand.REPORTS_TAB.equals(tabToDisplay)) { %>

<c:if test="${mainRequest != null}">
    <!--  Blank problem reports have default text of 'Additional Information', so let those be suppressed -->
    <c:if test="${mainRequest.note != 'Additional Information'}">
        <div
            class="<%=ManagedItemCommand.REPORTS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
            id="<%=ManagedItemCommand.REPORTS_TAB%>"
            style="<%=ManagedItemCommand.REPORTS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemCommand.REPORTS_TAB%>"></a>
            <tiles:insertAttribute name="reports" />        
        </div>
    </c:if>
</c:if>

<% } %>

    <c:if test="<%=itemType.isEntityType() || itemType.isPharmacySystem() || itemType.isDomainType()%>">
<% if (ManagedItemCommand.ALPHA_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemCommand.ALPHA_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemCommand.ALPHA_TAB%>"
    style="<%=ManagedItemCommand.ALPHA_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemCommand.ALPHA_TAB%>"></a>
    <tiles:insertAttribute name="az" />
</div>
<% } %>

    </c:if>
</div>
</div>
<tiles:insertAttribute name="buttons" />


<c:choose>
	<c:when test="${tableSelections != null && !empty tableSelections}">
	    <input id="multi.select.flag" type="hidden" value="true">
	</c:when>
	<c:otherwise>
	    <input id="multi.select.flag" type="hidden" value="false">
	</c:otherwise>
</c:choose>

</form>
<%-- <input id="dirty.flag" type="hidden" value="<%=((ManagedItemVo)request.getAttribute("oldItem")).diff(managedItem).size() >0%>"/>
<input id="partial.save.flag" type="hidden" value="<%=managedItem.isPartialSave() %>" /> --%>