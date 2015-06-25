<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<s:set
    scope="page"
    name="itemId"
    value="item.id" />
<s:set
    scope="page"
    name="itemTypeString"
    value="itemType.toFieldKey()" />
    
<H1>Add <peps:label key="${itemTypeString}"/></H1>

<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>

<peps:form id="dosageForm">
    <peps:model ognlPrefix="item">
    <div class="twoColumn">
        <peps:dataField key="dosage.form.name" />
        <peps:dataField key="exclude.from.dosage.chks" />
        <peps:dataField key="verb" />
        <peps:dataField key="other.language.verb" />
        <peps:dataField key="preposition" />
        <peps:dataField key="other.language.preposition" />
        <peps:dataField key="conjunction" />
        <peps:dataField key="local.med.routes" />
    </div>
    <div class="twoColumn">
        <peps:dataField key="df.dispense.units.per.dose" />
      
    </div>
    <div class="horizontalspacer panel"></div>
    <div class="panel">
         <peps:dataField key="df.units" />
        <peps:dataField key="df.nouns" />
    </div>
    </peps:model>

    <br />
    <tiles:insertAttribute name="buttons" />
</peps:form>