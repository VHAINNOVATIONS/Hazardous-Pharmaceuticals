<%@ taglib 
    prefix="form"
    uri="http://www.springframework.org/tags/form"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib 
	prefix="fmt" 
	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib 
	prefix="fn"
	uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>

<%@page import="java.util.List"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.utility.FieldKeyUtility"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.UrlUtility"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%@page import="java.util.Collection"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility"%>

<script
    src="/PRE/javascript/formDiff.js"
    type="text/javascript"></script>
<%
            ManagedItemVo managedItem = (ManagedItemVo)request.getAttribute(ControllerConstants.ITEM_KEY);
            EntityType itemType = managedItem.getEntityType();
            String tabToDisplay = (String) request.getAttribute(ControllerConstants.TAB_KEY);
            Collection<ModDifferenceVo> modDifferences = (Collection<ModDifferenceVo>)request.getAttribute("modDifferences");
%>
<peps:warnings warnings="${warnings}" id="warnings"/>
<form:form id="managedItemForm" 
    method="post" action="" 
    onsubmit="selectAllTheOptions('selectedWarningLabels'); return disableSubmit(this.id);">

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
        <li id="<%=ControllerConstants.CHILDREN_TAB%>"
            class="<%=ControllerConstants.CHILDREN_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
            href="<%= "#"+ ControllerConstants.CHILDREN_TAB %>"
            onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.CHILDREN_TAB%>')"
            title="<%= JspTagUtility.getText(request, "click.to.edit.associated") + " " + FieldKeyUtility.getChildFieldKey(managedItem.getEntityType()).getLocalizedName(request.getLocale(), managedItem.getEntityType())%>">
        <%=FieldKeyUtility.getChildFieldKey(managedItem.getEntityType()).getLocalizedName(request.getLocale(),
                    managedItem.getEntityType())%></a></li>
    </c:if>
    
    <c:if test="<%=itemType.isEntityType() || itemType.isPharmacySystem() || itemType.isDomainType() %>">
        <li id="<%=ControllerConstants.HISTORY_TAB%>"
            class="<%=ControllerConstants.HISTORY_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
            href="<%= "#"+ ControllerConstants.HISTORY_TAB %>"
            onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.HISTORY_TAB%>')"
            title="<%= JspTagUtility.getText(request, "click.to.review.history") %>"><spring:message code="history.tab" /></a></li>
    </c:if>
    
    <c:if test="${mainRequest != null}">
        <c:if test="${mainRequestSize > 0}">
            <li class="<%=ControllerConstants.REQUESTS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
                href="<%= "#" + ControllerConstants.REQUESTS_TAB %>"
                onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.REQUESTS_TAB%>')"
                title="<%= JspTagUtility.getText(request, "click.to.review.requests") %>"><peps:localizeName
                key="pending.modification" /></a></li>
         </c:if>
    
        <!--  Blank problem reports have default text of 'Additional Information', so let those be suppressed -->
        <c:if test="${mainRequest.note != 'Additional Information'}">
            <li class="<%=ControllerConstants.REPORTS_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
                href="<%= "#" + ControllerConstants.REPORTS_TAB %>"
                onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.REPORTS_TAB%>')"
                title="<%= JspTagUtility.getText(request, "click.to.review.reports") %>"><peps:localizeName
                key="problem.reports" /></a></li>
        </c:if>
    </c:if>
    
    <c:if test="<%=itemType.isEntityType() || itemType.isPharmacySystem() || itemType.isDomainType()%>">
    <li id="<%=ControllerConstants.ALPHA_TAB%>"
    	class="<%=ControllerConstants.ALPHA_TAB.equals(tabToDisplay) ? "TabbedPanelsTab TabbedPanelsTabSelected" : "TabbedPanelsTab"%>"><a
        href="<%= "#" + ControllerConstants.ALPHA_TAB %>"
        onclick="submitOnFormChange('managedItemForm', '<%=ControllerConstants.ALPHA_TAB%>')"
        title="<%= JspTagUtility.getText(request, "click.to.edit.fields") + " A - Z"%>"><spring:message code="alpha.tab" /></a></li>
    </c:if>
</ul>

