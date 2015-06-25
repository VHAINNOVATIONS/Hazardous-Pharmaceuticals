<%@page import="gov.va.med.pharmacy.peps.common.vo.UserVo"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>
<%
            UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
            boolean loggedIn = userContext != null && userContext.getUser() != null;
            
            pageContext.setAttribute("keyUsername", FieldKey.USERNAME.getKey());
            pageContext.setAttribute("keyPassword", FieldKey.PASSWORD.getKey());
            
            if (loggedIn) {                
                pageContext.setAttribute("user", userContext.getUser());
%>
<!-- Set a width less than the width of the app because the 100% width of the panel and fields causes scrollbars -->
<div class="panel" style="width: 1250px;">
You are logged into the system as:
<peps:text
    key="<%= FieldKey.FIRST_NAME.getKey() %>"
    label="true" 
    item="${user}" />
<peps:text
    key="<%= FieldKey.LAST_NAME.getKey() %>"
    label="true"
    item="${user}" />
<peps:text
    key="<%= FieldKey.USERNAME.getKey() %>"
    label="true"
    item="${user}" />
<peps:text
    key="<%= FieldKey.LOCATION.getKey() %>"
    label="true"
    item="${user}" />
<peps:text
    key="<%= FieldKey.ROLE_LIST.getKey() %>"
    label="true"
    item="${user}" />
</div>
<%
            }
            else {
                pageContext.setAttribute("user", new UserVo());    
%>
<div class="panel" style="width: 1250px;">
Please enter your username and password to login.
<form:form id="login" action="/PRE/login.go" onsubmit="return disableSumbit(this.id);">
    <table style="border:0px; padding: 0px;">
        <tr>
            <td style="align: right;"><peps:label key="${keyUsername}" />: </td><td><input name="user.${keyUsername}" type="text"></td>
        </tr>
        <tr>
            <td style="align: right;"><peps:label key="${keyPassword}" />: </td><td><input name="user.${keyPassword}" type="password"></td>
        </tr>
    </table>
    <peps:submit
        key="button.login"
        event="login" 
        onclick="deleteAllCookies()"
        isFlowButton="false" />
</form:form>
</div>
<%
}
%>