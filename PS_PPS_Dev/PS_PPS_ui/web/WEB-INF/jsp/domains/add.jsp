<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
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

ManagedItemVo parentItem = (ManagedItemVo)request.getAttribute("parentItem");

gov.va.med.pharmacy.peps.common.vo.EntityType parentEntityType = null;

if (parentItem != null) {
    parentEntityType = parentItem.getEntityType();
}

gov.va.med.pharmacy.peps.common.vo.EntityType myEntityType = ((ManagedItemVo)request.getAttribute("item")).getEntityType();
if (!myEntityType.equals(parentEntityType)) {
                    
%>
<peps:form id="managedDomainAdd">

<c:set
    scope="page"
    var="itemId"
    value="${item.id}" />
<c:set
    scope="page"
    var="itemTypeString"
    value="${item.entityType.name}" />
   
<H1>Add <peps:label key="${itemTypeString}"/></H1>


<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>

<div class="horizontalspacer"></div>


<c:forEach varStatus="details" var="field" items="${item.allWizardFields}">

<c:set
	scope="page"
	var="keyString"
	value="${field.key}" /> 
<c:choose>
    <c:when test="${item.entityType.fieldKey == 'local.drug.text' && keyString == 'text'}">  
		<peps:dataField item="${item}" key="${keyString}" rows="4" cols="10" managedDataAddLink="false" />
    </c:when>    
     <c:when test="${item.entityType.fieldKey == 'ingredient'}">
        <peps:dataField item="${item}" key="${keyString}" managedDataAddLink="true" />
    </c:when>
    <c:otherwise>
        <peps:dataField item="${item}" key="${keyString}" managedDataAddLink="false" />
     </c:otherwise>
</c:choose>         
</c:forEach>

<c:if test="${item.entityType.fieldKey != 'order.unit' && item.entityType.fieldKey != 'manufacturer' 
&& item.entityType.fieldKey != 'package.type' && item.entityType.fieldKey != 'special.handling' }">
	<peps:dataField item="${item}" key="psr.name" />
</c:if>
<br />
<tiles:insertAttribute name="buttons" />

</peps:form>
<% 
}
else {
%>
<peps:form id="managedDomainAdd">

<c:set
    scope="page"
    var="itemId"
    value="${item.id}" />
<c:set
    scope="page"
    var="itemTypeString"
    value="${parentItem.entityType.name}" />
       
<H1>Add <spring:message code="parent.${item.entityType.fieldKey}.name"/></H1>
<%-- <H1>Add <peps:label key="parent.${item.entityType.fieldKey}"/></H1> --%>

<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>

<div class="horizontalspacer"></div>


<c:forEach varStatus="details" var="field" items="${item.allWizardFields}">

<c:set
    scope="page"
    var="keyString"
    value="${field.key}" /> 
<c:choose>
     <c:when test="${item.entityType.fieldKey == 'local.drug.text' && keyString == 'text'}">  
        <peps:dataField item="${item}" key="${keyString}" rows="4" cols="10" managedDataAddLink="false" />
    </c:when>    
     <c:when test="${item.entityType.fieldKey == 'ingredient'}">
        <peps:dataField item="${item}" key="${keyString}" managedDataAddLink="true" />
    </c:when>
    <c:otherwise>
        <peps:dataField item="${item}" key="${keyString}" managedDataAddLink="false" />
     </c:otherwise>
</c:choose>           

</c:forEach>
<br />
<tiles:insertAttribute name="buttons" />

</peps:form>
<% } %>