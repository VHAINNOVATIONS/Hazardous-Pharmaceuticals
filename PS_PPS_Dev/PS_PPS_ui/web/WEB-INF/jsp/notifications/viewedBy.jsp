<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %> 
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
	
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
<div id="editFieldForm"><peps:form id="editField">
       <div class="column"> 
	       <peps:textfield
	            key="viewedBy"
	            label="Viewed By"
	            labelposition="left"  /> 
       </div>
</peps:form></div>
<div
    id="buttons"
    class="panel"><span class="column">
<button
    id="closewindow"
    onclick="window.close();"><%--<s:text name="button.cancel" /> --%><spring:message code="${button.cancel}" /></button>
</span></div>
<peps:javascriptLocalization />
</body>
</html>