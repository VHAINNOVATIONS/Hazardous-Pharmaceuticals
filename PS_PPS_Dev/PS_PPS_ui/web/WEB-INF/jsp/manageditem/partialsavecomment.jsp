<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<form:form id="partialComment" commandName="partialSave" onsubmit="return disableSubmit(this.id);">
<div class="horizontalspacer"></div>
<div class="row">
    <fieldset style="width: 400px; margin: 10px; white-space: nowrap">
        <legend><b><spring:message code="bookmark.comment.title" /></b></legend><br />
        <spring:message code="bookmark.comment.prompt" /><br /><br />
        <form:textarea path="comment" rows="4" cols="47" required="true" />
        <div class="horizontalspacer"></div><br />
        <span class="column floatLeft">
            <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/editPartialCancel.go" var="back"/>
            <peps:submit
                key="button.cancel" action="${back}" />
        </span>
        <span class="column floatRight">        
            <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/savePartial.go" var="savePartial"/>
            <peps:submit
                key="button.submit" action="${savePartial}" />
        </span>
    </fieldset>  
</div>    
</form:form>
