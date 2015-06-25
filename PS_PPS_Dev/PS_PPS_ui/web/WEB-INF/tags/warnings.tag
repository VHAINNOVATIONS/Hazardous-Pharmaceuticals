<%@ tag
    language="java"
    body-content="scriptless"%>
<%@taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
    <%@taglib 
    prefix="fn" 
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute
    name="id"
    required="true"
    type="java.lang.String"
    description="HTML ID attribute for form."%>
<%@ attribute
    name="name"
    required="false"
    type="java.lang.String"
    description="HTML Name attribute for form."%>
<%@ attribute
    name="warnings"
    required="true"
    type="java.util.List"
    description="The list of warnings on the page"
    rtexprvalue="true"%>
<c:if test="${warnings != null && !empty warnings}">
<div class="warnings" id="${id}">
	<span class="warningMessage">
	    <spring:message code="warnings"/>
	    <ul>
	        <c:forEach var="warning" items="${warnings}" varStatus="status">
	            <li id="warning_${status.index}" class="errorMessage3">${warning}</li>
	        </c:forEach>        
	    </ul>
	</span>
</div>
</c:if>