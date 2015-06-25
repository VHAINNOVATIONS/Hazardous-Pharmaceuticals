<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>

<peps:form id="ProductWizard14">
<peps:model ognlPrefix="item">
<div class="topspacer"></div>
    <div class="clearLeft">
    <fieldset><legend>VA Data Fields (Page 4 of 9)</legend>

    <div class="twocolumn">
        <peps:dataField key="national.formulary.name"  cssClass="formulary" />
        <peps:dataField key="local.non.formulary"  cssClass="aligncheckbox1" />
        <peps:dataField key="national.formulary.indicator" cssClass="aligncheckbox1" /> 
        <peps:dataField key="formulary" cssClass="formulary" cssClass="formularyalt" /> 
        <peps:dataField key="formulary.alternative" cssClass="formularyalt" />
        <peps:dataField key="lab.test.monitor" cssClass="formularyalt" />
        <peps:dataField key="monitor.routine" />
        <peps:dataField key="lab.monitor.mark" cssClass="aligncheckbox1" />
        <peps:dataField key="service.code" />
        <peps:dataField key="ien" />
        <peps:dataField key="override.df.dose.chk.exclusn" />
        <peps:dataField key="local.drug.texts" />
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
