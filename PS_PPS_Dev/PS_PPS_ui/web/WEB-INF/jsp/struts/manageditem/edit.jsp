<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
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
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.utility.FieldKeyUtility"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.UrlUtility"%>gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtilitystruts.OgnlUtility"%>
<form
    id="managedItemForm"
     method="post"
     action="<%=UrlUtility.createMvcExecutionUrl(request)%>"
     onsubmit="selectAllTheOptions('selectedWarningLabels'); return disableSubmit(this.id);">
<%
            ManagedItemVo managedItem = OgnlUtility.findValue(request, "item");            
            String tabToDisplay = (String) request.getAttribute(ManagedItemAction.TAB_ELEMENT_ID);
            //System.out.println("tabToDisplay = " + tabToDisplay);
%>

<s:set
    scope="page"
    name="itemId"
    value="item.id" />
<s:set
    scope="page"
    name="itemTypeString"
    value="itemType.toFieldKey()" />
<!-- Tabs for domains don't get a grey background -->
<s:if test="%{itemType.isDomainType() && !itemType.pharmacySystem}">
<%-- <c:if test="%{itemType.isDomainType() && !itemType.pharmacySystem}"> --%>
<H1><c:out value="${ titleEdit }" />&nbsp;<c:out value="${ itemValue }" /><%-- <s:text name="title.edit" />&nbsp;<s:text name="item.value" /> --%><peps:label key="${itemTypeString}" /></H1>
<div class="horizontalspacer"></div>
</s:if>
<%-- </c:if> --%>

<div
    id="TabbedPanels1"
    class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">

	<tiles:insertAttribute name="tabs.list" />

    <s:if test="%{item.hasChildren()}">
        <li id="<%=ManagedItemAction.CHILDREN_TAB%>tab"
        	class="<%=ManagedItemAction.CHILDREN_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
            href="<%= "#" + ManagedItemAction.CHILDREN_TAB %>"
            onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.CHILDREN_TAB%>');  "
            title="<%= OgnlUtility.getText(request, "click.to.edit.associated") + " " + FieldKeyUtility.getChildFieldKey(managedItem.getEntityType()).getLocalizedName(request.getLocale(), managedItem.getEntityType())%>">
        <%=FieldKeyUtility.getChildFieldKey(managedItem.getEntityType()).getLocalizedName(request.getLocale(),
                    managedItem.getEntityType())%></a></li>
    </s:if>
    
    <s:if test="%{itemType.isEntityType() || itemType.pharmacySystem || itemType.isDomainType()}">
    <li id="<%=ManagedItemAction.HISTORY_TAB%>tab"
    	class="<%=ManagedItemAction.HISTORY_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
        href="<%= "#" + ManagedItemAction.HISTORY_TAB %>"
        onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.HISTORY_TAB%>')"
        title="<%= OgnlUtility.getText(request, "click.to.review.history") %>"><%--<s:text name="history.tab" />--%><spring:message code="history.tab" /></a></li>
    </s:if>
    <s:if test="%{mainRequest != null}">
        <s:if test="%{mainRequestSize() > 0}">
            <li id="<%=ManagedItemAction.REQUESTS_TAB%>tab"
            	class="<%=ManagedItemAction.REQUESTS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
                href="<%= "#" + ManagedItemAction.REQUESTS_TAB %>"
                onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.REQUESTS_TAB%>')"
                title="<%= OgnlUtility.getText(request, "click.to.review.requests") %>"><peps:localizeName
                key="pending.modification" /></a></li>
         </s:if>
    
        <!--  Blank problem reports have default text of 'Additional Information', so let those be suppressed -->
        <s:if test="%{!mainRequest.note.equals('Additional Information')}">
            <li id="<%=ManagedItemAction.REPORTS_TAB%>tab"
            	class="<%=ManagedItemAction.REPORTS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
                href="<%= "#" + ManagedItemAction.REPORTS_TAB %>"
                onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.REPORTS_TAB%>')"
                title="<%= OgnlUtility.getText(request, "click.to.review.reports") %>"><peps:localizeName
                key="problem.reports" /></a></li>
        </s:if>
    </s:if>
    
    <%--SPRING/JSTL conversions
    <c:if test="%{item.hasChildren()}">
        <li class="<%=ManagedItemAction.CHILDREN_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
            href="<%= "#" + ManagedItemAction.CHILDREN_TAB %>"
            onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.CHILDREN_TAB%>')"
            title="<%= OgnlUtility.getText(request, "click.to.edit.associated") + " " + FieldKeyUtility.getChildFieldKey(managedItem.getEntityType()).getLocalizedName(request.getLocale(), managedItem.getEntityType())%>">
        <%=FieldKeyUtility.getChildFieldKey(managedItem.getEntityType()).getLocalizedName(request.getLocale(),
                    managedItem.getEntityType())%></a></li>
    </c:if>
    <c:if test="%{itemType.isEntityType() || itemType.pharmacySystem || itemType.isDomainType()}">
    <li class="<%=ManagedItemAction.HISTORY_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
        href="<%= "#" + ManagedItemAction.HISTORY_TAB %>"
        onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.HISTORY_TAB%>')"
        title="<%= OgnlUtility.getText(request, "click.to.review.history") %>"><spring:message code="history.tab" /></a></li>
    </c:if>
    <c:if test="%{mainRequest != null}">
        <c:if test="%{mainRequestSize() > 0}">
            <li class="<%=ManagedItemAction.REQUESTS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
                href="<%= "#" + ManagedItemAction.REQUESTS_TAB %>"
                onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.REQUESTS_TAB%>')"
                title="<%= OgnlUtility.getText(request, "click.to.review.requests") %>"><peps:localizeName
                key="pending.modification" /></a></li>
         </c:if>
    
        <!--  Blank problem reports have default text of 'Additional Information', so let those be suppressed -->
        <c:if test="%{!mainRequest.note.equals('Additional Information')}">
            <li class="<%=ManagedItemAction.REPORTS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
                href="<%= "#" + ManagedItemAction.REPORTS_TAB %>"
                onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.REPORTS_TAB%>')"
                title="<%= OgnlUtility.getText(request, "click.to.review.reports") %>"><peps:localizeName
                key="problem.reports" /></a></li>
        </c:if>
    </c:if> --%>

    <s:if test="%{itemType.isEntityType() || itemType.pharmacySystem || itemType.isDomainType()}">
    <li id="<%=ManagedItemAction.ALPHA_TAB%>tab"
    	class="<%=ManagedItemAction.ALPHA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
        href="<%= "#" + ManagedItemAction.ALPHA_TAB %>"
        onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.ALPHA_TAB%>')"
        title="<%= OgnlUtility.getText(request, "click.to.edit.fields") + " A - Z"%>"><%-- <s:text name="alpha.tab" /> --%><spring:message code="alpha.tab" /></a></li>
    </s:if>
    <%--
    <c:if test="%{itemType.isEntityType() || itemType.pharmacySystem || itemType.isDomainType()}">
    <li id="<%=ManagedItemAction.ALPHA_TAB%>tab"
    	class="<%=ManagedItemAction.ALPHA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
        href="<%= "#" + ManagedItemAction.ALPHA_TAB %>"
        onclick="submitOnFormChange('managedItemForm', '<%=ManagedItemAction.ALPHA_TAB%>')"
        title="<%= OgnlUtility.getText(request, "click.to.edit.fields") + " A - Z"%>"><s:text name="alpha.tab" /></a></li>
    </c:if>--%>
