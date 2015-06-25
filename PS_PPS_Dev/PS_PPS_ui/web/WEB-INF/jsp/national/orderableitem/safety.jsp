<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<div class="panel" style="height: 380px;" >
    <div class="column">
    <fieldset style="width: 1105px;"><legend class="legend">Precautions / Alerts</legend>
        <peps:dataField item="${item}" key="high.alert" cssClass="maxlen100" maxlength="120"/>
        <peps:dataField item="${item}" key="special.instructions" cssClass="maxlen100" maxlength="120"/>
    </fieldset> 
    </div>

    <div class="twoColumn">
    <fieldset style="width: 500px;"><legend class="legend">Guidelines</legend>
        <peps:dataField item="${item}" key="national.drug.texts" />
    </fieldset>
    </div>
    
    <div class="twoColumn">
    <fieldset style="width: 500px;"><legend class="legend">Patient Specific</legend>
        <peps:dataField item="${item}" key="patient.instructions" />
        <peps:dataField item="${item}" key="other.language.instructions" />
        <peps:dataField item="${item}" key="lifetime.cumulative.dosage"  cssClass="aligncheckbox2" />
    </fieldset>   
    </div>
</div>