<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>

<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>     
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %> 
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
	
<div class="panel">
    <div class="threeColumn"><b><font size="3">
    
    <peps:text
    	item="${item}"
        key="ndc"
        label="true" cssClass="ndceditheader" /></font></b>    
        
      <c:set
      	scope="page"
      	var="productId"
      	value="${item.product.id}" />
        
       
       <spring:message scope="page" var="productLinkAltText" code="click.to.edit.field" />
       
       <c:set 
       		scope="page" 
       		var="productLinkAltText2" 
       		value="<%=gov.va.med.pharmacy.peps.common.vo.EntityType.PRODUCT%>" />
      
        <peps:link
        	item= "${item}"
	        id="productParentLink"
	        event="edit"
	        key="va.product.name"
	        label="true"
	        tooltip="${productLinkAltText} ${productLinkAltText2}"
	        itemId="${productId}"
	        itemType="PRODUCT" cssClass="ndceditheader" />
        <peps:text
        	item="${item}"
	        key="va.print.name"
	        label="true" cssClass="ndceditheader" />
        <peps:text
       		item="${item}"         
	        key="dispense.unit"
	        label="true" cssClass="ndceditheader" />
    </div>
    <div class="eightColumn">&nbsp;</div>
    <div class="fourColumn">
    <peps:text
       	item="${item}"    	
        key="trade.name"
        label="true" cssClass="ndceditheader" />
        
    <peps:text
       	item="${item}"     
        key="manufacturer"
        label="true" cssClass="ndceditheader" />
                
        <c:choose>
        	<c:when test="item.itemStatus.inactive">
	        <span class="columnnopadding">
	        	<peps:text
        	       	item="${item}" 
	        		key="item.status"
	        		label="true" cssClass="ndceditheader redText boldText" dataCssClass="redText boldText" /></span><span class="column">(
	        		<peps:text 
       		       	item="${item}" 
	        		key="inactivation.date"
	        		label="false" cssClass="ndceditheader" />)</span>
	     	</c:when>
	     	<c:otherwise>
	        	<peps:text
        	       	item="${item}" 
	        		key="item.status"
	        		label="true" cssClass="ndceditheader" />
        	</c:otherwise>
        </c:choose>  
       
    </div>
    <div class="fourColumn"><center><peps:dataField item="${item}" key="image" labelPosition="none" /></center>
    </div>
        <div class="horizontalspacer"></div>
    
    <c:set
        scope="page"
        var="itemTypeString"
        value="${item.entityType.fieldKey}" />
    
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