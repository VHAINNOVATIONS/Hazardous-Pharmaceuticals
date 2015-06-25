<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %> 
	
<peps:form id="ProductWizard5">
	<peps:model ognlPrefix="item">
		<div class="topspacer"></div>
	    <div class="clearLeft">
	    <fieldset><legend>Dispensing Data Fields (Page 6 of 10)</legend>
	    <div class="twoColumn">
	        <peps:dataField key="cmop.dispense" cssClass="aligncheckbox1" />
	        <peps:dataField key="op.external.dispense" cssClass="aligncheckbox1" />
	        <peps:dataField key="product.price.per.order.unit" />
	        <peps:dataField key="product.price.per.dispense.unit" />
	        <peps:dataField key="approved.for.splitting" />
	        <peps:dataField key="product.dispense.unit.per.ord" />
	        <peps:dataField key="dispense.days.supply.limit" />
	        <peps:dataField key="dispense.limit.for.order" />
	        <peps:dataField key="dispense.limit.for.schedule" />
	    </div>
	    <div class="twoColumn">
	        <peps:dataField key="max.dispense.limit" />
	        <peps:dataField key="dispense.override" />
	        <peps:dataField key="dispense.override.reason" rows="2" cols="10" />
	        <peps:dataField key="override.reason.enterer" />
	        <peps:dataField key="total.dispense.quantity" />
	        <peps:dataField key="dispense.quantity.override" />
	        <peps:dataField key="dispense.quantity.override.reason" rows="2" cols="10" />
	    </div>
	    </fieldset>
	    </div>
	    
	    <div class="buttonspacer"></div>
	
	    <div class="twoColumn">
	        <peps:dataField key="dispense.quantity.limit" />
	    </div>
	
	    <div class="buttonspacer"></div>
	
	    <div id="buttons" class="panel">
	        <span class="floatRight">
	        <s:if test="%{getConversationScopeAttribute('templateSubFlow')}">
	            <span class="column"><peps:submit
	                key="button.cancel"
	                event="cancel" /></span>
	        </s:if>
	        <s:else>
	            <span class="column"><peps:cancel /></span>
	        </s:else>
	        
	        <%--
	        <c:choose>
		        <c:when test="%{getConversationScopeAttribute('templateSubFlow')}">
		            <span class="column"><peps:submit
		                key="button.cancel"
		                event="cancel" /></span>
		        </c:when>
		        <c:otherwise>
		            <span class="column"><peps:cancel /></span>
		        </c:otherwise>
		     </c:choose>
	         --%>
	        <span class="column">
	            <peps:submit
	                key="button.bookmark"
	                event="savePartial" />
	        </span>        
	        <span class="column">
	            <peps:submit
	                key="button.previous" 
	                event="previous" />
	        </span>       
	        <span class="column">
	            <peps:submit
	                key="button.submit" 
	                event="submit" />
	        </span>
	        <span class="column">
	            <peps:submit
	                key="button.next"
	                event="next" />
	        </span>
	        </span>
	    </div>
	    
	</peps:model>
</peps:form>
<div id="partial.save.flag" class="hidden"><s:property value="item.partialSave" /><%--<c:out value="${item.partialSave}" /> --%></div>
