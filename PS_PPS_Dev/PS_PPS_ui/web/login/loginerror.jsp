<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" %>
<html
    xmlns="http://www.w3.org/1999/xhtml"
    xml:lang="en"
    lang="en">
<head>
<!--
 * 
 * @author VHIT Security and Other Common Services (S&OCS)
 * @version 1.1.0.007
 * -->
<title>Forms Authentication Error</title>
</head>
<body>
<h3>Forms authentication login failed.</h3>
<p><a href="<%= request.getContextPath() %>/login/login.jsp">Try login again.</a></p>
</body>
</html>