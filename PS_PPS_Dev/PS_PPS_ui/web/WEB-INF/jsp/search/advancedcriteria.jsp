<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page import="gov.va.med.pharmacy.peps.common.vo.SearchTermVo"%>

<%@page import="java.util.List"%>

<%@page import="gov.va.med.pharmacy.peps.common.vo.ProductVo"%>

<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>

<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>

<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>

<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility"%>

<%@page
	import="gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField"%>

<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.utility.ApplicationContextUtility"%>

<%@page import="gov.va.med.pharmacy.peps.common.vo.ValueObject"%>


<%
	ManagedItemVo managedItem = null;
	if (request.getAttribute("item") != null) {
		managedItem = (ManagedItemVo) request.getAttribute("item");
		System.out.println(managedItem.toString());
	}
%>
<script src="/PRE/javascript/advancedSearch.js" type="text/javascript"></script>


<div class="horizontalspacer"></div>
<form:form id="advancedsearch" name="advancedsearch"
	modelAttribute="searchCriteria" action="/PRE/advancedcriteria.go"
	method="GET" onsubmit="return disableSubmit(this.id);">
	<input type="hidden" id="isFirstRun" name="isFirstRun" value="false" />
	<input type="hidden" id="hasEntityChanged" name="hasEntityChanged"
		value="false" />
	<input type="hidden" id="addTerm" name="addTerm" value="false" />


	<div class="row">
		<div class="panel">
			<div class="controlGroup">
				<table cellpadding="5" width="100%">
					<tr>
						<td>
							<div class="row">
								<spring:message code="button.retrieveTemplates"
									var="retrieveButton" />
								<peps:submit key="button.retrieveTemplates"
									action="/PRE/managesearchtemplates.go" />
								<c:out value="OR" />
								&nbsp;&nbsp;&nbsp;&nbsp;<span class="addproducttitle"> <c:out
										value="${templateName}" /> </span>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="verticalspacer"></div>
							<div>
								<strong><spring:message code="search.for.name" /><span>:</span>
								</strong>
							</div></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="row">
			<span class="column"> <spring:message code="item.type"/>:<c:choose>
					<c:when test="${empty searchMode}">
						<spring:message code="entity.type.description"
							var="entityTypeDesc" />
						<form:select title="${entityTypeDesc}" path="entityType"
							cssClass="comboBox" onchange="document.advancedsearch.submit();">
							<form:options items="${ entityTypeMap }" />
						</form:select>
					</c:when>
					<c:otherwise>
						<form:select title="${entityTypeDesc}" path="entityType"
							id="entity.entityType_disabled" cssClass="comboBox"
							onchange="document.advancedsearch.submit();" disabled="true">
							<form:options items="${ entityTypeMap }" />
						</form:select>
						<input type="hidden" id="searchMode" name="searchMode"
							value="${searchCriteria.entityType}" />

					</c:otherwise>

				</c:choose> </span> <input type="hidden" id="changed" name="changed"
				value="${changed}" />

			<c:set var="searchMode" scope="page"
				value="${searchCriteria.entityType}" />
			
			<div class="column22">
				<fieldset>		    
					<legend>
						<label  id="productTypes.label" 
								for="productTypes" 
								title="<spring:message code='categories.description'/>">
								<spring:message code="categories.name" />
						</label>
					</legend>
				<div>
					<form:checkboxes
						cssClass="checkbox"
						element="div"					 
						path="categories" 
						items="${categoryMap}" />
				</div>
				</fieldset>
			</div>
			<div class="column23">            
	            <fieldset>		    
					<legend>
						<label  id="productSubTypes.label"
	            	    	    title="<spring:message code='sub.categories.description'/>">
		                    	<spring:message code="sub.categories.name" />
		            	</label>
		            </legend>
		            <div>
		                <form:checkboxes
		                    cssClass="checkbox"
		                    element="div"                    
		                    path="subCategories" 
		                    items="${subCategoryMap}" />
		            </div>
	            </fieldset>
	        </div>
			
			<div class="column22">
			<fieldset>
			<spring:message code="item.status.description" var="itemStatusDesc" />
			<legend title="${itemStatusDesc}"><spring:message code="item.status.name" /></legend>
				<form:checkboxes title="${itemStatusDesc}" cssClass="checkbox"
					element="div" path="itemStatus" items="${itemStatusTypeMap}" /> 
			</fieldset> 
			</div>
					
			<div class="column22">
			<fieldset>
			<spring:message code="request.item.status.description" var="requestStatusDesc" />
			<legend title="${requestStatusDesc}"><spring:message code="request.item.status.name" /></legend>
				<form:checkboxes title="${requestStatusDesc}" cssClass="checkbox"
					element="div" path="requestStatus" items="${requestStatusTypeMap}" />
			</fieldset>
			</div>
		</div>
		<div class="row"></div>

		<div class="row">
			<br>
		</div>

		<c:forEach var="searchItems" items="${searchCriteria.searchTerms}"
			varStatus="iterStatus">
			<c:set scope="page" var="index" value="${iterStatus.index}" />
			<div class="row">
				<c:choose>
					<c:when test="${index == 0}">
						<span class="column">where</span>
					</c:when>
					<c:otherwise>
						<span class="column">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					</c:otherwise>
				</c:choose>
				<%-- 	        	<c:out value="1 ${searchTerms[index].searchField.key}" /> --%>
				<%-- 	        	<c:out value="3 ${searchCriteria.searchTerms[index].searchField.key}" /> --%>
				<%-- 	        	<c:out value="4 ${entityType}" /> --%>
				<%-- 	        	<c:out value="5 ${searchCriteria.entityType}" /> --%>
				<spring:message code="search.fields.description"
					var="searchFieldDesc" />
				<form:select title="${searchFieldDesc}"
					path="searchTerms[${index}].searchField.key" cssClass="comboBox"
					onchange="document.advancedsearch.submit();">
					<form:options items="${searchCriteria.searchFieldsMap}" />
				</form:select>
				<spring:message code="search.for.description" var="searchForDesc" />
				<form:select title="${searchForDesc}"
					path="searchTerms[${index}].searchType" cssClass="comboBox">
					<form:options items="${searchTypeMap}" />
				</form:select>
				<c:set scope="page" var="keyString"
					value="${searchCriteria.searchTerms[index].searchField.fieldKey.key}" />
				<c:set scope="page" var="value"
					value="${searchCriteria.searchTerms[index].value}" />
				<span id="searchValuesCollection"> <c:if
						test="${keyString != null }">												
						<peps:dataField id="searchTerms[${index}].value" item="${item}"
							value="${value}" key="${keyString}"
							isAdvancedSearchContext="true" labelPosition="none"
							managedDataAddLink="false" multiselect="false" postValue="true"
							name="searchTerms[${index}].value" />
					</c:if> </span>
				<c:if test="${index < (fn:length(searchCriteria.searchTerms)-1)}">
					<form:select path="searchTerms[${index}].advancedAndSearch"
						id="isAdvancedSelect" cssClass="comboBox"
						onchange="document.advancedsearch.submit();">
						<form:options items="${advancedAndSearchMap}" />
					</form:select>
				</c:if>
			</div>
			<div class="horizontalspacer"></div>
		</c:forEach>
	</div>
	<%-- 		</c:forEach> --%>

	<div class="row">
		<span class="column"> <peps:submit key="button.add"
				onclick="add();" /> </span>
	</div>
	<div class="row">
		<span class="column floatMiddle" style="position: relative"> <peps:submit
				key="button.search" onclick="busyProcess(this, runSearch('/PRE/search.update.go'));" />				
		</span>
		<div class="row">
			<a href="searchItems.go" title="Back to Simple Search"> Back to
				Simple Search</a>
		</div>
	</div>
</form:form>

