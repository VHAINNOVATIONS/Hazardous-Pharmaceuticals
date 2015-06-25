<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page 
	import="gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo"%>
<%@ taglib
	uri="http://tiles.apache.org/tags-tiles"
	prefix="tiles"%>
<%@ taglib
	prefix="peps"
	tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>

<spring:message    
	scope="page"
	var="skipResultsTitle"
	code="link.results.skip.title" /> 

<a name="simpleResults"
	id="simpleResults"
	title="${skipResultsTitle}"
	href="#simpleResultsReturn"></a>
	
<!-- <a id="searchResultsPage"></a> -->
<c:choose>
<c:when test="${items != null}">
	<div class="horizontalspacer"></div>
	<fieldset>
		<legend><b>SEARCH RESULTS</b></legend>
        <peps:table 
        tableId="<%= gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants.SEARCH_RESULTS_TABLE %>" 
        dataModel="${items}"
        printTemplate="${printTemplate}" 
        multiselectButtonAction="searchItems.go" 
        export="true"/> 
	</fieldset>
</c:when>
</c:choose>

<spring:message
	scope="page"
	var ="returnResultsTitle"
	code="link.results.return.title" />  

<a name="simpleResultsReturn"
	id="simpleResultsReturn"
	title="${returnResultsTitle}"
	href="#simpleResults"></a> 
	
	<script type="text/java-script">
	document.getElementById("simpleResults").focus();</script>


 