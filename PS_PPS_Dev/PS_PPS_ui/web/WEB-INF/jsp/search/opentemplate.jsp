<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>

<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>    
    
<%
UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
boolean notReadOnly = userContext != null && userContext.getUser().isEitherManager();

if (notReadOnly) {
%>

<c:set
    scope="page"
	var="entityType"
	value="${searchCriteria.entityType}" />
<c:if test="${items != null}">
    <c:choose>
        <c:when test="${searchMode}">
            <c:set
			    scope="page"
			    var="entityType"
			    value="${searchTemplate.searchCriteria.entityType}" />
	    </c:when>
	    <c:otherwise>
	        <c:set
                scope="page"
                var="entityType"
                value="${searchCriteria.entityType}" />
	    </c:otherwise>
     </c:choose>
     <div style="text-align: center;">
	     <c:choose>
	         <c:when test="${entityType.entityType}">
	             <spring:message
	                 scope="page"
	                 var="linkText"
	                 code="button.open.blank.${entityType.fieldKey}.template" />
	         </c:when>
	         <c:otherwise>
	             <spring:message
	                 scope="page"
	                 var="linkText"
	                 code="button.open.blank.template" />
	         </c:otherwise>         
	     </c:choose>
	     <span style="padding: 0 10px 0 10px;">
		     <peps:link
		        text="${linkText}"
		        event="openBlankTemplate/add"
		        itemType="${entityType}"/>
	     </span>
     </div>
</c:if>

<% 
}   
%>