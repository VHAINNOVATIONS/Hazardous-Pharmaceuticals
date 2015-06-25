<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.UrlUtility"%>
<script
    src="/PRE/javascript/formDiff.js"
    type="text/javascript"></script>
<form
    id="consoleActionForm"
    method="post"
    action="/PRE/systemActions.go"
    onsubmit="selectAllTheOptions('selectedWarningLabels'); return disableSubmit(this.id);">
<s:set
    scope="page"
    name="itemId"
    value="item.id" />
<s:set
    scope="page"
    name="itemTypeString"
    value="itemType.toFieldKey()" />

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
<div id="dirty.flag" class="hidden"><s:property value="oldItem.diff(item).size() > 0"/></div>