<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>

<peps:form id="ProductWizard12a">
<peps:model ognlPrefix="item">
<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>
    <div class="clearLeft">
    <fieldset><legend>Required Fields (Page 2 of 9)</legend>
    <div class="panel">

        <peps:dataField key="cs.fed.schedule" jsOnChange="processChangedCsFedSchedule()" />        
        <peps:dataField key="categories" />
        <peps:dataField key="single.multi.source.product" /> 
        <peps:dataField key="approved.for.splitting" />
        <peps:dataField key="special.handlings" /> 
        <peps:dataField key="dea.schedule" />
        <peps:dataField key="local.print.name" />
        <peps:dataField key="ncpdp.dispense.unit" />
        <peps:dataField key="ncpdp.quantity.multiplier" />
        <peps:dataField key="warning.label" />
        
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
                key="button.next"
                event="next" />
        </span>
        </span>
    </div>
        
</peps:model>
</peps:form>      
<div id="partial.save.flag" class="hidden"><s:property value="item.partialSave" /></div>
