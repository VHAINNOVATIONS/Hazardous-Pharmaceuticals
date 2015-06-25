<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib 
	prefix="peps" 
	tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib
    prefix="fmt" 
    uri="http://java.sun.com/jsp/jstl/fmt"  %>   
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>  
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
    
<link rel="stylesheet" type="text/css" href="css/reports.css" />

<table  cellpadding="5" width="100%">
				<tr>
					<td>
						<big>
							<strong><c:out value="${selectedReportType}"/></strong>	
						</big>
					</td>
				</tr>
				<c:if test="${showDateCriteria}">
					<tr>
						<td>
							<strong>Date Range:</strong>&nbsp;&nbsp;
							<fmt:formatDate type="both" dateStyle="short" pattern="MM/dd/yyyy" value="${startDate}" /> - 
							<fmt:formatDate type="both" dateStyle="short" pattern="MM/dd/yyyy" value="${endDate}" />
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
					<c:if test="${!empty reportsList}">
						<peps:table 
					        tableId="reportTable" 
							dataModel="${reportsList}" 
							printTemplate="${printTemplate}"
					        paged="false" /> 
					</c:if>
					<c:if test="${!empty ingredientList}">
						<peps:table 
					        tableId="reportTable" 
							dataModel="${ingredientList}" 
							printTemplate="${printTemplate}"
					        paged="false" /> 
					</c:if>
					</td>
				</tr>
			</table>
		
		
		
		<c:set scope="request" var="isEnt" value="${flowScope.getFlowExecutionContext}"/>
<c:out value="${isEnt}" />
<b><i>Request Scope</i>
	</b><br/> 
	<c:forEach items="${requestScope}" varStatus="status" var="parameter"> 
		<c:out value="${parameter}"/><br/><br/>
	</c:forEach>
<b><i>Page Scope</i>
	</b><br/> 
	<c:forEach items="${pageScope}" varStatus="status" var="parameter"> 
		<c:out value="${parameter}"/><br/><br/>
	</c:forEach>
				
