<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib 
	prefix="peps" 
	tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>    

<c:choose>
<c:when test="${items != null}">
	<div class="horizontalspacer"></div>
	<fieldset>
		<legend><b>SEARCH RESULTS</b></legend>
    <peps:table 
        tableId="<%= ControllerConstants.SEARCH_RESULTS_TABLE %>" 
        dataModel="${items}"
        printTemplate="<%=ControllerConstants.DOMAIN_SEARCH_PRINT_TEMPLATE%>" /> 
    </fieldset>
</c:when>
</c:choose>