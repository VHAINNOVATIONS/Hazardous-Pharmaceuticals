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

<peps:form id="localMedRouteForm">
        <peps:dataField item="${item}" key="value" />
        <peps:dataField item="${item}" key="abbreviation" />
        <peps:dataField item="${item}" key="iv.flag" cssClass="aligncheckbox214 labelreadonly" />
        <peps:dataField item="${item}" key="prompt.for.injection.site" cssClass="aligncheckbox214 labelreadonly" />
        <peps:dataField item="${item}" key="display.on.ivp" cssClass="aligncheckbox214 labelreadonly" />
        <peps:dataField item="${item}" key="outpatient.expansion" />
        <peps:dataField item="${item}" key="other.language.expansion" />
        <peps:dataField item="${item}" key="package.usages" cssClass="itemnumber" />
        <peps:dataField item="${item}" key="standard.med.route" />

    <br />
    <tiles:insertAttribute name="buttons" />
</peps:form>