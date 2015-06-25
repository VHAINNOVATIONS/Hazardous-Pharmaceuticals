<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>
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
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
<%

//Code to identify if the parentItem of this entity is the same as the current, if so, we need to tweak the page a bit

gov.va.med.pharmacy.peps.common.vo.ManagedItemVo parentItem = OgnlUtility
    .findValue(request, "parentEntityType");

gov.va.med.pharmacy.peps.common.vo.EntityType parentEntityType = null;

if (parentItem != null) {
    parentEntityType = parentItem.getEntityType();
}

gov.va.med.pharmacy.peps.common.vo.EntityType myEntityType = ((gov.va.med.pharmacy.peps.common.vo.ManagedItemVo)gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility
.findValue(request, "item")).getEntityType();
if (!myEntityType.equals(parentEntityType)) {
                    
%>
<peps:form id="managedDomainAdd">


<s:set
    scope="page"
    name="itemId"
    value="item.id" />
<s:set
    scope="page"
    name="itemTypeString"
    value="itemType.toFieldKey()" />
<%-- 
<spring:message
    scope="page"
    var="itemId"
    code="item.id" />
<spring:message
    scope="page"
    var="itemTypeString"
    code="itemType.toFieldKey()" />
 --%>    
<H1>Add <peps:label key="${itemTypeString}"/></H1>

<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>

<div class="horizontalspacer"></div>

<s:iterator
    value="wizardFields"
    status="details">
<%-- <c:forEach varStatus="details" items="wizardFields"> --%>
<s:set
    scope="page"
    name="keyString"
    value="wizardFields[#details.index].getKey()" />
<%-- 
<spring:message
	scope="page"
	var="keyString"
	code="wizardFields[#details.index].getKey()" /> 
<c:choose>
--%>
    <s:if test="#attr.itemTypeString == 'local.drug.text' && #attr.keyString == 'text'">
    <%-- <c:when test="#attr.itemTypeString == 'local.drug.text' && #attr.keyString == 'text'"> --%>  
		<peps:dataField key="${keyString}" ognlPrefix="item" rows="4" cols="10" managedDataAddLink="false" />
    </s:if>
    <%-- </c:when>--%>
    <s:elseif test="#attr.itemTypeString == 'ingredient'">
     <%-- <c:when test="#attr.itemTypeString == 'ingredient'">--%>
        <peps:dataField key="${keyString}" ognlPrefix="item" managedDataAddLink="true" />  
    </s:elseif> 
    <%-- </c:when> --%>
    <s:else>
    <%-- <c:otherwise> --%>
        <peps:dataField key="${keyString}" ognlPrefix="item" managedDataAddLink="false" />  
    </s:else> 
     <%-- </c:otherwise> --%>          
</s:iterator>
<%-- </c:forEach> --%>
<br />
<tiles:insertAttribute name="buttons" />

</peps:form>
<% 
}
else {
%>
<peps:form id="managedDomainAdd">

<s:set
    scope="page"
    name="itemId"
    value="item.id" />
<s:set
    scope="page"
    name="itemTypeString"
    value="itemType.toFieldKey()" />
<%-- 
<spring:message
    scope="page"
    var="itemId"
    code="item.id" />
<spring:message
    scope="page"
    var="itemTypeString"
    code="itemType.toFieldKey()" />
 --%>       
<H1>Add <peps:label key="parent.${itemTypeString}"/></H1>

<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>

<div class="horizontalspacer"></div>

<s:iterator
    value="wizardFields"
    status="details">
<%-- <c:forEach varStatus="details" items="wizardFields"> --%>

<s:set
    scope="page"
    name="keyString"
    value="wizardFields[#details.index].getKey()" />
<%-- 
<spring:message
	scope="page"
	var="keyString"
	code="wizardFields[#details.index].getKey()" /> 
<c:choose>
	
--%>
	
    <s:if test="#attr.itemTypeString == 'local.drug.text' && #attr.keyString == 'text'">
    <%-- <c:when test="#attr.itemTypeString == 'local.drug.text' && #attr.keyString == 'text'"> --%> 
        <peps:dataField key="${keyString}" ognlPrefix="item" rows="4" cols="10" managedDataAddLink="false" />
    </s:if>
   	<%-- </c:when>--%>
    <s:elseif test="#attr.itemTypeString == 'ingredient'">
    <%-- <c:when test="#attr.itemTypeString == 'ingredient'">--%>
        <peps:dataField key="${keyString}" ognlPrefix="item" managedDataAddLink="false" />  
    </s:elseif> 
    <%-- </c:when> --%>
    <s:else>
    <%-- <c:otherwise> --%>
        <peps:dataField key="${keyString}" ognlPrefix="item" managedDataAddLink="false" />  
    </s:else>
    <%-- </c:otherwise></c:choose> --%>           
</s:iterator>
<%-- </c:forEach> --%>
<br />
<tiles:insertAttribute name="buttons" />

</peps:form>
<% } %>