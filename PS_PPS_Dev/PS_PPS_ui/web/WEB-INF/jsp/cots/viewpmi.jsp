<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%> 
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
	
<div class="horizontalspacer"></div>
<h2 align="center"><spring:message code="patient.medication.information" /></h2>
<form:form id="retrievePmi" modelAttribute="drugReferenceBean" action="/PRE/pmi.go" method="GET" onsubmit="return disableSubmit(this.id);">
    <input type="hidden" id="isFirstRun" name="isFirstRun" value="false"/>
    <div style="width: 100%">
    <spring:message code="gcn.sequence.number.name" var="gcnSeqTxt"/>
    <spring:message code="spanish.text.name" var="spanishTxt"/>   
    <peps:controlheader 
        label="${gcnSeqTxt}"
        id="gcn.sequence.number"
        forattrib="gcn.sequence.number"/>    
    <form:input 
        path="gcnSequenceNumber"
        id="gcn.sequence.number"/>
    <peps:controlfooter 
        label="${gcnSeqTxt}"
        id="gcn.sequence.number"
        forattrib="gcn.sequence.number"/> 

    <peps:controlheader 
        label="${spanishTxt}"
        id="spanish.text"
        forattrib="spanish.text"/> 
    <form:select 
        path="languageChoice"
        items="${languageChoices}"
        id="spanish.text"
        />    
    <peps:controlfooter 
        label="${spanishTxt}"
        id="spanish.text"
        forattrib="spanish.text"/> 
    
    <peps:controlheader 
        label=""
        id="button.viewPmi"
        forattrib="button.viewPmi"/> 
    <peps:submit  key="button.viewPmi" />
     <peps:controlfooter 
        label=""
        id="button.viewPmi"
        forattrib="button.viewPmi"/> 
    
	</div>

</form:form>

<c:if test="${drugReferenceBean.patientMedicationInstruction != null}">
<div style="width: 100%">
    <div class="instructionalText">
    	<spring:message 
    		scope="page" 
    		var="printFriendlyVersion" 
    		code="print.friendly.version" />
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