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
    
<peps:form id="manageSearchTemplates">
<br /><span class="column"></span><div class="twoColumn">
<fieldset><legend>Search Templates</legend> 
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
                items="${templates}"
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
                <td><peps:dataField
                    item="${template}"
                    key="selected"
                    ognl="templates[${index}].selected"
                    labelPosition="none" 
                    cssClass="columnCheckbox" /></td>
                <td><peps:link
                    item="${template}"
                    key="displayable.name"                    
                    eventId="retrieve"
                    currentSearchTemplateId="${currentTemplateId}" /></td>
                <td><peps:text
                    item="${template}"
                    key="template.type"
                    ognl="templates[${index}].templateType" /></td>
                <td><peps:text
                    item="${template}"
                    key="notes" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="row"><span class="column">
    <script language="Javascript">
    //<!--
    var confirmMsg = "<spring:message code='template.removal.confirmation' />";
    //-->
    </script>
    <peps:submit
        key="button.remove" onclick="return confirm(confirmMsg)" /> </span> <span class="column"> <peps:submit
        key="button.setAsDefault" /> </span> <span class="column"> <peps:submit
        key="button.unDefault" /> </span></div>
    <div class="row"><peps:link
        eventId="advanced"
        text="Back to Advanced Search" /></div>
</fieldset>
</div>
</peps:form>

