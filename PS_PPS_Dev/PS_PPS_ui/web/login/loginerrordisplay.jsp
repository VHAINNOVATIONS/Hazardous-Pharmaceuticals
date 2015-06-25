<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<!--
 * 
 * @author VHIT Security and Other Common Services (S&OCS)
 * @version 1.1.0.007
 * -->
<title>Login Error</title>
</head>
<body>
    <%

String stringurl = "";
 String errorMessage = (String) session.getAttribute("gov.va.med.authentication.kernel.errormessage");
 if (errorMessage == null) errorMessage = "";
 
%>
    <!-- TODO: invalidate any login user demographics session object here? -->
    <h3>There was a login error detected by the login system:</h3>
    <table width="80%" align="center">
        <tr>
            <td align="left"><p><b><%= errorMessage %></b></p></td>
        </tr>
    </table>
    <%
        session.removeAttribute("gov.va.med.authentication.kernel.errormessage");
	    session = request.getSession(false);
	    stringurl =  request.getContextPath()+"/login/login.jsp"+"?relogin=true";
    %>
    <p><a href="<%= stringurl %>">Try login again.</a></p>
</body>
</html>
