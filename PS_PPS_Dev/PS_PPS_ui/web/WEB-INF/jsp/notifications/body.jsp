<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${notifications != null}">
    <fieldset>
    <legend>Notifications</legend>
    <peps:table
        tableId="notificationTable"
        dataModel="${notifications}"
        printTemplate="${notificationPrintTemplate}"
        multiselectButtonKey="button.hide"
        multiselectButtonAction="hide" />
    </fieldset>
</c:if>