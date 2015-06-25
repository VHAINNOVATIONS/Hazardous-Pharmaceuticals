<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
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
<peps:model ognlPrefix="item">
    <div class="threeColumn"><b><font size="3"><peps:text
        key="ndc"
        label="true" cssClass="ndceditheader" /></font></b>    
        <s:set
        scope="page"
        name="productId"
        value="item.product.id" />
        <%--
	        <c:set
	        	scope="page"
	        	var="productId"
	        	value="${item.product.id}" />
         --%>
        
        <s:set scope="page" name="productLinkAltText" value="getText('click.to.edit.field') + ' ' + getText('PRODUCT')" />
       
        <peps:redirectFlowLink
	        id="productParentLink"
	        flowId="manageItem2Flow"
	        key="va.product.name"
	        label="true"
	        tooltip="${productLinkAltText}"
	        itemId="${productId}"
	        itemType="PRODUCT" cssClass="ndceditheader" />
        <peps:text
	        key="va.print.name"
	        label="true" cssClass="ndceditheader" />
        <peps:text
	        key="dispense.unit"
	        label="true" cssClass="ndceditheader" />
    </div>
    <div class="fourColumn">
    <peps:text
        key="trade.name"
        label="true" cssClass="producteditheader" />
        <peps:text
        key="manufacturer"
        label="true" cssClass="producteditheader" />
        <s:if test="item.itemStatus.inactive">
	        <span class="columnnopadding">
	        <peps:text
	        	key="item.status"
	        	label="true" cssClass="producteditheader redText boldText" dataCssClass="redText boldText" /></span><span class="column">(<peps:text 
	        	key="inactivation.date"
	        	label="false" cssClass="producteditheader" />)</span>
	     </s:if>
	     <s:else>
	        <peps:text
	        	key="item.status"
	        	label="true" cssClass="producteditheader" />
        </s:else> 
        <%--
        <c:choose>
        	<c:when test="item.itemStatus.inactive">
	        <span class="columnnopadding">
	        	<peps:text
	        		key="item.status"
	        		label="true" cssClass="producteditheader redText boldText" dataCssClass="redText boldText" /></span><span class="column">(<peps:text 
	        		key="inactivation.date"
	        		label="false" cssClass="producteditheader" />)</span>
	     	</c:when>
	     	<c:otherwise>
	        	<peps:text
	        		key="item.status"
	        		label="true" cssClass="producteditheader" />
        	</c:otherwise>
        </c:choose>  
        --%>
    </div>
    <div class="threeColumn"><center><peps:dataField key="image" labelPosition="none" /></center>
    </div>
        <div class="horizontalspacer"></div>
    <s:set
        scope="page"
        name="itemTypeString"
        value="itemType.toFieldKey()" />
    <%--
    <c:set
        scope="page"
        var="itemTypeString"
        value="itemType.toFieldKey()" />
     --%>
    <div class="panel">
    <center><b><font size="3">
    <s:if test="%{mainRequest.requestType.addition}">
        <peps:label key="${itemTypeString}" /> Addition Request
    </s:if>
    <s:if test="%{mainRequest.requestType.modification}">
        <peps:label key="${itemTypeString}" /> Modification Request
    </s:if>
    <%--
    <c:if test="%{mainRequest.requestType.addition}">
        <peps:label key="${itemTypeString}" /> Addition Request
    </c:if>
    <c:if test="%{mainRequest.requestType.modification}">
        <peps:label key="${itemTypeString}" /> Modification Request
    </c:if>
     --%>
    </font></b></center>
</peps:model>
</div>