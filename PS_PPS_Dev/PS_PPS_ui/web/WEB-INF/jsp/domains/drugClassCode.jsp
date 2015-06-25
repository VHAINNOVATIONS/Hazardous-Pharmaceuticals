<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib 
    prefix="spring"
    uri="http://www.springframework.org/tags"%> 
<%@ taglib 
    prefix="form"
    uri="http://www.springframework.org/tags/form"%> 
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>

<%
//Code to identify if the parentItem of this entity is the same as the current, if so, we need to tweak the page a bit

ManagedItemVo parentItem = (ManagedItemVo)(request.getAttribute("parentItem"));
ManagedItemVo item = (ManagedItemVo)(request.getAttribute("item"));
    

EntityType parentEntityType = null;

if (parentItem != null) {
    parentEntityType = parentItem.getEntityType();
}

EntityType myEntityType = item.getEntityType();

if (!myEntityType.equals(parentEntityType)) {
                    
                    %>
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

<peps:form id="drugClassCodeForm">    
        <peps:dataField item="${item}" key="code" />
        <peps:dataField item="${item}" key="classification" />
        <peps:dataField item="${item}" key="classification.type" />
        <peps:dataField item="${item}" key="description" />
        <peps:dataField item="${item}" key="parent.drug.class" />
        <div class="buttonspacer"></div> 
        <peps:dataField item="${item}" key="psr.name" />

    <br />
    <tiles:insertAttribute name="buttons" />
</peps:form>

<%
} else {
  
    %>
    
<c:set
    scope="page"
    var="itemId"
    value="${item.id}" />
<c:set
    scope="page"
    var="itemTypeString"
    value="${item.entityType.name}" />

<H1>Add <spring:message code="parent.${item.entityType.fieldKey}.name"/></H1>   
<%-- <H1>Add <peps:label key="parent.${itemTypeString}"/></H1> --%>

<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>

<peps:form id="drugClassCodeForm">
        <peps:dataField item="${item}" key="code" />
        <peps:dataField item="${item}" key="classification" />
        <peps:dataField item="${item}" key="classification.type" />
        <peps:dataField item="${item}" key="description" />
        <peps:dataField item="${item}" key="parent.drug.class" managedDataAddLink="false"/>

    <br />
    <tiles:insertAttribute name="buttons" />
</peps:form>
    
    <%
}
%>