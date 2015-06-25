<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
	
<p><b><spring:message code="changes.committed" /></b></p>
 <%-- <s:text name="changes.committed" /></b></p> --%>
<peps:warnings />
<peps:form id="comfirmModifications">

	<s:if test="%{warnings != null && warnings.hasErrors()}">
        <peps:submit
            key="button.ignore"
            event="ok" />
    </s:if>
    <s:else>
        <peps:submit
            key="button.ok"
            event="ok" />
    </s:else>

    <%-- SPRING/JSTL 
    <c:choose>
	    <c:when test="%{warnings != null && warnings.hasErrors()}">
	        <peps:submit
	            key="button.ignore"
	            event="ok" />
	    </c:when>
	    <c:otherwise>
	        <peps:submit
	            key="button.ok"
	            event="ok" />
	    </c:otherwise>
	 </c:choose>
    --%>
</peps:form>