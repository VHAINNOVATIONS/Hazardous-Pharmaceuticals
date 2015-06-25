<%@ taglib 	
	prefix="menu" 
	uri="http://struts-menu.sf.net/tag"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
	
    <%--  permissions="rolesAdapter" --%>
    <menu:useMenuDisplayer name="TabbedMenu" bundle="navigation">
	    <menu:displayMenu name="home" />
        <menu:displayMenu name="itemManagement" />
        <menu:displayMenu name="reports" />
        <menu:displayMenu name="cotsServices" /> 
<%--         
		<s:if test="%{environment.local}">
            <menu:displayMenu name="notifications" />
        </s:if> 
--%>
        <menu:displayMenu name="administration" />
	    <menu:displayMenu name="accountManagement" />
	    <menu:displayMenu name="migration" />
    </menu:useMenuDisplayer>