<%@ page contentType="text/html; charset=UTF-8" buffer="256kb"%>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html
    xmlns="http://www.w3.org/1999/xhtml"
    xml:lang="en"
    lang="en">
<head>
<peps:head />
</head>
<body 
    onload="onLoad()"
    onunload="onUnload()"
    onbeforeunload="return onBeforeUnload()">
<div class="header">
<div class="banner"><!-- --><tiles:insertAttribute name="banner" /></div>
<%-- <s:set
    scope="page"
    name="skipLinkTitle"
    value="%{getText('link.nav.skip.title')}" />--%>

<spring:message 
    scope="page"
    var="skipLinkTitle"
    code="link.nav.skip.title" />
    
<div class="skip-links"><a

    name="skipnav"
    id="skipnav"
    class="HeaderLinks"
    title="${skipLinkTitle}"
    href="#startcontent"><spring:message code="link.nav.skip" /></a></div>
<div class="user-info"><!-- --><tiles:insertAttribute name="user-info" /></div>
</div>
<div class="navspacer"></div>
<div class="navheader" ><!-- --><tiles:insertAttribute name="primary-nav" /></div>
<div class="body">
<div class="body-header"><!-- --><peps:breadcrumbs /><a
    name="startcontent"></a><tiles:insertAttribute name="body-header" /></div>
<div class="body-content"><!-- --><peps:error id="fieldErrors" errors="${errors}" /></div>
<div class="body-content"><tiles:insertAttribute name="body-content" /></div>
<div class="body-footer"><!-- --><tiles:insertAttribute name="body-footer" /></div>
</div>
<div class="footer"><!-- --><tiles:insertAttribute name="footer" /></div>
<peps:javascriptLocalization />
</body>
</html>