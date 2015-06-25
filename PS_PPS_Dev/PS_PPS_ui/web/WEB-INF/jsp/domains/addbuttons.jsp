<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    

<span class="column">
    <c:url value="/${fn:toLowerCase(item.entityType)}/addCancel.go" var="cancelUrl"/>
    <peps:submit key="button.cancel" action="${cancelUrl}"/> 
</span>
<span class="column">    
    <peps:submit key="button.submit"/>
</span>
