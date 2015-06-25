<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>

<peps:form id="ProductWizard18">
<peps:model ognlPrefix="item">
<div class="topspacer"></div>
<div class="clearLeft">
<fieldset><legend>Additional Data Fields (Page 9 of 9)</legend>
<div class="twoColumn">
    <peps:dataField key="current.inventory" />
    <peps:dataField key="day.nd.or.dose.nl.limit" />
    <peps:dataField key="message" />
    <peps:dataField key="monitor.max.days" />
    <peps:dataField key="normal.amount.to.order" /> 
    <peps:dataField key="refrigeration" />
    <peps:dataField key="default.mail.service" />      
</div>

<div class="twoColumn">   
    <peps:dataField key="protect.from.light" />
    <peps:dataField key="do.not.handle.if.pregnant" />
    <peps:dataField key="hazardous.to.dispose" />
    <peps:dataField key="primary.epa.code" />
      <peps:dataField key="waste.sort.code" />
      <peps:dataField key="dot.shipping.name" />
    <peps:dataField key="hazardous.to.handle" />
    <peps:dataField key="hazardous.to.patient" />
    <peps:dataField key="long.term.out.of.stock" />
    <peps:dataField key="low.safety.margin" />
    <peps:dataField key="non.renewable" />
</div>

<div>    <peps:dataField key="daw.code" />     </div>

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
        </span>
    </div>
</peps:model>
</peps:form>
<div id="partial.save.flag" class="hidden"><s:property value="item.partialSave" /></div>