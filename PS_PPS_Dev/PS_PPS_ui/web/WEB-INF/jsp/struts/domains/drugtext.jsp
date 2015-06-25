<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>

<%--
<s:set
    scope="page"
    name="itemId"
    value="item.id" />
<s:set
    scope="page"
    name="itemTypeString"
    value="itemType.toFieldKey()" />
 --%>
<spring:message
    scope="page"
    var="itemId"
    code="item.id" />
<spring:message
    scope="page"
    var="itemTypeString"
    code="itemType.toFieldKey()" />
     
<H1>Add <peps:label key="${itemTypeString}"/></H1>

<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>

<peps:form id="drugTextForm">
    <peps:model ognlPrefix="item">
        <peps:dataField key="value" />        
        <%-- <s:if test="%{environment.national}"> --%>
        <c:if test="%{environment.national}">
            <peps:dataField key="text.national" />
        <%-- </s:if> --%>
        </c:if>
        <%-- <s:if test="%{environment.local}"> --%>
        <c:if test="%{environment.local}">
            <peps:dataField key="text.local" />  
        <%-- </s:if> --%>
        </c:if>
        <peps:dataField key="drug.text.synonyms" />
    </peps:model>
	<br />
    <tiles:insertAttribute name="buttons" />
</peps:form>