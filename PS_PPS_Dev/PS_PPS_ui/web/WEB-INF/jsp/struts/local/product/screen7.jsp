<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>

<peps:form id="ProductWizard17">
<peps:model ognlPrefix="item">
<div class="topspacer"></div>
<div class="clearLeft">
<fieldset><legend>Additional Data Fields (Page 7 of 9)</legend>
<div class="twoColumn">
    <peps:dataField key="ar.ws.amis.category" />
    <peps:dataField key="ar.ws.amis.conversion.number"  />
    <peps:dataField key="number.of.doses"  />
    <peps:dataField key="inpatient.medication.expired.order.max.time" />
    <peps:dataField key="inpatient.medication.expired.order.min.time" />
    <peps:dataField key="unit.dose.schedule" />
    <peps:dataField key="unit.dose.schedule.type" />
    <peps:dataField key="reorder.level" />
</div>
<div class="twoColumn">
    <peps:dataField key="do.not.mail" cssClass="aligncheckbox5" />
    <peps:dataField key="follow.up.time" cssClass="aligncheckbox5" />
    <peps:dataField key="high.risk" cssClass="aligncheckbox5" />
    <peps:dataField key="high.risk.followup" cssClass="aligncheckbox5" />
    <peps:dataField key="high.risk.followup.time.period"  />
    <peps:dataField key="gcn.sequence.number" /> 
</div>
<div class="panel">
    <div class="twoColumn">
    <fieldset style="width: 450px;" ><legend class="legend">Labs</legend>
        <peps:dataField key="lab.display.order.entry" />
        <peps:dataField key="lab.display.finishing.an.order" />
        <peps:dataField key="lab.display.administration" />
    </fieldset>  
    </div>
    <div class="twoColumn">
    <fieldset style="width: 450px;" ><legend class="legend">Vitals</legend>
        <peps:dataField key="vitals.display.order.entry" />
        <peps:dataField key="vitals.display.finish.an.order" />
        <peps:dataField key="vitals.display.administration" />        
    </fieldset>  
 </div>

</fieldset>
</div>
    <div class="buttonspacer"></div>
   
    <div id="buttons" class="panel">
        <span class="floatRight">
        <s:if test="%{getConversationScopeAttribute('templateSubFlow')}">
            <span class="column"><peps:submit
                key="button.cancel"
                event="cancel" /></span>
        </s:if>
        <s:else>
            <span class="column"><peps:cancel /></span>
        </s:else>
        <span class="column">
            <peps:submit
                key="button.bookmark"
                event="savePartial" />
        </span>        
        <span class="column">
            <peps:submit
                key="button.previous" 
                event="previous" />
        </span>       
        <span class="column">
            <peps:submit
                key="button.submit"
                event="submit" />
        </span>
        <span class="column">
            <peps:submit
                key="button.next"
                event="next" />
        </span>

        </span>
    </div>

</peps:model>
</peps:form>
<div id="partial.save.flag" class="hidden"><s:property value="item.partialSave" /></div>