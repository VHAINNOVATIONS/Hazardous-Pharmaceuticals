<%@ tag language="java" display-name="linkButton" pageEncoding="UTF-8" dynamic-attributes="dynamicAttributes" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="dynamicAttributeMap" type="java.util.Map"%>

<c:if test="${dynamicAttributeMap ne null}">
    <c:set var="dynamicAttributes" value="${dynamicAttributeMap}" />
</c:if>

<c:set var="onclick">window.location = '${url}';</c:set>

<c:forEach items="${dynamicAttributes}" var="dynamicAttribute"> 
    <c:if test="${dynamicAttribute.key eq 'onClick'}">
        <c:set var="onclick">${dynamicAttribute.value} ${onclick}</c:set>
    </c:if>
    <c:set var="dynamicAttributesString">${dynamicAttributesString} ${dynamicAttribute.key}="${dynamicAttribute.value}"</c:set>
</c:forEach>
<button type="button" ${dynamicAttributesString} onclick="${onclick}">
    <jsp:doBody />
</button>