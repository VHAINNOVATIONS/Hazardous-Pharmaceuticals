<%@ tag language="java" display-name="cancelButton" pageEncoding="UTF-8" dynamic-attributes="dynamicAttributes" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="url" type="java.lang.String"%>
<%@ taglib prefix="pps" tagdir="/WEB-INF/tags/pps"%>

<c:set var="url" value="${sessionScope['scopedTarget.pageTrail'].previousUrl}" />
<c:if test="${url eq null}">
    <c:set var="url" value="${sessionScope['scopedTarget.pageTrail'].currentUrl}" />
</c:if>
<c:url value="${url}" var="cancelUrl"/>


<pps:linkButton url="${cancelUrl}" dynamicAttributeMap="${dynamicAttributes}">
    <jsp:doBody />
</pps:linkButton>