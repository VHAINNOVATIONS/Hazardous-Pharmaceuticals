<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %> 
	
<peps:form id="ProductWizard5">

		<div class="topspacer"></div>
	    <div class="clearLeft">
	    <fieldset><legend>Dispensing Data Fields (Page 6 of 10)</legend>
	    <div class="twoColumn">
	        <peps:dataField key="cmop.dispense" cssClass="aligncheckbox1" item="${item}" />
	        <peps:dataField key="op.external.dispense" cssClass="aligncheckbox1" item="${item}" />
	        <peps:dataField key="product.price.per.order.unit" item="${item}" />
	        <peps:dataField key="product.price.per.dispense.unit" item="${item}" />
	        <peps:dataField key="approved.for.splitting" item="${item}" />
	        <peps:dataField key="product.dispense.unit.per.ord" item="${item}" />
	        <peps:dataField key="dispense.days.supply.limit" item="${item}" />
	        <peps:dataField key="dispense.limit.for.order" item="${item}" />
	        <peps:dataField key="dispense.limit.for.schedule" item="${item}" />
	    </div>
	    <div class="twoColumn">
	        <peps:dataField key="max.dispense.limit"item="${item}"  />
	        <peps:dataField key="dispense.override" item="${item}" />
	        <peps:dataField key="dispense.override.reason" rows="2" cols="10" item="${item}"/>
	        <peps:dataField key="override.reason.enterer" item="${item}" />
	        <peps:dataField key="total.dispense.quantity" item="${item}"/>
	        <peps:dataField key="dispense.quantity.override" item="${item}"/>
	        <peps:dataField key="dispense.quantity.override.reason" rows="2" cols="10" item="${item}"/>
	    </div>
	    </fieldset>
	    </div>
	    
	    <div class="buttonspacer"></div>
	
	    <div class="twoColumn">
	        <peps:dataField key="dispense.quantity.limit" item="${item}"/>
	    </div>
	
	    <div class="buttonspacer"></div>
	
	    <div id="buttons" class="panel">
	        <span class="floatRight">
	        
            <span class="column"><peps:submit
                key="button.cancel" /></span>
       
	        <span class="column">
	            <peps:submit
	                key="button.bookmark" />
	        </span>        
	        <span class="column">
	            <peps:submit
	                key="button.previous" />
	        </span>       
	        <span class="column">
	            <peps:submit
	                key="button.submit" />
	        </span>
	        <span class="column">
	            <peps:submit
	                key="button.next" />
	        </span>
	        </span>
	    </div>
	    
</peps:form>
<div id="partial.save.flag" class="hidden"><%-- <s:property value="item.partialSave" /> --%><c:out value="${item.partialSave}" /></div>