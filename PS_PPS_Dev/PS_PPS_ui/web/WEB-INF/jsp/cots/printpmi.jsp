<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>

 
<c:if test="${showPrintLink}">
    <div class="floatRight"><a href="javascript:window.print()"><spring:message code="print" /></a></div>
</c:if>
<h1><spring:message code="medication.instructions" /></h1>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.disclaimer}" /></div>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.title}" />&nbsp;<c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.phonetics}" /></div>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.brandName}" /></div>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.warning}" /></div>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.uses}" /></div>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.howToTake}" /></div>   
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.sideEffects}" /></div>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.medicalAlerts}" /></div>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.precautions}" /></div>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.drugInteractions}" /></div>   
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.overdose}" /></div>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.notes}" /></div>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.missedDose}" /></div>
<div class="instructionalText"><c:out value="${flowScope.drugReferenceBean.patientMedicationInstruction.storage}" /></div>