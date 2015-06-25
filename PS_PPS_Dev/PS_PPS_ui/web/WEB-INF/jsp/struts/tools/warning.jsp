<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>

<peps:form id="warn">

<div class="horizontalspacer"></div>

<div class="row">
<fieldset style="width: 30%; margin: 10px; white-space: nowrap"><legend><b>WARNING</b></legend>
<h1><s:text name="template.name.exists.warning.message" /> <span class="redText">"<s:label value="%{templateName}" />"</span>.</h1>
<h1><s:text name="template.name.exists.warning.confirmation" /></h1>
<div class="horizontalspacer"></div>
<span class="column floatRight">
    <peps:submit
        key="button.overwrite"
        event="submit" />
    <peps:submit
        key="button.cancel"
        event="cancel" />
</span>
</fieldset>
</div>
</peps:form>