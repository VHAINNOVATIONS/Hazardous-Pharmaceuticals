<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags" %> 
<%@taglib 
    prefix="fmt" 
    uri="http://java.sun.com/jsp/jstl/fmt" %>

 
<s:if test="flowScope.newState.contains('Popup')">
    <div class="floatRight"><a href="javascript:window.print()"><spring:message text="print" /></a></div>
</s:if>

<h1><s:text name="medication.instructions" /></h1>
<div class="instructionalText"><s:property value="patientMedicationInstruction.disclaimer" /></div>
<div class="instructionalText"><s:property value="patientMedicationInstruction.title" />&nbsp;<s:property value="patientMedicationInstruction.phonetics" /></div>
<div class="instructionalText"><s:property value="patientMedicationInstruction.brandName" /></div>
<div class="instructionalText"><s:property value="patientMedicationInstruction.warning" /></div>
<div class="instructionalText"><s:property value="patientMedicationInstruction.uses" /></div>
<div class="instructionalText"><s:property value="patientMedicationInstruction.howToTake" /></div>   
<div class="instructionalText"><s:property value="patientMedicationInstruction.sideEffects" /></div>
<div class="instructionalText"><s:property value="patientMedicationInstruction.medicalAlerts" /></div>
<div class="instructionalText"><s:property value="patientMedicationInstruction.precautions" /></div>
<div class="instructionalText"><s:property value="patientMedicationInstruction.drugInteractions" /></div>   
<div class="instructionalText"><s:property value="patientMedicationInstruction.overdose" /></div>
<div class="instructionalText"><s:property value="patientMedicationInstruction.notes" /></div>
<div class="instructionalText"><s:property value="patientMedicationInstruction.missedDose" /></div>
<div class="instructionalText"><s:property value="patientMedicationInstruction.storage" /></div>