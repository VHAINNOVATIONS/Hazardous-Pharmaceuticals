<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>	    
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<c:if test="${not empty requests}">
    <peps:table
        tableId="searchTable"
        dataModel="${requests}"
        printTemplate="<%=ControllerConstants.REQUESTS_SEARCH_PRINT_TEMPLATE%>"
        paged="false" />
</c:if>