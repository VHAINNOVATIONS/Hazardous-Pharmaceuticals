<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@ taglib 
	prefix="form" 
	uri="http://www.springframework.org/tags/form"%>
    
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<form:form 
	id="managedDomainSearchCriteria" 
	modelAttribute="searchCriteria" 
	action="/PRE/searchDataElements.go"
	method="GET"
	onsubmit="return disableSubmit(this.id);">
    <input type="hidden" id="isFirstRun" name="isFirstRun" value="false"/>

	<div class="row">	
		<span class="column">
			<spring:message code="search.fields.name" /><span>:</span>
			<div>
				<form:select 
					path="searchTerms[0].searchField.key"
					cssClass="comboBox">
					<form:options 
						items="${searchCriteria.searchFieldsMap}" />
				</form:select>
				
			</div>
		</span>
		<span class="column">
	     	<spring:message text="For" /><span>:</span>
			 <div>
		 		<form:input
			        path="searchTerms[0].value"
			        labelposition="top"
			        title="For" />
			</div>
		</span>
	    <div class="column22">
			<spring:message code="item.status.name" />
			<form:checkboxes 
				cssClass="checkbox"
				element="div"
				items="${itemStatusTypeMap}"
				path="itemStatus" />
			<br />
	   		<br />
		</div>
		<div class="column22">
			<spring:message code="request.item.status.name" />
			<form:checkboxes
				cssClass="checkbox"
				element="div"
				items="${requestStatusTypeMap}"
				path="requestStatus" />
			<br />
	    	<br />
		</div>
	    <span class="column">
	       <peps:submit
	    	    key="button.search" />
	    </span>
	    
    </div>
    <div class="bodyheaderspacer"></div>
</form:form>
