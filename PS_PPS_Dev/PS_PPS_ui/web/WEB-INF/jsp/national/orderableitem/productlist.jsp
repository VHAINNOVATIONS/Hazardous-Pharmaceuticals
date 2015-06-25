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
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%
            java.util.Map parameters = new java.util.HashMap();
            parameters.put(ControllerConstants.TAB_KEY, ControllerConstants.CHILDREN_TAB);
%>
<c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/startMoveChildren.go?tab=${tab}" var="moveChildrenUrl"/>
<peps:table
    tableId="<%= ControllerConstants.PRODUCT_LIST_TABLE %>"
    dataModel="${item.products}"
    printTemplate="<%=ControllerConstants.PRODUCT_LIST_PRINT_TEMPLATE%>"
    sortExportLinkParameters="<%=parameters%>" 
    multiselectButtonAction="${moveChildrenUrl}"
    multiselectButtonKey="move.products"/>

<spring:message var="openBlankChildTemplate" scope="page" code="button.open.blank.product.template" />

<peps:link
    item="${item}"
    text="${openBlankChildTemplate}"    
    itemId="${item.id}"
    itemType="PRODUCT"
    event="openBlankChildTemplate/add"/>