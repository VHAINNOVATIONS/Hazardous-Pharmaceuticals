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

<peps:form id="dosageForm">
    
    <div class="twoColumn">
        <peps:dataField item="${item}" key="dosage.form.name" />
        <peps:dataField item="${item}" key="exclude.from.dosage.chks" cssClass="matchwidth" />
        <peps:text item="${item}" key="verb" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="other.language.verb" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="preposition" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="other.language.preposition" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="conjunction" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="local.med.routes" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="df.nouns" label="true" cssClass="readonlynational" />
    </div>
    <div class="twoColumn">
        <peps:dataField item="${item}" key="df.dispense.units.per.dose" />
      
    </div>
    <div class="horizontalspacer panel"></div>
    <div class="panel">
         <peps:dataField item="${item}" key="df.units" managedDataAddLink="false"/>
    </div>
    <div class="buttonspacer"></div> 
    <peps:dataField item="${item}" key="psr.name" />
    <br />
    <tiles:insertAttribute name="buttons" />
</peps:form>