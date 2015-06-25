<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib 
	prefix="fn" 
	uri="http://java.sun.com/jsp/jstl/functions" %> 	
	
<div> 
<h1 style="text-align: left; padding-top: 15px;">Updates were successful.</h1>
<h2 style="text-align: left; padding-top: 5px; font-size: 15px;"> The results of the modifications are displayed below.</h2>
</div>
<div style="padding-top: 15px;">
<strong>Product:<spring:message code="item.vaProductName" /></strong>
<fieldset>
<legend>NDCs
</legend>

<c:if test="${fn:length(item.ndcs.size < 1)}">

    <div>No NDCs</div>
    
</c:if>

<c:forEach items="item.ndcs" varStatus="rowstatus">
     <div><c:out value="ndc" /></div>
</c:forEach>

</fieldset>
</div>

<div style="padding-top: 15px;">
<strong>Product: <spring:message code="item2.vaProductName" /></strong>
<fieldset>
<legend>NDCs
</legend>

<c:if test="${fn:length(item2.ndcs.size < 1)}">
    <div>No NDCs</div>

     <div><c:out value="ndc" /></div>
</c:if> 
    
<c:if test="${fn:length(item.ndcs.size < 1)}">
    <div>No NDCs</div>
</c:if>
<c:forEach items="item2.ndcs" varStatus="rowstatus">
     <div><c:out value="ndc" /></div>
</c:forEach>
</fieldset>
</div>