</ul>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<s:if test="%{itemType.isProduct()}">
	<script type="text/javascript">hideAll("<%=tabToDisplay%>");</script>
		<a id="showLink" href="" onclick="return switchVisability('<%=tabToDisplay%>');">Show All Tabs</a>
		<a id="hideLink" href="" onclick="return switchVisability('<%=tabToDisplay%>');" style="display: none">Hide  All Tabs </a>

      <s:if test="%{isFirst>2}">
            <script type="text/javascript">switchVisability("<%=tabToDisplay%>");</script>
      </s:if>
</s:if>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font> = required fields
<div
    class="TabbedPanelsContentGroup"
    id="tabs">
    <tiles:insertAttribute name="tabs.content" />

<% if (ManagedItemAction.CHILDREN_TAB.equals(tabToDisplay)) { %>
<s:if test="%{item.hasChildren()}">
    <div
        class="<%=ManagedItemAction.CHILDREN_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
        id="<%=ManagedItemAction.CHILDREN_TAB%>"
        style="<%=ManagedItemAction.CHILDREN_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.CHILDREN_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <tiles:insertAttribute name="children" />
    </peps:model> 
    </div>
</s:if>
<%--  SPRING/JSTL conversions
<c:if test="%{item.hasChildren()}">
    <div
        class="<%=ManagedItemAction.CHILDREN_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
        id="<%=ManagedItemAction.CHILDREN_TAB%>"
        style="<%=ManagedItemAction.CHILDREN_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.CHILDREN_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <tiles:insertAttribute name="children" />
    </peps:model> 
    </div>
</c:if>
 --%>
<% } %>
    <s:if test="%{itemType.isEntityType() || itemType.pharmacySystem || itemType.isDomainType()}">
    <%-- <c:if test="%{itemType.isEntityType() || itemType.pharmacySystem || itemType.isDomainType()}"> --%>
