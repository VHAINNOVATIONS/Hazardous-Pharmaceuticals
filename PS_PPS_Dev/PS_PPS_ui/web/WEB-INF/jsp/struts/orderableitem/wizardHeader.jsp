<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib 
	prefix="peps" 
	tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %> 

<div align="center" class="addproducttitle">
    Add New Orderable Item
</div>
<peps:model ognlPrefix="item"> 
	<div class="twoColumn">
	    <peps:text 
	        key="oi.name"
	        label="true" cssClass="editheader" />
	    <peps:text 
	        key="request.item.status"
	        label="true" cssClass="editheader" /> 
	</div>
	<div class="twoColumn">
		<s:if test="%{environment.local and item.isLocal()}">
		    <div id="parentNameDiv" class="twoColumn">
		</s:if>
		<s:else>
		    <div id="parentNameDiv" class="twoColumn hidden">
		</s:else>
		<%--
		<c:choose>
			<c:when test="%{environment.local and item.isLocal()}">
			    <div id="parentNameDiv" class="twoColumn">
			</c:when>
			<c:otherwise>
			    <div id="parentNameDiv" class="twoColumn hidden">
			</c:otherwise>
		</c:choose>
		 --%>
		    <peps:text
		        key="parent.name"
		        ognl="item.orderableItemParent.oiName"
		        label="true" 
		        cssClass="editheader" />
		</div>
	</div>
</peps:model>