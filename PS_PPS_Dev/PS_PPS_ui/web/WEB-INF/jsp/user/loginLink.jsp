<%@page import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>
<%@taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.PPSNUserContext != null && sessionScope.PPSNUserContext.user != null}">
	<c:set value="${sessionScope.PPSNUserContext.user}" var="user"/>
</c:if>

<c:choose>
	<c:when test="${user != null}">
		<spring:message code="welcome" />, 
		<peps:link cssClass="HeaderLinks" event="login" tooltip="View login information" text="${user.firstName}" tabindex="1"/>.
		<peps:link cssClass="HeaderLinks" event="logout"  text="Logout" onclick="cancelMigrationRefresh(); deleteAllCookies();" tabindex="1"/>.
		<br />	
		<peps:text key="roles" label="false" item="${user}" /> - <peps:text key="location" label="false" item="${user}" />		
	</c:when>
	<c:otherwise>
		<peps:link cssClass="HeaderLinks" event="login" text="Login" tabindex="1"/>
	</c:otherwise>
</c:choose>
