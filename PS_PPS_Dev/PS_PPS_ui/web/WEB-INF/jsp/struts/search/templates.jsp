
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
            <%-- <c:forEach 
                items="templates"
                varStatus="details">
                <spring:message
                    scope="page"
                    var="index"
                    code="#details.index" />
                <spring:message
                    scope="page"
                    var="currentTemplateId"
                    code="templates[#details.index].getId()" />
            <c:choose>       
                <c:when test="#attr.index % 2 == 0">
                    <tr class="odd">
                </c:when>
                <c:otherwise>
                    <tr class="even">
                </c:otherwise>
			</c:choose> --%>
			<s:iterator
                value="templates"
                status="details">
                <s:set
                    scope="page"
                    name="index"
                    value="#details.index" />
                <s:set
                    scope="page"
                    name="currentTemplateId"
                    value="templates[#details.index].getId()" />
                <s:if test="#attr.index % 2 == 0">

                    <tr class="odd">
                </s:if>
                <s:else>
                    <tr class="even">
                </s:else>
                <td><peps:dataField
                    key="selected"
                    ognl="templates[${index}].selected"
                    labelPosition="none" 
                    cssClass="columnCheckbox" /></td>
                <td><peps:resumeFlowLink
                    key="displayable.name"
                    ognl="templates[${index}].displayableName"
                    eventId="retrieve"
                    currentSearchTemplateId="${currentTemplateId}" /></td>
                <td><peps:text
                    key="template.type"
                    ognl="templates[${index}].templateType" /></td>
                <td><peps:text
                    key="notes"
                    ognl="templates[${index}].notes" /></td>
                </tr>
                </s:iterator>
            <%-- </c:forEach> --%>
        </tbody>
    </table>
    <div class="row"><span class="column">
    <script language="Javascript">
    //<!--
    var confirmMsg = "<s:text name='template.removal.confirmation' />";
    //-->
    </script>
    <peps:submit
        key="button.remove"
        event="remove" onclick="return confirm(confirmMsg)" /> </span> <span class="column"> <peps:submit
        key="button.setAsDefault"
        event="default" /> </span> <span class="column"> <peps:submit
        key="button.unDefault"
        event="undefault" /> </span></div>
    <div class="row"><peps:resumeFlowLink
        eventId="advanced"
        text="Back to Advanced Search" /></div>
</fieldset>
</div>
</peps:form>

