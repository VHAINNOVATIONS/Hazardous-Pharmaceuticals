<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>

<peps:form id="partialComment">
<div class="horizontalspacer"></div>
<div class="row">
    <fieldset style="width: 400px; margin: 10px; white-space: nowrap">
        <legend><b><%--<s:text name="bookmark.comment.title" /> --%><spring:message code="bookmark.comment.title" /></b></legend><br />
        <%--<s:text name="bookmark.comment.prompt" /> --%><spring:message code="bookmark.comment.prompt" /><br /><br />
        <peps:dataField key="comment" rows="4" cols="47" required="true" />
        <div class="horizontalspacer"></div><br />
        <span class="column floatLeft">
            <peps:submit
                key="button.cancel"
                event="cancel" />
        </span>
        <span class="column floatRight">
            <peps:submit
                key="button.submit"
                event="savePartial" />
        </span>
    </fieldset>  
</div>    
</peps:form>
