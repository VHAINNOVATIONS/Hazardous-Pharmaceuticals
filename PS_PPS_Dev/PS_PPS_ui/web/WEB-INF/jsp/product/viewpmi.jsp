<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%> 
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib 
	prefix="form"
	uri="http://www.springframework.org/tags/form"%> 
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>     	  
    
<h1><spring:message code="patient.medication.information" /></h1>   
<div class="row">
    <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/pmi.go" var="viewPmiUrl"/>
    <peps:dataField item="${item}" key="spanish.text" />
    <peps:submit key="button.viewPmi" action="${viewPmiUrl}" onclick="setCookie('retrievePMI', 3);"/>
       
    <span class="panel center">
	    
	
    </span>
    
</div>
<br />   
           
<c:if test="${flowScope.drugReferenceBean.patientMedicationInstruction != null && !empty flowScope.drugReferenceBean.patientMedicationInstruction}">
<div class="row" style="width: 100%">
    <div class="instructionalText">
    <spring:message var="printFriendlyVersion" scope="page" code="print.friendly.version"/>
    <peps:link
                id="pmiPopup"
                event="pmi"
                requestParameter="true" 
                isFirstRun="false"                                                        
                text="${printFriendlyVersion}"
                onclick="return popupWithOptions(this.href, 'print_pmi', 'resizable=yes,scrollbars=yes');" />
    </div>
<tiles:insertAttribute name="pmi" />
</div>
</c:if>