<% if (ManagedItemAction.HISTORY_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.HISTORY_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.HISTORY_TAB%>"
    style="<%=ManagedItemAction.HISTORY_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.HISTORY_TAB%>"></a>
    <peps:model ognlPrefix="item">
        <tiles:insertAttribute name="history" />
    </peps:model>
</div>
<% } %>
    </s:if> <%-- </c:if> --%>
<% if (ManagedItemAction.REQUESTS_TAB.equals(tabToDisplay)) { %>
<s:if test="%{mainRequest != null}">
    <s:if test="%{mainRequestSize()>0}">
        <div
            class="<%=ManagedItemAction.REQUESTS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
            id="<%=ManagedItemAction.REQUESTS_TAB%>"
            style="<%=ManagedItemAction.REQUESTS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.REQUESTS_TAB%>"></a>
            <peps:model ognlPrefix="item">
                <tiles:insertAttribute name="requests" />
            </peps:model>
        </div>
    </s:if>
</s:if>
<%--  SPRING/JSTL conversions
<c:if test="%{mainRequest != null}">
    <c:if test="%{mainRequestSize()>0}">
        <div
            class="<%=ManagedItemAction.REQUESTS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
            id="<%=ManagedItemAction.REQUESTS_TAB%>"
            style="<%=ManagedItemAction.REQUESTS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.REQUESTS_TAB%>"></a>
            <peps:model ognlPrefix="item">
                <tiles:insertAttribute name="requests" />
            </peps:model>
        </div>
    </c:if>
</c:if>
 --%>
<% } %>
<% if (ManagedItemAction.REPORTS_TAB.equals(tabToDisplay)) { %>
<s:if test="%{mainRequest != null}">
    <!--  Blank problem reports have default text of 'Additional Information', so let those be suppressed -->
    <s:if test="%{!mainRequest.note.equals('Additional Information')}">
        <div
            class="<%=ManagedItemAction.REPORTS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
            id="<%=ManagedItemAction.REPORTS_TAB%>"
            style="<%=ManagedItemAction.REPORTS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.REPORTS_TAB%>"></a>
            <tiles:insertAttribute name="reports" />        
        </div>
    </s:if>
</s:if>

<%-- SPRING/JSTL conversions
<c:if test="%{mainRequest != null}">
    <!--  Blank problem reports have default text of 'Additional Information', so let those be suppressed -->
    <c:if test="%{!mainRequest.note.equals('Additional Information')}">
        <div
            class="<%=ManagedItemAction.REPORTS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
            id="<%=ManagedItemAction.REPORTS_TAB%>"
            style="<%=ManagedItemAction.REPORTS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.REPORTS_TAB%>"></a>
            <tiles:insertAttribute name="reports" />        
        </div>
    </c:if>
</c:if>
 --%>
<% } %>
    <s:if test="%{itemType.isEntityType() || itemType.pharmacySystem || itemType.isDomainType()}">
    <%-- <c:if test="%{itemType.isEntityType() || itemType.pharmacySystem || itemType.isDomainType()}"> --%>
<% if (ManagedItemAction.ALPHA_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ManagedItemAction.ALPHA_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ManagedItemAction.ALPHA_TAB%>"
    style="<%=ManagedItemAction.ALPHA_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ManagedItemAction.ALPHA_TAB%>"></a>
    <tiles:insertAttribute name="az" />
</div>
<% } %>
    </s:if>
    <%-- </c:if> --%>
</div>
</div>
<tiles:insertAttribute name="buttons" />
<s:if test="%{tableSelections != null && tableSelections.size() > 0}">
    <div id="multi.select.flag" class="hidden"><s:property value="true"/></div>
</s:if>
<s:else>
    <div id="multi.select.flag" class="hidden"><s:property value="false"/></div>
</s:else>

<%-- SPRING/JSTL conversions
<c:choose>
	<c:when test="%{tableSelections != null && tableSelections.size() > 0}">
	    <div id="multi.select.flag" class="hidden"><s:property value="true"/></div>
	</c:when>
	<c:otherwise>
	    <div id="multi.select.flag" class="hidden"><s:property value="false"/></div>
	</c:otherwise>
</c:choose>
 --%>
</form>
<div id="dirty.flag" class="hidden"><s:property value="oldItem.diff(item).size() > 0"/></div>
<div id="partial.save.flag" class="hidden"><s:property value="item.partialSave" /></div>