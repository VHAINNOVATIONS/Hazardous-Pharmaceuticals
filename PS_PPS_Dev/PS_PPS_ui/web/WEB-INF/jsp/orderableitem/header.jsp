<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>  
<%
ManagedItemVo item = (ManagedItemVo)request.getAttribute("item");
%>
	
<div class="panel">
		<div class="column">
 				<b><font size="3"><peps:text
		    	item="${item}" 
		        key="oi.name"
		        label="true" cssClass="editheader" /></font></b>
		</div>
		<br /><br />
		<div class="threeColumn2left">
		   
		        <c:choose>
		        <c:when test="${item.itemStatus.inactive}">
			        <span class="column">
			        <b><font color="red"><peps:text
			        item="${item}"
			        key="item.status"
			        label="true" cssClass="editheader" /></font></b></span><span class="column">(<peps:text
                    item="${item}" 
			        key="inactivation.date"
			        label="false" cssClass="producteditheader" />)</span>
		        </c:when>
		        <c:otherwise>
		        <span class="columnnopadding">
			        <peps:text
			        item="${item}"
			        key="item.status"
			        label="true" cssClass="editheader" /></span>
		        </c:otherwise>
		        </c:choose> 
		         <br /><br />
		        <c:choose>
					<c:when test="<%=item.getVaDataFields().getDataField(FieldKey.PATIENT_INSTRUCTIONS).getValue() != null%>">
					<span class="column">
						<peps:label 
					        key="oi.safety.indicator" />:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="4" color="red">YES</font></span>
				   	</c:when>
				  	<c:otherwise>
				  	<span class="column">
					  	<peps:label 
					        key="oi.safety.indicator" />:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="4" color="black">NO</font></span>
				   	</c:otherwise>
			    </c:choose>
		</div>
		<div class="threeColumn">
		    <peps:text 
		    	item="${item}"
		        key="dosage.form"
		        label="true"  cssClass="editheader"/> 
		</div>
	    <div class="horizontalspacer"></div>

	    
	    <c:set
	        scope="page"
	        var="itemTypeString"
	        value="<%=item.getEntityType().toFieldKey() %>" />
	    <div class="panel">
	    <center><b><font size="3">

	    
	    <c:if test="${mainRequest.requestType.addition}">
	        <peps:label key="${itemTypeString}" /> Addition Request
	    </c:if>
	    <c:if test="${mainRequest.requestType.modification}">
	        <peps:label key="${itemTypeString}" /> Modification Request
	    </c:if>
	    
	    </font></b></center>
</div>
<div class="horizontalspacer"></div>