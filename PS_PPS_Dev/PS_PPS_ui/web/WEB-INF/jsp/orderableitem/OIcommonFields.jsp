<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib 
    prefix="form"
    uri="http://www.springframework.org/tags/form"%>     
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %> 
<%@ page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>

<% 
	ManagedItemVo managedItem = (ManagedItemVo)request.getAttribute("item"); 
	
%>
<peps:warnings id="warnings" warnings="${warnings}"/>
<br />

<peps:form id="OIWizard2">
	<h2>&nbsp;&nbsp;Confirming New OI Information</h2>
	<div class="panel">

        <peps:text item="${item}" key="oi.name" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="vista.oi.name" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="orderable.item.type" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="generic.name" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="dosage.form" label="true" cssClass="readonlynational" />
        <div class="rowspacer"></div>
       	<peps:text item="${item}" key="national.formulary.indicator" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="standard.med.route" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="categories" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="sub.categories" label="true" cssClass="readonlynational" />
        <div class="buttonspacer"></div>
        <peps:dataField item="${item}" key="psr.name" />
		<div class="buttonspacer"></div> 
		
        <span class="column">
            <c:url value="/${fn:toLowerCase(item.entityType)}/returnToAdd.go" var="returnUrl"/>
            <peps:submit key="button.return" action="${returnUrl}"/>
        </span>
        <span class="column">
            <c:url value="/${fn:toLowerCase(item.entityType)}/submitNewItem.go" var="submitUrl"/>
            <peps:submit key="button.accept.changes" action="${submitUrl}"/>
        </span>
        <span class="column">
            <c:url value="/${fn:toLowerCase(item.entityType)}/addMoreDetails.go" var="actionUrl"/>
            <peps:submit key="button.add.more.details" action="${actionUrl}"/>
        </span>
        
        <div class="rowspacer"></div>
        <div class="buttonspacer"></div>
    	
    	<span style="padding-left: 30px">
	    	<c:if test="${!flowScope.addingNewParent}">
				<c:url value="/${fn:toLowerCase(item.entityType)}/createBlankTemplate.go" var="createUrl"/>			        
		        <peps:submit key="button.accept.and.open.blank.product.template" action="${createUrl}" />
			</c:if>
		</span>
		
        
	</div>
</peps:form>