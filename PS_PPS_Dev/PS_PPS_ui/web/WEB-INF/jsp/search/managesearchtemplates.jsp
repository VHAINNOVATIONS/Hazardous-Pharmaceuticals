<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<script type="text/javascript">
	function setDefault() {
		window.location = '/PRE/setdefaulttemplate.go';
	}
	
</script>
<form:form id="manageSearchTemplates" name="manageSearchTemplates"  modelAttribute="manageTemplates" onsubmit="return disableSubmit(this.id);"
	action="/PRE/managesearchtemplates.go" method="GET">

	
	<br />
	<span class="column"></span>
	<div class="twoColumn">
	<fieldset>
		<legend>Search Templates</legend> 
	    <table
	        id=searchTemplates
	        frame="box"
	        rules="cols"
	        class="pepsTable" >
	        <thead>
	            <tr>
	                <th align="left"><peps:label key="select" abbreviation="true" /></th>
	                <th align="left"><peps:label key="displayable.name" abbreviation="true" /></th>
	                <th align="left"><peps:label key="template.type" abbreviation="true" /></th>
	                <th align="left"><peps:label key="notes" abbreviation="true" /></th>
	            </tr>
	        </thead>
	        <tbody>
				<c:forEach
				    var="template"
	                items="${manageTemplates.templatesList}"
	                varStatus="details">
	                <c:set
	                    scope="page"
	                    var="index"
	                    value="${details.index}" />
	                <c:set
	                    scope="page"
	                    var="currentTemplateId"
	                    value="${template.id}" />
	                
	                <c:choose>
		                <c:when test="${index % 2 == 0}">
		                    <tr class="odd">
		                </c:when>
		                <c:otherwise>
		                    <tr class="even">
		                </c:otherwise>
	                </c:choose>
					<td>
						<c:set scope="page" var="isSelected" value="${template.selected}" />
						
		               	<peps:dataField
		               			item="${template}"
		               			name="templatesList[${index}].selected"
		               			value="${isSelected}"
		                    	key="selected"
		                    	labelPosition="none" 
								managedDataAddLink="false"/>
					</td>
	                <td>
	                 <c:set
                		scope="page"
                		var="entityType"
                		value="${searchCriteria.entityType}" />
	                	<peps:link
		                    item="${template}"
		                    key="displayable.name"                    
		                    currentSearchTemplateId="${currentTemplateId}"
		                    event="retrieve"/></td>
		                    
	                <td>
	                	<peps:text 
		                	item="${template}" 
		                	key="template.type"/></td>
	                <td>
	                	<peps:text
		                    item="${template}"
		                    key="notes" /></td>
	                </tr>
	            </c:forEach>
	        </tbody>
	    </table>
    	<div class="row">
    		<span class="column">
		    	<script language="Javascript">
				    //<!--
				    var confirmMsg = "<spring:message code='template.removal.confirmation' />";
				    //-->
		    	</script>
		    	<peps:submit
			        key="button.remove" 
			        onclick="return confirm(confirmMsg)" 
			        action="removetemplate.go" /> 
			</span> 
			<span class="column"> 
				<peps:submit
			        key="button.setAsDefault" 
			        method="default"  
			        action="setasdefault.go"/>
				
			</span> 
			<span class="column"> 
				<peps:submit
			        key="button.unDefault" 
			        action="removeasdefault.go" /> 
		   </span>
		</div>
    	<div class="row">
    		<a href='<c:url value="${sessionScope['scopedTarget.pageTrail'].previousUrl}"/>'
                   title="Back to Advanced Search">
                   Back to Advanced Search</a>
        </div>
	</fieldset>
	</div>
</form:form>