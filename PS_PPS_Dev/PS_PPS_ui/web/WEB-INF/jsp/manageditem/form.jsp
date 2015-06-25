<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<peps:form id="managedItemForm">
        <tiles:insertAttribute name="fields" />
        <tiles:insertAttribute name="buttons" />
</peps:form>