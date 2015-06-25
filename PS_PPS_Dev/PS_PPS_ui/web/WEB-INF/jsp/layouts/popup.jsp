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
<div class="body-content"><tiles:insertAttribute name="body-content" /></div>
<peps:javascriptLocalization />
</body>
</html>