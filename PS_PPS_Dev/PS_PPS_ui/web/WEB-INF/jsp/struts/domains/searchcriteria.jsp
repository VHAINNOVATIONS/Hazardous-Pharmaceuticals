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
    
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>

<peps:form id="managedDomainSearchCriteria">
<s:if test="searchTemplate != null">
<%-- <c:if test="searchTemplate != null"> --%>
<div class="row">
<!-- added this hidden strength text field so that search submit would activate with Enter Key -->
    <s:textfield
        key="searchTemplate.searchCriteria.strength"
        label="%{getLocalizedFieldName('strength')}"
        labelposition="none"
        size="1"
        cssClass="hidden" /> 
    <span class="column"><s:select
        key="searchTemplate.searchCriteria.searchTerms[0].searchField.key"
        value="searchTemplate.searchCriteria.searchTerms[0].searchField.key"
        list="searchTemplate.searchCriteria.searchFields"
        listKey="key"
        listValue="getLocalizedName(locale)"
        label="%{getLocalizedFieldName('search.fields')}"
        title="%{getLocalizedFieldDescription('search.fields')}"
        labelposition="top" /></span>
    <span class="column">
    <s:textfield
        key="searchTemplate.searchCriteria.searchTerms[0].value"
        label="For"
        labelposition="top"
        title="For" /></span> 
    <span class="column">
    <s:checkboxlist
        key="searchTemplate.searchCriteria.itemStatus"
        list="@gov.va.med.pharmacy.peps.common.vo.ItemStatus@getValues()"
        listValue="getText('ItemStatus.' + name())"
        label="%{getLocalizedFieldName('item.status')}"
        labelposition="top"
        title="%{getLocalizedFieldDescription('item.status')}"
        cssClass="checkbox" /></span> 
    <span class="column">
    <s:checkboxlist
        key="searchTemplate.searchCriteria.requestStatus"
        list="@gov.va.med.pharmacy.peps.common.vo.RequestItemStatus@displayedValues()"
        listValue="getText('RequestItemStatus.' + name())"
        label="%{getLocalizedFieldName('request.item.status')}"
        labelposition="top"
        title="%{getLocalizedFieldDescription('request.item.status')}"
        cssClass="checkbox" /></span>
    <br />
    <br />

    <span class="column floatMiddle">
        <peps:submit
        key="button.search"
        event="search" /></span>
        <span class="column floatRight">
        </span>
    </div>
    <div class="bodyheaderspacer"></div>
</s:if>
<%-- </c:if> --%>
</peps:form>
