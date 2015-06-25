<%@ taglib 
prefix="peps" 
tagdir="/WEB-INF/tags"%>
<%@ taglib 
prefix="c" 
uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib 
prefix="spring" 
uri="http://www.springframework.org/tags"%>
<%@ taglib 
prefix="form" 
uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">

function setFocus()
{
	if(document.search.visitedPage.value == "true" )
		{
	  	var myfocus = document.getElementById("itemType"); 
	  	myfocus.focus();
		}
}

// Adds functions to existing onload event
var oldEvt = window.onload; 
window.onload = function() { 
	if (oldEvt) 
		oldEvt(); 
	setFocus(); 
} 

function setFocusOnResults()
{
		var searchFocus = document.getElementById("fieldErrors");
		if(searchFocus != null || searchFocus != undefined) {
			searchFocus.focus();
		}
}


</script>
<div class="horizontalspacer"></div>
<div class="searchcriteria">
<c:url value="/searchItems.go" var="searchAction"/>
<form:form 
	id="search"
	name="search"
	modelAttribute="searchCriteria" 
	action="${searchAction}"
	method="GET"
	onsubmit="return disableSubmit(this.id);">
	<input type="hidden" id="isFirstRun" name="isFirstRun" value="false"/>
	<input type="hidden" id="hasEntityChanged" name="hasEntityChanged" value="false"/>	
	<input type="hidden" id="visitedPage" name="visitedPage" value="${param.visited}"/>

	<div class="panel"> 
		<div class="column">
			<spring:message code="search.for.name" /><span class="required"> *</span><span>:</span>
				<div>
				    <spring:message code="search.for.description" var="searchForDesc"/>
					<form:input
					    title="${searchForDesc}"
						path="searchTerms[0].value" 
						size="15" tabindex="1"/>				
				</div>
				<form:radiobuttons
                    title="${searchForDesc}" 
			    	path="searchTerms[0].searchType"
			    	element="div"
			     	items="${searchTypeMap}" tabindex="5"/>
		</div>
		<div class="column">
			<spring:message code="entity.type.name" /><span>:</span>
			<div>
			    <spring:message code="entity.type.description" var="entityTypeDesc" />
					<form:select
						id="itemType"
	                    title="${entityTypeDesc}" 
						path="entityType" 
						onchange="reloadOncePrompted();" 
						tabindex="2">
						<form:options 
							items="${entityTypeMap}"/>
					</form:select>
			</div>
			<div class="column"><br /><br />
			<c:url value="/images/blank.gif" var="imgLocation"/>			
			<spring:message code="search.additional.filters" var="additionalFilters"/>
			<img src="${imgLocation}" width="1" height="1" title="${additionalFilters}" 
				alt="${additionalFilters}" >
			</div><br /><br />
					<div class="column">
				<peps:submit key="button.search" tabindex="7" onclick="setFocusOnResults();"/>
			</div>
		</div>
		<div class="column">
			<spring:message code="search.fields.abbreviation" /><span>:</span>
			<div>
			<spring:message code="search.fields.description" var="searchFieldsDescription"/>
				<form:select 
					path="searchTerms[0].searchField.key"
					title="${searchFieldsDescription}" tabindex="3">
			     	<form:options            
                        items="${searchCriteria.searchFieldsMap}"/>
				</form:select>
			</div>
			<br /><br />
				<a href="advancedcriteria.go"
                   title="Advanced Search" tabindex="12">
                   Advanced Search</a>
           	
		</div>
		
		
		<div id="strengthField" class="column20" style="display:inline;">
			<spring:message code="strength.abbreviation" /><span>:</span>
			<div style="width: auto">
			<spring:message code="strength.description"  var="strengthDescription"/>
				<form:input
				    title="${strengthDescription} (use only numbers)"
					path="strength" 
					size="8"
					tabindex="4"
				/>
			</div>
		</div>
		<div id="dosageFormField" class="column20" style="display:none;">
			<spring:message code="dosage.form.abbreviation" /><span>:</span>
			<div>
			    <spring:message code="dosage.form.description"  var="dosageFormDesc"/>
				<form:input 
				    title="dosageFormDesc"
					path="dosageForm" 
					size="7"
					tabindex="4"/>
			</div>			
		</div>
		<div id="blankField" class="column20" style="display:none;"></div>
		
		<c:choose>
		<c:when test="${searchCriteria.entityType.product}">
            <script type="text/javascript">
	           document.getElementById("strengthField").style.display="inline";
	           document.getElementById("dosageFormField").style.display="none";
	           document.getElementById("blankField").style.display="none";
		    </script>
		</c:when>
		<c:when test="${searchCriteria.entityType.orderableItem}">
		  <script type="text/javascript">
               document.getElementById("strengthField").style.display="none";
               document.getElementById("dosageFormField").style.display="inline";
               document.getElementById("blankField").style.display="none";
            </script>
		</c:when>
		<c:when test="${searchCriteria.entityType.ndc}">
          <script type="text/javascript">
               document.getElementById("strengthField").style.display="none";
               document.getElementById("dosageFormField").style.display="none";
               document.getElementById("blankField").style.display="inline";
            </script>
        </c:when>
		</c:choose>
		<div class="column23">
			<fieldset>		    
			<legend><label  id="productTypes.label" 
					for="productTypes" 
					title="<spring:message code='categories.description'/>">
					<spring:message code="categories.name" />
			</label></legend>
			<div>
				<form:checkboxes
					cssClass="checkbox"
					element="div"					 
					path="categories" 
					items="${categoryMap}" tabindex="8"/>
			</div>
			</fieldset>
		</div>
		<div class="column23">            
            <fieldset>		    
			<legend><label  id="productSubTypes.label"
                    title="<spring:message code='sub.categories.description'/>">
                    <spring:message code="sub.categories.name" />
            </label></legend>
            <div>
                <form:checkboxes
                    cssClass="checkbox"
                    element="div"                    
                    path="subCategories" 
                    items="${subCategoryMap}" tabindex="9"/>
            </div>
            </fieldset>
        </div>
		<div class="column22">
		<fieldset>		    
			<legend>
			<label  id="itemStatus.label" 
                    for="itemStatus" 
                    title="<spring:message code='item.status.description'/>">
			        <spring:message code="item.status.name" /></label></legend>
				<form:checkboxes 
					cssClass="checkbox"
					element="div"
					items="${itemStatusTypeMap}"
					path="itemStatus" tabindex="10"/>
		</fieldset>
		</div>
		<div class="column22">
		<fieldset>		    
			<legend><label  id="requestItemStatus.label" 
                    for="requestItemStatus" 
                    title="<spring:message code='request.item.status.description'/>">
                    <spring:message code="request.item.status.name" /></label></legend>
				<form:checkboxes
					cssClass="checkbox"
					element="div"					
					items="${requestStatusTypeMap}"
					path="requestStatus" tabindex="11"/>
		</fieldset>
		</div>
	</div>
</form:form>
</div>
<div class="horizontalspacer"></div>