<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%> 
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>	    
    
<h1><spring:message code="patient.medication.information" /></h1>   
<div class="column">
    <s:hidden name="gcnSequenceNumber" value="%{item.gcnSequenceNumber}" />
    <peps:dataField key="spanish.text" ognl="spanishText" />    
    <span class="panel center"><peps:submit
            key="button.viewPmi"
            event="retrievePmi" 
            onclick="setCookie('retrievePMI', 3);"/></span>
</div>
<c:if test="patientMedicationInstruction != null">
<div style="width: 100%">
    <div class="instructionalText"><s:set name="printFriendlyVersion" scope="page" value="getText('print.friendly.version')"/><peps:resumeFlowLink
                eventId="pmiPopup"
                text="${printFriendlyVersion}"
                onclick="return popupWithOptions(this.href, 'print_pmi', 'resizable=yes,scrollbars=yes');" /></div>
<tiles:insertAttribute name="pmi" />
</div>
</c:if>