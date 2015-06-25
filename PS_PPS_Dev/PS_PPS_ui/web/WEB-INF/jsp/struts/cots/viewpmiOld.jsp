<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%> 
<h1><s:text name="patient.medication.information" /></h1>
<peps:form id="retrievePmi"> 
    <div class="column">   
    <peps:dataField key="gcn.sequence.number" />
    <peps:dataField key="spanish.text" />
    <span class="panel center"><peps:submit
            key="button.viewPmi"
            event="retrievePmi" /></span>
</div>
</peps:form>
<s:if test="patientMedicationInstruction != null">

<div style="width: 100%">
    <div class="instructionalText"><s:set name="printFriendlyVersion" scope="page" value="getText('print.friendly.version')"/>
    <peps:resumeFlowLink
                eventId="pmiPopup"
                text="${printFriendlyVersion}"
                onclick="return popupWithOptions(this.href, 'print_pmi', 'resizable=yes,scrollbars=yes');" /></div>
<tiles:insertAttribute name="pmi" />
</div>
</s:if>
