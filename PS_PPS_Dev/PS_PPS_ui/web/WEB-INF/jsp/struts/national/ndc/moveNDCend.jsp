<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
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
<div> 
<h1 style="text-align: left; padding-top: 15px;">Updates were successful.</h1>
<h2 style="text-align: left; padding-top: 5px; font-size: 15px;"> The results of the modifications are displayed below.</h2>
</div>
<div style="padding-top: 15px;">
<strong>Product: <s:text name="item.vaProductName" /> <%-- <spring:message code="item.vaProductName" />--%></strong>
<fieldset>
<legend>NDCs
</legend>
<s:if test="item.ndcs.size() < 1">
<%-- <c:if test="item.ndcs.size() < 1"> --%>

    <div>No NDCs</div>
    
</s:if>
<s:iterator value="item.ndcs" status="rowstatus">
     <div><s:property value="ndc" /></div>
</s:iterator>
<%-- </c:if>
<c:forEach items="item.ndcs" varStatus="rowstatus">
     <div><c:out value="ndc" /></div>
</c:forEach>--%>

</fieldset>
</div>

<div style="padding-top: 15px;">
<strong>Product: <s:text name="item2.vaProductName" /><%-- <spring:message code="item2.vaProductName" />--%></strong>
<fieldset>
<legend>NDCs
</legend>
<s:if test="item2.ndcs.size() < 1">
<%-- <c:if test="item2.ndcs.size() < 1"> --%>
    <div>No NDCs</div>

</s:if>
<s:iterator value="item2.ndcs" status="rowstatus">
     <div><s:property value="ndc" /></div>
</s:iterator>
<%-- <c:if test="item2.ndcs.size() < 1">
    <div>No NDCs</div>
</c:if>
<c:forEach items="item2.ndcs" varStatus="rowstatus">
     <div><c:out value="ndc" /></div>
</c:forEach>--%>
</fieldset>
</div>