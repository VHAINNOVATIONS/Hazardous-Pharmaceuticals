<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<div class="panel">
    <fieldset><legend class="legend">High Risk / Hazardous Administration Data</legend>
    <div class="twoColumn">
        <peps:dataField key="patient.specific.label" cssClass="aligncheckbox225 labelreadonly" />
        <peps:dataField key="do.not.handle.if.pregnant" cssClass="aligncheckbox225 labelreadonly" />
        <peps:dataField key="witness.to.administration" cssClass="aligncheckbox225 labelreadonly" />   
        <peps:dataField key="follow.up.time" cssClass="aligncheckbox225 labelreadonly" />
    </div> 
    <div class="twoColumn">
        <peps:dataField key="high.risk" cssClass="aligncheckbox5 labelreadonly" />    
        <peps:dataField key="high.risk.followup" cssClass="aligncheckbox5 labelreadonly" />
        <peps:dataField key="high.risk.followup.time.period" />        
        <peps:dataField key="hazardous.to.dispose" cssClass="aligncheckbox5 labelreadonly" />
         <peps:dataField  key="primary.epa.code" />
      	<peps:dataField   key="waste.sort.code" />
      	<peps:dataField  key="dot.shipping.name" />
        <peps:dataField key="hazardous.to.handle" cssClass="aligncheckbox5 labelreadonly" />        
        <peps:dataField key="hazardous.to.patient" cssClass="aligncheckbox5 labelreadonly" />    
    </div>                        
    </fieldset>
    <div class="horizontalspacer"></div>
    <fieldset><legend class="legend">Lab / Vital Look-Up Data</legend>
<!--     <div class="twoColumn"> -->
<%--         <peps:dataField key="lab.display.order.entry" /> --%>
<%--         <peps:dataField key="lab.display.finishing.an.order" /> --%>
<%--         <peps:dataField key="lab.display.administration" /> --%>
<!--     </div>  -->
<!--     <div class="twoColumn"> -->
<%--         <peps:dataField key="vitals.display.order.entry"  cssStyle="width: 198px;" /> --%>
<%--         <peps:dataField key="vitals.display.finish.an.order"  cssStyle="width: 198px;" /> --%>
<%--         <peps:dataField key="vitals.display.administration"  cssStyle="width: 198px;" />    --%>
<!--     </div>  -->
    <div class="horizontalspacer"></div>
    <div class="twoColumn">
        <peps:dataField key="monitor.max.days" cssStyle="width: 212px;" />
    </div> 
    </fieldset>
</div>