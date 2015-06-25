<%@ taglib
    prefix="spring"
    uri="http://www.springframework.org/tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="form" 
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>

<form:form id="saveTemplateWarning" 
	name="saveTemplateWarning"  
	modelAttribute="manageTemplates"  
	onsubmit="return disableSubmit(this.id);"
	action="/PRE/overwritetemplate.go" method="GET">
	
	<div class="horizontalspacer"></div>
	<div class="row">
		<fieldset style="width: 30%; margin: 10px; white-space: nowrap"><legend><b>WARNING</b></legend>
		<form:input 
			type="hidden" 
		    path="templateName" 
			size="15"/>
		<form:input
		    type="hidden" 
			path="templateNotes" 
			size="15"/>
		<form:input
		    type="hidden" 
			path="templateType" 
			size="15"/>
			<h1><spring:message code="template.name.exists.warning.message" /> <span class="redText">"<c:out value="${manageTemplates.templateName}" />"</span>.</h1>
			<h1><spring:message code="template.name.exists.warning.confirmation" /></h1>
			<div class="horizontalspacer"></div>
			<span class="column floatRight">
				<peps:submit 
			    	key="button.overwrite" />
			    <peps:submit
			        key="button.cancel" action="overwritetemplate.cancel.go" />
			</span>
		</fieldset>
	</div>
</form:form>