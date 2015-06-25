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

<peps:form id="standardMedRouteForm">
    <peps:model ognlPrefix="item">
        <peps:dataField key="value" />
        <peps:dataField
            key="first.databank.med.route" />
        <peps:dataField
            key="replacement.standard.med.route" />
    </peps:model>

    <br />
    <tiles:insertAttribute name="buttons" />
</peps:form>