<label style="padding: 15px"></label>
<c:if test="<%=itemType.isProduct()%>">
    <a id="showLink" href="#" onclick="set_cookie('showTabs', true, 60); return switchVisability('${tab}');">Show All Tabs</a>
    <a id="hideLink" href="#" onclick="set_cookie('showTabs', false, 60); return switchVisability('${tab}');">Hide  All Tabs </a>
	<script type="text/javascript">
	   hideAll("${tab}", getCookie('showTabs'));
	   var tabButton =  getCookie('showTabs');
	   if(tabButton == 'true') {
		   document.getElementById("showLink").style.display = "none";
		   document.getElementById("hideLink").style.display = "inline";
		   
	   }
	   else {
		   document.getElementById("showLink").style.display = "inline";
		   document.getElementById("hideLink").style.display = "none";
	   }
	</script>
		

      <%-- XXX fix this
      <c:if test="<%=((Integer)request.getAttribute("isFirst")) > 2%>">
            <script type="text/javascript">switchVisability("<%=tabToDisplay%>");</script>
      </c:if> --%>
</c:if>
<c:if test="<%= !ControllerConstants.ALPHA_TAB.equals(tabToDisplay)%>">
	<label style="padding: 15px"><font color="red">*</font> = required fields</label>
</c:if>

<div
    class="TabbedPanelsContentGroup"
    id="tabs">
    <tiles:insertAttribute name="tabs.content" />

<% if (ControllerConstants.CHILDREN_TAB.equals(tabToDisplay)) { %>
 
<c:if test="<%=managedItem.hasChildren()%>">
    <div
        id="<%=ControllerConstants.CHILDREN_TAB%>"
        class="<%=ControllerConstants.CHILDREN_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
        style="<%=ControllerConstants.CHILDREN_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.CHILDREN_TAB%>"></a>

        <tiles:insertAttribute name="children" />

    </div>
</c:if>
<% } %>


    <c:if test="<%=itemType.isEntityType() || itemType.isPharmacySystem() || itemType.isDomainType() %>">
<% if (ControllerConstants.HISTORY_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.HISTORY_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.HISTORY_TAB%>"
    style="<%=ControllerConstants.HISTORY_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.HISTORY_TAB%>"></a>

        <tiles:insertAttribute name="history" />

</div>
<% } %>

    </c:if>
<% if (ControllerConstants.REQUESTS_TAB.equals(tabToDisplay)) { %>

 
<c:if test="${mainRequest != null}">
    <c:if test="${mainRequestSize>0}">
        <div
            class="<%=ControllerConstants.REQUESTS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
            id="<%=ControllerConstants.REQUESTS_TAB%>"
            style="<%=ControllerConstants.REQUESTS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.REQUESTS_TAB%>"></a>
                <tiles:insertAttribute name="requests" />
        </div>
    </c:if>
</c:if>

<% } %>
<% if (ControllerConstants.REPORTS_TAB.equals(tabToDisplay)) { %>

<c:if test="${mainRequest != null}">
    <!--  Blank problem reports have default text of 'Additional Information', so let those be suppressed -->
    <c:if test="${mainRequest.note != 'Additional Information'}">
        <div
            class="<%=ControllerConstants.REPORTS_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
            id="<%=ControllerConstants.REPORTS_TAB%>"
            style="<%=ControllerConstants.REPORTS_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.REPORTS_TAB%>"></a>
            <tiles:insertAttribute name="reports" />        
        </div>
    </c:if>
</c:if>

<% } %>

    <c:if test="<%=itemType.isEntityType() || itemType.isPharmacySystem() || itemType.isDomainType()%>">
<% if (ControllerConstants.ALPHA_TAB.equals(tabToDisplay)) { %>
<div
    class="<%=ControllerConstants.ALPHA_TAB.equals(tabToDisplay) ? "TabbedPanelsContent TabbedPanelsContentVisible" : "TabbedPanelsContent"%>"
    id="<%=ControllerConstants.ALPHA_TAB%>"
    style="<%=ControllerConstants.ALPHA_TAB.equals(tabToDisplay) ? "" : "display: none;"%>"><a name="<%=ControllerConstants.ALPHA_TAB%>"></a>
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

<c:choose>
	<c:when test="<%=DifferenceUtility.hasEditableDifference(modDifferences)&& (!ControllerConstants.REQUESTS_TAB.equals(tabToDisplay)) %>" >   
	    <input id="dirty.flag" type="hidden" value="true">
	</c:when>
	<c:otherwise>
	    <input id="dirty.flag" type="hidden" value="false">
	</c:otherwise>
</c:choose>

</form:form>
<% 	
%>
<%-- <input id="dirty.flag" type="hidden" value="<%=oldItem.compareTo(newItem) %>" /> --%>
<input id="partial.save.flag" type="hidden" value="<%=managedItem.isPartialSave() %>" />
<!-- <div id="dirty.flag" class="hidden"><s:property value="oldItem.diff(item).size() > 0"/></div> -->