<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.Environment" %>
<%@ taglib 
	prefix="peps" 
	tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %> 
<%
ManagedItemVo item = (ManagedItemVo) request.getAttribute("item");
Environment environment = (Environment) request.getAttribute("environment");
pageContext.setAttribute("item", item);
pageContext.setAttribute("environment", environment);
%>
<div align="center" class="addproducttitle">
    Add New Orderable Item
</div>

	<div class="twoColumn">
	    <peps:text 
            item="${item}"
	        key="oi.name"
	        label="true" cssClass="editheader" />
	    <peps:text 
            item="${item}"
	        key="request.item.status"
	        label="true" cssClass="editheader" /> 
	</div>
	<div class="twoColumn">
<%-- 		<c:choose> --%>
<%-- 			<c:when test="${environment.local && item.local}"> --%>
<!-- 			    <div id="parentNameDiv" class="twoColumn"> -->
<%-- 			</c:when> --%>
<%-- 			<c:otherwise> --%>
			    <div id="parentNameDiv" class="twoColumn hidden">
<%-- 			</c:otherwise> --%>
<%-- 		</c:choose> --%>
		    <peps:text
                item="${item}"
		        key="parent.name"
		        label="true" 
		        cssClass="editheader" />
	   </div>
	   </div>

