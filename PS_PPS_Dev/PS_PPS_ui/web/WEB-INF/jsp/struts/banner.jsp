<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>    
 <spring:message
    scope="page"
    var="logoAltText"
    code="logo.alt.text" /><img alt="${logoAltText}" 	src="/PRE/images/inter-header-banner.jpg" />  