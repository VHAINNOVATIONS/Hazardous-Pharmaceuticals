<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<div class="panel">
    <div class="twoColumn">
    <fieldset style="width: 500px;"><legend class="legend">Precautions / Alerts</legend>
        <peps:label key="high.alert" cssClass="readonlynational" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (new data field):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="textfield" id="textfield" class="textfield" /><br />
        <peps:label key="special.instructions" cssClass="readonlynational" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (new data field):
        <textarea name="special.instructions" id="special.instructions" cols="23" rows="4" /></textarea>
    </fieldset> 
    <fieldset style="width: 500px;"><legend class="legend">Patient Specific</legend>
        <peps:dataField key="patient.instructions" />
        <peps:dataField key="other.language.instructions" />
        <peps:dataField key="lifetime.cumulative.dosage" cssClass="aligncheckbox1" />
    </fieldset>   
    </div>
    <div class="twoColumn">
    <fieldset style="width: 500px;"><legend class="legend">Guidelines</legend>
        <peps:dataField key="national.drug.texts" />
    </fieldset>  
    </div>
</div>