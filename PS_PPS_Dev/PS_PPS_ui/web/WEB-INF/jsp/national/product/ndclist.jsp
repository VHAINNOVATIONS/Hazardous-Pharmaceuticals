<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %> 
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
	
<div class="panel">
<fieldset style="width: 998px;">
<div class="twoColumn">
    <peps:text item="${item}" key="normal.amount.to.order" label="true" cssClass="readonlynational" />
    <peps:text item="${item}" key="reorder.level" label="true" cssClass="readonlynational" />
</div>
<div class="twoColumn">
    <peps:text
        item="${item}"
	    key="single.multi.source.product"
	    label="true"
	    cssClass="readonlynational" />
    <peps:dataField
        item="${item}"
	    key="long.term.out.of.stock"
	    cssClass="aligncheckbox720" />
</div>
</fieldset>
</div>
<div class="horizontalspacer"></div>
<div class="panel">
    <peps:text
        item="${item}"
	    key="dispense.unit"
	    label="true"
	    cssClass="readonlynational" />
</div>
<%
            java.util.Map parameters = new java.util.HashMap();
            parameters.put(ControllerConstants.TAB_ELEMENT_ID, ControllerConstants.CHILDREN_TAB);
%>
<div class="panel" id="showInactivePanel">
    <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/changeShowActive.go?tab=${tab}" var="changeShowActiveUrl"/>
    <script type="text/javascript">
        function changeShowInactive() {
            checkForChanges = null;
            var form = document.getElementById('managedItemForm');
            form.action = '${changeShowActiveUrl}';
            form.method = 'POST';
            form.submit();
        }
    </script>
    <c:if test="${flowScope.showInactiveNDCs}">
        <c:set var="inactiveChecked" value="checked='checked'"/>
    </c:if>
    <input name="showInactiveNdcs" type="checkbox" ${inactiveChecked} onClick="changeShowInactive();" />
    <spring:message var="showInactiveNdcs" code="show.inactive.ndcs" scope="page" />
    <label for="showInactiveNdcs">${showInactiveNdcs}</label>
</div>
<c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/startMoveChildren.go?tab=${tab}" var="moveChildrenUrl"/>
<c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/startCopyChildren.go?tab=${tab}" var="copyChildrenUrl"/>
<peps:table    
    tableId="<%= ControllerConstants.NDC_LIST_TABLE %>"
    dataModel="${productNdcs}"
    printTemplate="<%=ControllerConstants.NDC_LIST_PRINT_TEMPLATE%>"
    export="false"
    sortExportLinkParameters="<%=parameters%>" 
    multiselectButtonAction="${moveChildrenUrl}"
    multiselectButtonKey="move.ndcs"
    multiselectButtonAction2="${copyChildrenUrl}"
    multiselectButtonKey2="copy.ndcs"/>


<spring:message var="openBlankChildTemplate" scope="page" code="button.open.blank.ndc.template" />

<peps:link    
    text="${openBlankChildTemplate}"    
    itemId="${item.id}"
    itemType="NDC"
    event="openBlankChildTemplate/add"/>
    
    