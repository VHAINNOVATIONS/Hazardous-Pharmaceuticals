<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<peps:form id="managedItemForm">
    <peps:model ognlPrefix="item">
        <tiles:insertAttribute name="fields" />
        <tiles:insertAttribute name="buttons" />
    </peps:model>
</peps:form>