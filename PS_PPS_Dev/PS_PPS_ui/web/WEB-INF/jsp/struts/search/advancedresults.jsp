<%@page import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%><%@ taglib
    prefix="s"
    uri="/struts-tags"%>
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
    <%
            UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
    boolean isAdmin = userContext != null && userContext.getUser().isAdministrativeLevel();
    
    if(isAdmin) {
      %>    
<s:set
    scope="page"
    name="skipResultsTitle"
    value="%{getText('link.results.skip.title')}" /> 
<a name="advancedResults"
    id="advancedResults"
    title="${skipResultsTitle}"
    href="#advancedResultsReturn"></a>
<s:if test="%{items != null}">
        <fieldset><legend>Search Results</legend><peps:table
            tableId="<%= gov.va.med.pharmacy.peps.presentation.common.action.SearchAction.SEARCH_RESULTS_TABLE %>"
            dataOgnl="items"
            fieldsOgnl="searchTemplate.printTemplate" />
        </fieldset>
    <peps:form id="managePrintTemplateForm">
        <div class="row"><span class="column"><s:textfield
            key="searchTemplate.templateName"
            label="Save As"
            title="Save As"
            size="15" 
            cssClass="saveas" /></span> 
            <span class="column"><s:textarea
            key="searchTemplate.notes"
            label="Notes"
            title="Notes"
            rows="3"
            cols="20"
            cssClass="saveas" /> </span> 

            <s:if test="%{environment.local}">
            <span class="column">
            <s:select
            key="searchTemplate.templateType"
            list="@gov.va.med.pharmacy.peps.common.vo.TemplateType@getLocalValues()"
            listValue="getText('TemplateType.' + name())"
            label="%{systemLevel.label}"
            labelposition="left"
            title="%{templateType.label}" cssClass="saveas" /></span>
            </s:if>
            <s:else>
            <span class="column"> <s:select
                key="searchTemplate.templateType"
                list="@gov.va.med.pharmacy.peps.common.vo.TemplateType@getNationalValues()"
                listValue="getText('TemplateType.' + name())"
                label="%{systemLevel.label}"
                labelposition="left"
                title="%{templateType.label}"
                cssClass="saveas" /></span>
        </s:else>

            
            <span class="column">&nbsp;&nbsp;&nbsp;</span><span class="column"><peps:submit
            key="button.saveTemplate"
            event="save" /></span> <span class="column"><peps:submit
            key="button.setAsDefault"
            event="default" /></span> <span class="column"><peps:submit
            key="button.managePrintTemplate"
            event="managePrintTemplate" /> </span></div>
    </peps:form>
</s:if>
<s:set
    scope="page"
    name="returnResultsTitle"
    value="%{getText('link.results.return.title')}" /> 
<a name="advancedResultsReturn"
    id="advancedResultsReturn"
    title="${returnResultsTitle}"
    href="#advancedResults"></a>
<script type="text/javascript">
    document.getElementById("advancedResults").focus();
</script>

<%
}
else {
%>
<s:set
    scope="page"
    name="skipResultsTitle"
    value="%{getText('link.results.skip.title')}" /> 
<a name="advancedResults"
    id="advancedResults"
    title="${skipResultsTitle}"
    href="#advancedResultsReturn"></a>
<s:if test="%{items != null}">
        <fieldset><legend>Search Results</legend><peps:table
            tableId="<%= gov.va.med.pharmacy.peps.presentation.common.action.SearchAction.SEARCH_RESULTS_TABLE %>"
            dataOgnl="items"
            fieldsOgnl="searchTemplate.printTemplate" />
        </fieldset>
    <peps:form id="managePrintTemplateForm">
        <div class="row"><span class="column"><s:textfield
            key="searchTemplate.templateName"
            label="Save As"
            title="Save As"
            size="15" 
            cssClass="saveas" /></span> 
            <span class="column"><s:textarea
            key="searchTemplate.notes"
            label="Notes"
            title="Notes"
            rows="3"
            cols="20"
            cssClass="saveas" /> </span> 

           
            <span class="column"> <s:select
            key="searchTemplate.templateType"
            list="@gov.va.med.pharmacy.peps.common.vo.TemplateType@getPersonalValues()"
            listValue="getText('TemplateType.' + name())"
            label="%{systemLevel.label}"
            labelposition="left"
            title="%{templateType.label}"
            cssClass="saveas" /></span> 

            
            <span class="column">&nbsp;&nbsp;&nbsp;</span><span class="column"><peps:submit
            key="button.saveTemplate"
            event="save" /></span> <span class="column"><peps:submit
            key="button.setAsDefault"
            event="default" /></span> <span class="column"><peps:submit
            key="button.managePrintTemplate"
            event="managePrintTemplate" /> </span></div>
    </peps:form>
</s:if>
<s:set
    scope="page"
    name="returnResultsTitle"
    value="%{getText('link.results.return.title')}" /> 
<a name="advancedResultsReturn"
    id="advancedResultsReturn"
    title="${returnResultsTitle}"
    href="#advancedResults"></a>
<script type="text/javascript">
    document.getElementById("advancedResults").focus();
</script>            
<% } %>

<%--   <span class="column"> 
       <peps:select
            key="searchTemplate.templateType"
            list="@gov.va.med.pharmacy.peps.common.vo.TemplateType@getPersonalValues()"
            listValue="getText('TemplateType.' + name())"
            label="%{systemLevel.label}"
            labelposition="left"
            title="%{templateType.label}"
            cssClass="saveas" />
        </span>   --%>
          
<%--    <span> 
   		<peps:textarea
            key="searchTemplate.notes"
            label="Notes"
            title="Notes"
            rows="3"
            cols="20"
            cssClass="saveas" />
        </span>  --%>
        
         
