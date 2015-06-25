<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
	
<s:if test="%{notifications != null}">
<%--<c:if test="%{notifications != null}"> --%>
    <fieldset><legend>Notifications</legend><peps:table
        tableId="notificationTable"
        dataOgnl="notifications"
        fieldsOgnl="notificationPrintTemplate"
        multiselectButtonKey="button.hide"
        multiselectButtonEvent="hide" /></fieldset>
</s:if>
<%-- </c:if> --%>