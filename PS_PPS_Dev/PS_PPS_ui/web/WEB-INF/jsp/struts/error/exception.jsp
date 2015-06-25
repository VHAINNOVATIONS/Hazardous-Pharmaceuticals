<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
	    

<%@page import="org.apache.commons.lang.exception.ExceptionUtils"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%
            Exception exception = (Exception) request.getAttribute("exception");

            if (exception == null) {
                exception = OgnlUtility.findValue(request, "exception");
            }

            String localizedMessage = StringEscapeUtils.escapeHtml(exception.getLocalizedMessage());
            String stackTrace = StringEscapeUtils.escapeHtml(ExceptionUtils.getFullStackTrace(exception));            
%>
<h1><spring:message code="exception.header" /></h1>
<p><spring:message code="exception.description" /></p>
<p><i><spring:message code="exception.message" /></i></p>
<pre class="exception"><%=localizedMessage%></pre>
<p><i><spring:message code="exception.details" /></i></p>
<pre class="exception"><%=stackTrace%></pre>