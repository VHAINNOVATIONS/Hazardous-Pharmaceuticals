<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<div class="panel">
    <div class="threeColumn2left">
    <fieldset style="width: 440px;"><legend class="legend">Setup</legend>
        <peps:dataField key="application.package.use" labelPosition="None" cssStyle="width: 218px; display: inline;" />
        <peps:dataField key="non.va.med" cssClass="aligncheckbox225" />
        <peps:dataField key="categories" cssStyle="width: 218px"/>
        <peps:text key="oi.iv.flag" label="true" cssClass="readonly"/>   
        <peps:dataField key="local.use" cssClass="aligncheckbox225" />     
    </fieldset> 
    </div>
    <div style="twoColumnwide">
    <fieldset style="width: 550px;"><legend class="legend">Schedule / Duration</legend>
        <peps:dataField key="oi.schedule.type" />
        <peps:dataField key="admin.schedules" cssStyle="width: 239px" />
        <peps:dataField key="day.nd.or.dose.nl.limit" cssStyle="width: 235px" />
        <peps:dataField key="oi.route" />
    </fieldset>   
    </div>
    <div class="horizontalspacer"></div>
    <div class="threeColumn2left">
    <fieldset style="width: 440px;"><legend class="legend">Formulary</legend>
        <peps:text key="national.formulary.indicator" label="true" cssClass="readonly"/>
        <peps:dataField key="formulary.status" cssStyle="width: 218px"/>
    </fieldset>  
    </div>
    <div style="twoColumnwide">
    <fieldset style="width: 550px;"><legend class="legend">OI Synonyms</legend>
                <peps:resumeFlowLink
                eventId="editField"
                fieldKey="orderable.item.synonym" 
                text="OI Synonyms"
                onclick="return popup(this.href, this.id, 280, 400, 260, 400);" 
                cssClass="linkpadding" />
    </fieldset>   
    </div>
</div>