<%--         <span>
		<peps:textfield
            key="searchTemplate.templateName"
            
            labelposition="top"
            label="Save As"
            title="Save As"
            size="15"
            cssClass="saveas" />
        </span>   --%>
 
 
 

 
 
 
 
<!-- SPRING ---------------------------------------------------------------------------->
<%-- 
<%@page import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%><%@ taglib
    prefix="s"
    uri="/struts-tags"%>
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
    <%
            UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
    boolean isAdmin = userContext != null && userContext.getUser().isAdministrativeLevel();
    
    if(isAdmin) {
      %>    
<spring:message
    scope="page"
    var="skipResultsTitle"
    code="link.results.skip.title" /> 
    
<a name="advancedResults"
    id="advancedResults"
    title="${skipResultsTitle}"
    href="#advancedResultsReturn"></a>
    
<c:if test="${not empty items}">
        <fieldset><legend>Search Results</legend><peps:table
            tableId="<%= gov.va.med.pharmacy.peps.presentation.common.action.SearchAction.SEARCH_RESULTS_TABLE %>"
            dataOgnl="items"
            fieldsOgnl="searchTemplate.printTemplate" />
        </fieldset>
    <peps:form id="managePrintTemplateForm">
        <div class="row">
	        <span class="column">
		        <peps:textfield
		            key="searchTemplate.templateName"
		            label="Save As"
		            title="Save As"
		            size="15" 
		            cssClass="saveas"
		            required="false" 
		        />
	        </span> 
            <span class="column">
            <peps:textarea
            key="searchTemplate.notes"
            label="Notes"
            title="Notes"
            rows="3"
            cols="20"
            cssClass="saveas" /> </span> 
		<c:choose>
            <c:when test="${environment.local}">
	            <span class="column">
	            <peps:select
	            key="searchTemplate.templateType"
	            list="@gov.va.med.pharmacy.peps.common.vo.TemplateType@getLocalValues()"
	            listValue="TemplateType. + name()"
	            label="${systemLevel.label}"
	            labelposition="left"
	            title="${templateType.label}" 
	            cssClass="saveas" 
	            />
	            </span>
            </c:when>
            <c:otherwise>
            <span class="column">          
            <peps:select
                key="searchTemplate.templateType"
                list="@gov.va.med.pharmacy.peps.common.vo.TemplateType@getNationalValues()"
                listValue="getText('TemplateType.' + name())"
                label="${systemLevel.label}"
                labelposition="left"
                title="${templateType.label}"
                cssClass="saveas" 
             />
             </span>
        </c:otherwise>
	</c:choose>          
            <span class="column">&nbsp;&nbsp;&nbsp;
            </span>
            <span class="column">
            <peps:submit
            key="button.saveTemplate"
            event="save" />
            </span> 
            <span class="column">
            <peps:submit
            key="button.setAsDefault"
            event="default" />
            </span> 
            <span class="column">
            <peps:submit
            key="button.managePrintTemplate"
            event="managePrintTemplate" /> 
            </span>
         </div>
    </peps:form>
</c:if>
<spring:message
    scope="page"
    var="returnResultsTitle"
    code="link.results.return.title" /> 
    
<a name="advancedResultsReturn"
    id="advancedResultsReturn"
    title="${returnResultsTitle}"
    href="#advancedResults"></a>
<script type="text/javascript">
    document.getElementById("advancedResults").focus();
</script>

<%
}
else {
%>
<spring:message
    scope="page"
    var="skipResultsTitle"
    code="link.results.skip.title" /> 
    
<a name="advancedResults"
    id="advancedResults"
    title="${skipResultsTitle}"
    href="#advancedResultsReturn"></a>
    
<c:if test="${not empty items}">
        <fieldset><legend>Search Results</legend><peps:table
            tableId="<%= gov.va.med.pharmacy.peps.presentation.common.action.SearchAction.SEARCH_RESULTS_TABLE %>"
            dataOgnl="items"
            fieldsOgnl="searchTemplate.printTemplate" />
        </fieldset>
    <peps:form id="managePrintTemplateForm">
        <div class="row"><span class="column">
        
        <peps:textfield
            key="searchTemplate.templateName"
            label="Save As"
            title="Save As"
            size="15" 
            cssClass="saveas" /></span> 
            <span class="column">
            
        <peps:textarea
            key="searchTemplate.notes"
            label="Notes"
            title="Notes"
            rows="3"
            cols="20"
            cssClass="saveas" /> </span> 

           
            <span class="column"> 
            <peps:select
            key="searchTemplate.templateType"
            list="@gov.va.med.pharmacy.peps.common.vo.TemplateType@getPersonalValues()"
            listValue="getText('TemplateType.' + name())"
            label="%{systemLevel.label}"
            labelposition="left"
            title="%{templateType.label}"
            cssClass="saveas" /></span> 
            
            <span class="column">&nbsp;&nbsp;&nbsp;</span><span class="column"><peps:submit
            key="button.saveTemplate"
            event="save" /></span> <span class="column"><peps:submit
            key="button.setAsDefault"
            event="default" /></span> <span class="column"><peps:submit
            key="button.managePrintTemplate"
            event="managePrintTemplate" /> </span></div>
    </peps:form>
</c:if>
<spring:message
    scope="page"
    var="returnResultsTitle"
    code="link.results.return.title" /> 
<a name="advancedResultsReturn"
    id="advancedResultsReturn"
    title="${returnResultsTitle}"
    href="#advancedResults"></a>
<script type="text/javascript">
    document.getElementById("advancedResults").focus();
</script>
<% } %> --%>
