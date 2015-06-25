<%@ tag
    language="java"
    body-content="empty"
    description="Dispaly a button (not a submit button) that confirms the cancel operation, and then redirects the browser to the flow's referer URL."%>
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>

<peps:button
    key="button.cancel"
    onclick="window.location = '${flowScope.referer}';" />