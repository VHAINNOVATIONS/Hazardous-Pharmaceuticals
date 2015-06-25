<%@page import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>
<%@taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>    
 
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%
            UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
            boolean loggedIn = userContext != null && userContext.getUser() != null;            

            if (loggedIn) {
%>
<spring:message code="welcome" />, 
<peps:redirectFlowLink cssClass="HeaderLinks" flowId="login.go" tooltip="View login information" text="<%=userContext.getUser().getFirstName()%>" />.
<peps:redirectFlowLink cssClass="HeaderLinks" flowId="logout.go"  text="Logout" />.
<br />

<peps:text key="<%= FieldKey.ROLE_LIST.getKey() %>" label="false" item="<%=userContext.getUser()%>" /> - <peps:text key="<%= FieldKey.LOCATION.getKey() %>" label="false" item="<%=userContext.getUser()%>" />
<%
			}
            else {
%>
<peps:redirectFlowLink cssClass="HeaderLinks" flowId="login.go" text="Login" />
<%
}
%>