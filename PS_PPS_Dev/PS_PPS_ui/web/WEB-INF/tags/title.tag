<%@ tag
    language="java"
    body-content="empty"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>

<spring:message code="title.peps" var="titlePeps"/>
<c:out value="${titlePeps}"/> - <c:out value="${sessionScope['scopedTarget.pageTrail'].currentPageTitle}"/>
