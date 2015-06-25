<%@ taglib  
    prefix="spring"
    uri="http://www.springframework.org/tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html
    xmlns="http://www.w3.org/1999/xhtml"
    xml:lang="en"
    lang="en">
<head>
<peps:head />
<style type="text/css">
html {
    width: auto;
}
</style>
</head>
<body
    onload="onLoad(); resizePopup();"
    onunload="onUnload()"
    onbeforeunload="return onBeforeUnload();">
<div class="body-content">
<peps:form id="popupForm">
    <peps:model ognlPrefix="item">
    <tiles:insertAttribute name="form-content" />
    </peps:model>
    <div class="hidden"><peps:submit
        id="submit.button"
        key="button.submit"
        event="submitPopup"
        disabled="true" /></div>
</peps:form>
<div
    id="buttons"
    class="panel"><span class="column">
<button
    id="closewindow"
    onclick="window.close();"><spring:message code="button.cancel" /></button>
</span><span class="column">
<button
    id="updateField"
    onclick="submitButton = 'submitPopup'; disableSubmit('popupForm'); submitPopup('popupForm','submit.button');"
    class="floatRight"><spring:message code="button.saveChanges" /></button>
</span></div>
</div>
<peps:javascriptLocalization />
</body>
</html>