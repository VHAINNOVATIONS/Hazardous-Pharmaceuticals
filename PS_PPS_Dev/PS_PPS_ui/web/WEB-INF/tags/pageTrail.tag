<%@ tag language="java"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>
    
<div class="panel breadcrumbs">
	<span class="column"> 
	    <c:set var="seperator" value="" />
	    <c:forEach items="${sessionScope['scopedTarget.pageTrail'].allPages}" var="page">
	        ${seperator}<c:url var="pageUrl" value="${page.url}" />
            <a class="pageTrailPage" title="Return to the ${page.label} page" href="${pageUrl}" tabindex="1">${page.label}</a>
            <c:set var="seperator" value=" &gt; " />
            <c:set var="help" value="${ page.roboHelpLabel }"/>
		</c:forEach> 
	</span> 
	<span class="column floatRight">
	    <a class="feedback" href="mailto:pre-feedback@datasys.swri.edu" tabindex="1">Provide Feedback</a>
	</span>
		<span class="column floatRight">
		<c:url value="/help/index.htm" var="helpIndex"/>
		<a  class="feedback" href='javascript:RH_OpenHelpTopic("${helpIndex}", "masterHelp/${ help }.htm")' tabindex="1">Page Help</a>		
	</span>
<!-- 	<span class="column floatRight"> -->
<%-- 		<img alt="Page Help" src="images/help_icon.png" align="top" height="50px" onclick='javascript:RH_OpenHelpTopic("help/index.htm", "masterHelp/${ help }.htm")' tabindex="1">		 --%>
<!-- 	</span> -->
<!-- 	<span class="column floatRight"> -->
<%-- 		<a href='javascript:RH_ShowHelp(0, "help/index.htm", HH_HELP_CONTEXT, ${ help })'>Help ${ help }</a> --%>
<%-- 		<a href='javascript:RH_OpenHelpTopic("help/index.htm", "masterHelp/${ help }.htm")' tabindex="1"> --%>
<!-- 	</span> -->
<!-- 	<span class="column floatRight"> -->
<!-- 		<a href='javascript:RH_ShowHelp(0, "help/index.htm", HH_DISPLAY_INDEX, 0)'>Help Main</a> -->
<!-- 	</span> -->
</div>