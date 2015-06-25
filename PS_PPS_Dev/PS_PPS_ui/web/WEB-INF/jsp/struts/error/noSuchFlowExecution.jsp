<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>   

<%-- <h1><s:text name="noSuchFlowExecutionException.header" /></h1> --%>
<h1><spring:message code="noSuchFlowExecutionException.header" /></h1>
<%-- <p><s:text name="noSuchFlowExecutionException.description" /></p> --%>
<p><spring:message code="noSuchFlowExecutionException.description" /></p>