<%@ taglib
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
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%> 
<%-- 
<s:set    
    scope="page"
    var="skipResultsTitle"
    name="link.results.skip.title" /> 
     --%>
<c:set    
    scope="page"
    var="skipResultsTitle"
    value="link.results.skip.title" /> 
<a name="simpleResults"
    id="simpleResults"
    title="${skipResultsTitle}"
    href="#simpleResultsReturn"></a>

<%-- <c:set var="itemsList" scope="session" value="${items}" /> --%>
<%-- <c:set var="itemsList" scope="session" value="<%=OgnlUtility.findValue(request, "items")%>" /> --%>

<%-- <c:if test="${not empty itemsList}"> --%>
<s:if test="%{items != null}">
    <div class="horizontalspacer"></div>
    <fieldset><legend><b>SEARCH RESULTS</b></legend>

<peps:table
        tableId="<%= gov.va.med.pharmacy.peps.presentation.common.action.SearchAction.SEARCH_RESULTS_TABLE %>"
        dataOgnl="items"
        fieldsOgnl="searchTemplate.printTemplate" /></fieldset>
<%-- </c:if > --%>
</s:if >
<c:set
    scope="page"
    var="returnResultsTitle"
    value="%{getText('link.results.return.title')}" />  
<%-- <s:set
    scope="page"
    name="returnResultsTitle"
    value="%{getText('link.results.return.title')}" />   --%>
<a name="simpleResultsReturn"
    id="simpleResultsReturn"
    title="${returnResultsTitle}"
    href="#simpleResults"></a>
<script type="text/javascript">
    document.getElementById("simpleResults").focus();
</script>