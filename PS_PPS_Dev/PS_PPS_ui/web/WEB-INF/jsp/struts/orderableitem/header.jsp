<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>  
	
<div class="panel">
	<peps:model ognlPrefix="item"> 
		<div class="threeColumn2left">
		    <b><font size="3"><peps:text 
		        key="oi.name"
		        label="true" cssClass="editheader" /></font></b>
		        
		        <s:if test="item.itemStatus.inactive">
		        <%-- 
		        <c:choose>
		        <c:when test="${item.itemStatus.inactive}"> --%>
			        <span class="columnnopadding"><peps:text
			        key="item.status"
			        label="true" cssClass="editheader redText boldText" dataCssClass="redText boldText" /></span><span class="column">(<peps:text 
			        key="inactivation.date"
			        label="false" cssClass="producteditheader" />)</span>
		        </s:if>
		        <%--</c:when> --%>
		        <s:else>
		        <%--<c:otherwise> --%>
			        <peps:text
			        key="item.status"
			        label="true" cssClass="editheader" />
		        </s:else>
		        <%--</c:otherwise>
		        <c:choose> --%> 
		        <span class="controlGroup">
			        <s:if test="item.dataFields['patient.instructions'].getValue() != null">
				        <peps:label 
				        key="oi.safety.indicator" />:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="4" color="red">YES</font>
			        </s:if>
			        <s:else>
				        <peps:label 
				        key="oi.safety.indicator" />:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="4" color="black">NO</font>
			        </s:else>
			        <%--
			        <c:choose>
				        <c:when test="item.dataFields['patient.instructions'].getValue() != null">
					        <peps:label 
					        key="oi.safety.indicator" />:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="4" color="red">YES</font>
				        </c:when>
				        <c:otherwise>
					        <peps:label 
					        key="oi.safety.indicator" />:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="4" color="black">NO</font>
				        </c:otherwise>
			        </c:choose>
			         --%>
		        </span>
		</div>
		<div class="fourColumn">
		    <peps:text 
		        key="dosage.form"
		        label="true" cssClass="producteditheader" /> 
		</div>
		<div class="fourColumn">
		    <peps:text 
		        key="local.use"
		        label="true" cssClass="producteditheader" /> 
		    <peps:text 
		        key="orderable.item.type"
		        label="true" cssClass="producteditheader" />
		   <peps:text
		        key="parent.name"
		        ognl="item.orderableItemParent.oiName"
		        label="true" 
		        cssClass="producteditheader" />
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
	        value="${itemType.toFieldKey()}" /> --%>
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
<div class="horizontalspacer"></div>