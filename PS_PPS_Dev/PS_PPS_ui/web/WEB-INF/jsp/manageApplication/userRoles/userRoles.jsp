<%@ taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<%@ taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib 
    prefix="pps" 
    tagdir="/WEB-INF/tags/pps"%>
    
<form:form commandName="userRolesBean">
<fieldset class="userRoles">
    <legend>
        <spring:message code="user.roles.legend" />
    </legend>
	<table class="pepsTable userRoles" rules="cols" frame="box">
	    <thead>
	        <tr>
	            <th scope="col"><spring:message code="user.roles.username"/></th>
	            <th scope="col"><spring:message code="user.roles.duz"/></th>
	            <th scope="col"><spring:message code="user.roles.location"/></th>
	            <th scope="col"><spring:message code="Role.PSS_PPSN_MIGRATOR"/></th>
	            <th scope="col"><spring:message code="Role.PSS_PPSN_SECOND_APPROVER"/></th>
	            <th scope="col"><spring:message code="Role.PSS_PPSN_MANAGER"/></th>
	            <th scope="col"><spring:message code="Role.PSS_PPSN_SUPERVISOR"/></th>
	        </tr>
	    </thead>
	    <tbody>
	        <c:forEach var="user" items="${users}" varStatus="status">
	            <c:choose>
	                <c:when test="${status.index mod 2 eq 0}">
	                    <c:set var="oddEven" value="even" />
	                </c:when>
	                <c:otherwise>
	                    <c:set var="oddEven" value="odd" />
	                </c:otherwise>
	            </c:choose>
	            <tr class="userRole ${oddEven}">
	                <td class="userRoleUsername">${user.username}</td>
	                <td class="userRoleDUZ">${user.duz}</td>
	                <td class="userRoleLocation">${user.location}</td>
	                <td class="userRole userRoleMig">
	                    <c:if test="${user.roleMigFlag}">
	                        <form:checkbox path="userRoles[${user.id}]['PSS_PPSN_MIGRATOR']" /></td>
	                    </c:if>
	                <td class="userRole userRolePsr">
	                    <c:if test="${user.rolePsrFlag}">
	                        <form:checkbox path="userRoles[${user.id}]['PSS_PPSN_SECOND_APPROVER']" /></td>
	                    </c:if>
	                </td>
	                <td class="userRole userRolePnm">
	                    <c:if test="${user.rolePnmFlag}">
	                        <form:checkbox path="userRoles[${user.id}]['PSS_PPSN_MANAGER']" /></td>
	                    </c:if>
	                </td>
	                <td class="userRole userRolePns">
	                    <c:if test="${user.rolePnsFlag}">
	                        <form:checkbox path="userRoles[${user.id}]['PSS_PPSN_SUPERVISOR']" /></td>
	                    </c:if>
	                </td>
	            </tr>
	        </c:forEach>
	    </tbody>
	</table>
</fieldset>
<div class="panel">
    <div class="leftRight">
        <span class="column">
            <input id="button.save.button" class="button" title="Submit" value="Submit" type=submit />
        </span>
        <span class="column">
            <pps:cancelButton id="button.cancel.button" class="button" title="Cancel">Cancel</pps:cancelButton>
        </span>
    </div>
</div>
</form:form>