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

<peps:form id="localMedRouteForm">
    <peps:model ognlPrefix="item">
        <peps:dataField key="value" />
        <peps:dataField key="abbreviation" />
        <peps:dataField key="iv.flag" cssClass="aligncheckbox214 labelreadonly" />
        <peps:dataField key="prompt.for.injection.site" cssClass="aligncheckbox214 labelreadonly" />
        <peps:dataField key="display.on.ivp" cssClass="aligncheckbox214 labelreadonly" />
        <peps:dataField key="outpatient.expansion" />
        <peps:dataField key="other.language.expansion" />
        <peps:dataField key="package.usages" cssClass="itemnumber" />
        <peps:dataField key="standard.med.route" />
    </peps:model>

    <br />
    <tiles:insertAttribute name="buttons" />
</peps:form>