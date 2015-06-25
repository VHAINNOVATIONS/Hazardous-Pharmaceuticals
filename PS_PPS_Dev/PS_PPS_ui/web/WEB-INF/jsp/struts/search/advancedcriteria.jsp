<%-- <%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>

<peps:form id="advancedsearch">
    <div class="row">
        <span class="fourColumn"><peps:submit
            key="button.retrieveTemplates"
            event="manageSearchTemplates" cssStyle="width: 165px;"/> 
            <peps:label 
             value="OR" />
             
            <br /><br />&nbsp;&nbsp;<s:label cssClass="addproducttitle"
             value="Search for:" /></span> 
                     <span class="addproducttitle"><s:label 
            value="%{templateName}" />
        </span> 
    </div>
    <div class="row">
           <s:if test="%{environment.local}">
                <span class="column"><s:radio
                key="searchTemplate.searchCriteria.localUse"
                list="@gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType@values()"
                listKey="name()"
                listValue="getText('LocalUseSearchType.' + name())"
                label="%{getLocalizedFieldName('local.use')}"
                labelposition="top"
                title="%{getLocalizedFieldDescription('local.use')}" />
               </span>
           </s:if> 
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
               
        </div>
    <div class="row">

        <span class="column">
            <s:if test="%{searchMode == null}"><s:select
                key="searchTemplate.entityType"
                list="@gov.va.med.pharmacy.peps.common.vo.EntityType@entities()"
                listValue="getText('EntityType.' + name())"
                labelposition="none"
                title="%{getLocalizedFieldDescription('entity.type')}"
                cssClass="comboBox"
                onchange="submitButton = 'entityType'; this.form.submit();" /></s:if> 
            <s:else><s:select
                id="searchTemplate.entityType_disabled"
                name="searchTemplate.entityType_disabled"
                value="searchMode"
                list="@gov.va.med.pharmacy.peps.common.vo.EntityType@entities()"
                listValue="getText('EntityType.' + name())"
                labelposition="none"
                title="%{getLocalizedFieldDescription('entity.type')}"
                cssClass="comboBox"
                onchange="submitButton = 'entityType'; this.form.submit();"
                disabled="true" />
                <s:hidden
                id="searchTemplate.entityType"
                name="searchTemplate.entityType"
                value="%{searchMode}" /></s:else> 
        </span> 
        <span class="column"><s:radio
                key="searchTemplate.searchCriteria.advancedAndSearch"
                list="advancedAndSearch.options"
                label="%{advancedAndSearch.label}"
                labelposition="top"
                title="%{advancedAndSearch.label}" />
        </span>

        <s:hidden
            id="changed"
            name="changed"
            value="%{changed}" /> 
    </div>

        <s:iterator
            value="searchTemplate.searchCriteria.searchTerms"
            status="iterStatus">
            <s:set
                scope="page"
                name="index"
                value="#iterStatus.index" />
        <div class="row">
        <s:if test="%{#attr.index == 0}">
        <span class="column"><s:label value="where" /></span>
        </s:if>
        <s:if test="%{#attr.index > 0}">
        <s:if test="%{searchTemplate.searchCriteria.advancedAndSearch}">
            <span class="column">&nbsp;&nbsp;<s:label value="and" />&nbsp;&nbsp;</span>
        </s:if>
        <s:else>
            <span class="column">&nbsp;&nbsp;<s:label value="or" />&nbsp;&nbsp;</span>
        </s:else>
        
        
        </s:if>
        <span class="column" id="searchFields"><s:select
            key="%{'searchTemplate.searchCriteria.searchTerms[' + #attr.index + '].searchField.key'}"
            value="%{searchTemplate.searchCriteria.searchTerms[#attr.index].searchField.key}"
            list="searchTemplate.searchCriteria.searchFields"
            listKey="key"
            listValue="getLocalizedName(locale)"
            label="%{getLocalizedFieldName('search.fields')}"
            title="%{getLocalizedFieldDescription('search.fields')}"
            labelposition="none"
            cssClass="comboBox"
            onchange="submitButton = 'advancedSearch'; this.form.submit();" /></span>

        <span class="column" id="searchTypes"><s:select
            id="%{'searchTemplate.searchCriteria.searchTerms[' + #attr.index + '].searchType'}"
            name="%{'searchTemplate.searchCriteria.searchTerms[' + #attr.index + '].searchType'}"
            list="@gov.va.med.pharmacy.peps.common.vo.SearchType@values()"
            listValue="getText('SearchType.' + name())"
            title="Search Type"
            labelposition="none"
            cssClass="comboBox"/> </span>
        <s:set
            scope="page"
            name="keyString"
            value="%{searchTemplate.searchCriteria.searchTerms[#attr.index].searchField.fieldKey.key}" />
        
        <span class="column" id="searchValues"><peps:dataField
            key="${keyString}"
            ognl="searchTemplate.searchCriteria.searchTerms[${index}].value"
            disabled="false"
            isAdvancedSearchContext="true"
            postValue="true"
            labelPosition="none"
            managedDataAddLink="false"
            multiselect="false" />
        </span>
    </div>
    </s:iterator>
    <div class="row"><peps:submit
        key="button.add"
        event="add" /></div>
    <div class="row"><span class="floatMiddle"><peps:submit
        key="button.search"
        event="search" /></span>
    <div class="row"><peps:resumeFlowLink
        eventId="simple"
        text="Back to Simple Search" /></div>
    </div>

