<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>

<script
    src="/PRE/javascript/formDiff.js"
    type="text/javascript"></script>
<form
    id="consoleActionForm"
    method="post"
    action="/PRE/systemActions.go"
    onsubmit="selectAllTheOptions('selectedWarningLabels'); return disableSubmit(this.id);">
<c:set
    scope="page"
    var="itemId"
    value="${item.id}" />
<c:set
    scope="page"
    var="itemTypeString"
    value="${item.entityType.name" />

<div
    id="TabbedPanels1"
    class="TabbedPanels">

<ul class="TabbedPanelsTabGroup">
    <tiles:insertAttribute name="tabs.list" />
 
</ul>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Note: Fields marked with <font color="red">*</font> are required
<div
    class="TabbedPanelsContentGroup"
    id="tabs">
    <tiles:insertAttribute name="tabs.content" />

</div>
</div>
</form>
<!-- <div id="dirty.flag" class="hidden"><s:property value="oldItem.diff(item).size() > 0"/></div> -->