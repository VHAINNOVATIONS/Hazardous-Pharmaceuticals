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

<peps:form id="medicationInstructionForm">
    <peps:model ognlPrefix="item">
        <peps:dataField key="value" />
        <peps:dataField key="intended.use" />
        <peps:dataField key="instructions" />
        <peps:dataField key="additional.instruction" />
        <peps:dataField key="med.instruction.schedule" />
        <peps:dataField key="med.instruction.expansion" />
        <peps:dataField key="frequency" /> 
        <peps:dataField key="default.admin.times" />
        <peps:dataField key="local.medication.route" />
        <peps:dataField key="med.instruction.synonym" />
        <peps:dataField key="other.language.expansion" />
        <peps:dataField key="plural" />
        <peps:dataField key="med.instruction.ward.multiple" />
    </peps:model>

    <br />
    <tiles:insertAttribute name="buttons" />
</peps:form>