</peps:form>
 --%>







<!-- SPRING------------------------------------------------------------- -->

<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>	

<peps:form id="advancedsearch">
    <div class="row">
        <span class="fourColumn"><peps:submit
            key="button.retrieveTemplates"
            event="manageSearchTemplates" cssStyle="width: 165px;"/> 
            <peps:label 
             value="OR" />
             
            <br /><br />&nbsp;&nbsp;
            
            <s:label cssClass="addproducttitle"
             value="Search for:" />

<%--             <peps:label cssClass="addproducttitle" --%>
<!--              value="Search for:" /> -->
                         
             </span> 
                     <span class="addproducttitle"><s:label 
            value="%{templateName}" />
        </span> 
    </div>
    <div class="row">
           <s:if test="%{environment.local}">
                <span class="column"><s:radio
                key="searchTemplate.searchCriteria.localUse"
                list="@gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType@values()"
                listKey="name()"
                listValue="getText('LocalUseSearchType.' + name())"
                label="%{getLocalizedFieldName('local.use')}"
                labelposition="top"
                title="%{getLocalizedFieldDescription('local.use')}" />
               </span>
           </s:if> 
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
               
        </div>
    <div class="row">

        <span class="column">
            <s:if test="%{searchMode == null}"><s:select
                key="searchTemplate.entityType"
                list="@gov.va.med.pharmacy.peps.common.vo.EntityType@entities()"
                listValue="getText('EntityType.' + name())"
                labelposition="none"
                title="%{getLocalizedFieldDescription('entity.type')}"
                cssClass="comboBox"
                onchange="submitButton = 'entityType'; this.form.submit();" /></s:if> 
            <s:else><s:select
                id="searchTemplate.entityType_disabled"
                name="searchTemplate.entityType_disabled"
                value="searchMode"
                list="@gov.va.med.pharmacy.peps.common.vo.EntityType@entities()"
                listValue="getText('EntityType.' + name())"
                labelposition="none"
                title="%{getLocalizedFieldDescription('entity.type')}"
                cssClass="comboBox"
                onchange="submitButton = 'entityType'; this.form.submit();"
                disabled="true" />
                <s:hidden
                id="searchTemplate.entityType"
                name="searchTemplate.entityType"
                value="%{searchMode}" /></s:else> 
        </span> 
        <span class="column"><s:radio
                key="searchTemplate.searchCriteria.advancedAndSearch"
                list="advancedAndSearch.options"
                label="%{advancedAndSearch.label}"
                labelposition="top"
                title="%{advancedAndSearch.label}" />
        </span>

        <s:hidden
            id="changed"
            name="changed"
            value="%{changed}" /> 
    </div>

        <s:iterator
            value="searchTemplate.searchCriteria.searchTerms"
            status="iterStatus">
            <s:set
                scope="page"
                name="index"
                value="#iterStatus.index" />
        <div class="row">
        <s:if test="%{#attr.index == 0}">
        <span class="column"><s:label value="where" /></span>
        </s:if>
        <s:if test="%{#attr.index > 0}">
        <s:if test="%{searchTemplate.searchCriteria.advancedAndSearch}">
            <span class="column">&nbsp;&nbsp;<s:label value="and" />&nbsp;&nbsp;</span>
        </s:if>
        <s:else>
            <span class="column">&nbsp;&nbsp;<s:label value="or" />&nbsp;&nbsp;</span>
        </s:else>
        
        
        </s:if>
        <span class="column" id="searchFields"><s:select
            key="%{'searchTemplate.searchCriteria.searchTerms[' + #attr.index + '].searchField.key'}"
            value="%{searchTemplate.searchCriteria.searchTerms[#attr.index].searchField.key}"
            list="searchTemplate.searchCriteria.searchFields"
            listKey="key"
            listValue="getLocalizedName(locale)"
            label="%{getLocalizedFieldName('search.fields')}"
            title="%{getLocalizedFieldDescription('search.fields')}"
            labelposition="none"
            cssClass="comboBox"
            onchange="submitButton = 'advancedSearch'; this.form.submit();" /></span>

        <span class="column" id="searchTypes"><s:select
            id="%{'searchTemplate.searchCriteria.searchTerms[' + #attr.index + '].searchType'}"
            name="%{'searchTemplate.searchCriteria.searchTerms[' + #attr.index + '].searchType'}"
            list="@gov.va.med.pharmacy.peps.common.vo.SearchType@values()"
            listValue="getText('SearchType.' + name())"
            title="Search Type"
            labelposition="none"
            cssClass="comboBox"/> </span>
        <s:set
            scope="page"
            name="keyString"
            value="%{searchTemplate.searchCriteria.searchTerms[#attr.index].searchField.fieldKey.key}" />
        
        <span class="column" id="searchValues"><peps:dataField
            key="${keyString}"
            ognl="searchTemplate.searchCriteria.searchTerms[${index}].value"
            disabled="false"
            isAdvancedSearchContext="true"
            postValue="true"
            labelPosition="none"
            managedDataAddLink="false"
            multiselect="false" />
        </span>
    </div>
    </s:iterator>
    <div class="row"><peps:submit
        key="button.add"
        event="add" /></div>
    <div class="row"><span class="floatMiddle"><peps:submit
        key="button.search"
        event="search" /></span>
    <div class="row"><peps:resumeFlowLink
        eventId="simple"
        text="Back to Simple Search" /></div>
    </div>

</peps:form>