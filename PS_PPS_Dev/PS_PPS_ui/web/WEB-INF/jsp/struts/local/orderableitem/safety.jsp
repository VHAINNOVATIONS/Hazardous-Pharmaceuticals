<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<div class="panel">
    <div class="twoColumn">
    <fieldset style="width: 450px;"><legend class="legend">Precautions / Alerts</legend>
        <peps:label key="high.alert" />&nbsp; (new data field):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="textfield" id="textfield" class="textfield" /><br />
        <peps:label key="special.instructions" />&nbsp; (new data field):
        <textarea name="special.instructions" id="special.instructions" cols="23" rows="4" /></textarea>
    </fieldset> 
    <fieldset style="width: 450px;"><legend class="legend">Patient Specific</legend>
        <peps:dataField key="patient.instructions" />
        <peps:dataField key="other.language.instructions" />
        <peps:dataField key="lifetime.cumulative.dosage" />
    </fieldset>   
    </div>
    <div class="twoColumn">
    <fieldset style="width: 450px;"><legend class="legend">Guidelines</legend>
        <peps:dataField key="local.drug.texts" />
    </fieldset>  
    </div>
</div>