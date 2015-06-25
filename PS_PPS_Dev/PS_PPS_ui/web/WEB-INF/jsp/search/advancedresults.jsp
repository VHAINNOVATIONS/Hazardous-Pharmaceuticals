<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<spring:message scope="page" var="skipResultsTitle"
	code="link.results.skip.title" />

<a name="advancedResults" id="advancedResults"
	title="${skipResultsTitle}" href="#advancedResultsReturn"></a>


<%-- 		<c:if test="${items != null}">    --%>
<fieldset>
	<legend>Search Results</legend>
	<peps:table tableId="<%= ControllerConstants.SEARCH_RESULTS_TABLE %>"
		dataModel="${items}" printTemplate="${printTemplate}"
		multiselectButtonAction="searchItems.go" export="true"/>
</fieldset>
<%-- 		</c:if> --%>
<c:if test="${not empty items}">
	<form:form id="managePrintTemplateForm" modelAttribute="searchCriteria"
		action="/PRE/advancedcriteria.go" method="GET"
		onsubmit="return disableSubmit(this.id);">

		<input type="hidden" id="setDefault" name="setDefault" value="false" />
		<input type="hidden" id="saveSearch" name="saveSearch" value="false" />
		<input type="hidden" id="prevTemplateName" name="prevTemplateName"
			value="${templateName}" />

		<div class="row">
			<span class="column"> 
			<!-- TEMPLATE TITLE --> 
			<spring:message
					code="displayable.name.description" var="nameDesc" /> <spring:message
					text="Save As" /><span>:</span> <input id="template"
				name="template" title="${nameDesc}" value="${templateName}"
				size="25" /> <span>&nbsp;&nbsp;&nbsp;</span> 
				<!-- NOTES --> 
				<spring:message
					code="notes.description" var="notesDesc" /> <spring:message
					text="Notes" /><span>:</span> <textarea title="${notesDesc}"
					name="notes" id="notes" rows="3" cols="20">${templateNotes }</textarea>

				<span>&nbsp;&nbsp;&nbsp;</span> 
				<!-- TYPE --> <spring:message
					code="template.type.description" var="typeDesc" /> <spring:message
					text="Save At" /><span>:</span> <c:set scope="page"
					var="valString" value="${templateType}" /> <form:select
					title="${typeDesc}" id="type" name="type" path=""
					cssClass="comboBox">
					<c:forEach var="option" items="${templateTypeMap}"
						varStatus="optionStatus">

						<c:choose>
							<c:when test="${option.key eq valString}">
								<option value="${option.key}" selected="selected">${option.value}</option>
							</c:when>
							<c:otherwise>
								<option value="${option.key}">${option.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select> </span> <span class="column"> <script language="Javascript">
					//<!--
					var confirmMsg = "<spring:message code='template.name.exists.warning.message' />";
				//-->
				</script> <peps:submit key="button.saveTemplate" action="savetemplate.go" />
			</span> <span class="column"> <peps:submit key="button.setAsDefault"
					onclick="setAsDefault();" /> </span> <span class="column"> <peps:submit
					key="button.managePrintTemplate" action="manageprinttemplate.go" />
			</span>
		</div>
	</form:form>
</c:if>
<div class="row">
	<span class="column"> </span>
</div>
<spring:message scope="page" var="returnResultsTitle"
	code="link.results.return.title" />
<a name="advancedResultsReturn" id="advancedResultsReturn"
	title="${returnResultsTitle}" href="#advancedResults"></a>
<script type="text/javascript">
	document.getElementById("advancedResults").focus();
</script>
<div class="row">
	<span class="column"><br> </span>
</div>


