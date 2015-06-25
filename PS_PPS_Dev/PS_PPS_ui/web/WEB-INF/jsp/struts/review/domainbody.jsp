<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>	    
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<c:if test="${not empty conversationScope['requests']}">
    <peps:table
        tableId="searchTable"
        dataOgnl="conversationScope['requests']"
        fieldsOgnl="conversationScope['requestSearchPrintTemplate']"
        paged="false" />
</c:if>