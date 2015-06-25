
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %> 
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>  
<div> 
<h1 style="text-align: left; padding-top: 15px;">Updates were successful.</h1>
<h2 style="text-align: left; padding-top: 5px; font-size: 15px;"> The results of the modifications are displayed below.</h2>
</div>
<div style="padding-top: 15px;">
<strong>Orderable Item: <s:text name="item.oiName" /><%-- <spring:message code="item.oiName" />--%></strong>
<fieldset>
<legend>Products
</legend>

<%--
<c:if test="item.products.size() < 1">
    <div>No Products</div>
</c:if>
--%>
<s:if test="item.products.size() &lt; 1">
    <div>No Products</div>
</s:if>

<%-- 
<c:forEach items="${item.products}" varStatus="rowstatus">
     <div><c:out value="${vaProductName}" /></div>
</c:forEach> 
--%>
<s:iterator value="item.products" status="rowstatus">
     <div><s:property value="vaProductName" /></div>
</s:iterator>
</fieldset>
</div>

<div style="padding-top: 15px;">
<strong>Orderable Item: <s:text name="item2.oiName" /><%-- <spring:message code="item2.oiName" />--%></strong>
<fieldset>
<legend>Products
</legend>

<%-- 
<c:if test="item2.products.size() < 1">
    <div>No Products</div>
</c:if>
<c:forEach items="item2.Products" varStatus="rowstatus">
     <div><c:out value="${vaProductName}" /></div>
</c:forEach> --%>
<s:if test="item2.products.size() &lt; 1">
    <div>No Products</div>
</s:if>
<s:iterator value="item2.Products" status="rowstatus">
     <div><s:property value="vaProductName" /></div>
</s:iterator>

</fieldset>
</div>