<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%

%>	
<p><b><spring:message code="changes.committed" /></b></p> 
<c:if test="${hasWarnings}">
    <peps:warnings warnings="${warnings}" id="modSummaryWarnings" />
</c:if>
<peps:form id="confirmModifications">    
    <c:choose>
	    <c:when test="${hasWarnings}">
	        <peps:submit key="button.ignore" action="acknowledgeModifications.go" />
	    </c:when>
	    <c:otherwise>
	        <peps:submit key="button.ok" action="acknowledgeModifications.go" />
	    </c:otherwise>
	 </c:choose>
   
</peps:form>