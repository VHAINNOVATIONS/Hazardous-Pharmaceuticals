<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
    prefix="spring"
    uri="http://www.springframework.org/tags"%> 
<%@ taglib 
    prefix="form"
    uri="http://www.springframework.org/tags/form"%> 
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>

<peps:warnings id="warnings" warnings="${warnings}"/>    
<br />

<peps:form id="ProductWizard2" > 
	<h2>&nbsp;&nbsp;Confirming New Product Information</h2>
	<div class="panel">
        
        <peps:text item="${item}"  key="va.product.name" label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="va.print.name" label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="dispense.unit" label="true" cssClass="confirm"  />
        <peps:text item="${item}"  key="dosage.form" label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="generic.name" label="true" cssClass="confirm"  />
        <div class="rowspacer"></div>
        <peps:text item="${item}"  key="product.strength" label="true" cssClass="confirm"  />
        <peps:text item="${item}"  key="product.unit" label="true" cssClass="confirm"  />
        <peps:text item="${item}"  key="oi.name" label="true" cssClass="confirm"  />
        <peps:text item="${item}"  key="active.ingredients" label="true" cssClass="confirm" />
        <div class="rowspacer"></div>
        <peps:text item="${item}"  key="drug.classes" label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="cs.fed.schedule" label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="single.multi.source.product" label="true" cssClass="confirm" /> 
        <peps:text item="${item}"  key="gcn.sequence.number" label="true" cssClass="confirm"  />
        <peps:text item="${item}"  key="dea.schedule" label="true" cssClass="confirm" />
        <div class="rowspacer"></div>
        <peps:text item="${item}"  key="ncpdp.dispense.unit" label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="ncpdp.quantity.multiplier" label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="categories" label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="special.handlings" label="true" cssClass="confirm" /> 
        <peps:text item="${item}"  key="national.formulary.indicator" label="true" cssClass="confirm" />
        <div class="rowspacer"></div>
        <peps:text item="${item}"  key="national.formulary.name" label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="cmop.id" label="true" cssClass="confirm" /> 
        <peps:text item="${item}"  key="cmop.dispense"  label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="fda.med.guide" label="true" cssClass="confirm" /> 
        <div class="rowspacer"></div>
        <peps:text item="${item}"  key="create.possible.dosage"  label="true" cssClass="confirm" /> 
        <peps:text item="${item}"  key="possible.dosages.auto.create" label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="product.package" label="true" cssClass="confirm" />
        <peps:text item="${item}"  key="exclude.drg.drg.interaction.check" label="true" cssClass="confirm" /> 
        <peps:text item="${item}"  key="override.df.dose.chk.exclusn" label="true" cssClass="confirm" /> 
        <peps:text item="${item}"  key="reduced.copay" label="true" cssClass="confirm" />  
        <div class="rowspacer"></div>   
        <peps:dataField item="${item}" key="psr.name" cssClass="confirm" />
		<div class="rowspacer"></div> 
		
        <span class="column">
            <c:url value="/${fn:toLowerCase(item.entityType)}/returnToAdd.go" var="returnUrl"/>
            <peps:submit key="button.return" action="${returnUrl}"/>
        </span>
        <c:if test="${flowScope.multipleSelectItemBean eq null}">
        <span class="column">
            <c:url value="/${fn:toLowerCase(item.entityType)}/submitNewItem.go" var="submitUrl"/>
            <peps:submit key="button.accept.changes" action="${submitUrl}"/>
        </span>
        </c:if>
        <c:if test="${flowScope.multipleSelectItemBean eq null}">
	        <span class="column">
	            <c:url value="/${fn:toLowerCase(item.entityType)}/addMoreDetails.go" var="actionUrl"/>
	            <peps:submit key="button.add.more.details" action="${actionUrl}"/>
	        </span>
        </c:if>
    	<c:if test="${flowScope.multipleSelectItemBean eq null}">
	    	<div class="rowspacer"></div>
	    	<div class="rowspacer"></div>
	    	<span style="padding-left: 28px">
		    	<c:if test="${!flowScope.addingNewParent && !flowScope.fdbAddKey}">
					<c:url value="/${fn:toLowerCase(item.entityType)}/createBlankTemplate.go" var="createUrl"/>
			        <peps:submit key="button.open.blank.ndc.template.confirm" action="${createUrl}" cssClass="wideButton4" />
		        </c:if>
	        </span>
        </c:if>
       	<c:if test="${flowScope.multipleSelectItemBean ne null and fn:length(flowScope.multipleSelectItemBean.itemIds) > 0}">
        	<div class="rowspacer"></div>
        	<div class="rowspacer"></div>
        	<span style="padding-left: 28px">
	       		<c:url value="/${fn:toLowerCase(item.entityType)}/saveProductTemplate.go" var="createUrl"/>
		       	<peps:submit key="button.open.blank.ndc.template.confirm2" action="${createUrl}" cssClass="wideButton4" />
			</span>
		</c:if>
	</div>
</peps:form>