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

<peps:form id="drugClassCodeForm">
    <peps:model ognlPrefix="item">
        <peps:dataField key="code" />
        <peps:dataField key="classification" />
        <peps:dataField key="classification.type" />
        <peps:dataField key="description" />
        <peps:dataField key="parent.drug.class" />
    </peps:model>

    <br />
    <tiles:insertAttribute name="buttons" />
</peps:form>

<%
} else {
  
    %>
    
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
   
<H1>Add <peps:label key="parent.${itemTypeString}"/></H1>

<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>

<peps:form id="drugClassCodeForm">
    <peps:model ognlPrefix="item">
        <peps:dataField key="code" />
        <peps:dataField key="classification" />
        <peps:dataField key="classification.type" />
        <peps:dataField key="description" />
        <peps:dataField key="parent.drug.class" managedDataAddLink="false"/>
    </peps:model>

    <br />
    <tiles:insertAttribute name="buttons" />
</peps:form>
    
    <%
}
%>