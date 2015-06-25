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

<peps:form id="medicationInstructionForm">
        <peps:dataField item="${item}" key="value" />
        <peps:dataField item="${item}" key="intended.use" />
        <peps:dataField item="${item}" key="instructions" />
        <peps:dataField item="${item}" key="additional.instruction" />
        <peps:dataField item="${item}" key="med.instruction.schedule" />
        <peps:dataField item="${item}" key="med.instruction.expansion" />
        <peps:dataField item="${item}" key="frequency" /> 
        <peps:dataField item="${item}" key="default.admin.times" />
        <peps:dataField item="${item}" key="local.medication.route" />
        <peps:dataField item="${item}" key="med.instruction.synonym" />
        <peps:dataField item="${item}" key="other.language.expansion" />
        <peps:dataField item="${item}" key="plural" />
        <peps:dataField item="${item}" key="med.instruction.ward.multiple" />

    <br />
    <tiles:insertAttribute name="buttons" />
</peps:form>