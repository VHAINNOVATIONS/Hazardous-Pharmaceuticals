<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<%@taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>    

<c:if test="${sessionScope.PPSNUserContext != null && sessionScope.PPSNUserContext.user != null}">
	<c:set value="${sessionScope.PPSNUserContext.user}" var="user"/>
</c:if>

<c:choose>
	<c:when test="${user != null}">
		<!-- Set a width less than the width of the app because the 100% width of the panel and fields causes scrollbars -->
		<div class="panel" style="width: 1250px;">
		<spring:message code="logged.in"/>
		<peps:text
		    key="first.name"
		    label="true" 
		    item="${user}" />
		<peps:text
		    key="last.name"
		    label="true"
		    item="${user}" />
		<peps:text
		    key="username"
		    label="true"
		    item="${user}" />
		<peps:text
		    key="location"
		    label="true"
		    item="${user}" />
		<peps:text
		    key="roles"
		    label="true"
		    item="${user}" />
		</div>
</c:when>
<c:otherwise>
	<div class="panel" style="width: 1250px;">
	<br /><spring:message code="logged.out"/><br /><br />
	<form:form id="login" action="/PRE/login.go" onsubmit="return disableSubmit(this.id);">
	    <table style="border:0px; padding: 0px;">
	        <tr>
	            <td style="align: right;"><peps:label key="username" />: </td><td><input name="user.username" type="text" class="textfield" ></td>
	        </tr>
	        <tr>
	            <td style="align: right;"><peps:label key="password" />: </td><td><input name="user.password" type="password" class="password" ></td>
	        </tr>
	    </table><br />
	    <peps:submit
	        key="button.login"
	        action="/PRE/login.go"
	        onclick="deleteAllCookies()"/>
	</form:form>
	</div>
</c:otherwise>
</c:choose>
