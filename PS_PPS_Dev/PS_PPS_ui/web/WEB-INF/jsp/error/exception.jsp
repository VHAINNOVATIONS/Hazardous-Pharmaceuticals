<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>	    
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%@page import="org.apache.commons.lang.exception.ExceptionUtils"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>

<h1><spring:message code="exception.header" /></h1>
<p><spring:message code="exception.description" /></p>
<c:if test="${isSwRI}">
<%
            Exception exception = (Exception) request.getAttribute("exception");
            String localizedMessage = StringEscapeUtils.escapeHtml(exception.getLocalizedMessage());
            String stackTrace = StringEscapeUtils.escapeHtml(ExceptionUtils.getFullStackTrace(exception));            
%>
<p><i><spring:message code="exception.message" /></i></p>
<pre class="exception"><%=localizedMessage%></pre>
<p><i><spring:message code="exception.details" /></i></p>
<pre class="exception"><%=stackTrace%></pre>
</c:if>

